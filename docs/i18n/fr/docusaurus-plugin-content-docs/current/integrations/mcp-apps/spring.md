---
title: Apps MCP Spring Boot
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
L'intégration de Spring Boot publie des vues webforJ routées via un serveur Spring AI MCP. Utilisez-le lorsqu'une application Spring Boot doit exposer une vue interactive à un client MCP, l'application AI qui se connecte au serveur.

:::tip[Vous n'utilisez pas Spring Boot ?]

Pour une application webforJ basée sur des servlets, utilisez la [configuration standard de webforJ](./without-spring) pour assembler et enregistrer le serveur MCP vous-même.
:::

## Ajouter les dépendances {#add-the-dependencies}

Importez le bill of materials de webforJ afin que les modules webforJ utilisent une seule version. Ajoutez ensuite le starter Spring Boot, le module MCP Apps et le starter serveur webmvc Spring AI.

```xml
<properties>
  <maven.compiler.release>21</maven.compiler.release>
  <webforj.version>26.02</webforj.version>
  <spring-ai.version>2.0.0</spring-ai.version>
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-bom</artifactId>
      <version>${webforj.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
    <version>${spring-ai.version}</version>
  </dependency>
</dependencies>
```

## Configurer le point de terminaison MCP {#configure-the-mcp-endpoint}

Configurez Spring AI pour utiliser HTTP Streamable dans `src/main/resources/application.properties` :

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI sert le transport sur `/mcp` par défaut. webforJ laisse ce chemin et les chemins de découverte OAuth 2.0 à Spring, au lieu de les traiter comme des routes UI. L'origine locale permet aux URLs des ressources et composants générés de pointer vers l'application en cours d'exécution. Consultez [la configuration de l'application MCP](./configuration) lorsqu'un proxy ou un tunnel donne à l'application une origine publique différente.

## Ajouter une application routée {#add-a-routed-app}

Créez une vue routée normale et ajoutez `@McpApp`. La description aide l'AI à déterminer quand l'outil est utile. Le nom explicite maintient le nom de l'outil stable si la route change.

```java
package com.example.inventory;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Affiche l'inventaire actuel d'un entrepôt.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventaire"));
  }
}
```

La vue fonctionne toujours à `/inventory` dans un navigateur. La configuration automatique de Spring publie un outil MCP `inventory` et une ressource UI qui ouvre cette route, et installe les filtres de servlet et les paramètres de session nécessaires pour intégrer webforJ.

## Démarrer le serveur {#start-the-server}

Démarrez le serveur en utilisant le flux de travail normal de l'application. Avec le port par défaut, le point de terminaison MCP est :

```text
http://localhost:8080/mcp
```

[Testez la vue minimale publiée](./testing) avec un client local ou distant. Consultez [l'ouverture d'input](./opening-apps) pour des arguments structurés et [la configuration de l'application MCP](./configuration) pour les origines publiques et les ressources externes.
