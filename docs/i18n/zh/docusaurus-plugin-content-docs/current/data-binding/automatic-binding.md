---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ 提供了多种功能，简化了开发人员的配置和自动绑定过程。本节演示如何有效地使用这些功能。

## 使用 `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` 方法自动将 UI 组件绑定到指定 bean 类的属性，简化绑定过程并减少手动设置。它根据组件的名称将可绑定组件（声明为表单或应用程序中的字段）与 bean 属性对齐。

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

当您想将 bean 属性绑定到具有不同名称的 UI 组件时，请使用 `UseProperty` 注解。该注解在绑定 bean 属性到 UI 组件时提供更高的精确度，尤其是在处理 [嵌套 bean 属性](/docs/data-binding/bindings#nested-bean-properties) 时。

```java
public class HeroRegistration extends App {
  // 绑定到 name 属性
  @UseProperty("name")
  TextField nameField = new TextField("姓名");
  
  // 绑定到嵌套 address.street 属性
  @UseProperty("address.street")
  TextField streetField = new TextField("街道");

  // 绑定到 power 属性
  ComboBox power = new ComboBox("能力");

  // ...
}
```

### `BindingExclude` 注解 {#bindingexclude-annotation}

使用 `BindingExclude` 注解来排除组件的自动绑定配置，当您希望手动绑定或完全排除时使用。

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

使用 `UseValidator` 注解声明在绑定过程中执行附加验证规则的验证器。验证器按您指定的顺序应用。

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("电子邮件地址");
}
```

### `UseTransformer` 注解 {#usetransformer-annotation}

使用 `UseTransformer` 注解直接在 UI 字段上声明一个转换器类。`BindingContext` 会自动应用指定的转换器。

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("日期字段");
}
```

### `BindingReadOnly` 注解 {#bindingreadonly-annotation}

使用 `BindingReadOnly` 来 [标记绑定为只读](./bindings/#configuring-readonly-bindings)。

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("用户 ID");
}
```

### `BindingRequired` 注解 {#bindingrequired-annotation}

使用 `BindingRequired` 来标记绑定为必需。另见 [必需绑定检测](#required-binding-detections)。

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("用户电子邮件");
}
```

## 自动写入数据 {#writing-data-automatically}

为了增强应用程序的响应性和动态性，您可以使用 `observe` 方法。此方法确保 UI 组件中的更改立即传播到数据模型。当您需要数据模型与 UI 之间的持续同步时，它特别有用。

`observe` 方法在上下文中的所有绑定上注册一个 `ValueChangeEvent` 监听器，以监视用户所做的更改，然后如果它们是有效的，立即将这些更改写入模型的绑定属性。当您首次调用该方法时，它会在 UI 组件中反映 bean 属性。

以下是如何使用 `observe` 的示例：

```java
Hero bean = new Hero("超人", "飞");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // 对 bean 执行操作。
  }
});
```

:::info 更新方向
这种自动绑定是单向的；当您更新 UI 组件时，模型中反映出更新，但模型中的更改仅在首次调用该方法时一次性反映在 UI 组件中。
:::

:::tip 注意事项
虽然 `observe` 增加了应用程序的交互性，但使用时请谨慎：

- **性能影响**：频繁更新可能会影响性能，特别是在复杂模型或缓慢后台服务的情况下。
- **用户体验**：自动更新不应干扰用户舒适地输入数据的能力。
:::


## 必需绑定检测 {#required-binding-detections}

当您将绑定标记为必需时，它会将组件标记为必需，前提是组件通过 `RequiredAware` 接口支持此状态。绑定本身并不强制执行此状态，而是根据需要在组件上设置它。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

在使用 [Jakarta 注解](./validation/jakarta-validation.md) 时，绑定可以根据 bean 属性上存在的以下任意注解自动检测必需状态：

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
