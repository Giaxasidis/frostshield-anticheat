package com.kaloudasdev.frostshield.checks.packet;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class PacketOrder extends Check {
    private int invalidOrder = 0;
    
    public PacketOrder() { super("PacketOrder", "Detects out-of-order packets", 8); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null || e.getFrom() == null) return;
        
        double deltaX = e.getTo().getX() - e.getFrom().getX();
        double deltaZ = e.getTo().getZ() - e.getFrom().getZ();
        double deltaXZ = Math.hypot(deltaX, deltaZ);
        
        if (deltaXZ > 10) {
            invalidOrder++;
            if (invalidOrder > 3) {
                flag(player, data, String.format("teleportDistance=%.2f", deltaXZ));
                invalidOrder = 0;
            }
        } else {
            invalidOrder = Math.max(0, invalidOrder - 1);
        }
    }
}
