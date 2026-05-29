---
title: Slider
sidebar_position: 101
_i18n_hash: 490cb925a92ffd4860f74b00491402e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`组件为用户提供了一种通过在一个最小值和最大值之间拖动旋钮来选择数值的方法。可以配置步长间隔、刻度线和标签来指导选择。

<!-- INTRO_END -->

## 基础 {#basics}

`Slider`设计为开箱即用，不需要额外的设置即可有效运行。默认情况下，它的范围从0到100，初始值为50，非常适合快速集成到任何应用程序中。对于更具体的用例，`Slider`可以通过方向、刻度线、标签和工具提示等属性进行自定义。

以下是一个允许用户在预定义范围内调整音量级别的`Slider`示例：

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`值 {#slider-value}

`Slider`值表示旋钮在滑块上的当前位置，并定义为`Slider`范围内的一个整数。当用户与滑块交互时，该值会动态更新，是跟踪用户输入的重要属性。

:::tip 默认值
默认情况下，`Slider`的起始值为50，假设默认范围为0到100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化时设置`Slider`的值，或者使用`setValue()`方法在稍后更新它。要检索当前值，请使用`getValue()`方法。

```java
Slider slider = new Slider();  
slider.setValue(25); // 设置滑块为25

Integer value = slider.getValue();  
System.out.println("当前滑块值: " + value);
```

## 最小和最大值 {#minimum-and-maximum-values}

最小值和最大值定义`Slider`的允许范围，确定`Slider`旋钮可以移动的边界。默认情况下，范围设置为0到100，但您可以根据需要自定义这些值。

`Slider`上的间隔具有默认步长为1，这意味着间隔的数量由范围决定。例如：
- 范围为0到10的滑块将具有10个间隔。
- 范围为0到100的滑块将具有100个间隔。

这些间隔均匀分布在滑块轨道上，其间距取决于`Slider`的尺寸。

以下是创建自定义范围的`Slider`示例：

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## 刻度配置 {#tick-configuration}

`Slider`组件提供灵活的刻度配置，允许您自定义刻度线的显示方式以及滑块旋钮与刻度线的交互。这包括调整大刻度和小刻度的间隔、显示/隐藏刻度线，以及启用对刻度线的吸附，以便于用户输入。

### 大刻度和小刻度间隔 {#major-and-minor-tick-spacing}

您可以定义大刻度和小刻度的间隔，这决定它们在`Slider`轨道上出现的频率：

- 大刻度较大，通常标记为关键值。
- 小刻度较小，并在大刻度之间出现，以提供更细的间隔。

使用以下`setMajorTickSpacing()`和`setMinorTickSpacing()`方法设置刻度间隔：
```java
slider.setMajorTickSpacing(10); // 每10个单位一个大刻度
slider.setMinorTickSpacing(2);  // 每2个单位一个小刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用`setTicksVisible()`方法切换刻度线的可见性。默认情况下，刻度线是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度线
slider.setTicksVisible(false); // 隐藏刻度线
```

### 吸附 {#snapping}

为了确保`Slider`旋钮在用户交互过程中与最近的刻度线对齐，请使用`setSnapToTicks()`方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

以下是一个完全配置的`Slider`，显示了主要和次要刻度设置以及精确调整的吸附功能：

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider`组件支持两种方向：水平（默认）和垂直。您可以根据UI布局和应用需求更改方向。

除了方向，`Slider`还可以反转。默认情况下：

- 水平`Slider`从最小值（左）到最大值（右）。
- 垂直`Slider`从最小值（底部）到最大值（顶部）。

当反转时，方向会被反转。使用`setInverted(true)`方法启用反转。

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## 标签 {#labels}

`Slider`组件支持在刻度线上的标签，以帮助用户更容易地解释值。您可以使用默认的数值标签或提供自定义标签，您可以根据需要切换它们的可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在大刻度处显示数值标签。这些值由`setMajorTickSpacing()`设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用`setLabels()`方法将默认数值标签替换为自定义文本。当您希望显示更有意义的值（例如温度、货币或类别）时，这很有帮助。

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

无论您使用的是默认标签还是自定义标签，您都可以使用`setLabelsVisible(true)`控制它们的可见性，或者使用`setLabelsVisible(false)`将其隐藏。

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## 工具提示 {#tooltips}

工具提示通过在旋钮上方或下方直接显示`Slider`的值来增强可用性，帮助用户进行更精确的调整。您可以根据需要配置工具提示的行为、可见性和格式。

要启用工具提示，请使用`setTooltipVisible()`方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示也可以配置为仅在用户与`Slider`交互时出现。使用`setTooltipVisibleOnSlideOnly()`方法启用此行为。这对于减少视觉杂乱而仍提供有用的反馈非常有用。

以下是一个完全配置的`Slider`示例，带有工具提示：

### 工具提示定制 {#tooltip-customization}

默认情况下，`Slider`显示带有当前值的工具提示。如果您想自定义此文本，请使用`setTooltipText()`方法。这在您希望工具提示显示静态或描述性文本而不是实时值时非常有用。

您还可以使用JavaScript表达式动态格式化工具提示。如果您的表达式包含`return`关键字，则会按原样使用。如果没有，它会被自动包裹在`return`和`;`之间，以形成有效的函数。例如：

```java
// 显示值后跟一个美元符号
slider.setTooltipText("return x + '$'"); 
```

或者简单地：

```java
// 解释为: return x + ' units';
slider.setTooltipText("x + ' units'"); 
```

## 样式 {#styling}

### 主题 {#themes}

`Slider`内置了6种主题，便于快速样式设置而无需使用CSS。通过使用内置的枚举类支持主题。
下面显示的是应用每种支持的主题的滑块：

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
