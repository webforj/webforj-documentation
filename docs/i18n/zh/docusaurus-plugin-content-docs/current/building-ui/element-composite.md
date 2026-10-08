---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2a742b2589b096aff73a1fcb67e041c1
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类包装自定义 HTML 元素或 [web 组件](https://developer.mozilla.org/en-US/docs/Web/API/Web_components)。它将你的 Java 类绑定到底层 `Element`，并允许你通过 Java 操作该元素的属性、属性和事件。当将 web 组件集成到 webforJ 应用中时使用。

:::tip 何时使用 `ElementComposite`
当包装第三方 web 组件，而 webforJ 尚未提供时，请使用 `ElementComposite`。如果有内置的 webforJ 组件能够满足用例（例如 `TextField`、`ColorField`、`Button` 等），请使用那种情况。对于不需要重用的一次性 DOM 操作，可以直接使用 `Element` 类，无需包装。
:::

本指南演示如何使用 `ElementComposite` 类实现 [Web Awesome 相对时间 web 组件](https://webawesome.com/docs/components/relative-time/)。

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## 类注解 {#class-annotations}

在 `ElementComposite` 子类的顶部常会出现三个注解：`@NodeName` 声明组件包装的 HTML 标签，`@JavaScript` 和 `@StyleSheet` 加载任何底层 web 组件所需的客户端资产。`@NodeName` 是必需的，并特定于 `ElementComposite`。`@JavaScript` 和 `@StyleSheet` 是通用的 webforJ 资产注解，适用于任何类，包括视图、组件或 `App` 类。

### `@NodeName` {#nodename}

`@NodeName` 注解声明组件包装的 HTML 标签。webforJ 在创建 DOM 中的底层元素时使用此名称。

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

标签名称必须与客户端注册的自定义元素匹配。没有此注解，框架无法确定创建哪个元素。

在子类中，`getNodeName()` 用于读取声明的标签，而 `getElement()` 返回底层 `Element`，以便你可以直接调用其 DOM 级方法。

### `@JavaScript` {#javascript}

`@JavaScript` 注解加载定义或注册底层 web 组件的脚本。将其放在类上，以便只有在使用组件时该脚本才会加载。

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

允许多个 `@JavaScript` 注解，webforJ 会自动去重加载。如果多个组件依赖于同一个脚本，该脚本不会加载两次。

请参阅 [导入 JavaScript 文件](../managing-resources/importing-assets#importing-javascript-files) 获取完整选项，包括 `top`、`attributes` 和加载时间。

### `@StyleSheet` {#stylesheet}

`@StyleSheet` 注解加载组件所需的 CSS 文件。它对于附带单独样式表的第三方组件或将组件特定样式与包装器捆绑在一起非常有用。

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

对于本地打包资产，使用 `ws://` 前缀引用 `resources/static` 中的文件：

```java
@StyleSheet("ws://components/relative-time.css")
```

请参阅 [导入 CSS 文件](../managing-resources/importing-assets#importing-css-files) 获取完整选项。

## 属性和属性描述符 {#property-and-attribute-descriptors}

属性和属性表示 web 组件的状态，通常保存数据或配置。`ElementComposite` 通过 `PropertyDescriptor` 两者都暴露。

`PropertyDescriptor` 上的两个工厂方法为每个绑定目标生成描述符：

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` 绑定到 DOM 节点上的 JavaScript 属性。`PropertyDescriptor.attribute()` 绑定到 HTML 属性。第一个参数是 web 组件所期待的名称，第二个是默认值，这也确定了描述符的 Java 类型。

在组件内将描述符声明为私有字段，然后通过 `set(PropertyDescriptor<V> property, V value)` 和 `get(PropertyDescriptor<V> property)` 进行读取和写入。

:::info
属性是 DOM 节点的内部状态，在标记中不会反映。属性是 HTML 标记，外部脚本和 CSS 可见。
:::

```java
// ElementComposite 类中名为 "title" 的示例属性
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// ElementComposite 类中名为 "value" 的示例属性
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

上述调用直接使用 `set()` 来显示原始形式。实际上，`set()` 和 `get()` 是 `ElementComposite` 上的 `protected` 方法。它们是将 Java 值与底层元素同步的原始层，而不是公共 API 使用者调用的内容。预期模式是将 `PropertyDescriptor` 保持为私有，并编写公共 `setX()` 和 `getX()` 方法，委托给原始方法。

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

对 `set(descriptor, value)` 的单个调用同时做了三件事。它将值通过 `setProperty()` 推送到客户端以用于属性，或通过 `setAttribute()` 用于属性。它在本地服务器端缓存中存储该值，组件实例每个映射一个。并记录与值一起的运行时类型，以便后续的 `get()` 调用知道如何进行反序列化。

该本地缓存是 `get()` 本身可以便宜的原因，因为 `get(descriptor)` 会从服务器端存储中返回缓存值，而不会进行网络调用，因为每个 `set()` 都确保缓存与客户端同步。可选的第二个 `boolean` 参数控制是否绕过缓存并从浏览器读取。

```java
String cached = get(heading);            // 从服务器端缓存读取
String live = get(heading, true);        // 强制从浏览器读取
```

当值可以在客户端更改而客户端服务器不知情，例如输入的 `<input>` 值时，请将 `fromClient` 设置为 true。对于服务器驱动的属性，默认避免往返。

可选的第三个参数是一个 `java.lang.reflect.Type`，用来控制结果的反序列化方式。webforJ 按此顺序解析类型：如果传入了显式的 `Type` 参数，则是该参数，然后是通过先前 `set()` 在同一描述符上记录的运行时类型，然后是 `Object.class`。通常，通过先前 `set()` 记录的类型已经足够，因此第三个参数通常可以省略。当记录的类丢失反序列化所需的信息时，例如，参数化类型如 `List<String>` 的运行时类只是 `ArrayList`。

下面的演示基于 web 组件的文档添加相对时间属性，并通过 getter 和 setter 公开它们。活动提要中的每一行使用不同的 `format` 和 `numeric` 值，以展示同一组件在不同配置下的渲染方式。

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### 属性与属性的对比 {#properties-versus-attributes}

虽然 `PropertyDescriptor.property()` 和 `PropertyDescriptor.attribute()` 看似可以互换，但它们针对底层元素的不同部分。选择错误会导致值静默失败。

属性是 DOM 节点上的 JavaScript 对象属性。它们可以持有任何类型，包括字符串、布尔值、数字、对象和数组，并且表示元素的当前运行时状态。设置属性是直接的 JavaScript 赋值。

属性是 HTML 标记。它们位于元素的开标签上，始终是字符串，并表示元素的初始配置。设置属性会触发 DOM 变更和字符串转换。

在某些情况下，两者保持同步。在其他情况下，它们会分歧。`<input>` 的 `value` 是经典示例：`value` 属性是初始值，而 `value` 属性是用户输入的当前值。在用户输入后读取属性会返回原始标记，而读取属性会返回字段的当前内容。

使用 **属性** 用于：

- **频繁更改的运行时状态**: 计数器、当前选择、输入值
- **非字符串类型**: 布尔值、数字、对象、数组
- **性能敏感的更新**: 属性跳过属性所需的字符串转换

使用 **属性** 用于：

- **初始配置**: 组件连接时仅读取一次的设置
- **CSS 选择器**: 你希望通过选择器如 `[disabled]` 或 `[variant="danger"]` 来定位的值
- **可访问性钩子**: `aria-label`、`role` 和其他 ARIA 属性
- **字符串类型的设置，变化很少**

在包装第三方 web 组件时，请查看组件的文档，以确认哪个名称映射到属性，哪个映射到属性。对只作为属性公开的属性使用 `PropertyDescriptor.attribute()` 将不起作用，反之亦然。组件将静默地忽略该值。

### 属性类型 {#typing-properties}

描述符以其值的 Java 类型为参数化。完整的声明语法为：

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` 泛型参数声明值的类型。默认值的运行时类型也固定了 `T`，因此泛型参数通常不需要显式指定。webforJ 在与客户端通信时使用 `T` 来序列化和反序列化值。

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

对于基本类型，其序列化是自动的，也包括它们的包装类型和 `String`。对于复杂类型，该值在赋给客户端的属性之前会被序列化为 JSON。

### 验证值 {#validating-values}

在调用 `set()` 之前验证 setter 中的值。setter 是自然的强制执行点，因为每次变更都是通过它进行的。

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

对于可空引用，请使用 `Objects.requireNonNull()`，以便在边界处暴露失败，而不是在呈现流程中晚些时候。

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

避免在 `get()` 中验证。读取应该保持便宜且一致。

### 枚举风格的属性 {#enum-style-properties}

大多数 web 组件期望小写或 kebab-case 字符串值来进行枚举类属性（`theme="primary"`、`expanse="xs"`）。webforJ 使用 Gson 来序列化枚举，但 Gson 的默认表示是常量名称的大写形式。使用 `@SerializedName` 为每个常量注释，以便序列化值与 web 组件期望的匹配。

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

使用枚举类型声明描述符，并在 setter 和 getter 中直接使用枚举。

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

这是 webforJ 内置组件用于 `Theme`、`Expanse` 和类似枚举的相同模式。公共 Java API 仍然保持类型安全，而 web 组件接收到的值是来自 `@SerializedName` 的字符串。

### 测试属性 {#testing-properties}

`PropertyDescriptorTester` 验证组件中每个 `PropertyDescriptor` 都正确连接。它扫描类中的描述符字段，使用默认值调用每个 setter，并将结果与 getter 返回的结果进行比较。测试器在集成错误到达运行应用之前捕获：一个写入错误描述符的 setter，一个读出不同属性的 getter，一个不能循环的默认值，或缺少对声明描述符的访问器。

组件的基本测试看起来是这样的：

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

某些描述符不遵循标准的 getter 和 setter 约定，或依赖于测试无法满足的外部状态。用 `@PropertyExclude` 注解它们以跳过它们。

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

`target` 参数接收一个类，当访问器位于组件以外的某处时。

有关测试表面的更多详细信息，请参阅 [PropertyDescriptorTester](../testing/property-descriptor-tester)。

## 关注接口 {#concern-interfaces}

关注接口提供 `ElementComposite` 子类组件的功能，而无需自己编写实现。这些接口将调用转发给底层元素。实现将支持的接口，使用子类类型参数化，使链式调用返回组件：

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

上面的三个接口涵盖了 `MyBadge` 需要的一切，而类中不需要任何方法体。`HasText` 暴露 `setText()` 并写入元素的文本内容。`HasClassName` 暴露 `addClassName()`，允许从 CSS 定位徽章。`HasStyle` 暴露 `setStyle()` 以进行行内样式。

有关可用接口的完整集合及其提供的内容，请参阅 [关心接口](./component-fundamentals#concern-interfaces) 在理解组件文章中。如果默认转发与包装元素公开的内容不匹配，请在子类中覆盖该方法。

## 事件 {#events}

### 事件注册 {#event-registration}

当浏览器中发生某些事情时，web 组件会调度 DOM 事件。要从 Java 做出反应，请使用 `addEventListener()` 监听这些事件。组件调度的事件集合各不相同，因此请查看组件自己的文档以获取可用的名称和负载。

`ElementComposite` 支持去抖动、限流、过滤和注册监听器上的自定义事件数据。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 仅接受带有 `@EventName` 注解的事件类，而 `Element` 接受任何字符串事件名称。
:::

### 内置事件类 {#built-in-event-classes}

`ElementClickEvent` 是 `ElementComposite` 附带的内置事件类。它面向底层元素的鼠标点击事件，提供坐标的类型访问器（`getClientX()`、`getClientY()`）、按钮信息（`getButton()`）和修饰键（`isCtrlKey()`、`isShiftKey()` 等）。

要在子类的公共 API 中公开点击处理，请实现 `HasElementClickListener<T>` 关注接口。它提供默认的 `onClick()` 和 `addClickListener()` 方法，委派给受保护的 `addEventListener()` 原始方法。

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

对于底层 web 组件调度的任何其他事件，定义一个自定义事件类。请参阅 [自定义事件类](#custom-event-classes)。

### 事件负载 {#event-payloads}

事件将数据从客户端传递到你的 Java 代码。通过 `getData()` 访问这些数据以获取原始事件数据，或者在可用的内置事件类上使用类型方法。有关高效负载处理的更多信息，请参阅 [事件指南](../building-ui/events)。

### 自定义事件类 {#custom-event-classes}

使用 `@EventName` 和 `@EventOptions` 定义自定义事件类，以便在 Java 事件中捕获客户端数据。使用此方法当 Java 处理程序需要来自浏览器的值时。

`@EventName` 将 Java 类绑定到组件在浏览器中调度的事件，因此标注为 `@EventName("change")` 的类会在底层元素发出 `change` 时触发。`@EventOptions` 控制与该事件一起传输的内容。每个 `@EventData` 在其中将键与对 DOM 事件求值的 JavaScript 表达式配对。结果在 Java 事件类中通过 `getData().get(key)` 获得。

下面的产品评论表单使用了此模式，与 [`wa-rating`](https://webawesome.com/docs/components/rating/) 一起。自定义 `ChangeEvent` 将评级值作为类型 `double` 传递，监听器使用它来启用提交按钮：

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### 事件选项 {#event-options}

`ElementEventOptions` 配置事件负载、去抖动或限流时机、过滤表达式和预执行代码。下面的代码片段显示了选项：

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

  // 等待用户停止输入后再延迟执行（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

// 在为自定义事件类注册监听器时应用这些选项
// （有关如何定义自定义事件类，请参阅自定义事件类部分）：
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` 只公开基于类的形式 `addEventListener(Class, listener, options)`。与注解为 `@EventName` 的事件类一起使用。如果直接针对字符串事件名称注册，请调用 `getElement().addEventListener("input", listener, options)`。
:::

#### 性能控制 {#performance-control}

**去抖动** 延迟执行，直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一个事件后等待 300ms
```

可用的去抖动阶段：

- `LEADING`: 立即触发，然后等待
- `TRAILING`: 等待安静期，然后触发（默认）
- `BOTH`: 立即触发并在安静期后触发

**限流** 限制执行频率：

```java
options.setThrottle(100); // 最多每 100ms 触发一次
```

## 与插槽互动 {#interacting-with-slots}

插槽是 web 组件内部的占位符，用户将内容填入。web 组件在其模板中通过 `<slot>` 或 `<slot name="...">` 声明其插槽，而包装器则公开将 Java 组件放入这些插槽的方法。

要将内容添加到插槽，请扩展 `ElementCompositeContainer` 而不是 `ElementComposite`。该容器携带相同的属性和描述符机械以及添加子项所需的方法。通过 `add()` 添加的子项将进入默认插槽。通过 `getElement().add(slotName, components)` 添加的子项将进入命名插槽。

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
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### 检查插槽内容 {#inspecting-slot-contents}

底层 `Element`（通过 `getElement()` 访问）提供方法来读取当前分配给插槽的内容：

- **`findComponentSlot()`**: 搜索所有插槽以查找特定组件并返回其所在插槽的名称，如果该组件不在任何插槽中则返回空字符串。
- **`getComponentsInSlot()`**: 返回分配给特定插槽的组件列表。可选择接受类类型以过滤结果。
- **`getFirstComponentInSlot()`**: 返回分配给插槽的第一个组件。可选择接受类类型以过滤。
