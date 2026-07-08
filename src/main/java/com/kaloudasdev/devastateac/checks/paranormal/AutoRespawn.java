package com.giaxasidis.frostshield.checks.paranormal;

import com.giaxasidis.frostshield.checks.Check;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoRespawn extends Check {
    private final Map<UUID, Long> deathTime = new HashMap<>();
    
    public AutoRespawn() { super("AutoRespawn", "Detects auto respawn hacks", 10); }

    @Override
    public void handle(Player player, PlayerData data, Event event, Object... args) {
        if (event instanceof PlayerDeathEvent) {
            deathTime.put(player.getUniqueId(), System.currentTimeMillis());
        }
        
        if (event instanceof PlayerRespawnEvent) {
            UUID uuid = player.getUniqueId();
            Long died = deathTime.get(uuid);
            if (died != null) {
                long delay = System.currentTimeMillis() - died;
                if (delay < 500) {
                    flag(player, data, String.format("respawnDelay=%dms", delay));
                }
                deathTime.remove(uuid);
            }
        }
    }
}
