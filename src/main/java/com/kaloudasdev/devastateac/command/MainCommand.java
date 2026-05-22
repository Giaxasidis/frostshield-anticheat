package com.kaloudasdev.frostshield.command;

import com.kaloudasdev.frostshield.FrostShield;
import com.kaloudasdev.frostshield.data.PlayerData;
import com.kaloudasdev.frostshield.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("frostshield.admin")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                FrostShield.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "FrostShield config reloaded!");
                Logger.log("&7[FrostShield] &aConfig reloaded by " + sender.getName());
                break;
                
            case "stats":
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    PlayerData data = FrostShield.getInstance().getPlayerDataManager().getData(p.getUniqueId());
                    if (data != null) {
                        sender.sendMessage(ChatColor.GOLD + "FrostShield Stats");
                        sender.sendMessage(ChatColor.YELLOW + "Total Violations: " + ChatColor.WHITE + data.getTotalViolations());
                        sender.sendMessage(ChatColor.YELLOW + "Air Ticks: " + ChatColor.WHITE + data.getAirTicks());
                        sender.sendMessage(ChatColor.YELLOW + "Ground Ticks: " + ChatColor.WHITE + data.getGroundTicks());
                    } else {
                        sender.sendMessage(ChatColor.RED + "No data yet!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Console can't use stats!");
                }
                break;
                
            case "reset":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /kac reset <player>");
                    return true;
                }
                Player target = FrostShield.getInstance().getServer().getPlayer(args[1]);
                if (target != null) {
                    PlayerData data = FrostShield.getInstance().getPlayerDataManager().getData(target.getUniqueId());
                    if (data != null) {
                        data.getCheckViolations().clear();
                        sender.sendMessage(ChatColor.GREEN + "Reset violations for " + target.getName());
                        Logger.log("&7[FrostShield] &aReset " + target.getName() + " by " + sender.getName());
                    } else {
                        sender.sendMessage(ChatColor.RED + "No data for that player!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                }
                break;
                
            case "checks":
                sender.sendMessage(ChatColor.GOLD + "Checks (" + FrostShield.getInstance().getCheckManager().getTotalChecks() + ")");
                FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
                    sender.sendMessage(ChatColor.YELLOW + "- " + check.name + ChatColor.GRAY + " (" + check.maxVl + " VL) - " + (check.enabled ? "&aENABLED" : "&cDISABLED"));
                });
                break;
                
            case "toggle":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /kac toggle <check>");
                    return true;
                }
                String checkName = args[1];
                var found = false;
                for (var check : FrostShield.getInstance().getCheckManager().getChecks()) {
                    if (check.name.equalsIgnoreCase(checkName)) {
                        check.enabled = !check.enabled;
                        sender.sendMessage(ChatColor.GREEN + "Check " + check.name + " now " + (check.enabled ? "ENABLED" : "DISABLED"));
                        found = true;
                        break;
                    }
                }
                if (!found) sender.sendMessage(ChatColor.RED + "Check not found!");
                break;
                
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "KaloudasAC Commands");
        sender.sendMessage(ChatColor.YELLOW + "/kac reload" + ChatColor.GRAY + " - Reload config");
        sender.sendMessage(ChatColor.YELLOW + "/kac stats" + ChatColor.GRAY + " - Your stats");
        sender.sendMessage(ChatColor.YELLOW + "/kac reset <player>" + ChatColor.GRAY + " - Reset violations");
        sender.sendMessage(ChatColor.YELLOW + "/kac checks" + ChatColor.GRAY + " - List all checks");
        sender.sendMessage(ChatColor.YELLOW + "/kac toggle <check>" + ChatColor.GRAY + " - Toggle check on/off");
        sender.sendMessage(ChatColor.GOLD + "Ban Commands");
        sender.sendMessage(ChatColor.YELLOW + "/kac ban <player> <reason> [duration]" + ChatColor.GRAY + " - Ban player");
        sender.sendMessage(ChatColor.YELLOW + "/kac unban <player>" + ChatColor.GRAY + " - Unban player");
        sender.sendMessage(ChatColor.YELLOW + "/kac banlist" + ChatColor.GRAY + " - List active bans");
        sender.sendMessage(ChatColor.YELLOW + "/kac baninfo <player>" + ChatColor.GRAY + " - Show ban info");
    }
}
