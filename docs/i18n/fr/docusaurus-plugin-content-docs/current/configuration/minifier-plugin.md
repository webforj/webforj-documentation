---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Plugin de minification <DocChip chip='since' label='25.11' />

Le plugin de minification webforJ automatise la [minification](https://en.wikipedia.org/wiki/Minification_(programming)) et l'optimisation des ressources CSS et JavaScript pendant le processus de construction. Le plugin découvre les ressources référencées via les [annotations de ressources](/docs/managing-resources/importing-assets) de webforJ et les minifie dans la sortie de construction, réduisant les tailles de fichiers et améliorant les temps de chargement sans modifier vos fichiers sources originaux.

## Configuration {#setup}

Si vous avez créé votre projet en utilisant [startforJ](https://docs.webforj.com/startforj) ou un [archétype](/docs/building-ui/archetypes/overview) webforJ, le plugin de minification est déjà configuré et s'exécute automatiquement lorsque vous construisez avec le profil `prod` en utilisant `mvn package -Pprod`.

Pour une configuration manuelle, le minificateur nécessite deux configurations : un processeur d'annotations pour découvrir les ressources pendant la compilation, et un plugin pour effectuer la minification.

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

    <!-- Configuration du plugin de minification -->
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
    // Processeur d'annotations pour découvrir les ressources lors de la compilation
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Implémentations de minification
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

Lorsque vous construisez votre projet, le plugin découvre automatiquement :

1. Les ressources référencées dans les annotations lors de la compilation
2. Minifie les fichiers CSS et JavaScript découverts
3. Rapporte la réduction de taille et le temps de traitement

### Résolution de protocole d'URL {#url-protocol-resolution}

Le plugin comprend les [protocoles d'URL](/docs/managing-resources/assets-protocols) de webforJ et les résout en chemins de système de fichiers :

| Protocole | Résout vers | Exemple |
|-----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Les URL sans protocole ne sont pas prises en charge par le minificateur et seront ignorées.

## Minificateurs intégrés {#built-in-minifiers}

webforJ inclut deux minificateurs prêts pour la production pour CSS et JavaScript.

| Minificateur | Caractéristiques | Ignorer |
|--------------|------------------|---------|
| CSS | Supprime les espaces, les commentaires, et optimise les valeurs des propriétés | `.min.css` |
| JavaScript | Renommage des variables, élimination de code mort, optimisation de la syntaxe | `.min.js`, `.min.mjs` |

## Options de configuration {#configuration-options}

Le plugin fournit des options pour désactiver la minification, personnaliser l'optimisation JavaScript et traiter des fichiers supplémentaires.

### Désactivation de la minification {#disabling-minification}

Vous voudrez peut-être désactiver la minification pendant le développement ou à des fins de débogage.

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

**Via la configuration de construction :**
```groovy
webforjMinify {
    skip = true
}
```

</TabItem>
</Tabs>

### Options du minificateur JavaScript {#javascript-minifier-options}

Le minificateur JavaScript propose plusieurs options de configuration pour contrôler le comportement d'optimisation.

:::info Seulement Maven
Les options du minificateur JavaScript sont actuellement uniquement disponibles pour Maven. Le support Gradle utilise les paramètres par défaut.
:::

| Option | Par défaut | Description |
|--------|------------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - supprime uniquement les espaces et les commentaires</li><li>`SIMPLE_OPTIMIZATIONS` - renommage des variables et élimination du code mort</li><li>`ADVANCED_OPTIMIZATIONS` - optimisation agressive avec renommage de fonctions/propriétés</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Version JavaScript d'entrée : `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` à `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Version JavaScript de sortie : identique à `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Définissez sur `true` pour conserver la mise en forme pour le débogage |

Configurez ces options dans la section `minifierConfigurations` :

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

### Minifier des fichiers supplémentaires {#minifying-additional-files}

Pour minifier des fichiers non découverts via des annotations, créez un fichier de configuration qui spécifie des motifs globaux :

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Modèles d'inclusion
**/*.css
**/*.js

# Modèles d'exclusion (préfixez avec !)
!**/*.min.css
!**/*.min.js
```

## Minificateurs personnalisés {#custom-minifiers}

Le plugin prend en charge des minificateurs personnalisés via l'interface de service provider de Java (SPI), vous permettant d'ajouter la prise en charge de types de fichiers supplémentaires ou de bibliothèques de minification alternatives.

### Création d'un minificateur personnalisé {#creating-a-custom-minifier}

Implémentez l'interface `AssetMinifier` pour créer votre propre minificateur. L'exemple suivant montre un minificateur JSON qui utilise Gson pour supprimer les espaces :

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
    // Ignorer les fichiers de config et les fichiers déjà minifiés
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

### Enregistrement de votre minificateur {#registering-your-minifier}

Créez un fichier de configuration de fournisseur de services :

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Utilisation de votre minificateur personnalisé {#using-your-custom-minifier}

Emballez votre minificateur en tant que JAR séparé et ajoutez-le comme dépendance de plugin :

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
    <!-- Minificateurs standard (optionnel) -->
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
      [WARN] Aucun minificateur enregistré via SPI. Ignorer la minification.
      [WARN] Assurez-vous que ph-css et/ou closure-compiler sont dans le classpath.
      ```

      Ajoutez des dépendances de module de minificateur à la configuration du plugin. Pour CSS, ajoutez `webforj-minify-phcss-css`. Pour JavaScript, ajoutez `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Aucun fichier traité</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si le plugin rapporte `0 fichiers traités`, vérifiez que :

      1. Le processeur d'annotations est configuré dans le `maven-compiler-plugin` avec `webforj-minify-foundation` dans `annotationProcessorPaths`
      2. Les annotations de ressources webforJ existent dans votre code source
      3. `target/classes/META-INF/webforj-resources.json` existe après la compilation
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

      Vérifiez que le fichier existe au bon chemin sous `src/main/resources/static` et que le protocole d'URL correspond à la structure du répertoire.
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
      [WARN] Erreur de minification du fichier /path/to/app.css : erreur de syntaxe à la ligne 42
      ```

      Le plugin émet un avertissement mais continue sans échouer à la construction. Le contenu original est préservé en cas d'échec de la minification. Pour corriger les erreurs de syntaxe, validez le CSS ou le JavaScript avec un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
