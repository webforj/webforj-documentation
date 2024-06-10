---
title: ProgressBar
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" target="_blank" clickable={false} iconName='shadow' />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-progress-bar" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar is component that visually displays the progress of some task. As the task progresses towards completion, the progress bar displays the task's percentage of completion. This percentage is represented visually by a rectangle which starts out empty and gradually becomes filled in as the task progresses. In addition, the progress bar can display a textual representation of this percentage.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.progressbar.ProgressBarBasic' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/progressbar/ProgressBarBasic.java'
height='150px'
/>

## Usages

The `ProgressBar` component is useful for visualizing the completion status of tasks. It supports:

- Configurable minimum and maximum values.
- Indeterminate mode for ongoing tasks without a definite end.
- Options for text visibility, animation, and striped designs for better visual feedback.

## Setting values

The ProgressBar component allows setting and getting its current value, minimum, and maximum limits.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation

The `ProgressBar` can be oriented horizontally or vertically.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.progressbar.ProgressBarOrientation' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/progressbar/ProgressBarOrientation.java'
height='175px'
/>

## Indeterminate state

The `ProgressBar` supports an indeterminate state for tasks with unknown completion time.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.progressbar.ProgressBarDeterminate' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/progressbar/ProgressBarDeterminate.java'
height='25px'
/>

## Text and text visibility

By default when created, the progress bar displays the percentage complete in the format `XX%`. Using the `setText()` method, you can use the placeholder `{{x}}` to get the current value as a percentage. Additionally, you can use the placeholder 
`{{value}}` to get the raw current value.

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling

### Themes

The `ProgressBar` component comes with <JavadocLink type="foundation" location="com/webforj/component/Theme"> themes </JavadocLink> built in for quick styling without the use of CSS. These themes are pre-defined styles that can be applied to buttons to change their appearance and visual presentation. 
They offer a quick and consistent way to customize the look of ProgressBars throughout an app. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.progressbar.ProgressBarThemes' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/progressbar/ProgressBarThemes.java'
height='320px'
/>

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ProgressBar} table='parts' />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ProgressBar} table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ProgressBar} table="reflects" />


## Best practices 

- **Use Appropriate Minimum and Maximum Values**: Set the minimum and maximum values to accurately reflect the task's range.
- **Update Progress Regularly**: Continuously update the progress value to provide real-time feedback to users.
- **Utilize Indeterminate State for Unknown Durations**: Use the indeterminate state for tasks with unpredictable durations to indicate ongoing progress.
- **Show Text for Better User Feedback**: Display text on the progress bar to offer additional context about the task's progress.


