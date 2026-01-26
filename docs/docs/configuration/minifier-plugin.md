---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
---

# Minifier plugin

<DocChip chip='since' label='25.11' />

The webforJ Minifier Plugin automatically [minifies](https://en.wikipedia.org/wiki/Minification_(programming)) and optimizes CSS and JavaScript assets during the build process. The plugin discovers assets referenced through webforJ [asset annotations](../managing-resources/importing-assets) and minifies them in place, reducing file sizes and improving load times without manual intervention.

## Setup {#setup}

The minifier plugin requires two plugins in your Maven configuration: an annotation processor to discover assets during compilation, and a plugin to perform the minification.

Add the following configuration to your `pom.xml`:

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
            <version>25.10-SNAPSHOT</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>

    <!-- Minifier Plugin Configuration -->
    <plugin>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-maven-plugin</artifactId>
      <version>25.10-SNAPSHOT</version>
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
          <version>25.10-SNAPSHOT</version>
        </dependency>
        <!-- JavaScript minification -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-closure-js</artifactId>
          <version>25.10-SNAPSHOT</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

:::info Why Two Configurations?
The annotation processor must be configured separately because it runs during compilation, before the minify plugin executes. Maven 3+ requires explicit annotation processor declarations for security reasons. This is standard practice for tools like Lombok, MapStruct, and other annotation processors.
:::

### Using the plugin {#using-the-plugin}

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

When you build your project with `mvn package`, the plugin automatically:

1. Discovers assets referenced in annotations during compilation
2. Minifies the discovered CSS and JavaScript files
3. Reports the size reduction and processing time

## Configuration options {#configuration-options}

### Disabling minification {#disabling-minification}

You may want to turn off minification during development or for debugging purposes.

**Via Command Line:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Via Plugin Configuration:**
```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <configuration>
    <skip>true</skip>
  </configuration>
</plugin>
```

### JavaScript minifier options {#javascript-minifier-options}

The JavaScript minifier uses Google Closure Compiler, which offers several configuration options to control optimization behavior.

#### Available options {#available-options}

`compilationLevel` (default: `SIMPLE_OPTIMIZATIONS`)
- `WHITESPACE_ONLY` - Removes only whitespace and comments
- `SIMPLE_OPTIMIZATIONS` - Includes variable renaming and dead code removal (recommended)
- `ADVANCED_OPTIMIZATIONS` - Aggressive optimization with function and property renaming

`languageIn` - Input JavaScript version (default: `ECMASCRIPT_NEXT`)
- Options: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` through `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT`

`languageOut` - Output JavaScript version (default: `ECMASCRIPT5`)
- Same options as `languageIn`, plus `NO_TRANSPILE` to skip transpilation

`prettyPrint` (default: `false`)
- Set to `true` to preserve code formatting for debugging purposes

```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <version>25.10-SNAPSHOT</version>
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
      <version>25.10-SNAPSHOT</version>
    </dependency>
  </dependencies>
</plugin>
```

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

## Built-in minifiers {#built-in-minifiers}

The plugin includes two production-ready minifiers for CSS and JavaScript.

<!-- vale off -->
**CSS Minifier:** Powered by ph-css, this minifier optimizes CSS files while preserving capabilities. It automatically skips files ending with `.min.css` to avoid redundant processing.

**JavaScript Minifier:** Uses Google Closure Compiler v20250820 to minify and optimize JavaScript. Files ending with `.min.js` or `.mjs` are automatically skipped.
<!-- vale on -->

## Custom minifiers {#custom-minifiers}

The plugin supports custom minifiers through Java's Service Provider Interface (SPI), allowing you to add support for additional file types or alternative minification libraries.

### Creating a custom minifier {#creating-a-custom-minifier}

Implement the `AssetMinifier` interface to create your own minifier:

```java
package com.example;

import com.webforj.minify.foundation.AssetMinifier;
import com.webforj.minify.foundation.MinificationException;
import java.nio.file.Path;
import java.util.Set;

public class SassMinifier implements AssetMinifier {

  @Override
  public String minify(String content, Path sourceFile) throws MinificationException {
    try {
      // Your minification logic here
      return compileSass(content);
    } catch (Exception e) {
      throw new MinificationException("Failed to minify " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("scss", "sass");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    // Skip already minified files
    String fileName = filePath.getFileName().toString().toLowerCase();
    return !fileName.endsWith(".min.scss") && !fileName.endsWith(".min.sass");
  }
}
```

### Registering your minifier {#registering-your-minifier}

Create a service provider configuration file:

```hocon title="META-INF/services/com.webforj.minify.foundation.AssetMinifier"
com.example.SassMinifier
```

### Using your custom minifier {#using-your-custom-minifier}

Add your minifier as a plugin dependency:

```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>my-sass-minifier</artifactId>
      <version>1.0.0</version>
    </dependency>
    <!-- Standard minifiers (optional) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>25.10-SNAPSHOT</version>
    </dependency>
  </dependencies>
</plugin>
```


