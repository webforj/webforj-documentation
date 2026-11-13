---
title: DSL de Kotlin
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
# DSL de Kotlin <DocChip chip='since' label='25.12' /> <DocChip chip='experimental' />
<!-- vale on -->

webforJ proporciona un *lenguaje específico de dominio* (DSL) de [Kotlin](https://kotlinlang.org/) que permite crear interfaces de usuario con una sintaxis concisa y segura en cuanto a tipos. En lugar de escribir código imperativo en Java, se escribe código declarativo que describe la estructura de la interfaz de usuario.

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

El DSL aprovecha las funciones de extensión de Kotlin, las lambdas con receptores y los parámetros predeterminados para crear una sintaxis de construcción natural. Los componentes se anidan unos dentro de otros, la configuración se realiza en bloques y el compilador detecta errores estructurales antes de la ejecución.

## Configuración {#setup}

<ExperimentalWarning />

No se requiere una instalación independiente de Kotlin. Maven gestiona la compilación mediante el complemento Maven de Kotlin, por lo que cualquier proyecto que ya compile con Maven puede añadir compatibilidad con Kotlin únicamente mediante la configuración de dependencias y complementos.

:::tip Inicio rápido
Para poner en marcha un proyecto de webforJ con Kotlin y todas las configuraciones necesarias listas para usar, consulta [esta sección sobre el uso del iniciador de Kotlin de webforJ](#kotlin-starter-project).
:::

### Dependencias {#dependencies}

Añade el módulo DSL de Kotlin de webforJ y la biblioteca estándar de Kotlin a tu `pom.xml`:

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

Si tienes previsto escribir pruebas en Kotlin, añade también la dependencia de pruebas de Kotlin. Se integra con JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Complemento Maven de Kotlin {#kotlin-maven-plugin}

Añade el complemento Maven de Kotlin para compilar tanto las fuentes de Kotlin como las de Java. La configuración `sourceDirs` que aparece a continuación permite que los archivos de Kotlin y Java coexistan en el mismo proyecto:

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

Con estos cambios, `mvn compile` compila las fuentes de Kotlin junto con las de Java. Los archivos de Kotlin pueden ubicarse en `src/main/kotlin` o en `src/main/java`; el complemento se encarga de ambos casos.

:::tip[Interoperabilidad con Java]
Kotlin compila a bytecode de JVM, por lo que funciona junto con el código Java existente. Puedes usar desde clases Java composiciones de Kotlin creadas con el DSL, anidar componentes Java estándar dentro de bloques DSL con `add()` y combinar archivos de Kotlin y Java en el mismo proyecto.
:::

### Proyecto inicial de Kotlin {#kotlin-starter-project}

Si prefieres omitir la configuración manual, el repositorio [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) proporciona un proyecto listo para ejecutar con todas las dependencias y la configuración del complemento ya incluidas. Clónalo y empieza a crear con el DSL de inmediato.

## Temas {#topics}

Los siguientes temas explican cómo usar el DSL y ampliarlo para incluir los componentes personalizados o las composiciones que crees.

<DocCardList className="topics-section" />
