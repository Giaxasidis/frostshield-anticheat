package com.giaxasidis.frostshield.checks.combat;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoTotem extends Check {
    private final Map<UUID, Long> lastTotemEquip = new HashMap<>();
    
    public AutoTotem() { super("AutoTotem", "Detects auto totem hacks", 15); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (!(event instanceof PlayerInteractEvent)) return;
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        
        if (e.getItem() != null && e.getItem().getType() == Material.TOTEM_OF_UNDYING) {
            long now = System.currentTimeMillis();
            UUID uuid = player.getUniqueId();
            long last = lastTotemEquip.getOrDefault(uuid, 0L);
            
            if (now - last < 100) {
                flag(player, data, String.format("totemEquipDelay=%dms", now - last));
            }
            lastTotemEquip.put(uuid, now);
        }
    }
}
