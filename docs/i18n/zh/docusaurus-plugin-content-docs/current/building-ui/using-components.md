---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 046749107d0e78ccfaab4017d4e374d1
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是 webforJ 应用程序的构建块。无论您是使用内置组件如 `Button` 和 `TextField`，还是使用团队提供的自定义组件，您与它们的交互方式遵循相同的一致模型：您配置属性、管理状态，并将组件组合到布局中。

本指南专注于这些日常操作：不是组件如何工作的内部细节，而是如何在实践中使用它们完成任务。

## 组件属性 {#component-properties}

每个组件都暴露出控制其内容、外观和行为的属性。大多数属性具有专用的类型化 Java 方法（`setText()`、`setTheme()`、`setExpanse()` 等），这是您在 webforJ 中配置组件的主要方式。以下部分涵盖适用于广泛组件类型的属性和方法。

### 文本内容 {#text-content}

`setText()` 方法将组件的可见文本设置为字面字符，例如 `Button` 上的标题或 `Label` 的内容。对于像 `TextField` 这样的输入组件，请使用 `setValue()` 来设置字段的当前值。

```java
Button button = new Button();
button.setText("点击我");

Label label = new Label();
label.setText("状态：准备好");

TextField field = new TextField();
field.setValue("初始值");
```

使用 `setText()` 写入的标记将作为这些字符显示，且永远不会运行，这样来自用户输入或外部数据的文本不会被解释为实时标记。

```java
// 作为字面字符 "<b>状态：准备好</b>" 显示
component.setText("<b>状态：准备好</b>");
```

:::note 使用 `<html>` 标签
早期版本的 webforJ 将包裹在 `<html>` 中并传递给 `setText()` 的值视为 HTML。这种行为已被弃用，并将在 webforJ 27.00 中删除。

第一次以 `<html>` 包装的值到达 `setText()` 时，会记录一个警告，指出组件名称和调用站点，以便将调用移动到 `setHtml()`。

要提前采用 webforJ 27.00 默认值，请将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用中，通过 `webforj.legacy-html-in-text` 设置相同的值。

```java
// webforj.legacyHtmlInText = true（默认值）
component.setText("<html><b>状态：准备好</b></html>"); // 渲染为粗体

// webforj.legacyHtmlInText = false
component.setText("<html><b>状态：准备好</b></html>"); // 显示字符 <b>状态：准备好</b>
```
:::

### 渲染 HTML {#rendering-html}

某些组件也支持 `setHtml()`，用于需要在内容中渲染内联 HTML 标记的情况：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

:::danger 跨站脚本攻击 (XSS)
为了防范 [跨站脚本攻击 (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss)，仅在您直接控制的内容上使用 `setHtml()`。
:::

### HTML 属性 {#html-attributes}

在 webforJ 中，大多数配置通过类型化的 Java 方法而不是原始 HTML 属性进行。然而，`setAttribute()` 对于传递没有专用 API 的可访问性属性是有用的：

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

DOM ID 通常用于测试选择器和样式表中的 CSS 定位。

:::tip 优先使用类进行多组件定位
与 CSS 类不同，ID 在您的应用中应是唯一的。如果您需要定位多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还内部分配组件的自动标识符。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端与服务器之间的通信。这些与您使用 `setAttribute()` 设置的 DOM `id` 属性是分开的。
:::

### 样式 {#styling}

三个方法覆盖大多数样式需求：`setStyle()` 用于单个 CSS 属性值，`addClassName()` 和 `removeClassName()` 用于应用或移除在样式表中定义的 CSS 类。
对于小的或一次性的样式调整，请使用 `setStyle()`，而对于更大或可重用的样式，请使用 CSS 类。

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

除了内容和外观，组件还有确定其是否可见以及是否响应用户交互的状态属性。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中渲染。`setEnabled()` 控制它在保持可见的同时是否接受输入或交互。在大多数情况下，禁用优于隐藏：禁用的按钮仍然传达存在一个操作，但尚不可用，这比让它出现和消失要少了很多迷惑。

```java
// 当复选框被选中时显示额外字段
TextField advancedField = new TextField("高级设置");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("显示高级设置");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// 仅在所需字段有值时启用按钮
Button submitButton = new Button("提交");
submitButton.setEnabled(false);

TextField nameField = new TextField("姓名");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning 禁用和隐藏并不等于安全
`setVisible(false)` 和 `setEnabled(false)` 仅影响 UI。它们无法阻止决心强的用户通过浏览器或伪造请求调用底层操作，因此永远不要依赖它们来保护敏感操作。始终在服务器上强制执行访问控制。有关更多详细信息，请参见 [禁用和隐藏并不等于安全](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security)。
:::

以下登录表单演示了 `setEnabled()` 的实际应用。只有在两个字段都有内容时，登录按钮才会启用，明确向用户表明在继续之前需要输入：

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## 使用容器 {#working-with-containers}

在 webforJ 中，布局由容器处理，容器是持有其他组件并控制其排列方式的组件。您不需要手动定位子组件；相反，您将它们添加到容器中，并配置该容器的布局属性。

### 添加组件 {#adding-components}

所有容器都提供 `add()` 方法。您可以一次传递一个组件或一次传递所有组件：

```java
FlexLayout container = new FlexLayout();

container.add(new Button("点击我"));

TextField nameField = new TextField("姓名");
TextField emailField = new TextField("电子邮件");
Button submitButton = new Button("提交");

container.add(nameField, emailField, submitButton);
```

### 布局选项 {#layout-options}

`FlexLayout` 是 webforJ 中的主要布局容器，涵盖大多数用例：行、列、对齐、间距和包装。对于更复杂的布局，如 CSS Grid 或自定义定位，您可以通过在任何容器组件上直接应用 CSS（`setStyle()` 或 `addClassName()`）来实现。请参阅 [FlexLayout](/docs/components/flex-layout) 文档以获取完整的布局选项。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中使用 `setVisible()` 的一个常见用途是仅在相关时显示额外的 UI。这使界面保持专注，减少视觉杂乱。您可以在用户输入后直接在当前布局中显示某个部分，而不是导航到新视图。

以下设置面板演示了这一点：基本通知偏好设置始终可见，而高级选项部分仅在用户请求时出现。一旦任何设置发生更改，保存按钮就会激活：

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### 容器管理 {#container-management}

使用 `remove()` 和 `removeAll()` 在运行时从容器中移除组件：

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("临时");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

当您需要完全替换内容时，例如用加载指示器替换加载数据时，这很有用。

## 表单验证 {#form-validation}

协调多个组件以阻止提交操作是 webforJ UI 中的常见模式。基本思想是每个输入字段注册一个监听器，每当值发生变化时，表单重新评估是否满足所有标准并相应更新提交按钮。

下面的示例手动连接了这些以便您看到组件状态和事件监听器如何协同工作。这并不是推荐的真实表单处理方法：手动监听器逻辑在表单增长时变得难以维护，并且它未将组件与底层数据模型连接起来。

:::tip 使用数据绑定进行表单验证
对于生产表单，使用 [数据绑定](/docs/data-binding/overview)。它涵盖验证、组件与您的模型之间的双向同步，以及通过 `BindingContext` 的值转换。这里显示的手动模式仅用于说明。
:::

在这个联系表单中，姓名字段不能为空，电子邮件必须包含 `@` 符号，消息必须至少 10 个字符长：

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件在创建后不必保持固定状态。您可以随时更新文本、交换 CSS 类和切换启用状态以响应应用事件。一个常见的例子是在长时间运行的任务中提供反馈：

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

在任务运行期间禁用按钮可以防止重复提交，更新标签则让用户了解正在发生的事情。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口允许您从组件本身外部观察组件生命周期事件。当您需要在组件创建或销毁时做出反应而不修改其实现时，这很有用。例如，您可能使用它来维护活动组件的注册表或在组件被移除时释放外部资源。

### 基本用法 {#basic-usage}

在任何组件上调用 `addLifecycleObserver()` 以注册回调。回调接收组件和生命周期事件：

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

DESTROY 事件对于保持注册表自动同步特别有用。与其手动移除不再需要的组件，您可以让组件通知注册表自己：

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

### 模式：组件协作 {#pattern-component-coordination}

管理一组相关组件的协调器类可以使用相同的方法保持其内部列表的准确性：

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

使用 `ComponentLifecycleObserver` 实现：
- 构建组件注册表
- 实现日志记录或监视
- 协调多个组件
- 清理外部资源

要在组件附加到 DOM 后执行代码，请参见 [Composing Components](/docs/building-ui/composing-components) 指南中的 `whenAttached()`。

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 承载任意服务器端数据。两个方法都接受一个键来标识数据。这在您需要将域对象或上下文与组件关联而不管理单独的查找结构时非常有用。

```java
Button button = new Button("处理");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据不会发送到客户端，您可以安全地存储敏感信息或大型对象，而不会影响网络流量。
