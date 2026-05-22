package com.kaloudasdev.frostshield.checks.paranormal;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class ElytraFly extends Check {
    private double lastSpeed = 0;
    private int speedTicks = 0;
    
    public ElytraFly() { super("ElytraFly", "Detects elytra speed hacks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (!player.isGliding()) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null || e.getFrom() == null) return;
        
        double deltaX = e.getTo().getX() - e.getFrom().getX();
        double deltaZ = e.getTo().getZ() - e.getFrom().getZ();
        double horizontal = Math.hypot(deltaX, deltaZ);
        
        if (horizontal > 1.2) {
            speedTicks++;
            if (speedTicks > 10) {
                flag(player, data, String.format("elytraSpeed=%.2f", horizontal));
                speedTicks = 0;
            }
        } else {
            speedTicks = Math.max(0, speedTicks - 1);
        }
    }
}
