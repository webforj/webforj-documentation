---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: c988b8fe49245d60c0e5f18c830595e3
---
Spring Boot est un choix populaire pour construire des applications Java, offrant l'injection de dépendances, l'auto-configuration et un modèle de serveur intégré. Lors de l'utilisation de Spring Boot avec webforJ, vous pouvez injecter des services, des dépôts et d'autres beans gérés par Spring directement dans vos composants d'interface utilisateur via l'injection par constructeur.

Lorsque vous utilisez Spring Boot avec webforJ, votre application s'exécute en tant que JAR exécutable avec un serveur Tomcat intégré au lieu de déployer un fichier WAR sur un serveur d'application externe. Ce modèle de packaging simplifie le déploiement et s'aligne sur les pratiques de déploiement cloud-native. Le modèle de composants et de routage de webforJ fonctionne aux côtés du contexte d'application de Spring pour gérer les dépendances et la configuration.

## Créer une application Spring Boot {#create-a-spring-boot-app}

Vous avez deux options pour créer une nouvelle application webforJ avec Spring Boot : utiliser l'outil graphique startforJ ou la ligne de commande Maven.

### Option 1 : Utiliser startforJ {#option-1-using-startforj}

La façon la plus simple de créer une nouvelle application webforJ est [startforJ](https://docs.webforj.com/startforj), qui génère un projet de démarrage minimal basé sur un archétype webforJ choisi. Ce projet de démarrage inclut toutes les dépendances requises, les fichiers de configuration et une mise en page pré-fabriquée, afin que vous puissiez commencer à construire dessus immédiatement.

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de projet de base (Nom de l'application, ID de groupe, ID d'artefact)  
- Version de webforJ et version de Java
- Couleur du thème et icône
- Archétype
- **Flavor** - Sélectionnez **webforJ Spring** pour créer un projet Spring Boot

En utilisant ces informations, startforJ créera un projet de base basé sur l'archétype choisi configuré pour Spring Boot.
Vous pouvez choisir de télécharger votre projet sous forme de fichier ZIP ou de le publier directement sur GitHub.

### Option 2 : Utiliser la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez directement un projet webforJ Spring Boot en utilisant les archétypes webforJ officiels :

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

Le paramètre `flavor` indique à l'archétype de générer un projet Spring Boot plutôt qu'un projet webforJ standard.

Cela crée un projet Spring Boot complet avec :
- Configuration parent POM Spring Boot
- Dépendance de démarrage Spring Boot webforJ
- Classe principale de l'application avec `@SpringBootApplication` et `@Routify`
- Vues d'exemple
- Fichiers de configuration pour Spring et webforJ

## Ajouter Spring Boot à des projets existants {#add-spring-boot-to-existing-projects}

Si vous avez une application webforJ existante, vous pouvez ajouter Spring Boot en modifiant la configuration de votre projet. Ce processus implique la mise à jour de votre configuration Maven, l'ajout de dépendances Spring et la conversion de votre classe principale.

:::info[Pour les projets existants uniquement]
Sauter cette section si vous créez un nouveau projet depuis zéro.
:::

### Étape 1 : Mettre à jour la configuration Maven {#step-1-update-maven-configuration}

Apportez les modifications suivantes à votre fichier POM :

1. Changez le packaging de WAR à JAR :
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Définissez Spring Boot comme le POM parent :
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Supprimez toute configuration spécifique au WAR comme :
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

Ajoutez le starter Spring Boot webforJ à vos dépendances. Conservez votre dépendance webforJ existante :

```xml title="pom.xml"
<dependencies>
    <!-- Votre dépendance webforJ existante -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Ajouter le starter Spring Boot -->
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

:::tip[webforJ DevTools pour le rafraîchissement automatique du navigateur]
La dépendance `webforj-spring-devtools` étend Spring DevTools avec un rafraîchissement automatique du navigateur. Lorsque vous enregistrez des modifications dans votre IDE, le navigateur se recharge automatiquement sans intervention manuelle. Voir le guide [Spring DevTools](spring-devtools) pour les détails de configuration.
:::

### Étape 3 : Mettre à jour les plugins de construction {#step-3-update-build-plugins}

Remplacez le plugin Jetty par le plugin Maven Spring Boot. Supprimez toute configuration Jetty existante et ajoutez :

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

L'annotation `@SpringBootApplication` active l'auto-configuration de Spring et le scan des composants. L'annotation `@Routify` reste la même, continuant de scanner vos packages de vues pour les routes.

### Étape 5 : Ajouter une configuration Spring {#step-5-add-spring-configuration}

Créez `application.properties` dans `src/main/resources` :

```Ini title="application.properties"
# Nom de l'application
spring.application.name=Hello World Spring

# Configuration du serveur
server.port=8080
server.shutdown=immediate

# Configuration des DevTools webforJ
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Votre fichier `webforj.conf` existant continue de fonctionner. Pointez-le vers votre classe principale :

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Exécuter l'application Spring Boot {#run-the-spring-boot-app}

Une fois configurée, exécutez votre application en utilisant :

```bash
mvn spring-boot:run
```

L'application démarre avec un serveur Tomcat intégré sur le port 8080 par défaut. Vos vues et routes webforJ existantes fonctionnent exactement comme auparavant, mais vous pouvez maintenant injecter des beans Spring et utiliser les fonctionnalités de Spring.

## Différences de configuration {#configuration-differences}

Lorsque vous passez à Spring Boot, plusieurs aspects de la configuration changent :

| Aspect | webforJ standard | webforJ Spring Boot |
|--------|-----------------|-------------------|
| **Packaging** | Fichier WAR | JAR exécutable |
| **Serveur** | Externe (Jetty, Tomcat) | Tomcat intégré |
| **Commande d'exécution** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Configuration principale** | `webforj.conf` uniquement | `webforj.conf` + `application.properties` |
| **Profils** | `webforj-dev.conf`, `webforj-prod.conf` | Profils Spring avec `application-{profile}.properties` |
| **Configuration du port** | Dans la configuration du plugin | `server.port` dans les propriétés |
