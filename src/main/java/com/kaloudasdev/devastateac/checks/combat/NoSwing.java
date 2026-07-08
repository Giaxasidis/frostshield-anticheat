package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoSwing extends Check {
    public NoSwing() { super("NoSwing", "Detects attacks without swing animation", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        data.incrementAttackCount();
        if (data.getAttackCount() > 5) {
            flag(player, data, String.format("attackCount=%d noSwing", data.getAttackCount()));
            data.resetAttackCount();
        }
    }
}
