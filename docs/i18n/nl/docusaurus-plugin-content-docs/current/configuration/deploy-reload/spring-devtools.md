---
title: Spring Boot
sidebar_position: 30
description: >-
  Set up live reload in a Spring Boot webforJ app, with the development tools
  delivered by the webforJ build plugin.
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
In een Spring Boot-app levert de [webforJ build-plugin](/docs/configuration/build-plugin) de ontwikkelingshulpmiddelen voor ontwikkelingsuitvoeringen. Het project verklaart geen afhankelijkheid voor deze hulpmiddelen en ze zijn nooit onderdeel van de verpakte app.

## Vereisten {#requirements}

De starterafhankelijkheid en de build-plugin. Een project dat is gemaakt vanuit een [archetype](/docs/introduction/getting-started) heeft beide.

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

met de [webforJ-plugin toegepast op de build](/docs/configuration/build-plugin#adding-the-plugin).

</TabItem>
</Tabs>

## Live reload inschakelen {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Start de app zoals gebruikelijk, `mvn` met Maven of `./gradlew bootRun` met Gradle. Java-wijzigingen worden toegepast na een compilatie, stylesheet- en afbeeldingswijzigingen worden ter plekke toegepast, en bronnen onder `src/main/frontend` worden opnieuw opgebouwd via de [frontend watch](/docs/configuration/deploy-reload/frontend-watch). De overige sleutels zijn vermeld in de [instellingen](/docs/configuration/deploy-reload/overview#settings).

## Spring DevTools {#spring-devtools}

Spring DevTools is optioneel, live reload werkt er zonder. Om het herstartmodel te gebruiken, voeg je de afhankelijkheid toe:

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

Met Spring DevTools aanwezig, herstart een gecompileerde wijziging de Spring-context en wordt de browser vernieuwd wanneer de herstart is voltooid. Met een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) ook geconfigureerd, worden de klasse-updates toegepast en blijft de herstart uitgeschakeld.

## Productiebouw {#production-builds}

`mvn package` en `./gradlew bootJar` produceren een app zonder ontwikkelingshulpmiddelen, zonder dat uitsluiting, profiel of eigenschap vereist is. De eigenschap `webforj.devtools.livereload.enabled` heeft geen effect in een verpakte app.
