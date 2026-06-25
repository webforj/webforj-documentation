---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
在 webforJ 中，绑定将 Java Bean 的特定属性链接到 UI 组件。这种链接使得 UI 和后端模型之间能够自动更新。每个绑定可以处理数据同步、验证、转换和事件管理。

您只能通过 `BindingContext` 启动绑定。它管理绑定实例的集合，每个实例将 UI 组件链接到一个 bean 的属性。它方便地对绑定进行分组操作，例如验证和 UI 组件与 bean 属性之间的同步。它充当一个聚合器，允许对多个绑定进行集体操作，从而简化应用程序内的数据流管理。

:::tip 自动绑定
本节介绍手动配置绑定的基础知识。此外，您可以根据表单中的 UI 组件自动创建绑定。一旦您掌握了基础知识，可以通过阅读 [自动绑定](/docs/data-binding/automatic-binding) 部分了解更多。
:::

## 配置绑定 {#configure-bindings}

首先创建一个新的 `BindingContext` 实例，该实例管理特定模型的所有绑定。此上下文对所有绑定进行集体验证和更新。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
每个表单应只有一个 `BindingContext` 实例，您应对表单中的所有组件使用该实例。
:::

### 绑定属性 {#the-bound-property}

绑定属性是 Java Bean 的特定字段或属性，可以链接到您应用中的 UI 组件。此链接允许 UI 中的更改直接影响数据模型的相应属性，反之亦然，因此 UI 和数据模型保持同步。

在设置绑定时，您应该提供属性名称作为字符串。此名称必须与 Java Bean 类中的字段名称匹配。以下是一个简单的示例：

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

`bind` 方法返回一个 `BindingBuilder`，用于创建 `Binding` 对象，您可以使用它配置绑定的多个设置，`add` 方法则是实际将绑定添加到上下文中。

### 绑定组件 {#the-bound-component}

绑定的另一侧是绑定组件，指的是与 Java Bean 属性交互的 UI 组件。绑定组件可以是支持用户交互和显示的任何 UI 组件，例如文本字段、组合框、复选框，或者任何实现了 `ValueAware` 接口的自定义组件。

绑定组件作为用户与底层数据模型交互的节点。它向用户显示数据，同时也捕获用户输入，这些输入随后被传回模型。

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## 读取和写入数据 {#reading-and-writing-data}

### 读取数据 {#reading-data}

读取数据涉及使用数据模型中的值填充 UI 组件。这通常在表单首次显示时或您需要由于底层模型的更改而重新加载数据时进行。`BindingContext` 提供的 `read` 方法使此过程变得简单明了。

```java
// 假设 Hero 对象已被实例化和初始化
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext 已配置好绑定
context.read(hero);
```

在此示例中，`read` 方法接收 `Hero` 的实例，并更新所有绑定的 UI 组件以反映英雄的属性。如果英雄的名称或能力更改，相应的 UI 组件（例如，用于名称的 `TextField` 和用于能力的 `ComboBox`）将显示这些新值。

### 写入数据 {#writing-data}

写入数据涉及从 UI 组件收集值并更新数据模型。这通常在用户提交表单时发生。`write` 方法在一个步骤中处理验证和模型更新。

```java
// 这可以通过表单提交事件触发
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

在上面的代码中，当用户点击提交按钮时，调用 `write` 方法。它执行所有配置的验证，并且如果数据通过所有检查，则使用来自绑定组件的新值更新 `Hero` 对象。如果数据有效，您可以将其保存到数据库或进一步处理。如果有验证错误，您应该适当处理，通常是通过向用户显示错误消息。

:::tip 验证错误报告
webforJ 的所有核心组件都有默认配置，可以自动报告验证错误，既可以内联也可以通过弹出窗口。您可以使用 [报告者](./validation/reporters.md) 自定义此行为。
:::

## 嵌套 bean 属性 <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

绑定属性可以是指向嵌套 bean 内部属性的点路径。路径中的每个部分遵循标准 JavaBean 获取和设置约定，因此 `address.street` 通过 `getAddress().getStreet()` 读取，通过 `getAddress().setStreet()` 写入。

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

在读取时，即使中间的 bean 为 `null`，路径解析也是安全的。如果 `Hero` 没有 `Address`，则绑定到 `address.street` 和 `address.city` 的组件读取为空，而不是抛出异常，因此表单仍然可以填充。

在写入时，上下文通过其无参数构造函数创建任何缺失的中间 bean，因此将表单写入没有 `Address` 的 `Hero` 时，会生成一个新的、填充的 `Address`。现有的 `Address` 会被重用。

对嵌套属性的 [Jakarta validation](/docs/data-binding/validation/jakarta-validation) 注解的检测方式与顶层属性相同。例如，在 `Address.street` 上的 `@NotNull` 注解将 `address.street` 绑定标记为 [required](/docs/data-binding/automatic-binding#bindingrequired-annotation)。

:::info 路径在前被验证
当您调用 `bind` 时，将验证完整路径。如果路径中的任何部分有拼写错误，无论是在顶层还是深入路径中，都会抛出 `IllegalArgumentException`，因此绑定错误会立即暴露，而不是在读取或写入时。
:::

<!-- vale off -->
## 只读数据 {#readonly-data}
<!-- vale on -->

在某些情况下，您可能希望您的应用程序显示数据，但不允许最终用户通过 UI 直接修改它。只读数据绑定解决了这一问题。webforJ 支持将绑定配置为只读，因此您可以显示数据，但不能通过绑定的 UI 组件编辑它。

### 配置只读绑定 {#configuring-readonly-bindings}

要设置只读绑定，您可以将绑定配置为关闭或忽略 UI 组件输入。这样，从 UI 的角度来看，数据保持不变，但在需要时仍然可以以编程方式更新。

```java
// 在绑定上下文中将文本字段配置为只读
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

在此配置中，`readOnly` 阻止 `nameTextField` 接受用户输入，因此文本字段显示数据，而不允许修改。

:::info
只有当 UI 组件实现 `ReadOnlyAware` 接口时，绑定才能将组件标记为只读。
:::

:::tip 组件只读与绑定只读
重要的是区分您配置为只读的绑定和您设置为以只读方式显示的 UI 组件。当您将绑定标记为只读时，它会影响绑定在写入过程中如何管理数据，而不仅仅是 UI 行为。

当您将绑定标记为只读时，系统会跳过数据更新。对 UI 组件的任何更改不会传回数据模型。因此，即使 UI 组件以某种方式接收到用户输入，它也不会更新底层数据模型。维持这种分离保护了数据的完整性，以防用户操作不应更改数据。

相反，将 UI 组件设置为只读，而不将绑定本身配置为只读，只是阻止用户对 UI 组件进行更改，但不会阻止绑定在程序或其他方式发生变化时更新数据模型。
:::

## 绑定 getter 和 setter {#binding-getters-and-setters}

Setter 和 getter 是 Java 中分别设置和获取属性值的方法。在数据绑定的上下文中，它们用于定义如何在绑定框架内更新和检索属性。

### 自定义 setter 和 getter {#customizing-setters-and-getters}

虽然 webforJ 可以自动使用标准 JavaBean 命名约定（例如，将 `getName()`、`setName()` 用于属性 `name`），但您可能需要定义自定义行为。这在属性不遵循常规命名或数据处理需要额外逻辑时是必要的。

### 使用自定义 getter {#using-custom-getters}

自定义 getter 在值检索过程涉及的不仅仅是返回属性时使用。例如，您可能希望格式化字符串、计算值或在访问属性时记录某些操作。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // 自定义逻辑：将名称转为大写
  });
```

### 使用自定义 setter {#using-custom-setters}

自定义 setter 在设置属性涉及额外操作（例如验证、转换或副作用，如记录或通知应用的其他部分）时发挥作用。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("将名称从 " + hero.getName() + " 更新为 " + name);
    hero.setName(name); // 额外操作：记录
  });
```
