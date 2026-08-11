---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: b8099816ab51d246d3a69c2ca8bd9108
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类包装了一个自定义 HTML 元素或 [web 组件](https://developer.mozilla.org/en-US/docs/Web/API/Web_components)。它将您的 Java 类绑定到基础的 `Element`，并让您可以通过 Java 操作该元素的属性、属性和事件。当将 web 组件集成到 webforJ 应用程序中时，请使用它。

:::tip 何时使用 `ElementComposite`
当包装一个 webforJ 尚未提供的第三方 web 组件时，请选择 `ElementComposite`。如果内置的 webforJ 组件可以满足用例（例如 `TextField`、`ColorField`、`Button` 等），请使用它。对于无需重用的一次性 DOM 工作，可以直接使用 `Element` 类而无需包装。
:::

本指南演示如何使用 `ElementComposite` 类实现 [Shoelace 相对时间 web 组件](https://shoelace.style/components/relative-time)。

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## 类注释 {#class-annotations}

`ElementComposite` 子类顶部通常出现三个注释：`@NodeName` 声明组件包装的 HTML 标签，而 `@JavaScript` 和 `@StyleSheet` 加载基础 web 组件所依赖的任何客户端资产。`@NodeName` 是必需的，并且特定于 `ElementComposite`。`@JavaScript` 和 `@StyleSheet` 是通用的 webforJ 资产注释，并适用于任何类，包括视图、组件或 `App` 类。

### `@NodeName` {#nodename}

`@NodeName` 注释声明组件包装的 HTML 标签。webforJ 在 DOM 中创建基础元素时会使用此名称。

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

标签名称必须与客户端注册的自定义元素匹配。没有这个注释，框架无法确定创建哪个元素。

在子类内，`getNodeName()` 读取声明的标签，而 `getElement()` 返回基础的 `Element`，以便您可以直接调用 DOM 级方法。

### `@JavaScript` {#javascript}

`@JavaScript` 注释加载定义或注册基础 web 组件的脚本。将其放在类上，以便只有在使用组件时才加载脚本。

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

允许多个 `@JavaScript` 注释，webforJ 会自动去重加载。如果多个组件依赖同一个脚本，该脚本不会加载两次。

有关完整的选项集，包括 `top`、`attributes` 和加载时机，请参见 [导入 JavaScript 文件](../managing-resources/importing-assets#importing-javascript-files)。

### `@StyleSheet` {#stylesheet}

`@StyleSheet` 注释加载组件所依赖的 CSS 文件。它对于附带单独样式表的第三方组件或将组件特定样式与包装器一起打包非常有用。

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

对于本地打包的资产，请使用 `ws://` 前缀引用 `resources/static` 中的文件：

```java
@StyleSheet("ws://components/relative-time.css")
```

有关完整的选项集，请参见 [导入 CSS 文件](../managing-resources/importing-assets#importing-css-files)。

## 属性和属性描述符 {#property-and-attribute-descriptors}

属性和属性表示 web 组件的状态，通常保存数据或配置。`ElementComposite` 通过 `PropertyDescriptor` 曝露这两者。

`PropertyDescriptor` 上的两个工厂方法生成描述符本身，每个绑定目标一个：

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` 绑定到 DOM 节点上的 JavaScript 属性。`PropertyDescriptor.attribute()` 绑定到 HTML 属性。第一个参数是 web 组件所期望的名称。第二个是默认值，它也固定了描述符的 Java 类型。

将描述符声明为组件上的私有字段，然后通过 `set(PropertyDescriptor<V> property, V value)` 和 `get(PropertyDescriptor<V> property)` 来读取和写入它。

:::info
属性是 DOM 节点的内部状态，不会反映在标记中。属性是 HTML 标记，对于外部脚本和 CSS 可见。
:::

```java
// ElementComposite 类中称为 "title" 的示例属性
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// ElementComposite 类中称为 "value" 的示例属性
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "我的标题");
set(value, "我的值");
```

上述调用直接使用 `set()` 显示了原始形式。实际上，`set()` 和 `get()` 是 `ElementComposite` 上的 `protected` 方法。它们是将 Java 值与基础元素同步的原始层，而不是消费者调用的公共 API。预期的模式是将 `PropertyDescriptor` 保持私有，并编写公共的 `setX()` 和 `getX()` 方法，以委托给原始方法。

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // 受保护的原始方法
    return this;
  }

  public String getHeading() {
    return get(heading);     // 受保护的原始方法
  }
}
```

对 `set(descriptor, value)` 的单个调用一次性执行三项操作。它通过 `setProperty()` 将值推送到客户端的属性，或通过 `setAttribute()` 为属性。它将值存储在一个本地的服务器端缓存中，每个组件实例一个映射。它还记录了运行时类型，以便后续的 `get()` 调用知道如何反序列化。

这个本地缓存是 `get()` 默认可以廉价调用的原因。`get(descriptor)` 从服务器端存储中返回缓存值而不进行网络调用，因为每个 `set()` 保持缓存与客户端同步。可选的 `boolean` 第二个参数控制是否绕过缓存并从浏览器读取。

```java
String cached = get(heading);            // 从服务器端缓存读取
String live = get(heading, true);        // 强制从浏览器读取
```

当值可以在客户端更改而服务器无法知晓时，将 `fromClient` 设置为 true，例如一个输入框的值。对于服务器驱动的属性，默认情况可以避免往返调用。

可选的第三个参数是 `java.lang.reflect.Type`，控制结果的反序列化方式。webforJ 按照以下顺序解析类型：如果传递了显式的 `Type` 参数，则使用它；否则使用先前通过相同描述符的 `set()` 记录的运行时类型；最后为 `Object.class`。实际上，先前 `set()` 记录的类型通常已经足够，因此可以省略第三个参数。当记录的类丢失反序列化所需的信息时，例如参数化类型 `List<String>` 的运行时类为 `ArrayList`，则需要它。

以下演示根据 web 组件的文档添加相对时间的属性，并通过 getter 和 setter 曝露它们。活动feed中的每一行使用不同的 `format` 和 `numeric` 值来展示同一个组件在不同配置下的呈现效果。

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### 属性与属性 {#properties-versus-attributes}

尽管 `PropertyDescriptor.property()` 和 `PropertyDescriptor.attribute()` 看起来可以互换，但它们针对的是基础元素的不同部分。选择错误将导致值默默地无法应用。

属性是 DOM 节点上的 JavaScript 对象属性。它们可以保存任何类型，包括字符串、布尔值、数字、对象和数组，表示元素的当前运行时状态。设置属性是直接的 JavaScript 赋值。

属性则为 HTML 标记。它们存在于元素的起始标签上，始终是字符串，表示元素的初始配置。设置属性会触发 DOM 变更和字符串转换。

在某些情况下，两者保持同步。在其他情况下则会偏离。`<input>` 的 `value` 是经典示例：`value` 属性是初始值，而 `value` 属性是用户输入的当前值。在用户键入后读取属性将返回原始标记，而读取属性将返回字段的当前内容。

使用 **属性** 适用于：

- **频繁变化的运行时状态**：计数器、当前选择、输入值
- **非字符串类型**：布尔值、数字、对象、数组
- **性能敏感的更新**：属性跳过属性所需的字符串转换

使用 **属性** 适用于：

- **初始配置**：组件在连接时读取一次的设置
- **CSS 选择器**：您希望用选择器（如 `[disabled]` 或 `[variant="danger"]`）定位的值
- **辅助功能挂钩**：`aria-label`、`role` 和其他 ARIA 属性
- **不常更改的类似于字符串的设置**

在包装第三方 web 组件时，请检查组件的文档以确认哪个名称映射到属性，哪个映射到属性。为组件仅作为属性公开的内容使用 `PropertyDescriptor.attribute()` 是无效的，反之亦然。组件将默默忽略该值。

### 类型属性 {#typing-properties}

描述符通过其值的 Java 类型进行参数化。完整的声明语法是：

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` 泛型参数声明值的类型。默认值的运行时类型也固定了 `T`，因此很少需要显式指定泛型参数。webforJ 使用 `T` 在与客户端通信时序列化和反序列化值。

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

对于原始类型、其装箱类型和 `String`，序列化是自动的。对于复杂类型，值在分配给客户端的属性之前会序列化为 JSON。

### 验证值 {#validating-values}

在调用 `set()` 之前验证 setter 中的值。setter 是自然的强制执行点，因为每个变更都通过它流动。

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max 必须是非负数");
  }
  set(max, value);
  return this;
}
```

对于可为空的引用，请使用 `Objects.requireNonNull()`，这样失败会在边界处显现，而不是在后面的渲染管道中。

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading 不能为空");
  set(heading, value);
  return this;
}
```

避免在 `get()` 中进行验证。读取操作应保持廉价且一致。

### 枚举风格的属性 {#enum-style-properties}

大多数 web 组件期望枚举类型属性使用小写或短横线分隔的小写字符串值（例如 `theme="primary"`、`expanse="xs"`）。webforJ 使用 Gson 来序列化枚举，但 Gson 的默认表示是常量名的大写形式。对每个常量使用 `@SerializedName` 进行注释，以便序列化值与 web 组件期望的内容相匹配。

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

用枚举类型声明描述符，并直接在 setter 和 getter 中使用枚举。

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

这是 webforJ 内置组件在 `Theme`、`Expanse` 和类似枚举上使用的相同模式。公共 Java API 保持类型安全，web 组件接收到的值是 `@SerializedName` 中的字符串。

### 测试属性 {#testing-properties}

`PropertyDescriptorTester` 验证组件中的每个 `PropertyDescriptor` 是否正确连接。它扫描类以查找描述符字段，用默认值调用每个 setter，并将结果与 getter 返回的结果进行比较。测试器在它们到达正在运行的应用程序之前捕获集成错误：写入错误描述符的 setter、读取不同属性的 getter、无法往返的默认值，或缺失访问器。

组件的基线测试看起来是这样的：

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

有些描述符不遵循标准的 getter 和 setter 约定，或者依赖于测试无法满足的外部状态。用 `@PropertyExclude` 对其进行注释以跳过它们。

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### 自定义 getter 和 setter 名称 {#custom-getter-and-setter-names}

如果描述符使用非标准的访问器名称，请用 `@PropertyMethods` 声明它们。

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target` 参数接受一个类，当访问器不在组件本身时，它会有其他地方。

有关测试表面的更多详细信息，请参见 [PropertyDescriptorTester](../testing/property-descriptor-tester)。

## 关注接口 {#concern-interfaces}

关注接口为 `ElementComposite` 子类组件提供功能，而无需自己编写实现。这些接口将调用转发到底层元素。实现组件应支持的接口，使用子类类型进行参数化，以便链式返回组件：

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // 无需实现。
}

MyBadge badge = new MyBadge()
    .setText("新")
    .addClassName("突出显示")
    .setStyle("color", "var(--dwc-color-primary)");
```

上面的三个接口覆盖了 `MyBadge` 所需的所有内容，且类中不需要任何方法主体。`HasText` 曝露 `setText()` 并写入元素的文本内容。`HasClassName` 曝露 `addClassName()`，使徽章可以从 CSS 中进行定位。`HasStyle` 曝露 `setStyle()` 用于内联样式。

有关可用接口的完整集以及每个接口提供的内容，请参见 [关注接口](./component-fundamentals#concern-interfaces) 文章中的理解组件。如果默认转发与包装元素所暴露的不匹配，则在子类中重写该方法。

## 事件 {#events}

### 事件注册 {#event-registration}

当浏览器中发生某些事情时，web 组件会调度 DOM 事件。要从 Java 响应这些事件，使用 `addEventListener()` 监听它们。组件调度的事件集各不相同，因此请检查组件自己的文档以获取可用事件的名称和有效负载。

`ElementComposite` 支持防抖、节流、过滤和注册监听器上的自定义事件数据。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 仅接受注释为 `@EventName` 的事件类，与 `Element` 不同，后者接受任意字符串事件名称。
:::

### 内置事件类 {#built-in-event-classes}

`ElementClickEvent` 是 `ElementComposite` 附带的一个内置事件类。它呈现基础元素上的鼠标点击事件，并具有用于坐标的类型化访问器（`getClientX()`、`getClientY()`）、按钮信息（`getButton()`）和修饰键（`isCtrlKey()`、`isShiftKey()` 等）。

要在子类的公共 API 上公开点击处理，实现 `HasElementClickListener<T>` 关注接口。它提供默认的 `onClick()` 和 `addClickListener()` 方法，以委托给受保护的 `addEventListener()` 原始方法。

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

对于其他底层 web 组件调度的事件，定义自定义事件类。有关自定义事件类的更多信息，请参见 [自定义事件类](#custom-event-classes)。

### 事件有效载荷 {#event-payloads}

事件承载了从客户端到 Java 代码的数据。通过 `getData()` 访问该数据以获取原始事件数据，或在内置事件类上使用可用的类型化方法。有关有效负载处理的更多信息，请参阅 [事件指南](../building-ui/events)。

### 自定义事件类 {#custom-event-classes}

定义自定义事件类时使用 `@EventName` 和 `@EventOptions` 来捕获客户端数据，以便在类型化的 Java 事件中使用。当 Java 处理程序需要来自浏览器的值时，请使用此方法。

`@EventName` 将 Java 类绑定到组件在浏览器中调度的事件，因此类注释为 `@EventName("sl-change")` 时，每当基础元素发出 `sl-change` 时，都会触发该事件。`@EventOptions` 控制与该事件一起传输的内容。每个 `@EventData` 在其中将一个键与针对 DOM 事件评估的 JavaScript 表达式配对。结果可以在 Java 事件类中通过 `getData().get(key)` 访问。

下面的产品评审表单使用此模式与 [`sl-rating`](https://shoelace.style/components/rating)。自定义 `ChangeEvent` 将评级值作为类型化的 `double` 携带，监听器使用它来启用提交按钮：

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### 事件选项 {#event-options}

`ElementEventOptions` 配置事件有效负载、防抖或节流时间、过滤表达式和预执行代码。下面的代码片段显示了这些选项：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // 在事件触发之前执行 JavaScript
  .setCode("component.classList.add('processing');")

  // 仅在满足条件时触发
  .setFilter("component.value.length >= 2")

  // 在用户停止输入后延迟执行（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

// 在注册自定义事件类的监听器时应用这些选项
// （参见上面自定义事件类部分关于如何定义一个的内容）：
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` 仅公开类基于的格式 `addEventListener(Class, listener, options)`。使用它时必须与注释为 `@EventName` 的事件类结合使用。要直接注册基于字符串的事件名称，请调用 `getElement().addEventListener("input", listener, options)`。
:::

#### 性能控制 {#performance-control}

**防抖** 延迟执行直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 等待最后事件后的 300ms
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发，然后在安静期后触发

**节流** 限制执行频率：

```java
options.setThrottle(100); // 每 100ms 最多触发一次
```

## 与插槽交互 {#interacting-with-slots}

插槽是 web 组件内部的占位符，用户可以向其中填充内容。web 组件在其模板中使用 `<slot>` 或 `<slot name="...">` 声明其插槽，而包装器则曝露方法将 Java 组件放入这些插槽中。

要向插槽添加内容，请扩展 `ElementCompositeContainer` 而不是 `ElementComposite`。容器具有与 `ElementComposite` 相同的属性和属性机制，以及添加子项所需的方法。通过 `add()` 添加的子项将进入默认插槽。通过 `getElement().add(slotName, components)` 添加的子项将进入命名插槽。

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

下面的演示显示了两个使用 [`sl-card`](https://shoelace.style/components/card) 构建的定价卡片，从 Java 中填充 `header`、默认和 `footer` 插槽：

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### 检查插槽内容 {#inspecting-slot-contents}

基础的 `Element`（通过 `getElement()` 访问）提供方法以读取当前分配给插槽的内容：

- **`findComponentSlot()`**：搜索所有插槽以查找特定组件，并返回包含它的插槽的名称，如果组件不在任何插槽中，则返回空字符串。
- **`getComponentsInSlot()`**：返回分配给给定插槽的组件列表。可选择接受一个类类型以过滤结果。
- **`getFirstComponentInSlot()`**：返回分配给插槽的第一个组件。可选择接受一个类类型以过滤。
