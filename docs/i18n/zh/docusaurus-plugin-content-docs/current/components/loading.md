---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在特定组件或区域上显示叠加层，表示操作正在进行，并暂时阻止交互。它适用于数据加载、计算或后台处理等任务。对于全局的应用级进程， [`BusyIndicator`](../components/busyindicator) 组件则覆盖整个界面。

<!-- INTRO_END -->

## 基础 {#basics}

创建 `Loading` 组件最简单的方法是初始化它而不需要任何额外设置。默认情况下，这将在其父内容上显示基本的旋转器。然而，您也可以提供一条消息来提供更多上下文。

以下是创建带有消息的 `Loading` 组件的示例：

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com.webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## 范围 {#scoping}

webforJ中的 `Loading` 组件可以将自身限制在特定的父容器中，例如 `Div`，确保它仅阻止用户在该元素内的交互。默认情况下，`Loading` 组件相对于其父组件，这意味着它覆盖父组件而不是整个应用程序。

要将 `Loading` 组件限制在其父级，只需将 `Loading` 组件添加到父容器中。例如，如果将其添加到 `Div`，加载叠加仅适用于该 `Div`：

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 将只会阻止在 parentDiv 中的交互
```

## 背景 {#backdrop}

webforJ中的 `Loading` 组件允许您显示一个背景，以阻止用户交互，直到过程完成。默认情况下，该组件启用背景，但如果需要，可以选择关闭它。

对于 `Loading` 组件，默认情况下背景是可见的。您可以使用 `setBackdropVisible()` 方法显式启用或关闭它：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景
loading.open();
```
:::info 背景关闭
即使您关闭背景，`Loading` 组件仍会继续阻止用户交互，以确保底层过程的完成不受干扰。背景仅控制视觉覆盖，而不是交互阻止行为。
:::

## `旋转器` {#spinner}

webforJ中的 `Loading` 组件包含一个 `Spinner`，可视指示后台操作正在进行。您可以通过多种选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com.webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## 用例 {#use-cases}
- **数据获取**  
   当从服务器或 API 检索数据时，`Loading` 组件会覆盖用户界面的特定部分，例如卡片或表单，以告知用户系统在后台工作。这在您希望仅在屏幕的一部分显示进度，而不阻止整个界面时是理想的。

- **卡片/部分中的内容加载**  
   `Loading` 组件可以被限制在页面的特定区域，例如单个卡片或容器。这在您希望表明用户界面的特定部分仍在加载，同时允许用户与页面的其他部分交互时非常有用。

- **复杂表单提交**  
   对于验证或处理时间较长的表单提交，`Loading` 组件为用户提供视觉反馈，向他们保证其输入正处于处理之中。

## 样式 {#styling}

<TableBuilder name="Loading" />
