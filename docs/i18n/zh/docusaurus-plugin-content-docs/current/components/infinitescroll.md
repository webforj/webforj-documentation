---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 8c7fc66f78d6508466b5fb9b5dfc3a68
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll` 组件在 webforJ 中自动加载更多内容，随着用户向下滚动，消除了分页的需要。这为列表、提要和数据密集的视图创造了流畅的体验，仅在需要时加载内容。

当用户到达可滚动内容的底部时，`InfiniteScroll` 触发一个事件以加载更多数据。在新内容加载时，它显示一个 [`Spinner`](../components/spinner) 及可自定义文本，指示更多项目正在路上。

<!-- INTRO_END -->

## 状态管理 {#state-management}

`InfiniteScroll` 组件发出事件并维护内部状态，以帮助管理内容加载的方式和时机。

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com.webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

要在用户滚动时获取更多数据，请使用 `onScroll()` 或 `addScrollListener()` 方法注册监听器。在监听器内部，您通常会加载附加内容并调用 `update()` 来刷新 `InfiniteScroll` 状态。

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Loaded item"));
  infiniteScroll.update();
});
```

一旦所有内容加载完成，标记滚动为已完成，以防止进一步触发。在设置完成后，请记得调用 `update()` 以应用新状态：

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
这将禁用进一步的无限滚动行为。

:::tip 重置加载标志
如果您稍后允许用户加载更多内容（例如，在刷新的时候），可以使用 `setCompleted(false)` 重置此标志。
:::


## 加载指示器自定义 {#loading-indicator-customization}

默认情况下，`InfiniteScroll` 显示内置加载指示器 - 一个小型动画 [`Spinner`](../components/spinner) 和“正在加载数据”文本。您可以通过将自定义消息传递给 `InfiniteScroll` 构造函数或使用 `setText()` 来更改显示的文本。

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
infiniteScroll.setText("Loading more items...");
```

类似地，您可以通过使用 `setIcon()` 自定义加载期间显示的 [`Icon`](../components/icon)。

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

### 完整自定义 {#full-customization}

如果您想完全用自己的标记替换 [`Spinner`](../components/spinner) 和文本，可以使用 `addToContent()` 直接将内容添加到特殊内容插槽中。

当您填充内容插槽时，它将完全替换默认的加载布局。

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## 样式 {#styling}

<TableBuilder name="InfiniteScroll" />
