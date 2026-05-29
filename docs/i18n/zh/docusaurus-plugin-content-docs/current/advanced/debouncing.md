---
sidebar_position: 21
title: Debouncing
slug: debouncing
_i18n_hash: 2096c774627674739fd237aed9a4f79e
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

防抖是一种技术，它延迟执行操作，直到自上一次调用以来经过了指定的时间。每个新调用都会重置计时器。这在像“搜索时输入”的场景中非常有用，在这种情况下，您希望等到用户停止输入后再执行搜索查询。

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

## 基本用法 {#basic-usage}

`Debouncer` 类提供了一种简单的方法来防抖操作。创建一个带有延迟（以秒为单位）的 `Debouncer`，然后调用 `run()` 传递您想要防抖的操作：

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

在这个例子中，只有在用户停止输入300毫秒后，`search()` 方法才会被调用。每一次按键都会通过 `onModify` 事件重置计时器，因此快速输入不会触发多次搜索。

## 它是如何工作的 {#how-it-works}

当您使用一个操作调用 `run()` 时：

1. 如果没有待处理的操作，`Debouncer` 会安排在延迟后运行该操作
2. 如果已经有一个操作在等待，则取消上一个操作，并使用新操作重新启动计时器
3. 一旦延迟结束而没有其他调用，操作将执行

`Debouncer` 使用 webforJ 的 [`Interval`](/docs/advanced/interval) 机制在 UI 线程上运行，因此您不需要在 `Environment.runLater()` 中包装 UI 更新。

:::tip 延迟单位
延迟参数使用秒作为单位，而不是毫秒。使用 `0.3f` 表示300毫秒，或使用 `1.5f` 表示1.5秒。
:::

## 控制执行 {#controlling-execution}

以下方法可以更精确地处理 `Debouncer` 的执行和使用：

### 取消待处理的操作 {#cancelling-a-pending-action}

使用 `cancel()` 来停止待处理的操作执行：

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// 用户在保存执行之前导航离开
debounce.cancel();
```

:::tip 取消待处理的防抖操作
与间隔一样，在组件被销毁时取消待处理的防抖操作是个好习惯。这可以防止内存泄漏，并避免操作在已销毁组件上执行时出现错误：

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

使用 `flush()` 来立即执行待处理的操作：

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

使用 `isPending()` 来验证操作是否在等待执行：

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("正在处理...");
}
```

## 事件级防抖与 `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ 提供了两种防抖的方法：

| 特性 | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------|-------------|-------------------------------------|
| 范围 | 任何操作 | 仅限元素事件 |
| 位置 | 服务器端 | 客户端 |
| 单位 | 秒（浮点数） | 毫秒（整数） |
| 灵活性 | 使用取消/强制任意控制 | 使用事件自动防抖 |

当需要对防抖进行程序化控制时，请使用 `Debouncer`，例如取消或强制执行待处理操作。当您想要简单的客户端防抖处理元素事件而不需要额外的服务器往返时，请使用 `ElementEventOptions`。

```java
// 使用 ElementEventOptions 进行客户端防抖
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // 此处理程序在客户端进行了防抖
}, options);
```
