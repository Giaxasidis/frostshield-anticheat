package com.giaxasidis.frostshield.checks;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.data.PlayerData;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class Check {
    public String name;
    public String description;
    public int maxVl;
    public boolean enabled = true;
    
    public Check(String name, String description, int maxVl) {
        this.name = name;
        this.description = description;
        this.maxVl = maxVl;
    }
    
    public abstract void handle(Player player, PlayerData data, Event event, Object... args);
    
    public void flag(Player player, PlayerData data, String details, int amount) {
        if (!enabled) return;
        data.addViolation(name, amount);
        int vl = data.getCheckViolations().getOrDefault(name, 0);
        
        Logger.logCheck(player.getName(), name, details + " (VL: " + vl + "/" + maxVl + ")");
        DiscordLogger.logCheat(player.getName(), name, vl, maxVl, details);
        Logger.alertInGame(player.getName(), name, vl, maxVl, details);
        
        if (vl >= maxVl && FrostShield.getInstance().getConfig().getBoolean("settings.auto-ban", true)) {
            FrostShield.getInstance().getPunishmentManager().banPlayer(player, name, vl);
        }
    }
    
    public void flag(Player player, PlayerData data, String details) { flag(player, data, details, 1); }
}
