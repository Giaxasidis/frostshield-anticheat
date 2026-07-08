package com.giaxasidis.frostshield.checks.world;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class GhostHand extends Check {
    public GhostHand() { super("GhostHand", "Detects ghost hand / reach through blocks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent e = (BlockBreakEvent) event;
        
        Material blockType = e.getBlock().getType();
        Location blockLoc = e.getBlock().getLocation();
        double distance = player.getLocation().distance(blockLoc);
        
        if (distance > 5.5 && !player.isInsideVehicle()) {
            flag(player, data, String.format("dist=%.2f block=%s", distance, blockType.name()));
        }
    }
}
