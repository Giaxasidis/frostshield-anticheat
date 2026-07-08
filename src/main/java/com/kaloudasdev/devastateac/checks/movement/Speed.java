package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Speed extends Check {
    public Speed() { super("Speed", "Detects speed hacks", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (player.isFlying() || player.isInsideVehicle() || player.isGliding()) return;
        if (data.getTeleportTicks() > 0 || data.getVelocityTicks() > 0) return;
        
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to == null) return;
        
        double deltaX = Math.abs(to.getX() - from.getX());
        double deltaZ = Math.abs(to.getZ() - from.getZ());
        double distance = Math.hypot(deltaX, deltaZ);
        double maxSpeed = 0.8;
        
        if (distance > maxSpeed) {
            flag(player, data, String.format("dist=%.4f max=%.4f", distance, maxSpeed));
        }
    }
}
