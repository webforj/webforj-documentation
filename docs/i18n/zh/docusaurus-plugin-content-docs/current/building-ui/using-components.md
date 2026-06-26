---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

组件是 webforJ 应用程序的构建块。无论您是使用内置组件如 `Button` 和 `TextField`，还是使用团队提供的自定义组件，与它们的交互方式遵循相同的一致模型：您配置属性、管理状态，并将组件组合成布局。

本指南专注于这些日常操作：不是组件工作的内部细节，而是如何在实践中有效地使用它们。

## 组件属性 {#component-properties}

每个组件都暴露出控制其内容、外观和行为的属性。大多数属性都有专门的、类型化的 Java 方法（`setText()`、`setTheme()`、`setExpanse()` 等），这也是您在 webforJ 中配置组件的主要方式。以下部分涵盖适用于各种组件类型的属性和方法。

### 文本内容 {#text-content}

`setText()` 方法将组件的可见文本设置为字面字符，例如 `Button` 上的标题或 `Label` 的内容。对于像 `TextField` 这样的输入组件，使用 `setValue()` 来设置字段的当前值。

```java
Button button = new Button();
button.setText("点击我");

Label label = new Label();
label.setText("状态：准备好");

TextField field = new TextField();
field.setValue("初始值");
```

使用 `setText()` 编写的标记将作为这些字符显示，而不会被运行，这样可以避免来自用户输入或外部数据的文本被解析为实时标记。

```java
// 作为字面字符 "<b>状态：准备好</b>" 显示
component.setText("<b>状态：准备好</b>");
```

:::note 使用 `<html>` 标签
早期版本的 webforJ 将传递给 `setText()` 的用 `<html>` 包裹的值视为 HTML。此行为已被弃用，并将在 webforJ 27.00 中移除。

第一次出现的 `<html>` 包装值到达 `setText()` 时，会记录一个警告，命名组件和调用站点，以便可以移至 `setHtml()`。

要提前采用 webforJ 27.00 的默认行为，请将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用中，通过 `webforj.legacy-html-in-text` 设置相同的值。

```java
// webforj.legacyHtmlInText = true（默认）
component.setText("<html><b>状态：准备好</b></html>"); // 渲染为粗体

// webforj.legacyHtmlInText = false
component.setText("<html><b>状态：准备好</b></html>"); // 显示字符 <b>状态：准备好</b>
```
:::

### 渲染 HTML {#rendering-html}

某些组件还支持 `setHtml()`，用于需要在内容中呈现内联 HTML 标记的情况：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

:::danger 跨站脚本（XSS）
为了防止 [跨站脚本（XSS）攻击](/docs/security/application-security/common-threats#cross-site-scripting-xss)，仅使用 `setHtml()` 与您直接控制的内容。
:::

### HTML 属性 {#html-attributes}

在 webforJ 中，大多数配置是通过类型化 Java 方法而不是原始 HTML 属性完成的。但是，`setAttribute()` 对于传递没有专门 API 的可访问性属性非常有用：

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

DOM ID 通常用于测试选择器和样式表中的 CSS 目标。

:::tip 优先使用类进行多组件目标
与 CSS 类不同，ID 在您的应用中应该是唯一的。如果需要目标多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还在内部自动分配标识符给组件。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端与服务器之间的通信。这些与您通过 `setAttribute()` 设置的 DOM `id` 属性是分开的。
:::

### 样式 {#styling}

三个方法覆盖大多数样式需求：`setStyle()` 用于单个 CSS 属性值，而 `addClassName()` 和 `removeClassName()` 用于应用或移除在您的样式表中定义的 CSS 类。使用 `setStyle()` 进行小的或一次性的样式调整，使用 CSS 类应用更大或可重用的样式。

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

除了内容和外观，组件还有状态属性，以确定它们是否可见以及是否响应用户交互。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中渲染。`setEnabled()` 控制在可见的情况下是否接受输入或交互。在大多数情况下，禁用比隐藏更可取：一个禁用的按钮仍然传达一个操作存在但尚不可用的信息，这比让它出现和消失更不令人困惑。

```java
// 当复选框被选中时揭示额外的字段
TextField advancedField = new TextField("高级设置");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("显示高级设置");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// 只有在必填字段有值时才启用按钮
Button submitButton = new Button("提交");
submitButton.setEnabled(false);

TextField nameField = new TextField("姓名");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning 禁用和隐藏并不是安全的
`setVisible(false)` 和 `setEnabled(false)` 仅影响 UI。它们不会阻止决心强烈的用户通过浏览器或精心构造的请求调用底层操作，因此永远不要依赖它们来保护敏感操作。始终在服务器上强制执行访问控制。有关更多详细信息，请参见 [禁用和隐藏并不是安全的](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security)。
:::

以下登录表单演示了 `setEnabled()` 的实际应用。登录按钮在两个字段都有内容之前保持禁用，这使用户明确输入是继续的必要前提：

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## 使用容器 {#working-with-containers}

在 webforJ 中，布局由容器处理，容器是包含其他组件并控制其排列方式的组件。您不手动定位子组件；相反，向容器添加它们并配置该容器的布局属性。

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

`FlexLayout` 是 webforJ 中的主要布局容器，涵盖了大多数用例：行、列、对齐、间距和换行。对于更复杂的排列，如 CSS Grid 或自定义定位，您可以通过 `setStyle()` 或 `addClassName()` 直接应用 CSS。请参见 [FlexLayout](/docs/components/flex-layout) 文档，了解完整的布局选项。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中使用 `setVisible()` 的一个常见用途是仅在相关时揭示附加 UI。这可以保持界面专注并减少视觉杂乱。您可以直接响应用户输入显示当前布局中的某个部分，而不是导航到新视图。

以下设置面板演示了这一点：基本通知首选项始终可见，而仅当用户要求时，高级选项部分才会出现。只要任何设置发生更改，保存按钮就会激活：

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### 容器管理 {#container-management}

使用 `remove()` 和 `removeAll()` 在运行时将组件移出容器：

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("临时");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

当您需要完全替换内容时，如将加载指示器换成加载的数据时，这非常有用。

## 表单验证 {#form-validation}

协调多个组件以控制提交操作是 webforJ UI 中的常见模式。基本思想是每个输入字段注册一个监听器，每当值更改时，表单重新评估是否满足所有标准，并相应地更新提交按钮。

以下示例手动连接，这样您可以看到组件状态和事件监听器如何协同工作。这不是实际表单的推荐方法：手动监听器逻辑随着表单的增长而变得难以维护，且不将组件与底层数据模型连接起来。

:::tip 使用数据绑定进行表单验证
对于生产表单，请使用 [数据绑定](/docs/data-binding/overview)。它涵盖验证、组件与模型之间的双向同步，以及通过 `BindingContext` 进行值转换。此处显示的手动模式仅用于说明。
:::

在此联系表单中，姓名字段不能为空，电子邮件必须包含 `@` 符号，消息必须至少 10 个字符长：

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件在创建后不必保持固定状态。您可以在任何时候更新文本、交换 CSS 类以及切换启用状态以响应应用事件。一个常见示例是在长时间运行的任务期间提供反馈：

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

在任务运行时禁用按钮可以防止重复提交，而更新标签则使用户了解正在发生的事情。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口允许您从组件外部观察组件生命周期事件。当您需要响应组件创建或销毁的事件而不修改其实现时，这非常有用。例如，您可能使用它维护活动组件的注册表或在组件被移除时释放外部资源。

### 基本用法 {#basic-usage}

在任何组件上调用 `addLifecycleObserver()` 以注册回调。回调接收组件和生命周期事件：

```java
Button button = new Button("看着我");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("按钮被创建");
            break;
        case DESTROY:
            System.out.println("按钮被销毁");
            break;
    }
});
```

### 模式：资源注册表 {#pattern-resource-registry}

DESTROY 事件对于保持注册表的自动同步特别有用。您可以让组件通知注册表本身，而不是手动删除不再需要的组件：

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

管理一组相关组件的协调器类可以使用相同的方法来保持其内部列表的准确性：

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

有关在组件附加到 DOM 后执行代码的信息，请参见 [组合组件](/docs/building-ui/composing-components) 指南中的 `whenAttached()`。 

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 载带任意服务器端数据。这两种方法都接受一个用于识别数据的键。当您需要将域对象或上下文与组件关联，而无需管理单独的查找结构时，这种方式非常有用。

```java
Button button = new Button("处理");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据从不发送到客户端，您可以安全地存储敏感信息或大型对象，而不会影响网络流量。
