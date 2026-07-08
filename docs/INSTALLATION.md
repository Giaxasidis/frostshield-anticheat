# Installation Guide

## Requirements

| Requirement | Version |
|-------------|---------|
| **Server Software** | Spigot 1.16.5 (or Paper/Tuinity forks) |
| **Java** | Java 17 or higher |
| **Memory** | Minimum 512MB, Recommended 1GB+ |

## Installation Steps

### Step 1: Download the Plugin

Download the latest `FrostShield-2.0.0.jar` from the [Releases](https://github.com/Giaxasidis/frostshield/releases) page.

### Step 2: Install on Your Server

```bash
# Copy the jar to your plugins folder
cp FrostShield-2.0.0.jar /your-server/plugins/
```

### Step 3: Restart Your Server

```bash
# Restart your server
/restart
```

Or use your server's restart command.

### Step 4: Verify Installation

Check the server console for:

```
[FrostShield] FrostShield AntiCheat Successfully Enabled!
[FrostShield] Checks Loaded: 40+
```

### Step 5: Configure the Plugin

```bash
# Edit the configuration file
nano plugins/FrostShield/config.yml
```

### Step 6: Reload Configuration

```bash
# In-game command
/fs reload
```

## Discord Webhook Setup (Optional)

1. Open Discord → Server Settings → Integrations → Webhooks
2. Click "New Webhook"
3. Name: `FrostShield AntiCheat`
4. Select the channel for alerts
5. Copy the Webhook URL
6. Add to `config.yml`:

```yaml
discord:
  webhook-url: "https://discord.com/api/webhooks/..."
  enabled: true
```

## Verification

Run these commands to verify everything is working:

```bash
# Check plugin status
/fs

# Check alerts are enabled
/fs toggle
```

## Troubleshooting

### Plugin not loading

- Verify Java 17 is installed: `java -version`
- Check server console for errors
- Ensure Spigot 1.16.5 or compatible fork

### Discord webhook not working

- Verify the webhook URL is correct
- Check network connectivity
- Ensure `discord.enabled: true`