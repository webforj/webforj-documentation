---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`组件在webforJ中提供了一种交互式控制，允许用户通过移动旋钮选择特定范围内的值。这个特性对于需要精确或直观输入的应用特别有用，例如选择音量、百分比或其他可调值。

## 基础 {#basics}

`Slider`设计为即插即用，无需额外设置即可有效运作。默认情况下，它的范围从0到100，起始值为50，非常适合快速集成到任何应用中。对于更具体的使用案例，`Slider`可以通过方向、刻度标记、标签和工具提示等属性进行定制。

以下是一个允许用户在预定义范围内调整音量级别的`Slider`示例：

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`值 {#slider-value}

`Slider`值表示旋钮在滑块上的当前位置，它被定义为`Slider`范围内的一个整数。该值会随着用户与滑块的互动而动态更新，是跟踪用户输入的重要属性。

:::tip 默认值
默认情况下，`Slider`的起始值为50，假定默认范围为0到100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化期间设置`Slider`的值，或稍后使用`setValue()`方法更新它。要检索当前值，请使用`getValue()`方法。

```java
Slider slider = new Slider();  
slider.setValue(25); // 设置滑块为25

Integer value = slider.getValue();  
System.out.println("当前滑块值: " + value);
```

## 最小值和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了`Slider`的可允许范围，确定了滑块旋钮的移动边界。默认情况下，范围设置为0到100，但您可以根据需要自定义这些值。

`Slider`的间隔默认步长为1，表示间隔的数量由范围决定。例如：
- 范围为0到10的滑块将有10个间隔。
- 范围为0到100的滑块将有100个间隔。

这些间隔均匀分布在滑块轨道上，间隔的间距取决于`Slider`的尺寸。

以下是创建具有自定义范围的`Slider`示例：

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## 刻度配置 {#tick-configuration}

`Slider`组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块旋钮如何与它们交互。这包括调整主要和次要刻度间距、显示/隐藏刻度标记以及启用对刻度标记的吸附，以进行精确输入。

### 主要和次要刻度间距 {#major-and-minor-tick-spacing}

您可以定义主要和次要刻度标记的间距，这决定了它们在`Slider`轨道上出现的频率：

- 主要刻度较大，通常带有标签以表示关键值。
- 次要刻度较小，在主要刻度之间出现以提供更细微的间隔。

使用以下`setMajorTickSpacing()`和`setMinorTickSpacing()`方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每10个单位一个主要刻度
slider.setMinorTickSpacing(2);  // 每2个单位一个次要刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用`setTicksVisible()`方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 吸附 {#snapping}

为了确保`Slider`旋钮在用户互动时与最近的刻度标记对齐，可以使用`setSnapToTicks()`方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

以下是一个完全配置的`Slider`示例，展示主要和次要刻度设置以及精确调整的吸附功能：

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider`组件支持两种方向：水平（默认）和垂直。您可以根据UI布局和应用要求更改方向。

除了方向，`Slider`还可以反转。默认情况下：

- 水平`Slider`从最小值（左）到最大值（右）。
- 垂直`Slider`从最小值（底部）到最大值（顶部）。

当反转时，这个方向是相反的。使用`setInverted(true)`方法启用反转。

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## 标签 {#labels}

`Slider`组件支持在刻度标记上显示标签，以帮助用户更容易地解释值。您可以使用默认数字标签或提供自定义标签，并根据需要切换其可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在主要刻度处显示数字标签。这些值由`setMajorTickSpacing()`设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用`setLabels()`方法用自定义文本替换默认数字标签。这样在要显示更有意义的值（例如温度、货币或类别）时非常有用。

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

无论您使用默认标签还是自定义标签，您都可以使用`setLabelsVisible(true)`控制它们的可见性，或者使用`setLabelsVisible(false)`隐藏它们。

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## 工具提示 {#tooltips}

工具提示通过在旋钮上方或下方直接显示`Slider`的值来增强可用性，帮助用户进行更精确的调整。您可以配置工具提示的行为、可见性和格式以满足您的需求。

要启用工具提示，请使用`setTooltipVisible()`方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示也可以配置为仅在用户与`Slider`交互时出现。使用`setTooltipVisibleOnSlideOnly()`方法启用此行为。这对于减少视觉杂乱而仍提供有用的反馈非常有用。

以下是一个完全配置的`Slider`示例，带有工具提示：

### 工具提示自定义 {#tooltip-customization}

默认情况下，`Slider`显示带有当前值的工具提示。如果您想自定义此文本，请使用`setTooltipText()`方法。当您希望工具提示显示静态或描述性文本而不是实时值时，这很有用。

您还可以使用JavaScript表达式动态格式化工具提示。如果您的表达式包含`return`关键字，按原样使用。如果没有，则自动用`return`和`;`包装以形成有效函数。例如：

```java
// 显示值后跟美元符号
slider.setTooltipText("return x + '$'"); 
```

或简单地：

```java
// 被解析为：return x + ' units';
slider.setTooltipText("x + ' units'"); 
```

## 样式 {#styling}

### 主题 {#themes}

`Slider`内置有6种主题，可以快速样式化而无需使用CSS。通过使用内置的枚举类支持主题功能。
下面展示了应用了每个支持主题的滑块：

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
