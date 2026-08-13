---
title: Spring Boot
sidebar_position: 30
description: >-
  Set up live reload in a Spring Boot webforJ app, with the development tools
  delivered by the webforJ build plugin.
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
Dans une application Spring Boot, le [plugin de construction webforJ](/docs/configuration/build-plugin) fournit les outils de développement pour les exécutions de développement. Le projet ne déclare aucune dépendance pour eux, et ils ne font jamais partie de l'application empaquetée.

## Exigences {#requirements}

La dépendance de démarrage et le plugin de construction. Un projet créé à partir d'un [archetype](/docs/introduction/getting-started) en a les deux.

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

avec le [plugin webforJ appliqué à la construction](/docs/configuration/build-plugin#adding-the-plugin).

</TabItem>
</Tabs>

## Activer le rechargement en direct {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Démarrez l'application comme d'habitude, `mvn` avec Maven ou `./gradlew bootRun` avec Gradle. Les modifications Java s'appliquent après une compilation, les modifications de feuille de style et d'image s'appliquent sur place, et les sources sous `src/main/frontend` se reconstruisent par le [watch frontend](/docs/configuration/deploy-reload/frontend-watch). Les autres clés sont listées dans les [paramètres](/docs/configuration/deploy-reload/overview#settings).

## Spring DevTools {#spring-devtools}

Spring DevTools est optionnel, le rechargement en direct fonctionne sans lui. Pour utiliser son modèle de redémarrage, ajoutez sa dépendance :

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

Avec Spring DevTools présent, un changement compilé redémarre le contexte Spring et le navigateur se rafraîchit lorsque le redémarrage est terminé. Avec un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) configuré également, l'outil applique les mises à jour de classe et le redémarrage reste désactivé.

## Builds de production {#production-builds}

`mvn package` et `./gradlew bootJar` produisent une application sans outils de développement, sans exclusion, profil ou propriété requise. La propriété `webforj.devtools.livereload.enabled` n'a aucun effet dans une application empaquetée.
