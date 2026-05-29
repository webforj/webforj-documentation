---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
webforJ 中的绑定将 Java Bean 的特定属性与 UI 组件连接。这种连接使 UI 和后端模型之间能够自动更新。每个绑定可以处理数据同步、验证、转换和事件管理。

您只能通过 `BindingContext` 来启动绑定。它管理一组绑定实例，每个实例将一个 UI 组件链接到一个 bean 的属性。它便于对绑定进行组操作，如验证和在 UI 组件与 bean 的属性之间的同步。它充当一个聚合器，允许对多个绑定进行集体操作，从而简化应用程序中数据流的管理。

:::tip 自动绑定
本节介绍手动配置绑定的基础知识。此外，您可以根据表单中的 UI 组件自动创建绑定。在掌握基础知识后，通过阅读 [Automatic Binding](./automatic-binding) 部分了解更多信息。
:::

## 配置绑定 {#configure-bindings}

首先创建 `BindingContext` 的新实例，该实例管理特定模型的所有绑定。该上下文确保所有绑定可以被集体验证和更新。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
每个表单应该只有一个 `BindingContext` 实例，并且您应该对表单中的所有组件使用该实例。
:::

### 绑定的属性 {#the-bound-property}

绑定属性是 Java Bean 的特定字段或属性，可以与您应用中的 UI 组件链接。 
这种链接允许 UI 中的变化直接影响数据模型的对应属性，反之亦然， 
从而促进了响应式用户体验。

设置绑定时，您应提供属性名称作为字符串。此名称必须与 Java Bean 类中的字段名称匹配。以下是一个简单示例：

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

`bind` 方法返回一个 `BindingBuilder`，该对象可用于配置绑定的多个设置，`add` 方法则是实际将绑定添加到上下文中。

### 绑定的组件 {#the-bound-component}

绑定的另一面是绑定组件，指的是与 Java Bean 的属性交互的 UI 组件。 
绑定组件可以是任何支持用户交互和显示的 UI 组件，例如文本字段、组合框、复选框，或  
任何实现 `ValueAware` 接口的自定义组件。

绑定组件充当用户与基础数据模型交互的点。 
它向用户显示数据，同时捕获用户输入，然后将其传回模型。

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## 读取和写入数据 {#reading-and-writing-data}

### 读取数据 {#reading-data}

读取数据涉及用数据模型中的值填充 UI 组件。 
这通常在表单首次显示时进行，或当由于基础模型的更改需要重新加载数据时进行。 
`BindingContext` 提供的 `read` 方法使这一过程简单明了。

```java
// 假设 Hero 对象已被实例化并初始化
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext 已与绑定配置
context.read(hero);
```

在这个例子中，`read` 方法接受一个 `Hero` 实例，并更新所有绑定的 UI 组件以反映英雄的属性。 
如果英雄的名称或力量发生变化，相应的 UI 组件（如用于名称的 `TextField` 和用于力量的 `ComboBox`） 
将显示这些新值。

### 写入数据 {#writing-data}

写入数据涉及从 UI 组件收集值并更新数据模型。 
这通常在用户提交表单时发生。`write` 方法在一步中处理验证和模型更新。

```java
// 这可能是由表单提交事件触发
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // 数据有效，hero 对象已更新
    // repository.save(hero); 
  } else {
    // 处理验证错误
    // results.getMessages();
  }
});
```

在上述代码中，当用户单击提交按钮时，调用 `write` 方法。 
它执行所有配置的验证，并且如果数据通过所有检查，则从绑定组件更新 `Hero` 对象。 
如果数据有效，您可能会保存到数据库或进一步处理。如果存在验证错误，您应适当处理，通常是通过向用户显示错误消息。

:::tip 验证错误报告
webforJ 的所有核心组件都有默认配置，以自动报告验证错误，既可以内联显示也可以通过弹出窗口显示。您可以使用 [Reporters](./validation/reporters.md) 自定义此行为。
:::

<!-- vale off -->
## 只读数据 {#readonly-data}
<!-- vale on -->

在某些情况下，您可能希望应用展示数据，但不允许最终用户通过 UI 直接修改它。 
这就是只读数据绑定变得至关重要的地方。webforJ 支持配置绑定为只读，确保您可以显示数据，但不能通过绑定的 UI 组件编辑数据。

### 配置只读绑定 {#configuring-readonly-bindings}

要设置只读绑定，您可以配置绑定以关闭或忽略 UI 组件输入。 
这确保数据从 UI 角度保持不变，同时在需要时仍然可以以编程方式更新。

```java
// 在绑定上下文中将文本字段配置为只读
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

在此配置中，`readOnly` 确保 `nameTextField` 不接受用户输入，有效地使文本字段显示 
数据而不允许修改。

:::info
只有在 UI 组件实现了 `ReadOnlyAware` 接口时，绑定才能将组件标记为只读。
:::

:::tip 组件只读 vs 绑定只读
重要的是要区分您配置为只读的绑定和您设置为以只读方式显示的 UI 组件。 
当您将绑定标记为只读时，会影响绑定在写入过程中的数据管理，而不仅仅是 UI 行为。

当您将绑定标记为只读时，系统会跳过数据更新。对 UI 组件的任何更改都不会传回数据模型。 
这确保了即使 UI 组件不小心接收了用户输入，也不会更新底层数据模型。 
维持这种分离对于在用户操作不应更改数据的场景中保持数据完整性至关重要。

相反，仅将 UI 组件设置为只读，而不将绑定本身配置为只读，会简单地阻止用户对 UI 组件做出更改，但不会阻止绑定在程序化或通过其他方式发生更改时更新数据模型。
:::

## 绑定的 getter 和 setter {#binding-getters-and-setters}

Setter 和 getter 是 Java 中的方法，分别用于设置和获得属性的值。
在数据绑定的上下文中，它们用于定义属性在绑定框架中的更新和检索方式。

### 自定义 setter 和 getter {#customizing-setters-and-getters}

虽然 webforJ 可以自动使用标准 JavaBean 命名约定
（例如，`getName()`、`setName()` 用于属性 `name`），但您可能需要定义自定义行为。
当属性不遵循常规命名或数据处理需要额外逻辑时，这一点尤为必要。

### 使用自定义 getter {#using-custom-getters}

当值检索过程涉及的不仅仅是返回一个属性时，就需要使用自定义 getter。
例如，您可能希望格式化字符串、计算值或在访问属性时记录某些操作。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // 自定义逻辑：将名称转换为大写
  });
```

### 使用自定义 setter {#using-custom-setters}

当设置属性涉及额外操作时，例如验证、转换或副作用
（如记录或通知应用程序其他部分），则会使用自定义 setter。

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("正在将名称从 " + hero.getName() + " 更新为 " + name);
    hero.setName(name); // 额外操作：记录
  });
```
