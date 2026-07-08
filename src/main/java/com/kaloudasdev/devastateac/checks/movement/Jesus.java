package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Jesus extends Check {
    private int waterTicks = 0;
    
    public Jesus() { super("Jesus", "Detects walking on water", 50); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (player.isFlying() || player.isInsideVehicle() || player.isGliding()) return;
        if (player.isSwimming()) { waterTicks = 0; return; }
        if (player.getVelocity().getY() > 0.1) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        Material feetBlock = e.getTo().getBlock().getType();
        Material aboveFeet = e.getTo().clone().add(0, 0.2, 0).getBlock().getType();
        boolean isOnWaterSurface = (feetBlock == Material.WATER && aboveFeet != Material.WATER);
        boolean isOnGround = player.isOnGround();
        
        if (isOnWaterSurface && isOnGround && !player.isSwimming() && data.getGroundTicks() > 10) {
            waterTicks++;
            if (waterTicks > 20) {
                flag(player, data, "walking_on_water_surface");
                waterTicks = 0;
            }
        } else {
            waterTicks = Math.max(0, waterTicks - 2);
        }
    }
}
