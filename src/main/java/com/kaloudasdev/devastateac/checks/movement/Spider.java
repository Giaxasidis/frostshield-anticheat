package com.kaloudasdev.frostshield.checks.movement;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Spider extends Check {
    public Spider() { super("Spider", "Detects spider hacks", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        if (data.getVelocityTicks() > 0 || data.getTeleportTicks() > 0) return;
        
        if (player.isInWater() || player.isSwimming()) return;
        
        Material feetBlock = e.getTo().getBlock().getType();
        if (feetBlock == Material.WATER) return;
        
        Material belowBlock = e.getTo().clone().subtract(0, 1, 0).getBlock().getType();
        if (belowBlock == Material.WATER) return;
        
        boolean isVertical = Math.abs(e.getTo().getX() - e.getFrom().getX()) < 0.01 && 
                            Math.abs(e.getTo().getZ() - e.getFrom().getZ()) < 0.01;
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (!player.isOnGround() && isVertical && deltaY > 0 && deltaY < 0.12 && data.getAirTicks() > 40) {
            flag(player, data, "climbing");
        }
    }
}
