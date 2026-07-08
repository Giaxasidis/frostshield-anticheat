package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoClicker extends Check {
    private long lastClick = 0;
    private final List<Long> clickTimes = new ArrayList<>();
    private int suspiciousPatternCount = 0;
    private double lastCps = 0;
    
    public AutoClicker() { super("AutoClicker", "Detects autoclicker with pattern analysis", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerInteractEvent)) return;
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        
        long now = System.currentTimeMillis();
        
        if (lastClick != 0) {
            long diff = now - lastClick;
            
            clickTimes.add(diff);
            if (clickTimes.size() > 40) {
                clickTimes.remove(0);
            }
            
            if (diff < 30) {
                data.incrementAttackCount();
                if (data.getAttackCount() > 20) {
                    flag(player, data, String.format("cps=%.2f (too fast)", 1000.0 / diff));
                    data.resetAttackCount();
                }
            } else {
                data.resetAttackCount();
            }
            
            if (clickTimes.size() > 20) {
                double sum = 0;
                double sumSq = 0;
                for (long t : clickTimes) {
                    sum += t;
                    sumSq += t * t;
                }
                double mean = sum / clickTimes.size();
                double variance = (sumSq / clickTimes.size()) - (mean * mean);
                double stdDev = Math.sqrt(variance);
                
                if (stdDev < 2.0 && mean < 60) {
                    suspiciousPatternCount++;
                    if (suspiciousPatternCount > 15) {
                        flag(player, data, String.format("patternAutoClicker mean=%.2f stdDev=%.2f", mean, stdDev));
                        suspiciousPatternCount = 0;
                    }
                } else {
                    suspiciousPatternCount = Math.max(0, suspiciousPatternCount - 1);
                }
            }
            
            if (clickTimes.size() > 20) {
                long windowStart = clickTimes.get(0);
                long windowEnd = clickTimes.get(clickTimes.size() - 1);
                long duration = windowEnd - windowStart;
                if (duration > 0) {
                    double cps = (clickTimes.size() * 1000.0) / duration;
                    if (cps > 18 && duration > 3000) {
                        flag(player, data, String.format("sustainedCPS=%.2f over %dms", cps, duration));
                    }
                    lastCps = cps;
                }
            }
            
            if (clickTimes.size() > 10) {
                long minDiff = Long.MAX_VALUE;
                long maxDiff = Long.MIN_VALUE;
                for (long t : clickTimes) {
                    if (t < minDiff) minDiff = t;
                    if (t > maxDiff) maxDiff = t;
                }
                if (maxDiff - minDiff < 5 && minDiff < 50) {
                    flag(player, data, String.format("zeroVariation min=%d max=%d", minDiff, maxDiff));
                }
            }
            
            if (diff < 40) {
                long recentClicks = 1;
                for (int i = clickTimes.size() - 2; i >= 0 && i >= clickTimes.size() - 20; i--) {
                    if (clickTimes.get(i) < 50) recentClicks++;
                }
                if (recentClicks > 20) {
                    flag(player, data, String.format("burstCPS=%d", recentClicks * 2));
                }
            }
        }
        
        lastClick = now;
    }
}
