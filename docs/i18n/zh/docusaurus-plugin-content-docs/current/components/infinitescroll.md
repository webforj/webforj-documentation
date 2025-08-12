---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 3384cb35d5087561cc9be2c11b95c7e1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll` 组件在 webforJ 中会自动加载更多内容，用户向下滚动时，无需分页。这为列表、信息流和数据密集型视图提供了流畅的体验，仅在必要时加载内容。

当用户到达可滚动内容的底部时，`InfiniteScroll` 会触发一个事件以加载更多数据。在新内容加载时，它会显示一个 [复 spinning](../components/spinner)，并带有可自定义的文本以指示更多项目正在到来。

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## 状态管理 {#state-management}

`InfiniteScroll` 组件会发出事件并维护内部状态，以帮助管理内容加载的方式和时机。

为了在用户滚动时获取更多数据，请使用 `onScroll()` 或 `addScrollListener()` 方法注册一个监听器。在监听器内部，通常可以加载其他内容并调用 `update()` 来刷新 `InfiniteScroll` 状态。

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("加载的项目"));
    infiniteScroll.update();
});
```

一旦所有内容加载完成，标记滚动为完成，以防止进一步触发。在设置完成后，请记得调用 `update()` 来应用新状态：

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
这会禁用进一步的无限滚动行为。

:::tip 重置加载标志
如果您稍后允许用户加载更多内容（例如，在刷新后），可以使用 `setCompleted(false)` 来重置此标志。
:::


## 加载指示器自定义 {#loading-indicator-customization}

默认情况下，`InfiniteScroll` 显示一个内置加载指示器 - 一个小型动画 [复 spinning](../components/spinner) 和 “加载数据” 文本。您可以通过将自定义消息传递给 `InfiniteScroll` 构造函数或使用 `setText()` 来更改显示的文本。

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("正在获取更多记录...");
infiniteScroll.setText("正在加载更多项目...");
```

同样，您可以使用 `setIcon()` 自定义加载期间显示的 [图标](../components/icon)。

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### 完全自定义 {#full-customization}

如果您想完全替换 [复 spinning](../components/spinner) 和文本为您自己的标记，
可以使用 `addToContent()` 直接将内容添加到特殊内容插槽中。

当您填充内容插槽时，它将完全替代默认加载布局。

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## 样式 {#styling}

<TableBuilder name="InfiniteScroll" />
