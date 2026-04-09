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

webforJ biedt een [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, of DSL, waarmee je gebruikersinterfaces kunt bouwen met een beknopte, type-veilige syntaxis. In plaats van imperatieve Java-code schrijf je declaratieve code die leest als een beschrijving van de structuur van je UI.

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

De DSL benut Kotlin-extensiefuncties, lambdas met ontvangers en standaardparameters om een natuurlijke bouwer-syntaxis te creëren. Componenten nestelen zich in elkaar, configuratie gebeurt in blokken, en de compiler vangt structurele fouten voor runtime op.

## Setup {#setup}

<ExperimentalWarning />

Er is geen aparte Kotlin-installatie vereist. Maven regelt de compilatie via de Kotlin Maven-plugin, dus elk project dat al met Maven bouwt, kan Kotlin-ondersteuning toevoegen met alleen configuratie van afhankelijkheden en plugins.

:::tip Snelle start
Om een webforJ-project met Kotlin snel aan de slag te krijgen met alle noodzakelijke configuraties, zie [dit gedeelte over het gebruik van de webforJ Kotlin-starter](#kotlin-starter-project).
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

Als je van plan bent om tests in Kotlin te schrijven, voeg dan ook de Kotlin-testafhankelijkheid toe. Deze integreert met JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-plugin {#kotlin-maven-plugin}

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronnen te compileren. De configuratie `sourceDirs` hieronder staat Kotlin- en Java-bestanden toe om in hetzelfde project te bestaan:

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

Met deze toevoegingen compileert `mvn compile` de Kotlin-bronnen samen met Java. Kotlin-bestanden kunnen in `src/main/kotlin` of `src/main/java` worden geplaatst, en de plugin behandelt beide.

:::tip[Java-interoperabiliteit]
Kotlin compileert naar JVM-bytocode, dus het werkt samen met bestaande Java-code. Je kunt met DSL gebouwde Kotlin-composieten vanuit Java-klassen gebruiken, standaard Java-componenten binnen DSL-blokken verpakken met `add()`, en Kotlin- en Java-bestanden in hetzelfde project mixen.
:::

### Kotlin-starterproject {#kotlin-starter-project}

Als je de handmatige configuratie wilt overslaan, biedt de [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) repository een kant-en-klaar project met alle afhankelijkheden en pluginconfiguraties al ingesteld. Clone het en begin meteen met bouwen met de DSL.

## Onderwerpen {#topics}

De volgende onderwerpen behandelen het gebruik van de DSL, evenals het uitbreiden ervan met aangepaste componenten of composieten die je maakt.

<DocCardList className="topics-section" />
