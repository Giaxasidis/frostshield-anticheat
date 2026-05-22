package com.kaloudasdev.frostshield.checks.movement;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastClimb extends Check {
    private int fastTicks = 0;
    
    public FastClimb() { super("FastClimb", "Detects fast climbing on ladders, vines, and stairs", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        if (data.getVelocityTicks() > 0 || data.getTeleportTicks() > 0) return;
        
        Material blockType = e.getTo().getBlock().getType();
        Material belowBlock = e.getTo().clone().subtract(0, 1, 0).getBlock().getType();
        
        boolean onLadder = (blockType == Material.LADDER || belowBlock == Material.LADDER);
        boolean onVine = (blockType == Material.VINE || belowBlock == Material.VINE);
        boolean onStairs = blockType.name().contains("STAIRS") || belowBlock.name().contains("STAIRS");
        
        if (onLadder || onVine || onStairs) {
            double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
            double maxSpeed = 0.25;
            
            if (onStairs && !player.isOnGround()) {
                maxSpeed = 0.35;
            }
            
            if (deltaY > maxSpeed) {
                fastTicks++;
                if (fastTicks > 10) {
                    String climbType = onLadder ? "LADDER" : (onVine ? "VINE" : "STAIRS");
                    flag(player, data, String.format("type=%s speed=%.3f max=%.3f", climbType, deltaY, maxSpeed));
                    fastTicks = 0;
                }
            } else {
                fastTicks = Math.max(0, fastTicks - 2);
            }
        } else {
            fastTicks = Math.max(0, fastTicks - 1);
        }
    }
}
