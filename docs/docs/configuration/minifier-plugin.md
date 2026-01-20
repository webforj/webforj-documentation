---
sidebar_position: 21
title: Minifier Plugin
---

# Minifier plugin

The webforJ Minifier Plugin automatically optimizes CSS and JavaScript assets during the build process. The plugin discovers assets referenced through webforJ [asset annotations](../managing-resources/importing-assets) and minifies them in place, reducing file sizes and improving load times without manual intervention.

## Requirements {#requirements}

- Java 17 or higher
- Maven 3.6+
- webforJ framework with annotation support

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

**Build Output:**
```
[INFO] Starting webforJ asset minification...
[INFO] Discovered 2 minifier implementation(s) via SPI
[INFO] Processing manifest: target/classes/META-INF/webforj-resources.json
[INFO] Found 2 asset(s) in manifest
[INFO] Minified app.css: 1200 → 850 bytes (29.2% reduction) in 45 ms
[INFO] Minified app.js: 3400 → 1800 bytes (47.1% reduction) in 280 ms
[INFO] Minification complete. Processed 2 file(s) in 335 ms
```

## How it works {#how-it-works}

The minification process operates through a two-phase approach that integrates with the Maven build lifecycle.

### Asset discovery {#asset-discovery}

During compilation, an annotation processor scans your source code for asset annotations:
- `@StyleSheet`
- `@JavaScript`
- `@InlineStyleSheet`
- `@InlineJavaScript`

For detailed information about these annotations and how to use them, see [Importing Assets](../managing-resources/importing-assets).

The processor generates a manifest file that records all discovered assets:

```json
{
  "version": "1.0",
  "generatedAt": "2025-10-17T12:00:00Z",
  "assets": [
    {
      "url": "ws://css/app.css",
      "type": "StyleSheet",
      "discoveredIn": "com.example.MyApp"
    }
  ]
}
```

This manifest is stored at `META-INF/webforj-resources.json` in your compiled output.

### Build-time minification {#build-time-minification}

After compilation completes, the Maven plugin executes in the `process-classes` phase:

1. **Loads Minifiers**: Discovers available minifier implementations using Java's Service Provider Interface (SPI)
2. **Reads Manifest**: Loads the list of assets from the generated manifest file
3. **Resolves Paths**: Converts webforJ URL protocols to actual filesystem paths
4. **Minifies Assets**: Processes each file using the appropriate minifier
5. **Updates Files**: Writes minified content back to the original files in place

For projects with more than 10 files, the plugin automatically processes assets in parallel to improve build performance.

### Protocol resolution {#protocol-resolution}

The plugin understands webforJ's URL protocols and translates them to filesystem locations. For more information about these protocols, see [Assets Protocols](../managing-resources/assets-protocols).

| Protocol | Resolves To | Example |
|----------|-------------|---------|
| `ws://` or `webserver://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

:::info Protocol-less URLs
URLs without a protocol (e.g., `css/app.css`) are treated as `static/` paths for minification purposes. The static directory can be configured using the `webforj.assetsDir` property. See [Configuration Properties](../configuration/properties) for more information.
:::

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

#### Available options {#available-options}

**Compilation Level** (default: `SIMPLE_OPTIMIZATIONS`)
- `WHITESPACE_ONLY` - Removes only whitespace and comments
- `SIMPLE_OPTIMIZATIONS` - Includes variable renaming and dead code removal (recommended)
- `ADVANCED_OPTIMIZATIONS` - Aggressive optimization with function and property renaming

**Language Settings**
- `languageIn` - Input JavaScript version (default: `ECMASCRIPT_NEXT`)
  - Options: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` through `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT`
- `languageOut` - Output JavaScript version (default: `ECMASCRIPT5`)
  - Same options as `languageIn`, plus `NO_TRANSPILE` to skip transpilation

**Pretty Print** (default: `false`)
- Set to `true` to preserve code formatting for debugging purposes

#### Debug output {#debug-output}

Enable Maven's debug logging to see detailed minification information:

```bash
mvn clean install -X
```

This displays:
- Applied configuration values
- Compilation level and language settings for each file
- Which minifier implementations are being used

### Minifying additional files {#minifying-additional-files}

To minify files not discovered through annotations, create a configuration file that specifies glob patterns:

**File**: `src/main/resources/META-INF/webforj-minify.txt`

```
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## Built-in minifiers {#built-in-minifiers}

The plugin includes two production-ready minifiers for CSS and JavaScript.

### CSS minifier {#css-minifier}

**Maven Artifact**: `webforj-minify-phcss-css`

Powered by ph-css 8.0.0, this minifier optimizes CSS files while preserving capabilities. It automatically skips files ending with `.min.css` to avoid redundant processing.

**Optimization Features:**
- Removes comments and unnecessary whitespace
- Optimizes property values and reduces redundancy
- Full CSS3 support including @media queries and vendor prefixes
- Returns original content if parsing fails (graceful error handling)

### JavaScript minifier {#javascript-minifier}

**Maven Artifact**: `webforj-minify-closure-js`

Uses Google Closure Compiler v20250820 to minify and optimize JavaScript. Files ending with `.min.js` or `.mjs` are automatically skipped.

**Default Settings:**
- Compilation level: `SIMPLE_OPTIMIZATIONS`
- Input language: `ECMASCRIPT_NEXT`
- Output language: `ECMASCRIPT5`

**Optimization Features:**
- Variable renaming and scope reduction
- Dead code elimination
- Full ES6+ syntax support with transpilation
- Returns original content if compilation errors occur (graceful error handling)

## Custom minifiers {#custom-minifiers}

The plugin supports custom minifiers through Java's Service Provider Interface (SPI), allowing you to add support for additional file types or alternative minification libraries.

### Plugin architecture {#plugin-architecture}

```
webforj-minify/
├── webforj-minify-foundation/     # Core interfaces + annotation processor
│   ├── AssetMinifier              # SPI interface for minifiers
│   ├── MinifierRegistry           # Thread-safe minifier registry
│   ├── ResourceResolver           # URL protocol resolver
│   ├── MinificationException      # Exception type
│   └── AssetAnnotationProcessor   # Generates manifest at compile time
├── webforj-minify-phcss-css/      # CSS minifier (ph-css 8.0.0)
├── webforj-minify-closure-js/     # JavaScript minifier (Closure Compiler)
└── webforj-minify-maven-plugin/   # Maven plugin
```

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

**File**: `META-INF/services/com.webforj.minify.foundation.AssetMinifier`

```
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

## Troubleshooting {#troubleshooting}

### No minifiers found {#no-minifiers-found}

**Symptoms:**
```
[WARN] No minifiers registered via SPI. Skipping minification.
[WARN] Ensure minifier modules are on the classpath.
```
<!--vale off-->
**Solution:** Verify that minifier dependencies are added to the **plugin** configuration, not the project dependencies:
<!--vale on-->
```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>25.10-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-closure-js</artifactId>
      <version>25.10-SNAPSHOT</version>
    </dependency>
  </dependencies>
</plugin>
```

### Missing asset manifest {#missing-asset-manifest}

<!--vale off-->
**Symptoms:** Plugin reports it cannot find `META-INF/webforj-resources.json`
<!--vale on-->

**Common Causes:**

1. **Annotation processor not configured** - Add `webforj-minify-foundation` to `annotationProcessorPaths` in `maven-compiler-plugin`
2. **No annotations in source code** - Make sure you're using `@StyleSheet` or `@JavaScript` annotations
3. **Annotation processing disabled** - Remove `<proc>none</proc>` from compiler configuration if present

<!--vale off-->
**Verification:** After compilation, check that `target/classes/META-INF/webforj-resources.json` exists
<!--vale on-->

### Asset files not found {#asset-files-not-found}

**Symptoms:**
```
[WARN] File not found: /path/to/static/css/app.css
```

**Solutions:**

1. Verify the file exists at `src/main/resources/static/css/app.css`
2. Check that the URL protocol matches your directory structure:
   - `ws://css/app.css` → `static/css/app.css`
   - `context://css/app.css` → `css/app.css`
3. Make sure the file isn't excluded by `.gitignore` or build filters

### Minification syntax errors {#minification-syntax-errors}

**Symptoms:**
```
[WARN] Error minifying file app.css: Parse error at line 42
```

:::info Graceful Error Handling
The plugin is designed to never fail your build due to minification errors. When minification fails, the plugin logs a warning and keeps the original file content unchanged.
:::

**To resolve:**

1. Validate your CSS/JavaScript syntax using a linter
2. Check for unsupported language features
3. Review the specific error message for line numbers and details
4. Consider using `prettyPrint: true` for JavaScript to make debugging easier

### Security warnings {#security-warnings}

**Symptoms:**
```
[WARN] Security violation for URL '../../../etc/passwd': Path traversal detected
```
<!--vale off-->
**Cause:** The URL contains path traversal attempts that could access files outside the resources directory

**Solution:** Use proper webforJ URL protocols (`ws://`, `context://`) and valid relative paths
<!--vale on-->
## Frequently asked questions {#frequently-asked-questions}

### Why configure the annotation processor separately? {#why-separate-annotation-processor}

Maven 3+ requires explicit annotation processor declarations for security reasons. Since annotation processing occurs during compilation (before the minify plugin runs), it can't be automatically configured. This is the same requirement for popular tools like Lombok, MapStruct, and Dagger.

### How to skip specific files? {#how-to-skip-files}

The built-in minifiers automatically skip already-minified files (`*.min.css`, `*.min.js`). For custom skip logic:

1. Create exclusion patterns in `META-INF/webforj-minify.txt` using the `!` prefix
2. Implement custom `shouldMinify()` logic in your own minifier

