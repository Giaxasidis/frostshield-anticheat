package com.giaxasidis.frostshield.checks.world;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockBreakMonitor extends Check {
    
    private final Map<UUID, Integer> breakCount = new HashMap<>();
    private final Map<UUID, Long> lastBreak = new HashMap<>();
    
    public BlockBreakMonitor() { super("BlockBreakMonitor", "Monitors block breaking", 3); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent e = (BlockBreakEvent) event;
        
        UUID uuid = player.getUniqueId();
        Material blockType = e.getBlock().getType();
        long now = System.currentTimeMillis();
        Long last = lastBreak.get(uuid);
        
        if (last != null && now - last < 500) {
            int count = breakCount.getOrDefault(uuid, 0) + 1;
            breakCount.put(uuid, count);
            
            Logger.log("&8[FrostShield] &7Block break: &f" + player.getName() + 
                " &7broke &f" + blockType.name() + " &7(rate: &f" + (now - last) + "ms&7) &7count: &f" + count);
            
            DiscordLogger.logCheat(player.getName(), "FastBreak", count, 3, 
                "broke " + blockType.name() + " in " + (now - last) + "ms");
            
            if (count >= 3) {
                Logger.log("&c&l[FrostShield] &f" + player.getName() + 
                    " &cbanned for breaking " + count + " blocks in " + (now - last) + "ms");
                flag(player, data, String.format("fastBreak blocks=%d time=%dms", count, now - last));
                breakCount.remove(uuid);
            }
        } else {
            breakCount.put(uuid, 1);
            Logger.log("&8[FrostShield] &7Block break: &f" + player.getName() + 
                " &7broke &f" + blockType.name() + " &7(count: 1)");
        }
        
        lastBreak.put(uuid, now);
    }
}
