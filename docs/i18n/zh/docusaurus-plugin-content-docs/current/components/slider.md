---
title: Slider
sidebar_position: 101
_i18n_hash: 56140362edd92adde8d6114a8e6652c9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` 组件为用户提供了一种通过拖动旋钮在最小和最大范围之间选择数值的方法。可以配置步长间隔、刻度标记和标签，以指导选择。

<!-- INTRO_END -->

## 基础 {#basics}

`Slider` 旨在开箱即用， 不需要额外设置即能有效功能。默认情况下，其范围从 0 到 100，起始值为 50，使其非常适合快速集成到任何应用程序中。对于更具体的用例，`Slider` 可以通过方向、刻度标记、标签和工具提示等属性进行自定义。

以下是一个 `Slider` 的示例，允许用户在预定义的范围内调节音量级别：

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` 值 {#slider-value}

`Slider` 的值表示旋钮在滑块上的当前位置，并在 `Slider` 的范围内定义为整数。随着用户与滑块的互动，该值会动态更新，因此它是跟踪用户输入的关键属性。

:::tip 默认值
默认情况下，`Slider` 从值为 50 开始，假定范围为 0 到 100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化时设置 `Slider` 的值，或通过 `setValue()` 方法在之后更新值。要检索当前值，请使用 `getValue()` 方法。

```java
Slider slider = new Slider();  
slider.setValue(25); // 设置滑块为 25

Integer value = slider.getValue();  
System.out.println("当前滑块值: " + value);
```

## 最小值和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了 `Slider` 的允许范围，确定了滑块旋钮可以移动的边界。默认情况下，范围设置为 0 到 100，但您可以根据需要自定义这些值。

`Slider` 的间隔默认步长为 1，这意味着间隔的数量由范围决定。例如：
- 范围为 0 到 10 的滑块将有 10 个间隔。
- 范围为 0 到 100 的滑块将有 100 个间隔。

这些间隔均匀分布在滑块轨道上，间隔的间距取决于 `Slider` 的尺寸。

下面是创建具有自定义范围的 `Slider` 的示例：

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## 刻度配置 {#tick-configuration}

`Slider` 组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块旋钮与其的互动。这包括调整主要和次要刻度间距、显示/隐藏刻度标记，以及启用对刻度标记的吸附，以便进行精确的用户输入。

### 主要和次要刻度间距 {#major-and-minor-tick-spacing}

您可以定义主要和次要刻度标记的间距，决定它们在 `Slider` 轨道上出现的频率：

- 主要刻度较大，并且通常标记以表示关键值。
- 次要刻度较小，并且出现在主要刻度之间，以提供更细的间隔。

使用以下 `setMajorTickSpacing()` 和 `setMinorTickSpacing()` 方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每 10 个单位设置主要刻度
slider.setMinorTickSpacing(2);  // 每 2 个单位设置次要刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用 `setTicksVisible()` 方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 吸附 {#snapping}

为了确保 `Slider` 的旋钮在用户交互时与最近的刻度标记对齐，可以使用 `setSnapToTicks()` 方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

下面是一个完整配置的 `Slider` 示例，显示主要和次要刻度设置以及精确调整的吸附功能：

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider` 组件支持两种方向：水平（默认）和垂直。您可以根据 UI 布局和应用要求更改方向。

除了方向，`Slider` 还可以被反转。默认情况下：

- 水平 `Slider` 从最小（左）到最大（右）。
- 垂直 `Slider` 从最小（底部）到最大（顶部）。

反转时，该方向将被逆转。使用 `setInverted(true)` 方法启用反转。

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## 标签 {#labels}

`Slider` 组件支持在刻度标记上显示标签，以帮助用户更轻松地解释值。您可以使用默认的数字标签或提供自定义标签，并根据需要切换它们的可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在主要刻度标记处显示数字标签。这些值由 `setMajorTickSpacing()` 设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用 `setLabels()` 方法将默认数字标签替换为自定义文本。当您想要显示更有意义的值（如温度、货币或分类）时，这很有帮助。

```java
Map<Integer, String> customLabels = Map.of(
    0, "冷",
    30, "凉爽",
    50, "适中",
    80, "温暖",
    100, "热"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### 切换标签可见性 {#toggling-label-visibility}

无论您使用默认标签还是自定义标签，您都可以使用 `setLabelsVisible(true)` 来控制它们的可见性，或者使用 `setLabelsVisible(false)` 来隐藏它们。

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## 工具提示 {#tooltips}

工具提示通过直接在旋钮上方或下方显示 `Slider` 的值，增强了可用性，帮助用户进行更精确的调整。您可以配置工具提示的行为、可见性和格式，以满足您的需求。

要启用工具提示，请使用 `setTooltipVisible()` 方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示还可以配置为仅在用户与 `Slider` 互动时出现。使用 `setTooltipVisibleOnSlideOnly()` 方法启用此行为。这在减少视觉杂乱的同时，在交互期间提供有用的反馈，特别有用。

以下是一个完整配置的 `Slider` 示例，其中包含工具提示：

### 工具提示自定义 {#tooltip-customization}

默认情况下，`Slider` 显示一个工具提示，显示其当前值。如果您想自定义此文本，请使用 `setTooltipText()` 方法。当您希望工具提示显示静态或描述性文本而不是实时值时，这很有用。

您还可以使用 JavaScript 表达式动态格式化工具提示。如果您的表达式包含 `return` 关键字，它将原样使用。如果没有，将自动用 `return` 和 `;` 包裹以形成有效的函数。例如：

```java
// 显示值后跟一个美元符号
slider.setTooltipText("return x + '$'"); 
```

或简单地：

```java
// 被解释为: return x + ' units';
slider.setTooltipText("x + ' units'"); 
```

## 样式 {#styling}

### 主题 {#themes}

`Slider` 提供了 6 种内置主题，方便在不使用 CSS 的情况下快速样式设置。主题通过使用内置的枚举类进行支持。
以下是应用每个支持主题的滑块的示例：

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
