---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll` 组件在 webforJ 中可以自动加载更多内容，用户向下滚动时无需进行分页。这为列表、信息流和数据密集型视图提供了平滑的体验，仅在需要时加载内容。

当用户到达可滚动内容的底部时，`InfiniteScroll` 会触发事件以加载更多数据。在新内容加载期间，它会显示一个可定制文本的 [`Spinner`](../components/spinner)，以指示更多项目即将到来。

<!-- INTRO_END -->

## 状态管理 {#state-management}

`InfiniteScroll` 组件会发出事件并维护内部状态，以帮助管理内容何时以及如何加载。

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

要在用户滚动时获取更多数据，请使用 `onScroll()` 或 `addScrollListener()` 方法注册监听器。在监听器内部，通常会加载额外内容并调用 `update()` 来刷新 `InfiniteScroll` 的状态。

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("加载的项目"));
  infiniteScroll.update();
});
```

一旦所有内容加载完毕，将滚动标记为完成，以防止进一步触发。在设置为完成后，记得调用 `update()` 来应用新状态：

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
这将禁用进一步的无限滚动行为。

:::tip 重置加载标志
如果您以后允许用户加载更多内容（例如，刷新后），可以使用 `setCompleted(false)` 来重置此标志。
:::


## 加载指示器自定义 {#loading-indicator-customization}

默认情况下，`InfiniteScroll` 显示一个内置的加载指示器 - 一个小型动画 [`Spinner`](../components/spinner) 以及“加载数据”的文本。您可以通过将自定义消息传递给 `InfiniteScroll` 构造函数或使用 `setText()` 来更改显示的文本。

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("获取更多记录...");
infiniteScroll.setText("加载更多项目...");
```

同样，您可以使用 `setIcon()` 自定义加载期间显示的 [`Icon`](../components/icon)。

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### 完全自定义 {#full-customization}

如果您想完全用自己的标记替换 [`Spinner`](../components/spinner) 和文本，可以使用 `addToContent()` 将内容直接添加到特殊内容槽中。

当您填充内容槽时，它将完全替换默认的加载布局。

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## 样式 {#styling}

<TableBuilder name="InfiniteScroll" />
