---
title: Slider
sidebar_position: 101
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

The `Slider` component in webforJ provides an interactive control that allows users to select a value within a specific range by moving a knob. This feature is particularly useful for apps requiring precise or intuitive input, such as selecting volumes, percentages, or other adjustable values.

## Basics

The `Slider` is designed to work right out of the box, requiring no additional setup to function effectively. By default, it spans a range from 0 to 100 with a starting value of 50, making it ideal for quick integration into any app. For more specific use cases, the `Slider` can be customized with properties such as orientation, tick marks, labels, and tooltips.

Here’s an example of a `Slider` that allows users to adjust volume levels within a predefined range:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` value

The `Slider` value represents the current position of the knob on the slider and is defined as an integer within the `Slider`'s range. This value dynamically updates as the user interacts with the slider, making it an essential property for tracking user input.

:::tip Default value
By default, the `Slider` starts with a value of 50, assuming the default range of 0 to 100.
:::

### Setting and getting the value

You can set the `Slider`'s value during initialization or update it later using the `setValue()` method. To retrieve the current value, use the `getValue()` method.

```java
Slider slider = new Slider();  
slider.setValue(25); // Sets the slider to 25

Integer value = slider.getValue();  
System.out.println("Current Slider Value: " + value);
```

## Minimum and maximum values

The minimum and maximum values define the allowable range of the `Slider`, determining the boundaries within which the `Slider` knob can move. By default, the range is set from 0 to 100, but you can customize these values to suit your needs.

The intervals on the `Slider` have a default step of 1, meaning the number of intervals is determined by the range. For example:
- A Slider with a range of 0 to 10 will have 10 intervals.
- A Slider with a range of 0 to 100 will have 100 intervals.

These intervals are evenly distributed along the slider track, with their spacing depending on the `Slider`’s dimensions.

Below is an example of creating a `Slider` with a custom range:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick configuration

The `Slider` component offers flexible tick configuration, allowing you to customize how tick marks are displayed and how the slider knob interacts with them. This includes adjusting major and minor tick spacing, showing/hiding tick marks, and enabling snapping to tick marks for precise user input.

### Major and minor tick spacing

You can define the spacing for major and minor tick marks, which determines how frequently they appear on the `Slider` track:

- Major ticks are larger and often labeled to represent key values.
- Minor ticks are smaller and appear between major ticks to offer finer intervals.

Set the tick spacing using the following `setMajorTickSpacing()` and `setMinorTickSpacing()` methods:
```java
slider.setMajorTickSpacing(10); // Major ticks every 10 units
slider.setMinorTickSpacing(2);  // Minor ticks every 2 units
```

### Show or hide ticks

You can toggle the visibility of tick marks using the `setTicksVisible()` method. By default, tick marks are hidden.

```java
slider.setTicksVisible(true); // Show tick marks
slider.setTicksVisible(false); // Hide tick marks
```

### Snapping

To ensure the `Slider` knob aligns with the nearest tick mark during user interaction, enable snapping using the `setSnapToTicks()` method:

```java
slider.setSnapToTicks(true); // Enable snapping
```

Here’s an example of a fully configured `Slider` showing major and minor tick settings along with the snapping capability for precise adjustments:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientation and inversion

The `Slider` component supports two orientations: horizontal (default) and vertical. You can change the orientation to suit your UI layout and app requirements.

In addition to orientation, the `Slider` can also be inverted. By default:

- A horizontal `Slider` goes from minimum (left) to maximum (right).
- A vertical `Slider` goes from minimum (bottom) to maximum (top).

When inverted, this direction is reversed. Use the `setInverted(true)` method to enable inversion.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Labels

The `Slider` component supports labels on tick marks to help users interpret the values more easily. You can use default numeric labels or provide custom ones, and you can toggle their visibility as needed.

### Default labels

By default, the slider can display numeric labels at major tick marks. These values are determined by the `setMajorTickSpacing()` setting. To enable default labels, use:

```java
slider.setLabelsVisible(true);
```

### Custom labels

You can replace the default numeric labels with custom text using the `setLabels()` method. This is helpful when you want to display more meaningful values (e.g., temperature, currency, or categories).

```java
Map<Integer, String> customLabels = Map.of(
    0, "Cold",
    30, "Cool",
    50, "Moderate",
    80, "Warm",
    100, "Hot"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Toggling label visibility

Whether you're using default or custom labels, you can control their visibility with `setLabelsVisible(true)` or hide them with `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips

Tooltips enhance usability by displaying the `Slider`’s value directly above or under the knob, helping users make more precise adjustments. You can configure the tooltip’s behavior, visibility, and format to suit your needs.

To enable tooltips, use the `setTooltipVisible()` method. By default, tooltips are disabled:

```java
slider.setTooltipVisible(true); // Enable tooltips
slider.setTooltipVisible(false); // Disable tooltips
```

Tooltips can also be configured to appear only when the user interacts with the `Slider`. Use the `setTooltipVisibleOnSlideOnly()` method to enable this behavior. This is especially useful for reducing visual clutter while still providing helpful feedback during interaction.

Here’s an example of a fully configured `Slider` with tooltips:


### Tooltip customization

By default, the `Slider` shows a tooltip with its current value. If you want to customize this text, use the `setTooltipText()` method. This is useful when you want the tooltip to show static or descriptive text instead of the live value.

You can also use a JavaScript expression to format the tooltip dynamically. If your expression includes the `return` keyword, it's used as-is. If not, it's automatically wrapped with `return` and `;` to form a valid function. For example:

```java
// Shows value followed by a dollar sign
slider.setTooltipText("return x + '$'"); 
```

Or simply:

```java
// Interpreted as: return x + ' units';
slider.setTooltipText("x + ' units'"); 
```


## Styling

### Themes

The `Slider` comes with 6 themes built in for quick styling without the use of CSS. Theming is supported by use of a built-in enum class.
Shown below are sliders with each of the supported Themes applied:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />