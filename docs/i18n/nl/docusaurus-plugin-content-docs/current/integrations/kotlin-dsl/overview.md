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

webforJ biedt een [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, of DSL, waarmee je UI's kunt bouwen met een beknopte, type-veilige syntaxis. In plaats van imperatieve Java-code, schrijf je declaratieve code die leest als een beschrijving van je UI-structuur.

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Naam");
name.setPlaceholder("Je naam");
layout.add(name);

Button submit = new Button("Verzenden", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
    direction = FlexDirection.COLUMN
    styles["gap"] = "10px"

    textField("Naam", placeholder = "Je naam")
    button("Verzenden", ButtonTheme.PRIMARY) {
        onClick { handleSubmit() }
    }
}
```

De DSL maakt gebruik van Kotlin extensiefuncties, lambdas met ontvangers en standaardparameters om een natuurlijke bouwer-syntaxis te creëren. Componenten nestelen zich in elkaar, configuratie vindt plaats in blokken, en de compiler vangt structurele fouten vóór runtime.

## Setup {#setup}

Er is geen aparte Kotlin-installatie vereist. Maven handelt de compilatie via de Kotlin Maven-plugin, zodat elk project dat al met Maven bouwt, Kotlin-ondersteuning kan toevoegen met alleen afhankelijkheid en plugin-configuratie.

### Afhankelijkheden {#dependencies}

Voeg de webforJ Kotlin DSL-module en de Kotlin-standaardbibliotheek toe aan je `pom.xml`:

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

Als je van plan bent om tests in Kotlin te schrijven, voeg dan ook de afhankelijkheid voor Kotlin-tests toe. Het integreert met JUnit:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven-plugin {#kotlin-maven-plugin}

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronnen te compileren. De `sourceDirs`-configuratie hieronder staat Kotlin- en Java-bestanden toe om samen in hetzelfde project te bestaan:

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

Met deze aanvullingen, compileert `mvn compile` Kotlin-bronnen samen met Java. Kotlin-bestanden kunnen in `src/main/kotlin` of `src/main/java` staan, en de plugin behandelt beide.

:::tip[Java interoperabiliteit]
Kotlin compileert naar JVM-bytecode, dus het werkt samen met bestaande Java-code. Je kunt DSL-gebouwde Kotlin-composieten vanuit Java-klassen gebruiken, standaard Java-componenten binnen DSL-blokken nestelen met `add()`, en Kotlin- en Java-bestanden in hetzelfde project mixen.
:::

## Onderwerpen {#topics}

De volgende onderwerpen behandelen het gebruik van de DSL, evenals het uitbreiden ervan naar aangepaste componenten of composieten die je maakt.

<DocCardList className="topics-section" />

## Kotlin voor Java-ontwikkelaars {#kotlin-for-java-developers}

<details>
<summary>Nieuw bij Kotlin? Hier zijn enkele van de belangrijkste taalfuncties waarop de DSL vertrouwt.</summary>

### Null-veiligheid {#null-safety}

Kotlin maakt onderscheid tussen nullable en non-nullable types bij het compileren:

```kotlin
// Java - elke referentie kan null zijn
String name = null;

// Kotlin - expliciete null-veiligheid
var name: String? = null        // Nullable, kan null zijn
var safeName: String = "value"  // Non-null, compiler handhaaft dit

// Veilige aanroepoperator - retourneert null als name null is
println(name?.length)

// Elvis-operator - biedt standaardwaarde als null
println(name ?: "standaard")
```

### Extensiefuncties {#extension-functions}

Kotlin stelt je in staat om methoden toe te voegen aan bestaande klassen zonder overerving:

```kotlin
// Java-aanpak - statische utility-klasse
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hallo");

// Kotlin-aanpak - extensiefunctie
fun String.addExclamation(): String = this + "!"
val result = "Hallo".addExclamation()  // Leest als een methode-aanroep
```

De DSL gebruikt extensiefuncties om bouwer-methoden aan componenten toe te voegen.

### Lambdas en trailing lambda-syntaxis {#lambdas-and-trailing-lambda-syntax}

Kotlin lambdas zijn beknopter dan die van Java, en wanneer een lambda de laatste parameter is, kan deze buiten de haakjes staan:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klikte"));

// Kotlin - lambda als laatste parameter staat buiten haakjes
button.onClick { println("Klikte") }

// Met expliciete parameter
button.onClick { event -> println("Klikte: $event") }
```

Deze trailing lambda-syntaxis maakt DSL-blokken mogelijk.

### Standaardparameters {#default-parameters}

Kotlin-functies kunnen standaardparameterwaarden hebben, waardoor de noodzaak voor overbelaste methoden vermindert:

```kotlin
// Java - meerdere constructors nodig
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - één functie met standaardwaarden
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
