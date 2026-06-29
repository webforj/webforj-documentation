---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) 被广泛认可为将验证逻辑集成到 Java 应用程序中的标准。这种标准化的验证方式允许开发人员使用声明式验证约束注释域模型属性。这些约束在运行时被强制执行，提供对内置和自定义规则的选项。

webforJ 通过 `JakartaValidator` 适配器与 Bean Validation 集成，开箱即用地提供完全支持。

## 安装 {#installation}

您需要在类路径中包含一个兼容的实现，例如 [Hibernate Validator](https://hibernate.org/validator/)。如果您的环境没有默认包含此实现，可以通过使用以下 Maven 依赖项手动添加：

```xml
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>8.0.1.Final</version>
</dependency>
<dependency>
  <groupId>org.glassfish.expressly</groupId>
  <artifactId>expressly</artifactId>
  <version>5.0.0</version>
</dependency>
```

## `JakartaValidator` {#the-jakartavalidator}

`JakartaValidator` 类作为适配器，桥接了 webforJ 绑定上下文和 Jakarta Validation。这一集成使得可以直接通过注释在 bean 类中使用复杂的验证规则。

### 激活 `JakartaValidator` {#activating-jakartavalidator}

要在整个上下文中激活 `JakartaValidator`，通常在构建 `BindingContext` 时使用 `useJakartaValidator` 参数。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### 为 bean 属性定义约束 {#defining-constraints-for-bean-properties}

基于注释的约束直接应用于 bean 类中，以指定验证条件，如以下示例所示：

```java
public class Hero {
  @NotEmpty(message = "姓名不能为空")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "未指定的能力")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "无效的能力")
  private String power;

  // getters and setters
}
```

这样的约束与绑定初始化期间程序设置的约束同样有效，并产生一致的验证结果。

:::warning
目前，`JakartaValidator` 仅识别直接分配给属性的约束，忽略任何未直接与属性相关的验证。
:::

### 验证嵌套 bean <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

直接在嵌套 bean 的字段上声明约束。当通过 [点分属性路径](/docs/data-binding/bindings#nested-bean-properties) 绑定其中一个字段时，该属性上的约束以与顶级属性相同的方式应用于绑定。

```java
public class Address {
  @NotBlank(message = "街道是必需的")
  @Size(max = 80, message = "街道太长")
  private String street;

  // getters and setters
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "姓名不能为空")
  @Length(min = 3, max = 20)
  private String name;

  // 一个带有 address.street 约束的嵌套 bean
  private Address address;

  // getters and setters
}
```

绑定 `address.street` 会验证 `Address.street` 上的 `@NotBlank`。每个绑定在其路径末尾对属性进行验证。

[嵌套 bean 示例](https://github.com/webforj/built-with-webforj) 通过此模式使用单个 `BindingContext` 绑定带有嵌套的 `Address` 和 `EmergencyContact` 的 `Employee`。

### 支持区域感知的验证消息 <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation 通过标准消息插值支持本地化的约束消息。当您更改应用程序的区域设置时，`JakartaValidator` 需要知道新的区域，以便能够以正确的语言解析消息。

`JakartaValidator` 实现了 `LocaleAware` 接口，这意味着 `BindingContext.setLocale()` 会自动将区域传播到上下文中的所有 Jakarta 验证器。您不需要手动更新每个验证器。

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// 当区域更改时，Jakarta 验证器会自动
// 生成新的区域中的消息
context.setLocale(Locale.GERMAN);
```

在实现 `LocaleObserver` 的组件中，在 `onLocaleChange()` 中调用 `context.setLocale()` 以保持验证消息与 UI 语言同步：

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

有关区域感知验证器的更多信息，请参阅 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages)。
