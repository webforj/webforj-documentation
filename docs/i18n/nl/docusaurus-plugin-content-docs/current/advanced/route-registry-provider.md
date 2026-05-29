---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: 03f86cbc79737ca141cc9d2e1ad2e28f
---
<!-- vale Google.Headings = NO -->
# Route Registry Provider <DocChip chip='since' label='25.11' />
<!-- vale Google.Headings = YES -->

De `RouteRegistryProvider` is een Service Provider Interface (SPI) die integratiekaders de mogelijkheid biedt om aangepaste route-ontdekkingsmechanismen te bieden. Dit stelt kaders in staat om hun eigen classpath-scanning en afhankelijkheidsinjectiesystemen te integreren met de routeringsinfrastructuur van webforJ.

## Overzicht {#overview}

webforJ ontdekt routes door pakketten te scannen op `@Route` geannoteerde componenten. De `RouteRegistryProvider` SPI stelt kaders in staat om dit standaardgedrag te overschrijven met hun eigen ontdekkingsmechanisme.

Gebruik deze SPI wanneer:

- Integreren met afhankelijkheidsinjectiekaders, zoals Spring, of Contexten en Afhankelijkheidsinjectie (CDI)
- Ondersteunen van gespecialiseerde omgevingen (OSGi, aangepaste classloaders, GraalVM)
- Bouwen van raamwerkadapters die de levenscyclus van routecomponenten moeten beheren
- Hergebruik van bestaande classpath-scans om de opstarttijd te optimaliseren

## Hoe het werkt {#how-it-works}

Wanneer `RouteRegistry.ofPackage()` wordt aangeroepen, controleert webforJ op geregistreerde providers via Java's `ServiceLoader`. Als een provider wordt gevonden, wordt het ontdekken van routes gedelegeerd aan die provider. Anders wordt het standaard scanmechanisme gebruikt.

## Je provider bouwen {#building-your-provider}

Om een aangepast routeontdekkingsprovider te creëren, implementeer je de SPI-interface en registreer je deze via Java's ServiceLoader-mechanisme.

### Implementeer de SPI {#implement-the-spi}

Maak een klasse die `RouteRegistryProvider` implementeert:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Scan pakketten en registreer @Route-componenten
  }
}
```

### Ontdekking inschakelen {#enable-discovery}

Voeg de volledig gekwalificeerde naam van je providerklasse toe aan `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
