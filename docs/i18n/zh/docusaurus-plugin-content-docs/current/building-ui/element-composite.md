---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 277c6e7e84197ab515cae210cd8207b0
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类包装了一个自定义 HTML 元素或 [web 组件](https://developer.mozilla.org/en-US/docs/Web/API/Web_components)。它将你的 Java 类绑定到基础 `Element`，并允许你通过 Java 操作该元素的属性、属性和事件。当将 web 组件集成到 webforJ 应用中时使用它。

:::tip 何时使用 `ElementComposite`
当包装一个 webforJ 尚未提供的第三方 web 组件时，请使用 `ElementComposite`。如果内置的 webforJ 组件覆盖了用例（例如 `TextField`、`ColorField`、`Button` 等），则应使用这些组件。对于不需要重用的一次性 DOM 工作，可以直接使用 `Element` 类而不需要包装。
:::

本指南演示了如何使用 `ElementComposite` 类实现 [Web Awesome 相对时间 web 组件](https://webawesome.com/docs/components/relative-time/)。

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## 类注解 {#class-annotations}

`ElementComposite` 子类顶部通常出现三个注解：`@NodeName` 声明组件包装的 HTML 标签，`@JavaScript` 和 `@StyleSheet` 加载底层 web 组件所依赖的任何客户端资产。`@NodeName` 是必需的，且特定于 `ElementComposite`。`@JavaScript` 和 `@StyleSheet` 是通用的 webforJ 资产注解，并适用于任何类，包括视图、组件或 `App` 类。

### `@NodeName` {#nodename}

`@NodeName` 注解声明组件包装的 HTML 标签。webforJ 在创建 DOM 中的底层元素时使用此名称。

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

标签名称必须与客户端上注册的自定义元素匹配。没有此注解，框架无法确定创建哪个元素。

在子类内部，`getNodeName()` 方法读取声明的标签，`getElement()` 方法返回底层 `Element`，以便你可以直接调用其 DOM 级别的方法。

### `@JavaScript` {#javascript}

`@JavaScript` 注解加载定义或注册底层 web 组件的脚本。将其放置在类上，以便在使用组件时仅加载该脚本。

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

允许多个 `@JavaScript` 注解，webforJ 会自动去重加载。如果多个组件依赖同一脚本，则不会重复加载相同的脚本。

有关完整选项集，包括 `top`、`attributes` 和加载时机，请参见 [导入 JavaScript 文件](../managing-resources/importing-assets#importing-javascript-files)。

### `@StyleSheet` {#stylesheet}

`@StyleSheet` 注解加载组件所依赖的 CSS 文件。对于提供单独样式表的第三方组件或者将组件特定的样式与包装器一起打包时，它非常有用。

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

对于本地打包的资产，使用 `ws://` 前缀引用 `resources/static` 中的文件：

```java
@StyleSheet("ws://components/relative-time.css")
```

有关完整选项集，请参见 [导入 CSS 文件](../managing-resources/importing-assets#importing-css-files)。

## 属性和属性描述符 {#property-and-attribute-descriptors}

属性和属性表示 web 组件的状态，通常持有数据或配置。`ElementComposite` 通过 `PropertyDescriptor` 同时公开了这两者。

`PropertyDescriptor` 上的两个工厂方法产生描述符本身，每个绑定目标一个：

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` 绑定到 DOM 节点上的 JavaScript 属性。`PropertyDescriptor.attribute()` 绑定到 HTML 属性。第一个参数是 web 组件期望的名称。第二个参数是默认值，这也固定了描述符的 Java 类型。

在组件上将描述符声明为私有字段，然后通过 `set(PropertyDescriptor<V> property, V value)` 和 `get(PropertyDescriptor<V> property)` 读取和写入。

:::info
属性是 DOM 节点的内部状态，不会反映在标记中。属性是 HTML 标记，可被外部脚本和 CSS 看到。
:::

```java
// ElementComposite 类中叫 "title" 的示例属性
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// ElementComposite 类中叫 "value" 的示例属性
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

上述调用直接使用 `set()` 以展示原始形式。实际上，`set()` 和 `get()` 是 `ElementComposite` 上的 `protected` 方法。它们是将 Java 值与底层元素同步的原始层，不是公共 API 消费者调用的。预期模式是将 `PropertyDescriptor` 保持私有，并编写公共的 `setX()` 和 `getX()` 方法，以委托给原始方法。

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // protected primitive
    return this;
  }

  public String getHeading() {
    return get(heading);     // protected primitive
  }
}
```

对 `set(descriptor, value)` 的单次调用同时执行了三件事情。它通过 `setProperty()` 将值推送到客户端用于属性，或通过 `setAttribute()` 用于属性。它将值存储在本地服务器端缓存中，为每个组件实例一个映射。它记录运行时类型与值一起，使后续的 `get()` 调用能够知道如何反序列化。

该本地缓存就是 `get()` 可以在默认情况下廉价的原因。`get(descriptor)` 从服务器端存储返回缓存的值，无需网络调用，因为每次 `set()` 都会保持缓存与客户端的同步。可选的 `boolean` 第二个参数控制是否旁路缓存并直接从浏览器读取。

```java
String cached = get(heading);            // 从服务器端缓存读取
String live = get(heading, true);        // 强制从浏览器读取
```

将 `fromClient` 设置为 true 当值可以在客户端变化而服务器不知道时，比如键入的 `<input>` 值。对于由服务器驱动的属性，默认避免往返。

第三个可选参数是 `java.lang.reflect.Type`，控制结果如何反序列化。webforJ 按照以下顺序解析类型：如果提供了显式的 `Type` 参数，随后是同一描述符上之前 `set()` 记录的运行时类型，最后是 `Object.class`。在实践中，之前 `set()` 记录的类型通常就足够了，因此可以省略第三个参数。当记录的类丢失了反序列化器所依赖的信息时，比如像 `List<String>` 这样参数化的类型，其运行时类仅为 `ArrayList`。

下面的演示根据 web 组件的文档添加了相对时间的属性，并通过 getter 和 setter 公开了它们。活动反馈中的每一行使用不同的 `format` 和 `numeric` 值，展示了同一组件在不同配置下的渲染情况。

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### 属性与属性 {#properties-versus-attributes}

尽管 `PropertyDescriptor.property()` 和 `PropertyDescriptor.attribute()` 看起来可以互换，但它们指向底层元素的不同部分。选择错误的一个会导致值默默地无法应用。

属性是 DOM 节点上的 JavaScript 对象属性。它们可以保存任何类型，包括字符串、布尔值、数字、对象和数组，并且表示元素的当前运行时状态。设置属性是直接的 JavaScript 赋值。

属性是 HTML 标记。它们位于元素的开始标签上，始终是字符串，并代表元素的初始配置。设置属性会触发 DOM 变化和字符串转换。

在某些情况下，两者保持同步。在其他情况下，它们会分歧。`<input>` 的 `value` 是经典例子：`value` 属性是初始值，而 `value` 属性是用户输入的当前值。用户输入后读取属性会返回原始标记，但读取属性则返回字段的当前内容。

使用 **属性** 来：

- **经常变化的运行时状态**：计数器、当前选择、输入的值
- **非字符串类型**：布尔值、数字、对象、数组
- **性能敏感的更新**：属性跳过了属性所需的字符串转换

使用 **属性** 来：

- **初始配置**：组件连接时一次性读取的设置
- **CSS 选择器**：希望通过选择器定位的值，例如 `[disabled]` 或 `[variant="danger"]`
- **可访问性钩子**：`aria-label`、`role` 和其他 ARIA 属性
- **很少变化的类似字符串的设置**

在包装第三方 web 组件时，请查看组件的文档以确认哪个名称映射到属性，哪个映射到属性。对于仅将某个内容暴露为属性的组件，使用 `PropertyDescriptor.attribute()` 将无法正常工作，反之亦然。组件将默默忽略该值。

### 属性类型 {#typing-properties}

描述符的参数是其值的 Java 类型。完整的声明语法是：

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` 泛型参数声明值的类型。默认值的运行时类型也固定了 `T`，因此泛型参数很少需要显式指定。webforJ 使用 `T` 在与客户端通信时序列化和反序列化值。

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

基本类型及其装箱等价类型和 `String` 的序列化是自动的。对于复杂类型，值会在被分配到客户端的属性之前作为 JSON 序列化。

### 验证值 {#validating-values}

在调用 `set()` 之前验证 setter 中的值。setter 是自然的强制执行点，因为每次变更都通过它流动。

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max must be non-negative");
  }
  set(max, value);
  return this;
}
```

对于可为空的引用，使用 `Objects.requireNonNull()` 以便在边界上出现故障而不是在后面的渲染管道中。

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

避免在 `get()` 中进行验证。读取应保持廉价和一致。

### 枚举风格属性 {#enum-style-properties}

大多数 web 组件期望以小写或短横线格式的字符串值用于枚举似的属性（例如 `theme="primary"`、`expanse="xs"`）。webforJ 使用 Gson 序列化枚举，但 Gson 的默认表示是常量名称的大写形式。使用 `@SerializedName` 注解每个常量，以便序列化值与 web 组件的预期值匹配。

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

将描述符声明为枚举类型，并在 setter 和 getter 中直接使用该枚举。

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

这是 webforJ 内置组件用于 `Theme`、`Expanse` 和类似枚举的相同模式。公共 Java API 保持类型安全，web 组件接收到的值是来自 `@SerializedName` 的字符串。

### 测试属性 {#testing-properties}

`PropertyDescriptorTester` 验证组件中的每个 `PropertyDescriptor` 是否连接正确。它扫描类以查找描述符字段，使用默认值调用每个 setter，并将结果与 getter 返回的值进行比较。该测试器在运行的应用程序之前捕获集成错误：写入错误描述符的 setter、读取不同属性的 getter、未能实现回环的默认值或对已声明描述符缺少访问器。

组件的基线测试看起来像这样：

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

某些描述符不遵循标准的 getter 和 setter 约定，或者它们依赖测试无法满足的外部状态。使用 `@PropertyExclude` 注解以跳过它们。

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### 自定义 getter 和 setter 名称 {#custom-getter-and-setter-names}

如果描述符使用非标准的访问器名称，请使用 `@PropertyMethods` 声明它们。

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target` 参数接受一个类，当访问器位于组件以外的地方时使用。

有关测试表面的更多详细信息，请参见 [PropertyDescriptorTester](../testing/property-descriptor-tester)。

## Concern 接口 {#concern-interfaces}

Concern 接口在不自己编写实现的情况下为 `ElementComposite` 子类组件提供能力。接口将调用转发到底层元素。实现组件应该支持的那些，使用子类类型进行参数化，以便连贯性返回组件：

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // 不需要实现。
}

MyBadge badge = new MyBadge()
    .setText("New")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

上述三个接口覆盖了 `MyBadge` 所需的一切，而不需要类中的任何方法体。`HasText` 暴露了 `setText()` 并写入元素的文本内容。`HasClassName` 暴露了 `addClassName()`，允许使用 CSS 定位徽章。`HasStyle` 暴露了 `setStyle()` 用于行内样式。

有关可用接口的完整集合以及每个接口提供的内容，请参见 [Concern 接口](./component-fundamentals#concern-interfaces) 中的理解组件文章。如果默认的转发与包装的元素所暴露的内容不匹配，则可以在子类中重写方法。

## 事件 {#events}

### 事件注册 {#event-registration}

web 组件在浏览器中发生某些事情时会调度 DOM 事件。要在 Java 中反应，使用 `addEventListener()` 监听这些事件。组件调度的事件集合会有所不同，请检查组件自己的文档以了解可用的名称和有效负载。

`ElementComposite` 支持防抖、节流、过滤和注册的侦听器上的自定义事件数据。

使用 `addEventListener()` 方法注册事件侦听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 仅接受带有 `@EventName` 注解的事件类，而非 `Element`，后者接受任何字符串事件名称。
:::

### 内置事件类 {#built-in-event-classes}

`ElementClickEvent` 是 `ElementComposite` 提供的唯一内置事件类。它在底层元素上表面化鼠标点击事件，带有坐标的类型访问器（`getClientX()`、`getClientY()`）、按钮信息（`getButton()`）和修饰键（`isCtrlKey()`、`isShiftKey()` 等）。

要在子类的公共 API 上公开点击处理，实现 `HasElementClickListener<T>` concern 接口。它提供默认的 `onClick()` 和 `addClickListener()` 方法，这些方法委托给受保护的 `addEventListener()` 原始方法。

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() 和 addClickListener() 现在在 MyBadge 上可用
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

对于底层 web 组件调度的任何其他事件，可以定义自定义事件类。请参见 [自定义事件类](#custom-event-classes)。

### 事件有效负载 {#event-payloads}

事件将数据从客户端传递到你的 Java 代码。通过 `getData()` 访问这些数据以获取原始事件数据，或在内置事件类上使用可用的类型方法。有关有效负载处理的更多信息，请参见 [事件指南](../building-ui/events)。

### 自定义事件类 {#custom-event-classes}

使用 `@EventName` 和 `@EventOptions` 定义自定义事件类以在 Java 事件中捕获客户端数据。使用这个当 Java 处理程序需要来自浏览器的值时。

`@EventName` 将 Java 类与组件在浏览器中调度的事件绑定在一起，因此标注为 `@EventName("change")` 的类会在底层元素发出 `change` 时触发。`@EventOptions` 控制与该事件一起传递的内容。在它内部的每个 `@EventData` 将一个键与对 DOM 事件评估的 JavaScript 表达式配对。结果可以通过 `getData().get(key)` 在 Java 事件类中访问。

下面的产品评审表单使用这种模式与 [`wa-rating`](https://webawesome.com/docs/components/rating/)。自定义的 `ChangeEvent` 将评分值作为类型 `double` 传递，而侦听器使用它来启用提交按钮：

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### 事件选项 {#event-options}

`ElementEventOptions` 配置事件有效负载、防抖或节流定时、过滤表达式和预执行代码。下面的代码片段展示了选项：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // 在事件触发前执行 JavaScript
  .setCode("component.classList.add('processing');")

  // 仅在条件满足时触发
  .setFilter("component.value.length >= 2")

  // 等待用户停止输入（300ms）后再执行
  .setDebounce(300, DebouncePhase.TRAILING);

// 在注册自定义事件类的侦听器时应用这些选项
// （请参见上面的自定义事件类节了解如何定义一个）：
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` 仅公开类基于的形式 `addEventListener(Class, listener, options)`。使用带有 `@EventName` 注解的事件类与之配合使用。要直接注册到字符串事件名称，请调用 `getElement().addEventListener("input", listener, options)`。
:::

#### 性能控制 {#performance-control}

**防抖** 在活动停止时延迟执行：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一个事件后等待 300ms
```

可用的防抖阶段：

- `LEADING`: 立即触发，然后等待
- `TRAILING`: 等待安静期，然后触发（默认）
- `BOTH`: 立即触发和在安静期后触发

**节流** 限制执行频率：

```java
options.setThrottle(100); // 每 100ms 至多触发一次
```

## 与插槽的交互 {#interacting-with-slots}

插槽是 web 组件内部的占位符，用户可以填充内容。web 组件使用 `<slot>` 或 `<slot name="...">` 在其模板中声明其插槽，而包装器则公开将 Java 组件放置到这些插槽中的方法。

要向插槽添加内容，请扩展 `ElementCompositeContainer` 而不是 `ElementComposite`。容器携带相同的属性和属性机制，以及添加子项所需的方法。通过 `add()` 添加的子项将进入默认插槽。通过 `getElement().add(slotName, components)` 添加的子项将进入命名插槽。

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

下面的演示展示了使用 [`wa-card`](https://webawesome.com/docs/components/card/) 构建的两个定价卡片，从 Java 填充 `header`、默认和 `footer` 插槽：

<ComponentDemo
path='/webforj/webawesomecard'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### 检查插槽内容 {#inspecting-slot-contents}

底层 `Element`（通过 `getElement()` 访问）提供了读取当前分配给插槽的内容的方法：

- **`findComponentSlot()`**：搜索所有插槽以查找特定组件并返回包含该组件的插槽名称，如果该组件未在任何插槽中，则返回空字符串。
- **`getComponentsInSlot()`**：返回分配给给定插槽的组件列表。可选地接受一个类类型以过滤结果。
- **`getFirstComponentInSlot()`**：返回分配给插槽的第一个组件。可选地接受一个类类型以过滤。
