package com.kaloudasdev.frostshield.data;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private final ConcurrentHashMap<UUID, PlayerData> dataMap = new ConcurrentHashMap<>();
    
    public PlayerData getData(UUID uuid) { return dataMap.get(uuid); }
    public PlayerData getOrCreateData(UUID uuid, String name) { return dataMap.computeIfAbsent(uuid, k -> new PlayerData(uuid, name)); }
    public void removeData(UUID uuid) { dataMap.remove(uuid); }
    public void tickAll() { dataMap.values().forEach(PlayerData::tick); }
    public void saveAllData() { }
}
