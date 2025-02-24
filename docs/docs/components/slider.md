---
title: Slider
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-slider" />

<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

The `Slider` component in webforJ provides an interactive control that allows users to select a value within a specific range by moving a knob. This feature is particularly useful for apps requiring precise or intuitive input, such as selecting volumes, percentages, or other adjustable values.

## Basics

The `Slider` is designed to work right out of the box, requiring no additional setup to function effectively. By default, it spans a range from 0 to 100 with a starting value of 50, making it ideal for quick integration into any app. For more specific use cases, the `Slider` can be customized with properties such as orientation, tick marks, labels, and tooltips.

Here’s an example of a `Slider` that allows users to adjust volume levels within a predefined range:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` value

The `Slider` value represents the current position of the knob on the slider and is defined as an integer within the `Slider`'s range. This value dynamically updates as the user interacts with the slider, making it an essential property for tracking user input.

:::tip Default value
By default, the `Slider` starts with a value of 50, assuming the default range of 0 to 100.
:::

### Setting the value

You can set the `Slider`'s initial value during initialization using the constructor or update it programmatically using the `setValue()` method:

```java
Slider slider = new Slider();  
slider.setValue(25); // Sets the slider to 25  
System.out.println("Current Slider Value: " + slider.getValue());
```

### Getting the value

Retrieve the current value of the `Slider` using the `getValue()` method:

```java
Integer value = slider.getValue();
System.out.println("The slider is currently at: " + value);
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
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
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
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '300px'
/>

## Orientation

The `Slider` component supports two orientations: horizontal and vertical. By default, the `Slider` is set to a horizontal orientation, but you can easily configure it to be vertical if needed. This flexibility allows the `Slider `to adapt to various UI designs and app requirements.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '400px'
/>

It's also possible to invert a `Slider`. By default, the minimum value of a vertical `Slider` is at the bottom and the maximum value is at the top. For a horizontal slider, the minimum value is to the left and the maximum value is to the right. The orientation reverses for inverted sliders.

<ComponentDemo 
path='/webforj/sliderinversion?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderInversionView.java'
height = '150px'
/>

## Labels

webforJ's `Slider` component provides options for adding labels to tick marks, making it easier for users to understand the values represented on the slider. Labels can be customized for specific tick values or toggled on and off based on the app's needs. 

Here is an example that demonstrates how labels can be applied to different `Slider` configurations. This includes a `Slider` with no ticks, a `Slider` with labels applied to tick values, and a `Slider` with labels applied to non-tick values:

<ComponentDemo 
path='/webforj/sliderticknontick?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickNonTickView.java'
height = '150px'
/>

### Default labels

By default, the `Slider` can display numeric labels that correspond to the values of major tick marks. These labels are automatically derived from the `setMajorTickSpacing()` configuration. To show the default labels, enable them with the `setLabelsVisible()` method:


### Custom labels

You can replace the default labels with custom text using the `setLabels()` method. This is useful for providing more meaningful context for the slider values, such as labeling temperatures or dollar amounts.

```java
// Define custom labels
Map<Integer, String> customLabels = Map.of(
    0, "Cold",
    25, "Cool",
    50, "Moderate",
    75, "Warm",
    100, "Hot"
);

// Apply custom labels to the slider
slider.setLabels(customLabels)
```

### Show or hide labels

Regardless of whether you are using default or custom labels, you can toggle their visibility using the `setLabelsVisible()` method:

```java
slider.setLabelsVisible(true); // Show labels
slider.setLabelsVisible(false); // Hide labels
```

## Tooltips

Tooltips enhance usability by displaying the `Slider`’s value directly above the knob, helping users make more precise adjustments. You can configure the tooltip’s behavior, visibility, and format to suit your needs.

To enable tooltips, use the `setTooltipVisible()` method. By default, tooltips are disabled:

```java
slider.setTooltipVisible(true); // Enable tooltips
slider.setTooltipVisible(false); // Disable tooltips
```

Tooltips can also be configured to appear only when the user interacts with the `Slider`. Use the `setTooltipVisibleOnSlideOnly()` method to enable this behavior. This is especially useful for reducing visual clutter while still providing helpful feedback during interaction.

Here’s an example of a fully configured `Slider` with tooltips:

<ComponentDemo 
path='/webforj/slidertemp?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTempView.java'
height = '100px'
/>

By default, tooltips display the `Slider`’s current value. To customize the tooltip text, use the `setTooltipText()` method. This method is ideal for cases where you want the tooltip to display static or descriptive text instead of dynamic values.

```java
slider.setTooltipText("Adjust the volume"); // Set a custom static tooltip
slider.setTooltipVisible(true);
```

## Themes

The `Slider` comes with 6 themes built in for quick styling without the use of CSS. Theming is supported by use of a built-in enum class.
Shown below are sliders with each of the supported Themes applied:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '620px'
/>

|Slider Themes|
|-|
|<ul><li>```Slider.Theme.DEFAULT```</li><li>```Slider.Theme.DANGER```</li><li>```Slider.Theme.GRAY```</li><li>```Slider.Theme.INFO```</li><li>```Slider.Theme.SUCCESS```</li><li>```Slider.Theme.WARNING```</li></ul>|

## Parts and CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Slider} />