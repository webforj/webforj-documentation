---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# Route Registry Provider <DocChip chip='since' label='25.11' />

De `RouteRegistryProvider` is een Service Provider Interface (SPI) die integratiekaders in staat stelt om aangepaste routeontdekkingsmechanismen te bieden. Dit stelt kaders in staat om hun eigen classpath-scanning en dependency injection systemen te integreren met de routinginfrastructuur van webforJ.

## Overzicht {#overview}

webforJ ontdekt routes door pakketten te scannen op `@Route` geannoteerde componenten. De `RouteRegistryProvider` SPI stelt kaders in staat om dit standaardgedrag te overschrijven met hun eigen ontdekkingsmechanisme.

Gebruik deze SPI wanneer:

- Integreren met dependency injection kaders (Spring, CDI, ...)
- Ondersteunen van gespecialiseerde omgevingen (OSGi, aangepaste classloaders, GraalVM)
- Bouwen van framework-adapters die de levenscyclus van routecomponenten moeten beheren
- Hergebruiken van bestaande classpath-scans om de opstarttijd te optimaliseren

## Hoe het werkt {#how-it-works}

Wanneer `RouteRegistry.ofPackage()` wordt aangeroepen, controleert webforJ op geregistreerde providers via Java's `ServiceLoader`. Als een provider wordt gevonden, wordt de routeontdekking gedelegeerd aan die provider. Anders wordt het standaard scanning-mechanisme gebruikt.

## Je provider bouwen {#building-your-provider}

Om een aangepast routeontdekkingsprovider te maken, implementeer je de SPI-interface en registreer je deze via Java's ServiceLoader-mechanisme.

### Implementeer de SPI {#implement-the-spi}

Maak een klasse die `RouteRegistryProvider` implementeert:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Scan pakketten en registreer @Route componenten
  }
}
```

### Ontdekking inschakelen {#enable-discovery}

Voeg de volledig gekwalificeerde classnaam van je provider toe aan `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
