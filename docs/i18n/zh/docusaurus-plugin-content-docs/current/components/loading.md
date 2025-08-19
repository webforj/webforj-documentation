---
title: Loading
sidebar_position: 65
_i18n_hash: 9bdb4d5c978b4070d3628566e5105088
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

`Loading` 组件在 webforJ 中显示一个覆盖层，表示正在处理某个操作，临时阻止用户交互，直到任务完成。此功能改善了用户体验，尤其在数据加载、计算或后台处理等可能需要一些时间的任务的情况下。对于全局性、应用范围内的进程，建议使用 [`BusyIndicator`](../components/busyindicator) 组件，该组件会阻止整个界面的交互。

## 基础 {#basics}

创建 `Loading` 组件的最简单方法是初始化它，而不进行任何额外设置。默认情况下，这会在其父内容上显示一个基本的旋转图标。您也可以提供一条消息以提供更多上下文。

以下是创建带消息的 `Loading` 组件的示例：

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## 范围 {#scoping}

webforJ 中的 `Loading` 组件可以自我限制于特定的父容器，如 `Div`，确保它仅在该元素内阻止用户交互。默认情况下，`Loading` 组件相对于其父组件，这意味着它覆盖父组件，而不是整个应用程序。

要将 `Loading` 组件限制到其父组件，只需将 `Loading` 组件添加到父容器。例如，如果您将其添加到 `Div`，那么加载覆盖层仅适用于该 `Div`：

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading 将仅阻止 parentDiv 内的交互
```

## 背景 {#backdrop}

webforJ 中的 `Loading` 组件允许您显示一个背景，以阻止用户在过程进行时的交互。默认情况下，该组件启用背景，但您可以选择在需要时关闭它。

对于 `Loading` 组件，背景默认可见。您可以使用 `setBackdropVisible()` 方法显式启用或关闭它：

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // 禁用背景
loading.open();
```
:::info 背景关闭
即使您关闭背景，`Loading` 组件仍然会继续阻止用户交互，以确保基础过程不被打断。背景仅控制视觉覆盖，而不影响交互阻止行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `Loading` 组件包括一个 `Spinner`，用于直观表示后台操作正在进行。您可以使用多个选项来自定义此旋转图标，包括其大小、速度、方向、主题和可见性。

以下是如何在 `Loading` 组件中自定义旋转图标的示例：

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## 用例 {#use-cases}
- **数据获取**  
   当从服务器或 API 检索数据时，`Loading` 组件会覆盖 UI 的特定部分，如卡片或表单，以告知用户系统在后台工作。当您希望只在屏幕的一个部分显示进度而不阻止整个界面时，这种方法非常理想。

- **卡片/区段中的内容加载**  
   `Loading` 组件可以限制在页面的特定区域，如单个卡片或容器中。这在您希望指示 UI 的某个特定部分仍在加载的同时，让用户与页面的其他部分交互时非常有用。

- **复杂的表单提交**  
   对于较长的表单提交，验证或处理需要时间，`Loading` 组件向用户提供视觉反馈，安慰他们的输入正在被积极处理。

## 样式 {#styling}

<TableBuilder name="Loading" />
