---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
验证器在将数据提交到数据模型之前，会根据定义的约束条件验证用户界面组件中的数据。您可以应用验证器来验证数据是否符合某些标准，例如是否在指定范围内、是否匹配某个模式或是否为空。

验证是针对每个绑定进行配置的，允许特定规则适用于每个数据点。每个数据项根据其自身需求进行验证。

## 添加验证器 {#adding-validators}

通过在 `BindingBuilder` 上使用 `useValidator` 方法将验证器添加到绑定中。

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "名称不能为空")
    .useValidator(value -> value.length() >= 3, "名称必须至少包含 3 个字符")
    .add();
```

在上面的示例中，两个验证器验证名称不能为空，并至少包含三个字符。

:::tip 验证器处理
每个绑定可以添加的验证器数量没有限制。绑定按照插入顺序应用验证器，并在第一次违规时停止。
:::

## 实现验证器 {#implementing-validators}

您可以通过实现 `Validator<T>` 接口来创建可重用的自定义验证器，其中 `T` 是您要验证的数据类型。此设置涉及定义验证方法，该方法检查数据并返回 `ValidationResult`。

以下是一个检查用户电子邮件是否有效的可重用验证器示例。

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

### 在绑定中使用验证器 {#using-validators-in-bindings}

定义了验证器后，您可以轻松地将其应用于应用程序中的任何相关绑定。这对于需要在应用程序不同部分之间有共同验证规则的组件特别有用，例如用户电子邮件地址或密码强度。

将 `EmailValidator` 应用到绑定中：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### 重写验证器消息 {#overriding-validator-messages}

您可以在绑定到特定用户界面组件时自定义验证器的错误消息。这允许您在验证失败时向用户提供更详细或上下文相关的信息。当相同的验证器适用于多个组件但根据使用的上下文需要不同的指导时，自定义消息尤其有用。

以下是在绑定中重写可重用验证器默认消息的方法：

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "无效电子邮件地址的自定义消息"))
    .add();
```

在上面的示例中，代码将 `EmailValidator` 应用于电子邮件字段，并提供特定于该字段的自定义错误消息。

:::tip 理解 `Validator.from`
`Validator.from` 方法包装一个传入的验证器，允许您在验证器不支持自定义消息的情况下指定自定义错误消息。当您需要在多个组件中应用相同的验证逻辑，但每个实例都有不同的、特定于上下文的错误消息时，这种技术特别有用。
:::

### 动态验证消息 <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

默认情况下，验证消息是在绑定时设置的静态字符串。在支持多种语言的应用程序中，当用户切换地区时，这些静态消息不会更新。为了解决这个问题，`useValidator` 和 `Validator` 工厂方法都接受一个 `Supplier<String>`，在每次验证失败时调用它，使得消息可以动态解析。

#### 具有动态消息的内联验证器 {#inline-validators-with-dynamic-messages}

将 `Supplier<String>` 而不是普通的 `String` 传递给 `useValidator`：

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

每次运行验证并且谓词失败时，供应者调用 `t()`，解析当前地区的消息。

#### 具有动态消息的工厂方法 {#factory-methods-with-dynamic-messages}

`Validator.of` 和 `Validator.from` 工厂方法同样接受供应者：

```java {4,10}
// 创建具有动态消息的基于谓词的验证器
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// 用动态覆盖消息包装现有验证器
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### 语言环境敏感的自定义验证器 {#locale-aware-custom-validators}

对于需要在内部生成语言环境敏感消息的可重用验证器，实施 `LocaleAware` 接口。当通过 `BindingContext.setLocale()` 更改语言环境时，上下文会自动将新的语言环境传播给所有实现此接口的验证器。下次验证运行时，将生成更新语言环境中的消息。
