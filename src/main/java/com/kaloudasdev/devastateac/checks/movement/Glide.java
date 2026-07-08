package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Glide extends Check {
    private int glideTicks = 0;
    
    public Glide() { super("Glide", "Detects glide hacks", 30); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (player.isGliding() || player.isFlying() || player.isInsideVehicle()) return;
        if (player.isInWater() || player.isSwimming()) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = e.getFrom().getY() - e.getTo().getY();
        
        if (!player.isOnGround() && data.getAirTicks() > 40 && deltaY < 0.02 && deltaY > 0) {
            glideTicks++;
            if (glideTicks > 40) {
                flag(player, data, String.format("slowFall=%.4f", deltaY));
                glideTicks = 0;
            }
        } else {
            glideTicks = Math.max(0, glideTicks - 2);
        }
    }
}
