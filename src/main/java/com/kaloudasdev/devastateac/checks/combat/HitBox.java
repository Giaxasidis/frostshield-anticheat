package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitBox extends Check {
    public HitBox() { super("HitBox", "Detects hitbox modifier", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        double distance = player.getLocation().distance(e.getEntity().getLocation());
        double eyeHeight = player.getEyeLocation().getY();
        double targetY = e.getEntity().getLocation().getY();
        double yDiff = Math.abs(eyeHeight - targetY);
        
        if (distance > 3.5 && yDiff > 2.5) {
            flag(player, data, String.format("dist=%.2f yDiff=%.2f", distance, yDiff));
        }
    }
}
