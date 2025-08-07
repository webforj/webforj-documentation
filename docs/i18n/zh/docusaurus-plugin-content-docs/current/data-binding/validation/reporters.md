---
sidebar_position: 3
title: Reporters
_i18n_hash: 217311f203d2736071c33d6650c74ec2
---
验证报告器用于向用户界面提供有关验证过程的反馈。此功能对于告知用户其输入验证的结果至关重要，特别是在复杂表单或数据密集型应用程序中。

## 什么是验证报告器？ {#whats-a-validation-reporter}

验证报告器是一个处理并向用户显示验证结果的组件。它充当验证逻辑与用户界面之间的桥梁，确保有效且清晰地传达验证结果。

:::tip 核心组件默认报告器
webforJ 包含 `DefaultBindingReporter`，这是一个设计用于与所有核心 webforJ 组件无缝协作的默认绑定报告器。这个内置的报告器自动显示验证错误，消除了在许多情况下需要自定义实现的必要性。根据组件的配置，`DefaultBindingReporter` 可以直接将验证错误作为弹出框或内联显示，正好位于组件下方。此功能显著简化了错误报告过程，确保验证错误以清晰直接的方式进行沟通，并通过提供即时、上下文相关的输入验证反馈来增强用户体验。
:::

## 配置验证报告器 {#configuring-validation-reporters}

您可以在绑定上下文中配置验证报告器，以自定义消息的呈现方式。通常，您会实现一个验证报告器，以汇总验证结果，然后以用户友好的方式显示它们，例如突出显示错误字段、显示错误消息或更新状态指示符。

以下是如何为一个字段设置验证报告器的示例

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Email Address");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Custom message for invalid email address"))
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

在上述代码中，电子邮件绑定包含一个自定义报告器，直接在输入字段下方显示验证消息。该设置利用 `useReporter` 方法，该方法配置了绑定如何处理和呈现验证结果。此方法有效地将验证逻辑与用户界面连接起来，确保任何验证问题立即对用户可见，从而增强表单的互动性和用户体验。
