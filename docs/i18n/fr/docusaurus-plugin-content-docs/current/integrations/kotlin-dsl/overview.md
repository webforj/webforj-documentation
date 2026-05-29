---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 4198ef6392f249bd21d0395c55b5817d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='25.12' />
<DocChip chip='experimental' />

webforJ fournit un [Kotlin](https://kotlinlang.org/) *Langage de Spécification de Domaine*, ou DSL, qui vous permet de construire des interfaces utilisateur avec une syntaxe concise et sûre pour le typage. Au lieu de code Java impératif, vous écrivez un code déclaratif qui se lit comme une description de la structure de votre interface utilisateur.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Nom");
name.setPlaceholder("Votre nom");
layout.add(name);

Button submit = new Button("Soumettre", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Nom", placeholder = "Votre nom")
  button("Soumettre", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

Le DSL tire parti des fonctions d'extension Kotlin, des lambdas avec receveurs et des paramètres par défaut pour créer une syntaxe de constructeur naturelle. Les composants s'imbriquent les uns dans les autres, la configuration se fait par blocs, et le compilateur détecte les erreurs structurelles avant l'exécution.

## Configuration {#setup}

<ExperimentalWarning />

Aucune installation Kotlin séparée n'est requise. Maven gère la compilation via le plugin Maven Kotlin, donc tout projet qui se construit déjà avec Maven peut ajouter le support Kotlin uniquement avec la configuration des dépendances et des plugins.

:::tip Démarrage rapide
Pour mettre en place un projet webforJ utilisant Kotlin avec toutes les configurations nécessaires prêtes à l'emploi, consultez [cette section sur l'utilisation du starter webforJ Kotlin](#kotlin-starter-project).
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

Si vous prévoyez d'écrire des tests en Kotlin, ajoutez également la dépendance de test Kotlin. Elle s'intègre avec JUnit :

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Plugin Maven Kotlin {#kotlin-maven-plugin}

Ajoutez le plugin Maven Kotlin pour compiler à la fois vos sources Kotlin et Java. La configuration `sourceDirs` ci-dessous permet aux fichiers Kotlin et Java de coexister dans le même projet :

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

Avec ces ajouts, `mvn compile` compile les sources Kotlin aux côtés de Java. Les fichiers Kotlin peuvent aller dans `src/main/kotlin` ou `src/main/java`, et le plugin gère les deux.

:::tip[Interopérabilité Java]
Kotlin compile en bytecode JVM, donc il fonctionne aux côtés du code Java existant. Vous pouvez utiliser des composites Kotlin construits avec DSL depuis des classes Java, imbriquer des composants Java standard à l'intérieur de blocs DSL avec `add()`, et mélanger des fichiers Kotlin et Java dans le même projet.
:::

### Projet starter Kotlin {#kotlin-starter-project}

Si vous préférez sauter la configuration manuelle, le [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) fournit un projet prêt à l'emploi avec toutes les dépendances et la configuration des plugins déjà en place. Clonez-le et commencez à construire avec le DSL tout de suite.

## Sujets {#topics}

Les sujets suivants couvrent l'utilisation du DSL, ainsi que son extension à tous les composants ou composites personnalisés que vous créez.

<DocCardList className="topics-section" />
