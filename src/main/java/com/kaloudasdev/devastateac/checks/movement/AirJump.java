package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class AirJump extends Check {
    public AirJump() { super("AirJump", "Detects extra jumps in air", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (player.isOnGround() || player.isInsideVehicle() || player.isDead()) return;
        if (data.getTeleportTicks() > 0 || data.getVelocityTicks() > 0) return;
        if (player.getAllowFlight()) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (deltaY > 0.8 && data.getAirTicks() > 5) {
            flag(player, data, String.format("airJump=%.4f airTicks=%d", deltaY, data.getAirTicks()));
        }
    }
}
