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

webforJ bietet eine [Kotlin](https://kotlinlang.org/) *domänenspezifische Sprache*, oder DSL, die es Ihnen ermöglicht, UIs mit prägnanter, typensicherer Syntax zu erstellen. Anstelle von imperativem Java-Code schreiben Sie deklarativen Code, der wie eine Beschreibung Ihrer UI-Struktur aussieht.

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

Die DSL nutzt Kotlin-Erweiterungsfunktionen, Lambdas mit Receivern und Standardparameter, um eine natürliche Builder-Syntax zu schaffen. Komponenten verschachteln sich ineinander, Konfigurationen erfolgen in Blöcken, und der Compiler erkennt strukturelle Fehler vor der Laufzeit.

## Setup {#setup}

:::warning experimental feature
Diese Funktion befindet sich noch in aktiver Entwicklung.
Die API kann sich in zukünftigen Versionen ändern, einschließlich möglicher inkompatibler Änderungen.

Sie sind herzlich eingeladen, es auszuprobieren und Feedback zu teilen. Ihre Rückmeldung wird dazu beitragen, das endgültige Design zu formen.
:::

Es ist keine separate Kotlin-Installation erforderlich. Maven übernimmt die Kompilierung über das Kotlin-Maven-Plugin, sodass jedes Projekt, das bereits mit Maven gebaut wird, Kotlin-Unterstützung allein durch Abhängigkeits- und Plugin-Konfiguration hinzufügen kann.

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

### Kotlin Maven Plugin {#kotlin-maven-plugin}

Fügen Sie das Kotlin Maven Plugin hinzu, um sowohl Ihre Kotlin- als auch Java-Quellen zu kompilieren. Die `sourceDirs`-Konfiguration unten ermöglicht es Kotlin- und Java-Dateien, im selben Projekt coexistieren:

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

Mit diesen Ergänzungen kompiliert `mvn compile` Kotlin-Quellen neben Java. Kotlin-Dateien können in `src/main/kotlin` oder `src/main/java` gehen, und das Plugin verwaltet beide.

:::tip[Java Interoperabilität]
Kotlin wird in JVM-Bytecode kompiliert, sodass es neben bestehendem Java-Code funktioniert. Sie können mit der DSL erstellte Kotlin-Komposite aus Java-Klassen verwenden, Standard-Java-Komponenten innerhalb von DSL-Blöcken mit `add()` verschachteln und Kotlin- und Java-Dateien im selben Projekt mischen.
:::

## Themen {#topics}

Die folgenden Themen decken die Verwendung der DSL sowie deren Erweiterung auf benutzerdefinierte Komponenten oder Komposite ab, die Sie erstellen.

<DocCardList className="topics-section" />

## Kotlin für Java-Entwickler {#kotlin-for-java-developers}

<details>
<summary>Neu in Kotlin? Hier sind einige der wichtigsten Sprachfunktionen, auf die die DSL angewiesen ist.</summary>

### Nullsicherheit {#null-safety}

Kotlin unterscheidet nullbare und nicht-nullbare Typen zur Compile-Zeit:

```kotlin
// Java - jede Referenz kann null sein
String name = null;

// Kotlin - explizite Nullbarkeit
var name: String? = null        // Nullable, kann null sein
var safeName: String = "Wert"   // Nicht-null, der Compiler erzwingt dies

// Sicherer Aufruf-Operator - gibt null zurück, wenn name null ist
println(name?.length)

// Elvis-Operator - liefert Standardwert bei null
println(name ?: "Standard")
```

### Erweiterungsfunktionen {#extension-functions}

Kotlin erlaubt es Ihnen, Methoden zu bestehenden Klassen hinzuzufügen, ohne Vererbung:

```kotlin
// Java-Ansatz - statische Dienstprogrammklasse
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

### Lambdas und Syntax der abschließenden Lambda {#lambdas-and-trailing-lambda-syntax}

Kotlin-Lambdas sind prägnanter als die von Java, und wenn eine Lambda der letzte Parameter ist, kann sie außerhalb der Klammern platziert werden:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Geklickt"));

// Kotlin - Lambda als letzter Parameter geht außerhalb der Klammern
button.onClick { println("Geklickt") }

// Mit explizitem Parameter
button.onClick { event -> println("Geklickt: $event") }
```

Diese Syntax für abschließende Lambdas ermöglicht es, DSL-Blöcke zu erstellen.

### Standardparameter {#default-parameters}

Kotlin-Funktionen können Standardparameterwerte haben, wodurch die Notwendigkeit für überladene Methoden verringert wird:

```kotlin
// Java - mehrere Konstruktoren benötigt
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - eine Funktion mit Standards
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
