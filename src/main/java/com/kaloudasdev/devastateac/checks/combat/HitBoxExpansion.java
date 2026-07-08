package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitBoxExpansion extends Check {
    private final java.util.Map<java.util.UUID, Integer> hitCount = new java.util.HashMap<>();
    
    public HitBoxExpansion() { super("HitBoxExpansion", "Detects expanded hitboxes", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        Player victim = (Player) e.getEntity();
        
        double distance = player.getLocation().distance(victim.getLocation());
        double eyeHeight = player.getEyeLocation().getY();
        double targetY = victim.getLocation().getY();
        double yDiff = Math.abs(eyeHeight - targetY);
        
        if (distance > 3.5 && yDiff > 2.8) {
            java.util.UUID uuid = player.getUniqueId();
            int count = hitCount.getOrDefault(uuid, 0) + 1;
            hitCount.put(uuid, count);
            
            if (count > 5) {
                flag(player, data, String.format("hitboxExpansion dist=%.2f yDiff=%.2f hits=%d", distance, yDiff, count));
                hitCount.put(uuid, 0);
            }
        } else {
            hitCount.entrySet().removeIf(entry -> entry.getValue() > 0);
        }
    }
}
