package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiKnockback extends Check {
    private int suspiciousTicks = 0;
    
    public AntiKnockback() { super("AntiKnockback", "Detects anti-knockback", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageEvent)) return;
        EntityDamageEvent e = (EntityDamageEvent) event;
        
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            data.setVelocityTicks(5);
            if (data.getVelocityTicks() > 0) {
                data.setVelocityTicks(data.getVelocityTicks() - 1);
            }
        }
    }
}
