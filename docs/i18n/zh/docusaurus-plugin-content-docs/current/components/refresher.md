---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

下拉刷新是移动设备和适合触摸的界面中的一种常见模式，而 `Refresher` 组件将其带入 webforJ 中的可滚动容器。当用户向下滑动超过可配置的阈值时，它会在视觉状态中进行过渡：`pull`、`release` 和 `refreshing`，每个状态都有可自定义的图标和本地化文本。它与 [`InfiniteScroll`](../components/infinitescroll) 非常搭配，可通过手势输入来重新加载或重置内容。

<!-- INTRO_END -->

## 实例化和国际化 {#instantiation-and-internationalization}

通过实例化 `Refresher` 并注册一个刷新监听器来添加 `Refresher`。当刷新操作完成时，调用 `finish()` 将组件重置为其空闲状态。

:::info 如何激活 `Refresher`
要激活 `Refresher`，**从可滚动区域的顶部点击并向下拖动**。虽然这种手势在移动设备上很常见，但在桌面上则不太常见——确保用鼠标按住并拉动。
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

这种方法通常用于刷新分页列表或重新启动无限滚动加载。

### 国际化 {#internationalization}

每个状态标签也可以使用 `RefresherI18n` 对象进行本地化。这三个状态为：

- Pull: 初始手势文本（例如，“下拉以刷新”）
- Release: 达到触发阈值（例如，“释放以刷新”）
- Refresh: 加载状态（例如，“正在刷新”）

这允许根据需要提供多语言支持和品牌调整。

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## 图标自定义 {#icon-customization}

您可以使用预定义的 [`Icon`](../components/icon) 或 [图标 URL](../managing-resources/assets-protocols) 来更改用于 `pull`/`release` 和 `refreshing` 阶段的图标。这些在您想要应用品牌或自定义动画时非常有用。

<ComponentDemo
path='/webforj/refresfericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## 下拉行为配置 {#pull-behavior-configuration}

### 阈值 {#threshold}

设置用户必须下拉的距离（以像素为单位），才能触发刷新：

```java
refresher.setThreshold(80); // 默认: 80px
```

### 阈值最大值 {#threshold-maximum}

要定义允许的最大下拉距离，请使用 `setThresholdMax()` 方法：

```java
refresher.setThresholdMax(160);
```

这些阈值控制手势灵敏度和阻力曲线。

## 状态管理 {#state-management}

`Refresher` 组件维护其自身的内部状态，并通过事件传达状态变化。当用户下拉超过定义的阈值时，`Refresher` 会发出刷新事件，您可以通过注册 `onRefresh()` 监听器来响应。

在此监听器中，您需要执行所需的操作——如获取新数据或重置列表——然后显式调用：

```java
refresher.finish();
```
:::warning 缺少 `finish()`
如果您忘记调用 `finish()`，则刷新器将无限期保持在加载状态。
:::

您还可以在任何时候以编程方式禁用 `Refresher`，以防止用户触发刷新行为：

```java
refresher.setEnabled(false);
```

这在应该临时禁止刷新时非常有用——例如，在加载屏幕期间或在执行其他关键过程时。

## 样式 {#styling}

### 主题 {#themes}

`Refresher` 组件支持多种主题，以在视觉上区分不同的状态或与您应用的外观和感觉相匹配。可以使用 `setTheme()` 方法应用主题。

以下示例在每次下拉刷新时循环遍历所有可用主题，让您实时预览 `Refresher` 在不同主题下的外观：

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
