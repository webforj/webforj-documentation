---
sidebar_position: 3
title: Reporters
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---

<!-- vale off -->

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<!-- vale on -->

验证报告器用于向用户界面提供有关验证过程的反馈。此功能对于告知用户其输入验证的结果至关重要，特别是在复杂的表单或数据密集型应用程序中。

## 什么是验证报告器？ {#whats-a-validation-reporter}

验证报告器是一个组件，用于处理并向用户展示验证的结果。它充当验证逻辑与用户界面之间的桥梁，确保验证结果得到有效和清晰的传达。

:::tip 核心组件默认报告器
webforJ 包含 `DefaultBindingReporter`，这是一个设计与所有核心 webforJ 组件无缝合作的默认绑定报告器。此内置报告器自动显示验证错误，消除了许多情况下自定义实现的需要。根据组件的配置，`DefaultBindingReporter` 直接作为弹出提示或内嵌的方式，显示在组件正下方的验证错误。这一功能显著简化了错误报告过程，确保了验证错误的清晰和直接的沟通，并通过提供即时的上下文敏感反馈来增强用户体验。
:::

## 配置验证报告器 {#configuring-validation-reporters}

您可以在绑定上下文中配置验证报告器，以自定义信息的呈现方式。通常，您会实现一个验证报告器以汇总验证结果，然后以用户友好的方式显示它们，如突出显示不正确的字段、显示错误消息或更新状态指示器。

以下是如何为字段设置验证报告器的示例：

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("电子邮件地址");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "无效电子邮件地址的自定义消息"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

在上述代码中，电子邮件绑定包含一个自定义报告器，可以直接在输入字段下显示验证消息。这个设置利用 `useReporter` 方法，配置了绑定如何处理和呈现验证结果。此方法有效地将验证逻辑与用户界面连接起来，确保任何验证问题对用户立即可见，从而增强表单的互动性和用户体验。
```
