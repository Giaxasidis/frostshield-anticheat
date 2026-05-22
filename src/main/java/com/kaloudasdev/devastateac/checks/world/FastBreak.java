package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class FastBreak extends Check {
    private long lastBreakTime = 0;
    
    public FastBreak() { super("FastBreak", "Detects fast break hacks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockBreakEvent)) return;
        
        long now = System.currentTimeMillis();
        if (lastBreakTime != 0) {
            long diff = now - lastBreakTime;
            if (diff < 20) {
                flag(player, data, String.format("breakDelay=%dms", diff));
            }
        }
        lastBreakTime = now;
    }
}
