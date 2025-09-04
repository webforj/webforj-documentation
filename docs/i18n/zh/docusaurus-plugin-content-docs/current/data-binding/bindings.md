---
sidebar_position: 2
title: Bindings
_i18n_hash: 0afea0971d509f25324b46172b5e020e
---
在webforJ中，绑定将Java Bean的特定属性与UI组件链接起来。这个链接使得UI与后端模型之间能够自动更新。每个绑定可以处理数据同步、验证、转换和事件管理。

您只能通过`BindingContext`来启动绑定。它管理一组绑定实例，每个绑定将一个UI组件链接到一个bean的属性。它便于对绑定执行分组操作，例如验证和UI组件与bean属性之间的同步。它充当聚合器，允许对多个绑定进行集体操作，从而简化应用程序中数据流的管理。

:::tip 自动绑定
本节介绍手动配置绑定的基础知识。此外，您可以根据表单中的UI组件自动创建绑定。一旦掌握了基础知识，可以通过阅读[自动绑定](./automatic-binding)部分了解更多内容。
:::

## 配置绑定 {#configure-bindings}

首先创建一个新的`BindingContext`实例，它管理特定模型的所有绑定。该上下文确保所有绑定可以被集体验证和更新。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
每个表单应只有一个`BindingContext`实例，并且您应使用此实例处理表单中的所有组件。
:::

### 绑定属性 {#the-bound-property}

绑定属性是Java Bean的特定字段或属性，可以与应用程序中的UI组件链接。这个链接允许UI中的变化直接影响数据模型的相应属性，反之亦然，从而促进反应式用户体验。

在设置绑定时，您应提供属性名称作为字符串。此名称必须与Java Bean类中的字段名称匹配。以下是一个简单示例：

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

`bind`方法返回一个`BindingBuilder`，该对象创建`Binding`对象，并可用于配置绑定的多项设置，`add`方法则是将绑定实际添加到上下文中的方法。

### 绑定组件 {#the-bound-component}

绑定的另一侧是绑定组件，它指的是与Java Bean属性交互的UI组件。绑定组件可以是任何支持用户交互和显示的UI组件，例如文本字段、下拉框、复选框或实现`ValueAware`接口的任何自定义组件。

绑定组件作为用户与底层数据模型的交互点。它向用户显示数据，并捕获用户输入，然后将其传播回模型。

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## 读取和写入数据 {#reading-and-writing-data}

### 读取数据 {#reading-data}

读取数据涉及用数据模型中的值填充UI组件。这通常在表单首次显示时，或当您需要由于底层模型的变化而重新加载数据时进行。`BindingContext`提供的`read`方法使这一过程变得简单。

```java
// 假设Hero对象已被实例化和初始化
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext已经配置了绑定
context.read(hero);
```

在此示例中，`read`方法接收一个`Hero`实例，并更新所有绑定的UI组件，以反映英雄的属性。如果英雄的姓名或能力发生变化，相应的UI组件（如姓名的`TextField`和能力的`ComboBox`）将显示这些新值。

### 写入数据 {#writing-data}

写入数据涉及从UI组件收集值并更新数据模型。这通常在用户提交表单时发生。`write`方法在一个步骤中处理验证和模型更新。

```java
// 这可能由表单提交事件触发
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // 数据有效，hero对象已被更新
    // repository.save(hero); 
  } else {
    // 处理验证错误
    // results.getMessages();
  }
});
```

在上面的代码中，当用户单击提交按钮时，将调用`write`方法。它执行所有配置的验证，并且如果数据通过所有检查，将使用绑定组件中的新值更新`Hero`对象。如果数据有效，您可能将其保存到数据库或进一步处理。如果存在验证错误，您应适当处理，通常是通过向用户显示错误消息。

:::tip 验证错误报告
webforJ的所有核心组件都具有默认配置，能够自动报告验证错误，无论是内联显示还是通过弹出窗口。您可以使用[报告者](./validation/reporters.md)自定义此行为。
:::

<!-- vale off -->
## 只读数据 {#readonly-data}
<!-- vale on -->

在某些情况下，您可能希望您的应用程序显示数据，但不允许最终用户通过UI直接修改它。这时只读数据绑定变得至关重要。webforJ支持将绑定配置为只读，确保您可以显示数据，但不能通过绑定的UI组件编辑数据。

### 配置只读绑定 {#configuring-readonly-bindings}

要设置只读绑定，您可以配置绑定以关闭或忽略UI组件输入。这确保数据从UI的角度保持不变，同时如果需要仍然可以以编程方式更新。

```java
// 在绑定上下文中配置文本字段为只读
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

在此配置中，`readOnly`确保`nameTextField`不接受用户输入，有效地使文本字段显示数据，而不允许修改。

:::info
只有当UI组件实现`ReadOnlyAware`接口时，绑定才能将组件标记为只读。
:::

:::tip 组件只读与绑定只读
重要的是要区分您配置为只读的绑定和您设置为显示为只读的UI组件。当您将绑定标记为只读时，它影响绑定在写入过程中如何管理数据，而不仅仅是UI行为。

当您将绑定标记为只读时，系统将跳过数据更新。对UI组件的任何更改将不会传输回数据模型。这确保了即使UI组件以某种方式接收用户输入，它也不会更新底层数据模型。在用户操作不应更改数据的情况下，保持这种分离对于维护数据完整性至关重要。

相比之下，将UI组件设置为只读，而不将绑定本身配置为只读，仅仅是阻止用户对UI组件进行更改，但不阻止绑定在程序性变更或通过其他方式发生更改时更新数据模型。
:::

## 绑定getter和setter {#binding-getters-and-setters}

Setter和getter是Java中的方法，分别用于设置和获取属性值。在数据绑定的上下文中，它们用于定义在绑定框架中如何更新和检索属性。

### 自定义setter和getter {#customizing-setters-and-getters}

虽然webforJ可以自动使用标准JavaBean命名约定（例如，`getName()`、`setName()`用于属性`name`），但您可能需要定义自定义行为。当属性不遵循惯例命名或数据处理需要额外逻辑时，这种情况是必要的。

### 使用自定义getter {#using-custom-getters}

当值检索过程不仅涉及返回属性时，使用自定义getter。例如，您可能希望格式化字符串，计算一个值，或在访问属性时记录某些操作。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // 自定义逻辑：将名称转换为大写
    });
```

### 使用自定义setter {#using-custom-setters}

自定义setter在设置属性涉及额外操作时发挥作用，例如验证、转换或副作用，如记录或通知应用程序的其他部分。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Updating name from " + hero.getName() + " to " + name);
        hero.setName(name); // 额外操作：记录
    });
```
