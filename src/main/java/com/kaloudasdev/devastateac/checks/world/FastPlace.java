package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FastPlace extends Check {
    private final Map<UUID, Integer> placeCount = new HashMap<>();
    private final Map<UUID, Long> lastPlace = new HashMap<>();
    
    public FastPlace() { super("FastPlace", "Detects fast block placement", 8); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockPlaceEvent)) return;
        
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();
        Long last = lastPlace.get(uuid);
        
        if (last != null && now - last < 40) {
            int count = placeCount.getOrDefault(uuid, 0) + 1;
            placeCount.put(uuid, count);
            
            if (count >= 8) {
                flag(player, data, String.format("fastPlace blocks=%d time=%dms", count, now - last));
                placeCount.put(uuid, 0);
            }
        } else {
            placeCount.put(uuid, 1);
        }
        
        lastPlace.put(uuid, now);
    }
}
