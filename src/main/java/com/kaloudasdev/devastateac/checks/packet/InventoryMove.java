package com.kaloudasdev.frostshield.checks.packet;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryMove extends Check {
    private final Map<UUID, Boolean> hasInventoryOpen = new HashMap<>();
    
    public InventoryMove() { super("InventoryMove", "Detects moving with inventory open", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (event instanceof InventoryOpenEvent) {
            hasInventoryOpen.put(player.getUniqueId(), true);
        }
        
        if (event instanceof PlayerMoveEvent && hasInventoryOpen.getOrDefault(player.getUniqueId(), false)) {
            if (player.isOnGround()) {
                flag(player, data, "moving_with_inventory_open");
            }
        }
    }
}
