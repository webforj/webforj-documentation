---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: cd2108b15ca8583794ddb366329c3638
---
L'intégration de Webswing avec webforJ implique deux composants : le serveur Webswing qui héberge votre application Swing, et le composant `WebswingConnector` dans votre application webforJ qui l'incorpore.

## Prérequis {#prerequisites}

Avant de commencer, assurez-vous d'avoir les prérequis suivants :

- **Application de bureau Java** : une application Swing, JavaFX ou SWT empaquetée sous forme de fichier JAR
- **Serveur Webswing** : à télécharger depuis [webswing.org](https://webswing.org)
- **Version webforJ `25.10` ou ultérieure** : requise pour la prise en charge de `WebswingConnector`

## Vue d'ensemble de l'architecture {#architecture-overview}

L'architecture d'intégration se compose de :

1. **Serveur Webswing** : exécute votre application Swing, capture le rendu de l'interface graphique et gère les entrées utilisateur
2. **Application webforJ** : héberge votre application web avec le `WebswingConnector` intégré
3. **Client navigateur** : affiche à la fois l'interface utilisateur de webforJ et l'application Swing intégrée

:::important Configuration du port
Webswing et webforJ doivent fonctionner sur des ports différents pour éviter les conflits. Les deux, webforJ et Webswing, fonctionnent généralement sur le port `8080`. Vous devez changer soit le port de Webswing, soit le port de webforJ.
:::

## Configuration du serveur Webswing {#webswing-server-setup}

### Installation et démarrage {#installation-and-startup}

1. **Téléchargez Webswing** depuis le [site officiel](https://www.webswing.org/en/downloads)
2. **Extrayez l'archive** à l'emplacement de votre choix (par exemple, `/opt/webswing` ou `C:\webswing`)
3. **Démarrez le serveur** à l'aide des scripts spécifiques à la plateforme :

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

### Configuration de l'application {#application-configuration}

Une fois le serveur en cours d'exécution, accédez à la console d'administration à `http://localhost:8080/admin` pour ajouter et configurer votre application Swing.

Dans la console d'administration, configurez :

- **Nom de l'application** : fait partie du chemin d'URL (par exemple, `myapp` → `http://localhost:8080/myapp/`)
- **Classe principale** : le point d'entrée de votre application Swing
- **Classpath** : chemin vers votre fichier JAR d'application et ses dépendances
- **Arguments JVM** : paramètres de mémoire, propriétés système et autres options JVM
- **Répertoire de base** : répertoire de travail pour l'application

Après configuration, votre application Swing sera accessible à `http://localhost:8080/[nom-de-l-app]/`

### Configuration CORS {#cors-configuration}

Lors de l'incorporation de Webswing dans une application webforJ fonctionnant sur un port ou un domaine différent, vous devez configurer le partage des ressources entre origines (CORS) dans Webswing. Cela permet au navigateur de charger le contenu de Webswing à partir de votre page webforJ.

Dans la console d'administration de Webswing, accédez à la configuration de votre application et définissez :

- **Origines autorisées** : Ajoutez l'origine de votre application webforJ (par exemple, `http://localhost:8090` ou `*` pour le développement)

Ce paramètre correspond à l'option `allowedCorsOrigins` dans la configuration de l'application Webswing.

## Intégration webforJ {#webforj-integration}

Une fois votre serveur Webswing en fonctionnement avec votre application Swing configurée et CORS activé, vous pouvez l'intégrer dans votre application webforJ.

### Ajouter une dépendance {#add-dependency}

L'intégration de Webswing dépend du module d'intégration Webswing de webforJ, qui fournit le composant `WebswingConnector` et des classes associées. Ajoutez ce qui suit à votre fichier `pom.xml` :

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Implémentation de base {#basic-implementation}

Créez une vue qui incorpore votre application Swing en utilisant le `WebswingConnector` :

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialiser le connecteur avec l'URL de votre application Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Définir les dimensions d'affichage
    connector.setSize("100%", "600px");

    // Ajouter au conteneur de la vue
    self.add(connector);
  }
}
```

Le connecteur établit automatiquement une connexion au serveur Webswing lorsqu'il est ajouté au DOM. L'interface utilisateur de l'application Swing est ensuite rendue au sein du composant connecteur.

## Options de configuration {#configuration-options}

La classe `WebswingOptions` vous permet de personnaliser le comportement du connecteur. Par défaut, le connecteur démarre automatiquement lorsqu'il est créé et utilise les paramètres de connexion standard. Vous pouvez modifier ce comportement en créant une instance de `WebswingOptions` et en l'appliquant au connecteur.

Par exemple, pour cacher le bouton de déconnexion dans un environnement de production où vous gérez l'authentification via votre application webforJ :

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Cacher le bouton de déconnexion

connector.setOptions(options);
```

Ou si vous avez besoin d'un contrôle manuel sur le moment où la connexion démarre :

```java
// Créer un connecteur sans auto-démarrage
WebswingConnector connector = new WebswingConnector(url, false);

// Configurer et démarrer quand prêt
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Les options couvrent la gestion des connexions, l'authentification, le débogage et la surveillance.
