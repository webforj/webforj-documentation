---
sidebar_position: 90
title: ProgressBar
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/progressbar/ProgressBar"/>

### Text and String Painting

By default when created, the progress bar will display the percentage complete in the format "XX%". Using the `setText()` method, you can use the placeholder {{x}} to get the current value as a percentage. Additionally, you can use the placeholder {{value}} to get the raw current value.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.progressbardemos.ProgressbarPlaceholders' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/progressbardemos/ProgressbarPlaceholders.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/progressbar/Placeholders.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/progressbarstyles/placeholder_styles.css' 
javaHighlight='{25}'
height = '100px'
/>

<br/>

### Orientation

The DWCJ's progress bar allows the developer to chose the direction in which the bar will fill as progress is made. The options are either filling vertically from bottom to top, or horizontally from left to right. Setting the orientation <b> does not </b> automatically set the dimensions for the bar, this must be done by the developer. 

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.progressbardemos.ProgressbarOrientation' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/progressbardemos/ProgressbarOrientation.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/progressbar/Orientation.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/progressbarstyles/orientation_styles.css' 
javaHighlight='{26}'
height = '435px'
/>

<br/>

### Indeterminate

The progress bar in the DWCJ allows for indeterminate display, displaying an animation that conveys continuous progress that is not quantified. 

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.progressbardemos.ProgressbarIndeterminate' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/progressbardemos/ProgressbarIndeterminate.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/progressbar/Indeterminate.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/progressbarstyles/indeterminate_styles.css' 
javaHighlight='{21}'
height = '100px'
/>

<br/>

### Upper and Lower Limit

The progress bar can has minimum and maximum values that can be set, which will alter how the progress

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.progressbardemos.ProgressbarMinMax' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/progressbardemos/ProgressbarMinMax.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/progressbar/MinMax.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/progressbarstyles/minmax_styles.css' 
javaHighlight='{25-26}'
height = '120px'
/>

<br />

### Themes

The DWCJ progress bar comes with 6 themes built in for quick styling without the use of CSS.
Shown below are progress bars with each of the supported Themes applied: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.progressbardemos.ProgressbarThemes' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/progressbardemos/ProgressbarThemes.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/progressbar/Themes.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/progressbarstyles/theme_styles.css' 
javaHighlight='{25,30,35,40,45,50}'
height = '300px'
/>

|Progress Bar Themes|
|-|
|<ul><li>```ProgressBar.Theme.DEFAULT```</li><li>```ProgressBar.Theme.DANGER```</li><li>```ProgressBar.Theme.GRAY```</li><li>```ProgressBar.Theme.INFO```</li><li>```ProgressBar.Theme.SUCCESS```</li><li>```ProgressBar.Theme.WARNING```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').ProgressBar} />