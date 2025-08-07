---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
验证器会在将数据提交到数据模型之前，检查您 UI 组件中的数据是否符合定义的约束条件。可以应用验证器以确保数据满足某些标准，例如在指定范围内、匹配模式或不为空。

验证是在每个绑定上配置的，允许特定规则适用于每个数据点。这种设置确保每个数据都根据其自身的要求经过验证。

## 添加验证器 {#adding-validators}

使用 `BindingBuilder` 上的 `useValidator` 方法向绑定添加验证器。

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "名称不能为空")
    .useValidator(value -> value.length() >= 3, "名称必须至少包含 3 个字符")
    .add();
```

在上面的示例中，两个验证器验证名称是否为空以及名称是否包含至少三个字符。

:::tip 验证器处理
每个绑定可以添加的验证器数量没有限制。绑定按插入顺序应用验证器，并在第一个违规处停止。
:::

## 实现验证器 {#implementing-validators}

您可以通过实现 `Validator<T>` 接口来创建自定义可重用的验证器，其中 `T` 是您要验证的数据类型。此设置涉及定义验证方法，该方法检查数据并返回 `ValidationResult`。

以下是一个可重用验证器的示例，它检查用户的电子邮件是否有效。

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("无效的电子邮件地址");
    }
  }
}
```

## 在绑定中使用验证器 {#using-validators-in-bindings}

定义验证器后，可以轻松将其应用于应用程序中的任何相关绑定。这对于需要跨应用程序不同部分的通用验证规则的组件特别有用，例如用户电子邮件地址或密码强度。

要将 `EmailValidator` 应用到绑定中：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## 重写验证器消息 {#overriding-validator-messages}

您可以在绑定到特定 UI 组件时自定义验证器的错误消息。这允许您在验证失败时向用户提供更详细或相关的上下文信息。当同一验证器适用于多个组件但需要根据使用上下文提供不同的用户指导时，自定义消息尤其有用。

以下是如何在绑定中重写可重用验证器默认消息的示例：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "无效电子邮件地址的自定义消息"))
    .add();
```

在上面的示例中，代码将 `EmailValidator` 应用到电子邮件字段，并使用专门针对该字段的自定义错误消息。这使得在验证失败时，能够提供更具针对性和更有帮助的用户体验。

:::tip 理解 `Validator.from`
`Validator.from` 方法将传递的验证器包装在一个新的验证器中，允许您指定自定义错误消息，以防验证器不支持自定义消息。当您需要在多个组件中应用相同的验证逻辑，但每个实例需要不同的、特定于上下文的错误消息时，此技术尤其有用。
:::
