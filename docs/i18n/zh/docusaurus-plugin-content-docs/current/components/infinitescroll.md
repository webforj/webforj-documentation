---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b6795a86cf03a60d9ef9e7d89749c9ab
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll` 组件在 webforJ 中自动加载更多内容，当用户向下滚动时，消除了分页的需求。这为列表、动态更新的内容以及数据密集的视图创造了平滑的体验，仅在需要时加载内容。

当用户到达可滚动内容的底部时，`InfiniteScroll` 会触发事件以加载更多数据。在新内容加载时，它会显示一个带有可自定义文本的 [`Spinner`](../components/spinner)，以指示更多项目正在加载中。

<!-- INTRO_END -->

## 状态管理 {#state-management}

`InfiniteScroll` 组件会发出事件并维护内部状态，以帮助管理内容的加载方式和时机。

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

要在用户滚动时获取更多数据，请使用 `onScroll()` 或 `addScrollListener()` 方法注册监听器。在监听器内部，您通常会加载附加内容并调用 `update()` 来刷新 `InfiniteScroll` 状态。

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Loaded item"));
    infiniteScroll.update();
});
```

一旦所有内容都加载完毕，标记滚动为已完成，以防止进一步触发。在设置为完成之后，请记得调用 `update()` 以应用新状态：

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
这将禁用进一步的无限滚动行为。

:::tip 重置加载标志
如果您稍后允许用户加载更多内容（例如，在刷新后），可以使用 `setCompleted(false)` 重置此标志。
:::


## 加载指示器自定义 {#loading-indicator-customization}

默认情况下，`InfiniteScroll` 显示一个内置的加载指示器——一个小型动画的 [`Spinner`](../components/spinner)，以及“正在加载数据”的文本。您可以通过将自定义消息传递给 `InfiniteScroll` 构造函数或使用 `setText()` 来更改显示的文本。

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
infiniteScroll.setText("Loading more items...");
```

同样，您可以通过使用 `setIcon()` 自定义加载期间显示的 [`Icon`](../components/icon)。

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### 完全自定义 {#full-customization}

如果您希望完全替换 [`Spinner`](../components/spinner) 和文本为您自己的标记，可以使用 `addToContent()` 直接向特殊内容插槽中添加内容。

当您填充内容插槽时，它将完全替换默认加载布局。

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## 样式 {#styling}

<TableBuilder name="InfiniteScroll" />
