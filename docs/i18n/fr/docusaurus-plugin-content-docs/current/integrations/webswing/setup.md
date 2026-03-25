---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: d948bababbedcfe831d4af62f8b6b088
---
Intégrer Webswing avec webforJ implique deux composants : le serveur Webswing qui héberge votre application Swing et le composant `WebswingConnector` dans votre application webforJ qui l'embed.

## Prérequis {#prerequisites}

Avant de commencer, assurez-vous d'avoir les prérequis suivants :

- **Application de bureau Java** : une application Swing, JavaFX ou SWT empaquetée en tant que fichier JAR
- **Serveur Webswing** : téléchargez depuis [webswing.org](https://webswing.org)
- **webforJ version `25.10` ou ultérieure** : requise pour le support de `WebswingConnector`

## Aperçu de l'architecture {#architecture-overview}

L'architecture d'intégration consiste en :

1. **Serveur Webswing** : exécute votre application Swing, capture le rendu de l'interface utilisateur et gère les entrées utilisateur
2. **Application webforJ** : héberge votre application web avec le `WebswingConnector` intégré
3. **Client de navigateur** : affiche à la fois l'interface utilisateur webforJ et l'application Swing intégrée

:::important Configuration des ports
Webswing et webforJ doivent s'exécuter sur des ports différents pour éviter les conflits. En général, webforJ et Webswing s'exécutent tous deux sur le port `8080`. Vous devez changer soit le port de Webswing, soit le port de webforJ.
:::

## Configuration du serveur Webswing {#webswing-server-setup}

### Installation et démarrage {#installation-and-startup}

1. **Téléchargez Webswing** depuis le [site officiel](https://www.webswing.org/en/downloads)
2. **Extrayez l'archive** à l'emplacement de votre choix (par exemple, `/opt/webswing` ou `C:\webswing`)
3. **Démarrez le serveur** en utilisant les scripts spécifiques à votre plateforme :

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

Une fois le serveur en marche, accédez à la console d'administration à `http://localhost:8080/admin` pour ajouter et configurer votre application Swing.

Dans la console d'administration, configurez :

- **Nom de l'application** : devient partie du chemin d'URL (par exemple, `myapp` → `http://localhost:8080/myapp/`)
- **Classe principale** : le point d'entrée de votre application Swing
- **Classpath** : le chemin vers votre JAR d'application et ses dépendances
- **Arguments JVM** : paramètres de mémoire, propriétés système et autres options JVM
- **Répertoire de travail** : répertoire de travail pour l'application

Après la configuration, votre application Swing sera accessible à `http://localhost:8080/[app-name]/`

### Configuration CORS {#cors-configuration}

Lors de l'intégration de Webswing dans une application webforJ exécutée sur un port ou un domaine différent, vous devez configurer le partage des ressources entre origines (CORS) dans Webswing. Cela permet au navigateur de charger le contenu de Webswing depuis votre page webforJ.

Dans la console d'administration de Webswing, accédez à la configuration de votre application et définissez :

- **Origines autorisées** : Ajoutez l'origine de votre application webforJ (par exemple, `http://localhost:8090` ou `*` pour le développement)

Ce paramètre correspond à l'option `allowedCorsOrigins` dans la configuration de l'application de Webswing.

## Intégration avec webforJ {#webforj-integration}

Une fois votre serveur Webswing en cours d'exécution avec votre application Swing configurée et CORS activé, vous pouvez l'intégrer dans votre application webforJ.

### Ajouter la dépendance {#add-dependency}

L'intégration de Webswing dépend du module d'intégration Webswing de webforJ, qui fournit le composant `WebswingConnector` et les classes associées. Ajoutez ce qui suit à votre fichier `pom.xml` :

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Mise en œuvre de base {#basic-implementation}

Créez une vue qui intègre votre application Swing en utilisant le `WebswingConnector` :

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
    // Initialisez le connecteur avec l'URL de votre application Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Définissez les dimensions d'affichage
    connector.setSize("100%", "600px");

    // Ajoutez au conteneur de la vue
    self.add(connector);
  }
}
```

Le connecteur établit automatiquement une connexion au serveur Webswing lorsqu'il est ajouté au DOM. L'interface utilisateur de l'application Swing est alors rendue au sein du composant connecteur.

## Options de configuration {#configuration-options}

La classe `WebswingOptions` vous permet de personnaliser le comportement du connecteur. Par défaut, le connecteur démarre automatiquement lors de sa création et utilise des paramètres de connexion standard. Vous pouvez modifier ce comportement en créant une instance de `WebswingOptions` et en l'appliquant au connecteur.

Par exemple, pour masquer le bouton de déconnexion dans un environnement de production où vous gérez l'authentification via votre application webforJ :

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
  .setDisableLogout(true);  // Masquer le bouton de déconnexion

connector.setOptions(options);
```

Ou si vous devez contrôler manuellement le moment où la connexion démarre :

```java
// Créer le connecteur sans démarrage automatique
WebswingConnector connector = new WebswingConnector(url, false);

// Configurer et démarrer quand vous êtes prêt
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Les options couvrent la gestion de la connexion, l'authentification, le débogage et la surveillance.
