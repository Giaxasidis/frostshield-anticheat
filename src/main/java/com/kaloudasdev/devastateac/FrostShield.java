package com.giaxasidis.frostshield;

import com.giaxasidis.frostshield.checks.CheckManager;
import com.giaxasidis.frostshield.command.AdminCommand;
import com.giaxasidis.frostshield.command.DACCommandTabCompleter;
import com.giaxasidis.frostshield.data.PlayerDataManager;
import com.giaxasidis.frostshield.listener.BukkitListener;
import com.giaxasidis.frostshield.listener.JoinListener;
import com.giaxasidis.frostshield.punishment.PunishmentManager;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class FrostShield extends JavaPlugin {
    private static FrostShield instance;
    private PlayerDataManager playerDataManager;
    private CheckManager checkManager;
    private PunishmentManager punishmentManager;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        
        instance = this;
        saveDefaultConfig();
        
        Logger.log("&c&lFrostShield - Maximum Security AntiCheat");
        Logger.log("&7Author: &Giaxasidis");
        Logger.log("&7Version: &f" + getDescription().getVersion());
        
        DiscordLogger.init();
        playerDataManager = new PlayerDataManager();
        checkManager = new CheckManager();
        punishmentManager = new PunishmentManager();
        
        AdminCommand adminCmd = new AdminCommand();
        getCommand("fs").setExecutor(adminCmd);
        getCommand("fs").setTabCompleter(new DACCommandTabCompleter());
        
        getServer().getPluginManager().registerEvents(new BukkitListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        Logger.log("&aFrostShield Successfully Enabled!");
        Logger.log("&7Checks Loaded: &f" + checkManager.getTotalChecks());
        Logger.log("&7Discord Logger: &f" + (DiscordLogger.isEnabled() ? "Enabled" : "Disabled"));
        Logger.log("&7Auto Ban: &f" + (getConfig().getBoolean("settings.auto-ban", true) ? "Enabled" : "Disabled"));
        Logger.log("&7Debug Mode: &f" + (getConfig().getBoolean("settings.debug", false) ? "Enabled" : "Disabled"));
        Logger.log("&7Load Time: &f" + loadTime + "ms");
        
        startTaskThreads();
    }
    
    private void startTaskThreads() {
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            playerDataManager.tickAll();
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        Logger.log("&8[FrostShield] &7Saving player data...");
        Logger.log("&8[FrostShield] &cPlugin disabled - Giaxasidis");
    }

    public static FrostShield getInstance() { return instance; }
    public PlayerDataManager getPlayerDataManager() { return playerDataManager; }
    public CheckManager getCheckManager() { return checkManager; }
    public PunishmentManager getPunishmentManager() { return punishmentManager; }
}
