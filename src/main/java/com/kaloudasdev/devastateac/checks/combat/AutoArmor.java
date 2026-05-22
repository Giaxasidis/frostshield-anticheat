package com.kaloudasdev.frostshield.checks.combat;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoArmor extends Check {
    private final Map<UUID, Long> lastArmorChange = new HashMap<>();
    private final Map<UUID, Integer> changeCount = new HashMap<>();
    
    public AutoArmor() { super("AutoArmor", "Detects auto armor hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof InventoryClickEvent)) return;
        InventoryClickEvent e = (InventoryClickEvent) event;
        
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        
        String itemType = item.getType().name();
        boolean isArmor = itemType.contains("HELMET") || itemType.contains("CHESTPLATE") || 
                          itemType.contains("LEGGINGS") || itemType.contains("BOOTS");
        
        if (isArmor && e.getSlot() >= 5 && e.getSlot() <= 8) {
            long now = System.currentTimeMillis();
            UUID uuid = player.getUniqueId();
            long last = lastArmorChange.getOrDefault(uuid, 0L);
            
            if (now - last < 50) {
                int count = changeCount.getOrDefault(uuid, 0) + 1;
                changeCount.put(uuid, count);
                if (count > 15) {
                    flag(player, data, String.format("armorChanges=%d time=%dms", count, now - last));
                    changeCount.put(uuid, 0);
                }
            } else {
                changeCount.put(uuid, Math.max(0, changeCount.getOrDefault(uuid, 0) - 2));
            }
            lastArmorChange.put(uuid, now);
        }
    }
}
