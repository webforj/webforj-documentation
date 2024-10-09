---
title: Slider
---

<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

### Upper and Lower Limit

Use the `setMinimum()` and `setMaximum()` methods to determine the upper and lower bound of the slider. Each interval on the slider has a distance of 1, meaning that a slider with maximum 10 and minimum of 0 will have 10 intervals on the slider, whereas a maximum of 100 and minimum of 0 will have 100 intervals. These intervals will evenly distribute on the slider, depending on the height and width dimensions that it has been given by the developer. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/slidermaxmindemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderMaxMinDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/MinMax.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/minmax_styles.css' 
javaHighlight='{17,21}'
height = '225px'
/>

<br />

### Ticks and Labeling

Slider components can be customized to show ticks, or spaces bounded by lines to represent distances. Major ticks will be larger and more noticeable than minor ticks, and both can be configured to appear at the desired intervals. It is also possible to set the slider to snap to the tick values using the `setSnapToTicks()` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/sliderlabeldemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/Label.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/label_styles.css' 
javaHighlight='{62-78}'
height = '325px'
/>


<br />

It is also possible to set labels on a slider. These labs may or may not correspond with ticks that have been set. To set the labels, simply create a map with key value pairs, where the keys are the integer values to be labeled, and the values are the desired label string. 

:::info
If tick snapping is enabled, the slider will only snap to ticked spaces specifically. If a non-ticked value has a label, it will not be snapped to by the slider.
:::
<br />

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/slidertickdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/Ticks.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/tick_styles.css' 
javaHighlight='{24-31}'
height = '225px'
/>

<br />

### Orientation and Inversion

Sliders can be oriented either horizontally or vertically by using the `setOrientation()` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/sliderorientationdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/Orientation.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/orientation_styles.css' 
javaHighlight='{37}'
height = '400px'
/>

<br />

It is also possible to invert a slider. By default, the minimum value of a vertical slider is at the bottom and the maximum value is at the top. For a horizontal slider, the minimum value is to the left and the maximum value is to the right. The orientation reverses for inverted sliders.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/sliderinversiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderInversionDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/Inversion.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/inversion_styles.css' 
javaHighlight='{42}'
height = '300px'
/>

<br />

### Themes

The slider component comes with 6 themes built in for quick styling without the use of CSS. Theming is supported by use of a built-in enum class.
Shown below are sliders with each of the supported Themes applied: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/sliderthemesdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesDemoView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/slider/Themes.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/sliderstyles/theme_styles.css' 
javaHighlight='{20,25,30,35,40,45}'
height = '620px'
/>

<br/>

|Slider Themes|
|-|
|<ul><li>```Slider.Theme.DEFAULT```</li><li>```Slider.Theme.DANGER```</li><li>```Slider.Theme.GRAY```</li><li>```Slider.Theme.INFO```</li><li>```Slider.Theme.SUCCESS```</li><li>```Slider.Theme.WARNING```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Slider} />