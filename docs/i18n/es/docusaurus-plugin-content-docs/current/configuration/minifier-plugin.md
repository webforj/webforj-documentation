---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Plugin Minificador <DocChip chip='since' label='25.11' />

El Plugin Minificador de webforJ automáticamente [minifica](https://en.wikipedia.org/wiki/Minification_(programming)) y optimiza activos de CSS y JavaScript durante el proceso de construcción. El plugin descubre activos referenciados a través de las [anotaciones de activos](/docs/managing-resources/importing-assets) de webforJ y los minifica en la salida de construcción, reduciendo el tamaño de los archivos y mejorando los tiempos de carga sin modificar tus archivos fuente originales.

## Configuración {#setup}

Si creaste tu proyecto utilizando [startforJ](https://docs.webforj.com/startforj) o un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ, el plugin minificador ya está configurado y se ejecuta automáticamente cuando construyes con el perfil `prod` usando `mvn package -Pprod`.

Para la configuración manual, el minificador requiere dos configuraciones: un procesador de anotaciones para descubrir activos durante la compilación, y un plugin para realizar la minificación.

<Tabs>
<TabItem value="maven" label="Maven">

Agrega lo siguiente a tu `pom.xml`:

```xml
<build>
  <plugins>
    <!-- Configuración del Procesador de Anotaciones -->
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

    <!-- Configuración del Plugin Minificador -->
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
        <!-- Minificación de CSS -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- Minificación de JavaScript -->
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

Agrega lo siguiente a tu `build.gradle`:

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
    // Procesador de anotaciones para descubrir activos durante la compilación
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Implementaciones del minificador
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

La tarea `minify` se ejecuta automáticamente antes de las tareas `jar` o `war`. También puedes ejecutarla manualmente con `./gradlew minify`.

</TabItem>
</Tabs>

## Usando el plugin {#using-the-plugin}

Una vez configurado, el plugin funciona automáticamente. Simplemente usa las anotaciones de activos de webforJ en tu código:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Tu código de aplicación
}
```

Cuando construyes tu proyecto, el plugin automáticamente:

1. Descubre activos referenciados en las anotaciones durante la compilación
2. Minifica los archivos de CSS y JavaScript descubiertos
3. Informa la reducción de tamaño y el tiempo de procesamiento

### Resolución de protocolos de URL {#url-protocol-resolution}

El plugin entiende los [protocolos de URL](/docs/managing-resources/assets-protocols) de webforJ y los resuelve a rutas del sistema de archivos:

| Protocolo | Se resuelve a | Ejemplo |
|-----------|---------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Las URL sin un protocolo no son soportadas por el minificador y serán omitidas.

## Minificadores integrados {#built-in-minifiers}

webforJ incluye dos minificadores listos para producción para CSS y JavaScript.

| Minificador | Características | Omitidos |
|-------------|----------------|----------|
| CSS         | Elimina espacios en blanco, comentarios y optimiza valores de propiedades | `.min.css` |
| JavaScript  | Renombrado de variables, eliminación de código muerto, optimización de sintaxis | `.min.js`, `.min.mjs` |

## Opciones de configuración {#configuration-options}

El plugin proporciona opciones para deshabilitar la minificación, personalizar la optimización de JavaScript y procesar archivos adicionales.

### Deshabilitando la minificación {#disabling-minification}

Es posible que desees desactivar la minificación durante el desarrollo o por motivos de depuración.

<Tabs>
<TabItem value="maven" label="Maven">

**A través de la línea de comandos:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**A través de la configuración del plugin:**
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

**A través de la configuración de construcción:**
```groovy
webforjMinify {
    skip = true
}
```

</TabItem>
</Tabs>

### Opciones del minificador de JavaScript {#javascript-minifier-options}

El minificador de JavaScript ofrece varias opciones de configuración para controlar el comportamiento de optimización.

:::info Solo Maven
Las opciones del minificador de JavaScript actualmente solo están disponibles para Maven. El soporte de Gradle utiliza configuraciones predeterminadas.
:::

| Opción               | Predeterminado               | Descripción |
|----------------------|------------------------------|-------------|
| `compilationLevel`   | `SIMPLE_OPTIMIZATIONS`       | <ul><li>`WHITESPACE_ONLY` - elimina solo espacios en blanco y comentarios</li><li>`SIMPLE_OPTIMIZATIONS` - renombrado de variables y eliminación de código muerto</li><li>`ADVANCED_OPTIMIZATIONS` - optimización agresiva con renombrado de funciones/propiedades</li></ul> |
| `languageIn`        | `ECMASCRIPT_NEXT`            | Versión de JavaScript de entrada: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` hasta `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut`       | `ECMASCRIPT5`                | Versión de JavaScript de salida: igual que `languageIn`, más `NO_TRANSPILE` |
| `prettyPrint`       | `false`                      | Configúralo en `true` para preservar el formato para depuración |

Configura estas opciones en la sección `minifierConfigurations`:

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

### Minificando archivos adicionales {#minifying-additional-files}

Para minificar archivos no descubiertos a través de anotaciones, crea un archivo de configuración que especifique patrones glob:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Patrones a incluir
**/*.css
**/*.js

# Patrones de exclusión (prefijar con !)
!**/*.min.css
!**/*.min.js
```

## Minificadores personalizados {#custom-minifiers}

El plugin soporta minificadores personalizados a través de la Interfaz de Proveedor de Servicios (SPI) de Java, lo que te permite agregar soporte para tipos de archivos adicionales o bibliotecas de minificación alternativas.

### Creando un minificador personalizado {#creating-a-custom-minifier}

Implementa la interfaz `AssetMinifier` para crear tu propio minificador. El siguiente ejemplo muestra un minificador JSON que utiliza Gson para eliminar espacios en blanco:

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
      logger.warning("JSON malformado en " + sourceFile + ", omitiendo: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Error al minificar el archivo JSON: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Omitir archivos de configuración y archivos ya minificados
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
    // Opciones de configuración si es necesario
  }
}
```

### Registrando tu minificador {#registering-your-minifier}

Crea un archivo de configuración del proveedor de servicios:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Usando tu minificador personalizado {#using-your-custom-minifier}

Empaqueta tu minificador como un JAR separado y agrégalo como dependencia del plugin:

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
    <!-- Minificadores estándar (opcional) -->
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
      [WARN] No hay minificadores registrados a través de SPI. Omitiendo la minificación.
      [WARN] Asegúrate de que ph-css y/o closure-compiler estén en el classpath.
      ```

      Agrega las dependencias del módulo del minificador a la configuración del plugin. Para CSS, agrega `webforj-minify-phcss-css`. Para JavaScript, agrega `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>No se procesaron archivos</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Si el plugin informa `Processed 0 files`, verifica que:

      1. El procesador de anotaciones está configurado en `maven-compiler-plugin` con `webforj-minify-foundation` en `annotationProcessorPaths`
      2. Las anotaciones de activos de webforJ existen en tu código fuente
      3. `target/classes/META-INF/webforj-resources.json` existe después de la compilación
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Archivo no encontrado</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Archivo no encontrado: /path/to/static/css/app.css (referenciado como 'ws://css/app.css')
      ```

      Verifica que el archivo exista en la ruta correcta bajo `src/main/resources/static` y que el protocolo de URL coincida con la estructura de directorios.
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
      [WARN] Error al minificar el archivo /path/to/app.css: error de análisis en la línea 42
      ```

      El plugin advierte pero continúa sin fallar la construcción. El contenido original se preserva cuando fallan las minificaciones. Para corregir errores de sintaxis, valida CSS o JavaScript con un linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
