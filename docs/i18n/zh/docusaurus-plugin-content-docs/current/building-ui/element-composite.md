---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类封装自定义 HTML 元素或 [web 组件](https://developer.mozilla.org/en-US/docs/Web/API/Web_components)。它将您的 Java 类绑定到基础的 `Element`，并允许您通过 Java 操作该元素的属性、属性和事件。在将 web 组件集成到 webforJ 应用时使用它。

:::tip 使用 `ElementComposite` 的时机
当封装第三方 web 组件而 webforJ 尚未提供时，可以使用 `ElementComposite`。如果现有的 webforJ 组件能够满足使用情况（如 `TextField`、`ColorField`、`Button` 等），请优先使用它。对于不需要重复使用的临时 DOM 工作，可以直接使用 `Element` 类，而无需封装。
:::

本指南演示如何使用 `ElementComposite` 类实现 [Shoelace 相对时间 web 组件](https://shoelace.style/components/relative-time)。

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## 类注解 {#class-annotations}

在 `ElementComposite` 子类的顶部常见三种注解：`@NodeName` 声明组件封装的 HTML 标签，`@JavaScript` 和 `@StyleSheet` 加载任何基础 web 组件所依赖的客户端资产。`@NodeName` 是必需的，并且特定于 `ElementComposite`。`@JavaScript` 和 `@StyleSheet` 是通用的 webforJ 资产注解，适用于任何类，包括视图、组件或 `App` 类。

### `@NodeName` {#nodename}

`@NodeName` 注解声明组件封装的 HTML 标签。webforJ 在创建 DOM 中的基础元素时使用此名称。

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

标签名称必须与客户端注册的自定义元素匹配。没有此注解，框架无法确定要创建哪个元素。

在子类内部，`getNodeName()` 返回声明的标签，`getElement()` 返回基础的 `Element`，这样您可以直接在其上调用 DOM 级别的方法。

### `@JavaScript` {#javascript}

`@JavaScript` 注解加载定义或注册基础 web 组件的脚本。将其放在类上，以便脚本仅在使用组件时加载。

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

允许有多个 `@JavaScript` 注解，webforJ 会自动消除重复加载。如果多个组件依赖于同一脚本，则不会重复加载。

有关完整选项集，包括 `top`、`attributes` 和加载时机，请参见 [导入 JavaScript 文件](../managing-resources/importing-assets#importing-javascript-files)。

### `@StyleSheet` {#stylesheet}

`@StyleSheet` 注解加载组件所依赖的 CSS 文件。它对于提供单独样式表的第三方组件或将组件特定样式与封装一起打包非常有用。

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

对于本地捆绑的资产，请使用 `ws://` 前缀引用 `resources/static` 中的文件：

```java
@StyleSheet("ws://components/relative-time.css")
```

有关完整选项集，请参见 [导入 CSS 文件](../managing-resources/importing-assets#importing-css-files)。

## 属性和属性描述符 {#property-and-attribute-descriptors}

属性和属性表示 web 组件的状态，通常保存数据或配置。`ElementComposite` 通过 `PropertyDescriptor` 两者都暴露。

`PropertyDescriptor` 上的两个工厂方法生成描述符本身，一个用于每个绑定目标：

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` 绑定到 DOM 节点上的 JavaScript 属性。`PropertyDescriptor.attribute()` 绑定到 HTML 属性。第一个参数是 web 组件所期望的名称。第二个是默认值，这也固定了描述符的 Java 类型。

将描述符声明为组件上的私有字段，然后通过 `set(PropertyDescriptor<V> property, V value)` 和 `get(PropertyDescriptor<V> property)` 读取和写入。

:::info
属性是 DOM 节点上的内部状态，不反映在标记中。属性是 HTML 标记，外部脚本和 CSS 可见。
:::

```java
// 在 ElementComposite 类中名为 "title" 的示例属性
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// 在 ElementComposite 类中名为 "value" 的示例属性
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

上述调用直接使用 `set()` 展示原始形式。实际上，`set()` 和 `get()` 是 `ElementComposite` 上的 `protected` 方法。它们是将 Java 值与基础元素同步的原始层，而不是公共 API 消费者调用的。预期的模式是保持 `PropertyDescriptor` 私有，并编写公共 `setX()` 和 `getX()` 方法，将调用委托给原始方法。

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // 保护原始方法
    return this;
  }

  public String getHeading() {
    return get(heading);     // 保护原始方法
  }
}
```

对 `set(descriptor, value)` 的单次调用同时完成三件事。它通过 `setProperty()` 将值推送到客户端（用于属性）或 `setAttribute()`（用于属性）。它将值存储在本地服务器端缓存中，每个组件实例一个映射。并且它记录运行时类型与值一起，以便后续的 `get()` 调用知道如何反序列化。

这个本地缓存是 `get()` 能够便宜的原因。`get(descriptor)` 从服务器端存储返回缓存的值，而无需网络调用，因为每次 `set()` 都保持缓存与客户端同步。可选的 `boolean` 第二个参数控制是否绕过缓存并从浏览器读取。

```java
String cached = get(heading);            // 从服务器端缓存读取
String live = get(heading, true);        // 强制从浏览器读取
```

当值可以在客户端变化而服务器不知情时，将 `fromClient` 设置为 true，例如，输入 `<input>` 的值。对于服务器驱动的属性，默认情况下避免往返。

可选的第三个参数是 `java.lang.reflect.Type`，控制结果如何反序列化。webforJ 按以下顺序解析类型：显式 `Type` 参数（如果传递），然后是同一描述符上之前 `set()` 记录的运行时类型，然后是 `Object.class`。在实践中，之前 `set()` 记录的类型通常就足够，所以第三个参数通常可以省略。当记录的类丢失反序列化所依赖的信息时，例如像 `List<String>` 这样的参数化类型，其运行时类只是 `ArrayList`。

下面的演示根据 web 组件的文档添加相对时间的属性，并通过 getter 和 setter 将其暴露。活动馈送中的每一行使用不同的 `format` 和 `numeric` 值，以显示同一组件在不同配置下的呈现方式。

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### 属性与属性的区别 {#properties-versus-attributes}

尽管 `PropertyDescriptor.property()` 和 `PropertyDescriptor.attribute()` 看起来可互换，但它们针对基础元素的不同部分。选择错误会导致值悄然未应用。

属性是 DOM 节点上的 JavaScript 对象属性。它们可以保存任何类型，包括字符串、布尔值、数字、对象和数组，并表示元素的当前运行时状态。设置属性是直接的 JavaScript 赋值。

属性是 HTML 标记。它们存在于元素的开标签上，始终是字符串，表示元素的初始配置。设置属性会触发 DOM 变更和字符串转换。

对于某些情况，两者保持同步。而对于其他情况，它们则会偏离。`<input>` 的 `value` 是经典示例：`value` 属性是初始值，而 `value` 属性是用户键入的当前值。用户输入后读取属性会返回原始标记，但读取属性会返回字段的当前内容。

使用 **属性** 用于：

- **频繁变化的运行时状态**：计数器、当前选择、输入值
- **非字符串类型**：布尔值、数字、对象、数组
- **性能敏感的更新**：属性跳过对属性的字符串转换

使用 **属性** 用于：

- **初始配置**：组件连接时读取一次的设置
- **CSS 选择器**：您希望通过选择器进行定位的值，例如 `[disabled]` 或 `[variant="danger"]`
- **可访问性钩子**：`aria-label`、`role` 和其他 ARIA 属性
- **不常变化的类似字符串的设置**

在封装第三方 web 组件时，请检查组件的文档以确认哪个名称映射到属性，哪个映射到属性。对仅作为属性公开的内容使用 `PropertyDescriptor.attribute()` 将不起作用，反之亦然。组件将安静地忽略该值。

### 属性类型 {#typing-properties}

描述符由其值的 Java 类型参数化。完整声明语法为：

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` 泛型参数声明值的类型。默认值的运行时类型也会固定 `T`，因此泛型参数很少需要显式指定。webforJ 使用 `T` 在与客户端通信时序列化和反序列化值。

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

对于基本体、其包装类和 `String`，序列化是自动进行的。对于复杂类型，值在分配给客户端的属性之前以 JSON 格式序列化。

### 验证值 {#validating-values}

在调用 `set()` 之前，在 setter 中验证值。setter 是自然的强制执行点，因为每次变更都通过它流动。

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

对于可为 null 的引用，使用 `Objects.requireNonNull()`，以便将失败暴露在边界，而不是在渲染管道中后期。

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

避免在 `get()` 中进行验证。读取操作应保持便宜和一致。

### 枚举风格的属性 {#enum-style-properties}

大多数 web 组件希望以小写或连字符格式字符串值来映射类似枚举的属性（`theme="primary"`、`expanse="xs"`）。webforJ 使用 Gson 来序列化枚举，但 Gson 的默认表示是常量名称的大写字母。使用 `@SerializedName` 注解每个常量，以便序列化后的值匹配 web 组件的预期。

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

用枚举类型声明描述符，在 setter 和 getter 中直接使用枚举。

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

`PropertyDescriptorTester` 验证组件中每个 `PropertyDescriptor` 是否正确连接。它扫描类以查找描述符字段，用默认值调用每个 setter，并将结果与 getter 返回的值进行比较。测试器可以在应用运行之前捕获集成错误：写入错误描述符的 setter、读取不同属性的 getter、不会来回传递的默认值，或者缺少对声明描述符的访问器。

组件的基线测试如下所示：

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

一些描述符不符合标准的 getter 和 setter 约定，或者它们依赖于测试无法满足的外部状态。用 `@PropertyExclude` 注解它们以跳过。

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### 自定义 getter 和 setter 名称 {#custom-getter-and-setter-names}

如果描述符使用非标准访问器名称，请用 `@PropertyMethods` 声明它们。

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target` 参数接受一个类，当访问器位于组件本身以外的地方时使用。

有关测试表面的更多详细信息，请参见 [PropertyDescriptorTester](../testing/property-descriptor-tester)。

## 关注接口 {#concern-interfaces}

关注接口为 `ElementComposite` 子类组件提供功能，而无需自己编写实现。接口将调用转发给基础元素。实现组件应支持的接口，并以子类类型参数化，以便链式调用返回组件：

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // 无需实现。
}

MyBadge badge = new MyBadge()
    .setText("New")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

上述三个接口覆盖了 `MyBadge` 所需的一切，而无需在类中添加任何方法主体。`HasText` 公开 `setText()` 并写入元素的文本内容。`HasClassName` 公开 `addClassName()`，允许从 CSS 定位徽章。`HasStyle` 公开 `setStyle()` 用于内联样式。

有关可用接口的完整集合及其提供的每个功能，请参见 [关注接口](./component-fundamentals#concern-interfaces) 在理解组件文章中。如果默认转发与包装元素公开的内容不匹配，请在子类中重写该方法。

## 事件 {#events}

### 事件注册 {#event-registration}

Web 组件在浏览器发生某些事件时会调度 DOM 事件。要在 Java 中作出反应，请使用 `addEventListener()` 监听这些事件。组件调度的事件集各不相同，因此请检查组件的文档以获取可用的名称和有效负载。

`ElementComposite` 支持防抖、节流、过滤和注册监听器的自定义事件数据。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 仅接受带有 `@EventName` 注解的事件类，而 `Element` 则接受任何字符串事件名称。
:::

### 内置事件类 {#built-in-event-classes}

`ElementClickEvent` 是 `ElementComposite` 自带的唯一内置事件类。它在基础元素上暴露鼠标点击事件，并提供坐标（`getClientX()`、`getClientY()`）、按钮信息（`getButton()`）和修饰键（`isCtrlKey()`、`isShiftKey()` 等）的类型访问器。

要在子类的公共 API 上公开点击处理，请实现 `HasElementClickListener<T>` 关注接口。它提供默认的 `onClick()` 和 `addClickListener()` 方法，以便委托给受保护的 `addEventListener()` 原始方法。

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

对于基础 web 组件调度的任何其他事件，请定义一个自定义事件类。请参见 [自定义事件类](#custom-event-classes)。

### 事件有效负载 {#event-payloads}

事件将数据从客户端传递到您的 Java 代码。通过 `getData()` 访问原始事件数据，或在内置事件类可用时使用类型方法。有关有效负载处理的更多信息，请参见 [事件指南](../building-ui/events)。

### 自定义事件类 {#custom-event-classes}

使用 `@EventName` 和 `@EventOptions` 定义自定义事件类，以在类型化 Java 事件中捕获客户端数据。当 Java 处理程序需要来自浏览器的值时使用此功能。

`@EventName` 将 Java 类绑定到组件在浏览器中调度的事件，因此对注解 `@EventName("sl-change")` 的类在基础元素发出 `sl-change` 时被触发。`@EventOptions` 控制与事件一起传输的内容。每个 `@EventData` 都在其中将一个键与基于 DOM 事件计算的 JavaScript 表达式配对。结果可以通过 `getData().get(key)` 在 Java 事件类中访问。

下面的产品评论表单使用这种模式与 [`sl-rating`](https://shoelace.style/components/rating)。自定义 `ChangeEvent` 将评分值作为类型化的 `double` 传递，监听器将其用来启用提交按钮：

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### 事件选项 {#event-options}

`ElementEventOptions` 配置事件有效负载、防抖或节流时机、过滤表达式和预执行代码。下面的代码片段展示了选项：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // 在事件触发前执行 JavaScript
  .setCode("component.classList.add('processing');")
  
  // 仅在满足条件时触发
  .setFilter("component.value.length >= 2")
  
  // 等待用户停止输入后延迟执行（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

// 在注册自定义事件类的监听器时应用这些选项
// （见上述自定义事件类部分如何定义）：
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` 仅暴露类基形式 `addEventListener(Class, listener, options)`。使用它时需配合带有 `@EventName` 注解的事件类。要直接针对字符串事件名称注册，请调用 `getElement().addEventListener("input", listener, options)`。
:::

#### 性能控制 {#performance-control}

**防抖** 延迟执行直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一次事件后等待 300ms
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发并在安静期后触发

**节流** 限制执行频率：

```java
options.setThrottle(100); // 每 100ms 至多触发一次
```

## 与插槽交互 {#interacting-with-slots}

插槽是 web 组件内部的占位符，用户可以填充内容。web 组件通过 `<slot>` 或 `<slot name="...">` 在其模板中声明插槽，封装器则暴露方法，将 Java 组件放入这些插槽中。

要向插槽添加内容，请扩展 `ElementCompositeContainer` 而不是 `ElementComposite`。容器携带相同的属性和属性机制以及添加子项所需的方法。通过 `add()` 添加的子项进入默认插槽。通过 `getElement().add(slotName, components)` 添加的子项进入命名插槽。

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

下面的演示展示了两个使用 [`sl-card`](https://shoelace.style/components/card) 构建的定价卡，从 Java 填充 `header`、默认和 `footer` 插槽：

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### 检查插槽内容 {#inspecting-slot-contents}

基础的 `Element`（通过 `getElement()` 访问）提供方法来读取当前分配给插槽的内容：

- **`findComponentSlot()`**：搜索所有插槽以查找特定组件，并返回包含它的插槽名称，如果组件不在任何插槽中则返回空字符串。
- **`getComponentsInSlot()`**：返回分配给给定插槽的组件列表。可选地接受类类型以过滤结果。
- **`getFirstComponentInSlot()`**：返回分配给插槽的第一个组件。可选地接受类类型以过滤。
