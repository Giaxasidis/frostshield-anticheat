package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.*;

public class MultiAura extends Check {
    private final Map<UUID, List<Long>> hitTimestamps = new HashMap<>();
    private final Map<UUID, Set<UUID>> hitEntities = new HashMap<>();
    
    public MultiAura() { super("MultiAura", "Detects hitting multiple entities at same time", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (!(e.getDamager() instanceof Player)) return;
        
        UUID playerId = player.getUniqueId();
        UUID victimId = e.getEntity().getUniqueId();
        long now = System.currentTimeMillis();
        
        List<Long> timestamps = hitTimestamps.getOrDefault(playerId, new ArrayList<>());
        Set<UUID> entities = hitEntities.getOrDefault(playerId, new HashSet<>());
        
        timestamps.add(now);
        entities.add(victimId);
        
        while (timestamps.size() > 0 && now - timestamps.get(0) > 1000) {
            timestamps.remove(0);
        }
        
        if (timestamps.size() > 20) {
            hitTimestamps.put(playerId, timestamps);
            flag(player, data, String.format("hitsPerSecond=%.2f", timestamps.size()));
        }
        
        if (entities.size() > 8 && timestamps.size() > 10) {
            flag(player, data, String.format("multiAura entities=%d in %dms", entities.size(), 
                now - timestamps.get(0)));
            entities.clear();
        }
        
        hitTimestamps.put(playerId, timestamps);
        hitEntities.put(playerId, entities);
        
        if (timestamps.size() > 0 && now - timestamps.get(timestamps.size() - 1) > 5000) {
            hitTimestamps.remove(playerId);
            hitEntities.remove(playerId);
        }
    }
}
