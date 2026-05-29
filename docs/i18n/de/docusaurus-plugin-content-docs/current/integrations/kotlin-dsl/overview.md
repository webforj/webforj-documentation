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

webforJ bietet eine [Kotlin](https://kotlinlang.org/) *domänenspezifische Sprache*, oder DSL, mit der Sie UIs mit prägnanter, typsicher Syntax erstellen können. Anstelle von imperativem Java-Code schreiben Sie deklarativen Code, der wie eine Beschreibung Ihrer UI-Struktur gelesen wird.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Name");
name.setPlaceholder("Ihr Name");
layout.add(name);

Button submit = new Button("Einreichen", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Name", placeholder = "Ihr Name")
  button("Einreichen", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Receivern und Standardparameter, um eine natürliche Builder-Syntax zu erstellen. Komponenten verschachteln sich, Konfiguration geschieht in Blöcken, und der Compiler erfasst strukturelle Fehler vor der Laufzeit.

## Einrichtung {#setup}

<ExperimentalWarning />

Es ist keine separate Kotlin-Installation erforderlich. Maven kümmert sich über das Kotlin-Maven-Plugin um die Kompilierung, sodass jedes Projekt, das bereits mit Maven gebaut wird, Kotlin-Unterstützung allein durch Konfiguration der Abhängigkeiten und Plugins hinzufügen kann.

:::tip Schneller Start
Um ein webforJ-Projekt mit Kotlin schnell in Betrieb zu nehmen, das alle benötigten Konfigurationen sofort bereitstellt, siehe [diesen Abschnitt zur Verwendung des webforJ Kotlin-Starter](#kotlin-starter-project).
:::

### Abhängigkeiten {#dependencies}

Fügen Sie das webforJ Kotlin DSL-Modul und die Kotlin-Standardbibliothek zu Ihrer `pom.xml` hinzu:

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

Wenn Sie vorhaben, Tests in Kotlin zu schreiben, fügen Sie auch die Kotlin-Testabhängigkeit hinzu. Sie integriert sich mit JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-Plugin {#kotlin-maven-plugin}

Fügen Sie das Kotlin Maven-Plugin hinzu, um sowohl Ihre Kotlin- als auch Java-Quellen zu kompilieren. Die Konfiguration `sourceDirs` unten ermöglicht es, dass Kotlin- und Java-Dateien im selben Projekt coexistieren:

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

Mit diesen Ergänzungen kompiliert `mvn compile` Kotlin-Quellen zusammen mit Java. Kotlin-Dateien können in `src/main/kotlin` oder `src/main/java` abgelegt werden, und das Plugin kümmert sich um beides.

:::tip[Java-Interoperabilität]
Kotlin wird in JVM-Bytecode kompiliert, sodass es neben vorhandenem Java-Code funktioniert. Sie können DSL-erstellte Kotlin-Komponenten aus Java-Klassen verwenden, Standard-Java-Komponenten innerhalb der DSL-Blöcke mit `add()` einfügen und Kotlin- und Java-Dateien im selben Projekt mischen.
:::

### Kotlin-Starterprojekt {#kotlin-starter-project}

Wenn Sie die manuelle Einrichtung überspringen möchten, bietet das [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) Repository ein einsatzbereites Projekt mit allen Abhängigkeiten und Plugin-Konfigurationen bereits eingerichtet. Klonen Sie es und beginnen Sie sofort mit dem Bau mit der DSL.

## Themen {#topics}

Die folgenden Themen behandeln die Verwendung der DSL sowie die Erweiterung um benutzerdefinierte Komponenten oder Zusammenstellungen, die Sie erstellen. 

<DocCardList className="topics-section" />
