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

webforJ proporciona un [Kotlin](https://kotlinlang.org/) *Lenguaje de Dominio Específico*, o DSL, que te permite construir interfaces de usuario con una sintaxis concisa y segura en cuanto a tipos. En lugar de escribir código Java imperativo, escribes código declarativo que se lee como una descripción de la estructura de tu interfaz de usuario.

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

El DSL aprovecha las funciones de extensión de Kotlin, las lambdas con receptores y los parámetros predeterminados para crear una sintaxis de constructor natural. Los componentes se anidan entre sí, la configuración ocurre en bloques, y el compilador detecta errores estructurales antes de la ejecución.

## Configuración {#setup}

:::warning característica experimental
Esta función todavía está en desarrollo activo.
La API puede cambiar en versiones futuras, incluyendo posibles cambios disruptivos.

Eres bienvenido a probarlo y compartir tus comentarios. Tu opinión ayudará a dar forma al diseño final.
:::

No se requiere una instalación separada de Kotlin. Maven maneja la compilación a través del complemento Kotlin para Maven, por lo que cualquier proyecto que ya se construya con Maven puede agregar soporte para Kotlin solo con la configuración de dependencias y complementos.

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

### Complemento de Maven para Kotlin {#kotlin-maven-plugin}

Agrega el complemento de Maven para Kotlin para compilar tanto tus fuentes de Kotlin como de Java. La configuración de `sourceDirs` a continuación permite que los archivos de Kotlin y Java coexistan en el mismo proyecto:

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

Con estas adiciones, `mvn compile` compila las fuentes de Kotlin junto con las de Java. Los archivos de Kotlin pueden ubicarse en `src/main/kotlin` o `src/main/java`, y el complemento maneja ambos.

:::tip[Interoperabilidad con Java]
Kotlin se compila a bytecode de JVM, por lo que funciona junto con el código Java existente. Puedes usar composites de Kotlin construidos con DSL desde clases Java, anidar componentes Java estándar dentro de bloques DSL con `add()`, y mezclar archivos de Kotlin y Java en el mismo proyecto.
:::

## Temas {#topics}

Los siguientes temas cubren el uso del DSL, así como la extensión a cualquier componente o composite personalizado que crees.

<DocCardList className="topics-section" />

## Kotlin para desarrolladores de Java {#kotlin-for-java-developers}

<details>
<summary>¿Nuevo en Kotlin? Aquí tienes algunas de las características clave del lenguaje en las que se basa el DSL.</summary>

### Seguridad de null {#null-safety}

Kotlin distingue entre tipos nullable y non-nullable en tiempo de compilación:

```kotlin
// Java - cualquier referencia puede ser null
String name = null;

// Kotlin - nullabilidad explícita
var name: String? = null        // Nullable, puede ser null
var safeName: String = "value"  // Non-null, el compilador lo verifica

// Operador de llamada segura - devuelve null si name es null
println(name?.length)

// Operador Elvis - proporciona un valor por defecto cuando es null
println(name ?: "default")
```

### Funciones de extensión {#extension-functions}

Kotlin te permite agregar métodos a clases existentes sin necesidad de herencia:

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

El DSL utiliza funciones de extensión para agregar métodos de constructor a los componentes.

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

Esta sintaxis de lambda final es lo que hace posibles los bloques del DSL.

### Parámetros predeterminados {#default-parameters}

Las funciones de Kotlin pueden tener valores de parámetro predeterminados, lo que reduce la necesidad de métodos sobrecargados:

```kotlin
// Java - se necesitan múltiples constructores
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - una función con valores predeterminados
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
