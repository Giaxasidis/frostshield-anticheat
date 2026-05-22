package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TriggerBot extends Check {
    private long lastAttack = 0;
    
    public TriggerBot() { super("TriggerBot", "Detects triggerbot", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        long now = System.currentTimeMillis();
        if (lastAttack != 0) {
            long diff = now - lastAttack;
            if (diff < 50 && diff > 20) {
                flag(player, data, String.format("delay=%dms", diff));
            }
        }
        lastAttack = now;
    }
}
