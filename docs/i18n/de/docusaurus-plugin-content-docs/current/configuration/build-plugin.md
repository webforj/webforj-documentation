---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 0c02e741918864a34c35227387259b40
---
# webforJ-Baustein-Plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Das webforJ-Baustein-Plugin führt die Build-Zeit-Arbeiten von webforJ als Teil Ihres Maven- oder Gradle-Builds aus. Sie fügen es einmal hinzu, und es bindet seine Ziele an die Phasen, die Sie bereits ausführen, ohne ein separates Frontend-Projekt synchron halten zu müssen. Es steuert den [Frontend-Bundler](/docs/managing-resources/bundler/overview), kompiliert das Frontend, führt die Frontend-Tests aus und bedient die Entwicklungsüberwachung.

## Hinzufügen des Plugins {#adding-the-plugin}

Ein aus einem [Archetyp](/docs/introduction/getting-started) erstelltes webforJ-Projekt hat bereits das Plugin. Um es zu einem bestehenden Projekt hinzuzufügen:

<Tabs>
<TabItem value="maven" label="Maven">

Die Deklaration des Plugins mit `<extensions>true</extensions>` bindet seine Ziele an den Build, ohne dass Ausführungsblöcke geschrieben werden müssen:

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

## Ziele {#goals}

Das Plugin bindet vier Ziele, jedes an eine Phase, die Sie bereits ausführen, sodass ein normales `mvn package` oder `gradle build` eine App mit dem kompilierten Frontend erzeugt, und `mvn test` die Frontend-Tests zusammen mit den Java-Tests ausführt.

| Maven-Ziel | Gradle-Aufgabe | Phase | Was es tut |
|------------|----------------|-------|------------|
| `bundle` | `webforjBundle` | `prepare-package` | Kompiliert das Frontend für die Produktion |
| `test` | `webforjTest` | `test` | Führt die Frontend-Tests aus |
| `clean` | `webforjCleanFrontend` | `clean` | Entfernt das generierte Frontend |
| `watch` | `webforjWatch` | manuell ausführen | Baut bei Änderungen während der Entwicklung neu |

Das `watch`-Ziel ist das, das Sie während der Entwicklung manuell ausführen, zusammen mit der App. Das Nachladeverhalten wird in [Frontend-Watch](/docs/configuration/deploy-reload/frontend-watch) behandelt.

## Optionen {#options}

Setzen Sie Optionen als Maven `<configuration>` (oder `-D`-Eigenschaften in der Befehlszeile) und als Gradle `webforj { }`-Erweiterungswerte. Die beiden Build-Tools spiegeln sich gegenseitig.

| Option | Maven-Eigenschaft | Gradle | Standardwert | Zweck |
|--------|-------------------|--------|--------------|-------|
| Bun-Version | `webforj.bundler.version` | `bunVersion` | verwaltet | Legt die Bun-Version für reproduzierbare Builds fest |
| Bun-Binärdatei | `webforj.bundler.path` | `bunPath` | herunterladen | Verwendet eine vorhandene Bun-Binärdatei anstelle des Herunterladens |
| Cache-Verzeichnis | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Wo verwaltete Bun-Binärdateien zwischengespeichert werden |
| Quellverzeichnis | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Wo sich die Frontend-Einstiegspunkte befinden |
| Arbeitsverzeichnis | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Wo das Plugin seine generierten Build-Dateien schreibt |
| Erweiterungen | `plugins` | `plugins` | — | Aktivieren oder Deaktivieren einer [Erweiterung](/docs/managing-resources/bundler/extensions/overview) nach ID, wie `webforj-tailwind` |
| Pakete ausschließen | `webforj.bundler.excludePackages` | `excludePackages` | — | Paketpräfixe, die während des Annotation-Scans übersprungen werden sollen |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Lädt das gesamte Frontend beim Start der App statt pro Ansicht, siehe [Eager-Bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Testargumente | `webforj.bundler.testArgs` | `testArgs` | — | Zusätzliche Argumente, die an den Frontend-Test-Runner übergeben werden |
| Tests überspringen | `skipTests`, `maven.test.skip` | — | `false` | Überspringt die Frontend-Tests |

Zum Beispiel, um die Bun-Version festzulegen und Tailwind zu aktivieren:

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
