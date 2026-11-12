---
sidebar_position: 40
title: Complemento de minificación
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Complemento Minifier <DocChip chip='since' label='25.11' />

El complemento Minifier de webforJ [minifica](https://en.wikipedia.org/wiki/Minification_(programming)) y optimiza automáticamente los recursos CSS y JavaScript durante el proceso de compilación. El complemento detecta los recursos a los que se hace referencia mediante las [anotaciones de recursos](/docs/managing-resources/importing-assets) de webforJ y los minifica en el resultado de la compilación, lo que reduce el tamaño de los archivos y mejora los tiempos de carga sin modificar los archivos de origen.

:::info La minificación está integrada en el empaquetador
El [empaquetador de frontend](/docs/managing-resources/bundler/overview) minifica CSS y JavaScript como parte de su compilación de producción, por lo que los proyectos que utilizan el empaquetador no necesitan este complemento. El complemento sigue disponible y cuenta con soporte para los proyectos que cargan recursos mediante las anotaciones de recursos sin usar el empaquetador. Las configuraciones existentes siguen funcionando como antes, sin necesidad de hacer cambios.
:::

## Configuración {#setup}

Para configurarlo, el minificador requiere dos elementos: un procesador de anotaciones para detectar recursos durante la compilación y un complemento que realice la minificación.

<Tabs>
<TabItem value="maven" label="Maven">

Añade lo siguiente a tu `pom.xml`:

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

Añade lo siguiente a tu `build.gradle`:

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

La tarea `minify` se ejecuta automáticamente antes de las tareas `jar` o `war`. También puedes ejecutarla manualmente con `./gradlew minify`.

</TabItem>
</Tabs>

## Uso del complemento {#using-the-plugin}

Una vez configurado, el complemento funciona automáticamente. Solo tienes que usar las anotaciones de recursos de webforJ en tu código:

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

Al compilar el proyecto, el complemento automáticamente:

1. Detecta los recursos a los que se hace referencia en las anotaciones durante la compilación
2. Minifica los archivos CSS y JavaScript detectados
3. Informa de la reducción de tamaño y del tiempo de procesamiento

### Resolución de protocolos de URL {#url-protocol-resolution}

El complemento reconoce los [protocolos de URL](/docs/managing-resources/assets-protocols) de webforJ y los resuelve a rutas del sistema de archivos:

| Protocolo | Se resuelve en | Ejemplo |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

El minificador no admite las URL sin protocolo y las omitirá.

## Minificadores integrados {#built-in-minifiers}

webforJ incluye dos minificadores listos para producción: uno para CSS y otro para JavaScript.

| Minificador | Características | Omite |
|----------|----------|-------|
| CSS | Elimina espacios en blanco y comentarios, y optimiza los valores de las propiedades | `.min.css` |
| JavaScript | Renombra variables, elimina código muerto y optimiza la sintaxis | `.min.js`, `.min.mjs` |

## Opciones de configuración {#configuration-options}

El complemento ofrece opciones para desactivar la minificación, personalizar la optimización de JavaScript y procesar archivos adicionales.

### Desactivar la minificación {#disabling-minification}

Quizá quieras desactivar la minificación durante el desarrollo o para depurar.

<Tabs>
<TabItem value="maven" label="Maven">

**Mediante la línea de comandos:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Mediante la configuración del complemento:**
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

**Mediante la configuración de compilación:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### Opciones del minificador de JavaScript {#javascript-minifier-options}

El minificador de JavaScript ofrece varias opciones de configuración para controlar el comportamiento de optimización.

:::note Compatibilidad con Gradle
A partir de v25.12, el complemento de Gradle permite pasar opciones al minificador de JavaScript.
:::

| Opción | Valor predeterminado | Descripción |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - elimina solo los espacios en blanco y los comentarios</li><li>`SIMPLE_OPTIMIZATIONS` - renombra variables y elimina código muerto</li><li>`ADVANCED_OPTIMIZATIONS` - optimización intensiva con renombrado de funciones y propiedades</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Versión de JavaScript de entrada: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` hasta `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Versión de JavaScript de salida: las mismas que `languageIn`, más `NO_TRANSPILE` |
| `prettyPrint` | `false` | Establécelo en `true` para conservar el formato y facilitar la depuración |

Configura estas opciones en la sección de configuración:

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

### Minificar archivos adicionales {#minifying-additional-files}

Para minificar archivos que no se detecten mediante anotaciones, crea un archivo de configuración con patrones glob:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## Minificadores personalizados {#custom-minifiers}

El complemento admite minificadores personalizados mediante la interfaz SPI (Service Provider Interface) de Java, lo que te permite añadir compatibilidad con otros tipos de archivo o bibliotecas de minificación alternativas.

### Crear un minificador personalizado {#creating-a-custom-minifier}

Implementa la interfaz `AssetMinifier` para crear tu propio minificador. El siguiente ejemplo muestra un minificador de JSON que usa Gson para eliminar los espacios en blanco:

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

### Registrar el minificador {#registering-your-minifier}

Crea un archivo de configuración del proveedor de servicios:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Usar tu minificador personalizado {#using-your-custom-minifier}

Empaqueta el minificador en un JAR independiente y añádelo como dependencia del complemento:

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

## Problemas comunes {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No hay minificadores registrados</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      Añade dependencias de los módulos de minificación a la configuración del complemento. Para CSS, añade `webforj-minify-phcss-css`. Para JavaScript, añade `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No se procesaron archivos</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si el complemento informa `Processed 0 files`, verifica que:

      1. El procesador de anotaciones esté configurado en `maven-compiler-plugin` con `webforj-minify-foundation` en `annotationProcessorPaths`
      2. Haya anotaciones de recursos de webforJ en el código fuente
      3. Exista `target/classes/META-INF/webforj-resources.json` después de la compilación
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No se encontró el archivo</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Verifica que el archivo exista en la ruta correcta dentro de `src/main/resources/static` y que el protocolo de URL coincida con la estructura de directorios.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Errores de minificación</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      El complemento muestra una advertencia, pero continúa sin interrumpir la compilación. Si la minificación falla, se conserva el contenido original. Para corregir errores de sintaxis, valida el CSS o JavaScript con un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
