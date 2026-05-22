package com.kaloudasdev.frostshield.checks.paranormal;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;

public class Freecam extends Check {
    
    public Freecam() { super("Freecam", "Detects freecam/ghost mode", 999); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        return;
    }
}
