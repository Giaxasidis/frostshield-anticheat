package com.giaxasidis.frostshield.listener;

import com.giaxasidis.frostshield.FrostShield;
import com.giaxasidis.frostshield.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class BukkitListener implements Listener {
    private final JoinListener joinListener = new JoinListener();
    public JoinListener getJoinListener() { return joinListener; }
    
    private boolean shouldCheck(Player player) {
        if (player.hasPermission("frostshield.bypass")) return false;
        if (player.getGameMode() == GameMode.CREATIVE) return false;
        if (player.getGameMode() == GameMode.SPECTATOR) return false;
        return true;
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        if (event.getTo() != null) {
            double deltaX = event.getTo().getX() - event.getFrom().getX();
            double deltaZ = event.getTo().getZ() - event.getFrom().getZ();
            data.setLastDeltaX(deltaX);
            data.setLastDeltaZ(deltaZ);
            data.setLastDeltaY(event.getTo().getY() - event.getFrom().getY());
        }
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        if (event.getTo() != null) {
            data.setLastX(event.getFrom().getX());
            data.setLastY(event.getFrom().getY());
            data.setLastZ(event.getFrom().getZ());
            data.setLastYaw(event.getFrom().getYaw());
            data.setLastPitch(event.getFrom().getPitch());
            data.setOnGround(p.isOnGround());
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player p = (Player) event.getDamager();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player p = (Player) event.getEntity();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        data.setTeleportTicks(5);
    }
    
    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle().getPassengers().isEmpty()) return;
        if (!(event.getVehicle().getPassengers().get(0) instanceof Player)) return;
        
        Player p = (Player) event.getVehicle().getPassengers().get(0);
        if (!shouldCheck(p)) return;
        
        PlayerData data = FrostShield.getInstance().getPlayerDataManager().getOrCreateData(p.getUniqueId(), p.getName());
        
        FrostShield.getInstance().getCheckManager().getChecks().forEach(check -> {
            try {
                check.handle(p, data, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FrostShield.getInstance().getPlayerDataManager().removeData(event.getPlayer().getUniqueId());
    }
}

