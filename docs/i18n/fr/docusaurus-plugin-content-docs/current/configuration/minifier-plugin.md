---
sidebar_position: 40
title: Minifier Plugin
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: a570df6cd0876073e88e7b38b6357b10
---
# Plugin Minifier <DocChip chip='since' label='25.11' />

Le plugin Minifier de webforJ minifie automatiquement et optimise les ressources CSS et JavaScript durant le processus de construction. Le plugin découvre les ressources référencées à travers les [annotations de ressources](/docs/managing-resources/importing-assets) de webforJ et les minifie dans la sortie de construction, réduisant ainsi la taille des fichiers et améliorant les temps de chargement sans modifier vos fichiers sources originaux.

:::info La minification est intégrée au bundler
Le [bundler frontend](/docs/managing-resources/bundler/overview) minifie le CSS et le JavaScript dans le cadre de sa construction de production, donc un projet utilisant le bundler n'a pas besoin de ce plugin. Le plugin reste disponible et supporté pour les projets qui chargent des ressources via les annotations de ressources sans le bundler. Les configurations existantes continuent de fonctionner comme auparavant, sans changement requis.
:::

## Configuration {#setup}

Si vous avez créé votre projet en utilisant [startforJ](https://docs.webforj.com/startforj) ou un [archétype](/docs/building-ui/archetypes/overview) webforJ, le plugin minifier est déjà configuré et s'exécute automatiquement lorsque vous bâtissez avec le profil `prod` en utilisant `mvn package -Pprod`.

Pour une configuration manuelle, le minifier nécessite deux configurations : un processeur d'annotations pour découvrir les ressources durant la compilation, et un plugin pour effectuer la minification.

<Tabs>
<TabItem value="maven" label="Maven">

Ajoutez ce qui suit à votre `pom.xml` :

```xml
<build>
  <plugins>
    <!-- Configuration du processeur d'annotations -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <configuration>
        <annotationProcessorPaths>
          <path>
            <groupId>com.webforj</groupId>
            <artifactId>webforj-minify-foundation</artifactId>
            <version>${webforj.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>

    <!-- Configuration du plugin Minifier -->
    <plugin>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-maven-plugin</artifactId>
      <version>${webforj.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>minify</goal>
          </goals>
        </execution>
      </executions>
      <dependencies>
        <!-- Minification CSS -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- Minification JavaScript -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-closure-js</artifactId>
          <version>${webforj.version}</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Ajoutez ce qui suit à votre `build.gradle` :

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-minify-gradle-plugin:${webforjVersion}"
  }
}

plugins {
  id 'java'
}

apply plugin: 'com.webforj.minify'

dependencies {
  // Processeur d'annotations pour découvrir les ressources durant la compilation
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Implémentations du Minifier
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

La tâche `minify` s'exécute automatiquement avant les tâches `jar` ou `war`. Vous pouvez également l'exécuter manuellement avec `./gradlew minify`.

</TabItem>
</Tabs>

## Utilisation du plugin {#using-the-plugin}

Une fois configuré, le plugin fonctionne automatiquement. Utilisez simplement les annotations de ressources webforJ dans votre code :

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Votre code d'application
}
```

Lorsque vous construisez votre projet, le plugin :

1. Découvre les ressources référencées dans les annotations durant la compilation
2. Minifie les fichiers CSS et JavaScript découverts
3. Rapporte la réduction de taille et le temps de traitement

### Résolution de protocoles d'URL {#url-protocol-resolution}

Le plugin comprend les [protocoles d'URL](https://docs.webforj.com/managing-resources/assets-protocols) webforJ et les résout en chemins de système de fichiers :

| Protocole | Résout à | Exemple |
|-----------|----------|---------|
| `ws://`    | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Les URL sans protocole ne sont pas supportées par le minifier et seront ignorées.

## Minifiers intégrés {#built-in-minifiers}

webforJ inclut deux minifiers prêts pour la production pour CSS et JavaScript.

| Minifier | Fonctionnalités | Ignore |
|----------|----------------|--------|
| CSS      | Supprime les espaces blancs, les commentaires et optimise les valeurs des propriétés | `.min.css` |
| JavaScript| Renommage des variables, élimination du code mort, optimisation de la syntaxe | `.min.js`, `.min.mjs` |

## Options de configuration {#configuration-options}

Le plugin fournit des options pour désactiver la minification, personnaliser l'optimisation JavaScript et traiter des fichiers supplémentaires.

### Désactivation de la minification {#disabling-minification}

Vous pouvez souhaiter désactiver la minification pendant le développement ou à des fins de débogage.

<Tabs>
<TabItem value="maven" label="Maven">

**Via la ligne de commande :**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Via la configuration du plugin :**
```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <configuration>
    <skip>true</skip>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

**Via la configuration de build :**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### Options du minifier JavaScript {#javascript-minifier-options}

Le minifier JavaScript offre plusieurs options de configuration pour contrôler le comportement d'optimisation.

:::note Support Gradle
À partir de v25.12, le plugin Gradle prend en charge le passage des options de minification JavaScript.
:::

| Option           | Par défaut                  | Description |
|------------------|----------------------------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS`     | <ul><li>`WHITESPACE_ONLY` - supprime les espaces et les commentaires uniquement</li><li>`SIMPLE_OPTIMIZATIONS` - renommage des variables et suppression du code mort</li><li>`ADVANCED_OPTIMIZATIONS` - optimisation agressive avec renommage de fonctions/propriétés</li></ul> |
| `languageIn`      | `ECMASCRIPT_NEXT`          | Version JavaScript d'entrée : `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` à `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut`     | `ECMASCRIPT5`              | Version JavaScript de sortie : identique à `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint`     | `false`                    | Réglez sur `true` pour préserver le formatage pour le débogage |

Configurez ces options dans la section de configuration :

<Tabs>
<TabItem value="maven" label="Maven">

```xml {7-12}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <configuration>
    <minifierConfigurations>
      <closureJs>
        <compilationLevel>SIMPLE_OPTIMIZATIONS</compilationLevel>
        <languageIn>ECMASCRIPT_2020</languageIn>
        <languageOut>ECMASCRIPT5</languageOut>
        <prettyPrint>false</prettyPrint>
      </closureJs>
    </minifierConfigurations>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>minify</goal>
      </goals>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-closure-js</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy
webforjMinify {
    skip = false  // Réglez sur true pour ignorer la minification
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Minification de fichiers supplémentaires {#minifying-additional-files}

Pour minifier des fichiers non découverts par les annotations, créez un fichier de configuration spécifiant des motifs glob :

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Inclus les motifs
**/*.css
**/*.js

# Motifs d'exclusion (préfixez par !)
!**/*.min.css
!**/*.min.js
```

## Minifiers personnalisés {#custom-minifiers}

Le plugin supporte des minifiers personnalisés via l'interface de fournisseur de services de Java (SPI), vous permettant d'ajouter un support pour des types de fichiers supplémentaires ou des bibliothèques de minification alternatives.

### Création d'un minifier personnalisé {#creating-a-custom-minifier}

Implémentez l'interface `AssetMinifier` pour créer votre propre minifier. L'exemple suivant montre un minifier JSON qui utilise Gson pour supprimer les espaces :

```java title="src/main/java/com/example/minifier/JsonMinifier.java"
package com.example.minifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.webforj.minify.common.AssetMinifier;
import com.webforj.minify.common.MinificationException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class JsonMinifier implements AssetMinifier {

  private static final Logger logger = Logger.getLogger(JsonMinifier.class.getName());
  private final Gson gson = new GsonBuilder().create();

  @Override
  public String minify(String content, Path sourceFile) throws MinificationException {
    try {
      JsonElement element = gson.fromJson(content, JsonElement.class);
      return gson.toJson(element);
    } catch (JsonSyntaxException e) {
      logger.warning("JSON malformé dans " + sourceFile + ", ignoré : " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Échec de la minification du fichier JSON : " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Ignorer les fichiers de configuration et déjà minifiés
    if (filename.equals("package.json") || filename.equals("tsconfig.json")) {
      return false;
    }
    if (filename.endsWith("-lock.json") || filename.endsWith(".min.json")) {
      return false;
    }
    return true;
  }

  @Override
  public void configure(Map<String, Object> options) {
    // Options de configuration si nécessaire
  }
}
```

### Enregistrement de votre minifier {#registering-your-minifier}

Créez un fichier de configuration du fournisseur de services :

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Utilisation de votre minifier personnalisé {#using-your-custom-minifier}

Packagez votre minifier en tant que JAR séparé et ajoutez-le en tant que dépendance de plugin :

```xml {5-9}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>json-minifier</artifactId>
      <version>1.0.0</version>
    </dependency>
    <!-- Minifiers standards (optionnel) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Problèmes courants {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Aucun minifier enregistré</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Aucun minifier enregistré via SPI. Ignorer la minification.
      [WARN] Assurez-vous que ph-css et/ou closure-compiler sont sur le classpath.
      ```

      Ajoutez les dépendances de module minifier à la configuration du plugin. Pour CSS, ajoutez `webforj-minify-phcss-css`. Pour JavaScript, ajoutez `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Aucun fichier traité</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si le plugin signale `0 fichiers traités`, vérifiez que :

      1. Le processeur d'annotations est configuré dans le `maven-compiler-plugin` avec `webforj-minify-foundation` dans `annotationProcessorPaths`
      2. Les annotations de ressources webforJ existent dans votre code source
      3. `target/classes/META-INF/webforj-resources.json` existe après compilation
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Fichier non trouvé</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Fichier non trouvé : /path/to/static/css/app.css (référencé comme 'ws://css/app.css')
      ```

      Vérifiez que le fichier existe au bon chemin sous `src/main/resources/static` et que le protocole URL correspond à la structure du répertoire.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Erreurs de minification</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Erreur lors de la minification du fichier /path/to/app.css : erreur d'analyse à la ligne 42
      ```

      Le plugin avertit mais continue sans échouer la construction. Le contenu original est préservé lorsque la minification échoue. Pour corriger les erreurs de syntaxe, validez le CSS ou le JavaScript avec un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
