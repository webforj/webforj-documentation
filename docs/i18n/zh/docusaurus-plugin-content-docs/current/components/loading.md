---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 6e4493f64eb29033ed8a5d594accdb33
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在特定组件或区域上显示一个覆盖层，表明操作正在进行中，并暂时阻止交互。它非常适用于数据加载、计算或后台处理等任务。对于全局应用程序范围内的流程，[`BusyIndicator`](../components/busyindicator) 组件则覆盖整个界面。

<!-- INTRO_END -->

在没有任何额外设置的情况下初始化 `Loading` 组件时，会在其父内容上显示一个旋转器。当过程需要更多上下文时，可以传递一条消息，如下例所示。

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## 范围 {#scoping}

webforJ 中的 `Loading` 组件可以将自己限定在特定的父容器内，例如 `Div`，确保它只在该元素内阻止用户交互。默认情况下，`Loading` 组件是相对于其父组件的，这意味着它覆盖的是父组件而不是整个应用程序。

要将 `Loading` 组件限制在其父组件中，只需将 `Loading` 组件添加到父容器。例如，如果您将它添加到 `Div`，加载覆盖仅适用于该 `Div`：

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // 仅在 parentDiv 内阻止交互
```

## 背景 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示一个背景，以阻止用户在过程中与之交互。默认情况下，该组件启用背景，但您可以根据需要将其关闭。

对于 `Loading` 组件，背景默认是可见的。您可以使用 `setBackdropVisible()` 方法显式启用或关闭它：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景
loading.open();
```
:::info 背景关闭
即使关闭了背景，`Loading` 组件仍然会继续阻止用户交互，以确保底层过程顺利完成。背景只是控制视觉覆盖，而不是阻止交互行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `Loading` 组件包括一个 `Spinner`，用于直观地表示后台操作正在进行。您可以使用多个选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
]}
height='300px'
/>

## 用例 {#use-cases}
- **数据提取**
   当从服务器或 API 获取数据时，`Loading` 组件会覆盖 UI 的特定部分，例如卡片或表单，以告知用户系统正在后台工作。当您希望只在屏幕的一部分上显示进度而不阻止整个界面时，这很理想。

- **卡片/区域内容加载**
   `Loading` 组件可以限定于页面的特定区域，例如单个卡片或容器。这在您想指示 UI 的特定部分仍在加载同时允许用户与页面的其他部分交互时非常有用。

- **复杂表单提交**
   对于需要时间进行验证或处理的较长表单提交，`Loading` 组件为用户提供视觉反馈，确保他们的输入正在积极处理。

## 样式 {#styling}

<TableBuilder name="Loading" />
