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

webforJ 提供了一种 [Kotlin](https://kotlinlang.org/) *领域特定语言*，或称为 DSL，使您能够使用简洁的、类型安全的语法构建用户界面。您写的不是命令式 Java 代码，而是像描述用户界面结构一样的声明性代码。

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

该 DSL 利用了 Kotlin 扩展函数、带接收者的 lambda 和默认参数来创建自然的构建器语法。组件相互嵌套，配置在块中进行，而且编译器会在运行时之前捕捉结构错误。

## 设置 {#setup}

<ExperimentalWarning />

不需要单独安装 Kotlin。Maven 通过 Kotlin Maven 插件处理编译，因此任何已经使用 Maven 构建的项目都可以仅通过依赖项和插件配置添加对 Kotlin 的支持。

:::tip 快速开始
要快速启动一个使用 Kotlin 的 webforJ 项目并运行所需的所有配置，请参见 [使用 webforJ Kotlin 启动器的相关章节](#kotlin-starter-project)。
:::

### 依赖 {#dependencies}

将 webforJ Kotlin DSL 模块和 Kotlin 标准库添加到您的 `pom.xml`：

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

如果您计划在 Kotlin 中编写测试，还请添加 Kotlin 测试依赖项。它与 JUnit 集成：

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven 插件 {#kotlin-maven-plugin}

添加 Kotlin Maven 插件以编译您的 Kotlin 和 Java 源代码。下面的 `sourceDirs` 配置允许 Kotlin 和 Java 文件在同一项目中共存：

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

通过这些补充，`mvn compile` 将 Kotlin 源文件与 Java 一起编译。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java` 中，插件会处理这两者。

:::tip[Java 互操作性]
Kotlin 编译为 JVM 字节码，因此可以与现有 Java 代码协同工作。您可以从 Java 类使用 DSL 构建的 Kotlin 组合，使用 `add()` 将标准 Java 组件嵌套在 DSL 块中，并在同一项目中混合 Kotlin 和 Java 文件。
:::

### Kotlin 启动项目 {#kotlin-starter-project}

如果您宁愿跳过手动设置， [webforJ Kotlin 启动器](https://github.com/webforj/webforj-kotlin-starter) 仓库提供了一个现成可运行的项目，所有依赖项和插件配置已经到位。克隆它并立即开始使用 DSL 构建。

## 主题 {#topics}

以下主题涵盖使用 DSL 以及扩展到您创建的任何自定义组件或组合。 

<DocCardList className="topics-section" />
