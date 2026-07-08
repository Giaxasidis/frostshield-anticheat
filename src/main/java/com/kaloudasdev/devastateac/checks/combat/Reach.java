package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Reach extends Check {
    public Reach() { super("Reach", "Detects extended reach", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        double distance = player.getLocation().distance(e.getEntity().getLocation());
        double maxReach = 5.2;
        
        if (player.isInsideVehicle()) maxReach = 6.0;
        
        if (distance > maxReach && distance < 8.0) {
            flag(player, data, String.format("dist=%.2f max=%.2f", distance, maxReach));
        }
    }
}
