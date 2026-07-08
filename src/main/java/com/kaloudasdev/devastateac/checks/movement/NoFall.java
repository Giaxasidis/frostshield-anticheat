package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall extends Check {
    public NoFall() { super("NoFall", "Detects no fall", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = e.getFrom().getY() - e.getTo().getY();
        if (deltaY > 6.0 && !player.isOnGround() && player.getFallDistance() < 1.0) {
            flag(player, data, String.format("fall=%.2f", deltaY));
        }
    }
}
