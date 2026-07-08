package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoSlowdown extends Check {
    public NoSlowdown() { super("NoSlowdown", "Detects no slowdown while using items", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (!player.isBlocking() && !player.isHandRaised()) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null || e.getFrom() == null) return;
        
        double deltaX = Math.abs(e.getTo().getX() - e.getFrom().getX());
        double deltaZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
        double distance = Math.hypot(deltaX, deltaZ);
        
        double maxSpeed = 0.3;
        if (distance > maxSpeed) {
            flag(player, data, String.format("dist=%.4f max=%.4f usingItem=true", distance, maxSpeed));
        }
    }
}
