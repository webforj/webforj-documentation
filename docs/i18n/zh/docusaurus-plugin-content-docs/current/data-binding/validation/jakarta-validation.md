---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: fa09682ac85db8e2c53ff9eea2d0633e
---
[Java Bean Validation](https://beanvalidation.org/) 被广泛认为是将验证逻辑集成到 Java 应用程序中的标准。它通过允许开发人员使用声明性验证约束注释领域模型属性，采用统一的验证方法。这些约束在运行时强制执行，支持内置规则和自定义规则的选项。

webforJ 通过 `JakartaValidator` 适配器与 Bean Validation 集成，开箱即用提供全面支持。

## 安装 {#installation}

有必要在您的类路径中包含一个兼容的实现，如 [Hibernate Validator](https://hibernate.org/validator/)。如果您的环境默认没有此实现，则可以通过使用以下 Maven 依赖项手动添加它：

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

`JakartaValidator` 类作为适配器，桥接 webforJ 绑定上下文与 Jakarta Validation。此集成使得可以直接通过 Bean 类中的注释使用复杂的验证规则。

### 激活 `JakartaValidator` {#activating-jakartavalidator}

要在整个上下文中激活 `JakartaValidator`，通常在构建 `BindingContext` 时使用 `useJakartaValidator` 参数。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### 为 Bean 属性定义约束 {#defining-constraints-for-bean-properties}

基于注释的约束被直接应用于 Bean 类中，以指定验证条件，如下例所示：

```java
public class Hero {
  @NotEmpty(message = "名称不能为空")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "未指定的能力")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "无效的能力")
  private String power;

  // getters and setters
}
```

这样的约束与在绑定初始化期间以编程方式设置的约束同样有效，并产生一致的验证结果。

:::warning
当前，`JakartaValidator` 仅识别直接分配给属性的约束，并忽略与属性无直接关联的任何验证。
:::

### 支持区域设置的验证消息 <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation 通过标准消息插值支持本地化约束消息。当您更改应用程序区域时，`JakartaValidator` 需要知道新区域，以便它可以以正确的语言解析消息。

`JakartaValidator` 实现了 `LocaleAware` 接口，这意味着 `BindingContext.setLocale()` 会自动将区域传播到上下文中的所有 Jakarta 验证器。您无需手动更新每个验证器。

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// 当区域更改时，Jakarta 验证器会自动
// 以新的区域生成消息
context.setLocale(Locale.GERMAN);
```

在实现 `LocaleObserver` 的组件中，在 `onLocaleChange()` 内调用 `context.setLocale()` 以保持验证消息与 UI 语言同步：

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

有关区域感知验证器的更多信息，请参见 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages)。
