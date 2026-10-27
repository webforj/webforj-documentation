---
title: Projets Existants
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
Un projet qui se déploie avec le plugin d'installation obsolète webforJ passe à BBjShip avec une exécution d'OpenRewrite. L'exécution modifie le `pom.xml` et le `webforj.conf` du projet. Le code Java, les feuilles de style et les ressources restent inchangés.

## Migrer avec OpenRewrite {#migrate-with-openrewrite}

Ajoutez le plugin Maven OpenRewrite à votre `pom.xml`, avec la recette webforJ 26.03 :

```xml title="pom.xml"
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>com.webforj.rewrite.v26.UpgradeWebforj_26_03</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>26.03</version>
    </dependency>
  </dependencies>
</plugin>
```

Exécutez la migration, puis examinez ce qui a changé :

```bash
mvn rewrite:run
git diff
```

Le projet migré se déploie avec BBjShip. `mvn install` déploie l'application, et `mvn` démarre une [exécution de développement](/docs/configuration/deploy-reload/bbjservices). Retirez ensuite le plugin OpenRewrite de votre `pom.xml`. [Mises à jour automatisées](/docs/upgrading/automated-upgrades#previewing-changes) montre comment prévisualiser les changements avant l'exécution.

## Ce que la migration change {#what-the-migration-changes}

### Le plugin de construction {#the-build-plugin}

Le plugin d'installation devient le [plugin de construction webforJ](/docs/configuration/build-plugin) avec `<extensions>true</extensions>`, donc `mvn install` déploie sans exécution. Ses paramètres passent dans le bloc `<bbj>` :

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
  <configuration>
    <bbj>
      <publishName>inventory</publishName>
      <deployment>
        <url>http://localhost:8888/webforj-bbjship</url>
      </deployment>
    </bbj>
  </configuration>
</plugin>
```

| Plugin d'installation | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, avec le chemin `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` dans `webforj.conf` |
| `<debug>` | `webforj.debug` dans `webforj.conf` |

[Déploiement](/docs/configuration/bbjship/deploying#settings) énumère chaque paramètre du bloc `<bbj>`.

### Le moteur webforJ {#the-webforj-engine}

BBjServices fournit le moteur webforJ, donc votre application ne doit pas avoir le sien. La dépendance `webforj` exclut déjà `webforj-engine`. La migration la déclare à nouveau dans la portée `provided` afin que votre IDE continue de compiler contre :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### Le fichier de configuration {#webforjconf}

La classe d'entrée et le drapeau de débogage passent du plugin d'installation au `webforj.conf`, à côté des paramètres avec lesquels une application BBjShip fonctionne :

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` sert les fichiers statiques `ws://` et les icônes `icons://` de l'application. Sans cela, une application qui utilise `icons://` échoue à démarrer dans BBjServices. Les clés que le fichier définit restent inchangées.

### Exécutions de développement {#development-runs}

La migration ajoute HotswapAgent à la configuration du plugin de construction :

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Elle définit également `compile webforj:watch` comme objectif par défaut de la construction, donc `mvn` exécute l'application dans les BBjServices de votre propre machine :

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

Consultez [BBjServices](/docs/configuration/deploy-reload/bbjservices) dans le redéploiement et le rechargement en direct.

## Sans le plugin d'installation {#without-the-install-plugin}

Un projet qui n'a jamais utilisé le plugin d'installation obtient la même configuration manuellement : le [plugin de construction](#the-build-plugin) avec un bloc `<bbj>`, l' [exclusion et la déclaration `provided` de `webforj-engine`](#the-webforj-engine), et [`webforj.conf`](#webforjconf) avec `webforj.assetsSource = packaged`. Le projet utilise un emballage `jar`, et la dépendance `webforj` exclut `webforj-engine` :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj</artifactId>
  <version>${webforj.version}</version>
  <exclusions>
    <exclusion>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-engine</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
