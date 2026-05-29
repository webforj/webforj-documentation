---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: 03f86cbc79737ca141cc9d2e1ad2e28f
---
<!-- vale Google.Headings = NO -->
# Fournisseur d'enregistrement de routes <DocChip chip='since' label='25.11' />
<!-- vale Google.Headings = YES -->

Le `RouteRegistryProvider` est une interface de fournisseur de services (SPI) qui permet aux frameworks d'intégration de fournir des mécanismes de découverte de routes personnalisés. Cela permet aux frameworks d'intégrer leurs propres systèmes de numérisation de classpath et d'injection de dépendances avec l'infrastructure de routage de webforJ.

## Aperçu {#overview}

webforJ découvre des routes en scannant les packages pour des composants annotés avec `@Route`. Le SPI `RouteRegistryProvider` permet aux frameworks de remplacer ce comportement par défaut par leur propre mécanisme de découverte.

Utilisez ce SPI lorsque :

- Vous intégrez des frameworks d'injection de dépendances, tels que Spring, ou des Contextes et Injection de Dépendances (CDI)
- Vous supportez des environnements spécialisés (OSGi, classloaders personnalisés, GraalVM)
- Vous construisez des adaptateurs de framework qui doivent gérer le cycle de vie des composants de route
- Vous réutilisez les numérisations de classpath existantes pour optimiser le temps de démarrage

## Fonctionnement {#how-it-works}

Lorsque `RouteRegistry.ofPackage()` est appelé, webforJ vérifie les fournisseurs enregistrés via `ServiceLoader` de Java. Si un fournisseur est trouvé, la découverte des routes est déléguée à ce fournisseur. Sinon, le mécanisme de numérisation par défaut est utilisé.

## Construire votre fournisseur {#building-your-provider}

Pour créer un fournisseur de découverte de routes personnalisé, implémentez l'interface SPI et enregistrez-la via le mécanisme ServiceLoader de Java.

### Implémenter le SPI {#implement-the-spi}

Créez une classe qui implémente `RouteRegistryProvider` :

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Scanner les packages et enregistrer les composants @Route
  }
}
```

### Activer la découverte {#enable-discovery}

Ajoutez le nom de classe pleinement qualifié de votre fournisseur à `META-INF/services/com.webforj.router.RouteRegistryProvider` :

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
