package com.giaxasidis.frostshield.utils;

import com.giaxasidis.frostshield.FrostShield;
import java.time.Instant;

public class DiscordLogger {
    
    private static boolean enabled = false;
    private static String cheatWebhook = "";
    private static String banWebhook = "";
    private static String staffWebhook = "";
    private static String joinWebhook = "";
    private static String consoleWebhook = "";
    
    public static void init() {
        cheatWebhook = FrostShield.getInstance().getConfig().getString("discord.webhooks.cheat-detection", "");
        banWebhook = FrostShield.getInstance().getConfig().getString("discord.webhooks.ban-system", "");
        staffWebhook = FrostShield.getInstance().getConfig().getString("discord.webhooks.staff-actions", "");
        joinWebhook = FrostShield.getInstance().getConfig().getString("discord.webhooks.join-leave", "");
        consoleWebhook = FrostShield.getInstance().getConfig().getString("discord.webhooks.console-logs", "");
        
        enabled = FrostShield.getInstance().getConfig().getBoolean("discord.enabled", false);
        if (enabled) {
            logInfo("FrostShield v" + FrostShield.getInstance().getDescription().getVersion() + " started!");
        }
    }
    
    private static void sendToWebhook(String webhookUrl, String title, String description, int color) {
        if (!enabled || webhookUrl == null || webhookUrl.isEmpty()) return;
        
        FrostShield.getInstance().getServer().getScheduler().runTaskAsynchronously(FrostShield.getInstance(), () -> {
            try {
                DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                webhook.setUsername("FrostShield");
                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                embed.setTitle(title);
                embed.setColor(color);
                embed.setDescription(description);
                embed.setTimestamp(Instant.now().toString());
                webhook.addEmbed(embed);
                webhook.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public static void logCheat(String player, String check, int vl, int maxVl, String details) {
        if (!enabled || cheatWebhook.isEmpty()) return;
        String desc = "**" + player + "** failed **" + check + "**\nVL: " + vl + "/" + maxVl + "\nDetails: " + details;
        sendToWebhook(cheatWebhook, "CHEAT DETECTED", desc, 0xFF0000);
    }
    
    public static void logBan(String player, String reason, int durationMinutes) {
        if (!enabled || banWebhook.isEmpty()) return;
        String duration = durationMinutes == 0 ? "Permanent" : durationMinutes + " minutes";
        String desc = "**" + player + "** was banned!\nReason: " + reason + "\nDuration: " + duration;
        sendToWebhook(banWebhook, "PLAYER BANNED", desc, 0xFF0000);
    }
    
    public static void logStaffAction(String staff, String target, String action) {
        if (!enabled || staffWebhook.isEmpty()) return;
        String desc = "**" + staff + "** " + action + " **" + target + "**";
        sendToWebhook(staffWebhook, "STAFF ACTION", desc, 0x00FF00);
    }
    
    public static void logJoinLeave(String player, String uuid, String ip, String type) {
        if (!enabled || joinWebhook.isEmpty()) return;
        String desc = "**" + player + "** " + type + "\nUUID: " + uuid + "\nIP: " + ip;
        int color = type.equals("joined") ? 0x00FF00 : 0xFFAA00;
        sendToWebhook(joinWebhook, type.toUpperCase(), desc, color);
    }
    
    public static void logInfo(String message) {
        if (!enabled || consoleWebhook.isEmpty()) return;
        sendToWebhook(consoleWebhook, "INFO", message, 0x00FFFF);
    }
    
    public static void logConsole(String level, String message) {
        if (!enabled || consoleWebhook.isEmpty()) return;
        int color = 0xFFFFFF;
        if (level.equals("WARN")) color = 0xFFAA00;
        if (level.equals("ERROR")) color = 0xFF0000;
        sendToWebhook(consoleWebhook, level, message, color);
    }
    
    public static boolean isEnabled() { return enabled; }
}
