---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 7cb4ddbb9aea86ff6f501296b42c5bbf
---
# webforJ Build-Plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Das webforJ Build-Plugin führt die Build-Zeit-Arbeiten von webforJ als Teil Ihres Maven- oder Gradle-Builds aus. Sie fügen es einmal hinzu, und es bindet seine Ziele an die Phasen, die Sie bereits ausführen, ohne dass ein separates Frontend-Projekt synchronisiert werden muss. Es steuert den [Frontend-Bundler](/docs/managing-resources/bundler/overview), kompiliert das Frontend, führt die Frontend-Tests aus, bedient die Entwicklungsüberwachung und bindet ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) an die App, die es startet.

## Hinzufügen des Plugins {#adding-the-plugin}

Ein webforJ-Projekt, das aus einem [Archetyp](/docs/introduction/getting-started) erstellt wurde, hat bereits das Plugin. Um es zu einem bestehenden Projekt hinzuzufügen:

<Tabs>
<TabItem value="maven" label="Maven">

Die Deklaration des Plugins mit `<extensions>true</extensions>` bindet seine Ziele an den Build, ohne Ausführungsblöcke schreiben zu müssen:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Fügen Sie das Plugin über eine `buildscript`-Classpath-Abhängigkeit hinzu und wenden Sie es an:

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

## Ziele und Aufgaben {#goals-and-tasks}

Drei Ziele sind an Phasen gebunden, die Sie bereits ausführen, sodass ein normales `mvn package` oder `./gradlew build` eine App mit integriertem Frontend produziert, und die Testphase führt die Frontend-Tests neben den Java-Tests aus. Die Überwachungsfunktion starten Sie manuell während der Entwicklung:

| Maven-Ziel | Gradle-Aufgabe | Wird ausgeführt | Was es macht |
|------------|----------------|-----------------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, vor jedem jar und war | Kompiliert das Frontend für die verpackte App |
| `test` | `webforjTest` | mit der Testphase | Führt die Frontend-Tests aus |
| `clean` | `webforjCleanFrontend` | mit der Bereinigungsphase | Entfernt das generierte Frontend |
| `watch` | `webforjWatch` | manuell, zusammen mit der App | Baut bei Änderungen während der Entwicklung neu auf |

Starten Sie die Überwachungsfunktion vor dem Ziel, das die App ausführt, z. B. `mvn compile webforj:watch spring-boot:run`. Ein Archetyp-Projekt legt dies als das Standardziel fest, sodass `mvn` alleine alles startet. Das Verhalten beim Neuladen wird in [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) behandelt.

Überspringen Sie die Frontend-Tests zusammen mit den Java-Tests, `-DskipTests` oder `-Dmaven.test.skip` mit Maven und `-PskipTests` mit Gradle.

## Optionen {#options}

Setzen Sie Optionen als Maven `<configuration>`-Elemente oder als Gradle `webforj { }`-Erweiterungswerte. Jede Maven-Option außer `plugins` und `hotswap` akzeptiert auch eine `-D`-Eigenschaft in der Befehlszeile. Die beiden Build-Tools spiegeln einander wider:

| Maven-Element | Maven-Eigenschaft | Gradle | Standard | Zweck |
|---------------|-------------------|--------|----------|-------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | verwaltet | Legen Sie die Bun-Version für reproduzierbare Builds fest |
| `bunPath` | `webforj.bundler.path` | `bunPath` | herunterladen | Verwenden Sie eine vorhandene Bun-Binärdatei anstelle von Herunterladen |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Wo verwaltete Bun-Binärdateien zwischengespeichert werden |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Wo die Frontend-Einstiegsquellen leben |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Wo das Plugin seine generierten Build-Dateien schreibt |
| `plugins` | — | `plugins` | — | Aktivieren oder deaktivieren Sie eine [Erweiterung](/docs/managing-resources/bundler/extensions/overview) nach ID, wie `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Paketpräfixe, die während des Annotation-Scans übersprungen werden sollen |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Laden Sie das gesamte Frontend beim Start der App anstelle pro Ansicht, siehe [Eager-Bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Zusätzliche Argumente, die an den Frontend-Test-Runner übergeben werden |
| `hotswap` | — | `hotswap` | — | Fügen Sie ein Werkzeug zum Aktualisieren der Klassen an die App an, die der Build startet, siehe [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Um beispielsweise die Bun-Version festzulegen und Tailwind zu aktivieren:

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
