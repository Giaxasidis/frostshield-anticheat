## Security Policy

## Supported Versions

Only the latest stable release receives security updates. Users must upgrade to the most recent version to remain protected.

| Version | Supported |
|---------|-----------|
| Latest stable (1.x) | Yes |
| Development builds | Testing Only |

## Reporting a Vulnerability

**Do not report security vulnerabilities through public GitHub issues, discussions, or pull requests.**

### Reporting Channels

| Method | Contact |
|--------|---------|
| Discord | `@kaloudasdev` (direct message) |
| Email | `kaloudasdev@gmail.com` |

### Report Requirements

Include the following information:

| Field | Description |
|-------|-------------|
| **Type** | Exploit type (bypass, crash, lag machine, etc.) |
| **Severity** | Critical / High / Medium / Low |
| **Description** | Clear explanation of the vulnerability |
| **Steps to Reproduce** | Detailed reproduction steps |
| **Proof of Concept** | Code snippets, screenshots, or video (no live exploitation) |
| **Minecraft Version** | Server version (e.g., 1.16.5) |
| **FrostShield Version** | Specific version number |
| **Suggested Fix** | Optional, but appreciated |

## Vulnerability Disclosure Process

| Step | Action | Timeline |
|------|--------|----------|
| 1 | Reporter submits vulnerability via private channel | Day 0 |
| 2 | Developer acknowledges receipt | Within 48 hours |
| 3 | Developer validates and triages the issue | Within 5 business days |
| 4 | Developer develops and tests a fix | Based on severity |
| 5 | Developer releases patched version | Upon completion |
| 6 | Developer notifies the reporter of resolution | Within 24 hours of release |
| 7 | Vulnerability is publicly disclosed (if applicable) | After patch release |

## Severity Classification

| Severity | Definition | Response Time | Fix Timeframe |
|----------|------------|---------------|----------------|
| **Critical** | Server crash, remote code execution, complete bypass | 24 hours | 48 hours |
| **High** | Major cheat bypass, significant performance impact | 48 hours | 7 days |
| **Medium** | Minor bypass, false positive issues | 5 business days | 14 days |
| **Low** | Configuration issues, minor improvements | 10 business days | 30 days |

## What We Consider a Security Vulnerability

| Category | Examples |
|----------|----------|
| **Cheat Bypass** | Any method that allows a cheat to bypass FrostShield detection |
| **Server Crash** | Input or packet that crashes the server |
| **Performance Degradation** | Input that causes severe lag or memory issues |
| **Privilege Escalation** | Unauthorized access to admin commands or permissions |
| **Data Leak** | Unauthorized access to player data or server information |

## What We Do NOT Consider a Security Vulnerability

| Category | Reason |
|----------|--------|
| **False positives** | Report as a normal issue, not security |
| **Configuration questions** | Use Discord for support |
| **Feature requests** | Use GitHub Discussions |
| **Performance optimization** | Report as a normal issue |
| **Compatibility issues** | Report as a normal issue |

## Safe Harbor

When reporting vulnerabilities in accordance with this policy:

- The reporter will not face legal action
- The reporter will not be penalized for good-faith testing
- The reporter will receive credit upon public disclosure (unless anonymity is requested)

## Prohibited Actions

The following actions are **not permitted** under this policy:

| Action | Status |
|--------|--------|
| Social engineering attacks | Prohibited |
| Denial of service attacks | Prohibited |
| Testing on production servers without permission | Prohibited |
| Accessing or modifying other servers' data | Prohibited |
| Public disclosure before fix is released | Prohibited |
| Automated vulnerability scanning without permission | Prohibited |

## Recognition

Security researchers who report valid, previously unknown vulnerabilities will be:

- Added to the security acknowledgments section in the documentation
- Credited in release notes
- Mentioned on Discord (upon request)

## Contact

For any security-related inquiries, please contact:

**KaloudasDev**  
Discord: `@kaloudasdev`  
Email: `kaloudasdev@gmail.com`

*This security policy is effective as of 2026 and applies to all versions of FrostShield AntiCheat.*