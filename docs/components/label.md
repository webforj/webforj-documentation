---
sidebar_position: 50 
title: Label
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/label/Label"/>

The `Label` class is used to create a label object which shows static text. Here's an example of how to create a Label object:

```java
Label myLabel = new Label("Hello, world!");
```

### Constructors

The `Label` class has three constructors:

1. `Label()`: Creates an empty label.
2. `Label(String)`: Creates a label with the specified text.
3. `Label(String, boolean)`: Creates a label with the specified text and sets whether or not the text should be wrapped.

Here's an example of how to create a Label object using each constructor:

```java
// Creates an empty label.
Label emptyLabel = new Label();

// Creates a label with the specified text.
Label textLabel = new Label("Hello, world!");

// Creates a label with the specified text and sets whether or not the text should be wrapped.
Label wrappedLabel = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", true);

```

### HTML Injection

In addition to using the label as static text, it can also be used as an HTML tag within your code. Simply set the label's text to the desired HTML tag with the various attributes, class names, etc, and
the label will be replaced with the desired HTML element.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/label/Demo.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/text_styles.css' 
javaHighlight='{16-18}'
height="250px"
/>

### Line Wrapping

The line wrap method for a `Label` component sets whether or not the component automatically wraps the text within the label onto multiple lines when it exceeds the available width, ensuring that the entire text is visible without overflowing the container. Setting this value to false will truncate any text that falls out of the component's bounds. This helps manipulate readability and truncation of longer text content within the label. The default value for lineWrap is `true`.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelLineWrap' 
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelLineWrap.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/wrap_styles.css' 
javaHighlight='{16-18}'
height="250px"
/>

<br />

### Alignment

The `Label` class implements the TextAlignable interface, which allows you to set the text alignment of the label. The possible alignment values are Alignment.LEFT, Alignment.MIDDLE, and Alignment.RIGHT. To set the alignment, use the setTextAlignment method:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelAlignment' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelAlignment.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/label/Alignment.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/alignment_styles.css' 
height="450px"
/>


## Events

The `Label` class provides methods to add and remove event listeners for the following events:

- `MouseEnterEvent`
- `MouseExitEvent`
- `RightMouseDownEvent`

To add an event listener, use the appropriate method:

```java
myLabel.addMouseEnterListener( e -> {
  //Executed when the event fires
});
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myLabel.onMouseEnter( e -> {
    //Executed when the event fires
});
```

To remove an event listener, use the appropriate method:

```java
myLabel.removeMouseEnterListener(listener);
```

:::info
For a method to be removed via the appropriate removeListener method, the signature of the method must be saved. 
:::

## Example

Here is an example of how to use the `Label` class to create a simple UI component which allows the user to hover over the text to change the message:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelSample' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelSample.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/label/Sample.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/text_styles.css' 
javaHighlight='{43-45}'
/>

<br/>
