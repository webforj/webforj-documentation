---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

`BusyIndicator`提供视觉提示，以确保用户意识到正在进行的过程，防止他们过早与系统交互。它通常覆盖整个应用界面以进行全局操作。

虽然[`Loading`](../components/loading)组件专注于应用中的特定区域或组件，但`BusyIndicator`处理全局的、整个应用的过程，并阻止整个界面的交互。这种范围上的区别使得[`Loading`](../components/loading)组件非常适合于更本地化、特定组件的场景，例如在页面特定部分加载数据。相反，`BusyIndicator`适用于影响整个应用的系统级操作，例如初始化应用或执行重大数据同步。

## Basics {#basics}

webforJ中的`BusyIndicator`显示为一个简单的旋转器，使其易于使用而无需配置。但是，您可以通过添加消息、调整旋转器的主题或修改可见性设置来进行自定义。这使您能够提供更多上下文或样式，同时保持功能性和开箱即用的解决方案。

在这个示例中，`BusyIndicator`阻止界面上的任何用户操作，直到操作完成。

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

webforJ中的`BusyIndicator`组件允许您显示一个背景，以在过程进行时阻止用户交互。默认情况下，组件启用背景，但您可以选择在必要时关闭它。

`BusyIndicator`默认显示背景。您可以使用`setBackdropVisible()`方法控制背景的可见性，如下所示：

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // 禁用背景
busyIndicator.open();
```
:::info 关闭背景
即使您关闭背景，`BusyIndicator`组件仍会阻止用户交互，以确保背后的过程不中断地完成。背景仅控制视觉覆盖，而不是交互阻止行为。
:::

## `Spinner` {#spinner}

webforJ中的`BusyIndicator`组件包含一个`Spinner`，在背景操作进行时以视觉方式指示操作正在进行。您可以通过多种选项自定义该旋转器，包括其大小、速度、方向、主题和可见性。

以下是如何在`BusyIndicator`组件内自定义旋转器的示例：

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## 使用案例 {#use-cases}
- **页面广泛处理**  
   `BusyIndicator`适合用于更大、页面范围的操作，例如当用户启动影响整个页面的任务时，如上传文件或在多个部分处理数据。它可以告诉用户整个应用正在工作，防止进一步交互，直到过程完成。

- **关键系统操作**  
   执行关键系统任务时，例如同步数据、应用全局更新或处理敏感信息时，`BusyIndicator`提供清晰的视觉反馈，表明重大操作正在进行，允许用户等到完成后再进行操作。

- **异步数据加载**  
   在涉及异步数据处理的场景中，例如调用多个API或等待复杂计算时，`BusyIndicator`组件积极指示系统忙碌，提示用户在执行其他操作之前耐心等待。

## 样式 {#styling}

<TableBuilder name="BusyIndicator" />
