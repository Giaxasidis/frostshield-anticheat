package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

public class IllegalStack extends Check {
    public IllegalStack() { super("IllegalStack", "Detects illegal item stacking", 8); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockPlaceEvent)) return;
        
        for (org.bukkit.inventory.ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getAmount() > 64 && item.getType() != Material.AIR) {
                flag(player, data, String.format("item=%s amount=%d", item.getType().name(), item.getAmount()));
                break;
            }
        }
    }
}
