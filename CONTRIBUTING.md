## Contributing

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

## Reporting Issues

### Allowed Reports

| Type | Channel |
|------|---------|
| Bug reports | GitHub Issues |
| Security vulnerabilities | Email / Discord (see SECURITY.md) |
| Feature requests | GitHub Discussions |
| Documentation errors | GitHub Issues |

### Issue Report Template

When creating an issue, include:

```
**Description:**
[Clear description of the issue]

**Steps to Reproduce:**
1. Go to '...'
2. Click on '...'
3. See error

**Expected Behavior:**
[What should happen]

**Actual Behavior:**
[What actually happens]

**Environment:**
- Browser: [e.g. Chrome 120]
- OS: [e.g. Windows 11]
- Device: [e.g. Desktop]

**Screenshots:**
[If applicable]
```

## Pull Requests

**External pull requests are not accepted.**

Internal pull requests (authorized collaborators only) must follow these requirements:

| Requirement | Status |
|-------------|--------|
| Code passes linting | Required |
| No breaking changes | Required |
| Documentation updated | Required |
| Tested locally | Required |
| Reviewed by owner | Required |

## Development Standards

### Code Style

| Aspect | Standard |
|--------|----------|
| Language | TypeScript |
| Linting | ESLint (project configuration) |
| Formatting | Prettier (if configured) |
| Comments | JSDoc for public functions |

### Commit Messages

Format:

```
<type>: <subject>

<body (optional)>

<footer (optional)>
```

Types:

| Type | Use |
|------|-----|
| feat | New feature |
| fix | Bug fix |
| docs | Documentation only |
| style | Code style (formatting, semicolons, etc.) |
| refactor | Code change that neither fixes nor adds feature |
| perf | Performance improvement |
| test | Adding or updating tests |
| chore | Maintenance tasks |

Example:

```
feat(auth): add Discord OAuth login button

- Implement Sign In with Discord button
- Add session management
- Update user profile display

Closes #42
```

## Feature Requests

Feature requests are welcome but not guaranteed to be implemented. The owner reserves the right to accept, reject, or postpone any feature request.

## Code of Conduct

All contributors must adhere to the [Code of Conduct](./CODE_OF_CONDUCT.md).

## Questions

For questions about contributing, contact:

**KaloudasDev**  
Email: `kaloudasdev@gmail.com`  
Discord: `@kaloudasdev`

*This document applies to all contributors.*