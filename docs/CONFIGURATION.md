# Configuration Reference

## File Location

`plugins/FrostShield/config.yml`

## Settings Section

```yaml
settings:
  debug: false
  auto-ban: true
  max-violations: 50
  alert-enabled: true
  log-to-file: true
```

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `debug` | boolean | false | Enables detailed debug logging |
| `auto-ban` | boolean | true | Automatically bans players at max violations |
| `max-violations` | integer | 50 | Maximum violations before punishment |
| `alert-enabled` | boolean | true | Shows alerts to staff with permission |
| `log-to-file` | boolean | true | Saves violations to log file |

## Discord Section

```yaml
discord:
  webhook-url: "YOUR_WEBHOOK_URL"
  enabled: false
```

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `webhook-url` | string | "" | Discord webhook URL for notifications |
| `enabled` | boolean | false | Enable/disable Discord integration |

## Punishments Section

```yaml
punishments:
  ban-message: "&c&l[FrostShield] &fYou were banned for cheating! (Check: %check%, VL: %vl%)"
  kick-message: "&c&l[FrostShield] &fCheating detected: %check% (VL: %vl%)"
  alert-message: "&c&l[FrostShield] &f%player% &7failed &c%check% &7(VL: %vl%/%max%)"
```

| Placeholder | Description |
|-------------|-------------|
| `%check%` | Name of the detected cheat |
| `%vl%` | Current violation level |
| `%max%` | Maximum violation threshold |
| `%player%` | Player name |

## Checks Configuration

### Movement Checks

```yaml
checks:
  movement:
    enabled: true
    speed:
      max-speed: 0.55
      violations-before-punish: 20
    fly:
      max-air-ticks: 10
      violations-before-punish: 15
    nofall:
      enabled: true
      violations-before-punish: 10
```

### Combat Checks

```yaml
checks:
  combat:
    enabled: true
    reach:
      max-distance: 4.2
      violations-before-punish: 15
    killaura:
      max-angles: 45
      violations-before-punish: 10
    autoclicker:
      max-cps: 20
      violations-before-punish: 12
```

### Packet Checks

```yaml
checks:
  packet:
    enabled: true
    timer:
      max-packet-rate: 22
      violations-before-punish: 10
    badpackets:
      enabled: true
      violations-before-punish: 8
```

## Configuration Tuning

### Server Type Recommendations

| Server Type | Max Violations | Notes |
|-------------|---------------|-------|
| **Competitive** | 5-10 | Stricter detection, faster bans |
| **Semi-Vanilla** | 10-20 | Balanced detection |
| **Anarchy/Survival** | 20-50 | More lenient, fewer false positives |

### Check Sensitivity

| Setting | Value | Use Case |
|---------|-------|----------|
| **Strict** | Lower thresholds | Competitive/PvP servers |
| **Normal** | Default values | General use (recommended) |
| **Lenient** | Higher thresholds | Casual/Survival servers |

## Reloading Configuration

After making changes, reload the configuration:

```bash
/fs reload
```