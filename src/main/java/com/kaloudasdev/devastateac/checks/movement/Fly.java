package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Fly extends Check {
    private double lastDeltaY = 0;
    private int suspiciousTicks = 0;
    
    public Fly() { super("Fly", "Detects fly hacks", 25); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (player.isFlying() || player.isInsideVehicle() || player.isGliding()) return;
        if (data.getTeleportTicks() > 0 || data.getVelocityTicks() > 0) return;
        if (player.isDead()) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (!player.isOnGround() && deltaY > 0 && deltaY < 0.01 && data.getAirTicks() > 40) {
            if (Math.abs(deltaY - lastDeltaY) < 0.0001) {
                suspiciousTicks++;
                if (suspiciousTicks > 15) {
                    flag(player, data, String.format("deltaY=%.6f airTicks=%d pattern=%d", deltaY, data.getAirTicks(), suspiciousTicks));
                    suspiciousTicks = 0;
                }
            } else {
                suspiciousTicks = Math.max(0, suspiciousTicks - 1);
            }
        }
        
        lastDeltaY = deltaY;
    }
}
