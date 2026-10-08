---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 是一个全屏覆盖组件，用于指示正在进行的过程，并在完成之前阻止用户交互。在初始化或数据同步等操作期间，它覆盖整个界面。与专注于界面特定区域的 [`Loading`](../components/loading) 组件不同，`BusyIndicator` 在全局应用。

`BusyIndicator` 以旋转器的形式显示，无需配置。添加消息、更改旋转器的主题或调整可见性设置，当过程需要更多上下文时。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## 背景 {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示一个背景，以阻止用户在过程进行时的交互。默认情况下，该组件启用背景，但您可以选择在需要时将其关闭。

`BusyIndicator` 默认显示背景。您可以使用 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator` 组件仍然会继续阻止用户交互，以确保基础过程能够顺利完成。背景仅控制视觉覆盖效果，而不是交互阻塞行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `BusyIndicator` 组件包含一个 `Spinner`，用于直观地指示后台操作正在进行中。您可以使用多个选项自定义该旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `BusyIndicator` 组件中自定义旋转器的示例：

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## 用例 {#use-cases}
- **全页面处理**
   `BusyIndicator` 非常适合用于较大、全页面的操作，例如，当用户启动的任务影响整个页面时，如上传文件或跨多个部分处理数据。它可以告知用户整个应用正在工作，防止在过程完成之前的进一步交互。

- **关键系统操作**
   当执行系统关键任务时，例如同步数据、应用系统级更新或处理敏感信息时，`BusyIndicator` 提供清晰的视觉反馈，表明正在进行重大操作，用户需等待直至完成。

- **异步数据加载**
   在涉及异步数据处理的场景中，例如调用多个 API 或等待复杂计算时，`BusyIndicator` 组件积极指示系统正忙，提示用户在执行其他操作之前等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
