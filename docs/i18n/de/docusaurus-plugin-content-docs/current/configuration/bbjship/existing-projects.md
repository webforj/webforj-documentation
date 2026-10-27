---
title: Vorhandene Projekte
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
Ein Projekt, das mit dem veralteten webforJ-Installationsplugin bereitgestellt wird, wechselt mit einem OpenRewrite-Durchgang zu BBjShip. Der Durchgang ändert die `pom.xml` und die `webforj.conf` des Projekts. Java-Code, Stylesheets und Ressourcen bleiben unverändert.

## Migration mit OpenRewrite {#migrate-with-openrewrite}

Fügen Sie das OpenRewrite Maven-Plugin zu Ihrer `pom.xml` hinzu, mit dem webforJ 26.03-Rezept:

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

Führen Sie die Migration aus und überprüfen Sie, was sich geändert hat:

```bash
mvn rewrite:run
git diff
```

Das migrierte Projekt wird mit BBjShip bereitgestellt. `mvn install` stellt die App bereit und `mvn` startet einen [Entwicklungsdurchlauf](/docs/configuration/deploy-reload/bbjservices). Entfernen Sie anschließend das OpenRewrite-Plugin aus Ihrer `pom.xml`. [Automatisierte Upgrades](/docs/upgrading/automated-upgrades#previewing-changes) zeigt, wie Sie die Änderungen vor dem Durchlauf anzeigen können.

## Was die Migration ändert {#what-the-migration-changes}

### Das Build-Plugin {#the-build-plugin}

Das Installationsplugin wird zum [webforJ-Build-Plugin](/docs/configuration/build-plugin) mit `<extensions>true</extensions>`, sodass `mvn install` ohne Ausführung bereitstellt. Seine Einstellungen werden in den `<bbj>`-Block verschoben:

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

| Installationsplugin | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, mit dem Pfad `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` in `webforj.conf` |
| `<debug>` | `webforj.debug` in `webforj.conf` |

[Bereitstellung](/docs/configuration/bbjship/deploying#settings) listet jede Einstellung des `<bbj>`-Blocks auf.

### Die webforJ-Engine {#the-webforj-engine}

BBjServices bietet die webforJ-Engine, daher muss Ihre App ihre eigene nicht mitbringen. Die `webforj`-Abhängigkeit schließt bereits `webforj-engine` aus. Die Migration deklariert es erneut im `provided`-Scope, damit Ihre IDE weiterhin damit kompiliert:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### Die Konfigurationsdatei {#webforjconf}

Die Einstiegsklasse und das Debug-Flag werden vom Installationsplugin in `webforj.conf` verschoben, neben die Einstellungen, mit denen eine BBjShip-App ausgeführt wird:

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` stellt die `ws://`-statischen Dateien und die `icons://`-Icons aus der App bereit. Ohne dies kann eine App, die `icons://` verwendet, in BBjServices nicht gestartet werden. Die Schlüssel, die die Datei bereits festlegt, bleiben unverändert.

### Entwicklungsdurchläufe {#development-runs}

Die Migration fügt HotswapAgent zur Konfiguration des Build-Plugins hinzu:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Es wird auch `compile webforj:watch` als das Standardziel des Builds festgelegt, sodass `mvn` die App in den BBjServices Ihres eigenen Rechners ausführt:

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

Siehe [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Neu-Bereitstellung und Live-Reload.

## Ohne das Installationsplugin {#without-the-install-plugin}

Ein Projekt, das das Installationsplugin nie verwendet hat, erhält dieselbe Konfiguration manuell: das [Build-Plugin](#the-build-plugin) mit einem `<bbj>`-Block, die `webforj-engine` [Ausschluss und `provided`-Deklaration](#the-webforj-engine) sowie [`webforj.conf`](#webforjconf) mit `webforj.assetsSource = packaged`. Das Projekt verwendet die `jar`-Verpackung, und die `webforj`-Abhängigkeit schließt `webforj-engine` aus:

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
