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

webforJ proporciona un [Kotlin](https://kotlinlang.org/) *Lenguaje Específico de Dominio*, o DSL, que te permite construir interfaces de usuario con sintaxis concisa y segura en cuanto a tipos. En lugar de código Java imperativo, escribes código declarativo que se lee como una descripción de la estructura de tu interfaz de usuario.

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

El DSL aprovecha las funciones de extensión de Kotlin, lambdas con receptores y parámetros por defecto para crear una sintaxis de constructor natural. Los componentes se anidan unos dentro de otros, la configuración se realiza en bloques y el compilador detecta errores estructurales antes de la ejecución.

## Configuración {#setup}

No se requiere una instalación separada de Kotlin. Maven se encarga de la compilación a través del plugin de Maven de Kotlin, por lo que cualquier proyecto que ya se construya con Maven puede añadir soporte para Kotlin solo con la configuración de dependencias y plugins.

### Dependencias {#dependencies}

Agrega el módulo webforJ Kotlin DSL y la biblioteca estándar de Kotlin a tu `pom.xml`:

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

Si planeas escribir pruebas en Kotlin, también agrega la dependencia de prueba de Kotlin. Se integra con JUnit:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Plugin de Maven de Kotlin {#kotlin-maven-plugin}

Agrega el plugin de Maven de Kotlin para compilar tanto tus fuentes de Kotlin como de Java. La configuración `sourceDirs` a continuación permite que los archivos de Kotlin y Java coexistan en el mismo proyecto:

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

Con estas adiciones, `mvn compile` compila las fuentes de Kotlin junto con Java. Los archivos de Kotlin pueden ir en `src/main/kotlin` o `src/main/java`, y el plugin maneja ambos.

:::tip[Interoperabilidad con Java]
Kotlin se compila en bytecode de JVM, por lo que funciona junto con el código Java existente. Puedes utilizar composites de Kotlin construidos con DSL desde clases Java, anidar componentes estándar de Java dentro de bloques DSL con `add()`, y mezclar archivos de Kotlin y Java en el mismo proyecto.
:::

## Temas {#topics}

Los siguientes temas cubren el uso del DSL, así como su extensión a cualquier componente o composite personalizado que crees.

<DocCardList className="topics-section" />

## Kotlin para desarrolladores de Java {#kotlin-for-java-developers}

<details>
<summary>¿Nuevo en Kotlin? Aquí tienes algunas de las características clave del lenguaje en las que se basa el DSL.</summary>

### Seguridad de nulos {#null-safety}

Kotlin distingue entre tipos anulables y no anulables en tiempo de compilación:

```kotlin
// Java - cualquier referencia puede ser null
String name = null;

// Kotlin - nulidad explícita
var name: String? = null        // Anulable, puede ser null
var safeName: String = "value"  // No anulable, el compilador hace cumplir esto

// Operador de llamada segura - devuelve null si name es null
println(name?.length)

// Operador Elvis - proporciona valor por defecto cuando es null
println(name ?: "default")
```

### Funciones de extensión {#extension-functions}

Kotlin te permite agregar métodos a clases existentes sin herencia:

```kotlin
// Enfoque de Java - clase de utilidad estática
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hello");

// Enfoque de Kotlin - función de extensión
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // Se lee como una llamada a método
```

El DSL utiliza funciones de extensión para agregar métodos de construcción a los componentes.

### Lambdas y sintaxis de lambda final {#lambdas-and-trailing-lambda-syntax}

Las lambdas de Kotlin son más concisas que las de Java, y cuando una lambda es el último parámetro, puede ir fuera de los paréntesis:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Clicked"));

// Kotlin - lambda como último parámetro va fuera de los paréntesis
button.onClick { println("Clicked") }

// Con parámetro explícito
button.onClick { event -> println("Clicked: $event") }
```

Esta sintaxis de lambda final es lo que hace posibles los bloques DSL.

### Parámetros por defecto {#default-parameters}

Las funciones de Kotlin pueden tener valores de parámetros por defecto, reduciendo la necesidad de métodos sobrecargados:

```kotlin
// Java - se necesitan múltiples constructores
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - una función con valores por defecto
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
