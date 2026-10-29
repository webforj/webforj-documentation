---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: a6ab827c0422e8f10369500acc310007
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 是一个全屏覆盖层，表示正在进行的过程并阻止用户交互，直到其完成。它在初始化或数据同步等操作期间覆盖整个界面。虽然 [`Loading`]（../components/loading）组件关注界面内的特定区域，但 `BusyIndicator` 是全局适用的。

`BusyIndicator` 显示为一个旋转器，无需配置。在需要更多上下文时，您可以添加消息、更改旋转器的主题，或调整可见性设置。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## 背景 {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示一个背景以在进程进行时阻止用户交互。默认情况下，该组件启用背景，但您可以根据需要将其关闭。

`BusyIndicator` 默认显示背景。您可以使用 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = App.getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使在您关闭背景时， `BusyIndicator` 组件仍然会阻止用户交互，以确保底层进程 uninterrupted 地完成。背景仅控制视觉覆盖，而不是交互阻塞行为。
:::

## `旋转器` {#spinner}

webforJ 中的 `BusyIndicator` 组件包括一个 `旋转器`，用以视觉指示后台操作正在进行。您可以通过几个选项自定义此旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `BusyIndicator` 组件内自定义旋转器的示例：

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## 用例 {#use-cases}
- **整体页面处理**
   `BusyIndicator` 非常适合较大、页面范围的操作，例如用户启动影响整个页面的任务，比如上传文件或在多个部分处理数据。它可以通知用户整个应用正在工作，防止在过程完成之前的进一步交互。

- **关键系统操作**
   在执行系统关键任务时，例如同步数据、应用全局更新或处理敏感信息，`BusyIndicator` 提供清晰的视觉反馈，表明有重大操作正在进行，允许用户等待其完成。

- **异步数据加载**
   在涉及异步数据处理的场景中，例如调用多个 API 或等待复杂计算时，`BusyIndicator` 组件积极表明系统正忙碌，提示用户在执行其他操作之前等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
