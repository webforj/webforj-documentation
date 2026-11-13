---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Build webforJ UIs declaratively with the Kotlin DSL, a preview feature, using
  type-safe builders, lambdas with receivers, and nested blocks.
_i18n_hash: 5beb40faf3cca20567062bc93ef15e2b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# DSL Kotlin <DocChip chip='since' label='25.12' /> <DocChip chip='experimental' />
<!-- vale on -->

webforJ fournit un *Domain Specific Language* (langage spécifique à un domaine), ou DSL, pour [Kotlin](https://kotlinlang.org/) qui vous permet de créer des interfaces utilisateur avec une syntaxe concise et sûre du point de vue des types. Au lieu d’écrire du code Java impératif, vous écrivez du code déclaratif qui décrit la structure de votre interface utilisateur.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Name");
name.setPlaceholder("Your name");
layout.add(name);

Button submit = new Button("Submit", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Name", placeholder = "Your name")
  button("Submit", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

Le DSL tire parti des fonctions d’extension de Kotlin, des lambdas avec récepteur et des paramètres par défaut pour créer une syntaxe de construction naturelle. Les composants s’imbriquent les uns dans les autres, la configuration s’effectue dans des blocs, et le compilateur détecte les erreurs structurelles avant l’exécution.

## Configuration {#setup}

<ExperimentalWarning />

Aucune installation distincte de Kotlin n’est nécessaire. Maven gère la compilation via le plugin Kotlin Maven. Tout projet qui utilise déjà Maven pour sa compilation peut donc ajouter la prise en charge de Kotlin en configurant uniquement les dépendances et le plugin.

:::tip Démarrage rapide
Pour démarrer rapidement un projet webforJ avec Kotlin et toutes les configurations nécessaires déjà en place, consultez [cette section sur l’utilisation du starter Kotlin webforJ](#kotlin-starter-project).
:::

### Dépendances {#dependencies}

Ajoutez le module DSL Kotlin de webforJ et la bibliothèque standard Kotlin à votre `pom.xml` :

```xml
<dependency>
  <groupId>com.webforj.kotlin</groupId>
  <artifactId>webforj-kotlin</artifactId>
  <version>${webforj.version}</version>
</dependency>

<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-stdlib-jdk8</artifactId>
  <version>${kotlin.version}</version>
</dependency>
```

Si vous prévoyez d’écrire des tests en Kotlin, ajoutez également la dépendance de test Kotlin. Elle s’intègre à JUnit :

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Plugin Kotlin Maven {#kotlin-maven-plugin}

Ajoutez le plugin Kotlin Maven pour compiler vos sources Kotlin et Java. La configuration `sourceDirs` ci-dessous permet aux fichiers Kotlin et Java de coexister dans le même projet :

```xml
<plugin>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-maven-plugin</artifactId>
  <version>${kotlin.version}</version>
  <executions>
    <execution>
      <id>compile</id>
      <phase>compile</phase>
      <goals>
        <goal>compile</goal>
      </goals>
      <configuration>
        <sourceDirs>
          <sourceDir>src/main/java</sourceDir>
          <sourceDir>target/generated-sources/annotations</sourceDir>
          <sourceDir>src/main/kotlin</sourceDir>
        </sourceDirs>
      </configuration>
    </execution>
    <execution>
      <id>test-compile</id>
      <phase>test-compile</phase>
      <goals>
        <goal>test-compile</goal>
      </goals>
      <configuration>
        <sourceDirs>
          <sourceDir>src/test/java</sourceDir>
          <sourceDir>target/generated-test-sources/test-annotations</sourceDir>
          <sourceDir>src/test/kotlin</sourceDir>
        </sourceDirs>
      </configuration>
    </execution>
  </executions>
  <configuration>
    <jvmTarget>${maven.compiler.target}</jvmTarget>
  </configuration>
</plugin>
```

Avec ces ajouts, `mvn compile` compile les sources Kotlin en même temps que celles de Java. Les fichiers Kotlin peuvent être placés dans `src/main/kotlin` ou `src/main/java`, et le plugin gère les deux.

:::tip[Interopérabilité avec Java]
Kotlin est compilé en bytecode JVM et fonctionne donc avec le code Java existant. Vous pouvez utiliser depuis des classes Java des composites créés avec le DSL Kotlin, imbriquer des composants Java standards dans des blocs DSL avec `add()` et mélanger des fichiers Kotlin et Java dans le même projet.
:::

### Projet starter Kotlin {#kotlin-starter-project}

Si vous préférez éviter la configuration manuelle, le dépôt [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) fournit un projet prêt à l’emploi, avec toutes les dépendances et la configuration du plugin déjà en place. Clonez-le et commencez immédiatement à créer avec le DSL.

## Sujets {#topics}

Les sujets suivants expliquent comment utiliser le DSL et l’étendre à vos composants personnalisés ou composites.

<DocCardList className="topics-section" />
