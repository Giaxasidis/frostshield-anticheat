## FrostShield AntiCheat

[![Spigot 1.16.5](https://img.shields.io/badge/Spigot-1.16.5-orange.svg?logo=spigotmc&logoColor=orange)](https://www.spigotmc.org/)
[![Java 17](https://img.shields.io/badge/Java-17-blue.svg?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![License: Proprietary](https://img.shields.io/badge/License-Proprietary-red.svg)](LICENSE.md)
[![Version](https://img.shields.io/badge/Version-2.0.0-brightgreen.svg)](https://github.com/KaloudasDev/frostshield/releases)

**Freeze every cheat. Forever.**

FrostShield is a high-performance, advanced anti-cheat plugin for mMinecraft servers running Spigot 1.16.5. It provides comprehensive cheat detection with minimal performance impact, protecting your server from cheaters.

## Quick Start

### Requirements

| Requirement | Version |
|-------------|---------|
| **Server Software** | Spigot 1.16.5 (or Paper/Tuinity forks) |
| **Java** | Java 17 or higher |
| **Memory** | Minimum 512MB, Recommended 1GB+ |

### Installation

```bash
# Copy plugin to server's plugins directory
cp FrostShield-2.0.0.jar /your-server/plugins/

# Restart server to load the plugin
/restart

# Edit configuration file
nano plugins/FrostShield/config.yml

# Reload configuration without server restart
/fs reload
```

## Features

| Feature | Description |
|---------|-------------|
| **40+ Detection Checks** | Comprehensive coverage of movement, combat, packet and more |
| **Real-time Detection** | Instant cheat detection with configurable violation thresholds |
| **Auto-Ban System** | Automatic punishment system with customizable messages |
| **Low Performance Impact** | Optimized async processing for minimal server lag |
| **Discord Integration** | Real-time cheat notifications via Discord webhooks |
| **Configurable Checks** | Every check can be individually toggled and fine-tuned |
| **Player Data Tracking** | Advanced player profiling with violation history |
| **Command System** | Full administrative controls with tab completion |

## Quick Configuration

### Main Configuration (`config.yml`)

```yaml

# General plugin settings
settings:
  debug: false                    # Enable debug logging (highly verbose)
  auto-ban: true                  # Automatically ban at max violations
  max-violations: 50              # Violation threshold before punishment
  alert-enabled: true             # Show cheat alerts to staff
  log-to-file: true               # Write violations to log file

# Discord webhook integration (optional)
discord:
  webhook-url: "DISCORD_WEBHOOK_URL"  # Discord webhook endpoint
  enabled: false                       # Toggle Discord notifications

# Punishment message templates
punishments:
  ban-message: "&c&l[FrostShield] &fYou were banned for cheating!"
  kick-message: "&c&l[FrostShield] &fCheating detected: %check%"
  alert-message: "&c&l[FrostShield] &f%player% &7failed &c%check%"

# Detection check categories
checks:
  movement:
    enabled: true
    speed:
      max-speed: 0.55
      violations-before-punish: 20
```

## Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/fs` | `frostshield.admin` | Main command - shows plugin info |
| `/fs toggle` | `frostshield.admin` | Toggle alerts on/off |
| `/fs reload` | `frostshield.admin` | Reload configuration |
| `/fs ban <player>` | `frostshield.admin` | Manually ban a player |
| `/fs stats <player>` | `frostshield.admin` | View player violation statistics |

## Permission Nodes

| Permission | Description |
|------------|-------------|
| `frostshield.admin` | Full administrative access |
| `frostshield.alert` | Receive cheat alerts |
| `frostshield.bypass` | Bypass all checks (for staff) |

## Building from Source

### Prerequisites

- Java 17 JDK
- Maven 3.8+

### Build

```bash
# Clone the repository
git clone https://github.com/KaloudasDev/frostshield.git

# Navigate to project directory
cd frostshield

# Compile and package the plugin
mvn clean package
```

## API for Developers

```java
// Get violation level for a specific check
int violations = FrostShield.getInstance()
    .getPlayerDataManager()
    .getPlayerData(player)
    .getViolations("speed");

// Check if a player is currently banned
boolean isBanned = FrostShield.getInstance()
    .getPunishmentManager()
    .isPlayerBanned(player);
```

## Support

| Channel | Link |
|---------|------|
| **Discord** | [Contact me on Discord](https://discord.com/users/1069279857072160921) |
| **Email** | kaloudasdev@gmail.com |
| **GitHub** | [Report any possible bugs](https://github.com/KaloudasDev) |

## Legal

This plugin is **proprietary software**. See [LICENSE.md](LICENSE.md) for complete terms.

- No copying, modification, or distribution without explicit written permission
- Commercial use is prohibited without licensing agreement
- Reverse engineering is strictly forbidden

**Maintained by [KaloudasDev](https://github.com/KaloudasDev)**