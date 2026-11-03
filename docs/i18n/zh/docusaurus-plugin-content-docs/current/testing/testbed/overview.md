---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  The TestBed is webforJ's browserless testing, which runs views and components
  in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
_i18n_hash: a97354d7621ab9d89fc8b88c7592847f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

测试 webforJ 视图通常需要启动应用、打开浏览器，再使用 Selenium 或 Playwright 操作它。这样可以捕获只有在浏览器中才能发现的问题，但每项测试都要付出启动浏览器和服务器，以及等待它们就绪的代价。

TestBed 是 webforJ 提供的无浏览器测试方式。视图在普通的 JUnit 测试中运行，不会打开浏览器，也不会启动服务器。测试会添加视图，像用户一样与之交互，再从 Java 中检查结果。组件会像在运行中的应用里一样附加、触发事件和导航；每次交互都会在测试运行下一行代码之前完成，因此无需等待，也不会出现不稳定的测试。

## 测试的工作方式 {#how-a-test-works}

TestBed 测试会按照用户的操作步骤进行：找到页面上的组件，与之交互，然后检查页面随后显示的内容。下面的表单会订阅一个电子邮件地址，并且只有在字段中填写了地址后，**Subscribe** 按钮才会启用：

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

测试会找到字段和按钮，输入地址、点击按钮，然后检查确认信息：

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

TestBed 的每个部分都负责一个步骤：

- **查找。** `ComponentLocator` 描述如何像用户一样识别组件：根据组件类型和显示内容来定位，例如根据标签查找字段。请参阅[查找组件](./finding-components)。
- **操作。** `UserInteraction` 执行用户可以进行的操作。如果点击的是禁用按钮，测试会失败，而不会运行该按钮的监听器。请参阅[用户交互](./user-interaction)。
- **检查。** `verifyThat` 检查用户在组件上会看到的内容；快照则会将整个视图与已保存的描述进行比较。请参阅[断言](./assertions)和[快照](./snapshots)。

测试不会直接访问视图的字段，而是通过页面显示的内容进行操作，因此即使视图的构建方式发生变化，测试仍能通过；如果用户看到的内容发生变化，测试则会失败。

除了测试单个视图，TestBed 还可以导航路由、测试受保护的视图、响应对话框以及运行计时器，详见[测试应用](./testing-apps)；它也可以在 Spring Boot 应用中运行，并使用该应用的 Bean 和安全机制。

浏览器测试仍然适用于只有浏览器才能显示的内容，例如样式、布局和客户端行为。TestBed 则负责其余部分：测试用户与视图交互时，视图会如何响应。

从[入门指南](./getting-started)开始，添加依赖项并编写第一个测试。

## 主题 {#topics}

<DocCardList className="topics-section" />
