---
sidebar_position: 6
title: Jakarta Validation
---

[Java Bean Validation](https://beanvalidation.org/) is widely recognized as the standard for integrating validation logic into Java applications. It utilizes a uniform approach to validation by allowing developers to annotate domain model properties with declarative validation constraints. These constraints are enforced at runtime, with options for both built-in and custom-defined rules.

webforJ seamlessly integrates with Bean Validation through the `JakartaValidator` adapter, providing robust support out of the box.

## Installation

It's necessary to include a compatible implementation, such as [Hibernate Validator](https://hibernate.org/validator/), in your classpath. If your environment doesn't come with this implementation by default, you can add it manually by using the following Maven dependencies:

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

## The `JakartaValidator`

The `JakartaValidator` class serves as an adapter, bridging the webforJ binding context with Jakarta Validation. This integration enables the use of complex validation rules directly via annotations in the bean class.

### Activating `JakartaValidator`

To activate the `JakartaValidator` across the entire context, you typically use the `useJakartaValidator` parameter when constructing the `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Defining constraints for bean properties

Annotation-based constraints are directly applied within the bean class to specify validation conditions, as illustrated in the example below:

```java
public class Hero {
  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Invalid power")
  private String power;

  // getters and setters
}
```

Such constraints are as effective as those set programmatically during the binding initialization, ensuring consistent validation outcomes.

:::warning
Currently, the `JakartaValidator` only recognizes constraints that are directly assigned to properties and ignores any validations not directly associated with properties.
:::


