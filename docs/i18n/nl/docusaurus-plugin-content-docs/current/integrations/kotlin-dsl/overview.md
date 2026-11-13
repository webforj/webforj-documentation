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
# Kotlin-DSL <DocChip chip='since' label='25.12' /> <DocChip chip='experimental' />
<!-- vale on -->

webforJ biedt een [Kotlin](https://kotlinlang.org/)*Domain Specific Language*, of DSL, waarmee je UI's kunt bouwen met een beknopte, typeveilige syntaxis. In plaats van imperatieve Java-code schrijf je declaratieve code die leest als een beschrijving van de structuur van je UI.

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

De DSL maakt gebruik van Kotlin-extensiefuncties, lambdas met receivers en standaardparameters om een natuurlijke builder-syntaxis te bieden. Componenten worden in elkaar genest, configuratie vindt plaats in blokken en de compiler onderschept structurele fouten voordat de code wordt uitgevoerd.

## Installatie {#setup}

<ExperimentalWarning />

Een afzonderlijke Kotlin-installatie is niet nodig. Maven verzorgt de compilatie via de Kotlin Maven-plugin, dus elk project dat al met Maven wordt gebouwd, kan Kotlin-ondersteuning toevoegen met alleen configuratie van afhankelijkheden en plugins.

:::tip Snel aan de slag
Zie [dit gedeelte over het gebruik van de webforJ Kotlin-starter](#kotlin-starter-project) om een webforJ-project met Kotlin op te zetten en direct aan de slag te gaan met alle benodigde configuraties.
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

Als je tests in Kotlin wilt schrijven, voeg dan ook de Kotlin-testafhankelijkheid toe. Deze integreert met JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-plugin {#kotlin-maven-plugin}

Voeg de Kotlin Maven-plugin toe om zowel je Kotlin- als Java-bronbestanden te compileren. Met de onderstaande `sourceDirs`-configuratie kunnen Kotlin- en Java-bestanden naast elkaar in hetzelfde project staan:

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

Met deze toevoegingen compileert `mvn compile` Kotlin-bronbestanden naast Java. Kotlin-bestanden kunnen in `src/main/kotlin` of `src/main/java` staan; de plugin verwerkt beide.

:::tip[Interoperabiliteit met Java]
Kotlin wordt gecompileerd naar JVM-bytecode en werkt dus naast bestaande Java-code. Je kunt met de DSL gebouwde Kotlin-composieten gebruiken vanuit Java-klassen, standaard Java-componenten nesten in DSL-blokken met `add()` en Kotlin- en Java-bestanden combineren in hetzelfde project.
:::

### Kotlin-starterproject {#kotlin-starter-project}

Als je de handmatige configuratie liever overslaat, biedt de repository [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) een project dat direct kan worden uitgevoerd, met alle afhankelijkheden en pluginconfiguratie al ingesteld. Kloon de repository en begin meteen met bouwen met de DSL.

## Onderwerpen {#topics}

In de volgende onderwerpen lees je hoe je de DSL gebruikt en uitbreidt voor aangepaste componenten of composieten die je maakt.

<DocCardList className="topics-section" />
