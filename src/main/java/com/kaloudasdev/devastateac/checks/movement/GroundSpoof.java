package com.kaloudasdev.frostshield.checks.movement;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class GroundSpoof extends Check {
    public GroundSpoof() { super("GroundSpoof", "Detects ground spoof", 999); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        return;
    }
}
