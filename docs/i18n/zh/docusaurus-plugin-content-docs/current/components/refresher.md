---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

下拉刷新是移动设备和触控友好界面中的一种常见模式，`Refresher` 组件将其引入了 webforJ 中的可滚动容器。当用户向下滑动超过可配置的阈值时，它会通过视觉状态转换：`pull`、`release` 和 `refreshing`，每个状态都有可自定义的图标和本地化文本。它与 [`InfiniteScroll`](../components/infinitescroll) 搭配非常好，可以通过手势输入重新加载或重置内容。

<!-- INTRO_END -->

## 实例化和国际化 {#instantiation-and-internationalization}

通过实例化 `Refresher` 并注册一个刷新监听器来添加 `Refresher`。当刷新操作完成时，调用 `finish()` 将组件重置为空闲状态。

:::info 如何激活 `Refresher`
要激活 `Refresher`，**从可滚动区域的顶部点击并向下拖动**。虽然这种手势在移动设备上很常见，但在桌面上较少见——请确保用鼠标按住并向下拉。
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

每个状态标签也可以使用 `RefresherI18n` 对象进行本地化。三个状态是：

- Pull：初始手势文本（例如，“下拉以刷新”）
- Release：触发阈值已达到（例如，“释放以刷新”）
- Refresh：加载状态（例如，“正在刷新”）

这允许多语言支持和必要时的品牌调整。

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 图标自定义 {#icon-customization}

您可以使用预定义的 [`Icon`](../components/icon) 或 [Icon URL](../managing-resources/assets-protocols) 更改用于 `pull`/`release` 和 `refreshing` 阶段的图标。这在您想要应用品牌或者自定义动画时非常有用。

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## 下拉行为配置 {#pull-behavior-configuration}

### 阈值 {#threshold}

设置用户必须下拉的距离（单位：像素），才能触发刷新：

```java
refresher.setThreshold(80); // 默认：80px
```

### 最大阈值 {#threshold-maximum}

要定义允许的最大下拉距离，请使用 `setThresholdMax()` 方法：

```java
refresher.setThresholdMax(160);
```

这些阈值控制手势的灵敏度和阻力曲线。

## 状态管理 {#state-management}

`Refresher` 组件维护自己的内部状态，并通过事件传达状态更改。当用户向下拉到达定义的阈值时，`Refresher` 会发出刷新事件，您可以通过注册 `onRefresh()` 监听器来响应。

在此监听器中，您需要执行所需的操作，例如获取新数据或重置列表，然后显式调用：

```java
refresher.finish();
```
:::warning 缺少 `finish()`
如果您忘记调用 `finish()`，刷新组件将无限期处于加载状态。
:::

您还可以在任何时候以编程方式禁用 `Refresher`，以防止用户触发刷新行为：

```java
refresher.setEnabled(false);
```

这在刷新应该暂时被禁止时非常有用——例如，在加载屏幕上或在另一个关键过程运行时。

## 样式 {#styling}

### 主题 {#themes}

`Refresher` 组件支持多种主题，以在视觉上区分不同状态或与您应用的外观和感觉相匹配。可以使用 `setTheme()` 方法应用主题。

以下示例每次您下拉刷新时循环所有可用主题，使您实时预览 `Refresher` 在不同主题下的外观：

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
