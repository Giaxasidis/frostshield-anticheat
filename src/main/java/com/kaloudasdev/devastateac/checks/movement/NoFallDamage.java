package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamage extends Check {
    
    public NoFallDamage() { super("NoFallDamage", "Detects no fall damage hack", 8); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageEvent)) return;
        EntityDamageEvent e = (EntityDamageEvent) event;
        
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            double damage = e.getDamage();
            double fallDistance = player.getFallDistance();
            
            Logger.log("&8[FrostShield] &7[NoFallDamage] &f" + player.getName() + 
                " &7fallDistance= &f" + String.format("%.2f", fallDistance) + 
                " &7damage= &f" + String.format("%.2f", damage));
            
            DiscordLogger.logCheat(player.getName(), "NoFallDamage", 0, 8, 
                "fallDist=" + String.format("%.2f", fallDistance) + 
                " damage=" + String.format("%.2f", damage));
            
            if (fallDistance > 5.0 && damage < 0.5) {
                flag(player, data, String.format("fallDist=%.2f damage=%.2f (expected >2.0)", 
                    fallDistance, damage));
            }
        }
    }
}
