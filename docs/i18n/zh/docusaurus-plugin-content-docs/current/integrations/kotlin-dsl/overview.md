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

webforJ 提供了一种 [Kotlin](https://kotlinlang.org/) *领域特定语言*，或 DSL，允许你使用简洁、类型安全的语法构建用户界面。你编写声明式代码，描述你的 UI 结构，而不是使用命令式的 Java 代码。

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

该 DSL 利用 Kotlin 扩展函数、带接收者的 lambda 和默认参数创建自然的构建器语法。组件相互嵌套，配置在块中进行，编译器在运行时之前捕捉结构错误。

## 设置 {#setup}

无需单独安装 Kotlin。Maven 通过 Kotlin Maven 插件处理编译，因此任何已经使用 Maven 构建的项目只需添加依赖和插件配置即可支持 Kotlin。

### 依赖 {#dependencies}

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

如果你计划在 Kotlin 中编写测试，还需添加 Kotlin 测试依赖。它与 JUnit 集成：

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

通过这些添加，`mvn compile` 将编译 Kotlin 源代码与 Java 一起。Kotlin 文件可以放在 `src/main/kotlin` 或 `src/main/java` 中，插件可以处理这两者。

:::tip[Java 互操作性]
Kotlin 编译为 JVM 字节码，因此可以与现有的 Java 代码一起使用。你可以从 Java 类中使用 DSL 构建的 Kotlin 复合体，在 DSL 块内使用 `add()` 嵌套标准 Java 组件，并在同一项目中混合 Kotlin 和 Java 文件。
:::

## 主题 {#topics}

以下主题涵盖使用 DSL 以及扩展到你创建的任何自定义组件或复合体。

<DocCardList className="topics-section" />

## Java 开发者的 Kotlin {#kotlin-for-java-developers}

<details>
<summary>对 Kotlin 不熟悉？以下是 DSL 依赖的一些关键语言特性。</summary>

### 空安全 {#null-safety}

Kotlin 在编译时区分可空和不可空类型：

```kotlin
// Java - 任何引用都可以是 null
String name = null;

// Kotlin - 显式空性
var name: String? = null        // 可空，可以是 null
var safeName: String = "value"  // 不可空，编译器强制执行这一点

// 安全调用操作符 - 如果 name 为 null，则返回 null
println(name?.length)

// Elvis 操作符 - 当为空时提供默认值
println(name ?: "default")
```

### 扩展函数 {#extension-functions}

Kotlin 允许你向现有类添加方法而不使用继承：

```kotlin
// Java 方法 - 静态实用类
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hello");

// Kotlin 方法 - 扩展函数
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // 读起来像一个方法调用
```

该 DSL 使用扩展函数向组件添加构建器方法。

### Lambda 和尾随 Lambda 语法 {#lambdas-and-trailing-lambda-syntax}

Kotlin 的 lambda 比 Java 更为简洁，当 lambda 是最后一个参数时，可以放在括号外：

```kotlin
// Java
button.addClickListener(e -> System.out.println("Clicked"));

// Kotlin - 最后一个参数的 lambda 放在括号外
button.onClick { println("Clicked") }

// 显式参数
button.onClick { event -> println("Clicked: $event") }
```

这种尾随 lambda 语法正是使 DSL 块成为可能的原因。

### 默认参数 {#default-parameters}

Kotlin 函数可以具有默认参数值，从而减少重载方法的需要：

```kotlin
// Java - 需要多个构造函数
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - 一个函数带有默认值
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
