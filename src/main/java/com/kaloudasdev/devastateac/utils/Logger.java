package com.giaxasidis.frostshield.utils;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Logger {
    
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    
    public static void info(String message) {
        log("&8[FrostShield] &f" + message);
    }
    
    public static void warn(String message) {
        log("&8[FrostShield] &e" + message);
    }
    
    public static void severe(String message) {
        log("&8[FrostShield] &c" + message);
    }
    
    public static void debug(String message) {
        if (FrostShield.getInstance().getConfig().getBoolean("settings.debug", false)) {
            log("&8[FrostShield] &8[DEBUG] &f" + message);
        }
    }
    
    public static void logCheck(String player, String check, String data) {
        log("&8[FrostShield] &7[" + check + "] &f" + player + " &7" + data);
    }
    
    public static void alertInGame(String playerName, String check, int vl, int maxVl, String details) {
        Player target = Bukkit.getPlayer(playerName);
        if (target != null) {
            PlayerData data = FrostShield.getInstance().getPlayerDataManager().getData(target.getUniqueId());
            if (data != null && !data.areNotificationsEnabled()) {
                return;
            }
        }
        
        String msg = FrostShield.getInstance().getConfig().getString("punishments.alert-message", 
            "&c&l[FrostShield] &f%player% &7failed &c%check% &7(VL: %vl%/%max%)");
        msg = msg.replace("%player%", playerName)
                 .replace("%check%", check)
                 .replace("%vl%", String.valueOf(vl))
                 .replace("%max%", String.valueOf(maxVl))
                 .replace("%details%", details);
        
        String finalMsg = ChatColor.translateAlternateColorCodes('&', msg);
        
        Bukkit.getOnlinePlayers().stream()
            .filter(p -> p.hasPermission("frostshield.notify"))
            .forEach(p -> p.sendMessage(finalMsg));
        
        log("&c[VL] " + playerName + " - " + check + " - VL: " + vl + "/" + maxVl + " - " + details);
    }
    
    public static void broadcastToAdmins(String message) {
        String colored = ChatColor.translateAlternateColorCodes('&', message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("frostshield.admin")) {
                p.sendMessage(colored);
            }
        }
        log(message);
    }
}
