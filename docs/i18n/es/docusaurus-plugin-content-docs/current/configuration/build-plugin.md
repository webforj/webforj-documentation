---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 09a13bb6da32b3c4c0e77d4e44c1acb4
---
# Plugin de construcción de webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

El plugin de construcción de webforJ ejecuta el trabajo de tiempo de construcción de webforJ como parte de tu construcción de Maven o Gradle. Lo agregas una vez y vincula sus objetivos a las fases que ya ejecutas, sin un proyecto frontend separado que mantener sincronizado. Controla el [agregador de frontend](/docs/managing-resources/bundler/overview), compilando el frontend, ejecutando las pruebas del frontend, sirviendo la vigilancia de desarrollo y adjuntando una [herramienta de intercambio en caliente](/docs/configuration/deploy-reload/hotswap) a la aplicación que inicia.

## Agregar el plugin {#adding-the-plugin}

Un proyecto de webforJ creado a partir de un [arquetipo](/docs/introduction/getting-started) ya tiene el plugin. Para agregarlo a un proyecto existente:

<Tabs>
<TabItem value="maven" label="Maven">

Declarar el plugin con `<extensions>true</extensions>` vincula sus objetivos a la construcción sin bloques de ejecución que escribir:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Agrega el plugin a través de una dependencia de classpath en `buildscript` y aplícalo:

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

Tres objetivos se vinculan a las fases que ya ejecutas, por lo que un `mvn package` normal o `./gradlew build` produce una aplicación con su frontend compilado, y la fase de prueba ejecuta las pruebas del frontend junto con las pruebas de Java. La vigilancia es la que inicias manualmente durante el desarrollo:

| Objetivo de Maven | Tarea de Gradle | Se ejecuta | Lo que hace |
|-------------------|------------------|------------|-------------|
| `bundle` | `webforjBundle` | `prepare-package`, antes de cada jar y war | Compila el frontend para la aplicación empaquetada |
| `test` | `webforjTest` | con la fase de prueba | Ejecuta las pruebas del frontend |
| `clean` | `webforjCleanFrontend` | con la fase de limpieza | Elimina el frontend generado |
| `watch` | `webforjWatch` | manualmente, junto con la aplicación | Reconstruye en cambio durante el desarrollo |
| `push-keys` | `webforjPushKeys` | manualmente, una vez por implementación | Genera el par de claves para [notificaciones push](/docs/advanced/push-notifications) e imprime las líneas de configuración |

Inicia la vigilancia como el objetivo anterior al que ejecuta la aplicación, `mvn compile webforj:watch spring-boot:run`, por ejemplo. Un proyecto arquetipo establece esto como el objetivo predeterminado, por lo que `mvn` solo inicia todo. Su comportamiento de recarga se cubre en [Vigilancia de frontend](/docs/configuration/deploy-reload/frontend-watch).

Omitir las pruebas del frontend junto con las pruebas de Java, `-DskipTests` o `-Dmaven.test.skip` con Maven y `-PskipTests` con Gradle.

## Opciones {#options}

Establece opciones como elementos `<configuration>` de Maven, o como valores de extensión `webforj { }` de Gradle. Cada opción de Maven excepto `plugins` y `hotswap` también acepta una propiedad `-D` en la línea de comandos. Las dos herramientas de construcción se reflejan entre sí:

| Elemento de Maven | Propiedad de Maven | Gradle | Predeterminado | Propósito |
|-------------------|--------------------|--------|----------------|-----------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | gestionado | Fija la versión de Bun para construcciones reproducibles |
| `bunPath` | `webforj.bundler.path` | `bunPath` | descargar | Usa un binario de Bun existente en lugar de descargar |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Dónde se almacenan los binarios de Bun gestionados |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Dónde viven las fuentes de entrada del frontend |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Dónde el plugin escribe sus archivos de construcción generados |
| `plugins` | — | `plugins` | — | Activa o desactiva una [extensión](/docs/managing-resources/bundler/extensions/overview) por id, como `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Prefijos de paquete para omitir durante el escaneo de anotaciones |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Carga todo el frontend al inicio de la aplicación en lugar de por vista, ver [Paquete ansioso](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Argumentos adicionales pasados al ejecutor de pruebas del frontend |
| `hotswap` | — | `hotswap` | — | Adjunta una herramienta de actualización de clases a la aplicación que inicia la construcción, ver [Intercambio en caliente](/docs/configuration/deploy-reload/hotswap) |

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
