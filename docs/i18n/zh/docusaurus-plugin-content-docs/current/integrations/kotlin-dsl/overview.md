---
title: Kotlin DSL
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
# Kotlin DSL <DocChip chip='since' label='25.12' /> <DocChip chip='experimental' />
<!-- vale on -->

webforJ 提供了一种 [Kotlin](https://kotlinlang.org/) *领域专用语言*（DSL），让你能够使用简洁且类型安全的语法构建 UI。你无需编写命令式 Java 代码，而是编写声明式代码，以描述 UI 结构的方式呈现。

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

该 DSL 利用 Kotlin 扩展函数、带接收者的 lambda 和默认参数，构建出自然的构造器语法。组件可以相互嵌套，在代码块中进行配置，编译器还会在运行前捕获结构错误。

## 设置 {#setup}

<ExperimentalWarning />

无需单独安装 Kotlin。Maven 通过 Kotlin Maven 插件处理编译，因此任何已能使用 Maven 构建的项目，只需配置依赖项和插件即可添加 Kotlin 支持。

:::tip 快速开始
若要使用开箱即用的所有必要配置，快速启动一个使用 Kotlin 的 webforJ 项目，请参阅[本节：使用 webforJ Kotlin starter](#kotlin-starter-project)。
:::

### 依赖项 {#dependencies}

将 webforJ Kotlin DSL 模块和 Kotlin 标准库添加到 `pom.xml`：

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

如果计划使用 Kotlin 编写测试，还需添加 Kotlin 测试依赖项。它与 JUnit 集成：

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven 插件 {#kotlin-maven-plugin}

添加 Kotlin Maven 插件，以编译 Kotlin 和 Java 源代码。下面的 `sourceDirs` 配置允许 Kotlin 和 Java 文件共存于同一项目中：

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

添加这些配置后，`mvn compile` 会将 Kotlin 源代码与 Java 源代码一同编译。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java` 中，插件会同时处理这两个目录。

:::tip[与 Java 互操作]
Kotlin 会编译为 JVM 字节码，因此可以与现有 Java 代码协同工作。你可以在 Java 类中使用通过 DSL 构建的 Kotlin 复合组件，在 DSL 代码块中通过 `add()` 嵌套标准 Java 组件，还可以在同一项目中混合使用 Kotlin 和 Java 文件。
:::

### Kotlin starter 项目 {#kotlin-starter-project}

如果希望跳过手动设置，[webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) 仓库提供了一个可直接运行的项目，其中已配置好所有依赖项和插件。克隆该项目后，即可立即使用 DSL 开始构建。

## 主题 {#topics}

以下主题介绍如何使用 DSL，以及如何扩展 DSL，以支持你创建的自定义组件或复合组件。

<DocCardList className="topics-section" />
