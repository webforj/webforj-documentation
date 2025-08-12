---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

`Refresher` 组件在 webforJ 中启用在可滚动容器内进行下拉刷新交互，非常适合在移动或触控友好的界面中动态加载数据。当用户向下滑动超过可配置的阈值时，刷新组件通过视觉状态进行过渡：`pull`、`release` 和 `refreshing`。每个状态呈现一个可自定义的图标和本地化的文本，以清晰地传达反馈。

您可以将 `Refresher` 与像 [`InfiniteScroll`](../components/infinitescroll) 的组件一起使用，以通过简单的手势输入重新加载内容或重置状态。该组件在交互行为、外观、本地化以及与您其余 UI 的集成方面完全可配置。

## 实例化和国际化 {#instantiation-and-internationalization}

通过实例化并注册一个刷新监听器来添加 `Refresher`。当刷新操作完成时，调用 `finish()` 将组件重置为其空闲状态。

:::info 如何激活 `Refresher`
要激活 `Refresher`，**从可滚动区域的顶部点击并向下拖动**。虽然这种手势在移动设备上很常见，但在桌面上不太常见——请确保用鼠标保持并拉动。
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

这种方法通常用于刷新分页列表或重新启动无限滚动加载。

### 国际化 {#internationalization}

每个状态标签也可以使用 `RefresherI18n` 对象进行本地化。三个状态为：

- Pull：初始手势文本（例如，“下拉以刷新”）
- Release：触发阈值已达到（例如，“松开以刷新”）
- Refresh：加载状态（例如，“正在刷新”）

这允许根据需要进行多语言支持和品牌调整。

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 图标自定义 {#icon-customization}

您可以使用预定义的 [`Icons`](../components/icon) 或图标 URL ([Icon URL](../managing-resources/assets-protocols)) 更改用于 `pull`/`release` 和 `refreshing` 阶段的图标。这在您希望应用品牌或自定义动画时非常有用。

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 下拉行为配置 {#pull-behavior-configuration}

### 阈值 {#threshold}

设置用户必须向下拉动的距离（以像素为单位），以触发刷新：

```java
refresher.setThreshold(80); // 默认：80px
```

### 阈值最大值 {#threshold-maximum}

要定义允许的最大拉动距离，请使用 `setThresholdMax()` 方法：

```java
refresher.setThresholdMax(160);
```

这些阈值控制手势的灵敏度和阻力曲线。

## 状态管理 {#state-management}

`Refresher` 组件维护自己的内部状态，并通过事件传达状态变化。当用户向下拉动超过定义的阈值时，`Refresher` 会发出一个刷新事件，您可以通过注册 `onRefresh()` 监听器进行响应。

在此监听器中，您需要执行所需的操作，诸如提取新数据或重置列表，然后明确调用：

```java
refresher.finish();
```
:::warning 缺少 `finish()`
如果您忘记调用 `finish()`，刷新器将无限期停留在加载状态。
:::

您还可以在任何时候以编程方式禁用 `Refresher` ，以防止用户触发刷新行为：

```java
refresher.setEnabled(false);
```

当应该暂时不允许刷新时，这会很有用——例如，在加载屏幕期间或在另一个关键进程运行时。

## 样式设置 {#styling}

### 主题 {#themes}

`Refresher` 组件支持多种主题，以在视觉上区分不同状态或与您的应用程序的外观和感觉相匹配。主题可以通过 `setTheme()` 方法应用。

以下示例在每次下拉刷新时循环遍历所有可用主题，为您提供 `Refresher` 在不同主题下的实时预览：

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
