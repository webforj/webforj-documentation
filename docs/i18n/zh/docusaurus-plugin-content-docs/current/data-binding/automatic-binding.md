---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 60ea231c7622e56330eef34d26d615cc
---
webforJ 提供了一些功能，可以简化开发人员的配置和自动绑定过程。本节演示了如何有效使用这些功能。

## 使用 `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` 方法自动将 UI 组件绑定到指定 bean 类的属性，简化了绑定过程并减少了手动设置。它根据名称将声明为字段的可绑定组件与 bean 属性对齐。

```java
public class HeroRegistration extends App {
  // 可绑定组件
  TextField name = new TextField("文本字段");
  ComboBox power = new ComboBox("能力");

  // ...

  @Override
  public void run() throws WebforjException {
    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    // ...
  }
}
```

```java
public class Hero {
  private String name;
  private String power;

  // 设置器和获取器
}
```

### `UseProperty` 注解 {#useproperty-annotation}

当您想要将 bean 属性绑定到具有不同名称的 UI 组件时，请使用 `UseProperty` 注解。
此注解在将 bean 属性绑定到 UI 组件时提供更高的精确度，特别是在处理 [嵌套 bean 属性](/docs/data-binding/bindings#nested-bean-properties) 时。

```java
public class HeroRegistration extends App {
  // 绑定到 name 属性
  @UseProperty("name")
  TextField nameField = new TextField("姓名");

  // 绑定到嵌套的 address.street 属性
  @UseProperty("address.street")
  TextField streetField = new TextField("街道");

  // 绑定到 power 属性
  ComboBox power = new ComboBox("能力");

  // ...
}
```

### `BindingExclude` 注解 {#bindingexclude-annotation}

使用 `BindingExclude` 注解可以将组件排除在自动绑定配置之外，当您更喜欢手动绑定或完全排除它时。

```java
public class HeroRegistration extends App {
  // 可绑定组件
  @UseProperty("name")
  TextField nameField = new TextField("文本字段");

  @BindingExclude
  ComboBox power = new ComboBox("能力");

  // ...
}
```

### `UseValidator` 注解 {#usevalidator-annotation}

使用 `UseValidator` 注解声明在绑定过程中强制额外验证规则的验证器。验证器按您指定的顺序应用。

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("电子邮箱");
}
```

### `UseTransformer` 注解 {#usetransformer-annotation}

使用 `UseTransformer` 注解可以直接在 UI 字段上声明一个转换器类。`BindingContext` 会自动应用指定的转换器。

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("日期字段");
}
```

### `BindingReadOnly` 注解 {#bindingreadonly-annotation}

使用 `BindingReadOnly` [将绑定标记为只读](./bindings/#configuring-readonly-bindings)。

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("用户 ID");
}
```

### `BindingRequired` 注解 {#bindingrequired-annotation}

使用 `BindingRequired` 将绑定标记为必填。另请参见 [必填绑定检测](#required-binding-detections)。

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("用户邮箱");
}
```

## 自动写入数据 {#writing-data-automatically}

为了提高应用程序的响应能力和动态性，您可以使用 `observe` 方法。此方法确保 UI 组件中的更改立即传播到数据模型。当您需要在数据模型与 UI 之间进行持续同步时，它特别有用。

`observe` 方法在上下文中为所有绑定注册一个 `ValueChangeEvent` 监听器，以监控用户所做的更改，然后立即将这些更改写入模型中绑定的属性（如果有效）。当您第一次调用此方法时，它会在 UI 组件中反映 bean 属性。

以下是如何使用 `observe` 的示例：

```java
Hero bean = new Hero("超人", "飞行");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // 对 bean 采取行动。
  }
});
```

:::info 更新方向
此自动绑定是单向的；当您更新 UI 组件时，更新将反映在模型中，但模型中的更改仅在您首次调用该方法时反映在 UI 组件中。
:::

:::tip 注意事项
虽然 `observe` 增加了应用程序的互动性，但使用时要谨慎：

- **性能影响**：频繁更新可能会影响性能，特别是在复杂模型或较慢的后端服务中。
- **用户体验**：自动更新不应干扰用户舒适输入数据的能力。
:::


## 必填绑定检测 {#required-binding-detections}

当您将绑定标记为必填时，它会将组件标记为必填，前提是该组件通过 `RequiredAware` 接口支持此状态。绑定本身并不强制执行此状态，而是在可用时将其设置在组件上。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

在使用 [Jakarta 注解](./validation/jakarta-validation.md) 时，绑定可以根据 bean 属性上存在的以下任何注解自动检测必填状态：

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
