---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

组件是 webforJ 应用程序的构建块。无论您是使用内置组件，如 `Button` 和 `TextField`，还是使用您的团队提供的自定义组件，您与它们的交互方式遵循相同的一致模型：您配置属性、管理状态并将组件组合成布局。

本指南专注于那些日常操作：不是组件如何工作的内部细节，而是如何在实践中与它们完成任务。

## 组件属性 {#component-properties}

每个组件都公开属性来控制其内容、外观和行为。大多数属性都有专门的、类型安全的 Java 方法（`setText()`、`setTheme()`、`setExpanse()` 等），这是您在 webforJ 中配置组件的主要方式。下面的部分涵盖了适用于各种组件类型的属性和方法。

### 文本内容 {#text-content}

`setText()` 方法设置组件的可见文本，例如 `Button` 的标题或 `Label` 的内容。对于像 `TextField` 这样的输入组件，请使用 `setValue()` 来设置字段的当前值。

```java
Button button = new Button();
button.setText("点击我");

Label label = new Label();
label.setText("状态：准备好");

TextField field = new TextField();
field.setValue("初始值");
```

有些组件还支持 `setHtml()`，用于需要内容中包含内联 HTML 标记的情况：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

### HTML 属性 {#html-attributes}

在 webforJ 中，大多数配置是通过类型安全的 Java 方法而不是原始 HTML 属性完成的。然而，`setAttribute()` 对于传递没有专门 API 的可访问性属性是有用的：

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

TextField emailField = new TextField("邮箱");
emailField.setAttribute("id", "email-input");
```

DOM ID 通常用于测试选择器和您样式表中的 CSS 目标。

:::tip 优先使用类进行多组件定位
与 CSS 类不同，ID 在您的应用中应该是唯一的。如果需要定位多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还会在内部自动为组件分配标识符。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端和服务器之间的通信。这些与您通过 `setAttribute()` 设置的 DOM `id` 属性是分开的。
:::

### 样式 {#styling}

三个方法涵盖大多数样式需求：`setStyle()` 用于单个 CSS 属性值，`addClassName()` 和 `removeClassName()` 用于应用或删除在样式表中定义的 CSS 类。 
使用 `setStyle()` 进行小的或一次性的样式调整，使用 CSS 类应用更大或可重用的样式。

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

:::note 旧版方法
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) 是一种旧版方法，通常不建议新项目使用。在大多数情况下，请将样式保留在单独的 CSS 文件中。
:::

## 组件状态 {#component-state}

除了内容和外观，组件还有状态属性，决定它们是否可见以及是否响应用户交互。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中渲染。`setEnabled()` 控制组件是否在保持可见的同时接受输入或交互。在大多数情况下，禁用比隐藏更可取：禁用的按钮仍然传达出存在操作，但尚不可用，这比让其出现和消失更不让人困惑。

```java
// 当选中一个复选框时显示额外的字段
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

以下登录表单演示了 `setEnabled()` 的实际应用。签入按钮在两个字段都有内容之前保持禁用，从而清楚地表明用户输入是必须的：

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## 与容器一起工作 {#working-with-containers}

在 webforJ 中，布局由容器处理，容器是包含其他组件并控制其排列方式的组件。您不会手动定位子组件；而是将它们添加到容器中并配置该容器的布局属性。

### 添加组件 {#adding-components}

所有容器都提供一个 `add()` 方法。您可以一次传递一个组件，也可以一次性传递多个组件：

```java
FlexLayout container = new FlexLayout();

container.add(new Button("点击我"));

TextField nameField = new TextField("姓名");
TextField emailField = new TextField("邮箱");
Button submitButton = new Button("提交");

container.add(nameField, emailField, submitButton);
```

### 布局选项 {#layout-options}

`FlexLayout` 是 webforJ 中主要的布局容器，覆盖大多数用例：行、列、对齐、间距和换行。对于 CSS Grid 或自定义定位等更复杂的布局，您可以通过 `setStyle()` 或 `addClassName()` 在任何容器组件上直接应用 CSS。请参见 [FlexLayout](/docs/components/flex-layout) 文档以获取完整的布局选项。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中，`setVisible()` 的常见用法是仅在相关时显示额外的 UI。这使得界面保持集中并减少视觉混乱。您可以在用户输入的直接响应中显示当前布局的某个部分，而不是导航到新视图。

以下设置面板演示了这一点：基本通知偏好始终可见，而高级选项部分仅在用户请求时出现。保存按钮在任何设置更改后立即激活：

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
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

当您需要完全替换内容时，这很有用，例如将加载指示器与已加载数据交换。

## 表单验证 {#form-validation}

协调多个组件来控制提交操作是 webforJ UI 中最常见的模式之一。核心思想很简单：每个输入字段注册一个监听器，任何值更改时，表单重新评估是否满足所有条件，并相应更新提交按钮。

这比用户单击提交后仅显示验证错误更可取，因为它提供持续反馈并防止不必要的提交。提交按钮充当指示器：禁用意味着表单尚未准备好，启用意味着已准备好。

在此联系表单中，姓名字段不能为空，电子邮件必须包含 `@` 符号，并且消息必须至少 10 个字符：

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件在创建后不必保持固定状态。您可以随时更新文本、交换 CSS 类和切换启用状态，以响应应用事件。一个常见的例子是在长时间运行的任务中提供反馈：

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

在任务运行期间禁用按钮可以防止重复提交，同时更新标签可以让用户了解正在发生的事情。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口使您能够从组件内部观察组件生命周期事件。这在您需要在不修改其实现的情况下响应组件创建或销毁时非常有用。例如，您可以使用它来维护活动组件的注册表或在组件被移除时释放外部资源。

### 基本用法 {#basic-usage}

在任何组件上调用 `addLifecycleObserver()` 以注册回调。回调接收组件和生命周期事件：

```java
Button button = new Button("观看我");

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

### 模式：资源注册 {#pattern-resource-registry}

DESTROY 事件对于自动保持注册表同步特别有用。您可以让组件自己通知注册表，而不是手动移除不再需要的组件：

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

协调一组相关组件的协调器类可以使用相同的方法来准确保持其内部列表：

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
- 实现日志记录或监视
- 协调多个组件
- 清理外部资源

要在组件附加到 DOM 后执行代码，请参阅 Composite Components 指南中的 [`whenAttached()`](/docs/building-ui/composite-components)。

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 执行任意的服务器端数据传递。两个方法都接受一个键来标识数据。当您需要将域对象或上下文与组件关联时，这非常有用，而不需要管理一个单独的查找结构。

```java
Button button = new Button("处理");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据不会发送到客户端，您可以安全地存储敏感信息或大型对象，而不会影响网络流量。
