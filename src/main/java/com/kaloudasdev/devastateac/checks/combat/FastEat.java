package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FastEat extends Check {
    private final Map<UUID, Long> lastEat = new HashMap<>();
    
    public FastEat() { super("FastEat", "Detects fast eat hacks", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerItemConsumeEvent)) return;
        PlayerItemConsumeEvent e = (PlayerItemConsumeEvent) event;
        
        Material item = e.getItem().getType();
        boolean isFood = item.isEdible();
        
        if (isFood) {
            long now = System.currentTimeMillis();
            UUID uuid = player.getUniqueId();
            long last = lastEat.getOrDefault(uuid, 0L);
            
            if (last != 0 && now - last < 500) {
                flag(player, data, String.format("eatDelay=%dms", now - last));
            }
            lastEat.put(uuid, now);
        }
    }
}
