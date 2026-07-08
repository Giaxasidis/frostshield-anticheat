package com.giaxasidis.frostshield.punishment;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.data.PlayerData;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PunishmentManager {
    
    public void banPlayer(Player player, String check, int vl) {
        String banMessage = FrostShield.getInstance().getConfig().getString("punishments.ban-message", 
            "&c&l[FrostShield] &fYou were banned for cheating! (Check: %check%, VL: %vl%)");
        banMessage = banMessage.replace("%check%", check).replace("%vl%", String.valueOf(vl));
        
        String finalMessage = ChatColor.translateAlternateColorCodes('&', banMessage);
        
        Bukkit.getScheduler().runTask(FrostShield.getInstance(), () -> {
            player.kickPlayer(finalMessage);
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), finalMessage, null, "FrostShield");
        });
        
        Logger.log("&c&l[FrostShield] &f" + player.getName() + " was banned for " + check + " (VL: " + vl + ")");
        DiscordLogger.logBan(player.getName(), check, vl);
        
        Bukkit.getOnlinePlayers().stream()
            .filter(p -> p.hasPermission("frostshield.admin"))
            .forEach(p -> p.sendMessage(ChatColor.RED + "[FrostShield] " + player.getName() + " was banned for " + check));
    }
    
    public void kickPlayer(Player player, String check, int vl) {
        String kickMessage = FrostShield.getInstance().getConfig().getString("punishments.kick-message", 
            "&c&l[FrostShield] &fCheating detected: %check% (VL: %vl%)");
        kickMessage = kickMessage.replace("%check%", check).replace("%vl%", String.valueOf(vl));
        
        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));
        
        Logger.log("&e[FrostShield] &f" + player.getName() + " was kicked for " + check + " (VL: " + vl + ")");
    }
    
    public void warnPlayer(Player player, String check, int vl, int maxVl) {
        String warnMessage = FrostShield.getInstance().getConfig().getString("punishments.warn-message", 
            "&c&l[FrostShield] &fCheating detected: %check% (VL: %vl%/%max%)");
        warnMessage = warnMessage.replace("%check%", check).replace("%vl%", String.valueOf(vl)).replace("%max%", String.valueOf(maxVl));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', warnMessage));
    }
}
