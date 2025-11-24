---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
---

# Route Registry Provider <DocChip chip='since' label='25.11' />

The `RouteRegistryProvider` is a Service Provider Interface (SPI) that allows integration frameworks to provide custom route discovery mechanisms. This enables frameworks to integrate their own classpath scanning and dependency injection systems with webforJ's routing infrastructure.

## Overview {#overview}

webforJ discovers routes by scanning packages for `@Route` annotated components. The `RouteRegistryProvider` SPI allows frameworks to override this default behavior with their own discovery mechanism.

Use this SPI when:

- Integrating with dependency injection frameworks (Spring, CDI, ...)
- Supporting specialized environments (OSGi, custom classloaders, GraalVM)
- Building framework adapters that need to manage route component lifecycle
- Reusing existing classpath scans to optimize startup time

## How it works {#how-it-works}

When `RouteRegistry.ofPackage()` is called, webforJ checks for registered providers via Java's `ServiceLoader`. If a provider is found, route discovery is delegated to that provider. Otherwise, the default scanning mechanism is used.

## Building your provider {#building-your-provider}

To create a custom route discovery provider, implement the SPI interface and register it via Java's ServiceLoader mechanism.

### Implement the SPI {#implement-the-spi}

Create a class that implements `RouteRegistryProvider`:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Scan packages and register @Route components
  }
}
```

### Enable discovery {#enable-discovery}

Add your provider's fully qualified class name to `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```