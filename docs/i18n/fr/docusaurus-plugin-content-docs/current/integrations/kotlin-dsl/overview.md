---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 21ef4feee90e5c4f6827a48ce1755d0b
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

webforJ fournit un [Kotlin](https://kotlinlang.org/) *Langage Spécifique au Domaine*, ou DSL, qui vous permet de créer des interfaces utilisateur avec une syntaxe concise et sécurisée. Au lieu d'écrire du code Java impératif, vous écrivez un code déclaratif qui se lit comme une description de la structure de votre interface utilisateur.

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

Le DSL tire parti des fonctions d'extension Kotlin, des lambdas avec récepteurs et des paramètres par défaut pour créer une syntaxe de constructeur naturelle. Les composants s'imbriquent les uns dans les autres, la configuration se fait dans des blocs et le compilateur détecte les erreurs structurelles avant l'exécution.

## Configuration {#setup}

:::warning fonction expérimentale
Cette fonction est encore en développement actif.
L'API peut changer dans les versions futures, y compris des changements potentiellement disruptifs.

Vous êtes invité à l'essayer et à partager vos retours. Vos commentaires aideront à façonner le design final.
:::

Aucune installation Kotlin séparée n'est requise. Maven gère la compilation via le plugin Maven Kotlin, donc tout projet qui se construit déjà avec Maven peut ajouter le support Kotlin avec la seule configuration de dépendance et de plugin.

:::tip Démarrage rapide
Pour démarrer un projet webforJ utilisant Kotlin avec toutes les configurations nécessaires dès le départ, consultez [cette section sur l'utilisation du starter webforJ Kotlin](#kotlin-starter-project).
:::

### Dépendances {#dependencies}

Ajoutez le module webforJ Kotlin DSL et la bibliothèque standard Kotlin à votre `pom.xml`:

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

Si vous prévoyez d'écrire des tests en Kotlin, ajoutez également la dépendance de test Kotlin. Elle s'intègre avec JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Plugin Maven Kotlin {#kotlin-maven-plugin}

Ajoutez le plugin Maven Kotlin pour compiler vos sources Kotlin et Java. La configuration `sourceDirs` ci-dessous permet aux fichiers Kotlin et Java de coexister dans le même projet:

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

### Projet de démarrage Kotlin {#kotlin-starter-project}

Si vous préférez sauter la configuration manuelle, le [WebforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) fournit un projet prêt à l'emploi avec toutes les dépendances et configurations de plugins déjà en place. Clonez-le et commencez à construire avec le DSL dès que possible.

## Sujets {#topics}

Les sujets suivants couvrent l'utilisation du DSL, ainsi que son extension à tous les composants ou composites personnalisés que vous créez.

<DocCardList className="topics-section" />

## Kotlin pour les développeurs Java {#kotlin-for-java-developers}

<details>
<summary>Vous débutez avec Kotlin ? Voici quelques-unes des principales fonctionnalités du langage sur lesquelles le DSL repose.</summary>

### Sécurité nulle {#null-safety}

Kotlin distingue les types nullable et non-nullable au moment de la compilation:

```kotlin
// Java - toute référence peut être nulle
String name = null;

// Kotlin - nullabilité explicite
var name: String? = null        // Nullable, peut être nul
var safeName: String = "value"  // Non-null, le compilateur impose cela

// Opérateur d'appel sécurisé - retourne null si name est nul
println(name?.length)

// Opérateur Elvis - fournit un défaut quand nul
println(name ?: "default")
```

### Fonctions d'extension {#extension-functions}

Kotlin vous permet d'ajouter des méthodes aux classes existantes sans héritage:

```kotlin
// Approche Java - classe utilitaire statique
public class StringUtils {
  public static String addExclamation(String input) {
    return input + "!";
  }
}
String result = StringUtils.addExclamation("Hello");

// Approche Kotlin - fonction d'extension
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // Se lit comme un appel de méthode
```

Le DSL utilise des fonctions d'extension pour ajouter des méthodes de construction aux composants.

### Lambdas et syntaxe lambda finale {#lambdas-and-trailing-lambda-syntax}

Les lambdas Kotlin sont plus concises que celles de Java, et lorsqu'une lambda est le dernier paramètre, elle peut sortir des parenthèses:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Cliqué"));

// Kotlin - lambda comme dernier paramètre sort des parenthèses
button.onClick { println("Cliqué") }

// Avec paramètre explicite
button.onClick { event -> println("Cliqué : $event") }
```

Cette syntaxe de lambda finale permet de rendre possibles les blocs de DSL.

### Paramètres par défaut {#default-parameters}

Les fonctions Kotlin peuvent avoir des valeurs de paramètre par défaut, ce qui réduit le besoin de méthodes surchargées:

```kotlin
// Java - plusieurs constructeurs nécessaires
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - une fonction avec des défauts
fun button(
  text: String = "",
  theme: ButtonTheme = ButtonTheme.DEFAULT,
  block: Button.() -> Unit = {}
): Button
```

</details>
