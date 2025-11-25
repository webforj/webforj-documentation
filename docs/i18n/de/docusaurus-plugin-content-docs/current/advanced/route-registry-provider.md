---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# Route Registry Provider <DocChip chip='since' label='25.11' />

Der `RouteRegistryProvider` ist ein Service Provider Interface (SPI), das Integrationsframeworks ermöglicht, benutzerdefinierte Routenentdeckungsmechanismen bereitzustellen. Dies ermöglicht es Frameworks, ihre eigenen Classpath-Scans und Dependency-Injection-Systeme mit der Routing-Infrastruktur von webforJ zu integrieren.

## Überblick {#overview}

webforJ entdeckt Routen, indem es Pakete auf `@Route` annotierte Komponenten durchsucht. Das `RouteRegistryProvider` SPI ermöglicht es Frameworks, dieses Standardverhalten mit ihrem eigenen Entdeckungsmechanismus zu überschreiben.

Verwenden Sie dieses SPI, wenn:

- Integration mit Dependency-Injection-Frameworks (Spring, CDI, ...)
- Unterstützung spezialisierter Umgebungen (OSGi, benutzerdefinierte Classloader, GraalVM)
- Erstellung von Framework-Adaptern, die den Lebenszyklus von Routenkomponenten verwalten müssen
- Wiederverwendung vorhandener Classpath-Scans zur Optimierung der Startzeit

## Funktionsweise {#how-it-works}

Wenn `RouteRegistry.ofPackage()` aufgerufen wird, überprüft webforJ, ob registrierte Anbieter über Java's `ServiceLoader` vorhanden sind. Wenn ein Anbieter gefunden wird, wird die Routenentdeckung an diesen Anbieter delegiert. Andernfalls wird der Standard-Scan-Mechanismus verwendet.

## Erstellen Ihres Anbieters {#building-your-provider}

Um einen benutzerdefinierten Routenentdeckungsanbieter zu erstellen, implementieren Sie das SPI-Interface und registrieren Sie es über den ServiceLoader-Mechanismus von Java.

### Implementieren Sie das SPI {#implement-the-spi}

Erstellen Sie eine Klasse, die `RouteRegistryProvider` implementiert:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Pakete scannen und @Route-Komponenten registrieren
  }
}
```

### Entdeckung aktivieren {#enable-discovery}

Fügen Sie den vollständig qualifizierten Klassennamen Ihres Anbieters zu `META-INF/services/com.webforj.router.RouteRegistryProvider` hinzu:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
