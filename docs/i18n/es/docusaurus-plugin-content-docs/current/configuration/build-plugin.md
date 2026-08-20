---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 7cb4ddbb9aea86ff6f501296b42c5bbf
---
# plugin de compilación webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

El plugin de compilación webforJ ejecuta el trabajo de tiempo de compilación de webforJ como parte de tu compilación de Maven o Gradle. Lo agregas una vez, y enlaza sus objetivos a las fases que ya ejecutas, sin necesidad de mantener un proyecto frontend separado en sincronía. Impulsa el [empaquetador frontend](/docs/managing-resources/bundler/overview), compilando el frontend, ejecutando las pruebas de frontend, sirviendo la vigilancia de desarrollo y conectando una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) a la aplicación que inicia.

## Agregando el plugin {#adding-the-plugin}

Un proyecto webforJ creado desde un [arquetipo](/docs/introduction/getting-started) ya tiene el plugin. Para agregarlo a un proyecto existente:

<Tabs>
<TabItem value="maven" label="Maven">

Declarar el plugin con `<extensions>true</extensions>` enlaza sus objetivos a la compilación sin bloques de ejecución que escribir:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Agrega el plugin a través de una dependencia en el classpath de `buildscript` y aplícalo:

```groovy title="build.gradle"
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-gradle-plugin:${webforjVersion}"
  }
}

apply plugin: 'com.webforj'
```

</TabItem>
</Tabs>

## Objetivos y tareas {#goals-and-tasks}

Tres objetivos se enlazan a fases que ya ejecutas, por lo que un `mvn package` normal o `./gradlew build` produce una aplicación con su frontend compilado, y la fase de prueba ejecuta las pruebas de frontend junto con las pruebas de Java. La vigilancia es la que inicias manualmente durante el desarrollo:

| Objetivo de Maven | Tarea de Gradle | Se ejecuta | Qué hace |
|-------------------|-----------------|------------|----------|
| `bundle`          | `webforjBundle` | `prepare-package`, antes de cada jar y war | Compila el frontend para la aplicación empaquetada |
| `test`            | `webforjTest`   | con la fase de prueba | Ejecuta las pruebas de frontend |
| `clean`           | `webforjCleanFrontend` | con la fase de limpieza | Elimina el frontend generado |
| `watch`           | `webforjWatch`  | manualmente, junto con la aplicación | Reconstruye en cambios durante el desarrollo |

Inicia la vigilancia como el objetivo antes de aquel que ejecuta la aplicación, `mvn compile webforj:watch spring-boot:run` por ejemplo. Un proyecto de arquetipo configura esto como el objetivo predeterminado, por lo que `mvn` solo inicia todo. Su comportamiento de recarga se cubre en [Vigilancia de frontend](/docs/configuration/deploy-reload/frontend-watch).

Saltar las pruebas de frontend junto con las pruebas de Java, `-DskipTests` o `-Dmaven.test.skip` con Maven y `-PskipTests` con Gradle.

## Opciones {#options}

Establecer opciones como elementos `<configuration>` de Maven, o como valores de extensión `webforj { }` de Gradle. Cada opción de Maven excepto `plugins` y `hotswap` también acepta una propiedad `-D` en la línea de comandos. Las dos herramientas de compilación se reflejan mutuamente:

| Elemento de Maven | Propiedad de Maven | Gradle | Predeterminado | Propósito |
|--------------------|--------------------|--------|----------------|-----------|
| `bunVersion`       | `webforj.bundler.version` | `bunVersion` | gestionado       | Fijar la versión de Bun para construcciones reproducibles |
| `bunPath`         | `webforj.bundler.path`   | `bunPath` | descargar       | Usar un binario de Bun existente en lugar de descargar |
| `cacheDir`        | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Donde se almacenan en caché los binarios de Bun gestionados |
| `sourceRoot`      | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Donde viven los orígenes de entrada del frontend |
| `workDir`         | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Donde el plugin escribe sus archivos generados de compilación |
| `plugins`         | —                      | `plugins` | —               | Activar o desactivar una [extensión](/docs/managing-resources/bundler/extensions/overview) por id, como `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Prefijos de paquete que se omiten durante el análisis de anotaciones |
| `eager`           | `webforj.bundler.eager` | `eager` | `false`         | Cargar todo el frontend al inicio de la aplicación en lugar de por vista, ver [Paquete Eager](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs`        | `webforj.bundler.testArgs` | `testArgs` | — | Argumentos adicionales pasados al ejecutor de pruebas de frontend |
| `hotswap`         | —                      | `hotswap` | —               | Adjuntar una herramienta de actualización de clase a la aplicación que inicia la compilación, ver [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Por ejemplo, para fijar la versión de Bun y activar Tailwind:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <bunVersion>1.3.0</bunVersion>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  bunVersion = '1.3.0'
  plugins.put('webforj-tailwind', 'true')
}
```

</TabItem>
</Tabs>
