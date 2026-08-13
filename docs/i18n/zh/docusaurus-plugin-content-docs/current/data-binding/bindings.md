---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
在 webforJ 中，绑定将 Java Bean 的特定属性链接到 UI 组件。这种链接可以实现 UI 和后端模型之间的自动更新。每个绑定都可以处理数据同步、验证、转换和事件管理。

您只能通过 `BindingContext` 启动绑定。它管理一组绑定实例，每个实例将一个 UI 组件链接到一个 bean 的属性。它便于对绑定进行组操作，例如验证和 UI 组件与 bean 属性之间的同步。它充当聚合器，允许对多个绑定进行集体操作，从而简化应用程序中数据流的管理。

:::tip 自动绑定
本节介绍手动配置绑定的基础知识。此外，您还可以根据表单中的 UI 组件自动创建绑定。一旦您掌握了基础知识，可以通过阅读 [Automatic Binding](/docs/data-binding/automatic-binding) 部分进一步了解。
:::

## 配置绑定 {#configure-bindings}

首先创建一个新的 `BindingContext` 实例，该实例管理特定模型的所有绑定。
此上下文可以集体验证和更新所有绑定。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
每个表单应该只有一个 `BindingContext` 实例，您应该对表单中的所有组件使用此实例。
:::

### 被绑定的属性 {#the-bound-property}

绑定属性是 Java Bean 的特定字段或属性，可以链接到您应用中的 UI 组件。
这种链接允许 UI 的变化直接影响数据模型的相应属性，反之亦然，因此 UI 和数据模型保持同步。

在设置绑定时，您应该提供属性名称作为字符串。该名称必须与 Java Bean 类中的字段名称匹配。下面是一个简单的示例：

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

`bind` 方法返回一个 `BindingBuilder`，它创建 `Binding` 对象，您可以用它来配置绑定多种设置，`add` 方法实际上将绑定添加到上下文中。

### 被绑定的组件 {#the-bound-component}

绑定的另一侧是被绑定的组件，它指的是与 Java Bean 属性交互的 UI 组件。
被绑定的组件可以是任何支持用户交互和显示的 UI 组件，例如文本字段、组合框、复选框或任何实现了 `ValueAware` 接口的自定义组件。

被绑定的组件作为用户与底层数据模型交互的节点。
它向用户显示数据，并捕获用户输入，然后再传播回模型。

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## 读取和写入数据 {#reading-and-writing-data}

### 读取数据 {#reading-data}

读取数据涉及使用数据模型中的值填充 UI 组件。
通常是在首次显示表单时，或者当您需要因底层模型的更改重新加载数据时执行此操作。
`BindingContext` 提供的 `read` 方法使此过程变得简单。

```java
// 假设 Hero 对象已经实例化并初始化
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext 已通过绑定进行配置
context.read(hero);
```

在这个例子中，`read` 方法接受一个 `Hero` 实例，并更新所有绑定的 UI 组件，以反映英雄的属性。
如果英雄的名字或能力发生变化，相关的 UI 组件（如名字的 `TextField` 和能力的 `ComboBox`）将显示这些新值。

### 写入数据 {#writing-data}

写入数据涉及从 UI 组件收集值并更新数据模型。
这通常发生在用户提交表单时。`write` 方法在一步中处理验证和模型更新。

```java
// 这可能通过表单提交事件触发
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // 数据有效，英雄对象已更新
    // repository.save(hero);
  } else {
    // 处理验证错误
    // results.getMessages();
  }
});
```

在上面的代码中，当用户点击提交按钮时，将调用 `write` 方法。
它执行所有配置的验证，并且如果数据通过所有检查，将使用来自绑定组件的新值更新 `Hero` 对象。
如果数据有效，您可能会保存到数据库或进一步处理。如果存在验证错误，您应适当处理，通常方法是向用户显示错误消息。

:::tip 验证错误报告
webforJ 的所有核心组件都有默认配置，可以自动报告验证错误，无论是内联还是通过弹出框。您可以使用 [Reporters](./validation/reporters.md) 自定义此行为。
:::

## 嵌套 Bean 属性 <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

绑定属性可以是指向嵌套 bean 内部属性的点路径。路径中的每个段都遵循标准 JavaBean 获取器和设置器约定，因此 `address.street` 通过 `getAddress().getStreet()` 读取，通过 `getAddress().setStreet()` 写入。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters and setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters and setters
}
```

在读取时，即使中间 bean 为 `null`，路径也会安全解析。如果一个 `Hero` 没有 `Address`，绑定到 `address.street` 和 `address.city` 的组件将以空值读取，而不会抛出异常，因此表单仍然会填充。

在写入时，上下文通过其无参数构造函数创建任何缺失的中间 bean，因此将表单写入没有 `Address` 的 `Hero` 会生成新的、填充的 `Address`。现有的 `Address` 将被重用。

嵌套属性上的 [Jakarta validation](/docs/data-binding/validation/jakarta-validation) 注释与顶层属性的检测方式相同。诸如 `@NotNull` 的注释放在 `Address.street` 上，标记 `address.street` 绑定为 [required](/docs/data-binding/automatic-binding#bindingrequired-annotation)。

:::info 路径在前期进行验证
当您调用 `bind` 时，整个路径会被验证。路径中任何段的拼写错误，无论是在顶层还是在路径更深处，都会抛出 `IllegalArgumentException`，因此绑定错误会立即显现，而不是在读取或写入时。
:::

<!-- vale off -->
## 只读数据 {#readonly-data}
<!-- vale on -->

在某些情况下，您可能希望您的应用显示数据，但不允许最终用户直接通过 UI 修改数据。
只读数据绑定解决了这个问题。webforJ 支持将绑定配置为只读，因此您可以显示数据，但不能通过绑定的 UI 组件进行编辑。

### 配置只读绑定 {#configuring-readonly-bindings}

要设置只读绑定，您可以配置绑定以关闭或忽略 UI 组件输入。
这样，数据在 UI 角度保持不变，同时在需要时仍然可以按程序更新。

```java
// 在绑定上下文中配置文本字段为只读
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

在此配置中，`readOnly` 阻止 `nameTextField` 接受用户输入，因此文本字段显示数据而不允许修改。

:::info
只有当 UI 组件实现 `ReadOnlyAware` 接口时，绑定才能将组件标记为只读。
:::

:::tip 组件只读与绑定只读
区分您配置为只读的绑定与您设置为显示为只读的 UI 组件是很重要的。
当您将绑定标记为只读时，它影响绑定在写入过程中的数据管理方式，而不仅仅是 UI 行为。

当您将绑定标记为只读时，系统会跳过数据更新。对 UI 组件的任何更改都不会传输回数据模型。
因此，即使 UI 组件以某种方式接收到用户输入，它也不会更新底层数据模型。
维护此分离可保护在用户操作不应更改数据的场景中的数据完整性。

相反，将 UI 组件设置为只读，而不将绑定本身配置为只读，仅仅是阻止用户对 UI 组件进行更改，但如果通过程序或其他方式发生更改，绑定不会停止更新数据模型。
:::

## 绑定 getter 和 setter {#binding-getters-and-setters}

设置器和获取器是 Java 中分别设置和获取属性值的方法。
在数据绑定的上下文中，它们用于定义属性在绑定框架内如何更新和检索。

### 自定义设置器和获取器 {#customizing-setters-and-getters}

尽管 webforJ 可以自动使用标准 JavaBean 命名约定（例如，对于属性 `name`，使用 `getName()`、`setName()`），但您可能需要定义自定义行为。
当属性不遵循常规命名，或当数据处理需要额外的逻辑时，这就变得必要。

### 使用自定义获取器 {#using-custom-getters}

当值检索过程涉及的不仅仅是返回属性时，使用自定义获取器。
例如，您可能想格式化字符串、计算值或在访问属性时记录某些操作。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // 自定义逻辑：将名称转换为大写
  });
```

### 使用自定义设置器 {#using-custom-setters}

当设置属性涉及额外操作（例如验证、转换或日志记录等副作用）时，使用自定义设置器。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("正在更新姓名从 " + hero.getName() + " 到 " + name);
    hero.setName(name); // 额外操作：日志记录
  });
```
