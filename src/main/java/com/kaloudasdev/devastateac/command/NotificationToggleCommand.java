package com.kaloudasdev.frostshield.command;

import com.kaloudasdev.frostshield.FrostShield;
import com.kaloudasdev.frostshield.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NotificationToggleCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this!");
            return true;
        }
        
        Player p = (Player) sender;
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        boolean newState = !data.areNotificationsEnabled();
        data.setNotificationsEnabled(newState);
        
        if (newState) {
            p.sendMessage(ChatColor.GREEN + "[FrostShield] Notifications: ENABLED");
        } else {
            p.sendMessage(ChatColor.RED + "[FrostShield] Notifications: DISABLED");
        }
        
        return true;
    }
}
