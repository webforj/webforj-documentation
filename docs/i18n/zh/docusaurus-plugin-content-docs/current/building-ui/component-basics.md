---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是可以添加到窗口中的基本构件，提供用户界面功能和自定义行为。在 webforJ 中，`Component` 类作为引擎中所有组件的基础。

## 生命周期管理 {#lifecycle-management}

理解组件生命周期对于有效地创建、管理和使用组件至关重要。以下两个生命周期状态具有操作其行为的方法。这些方法不应由用户显式调用。

### 创建和销毁钩子 {#create-and-destroy-hooks}

所有扩展 `Component` 类的类负责实现当 `Component` 被创建和被销毁时执行的功能。这是通过分别重写 `onCreate()` 和 `onDestroy()` 方法来实现的。

#### `onCreate()` {#oncreate}

`onCreate()` 方法在组件被添加到窗口时被调用。创建组件涉及设置其初始状态和功能。在这里，你定义组件在首次创建时应执行的操作。无论是初始化变量、设置事件监听器还是执行任何其他设置，`onCreate()` 方法都是自定义组件行为的入口点。

这个钩子接收一个窗口实例，允许添加组件中包含的组件。

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()` 方法是将组件及其任何组成部分添加到窗口的地方。
:::

#### `onDestroy()` {#ondestroy}

销毁组件是管理资源和确保适当清理的关键部分。当组件不再需要或希望释放与之相关的资源时，就需要销毁组件。它允许开发者执行清理任务，如停止计时器、释放内存或分离事件监听器。它还允许对任何组成组件调用 `destroy()` 方法。

:::tip
`onDestroy()` 方法负责对任何组成组件调用 `destroy()` 方法。否则，这些组件仍将存在于 DOM 中，但无法通过 API 访问。
:::

### 异步附加 {#asynchronous-attachment}

`whenAttached()` 方法允许在组件添加到窗口后执行功能。该方法返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许在组件在 DOM 中附加后以异步方式执行额外指定的行为。

:::tip
与之前的三个方法不同，`whenAttached()` 是用户应显式调用的方法。
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* 明确调用 whenAttached()，当按钮附加到 Frame 时将显示消息框。*/
    button.whenAttached().thenAccept(e -> {
      showMessageDialog("我已附加！", "异步附加");
    });
  
    // 调用 onCreate() 方法
    window.add(button); 
  }
}
```

### 观察者 {#observers}

观察者在跟踪组件生命周期事件中起着重要作用。可以使用 `addLifecycleObserver()` 和 `removeLifecycleObserver()` 方法添加和移除观察者，并接收关于组件创建和销毁等事件的通知。

通过添加观察者，你可以在创建或销毁组件时采取行动。这对于实施自定义逻辑或处理基于组件事件的特定场景尤其有用。

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

组件 ID 作为组件的唯一标识符，允许你有效地与它们交互并管理其状态。

#### 服务器端组件 ID {#server-side-component-id}

从 `Component` 类创建的每个组件都会自动分配一个服务器端标识符。服务器端 ID 对于在框架内部跟踪和识别组件至关重要。你可以使用 `getComponentId()` 方法检索服务器端组件 ID。

在许多情况下，拥有唯一的服务器端标识符是必要的，例如在容器中查询特定组件时。

#### 客户端组件 ID {#client-side-component-id}

客户端 ID 允许用户获取用 Java 创建的服务器组件的客户端表示。所有提供的 webforJ 组件都实现了此 ID。如果你想访问并使用客户端组件，可以执行 `object.get()` 并使用客户端 ID 获取所需的客户端组件。

:::important
此 ID **不是** DOM 中元素的 ID 属性。
:::

在下面的示例中，向按钮添加了 `onClick` 事件，然后通过调用方法在客户端组件上触发事件，该方法在使用 `object.get()` 方法获取后调用。

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("点击我");
  btn.onClick(e -> {
    showMessageDialog("按钮被点击了", "发生了一个事件");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### 用户数据 {#user-data}

`Component` 类允许你通过 `setUserData()` 方法在组件中包含附加信息。此信息仅在组件的服务器端可通过 `getUserData()` 方法访问，且不会发送到客户端。

当有信息需要与组件一起包含，并且希望在不请求客户端的情况下访问这些信息时，这非常有用。
