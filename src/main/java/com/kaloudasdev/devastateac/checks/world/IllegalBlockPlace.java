package com.giaxasidis.frostshield.checks.world;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class IllegalBlockPlace extends Check {
    
    private static final List<Material> ILLEGAL_PLACES = Arrays.asList(
        Material.LADDER,
        Material.VINE,
        Material.TRIPWIRE,
        Material.TRIPWIRE_HOOK,
        Material.RAIL,
        Material.POWERED_RAIL,
        Material.DETECTOR_RAIL,
        Material.ACTIVATOR_RAIL
    );
    
    public IllegalBlockPlace() { super("IllegalBlockPlace", "Detects illegal block placement", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockPlaceEvent)) return;
        BlockPlaceEvent e = (BlockPlaceEvent) event;
        
        Block block = e.getBlock();
        Block against = e.getBlockAgainst();
        
        if (ILLEGAL_PLACES.contains(block.getType())) {
            boolean canPlace = false;
            
            if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
                if (against.getType().isSolid()) canPlace = true;
            }
            
            if (!canPlace) {
                flag(player, data, String.format("illegal_place block=%s against=%s", 
                    block.getType().name(), against.getType().name()));
            }
        }
    }
}
