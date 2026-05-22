package com.kaloudasdev.frostshield.listener;

import com.kaloudasdev.frostshield.FrostShield;
import com.kaloudasdev.frostshield.ban.BanManager;
import com.kaloudasdev.frostshield.utils.DiscordLogger;
import com.kaloudasdev.frostshield.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetSocketAddress;

public class JoinListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = getPlayerIp(player);
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        
        BanManager.getInstance().registerPlayerIp(player);
        
        Logger.log("&8[FrostShield] &7Player joined: &f" + name + " &7| UUID: &f" + uuid + " &7| IP: &f" + ip);
        DiscordLogger.logJoinLeave(name, uuid, ip, "joined");
        
        if (BanManager.getInstance().isIpBanned(ip)) {
            var banInfo = BanManager.getInstance().getBanInfo(ip);
            if (banInfo != null) {
                Logger.log("&c[FrostShield] &f" + name + " &cis on BANNED IP! &7IP: &f" + ip + " &7Reason: &f" + banInfo.reason);
                DiscordLogger.logJoinLeave(name, uuid, ip, "ALT DETECTED - " + banInfo.reason);
                player.kickPlayer("?c?l[FrostShield]?r\n?fYour IP is banned!?r\n?eReason: ?f" + banInfo.reason);
                return;
            }
        }
        
        if (BanManager.getInstance().isPlayerBanned(player)) {
            var banInfo = BanManager.getInstance().getBanInfo(name);
            if (banInfo != null) {
                Logger.log("&c[FrostShield] &f" + name + " &cis BANNED! IP: &f" + ip + " &cReason: &f" + banInfo.reason);
                DiscordLogger.logBan(name, banInfo.reason, banInfo.isPermanent() ? 0 : 60);
                player.kickPlayer("?c?l[FrostShield]?r\n?fYou are banned!?r\n?eReason: ?f" + banInfo.reason + "\n?eExpires: ?f" + (banInfo.isPermanent() ? "Never" : banInfo.getDurationString()));
                return;
            }
        }
        
        Logger.log("&8[FrostShield] &aPlayer &f" + name + " &ais clean - no active bans");
    }
    
    private String getPlayerIp(Player player) {
        try {
            InetSocketAddress address = (InetSocketAddress) player.getAddress();
            if (address != null && address.getAddress() != null) {
                return address.getAddress().getHostAddress();
            }
        } catch (Exception e) {
            return "unknown";
        }
        return "unknown";
    }
}
