package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterWalk extends Check {
    private int surfaceTicks = 0;
    private int waterExitTicks = 0;
    
    public WaterWalk() { super("WaterWalk", "Detects walking on water surface", 25); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (player.isFlying() || player.isInsideVehicle() || player.isGliding()) return;
        if (player.isSwimming()) { surfaceTicks = 0; return; }
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        if (waterExitTicks > 0) {
            waterExitTicks--;
            return;
        }
        
        if (data.wasInWater() && !player.isInWater()) {
            waterExitTicks = 40;
            surfaceTicks = 0;
            return;
        }
        
        Material feetBlock = e.getTo().clone().subtract(0, 0.1, 0).getBlock().getType();
        Material belowFeet = e.getTo().clone().subtract(0, 0.5, 0).getBlock().getType();
        boolean isOnWaterSurface = (feetBlock == Material.WATER && belowFeet == Material.WATER);
        
        boolean isJumpingOnWater = (!player.isOnGround() && data.getAirTicks() < 5 && isOnWaterSurface);
        boolean isWalkingOnWater = (player.isOnGround() && isOnWaterSurface);
        
        if ((isWalkingOnWater || isJumpingOnWater) && !player.isSwimming()) {
            surfaceTicks++;
            if (surfaceTicks > 25) {
                flag(player, data, "walking_on_water_surface");
                surfaceTicks = 0;
            }
        } else {
            surfaceTicks = Math.max(0, surfaceTicks - 1);
        }
        
        data.setWasInWater(player.isInWater());
    }
}
