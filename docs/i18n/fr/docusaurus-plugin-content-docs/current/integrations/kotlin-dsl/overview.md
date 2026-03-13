---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b27e06c94bdd94dd90f7411523e442f5
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

webforJ fournit un [Kotlin](https://kotlinlang.org/) *Langage Spécifique au Domaine*, ou DSL, qui vous permet de construire des interfaces utilisateur avec une syntaxe concise et sûre en types. Au lieu d'utiliser le code Java impératif, vous écrivez du code déclaratif qui se lit comme une description de la structure de votre interface utilisateur.

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

Le DSL tire parti des fonctions d'extension Kotlin, des lambdas avec récepteurs et des paramètres par défaut pour créer une syntaxe de constructeur naturelle. Les composants s'imbriquent les uns dans les autres, la configuration se fait en blocs, et le compilateur détecte les erreurs structurelles avant l'exécution.

## Configuration {#setup}

:::warning fonctionnalité expérimentale
Cette fonctionnalité est encore en cours de développement actif.
L'API peut changer dans les futures versions, y compris des changements potentiellement disruptifs.

Vous êtes invités à l'essayer et à partager vos retours. Vos commentaires aideront à façonner le design final.
:::

Aucune installation Kotlin séparée n'est requise. Maven gère la compilation via le plugin Maven Kotlin, donc tout projet qui compile déjà avec Maven peut ajouter le support Kotlin avec configuration de dépendance et de plugin seule.

### Dépendances {#dependencies}

Ajoutez le module Kotlin DSL de webforJ et la bibliothèque standard Kotlin à votre `pom.xml` :

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

Avec ces ajouts, `mvn compile` compile les sources Kotlin aux côtés de Java. Les fichiers Kotlin peuvent aller dans `src/main/kotlin` ou `src/main/java`, et le plugin gère les deux.

:::tip[Interopérabilité Java]
Kotlin se compile en bytecode JVM, donc il fonctionne aux côtés du code Java existant. Vous pouvez utiliser des composites Kotlin construits avec le DSL depuis des classes Java, imbriquer des composants Java standard à l'intérieur des blocs DSL avec `add()`, et mélanger des fichiers Kotlin et Java dans le même projet.
:::

## Sujets {#topics}

Les sujets suivants couvrent l'utilisation du DSL, ainsi que son extension à tout composant ou composite personnalisé que vous créez.

<DocCardList className="topics-section" />

## Kotlin pour les développeurs Java {#kotlin-for-java-developers}

<details>
<summary>Nouveau dans Kotlin ? Voici quelques-unes des fonctionnalités clés du langage dont le DSL dépend.</summary>

### Sécurité des nulls {#null-safety}

Kotlin distingue les types nullables et non-nullables au moment de la compilation :

```kotlin
// Java - toute référence peut être null
String name = null;

// Kotlin - nullabilité explicite
var name: String? = null        // Nullable, peut être null
var safeName: String = "value"  // Non-null, le compilateur impose cela

// Opérateur de sécurité d'appel - renvoie null si name est null
println(name?.length)

// Opérateur Elvis - fournit une valeur par défaut quand null
println(name ?: "default")
```

### Fonctions d'extension {#extension-functions}

Kotlin vous permet d'ajouter des méthodes à des classes existantes sans héritage :

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

### Lambdas et syntaxe de lambda terminale {#lambdas-and-trailing-lambda-syntax}

Les lambdas Kotlin sont plus concises que celles de Java, et lorsqu'une lambda est le dernier paramètre, elle peut aller à l'extérieur des parenthèses :

```kotlin
// Java
button.addClickListener(e -> System.out.println("Cliqué"));

// Kotlin - lambda comme dernier paramètre va à l'extérieur des parenthèses
button.onClick { println("Cliqué") }

// Avec paramètre explicite
button.onClick { event -> println("Cliqué: $event") }
```

Cette syntaxe de lambda terminale rend possible les blocs DSL.

### Paramètres par défaut {#default-parameters}

Les fonctions Kotlin peuvent avoir des valeurs de paramètres par défaut, réduisant la nécessité de méthodes surchargées :

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
