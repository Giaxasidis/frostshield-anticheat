package com.giaxasidis.frostshield.checks.packet;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastLadder extends Check {
    public FastLadder() { super("FastLadder", "Detects fast ladder climbing", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        if (!player.getLocation().getBlock().getType().toString().contains("LADDER")) return;
        
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
        double maxSpeed = 0.25;
        
        if (deltaY > maxSpeed) {
            flag(player, data, String.format("ladderSpeed=%.4f", deltaY));
        }
    }
}
