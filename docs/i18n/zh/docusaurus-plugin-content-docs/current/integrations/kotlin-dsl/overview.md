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

webforJ 提供了一种 [Kotlin](https://kotlinlang.org/) *领域特定语言*，简称 DSL，使您可以使用简洁、类型安全的语法构建 UI。您编写的是一种声明式代码，看起来像是对您的 UI 结构的描述，而不是命令式的 Java 代码。

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

该 DSL 利用了 Kotlin 扩展函数、带接收者的 lambda 以及默认参数来创建自然的构建器语法。组件彼此嵌套，配置在块中进行，编译器在运行时之前捕获结构性错误。

## 设置 {#setup}

:::warning experimental feature
此功能仍在积极开发中。
API 可能会在未来版本中更改，包括可能的重大更改。

欢迎您尝试并提供反馈。您的意见将有助于塑造最终设计。
:::

不需要单独的 Kotlin 安装。Maven 通过 Kotlin Maven 插件处理编译，因此任何已经使用 Maven 构建的项目只需通过依赖项和插件配置即可添加 Kotlin 支持。

### 依赖项 {#dependencies}

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

如果您打算用 Kotlin 编写测试，还需要添加 Kotlin 测试依赖项。它与 JUnit 集成：

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven 插件 {#kotlin-maven-plugin}

添加 Kotlin Maven 插件以编译您的 Kotlin 和 Java 源文件。以下 `sourceDirs` 配置允许 Kotlin 和 Java 文件共存于同一项目中：

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

通过这些附加项，`mvn compile` 将同时编译 Kotlin 源文件和 Java 源文件。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java` 中，插件会处理这两者。

:::tip[Java 互操作性]
Kotlin 编译为 JVM 字节码，因此可以与现有的 Java 代码一起工作。您可以从 Java 类中使用 DSL 构建的 Kotlin 组合，在 DSL 块内嵌套标准 Java 组件，并混合 Kotlin 和 Java 文件在同一项目中。
:::

## 主题 {#topics}

以下主题涵盖使用 DSL 的方法，以及扩展到您创建的任何自定义组件或组合的方法。

<DocCardList className="topics-section" />

## 面向 Java 开发人员的 Kotlin {#kotlin-for-java-developers}

<details>
<summary>对 Kotlin 不熟悉？以下是 DSL 依赖的一些关键语言特性。</summary>

### 空安全 {#null-safety}

Kotlin 在编译时区分可空类型和非空类型：

```kotlin
// Java - 任何引用都可以为 null
String name = null;

// Kotlin - 显式的可空性
var name: String? = null        // 可空，可以为 null
var safeName: String = "value"  // 非空，编译器强制执行这一点

// 安全调用操作符 - 当 name 为 null 时返回 null
println(name?.length)

// 埃尔维斯操作符 - 当为 null 时提供默认值
println(name ?: "default")
```

### 扩展函数 {#extension-functions}

Kotlin 让您可以在不进行继承的情况下向现有类添加方法：

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
val result = "Hello".addExclamation()  // 看起来像是方法调用
```

DSL 使用扩展函数向组件添加构建方法。

### Lambda 和尾 lambda 语法 {#lambdas-and-trailing-lambda-syntax}

Kotlin 的 lambda 比 Java 的更简洁，当 lambda 是最后一个参数时，可以放在括号外：

```kotlin
// Java
button.addClickListener(e -> System.out.println("Clicked"));

// Kotlin - 作为最后参数的 lambda 放在括号外
button.onClick { println("Clicked") }

// 带显式参数
button.onClick { event -> println("Clicked: $event") }
```

这种尾 lambda 语法使 DSL 块成为可能。

### 默认参数 {#default-parameters}

Kotlin 函数可以具有默认参数值，从而减少重载方法的需要：

```kotlin
// Java - 需要多个构造函数
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - 一个带默认值的函数
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
