---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 8106f15ba96904324822afd0169ec09b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在特定组件或区域上显示一个覆盖层，表明操作正在进行，并暂时阻止交互。它非常适合数据加载、计算或后台处理等任务。对于全局、整个应用程序的过程，[`BusyIndicator`](../components/busyindicator) 组件则覆盖整个界面。

<!-- INTRO_END -->

初始化 `Loading` 组件而不添加任何额外设置，会在其父内容上显示一个旋转器。当过程需要更多上下文时，可以传递一条消息，如下例所示。

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## 作用范围 {#scoping}

webforJ 中的 `Loading` 组件可以将其作用范围限定于特定的父容器，例如 `Div`，确保它仅在该元素内阻止用户交互。默认情况下，`Loading` 组件相对于其父级，这意味着它覆盖的是父组件而非整个应用程序。

要将 `Loading` 组件限制在其父级中，只需将 `Loading` 组件添加到父容器中。例如，如果您将其添加到 `Div` 中，加载覆盖层仅适用于该 `Div`：

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 仅会阻止 parentDiv 内的交互
```

## 幕布 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示一个幕布，以阻止用户在过程进行时的交互。默认情况下，组件启用幕布，但您可以根据需要将其关闭。

对于 `Loading` 组件，幕布默认是可见的。您可以使用 `setBackdropVisible()` 方法显式启用或关闭幕布：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用幕布
loading.open();
```
:::info 幕布关闭
即使在关闭幕布时，`Loading` 组件仍会阻止用户交互，以确保底层过程的中断完成。幕布只是控制视觉覆盖，而不是交互阻止行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `Loading` 组件包括一个 `Spinner`，可以直观地指示后台操作正在进行。您可以根据多种选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## 用例 {#use-cases}
- **数据获取**
   在从服务器或 API 检索数据时，`Loading` 组件会覆盖 UI 的特定部分，例如卡片或表单，以通知用户系统正在后台工作。此方案特别适合想要在屏幕的某一部分显示进度而不阻塞整个界面的情况。

- **卡片/区域中的内容加载**
   `Loading` 组件可以作用于页面的特定区域，例如单独的卡片或容器。这在您想要指示界面的特定区域仍在加载时非常有用，同时允许用户与页面的其他部分进行交互。

- **复杂表单提交**
   对于验证或处理需要时间的长期表单提交，`Loading` 组件为用户提供视觉反馈，确保他们的输入正在积极处理。

## 样式 {#styling}

<TableBuilder name="Loading" />
