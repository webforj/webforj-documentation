---
sidebar_position: 40
title: Plugin de minification
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Plug-in de minification <DocChip chip='since' label='25.11' />

Le plug-in de minification webforJ [minifie](https://en.wikipedia.org/wiki/Minification_(programming)) et optimise automatiquement les ressources CSS et JavaScript pendant le processus de compilation. Il détecte les ressources référencées par les [annotations de ressources](/docs/managing-resources/importing-assets) webforJ et les minifie dans le résultat de compilation, réduisant ainsi la taille des fichiers et améliorant les temps de chargement, sans modifier vos fichiers source d’origine.

:::info La minification est intégrée au bundler
Le [bundler frontend](/docs/managing-resources/bundler/overview) minifie les fichiers CSS et JavaScript lors de sa compilation de production. Un projet qui utilise le bundler n’a donc pas besoin de ce plug-in. Le plug-in reste disponible et pris en charge pour les projets qui chargent des ressources au moyen des annotations de ressources sans utiliser le bundler. Les configurations existantes continuent de fonctionner comme avant, sans modification nécessaire.
:::

## Configuration {#setup}

La configuration du minificateur nécessite deux éléments : un processeur d’annotations pour détecter les ressources pendant la compilation et un plug-in pour effectuer la minification.

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

    <!-- Configuration du plug-in de minification -->
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
  // Processeur d'annotations pour détecter les ressources pendant la compilation
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Implémentations des minificateurs
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

La tâche `minify` s’exécute automatiquement avant les tâches `jar` ou `war`. Vous pouvez également la lancer manuellement avec `./gradlew minify`.

</TabItem>
</Tabs>

## Utilisation du plug-in {#using-the-plugin}

Une fois configuré, le plug-in fonctionne automatiquement. Il vous suffit d’utiliser les annotations de ressources webforJ dans votre code :

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Code de votre application
}
```

Lors de la compilation de votre projet, le plug-in effectue automatiquement les opérations suivantes :

1. Détecte les ressources référencées dans les annotations pendant la compilation
2. Minifie les fichiers CSS et JavaScript détectés
3. Indique la réduction de taille et le temps de traitement

### Résolution des protocoles d’URL {#url-protocol-resolution}

Le plug-in reconnaît les [protocoles d’URL](/docs/managing-resources/assets-protocols) webforJ et les résout en chemins d’accès au système de fichiers :

| Protocole | Résolution | Exemple |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Les URL sans protocole ne sont pas prises en charge par le minificateur et sont ignorées.

## Minificateurs intégrés {#built-in-minifiers}

webforJ inclut deux minificateurs prêts pour la production, pour CSS et JavaScript.

| Minificateur | Fonctionnalités | Fichiers ignorés |
|----------|----------|-------|
| CSS | Supprime les espaces et les commentaires, et optimise les valeurs des propriétés | `.min.css` |
| JavaScript | Renommage des variables, élimination du code mort, optimisation de la syntaxe | `.min.js`, `.min.mjs` |

## Options de configuration {#configuration-options}

Le plug-in propose des options permettant de désactiver la minification, de personnaliser l’optimisation JavaScript et de traiter des fichiers supplémentaires.

### Désactivation de la minification {#disabling-minification}

Vous pouvez désactiver la minification pendant le développement ou à des fins de débogage.

<Tabs>
<TabItem value="maven" label="Maven">

**En ligne de commande :**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Dans la configuration du plug-in :**
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

**Dans la configuration de compilation :**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### Options du minificateur JavaScript {#javascript-minifier-options}

Le minificateur JavaScript propose plusieurs options de configuration pour contrôler le comportement d’optimisation.

:::note Prise en charge de Gradle
À partir de la version 25.12, le plug-in Gradle permet de transmettre des options au minificateur JavaScript.
:::

| Option | Valeur par défaut | Description |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - supprime uniquement les espaces et les commentaires</li><li>`SIMPLE_OPTIMIZATIONS` - renomme les variables et supprime le code mort</li><li>`ADVANCED_OPTIMIZATIONS` - optimisation agressive avec renommage des fonctions et des propriétés</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Version JavaScript en entrée : `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` à `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Version JavaScript en sortie : mêmes valeurs que pour `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Définissez cette option sur `true` pour conserver la mise en forme à des fins de débogage |

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
    skip = false  // Définissez cette valeur sur true pour ignorer la minification
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

Pour minifier des fichiers qui n’ont pas été détectés par les annotations, créez un fichier de configuration indiquant des motifs glob :

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Motifs d'inclusion
**/*.css
**/*.js

# Motifs d'exclusion (préfixés par !)
!**/*.min.css
!**/*.min.js
```

## Minificateurs personnalisés {#custom-minifiers}

Le plug-in prend en charge les minificateurs personnalisés au moyen de l’interface Java Service Provider Interface (SPI). Vous pouvez ainsi ajouter la prise en charge d’autres types de fichiers ou utiliser d’autres bibliothèques de minification.

### Création d’un minificateur personnalisé {#creating-a-custom-minifier}

Implémentez l’interface `AssetMinifier` pour créer votre propre minificateur. L’exemple suivant présente un minificateur JSON qui utilise Gson pour supprimer les espaces :

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
      logger.warning("Malformed JSON in " + sourceFile + ", skipping: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Failed to minify JSON file: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Ignorer les fichiers de configuration et les fichiers déjà minifiés
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
    // Options de configuration, le cas échéant
  }
}
```

### Enregistrement de votre minificateur {#registering-your-minifier}

Créez un fichier de configuration de fournisseur de service :

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Utilisation de votre minificateur personnalisé {#using-your-custom-minifier}

Empaquetez votre minificateur dans un JAR séparé et ajoutez-le comme dépendance du plug-in :

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
    <!-- Minificateurs standard (facultatif) -->
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
    <p>Aucun minificateur enregistré</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      Ajoutez les dépendances des modules de minification à la configuration du plug-in. Pour CSS, ajoutez `webforj-minify-phcss-css`. Pour JavaScript, ajoutez `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Aucun fichier traité</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si le plug-in indique `Processed 0 files`, vérifiez que :

      1. Le processeur d’annotations est configuré dans `maven-compiler-plugin`, avec `webforj-minify-foundation` dans `annotationProcessorPaths`
      2. Des annotations de ressources webforJ sont présentes dans votre code source
      3. Le fichier `target/classes/META-INF/webforj-resources.json` existe après la compilation
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Fichier introuvable</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Vérifiez que le fichier existe à l’emplacement correct sous `src/main/resources/static` et que le protocole de l’URL correspond à la structure des répertoires.
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
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      Le plug-in émet un avertissement, mais poursuit la compilation sans l’interrompre. En cas d’échec de la minification, le contenu d’origine est conservé. Pour corriger les erreurs de syntaxe, validez le CSS ou le JavaScript à l’aide d’un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
