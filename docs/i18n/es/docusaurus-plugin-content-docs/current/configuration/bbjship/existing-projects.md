---
title: Proyectos Existentes
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
Un proyecto que se despliega con el plugin de instalación en desuso de webforJ se mueve a BBjShip con una ejecución de OpenRewrite. Esta ejecución cambia el `pom.xml` y el `webforj.conf` del proyecto. El código Java, las hojas de estilo y los recursos permanecen sin cambios.

## Migrar con OpenRewrite {#migrate-with-openrewrite}

Agrega el plugin Maven de OpenRewrite a tu `pom.xml`, con la receta de webforJ 26.03:

```xml title="pom.xml"
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>com.webforj.rewrite.v26.UpgradeWebforj_26_03</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>26.03</version>
    </dependency>
  </dependencies>
</plugin>
```

Ejecuta la migración y luego revisa qué ha cambiado:

```bash
mvn rewrite:run
git diff
```

El proyecto migrado se despliega con BBjShip. `mvn install` despliega la aplicación, y `mvn` inicia una [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices). Elimina el plugin de OpenRewrite de tu `pom.xml` después. [Actualizaciones automáticas](/docs/upgrading/automated-upgrades#previewing-changes) muestra cómo previsualizar los cambios antes de la ejecución.

## Qué cambia la migración {#what-the-migration-changes}

### El plugin de construcción {#the-build-plugin}

El plugin de instalación se convierte en el [plugin de construcción de webforJ](/docs/configuration/build-plugin) con `<extensions>true</extensions>`, por lo que `mvn install` despliega sin ejecución. Sus configuraciones se trasladan al bloque `<bbj>`:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
  <configuration>
    <bbj>
      <publishName>inventory</publishName>
      <deployment>
        <url>http://localhost:8888/webforj-bbjship</url>
      </deployment>
    </bbj>
  </configuration>
</plugin>
```

| Plugin de instalación | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, con la ruta `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` en `webforj.conf` |
| `<debug>` | `webforj.debug` en `webforj.conf` |

[Despliegue](/docs/configuration/bbjship/deploying#settings) enumera cada configuración del bloque `<bbj>`.

### El motor de webforJ {#the-webforj-engine}

BBjServices proporciona el motor de webforJ, por lo que tu aplicación no debe llevar el suyo propio. La dependencia `webforj` ya excluye `webforj-engine`. La migración la declara nuevamente en el alcance `provided` para que tu IDE todavía compile en contra de ella:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### El archivo de configuración {#webforjconf}

La clase de entrada y la bandera de depuración se trasladan del plugin de instalación a `webforj.conf`, junto a la configuración con la que se ejecuta una aplicación BBjShip:

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` sirve los archivos estáticos de `ws://` y los iconos de `icons://` de la aplicación. Sin esto, una aplicación que usa `icons://` no puede iniciar en BBjServices. Las claves que ya establece el archivo permanecen sin cambios.

### Ejecuciones de desarrollo {#development-runs}

La migración añade HotswapAgent a la configuración del plugin de construcción:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

También establece `compile webforj:watch` como la meta predeterminada de la construcción, por lo que `mvn` ejecuta la aplicación en los BBjServices de tu propia máquina:

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

Consulta [BBjServices](/docs/configuration/deploy-reload/bbjservices) en Redeploy y Live Reload.

## Sin el plugin de instalación {#without-the-install-plugin}

Un proyecto que nunca usó el plugin de instalación obtiene la misma configuración a mano: el [plugin de construcción](#the-build-plugin) con un bloque `<bbj>`, la exclusión de `webforj-engine` y la declaración `provided` [de la dependencia](#the-webforj-engine), y [`webforj.conf`](#webforjconf) con `webforj.assetsSource = packaged`. El proyecto utiliza empaquetado `jar`, y la dependencia `webforj` excluye `webforj-engine`:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj</artifactId>
  <version>${webforj.version}</version>
  <exclusions>
    <exclusion>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-engine</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
