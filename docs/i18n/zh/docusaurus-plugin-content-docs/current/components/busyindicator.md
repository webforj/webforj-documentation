---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 是一个全屏覆盖，表示正在进行的过程，并阻止用户互动，直到操作完成。在初始化或数据同步等操作期间，它覆盖整个界面。而 [`Loading`](../components/loading) 组件则关注于界面内的特定区域，`BusyIndicator` 则适用于全局。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 中的 `BusyIndicator` 显示为一个简单的旋转器，使用起来非常方便，无需配置。然而，您可以通过添加消息、调整旋转器的主题或修改可见性设置来进行自定义。这使您能在保持功能性和开箱即用的解决方案的同时，提供更多的上下文或样式。

在此示例中，`BusyIndicator` 阻止整个界面上的任何用户操作，直到操作完成。

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## 背景 {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示一个背景，以在处理过程中阻止用户互动。默认情况下，该组件启用背景，但如果需要，您可以选择关闭它。

`BusyIndicator` 默认显示背景。您可以通过 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator` 组件仍会阻止用户互动，以确保基础过程不被打断完成。背景仅控制视觉覆盖，而不是互动阻止行为。
:::

## `旋转器` {#spinner}

webforJ 中的 `BusyIndicator` 组件包含一个 `Spinner`，用于直观地指示后台操作正在进行。您可以使用多种选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是您如何在 `BusyIndicator` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## 用例 {#use-cases}
- **全页处理**  
   `BusyIndicator` 非常适合较大、全页范围的操作，例如当用户发起影响整个页面的任务时，如上传文件或在多个区域处理数据。它可以告诉用户整个应用正在工作，防止进一步的互动，直到过程完成。

- **关键系统操作**  
   在执行关乎系统的关键任务时，如同步数据、应用系统范围的更新或处理敏感信息，`BusyIndicator` 提供清晰的视觉反馈，表明主要操作正在进行，让用户耐心等待直到完成。

- **异步数据加载**  
   在涉及异步数据处理的场景中，例如调用多个API或等待复杂计算时，`BusyIndicator` 组件积极指示系统繁忙，提示用户在执行其他操作之前等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
