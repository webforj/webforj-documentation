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

webforJ proporciona un [Kotlin](https://kotlinlang.org/) *Lenguaje Específico de Dominio*, o DSL, que te permite construir interfaces de usuario con una sintaxis concisa y segura en cuanto a tipos. En lugar de código Java imperativo, escribes código declarativo que se lee como una descripción de la estructura de tu interfaz.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Nombre");
name.setPlaceholder("Tu nombre");
layout.add(name);

Button submit = new Button("Enviar", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Nombre", placeholder = "Tu nombre")
  button("Enviar", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

El DSL aprovecha las funciones de extensión de Kotlin, lambdas con receptores y parámetros predeterminados para crear una sintaxis de constructor natural. Los componentes se anidan dentro de otros, la configuración se realiza en bloques y el compilador detecta errores estructurales antes de la ejecución.

## Configuración {#setup}

<ExperimentalWarning />

No se requiere una instalación separada de Kotlin. Maven maneja la compilación a través del complemento de Kotlin para Maven, por lo que cualquier proyecto que ya se construya con Maven puede agregar soporte para Kotlin solo con la configuración de dependencias y complementos.

:::tip Inicio rápido
Para obtener un proyecto webforJ que utilice Kotlin en funcionamiento con todas las configuraciones necesarias desde el principio, consulta [esta sección sobre cómo usar el iniciador de Kotlin de webforJ](#kotlin-starter-project).
:::

### Dependencias {#dependencies}

Agrega el módulo DSL de Kotlin de webforJ y la biblioteca estándar de Kotlin a tu `pom.xml`:

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

### Complemento de Maven de Kotlin {#kotlin-maven-plugin}

Agrega el complemento de Maven de Kotlin para compilar tanto tus fuentes de Kotlin como de Java. La configuración `sourceDirs` a continuación permite que los archivos de Kotlin y Java coexistan en el mismo proyecto:

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

Con estas adiciones, `mvn compile` compila fuentes de Kotlin junto con Java. Los archivos de Kotlin pueden ir en `src/main/kotlin` o `src/main/java`, y el complemento maneja ambos.

:::tip[Interoperabilidad con Java]
Kotlin se compila a bytecode de JVM, por lo que funciona junto con el código Java existente. Puedes usar compuestos de Kotlin construidos con DSL desde clases Java, anidar componentes estándar de Java dentro de bloques de DSL con `add()`, y mezclar archivos de Kotlin y Java en el mismo proyecto.
:::

### Proyecto iniciador de Kotlin {#kotlin-starter-project}

Si prefieres omitir la configuración manual, el [Iniciador de Kotlin de webforJ](https://github.com/webforj/webforj-kotlin-starter) proporciona un proyecto listo para funcionar con todas las dependencias y configuraciones de complementos ya en su lugar. Clónalo y comienza a construir con el DSL de inmediato.

## Temas {#topics}

Los siguientes temas cubren el uso del DSL, así como su extensión a cualquier componente o compuesto personalizado que crees.

<DocCardList className="topics-section" />
