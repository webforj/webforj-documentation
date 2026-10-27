---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 88cace5ce1650eaaf33dfc4535125dc0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` 组件为用户提供了一种通过在最小值和最大值之间拖动滑块来选择数值的方法。可以配置步骤间隔、刻度标记和标签，以引导选择。

<!-- INTRO_END -->

一个新的 `Slider` 范围从 0 到 100，起始值为 50，因此无需任何设置即可使用。可以为方向、刻度标记、标签和工具提示等属性进行配置，以覆盖更具体的情况，例如下面的音量控制。

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` 值 {#slider-value}

`Slider` 值表示滑块在滑块上的当前位置，并被定义为在 `Slider` 范围内的整数。这个值会在用户与滑块交互时动态更新，成为追踪用户输入的重要属性。

:::tip 默认值
默认情况下，`Slider` 的起始值为 50，假设默认范围为 0 到 100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化期间设置 `Slider` 的值或使用 `setValue()` 方法在之后更新它。要检索当前值，请使用 `getValue()` 方法。

```java
Slider slider = new Slider();
slider.setValue(25); // 将滑块设置为 25

Integer value = slider.getValue();
System.out.println("当前滑块值: " + value);
```

## 最小值和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了 `Slider` 的允许范围，确定滑块可以移动的边界。默认情况下，范围设置为 0 到 100，但您可以自定义这些值以满足您的需求。

`Slider` 上的间隔具有默认步长为 1，这意味着间隔的数量由范围决定。例如：
- 范围为 0 到 10 的滑块将具有 10 个间隔。
- 范围为 0 到 100 的滑块将具有 100 个间隔。

这些间隔平均分布在滑块轨道上，其间距根据 `Slider` 的尺寸而定。

下面是创建具有自定义范围的 `Slider` 的示例：

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## 刻度配置 {#tick-configuration}

`Slider` 组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块与其交互的方式。这包括调整主要和次要刻度间距、显示/隐藏刻度标记以及启用对刻度标记的吸附，以便进行精确的用户输入。

### 主要和次要刻度间距 {#major-and-minor-tick-spacing}

您可以定义主要和次要刻度标记的间距，这决定了它们在 `Slider` 轨道上出现的频率：

- 主要刻度较大，通常带有标签以表示关键值。
- 次要刻度较小，介于主要刻度之间，以提供更细的间隔。

使用以下 `setMajorTickSpacing()` 和 `setMinorTickSpacing()` 方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每 10 个单位设置一个主要刻度
slider.setMinorTickSpacing(2);  // 每 2 个单位设置一个次要刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用 `setTicksVisible()` 方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 吸附 {#snapping}

为了确保 `Slider` 滑块在用户交互过程中与最近的刻度标记对齐，可以使用 `setSnapToTicks()` 方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

下面是示例，展示了一个完全配置的 `Slider`，显示了主要和次要刻度设置以及用于精确调整的吸附功能：

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider` 组件支持两种方向：水平（默认）和垂直。您可以根据 UI 布局和应用程序要求更改方向。

除了方向外，`Slider` 还可以反转。默认情况下：

- 水平 `Slider` 从最小值（左）到最大值（右）。
- 垂直 `Slider` 从最小值（底部）到最大值（顶部）。

反转后，这个方向是反向的。使用 `setInverted(true)` 方法启用反转。

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## 标签 {#labels}

`Slider` 组件支持在刻度标记上使用标签，以帮助用户更容易地理解值。您可以使用默认的数值标签或提供自定义标签，并可以根据需要切换其可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在主要刻度标记上显示数值标签。这些值由 `setMajorTickSpacing()` 设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用 `setLabels()` 方法将默认数值标签替换为自定义文本。这在您希望显示更有意义的值（例如温度、货币或类别）时非常有用。

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

无论您使用默认标签还是自定义标签，都可以使用 `setLabelsVisible(true)` 控制其可见性或使用 `setLabelsVisible(false)` 隐藏它们。

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## 工具提示 {#tooltips}

工具提示通过在滑块上方或下方直接显示 `Slider` 的值来增强可用性，从而帮助用户进行更精确的调整。您可以根据需要配置工具提示的行为、可见性和格式。

要启用工具提示，请使用 `setTooltipVisible()` 方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示还可以配置为仅在用户与 `Slider` 交互时出现。使用 `setTooltipVisibleOnSlideOnly()` 方法启用此行为。这在减少视觉杂乱的同时，仍然在交互过程中提供有用的反馈时尤其有用。

这是一个完全配置的带有工具提示的 `Slider` 示例：

### 工具提示自定义 {#tooltip-customization}

默认情况下，`Slider` 显示带有当前值的工具提示。如果您希望自定义此文本，可以使用 `setTooltipText()` 方法。当您希望工具提示显示静态或描述性文本而不是实时值时，这很有用。

您还可以使用 JavaScript 表达式动态格式化工具提示。如果您的表达式包含 `return` 关键字，它将按原样使用。如果没有，它会自动用 `return` 和 `;` 包裹，以形成有效的函数。例如：

```java
// 显示值后跟美元符号
slider.setTooltipText("return x + '$'");
```

或者简单地：

```java
// 被解释为: return x + ' 单位';
slider.setTooltipText("x + ' 单位'");
```

## 样式 {#styling}

### 主题 {#themes}

`Slider` 具有内置的 6 种主题，可快速进行样式设置，而无需使用 CSS。主题通过使用内置的枚举类来支持。
下面显示的是应用每个受支持主题的滑块：

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
