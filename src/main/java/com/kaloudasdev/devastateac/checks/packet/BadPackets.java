package com.kaloudasdev.frostshield.checks.packet;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class BadPackets extends Check {
    public BadPackets() { super("BadPackets", "Detects invalid packet data", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        float yaw = e.getTo().getYaw();
        float pitch = e.getTo().getPitch();
        
        if (Math.abs(yaw) > 3600) {
            flag(player, data, String.format("yaw=%.2f pitch=%.2f", yaw, pitch));
        }
        
        if (Double.isNaN(e.getTo().getX()) || Double.isNaN(e.getTo().getY()) || Double.isNaN(e.getTo().getZ())) {
            flag(player, data, "NaN position detected");
        }
    }
}
