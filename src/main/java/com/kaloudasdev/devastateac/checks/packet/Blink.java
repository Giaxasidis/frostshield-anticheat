package com.giaxasidis.frostshield.checks.packet;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Blink extends Check {
    private double lastX, lastY, lastZ;
    private int noMoveTicks = 0;
    
    public Blink() { super("Blink", "Detects blink / fake lag", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaX = Math.abs(e.getTo().getX() - lastX);
        double deltaZ = Math.abs(e.getTo().getZ() - lastZ);
        double deltaY = Math.abs(e.getTo().getY() - lastY);
        
        if (deltaX < 0.001 && deltaZ < 0.001 && deltaY < 0.001) {
            noMoveTicks++;
            if (noMoveTicks > 40 && !player.isOnGround() && player.getVelocity().length() < 0.1) {
                flag(player, data, String.format("noMoveTicks=%d", noMoveTicks));
            }
        } else {
            noMoveTicks = 0;
        }
        
        lastX = e.getTo().getX();
        lastY = e.getTo().getY();
        lastZ = e.getTo().getZ();
    }
}
