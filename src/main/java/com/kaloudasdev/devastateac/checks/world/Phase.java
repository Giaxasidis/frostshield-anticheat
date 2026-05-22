package com.kaloudasdev.frostshield.checks.world;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Phase extends Check {
    public Phase() { super("Phase", "Detects phasing through blocks", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null || e.getFrom() == null) return;
        
        if (data.getTeleportTicks() > 5 || player.isDead() || player.isInsideVehicle()) return;
        
        if (e.getTo().getBlock().getType().isSolid() && 
            e.getFrom().getBlock().getType().isSolid() &&
            data.getGroundTicks() > 30 &&
            !player.isOnGround()) {
            flag(player, data, "phase_through_block");
        }
    }
}
