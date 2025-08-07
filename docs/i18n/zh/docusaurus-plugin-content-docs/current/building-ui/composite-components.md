---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

开发人员通常希望创建包含组成组件的组件，以便在应用程序级别使用。`Composite` 组件为开发人员提供了他们需要的工具，以创建自己的组件，同时控制选择向用户展示的内容。

它允许开发人员管理特定类型的 `Component` 实例，提供一种封装其行为的方法。它要求任何扩展的子类指定其打算管理的 `Component` 类型，确保 `Composite` 的子类与其底层 `Component` 本质上是相互关联的。

:::tip
强烈建议通过利用 `Composite` 组件来创建自定义组件，而不是扩展基础 `Component` 组件。
:::

要使用 `Composite` 组件，首先创建一个新的 Java 类，该类扩展 `Composite` 组件。将您希望管理的组件类型指定为泛型类型参数。

```java
public class ApplicationComponent extends Composite<Div> {
	// 实现
}
```

## 组件绑定 {#component-binding}

`Composite` 类要求开发人员指定它所管理的 `Component` 类型。这种强关联确保了 `Composite` 组件本质上与其底层组件相连。这也提供了相对于传统继承的好处，因为它允许开发人员确切决定公开 API 的功能。

默认情况下，`Composite` 组件利用其子类的泛型类型参数来识别和实例化绑定的组件类型。这是基于组件类具有无参数构造函数的假设。开发人员可以通过重写 `initBoundComponent()` 方法来自定义组件初始化过程。这允许在创建和管理绑定组件时拥有更大的灵活性，包括调用带参数的构造函数。

以下代码片段重写了 initBoundComponent 方法，以使用 [`FlexLayout`](../components/flex-layout.md) 类的带参数构造函数：

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("提交");
		return new FlexLayout(nameField, submit);
	}
}
```

## 生命周期管理 {#lifecycle-management}

与 `Component` 不同，开发人员在使用 `Composite` 组件时无需实现 `onCreate()` 和 `onDestroy()` 方法。`Composite` 组件为您处理这些方面。

如果您需要在其生命周期的各个阶段访问绑定组件，`onDidCreate()` 和 `onDidDestroy()` 钩子允许开发人员访问这些生命周期阶段以执行额外功能。利用这些钩子是可选的。

`onDidCreate()` 方法在绑定组件创建并添加到窗口后立即调用。使用此方法设置您的组件，修改所需的任何配置，并添加子组件（如果适用）。而 `Component` 类的 `onCreate()` 方法采用一个 [Window](#) 实例，`onDidCreate()` 方法则采用绑定组件，从而无需直接调用 `getBoundComponent()` 方法。例如：

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// 将子组件添加到容器
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
此逻辑也可以在构造函数中实现，通过调用 `getBoundComponent()`。
:::

同样，`onDidDestroy()` 方法在绑定组件销毁后触发，并允许在销毁时执行额外行为（如果需要）。

### 示例 `Composite` 组件 {#example-composite-component}

在以下示例中，创建了一个简单的待办事项应用程序，其中添加到列表中的每个项目都是一个 `Composite` 组件，由一个样式为开关的 [`RadioButton`](../components/radio-button.md) 和一个带有文本的 [`Div`](#) 组成。

此组件的逻辑在构造函数中设置，该构造函数设置样式并使用 `getBoundComponent` 方法将组成组件添加到绑定组件，并添加事件逻辑。

:::tip
这也可以在 `onDidCreate()` 方法中实现，这样就可以直接访问绑定的 [`FlexLayout`](../components/flex-layout.md) 组件。
:::

然后实例化此组件并在应用程序中使用，使其可以在各种位置使用，成为创建自定义组件的强大工具。

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
