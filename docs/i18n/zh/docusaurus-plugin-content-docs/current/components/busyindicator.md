---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator` 提供视觉提示，以确保用户意识到正在进行的过程，从而防止他们过早与系统交互。它通常覆盖整个应用界面，适用于全局操作。

而 [`Loading`](../components/loading) 组件则专注于应用中的特定区域或组件，`BusyIndicator` 处理全局的、应用范围内的过程，并阻止整个界面的交互。这种范围上的差异使得 [`Loading`](../components/loading) 组件非常适合于更本地化、组件特定的情况，例如在页面的特定部分加载数据。相反，`BusyIndicator` 适合影响整个应用的系统范围操作，例如初始化应用或执行重大数据同步。

## Basics {#basics}

`BusyIndicator` 在 webforJ 中显示为一个简单的旋转加载器，使用起来无需配置。然而，您可以通过添加消息、调整加载器的主题或修改可见性设置来进行自定义。这使您能够提供更多的上下文或风格，同时保持功能齐全的开箱即用的解决方案。

在这个例子中，`BusyIndicator` 在操作完成之前会阻止用户在整个界面上的任何操作。

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

webforJ 中的 `BusyIndicator` 组件允许您显示一个背景，以在进程进行时阻止用户交互。默认情况下，组件启用背景，但如果需要，您可以选择将其关闭。

`BusyIndicator` 默认显示背景。您可以使用 `setBackdropVisible()` 方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator` 组件仍然会阻止用户交互，以确保基础进程可以不间断地完成。背景仅控制视觉覆盖，而不是交互阻止行为。
:::

## `Spinner` {#spinner}

webforJ 中的 `BusyIndicator` 组件包含一个 `Spinner`，可视化指示后台操作正在进行。您可以使用多个选项自定义该加载器，包括其大小、速度、方向、主题和可见性。

以下是如何在 `BusyIndicator` 组件中自定义加载器的示例：

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **全页面处理**  
   `BusyIndicator` 非常适合较大、全页面的操作，例如当用户启动影响整个页面的任务时，如上传文件或跨多个部分处理数据。它可以告知用户整个应用正在工作，防止在处理完成之前的进一步交互。

- **关键系统操作**  
   在执行系统关键任务时，如同步数据、应用系统范围的更新或处理敏感信息，`BusyIndicator` 提供清晰的视觉反馈，表明正在进行重大操作，允许用户等待其完成。

- **异步数据加载**  
   在涉及异步数据处理的场景中，例如调用多个 API 或等待复杂计算时，`BusyIndicator` 组件会积极指示系统正在忙碌，并提示用户在执行其他操作之前等待。

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
