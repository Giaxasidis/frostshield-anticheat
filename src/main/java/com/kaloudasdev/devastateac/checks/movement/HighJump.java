package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class HighJump extends Check {
    public HighJump() { super("HighJump", "Detects high jump hacks", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        if (deltaY > 1.2 && !player.isOnGround() && data.getVelocityTicks() == 0) {
            flag(player, data, String.format("jump=%.2f", deltaY));
        }
    }
}
