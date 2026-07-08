package com.giaxasidis.frostshield.checks.movement;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class JumpReset extends Check {
    private double lastDeltaY = 0;
    private int waterJumpTicks = 0;
    
    public JumpReset() { super("JumpReset", "Detects jump reset hacks", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getTo() == null) return;
        
        boolean isInWater = player.isInWater() || player.isSwimming();
        double deltaY = e.getTo().getY() - e.getFrom().getY();
        
        if (isInWater && deltaY > 0.3 && lastDeltaY < 0) {
            waterJumpTicks++;
            if (waterJumpTicks > 8) {
                flag(player, data, String.format("waterJumpReset deltaY=%.2f", deltaY));
                waterJumpTicks = 0;
            }
        } else {
            waterJumpTicks = Math.max(0, waterJumpTicks - 1);
        }
        
        lastDeltaY = deltaY;
    }
}
