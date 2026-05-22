package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

public class Scaffold extends Check {
    private float lastYaw = 0;
    private int suspiciousPlacements = 0;
    
    public Scaffold() { super("Scaffold", "Detects scaffold / tower hacks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockPlaceEvent)) return;
        
        float currentYaw = player.getLocation().getYaw();
        float yawDiff = Math.abs(currentYaw - lastYaw);
        
        if (yawDiff < 3 && lastYaw != 0 && !player.isOnGround() && data.getAirTicks() > 8) {
            suspiciousPlacements++;
            if (suspiciousPlacements > 15) {
                flag(player, data, String.format("yawStable=%.2f airTicks=%d", yawDiff, data.getAirTicks()));
                suspiciousPlacements = 0;
            }
        } else {
            suspiciousPlacements = Math.max(0, suspiciousPlacements - 1);
        }
        
        lastYaw = currentYaw;
    }
}
