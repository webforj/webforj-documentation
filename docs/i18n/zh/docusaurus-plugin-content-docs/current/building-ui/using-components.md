---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: cf47b1c83e67cb4c4998c149a7696701
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是 webforJ 应用程序的构建块。无论您是使用内置组件，例如 `Button` 和 `TextField`，还是使用您团队提供的自定义组件，与它们的交互方式遵循相同的一致模型：您配置属性、管理状态，并将组件组合成布局。

本指南重点介绍这些日常操作：不是组件如何工作的内部原理，而是如何在实践中完成工作。

## 组件属性 {#component-properties}

每个组件都暴露出控制其内容、外观和行为的属性。这些大多数都有专门的、类型化的 Java 方法（`setText()`、`setTheme()`、`setExpanse()` 等），这是您在 webforJ 中配置组件的主要方式。以下部分涵盖适用于各组件类型的属性和方法。

### 文本内容 {#text-content}

`setText()` 方法设置组件的可见文本，如 `Button` 上的标题或 `Label` 的内容。对于像 `TextField` 这样的输入组件，请使用 `setValue()` 来设置字段的当前值。

```java
Button button = new Button();
button.setText("点击我");

Label label = new Label();
label.setText("状态：准备就绪");

TextField field = new TextField();
field.setValue("初始值");
```

某些组件还支持 `setHtml()`，用于需要内容中内联 HTML 标记的情况：

```java
Div container = new Div();
container.setHtml("<strong>粗体文本</strong> 和 <em>斜体文本</em>");
```

### HTML 属性 {#html-attributes}

在 webforJ 中，大多数配置是通过类型化的 Java 方法而不是原始 HTML 属性完成的。然而，`setAttribute()` 对于传递没有专门 API 的可访问性属性是很有用的：

```java
Button button = new Button("提交");
button.setAttribute("aria-label", "提交表单");
button.setAttribute("aria-describedby", "form-hint");
```

:::note 检查组件支持
并不是所有组件都支持任意属性。这取决于底层组件的实现。
:::

### 组件 IDs {#component-ids}

您可以使用 `setAttribute()` 将 ID 分配给组件的 HTML 元素：

```java
Button submitButton = new Button("提交");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("电子邮件");
emailField.setAttribute("id", "email-input");
```

DOM ID 通常用于测试选择器和样式表中的 CSS 定位。

:::tip 优先使用类进行多组件定位
与 CSS 类不同，ID 在您的应用程序中应该是唯一的。如果您需要定位多个组件，请使用 `addClassName()`。
:::

:::info 框架管理的 ID
webforJ 还会在内部自动分配标识符给组件。服务器端 ID（通过 `getComponentId()` 访问）用于框架跟踪，而客户端 ID（通过 `getClientComponentId()` 访问）用于客户端与服务器的通信。这些与您使用 `setAttribute()` 设置的 DOM `id` 属性是分开的。
:::

### 样式 {#styling}

三个方法覆盖了大多数样式需求：`setStyle()` 用于单个 CSS 属性值，`addClassName()` 和 `removeClassName()` 用于应用或删除在样式表中定义的 CSS 类。
对于小的或一次性的样式调整，请使用 `setStyle()`，而使用 CSS 类可以应用更大或可重用的样式。

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
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) 是一种旧方法，通常不推荐在新项目中使用。在大多数情况下，请将样式保留在单独的 CSS 文件中。
:::

## 组件状态 {#component-state}

除了内容和外观，组件还有确定其是否可见以及是否响应用户交互的状态属性。最常用的两个是 `setVisible()` 和 `setEnabled()`。

`setVisible()` 控制组件是否在 UI 中呈现。`setEnabled()` 控制它在保持可见的同时是否接受输入或交互。在大多数情况下，禁用比隐藏更可取：禁用的按钮仍然传达存在某个操作，但目前不可用，这比出现和消失的状态更少令人困惑。

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

以下登录表单展示了 `setEnabled()` 的实际应用。只有当两个字段都有内容时，登录按钮才会启用，这清楚地向用户表明在继续之前需要输入：

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## 与容器一起工作 {#working-with-containers}

在 webforJ 中，布局由容器处理，它们是保存其他组件并控制其排列方式的组件。您不需要手动定位子组件；相反，您将它们添加到容器中并配置该容器的布局属性。

### 添加组件 {#adding-components}

所有容器都提供 `add()` 方法。您可以一次传递一个组件，或一次性传递所有组件：

```java
FlexLayout container = new FlexLayout();

container.add(new Button("点击我"));

TextField nameField = new TextField("姓名");
TextField emailField = new TextField("电子邮件");
Button submitButton = new Button("提交");

container.add(nameField, emailField, submitButton);
```

### 布局选项 {#layout-options}

`FlexLayout` 是 webforJ 的主要布局容器，涵盖大多数用例：行、列、对齐、间距和换行。对于更复杂的布局安排，如 CSS 网格或自定义定位，您可以通过在任何容器组件上应用 CSS 直接使用 `setStyle()` 或 `addClassName()`。有关完整布局选项，请参见 [FlexLayout](/docs/components/flex-layout) 文档。

### 显示和隐藏部分 {#showing-hiding-sections}

在容器中使用 `setVisible()` 的常见用法是仅在相关时显示额外的 UI。这保持了界面的专注并减少了视觉混乱。您可以在直接响应用户输入时显示当前布局中的一部分，而不是导航到新视图。

以下设置面板演示了这一点：基本通知首选项始终可见，而高级选项部分仅在用户请求时出现。保存按钮会在任何设置更改后立即启用：

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

这在您需要完全替换内容时很有用，例如将加载指示器置换为已加载的数据。

## 表单验证 {#form-validation}

协调多个组件以控制提交操作是 webforJ UI 中最常见的模式之一。核心思想很简单：每个输入字段注册一个监听器，每当任何值发生更改时，表单会重新评估所有条件是否满足，并相应地更新提交按钮。

这比用户点击提交后仅显示验证错误更可取，因为它提供持续反馈并防止不必要的提交。提交按钮作为指示器：禁用表示表单尚未准备好，启用则表示准备好了。

在这个联系表单中，姓名字段不能为空，电子邮件必须包含 `@` 符号，消息必须至少有 10 个字符：

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## 动态内容更新 {#dynamic-content-updates}

组件在创建后不必保持固定状态。您可以随时更新文本、交换 CSS 类以及切换启用状态以响应应用事件。一个常见的例子是在长时间运行的任务中提供反馈：

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

在任务运行时禁用按钮可防止重复提交，而更新标签可以让用户了解发生的事情。

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver` 接口允许您从组件本身之外观察组件生命周期事件。当您需要响应组件的创建或销毁而不修改其实现时，这非常有用。例如，您可以使用它来维护活动组件的注册表或在组件被移除时释放外部资源。

### 基本用法 {#basic-usage}

在任何组件上调用 `addLifecycleObserver()` 注册一个回调。回调接收组件和生命周期事件：

```java
Button button = new Button("观察我");

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

DESTROY 事件对于保持注册表自动同步特别有用。您可以让组件自己通知注册表，而不是在不再需要时手动移除组件：

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

管理一组相关组件的协调器类可以使用同样的方法来保持其内部列表的准确性：

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

有关在组件附加到 DOM 后执行代码的信息，请参阅 [`whenAttached()`](/docs/building-ui/composite-components) 中的复合组件指南。

## 用户数据 {#user-data}

组件可以通过 `setUserData()` 和 `getUserData()` 携带任意服务器端数据。这两个方法接受一个键来标识数据。当您需要将域对象或上下文与组件关联而不管理单独的查找结构时，这非常有用。

```java
Button button = new Button("处理");
button.setUserData("上下文", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("上下文");
    processTask(context.getUserId(), context.getTaskId());
});
```

由于用户数据从不发送到客户端，因此您可以安全地存储敏感信息或大型对象，而不影响网络流量。
