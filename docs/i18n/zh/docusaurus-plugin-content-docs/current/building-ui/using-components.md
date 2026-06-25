---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

组件是 webforJ 应用的构建块。无论您是使用内置组件，例如 `Button` 和 `TextField`，还是与您的团队提供的自定义组件进行合作，您与它们的交互方式都遵循相同的一致模型：您配置属性，管理状态，并将组件组合成布局。

本指南专注于这些日常操作：不是组件如何工作的内部机制，而是如何在实践中使用它们完成任务。

## 组件属性 {#component-properties}

每个组件都暴露控制其内容、外观和行为的属性。大多数属性都有专门的、类型化的 Java 方法（`setText()`、`setTheme()`、`setExpanse()`等），这是您在 webforJ 中配置组件的主要方式。以下部分涵盖适用于组件类型的属性和方法。

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

使用 `setText()` 编写的标记以这些字符的形式显示，且从不运行，这使得来自用户输入或外部数据的文本不会被解释为实时标记。

```java
// 显示为字面字符 "<b>状态：准备好</b>"
component.setText("<b>状态：准备好</b>");
```

:::note 使用 `<html>` 标签
早期版本的 webforJ 将用 `<html>` 包裹的值视为 HTML，并传递给 `setText()`。此行为已被弃用，并将在 webforJ 27.00 中移除。

第一次到达 `setText()` 的 `<html>` 包裹值时，将记录一个警告，说明组件和调用站点，以便将调用移动到 `setHtml()`。

要提前采用 webforJ 27.00 的默认设置，请将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用中，通过 `webforj.legacy-html-in-text` 设置相同的值。

```java
// webforj.legacyHtmlInText = true（默认）
component.setText("<html><b>状态：准备好</b></html>"); // 渲染为粗体

// webforj.legacyHtmlInText = false
component.setText("<html><b>状态：准备好</b></html>"); // 显示字符 <b>状态：准备好</b>
```
:::

### 渲染 HTML {#rendering-html}

某些组件还支持 `setHtml()`，用于需要在内容中渲染行内 HTML 标记的情况：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

:::danger 跨站脚本攻击 (XSS)
作为对抗 [跨站脚本 (XSS) 攻击](/docs/security/application-security/common-threats#cross-site-scripting-xss) 的预防，只在您直接控制的内容上使用 `setHtml()`。
:::

### HTML 属性 {#html-attributes}

在 webforJ 中，大多数配置是通过类型化的 Java 方法而非原始 HTML 属性完成的。然而，`setAttribute()` 对于传递没有专门 API 的辅助属性非常有用：

```java
Button button = new Button("提交");
button.setAttribute("aria-label", "提交表单");
button.setAttribute("aria-describedby", "form-hint");
```

:::note 检查组件支持
并非所有组件都支持任意属性。这取决于底层组件的实现。
:::

### 组件 IDs {#component-ids}

您可以使用 `setAttribute()` 为组件的 HTML 元素分配一个 ID：

```java
Button submitButton = new Button("提交");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("邮箱");
emailField.setAttribute("id", "email-input");
```

DOM IDs 通常用于测试选择器和在样式表中的 CSS 定位。

:::tip 首选类以进行多组件定向
与 CSS 类不同，ID 应该在您的应用中是唯一的。如果需要定向多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还在内部自动分配标识符给组件。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端与服务器之间的通信。这些与您使用 `setAttribute()` 设置的 DOM `id` 属性是不同的。
:::

### 样式 {#styling}

三个方法涵盖了大多数样式需求：`setStyle()` 用于单个 CSS 属性值，而 `addClassName()` 和 `removeClassName()` 用于应用或移除在样式表中定义的 CSS 类。使用 `setStyle()` 进行小的或一次性的样式调整，使用 CSS 类以应用更大或可重用的样式。

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

:::note 旧方法
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) 是一种旧方法，通常不推荐在新项目中使用。在大多数情况下，保持样式在单独的 CSS 文件中。
:::

## 组件状态 {#component-state}

除了内容和外观之外，组件还有状态属性，决定它们是否可见以及是否对用户交互做出响应。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中渲染。`setEnabled()` 控制其是否在可见的情况下接受输入或交互。在大多数情况下，禁用比隐藏更为合适：禁用按钮仍然传达出一个存在但尚不可用的操作，这比让按钮出现和消失的情况更不让人困惑。

```java
// 当复选框被选中时显示额外的字段
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

以下登录表单演示了 `setEnabled()` 的实际应用。直到两个字段都有内容之前，登录按钮保持禁用，这清楚地向用户表明在继续之前需要输入：

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## 与容器的工作 {#working-with-containers}

在 webforJ 中，布局由容器处理，容器是存放其他组件并控制其排列方式的组件。您不需要手动定位子组件；相反，您将它们添加到一个容器，并配置该容器的布局属性。

### 添加组件 {#adding-components}

所有容器都提供一个 `add()` 方法。您可以一次传递一个组件或一次传递所有组件：

```java
FlexLayout container = new FlexLayout();

container.add(new Button("点击我"));

TextField nameField = new TextField("姓名");
TextField emailField = new TextField("邮箱");
Button submitButton = new Button("提交");

container.add(nameField, emailField, submitButton);
```

### 布局选项 {#layout-options}

`FlexLayout` 是 webforJ 中的主要布局容器，涵盖大多数用例：行、列、对齐、间距和换行。对于更复杂的排列，例如 CSS Grid 或自定义定位，您可以通过 `setStyle()` 或 `addClassName()` 直接应用 CSS 到任意容器组件。请参阅 [FlexLayout](/docs/components/flex-layout) 文档以获取完整的布局选项。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中使用 `setVisible()` 的常见方式是仅在相关时显示额外的 UI。这使界面保持聚焦，减少视觉杂乱。您可以在用户输入的直接回应中显示当前布局的某一部分，而不是导航到新视图。

以下设置面板演示了这一点：基本的通知首选项始终可见，只有在用户请求时，高级选项的部分才会出现。保存按钮在任何设置更改后激活：

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### 容器管理 {#container-management}

使用 `remove()` 和 `removeAll()` 在运行时将组件从容器中移除：

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("临时");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

当您需要完全替换内容时，这非常有用，例如将加载指示器与加载的数据进行交换。

## 表单验证 {#form-validation}

协调多个组件以限制提交操作是 webforJ 界面中的最常见模式之一。核心理念很简单：每个输入字段注册一个监听器，每当任何值更改时，表单重新评估是否满足所有标准，并相应更新提交按钮。

这比仅在用户点击提交后显示验证错误要更好，因为它提供了持续的反馈并防止不必要的提交。提交按钮作为指示器：禁用意味着表单尚未准备好，启用意味着它已准备好。

在此联系表单中，姓名字段不能为空，邮箱中必须包含 `@` 符号，消息必须至少 10 个字符:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件创建后不必保持固定状态。您可以在响应应用事件时随时更新文本、交换 CSS 类并切换启用状态。一个常见示例是在长时间运行的任务中提供反馈：

```java
Label statusLabel = new Label("准备好");
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

在任务运行时禁用按钮可以防止重复提交，而更新标签可以让用户了解发生了什么。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口允许您从组件本身之外观察组件生命周期事件。这在您需要对组件的创建或销毁做出反应而不修改其实现时非常有用。例如，您可以使用它维护活动组件的注册或在组件被移除时释放外部资源。

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

DESTROY 事件对于保持注册表自动同步特别有用。与其在不再需要时手动移除组件，不如让组件通知注册表自身：

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

一个管理一组相关组件的协调器类可以使用相同的方法来保持其内部列表的准确性：

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
- 实现日志记录或监控
- 协调多个组件
- 清理外部资源

要执行代码在组件附加到 DOM 后，请参阅 [`whenAttached()`](/docs/building-ui/composing-components) 中的复合组件指南。

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 携带任意的服务器端数据。这两个方法都接受一个键来识别数据。当您需要将域对象或上下文与组件关联而不管理单独的查找结构时，这非常有用。

```java
Button button = new Button("处理");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据永远不会发送给客户端，因此您可以安全地存储敏感信息或大型对象，而不会影响网络流量。
