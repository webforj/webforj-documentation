---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
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

webforJ provides a [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, or DSL, that lets you build UIs with concise, type-safe syntax. Instead of imperative Java code, you write declarative code that reads like a description of your UI structure.

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

The DSL takes advantage of Kotlin extension functions, lambdas with receivers, and default parameters to create a natural builder syntax. Components nest inside each other, configuration happens in blocks, and the compiler catches structural mistakes before runtime.

## Setup {#setup}

:::warning experimental feature
This feature is still under active development.
The API may change in future versions, including possible breaking changes.

You're welcome to try it and share feedback. Your input will help shape the final design.
:::

No separate Kotlin installation is required. Maven handles compilation through the Kotlin Maven plugin, so any project that already builds with Maven can add Kotlin support with dependency and plugin configuration alone.

:::tip Quick start
To get a webforJ project using Kotlin up and running with all the needed configurations out of the box, see [this section on using the webforJ Kotlin starter](#kotlin-starter-project).
:::

### Dependencies {#dependencies}

Add the webforJ Kotlin DSL module and the Kotlin standard library to your `pom.xml`:

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

If you plan to write tests in Kotlin, also add the Kotlin test dependency. It integrates with JUnit:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven plugin {#kotlin-maven-plugin}

Add the Kotlin Maven plugin to compile both your Kotlin and Java sources. The `sourceDirs` configuration below allows Kotlin and Java files to coexist in the same project:

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

With these additions, `mvn compile` compiles Kotlin sources alongside Java. Kotlin files can go in `src/main/kotlin` or `src/main/java`, and the plugin handles both.

:::tip[Java interoperability]
Kotlin compiles to JVM bytecode, so it works alongside existing Java code. You can use DSL-built Kotlin composites from Java classes, nest standard Java components inside DSL blocks with `add()`, and mix Kotlin and Java files in the same project.
:::

### Kotlin starter project {#kotlin-starter-project}

If you'd rather skip the manual setup, the [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) repository provides a ready-to-run project with all dependencies and plugin configuration already in place. Clone it and start building with the DSL right away.

## Topics {#topics}

The following topics cover using the DSL, as well as extending it to any custom components or composites you create.

<DocCardList className="topics-section" />
