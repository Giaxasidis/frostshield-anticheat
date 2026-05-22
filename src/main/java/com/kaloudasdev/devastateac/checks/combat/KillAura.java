package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillAura extends Check {
    public KillAura() { super("KillAura", "Detects kill aura / aim assist", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        float yawChange = Math.abs(player.getLocation().getYaw() - data.getLastYaw());
        
        if (yawChange < 0.1 && data.getLastYaw() != 0 && yawChange > 0.01) {
            data.incrementAttackCount();
            if (data.getAttackCount() > 20) {
                flag(player, data, String.format("yawChange=%.2f attacks=%d", yawChange, data.getAttackCount()));
                data.resetAttackCount();
            }
        } else {
            data.resetAttackCount();
        }
        
        data.setLastYaw(player.getLocation().getYaw());
    }
}
