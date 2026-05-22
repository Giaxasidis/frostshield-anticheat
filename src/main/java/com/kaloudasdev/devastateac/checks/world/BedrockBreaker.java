package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class BedrockBreaker extends Check {
    public BedrockBreaker() { super("BedrockBreaker", "Detects bedrock breaking hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent e = (BlockBreakEvent) event;
        
        if (e.getBlock().getType() == Material.BEDROCK) {
            flag(player, data, "attempted to break bedrock");
        }
    }
}
