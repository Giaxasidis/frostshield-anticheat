package com.giaxasidis.frostshield.checks.packet;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class InvalidMove extends Check {
    public InvalidMove() { super("InvalidMove", "Detects impossible movement", 8); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null || e.getFrom() == null) return;
        
        double deltaX = Math.abs(e.getTo().getX() - e.getFrom().getX());
        double deltaZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
        double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
        double deltaXZ = Math.hypot(deltaX, deltaZ);
        
        if (deltaXZ > 20 || deltaY > 20) {
            flag(player, data, String.format("dxz=%.2f dy=%.2f", deltaXZ, deltaY));
        }
    }
}
