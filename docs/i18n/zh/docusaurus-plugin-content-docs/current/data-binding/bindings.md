---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
在webforJ中，绑定将Java Bean的特定属性链接到UI组件。此链接使UI与后端模型之间能够自动更新。每个绑定可处理数据同步、验证、转换和事件管理。

您只能通过`BindingContext`来初始化绑定。它管理一组绑定实例，每个实例将一个UI组件链接到一个bean的属性。它便于对绑定进行组操作，例如验证和UI组件与bean属性之间的同步。它充当聚合器，允许对多个绑定进行集体操作，从而简化应用程序中数据流的管理。

:::tip 自动绑定
本节介绍手动配置绑定的基础知识。此外，您可以根据表单中的UI组件自动创建绑定。一旦掌握了基础知识，您可以通过阅读[自动绑定](./automatic-binding)部分了解更多信息。
:::

## 配置绑定 {#configure-bindings}

首先创建一个新的`BindingContext`实例，它管理特定模型的所有绑定。此上下文确保所有绑定可以被集合验证和更新。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
每个表单应该只有一个`BindingContext`实例，并且您应该将此实例用于表单中的所有组件。
:::

### 绑定属性 {#the-bound-property}

绑定属性是Java Bean的特定字段或属性，可链接到您的应用中的UI组件。此链接使UI中的更改直接影响数据模型中相应的属性，反之亦然，从而促进响应式用户体验。

在设置绑定时，您应该提供属性名称作为字符串。该名称必须与Java Bean类中的字段名称匹配。以下是一个简单示例：

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
    .bind(textField, "power")
    .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

`bind`方法返回一个`BindingBuilder`，该对象用于创建`Binding`对象，您可以用来配置绑定的多种设置，`add`方法是将绑定实际添加到上下文中的方法。

### 绑定组件 {#the-bound-component}

绑定的另一方是绑定组件，指的是与Java Bean的属性交互的UI组件。绑定组件可以是任何支持用户交互和显示的UI组件，如文本字段、组合框、复选框或任何实现`ValueAware`接口的自定义组件。

绑定组件充当用户与底层数据模型的交互点。它向用户显示数据，并捕获用户输入，然后将其传播回模型。

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## 读取和写入数据 {#reading-and-writing-data}

### 读取数据 {#reading-data}

读取数据涉及使用数据模型中的值填充UI组件。这通常在表单初次显示时进行，或者在由于底层模型的更改需要重新加载数据时进行。`BindingContext`提供的`read`方法使此过程变得简单。

```java
// 假设Hero对象已被实例化并初始化
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext已经通过绑定配置
context.read(hero);
```

在此示例中，`read`方法接受一个`Hero`实例，并更新所有绑定的UI组件以反映超级英雄的属性。如果超级英雄的名称或力量更改，相应的UI组件（如名称的`TextField`和力量的`ComboBox`）将显示这些新值。

### 写入数据 {#writing-data}

写入数据涉及从UI组件收集值并更新数据模型。这通常在用户提交表单时发生。`write`方法在一步中处理验证和模型更新。

```java
// 这可能由表单提交事件触发
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // 数据有效，并且hero对象已更新
    // repository.save(hero); 
  } else {
    // 处理验证错误
    // results.getMessages();
  }
});
```

在上述代码中，当用户点击提交按钮时，将调用`write`方法。它执行所有配置的验证，并且如果数据通过所有检查，则从绑定组件更新`Hero`对象。数据有效时，您可能会将其保存到数据库或进一步处理。如果存在验证错误，您应适当地处理，通常是通过向用户显示错误消息。

:::tip 验证错误报告
webforJ的所有核心组件都具有默认配置，可以自动报告验证错误，既可以内联报告，也可以通过弹出窗口报告。您可以使用[报告工具](./validation/reporters.md)自定义此行为。
:::

<!-- vale off -->
## 只读数据 {#readonly-data}
<!-- vale on -->

在某些情况下，您可能希望您的应用显示数据，同时不允许最终用户通过UI直接修改它。这时，只读数据绑定变得至关重要。webforJ支持配置绑定为只读，确保您可以显示数据，但不能通过绑定的UI组件进行编辑。

### 配置只读绑定 {#configuring-readonly-bindings}

要设置只读绑定，您可以配置绑定以关闭或忽略UI组件输入。确保数据在UI角度保持不变，同时如果需要仍可通过编程更新。

```java
// 在绑定上下文中配置文本字段为只读
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

在此配置中，`readOnly`确保`nameTextField`不接受用户输入，从而使文本字段显示数据而不允许修改。

:::info
绑定只能在UI组件实现了`ReadOnlyAware`接口的情况下将该组件标记为只读。
:::

:::tip 组件只读与绑定只读
区分您配置为只读的绑定与您设置为只读显示的UI组件是非常重要的。当您将绑定标记为只读时，这影响绑定如何在写入过程中管理数据，而不仅仅是UI行为。

当您将绑定标记为只读时，系统将跳过数据更新。UI组件的任何变更都不会反馈到底层数据模型。这确保持数据完整性，在用户操作不应更改数据的场景下尤为重要。

相比之下，仅将UI组件设置为只读，而不将绑定本身配置为只读，仅停止用户对UI组件的更改，但如果数据通过编程或其他方式发生变化，绑定仍然会更新数据模型。
:::

## 绑定Getter和Setter {#binding-getters-and-setters}

Setter和Getter是在Java中设置和获取属性值的方法。在数据绑定的上下文中，它们用于定义属性在绑定框架内部如何更新和检索。

### 定制Setter和Getter {#customizing-setters-and-getters}

虽然webforJ可以自动使用标准JavaBean命名约定（例如，对于属性`name`，使用`getName()`、`setName()`），但您可能需要定义自定义行为。这在属性不遵循传统命名或数据处理需要附加逻辑时是必要的。

### 使用自定义Getter {#using-custom-getters}

当值检索过程不仅仅涉及返回一个属性时，使用自定义Getter。例如，您可能希望格式化字符串、计算值或在访问属性时记录某些操作。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // 自定义逻辑：将名称转换为大写
    });
```

### 使用自定义Setter {#using-custom-setters}

当设置属性涉及额外操作时，例如验证、转换或记录等副作用时，自定义Setter就会发挥作用。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("将名称从 " + hero.getName() + " 更新为 " + name);
        hero.setName(name); // 附加操作：日志记录
    });
```
