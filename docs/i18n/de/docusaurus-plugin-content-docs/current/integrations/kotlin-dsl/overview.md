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

webforJ bietet eine [Kotlin](https://kotlinlang.org/) *domänenspezifische Sprache*, oder DSL, die es Ihnen ermöglicht, Benutzeroberflächen mit einer prägnanten, typsicheren Syntax zu erstellen. Anstelle von imperativem Java-Code schreiben Sie deklarativen Code, der wie eine Beschreibung Ihrer UI-Struktur aussieht.

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

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Empfängern und Standardparameter, um eine natürliche Builder-Syntax zu erstellen. Komponenten nisten sich gegenseitig ein, Konfiguration erfolgt in Blöcken, und der Compiler erkennt strukturelle Fehler vor der Laufzeit.

## Einrichtung {#setup}

:::warning experimentelles Feature
Dieses Feature befindet sich noch in aktiver Entwicklung.
Die API kann in zukünftigen Versionen geändert werden, einschließlich möglicher inkompatibler Änderungen.

Sie sind herzlich eingeladen, es auszuprobieren und Feedback zu geben. Ihr Input wird dazu beitragen, das endgültige Design zu gestalten.
:::

Es ist keine separate Kotlin-Installation erforderlich. Maven übernimmt die Kompilierung über das Kotlin Maven-Plugin, sodass jedes Projekt, das bereits mit Maven gebaut wird, Kotlin-Unterstützung allein mit Abhängigkeits- und Plugin-Konfiguration hinzufügen kann.

:::tip Schnellstart
Um ein webforJ-Projekt, das Kotlin verwendet, mit allen benötigten Konfigurationen sofort zum Laufen zu bringen, siehe [diesen Abschnitt zur Verwendung des webforJ-Kotlin-Starters](#kotlin-starter-project).
:::

### Abhängigkeiten {#dependencies}

Fügen Sie das webforJ Kotlin DSL-Modul und die Kotlin Standardbibliothek zu Ihrer `pom.xml` hinzu:

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

Wenn Sie planen, Tests in Kotlin zu schreiben, fügen Sie auch die Kotlin-Testabhängigkeit hinzu. Sie integriert sich in JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven-Plugin {#kotlin-maven-plugin}

Fügen Sie das Kotlin Maven-Plugin hinzu, um sowohl Ihre Kotlin- als auch Java-Quellen zu kompilieren. Die Konfiguration `sourceDirs` unten erlaubt es, dass Kotlin- und Java-Dateien im selben Projekt coexistieren:

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

Mit diesen Ergänzungen kompilieren `mvn compile` Kotlin-Quellen zusammen mit Java. Kotlin-Dateien können in `src/main/kotlin` oder `src/main/java` abgelegt werden, und das Plugin verarbeitet beide.

:::tip[Java-Interoperabilität]
Kotlin wird in JVM-Bytecode kompiliert, sodass es zusammen mit bestehendem Java-Code funktioniert. Sie können von Java-Klassen aus DSL-erstellte Kotlin-Komposite verwenden, standardmäßige Java-Komponenten in DSL-Blöcken mit `add()` einfügen und Kotlin- und Java-Dateien im selben Projekt mischen.
:::

### Kotlin-Starter-Projekt {#kotlin-starter-project}

Wenn Sie die manuelle Einrichtung überspringen möchten, stellt das [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) Repository ein sofort einsatzbereites Projekt mit allen Abhängigkeiten und Plugin-Konfigurationen bereit. Klonen Sie es und beginnen Sie sofort mit dem Bau mit der DSL.

## Themen {#topics}

Die folgenden Themen behandeln die Verwendung der DSL sowie deren Erweiterung auf benutzerdefinierte Komponenten oder Komposite, die Sie erstellen.

<DocCardList className="topics-section" />

## Kotlin für Java-Entwickler {#kotlin-for-java-developers}

<details>
<summary>Neu in Kotlin? Hier sind einige der wichtigsten Sprachmerkmale, auf die die DSL angewiesen ist.</summary>

### Null-Sicherheit {#null-safety}

Kotlin unterscheidet nullable und non-nullable Typen zur Compile-Zeit:

```kotlin
// Java - jede Referenz kann null sein
String name = null;

// Kotlin - explizite Nullbarkeit
var name: String? = null        // Nullable, kann null sein
var safeName: String = "value"  // Nicht-null, der Compiler erzwingt dies

// Sicherer Aufruf-Operator - gibt null zurück, wenn name null ist
println(name?.length)

// Elvis-Operator - gibt Standardwert zurück, wenn null
println(name ?: "Standard")
```

### Erweiterungsfunktionen {#extension-functions}

Kotlin ermöglicht es Ihnen, bestehenden Klassen Methoden ohne Vererbung hinzuzufügen:

```kotlin
// Java-Ansatz - statische Hilfsklasse
public class StringUtils {
  public static String addExclamation(String input) {
    return input + "!";
  }
}
String result = StringUtils.addExclamation("Hallo");

// Kotlin-Ansatz - Erweiterungsfunktion
fun String.addExclamation(): String = this + "!"
val result = "Hallo".addExclamation()  // Liest sich wie ein Methodenaufruf
```

Die DSL verwendet Erweiterungsfunktionen, um Builder-Methoden zu Komponenten hinzuzufügen.

### Lambdas und nachfolgende Lambda-Syntax {#lambdas-and-trailing-lambda-syntax}

Kotlin-Lambdas sind prägnanter als die von Java, und wenn eine Lambda der letzte Parameter ist, kann sie außerhalb der Klammern stehen:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klick"));

// Kotlin - Lambda als letzter Parameter geht außerhalb der Klammern
button.onClick { println("Klick") }

// Mit explizitem Parameter
button.onClick { event -> println("Klick: $event") }
```

Diese nachfolgende Lambda-Syntax macht die DSL-Blöcke möglich.

### Standardparameter {#default-parameters}

Kotlin-Funktionen können Standardparameterwerte haben, was die Notwendigkeit für überladene Methoden verringert:

```kotlin
// Java - mehrere Konstruktoren erforderlich
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - eine Funktion mit Standardeinstellungen
fun button(
  text: String = "",
  theme: ButtonTheme = ButtonTheme.DEFAULT,
  block: Button.() -> Unit = {}
): Button
```

</details>
