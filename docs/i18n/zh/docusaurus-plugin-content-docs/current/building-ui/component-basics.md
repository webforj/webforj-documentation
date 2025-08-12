---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: e4d0cb9dd9f53dabda8bebe6664bf0d3
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是可以添加到窗口中的基本构建块，提供用户界面功能和自定义行为。在 webforJ 中，`Component` 类作为引擎内所有组件的基础。

## 生命周期管理 {#lifecycle-management}

理解组件生命周期对于有效创建、管理和利用组件至关重要。以下两个生命周期状态有方法来操控它们的行为。这些方法不应由用户显式调用。

### 创建和销毁钩子 {#create-and-destroy-hooks}

所有扩展 `Component` 类的类都负责任何在 `Component` 被创建和销毁时执行的功能。这是通过分别重写 `onCreate()` 和 `onDestroy()` 方法来实现的。

#### `onCreate()` {#oncreate}

在组件添加到窗口时调用 `onCreate()` 方法。创建组件涉及设置它们的初始状态和功能。这是您定义组件在首次创建时应做什么的地方。无论是初始化变量、设置事件监听器还是执行任何其他设置，`onCreate()` 方法都是您自定义组件行为的入口点。

此钩子接收一个窗口实例，允许在组件内添加包含的组件。

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()` 方法是应将组件和任何组成部分添加到窗口的地方。
:::

#### `onDestroy()` {#ondestroy}

销毁组件是管理资源和确保适当清理的重要部分。当组件不再需要或希望释放与之相关的资源时，有必要销毁组件。它允许开发人员执行清理任务，例如停止计时器、释放内存或分离事件监听器。它还允许在任何组成组件上调用 `destroy()` 方法。

:::tip
`onDestroy()` 方法负责调用任何组成组件上的 `destroy()` 方法。否则，这些组件将仍然存在于 DOM 中，但不会通过 API 可达。
:::

### 异步附件 {#asynchronous-attachment}

`whenAttached()` 方法允许在组件已添加到窗口后执行功能。此方法返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许在组件附加到 DOM 后异步执行额外指定的行为。

:::tip
与前面的三个方法不同，`whenAttached()` 是用户应显式调用的方法。
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* 显式调用 whenAttached()，当按钮附加到 Frame 时将显示消息框。*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("我已附加！", "异步附件");
    });
  
    // 调用 onCreate() 方法
    window.add(button); 
  }
}
```

### 观察者 {#observers}

观察者在跟踪组件生命周期事件中扮演着重要角色。可以使用 `addLifecycleObserver()` 和 `removeLifecycleObserver()` 方法添加和移除观察者，观察者会接收有关组件创建和销毁等事件的通知。

通过添加观察者，您可以在组件被创建或销毁时采取行动。这对于实现自定义逻辑或根据组件事件处理特定场景特别有用。

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // 实现按钮被销毁时要执行的逻辑
  }
});
```

## 组件属性 {#component-properties}

### 组件标识符 {#component-identifiers}

组件 ID 作为组件的唯一标识符，使您能够与它们交互并有效管理它们的状态。

#### 服务器端组件 ID {#server-side-component-id}

每个从 `Component` 类创建的组件都会自动分配一个服务器端标识符。服务器端 ID 对于框架内组件的内部跟踪和识别至关重要。您可以使用 `getComponentId()` 方法检索服务器端组件 ID。

在许多情况下，拥有唯一的服务器端标识符是必要的，例如在容器中查询特定组件时。

#### 客户端组件 ID {#client-side-component-id}

客户端 ID 允许用户获取在 Java 中创建的服务器组件的客户端表示。所有提供的 webforJ 组件都提供此 ID 的实现。如果要访问并使用客户端组件，可以执行 `object.get()` 以获取所需的客户端组件。

:::important
此 ID **不是** DOM 中元素的 ID 属性。
:::

在下面的示例中，向按钮添加了一个 `onClick` 事件，然后在通过 `object.get()` 方法获取客户端组件后，调用该方法触发该事件。

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("点击我");
  btn.onClick(e -> {
    showMessageDialog("按钮被点击", "发生了一个事件");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### 用户数据 {#user-data}

`Component` 类允许您使用 `setUserData()` 方法在组件中包含附加信息。这些信息仅在组件的服务器端通过 `getUserData()` 方法访问，且不发送到客户端。

当有信息需要与组件一起包含，并且该信息应在不往返客户端检索的情况下可访问时，这非常有用。
