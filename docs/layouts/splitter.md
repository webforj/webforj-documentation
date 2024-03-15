---
sidebar_position: 20
title: Splitter
---

import ComponentDemoMultiple from '@site/src/components/DocsTools/ComponentDemoMultiple';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';


The `Splitter` component provides a flexible way to divide and resize content within your application. It is designed to encapsulate two resizable components, known as the master and detail components. These components are separated by a divider, which allows users to dynamically adjust the size of each component according to their preferences.

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterBasic?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBasic.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

## Constructors

The `Splitter` component has the following parameterized constructors, in addition to the default constructor:

`public Splitter(String id, Component master, Component detail)`: This constructor creates a `Splitter` instance with a specified ID, master component, and detail component.

`public Splitter(Component master, Component detail)`: This constructor is a shorthand version of the previous one, creating a `Splitter` without specifying an ID.

`public Splitter(String id)`: This constructor allows you to create a `Splitter` instance with only a specified ID. 


## Minimum and Maximum Size 

The `Splitter` component provides methods to set minimum and maximum sizes for its panels, allowing developers to control the resizing behavior of the components within the `Splitter`. When users attempt to resize panels beyond the specified minimum or maximum sizes, the splitter component will enforce these constraints, ensuring that panels remain within the defined boundaries.


### Setting Sizes

The `setMasterMinSize(String masterMinSize)` method is used to specify the minimum size for the master panel of the splitter. Similarly, the `setDetailMinSize(String detailMinSize)` method is available for setting the minimum size of the detail panel.

Likewise, the `setMasterMaxSize(String masterMaxSize)` method allows developers to define the maximum size for the master panel, while the `setDetailMaxSize(String detailMaxSize)` method sets the maximum size for the detail panel.

Sizes can be specified using various units such as pixels (px), percentages (%), or other valid CSS units, as shown below:

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterMinMax?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterMinMax.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

## Orientation

The `Splitter` component offers the flexibility to configure its orientation, allowing developers to create layouts tailored to specific design requirements. By specifying the orientation, developers can control whether the resizable panels are arranged horizontally or vertically, providing versatility in layout design.

To configure the orientation use the provided Enum class to specify whether the `Splitter` should render horizontally or vertically:

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterOrientation?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterOrientation.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

## Relative Position

To set the initial position of the divider bar in the `Splitter` component, the `setPositionRelative()` can be used. This method takes a numeric value from 0 to 100 representing the percentage of the given space in the `Splitter`, and will divider at the given percentage of total width:

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterPosition?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterPosition.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

## Nesting

The `Splitter` component supports nesting, allowing developers to create complex layouts with multiple levels of resizable panels. Nesting `Splitter` components enables the creation of sophisticated user interfaces with granular control over the arrangement and resizing of content.

To nest Splitter components, simply instantiate new `Splitter` instances and add them as children to existing `Splitter` components. This hierarchical structure allows for the creation of multi-level layouts with flexible resizing capabilities. This is demonstrated in the program below:

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterNested?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterNested.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

## AutoSave

The `Splitter` component includes an AutoSave feature, which saves the state of panel sizes to local storage to keep dimensions consistent between reloads.

When AutoSave is activated, the `Splitter` component automatically stores the state of panel sizes in the local storage of the web browser. This means that when users resize panels, their chosen sizes are persistently saved across page reloads or browser sessions. This feature reduces the need for manual adjustments.

### Cleaning the state

In order to programmatically remove the saved state to revert the splitter back to default settings/dimensions, the `cleanState()` method can be called to remove any saved state data related to the `Splitter` component from the local storage of the web browser.

<ComponentDemoMultiple 
path='https://demo.webforj.com/webapp/controlsamples/SplitterAutoSave?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterAutoSave.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='600px'
/>

In the above demo, the `setAutosave(true)` method is called on each `Splitter` instance to enable the AutoSave feature. This ensures that the state of panel sizes is automatically saved to local storage. When reloading the browser, the size of these splitters remains constant. 

Clicking the "Clear State" button will then call the `cleanState()` method and refreshes the browser window to display the original dimensions.

## Resizing Event

The `SplitterResizeEvent` provides a mechanism for developers to respond to changes in the size of a `Splitter` component. By handling resize events, developers can implement custom logic to adjust the layout or update UI elements based on changes in `Splitter` dimensions. 

### Listener Registration

To respond to splitter resize events, developers need to register event listeners that are triggered whenever the splitter's size changes. This can be achieved by adding an event listener to the splitter component.

```java
Splitter splitter = new Splitter();
splitter.addResizeListener(e -> {
    // Handle splitter resize event
    // Perform actions such as adjusting layout or updating UI elements
});
```

## Styling

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Splitter} table='parts'  />

### Slots

Listed below are the slots available for utilization within the `Button` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} table='slots'  />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button}  table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} table="reflects" />

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-splitter' table="dependencies"/>