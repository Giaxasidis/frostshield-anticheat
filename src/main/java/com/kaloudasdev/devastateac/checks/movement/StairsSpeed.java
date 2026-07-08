package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class StairsSpeed extends Check {
    private int fastTicks = 0;
    
    public StairsSpeed() { super("StairsSpeed", "Detects fast stairs climbing", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        if (data.getVelocityTicks() > 0 || data.getTeleportTicks() > 0) return;
        
        Block blockUnder = e.getTo().clone().subtract(0, 0.5, 0).getBlock();
        String blockType = blockUnder.getType().name();
        
        if (blockType.contains("STAIRS") && player.isOnGround()) {
            double deltaX = Math.abs(e.getTo().getX() - e.getFrom().getX());
            double deltaZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
            double distance = Math.hypot(deltaX, deltaZ);
            double normalSpeed = 0.35;
            
            if (distance > normalSpeed) {
                fastTicks++;
                if (fastTicks > 20) {
                    flag(player, data, String.format("stairsSpeed=%.3f", distance));
                    fastTicks = 0;
                }
            } else {
                fastTicks = Math.max(0, fastTicks - 1);
            }
        } else {
            fastTicks = Math.max(0, fastTicks - 2);
        }
    }
}
