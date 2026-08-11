---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 7cb4ddbb9aea86ff6f501296b42c5bbf
---
# plugin de build webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Le plugin de build webforJ exécute le travail de build de webforJ dans le cadre de votre build Maven ou Gradle. Vous l'ajoutez une fois, et il lie ses objectifs aux phases que vous exécutez déjà, sans projet frontend séparé à maintenir à jour. Il pilote le [bundler frontend](/docs/managing-resources/bundler/overview), compile le frontend, exécute les tests frontend, sert la surveillance en développement, et attache un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) à l'application qu'il démarre.

## Ajout du plugin {#adding-the-plugin}

Un projet webforJ créé à partir d'un [archétype](/docs/introduction/getting-started) a déjà le plugin. Pour l'ajouter à un projet existant :

<Tabs>
<TabItem value="maven" label="Maven">

Déclarer le plugin avec `<extensions>true</extensions>` lie ses objectifs au build sans blocs d'exécution à écrire :

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Ajoutez le plugin via une dépendance de classpath `buildscript` et appliquez-le :

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

## Objectifs et tâches {#goals-and-tasks}

Trois objectifs sont liés aux phases que vous exécutez déjà, donc un `mvn package` normal ou un `./gradlew build` produit une application avec son frontend compilé, et la phase de test exécute les tests frontend aux côtés des tests Java. La surveillance est celle que vous démarrez manuellement pendant le développement :

| Objectif Maven | Tâche Gradle | Exécute | Ce qu'il fait |
|----------------|--------------|---------|---------------|
| `bundle` | `webforjBundle` | `prepare-package`, avant chaque jar et war | Compile le frontend pour l'application empaquetée |
| `test` | `webforjTest` | avec la phase de test | Exécute les tests frontend |
| `clean` | `webforjCleanFrontend` | avec la phase de nettoyage | Supprime le frontend généré |
| `watch` | `webforjWatch` | manuellement, aux côtés de l'application | Reconstruit lors de modifications pendant le développement |

Démarrez la surveillance comme l'objectif avant celui qui exécute l'application, `mvn compile webforj:watch spring-boot:run` par exemple. Un projet archétype définit cela comme l'objectif par défaut, donc `mvn` seul démarre tout. Son comportement de rechargement est couvert dans [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Ignorez les tests frontend avec les tests Java, `-DskipTests` ou `-Dmaven.test.skip` avec Maven et `-PskipTests` avec Gradle.

## Options {#options}

Définissez les options comme éléments `<configuration>` de Maven, ou comme valeurs d'extension `webforj { }` de Gradle. Chaque option Maven sauf `plugins` et `hotswap` accepte également une propriété `-D` en ligne de commande. Les deux outils de build se reflètent mutuellement :

| Élément Maven | Propriété Maven | Gradle | Par défaut | But |
|---------------|-----------------|--------|------------|-----|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | géré | Fixez la version de Bun pour des builds reproductibles |
| `bunPath` | `webforj.bundler.path` | `bunPath` | téléchargement | Utilisez un binaire Bun existant au lieu de télécharger |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Où les binaires Bun gérés sont mis en cache |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Où se trouvent les sources d'entrée du frontend |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Où le plugin écrit ses fichiers de build générés |
| `plugins` | — | `plugins` | — | Activez ou désactivez une [extension](/docs/managing-resources/bundler/extensions/overview) par id, comme `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Préfixes de packages à ignorer pendant l'analyse des annotations |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Chargez l'ensemble du frontend au démarrage de l'application plutôt que par vue, voir [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Arguments supplémentaires passés au runner de tests frontend |
| `hotswap` | — | `hotswap` | — | Attachez un outil de mise à jour de classe à l'application que le build démarre, voir [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Par exemple, pour fixer la version de Bun et activer Tailwind :

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
