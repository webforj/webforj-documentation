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

webforJ biedt een [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, of DSL, waarmee je UI's kunt bouwen met een beknopte, type-veilige syntaxis. In plaats van imperatieve Java-code schrijf je declaratieve code die leest als een beschrijving van de structuur van je UI.

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

De DSL maakt gebruik van Kotlin-extensie-methoden, lambdas met ontvangers en standaardparameters om een natuurlijke bouwer-syntaxis te creëren. Componenten nestelen zich in elkaar, configuratie gebeurt in blokken, en de compiler vangt structurele fouten voor de uitvoering op.

## Setup {#setup}

:::warning experimental feature
Deze functie is nog steeds in actieve ontwikkeling.
De API kan in toekomstige versies veranderen, inclusief mogelijke breaking changes.

Je bent van harte welkom om het uit te proberen en feedback te delen. Jouw input zal helpen de uiteindelijke vormgeving te bepalen.
:::

Er is geen aparte Kotlin-installatie nodig. Maven zorgt voor de compilatie via de Kotlin Maven-plugin, zodat elk project dat al met Maven bouwt, Kotlin-ondersteuning kan toevoegen met alleen afhankelijkheid- en pluginconfiguratie.

### Dependencies {#dependencies}

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

Als je van plan bent om tests in Kotlin te schrijven, voeg dan ook de Kotlin test-afhankelijkheid toe. Dit integreert met JUnit:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven plugin {#kotlin-maven-plugin}

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronnen te compileren. De configuratie `sourceDirs` hieronder staat Kotlin- en Java-bestanden toe om naast elkaar in hetzelfde project te bestaan:

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

Met deze toevoegingen compileert `mvn compile` Kotlin-bronnen naast Java. Kotlin-bestanden kunnen in `src/main/kotlin` of `src/main/java` worden geplaatst, en de plugin verwerkt beide.

:::tip[Java interoperability]
Kotlin compileert naar JVM-bytecode, zodat het samenwerkt met bestaande Java-code. Je kunt DSL-gebouwde Kotlin-composieten vanuit Java-klassen gebruiken, standaard Java-componenten binnen DSL-blokken met `add()` nestelen, en Kotlin- en Java-bestanden in hetzelfde project mixen.
:::

## Topics {#topics}

De volgende onderwerpen behandelen het gebruik van de DSL, evenals het uitbreiden ervan naar aangepaste componenten of composieten die je creëert.

<DocCardList className="topics-section" />

## Kotlin voor Java-ontwikkelaars {#kotlin-for-java-developers}

<details>
<summary>Nieuw bij Kotlin? Hier zijn enkele van de belangrijkste taalfuncties waarop de DSL zich baseert.</summary>

### Null-veiligheid {#null-safety}

Kotlin onderscheidt nullable en non-nullable types tijdens de compileertijd:

```kotlin
// Java - elke referentie kan null zijn
String name = null;

// Kotlin - expliciete null-veiligheid
var name: String? = null        // Nullable, kan null zijn
var safeName: String = "waarde" // Non-null, compiler handhaaft dit

// Veilige aanroepoperator - retourneert null als naam null is
println(name?.length)

// Elvis-operator - biedt standaardwaarde wanneer null
println(name ?: "standaard")
```

### Extensie-methoden {#extension-functions}

Kotlin stelt je in staat om methoden aan bestaande klassen toe te voegen zonder overerving:

```kotlin
// Java-aanpak - statische hulpprogrammaklasse
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hallo");

// Kotlin-aanpak - extensiemethode
fun String.addExclamation(): String = this + "!"
val resultat = "Hallo".addExclamation()  // Leest als een methodeaanroep
```

De DSL gebruikt extensie-methoden om bouwer-methoden aan componenten toe te voegen.

### Lambdas en trailing lambda-syntaxis {#lambdas-and-trailing-lambda-syntax}

Kotlin-lambdas zijn beknopter dan die van Java, en wanneer een lambda de laatste parameter is, kan deze buiten de haakjes gaan:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klikt"));

// Kotlin - lambda als laatste parameter gaat buiten de haakjes
button.onClick { println("Klikt") }

// Met expliciete parameter
button.onClick { event -> println("Klikt: $event") }
```

Deze trailing lambda-syntaxis maakt DSL-blokken mogelijk.

### Standaardparameters {#default-parameters}

Kotlin-functies kunnen standaardparameterwaarden hebben, waardoor de behoefte aan overbelaste methoden vermindert:

```kotlin
// Java - meerdere constructeurs nodig
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
