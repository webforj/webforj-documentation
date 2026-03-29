---
title: Loading
sidebar_position: 65
_i18n_hash: 45fa6bcfc4a2fd5995a06dc98b6f91bf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在特定组件或区域上显示叠加层，表明操作正在进行并暂时阻止交互。它适用于数据加载、计算或后台处理等任务。对于全局的应用程序级流程，[`BusyIndicator`](../components/busyindicator) 组件则覆盖整个界面。

## 基础知识 {#basics}

创建 `Loading` 组件的最简单方法是初始化它，而不添加任何额外的设置。默认情况下，这会在其父内容上显示基本的旋转器。但是，您也可以提供消息以获得更多上下文。

以下是创建带有消息的 `Loading` 组件的示例：

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## 范围 {#scoping}

webforJ 中的 `Loading` 组件可以限制在特定的父容器内，例如 `Div`，确保它仅在该元素内阻止用户交互。默认情况下，`Loading` 组件相对于其父元素，即覆盖父组件而不是整个应用。

要将 `Loading` 组件限制在其父母位置，只需将 `Loading` 组件添加到父容器中。例如，如果您将其添加到 `Div`，加载叠加仅适用于该 `Div`：

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 将仅阻止在 parentDiv 中的交互
```

## 背景层 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示背景层以阻止用户交互，直到过程完成。默认情况下，该组件启用了背景层，但您可以根据需要将其关闭。

对于 `Loading` 组件，背景层默认是可见的。您可以使用 `setBackdropVisible()` 方法明确启用或关闭它：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景层
loading.open();
```
:::info 关闭背景层
即使您关闭背景层，`Loading` 组件仍会阻止用户交互，以确保底层过程不受干扰地完成。背景层仅控制视觉叠加，而不阻止交互行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `Loading` 组件包含一个 `Spinner`，以可视化方式指示后台操作正在进行。您可以使用多个选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转器的示例：

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## 用例 {#use-cases}
- **数据获取**  
   在从服务器或 API 获取数据时，`Loading` 组件在 UI 的特定部分叠加，例如卡片或表单，以通知用户系统在后台工作。当您希望仅在屏幕的某个部分显示进度而不阻止整个界面时，这非常理想。

- **卡片/区域中的内容加载**  
   `Loading` 组件可以限制在页面的特定区域，例如单独的卡片或容器。这在您希望指示 UI 的特定部分仍在加载的同时允许用户与页面的其他部分进行交互时非常有用。

- **复杂表单提交**  
   对于需要时间进行验证或处理的较长表单提交，`Loading` 组件为用户提供可视反馈，让他们放心，他们的输入正在积极处理。

## 样式 {#styling}

<TableBuilder name="Loading" />
