package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AimAssist extends Check {
    private float lastYaw = 0;
    
    public AimAssist() { super("AimAssist", "Detects aim assist", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        float currentYaw = player.getLocation().getYaw();
        float yawDiff = Math.abs(currentYaw - lastYaw);
        float pitchDiff = Math.abs(player.getLocation().getPitch() - data.getLastPitch());
        
        if (yawDiff < 0.5 && pitchDiff < 0.5 && yawDiff > 0.01 && lastYaw != 0) {
            flag(player, data, String.format("yawDiff=%.4f pitchDiff=%.4f", yawDiff, pitchDiff));
        }
        
        lastYaw = currentYaw;
        data.setLastPitch(player.getLocation().getPitch());
    }
}
