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

webforJ bietet eine [Kotlin](https://kotlinlang.org/)-*Domain Specific Language* (DSL), mit der sich Benutzeroberflächen mit einer knappen, typsicheren Syntax erstellen lassen. Statt imperativem Java-Code schreibst du deklarativen Code, der deine UI-Struktur beschreibt.

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

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Empfängern und Standardparameter, um eine natürliche Builder-Syntax zu ermöglichen. Komponenten werden ineinander verschachtelt, die Konfiguration erfolgt in Blöcken und der Compiler erkennt strukturelle Fehler bereits vor der Laufzeit.

## Einrichtung {#setup}

<ExperimentalWarning />

Eine separate Kotlin-Installation ist nicht erforderlich. Maven übernimmt die Kompilierung über das Kotlin-Maven-Plugin. Daher kann jedes Projekt, das bereits mit Maven erstellt wird, allein durch die Konfiguration von Abhängigkeiten und Plugin um Kotlin-Unterstützung erweitert werden.

:::tip Schnellstart
Eine Anleitung, wie du ein webforJ-Projekt mit Kotlin samt allen erforderlichen Konfigurationen sofort startklar machst, findest du [in diesem Abschnitt zur Verwendung des webforJ Kotlin Starter](#kotlin-starter-project).
:::

### Abhängigkeiten {#dependencies}

Füge das webforJ-Kotlin-DSL-Modul und die Kotlin-Standardbibliothek zu deiner `pom.xml` hinzu:

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

Wenn du Tests in Kotlin schreiben möchtest, füge außerdem die Kotlin-Test-Abhängigkeit hinzu. Sie ist in JUnit integriert:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin-Maven-Plugin {#kotlin-maven-plugin}

Füge das Kotlin-Maven-Plugin hinzu, um sowohl deine Kotlin- als auch deine Java-Quellen zu kompilieren. Die folgende `sourceDirs`-Konfiguration ermöglicht es, Kotlin- und Java-Dateien im selben Projekt zu verwenden:

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

Mit diesen Ergänzungen kompiliert `mvn compile` Kotlin-Quellen zusammen mit Java. Kotlin-Dateien können in `src/main/kotlin` oder `src/main/java` abgelegt werden; das Plugin verarbeitet beide Verzeichnisse.

:::tip[Interoperabilität mit Java]
Kotlin wird in JVM-Bytecode kompiliert und funktioniert daher zusammen mit vorhandenem Java-Code. Du kannst mit der DSL erstellte Kotlin-Kompositkomponenten in Java-Klassen verwenden, Standard-Java-Komponenten mit `add()` in DSL-Blöcke einbetten und Kotlin- und Java-Dateien im selben Projekt kombinieren.
:::

### Kotlin-Starterprojekt {#kotlin-starter-project}

Wenn du die manuelle Einrichtung überspringen möchtest, bietet das Repository [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) ein sofort startbereites Projekt, in dem alle Abhängigkeiten und die Plugin-Konfiguration bereits eingerichtet sind. Klone es und beginne direkt mit der DSL zu arbeiten.

## Themen {#topics}

Die folgenden Themen behandeln die Verwendung der DSL und ihre Erweiterung um beliebige benutzerdefinierte Komponenten oder Kompositkomponenten.

<DocCardList className="topics-section" />
