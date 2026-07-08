package com.giaxasidis.frostshield.checks.packet;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class Timer extends Check {
    private long lastMove = 0;
    private int fastPackets = 0;
    
    public Timer() { super("Timer", "Detects timer / speed packet manipulation", 25); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        
        long now = System.currentTimeMillis();
        if (lastMove != 0) {
            long diff = now - lastMove;
            if (diff < 20) {
                fastPackets++;
                if (fastPackets > 50) {
                    flag(player, data, String.format("packetRate=%dms", diff));
                    fastPackets = 0;
                }
            } else {
                fastPackets = Math.max(0, fastPackets - 1);
            }
        }
        lastMove = now;
    }
}
