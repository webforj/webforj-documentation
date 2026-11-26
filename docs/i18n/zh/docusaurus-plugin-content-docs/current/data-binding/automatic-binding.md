---
sidebar_position: 5
title: 自动绑定
description: >-
  使用 UseProperty、BindingExclude 和 UseValidator 注解，通过 BindingContext.of 自动将 UI
  字段绑定到 bean 属性。
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ 提供了多项功能，可简化开发者的配置和自动绑定流程。本节将演示如何有效使用这些功能。

## 使用 `BindingContext.of` {#using-bindingcontextof}

`BindingContext.of` 方法会自动将 UI 组件绑定到指定 Bean 类的属性，从而简化绑定流程并减少手动配置。它会根据名称，将表单或应用中声明为字段的可绑定组件与 Bean 属性对应起来。

```java
public class HeroRegistration extends App {
  // Bindable components
  TextField name = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

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

  // Setters and getters
}
```

### `UseProperty` 注解 {#useproperty-annotation}

如果要将 Bean 属性绑定到名称不同的 UI 组件，请使用 `UseProperty` 注解。
此注解可以更精确地将 Bean 属性绑定到 UI 组件，在处理[嵌套 Bean 属性](/docs/data-binding/bindings#nested-bean-properties)时尤其有用。

```java
public class HeroRegistration extends App {
  // Binds to the name property
  @UseProperty("name")
  TextField nameField = new TextField("Name");

  // Binds to the nested address.street property
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  // Binds to the power property
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `BindingExclude` 注解 {#bindingexclude-annotation}

如果希望手动绑定某个组件或完全排除该组件，请使用 `BindingExclude` 注解，将其从自动绑定配置中排除。

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `UseValidator` 注解 {#usevalidator-annotation}

使用 `UseValidator` 注解声明验证器，以便在绑定过程中强制执行额外的验证规则。验证器会按照指定顺序应用。

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### `UseTransformer` 注解 {#usetransformer-annotation}

使用 `UseTransformer` 注解可直接在 UI 字段上声明转换器类。`BindingContext` 会自动应用指定的转换器。

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### `BindingReadOnly` 注解 {#bindingreadonly-annotation}

使用 `BindingReadOnly` 将绑定[标记为只读](./bindings/#configuring-readonly-bindings)。

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### `BindingRequired` 注解 {#bindingrequired-annotation}

使用 `BindingReadOnly` 将绑定标记为必填。另请参阅[必填绑定检测](#required-binding-detections)。


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## 自动写入数据 {#writing-data-automatically}

为了增强应用的响应能力和动态性，可以使用 `observe` 方法。此方法可确保 UI 组件中的更改立即同步到数据模型。当需要在数据模型与 UI 之间持续同步时，此方法尤其有用。

`observe` 方法会为上下文中的所有绑定注册 `ValueChangeEvent` 监听器，以监控用户所做的更改，并在更改有效时立即将其写入模型的绑定属性。首次调用此方法时，它会将 Bean 属性反映到 UI 组件中。

以下示例展示了如何使用 `observe`：

```java
Hero bean = new Hero("Superman", "Fly");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Take action with the bean.
  }
});
```

:::info 更新方向
此自动绑定是单向的：更新 UI 组件时，更改会同步到模型；而模型中的更改只会在首次调用该方法时反映到 UI 组件中。
:::

:::tip 注意事项
虽然 `observe` 可以提升应用的交互性，但请谨慎使用：

- **性能影响**：频繁更新可能会影响性能，尤其是在模型复杂或后端服务响应缓慢时。
- **用户体验**：自动更新不应妨碍用户顺畅地输入数据。
:::


## 必填绑定检测 {#required-binding-detections}

将绑定标记为必填时，如果组件通过 `RequiredAware` 接口支持此状态，该组件也会被标记为必填。绑定本身不会强制执行此状态，而是在适用时将该状态设置到组件上。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 使用 [Jakarta 注解](./validation/jakarta-validation.md)时，绑定可以根据 Bean 属性上是否存在以下任一注解，自动检测必填状态：

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note 使用 `@Size` 设置必填绑定 <DocChip chip='since' label='26.10' />
只有在定义了 `min` 值时，`@Size` 才会将绑定的组件标记为必填。
在早期版本中，`@Size` 始终会将绑定的组件标记为必填。
:::
