---
sidebar_position: 3
title: 使用组件
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
_i18n_hash: df0f3d5a956eda1abd755f646899a7cc
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是 webforJ 应用程序的构建块。无论您使用内置组件如 `Button` 和 `TextField`，还是使用团队提供的自定义组件，与它们的交互方式都遵循相同的一致模型：您配置属性、管理状态，并将组件组合成布局。

本指南侧重于日常操作：不是组件内部的工作原理，而是在实践中如何完成任务。

## 组件属性 {#component-properties}

每个组件都提供控制其内容、外观和行为的属性。其中大多数都有专用的、类型化的 Java 方法（`setText()`、`setTheme()`、`setExpanse()` 等），这是您在 webforJ 中配置组件的主要方式。以下部分涵盖了广泛适用的属性和方法。

### 文本内容 {#text-content}

`setText()` 方法将组件的可见文本设置为字面字符，例如 `Button` 上的标题或 `Label` 的内容。对于像 `TextField` 这样的输入组件，使用 `setValue()` 来设置字段的当前值。

```java
Button button = new Button();
button.setText("点击我");

Label label = new Label();
label.setText("状态：准备就绪");

TextField field = new TextField();
field.setValue("初始值");
```

使用 `setText()` 编写的标记将以那些字符的形式显示，并且从不运行，这样可以防止来自用户输入或外部数据的文本被解释为实时标记。

```java
// 作为字面字符 "<b>状态：准备就绪</b>" 显示
component.setText("<b>状态：准备就绪</b>");
```

:::note 使用 `<html>` 标签
早期版本的 webforJ 将包裹在 `<html>` 中并传递给 `setText()` 的值视为 HTML。此行为已被弃用，并将在 webforJ 27.00 中移除。

第一次将包裹在 `<html>` 中的值传递到 `setText()` 时，会记录一个警告，指明组件名称和调用位置，以便将调用移到 `setHtml()`。

为了提前采用 webforJ 27.00 的默认设置，请将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用程序中，通过 `webforj.legacy-html-in-text` 设置相同的值。

```java
// webforj.legacyHtmlInText = true（默认）
component.setText("<html><b>状态：准备就绪</b></html>"); // 渲染为粗体

// webforj.legacyHtmlInText = false
component.setText("<html><b>状态：准备就绪</b></html>"); // 显示字符 <b>状态：准备就绪</b>
```
:::

### 渲染 HTML {#rendering-html}

某些组件还支持 `setHtml()`，用于需要渲染内联 HTML 标记的内容：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

:::danger 跨站脚本 (XSS)
作为对抗 [跨站脚本 (XSS) 攻击](/docs/security/application-security/common-threats#cross-site-scripting-xss) 的预防，只对您直接控制的内容使用 `setHtml()`。
:::

### HTML 属性 {#html-attributes}

在 webforJ 中，大部分配置是通过类型化 Java 方法完成的，而不是原始 HTML 属性。然而，`setAttribute()` 对于传递没有专用 API 的可访问性属性非常有用：

```java
Button button = new Button("提交");
button.setAttribute("aria-label", "提交表单");
button.setAttribute("aria-describedby", "form-hint");
```

:::note 检查组件支持
并非所有组件都支持任意属性。这取决于底层组件的实现。
:::

### 组件 ID {#component-ids}

您可以使用 `setAttribute()` 为组件的 HTML 元素分配一个 ID：

```java
Button submitButton = new Button("提交");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("电子邮件");
emailField.setAttribute("id", "email-input");
```

DOM ID 通常用于测试选择器和您的样式表中的 CSS 定位。

:::tip 优先使用类来针对多个组件
与 CSS 类不同，ID 应在您的应用程序中是唯一的。如果您需要针对多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还会在内部为组件分配自动标识符。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端与服务器间的通信。这些与您通过 `setAttribute()` 设置的 DOM `id` 属性是分开的。
:::

### 样式 {#styling}

三个方法涵盖了大多数样式需求：`setStyle()` 用于单个 CSS 属性值，`addClassName()` 和 `removeClassName()` 用于应用或移除在样式表中定义的 CSS 类。
使用 `setStyle()` 进行小范围或一次性的样式调整，使用 CSS 类来应用更大的或可重用的样式。

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("切换");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## 组件状态 {#component-state}

除了内容和外观，组件还有决定其可见性和是否响应用户交互的状态属性。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中呈现。`setEnabled()` 控制它是否接受输入或交互，同时保持可见。在大多数情况下，禁用比隐藏更为可取：禁用的按钮仍然传达出某个操作存在但尚不可用的信号，这比出现和消失更少让人困惑。

```java
// 当复选框选中时，显示一个额外的字段
TextField advancedField = new TextField("高级设置");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("显示高级设置");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// 仅在必填字段有值时启用按钮
Button submitButton = new Button("提交");
submitButton.setEnabled(false);

TextField nameField = new TextField("姓名");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning 禁用和隐藏不是安全保障
`setVisible(false)` 和 `setEnabled(false)` 只影响用户界面。它们并不能阻止决心强烈的用户通过浏览器或构造请求触发底层操作，因此切勿依赖它们来保护敏感操作。始终在服务器上实施访问控制。有关更多详细信息，请参见 [禁用和隐藏不是安全保障](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security)。
:::

以下登录表单展示了 `setEnabled()` 的实际应用。只有当两个字段都有内容时，登录按钮才保持禁用，清楚地向用户表明在继续之前需要输入：

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
]}
height='450px'
/>

## 与容器一起工作 {#working-with-containers}

在 webforJ 中，布局由容器处理，容器是持有其他组件并控制它们排列方式的组件。您不需要手动定位子组件；相反，您将它们添加到容器中并配置该容器的布局属性。

### 添加组件 {#adding-components}

所有容器都提供 `add()` 方法。您可以一次传递一个组件或所有组件：

```java
FlexLayout container = new FlexLayout();

container.add(new Button("点击我"));

TextField nameField = new TextField("姓名");
TextField emailField = new TextField("电子邮件");
Button submitButton = new Button("提交");

container.add(nameField, emailField, submitButton);
```

### 布局选项 {#layout-options}

`FlexLayout` 是 webforJ 中主要的布局容器，涵盖了大多数用例：行、列、对齐、间距和换行。对于更复杂的排列，例如 CSS 网格或自定义定位，您可以通过在任何容器组件上施加 CSS 直接使用 `setStyle()` 或 `addClassName()`。请参阅 [FlexLayout](/docs/components/flex-layout) 文档以获取完整的布局选项。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中常见的 `setVisible()` 用法是仅在相关时显示额外的 UI。这保持了界面的专注，减少了视觉杂乱。您可以在用户输入直接反应的情况下显示当前布局的一部分，而不是导航到新视图。

以下设置面板展示了这一点：基本通知偏好始终可见，只有当用户请求时，才会出现高级选项部分。当任何设置更改时，保存按钮会立即激活：

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
]}
height='450px'
/>

### 容器管理 {#container-management}

使用 `remove()` 和 `removeAll()` 在运行时将组件从容器中移除：

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("临时按钮");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

当您需要完全替换内容时，例如用加载指示器换取加载数据，这非常有用。

## 表单验证 {#form-validation}

协调多个组件来控制提交操作是 webforJ UI 中一种常见模式。基本思想是每个输入字段注册一个侦听器，每当值更改时，表单重新评估是否满足所有条件，并相应更新提交按钮。

下面的示例手动连接这些，您可以看到组件状态与事件侦听器是如何协同工作的。这不是实际表单的推荐方法：手动侦听器逻辑随着表单的增长变得难以维护，并且不会将您的组件与基础数据模型连接起来。

:::tip 使用数据绑定进行表单验证
对于生产表单，使用 [数据绑定](/docs/data-binding/overview)。它涵盖验证、组件与您的模型之间的双向同步以及通过 `BindingContext` 的值转换。这里展示的手动模式仅用于说明。
:::

在此联系表单中，姓名字段不能为空，电子邮件必须包含 `@` 符号，消息的长度必须至少为 10 个字符：

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
]}
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件在创建后不必保持固定状态。您可以在任何时刻根据应用事件更新文本、交换 CSS 类和切换启用状态。一个常见示例是在长时间运行的任务期间提供反馈：

```java
Label statusLabel = new Label("准备就绪");
Button startButton = new Button("开始处理");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("处理中...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("完成");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

在任务运行时禁用按钮可以防止重复提交，更新标签使用户了解正在发生的事情。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口让您可以观察组件生命周期事件，而无需对组件本身进行修改。当您需要响应组件的创建或销毁时，这非常有用。例如，您可能会使用它来维护活动组件的注册表或在组件被移除时释放外部资源。

### 基本用法 {#basic-usage}

在任何组件上调用 `addLifecycleObserver()` 来注册回调。回调接收组件和生命周期事件：

```java
Button button = new Button("观察我");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("按钮已创建");
            break;
        case DESTROY:
            System.out.println("按钮已销毁");
            break;
    }
});
```

### 模式：资源注册表 {#pattern-resource-registry}

DESTROY 事件对于保持注册表自动同步尤其有用。与手动去移除不再需要的组件相比，您让组件自己通知注册表：

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();

    public void track(Component component, String name) {
        activeComponents.put(name, component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### 模式：组件协调 {#pattern-component-coordination}

管理一组相关组件的协调类可以使用相同的方法来保持其内部列表的准确性：

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();

    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }

    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### 何时使用 {#when-to-use}

使用 `ComponentLifecycleObserver` 来：
- 构建组件注册表
- 实施日志或监视
- 协调多个组件
- 清理外部资源

要在组件附加到 DOM 后执行代码，请参阅 [Composing Components](/docs/building-ui/composing-components) 指南中的 `whenAttached()`。

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 携带任意服务器端数据。这两个方法都需要一个键来标识数据。当您需要将领域对象或上下文与组件关联而无需管理单独的查找结构时，这非常有用。

```java
Button button = new Button("处理");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据从不发送到客户端，您可以安全地存储敏感信息或大对象，而不会影响网络流量。
