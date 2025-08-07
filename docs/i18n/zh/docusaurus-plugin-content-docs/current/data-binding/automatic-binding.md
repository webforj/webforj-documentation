---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: 170c308c3b93a933f5fb85c0f0ec4f15
---
webforJ 提供了几个功能，可以简化开发人员的配置和自动绑定过程。本节演示了如何有效使用这些功能。

## 使用 `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` 方法自动将 UI 组件绑定到指定 bean 类的属性，简化了绑定过程并减少了手动设置。它根据名称将声明为表单或应用程序字段的可绑定组件与 bean 属性对齐。

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

使用 `UseProperty` 注解在 UI 字段名称与 bean 属性名称不匹配时指定 bean 属性名称。

```java
public class HeroRegistration extends App {
  // 可绑定组件
  @UseProperty("name")
  TextField nameField = new TextField("文本字段");
  ComboBox power = new ComboBox("能力");

  // ...
}
```

在上面的示例中，UI 字段名称是 `nameField` ，但 bean 属性为 `name` 。您可以使用 bean 属性名称对 UI 字段进行注解，以确保正确绑定。

### `BindingExclude` 注解 {#bindingexclude-annotation}

使用 `BindingExclude` 注解在您希望手动绑定组件或完全排除它时，将其从自动绑定配置中排除。

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

使用 `UseValidator` 注解声明在绑定期间强制执行的验证器，验证器按您指定的顺序应用。

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("电子邮箱地址");
}
```

### `UseTransformer` 注解 {#usetransformer-annotation}

使用 `UseTransformer` 注解直接在 UI 字段上声明转换器类。`BindingContext` 会自动应用指定的转换器。

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("日期字段");
}
```

### `BindingReadOnly` 注解 {#bindingreadonly-annotation}

使用 `BindingReadOnly` 来[标记绑定为只读](./bindings/#configuring-readonly-bindings)。

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("用户 ID");
}
```

### `BindingRequired` 注解 {#bindingrequired-annotation}

使用 `BindingRequired` 标记绑定为必填。另请参见 [必填绑定检测](#required-binding-detections)。

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("用户电子邮箱");
}
```

## 自动写入数据 {#writing-data-automatically}

为了增强应用程序的响应能力和动态性，您可以使用 `observe` 方法。此方法确保 UI 组件中的更改立即传播到数据模型。当您需要在数据模型和 UI 之间进行持续同步时，它特别有用。

`observe` 方法在上下文中为所有绑定注册 `ValueChangeEvent` 监听器，以监视用户所做的更改，然后如果更改有效，它立即将这些更改写入模型的绑定属性。当您第一次调用此方法时，它会将 bean 属性反映到 UI 组件中。

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
    // 对 bean 采取行动。
  }
});
```

:::info 更新说明
这个自动绑定是单向的；当您更新 UI 组件时，更新会反映在模型中，但模型中的更改只在您第一次调用方法时反映在 UI 组件中。
::: 

:::tip 注意事项
虽然 `observe` 增加了应用程序的交互性，但使用时要谨慎：

- **性能影响**：频繁更新可能会影响性能，特别是在使用复杂模型或慢速后端服务时。
- **用户体验**：自动更新不应干扰用户舒适地输入数据的能力。
:::

## 必填绑定检测 {#required-binding-detections}

当您将绑定标记为必填时，它将组件标记为必填，前提是该组件通过 `RequiredAware` 接口支持此状态。绑定本身并不强制执行此状态，而是在适用时将其设置在组件上。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

使用 [Jakarta 注解](./validation/jakarta-validation.md) 时，绑定可以根据 bean 属性上存在的以下任何注解自动检测必填状态：

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
