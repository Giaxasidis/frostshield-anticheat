package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Criticals extends Check {
    public Criticals() { super("Criticals", "Detects critical hit hacks", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        boolean isCritical = !player.isOnGround() && !player.isInsideVehicle() && !player.isInWater();
        
        if (isCritical && data.getAirTicks() > 5 && player.getFallDistance() < 0.1) {
            flag(player, data, String.format("airTicks=%d fallDist=%.2f", data.getAirTicks(), player.getFallDistance()));
        }
    }
}
