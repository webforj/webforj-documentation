---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

组件是可以添加到窗口的基本构建块，提供用户界面功能和自定义行为。在webforJ中，`Component`类是引擎中所有组件的基础。

## 生命周期管理 {#lifecycle-management}

理解组件生命周期对于有效创建、管理和利用组件至关重要。以下两个生命周期状态具有操作其行为的方法。这些方法不应由用户显式调用。

### 创建和销毁钩子 {#create-and-destroy-hooks}

所有扩展`Component`类的类负责实现组件被创建和销毁时要执行的功能。这是通过分别重写`onCreate()`和`onDestroy()`方法来完成的。

#### `onCreate()` {#oncreate}

当组件被添加到窗口时，会调用`onCreate()`方法。创建组件包括设置其初始状态和功能。在此处定义组件在首次创建时应执行的操作。无论是初始化变量、设置事件监听器，还是执行其他任何设置，`onCreate()`方法都是自定义组件行为的入口点。

此钩子接收一个窗口实例，允许将组件添加到该组件中。

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()`方法是将组件及其任何组成部分添加到窗口的地方。
:::

#### `onDestroy()` {#ondestroy}

销毁组件是管理资源和确保适当清理的重要部分。当组件不再需要时或希望释放与其关联的资源时，就需要销毁组件。它允许开发者执行清理任务，如停止定时器、释放内存或卸载事件监听器。它还允许对任何组成组件调用`destroy()`方法。

:::tip
`onDestroy()`方法负责对任何组成组件调用`destroy()`方法。否则，这些组件将仍然存在于DOM中，但无法通过API访问。
:::

### 异步附加 {#asynchronous-attachment}

`whenAttached()`方法允许在组件被添加到窗口后执行功能。此方法返回一个<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，它允许在组件在DOM中附加后异步执行额外的指定行为。

:::tip
与前面三个方法不同，`whenAttached()`是用户显式调用的。
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* 显式调用whenAttached()，当按钮附加到Frame时将显示消息框。*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("我已附加！", "异步附加");
    });
  
    // 调用onCreate()方法
    window.add(button); 
  }
}
```

### 观察者 {#observers}

观察者在跟踪组件生命周期事件中扮演着重要角色。可以使用`addLifecycleObserver()`和`removeLifecycleObserver()`方法添加和移除观察者，并接收关于组件创建和销毁等事件的通知。

通过添加观察者，您可以在组件创建或销毁时采取行动。这对于实现自定义逻辑或基于组件事件处理特定场景特别有用。

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // 当按钮被销毁时执行的逻辑
  }
});
```

## 组件属性 {#component-properties}

### 组件标识符 {#component-identifiers}

组件ID作为组件的唯一标识符，使您能够与其交互并有效管理其状态。

#### 服务器端组件ID {#server-side-component-id}

从`Component`类创建的每个组件都会自动分配一个服务器端标识符。服务器端ID对于框架内组件的内部跟踪和识别至关重要。您可以使用`getComponentId()`方法检索服务器端组件ID。

在许多情况下，拥有唯一的服务器端标识符是必要的，例如在容器中查询特定组件时。

#### 客户端组件ID {#client-side-component-id}

客户端ID允许用户获取用Java创建的服务器组件的客户端表示。所有提供的webforJ组件都有此ID的实现。如果您想获得并使用客户端组件，可以执行`object.get()`与客户端ID结合，以获取所需的客户端组件。

:::重要
此ID**不是**DOM中元素的ID属性。
:::

在下面的示例中，一旦通过调用客户端组件的`object.get()`方法获得后，将为按钮添加一个`onClick`事件，然后触发它。

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("点击我");
  btn.onClick(e -> {
    showMessageDialog("按钮被点击", "事件发生了");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### 用户数据 {#user-data}

`Component`类允许您使用`setUserData()`方法在组件中包含附加信息。该信息仅在组件的服务器端通过`getUserData()`方法访问，并不会发送到客户端。

当有信息应与组件一起包含时，这非常有用，并且该信息应在不向客户端检索的情况下可访问。
