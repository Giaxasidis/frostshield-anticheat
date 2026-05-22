# Command Reference

## Main Command: `/fs`

Alias: `/frostshield`

## Commands List

| Command | Permission | Description |
|---------|------------|-------------|
| `/fs` | `frostshield.admin` | Shows plugin information and status |
| `/fs toggle` | `frostshield.admin` | Toggles cheat alerts on/off |
| `/fs reload` | `frostshield.admin` | Reloads the configuration file |
| `/fs ban <player>` | `frostshield.admin` | Manually bans a player |
| `/fs stats <player>` | `frostshield.admin` | Shows violation statistics for a player |

## Command Details

### `/fs`

Displays plugin information:

```
FrostShield AntiCheat v2.0.0
Author: KaloudasDev
Status: Active
Checks Loaded: 40+
Alerts: Enabled
Auto-Ban: Enabled
```

### `/fs toggle`

Toggles cheat alerts on/off for the executing player.

**Output:**
```
Alerts enabled
```
or
```
Alerts disabled
```

### `/fs reload`

Reloads the configuration file without restarting the server.

**Output:**
```
Configuration reloaded successfully
```

### `/fs ban <player>`

Manually bans a player with FrostShield's ban system.

**Example:**
```
/fs ban Notch
```

**Output:**
```
Player Notch has been banned
```

### `/fs stats <player>`

Shows violation statistics for a specific player.

**Example:**
```
/fs stats Notch
```

**Output:**
```
Violations for Notch:
Speed: 15/20
KillAura: 8/10
Reach: 12/15
```

## Permission Nodes

| Permission | Description |
|------------|-------------|
| `frostshield.admin` | Access to all FrostShield commands |
| `frostshield.alert` | Receive cheat alerts in chat |
| `frostshield.bypass` | Bypass all cheat detection checks |

## Setting Permissions

### Using LuckPerms

```bash
/lp user Notch permission set frostshield.admin true
/lp group admin permission set frostshield.alert true
```

### Using PermissionsEx

```yaml
permissions:
  frostshield.admin:
    default: op
  frostshield.alert:
    default: op
  frostshield.bypass:
    default: op
```