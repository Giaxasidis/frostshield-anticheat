package com.giaxasidis.frostshield.checks.paranormal;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class ESP extends Check {
    public ESP() { super("ESP", "Detects ESP / radar hacks", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        
        for (Player target : player.getWorld().getPlayers()) {
            if (target.equals(player)) continue;
            if (target.isSneaking() && !player.hasLineOfSight(target)) {
                double distance = player.getLocation().distance(target.getLocation());
                if (distance < 10) {
                    flag(player, data, String.format("tracked_through_wall dist=%.2f", distance));
                }
            }
        }
    }
}
