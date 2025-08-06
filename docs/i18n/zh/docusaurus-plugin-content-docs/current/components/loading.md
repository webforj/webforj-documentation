---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在 webforJ 中显示一个覆盖层，指示正在处理操作，暂时阻止用户交互，直到任务完成。此功能提高了用户体验，尤其是在数据加载、计算或后台处理等任务可能需要一些时间的情况下。对于全局应用程序进程，建议使用 [`BusyIndicator`](../components/busyindicator) 组件，该组件在整个界面上阻止交互。

## 基础 {#basics}

创建 `Loading` 组件的最简单方法是初始化它而不进行任何额外设置。默认情况下，这将在其父内容上显示一个基本的旋转器。但是，您也可以提供消息以获得更多上下文。

以下是如何创建一个带有消息的 `Loading` 组件的示例：

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## 范围 {#scoping}

webforJ 中的 `Loading` 组件可以将自己范围限定于特定的父容器，例如 `Div`，确保它只在该元素内阻止用户交互。默认情况下，`Loading` 组件相对于其父级，这意味着它覆盖了父组件而不是整个应用程序。

要将 `Loading` 组件限制在其父级，只需将 `Loading` 组件添加到父容器。例如，如果您将其添加到 `Div` 中，则加载覆盖仅适用于该 `Div`：

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 只会阻止在 parentDiv 内的交互
```

## 背景 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示一个背景以阻止用户交互，同时处理正在进行的过程。默认情况下，组件启用背景，但如果需要，您可以选择将其关闭。

对于 `Loading` 组件，背景默认是可见的。您可以使用 `setBackdropVisible()` 方法显式启用或关闭它：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景
loading.open();
```
:::info 背景关闭
即使在您关闭背景时，`Loading` 组件仍然会继续阻止用户交互，以确保底层过程不被打断。背景仅控制视觉覆盖，而不是交互阻止行为。
:::

## `旋转器` {#spinner}

webforJ 中的 `Loading` 组件包含一个 `Spinner`，可视地指示后台操作正在进行。您可以使用多个选项来自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件内自定义旋转器的示例：

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## 用例 {#use-cases}
- **数据获取**  
   当从服务器或 API 获取数据时，`Loading` 组件将覆盖 UI 的特定部分，例如卡片或表单，以通知用户系统在后台工作。这在您希望仅对屏幕的一部分显示进度而不阻止整个界面时是理想的。

- **卡片/部分中的内容加载**  
   `Loading` 组件可以限定于页面的特定区域，例如单个卡片或容器。当您希望表明 UI 的特定部分仍在加载，同时允许用户与页面的其他部分交互时，这非常有用。

- **复杂表单提交**  
   对于需要时间进行验证或处理的较长表单提交，`Loading` 组件为用户提供视觉反馈，安抚他们的输入正在积极处理。

## 风格 {#styling}

<TableBuilder name="Loading" />
