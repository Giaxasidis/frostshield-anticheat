package com.giaxasidis.frostshield.checks.paranormal;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestAura extends Check {
    private long lastChestOpen = 0;
    
    public ChestAura() { super("ChestAura", "Detects chest aura / auto loot", 12); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerInteractEvent)) return;
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        
        if (e.getClickedBlock() != null && e.getClickedBlock().getState() instanceof Chest) {
            long now = System.currentTimeMillis();
            if (lastChestOpen != 0 && now - lastChestOpen < 100) {
                flag(player, data, String.format("chestOpenDelay=%dms", now - lastChestOpen));
            }
            lastChestOpen = now;
        }
    }
}
