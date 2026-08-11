# webforJ Integration Tests

This directory contains Playwright-based integration tests for the webforJ documentation samples.

## Running Tests

### Local Development (on your machine)

Run all tests:
```bash
mvn verify
```

Run a specific test:
```bash
mvn verify -Dit.test=AppNavIT
```

### Docker Container (isolated environment)

Build the Docker image first:
```bash
mvn exec:exec@docker-e2e-build
```

Run all tests in Docker:
```bash
mvn exec:exec@docker-e2e
```

Run a specific test in Docker:
```bash
mvn exec:exec@docker-e2e-single -Dtest=AppNavIT
```

## Configuration Options

Tests can be configured using the `-Dwebforj.e2e` parameter with comma-separated key=value pairs:

```bash
# Run with specific browsers (use : to separate multiple browsers)
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="browsers=firefox:chromium"

# Run in headed mode (see browser window - only works outside Docker)
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="headless=false"

# Combine multiple options
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="browsers=firefox,headless=false,slow.mo=500"
```

### Available Options

| Option | Description | Default | Example |
|--------|-------------|---------|---------|
| `browsers` | Browsers to test (use : for multiple) | `chromium,firefox,webkit` | `browsers=firefox:chromium` |
| `headless` | Run without browser UI | `true` | `headless=false` |
| `slow.mo` | Slow down operations (ms) | `0` | `slow.mo=500` |
| `default.timeout` | Default timeout (ms) | `30000` | `default.timeout=60000` |
| `port` | Server port | `8998` | `port=8080` |

### Browser Support

- **chromium** - Fast, good for development
- **firefox** - Good for testing Firefox-specific features
- **webkit** - Safari's engine, good for macOS/iOS testing

## Test Structure

### @BrowserTest Annotation

All tests use the custom `@BrowserTest` annotation which automatically runs the test on all configured browsers:

```java
@BrowserTest
public void testFeature() {
    // This test will run 3 times: once for each browser
}
```

### Base Test Class

All tests extend `BaseTest` which provides:
- Browser lifecycle management
- Page navigation helpers
- Configuration access

## Docker Notes

- Tests run headless in Docker (no UI visible)
- Maven dependencies are cached in `webforj-e2e-maven-repo` volume
- First run downloads dependencies, subsequent runs are faster
- The container mounts your project directory, so changes are reflected immediately

To clean the Maven cache:
```bash
docker volume rm webforj-e2e-maven-repo
```

## Troubleshooting

### Tests fail with "Cannot find browser"
Make sure Playwright browsers are installed:
```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

### Tests timeout
Increase the timeout:
```bash
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="default.timeout=60000"
```

### Need to debug visually
Run outside Docker with headed mode:
```bash
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="headless=false,slow.mo=1000"
```

### Port conflicts
Change the port:
```bash
mvn verify -Dit.test=AppNavIT -Dwebforj.e2e="port=9999"
```