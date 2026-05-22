package com.kaloudasdev.frostshield.ban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.kaloudasdev.frostshield.FrostShield;
import com.kaloudasdev.frostshield.utils.DiscordLogger;
import com.kaloudasdev.frostshield.utils.Logger;

public class BanManager {
    
    private static BanManager instance;
    private final Map<String, BanEntry> bannedIps;
    private final Map<UUID, BanEntry> bannedPlayers;
    private final Map<String, List<UUID>> ipToPlayers;
    private final File banFile;
    private final File altFile;
    
    public BanManager() {
        this.bannedIps = new ConcurrentHashMap<>();
        this.bannedPlayers = new ConcurrentHashMap<>();
        this.ipToPlayers = new ConcurrentHashMap<>();
        this.banFile = new File(FrostShield.getInstance().getDataFolder(), "bans.dat");
        this.altFile = new File(FrostShield.getInstance().getDataFolder(), "alts.dat");
        loadBans();
        loadAlts();
    }
    
    public static BanManager getInstance() {
        if (instance == null) {
            instance = new BanManager();
        }
        return instance;
    }
    
    public static class BanEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        public final UUID uuid;
        public final String playerName;
        public final String ipAddress;
        public final String reason;
        public final String bannedBy;
        public final long banTime;
        public final long expiryTime;
        public final boolean isIpBan;
        
        public BanEntry(UUID uuid, String playerName, String ipAddress, String reason, String bannedBy, long durationSeconds, boolean isIpBan) {
            this.uuid = uuid;
            this.playerName = playerName;
            this.ipAddress = ipAddress;
            this.reason = reason;
            this.bannedBy = bannedBy;
            this.banTime = System.currentTimeMillis();
            this.expiryTime = durationSeconds == 0 ? Long.MAX_VALUE : this.banTime + (durationSeconds * 1000);
            this.isIpBan = isIpBan;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
        
        public boolean isPermanent() {
            return expiryTime == Long.MAX_VALUE;
        }
        
        public String getDurationString() {
            if (isPermanent()) return "Permanent";
            long diff = expiryTime - System.currentTimeMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            long hours = (diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
            long minutes = (diff % (60 * 60 * 1000)) / (60 * 1000);
            if (days > 0) return days + "d " + hours + "h";
            if (hours > 0) return hours + "h " + minutes + "m";
            return minutes + "m";
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadBans() {
        if (!banFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(banFile))) {
            Map<String, BanEntry> loadedIps = (Map<String, BanEntry>) ois.readObject();
            Map<UUID, BanEntry> loadedPlayers = (Map<UUID, BanEntry>) ois.readObject();
            loadedIps.forEach((ip, entry) -> { if (!entry.isExpired()) bannedIps.put(ip, entry); });
            loadedPlayers.forEach((uuid, entry) -> { if (!entry.isExpired()) bannedPlayers.put(uuid, entry); });
            Logger.log("&8[FrostShield] &7Loaded &f" + (bannedIps.size() + bannedPlayers.size()) + " &7active bans");
        } catch (Exception e) {
            Logger.log("&8[FrostShield] &cFailed to load bans: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadAlts() {
        if (!altFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(altFile))) {
            Map<String, List<UUID>> loaded = (Map<String, List<UUID>>) ois.readObject();
            ipToPlayers.putAll(loaded);
        } catch (Exception e) { }
    }
    
    private void saveBans() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(banFile))) {
            oos.writeObject(bannedIps);
            oos.writeObject(bannedPlayers);
        } catch (Exception e) {
            Logger.log("&8[FrostShield] &cFailed to save bans: " + e.getMessage());
        }
    }
    
    private void saveAlts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(altFile))) {
            oos.writeObject(ipToPlayers);
        } catch (Exception e) { }
    }
    
    public void registerPlayerIp(Player player) {
        String ip = getPlayerIp(player);
        if (ip == null || ip.equals("unknown")) return;
        UUID uuid = player.getUniqueId();
        ipToPlayers.computeIfAbsent(ip, k -> new ArrayList<>()).add(uuid);
        saveAlts();
    }
    
    public List<String> getAltAccounts(String ip) {
        List<String> alts = new ArrayList<>();
        List<UUID> uuids = ipToPlayers.get(ip);
        if (uuids != null) {
            for (UUID uuid : uuids) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    alts.add(p.getName());
                } else {
                    alts.add(uuid.toString());
                }
            }
        }
        return alts;
    }
    
    public boolean isPlayerBanned(Player player) {
        if (bannedPlayers.containsKey(player.getUniqueId())) {
            BanEntry entry = bannedPlayers.get(player.getUniqueId());
            if (entry.isExpired()) {
                bannedPlayers.remove(player.getUniqueId());
                return false;
            }
            return true;
        }
        String ip = getPlayerIp(player);
        if (ip != null && bannedIps.containsKey(ip)) {
            BanEntry entry = bannedIps.get(ip);
            if (entry.isExpired()) {
                bannedIps.remove(ip);
                return false;
            }
            return true;
        }
        return false;
    }
    
    public String getPlayerIp(Player player) {
        try {
            InetSocketAddress address = (InetSocketAddress) player.getAddress();
            if (address != null && address.getAddress() != null) {
                return address.getAddress().getHostAddress();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    public void banPlayer(Player player, String reason, String bannedBy, long durationSeconds) {
        String ip = getPlayerIp(player);
        if (ip == null) ip = "unknown";
        
        BanEntry entry = new BanEntry(player.getUniqueId(), player.getName(), ip, reason, bannedBy, durationSeconds, false);
        bannedPlayers.put(player.getUniqueId(), entry);
        saveBans();
        
        String durationMsg = durationSeconds == 0 ? "permanently" : "for " + entry.getDurationString();
        
        Logger.log("&c&l[FrostShield] &f" + player.getName() + " &cwas banned " + durationMsg + " by " + bannedBy + " &7Reason: &f" + reason);
        
        String broadcastMsg = "&c&l[FrostShield] &f" + player.getName() + " &7was banned for &f" + reason;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("frostshield.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', broadcastMsg));
            }
        }
        
        DiscordLogger.logBan(player.getName(), reason, (int)(durationSeconds / 60));
        
        String kickMsg = "?c?l[FrostShield]?r\n?fYou have been banned!\n?eReason: ?f" + reason + "\n?eDuration: ?f" + (durationSeconds == 0 ? "Permanent" : entry.getDurationString()) + "\n?eBanned by: ?f" + bannedBy;
        player.kickPlayer(kickMsg);
    }
    
    public void banIp(Player player, String reason, String bannedBy, long durationSeconds) {
        String ip = getPlayerIp(player);
        if (ip == null || ip.equals("unknown")) {
            Logger.log("&c[FrostShield] &fCannot ban IP for " + player.getName() + " - IP unknown");
            return;
        }
        
        BanEntry entry = new BanEntry(player.getUniqueId(), player.getName(), ip, reason, bannedBy, durationSeconds, true);
        bannedIps.put(ip, entry);
        
        for (Player online : Bukkit.getOnlinePlayers()) {
            String onlineIp = getPlayerIp(online);
            if (ip.equals(onlineIp)) {
                String kickMsg = "?c?l[FrostShield]?r\n?fYour IP has been banned!\n?eReason: ?f" + reason + "\n?eDuration: ?f" + (durationSeconds == 0 ? "Permanent" : entry.getDurationString()) + "\n?eBanned by: ?f" + bannedBy;
                online.kickPlayer(kickMsg);
            }
        }
        
        saveBans();
        
        String durationMsg = durationSeconds == 0 ? "permanently" : "for " + entry.getDurationString();
        Logger.log("&c&l[FrostShield] &fIP " + ip + " &cwas banned " + durationMsg + " by " + bannedBy + " &7Reason: &f" + reason);
        
        String broadcastMsg = "&c&l[FrostShield] &fIP " + ip + " &7was banned for &f" + reason;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("frostshield.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', broadcastMsg));
            }
        }
        
        DiscordLogger.logInfo("IP BAN: " + ip + " banned by " + bannedBy + " - Reason: " + reason);
    }
    
    public boolean unbanPlayer(String target) {
        UUID targetUuid = null;
        String targetName = null;
        
        for (Map.Entry<UUID, BanEntry> entry : bannedPlayers.entrySet()) {
            if (entry.getValue().playerName.equalsIgnoreCase(target) || entry.getKey().toString().equals(target)) {
                targetUuid = entry.getKey();
                targetName = entry.getValue().playerName;
                break;
            }
        }
        
        if (targetUuid == null) return false;
        
        bannedPlayers.remove(targetUuid);
        saveBans();
        
        Logger.log("&8[FrostShield] &a" + targetName + " was unbanned");
        
        String broadcastMsg = "&a&l[FrostShield] &f" + targetName + " &7was unbanned";
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("frostshield.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', broadcastMsg));
            }
        }
        
        return true;
    }
    
    public boolean unbanIp(String ip) {
        if (!bannedIps.containsKey(ip)) return false;
        bannedIps.remove(ip);
        saveBans();
        Logger.log("&8[FrostShield] &aIP " + ip + " was unbanned");
        
        String broadcastMsg = "&a&l[FrostShield] &fIP " + ip + " &7was unbanned";
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("frostshield.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', broadcastMsg));
            }
        }
        
        return true;
    }
    
    public List<BanEntry> getActiveBans() {
        List<BanEntry> active = new ArrayList<>();
        active.addAll(bannedPlayers.values());
        active.addAll(bannedIps.values());
        active.sort((a, b) -> Long.compare(b.banTime, a.banTime));
        return active;
    }
    
    public BanEntry getBanInfo(String target) {
        for (BanEntry entry : bannedPlayers.values()) {
            if (entry.playerName.equalsIgnoreCase(target)) return entry;
        }
        for (Map.Entry<String, BanEntry> entry : bannedIps.entrySet()) {
            if (entry.getKey().equals(target)) return entry.getValue();
        }
        return null;
    }
    
    public boolean isIpBanned(String ip) {
        return bannedIps.containsKey(ip);
    }
}
