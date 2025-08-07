---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`组件在webforJ中提供了一种交互式控制，允许用户通过移动旋钮在特定范围内选择值。该功能对于需要精确或直观输入的应用程序尤为有用，例如选择音量、百分比或其他可调值。

## 基础 {#basics}

`Slider`设计为即插即用，无需额外设置即可有效运行。默认情况下，它的范围从0到100，起始值为50，非常适合快速集成到任何应用中。对于更具体的用例，`Slider`可以通过方向、刻度标记、标签和工具提示等属性进行自定义。

以下是一个允许用户在预定义范围内调整音量水平的`Slider`示例：

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`值 {#slider-value}

`Slider`值代表旋钮在滑块上的当前位置信息，并在`Slider`的范围内定义为整数。当用户与滑块交互时，该值会动态更新，因此它是跟踪用户输入的一个重要属性。

:::tip 默认值
默认情况下，`Slider`的起始值为50，默认范围为0到100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化期间设置`Slider`的值，或使用`setValue()`方法稍后更新。要获取当前值，请使用`getValue()`方法。

```java
Slider slider = new Slider();  
slider.setValue(25); // 将滑块设置为25

Integer value = slider.getValue();  
System.out.println("当前滑块值: " + value);
```

## 最小值和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了`Slider`的允许范围，确定`Slider`旋钮可以移动的边界。默认情况下，范围设置为0到100，但您可以根据需要自定义这些值。

`Slider`的间隔默认步长为1，这意味着间隔的数量由范围决定。例如：
- 范围为0到10的Slider将具有10个间隔。
- 范围为0到100的Slider将具有100个间隔。

这些间隔均匀分布在滑块轨道上，其间距取决于`Slider`的尺寸。

以下是创建具有自定义范围的`Slider`示例：

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## 刻度配置 {#tick-configuration}

`Slider`组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块旋钮与它们的交互方式。这包括调整大刻度和小刻度间距、显示/隐藏刻度标记，以及启用对刻度标记的吸附以实现精准用户输入。

### 大刻度和小刻度间距 {#major-and-minor-tick-spacing}

您可以定义大刻度和小刻度标记的间距，决定它们在`Slider`轨道上出现的频率：

- 大刻度较大，通常标记为关键值。
- 小刻度较小，出现在大刻度之间，以提供更细的间隔。

使用以下`setMajorTickSpacing()`和`setMinorTickSpacing()`方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每10个单位一个大刻度
slider.setMinorTickSpacing(2);  // 每2个单位一个小刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用`setTicksVisible()`方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 吸附 {#snapping}

为确保`Slider`旋钮在用户交互期间与最近的刻度标记对齐，可以使用`setSnapToTicks()`方法启用吸附：

```java
slider.setSnapToTicks(true); // 启用吸附
```

以下是一个完全配置的`Slider`示例，展示了大刻度和小刻度设置以及用于精确调整的吸附功能：

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider`组件支持两种方向：水平（默认）和垂直。您可以更改方向以适应您的UI布局和应用要求。

除了方向，`Slider`还可以反转。默认情况下：

- 水平`Slider`从最小值（左）到最大值（右）。
- 垂直`Slider`从最小值（底部）到最大值（顶部）。

反转时，方向会被反转。使用`setInverted(true)`方法启用反转。

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## 标签 {#labels}

`Slider`组件支持在刻度标记上显示标签，以帮助用户更容易地理解值。您可以使用默认的数字标签或提供自定义标签，并可以根据需要切换它们的可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在大刻度标记上显示数字标签。这些值由`setMajorTickSpacing()`设置决定。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用`setLabels()`方法用自定义文本替换默认数字标签。当您希望显示更有意义的值（例如温度、货币或类别）时，这个功能是非常有用的。

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

无论您使用默认标签还是自定义标签，您都可以通过`setLabelsVisible(true)`控制它们的可见性，或使用`setLabelsVisible(false)`将其隐藏。

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

工具提示也可以配置为仅在用户与`Slider`交互时出现。使用`setTooltipVisibleOnSlideOnly()`方法启用此功能。这对于减少视觉杂乱同时在交互期间提供有用的反馈尤其有用。

以下是一个完全配置的带工具提示的`Slider`示例：

### 工具提示自定义 {#tooltip-customization}

默认情况下，`Slider`在其当前值上显示工具提示。如果您想自定义此文本，请使用`setTooltipText()`方法。当您希望工具提示显示静态或描述性文本而不是实时值时，这非常有用。

您还可以使用JavaScript表达式动态格式化工具提示。如果您的表达式中包含`return`关键字，它将按原样使用。如果没有，它会被自动用`return`和`;`包裹，以形成有效的函数。例如：

```java
// 显示值后跟美元符号
slider.setTooltipText("return x + '$'"); 
```

或者简单地：

```java
// 被解释为：return x + ' units';
slider.setTooltipText("x + ' units'"); 
```

## 样式 {#styling}

### 主题 {#themes}

`Slider`提供六种内置主题，可快速进行样式设置，无需使用CSS。通过内置的枚举类支持主题设置。
以下是应用每个支持主题的滑块示例：

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
