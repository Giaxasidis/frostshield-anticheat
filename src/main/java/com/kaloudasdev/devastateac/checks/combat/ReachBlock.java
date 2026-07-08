package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReachBlock extends Check {
    private final Map<UUID, Integer> reachCount = new HashMap<>();
    
    public ReachBlock() { super("ReachBlock", "Detects hitting through walls/blocks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        Player victim = (Player) e.getEntity();
        Location playerEye = player.getEyeLocation();
        Location victimEye = victim.getEyeLocation();
        
        boolean hasLineOfSight = player.hasLineOfSight(victim);
        double distance = playerEye.distance(victimEye);
        
        if (!hasLineOfSight && distance < 6.0) {
            UUID uuid = player.getUniqueId();
            int count = reachCount.getOrDefault(uuid, 0) + 1;
            reachCount.put(uuid, count);
            
            Logger.log("&c[ReachBlock] &f" + player.getName() + " &7hit &f" + victim.getName() + 
                " &7through wall! &8Dist: &f" + String.format("%.2f", distance) + 
                " &8Count: &f" + count);
            
            DiscordLogger.logCheat(player.getName(), "ReachBlock", count, 10, 
                "hit through wall at distance " + String.format("%.2f", distance) + " blocks");
            
            if (count >= 10) {
                flag(player, data, String.format("reachThroughWall dist=%.2f hits=%d", distance, count));
                reachCount.put(uuid, 0);
            }
        } else {
            if (reachCount.containsKey(player.getUniqueId())) {
                int current = reachCount.get(player.getUniqueId());
                if (current > 0) {
                    reachCount.put(player.getUniqueId(), Math.max(0, current - 1));
                }
            }
        }
    }
}
