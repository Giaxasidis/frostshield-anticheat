package com.kaloudasdev.frostshield.checks.movement;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Prediction extends Check {
    public Prediction() { super("Prediction", "Detects unnatural movement patterns", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaX = e.getTo().getX() - e.getFrom().getX();
        double deltaZ = e.getTo().getZ() - e.getFrom().getZ();
        double horizontal = Math.hypot(deltaX, deltaZ);
        
        data.getSpeedHistory().add(horizontal);
        if (data.getSpeedHistory().size() > 10) {
            double avg = data.getSpeedHistory().stream().mapToDouble(d -> d).average().orElse(0);
            if (avg > 0.75 && horizontal < 0.01 && data.getAirTicks() > 5) {
                flag(player, data, String.format("avg=%.4f current=%.4f", avg, horizontal));
            }
        }
    }
}
