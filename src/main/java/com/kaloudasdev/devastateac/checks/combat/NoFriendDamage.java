package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoFriendDamage extends Check {
    private final java.util.Map<java.util.UUID, Integer> hitCount = new java.util.HashMap<>();
    
    public NoFriendDamage() { super("NoFriendDamage", "Detects no friend damage hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;
        
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        
        if (attacker.equals(victim)) return;
        
        double damage = e.getDamage();
        double expectedDamage = 1.0;
        
        if (damage < expectedDamage / 2 && damage > 0) {
            java.util.UUID uuid = attacker.getUniqueId();
            int count = hitCount.getOrDefault(uuid, 0) + 1;
            hitCount.put(uuid, count);
            if (count > 10) {
                flag(attacker, data, String.format("damage=%.2f expected=%.2f hits=%d", damage, expectedDamage, count));
                hitCount.put(uuid, 0);
            }
        } else {
            hitCount.entrySet().removeIf(entry -> entry.getValue() > 0);
            hitCount.put(attacker.getUniqueId(), Math.max(0, hitCount.getOrDefault(attacker.getUniqueId(), 0) - 1));
        }
    }
}
