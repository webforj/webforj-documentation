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

webforJ bietet eine [Kotlin](https://kotlinlang.org/) *Domänenspezifische Sprache*, oder DSL, die es Ihnen ermöglicht, UIs mit prägnanter, typsicherer Syntax zu erstellen. Anstelle von imperativem Java-Code schreiben Sie deklarativen Code, der wie eine Beschreibung Ihrer UI-Struktur aussieht.

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

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Empfängern und Standardparameter, um eine natürliche Builder-Syntax zu schaffen. Komponenten nisten in einander, die Konfiguration erfolgt in Blöcken, und der Compiler erfasst strukturelle Fehler vor der Laufzeit.

## Einrichtung {#setup}

Es ist keine separate Kotlin-Installation erforderlich. Maven verwaltet die Kompilierung über das Kotlin Maven-Plugin, sodass jedes Projekt, das bereits mit Maven gebaut wird, Kotlin-Unterstützung durch Anpassung der Abhängigkeiten und Plugin-Konfiguration hinzufügen kann.

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

Wenn Sie planen, Tests in Kotlin zu schreiben, fügen Sie auch die Kotlin-Testabhängigkeit hinzu. Es integriert sich mit JUnit:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven-Plugin {#kotlin-maven-plugin}

Fügen Sie das Kotlin Maven-Plugin hinzu, um sowohl Ihre Kotlin- als auch Ihre Java-Quellen zu kompilieren. Die untenstehende `sourceDirs`-Konfiguration ermöglicht es, dass Kotlin- und Java-Dateien im selben Projekt coexistieren:

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

Mit diesen Ergänzungen kompiliert `mvn compile` die Kotlin-Quellen neben Java. Kotlin-Dateien können in `src/main/kotlin` oder `src/main/java` abgelegt werden, und das Plugin verwaltet beides.

:::tip[Java-Interoperabilität]
Kotlin wird in JVM-Bytecode kompiliert, sodass es zusammen mit bestehendem Java-Code funktioniert. Sie können von Java-Klassen aus DSL-erstellte Kotlin-Komponenten verwenden, Standard-Java-Komponenten in DSL-Blöcken mit `add()` einfügen und Kotlin- und Java-Dateien im selben Projekt mischen.
:::

## Themen {#topics}

Die folgenden Themen behandeln die Verwendung der DSL sowie deren Erweiterung um benutzerdefinierte Komponenten oder Kompositionen, die Sie erstellen.

<DocCardList className="topics-section" />

## Kotlin für Java-Entwickler {#kotlin-for-java-developers}

<details>
<summary>Neu in Kotlin? Hier sind einige der wichtigsten Sprachmerkmale, auf die die DSL angewiesen ist.</summary>

### Nullsicherheit {#null-safety}

Kotlin unterscheidet zwischen nullable und non-nullable Typen zur Kompilierzeit:

```kotlin
// Java - jeder Verweis kann null sein
String name = null;

// Kotlin - explizite Nullbarkeit
var name: String? = null        // Nullable, kann null sein
var safeName: String = "value"  // Non-null, der Compiler erzwingt dies

// Sicherer Aufrufoperator - gibt null zurück, wenn name null ist
println(name?.length)

// Elvis-Operator - bietet Standardwert, wenn null
println(name ?: "Standardwert")
```

### Erweiterungsfunktionen {#extension-functions}

Kotlin ermöglicht es Ihnen, bestehenden Klassen Methoden ohne Vererbung hinzuzufügen:

```kotlin
// Java-Ansatz - statische Utility-Klasse
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

### Lambdas und Trailing Lambda-Syntax {#lambdas-and-trailing-lambda-syntax}

Kotlin-Lambdas sind prägnanter als die von Java, und wenn eine Lambda der letzte Parameter ist, kann sie außerhalb der Klammern stehen:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Geklickt"));

// Kotlin - Lambda als letzter Parameter geht außerhalb der Klammern
button.onClick { println("Geklickt") }

// Mit explizitem Parameter
button.onClick { event -> println("Geklickt: $event") }
```

Diese Trailing Lambda-Syntax macht DSL-Blöcke möglich.

### Standardparameter {#default-parameters}

Kotlin-Funktionen können Standardparameterwerte haben, wodurch die Notwendigkeit für überladene Methoden reduziert wird:

```kotlin
// Java - mehrere Konstruktoren benötigt
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - eine Funktion mit Standardwerten
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
