package com.giaxasidis.frostshield.command;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.ban.BanManager;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("frostshield.admin")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }
        
        if (args.length < 1) {
            sendHelp(sender);
            return true;
        }
        
        String subCmd = args[0].toLowerCase();
        
        switch (subCmd) {
            case "ban":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /kac ban <player> <reason> [duration]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                StringBuilder reason = new StringBuilder();
                long duration = 0;
                for (int i = 2; i < args.length; i++) {
                    if (args[i].matches("\\d+[smhdw]") && i == 2) {
                        duration = parseDuration(args[i]);
                        continue;
                    }
                    reason.append(args[i]).append(" ");
                }
                if (reason.length() == 0) reason.append("Cheating");
                BanManager.getInstance().banPlayer(target, reason.toString().trim(), sender.getName(), duration);
                sender.sendMessage(ChatColor.GREEN + "Banned " + target.getName());
                break;
                
            case "unban":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /kac unban <player>");
                    return true;
                }
                if (BanManager.getInstance().unbanPlayer(args[1])) {
                    sender.sendMessage(ChatColor.GREEN + "Unbanned " + args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found in ban list!");
                }
                break;
                
            case "banlist":
                var bans = BanManager.getInstance().getActiveBans();
                if (bans.isEmpty()) {
                    sender.sendMessage(ChatColor.GREEN + "No active bans.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Active Bans (" + bans.size() + ")");
                    for (var ban : bans) {
                        sender.sendMessage(ChatColor.YELLOW + ban.playerName + ChatColor.GRAY + " - " + ban.reason + " (" + (ban.isPermanent() ? "Permanent" : ban.getDurationString()) + ")");
                    }
                }
                break;
                
            case "baninfo":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /kac baninfo <player>");
                    return true;
                }
                var info = BanManager.getInstance().getBanInfo(args[1]);
                if (info == null) {
                    sender.sendMessage(ChatColor.RED + "Player not banned!");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Ban Info for " + info.playerName + "");
                    sender.sendMessage(ChatColor.YELLOW + "Reason: " + ChatColor.WHITE + info.reason);
                    sender.sendMessage(ChatColor.YELLOW + "Banned by: " + ChatColor.WHITE + info.bannedBy);
                    sender.sendMessage(ChatColor.YELLOW + "Duration: " + ChatColor.WHITE + (info.isPermanent() ? "Permanent" : info.getDurationString()));
                    sender.sendMessage(ChatColor.YELLOW + "IP: " + ChatColor.WHITE + info.ipAddress);
                }
                break;
                
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }
    
    private long parseDuration(String arg) {
        char unit = arg.charAt(arg.length() - 1);
        int value = Integer.parseInt(arg.substring(0, arg.length() - 1));
        switch (unit) {
            case 's': return value;
            case 'm': return value * 60;
            case 'h': return value * 3600;
            case 'd': return value * 86400;
            case 'w': return value * 604800;
            default: return 0;
        }
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "KaloudasAC Ban Commands");
        sender.sendMessage(ChatColor.YELLOW + "/kac ban <player> <reason> [duration]" + ChatColor.GRAY + " - Ban player");
        sender.sendMessage(ChatColor.YELLOW + "/kac unban <player>" + ChatColor.GRAY + " - Unban player");
        sender.sendMessage(ChatColor.YELLOW + "/kac banlist" + ChatColor.GRAY + " - List active bans");
        sender.sendMessage(ChatColor.YELLOW + "/kac baninfo <player>" + ChatColor.GRAY + " - Show ban details");
    }
}
