---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
验证器在您 UI 组件中检查数据是否符合定义的约束，然后才能将这些数据提交到数据模型。您可以应用验证器以确保数据满足特定标准，例如在指定范围内、匹配特定模式或不为空。

验证是在每个绑定中配置的，允许特定规则应用于每个数据点。这种设置确保每一条数据根据其自身要求进行验证。

## 添加验证器 {#adding-validators}

通过在 `BindingBuilder` 上使用 `useValidator` 方法将验证器添加到绑定中。

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "姓名不能为空")
    .useValidator(value -> value.length() >= 3, "姓名必须至少包含 3 个字符")
    .add();
```

在上面的示例中，两个验证器验证姓名不为空，并且包含至少三个字符。

:::tip 验证器处理
每个绑定可以添加的验证器数量没有限制。绑定按照插入顺序应用验证器，并在第一次违规时停止。
:::

## 实现验证器 {#implementing-validators}

您可以通过实现 `Validator<T>` 接口创建可重用的自定义验证器，其中 `T` 是您想要验证的数据类型。此设置涉及定义验证方法，该方法检查数据并返回一个 `ValidationResult`。

以下是一个可重用的验证器示例，它检查用户的电子邮件是否有效。

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

一旦您定义了验证器，可以轻松地将其应用于您应用中的任何相关绑定。这对于需要在应用不同部分之间使用通用验证规则的组件特别有用，例如用户电子邮件地址或密码强度。

要将 `EmailValidator` 应用到绑定：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## 覆盖验证器消息 {#overriding-validator-messages}

您可以在绑定到特定 UI 组件的地方自定义验证器的错误消息。这允许您在验证失败时为用户提供更详细或上下文相关的信息。当同一验证器适用于多个组件，但根据其使用的上下文需要不同的用户指导时，自定义消息尤为有用。

以下是如何在绑定中覆盖可重用验证器的默认消息：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "电子邮件地址无效的自定义消息"))
    .add();
```

在上面的示例中，代码对电子邮件字段应用 `EmailValidator`，并使用专门为该字段量身定制的自定义错误消息。这使得在验证失败时提供更有针对性和有帮助的用户体验成为可能。

:::tip 理解 `Validator.from`
`Validator.from` 方法将传入的验证器封装为一个新验证器，允许您指定自定义错误消息，前提是该验证器不支持自定义消息。当您需要在多个组件之间应用相同的验证逻辑，但每个实例需要不同的上下文特定错误消息时，这种技术特别有用。
:::
