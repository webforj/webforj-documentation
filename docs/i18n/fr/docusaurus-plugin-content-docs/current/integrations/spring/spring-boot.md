---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot est un choix populaire pour construire des applications Java, offrant une injection de dépendances, une configuration automatique et un modèle de serveur intégré. Lors de l'utilisation de Spring Boot avec webforJ, vous pouvez injecter des services, des référentiels et d'autres beans gérés par Spring directement dans vos composants d'interface utilisateur via l'injection par constructeur.

Lorsque vous utilisez Spring Boot avec webforJ, votre application s'exécute en tant que JAR exécutable avec un serveur Tomcat intégré au lieu de déployer un fichier WAR sur un serveur d'application externe. Ce modèle d'emballage simplifie le déploiement et s'aligne avec les pratiques de déploiement cloud-native. Le modèle de composants de webforJ et le routage fonctionnent avec le contexte applicatif de Spring pour la gestion des dépendances et de la configuration.

## Créer une application Spring Boot {#create-a-spring-boot-app}

Vous avez deux options pour créer une nouvelle application webforJ avec Spring Boot : utiliser l'outil graphique startforJ ou la ligne de commande Maven.

### Option 1 : Utiliser startforJ {#option-1-using-startforj}

La façon la plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage comprend toutes les dépendances requises, les fichiers de configuration et une mise en page préfabriquée, vous permettant de commencer à construire immédiatement.

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de projet de base (Nom de l'application, ID de groupe, ID d'artifact)
- Version de webforJ et version de Java
- Couleur et icône de thème
- Archétype
- **Exécute avec** - Sélectionnez **Spring Boot** pour créer un projet Spring Boot

Avec ces informations, startforJ créera un projet de base à partir de l'archétype choisi configuré pour Spring Boot.
Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

### Option 2 : Utiliser la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez un projet Spring Boot webforJ directement en utilisant les archétypes officiels de webforJ :

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

Le paramètre `flavor` indique à l'archétype de générer un projet Spring Boot au lieu d'un projet webforJ standard.

Cela crée un projet Spring Boot complet avec :
- Configuration POM parent Spring Boot
- Dépendance de démarrage Spring Boot de webforJ
- Classe principale de l'application avec `@SpringBootApplication` et `@Routify`
- Vues d'exemple
- Fichiers de configuration pour Spring et webforJ

## Exécuter l'application Spring Boot {#run-the-spring-boot-app}

Un projet d'archétype fixe son objectif Maven par défaut, donc `mvn` sans arguments compile l'application, démarre le [frontend watch](/docs/configuration/deploy-reload/frontend-watch), et exécute l'application :

```bash
mvn
```

L'application démarre avec un serveur Tomcat intégré sur le port 8080 par défaut. Vos vues et routes webforJ existantes fonctionnent exactement comme auparavant, mais vous pouvez désormais injecter des beans Spring et utiliser les fonctionnalités de Spring.

## Configuration {#configuration}

Utilisez le fichier `application.properties` dans `src/main/resources` pour configurer votre application.
 Consultez [Configuration des propriétés](/docs/configuration/properties) pour des informations sur les propriétés de configuration de webforJ.

Les paramètres `application.properties` suivants de webforJ sont spécifiques à Spring :

| Propriété | Type | Description | Par défaut |
|-----------|------|-------------|------------|
| **`webforj.servlet-mapping`** | String | Modèle de mappage URL pour le servlet webforJ. | `/*` |
| **`webforj.exclude-urls`** | Liste | Modèles d'URL qui ne doivent pas être gérés par webforJ lorsqu'ils sont mappés à la racine. Lorsque webforJ est mappé au contexte racine (`/*`), ces modèles d'URL seront exclus de la gestion de webforJ et peuvent être gérés par les contrôleurs Spring MVC à la place. Cela permet aux points de terminaison REST et à d'autres mappages Spring MVC de coexister avec les routes webforJ. | `[]` |

### Différences de configuration {#configuration-differences}

Lorsque vous passez à Spring Boot, plusieurs aspects de la configuration changent :

| Aspect | webforJ standard | webforJ Spring Boot |
|--------|-------------------|---------------------|
| **Emballage** | Fichier WAR | JAR exécutable |
| **Serveur** | Externe (Jetty, Tomcat) | Tomcat intégré |
| **Commande d'exécution** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuration principale** | `webforj.conf` uniquement | `application.properties` + `webforj.conf` |
| **Profils** | `webforj-dev.conf`, `webforj-prod.conf` | Profils Spring avec `application-{profile}.properties` |
| **Configuration du port** | Dans la configuration du plugin | `server.port` dans les propriétés |
