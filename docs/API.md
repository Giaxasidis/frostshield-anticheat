# Developer API

## Getting Started

Add FrostShield as a dependency in your `plugin.yml`:

```yaml
depend: [FrostShield]
```

## Maven Dependency

```xml
<repository>
    <id>frostshield-repo</id>
    <url>https://repo.kaloudasdev.com/releases</url>
</repository>

<dependency>
    <groupId>com.kaloudasdev</groupId>
    <artifactId>FrostShield</artifactId>
    <version>2.0.0</version>
    <scope>provided</scope>
</dependency>
```

## API Methods

### Getting Plugin Instance

```java
import com.kaloudasdev.frostshield.FrostShield;

FrostShield frostShield = FrostShield.getInstance();
```

### Player Data Manager

```java
PlayerDataManager dataManager = frostShield.getPlayerDataManager();

PlayerData playerData = dataManager.getPlayerData(player);

int violations = playerData.getViolations("speed");

playerData.resetViolations("speed");
```

### Punishment Manager

```java
PunishmentManager punishmentManager = frostShield.getPunishmentManager();

boolean isBanned = punishmentManager.isPlayerBanned(player);

String banReason = punishmentManager.getBanReason(player);

punishmentManager.punishPlayer(player, "speed", violations, maxViolations);
```

### Check Manager

```java
CheckManager checkManager = frostShield.getCheckManager();

List<Check> activeChecks = checkManager.getActiveChecks();

Check check = checkManager.getCheck("speed");

boolean isEnabled = checkManager.isCheckEnabled("speed");
```

### Event Listening

```java
import com.kaloudasdev.frostshield.events.PlayerViolationEvent;

@EventHandler
public void onViolation(PlayerViolationEvent event) {
    Player player = event.getPlayer();
    String checkName = event.getCheckName();
    int violations = event.getViolations();
    
    getLogger().info(player.getName() + " failed " + checkName + " (VL: " + violations + ")");
}
```

### Discord Logger

```java
DiscordLogger.sendMessage("Custom message here");

DiscordLogger.sendViolationAlert(player, checkName, violations);
```