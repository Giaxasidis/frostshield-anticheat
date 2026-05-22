package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChestStealer extends Check {
    private final Map<UUID, Integer> clickCount = new HashMap<>();
    private final Map<UUID, Long> lastClick = new HashMap<>();
    
    public ChestStealer() { super("ChestStealer", "Detects chest stealer hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof InventoryClickEvent)) return;
        InventoryClickEvent e = (InventoryClickEvent) event;
        
        if (e.getInventory().getType() == InventoryType.CHEST || 
            e.getInventory().getType() == InventoryType.BARREL) {
            
            long now = System.currentTimeMillis();
            UUID uuid = player.getUniqueId();
            long last = lastClick.getOrDefault(uuid, 0L);
            
            if (now - last < 30) {
                int count = clickCount.getOrDefault(uuid, 0) + 1;
                clickCount.put(uuid, count);
                if (count > 40) {
                    flag(player, data, String.format("chestClicks=%d rate=%.2f", count, 1000.0 / (now - last)));
                    clickCount.put(uuid, 0);
                }
            } else {
                clickCount.put(uuid, Math.max(0, clickCount.getOrDefault(uuid, 0) - 5));
            }
            lastClick.put(uuid, now);
        }
    }
}
