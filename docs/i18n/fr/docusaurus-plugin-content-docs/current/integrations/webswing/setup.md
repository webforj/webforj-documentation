---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
L'intégration de Webswing avec webforJ implique deux composants : le serveur Webswing qui héberge votre application Swing et le composant `WebswingConnector` dans votre application webforJ qui l'incorpore.

## Prérequis

Avant de commencer, assurez-vous d'avoir les prérequis suivants :

- **Application de bureau Java** : une application Swing, JavaFX ou SWT empaquetée en tant que fichier JAR
- **Serveur Webswing** : téléchargez-le depuis [webswing.org](https://webswing.org)
- **Version webforJ `25.10` ou ultérieure** : requise pour le support de `WebswingConnector`

## Vue d'ensemble de l'architecture

L'architecture d'intégration se compose de :

1. **Serveur Webswing** : exécute votre application Swing, capture le rendu de l'interface utilisateur et gère les entrées des utilisateurs
2. **Application webforJ** : héberge votre application web avec le `WebswingConnector` intégré
3. **Client de navigateur** : affiche à la fois l'interface utilisateur webforJ et l'application Swing intégrée

:::important Configuration du port
Webswing et webforJ doivent s'exécuter sur des ports différents pour éviter les conflits. En général, webforJ et Webswing fonctionnent sur le port `8080`. Vous devez changer soit le port Webswing, soit le port webforJ.
:::

## Configuration du serveur Webswing

### Installation et démarrage

1. **Téléchargez Webswing** depuis le [site officiel](https://www.webswing.org/en/downloads)
2. **Extrayez l'archive** à l'emplacement de votre choix (par exemple, `/opt/webswing` ou `C:\webswing`)
3. **Démarrez le serveur** à l'aide des scripts spécifiques à votre plateforme :

<Tabs>
      <TabItem value="Linux" label="Linux" default>
        ```bash
        webswing.sh
        ```
      </TabItem>
      <TabItem value="macOS" label="macOS">
        ```bash
        webswing.command
        ```
      </TabItem>
      <TabItem value="Windows" label="Windows">
        ```bash
        webswing.bat
        ```
      </TabItem>
</Tabs>

4. **Vérifiez que le serveur fonctionne** en accédant à `http://localhost:8080`

### Configuration de l'application

Une fois le serveur en marche, accédez à la console d'administration à `http://localhost:8080/admin` pour ajouter et configurer votre application Swing.

Dans la console d'administration, configurez :

- **Nom de l'application** : fait partie de l'URL (par exemple, `myapp` → `http://localhost:8080/myapp/`)
- **Classe principale** : le point d'entrée de votre application Swing
- **Classpath** : chemin vers votre fichier JAR et ses dépendances
- **Arguments JVM** : paramètres de mémoire, propriétés système et autres options JVM
- **Répertoire de travail** : répertoire de travail pour l'application

Après la configuration, votre application Swing sera accessible à `http://localhost:8080/[app-name]/`

## Intégration webforJ

Une fois votre serveur Webswing en marche avec votre application Swing configurée, vous pouvez l'intégrer dans votre application webforJ. Cela implique d'ajouter la dépendance, de configurer le partage des ressources entre origines (CORS) et de créer une vue avec le composant `WebswingConnector`.

### Ajouter la dépendance

Ajoutez le module d'intégration Webswing à votre projet webforJ. Cela fournit le composant `WebswingConnector` et les classes associées.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### Configuration CORS

La configuration de Cross-Origin Resource Sharing (CORS) est requise lorsque Webswing et webforJ fonctionnent sur des ports ou domaines différents. La politique de même origine du navigateur bloque les requêtes entre différentes origines sans les en-têtes CORS appropriés.

Créez un filtre de servlet pour ajouter des en-têtes CORS à votre application webforJ :

```java title="CorsFilter.java"
package com.example.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // pass
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("Access-Control-Allow-Origin", "*");
      httpResponse.setHeader("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Authorization, X-Requested-With");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // pass
  }
}
```

Enregistrez le filtre dans votre `web.xml` :

```xml
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>com.example.config.CorsFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

:::important Accès dans les environnements de production
Pour les environnements de production, remplacez le caractère générique (`*`) dans `Access-Control-Allow-Origin` par l'URL spécifique de votre serveur Webswing pour des raisons de sécurité.
:::

### Implémentation de base

Créez une vue qui intègre votre application Swing à l'aide du `WebswingConnector` :

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialiser le connecteur avec l'URL de votre application Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Définir les dimensions d'affichage
    connector.setSize("100%", "600px");

    // Ajouter au conteneur de la vue
    getBoundComponent().add(connector);
  }
}
```

Le connecteur établit automatiquement une connexion avec le serveur Webswing lorsqu'il est ajouté au DOM. L'interface utilisateur de l'application Swing est ensuite rendue dans le composant connecteur.

## Options de configuration

La classe `WebswingOptions` vous permet de personnaliser le comportement du connecteur. Par défaut, le connecteur démarre automatiquement lors de sa création et utilise les paramètres de connexion standard. Vous pouvez modifier ce comportement en créant une instance de `WebswingOptions` et en l'appliquant au connecteur.

Par exemple, pour masquer le bouton de déconnexion dans un environnement de production où vous gérez l'authentification via votre application webforJ :

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Masquer le bouton de déconnexion

connector.setOptions(options);
```

Ou si vous avez besoin d'un contrôle manuel sur le moment où la connexion démarre :

```java
// Créer le connecteur sans démarrage automatique
WebswingConnector connector = new WebswingConnector(url, false);

// Configurer et démarrer quand prêt
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Les options couvrent la gestion des connexions, l'authentification, le débogage et la surveillance.
