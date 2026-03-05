---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 是一个全屏覆盖，会指示正在进行的过程并在完成之前阻止用户交互。在初始化或数据同步等操作过程中，它覆盖整个界面。而 [`Loading`](../components/loading) 组件则专注于界面中的特定区域，`BusyIndicator` 则是全局适用的。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 中的 `BusyIndicator` 显示为一个简单的旋转图标，使用起来非常方便，无需配置。然而，您可以通过添加消息、调整旋转图标的主题或修改可见性设置来自定义它。这使您可以在保持功能性和开箱即用解决方案的同时提供更多上下文或样式。

在这个例子中，`BusyIndicator` 防止用户在整个界面上进行任何操作，直到操作完成。

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## 背景 {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示背景以阻止用户交互，同时一个过程正在进行。默认情况下，组件启用背景，但您可以选择在需要时关闭它。

`BusyIndicator` 默认显示背景。您可以使用 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator` 组件仍会继续阻止用户交互，以确保底层过程能够不间断完成。背景仅控制可视覆盖，而不影响交互阻止行为。
:::

## `旋转图标` {#spinner}

webforJ 中的 `BusyIndicator` 组件包含一个 `Spinner`，可视化指示后台操作正在进行。您可以通过多个选项自定义此旋转图标，包括其大小、速度、方向、主题和可见性。

以下是如何在 `BusyIndicator` 组件中自定义旋转图标的示例：

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## 使用场景 {#use-cases}
- **整页处理**  
   `BusyIndicator` 非常适合更大、全页的操作，例如用户启动影响整个页面的任务时，如上传文件或对多个部分处理数据。它可以告知用户整个应用正在工作，防止在过程完成之前的进一步交互。

- **关键系统操作**  
   在执行系统关键任务时，例如同步数据、应用系统范围的更新或处理敏感信息，`BusyIndicator` 提供清晰的视觉反馈，以指示重大操作正在进行，允许用户等待直到操作完成。

- **异步数据加载**  
   在涉及异步数据处理的场景中，如调用多个 API 或等待复杂计算时，`BusyIndicator` 组件积极表明系统繁忙，提示用户在执行其他操作之前等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
