---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# Plugin de construcción de webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

El plugin de construcción de webforJ ejecuta el trabajo de construcción de webforJ como parte de tu construcción de Maven o Gradle. Lo agregas una vez y vincula sus objetivos a las fases que ya ejecutas, sin un proyecto frontend separado que mantener sincronizado. Impulsa el [enlazador de frontend](/docs/managing-resources/bundler/overview), compilando el frontend, ejecutando las pruebas de frontend, sirviendo la vigilancia de desarrollo y adjuntando una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) a la aplicación que inicia.

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

Agrega el plugin a través de una dependencia de classpath de `buildscript` y aplícalo:

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

Tres objetivos se vinculan a fases que ya ejecutas, por lo que un `mvn package` normal o `./gradlew build` produce una aplicación con su frontend compilado, y la fase de prueba ejecuta las pruebas de frontend junto con las pruebas de Java. La vigilancia es la que inicias manualmente durante el desarrollo:

| Objetivo de Maven | Tarea de Gradle | Ejecuta | Qué hace |
|-------------------|------------------|---------|----------|
| `bundle` | `webforjBundle` | `prepare-package`, antes de cada jar y war | Compila el frontend para la aplicación empaquetada |
| `test` | `webforjTest` | con la fase de prueba | Ejecuta las pruebas de frontend |
| `clean` | `webforjCleanFrontend` | con la fase de limpieza | Elimina el frontend generado |
| `watch` | `webforjWatch` | manualmente, junto con la aplicación | Recompila al cambiar durante el desarrollo |
| `push-keys` | `webforjPushKeys` | manualmente, una vez por despliegue | Genera el par de claves para [notificaciones push](/docs/advanced/push-notifications) e imprime las líneas de configuración |
| `bbj-setup` | Solo Maven | manualmente, en la máquina BBj | Instala [BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin) en el BBj local y habilita su punto final de despliegue |
| `bbj-package` | Solo Maven | con la fase de paquete, con un bloque `<bbj>` | Escribe el [archivo de despliegue BBj](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy` | Solo Maven | con la fase de instalación, con un bloque `<bbj>` | [Despliega la aplicación](/docs/configuration/bbjship/deploying#deploy) en BBjServices |
| `bbj-undeploy` | Solo Maven | manualmente | [Elimina la aplicación](/docs/configuration/bbjship/deploying#undeploy) de BBjServices |

Inicia la vigilancia como objetivo antes del que ejecuta la aplicación, por ejemplo, `mvn compile webforj:watch spring-boot:run`. Un proyecto arquetipo establece esto como el objetivo predeterminado, por lo que `mvn` solo inicia todo. Su comportamiento de recarga se detalla en [Vigilancia de frontend](/docs/configuration/deploy-reload/frontend-watch).

Salta las pruebas de frontend junto con las pruebas de Java, `-DskipTests` o `-Dmaven.test.skip` con Maven y `-PskipTests` con Gradle.

## Opciones {#options}

Establece opciones como elementos `<configuration>` de Maven, o como valores de extensión `webforj { }` de Gradle. Cada opción de Maven, excepto `plugins` y `hotswap`, también acepta una propiedad `-D` en la línea de comandos. Las dos herramientas de construcción se reflejan entre sí:

| Elemento de Maven | Propiedad de Maven | Gradle | Predeterminado | Propósito |
|-------------------|--------------------|--------|----------------|-----------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | gestionado | Fijar la versión de Bun para construcciones reproducibles |
| `bunPath` | `webforj.bundler.path` | `bunPath` | descarga | Utilizar un binario de Bun existente en lugar de descargar |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Donde se almacenan en caché los binarios de Bun gestionados |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Donde residen las fuentes de entrada del frontend |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Donde el plugin escribe sus archivos de construcción generados |
| `plugins` | — | `plugins` | — | Activar una [extensión](/docs/managing-resources/bundler/extensions/overview) por id, como `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Prefijos de paquete a omitir durante el escaneo de anotaciones |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Cargar todo el frontend al inicio de la aplicación en lugar de por vista, ver [Paquete ansioso](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Argumentos adicionales pasados al ejecutor de pruebas de frontend |
| `hotswap` | — | `hotswap` | — | Adjuntar una herramienta de actualización de clase a la aplicación que inicia la construcción, ver [Hotswap](/docs/configuration/deploy-reload/hotswap) |

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
