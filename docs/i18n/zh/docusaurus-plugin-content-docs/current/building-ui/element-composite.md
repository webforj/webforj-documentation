---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类包装一个自定义 HTML 元素或 [Web 组件](https://developer.mozilla.org/en-US/docs/Web/API/Web_components)。它将你的 Java 类绑定到底层的 `Element`，并允许你通过 Java 操作该元素的属性、属性和事件。将其用于将 Web 组件集成到 webforJ 应用中。

:::tip 使用 `ElementComposite` 的时机
当包装一个 webforJ 尚未提供的第三方 Web 组件时，请使用 `ElementComposite`。如果一个内置的 webforJ 组件满足用例（例如 `TextField`、`ColorField`、`Button` 等），那就使用那个。对于不需要重用的一次性 DOM 工作，可以直接使用 `Element` 类而无需包装。
:::

本指南演示如何使用 `ElementComposite` 类实现 [Web Awesome 相对时间 Web 组件](https://webawesome.com/docs/components/relative-time/)。

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## 类注解 {#class-annotations}

在 `ElementComposite` 子类的顶部，通常会出现三个注解：`@NodeName` 声明组件包装的 HTML 标签，`@JavaScript` 和 `@StyleSheet` 加载底层 Web 组件依赖的任何客户端资产。`@NodeName` 是必需的，并且特定于 `ElementComposite`。`@JavaScript` 和 `@StyleSheet` 是通用 webforJ 资产注解，可以应用于任何类，包括视图、组件或 `App` 类。

### `@NodeName` {#nodename}

`@NodeName` 注解声明组件包装的 HTML 标签。webforJ 在创建 DOM 中的底层元素时使用这个名称。

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

标签名称必须与客户端上注册的自定义元素匹配。没有这个注解，框架无法确定创建哪个元素。

在子类中，`getNodeName()` 可以读取声明的标签，`getElement()` 返回底层的 `Element`，因此你可以直接对其调用 DOM 级别的方法。

### `@JavaScript` {#javascript}

`@JavaScript` 注解加载定义或注册底层 Web 组件的脚本。将其放在类上，以便在使用组件时只加载该脚本。

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

允许多个 `@JavaScript` 注解，webforJ 会自动去重。多个依赖相同脚本的组件不会导致该脚本加载两次。

有关完整选项集（包括 `top`、`attributes` 和加载时机），请参见 [导入 JavaScript 文件](../managing-resources/importing-assets#importing-javascript-files)。

### `@StyleSheet` {#stylesheet}

`@StyleSheet` 注解加载组件依赖的 CSS 文件。它用于包含具有单独样式表的第三方组件，或将组件特定的样式与包装器一起捆绑。

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

对于本地捆绑的资产，使用 `ws://` 前缀引用 `resources/static` 中的文件：

```java
@StyleSheet("ws://components/relative-time.css")
```

有关完整选项集，请参见 [导入 CSS 文件](../managing-resources/importing-assets#importing-css-files)。

## 属性和属性描述符 {#property-and-attribute-descriptors}

属性和属性表示 Web 组件的状态，通常保存数据或配置。`ElementComposite` 通过 `PropertyDescriptor` 暴露这两者。

`PropertyDescriptor` 上的两个工厂方法可以生成描述符，一个针对绑定目标：

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` 绑定到 DOM 节点上的JavaScript 属性。`PropertyDescriptor.attribute()` 绑定到 HTML 属性。第一个参数是 Web 组件期望的名称。第二个是默认值，还固定了描述符的 Java 类型。

在组件上将描述符声明为私有字段，然后通过 `set(PropertyDescriptor<V> property, V value)` 和 `get(PropertyDescriptor<V> property)` 读取和写入。

:::info
属性是 DOM 节点的内部状态，在标记中不可见。属性是 HTML 标记，对外部脚本和 CSS 可见。
:::

```java
// 在 ElementComposite 类中的示例属性 "title"
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// 在 ElementComposite 类中的示例属性 "value"
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "我的标题");
set(value, "我的值");
```

上述调用直接使用 `set()` 以显示原始形式。在实践中，`set()` 和 `get()` 是`ElementComposite` 的 `protected` 方法。它们是保持 Java 值与底层元素同步的原始层，而不是公共 API 消费者调用的接口。推荐的模式是将 `PropertyDescriptor` 保持私有，并编写公共的 `setX()` 和 `getX()` 方法，委托给原始方法。

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // 保护的原始方法
    return this;
  }

  public String getHeading() {
    return get(heading);     // 保护的原始方法
  }
}
```

对 `set(descriptor, value)` 的单次调用同时完成三件事。它通过 `setProperty()` 将值推送到客户端（用于属性），或通过 `setAttribute()`（用于属性）存储值到服务器端缓存，针对每个组件实例一个映射。并且它在运行时类型旁边记录值，以便后续的 `get()` 调用知道如何反序列化。

该本地缓存是 `get()` 默认可以快速的原因。`get(descriptor)` 从服务器端存储中返回缓存值，无需网络调用，因为每个 `set()` 都使缓存与客户端保持同步。可选的 `boolean` 第二个参数控制是否绕过缓存并从浏览器中读取。

```java
String cached = get(heading);            // 从服务器端缓存读取
String live = get(heading, true);        // 强制从浏览器读取
```

当值可以在客户端发生更改而服务器不知情时，将 `fromClient` 设置为 `true`，例如类型 `<input>` 的值。对于服务器驱动的属性，默认情况下避免往返调用。

可选的第三个参数是 `java.lang.reflect.Type`，控制结果的反序列化方式。webforJ 按照以下顺序解析类型：如果传递了显式的 `Type` 参数，则使用它；否则使用 `set()` 在同一描述符上记录的运行时类型；再不然则使用 `Object.class`。实际上，之前 `set()` 记录的类型通常就足够，因此第三个参数通常可以省略。当记录的类丢失反序列化器所需的信息时，就需要它，例如参数化类型如 `List<String>` 其运行时类仅为 `ArrayList`。

下面的演示根据 Web 组件的文档为相对时间添加属性，并通过 getter 和 setter 进行暴露。活动饼干流中的每一行使用不同的 `format` 和 `numeric` 值，以显示相同组件在不同配置下的渲染效果。

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### 属性与属性 {#properties-versus-attributes}

尽管 `PropertyDescriptor.property()` 和 `PropertyDescriptor.attribute()` 看似可以互换，但它们针对的是底层元素的不同部分。选择错误的一个会导致值默默失效。

属性是 DOM 节点上的 JavaScript 对象属性。它们可以保存任何类型，包括字符串、布尔值、数字、对象和数组，表示元素的当前运行时状态。设置属性是直接的 JavaScript 赋值。

属性是 HTML 标记。它们位于元素的开标签上，总是字符串，并表示元素的初始配置。设置属性触发 DOM 变更和字符串转换。

在某些情况下，两者保持同步。在其他情况下，它们则分歧。输入框的 `value` 是经典的例子：`value` 属性是初始值，而 `value` 属性则是用户输入的当前值。在用户输入后读取属性返回原始标记，但读取属性返回字段的当前内容。

**属性** 适用场景：

- **频繁更改的运行时状态**：计数器、当前选择、输入值
- **非字符串类型**：布尔值、数字、对象、数组
- **性能敏感的更新**：属性跳过了属性所需的字符串转换

**属性** 用于：

- **初始配置**：组件在连接时只读取一次的设置
- **CSS 选择器**：你希望通过像 `[disabled]` 或 `[variant="danger"]` 这样的选择器来定位的值
- **可访问性钩子**：`aria-label`、`role` 和其他 ARIA 属性
- **很少更改的类似字符串的设置**

当包装第三方 Web 组件时，请检查组件的文档以确认哪个名称映射到属性，哪个映射到属性。使用 `PropertyDescriptor.attribute()` 对于组件仅以属性公开的内容是行不通的，反之亦然。组件将默默忽略该值。

### 属性类型 {#typing-properties}

描述符由其值的 Java 类型参数化。完整声明语法为：

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` 泛型参数声明值的类型。默认值的运行时类型还固定了 `T`，所以泛型参数很少需要显式指定。webforJ 使用 `T` 在与客户端通信时序列化和反序列化值。

```java
private final PropertyDescriptor<String> label =
    PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
    PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
    PropertyDescriptor.property("step", 1.0);
```

对于基本类型、其包装类型和 `String`，序列化是自动的。对于复杂类型，值在分配到客户端上的属性之前会先作为 JSON 序列化。

### 验证值 {#validating-values}

在调用 `set()` 之前验证 setter 中的值。setter 是自然的强制点，因为每个变更都通过它流动。

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max 必须为非负数");
  }
  set(max, value);
  return this;
}
```

对于可为空的引用，请使用 `Objects.requireNonNull()`，以便失败在边界处显现，而不是在后续渲染管道中。

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading 不能为 null");
  set(heading, value);
  return this;
}
```

避免在 `get()` 中验证。读取应保持廉价且一致。

### 类似枚举的属性 {#enum-style-properties}

大多数 Web 组件期望枚举样式的属性使用小写字母或短横线命名的字符串值（`theme="primary"`、`expanse="xs"`）。webforJ 使用 Gson 来序列化枚举，但 Gson 的默认表示法是以大写形式的常量名称。用 `@SerializedName` 注解每个常量，以便序列化值与 Web 组件期望的匹配。

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

用枚举类型声明描述符，并在 setter 和 getter 中直接使用枚举。

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

这是 webforJ 内置组件对于 `Theme`、`Expanse` 和相似枚举使用的相同模式。公共 Java API 保持类型安全，Web 组件接收到的值是来自 `@SerializedName` 的字符串。

### 测试属性 {#testing-properties}

`PropertyDescriptorTester` 验证组件中每个 `PropertyDescriptor` 的连接是否正确。它扫描类以查找描述符字段，调用每个 setter，使用默认值，并将结果与 getter 返回的内容进行比较。测试器捕捉集成错误，防止在运行的应用中出现：向错误描述符写入的 setter，读取不同属性的 getter，未能来回传递的默认值，或缺失的访问器用于声明的描述符。

一个组件的基础测试看起来如下：

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### 排除属性 {#excluding-properties}

某些描述符不遵循标准的 getter 和 setter 约定，或依赖于测试无法满足的外部状态。用 `@PropertyExclude` 注解它们以跳过。

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### 自定义 getter 和 setter 名称 {#custom-getter-and-setter-names}

如果描述符使用非标准访问器名称，则用 `@PropertyMethods` 声明它们。

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target` 参数可以接受一个类，当访问器存储在组件本身以外的地方。

有关测试表面的更多详细信息，请参见 [PropertyDescriptorTester](../testing/property-descriptor-tester)。

## 关注接口 {#concern-interfaces}

关注接口为 `ElementComposite` 子类组件提供功能，而无需自己编写实现。接口将调用转发至底层元素。实现组件应支持的接口，使用子类类型参数化，因此链式调用返回组件：

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // 无需实现。
}

MyBadge badge = new MyBadge()
    .setText("新")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

以上三个接口涵盖了 `MyBadge` 所需的一切，而无需在类中包含方法体。`HasText` 暴露 `setText()` 并写入元素的文本内容。`HasClassName` 暴露 `addClassName()`，允许通过 CSS 定位徽章。`HasStyle` 暴露 `setStyle()` 用于内联样式。

有关可用接口及其提供的内容的完整列表，请参见 [关注接口](./component-fundamentals#concern-interfaces) 在理解组件的文章中。如果默认转发与被包装元素暴露的不匹配，请在子类中重写该方法。

## 事件 {#events}

### 事件注册 {#event-registration}

Web 组件在浏览器中发生某些事件时会发送 DOM 事件。要从 Java 中进行反应，请使用 `addEventListener()` 监听这些事件。组件派发的事件集合因组件而异，因此请检查组件自身文档以获取可用的名称和有效负载。

`ElementComposite` 支持去抖动、限流、过滤和自定义事件数据的注册监听器。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加单击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理单击事件
});
```

:::info
`ElementComposite` 仅接受带有 `@EventName` 注解的事件类，与 `Element` 不同，后者接受任何字符串事件名称。
:::

### 内置事件类 {#built-in-event-classes}

`ElementClickEvent` 是唯一的内置事件类，它与 `ElementComposite` 一起提供。它对底层元素的鼠标单击事件提供了类型化访问器，用于坐标（`getClientX()`、`getClientY()`）、按钮信息（`getButton()`）和修饰键（`isCtrlKey()`、`isShiftKey()` 等）。

要在子类的公共 API 中公开单击处理，请实现 `HasElementClickListener<T>` 关注接口。它提供默认的 `onClick()` 和 `addClickListener()` 方法，这些方法委托给保护性 `addEventListener()` 原始方法。

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() 和 addClickListener() 现在可以在 MyBadge 中使用
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

对于底层 Web 组件派发的其他事件，定义一个自定义事件类。请参见 [自定义事件类](#custom-event-classes)。

### 事件有效负载 {#event-payloads}

事件携带数据从客户端到你的 Java 代码。通过 `getData()` 访问这些数据以获取原始事件数据，或者在内置事件类上使用可用的类型化方法。有关有效负载处理的更多信息，请参见 [事件指南](../building-ui/events)。

### 自定义事件类 {#custom-event-classes}

使用 `@EventName` 和 `@EventOptions` 定义自定义事件类，以在类型化 Java 事件中捕获客户端数据。当 Java 处理程序需要来自浏览器的值时，使用此模式。

`@EventName` 将 Java 类与组件在浏览器中派发的事件绑定，因此一个注解为 `@EventName("change")` 的类将在底层元素发出 `change` 时触发。`@EventOptions` 控制与该事件一起传输的内容。每个 `@EventData` 都与一个键配对，该键是对 DOM 事件评估的 JavaScript 表达式。结果可以通过 Java 事件类中的 `getData().get(key)` 访问。

下面的产品评价表单使用此模式与 [`wa-rating`](https://webawesome.com/docs/components/rating/)。自定义的 `ChangeEvent` 将评级值作为类型化的 `double` 携带，监听器使用它来启用提交按钮：

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### 事件选项 {#event-options}

`ElementEventOptions` 配置事件有效负载、去抖或限流时间、过滤表达式和执行前的代码。以下代码段展示了这些选项：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // 在事件触发之前执行 JavaScript
  .setCode("component.classList.add('processing');")

  // 仅在条件满足时触发
  .setFilter("component.value.length >= 2")

  // 用户停止输入后延迟执行（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

// 在为自定义事件类注册监听器时应用这些选项
// （请参见上述自定义事件类部分了解如何定义）：
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` 仅暴露基于类的形式 `addEventListener(Class, listener, options)`。使用带有 `@EventName` 注解的事件类。要直接根据字符串事件名称注册，请调用 `getElement().addEventListener("input", listener, options)`。
:::

#### 性能控制 {#performance-control}

**去抖** 延迟执行直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 用户最后活动后等待 300ms
```

可用的去抖阶段：

- `LEADING`: 立即触发，然后等待
- `TRAILING`: 等待静默期，然后触发（默认）
- `BOTH`: 立即触发并在静默期后触发

**限流** 限制执行频率：

```java
options.setThrottle(100); // 每 100ms 最多触发一次
```

## 与槽交互 {#interacting-with-slots}

槽是 Web 组件中用户填充内容的占位符。Web 组件在其模板中使用 `<slot>` 或 `<slot name="...">` 声明它的槽，包装器则提供将 Java 组件放入这些槽的方法。

要向槽添加内容，请扩展 `ElementCompositeContainer` 而不是 `ElementComposite`。容器携带相同的属性和属性机制，以及添加子组件所需的方法。通过 `add()` 添加的子组件进入默认槽。通过 `getElement().add(slotName, components)` 添加的子组件进入命名槽。

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

以下演示展示了两个使用 [`wa-card`](https://webawesome.com/docs/components/card/) 构建的定价卡，从 Java 填充 `header`、默认和 `footer` 槽：

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### 检查槽内容 {#inspecting-slot-contents}

底层的 `Element`（通过 `getElement()` 访问）提供了读取当前分配给槽的内容的方法：

- **`findComponentSlot()`**：搜索所有槽以查找特定组件，并返回包含该组件的槽的名称，如果组件不在任何槽中则返回空字符串。
- **`getComponentsInSlot()`**：返回分配给给定槽的组件列表。可以选择传入类类型以过滤结果。
- **`getFirstComponentInSlot()`**：返回分配给槽的第一个组件。可以选择传入类类型以过滤。
