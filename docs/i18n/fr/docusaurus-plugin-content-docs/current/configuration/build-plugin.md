---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# Plugin de construction webforJ <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

Le plugin de construction webforJ exécute le travail de temps de construction de webforJ dans le cadre de votre construction Maven ou Gradle. Vous l'ajoutez une fois, et il lie ses objectifs aux phases que vous exécutez déjà, sans projet frontal séparé à synchroniser. Il pilote le [regroupement frontal](/docs/managing-resources/bundler/overview), compilant le frontend, exécutant les tests frontend, servant le développement en mode surveillance, et attachant un [outil de hot-swap](/docs/configuration/deploy-reload/hotswap) à l'application qu'il démarre.

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

Ajoutez le plugin via une dépendance classpath `buildscript` et appliquez-le :

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

Trois objectifs sont liés aux phases que vous exécutez déjà, donc un `mvn package` normal ou `./gradlew build` produit une application avec son frontend compilé, et la phase de test exécute les tests frontend aux côtés des tests Java. La surveillance est celle que vous démarrez manuellement pendant le développement :

| Objectif Maven | Tâche Gradle | Exécution | Ce qu'elle fait |
|----------------|--------------|-----------|-----------------|
| `bundle`       | `webforjBundle` | `prepare-package`, avant chaque jar et war | Compile le frontend pour l'application empaquetée |
| `test`         | `webforjTest` | avec la phase de test | Exécute les tests frontend |
| `clean`        | `webforjCleanFrontend` | avec la phase de nettoyage | Supprime le frontend généré |
| `watch`        | `webforjWatch` | manuellement, aux côtés de l'application | Reconstruit lors de changements pendant le développement |
| `push-keys`    | `webforjPushKeys` | manuellement, une fois par déploiement | Génère la paire de clés pour [notifications push](/docs/advanced/push-notifications) et imprime les lignes de configuration |
| `bbj-setup`    | Maven uniquement | manuellement, sur la machine BBj | Installe [BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin) dans le local BBj et active son point de déploiement |
| `bbj-package`  | Maven uniquement | avec la phase de paquet, avec un bloc `<bbj>` | Écrit l'[archive de déploiement BBj](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy`   | Maven uniquement | avec la phase d'installation, avec un bloc `<bbj>` | [Déploie l'application](/docs/configuration/bbjship/deploying#deploy) vers BBjServices |
| `bbj-undeploy` | Maven uniquement | manuellement | [Supprime l'application](/docs/configuration/bbjship/deploying#undeploy) de BBjServices |

Démarrez la surveillance comme objectif avant celui qui exécute l'application, par exemple `mvn compile webforj:watch spring-boot:run`. Un projet archétype définit cela comme l'objectif par défaut, donc `mvn` seul démarre tout. Son comportement de rechargement est couvert dans [Surveillance Frontend](/docs/configuration/deploy-reload/frontend-watch).

Sautez les tests frontend avec les tests Java, en utilisant `-DskipTests` ou `-Dmaven.test.skip` avec Maven et `-PskipTests` avec Gradle.

## Options {#options}

Définissez les options comme éléments `<configuration>` de Maven, ou comme valeurs d'extension `webforj { }` de Gradle. Chaque option Maven, sauf `plugins` et `hotswap`, accepte également une propriété `-D` en ligne de commande. Les deux outils de construction se reflètent mutuellement :

| Élément Maven | Propriété Maven | Gradle | Par défaut | But |
|---------------|-----------------|--------|------------|-----|
| `bunVersion`  | `webforj.bundler.version` | `bunVersion` | géré | Fixer la version de Bun pour des constructions reproductibles |
| `bunPath`     | `webforj.bundler.path` | `bunPath` | téléchargement | Utiliser un binaire Bun existant au lieu de le télécharger |
| `cacheDir`    | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Emplacement où les binaires Bun gérés sont mis en cache |
| `sourceRoot`  | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Emplacement où se trouvent les sources d'entrée du frontend |
| `workDir`     | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Emplacement où le plugin écrit ses fichiers de construction générés |
| `plugins`     | —                | `plugins` | — | Activer ou désactiver une [extension](/docs/managing-resources/bundler/extensions/overview) par id, comme `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Préfixes de paquet à ignorer lors de l'analyse des annotations |
| `eager`       | `webforj.bundler.eager` | `eager` | `false` | Charger l'ensemble du frontend au démarrage de l'application au lieu de par vue, voir [Bundle impatient](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs`    | `webforj.bundler.testArgs` | `testArgs` | — | Arguments supplémentaires passés au moteur de test frontend |
| `hotswap`     | —                | `hotswap` | — | Attacher un outil de mise à jour de classe à l'application que la construction démarre, voir [Hotswap](/docs/configuration/deploy-reload/hotswap) |

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
