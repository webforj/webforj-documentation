---
sidebar_position: 21
title: Debouncing
slug: debouncing
description: >-
  Delay actions until activity settles using the Debouncer class for
  search-as-you-type, autosave, and other rate-limited UI work.
_i18n_hash: fd81dccbd2aeb6e50922c2d09de536de
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

防抖是一种技术，它延迟执行操作，直到自上次调用以来经过了指定时间。每次新调用都会重置计时器。这在输入搜索时很有用，您希望在用户停止输入之前等待一段时间，然后再执行搜索查询。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

创建一个带有延迟（以秒为单位）的 `Debouncer`，然后调用 `run()` 并传递您想要防抖的操作：

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

在这个例子中，只有在用户停止输入 300 毫秒后，`search()` 方法才会被调用。每次键入都会通过 `onModify` 事件重置计时器，这样快速输入不会触发多个搜索。

## 工作原理 {#how-it-works}

当您以某个操作调用 `run()` 时：

1. 如果没有待处理的操作，`Debouncer` 将安排该操作在延迟后运行
2. 如果已经有待处理的操作，则取消之前的操作，并用新操作重置计时器
3. 一旦延迟结束而没有其他调用，该操作将执行

`Debouncer` 在 UI 线程上运行，利用 webforJ 的 [`Interval`](/docs/advanced/interval) 机制，因此您不需要将 UI 更新包装在 `Environment.runLater()` 中。

:::tip 延迟单位
延迟参数使用秒作为单位，而不是毫秒。使用 `0.3f` 表示 300 毫秒，或 `1.5f` 表示 1.5 秒。
:::

## 控制执行 {#controlling-execution}

以下方法可以更精确地处理 `Debouncer` 的执行和使用：

### 取消待处理操作 {#cancelling-a-pending-action}

使用 `cancel()` 取消待处理操作的执行：

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// 用户在保存执行前导航离开
debounce.cancel();
```

:::tip 取消待处理防抖
像间隔一样，销毁组件时取消待处理的防抖操作是一个好习惯。这可以防止内存泄漏，并避免在已销毁组件上执行操作时出现错误：

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

// 在表单提交之前强制验证
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### 检查待处理状态 {#checking-pending-status}

使用 `isPending()` 验证操作是否在等待执行：

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
| 范围 | 任何操作 | 仅限元素事件 |
| 位置 | 服务器端 | 客户端 |
| 单位 | 秒（浮点数） | 毫秒（整数） |
| 灵活性 | 拥有取消/强制执行的完全控制 | 带有事件的自动化 |

当您需要对防抖进行编程控制（如取消或强制执行待处理操作）时，使用 `Debouncer`。当您希望简单地对元素事件进行客户端防抖，而无需额外的服务器往返时，使用 `ElementEventOptions`。

```java
// 使用 ElementEventOptions 进行客户端防抖
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // 此处理程序在客户端上被防抖
}, options);
```
