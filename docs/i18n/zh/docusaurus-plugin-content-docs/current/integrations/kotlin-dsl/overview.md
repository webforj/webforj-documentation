---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 21ef4feee90e5c4f6827a48ce1755d0b
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

webforJ 提供了一个 [Kotlin](https://kotlinlang.org/) *领域特定语言*，或者称为 DSL，它允许你使用简洁且类型安全的语法构建用户界面。你编写的是声明式代码，看起来就像是对你 UI 结构的描述，而不是命令式的 Java 代码。

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

该 DSL 利用了 Kotlin 扩展函数、带接收者的 Lambda 和默认参数来创建一种自然的构建者语法。组件彼此嵌套，配置发生在块中，编译器在运行时之前捕捉结构上的错误。

## 配置 {#setup}

:::warning experimental feature
此功能仍在积极开发中。
API 可能在未来版本中发生更改，包括可能的破坏性更改。

欢迎你尝试并分享反馈。你的输入将有助于塑造最终设计。
:::

不需要单独的 Kotlin 安装。Maven 通过 Kotlin Maven 插件处理编译，因此任何已经使用 Maven 构建的项目都可以仅通过依赖项和插件配置来添加 Kotlin 支持。

:::tip 快速开始
要使用 Kotlin 启动并运行一个 webforJ 项目，并获得开箱即用的所有所需配置，请参见 [关于使用 webforJ Kotlin 启动器的这一部分](#kotlin-starter-project)。
:::

### 依赖项 {#dependencies}

将 webforJ Kotlin DSL 模块和 Kotlin 标准库添加到你的 `pom.xml`：

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

如果你计划用 Kotlin 编写测试，也请添加 Kotlin 测试依赖项。它与 JUnit 集成：

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven 插件 {#kotlin-maven-plugin}

添加 Kotlin Maven 插件以编译你的 Kotlin 和 Java 源代码。下面的 `sourceDirs` 配置允许 Kotlin 和 Java 文件共存于同一项目中：

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

通过这些补充，`mvn compile` 将编译 Kotlin 源代码和 Java 源代码。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java` 中，插件会处理这两者。

:::tip[Java 互操作性]
Kotlin 编译成 JVM 字节码，因此可以与现有的 Java 代码共存。你可以从 Java 类中使用由 DSL 构建的 Kotlin 组合，使用 `add()` 将标准 Java 组件嵌套在 DSL 块内，并在同一项目中混合使用 Kotlin 和 Java 文件。
:::

### Kotlin 启动项目 {#kotlin-starter-project}

如果你希望跳过手动设置，[webforJ Kotlin 启动器](https://github.com/webforj/webforj-kotlin-starter) 存储库提供了一个已经准备好运行的项目，所有依赖项和插件配置已经到位。克隆它并立即开始使用 DSL 进行构建。

## 主题 {#topics}

以下主题涵盖了使用 DSL 的内容，以及扩展到你创建的任何自定义组件或组合。

<DocCardList className="topics-section" />

## 针对 Java 开发者的 Kotlin {#kotlin-for-java-developers}

<details>
<summary>对 Kotlin 不熟悉？以下是 DSL 所依赖的一些关键语言特性。</summary>

### 空安全 {#null-safety}

Kotlin 在编译时区分可空和不可空类型：

```kotlin
// Java - 任何引用都可以为 null
String name = null;

// Kotlin - 显式的可空性
var name: String? = null        // 可空，可以为 null
var safeName: String = "value"  // 不可空，编译器强制这一点

// 安全调用操作符 - 如果 name 为 null 返回 null
println(name?.length)

// 埃尔维斯操作符 - 当为 null 时提供默认值
println(name ?: "default")
```

### 扩展函数 {#extension-functions}

Kotlin 允许你在现有类上添加方法而无需继承：

```kotlin
// Java 方法 - 静态工具类
public class StringUtils {
  public static String addExclamation(String input) {
    return input + "!";
  }
}
String result = StringUtils.addExclamation("Hello");

// Kotlin 方法 - 扩展函数
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // 看起来像是一个方法调用
```

DSL 使用扩展函数来添加构建器方法到组件。

### Lambdas 和尾随 lambda 语法 {#lambdas-and-trailing-lambda-syntax}

Kotlin 的 Lambda 比 Java 的更简洁，当 Lambda 是最后一个参数时，可以放在括号外面：

```kotlin
// Java
button.addClickListener(e -> System.out.println("Clicked"));

// Kotlin - 尾随参数的 lambda 放在括号外
button.onClick { println("Clicked") }

// 带显式参数
button.onClick { event -> println("Clicked: $event") }
```

这种尾随 lambda 语法使得 DSL 块成为可能。

### 默认参数 {#default-parameters}

Kotlin 函数可以有默认参数值，减少了重载方法的需要：

```kotlin
// Java - 需要多个构造函数
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - 一个具有默认值的函数
fun button(
  text: String = "",
  theme: ButtonTheme = ButtonTheme.DEFAULT,
  block: Button.() -> Unit = {}
): Button
```

</details>
