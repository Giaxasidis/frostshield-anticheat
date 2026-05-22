# Detection Modules

## Overview

FrostShield includes 40+ detection checks across 5 categories:

| Category | Checks |
|----------|--------|
| Combat | 14 checks |
| Movement | 18 checks |
| Packet | 7 checks |
| World | 9 checks |
| Paranormal | 8 checks |

## Combat Checks (14)

| Check | Description |
|-------|-------------|
| **KillAura** | Detects automated attack bots with angle and packet analysis |
| **Reach** | Monitors attack distance beyond vanilla limits |
| **HitBox** | Detects extended hitbox modifications |
| **AutoClicker** | Identifies unnatural click patterns and rates |
| **AimAssist** | Detects unnatural aim smoothing and precision |
| **AntiKnockback** | Monitors for knockback manipulation |
| **Criticals** | Detects critical hit automation |
| **TriggerBot** | Identifies automated attack timing |
| **FastEat** | Monitors for accelerated eating/consumption |
| **AutoArmor** | Detects instant armor equipping |
| **AutoTotem** | Identifies automated totem placement |
| **NoFriendDamage** | Prevents friend damage exploits |
| **NoSwing** | Detects attack without animation |
| **PacketFly** | Identifies packet-based flight manipulation |
| **MultiAura** | Detects multi-target attack automation |

## Movement Checks (18)

| Check | Description |
|-------|-------------|
| **Speed** | Detects movement speed modifications |
| **Fly** | Monitors for flight and gravity manipulation |
| **NoFall** | Detects fall damage negation |
| **Jesus** | Identifies water walking exploits |
| **Step** | Monitors for block stepping cheats |
| **Velocity** | Detects knockback modification |
| **Spider** | Identifies wall climbing cheats |
| **Glide** | Monitors for slow-fall manipulation |
| **HighJump** | Detects jump height modification |
| **WaterWalk** | Identifies water surface walking |
| **AirJump** | Detects mid-air jumping |
| **FastClimb** | Monitors for accelerated ladder climbing |
| **GroundSpoof** | Detects ground state spoofing |
| **JumpReset** | Identifies immediate jump reset after landing |
| **NoSlowdown** | Detects item slowdown bypass |
| **Prediction** | Advanced movement prediction analysis |
| **StairsSpeed** | Monitors for stair movement exploits |

## Packet Checks (7)

| Check | Description |
|-------|-------------|
| **Timer** | Detects game tick manipulation |
| **BadPackets** | Identifies malformed or impossible packets |
| **Blink** | Monitors for packet delay cheats |
| **InventoryMove** | Detects movement during inventory interaction |
| **PacketOrder** | Verifies correct packet sequencing |
| **InvalidMove** | Detects impossible movement packets |
| **FastLadder** | Monitors for accelerated ladder climbing |

## World Checks (9)

| Check | Description |
|-------|-------------|
| **Nuker** | Detects instant block breaking |
| **FastBreak** | Monitors for accelerated mining |
| **FastPlace** | Identifies instant block placement |
| **Scaffold** | Detects automated bridging cheats |
| **Phase** | Monitors for block phase exploitation |
| **GhostHand** | Identifies through-wall interaction |
| **ChestStealer** | Detects automated chest looting |
| **AutoTool** | Identifies automatic tool switching |
| **BedrockBreaker** | Monitors for bedrock breaking exploits |

## Paranormal Checks (8)

| Check | Description |
|-------|-------------|
| **ESP** | Detects wall-hack modifications |
| **Freecam** | Monitors for out-of-body camera exploits |
| **ChestAura** | Detects automated chest interaction |
| **BoatFly** | Identifies boat flight exploits |
| **ElytraFly** | Monitors for elytra speed manipulation |
| **AntiHunger** | Detects hunger manipulation |
| **AntiVoid** | Identifies void protection exploits |
| **AutoRespawn** | Monitors for instant respawn exploitation |

## Enabling/Disabling Checks

In `config.yml`:

```yaml
checks:
  combat:
    enabled: true
    killaura:
      enabled: true
```