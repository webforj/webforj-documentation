---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools fournit des redémarrages automatiques de l'application lorsque le code change. webforJ DevTools ajoute un rafraîchissement automatique du navigateur - lorsque Spring redémarre votre application, le navigateur se rafraîchit automatiquement via le serveur LiveReload de webforJ.

Différents types de fichiers déclenchent des comportements de rechargement différents. Les modifications du code Java provoquent un redémarrage complet de Spring et un rafraîchissement du navigateur. Les modifications CSS et d'images se mettent à jour sans rechargement de page, préservant les données de formulaire et l'état de l'application.

## Comprendre webforJ DevTools {#understanding-webforj-devtools}

webforJ étend Spring DevTools avec une synchronisation du navigateur. Lorsque Spring détecte les changements de fichiers et redémarre, webforJ DevTools rafraîchit automatiquement votre navigateur.

### Comportement de rechargement {#reload-behavior}

Différents types de fichiers déclenchent différentes stratégies de rechargement :

- **Fichiers Java** - Rechargement complet de la page du navigateur après le redémarrage de Spring
- **Fichiers CSS** - Mises à jour de style sans rechargement de page  
- **Fichiers JavaScript** - Rechargement complet de la page du navigateur après le redémarrage de Spring
- **Images** - Rafraîchissement sur place sans rechargement de page

## Dépendances {#dependencies}

Ajoutez à la fois Spring DevTools et webforJ DevTools à votre projet :

```xml title="pom.xml"
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-devtools</artifactId>
    <version>${webforj.version}</version>
    <optional>true</optional>
</dependency>
```

## Configuration {#configuration}

Activez webforJ DevTools dans les propriétés de votre application :

```Ini title="application.properties"
# Activer le rechargement automatique du navigateur webforJ
webforj.devtools.livereload.enabled=true

# Activer l'arrêt immédiat pour des redémarrages plus rapides
server.shutdown=immediate
```

### Configuration avancée {#advanced-configuration}

Configurez la connexion WebSocket et le comportement de rechargement :

```Ini title="application.properties"
# Port du serveur WebSocket (par défaut : 35730)
webforj.devtools.livereload.websocket-port=35730

# Chemin de point de terminaison WebSocket (par défaut : /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Intervalle de battement en millisecondes (par défaut : 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Activer le rechargement à chaud pour les ressources statiques (par défaut : true)
webforj.devtools.livereload.static-resources-enabled=true
```
