---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 是一个全屏覆盖，表示正在进行的过程，并在完成之前阻止用户交互。它在初始化或数据同步等操作期间覆盖整个界面。虽然 [`Loading`](../components/loading) 组件侧重于界面中的特定区域，但 `BusyIndicator` 是全局应用的。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 中的 `BusyIndicator` 显示为一个简单的旋转器，使其易于使用而无需配置。然而，您可以通过添加消息、调整旋转器的主题或修改可见性设置来自定义它。这使您能够在提供更多上下文或样式的同时，保持一个功能齐全的开箱即用解决方案。

在这个例子中，`BusyIndicator` 阻止整个界面上的任何用户操作，直到操作完成。

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## 背景 {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示一个背景，以阻止用户在过程进行期间的交互。默认情况下，组件启用背景，但您可以选择在需要时关闭它。

`BusyIndicator` 默认显示背景。您可以使用 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator` 组件仍会阻止用户交互，以确保底层过程不会中断地完成。背景只是控制视觉覆盖，而不是交互阻止行为。
:::

## `旋转器` {#spinner}

webforJ 中的 `BusyIndicator` 组件包含一个 `Spinner`，可视地指示后台操作正在进行。您可以使用多个选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `BusyIndicator` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## 用例 {#use-cases}
- **全页处理**
   `BusyIndicator` 非常适合更大、全页范围的操作，例如当用户发起影响整个页面的任务时，比如上传文件或在多个部分处理数据。它可以告知用户整个应用正在工作，防止在过程完成之前的进一步交互。

- **关键系统操作**
   在执行系统关键任务时，如同步数据、应用系统范围的更新或处理敏感信息，`BusyIndicator` 提供清晰的视觉反馈，指示正在进行重大操作，允许用户等待直到完成。

- **异步数据加载**
   在涉及异步数据处理的场景中，例如在调用多个 API 或等待复杂计算时，`BusyIndicator` 组件主动指示系统繁忙，提醒用户在执行其他操作之前等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
