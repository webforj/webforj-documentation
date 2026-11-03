---
sidebar_position: 1
title: 入门
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# 入门 <DocChip chip='since' label='26.03' />

TestBed 是 webforJ 的无浏览器测试方案：它可以在普通的 JUnit 测试中运行视图和组件，无需浏览器或服务器。本页将介绍如何添加依赖、编写第一个测试并运行测试。

`@WebforjTest` 注解会为类中的每个测试设置一个 `WebforjTestbed` 上下文。在测试中，`ComponentLocator` 用于查找页面上的组件，`UserInteraction` 用于模拟用户与组件的交互。

<!-- INTRO_END -->

## 添加依赖 {#adding-the-dependency}

TestBed 由单独的模块提供。将它以测试作用域添加到应用中：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

版本由 webforJ 物料清单（BOM）提供，因此依赖项中无需声明版本。通过 [原型](/docs/introduction/getting-started)创建的项目已经导入了 BOM。该模块还会引入 JUnit Jupiter API。

:::info Spring Boot 应用
Spring Boot 应用应改用 `webforj-spring-testbed` 模块。请参阅 [Spring 测试](/docs/integrations/spring/testing)。
:::

## 编写第一个测试 {#writing-a-first-test}

以下表单会向用户输入的姓名问好：

```java title="GreetingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class GreetingForm extends Composite<Div> {

  public GreetingForm() {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    Button greet = new Button(
        "Greet", e -> greeting.setText("Hello, " + name.getValue() + "!"));

    getBoundComponent().add(name, greet, greeting);
  }
}
```

测试会输入姓名、点击 **Greet**，并检查问候语：

```java title="GreetingFormTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.GreetingForm;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class GreetingFormTest {
  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingForm());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}
```

这个测试的步骤与用户的操作过程相似：

1. `@WebforjTest` 会为每个测试方法提供一个 `WebforjTestbed`，即测试运行所处的上下文。
2. `context.add()` 会将表单添加到页面并运行其完整生命周期，这与运行中的应用将表单添加到框架时的行为相同。
3. `ComponentLocator` 根据标签查找字段，并根据文本查找按钮。请参阅[查找组件](./finding-components)。
4. `UserInteraction` 会模拟用户输入和点击，因此按钮的监听器会运行。请参阅[用户交互](./user-interaction)。
5. `verifyThat` 会检查用户实际能读到的文本。请参阅[断言](./assertions)。

## `@WebforjTest` 提供的功能 {#what-webforjtest-provides}

带有该注解的类中的每个测试都会在各自独立的 `WebforjTestbed` 中运行。上下文会在每个测试开始前设置，并在测试结束后销毁，因此每个测试都从空页面开始，不会沿用上一个测试的状态。

测试可以通过以下两种方式访问上下文：

- 在测试方法中声明一个 `WebforjTestbed` 参数，如第一个测试所示。
- 调用 `WebforjTestbed.getCurrent()`，例如，在该类各项测试共用的方法中调用。

`context.add()` 接受一个或多个组件，并将它们添加到上下文的根框架中；`context.getFrame()` 会返回该根框架。

如果测试需要在路由之间导航，而不是添加组件，可以设置 `@WebforjTest` 的 `app`、`routeClasses` 或 `routePackages` 属性。请参阅[测试应用](./testing-apps)。

## 运行测试 {#running-the-tests}

TestBed 测试是普通的 JUnit 测试。可以使用构建工具运行，也可以像其他 JUnit 测试一样从 IDE 中运行：

```bash title="Terminal"
mvn test
```
