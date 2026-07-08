package com.giaxasidis.frostshield.command;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.ban.BanManager;
import com.giaxasidis.frostshield.utils.DiscordLogger;
import com.giaxasidis.frostshield.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AdminCommand implements CommandExecutor {
    
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
                    sender.sendMessage(ChatColor.RED + "Usage: /fs ban <player> <reason> [duration]");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                StringBuilder reason = new StringBuilder();
                long banDuration = 0;
                for (int i = 2; i < args.length; i++) {
                    if (args[i].matches("\\d+[smhdw]") && i == 2) {
                        banDuration = parseDuration(args[i]);
                        continue;
                    }
                    reason.append(args[i]).append(" ");
                }
                if (reason.length() == 0) reason.append("Cheating");
                BanManager.getInstance().banPlayer(target, reason.toString().trim(), sender.getName(), banDuration);
                sender.sendMessage(ChatColor.GREEN + "Banned " + target.getName());
                break;
                
            case "banip":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs banip <player> <reason> [duration]");
                    return true;
                }
                Player ipTarget = Bukkit.getPlayer(args[1]);
                if (ipTarget == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                StringBuilder ipReason = new StringBuilder();
                long ipBanDuration = 0;
                for (int i = 2; i < args.length; i++) {
                    if (args[i].matches("\\d+[smhdw]") && i == 2) {
                        ipBanDuration = parseDuration(args[i]);
                        continue;
                    }
                    ipReason.append(args[i]).append(" ");
                }
                if (ipReason.length() == 0) ipReason.append("IP Ban");
                BanManager.getInstance().banIp(ipTarget, ipReason.toString().trim(), sender.getName(), ipBanDuration);
                sender.sendMessage(ChatColor.GREEN + "IP banned " + ipTarget.getName());
                break;
                
            case "unban":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs unban <player>");
                    return true;
                }
                if (BanManager.getInstance().unbanPlayer(args[1])) {
                    sender.sendMessage(ChatColor.GREEN + "Unbanned " + args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found in ban list!");
                }
                break;
                
            case "unbanip":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs unbanip <ip>");
                    return true;
                }
                if (BanManager.getInstance().unbanIp(args[1])) {
                    sender.sendMessage(ChatColor.GREEN + "Unbanned IP " + args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "IP not found in ban list!");
                }
                break;
                
            case "banlist":
                var bans = BanManager.getInstance().getActiveBans();
                if (bans.isEmpty()) {
                    sender.sendMessage(ChatColor.GREEN + "No active bans.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Active Bans (" + bans.size() + ")");
                    for (var ban : bans) {
                        String type = ban.isIpBan ? "[IP BAN]" : "[ACCOUNT BAN]";
                        sender.sendMessage(ChatColor.YELLOW + type + " " + (ban.isIpBan ? ban.ipAddress : ban.playerName) + ChatColor.GRAY + " - " + ban.reason + " (" + (ban.isPermanent() ? "Permanent" : ban.getDurationString()) + ")");
                    }
                }
                break;
                
            case "baninfo":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs baninfo <player/ip>");
                    return true;
                }
                var info = BanManager.getInstance().getBanInfo(args[1]);
                if (info == null) {
                    sender.sendMessage(ChatColor.RED + "Not banned!");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "Ban Info");
                    sender.sendMessage(ChatColor.YELLOW + "Target: " + ChatColor.WHITE + (info.isIpBan ? info.ipAddress : info.playerName));
                    sender.sendMessage(ChatColor.YELLOW + "Reason: " + ChatColor.WHITE + info.reason);
                    sender.sendMessage(ChatColor.YELLOW + "Banned by: " + ChatColor.WHITE + info.bannedBy);
                    sender.sendMessage(ChatColor.YELLOW + "Duration: " + ChatColor.WHITE + (info.isPermanent() ? "Permanent" : info.getDurationString()));
                    if (!info.isIpBan) {
                        sender.sendMessage(ChatColor.YELLOW + "IP: " + ChatColor.WHITE + info.ipAddress);
                    }
                }
                break;
                
            case "kick":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs kick <player> [reason]");
                    return true;
                }
                Player kickTarget = Bukkit.getPlayer(args[1]);
                if (kickTarget == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                StringBuilder kickReason = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    kickReason.append(args[i]).append(" ");
                }
                if (kickReason.length() == 0) kickReason.append("Kicked by staff");
                kickTarget.kickPlayer(ChatColor.RED + "[FrostShield]\n" + ChatColor.WHITE + kickReason.toString());
                sender.sendMessage(ChatColor.GREEN + "Kicked " + kickTarget.getName());
                Logger.log("&7[FrostShield] &f" + kickTarget.getName() + " kicked by " + sender.getName());
                break;
                
            case "lookup":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs lookup <player>");
                    return true;
                }
                Player lookupTarget = Bukkit.getPlayer(args[1]);
                if (lookupTarget == null) {
                    sender.sendMessage(ChatColor.RED + "Player not online!");
                    return true;
                }
                String playerIp = BanManager.getInstance().getPlayerIp(lookupTarget);
                sender.sendMessage(ChatColor.GOLD + "Player Info");
                sender.sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.WHITE + lookupTarget.getName());
                sender.sendMessage(ChatColor.YELLOW + "UUID: " + ChatColor.WHITE + lookupTarget.getUniqueId());
                sender.sendMessage(ChatColor.YELLOW + "IP: " + ChatColor.WHITE + (playerIp != null ? playerIp : "unknown"));
                sender.sendMessage(ChatColor.YELLOW + "GameMode: " + ChatColor.WHITE + lookupTarget.getGameMode().name());
                sender.sendMessage(ChatColor.YELLOW + "World: " + ChatColor.WHITE + lookupTarget.getWorld().getName());
                sender.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.WHITE + lookupTarget.getLocation().getBlockX() + ", " + lookupTarget.getLocation().getBlockY() + ", " + lookupTarget.getLocation().getBlockZ());
                break;
                
            case "alts":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs alts <player>");
                    return true;
                }
                Player altTarget = Bukkit.getPlayer(args[1]);
                if (altTarget == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                String ip = BanManager.getInstance().getPlayerIp(altTarget);
                if (ip == null) {
                    sender.sendMessage(ChatColor.RED + "Could not get IP for that player!");
                    return true;
                }
                var alts = BanManager.getInstance().getAltAccounts(ip);
                sender.sendMessage(ChatColor.GOLD + "Alt Accounts for " + altTarget.getName() + " (IP: " + ip + ")");
                if (alts.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "No alt accounts found");
                } else {
                    for (String alt : alts) {
                        sender.sendMessage(ChatColor.YELLOW + "- " + alt);
                    }
                }
                break;
                
            case "alert":
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "Usage: /fs alert <player> <level> <message>");
                    sender.sendMessage(ChatColor.GRAY + "Levels: CRITICAL, WARNING, INFO");
                    return true;
                }
                Player alertTarget = Bukkit.getPlayer(args[1]);
                if (alertTarget == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                String level = args[2].toUpperCase();
                StringBuilder alertMsg = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    alertMsg.append(args[i]).append(" ");
                }
                String finalMsg = alertMsg.toString().trim();
                
                int alertDuration;
                String title;
                
                switch (level) {
                    case "CRITICAL":
                        alertDuration = 100;
                        title = ChatColor.DARK_RED + "" + ChatColor.BOLD + "CRITICAL ALERT";
                        sendBlinkingAlert(alertTarget, title, ChatColor.RED + finalMsg, alertDuration, Sound.ENTITY_WITHER_SPAWN, Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
                        break;
                    case "WARNING":
                        alertDuration = 70;
                        title = ChatColor.GOLD + "" + ChatColor.BOLD + "WARNING";
                        sendBlinkingAlert(alertTarget, title, ChatColor.YELLOW + finalMsg, alertDuration, Sound.BLOCK_NOTE_BLOCK_PLING, Sound.BLOCK_ANVIL_LAND);
                        break;
                    case "INFO":
                        alertDuration = 40;
                        title = ChatColor.BLUE + "" + ChatColor.BOLD + "INFO";
                        sendBlinkingAlert(alertTarget, title, ChatColor.AQUA + finalMsg, alertDuration, Sound.BLOCK_NOTE_BLOCK_PLING, null);
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid level! Use: CRITICAL, WARNING, INFO");
                        return true;
                }
                
                sender.sendMessage(ChatColor.GREEN + "Alert sent to " + alertTarget.getName() + " (Level: " + level + ")");
                DiscordLogger.logStaffAction(sender.getName(), alertTarget.getName(), level + " alert: " + finalMsg);
                break;
                
            case "notify":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can use this!");
                    return true;
                }
                Player p = (Player) sender;
                var data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
                boolean newState = !data.areNotificationsEnabled();
                data.setNotificationsEnabled(newState);
                p.sendMessage(newState ? ChatColor.GREEN + "[FrostShield] Notifications: ENABLED" : ChatColor.RED + "[FrostShield] Notifications: DISABLED");
                break;
                
            case "reload":
                FrostShield.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "FrostShield config reloaded!");
                Logger.log("&7[FrostShield] &aConfig reloaded by " + sender.getName());
                break;
                
            case "stats":
                if (sender instanceof Player) {
                    Player p2 = (Player) sender;
                    var stats = FrostShield.getInstance().getPlayerDataManager().getData(p2.getUniqueId());
                    if (stats != null) {
                        sender.sendMessage(ChatColor.GOLD + "Your Stats");
                        sender.sendMessage(ChatColor.YELLOW + "Violations: " + ChatColor.WHITE + stats.getTotalViolations());
                        sender.sendMessage(ChatColor.YELLOW + "Air Ticks: " + ChatColor.WHITE + stats.getAirTicks());
                        sender.sendMessage(ChatColor.YELLOW + "Ground Ticks: " + ChatColor.WHITE + stats.getGroundTicks());
                    } else {
                        sender.sendMessage(ChatColor.RED + "No data yet!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Console can't use stats!");
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
                    sender.sendMessage(ChatColor.RED + "Usage: /fs toggle <check>");
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
    
    private void sendBlinkingAlert(Player player, String title, String subtitle, int durationTicks, Sound firstSound, Sound secondSound) {
        new BukkitRunnable() {
            int ticks = 0;
            boolean show = true;
            
            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }
                
                if (ticks >= durationTicks) {
                    player.sendTitle("", "", 0, 0, 0);
                    this.cancel();
                    return;
                }
                
                if (show) {
                    player.sendTitle(title, subtitle, 0, 10, 0);
                    if (firstSound != null) {
                        player.playSound(player.getLocation(), firstSound, 1.0f, 1.0f);
                    }
                } else {
                    player.sendTitle("", "", 0, 5, 0);
                    if (secondSound != null) {
                        player.playSound(player.getLocation(), secondSound, 0.8f, 0.8f);
                    }
                }
                
                show = !show;
                ticks += 10;
            }
        }.runTaskTimer(FrostShield.getInstance(), 0L, 10L);
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
        sender.sendMessage(ChatColor.GOLD + "FrostShield Commands");
        sender.sendMessage(ChatColor.YELLOW + "/fs reload" + ChatColor.GRAY + " - Reload config");
        sender.sendMessage(ChatColor.YELLOW + "/fs stats" + ChatColor.GRAY + " - Your stats");
        sender.sendMessage(ChatColor.YELLOW + "/fs checks" + ChatColor.GRAY + " - List all checks");
        sender.sendMessage(ChatColor.YELLOW + "/fs toggle <check>" + ChatColor.GRAY + " - Toggle check");
        sender.sendMessage(ChatColor.YELLOW + "/fs notify" + ChatColor.GRAY + " - Toggle alerts");
        sender.sendMessage(ChatColor.GOLD + "Punishment Commands");
        sender.sendMessage(ChatColor.YELLOW + "/fs ban <player> <reason> [duration]" + ChatColor.GRAY + " - Ban account");
        sender.sendMessage(ChatColor.YELLOW + "/fs banip <player> <reason> [duration]" + ChatColor.GRAY + " - Ban IP");
        sender.sendMessage(ChatColor.YELLOW + "/fs unban <player>" + ChatColor.GRAY + " - Unban account");
        sender.sendMessage(ChatColor.YELLOW + "/fs unbanip <ip>" + ChatColor.GRAY + " - Unban IP");
        sender.sendMessage(ChatColor.YELLOW + "/fs banlist" + ChatColor.GRAY + " - List bans");
        sender.sendMessage(ChatColor.YELLOW + "/fs baninfo <player/ip>" + ChatColor.GRAY + " - Ban details");
        sender.sendMessage(ChatColor.YELLOW + "/fs kick <player> [reason]" + ChatColor.GRAY + " - Kick player");
        sender.sendMessage(ChatColor.YELLOW + "/fs lookup <player>" + ChatColor.GRAY + " - Player info");
        sender.sendMessage(ChatColor.YELLOW + "/fs alts <player>" + ChatColor.GRAY + " - Find alt accounts");
        sender.sendMessage(ChatColor.GOLD + "Alert Commands");
        sender.sendMessage(ChatColor.YELLOW + "/fs alert <player> CRITICAL <message>" + ChatColor.GRAY + " - 10 seconds, loud sounds");
        sender.sendMessage(ChatColor.YELLOW + "/fs alert <player> WARNING <message>" + ChatColor.GRAY + " - 7 seconds, medium sounds");
        sender.sendMessage(ChatColor.YELLOW + "/fs alert <player> INFO <message>" + ChatColor.GRAY + " - 4 seconds, subtle sounds");
    }
}
