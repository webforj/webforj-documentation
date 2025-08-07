---
title: Refresher
sidebar_position: 101
_i18n_hash: de00fad980f74bdd18544409408de0b8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

`Refresher` 组件在 webforJ 中启用可下拉刷新交互，适用于可滚动的容器——非常适合在移动或适合点击的界面中动态加载数据。当用户向下滑动超出可配置阈值时，刷新器会经历三个视觉状态：`pull`、`release` 和 `refreshing`。每个状态都提供可自定义的图标和本地化文本，以清晰地传达反馈。

您可以将 `Refresher` 与 [`InfiniteScroll`](../components/infinitescroll) 组件一起使用，以通过简单的手势输入重新加载内容或重置状态。该组件在交互行为、外观、本地化和与用户界面的其他部分集成方面可进行全面配置。

## 实例化和国际化 {#instantiation-and-internationalization}

通过实例化并注册刷新监听器来添加 `Refresher`。当刷新操作完成时，调用 `finish()` 将组件重置为闲置状态。

:::info 如何激活 `Refresher`
要激活 `Refresher`，**从可滚动区域的顶部点击并向下拖动**。虽然这一手势在移动设备上相对常见，但在桌面上较不常见——请确保用鼠标按住并拉动。
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

- Pull：初始手势文本（例如，“向下拉以刷新”）
- Release：触发阈值已达到（例如，“松开以刷新”）
- Refresh：加载状态（例如，“正在刷新”）

这允许根据需要提供多语言支持和品牌调整。

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 图标自定义 {#icon-customization}

您可以使用预定义的 [`Icon`](../components/icon) 或 [Icon URL](../managing-resources/assets-protocols) 来更改在 `pull`/`release` 和 `refreshing` 阶段使用的图标。这在您想要应用品牌或自定义动画时非常有用。

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 下拉行为配置 {#pull-behavior-configuration}

### 阈值 {#threshold}

设置用户必须向下拉动多远（以像素为单位），才能触发刷新：

```java
refresher.setThreshold(80); // 默认：80px
```

### 阈值最大值 {#threshold-maximum}

要定义允许的最大拉动距离，请使用 `setThresholdMax()` 方法：

```java
refresher.setThresholdMax(160);
```

这些阈值控制手势的敏感性和阻力曲线。

## 状态管理 {#state-management}

`Refresher` 组件维护其内部状态，并通过事件传递状态更改。当用户向下拉动超出定义的阈值时，`Refresher` 会发出刷新事件，您可以通过注册 `onRefresh()` 监听器来响应。

在此监听器内部，您需要执行所需的操作，例如获取新数据或重置列表，然后明确调用：

```java
refresher.finish();
```
:::warning 缺少 `finish()`
如果您忘记调用 `finish()`，刷新器将无限期保持加载状态。
:::

您还可以随时以编程方式禁用 `Refresher`，以防止用户触发刷新行为：

```java
refresher.setEnabled(false);
```

在刷新暂时不允许的时候，例如在加载屏幕上或另一个关键过程正在运行期间，这非常有用。

## 样式 {#styling}

### 主题 {#themes}

`Refresher` 组件支持多种主题，以在视觉上区分不同的状态或与应用程序的外观和感觉相匹配。可以使用 `setTheme()` 方法应用主题。

以下示例在您下拉刷新时，每次循环通过所有可用的主题，给您一个实时预览 `Refresher` 在不同主题下的外观：

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
