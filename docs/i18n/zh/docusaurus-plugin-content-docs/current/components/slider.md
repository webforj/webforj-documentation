---
title: Slider
sidebar_position: 101
_i18n_hash: 16e62c6e021597448e33a04ebfd6f201
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` 组件让用户通过在最小和最大范围之间拖动旋钮来选择数值。可以配置步长间隔、刻度标记和标签，以指导选择。

<!-- INTRO_END -->

## 基础 {#basics}

`Slider` 设计为开箱即用，无需额外设置即可有效运行。默认情况下，其范围从0到100，起始值为50，非常适合快速集成到任何应用程序中。对于更具体的用例，可以使用方向、刻度标记、标签和工具提示等属性自定义`Slider`。

以下是一个允许用户在预定义范围内调整音量水平的`Slider`示例：

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` 值 {#slider-value}

`Slider` 值表示旋钮在滑块上的当前位置，并定义为`Slider`范围内的整数。随着用户与滑块的交互，这个值会动态更新，是跟踪用户输入的重要属性。

:::tip 默认值
默认情况下，`Slider` 从值50开始，假设默认范围为0到100。
:::

### 设置和获取值 {#setting-and-getting-the-value}

您可以在初始化期间设置`Slider`的值，或使用`setValue()`方法在以后更新它。要检索当前值，请使用`getValue()`方法。

```java
Slider slider = new Slider();  
slider.setValue(25); // 将滑块设置为25

Integer value = slider.getValue();  
System.out.println("当前滑块值: " + value);
```

## 最小和最大值 {#minimum-and-maximum-values}

最小值和最大值定义了`Slider`的可允许范围，确定了`Slider`旋钮可以移动的边界。默认情况下，范围设置为0到100，但您可以根据需要自定义这些值。

`Slider`的间隔默认步长为1，这意味着间隔的数量由范围确定。例如：
- 范围为0到10的Slider将有10个间隔。
- 范围为0到100的Slider将有100个间隔。

这些间隔沿滑动轨迹均匀分布，其间距取决于`Slider`的尺寸。

以下是创建自定义范围的`Slider`示例：

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## 刻度配置 {#tick-configuration}

`Slider` 组件提供灵活的刻度配置，允许您自定义刻度标记的显示方式以及滑块旋钮与之的交互。这包括调整主要和次要刻度的间距、显示/隐藏刻度标记，并为精确的用户输入启用贴合刻度标记。

### 主要和次要刻度间距 {#major-and-minor-tick-spacing}

您可以定义主要和次要刻度标记的间距，这决定了它们在`Slider`轨迹上出现的频率：

- 主要刻度较大，通常标记关键值。
- 次要刻度较小，出现在主要刻度之间，以提供更细的间隔。

使用以下 `setMajorTickSpacing()` 和 `setMinorTickSpacing()` 方法设置刻度间距：
```java
slider.setMajorTickSpacing(10); // 每10个单位设置主要刻度
slider.setMinorTickSpacing(2);  // 每2个单位设置次要刻度
```

### 显示或隐藏刻度 {#show-or-hide-ticks}

您可以使用 `setTicksVisible()` 方法切换刻度标记的可见性。默认情况下，刻度标记是隐藏的。

```java
slider.setTicksVisible(true); // 显示刻度标记
slider.setTicksVisible(false); // 隐藏刻度标记
```

### 贴合 {#snapping}

为了确保`Slider`旋钮在用户交互期间对齐到最近的刻度标记，可以使用 `setSnapToTicks()` 方法启用贴合：

```java
slider.setSnapToTicks(true); // 启用贴合
```

以下是一个完整配置的 `Slider` 示例，展示主要和次要刻度设置以及用于精确调整的贴合功能：

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## 方向和反转 {#orientation-and-inversion}

`Slider` 组件支持两种方向：水平（默认）和垂直。您可以更改方向以适应您的用户界面布局和应用需求。

除了方向，`Slider` 还可以反转。默认情况下：

- 水平 `Slider` 从最小值（左）到最大值（右）。
- 垂直 `Slider` 从最小值（底部）到最大值（顶部）。

反转后，该方向将被反转。使用 `setInverted(true)` 方法启用反转。

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## 标签 {#labels}

`Slider` 组件支持刻度标记上的标签，以帮助用户更容易理解值。您可以使用默认的数字标签或提供自定义标签，并根据需要切换它们的可见性。

### 默认标签 {#default-labels}

默认情况下，滑块可以在主要刻度上显示数字标签。这些值是由 `setMajorTickSpacing()` 设置决定的。要启用默认标签，请使用：

```java
slider.setLabelsVisible(true);
```

### 自定义标签 {#custom-labels}

您可以使用 `setLabels()` 方法将默认数字标签替换为自定义文本。这在您希望显示更有意义的值（例如温度、货币或类别）时非常有用。

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

无论您使用默认标签还是自定义标签，都可以使用 `setLabelsVisible(true)` 控制它们的可见性，或使用 `setLabelsVisible(false)` 隐藏它们。

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## 工具提示 {#tooltips}

工具提示通过在旋钮上方或下方直接显示`Slider`的值，增强了可用性，帮助用户进行更精确的调整。您可以配置工具提示的行为、可见性和格式，以满足您的需求。

要启用工具提示，请使用 `setTooltipVisible()` 方法。默认情况下，工具提示是禁用的：

```java
slider.setTooltipVisible(true); // 启用工具提示
slider.setTooltipVisible(false); // 禁用工具提示
```

工具提示还可以配置为仅在用户与`Slider`交互时出现。使用 `setTooltipVisibleOnSlideOnly()` 方法启用此行为。这对于减少视觉杂乱而仍提供交互期间有用反馈特别有用。

以下是一个完整配置的 `Slider` 示例，带有工具提示：

### 工具提示定制 {#tooltip-customization}

默认情况下，`Slider` 显示带有当前值的工具提示。如果您想定制此文本，请使用 `setTooltipText()` 方法。如果希望工具提示显示静态或描述性文本而不是实时值，这是非常有用的。

您还可以使用JavaScript表达式动态格式化工具提示。如果您的表达式包含 `return` 关键字，它将按原样使用。如果没有，它将自动包装为 `return` 和 `;` 以形成有效的函数。例如：

```java
// 显示值后跟美元符号
slider.setTooltipText("return x + '$'"); 
```

或简单地：

```java
// 被解释为: return x + '单位';
slider.setTooltipText("x + '单位'"); 
```


## 风格 {#styling}

### 主题 {#themes}

`Slider` 自带6种内置主题，以便快速样式设计，而无需使用CSS。主题支持通过使用内置的枚举类。
以下是应用每个支持的主题的滑块示例：

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
