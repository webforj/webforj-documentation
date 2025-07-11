---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: updated-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

The `TabbedPane` class provides an organized way of displaying content that's divided into multiple sections, each associated with a `Tab`. Users can switch between them by clicking labeled headers. It’s ideal when you want multiple panels accessible in one place, but only need to show one at a time.

<ComponentDemo 
path='/webforj/tabbedpane?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneView.java'
height="400px"
/>
  
## Tabs {#tabs}

`Tabs` are the clickable headers that organize and control content switching in a `TabbedPane`. Each `Tab` represents a different section of content that users can access.

:::important
`Tabs` aren't intended to be used as standalone components. They're meant to be used in conjunction with `TabbedPanes`. This class isn't a `Component` and shouldn't be used as such.
:::

### Properties {#properties}

`Tabs` are comprised of the following properties, which are then used when adding them in a `TabbedPane`. These properties have getters and setters to facilitate customization within a `TabbedPane`.

1. **Key(`Object`)**: Represents the unique identifier for the `Tab`.

2. **Text(`String`)**: The text that will be displayed as a title for the `Tab` within the `TabbedPane`. This is also referred to as the title via the `getTitle()` and `setTitle(String title)` methods.

3. **Tooltip(`String`)**: The tooltip text that is associated with the `Tab`, which will be displayed when the cursor hovers over the `Tab`.

4. **Enabled(`boolean`)**: Represents whether the `Tab` is currently enabled or not. Can be modified with the `setEnabled(boolean enabled)` method.

5. **Closeable(`boolean`)**: Represents whether the `Tab` can be closed. Can be modified with the `setCloseable(boolean enabled)` method. This will add a close button on the `Tab` which can be clicked on by the user, and fires a removal event. The     `TabbedPane` component dictates how to handle the removal.

6. **Slot(`Component`)**: 
    Slots let you add extra elements to a `Tab` like icons, labels, spinners, avatars, or even reset buttons. These additions help convey meaning more clearly and give you room to customize the `Tab’s` appearance and behavior.
    
    You can add a component to the `prefix` slot of a `Tab` during construction. Alternatively, you can use the `setPrefixComponent()` and `setSuffixComponent()` methods to insert various components before and after the displayed option within a `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulation {#tab-manipulation}

Various methods exist to allow developers to add, insert, remove and manipulate various properties of `Tab` elements within the `TabbedPane`.

### Adding a `Tab` {#adding-a-tab}

The `TabbedPane` offers multiple overloaded versions of `addTab()` and `add()` to give you flexibility when adding `Tabs`. Each new `Tab` is added after the existing ones.

1. **`addTab(String text)`** - Adds a `Tab` to the `TabbedPane` with the specified `String` as the text of the `Tab`.
2. **`addTab(Tab tab)`** - Adds the `Tab` provided  as a parameter to the `TabbedPane`.
3. **`addTab(String text, Component component)`** - Adds a `Tab` with the given `String` as the text of the `Tab`, and the provided `Component` displayed in the content section of the `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Adds the provided `Tab` and displays the provided `Component` in the content section of the `TabbedPane`.
5. **`add(Component... component)`** - Adds one or more `Component` instances to the `TabbedPane`, creating a discrete `Tab` for each one, with the text being set to the name of the `Component`

:::info
The `add(Component... component)` determines the name of the passed `Component` by calling the `component.getName()` on the passed argument.
:::

### Inserting a `Tab` {#inserting-a-tab}

In addition to appending `Tabs` to the end, the `TabbedPane` also supports inserting `Tabs` at specific positions. This is achieved through the various overloaded forms of the `insertTab()` method.

1. **`insertTab(int index, String text)`** - Inserts a `Tab` into the `TabbedPane` at the given index with the specified `String` as the text of the `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserts the `Tab` provided as a parameter to the `TabbedPane` at the specified index.
3. **`insertTab(int index, String text, Component component)`** - Inserts a `Tab` with the given `String` as the text of the `Tab`, and the provided `Component` displayed in the content section of the `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Inserts the provided `Tab` and displays the provided `Component` in the content section of the `TabbedPane`.

### Removing a `Tab` {#removing-a-tab}

To remove a single `Tab` from the `TabbedPane`, use one of the following methods:

1. **`removeTab(Tab tab)`** - Removes a `Tab` from the `TabbedPane` by passing the Tab instance to be removed.
2. **`removeTab(int index)`** - Removes a `Tab` from the `TabbedPane` by specifying the 
index of the `Tab` to be removed.

In addition to the two above methods for removal of a single `Tab`, use the **`removeAllTabs()`** method to clear the `TabbedPane` of all tabs.

:::info
The `remove()` and `removeAll()` methods do not remove tabs within the component.
:::

## Content management {#content-management}

### Associating content with `Tabs`

Each `Tab` can be associated with any webforJ component, from simple text to complex forms, charts, or even `Composite` components. This allows you to organize large interfaces into manageable, sectioned views.

There are several ways to associate content with a Tab:

- Provide both the `Tab` label and component

```java
TabbedPane pane = new TabbedPane();

// Add tabs with different types of content
pane.addTab("User Profile", new UserProfileForm());
pane.addTab("Settings", new SettingsPanel());
pane.addTab("Analytics", new AnalyticsChart());
```

- Provide a `Tab` instance and a Component

For more control (e.g., to add a prefix icon or tooltip), you can pass a `Tab` instance along with a component:

```java
Tab settingsTab = new Tab("Settings", TablerIcon.create("settings"));
pane.addTab(settingsTab, new SettingsPanel());
```

- Use `setComponentFor()`

To change or update the content associated with an existing `Tab`, use `setComponentFor()`:

```java
pane.setComponentFor(settingsTab, new UpdatedSettingsPanel());
pane.setComponentFor(1, new UpdatedSettingsPanel()); // By index
```

### Automatic content switching

The `TabbedPane` handles content visibility based on tab selection:

- Clicking a `Tab` shows the corresponding content panel
- Switching `Tabs` hides the previous content and displays the new one
- Keyboard navigation is supported after focusing the tab list. Use the `Tab` key to focus the `Tabs,` then arrow keys to navigate between them. The visible content updates automatically as you switch `Tabs`.

## `Tab` events

The `TabbedPane` fires events when users interact with `Tabs`:

- **`TabSelectEvent`** - Fired when a `Tab` is selected
- **`TabDeselectEvent`** - Fired when a `Tab` loses selection  
- **`TabCloseEvent`** - Fired when a closeable `Tab's` close button is clicked

```java
tabbedPane.onSelect(event -> {
   Tab selectedTab = event.getTab();
   // Handle when a tab becomes active
});

tabbedPane.onDeselect(event -> {
   Tab deselectedTab = event.getTab();
   // Handle when a tab loses focus
});

tabbedPane.onClose(event -> {
   Tab closedTab = event.getTab();
   // Handle close button clicks
});
```

## Configuration and layout {#configuration-and-layout}

The `TabbedPane` consists of two main areas: the **tab headers** (displayed in a specified location) and the **content body** (where the associated component for the active `Tab` is displayed). The content body can contain any webforJ component or [`Composite`](../building-ui/composite-components) component.

### Swiping {#swiping}

The `TabbedPane` supports swipe-based navigation, making it well-suited for mobile interfaces. You can also enable mouse-based swiping for desktop environments. Both features are disabled by default and can be activated using the `setSwipeable(boolean)` and `setSwipeWithMouse(boolean)` methods.

<!-- <AppLayoutViewer path='https://demo.webforj.com?class=componentdemos.tabbedpanedemos.TabbedPaneSwipe&platform=mobile' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/java/componentdemos/tabbedpanedemos/TabbedPaneSwipe.java'
/> -->

### Tab placement {#tab-placement}

The `Tabs` within a `TabbedPane` can be placed in various positions within the component based on the application developers preference. Provided options are set using the provided enum, which has the values of `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, or `HIDDEN`. The default setting is `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="300px"
/>

### Alignment {#alignment}

In addition to changing the placement of the `Tab` elements within the `TabbedPane`, it is also possible to configure how the tabs will align within the component. By default, the setting `AUTO` is in effect, which allows the placement of the tabs to dictate their alignment.

The other options are `START`, `END`, `CENTER`, and `STRETCH`. The first three describe the position relative to the component, with `STRETCH` making the tabs fill the available space.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Border and activity indicator {#border-and-activity-indicator}

By default, the `TabbedPane` displays a border around its `Tabs`, with the border’s position determined by the configured `Placement`. This visual boundary helps distinguish the `Tab` area from the content section.

When a `Tab` is selected, an activity indicator appears beneath it to highlight the active state.

Both of these features can be customized:

- Use `setBorderless(true)` to hide the tab border. The default value is false, which keeps the border visible.
- Use `setHideActiveIndicator(true)` to remove the activity indicator from the active Tab. The default value is false.

:::info
The `Tab` border applies exclusively to the area occupied by the `Tabs` themselves. It doesn't extend around the full `TabbedPane`, and serves solely to delineate the `Tabs` from the content section.
:::

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Activation modes {#activation-modes}

For more fine-grained control over how the `TabbedPane` behaves when being navigated by the keyboard, the `Activation` mode can be set to specify how the component should behave.

- **`Auto`**: When set to auto, navigating `Tabs` with the arrow keys will instantly show the corresponding tab component.

- **`Manual`**: When set to manual, the `Tab` will receive focus but won't show until the user presses `Space` or `Enter`.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Removal options {#removal-options}

Individual `Tab` elements can be set to be closable. Closable tabs will have a close button added to the `Tab`, which fires a close event when clicked. The `TabbedPane` dictates how this behavior is handled.

- **`Manual`**: By default, removal is set to `MANUAL`, which means that the event is fired, but it is up to the developer to handle this event in whatever way they choose.

- **`Auto`**: Alternatively, `AUTO` can be used which will fire the event, and also remove the `Tab` from the component for the developer, removing the need for the developer to implement this behavior manually. 

## Styling {#styling}

### Expanse and theme {#expanse-and-theme}

The `TabbedPane` comes with built-in `Expanse` and `Theme` options similar to other webforJ components. These can be used to quickly add styling that conveys various meaning to the end user without needing to style the component with CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />