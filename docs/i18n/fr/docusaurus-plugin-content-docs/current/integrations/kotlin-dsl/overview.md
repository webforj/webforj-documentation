---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2c5f0dc99b29342a5ae0f1f4774d3a36
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ fournit un [Kotlin](https://kotlinlang.org/) *Langage de Spécialisation de Domaine*, ou DSL, qui vous permet de construire des interfaces utilisateur avec une syntaxe concise et sûre en termes de types. Au lieu d'un code Java impératif, vous écrivez un code déclaratif qui se lit comme une description de la structure de votre interface utilisateur.

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

Le DSL tire parti des fonctions d'extension de Kotlin, des lambdas avec des récepteurs et des paramètres par défaut pour créer une syntaxe de constructeur naturelle. Les composants s'imbriquent les uns dans les autres, la configuration se fait dans des blocs, et le compilateur détecte les erreurs structurelles avant l'exécution.

## Installation {#setup}

Aucune installation séparée de Kotlin n'est requise. Maven gère la compilation via le plugin Maven Kotlin, donc tout projet qui se construit déjà avec Maven peut ajouter le support de Kotlin avec une simple configuration de dépendance et de plugin.

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

Ajoutez le plugin Maven Kotlin pour compiler vos sources Kotlin et Java. La configuration `sourceDirs` ci-dessous permet aux fichiers Kotlin et Java de coexister dans le même projet :

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

Avec ces ajouts, `mvn compile` compile les sources Kotlin en parallèle des sources Java. Les fichiers Kotlin peuvent être placés dans `src/main/kotlin` ou `src/main/java`, et le plugin gère les deux.

:::tip[Interopérabilité Java]
Kotlin se compile en bytecode JVM, donc il fonctionne aux côtés du code Java existant. Vous pouvez utiliser des composites Kotlin construits avec le DSL depuis des classes Java, imbriquer des composants Java standard à l'intérieur des blocs du DSL avec `add()`, et mélanger des fichiers Kotlin et Java dans le même projet.
:::

## Thèmes {#topics}

Les sujets suivants couvrent l'utilisation du DSL, ainsi que son extension à tous les composants ou composites personnalisés que vous créez.

<DocCardList className="topics-section" />

## Kotlin pour les développeurs Java {#kotlin-for-java-developers}

<details>
<summary>Vous débutez avec Kotlin ? Voici quelques-unes des principales fonctionnalités du langage sur lesquelles le DSL s'appuie.</summary>

### Sécurité des nulls {#null-safety}

Kotlin distingue les types nullable et non-nullable au moment de la compilation :

```kotlin
// Java - toute référence peut être nulle
String name = null;

// Kotlin - nullabilité explicite
var name: String? = null        // Nullable, peut être nul
var safeName: String = "valeur" // Non-null, le compilateur impose cela

// Opérateur d'appel sécurisé - renvoie null si name est nul
println(name?.length)

// Opérateur Elvis - fournit une valeur par défaut quand nul
println(name ?: "default")
```

### Fonctions d'extension {#extension-functions}

Kotlin vous permet d'ajouter des méthodes aux classes existantes sans hébergement :

```kotlin
// Approche Java - classe utilitaire statique
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Bonjour");

// Approche Kotlin - fonction d'extension
fun String.addExclamation(): String = this + "!"
val result = "Bonjour".addExclamation()  // Se lit comme un appel de méthode
```

Le DSL utilise des fonctions d'extension pour ajouter des méthodes de constructeur aux composants.

### Lambdas et syntaxe de lambda de fin {#lambdas-and-trailing-lambda-syntax}

Les lambdas Kotlin sont plus concises que celles de Java, et lorsqu'une lambda est le dernier paramètre, elle peut être placée en dehors des parenthèses :

```kotlin
// Java
button.addClickListener(e -> System.out.println("Cliqué"));

// Kotlin - lambda comme dernier paramètre en dehors des parenthèses
button.onClick { println("Cliqué") }

// Avec un paramètre explicite
button.onClick { event -> println("Cliqué: $event") }
```

Cette syntaxe de lambda de fin est ce qui rend possible les blocs DSL.

### Paramètres par défaut {#default-parameters}

Les fonctions Kotlin peuvent avoir des valeurs par défaut pour les paramètres, réduisant le besoin de méthodes surchargées :

```kotlin
// Java - plusieurs constructeurs nécessaires
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - une fonction avec des valeurs par défaut
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
