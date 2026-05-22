package com.kaloudasdev.frostshield.data;

import java.util.*;

public class PlayerData {
    private final UUID uuid;
    private final String name;
    private int totalViolations;
    private final Map<String, Integer> checkViolations;
    private double lastX, lastY, lastZ;
    private float lastYaw, lastPitch;
    private boolean onGround, wasOnGround;
    private int airTicks, groundTicks, teleportTicks, velocityTicks;
    private final List<Double> speedHistory;
    private final List<Long> packetTimestamps;
    private final List<Float> rotationHistory;
    private long lastAttackTime;
    private int attackCount;
    private double lastDeltaX, lastDeltaY, lastDeltaZ;
    private boolean notificationsEnabled = true;
    private boolean wasInWater = false;
    
    public PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.totalViolations = 0;
        this.checkViolations = new HashMap<>();
        this.speedHistory = new ArrayList<>();
        this.packetTimestamps = new ArrayList<>();
        this.rotationHistory = new ArrayList<>();
        this.onGround = true;
        this.wasOnGround = true;
        this.notificationsEnabled = true;
    }
    
    public void addViolation(String check, int amount) { totalViolations += amount; checkViolations.merge(check, amount, Integer::sum); }
    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public int getTotalViolations() { return totalViolations; }
    public Map<String, Integer> getCheckViolations() { return checkViolations; }
    public double getLastX() { return lastX; }
    public void setLastX(double lastX) { this.lastX = lastX; }
    public double getLastY() { return lastY; }
    public void setLastY(double lastY) { this.lastY = lastY; }
    public double getLastZ() { return lastZ; }
    public void setLastZ(double lastZ) { this.lastZ = lastZ; }
    public float getLastYaw() { return lastYaw; }
    public void setLastYaw(float lastYaw) { this.lastYaw = lastYaw; }
    public float getLastPitch() { return lastPitch; }
    public void setLastPitch(float lastPitch) { this.lastPitch = lastPitch; }
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.wasOnGround = this.onGround; this.onGround = onGround; }
    public boolean wasOnGround() { return wasOnGround; }
    public int getAirTicks() { return airTicks; }
    public void setAirTicks(int airTicks) { this.airTicks = airTicks; }
    public void incrementAirTicks() { this.airTicks++; }
    public void resetAirTicks() { this.airTicks = 0; }
    public int getGroundTicks() { return groundTicks; }
    public void setGroundTicks(int groundTicks) { this.groundTicks = groundTicks; }
    public void incrementGroundTicks() { this.groundTicks++; }
    public void resetGroundTicks() { this.groundTicks = 0; }
    public int getTeleportTicks() { return teleportTicks; }
    public void setTeleportTicks(int teleportTicks) { this.teleportTicks = teleportTicks; }
    public int getVelocityTicks() { return velocityTicks; }
    public void setVelocityTicks(int velocityTicks) { this.velocityTicks = velocityTicks; }
    public List<Double> getSpeedHistory() { return speedHistory; }
    public List<Long> getPacketTimestamps() { return packetTimestamps; }
    public List<Float> getRotationHistory() { return rotationHistory; }
    public long getLastAttackTime() { return lastAttackTime; }
    public void setLastAttackTime(long lastAttackTime) { this.lastAttackTime = lastAttackTime; }
    public int getAttackCount() { return attackCount; }
    public void setAttackCount(int attackCount) { this.attackCount = attackCount; }
    public void incrementAttackCount() { this.attackCount++; }
    public void resetAttackCount() { this.attackCount = 0; }
    public double getLastDeltaX() { return lastDeltaX; }
    public void setLastDeltaX(double lastDeltaX) { this.lastDeltaX = lastDeltaX; }
    public double getLastDeltaY() { return lastDeltaY; }
    public void setLastDeltaY(double lastDeltaY) { this.lastDeltaY = lastDeltaY; }
    public double getLastDeltaZ() { return lastDeltaZ; }
    public void setLastDeltaZ(double lastDeltaZ) { this.lastDeltaZ = lastDeltaZ; }
    public boolean areNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean enabled) { this.notificationsEnabled = enabled; }
    public boolean wasInWater() { return wasInWater; }
    public void setWasInWater(boolean wasInWater) { this.wasInWater = wasInWater; }
    
    public void tick() {
        if (teleportTicks > 0) teleportTicks--;
        if (velocityTicks > 0) velocityTicks--;
        if (onGround) { groundTicks++; airTicks = 0; }
        else { airTicks++; groundTicks = 0; }
        if (attackCount > 0) attackCount--;
        if (speedHistory.size() > 20) speedHistory.remove(0);
        if (packetTimestamps.size() > 100) packetTimestamps.remove(0);
        if (rotationHistory.size() > 50) rotationHistory.remove(0);
    }
}

