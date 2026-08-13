---
title: Spring Boot
sidebar_position: 30
description: >-
  Set up live reload in a Spring Boot webforJ app, with the development tools
  delivered by the webforJ build plugin.
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
In einer Spring Boot-App liefert das [webforJ-Build-Plugin](/docs/configuration/build-plugin) die Entwicklungstools für Entwicklungsdurchläufe. Das Projekt erklärt keine Abhängigkeit dafür, und sie sind niemals Teil der verpackten App.

## Anforderungen {#requirements}

Die Starter-Abhängigkeit und das Build-Plugin. Ein aus einem [Archetyp](/docs/introduction/getting-started) erstelltes Projekt verfügt über beides.

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
```

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-spring-boot-starter'
}
```

mit dem [webforJ-Plugin, das auf den Build angewendet wird](/docs/configuration/build-plugin#adding-the-plugin).

</TabItem>
</Tabs>

## Live-Reload aktivieren {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Starten Sie die App wie gewohnt, `mvn` mit Maven oder `./gradlew bootRun` mit Gradle. Java-Änderungen werden nach einer Kompilierung angewendet, Stylesheet- und Bildänderungen erfolgen sofort, und die Quellen in `src/main/frontend` werden über das [Frontend-Watch](/docs/configuration/deploy-reload/frontend-watch) neu kompiliert. Die verbleibenden Schlüssel sind in den [Einstellungen](/docs/configuration/deploy-reload/overview#settings) aufgeführt.

## Spring DevTools {#spring-devtools}

Spring DevTools ist optional, Live-Reload funktioniert auch ohne. Um das Neustartmodell zu verwenden, fügen Sie seine Abhängigkeit hinzu:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
}
```

</TabItem>
</Tabs>

Mit vorhandenem Spring DevTools startet eine kompilierte Änderung den Spring-Kontext neu, und der Browser wird aktualisiert, wenn der Neustart abgeschlossen ist. Mit einem [Hot-Swap-Tool](/docs/configuration/deploy-reload/hotswap), das ebenfalls konfiguriert ist, werden die Klassenuploads angewendet und der Neustart bleibt deaktiviert.

## Produktionsbuilds {#production-builds}

`mvn package` und `./gradlew bootJar` erzeugen eine App ohne Entwicklungstools, ohne dass eine Ausschluss-, Profil- oder Eigenschaft erforderlich ist. Die `webforj.devtools.livereload.enabled`-Eigenschaft hat in einer verpackten App keine Wirkung.
