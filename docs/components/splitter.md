---
title: Splitter
---

<!-- vale off -->

import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<!-- vale on -->


<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-splitter" />

<JavadocLink type="foundation" location="com/webforj/component/layout/splitter/Splitter" top='true'/>


The `Splitter` component, designed to divide and resize content within your app, encapsulates two resizable components: the master and the detail components. A divider separates these components, allowing users to dynamically adjust each component's size according to their preferences.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='300px'
/>

## Min and max size 

The `Splitter` component provides methods to set minimum and maximum sizes for its panels, allowing you to control the resizing behavior of the components within the `Splitter`. When users attempt to resize panels beyond the specified min or max sizes, the splitter component enforces these constraints, ensuring that panels remain within the defined boundaries.

### Setting sizes

The `setMasterMinSize(String masterMinSize)` method specifies the minimum size for the master panel of the splitter. Likewise, the `setMasterMaxSize(String masterMaxSize)` method specifies the maximum size for the master panel.

You can specify sizes using any valid CSS units, as shown below:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='300px'
/>

## Orientation

You can configure orientation in the `Splitter` component, allowing you to create layouts tailored to specific design requirements. By specifying the orientation, the component arranges panels horizontally or vertically, providing versatility in layout design.

To configure the orientation, use the supported orientations Enum to specify whether the `Splitter` should render horizontally or vertically:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='300px'
/>

## Relative position

To set the initial position of the divider bar in the `Splitter` component, use `setPositionRelative`. This method takes a numeric value from `0` to `100` representing the percentage of the given space in the `Splitter`, and displays the divider at the given percentage of total width:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='300px'
/>

## Nesting

Splitter nesting allows you to create complex layouts with levels of resizable panels. It enables the creation of sophisticated user interfaces with granular control over the arrangement and resizing of content.

To nest Splitter components, instantiate new `Splitter` instances and add them as children to existing `Splitter` components. This hierarchical structure allows for the creation of multi-level layouts with flexible resizing capabilities. The program below demonstrates this:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='300px'
/>

## Auto save

The `Splitter` component includes an AutoSave option, which saves the state of panel sizes to local storage to keep dimensions consistent between reloads.

When you set the auto-save configuration, the `Splitter` component automatically stores the state of panel sizes in the web browser's local storage. This ensures that the sizes users choose for panels persist across page reloads or browser sessions, reducing the need for manual adjustments.

### Cleaning the state

To programmatically revert the `Splitter` back to default settings and dimensions, call the `cleanState()` method to remove any saved state data related to the `Splitter` component from the local storage of the web browser.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/splitter/SplitterBox.java',]}
height='400px'
/>

In the preceding demo, each Splitter instance activates the AutoSave feature by calling the `setAutosave` method. This ensures that panel sizes are automatically saved to local storage. Thus, when reloading the browser, the sizes of these splitters remain the same.

Clicking the "Clear State" button calls the `cleanState()` method and refreshes the browser window to display the original dimensions.

## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, necessary for styling via CSS.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Splitter} table='parts'  />
<!-- vale on -->

### Slots

These are the available slots within the `Splitter` component. These slots serve as placeholders, guiding the insertion of customized element children into the shadow tree.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Splitter} table='slots'  />
<!-- vale on -->

<!-- ### CSS properties

Here are the different CSS properties used in the component, along with a brief explanation of their purpose.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Splitter}  table='properties'/> -->


### Reflected attributes

The component's reflected attributes appear as attributes in the rendered HTML element within the DOM, allowing for styling based on these attributes.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Splitter} table="reflects" exclusions={require('@site/static/exclusions.json').splitter.reflects}/>
<!-- vale on -->

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-splitter' table="dependencies"/>

## Best practices 

To ensure an optimal user experience when using the `Splitter` component, consider the following best practices: 

- **Adjust Based on Content**: When deciding on the orientation and initial sizes of panels, consider the content's priority. For example, in a layout with a navigation sidebar and a main content area, the sidebar should typically remain narrower with a set min size for clear navigation.

- **Strategic Nesting**: Nesting splitters can create versatile layouts but can complicate the UI and impact performance. Plan your nested layouts to ensure they're intuitive and enhance user experience.

- **Remember User Preferences**: Use the AutoSave feature to remember user adjustments across sessions, enhancing the user experience. Provide an option to allow users to reset to default settings.