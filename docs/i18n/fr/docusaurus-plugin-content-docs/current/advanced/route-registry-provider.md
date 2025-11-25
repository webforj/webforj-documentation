---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# Fournisseur de registre de routes <DocChip chip='since' label='25.11' />

Le `RouteRegistryProvider` est une Interface de Fournisseur de Services (SPI) qui permet aux frameworks d'intégration de fournir des mécanismes de découverte de routes personnalisés. Cela permet aux frameworks d'intégrer leur propre système de recherche sur le classpath et de gestion des dépendances avec l'infrastructure de routage de webforJ.

## Vue d'ensemble {#overview}

webforJ découvre des routes en scannant les packages pour les composants annotés avec `@Route`. Le SPI `RouteRegistryProvider` permet aux frameworks de remplacer ce comportement par défaut par leur propre mécanisme de découverte.

Utilisez ce SPI lorsque :

- Vous intégrez des frameworks de gestion des dépendances (Spring, CDI, ...)
- Vous supportez des environnements spécialisés (OSGi, classloaders personnalisés, GraalVM)
- Vous construisez des adaptateurs de framework qui doivent gérer le cycle de vie des composants de route
- Vous réutilisez des scans de classpath existants pour optimiser le temps de démarrage

## Comment cela fonctionne {#how-it-works}

Lorsque `RouteRegistry.ofPackage()` est appelé, webforJ vérifie les fournisseurs enregistrés via `ServiceLoader` de Java. Si un fournisseur est trouvé, la découverte de routes est déléguée à ce fournisseur. Sinon, le mécanisme de scan par défaut est utilisé.

## Créer votre fournisseur {#building-your-provider}

Pour créer un fournisseur de découverte de routes personnalisé, implémentez l'interface SPI et enregistrez-le via le mécanisme ServiceLoader de Java.

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

Ajoutez le nom de votre classe entièrement qualifié à `META-INF/services/com.webforj.router.RouteRegistryProvider` :

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
