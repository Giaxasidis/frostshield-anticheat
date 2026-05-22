package com.kaloudasdev.frostshield.checks.paranormal;

import com.kaloudasdev.frostshield.checks.Check;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class AntiHunger extends Check {
    public AntiHunger() { super("AntiHunger", "Detects hunger manipulation", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof FoodLevelChangeEvent)) return;
        FoodLevelChangeEvent e = (FoodLevelChangeEvent) event;
        
        int newFood = e.getFoodLevel();
        int currentFood = player.getFoodLevel();
        
        if (newFood > currentFood && player.getSaturation() < 1.0) {
            flag(player, data, String.format("foodGain=%d current=%d new=%d", newFood - currentFood, currentFood, newFood));
        }
    }
}
