---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# webforJ Build-Plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Das webforJ-Build-Plugin führt die Build-Zeitarbeiten von webforJ als Teil Ihres Maven- oder Gradle-Builds aus. Sie fügen es einmal hinzu, und es bindet seine Ziele an die Phasen, die Sie bereits ausführen, ohne ein separates Frontend-Projekt zum Synchronisieren. Es steuert den [Frontend-Bundler](/docs/managing-resources/bundler/overview), kompilieren das Frontend, führt die Frontend-Tests aus, serviert den Entwicklungswatch und fügt ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) zu der Anwendung hinzu, die es startet.

## Hinzufügen des Plugins {#adding-the-plugin}

Ein webforJ-Projekt, das aus einem [Archetyp](/docs/introduction/getting-started) erstellt wurde, hat das Plugin bereits. Um es zu einem bestehenden Projekt hinzuzufügen:

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

## Ziele und Aufgaben {#goals-and-tasks}

Drei Ziele sind an Phasen gebunden, die Sie bereits ausführen, sodass ein normales `mvn package` oder `./gradlew build` eine Anwendung mit ihrem Frontend kompiliert, und die Testphase führt die Frontend-Tests zusammen mit den Java-Tests aus. Der Watch ist der, den Sie während der Entwicklung manuell starten:

| Maven-Ziel | Gradle-Aufgabe | Ausgeführt | Was es tut |
|------------|----------------|------------|------------|
| `bundle` | `webforjBundle` | `prepare-package`, vor jeder Jar- und War-Datei | Kompiliert das Frontend für die verpackte Anwendung |
| `test` | `webforjTest` | mit der Testphase | Führt die Frontend-Tests aus |
| `clean` | `webforjCleanFrontend` | mit der Clean-Phase | Entfernt das generierte Frontend |
| `watch` | `webforjWatch` | manuell, neben der Anwendung | Baut bei Änderungen während der Entwicklung neu |
| `push-keys` | `webforjPushKeys` | manuell, einmal pro Bereitstellung | Generiert das Schlüsselpaar für [Push-Benachrichtigungen](/docs/advanced/push-notifications) und druckt die Konfigurationszeilen aus |
| `bbj-setup` | Nur Maven | manuell, auf der BBj-Maschine | Installiert [BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin) im lokalen BBj und aktiviert seinen Bereitstellungspunkt |
| `bbj-package` | Nur Maven | mit der Paketphase, mit einem `<bbj>`-Block | Schreibt das [BBj-Bereitstellungsarchiv](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy` | Nur Maven | mit der Installationsphase, mit einem `<bbj>`-Block | [Stellt die Anwendung bereit](/docs/configuration/bbjship/deploying#deploy) bei BBjServices |
| `bbj-undeploy` | Nur Maven | manuell | [Entfernt die Anwendung](/docs/configuration/bbjship/deploying#undeploy) von BBjServices |

Starten Sie den Watch als Ziel vor demjenigen, das die Anwendung ausführt, z. B. `mvn compile webforj:watch spring-boot:run`. Ein Archetyp-Projekt legt dies als das Standardziel fest, sodass `mvn` allein alles startet. Das Verhalten beim Neuladen wird in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) behandelt.

Überspringen Sie die Frontend-Tests zusammen mit den Java-Tests, `-DskipTests` oder `-Dmaven.test.skip` mit Maven und `-PskipTests` mit Gradle.

## Optionen {#options}

Setzen Sie Optionen als Maven-`<configuration>`-Elemente oder als Gradle-`webforj { }`-Erweiterungswerte. Jede Maven-Option außer `plugins` und `hotswap` akzeptiert auch eine `-D`-Eigenschaft in der Befehlszeile. Die beiden Build-Tools spiegeln sich gegenseitig wider:

| Maven-Element | Maven-Eigenschaft | Gradle | Standard | Zweck |
|----------------|-------------------|--------|----------|-------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | verwaltet | Fixieren Sie die Bun-Version für reproduzierbare Builds |
| `bunPath` | `webforj.bundler.path` | `bunPath` | herunterladen | Verwenden Sie eine vorhandene Bun-Binärdatei, anstatt sie herunterzuladen |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Wo verwaltete Bun-Binärdateien zwischengespeichert werden |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Wo die Frontend-Eingangsquellen liegen |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Wo das Plugin seine generierten Build-Dateien schreibt |
| `plugins` | — | `plugins` | — | Schalten Sie eine [Erweiterung](/docs/managing-resources/bundler/extensions/overview) durch ID ein oder aus, z. B. `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Paketpräfixe, die während des Annotation Scans übersprungen werden sollen |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Laden Sie das gesamte Frontend beim Start der Anwendung anstelle von pro Ansicht, siehe [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Zusätzliche Argumente, die an den Frontend-Testläufer übergeben werden |
| `hotswap` | — | `hotswap` | — | Fügen Sie ein Klassenaktualisierungstool zu der Anwendung hinzu, die der Build startet, siehe [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Zum Beispiel, um die Bun-Version festzulegen und Tailwind zu aktivieren:

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
