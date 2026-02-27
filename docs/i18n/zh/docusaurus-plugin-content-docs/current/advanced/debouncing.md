---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: 89cdcc39e4954963d7e19cb0e5665ca4
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

防抖是一种技术，它延迟执行某个操作，直到自上次调用以来经过的指定时间。每个新的调用都会重置计时器。这在搜索时很有用，例如在用户输入时您希望等待直到用户停止输入后再执行搜索查询。

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java'
height='265px'
/>

## 基本用法 {#basic-usage}

`Debouncer` 类提供了一种简单的方式来防抖操作。创建一个设置了延迟的 `Debouncer`，然后调用 `run()` 方法并传入您希望防抖的操作：

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

在这个例子中，`search()` 方法仅在用户停止输入 300 毫秒后被调用。每次按键都会通过 `onModify` 事件重置计时器，因此快速输入不会触发多个搜索。

## 工作原理 {#how-it-works}

当您使用某个操作调用 `run()` 时：

1. 如果没有待处理的操作，`Debouncer` 将该操作安排在延迟后运行
2. 如果已经有待处理的操作，则取消之前的操作，并用新操作重启计时器
3. 一旦延迟时间已过而没有新的调用，该操作将执行

`Debouncer` 在 UI 线程上运行，使用 webforJ 的 [`Interval`](/docs/advanced/interval) 机制，因此您无需将 UI 更新包装在 `Environment.runLater()` 中。

:::tip 延迟单位
延迟参数使用秒作为单位，而不是毫秒。使用 `0.3f` 表示 300 毫秒，或 `1.5f` 表示 1.5 秒。
:::

## 控制执行 {#controlling-execution}

以下方法可用于更精确地处理 `Debouncer` 的执行和使用：

### 取消待处理的操作 {#cancelling-a-pending-action}

使用 `cancel()` 来停止待处理操作的执行：

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// 用户在保存执行前导航离开
debounce.cancel();
```

:::tip 取消待处理的防抖
与间隔一样，销毁组件时取消待处理的防抖操作是个好习惯。这可以防止内存泄漏，并避免在已销毁组件上执行操作时出现错误：

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
:::

### 强制立即执行 {#forcing-immediate-execution}

使用 `flush()` 立即执行待处理操作：

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// 提交表单前强制验证
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### 检查待处理状态 {#checking-pending-status}

使用 `isPending()` 验证某个操作是否在等待执行：

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("正在处理...");
}
```

## 事件级防抖与 `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ 提供了两种防抖方法：

| 特性 | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------|-------------|-------------------------------------|
| 范围 | 任何操作   | 仅元素事件                             |
| 位置 | 服务器端   | 客户端                               |
| 单位 | 秒（浮动） | 毫秒（整数）                          |
| 灵活性 | 完全控制（取消/强制） | 自动与事件对接                     |

当您需要对防抖进行编程控制，例如取消或强制待处理操作时，使用 `Debouncer`。当您希望为元素事件提供简单的客户端防抖而无需额外的服务器往返时，使用 `ElementEventOptions`。

```java
// 使用 ElementEventOptions 进行客户端防抖
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // 此处理程序在客户端上防抖
}, options);
```
