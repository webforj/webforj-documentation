---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 09a13bb6da32b3c4c0e77d4e44c1acb4
---
# Plugin de construction webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Le plugin de construction webforJ exécute le travail de construction de webforJ comme partie de votre construction Maven ou Gradle. Vous l'ajoutez une fois, et il attache ses objectifs aux phases que vous exécutez déjà, sans projet frontend séparé à synchroniser. Il pilote le [regroupement frontend](/docs/managing-resources/bundler/overview), compilant le frontend, exécutant les tests frontend, servant le développement en veille, et attachant un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) à l'application qu'il démarre.

## Ajout du plugin {#adding-the-plugin}

Un projet webforJ créé à partir d'un [archétype](/docs/introduction/getting-started) possède déjà le plugin. Pour l'ajouter à un projet existant :

<Tabs>
<TabItem value="maven" label="Maven">

Déclarer le plugin avec `<extensions>true</extensions>` lie ses objectifs à la construction sans blocs d'exécution à écrire :

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

Trois objectifs sont liés aux phases que vous exécutez déjà, donc un `mvn package` normal ou `./gradlew build` produit une application avec son frontend compilé, et la phase de test exécute les tests frontend aux côtés des tests Java. La veille est celle que vous démarrez manuellement pendant le développement :

| Objectif Maven | Tâche Gradle | Exécute | Ce qu'elle fait |
|----------------|--------------|---------|-----------------|
| `bundle` | `webforjBundle` | `prepare-package`, avant chaque jar et war | Compile le frontend pour l'application empaquetée |
| `test` | `webforjTest` | avec la phase de test | Exécute les tests frontend |
| `clean` | `webforjCleanFrontend` | avec la phase de nettoyage | Retire le frontend généré |
| `watch` | `webforjWatch` | manuellement, à côté de l'application | Reconstruit lors des changements pendant le développement |
| `push-keys` | `webforjPushKeys` | manuellement, une fois par déploiement | Génère la paire de clés pour les [notifications push](/docs/advanced/push-notifications) et imprime les lignes de configuration |

Démarrez la veille comme l'objectif avant celui qui exécute l'application, par exemple `mvn compile webforj:watch spring-boot:run`. Un projet archétype définit cela comme l'objectif par défaut, donc `mvn` seul démarre tout. Son comportement de rechargement est couvert dans [Veille Frontend](/docs/configuration/deploy-reload/frontend-watch).

Sauter les tests frontend avec les tests Java, `-DskipTests` ou `-Dmaven.test.skip` avec Maven et `-PskipTests` avec Gradle.

## Options {#options}

Définissez les options en tant qu'éléments `<configuration>` Maven, ou en tant que valeurs d'extension `webforj { }` Gradle. Chaque option Maven, sauf `plugins` et `hotswap`, accepte également une propriété `-D` dans la ligne de commande. Les deux outils de construction se reflètent l'un l'autre :

| Élement Maven | Propriété Maven | Gradle | Défaut | But |
|---------------|-----------------|--------|--------|-----|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | géré | Fixez la version de Bun pour des constructions reproductibles |
| `bunPath` | `webforj.bundler.path` | `bunPath` | téléchargement | Utilisez un binaire Bun existant au lieu de télécharger |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Où les binaires Bun gérés sont mis en cache |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Où se trouvent les sources d'entrée du frontend |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Où le plugin écrit ses fichiers de construction générés |
| `plugins` | — | `plugins` | — | Activer ou désactiver une [extension](/docs/managing-resources/bundler/extensions/overview) par id, comme `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Préfixes de package à sauter lors du scan des annotations |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Charger tout le frontend au démarrage de l'application au lieu de par vue, voir [Bundle Éager](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Arguments supplémentaires passés au coureur de tests frontend |
| `hotswap` | — | `hotswap` | — | Attacher un outil de mise à jour de classe à l'application que la construction démarre, voir [Hotswap](/docs/configuration/deploy-reload/hotswap) |

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
