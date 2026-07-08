package com.giaxasidis.frostshield.checks.world;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoTool extends Check {
    private final Map<UUID, Long> lastBreak = new HashMap<>();
    private int suspiciousSwitches = 0;
    
    public AutoTool() { super("AutoTool", "Detects auto tool hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (event instanceof BlockBreakEvent) {
            lastBreak.put(player.getUniqueId(), System.currentTimeMillis());
            suspiciousSwitches = 0;
        }
        
        if (event instanceof PlayerItemHeldEvent) {
            UUID uuid = player.getUniqueId();
            Long last = lastBreak.get(uuid);
            if (last != null && System.currentTimeMillis() - last < 10) {
                suspiciousSwitches++;
                if (suspiciousSwitches > 10) {
                    flag(player, data, "auto_tool_switch");
                    suspiciousSwitches = 0;
                }
            } else {
                suspiciousSwitches = Math.max(0, suspiciousSwitches - 1);
            }
        }
    }
}
