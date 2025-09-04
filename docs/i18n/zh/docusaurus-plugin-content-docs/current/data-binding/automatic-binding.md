---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: e914be874b2c22c5e32f7fce4b5f1885
---
webforJ 提供了多个功能，可以简化开发人员的配置和自动绑定过程。本节演示如何有效地使用这些功能。

## Using `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` 方法自动将 UI 组件绑定到指定 bean 类的属性，简化了绑定过程并减少了手动设置。它根据名称将可绑定组件（在表单或应用中声明为字段）与 bean 属性对齐。

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

使用 `UseProperty` 注解来指定 bean 属性名称，当 UI 字段名称与 bean 属性名称不匹配时。

```java
public class HeroRegistration extends App {
  // 可绑定组件
  @UseProperty("name")
  TextField nameField = new TextField("文本字段");
  ComboBox power = new ComboBox("能力");

  // ...
}
```

在上面的示例中，UI 字段名称为 `nameField`，但 bean 属性为 `name`。可以使用 bean 属性名称注解 UI 字段以确保正确绑定。

### `BindingExclude` 注解 {#bindingexclude-annotation}

使用 `BindingExclude` 注解可以在您想手动绑定或完全排除组件时，将组件从自动绑定配置中排除。

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

使用 `UseValidator` 注解来声明在绑定过程中强制实施附加验证规则的验证器。验证器按您指定的顺序应用。

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("邮箱地址");
}
```

### `UseTransformer` 注解 {#usetransformer-annotation}

使用 `UseTransformer` 注解可以直接在 UI 字段上声明转换器类。`BindingContext` 会自动应用指定的转换器。

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
  TextField IDField = new TextField("用户ID");
}
```

### `BindingRequired` 注解 {#bindingrequired-annotation}

使用 `BindingRequired` 来标记绑定为必需。另见 [必需的绑定检测](#required-binding-detections)。

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("用户邮箱");
}
```

## 自动写入数据 {#writing-data-automatically}

为了增强应用程序的响应性和动态性，您可以使用 `observe` 方法。该方法确保 UI 组件中的更改立即传播到数据模型。这在需要数据模型与 UI 之间持续同步时特别有用。

`observe` 方法在上下文中所有绑定上注册 `ValueChangeEvent` 监听器，以监控用户的更改，然后立即将这些更改写入模型的绑定属性（如果它们有效）。当您首次调用此方法时，它会将 bean 属性反映到 UI 组件中。

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
    // 对 bean 进行操作。
  }
});
```

:::info 更新方向
这种自动绑定是单向的；当您更新 UI 组件时，模型中的更新会反映，但模型中的更改仅在您首次调用该方法时反映到 UI 组件中。
:::

:::tip 注意事项
虽然 `observe` 提高了应用程序的互动性，但重要的是要谨慎使用：

- **性能影响**：频繁的更新可能会影响性能，特别是在模型复杂或后端服务缓慢时。
- **用户体验**：自动更新不应干扰用户舒适输入数据的能力。
:::

## 必需的绑定检测 {#required-binding-detections}

当您将绑定标记为必需时，它将组件标记为必需，前提是该组件通过 `RequiredAware` 接口支持此状态。绑定本身不强制执行此状态，而是在适用时将其设置在组件上。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

使用 [Jakarta 注解](./validation/jakarta-validation.md) 时，绑定可以自动检测根据以下任一注解在 bean 属性上的存在来确定必需状态：

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`
