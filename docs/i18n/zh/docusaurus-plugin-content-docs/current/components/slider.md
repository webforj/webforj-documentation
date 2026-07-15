---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 06f08c2c7500c5fb8d50a1dcfd8488da
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` 组件为用户提供了一种通过在最小值和最大值之间拖动滑块来选择数值的方式。可以配置步长间隔、刻度标记和标签，以引导选择。

<!-- INTRO_END -->

## 基础 {#basics}

`Slider` 设计为即插即用，无需额外设置即可有效运行。默认情况下，它的范围从 0 到 100，起始值为 50，非常适合快速集成到任何应用程序中。对于更具体的用例，可以通过方向、刻度标记、标签和工具提示等属性自定义 `Slider`。

以下是一个 `Slider` 的示例，它允许用户在预定义范围内调整音量水平：

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` 值 {#slider-value}

`Slider` 值表示滑块在滑动条上的当前位置，定义为 `Slider` 范围内的整数。此值会在用户与滑动条交互时动态更新，是跟踪用户输入的重要属性。

:::tip 默认值
默认情况下，`Slider` 的起始值为 50，假定默认范围为 0 到 100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化期间设置 `Slider` 的值，或使用 `setValue()` 方法稍后更新它。要检索当前值，请使用 `getValue()` 方法。

```java
Slider slider = new Slider();
slider.setValue(25); // 将滑块设置为 25

Integer value = slider.getValue();
System.out.println("当前滑块值: " + value);
```

## 最小值和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了 `Slider` 的允许范围，确定滑块可以移动的边界。默认情况下，范围设置为 0 到 100，但您可以根据需要自定义这些值。

`Slider` 的步长默认设为 1，这意味着间隔的数量由范围决定。例如：
- 范围为 0 到 10 的滑块将有 10 个间隔。
- 范围为 0 到 100 的滑块将有 100 个间隔。

这些间隔均匀分布在滑条轨道上，其间距取决于 `Slider` 的尺寸。

以下是创建具有自定义范围的 `Slider` 的示例：

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## 刻度配置 {#tick-configuration}

`Slider` 组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块与刻度标记的交互。这包括调整主要和次要刻度间距、显示/隐藏刻度标记，以及启用对刻度的吸附以实现精确用户输入。

### 主要和次要刻度间距 {#major-and-minor-tick-spacing}

您可以定义主要和次要刻度标记的间距，这决定了它们在 `Slider` 轨道上出现的频率：

- 主要刻度较大，通常带有标签，表示关键值。
- 次要刻度较小，并且出现在主要刻度之间，以提供更细的间隔。

使用以下 `setMajorTickSpacing()` 和 `setMinorTickSpacing()` 方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每 10 个单位一个主要刻度
slider.setMinorTickSpacing(2);  // 每 2 个单位一个次要刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用 `setTicksVisible()` 方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 吸附 {#snapping}

为了确保 `Slider` 滑块在用户交互时与最近的刻度标记对齐，请使用 `setSnapToTicks()` 方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

以下是一个完全配置的 `Slider` 示例，显示主要和次要刻度设置，以及用于精确调整的吸附功能：

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider` 组件支持两种方向：水平（默认）和垂直。您可以更改方向以适应您的 UI 布局和应用需求。

除了方向，`Slider` 还可以被反转。默认情况下：

- 水平 `Slider` 从最小值（左）到最大值（右）。
- 垂直 `Slider` 从最小值（底部）到最大值（顶部）。

当反转时，此方向将被反转。使用 `setInverted(true)` 方法启用反转。

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## 标签 {#labels}

`Slider` 组件支持在刻度标记上显示标签，以帮助用户更容易地理解值。您可以使用默认的数字标签或提供自定义标签，并根据需要切换可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在主要刻度标记上显示数字标签。这些值由 `setMajorTickSpacing()` 设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用 `setLabels()` 方法将默认数字标签替换为自定义文本。这在您希望显示更有意义的值（如温度、货币或类别）时特别有用。

```java
Map<Integer, String> customLabels = Map.of(
  0, "冷",
  30, "凉",
  50, "适中",
  80, "温暖",
  100, "热"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### 切换标签可见性 {#toggling-label-visibility}

无论您使用默认标签还是自定义标签，您都可以使用 `setLabelsVisible(true)` 控制其可见性，或使用 `setLabelsVisible(false)` 隐藏它们。

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## 工具提示 {#tooltips}

工具提示通过在滑块上方或下方直接显示 `Slider` 的值来提高可用性，帮助用户进行更精确的调整。您可以根据需要配置工具提示的行为、可见性和格式。

要启用工具提示，请使用 `setTooltipVisible()` 方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示也可以配置为仅在用户与 `Slider` 交互时出现。使用 `setTooltipVisibleOnSlideOnly()` 方法启用此行为。这在减少视觉混乱的同时，仍然提供了有用的反馈。

以下是一个完全配置的 `Slider` 示例，具有工具提示：

### 工具提示自定义 {#tooltip-customization}

默认情况下，`Slider` 显示工具提示，显示其当前值。如果您想自定义此文本，请使用 `setTooltipText()` 方法。当您希望工具提示显示静态或描述性文本而不是实时值时，这非常有用。

您还可以使用 JavaScript 表达式动态格式化工具提示。如果您的表达式包括 `return` 关键字，它将按原样使用。如果没有，则会自动用 `return` 和 `;` 包装以形成有效的函数。例如：

```java
// 显示值后跟美元符号
slider.setTooltipText("return x + '$'");
```

或者简单地：

```java
// 被解释为: return x + ' units';
slider.setTooltipText("x + ' units'");
```

## 样式 {#styling}

### 主题 {#themes}

`Slider` 附带 6 种主题，可快速实现样式，无需使用 CSS。通过使用内置的枚举类来支持主题。
以下是应用每种支持主题的滑块示例：

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
