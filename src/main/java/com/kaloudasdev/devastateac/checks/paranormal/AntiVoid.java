package com.kaloudasdev.frostshield.checks.paranormal;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class AntiVoid extends Check {
    public AntiVoid() { super("AntiVoid", "Detects anti-void hacks", 20); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        if (e.getTo().getY() < -10 && !player.isFlying() && data.getVelocityTicks() == 0) {
            flag(player, data, String.format("y=%.2f", e.getTo().getY()));
        }
    }
}
