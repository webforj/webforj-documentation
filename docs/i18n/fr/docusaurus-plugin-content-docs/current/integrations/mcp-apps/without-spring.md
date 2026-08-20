---
title: Standard webforJ setup
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
La configuration standard crée le serveur MCP, enregistre son servlet et installe le support webforJ lui-même.

:::tip[Quand utiliser cette configuration]

Utilisez la configuration de servlet standard lorsque l'application n'utilise pas Spring Boot. Pour les applications Spring Boot, utilisez la [configuration Spring Boot](./spring), qui publie automatiquement des vues routées via Spring AI.
:::

:::warning[Les services BBj ne sont pas supportés]

Les applications MCP ont besoin de contrôler le contexte servlet de l'application pour installer le filtre cross-origin, les paramètres de cookie, la gestion de la découverte OAuth 2.0 et les autres support d'intégration nécessaires à l'hôte. Une application déployée via les services BBj ne peut pas initier cette configuration de servlet. Déployez l'application dans un conteneur de servlet que l'application contrôle à la place.
:::

## Ajoutez les dépendances du serveur MCP {#add-the-dependencies}

Ajoutez le module MCP Apps à côté de la dépendance webforJ existante. Il fournit la contribution webforJ et le SDK MCP utilisé pour assembler le serveur.

L'initialisateur implémente également `ServletContainerInitializer` et utilise d'autres types de Jakarta Servlet. Ajoutez l'API Servlet avec un scope `provided` afin que ces types soient disponibles au moment de la compilation sans emballer une deuxième implémentation de servlet. Le conteneur servlet, tel que Jetty, les fournit au moment de l'exécution.

```xml
<properties>
  <webforj.version>26.02</webforj.version>
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
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.1.0</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

## Ajoutez l'initialiseur {#add-the-initializer}

L'initialiseur scanne le package contenant les vues routées, contribue à leurs outils et ressources UI à un serveur MCP, et monte Streamable HTTP à `/mcp`.

```java
package com.example.inventory;

import com.webforj.mcp.McpAppContribution;
import com.webforj.mcp.McpAppOptions;
import com.webforj.mcp.McpAppServletPath;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import java.util.Set;

public class InventoryMcpServerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext context) {
    McpAppOptions options = McpAppOptions.load();
    McpAppContribution contribution = McpAppContribution.ofPackages(
        new String[] {"com.example.inventory"}, McpAppServletPath.of(context));
    contribution.getOrigin().configure(options.getOrigin());

    HttpServletStreamableServerTransportProvider transport =
        HttpServletStreamableServerTransportProvider.builder().mcpEndpoint("/mcp")
            .contextExtractor(request -> {
              contribution.getOrigin().observe(request);
              return McpTransportContext.EMPTY;
            }).build();

    McpSyncServer server = McpServer.sync(transport)
        .serverInfo("inventory", "1.0.0")
        .capabilities(ServerCapabilities.builder().tools(true).resources(false, true).build())
        .tools(contribution.getToolSpecifications())
        .resources(contribution.getResourceSpecifications())
        .build();

    ServletRegistration.Dynamic registration = context.addServlet("mcpServlet", transport);
    registration.setAsyncSupported(true);
    registration.addMapping("/mcp/*");

    context.addListener(new ServletContextListener() {
      @Override
      public void contextDestroyed(ServletContextEvent event) {
        server.close();
      }
    });

    contribution.install(context, options);
  }
}
```

`McpAppContribution.ofPackages` crée les deux collections enregistrées sur le serveur. `getToolSpecifications()` contient les outils générés à partir des vues routées `@McpApp`. `getResourceSpecifications()` contient les ressources générées `ui://webforj/...` que les clients MCP lisent pour rendre ces vues. En enregistrant les outils sans leurs ressources UI, on expose des appels que le client ne peut pas afficher.

L'extracteur de contexte de requête permet à webforJ d'observer l'origine publique lorsque `webforj.origin` n'est pas défini. L'appel final `install` ajoute la politique de ressources d'application, la gestion des origines croisées, les paramètres de cookie de session, la gestion de la découverte OAuth 2.0, et le support des favicons. Un serveur qui publie les outils mais qui omet cet appel peut exposer une ressource que le client ne peut pas exécuter correctement.

## Enregistrez l'initialiseur {#register-the-initializer}

Enregistrez l'initialiseur avec le chargeur de services Java dans `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

Le conteneur servlet charge cette classe lors du démarrage de l'application. Gardez le nom de classe complètement qualifié dans le fichier de service synchronisé avec le package de l'initialiseur.

## Configurez le déploiement {#configure-the-deployment}

Les déploiements standard lisent les paramètres de l'application MCP à partir de `webforj.conf`. Par exemple :

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

Le serveur minimal peut désormais être vérifié avec n'importe lequel des clients décrits dans [Test d'une application MCP](./testing). La [configuration de l'application MCP](./configuration) explique les origines publiques et clients lorsque le déploiement nécessite plus que les paramètres par défaut locaux.
