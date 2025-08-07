---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: 68a57d576ce21a9f99b121e5db3cf85f
---
[Java Bean Validation](https://beanvalidation.org/) 被广泛认可为将验证逻辑集成到 Java 应用程序中的标准。它通过允许开发者使用声明性验证约束对领域模型属性进行注释，采用统一的验证方法。这些约束在运行时强制执行，支持内置规则和自定义规则。

webforJ 通过 `JakartaValidator` 适配器与 Bean Validation 无缝集成，提供开箱即用的强大支持。

## 安装 {#installation}

必须在类路径中包含一个兼容的实现，例如 [Hibernate Validator](https://hibernate.org/validator/)。如果您的环境默认没有此实现，可以通过以下 Maven 依赖手动添加：

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

`JakartaValidator` 类作为适配器，将 webforJ 绑定上下文与 Jakarta 验证连接起来。此集成使得可以直接通过 bean 类中的注解使用复杂的验证规则。

### 激活 `JakartaValidator` {#activating-jakartavalidator}

要在整个上下文中激活 `JakartaValidator`，通常在构造 `BindingContext` 时使用 `useJakartaValidator` 参数。

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### 为 bean 属性定义约束 {#defining-constraints-for-bean-properties}

基于注解的约束直接应用于 bean 类中，以指定验证条件，如下例所示：

```java
public class Hero {
  @NotEmpty(message = "名称不能为空")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "未指定能力")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "无效的能力")
  private String power;

  // getters and setters
}
```

这些约束的效果与在绑定初始化期间以编程方式设置的约束相同，确保一致的验证结果。

:::warning
目前，`JakartaValidator` 仅识别直接分配给属性的约束，并忽略与属性没有直接关联的任何验证。
:::
