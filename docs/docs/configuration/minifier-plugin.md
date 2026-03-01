---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
---

# Minifier plugin <DocChip chip='since' label='25.11' />

The webforJ Minifier Plugin automatically [minifies](https://en.wikipedia.org/wiki/Minification_(programming)) and optimizes CSS and JavaScript assets during the build process. The plugin discovers assets referenced through webforJ [asset annotations](/docs/managing-resources/importing-assets) and minifies them in the build output, reducing file sizes and improving load times without modifying your original source files.

## Setup {#setup}

If you created your project using [startforJ](https://docs.webforj.com/startforj) or a webforJ [archetype](/docs/building-ui/archetypes/overview), the minifier plugin is already configured and runs automatically when you build with the `prod` profile using `mvn package -Pprod`.

For manual setup, the minifier requires two configurations: an annotation processor to discover assets during compilation, and a plugin to perform the minification.

<Tabs>
<TabItem value="maven" label="Maven">

Add the following to your `pom.xml`:

```xml
<build>
  <plugins>
    <!-- Annotation Processor Configuration -->
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

    <!-- Minifier Plugin Configuration -->
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
        <!-- CSS minification -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript minification -->
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

Add the following to your `build.gradle`:

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
    // Annotation processor for discovering assets during compilation
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Minifier implementations
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

The `minify` task runs automatically before the `jar` or `war` tasks. You can also run it manually with `./gradlew minify`.

</TabItem>
</Tabs>

## Using the plugin {#using-the-plugin}

Once configured, the plugin works automatically. Simply use webforJ asset annotations in your code:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Your application code
}
```

When you build your project, the plugin automatically:

1. Discovers assets referenced in annotations during compilation
2. Minifies the discovered CSS and JavaScript files
3. Reports the size reduction and processing time

### URL protocol resolution {#url-protocol-resolution}

The plugin understands webforJ [URL protocols](/docs/managing-resources/assets-protocols) and resolves them to filesystem paths:

| Protocol | Resolves To | Example |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs without a protocol are not supported by the minifier and will be skipped.

## Built-in minifiers {#built-in-minifiers}

webforJ includes two production-ready minifiers for CSS and JavaScript.

| Minifier | Features | Skips |
|----------|----------|-------|
| CSS | Removes whitespace, comments, and optimizes property values | `.min.css` |
| JavaScript | Variable renaming, dead code elimination, syntax optimization | `.min.js`, `.min.mjs` |

## Configuration options {#configuration-options}

The plugin provides options for disabling minification, customizing JavaScript optimization, and processing additional files.

### Disabling minification {#disabling-minification}

You may want to turn off minification during development or for debugging purposes.

<Tabs>
<TabItem value="maven" label="Maven">

**Via command line:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Via plugin configuration:**
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

**Via build configuration:**
```groovy
webforjMinify {
    skip = true
}
```

</TabItem>
</Tabs>

### JavaScript minifier options {#javascript-minifier-options}

The JavaScript minifier offers several configuration options to control optimization behavior.

:::note Gradle Support
Starting with v25.12, the Gradle plugin supports passing JavaScript minifier options.
:::

| Option | Default | Description |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - removes whitespace and comments only</li><li>`SIMPLE_OPTIMIZATIONS` - variable renaming and dead code removal</li><li>`ADVANCED_OPTIMIZATIONS` - aggressive optimization with function/property renaming</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Input JavaScript version: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` through `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Output JavaScript version: same as `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Set to `true` to preserve formatting for debugging |

Configure these options in the configuration section:

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
    skip = false  // Set to true to skip minification
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Minifying additional files {#minifying-additional-files}

To minify files not discovered through annotations, create a configuration file that specifies glob patterns:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## Custom minifiers {#custom-minifiers}

The plugin supports custom minifiers through Java's Service Provider Interface (SPI), allowing you to add support for additional file types or alternative minification libraries.

### Creating a custom minifier {#creating-a-custom-minifier}

Implement the `AssetMinifier` interface to create your own minifier. The following example shows a JSON minifier that uses Gson to remove whitespace:

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
    // Skip config files and already minified files
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
    // Configuration options if needed
  }
}
```

### Registering your minifier {#registering-your-minifier}

Create a service provider configuration file:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Using your custom minifier {#using-your-custom-minifier}

Package your minifier as a separate JAR and add it as a plugin dependency:

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
    <!-- Standard minifiers (optional) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Common issues {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No minifiers registered</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Make sure ph-css and/or closure-compiler are on the classpath.
      ```

      Add minifier module dependencies to the plugin configuration. For CSS, add `webforj-minify-phcss-css`. For JavaScript, add `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No files processed</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      If the plugin reports `Processed 0 files`, verify that:

      1. The annotation processor is configured in `maven-compiler-plugin` with `webforj-minify-foundation` in `annotationProcessorPaths`
      2. webforJ asset annotations exist in your source code
      3. `target/classes/META-INF/webforj-resources.json` exists after compilation
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>File not found</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Verify the file exists at the correct path under `src/main/resources/static` and that the URL protocol matches the directory structure.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minification errors</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      The plugin warns but continues without failing the build. The original content is preserved when minification fails. To fix syntax errors, validate CSS or JavaScript with a linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
