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

webforJ biedt een [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, of DSL, waarmee je gebruikersinterfaces kunt bouwen met een compacte, type-veilige syntaxis. In plaats van imperatieve Java-code schrijf je declaratieve code die leest als een beschrijving van de structuur van je gebruikersinterface.

<!-- INTRO_END -->

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

De DSL maakt gebruik van Kotlin-extensiefuncties, lambdas met ontvangers en standaardparameters om een natuurlijke builder-syntaxis te creëren. Componenten worden binnen elkaar genest, configuratie vindt plaats in blokken en de compiler vangt structurele fouten op vóór de uitvoering.

## Setup {#setup}

:::warning experimentele functie
Deze functie is nog steeds in actieve ontwikkeling.
De API kan in toekomstige versies veranderen, inclusief mogelijke brekende wijzigingen.

Je bent van harte welkom om het uit te proberen en feedback te geven. Je inbreng zal helpen de uiteindelijke vormgeving te bepalen.
:::

Er is geen aparte Kotlin-installatie vereist. Maven beheert de compilatie via de Kotlin Maven-plugin, zodat elk project dat al met Maven bouwt, Kotlin-ondersteuning kan toevoegen met alleen afhankelijkheid en plugin-configuratie.

:::tip Snelle start
Om een webforJ-project met Kotlin werkend te krijgen met alle benodigde configuraties direct uit de doos, zie [deze sectie over het gebruik van de webforJ Kotlin-starter](#kotlin-starter-project).
:::

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

Als je van plan bent om tests in Kotlin te schrijven, voeg dan ook de Kotlin-testafhankelijkheid toe. Het integreert met JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-plugin {#kotlin-maven-plugin}

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronnen te compileren. De configuratie `sourceDirs` hieronder staat Kotlin- en Java-bestanden toe om in hetzelfde project te coexistenteren:

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

Met deze aanvullingen compileert `mvn compile` Kotlin-bronnen naast Java. Kotlin-bestanden kunnen worden geplaatst in `src/main/kotlin` of `src/main/java`, en de plugin verwerkt beide.

:::tip[Java-interoperabiliteit]
Kotlin compileert naar JVM-bytecode, zodat het naast bestaande Java-code kan werken. Je kunt DSL-gebouwde Kotlin-composieten vanuit Java-klassen gebruiken, standaard Java-componenten binnen DSL-blokken nestelen met `add()`, en Kotlin- en Java-bestanden in hetzelfde project mixen.
:::

### Kotlin-starterproject {#kotlin-starter-project}

Als je liever de handmatige installatie overslaat, biedt de [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) repository een kant-en-klaar project met alle afhankelijkheden en plugin-configuratie al ingesteld. Clone het en begin direct met het bouwen met de DSL.

## Onderwerpen {#topics}

De volgende onderwerpen behandelen het gebruik van de DSL, evenals het uitbreiden ervan naar eventuele aangepaste componenten of composieten die je creëert.

<DocCardList className="topics-section" />

## Kotlin voor Java-ontwikkelaars {#kotlin-for-java-developers}

<details>
<summary>Nieuwe in Kotlin? Hier zijn enkele van de belangrijkste taalkenmerken waarop de DSL vertrouwt.</summary>

### Null-veiligheid {#null-safety}

Kotlin maakt onderscheid tussen null- en niet-null-typen tijdens het compileren:

```kotlin
// Java - elke referentie kan null zijn
String name = null;

// Kotlin - expliciete null-veiligheid
var name: String? = null        // Nullable, kan null zijn
var safeName: String = "waarde" // Niet-null, de compiler handhaaft dit

// Veilige aanroepoperator - geeft null terug als name null is
println(name?.length)

// Elvis-operator - biedt standaardwaarde wanneer null
println(name ?: "standaard")
```

### Extensiefuncties {#extension-functions}

Kotlin laat je methoden toevoegen aan bestaande klassen zonder overerving:

```kotlin
// Java-aanpak - statische hulpprogrammaklasse
public class StringUtils {
  public static String addExclamation(String input) {
    return input + "!";
  }
}
String result = StringUtils.addExclamation("Hallo");

// Kotlin-aanpak - extensiefunctie
fun String.addExclamation(): String = this + "!"
val result = "Hallo".addExclamation()  // Leest als een method call
```

De DSL gebruikt extensiefuncties om builder-methoden aan componenten toe te voegen.

### Lambdas en trailing lambda-syntaxis {#lambdas-and-trailing-lambda-syntax}

Kotlin-lambdas zijn compacter dan die van Java, en als een lambda de laatste parameter is, kan deze buiten de haakjes gaan:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Geklikt"));

// Kotlin - lambda als laatste parameter gaat buiten haakjes
button.onClick { println("Geklikt") }

// Met expliciete parameter
button.onClick { event -> println("Geklikt: $event") }
```

Deze trailing lambda-syntaxis maakt het mogelijk om DSL-blokken te creëren.

### Standaardparameters {#default-parameters}

Kotlin-functies kunnen standaardparameterwaarden hebben, wat de noodzaak van overbelaste methoden vermindert:

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
