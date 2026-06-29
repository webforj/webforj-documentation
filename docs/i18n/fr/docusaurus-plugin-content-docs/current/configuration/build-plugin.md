---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 0c02e741918864a34c35227387259b40
---
# plugin de build webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Le plugin de build webforJ exécute le travail de build de webforJ dans le cadre de votre build Maven ou Gradle. Vous l'ajoutez une fois, et il lie ses objectifs aux phases que vous exécutez déjà, sans projet frontend séparé à maintenir à jour. Il pilote le [regroupement frontend](/docs/managing-resources/bundler/overview), compilant le frontend, exécutant les tests frontend et servant le mode développement.

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

Ajoutez le plugin via une dépendance de classe `buildscript` et appliquez-le :

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

## Objectifs {#goals}

Le plugin lie quatre objectifs, chacun à une phase que vous exécutez déjà, de sorte qu'un `mvn package` ou `gradle build` produit une application avec son frontend compilé, et `mvn test` exécute les tests frontend aux côtés des tests Java.

| Objectif Maven | Tâche Gradle | Phase | Que fait-il |
|----------------|--------------|-------|-------------|
| `bundle` | `webforjBundle` | `prepare-package` | Compile le frontend pour la production |
| `test` | `webforjTest` | `test` | Exécute les tests frontend |
| `clean` | `webforjCleanFrontend` | `clean` | Supprime le frontend généré |
| `watch` | `webforjWatch` | exécuté manuellement | Reconstruit lors des changements pendant le développement |

L'objectif `watch` est celui que vous exécutez manuellement pendant le développement, aux côtés de l'application. Son comportement de rechargement est couvert dans [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Options {#options}

Définissez les options comme `<configuration>` Maven (ou propriétés `-D` en ligne de commande), et comme valeurs d'extension `webforj { }` Gradle. Les deux outils de build se reflètent mutuellement.

| Option | Propriété Maven | Gradle | Par défaut | But |
|--------|-----------------|--------|------------|-----|
| Version de Bun | `webforj.bundler.version` | `bunVersion` | géré | Fixez la version de Bun pour des builds reproductibles |
| Binaire de Bun | `webforj.bundler.path` | `bunPath` | télécharger | Utilisez un binaire Bun existant au lieu de télécharger |
| Répertoire de cache | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Où les binaires Bun gérés sont mis en cache |
| Racine de source | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Où résident les sources d'entrée du frontend |
| Répertoire de travail | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Où le plugin écrit ses fichiers de build générés |
| Extensions | `plugins` | `plugins` | — | Activez ou désactivez une [extension](/docs/managing-resources/bundler/extensions/overview) par id, comme `webforj-tailwind` |
| Exclure des paquets | `webforj.bundler.excludePackages` | `excludePackages` | — | Préfixes de paquets à ignorer lors de l'analyse des annotations |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Chargez l'ensemble du frontend au démarrage de l'application au lieu par vue, voir [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Arguments de test | `webforj.bundler.testArgs` | `testArgs` | — | Arguments supplémentaires passés au runner de tests frontend |
| Ignorer les tests | `skipTests`, `maven.test.skip` | — | `false` | Ignorez les tests frontend |

Par exemple, pour fixer la version de Bun et activer Tailwind :

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
