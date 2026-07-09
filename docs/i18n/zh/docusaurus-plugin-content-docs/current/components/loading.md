---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在特定组件或区域上显示一个覆盖层，表示操作正在进行中，并暂时阻止交互。它非常适合数据加载、计算或后台处理等任务。对于全局应用过程，[`BusyIndicator`](../components/busyindicator) 组件则覆盖整个界面。

## 基础 {#basics}

创建 `Loading` 组件的最简单方法是初始化它而不添加任何额外设置。默认情况下，这将在其父内容上显示一个基本的旋转器。然而，您也可以提供一个消息以提供更多上下文。

以下是创建带有消息的 `Loading` 组件的示例：

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com.webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## 范围 {#scoping}

webforJ 中的 `Loading` 组件可以将其作用范围限定于特定的父容器，例如 `Div`，确保它只会阻止该元素内的用户交互。默认情况下，`Loading` 组件相对于其父组件，即它覆盖的是父组件，而不是整个应用。

要将 `Loading` 组件限制在其父组件上，只需将 `Loading` 组件添加到父容器中。例如，如果您将其添加到 `Div` 中，则加载覆盖层只适用于该 `Div`：

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 只会阻止 parentDiv 内的交互
```

## 背景 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示背景以阻止用户交互，直到处理完成。默认情况下，组件启用背景，但您可以在需要时将其关闭。

对于 `Loading` 组件，背景默认可见。您可以使用 `setBackdropVisible()` 方法显式启用或关闭背景：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景
loading.open();
```
:::info 背景关闭
即使您关闭背景，`Loading` 组件仍会继续阻止用户交互，以确保底层处理能够不受干扰地完成。背景仅控制视觉覆盖层，而不是交互阻止行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `Loading` 组件包含一个 `Spinner`，可以直观地指示后台操作正在进行。您可以使用多个选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## 使用案例 {#use-cases}
- **数据获取**
   当从服务器或 API 获取数据时，`Loading` 组件会在 UI 的特定部分（例如卡片或表单）上覆盖，以告知用户系统正在后台工作。当您想在屏幕的某一部分显示进度而不阻止整个界面时，这非常理想。

- **卡片/部分内容加载**
   `Loading` 组件可以作用于页面的特定区域，例如单个卡片或容器。当您希望指示 UI 的特定部分仍在加载，而允许用户与页面的其他部分进行交互时，这非常有用。

- **复杂表单提交**
   对于需要时间进行验证或处理的较长表单提交，`Loading` 组件向用户提供了视觉反馈，使他们安心，表明其输入正在积极处理。

## 样式 {#styling}

<TableBuilder name="Loading" />
