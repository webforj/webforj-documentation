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

webforJ 提供了一种 [Kotlin](https://kotlinlang.org/) *特定领域语言*，简称 DSL，让您能够使用简洁、类型安全的语法构建用户界面。您编写的是声明式代码，类似于您 UI 结构的描述，而不是命令式的 Java 代码。

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

该 DSL 利用 Kotlin 扩展函数、带接收者的 lambda 和默认参数来创建自然的构建器语法。组件嵌套在一起，配置在块中进行，编译器在运行时之前捕捉结构性错误。

## Setup {#setup}

:::warning experimental feature
该功能仍在积极开发中。
API 可能在未来版本中发生变化，包括可能的重大更改。

欢迎您尝试并分享反馈。您的意见将帮助塑造最终设计。
:::

不需要单独的 Kotlin 安装。Maven 通过 Kotlin Maven 插件处理编译，因此任何已经使用 Maven 构建的项目只需要添加依赖关系和插件配置便可以添加 Kotlin 支持。

:::tip 快速开始
要快速启动一个使用 Kotlin 的 webforJ 项目，并具备所有必要的配置，请参见 [这一部分关于使用 webforJ Kotlin 启动器](#kotlin-starter-project)。
:::

### Dependencies {#dependencies}

将 webforJ Kotlin DSL 模块和 Kotlin 标准库添加到您的 `pom.xml` 中：

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

如果您计划用 Kotlin 编写测试，还需添加 Kotlin 测试依赖项。它与 JUnit 集成：

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven plugin {#kotlin-maven-plugin}

添加 Kotlin Maven 插件以编译 Kotlin 和 Java 源代码。下面的 `sourceDirs` 配置允许 Kotlin 和 Java 文件共存于同一项目中：

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

通过这些添加，`mvn compile` 将与 Java 代码一起编译 Kotlin 源文件。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java`，插件会处理两者。

:::tip[Java 互操作性]
Kotlin 编译为 JVM 字节码，因此可以与现有的 Java 代码一起工作。您可以从 Java 类中使用 DSL 构建的 Kotlin 组件，将标准 Java 组件嵌套在 DSL 块中并使用 `add()`，以及在同一项目中混合使用 Kotlin 和 Java 文件。
:::

### Kotlin starter project {#kotlin-starter-project}

如果您想要跳过手动设置， [webforJ Kotlin 启动器](https://github.com/webforj/webforj-kotlin-starter) 仓库提供了一个即用的项目，已经包含了所有依赖项和插件配置。克隆它并立即开始使用 DSL 构建。

## Topics {#topics}

以下主题涵盖了使用 DSL 的内容，以及扩展到您创建的任何自定义组件或综合体的内容。

<DocCardList className="topics-section" />
