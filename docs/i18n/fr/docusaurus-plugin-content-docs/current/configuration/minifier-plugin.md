---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: updated-content
_i18n_hash: bbc598d57e4531fcd7f76fe117d2e49a
---
# Plugin Minifier <DocChip chip='since' label='25.11' />

Le plugin Minifier de webforJ minimise automatiquement [minifie](https://en.wikipedia.org/wiki/Minification_(programming)) et optimise les actifs CSS et JavaScript pendant le processus de build. Le plugin découvre les actifs référencés via les [annotations d'actifs de webforJ](/docs/managing-resources/importing-assets) et les minifie dans la sortie de build, réduisant les tailles de fichiers et améliorant les temps de chargement sans modifier vos fichiers source originaux.

## Configuration {#setup}

Si vous avez créé votre projet en utilisant [startforJ](https://docs.webforj.com/startforj) ou un [archétype](/docs/building-ui/archetypes/overview) webforJ, le plugin minifier est déjà configuré et s'exécute automatiquement lorsque vous construisez avec le profil `prod` en utilisant `mvn package -Pprod`.

Pour une configuration manuelle, le minifier nécessite deux configurations : un processeur d'annotations pour découvrir les actifs pendant la compilation, et un plugin pour effectuer la minification.

<Tabs>
<TabItem value="maven" label="Maven">

Ajoutez le suivant à votre `pom.xml` :

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

Ajoutez le suivant à votre `build.gradle` :

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
  // Processeur d'annotations pour découvrir les actifs pendant la compilation
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

Une fois configuré, le plugin fonctionne automatiquement. Utilisez simplement les annotations d'actifs webforJ dans votre code :

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

Lorsque vous compilez votre projet, le plugin :

1. Découvre les actifs référencés dans les annotations pendant la compilation
2. Minifie les fichiers CSS et JavaScript découverts
3. Informe de la réduction de taille et du temps de traitement

### Résolution des protocoles d'URL {#url-protocol-resolution}

Le plugin comprend les [protocoles d'URL de webforJ](/docs/managing-resources/assets-protocols) et les résout en chemins de système de fichiers :

| Protocole | Résout à | Exemple |
|-----------|----------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Les URL sans protocole ne sont pas prises en charge par le minifier et seront ignorées.

## Minifiers intégrés {#built-in-minifiers}

webforJ inclut deux minifiers prêts pour la production pour CSS et JavaScript.

| Minifier | Fonctionnalités | Ignore |
|----------|-----------------|--------|
| CSS | Supprime les espaces, les commentaires et optimise les valeurs de propriété | `.min.css` |
| JavaScript | Renommage des variables, élimination du code mort, optimisation de la syntaxe | `.min.js`, `.min.mjs` |

## Options de configuration {#configuration-options}

Le plugin fournit des options pour désactiver la minification, personnaliser l'optimisation JavaScript et traiter des fichiers supplémentaires.

### Désactivation de la minification {#disabling-minification}

Vous pouvez vouloir désactiver la minification pendant le développement ou à des fins de débogage.

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

**Via la configuration du build :**
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
À partir de v25.12, le plugin Gradle prend en charge le passage des options du minifier JavaScript.
:::

| Option | Par défaut | Description |
|--------|------------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - supprime uniquement les espaces et les commentaires</li><li>`SIMPLE_OPTIMIZATIONS` - renommage des variables et suppression du code mort</li><li>`ADVANCED_OPTIMIZATIONS` - optimisation agressive avec renommage de fonction/propriété</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Version d'entrée JavaScript : `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` jusqu'à `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Version de sortie JavaScript : identique à `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Défini sur `true` pour conserver le formatage pour le débogage |

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
    skip = false  // Défini sur true pour ignorer la minification
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

Pour minifier des fichiers non découverts via les annotations, créez un fichier de configuration qui spécifie des motifs globaux :

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Modèles d'inclusion
**/*.css
**/*.js

# Modèles d'exclusion (préfixés par !)
!**/*.min.css
!**/*.min.js
```

## Minifiers personnalisés {#custom-minifiers}

Le plugin prend en charge les minifiers personnalisés via l'interface de service de Java (SPI), vous permettant d'ajouter un support pour des types de fichiers supplémentaires ou des bibliothèques de minification alternatives.

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
      logger.warning("JSON malformé dans " + sourceFile + ", saut : " + e.getMessage());
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
    // Options de configuration si nécessaire
  }
}
```

### Enregistrement de votre minifier {#registering-your-minifier}

Créez un fichier de configuration de fournisseur de service :

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Utilisation de votre minifier personnalisé {#using-your-custom-minifier}

Packagez votre minifier en tant que JAR séparé et ajoutez-le en tant que dépendance du plugin :

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
      [WARN] Aucun minifier enregistré via SPI. Saut de la minification.
      [WARN] Assurez-vous que ph-css et/ou closure-compiler sont sur le chemin de classe.
      ```

      Ajoutez des dépendances de module minifier à la configuration du plugin. Pour CSS, ajoutez `webforj-minify-phcss-css`. Pour JavaScript, ajoutez `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Aucun fichier traité</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si le plugin indique `0 fichiers traités`, vérifiez que :

      1. Le processeur d'annotations est configuré dans `maven-compiler-plugin` avec `webforj-minify-foundation` dans `annotationProcessorPaths`
      2. Les annotations d'actifs webforJ existent dans votre code source
      3. `target/classes/META-INF/webforj-resources.json` existe après compilation
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
      [WARN] Fichier introuvable : /path/to/static/css/app.css (référencé comme 'ws://css/app.css')
      ```

      Vérifiez que le fichier existe au bon chemin sous `src/main/resources/static` et que le protocole d'URL correspond à la structure de répertoire.
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
      [WARN] Erreur lors de la minification du fichier /path/to/app.css : erreur de parsing à la ligne 42
      ```

      Le plugin avertit mais continue sans échouer le build. Le contenu original est préservé lorsque la minification échoue. Pour corriger les erreurs de syntaxe, validez CSS ou JavaScript avec un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
