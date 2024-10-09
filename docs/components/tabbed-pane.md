---
title: TabbedPane
slug: tabbedpane
---

import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-tabbed-pane" clickable={false} iconName='code'/>


<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

The `TabbedPane` class provides a compact and organized way of displaying content that is divided into multiple sections, each associated with a `Tab`. Users can switch between these sections by clicking on the respective tabs, often labeled with text and/or icons. This class simplifies the creation of multifaceted interfaces where different content or forms need to be accessible but not simultaneously visible.

## Usages

The `TabbedPane` class is a gives developers a powerful tool for organizing and presenting multiple tabs or sections within a UI. Here are some typical scenarios where you might utilize a `TabbedPane` in your application:

1. **Document Viewer**: Implementing a document viewer where each tab represents a different document or file. Users can easily switch between open documents for efficient multitasking.

2. **Data Management:**: Utilize a `TabbedPane` to organize data management tasks, for instance:
    >- Different dataset to be displayed in an application
    >- Various user profiles can be displayed within separate tabs
    >- Different profiles in a user management system

3. **Module Selection**: A `TabbedPane` can represent different modules or sections. Each tab can encapsulate the functionalities of a specific module, enabling users to focus on one aspect of the application at a time.

4. **Task Management**: Task management applications can use a `TabbedPane` to represent various projects or tasks. Each tab could correspond to a specific project, allowing users to manage and track tasks separately.

5. **Program Navigation**: Within an application that needs to run various programs, a `TabbedPane` could:
    >- Serve as a sidebar which allows for different applications or programs to be run within a single application, such as what is shown in the [`AppLayout`](./app-layout.md) template
    >- Create a top bar which can serve a similar purpose, or represent sub-applications within an already selected application.
  
## Tabs

Tabs are UI elements that can be added to tabbed panes to organize and switch between different content views.

:::important
Tabs are not intended to be used as standalone components. They are meant to be used in conjunction with tabbed panes. This class is not a `Component` and should not be used as such.
:::

### Properties

Tabs are comprised of the following properties, which are then used when adding them in a `TabbedPane`. These properties have getters and setters to facilitate customization within a `TabbedPane`.

1. **Key(`Object`)**: Represents the unique identifier for the `Tab`.

2. **Text(`String`)**: The text that will be displayed as a title for the `Tab` within the `TabbedPane`. This is also referred to as the title via the `getTitle()` and `setTitle(String title)` methods.

3. **Tooltip(`String`)**: The tooltip text that is associated with the `Tab`, which will be displayed when the cursor hovers over the `Tab`.

4. **Enabled(`boolean`)**: Represents whether the `Tab` is currently enabled or not. Can be modified with the `setEnabled(boolean enabled)` method.

5. **Closeable(`boolean`)**: Represents whether the `Tab` can be closed. Can be modified with the `setCloseable(boolean enabled)` method. This will add a close button on the `Tab` which can be clicked on by the user, and fires a removal event. The     `TabbedPane` component dictates how to handle the removal.

6. **Slot(`Component`)**: 
    Slots provide flexible options for improving the capability of a `Tab`. You can have icons, labels, loading spinners, clear/reset capability, avatar/profile pictures, and other beneficial components nested within a `Tab` to further clarify intended meaning to users.
    You can add a component to the `prefix` slot of a `Tab` during construction. Alternatively, you can use the `setPrefixComponent()` and `setSuffixComponent()` methods to insert various components before and after the displayed option within a `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` Manipulation

Various methods exist to allow developers to add, insert, remove and manipulate various properties of `Tab` elements within the `TabbedPane`.

### Adding a Tab

The `addTab()` and `add()` methods exist in different overloaded capacities to allow developers flexibility in adding new tabs to the `TabbedPane`. Adding a `Tab` will place it after any previously existing tabs.

1. **`addTab(String text)`** - Adds a `Tab` to the `TabbedPane` with the specified `String` as the text of the `Tab`.
2. **`addTab(Tab tab)`** - Adds the `Tab` provided  as a parameter to the `TabbedPane`.
3. **`addTab(String text, Component component)`** - Adds a `Tab` with the given `String` as the text of the `Tab`, and the provided `Component` displayed in the content section of the `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Adds the provided `Tab` and displays the provided `Component` in the content section of the `TabbedPane`.
5. **`add(Component... component)`** - Adds one or more `Component` instances to the `TabbedPane`, creating a discrete `Tab` for each one, with the text being set to the name of the `Component`

:::info
The `add(Component... component)` determines the name of the passed `Component` by calling the `component.getName()` on the passed argument.
:::

### Inserting a Tab

In addition to adding a `Tab` at the end of the existing tabs, it is also possible to create a new one at a designated position. To do this, multiple overloaded versions of the `insertTab()`. 

1. **`insertTab(int index, String text)`** - Inserts a `Tab` into the `TabbedPane` at the given index with the specified `String` as the text of the `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserts the `Tab` provided as a parameter to the `TabbedPane` at the specified index.
3. **`insertTab(int index, String text, Component component)`** - Inserts a `Tab` with the given `String` as the text of the `Tab`, and the provided `Component` displayed in the content section of the `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Inserts the provided `Tab` and displays the provided `Component` in the content section of the `TabbedPane`.

### Removing a Tab

To remove a single `Tab` from the `TabbedPane`, use one of the following methods:

1. **`removeTab(Tab tab)`** - Removes a `Tab` from the `TabbedPane` by passing the Tab instance to be removed.
2. **`removeTab(int index)`** - Removes a `Tab` from the `TabbedPane` by specifying the 
index of the `Tab` to be removed.

In addition to the two above methods for removal of a single `Tab`, use the **`removeAllTabs()`** method to clear the `TabbedPane` of all tabs.

:::info
The `remove()` and `removeAll()` methods do not remove tabs within the component.
:::

### Tab/Component Association

To change the `Component` to be displayed for a given `Tab`, call the `setComponentFor()` method, and pass either the instance of the `Tab`, or the index of that Tab within the `TabbedPane`.

:::info
If this method is used on a `Tab` that is already associated with a `Component`, the previously associated `Component` will be destroyed.
:::

## Configuration and Layout

The `TabbedPane` class has two constituent parts: a `Tab` that is displayed in a specified location, and a component to be displayed. This can be a single component, or a [`Composite`](../building-ui/composite-components) component, allowing for the display of more complex components within a tab's content section.

### Swiping

The `TabbedPane` supports navigating through the various tabs via swiping. This is ideal for a mobile application, but can also be configured via a built-in method to support mouse swiping. Both swiping and mouse swipping are disabled by default, but can be enabled with the `setSwipable(boolean)` and `setSwipableWithMouse(boolean)` methods, respectively. 

<!-- <AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.tabbedpanedemos.TabbedPaneSwipe&platform=mobile' mobile='true'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/tabbedpanedemos/TabbedPaneSwipe.java'
/> -->

### Tab Placement 

The `Tabs` within a `TabbedPane` can be placed in various positions within the component based on the application developers preference. Provided options are set using the provided enum, which has the values of `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, or `HIDDEN`. The default setting is `TOP`.


<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alignment

In addition to changing the placement of the `Tab` elements within the `TabbedPane`, it is also possible to configure how the tabs will align within the component. By default, the setting `AUTO` is in effect, which allows the placement of the tabs to dictate their alignment.

The other options are `START`, `END`, `CENTER`, and `STRETCH`. The first three describe the position relative to the component, with `STRETCH` making the tabs fill the available space.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Border and Activity Indicator

The `TabbedPane` will have a border displayed for the tabs within it by default, placed dependant on which `Placement` has been set. This border helps to visualize the space that the various tabs within the pane take up. 

When a `Tab` is clicked on, by default, an activity indicator is displayed near that `Tab` to help highlight which is the currently selected `Tab`.

Both of these options can be customized by a developer by changing the boolean values using the appropriate setter methods. To change whether or not the border is shown, the `setBorderless(boolean)` method can be used, with `true` hiding the border, and `false`, the default value, displaying the border.

:::info
This border doesn't apply to the entirety of the `TabbedPane` component, and merely serves as a separator between the tabs and the content of the component.
:::

To set the visibility of the active indicator, the `setHideActiveIndicator(boolean)` method can be used. Passing `true` to this method will hide the active indicator beneath an active `Tab`, whereas `false`, the default, keeps the indicator displayed.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activation Modes 

For more fine-grained control over how the `TabbedPane` behaves when being navigated by the keyboard, the `Activation` mode can be set to specify how the component should behave.

- **`Auto`**: When set to auto, navigating tabs with the arrow keys will instantly show the corresponding tab component.

- **`Manual`**: When set to manual, the tab will receive focus but will not show until the user presses space or enter.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Removal Options

Individual `Tab` elements can be set to be closable. Closable tabs will have a close button added to the tab, which fires a close event when clicked. The `TabbedPane` dictates how this behavior is handled.

- **`Manual`**: By default, removal is set to `MANUAL`, which means that the event is fired, but it is up to the developer to handle this event in whatever way they choose.

- **`Auto`**: Alternatively, `AUTO` can be used which will fire the event, and also remove the `Tab` from the component for the developer, removing the need for the developer to implement this behavior manually. 

## Styling

### Expanse and Theme

The `TabbedPane` comes with built-in `Expanse` and `Theme` options similar to other webforJ components. These can be used to quickly add styling that conveys various meaning to the end user without needing to style the component with CSS.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TabbedPane} table='parts' />

### Slots

Listed below are the slots available for utilization within the `Button` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TabbedPane} table='slots' />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TabbedPane} table='properties' />

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TabbedPane} table="reflects" />

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-tabbed-pane' table="dependencies" />

## Best Practices 

The following practices are recommended for utilizing the `TabbedPane` within applications:

- **Logical Grouping**: Use tabs to logically group related content
    >- Each tab should represent a distinct category or functionality within your application
    >- Group similar or logical tabs near one another

- **Limited Tabs**: Avoid overwhelming users with too many tabs. Consider using a hierarchical structure or other navigation patterns where applicable for a clean interface

- **Clear Labels**: Clearly label your Tabs for intuitive use
    >- Provide clear and concise labels for each tab
    >- Labels should reflect the content or purpose, making it easy for users to understand
    >- Utilize icons and distinct colors where applicable

- **Keyboard Navigation** Use webforJ's `TabbedPane` keyboard navigation support to make interaction with the `TabbedPane` more seamless and intuitive for the end user

- **Default Tab**: If the default tab is not placed at the beginning of the `TabbedPane`, consider setting this tab as default for essential or commonly used information.

