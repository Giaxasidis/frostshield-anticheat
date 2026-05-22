## Contributing to FrostShield AntiCheat

Thank you for your interest in contributing. Please read this document carefully before submitting any contributions.

## Important Notice

This project is **proprietary software**. Contribution privileges are granted on a limited, case-by-case basis.

| Category | Status |
|----------|--------|
| Public contributions | Closed |
| External pull requests | Not accepted |
| Issue reporting | Allowed (see below) |
| Security reports | Allowed (see SECURITY.md) |
| Feature requests | Considered but not guaranteed |

## Who Can Contribute

Only the following individuals may contribute code or documentation:

- The project owner (KaloudasDev)
- Explicitly authorized collaborators with written permission

All contributions from authorized collaborators remain the property of the project owner.

### Code Style Requirements

| Aspect | Standard |
|--------|----------|
| Indentation | 4 spaces (no tabs) |
| Braces | Same line (K&R style) |
| Line length | Maximum 120 characters |
| Javadoc | Required for all public methods |
| Package naming | `com.kaloudasdev.frostshield.*` |
| Class naming | PascalCase |
| Method/Variable naming | camelCase |
| Constants | UPPER_SNAKE_CASE |

## Development Standards

### Code Example

```java
/**
 * Speed detection check.
 * Monitors player movement for speed hacks and acceleration cheats.
 */
public class Speed extends Check {
    
    private static final double MAX_HORIZONTAL_SPEED = 0.55;
    private static final int VIOLATION_THRESHOLD = 20;
    
    /**
     * Checks player movement for speed violations.
     *
     * @param player The player to check
     * @param moveEvent The movement event data
     */
    @Override
    public void check(Player player, MoveEvent moveEvent) {
        double horizontalSpeed = calculateHorizontalSpeed(moveEvent);
        
        if (horizontalSpeed > MAX_HORIZONTAL_SPEED) {
            handleViolation(player, horizontalSpeed);
        }
    }
    
    private double calculateHorizontalSpeed(MoveEvent event) {
        // Implementation here
        return 0.0;
    }
}
```

### Testing Requirements

| Test Type | Required |
|-----------|----------|
| Local server test | Yes |
| No console errors | Yes |
| No performance degradation | Yes |
| No false positives with legit players | Yes |
| Detection accuracy with known cheats | Yes |

## Documentation Standards

When updating documentation:

| Document | Update Required When |
|----------|---------------------|
| `README.md` | Features, requirements, or installation changes |
| `docs/CONFIGURATION.md` | Configuration options change |
| `docs/DETECTION_MODULES.md` | Checks are added, removed, or modified |
| `docs/COMMANDS.md` | Commands or permissions change |
| `docs/API.md` | API methods change |
| `SECURITY.md` | Security policies change |

## Code of Conduct

All contributors must adhere to the [Code of Conduct](./CODE_OF_CONDUCT.md).

## Questions

For questions about contributing, contact:

**KaloudasDev**  
Discord: `@kaloudasdev`  
Email: `kaloudasdev@gmail.com`

*This document applies to all contributors.*