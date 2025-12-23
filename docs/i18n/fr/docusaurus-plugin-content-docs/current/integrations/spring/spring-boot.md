---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 620d9c2e8df79418d1e4902b970b42c8
---
Spring Boot est un choix populaire pour construire des applications Java, offrant l'injection de dépendances, l'auto-configuration et un modèle de serveur intégré. Lorsque vous utilisez Spring Boot avec webforJ, vous pouvez injecter des services, des dépôts et d'autres beans gérés par Spring directement dans vos composants UI par injection via le constructeur.

Lorsque vous utilisez Spring Boot avec webforJ, votre application s'exécute en tant que JAR exécutable avec un serveur Tomcat intégré au lieu de déployer un fichier WAR sur un serveur d'application externe. Ce modèle d'emballage simplifie le déploiement et s'aligne avec les pratiques de déploiement cloud-native. Le modèle de composants et le routage de webforJ fonctionnent en harmonie avec le contexte d'application de Spring pour gérer les dépendances et la configuration.

## Créer une application Spring Boot {#create-a-spring-boot-app}

Vous avez deux options pour créer une nouvelle application webforJ avec Spring Boot : utiliser l'outil graphique startforJ ou la ligne de commande Maven.

<!-- vale off -->
### Option 1 : Utilisation de startforJ {#option-1-using-startforj}
<!-- vale on -->

La manière la plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage inclut toutes les dépendances requises, les fichiers de configuration et une mise en page préfaite, vous permettant de commencer à construire immédiatement.

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (Nom de l'application, ID de groupe, ID d'artefact)  
- Version de webforJ et version de Java
- Couleur du thème et icône
- Archétype
- **Saveur** - Sélectionnez **webforJ Spring** pour créer un projet Spring Boot

En utilisant ces informations, startforJ créera un projet de base à partir de l'archétype choisi configuré pour Spring Boot.
Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

### Option 2 : Utilisation de la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez un projet webforJ Spring Boot directement en utilisant les archétypes officiels de webforJ :

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

Cela crée un projet complet Spring Boot avec :
- Configuration POM parent de Spring Boot
- Dépendance du démarrage Spring Boot de webforJ
- Classe principale de l'application avec `@SpringBootApplication` et `@Routify`
- Vues d'exemple
- Fichiers de configuration pour Spring et webforJ

## Ajouter Spring Boot à des projets existants {#add-spring-boot-to-existing-projects}

Si vous avez une application webforJ existante, vous pouvez ajouter Spring Boot en modifiant la configuration de votre projet. Ce processus implique la mise à jour de votre configuration Maven, l'ajout de dépendances Spring et la conversion de votre classe principale d'application.

:::info[Pour les projets existants uniquement]
Passez cette section si vous créez un nouveau projet de zéro. Ce guide suppose que **la version de webforJ est 25.11 ou supérieure**.
:::

### Étape 1 : Mettre à jour la configuration Maven {#step-1-update-maven-configuration}

Apportez les changements suivants à votre fichier POM :

1. Changez l'emballage de WAR à JAR :
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Définissez Spring Boot comme POM parent :
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Supprimez toute configuration spécifique à WAR telle que :
   - `maven-war-plugin`
   - Références au répertoire `webapp`
   - Configuration liée à `web.xml`

Si vous avez déjà un POM parent, vous devrez importer le Bill of Materials (BOM) de Spring Boot à la place :

```xml title="pom.xml"
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.5.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Étape 2 : Ajouter des dépendances Spring {#step-2-add-spring-dependencies}

Ajoutez le démarrage Spring Boot de webforJ à vos dépendances :

:::info[Simplification pour webforJ 25.11+]
À partir de **la version 25.11 de webforJ**, le `webforj-spring-boot-starter` inclut toutes les dépendances de base de webforJ de manière transitive. Vous n'avez plus besoin d'ajouter explicitement la dépendance `com.webforj:webforj`.

Pour les versions **avant 25.11**, vous devez inclure les deux dépendances séparément.
:::

**Pour webforJ 25.11 et ultérieur :**

```xml title="pom.xml"
<dependencies>
    <!-- Ajouter le démarrage Spring Boot (inclut webforJ de manière transitive) -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Ajouter devtools -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-spring-devtools</artifactId>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
    </dependency>
</dependencies>
```

**Pour les versions avant 25.11 :**

```xml title="pom.xml"
<dependencies>
    <!-- Ajouter explicitement la dépendance webforJ -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Ajouter le démarrage Spring Boot -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Ajouter devtools -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-spring-devtools</artifactId>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
    </dependency>
</dependencies>
```

:::tip[DevTools pour webforJ pour un rafraîchissement automatique du navigateur]
La dépendance `webforj-spring-devtools` étend Spring DevTools avec un rafraîchissement automatique du navigateur. Lorsque vous enregistrez des modifications dans votre IDE, le navigateur se recharge automatiquement sans intervention manuelle. Consultez le guide [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) pour plus de détails sur la configuration.
:::

### Étape 3 : Mettre à jour les plugins de construction {#step-3-update-build-plugins}

Remplacez le plugin Jetty par le plugin Maven de Spring Boot. Supprimez toute configuration Jetty existante et ajoutez :

```xml title="pom.xml"
<build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludeDevtools>true</excludeDevtools>
        </configuration>
      </plugin>
    </plugins>
</build>
```

### Étape 4 : Convertir votre classe d'application {#step-4-convert-your-app-class}

Transformez votre classe principale `App` en une application Spring Boot en ajoutant les annotations Spring nécessaires et une méthode main :

```java title="Application.java"
package com.example;

import com.webforj.App;
import com.webforj.annotation.Routify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify(packages = "com.example.views")
public class Application extends App {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    // Conservez votre méthode run() existante si vous en avez une
    @Override
    public void run() throws WebforjException {
      // Votre code d'initialisation existant 
    }
}
```

L'annotation `@SpringBootApplication` active l'auto-configuration et le scan de composants de Spring. L'annotation `@Routify` reste la même, continuant à scanner vos packages de vues pour les routes.

### Étape 5 : Ajouter une configuration Spring {#step-5-add-spring-configuration}

Créez `application.properties` dans `src/main/resources` :

```Ini title="application.properties"
# Nom de la classe entièrement qualifiée du point d'entrée de l'application
webforj.entry = org.example.Application

# Nom de l'application
spring.application.name=Hello World Spring

# Configuration du serveur
server.port=8080
server.shutdown=immediate

# Configuration des DevTools de webforJ
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Exécuter l'application Spring Boot {#run-the-spring-boot-app}

Une fois configurée, exécutez votre application en utilisant :

```bash
mvn spring-boot:run
```

L'application démarre avec un serveur Tomcat intégré sur le port 8080 par défaut. Vos vues et routes webforJ existantes fonctionnent exactement comme auparavant, mais maintenant vous pouvez injecter des beans Spring et utiliser les fonctionnalités de Spring.

## Configuration

Utilisez le fichier `application.properties` dans `src/main/resources` pour configurer votre application. 
 Consultez [Configuration des propriétés](/docs/configuration/properties) pour des informations sur les propriétés de configuration de webforJ.

Les paramètres `application.properties` de webforJ suivants sont spécifiques à Spring :

| Propriété | Type | Description | Par défaut|
|-----------|------|-------------|-----------|
| **`webforj.servlet-mapping`** | String | Modèle de mappage URL pour le servlet webforJ. | `/*` |
| **`webforj.exclude-urls`** | Liste | Modèles d'URL qui ne devraient pas être traités par webforJ lorsqu'ils sont mappés à la racine. Lorsque webforJ est mappé au contexte racine (`/*`), ces modèles d'URL seront exclus du traitement par webforJ et peuvent être gérés par des contrôleurs Spring MVC à la place. Cela permet aux points de terminaison REST et autres mappings Spring MVC de coexister avec les routes webforJ. | `[]` |

### Différences de configuration {#configuration-differences}

Lorsque vous passez à Spring Boot, plusieurs aspects de la configuration changent :

| Aspect | webforJ standard | webforJ Spring Boot |
|--------|-----------------|---------------------|
| **Emballage** | fichier WAR | JAR exécutable |
| **Serveur** | Externe (Jetty, Tomcat) | Tomcat intégré |
| **Commande d'exécution** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuration principale** | `webforj.conf` uniquement | `application.properties` + `webforj.conf`  |
| **Profils** | `webforj-dev.conf`, `webforj-prod.conf` | Profils Spring avec `application-{profile}.properties` |
| **Configuration du port** | Dans la configuration du plugin | `server.port` dans les propriétés |
