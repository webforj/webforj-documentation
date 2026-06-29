---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 0c02e741918864a34c35227387259b40
---
# plugin de construcción webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

El plugin de construcción webforJ ejecuta el trabajo en tiempo de construcción de webforJ como parte de tu construcción de Maven o Gradle. Lo agregas una vez, y vincula sus objetivos a las fases que ya ejecutas, sin un proyecto frontend separado para mantener en sincronización. Controla el [empaquetador de frontend](/docs/managing-resources/bundler/overview), compilando el frontend, ejecutando las pruebas de frontend y sirviendo la vigilancia de desarrollo.

## Agregando el plugin {#adding-the-plugin}

Un proyecto webforJ creado a partir de un [arquetipo](/docs/introduction/getting-started) ya tiene el plugin. Para agregarlo a un proyecto existente:

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

Agrega el plugin a través de una dependencia de classpath `buildscript` y aplícalo:

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

## Objetivos {#goals}

El plugin vincula cuatro objetivos, cada uno a una fase que ya ejecutas, de modo que un `mvn package` o `gradle build` normal produce una app con su frontend compilado, y `mvn test` ejecuta las pruebas del frontend junto a las pruebas de Java.

| Objetivo de Maven | Tarea de Gradle | Fase | Qué hace |
|-------------------|-----------------|------|----------|
| `bundle` | `webforjBundle` | `prepare-package` | Compila el frontend para producción |
| `test` | `webforjTest` | `test` | Ejecuta las pruebas del frontend |
| `clean` | `webforjCleanFrontend` | `clean` | Elimina el frontend generado |
| `watch` | `webforjWatch` | ejecutado manualmente | Reconstruye al cambiar durante el desarrollo |

El objetivo `watch` es el que ejecutas manualmente durante el desarrollo, junto a la app. Su comportamiento de recarga se cubre en [Vigilancia de frontend](/docs/configuration/deploy-reload/frontend-watch).

## Opciones {#options}

Establece opciones como `<configuration>` de Maven (o propiedades `-D` en la línea de comandos), y como valores de extensión `webforj { }` de Gradle. Las dos herramientas de construcción se reflejan entre sí.

| Opción | Propiedad de Maven | Gradle | Predeterminado | Propósito |
|--------|--------------------|--------|----------------|-----------|
| Versión de Bun | `webforj.bundler.version` | `bunVersion` | gestionado | Fijar la versión de Bun para construcciones reproducibles |
| Binario de Bun | `webforj.bundler.path` | `bunPath` | descargar | Usar un binario de Bun existente en lugar de descargar |
| Directorio de caché | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Donde se almacenan en caché los binarios de Bun gestionados |
| Raíz de origen | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Donde viven las fuentes de entrada del frontend |
| Directorio de trabajo | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Donde el plugin escribe sus archivos de construcción generados |
| Extensiones | `plugins` | `plugins` | — | Activar o desactivar una [extensión](/docs/managing-resources/bundler/extensions/overview) por id, como `webforj-tailwind` |
| Paquetes a excluir | `webforj.bundler.excludePackages` | `excludePackages` | — | Prefijos de paquetes a omitir durante el escaneo de anotaciones |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Cargar todo el frontend al inicio de la app en lugar de por vista, ver [Paquete eager](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Argumentos de prueba | `webforj.bundler.testArgs` | `testArgs` | — | Argumentos extra pasados al corredor de pruebas del frontend |
| Omitir pruebas | `skipTests`, `maven.test.skip` | — | `false` | Omitir las pruebas del frontend |

Por ejemplo, para fijar la versión de Bun y activar Tailwind:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <version>1.3.0</version>
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
