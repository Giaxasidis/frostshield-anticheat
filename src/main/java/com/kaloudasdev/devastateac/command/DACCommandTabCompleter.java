package com.giaxasidis.frostshield.command;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.ban.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DACCommandTabCompleter implements TabCompleter {

    private static final List<String> MAIN_COMMANDS = Arrays.asList(
        "ban", "banip", "unban", "unbanip", "banlist", "baninfo",
        "kick", "lookup", "alts", "alert", "notify", "reload", 
        "stats", "checks", "toggle", "help"
    );
    
    private static final List<String> ALERT_LEVELS = Arrays.asList(
        "CRITICAL", "WARNING", "INFO"
    );
    
    private static final List<String> DURATIONS = Arrays.asList(
        "10s", "30s", "1m", "5m", "10m", "30m", "1h", "2h", "6h", "12h", "1d", "2d", "7d", "30d"
    );

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (!sender.hasPermission("frostshield.admin")) {
            return completions;
        }
        
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            completions = MAIN_COMMANDS.stream()
                .filter(cmd -> cmd.startsWith(partial))
                .collect(Collectors.toList());
        }
        else if (args.length == 2) {
            String subCmd = args[0].toLowerCase();
            
            switch (subCmd) {
                case "ban":
                case "banip":
                case "kick":
                case "lookup":
                case "alts":
                    completions = Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                    break;
                    
                case "unban":
                case "baninfo":
                    completions = BanManager.getInstance().getActiveBans().stream()
                        .filter(ban -> ban.playerName != null && !ban.isIpBan)
                        .map(ban -> ban.playerName)
                        .filter(name -> name != null && name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                    break;
                    
                case "unbanip":
                    completions = BanManager.getInstance().getActiveBans().stream()
                        .filter(ban -> ban.isIpBan && ban.ipAddress != null)
                        .map(ban -> ban.ipAddress)
                        .filter(ip -> ip != null && ip.startsWith(args[1]))
                        .collect(Collectors.toList());
                    break;
                    
                case "alert":
                    completions = Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                    break;
                    
                case "toggle":
                    completions = FrostShield.getInstance().getCheckManager().getChecks().stream()
                        .map(check -> check.name)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                    break;
            }
        }
        else if (args.length == 3) {
            String subCmd = args[0].toLowerCase();
            
            if (subCmd.equals("ban") || subCmd.equals("banip")) {
                String partial = args[2].toLowerCase();
                completions = DURATIONS.stream()
                    .filter(d -> d.startsWith(partial))
                    .collect(Collectors.toList());
            }
            else if (subCmd.equals("alert")) {
                String partial = args[2].toUpperCase();
                completions = ALERT_LEVELS.stream()
                    .filter(level -> level.startsWith(partial))
                    .collect(Collectors.toList());
            }
        }
        else if (args.length == 4) {
            String subCmd = args[0].toLowerCase();
            if (subCmd.equals("alert")) {
                completions = Arrays.asList("your_message_here");
            }
        }
        
        return completions;
    }
}
