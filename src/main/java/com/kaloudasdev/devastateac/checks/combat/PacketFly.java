package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.LinkedList;
import java.util.Queue;

public class PacketFly extends Check {
    private Queue<Double> speedPattern = new LinkedList<>();
    
    public PacketFly() { super("PacketFly", "Detects packet fly hacks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        if (Math.abs(data.getLastDeltaY()) > 1.5 && data.getAirTicks() > 5) {
            speedPattern.add(data.getLastDeltaY());
            if (speedPattern.size() > 10) {
                double avg = speedPattern.stream().mapToDouble(d -> d).average().orElse(0);
                if (avg > 2.0) {
                    flag(player, data, String.format("avgPacketSpeed=%.2f", avg));
                    speedPattern.clear();
                }
            }
        }
        if (speedPattern.size() > 20) speedPattern.poll();
    }
}
