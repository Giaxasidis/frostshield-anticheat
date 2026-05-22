package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class Nuker extends Check {
    private int breakCount = 0;
    private long lastBreak = 0;
    
    public Nuker() { super("Nuker", "Detects nuker / fast break hacks", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockBreakEvent)) return;
        
        long now = System.currentTimeMillis();
        if (now - lastBreak < 100) {
            breakCount++;
            if (breakCount > 15) {
                flag(player, data, String.format("blocksPerSecond=%.2f", breakCount * (1000.0 / (now - lastBreak))));
                breakCount = 0;
            }
        } else {
            breakCount = 0;
        }
        lastBreak = now;
    }
}
