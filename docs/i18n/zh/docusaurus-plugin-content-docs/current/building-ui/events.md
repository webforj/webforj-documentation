---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: c5c07ac4ca0f8d88ea6ef86afd5bb408
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

组件，无论是自定义的还是框架的一部分，支持事件处理。您可以添加事件监听器以捕获各种类型的事件，例如用户交互、状态变化或其他自定义事件。这些事件监听器可用于在事件发生时触发特定的动作或行为。

在下面的示例中，使用支持的三种方法之一：lambda 表达式、匿名类和方法引用，来添加事件。
## 添加事件 {#adding-events}

添加事件监听器可以使用以下模式之一，其中：

- **`myComponent`** 是您希望附加事件监听器的组件。

- **`addEventListener`** 被事件特定的方法替换。

- **`EventListener`** 被正在监听的事件类型替换。

```java
myComponent.addEventListener(e -> {
  //当事件触发时执行
});

//或者

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //当事件触发时执行
  }
});

//或者

myComponent.addEventListener(this::eventMethod);
```

额外的语法糖方法或别名已被添加，以允许使用 `on` 前缀后跟事件的替代添加事件，例如：

```java
myComponent.onEvent(e -> {
  //当事件触发时执行
});
```

## 移除事件 {#removing-an-event}

添加事件监听器时，会返回一个 `ListenerRegistration` 对象。这可以用于在稍后移除事件等其他用途。

```java
//添加事件
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //当事件触发时执行
  });

//移除事件
listenerRegistration.remove();
```

## 使用事件负载 {#using-event-payload}

重要的是要注意，事件通常带有负载，其中包含与事件相关的额外信息。您可以在事件处理程序中有效利用此负载，以访问相关数据，而无需在客户端和服务器之间进行不必要的往返。这可以提高应用程序的性能。

以下代码片段查询组件以获取信息，出于演示目的，这些信息已包含在事件负载中，表示效率低下的代码：

```java
myComponent.addEventListener(e -> {
  // 从组件访问数据
  String componentText = e.getComponent().getText();

  //或者如果组件在函数的作用域内可访问
  String componentText = myComponent.getText();

  // 使用 componentText 执行其他操作。
});
```

相反，利用方法的负载，为了说明例子，包括组件的文本，避免了往返：

```java
myComponent.addEventListener(e -> {
  // 从事件负载访问数据
  String componentText = e.getText();
  
  // 使用 componentText 执行其他操作。
});
```

这种方法最小化了查询组件以获取信息的需要，因为数据已经可以在事件负载中获得。通过遵循这种高效的事件处理实践，您可以增强组件的性能和响应能力。如需更多信息，您可以参考 [客户端/服务器交互](../architecture/client-server)。

### 示例 {#sample}

下面的演示显示了如何将 <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> 添加到一个 [`Button`](#) 上。这个 [`Button`](#) 还使用事件负载中的信息在屏幕上显示信息。

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
