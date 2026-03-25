---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 36366a03c9784b451033e5161bdc7359
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

webforJ biedt een [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, of DSL, waarmee je UI's kunt bouwen met een beknopte, type-veilige syntaxis. In plaats van imperative Java-code, schrijf je declaratieve code die leest als een beschrijving van je UI-structuur.

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

De DSL maakt gebruik van Kotlin-extensiefuncties, lambdas met ontvangers en standaardparameters om een natuurlijke bouwersyntaxis te creëren. Componenten nestelen zich in elkaar, configuratie gebeurt in blokken en de compiler vangt structurele fouten op vóór runtime.

## Setup {#setup}

:::warning experimentele functie
Deze functie is nog in actieve ontwikkeling. 
De API kan in toekomstige versies veranderen, inclusief mogelijke breaking changes.

Je bent van harte welkom om het uit te proberen en feedback te delen. Jouw input zal helpen bij het vormgeven van het uiteindelijke ontwerp.
:::

Geen aparte Kotlin-installatie is vereist. Maven regelt de compilatie via de Kotlin Maven-plugin, zodat elk project dat al met Maven bouwt, Kotlin-ondersteuning kan toevoegen met alleen afhankelijkheids- en pluginconfiguratie.

:::tip Snelle start
Om een webforJ-project met Kotlin aan de slag te krijgen met alle benodigde configuraties vanaf het begin, zie [dit gedeelte over het gebruik van de webforJ Kotlin-starter](#kotlin-starter-project).
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

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronnen te compileren. De `sourceDirs` configuratie hieronder staat toe dat Kotlin- en Java-bestanden in hetzelfde project naast elkaar bestaan:

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

Met deze toevoegingen, compileert `mvn compile` Kotlin-bronnen samen met Java. Kotlin-bestanden kunnen in `src/main/kotlin` of `src/main/java` staan, en de plugin handelt beide.

:::tip[Java-interoperabiliteit]
Kotlin compileert naar JVM-bytecode, dus het werkt naast bestaande Java-code. Je kunt DSL-gebouwde Kotlin-composities vanuit Java-klassen gebruiken, standaard Java-componenten in DSL-blokken nestelen met `add()`, en Kotlin- en Java-bestanden in hetzelfde project mixen.
:::

### Kotlin starter project {#kotlin-starter-project}

Als je de handmatige installatie wilt overslaan, biedt de [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) repository een kant-en-klaar project met alle afhankelijkheden en pluginconfiguraties al ingesteld. Kloneer het en begin meteen met bouwen met de DSL.

## Onderwerpen {#topics}

De volgende onderwerpen behandelen het gebruik van de DSL, evenals het uitbreiden ervan naar eventuele aangepaste componenten of composities die je maakt.

<DocCardList className="topics-section" />
