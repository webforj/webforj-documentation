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

webforJ bietet eine [Kotlin](https://kotlinlang.org/) *Domänenspezifische Sprache*, oder DSL, die es Ihnen ermöglicht, Benutzeroberflächen mit einer prägnanten, typensicheren Syntax zu erstellen. Anstelle von imperativem Java-Code schreiben Sie deklarativen Code, der wie eine Beschreibung Ihrer UI-Struktur liest.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Name");
name.setPlaceholder("Ihr Name");
layout.add(name);

Button submit = new Button("Absenden", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Name", placeholder = "Ihr Name")
  button("Absenden", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Empfängern und Standardparameter, um eine natürliche Builder-Syntax zu schaffen. Komponenten verschachteln sich gegenseitig, die Konfiguration geschieht in Blöcken, und der Compiler erkennt strukturelle Fehler vor der Laufzeit.

## Setup {#setup}

:::warning experimental feature
Dieses Feature befindet sich noch in aktiver Entwicklung.
Die API kann sich in zukünftigen Versionen ändern, einschließlich möglicher Breaking Changes.

Sie sind herzlich eingeladen, es auszuprobieren und Feedback zu geben. Ihr Input wird helfen, das endgültige Design zu gestalten.
:::

Es ist keine separate Kotlin-Installation erforderlich. Maven kümmert sich um die Kompilierung über das Kotlin Maven-Plugin, sodass jedes Projekt, das bereits mit Maven gebaut wird, Kotlin-Unterstützung nur durch Abhängigkeits- und Plugin-Konfiguration hinzufügen kann.

:::tip Schneller Einstieg
Um ein webforJ-Projekt mit Kotlin schnell zum Laufen zu bringen und alle benötigten Konfigurationen sofort zu haben, siehe [diesen Abschnitt zur Verwendung des webforJ Kotlin-Starter](#kotlin-starter-project).
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

Wenn Sie planen, Tests in Kotlin zu schreiben, fügen Sie auch die Kotlin-Testabhängigkeit hinzu. Sie integriert sich mit JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-Plugin {#kotlin-maven-plugin}

Fügen Sie das Kotlin Maven-Plugin hinzu, um sowohl Ihre Kotlin- als auch Ihre Java-Quellen zu kompilieren. Die folgende `sourceDirs`-Konfiguration ermöglicht es, dass Kotlin- und Java-Dateien im selben Projekt coexistieren:

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

:::tip[Java Interoperabilität]
Kotlin wird zu JVM-Bytecode kompiliert, sodass es neben vorhandenem Java-Code funktioniert. Sie können von Java-Klassen aus DSL-erstellte Kotlin-Komposits verwenden, standardmäßige Java-Komponenten in DSL-Blöcke mit `add()` einfügen und Kotlin- und Java-Dateien im selben Projekt mischen.
:::

### Kotlin-Starterprojekt {#kotlin-starter-project}

Wenn Sie die manuelle Einrichtung lieber überspringen möchten, bietet das [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) Repository ein startbereites Projekt mit bereits enthaltenen Abhängigkeiten und Plugin-Konfigurationen. Klonen Sie es und beginnen Sie sofort mit dem DSL zu bauen.

## Themen {#topics}

Die folgenden Themen decken die Verwendung der DSL sowie deren Erweiterung auf benutzerdefinierte Komponenten oder Komposits ab, die Sie erstellen. 

<DocCardList className="topics-section" />
