package com.kaloudasdev.frostshield.checks.movement;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Step extends Check {
    public Step() { super("Step", "Detects step hacks", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        if (data.getVelocityTicks() > 0 || data.getTeleportTicks() > 0) return;
        if (player.isInWater() || player.isSwimming()) return;
        
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (deltaY > 1.2 && deltaY < 1.6 && !player.isOnGround() && data.getAirTicks() > 5) {
            flag(player, data, String.format("step=%.2f", deltaY));
        }
    }
}
