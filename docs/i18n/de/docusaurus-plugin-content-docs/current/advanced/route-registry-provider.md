---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: 03f86cbc79737ca141cc9d2e1ad2e28f
---
<!-- vale Google.Headings = NO -->
# Anbieter für Routenregistrierung <DocChip chip='since' label='25.11' />
<!-- vale Google.Headings = YES -->

Der `RouteRegistryProvider` ist eine Service Provider Interface (SPI), die es Integrationsframeworks ermöglicht, benutzerdefinierte Mechanismen zur Routenentdeckung bereitzustellen. Dies ermögliche es Frameworks, ihre eigenen Klassenpfad-Scans und Dependency Injection-Systeme mit der Routing-Infrastruktur von webforJ zu integrieren.

## Übersicht {#overview}

webforJ entdeckt Routen, indem Pakete nach mit `@Route` annotierten Komponenten durchsucht werden. Das `RouteRegistryProvider` SPI ermöglicht es Frameworks, dieses Standardverhalten mit ihrem eigenen Entdeckungsmechanismus zu überschreiben.

Verwenden Sie dieses SPI, wenn:

- Sie mit Dependency Injection-Frameworks wie Spring oder Contexts and Dependency Injection (CDI) integrieren
- Spezialisierte Umgebungen unterstützen (OSGi, benutzerdefinierte Classloader, GraalVM)
- Framework-Adapter erstellen, die den Lebenszyklus von Routenkomponenten verwalten müssen
- Vorhandene Klassenpfadscans wiederverwenden, um die Startzeit zu optimieren

## Funktionsweise {#how-it-works}

Wenn `RouteRegistry.ofPackage()` aufgerufen wird, überprüft webforJ registrierte Anbieter über Java's `ServiceLoader`. Wenn ein Anbieter gefunden wird, wird die Routenentdeckung an diesen Anbieter delegiert. Andernfalls wird der Standard-Scan-Mechanismus verwendet.

## Ihren Anbieter erstellen {#building-your-provider}

Um einen benutzerdefinierten Anbieter für die Routenentdeckung zu erstellen, implementieren Sie das SPI-Interface und registrieren Sie es über den ServiceLoader-Mechanismus von Java.

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
