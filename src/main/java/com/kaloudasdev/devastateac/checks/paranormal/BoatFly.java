package com.kaloudasdev.frostshield.checks.paranormal;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class BoatFly extends Check {
    private double lastY = 0;
    private int flyTicks = 0;
    
    public BoatFly() { super("BoatFly", "Detects boat fly hacks", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof VehicleMoveEvent)) return;
        VehicleMoveEvent e = (VehicleMoveEvent) event;
        if (!(e.getVehicle() instanceof Boat) || !(e.getVehicle().getPassengers().contains(player))) return;
        
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (deltaY > 1.0 && !player.isOnGround()) {
            flyTicks++;
            if (flyTicks > 10) {
                flag(player, data, String.format("boatFlyDelta=%.2f", deltaY));
                flyTicks = 0;
            }
        } else {
            flyTicks = Math.max(0, flyTicks - 1);
        }
    }
}
