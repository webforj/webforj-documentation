---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot est un choix populaire pour créer des applications Java, car il fournit l’injection de dépendances, l’auto-configuration et un modèle de serveur intégré. Lorsque vous utilisez Spring Boot avec webforJ, vous pouvez injecter des services, des dépôts et d’autres beans gérés par Spring directement dans vos composants d’interface utilisateur à l’aide de l’injection par constructeur.

Lorsque vous utilisez Spring Boot avec webforJ, votre application s’exécute sous la forme d’un JAR exécutable avec un serveur Tomcat intégré, au lieu de déployer un fichier WAR sur un serveur d’applications externe. Ce modèle de packaging simplifie le déploiement et s’aligne sur les pratiques de déploiement cloud-native. Le modèle de composants et le routage de webforJ fonctionnent en parallèle du contexte d’application de Spring pour gérer les dépendances et la configuration.

## Créer une application Spring Boot {#create-a-spring-boot-app}

Vous avez deux possibilités pour créer une application webforJ avec Spring Boot : utiliser l’outil graphique startforJ ou la ligne de commande Maven.

<!-- vale off -->
### Option 1 : Utiliser startforJ {#option-1-using-startforj}
<!-- vale on -->

La façon la plus simple de créer une application webforJ est d’utiliser [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur l’archétype webforJ de votre choix. Ce projet de démarrage comprend toutes les dépendances et tous les fichiers de configuration nécessaires, ainsi qu’une mise en page prête à l’emploi, afin que vous puissiez commencer à développer immédiatement.

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (nom de l’application, ID de groupe, ID d’artefact)
- Version de webforJ et version de Java
- Couleur du thème et icône
- Archétype
- **S’exécute sur** - Sélectionnez **Spring Boot** pour créer un projet Spring Boot

À partir de ces informations, startforJ crée un projet de base à partir de l’archétype choisi et le configure pour Spring Boot.
Vous pouvez télécharger votre projet sous forme de fichier ZIP ou le publier directement sur GitHub.

### Option 2 : Utiliser la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez directement un projet webforJ Spring Boot à l’aide des archétypes officiels de webforJ :

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

Le paramètre `flavor` indique à l’archétype de générer un projet Spring Boot plutôt qu’un projet webforJ standard.

Cette commande crée un projet Spring Boot complet avec :
- La configuration du POM parent Spring Boot
- La dépendance du starter Spring Boot de webforJ
- La classe principale de l’application avec `@SpringBootApplication` et `@Routify`
- Des vues d’exemple
- Des fichiers de configuration pour Spring et webforJ

## Exécuter l’application Spring Boot {#run-the-spring-boot-app}

Un projet basé sur un archétype définit son objectif Maven par défaut. Ainsi, `mvn` sans argument compile l’application, démarre la [surveillance du frontend](/docs/configuration/deploy-reload/frontend-watch) et exécute l’application :

```bash
mvn
```

Par défaut, l’application démarre sur le port 8080 avec un serveur Tomcat intégré. Vos vues et routes webforJ existantes fonctionnent exactement comme avant, mais vous pouvez désormais injecter des beans Spring et utiliser les fonctionnalités de Spring.

## Configuration {#configuration}

Utilisez le fichier `application.properties` dans `src/main/resources` pour configurer votre application.
 Consultez la page [Configuration des propriétés](/docs/configuration/properties) pour en savoir plus sur les propriétés de configuration de webforJ.

Les paramètres `application.properties` de webforJ suivants sont spécifiques à Spring :

| Propriété | Type | Description | Valeur par défaut|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | Modèle de correspondance d’URL pour la servlet webforJ. | `/*` |
| **`webforj.exclude-urls`** | List | Modèles d’URL qui ne doivent pas être gérés par webforJ lorsque celui-ci est associé à la racine. Lorsque webforJ est associé au contexte racine (`/*`), ces modèles d’URL sont exclus de la gestion par webforJ et peuvent être gérés par les contrôleurs Spring MVC. Cela permet aux points de terminaison REST et aux autres mappages Spring MVC de coexister avec les routes webforJ. | `[]` |

### Différences de configuration {#configuration-differences}

Lorsque vous passez à Spring Boot, plusieurs aspects de la configuration changent :

| Aspect | webforJ standard | webforJ avec Spring Boot |
|--------|-----------------|-------------------|
| **Packaging** | Fichier WAR | JAR exécutable |
| **Serveur** | Externe (Jetty, Tomcat) | Tomcat intégré |
| **Commande d’exécution** | `mvn` | `mvn` |
| **Configuration principale** | `webforj.conf` uniquement | `application.properties` + `webforj.conf`  |
| **Profils** | `webforj-dev.conf`, `webforj-prod.conf` | Profils Spring avec `application-{profile}.properties` |
| **Configuration du port** | Dans la configuration du plug-in | `server.port` dans les propriétés |

:::info Exécuter avec `mvn`, et non avec `mvn spring-boot:run`
`mvn spring-boot:run` démarre uniquement l’application. Cette commande ne démarre pas la [surveillance du frontend](/docs/configuration/deploy-reload/frontend-watch) ; les sources dans `src/main/frontend`, si votre projet en contient, ne sont donc ni compilées ni reconstruites pendant l’exécution de l’application. La commande `mvn` seule exécute l’objectif par défaut du projet, qui démarre la surveillance, puis l’application.
:::
