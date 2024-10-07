---
title: Spinner
draft: true
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-spinner" clickable={false} iconName='code'/>


<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

A `Spinner` is a UI component used to indicate the progress of an indeterminate operation. When a process takes time to complete but the exact duration is unknown (such as loading content or submitting a form), a spinner provides a visual cue to users that something is in progress. 

webforJ's `Spinner` and its variants are highly customizable, allowing developers to customize it in a way that best suits the needs of the app being created.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.spinnerdemos.SpinnerDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/spinnerdemos/SpinnerDemo.java'
height = '225px'
/>

:::info Defaults
The `Spinner` component will have `Theme.DEFAULT` and `SpinnerExpanse.NONE` applied when the default constructor is used.
:::

## Controlling the `Spinner`

The `Spinner` component allows you to control the speed at which it spins as well as pausing and resuming rotation. 

### Speed

You can adjust the speed of the spinner's rotation using the `setSpeed()` method. The speed is defined in milliseconds needed to complete a full rotation, where a smaller value means a faster rotation. 

```java
spinner.setSpeed(500);  // Sets the spinner to rotate every 500 milliseconds
```

In this example, the spinner completes a rotation every 500 milliseconds. You can increase this value for a slower rotation or decrease it for a faster one.

:::Default
By default, the `Spinner` will take 1000 milliseconds to complete one full rotation.
:::

### Pausing and resuming

To pause the spinner, use the `setPaused()` method. This stops the rotation of the `Spinner`. This would commonly be done before hiding the `Spinner` from view, but does not in and of itself hide the `Spinner` component.

```java
spinner.setPaused(true);  // Pauses the spinner
```

To resume the spinning animation, simply pass `false` to the `setPaused()` method.

```java
spinner.setPaused(false);  // Resumes the spinner
```

## Rotation direction

