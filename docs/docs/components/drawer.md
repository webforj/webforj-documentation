---
title: Drawer
sidebar_position: 35
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

The `Drawer` component in webforJ creates a sliding panel that appears from the edge of the screen, revealing additional content without leaving the current view. It's commonly used for side navigation, filter menus, user settings, or compact notifications that need to appear temporarily without disrupting the main interface.

`Drawers` stack automatically when multiple are opened, making them a flexible choice for space-constrained interfaces.

The example below shows this behavior within the [`AppLayout`](../components/app-layout) component. The navigation drawer triggered by the hamburger menu is built into [`AppLayout`](../components/app-layout), while the welcome popup at the bottom uses a standalone `Drawer` instance. Both coexist and stack independently, demonstrating how `Drawers` can be integrated within layout components or used as standalone elements.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

The `Drawer` component supports autofocus, which automatically sets focus on the first focusable element when the `Drawer` opens. This improves usability by bringing attention directly to the first actionable element.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Example -->

## Label {#label}

The `setLabel()` method can provide a meaningful description of the content inside a `Drawer`. When a label is set, assistive technologies like screen readers can announce it, helping users understand the purpose of the `Drawer` without seeing its visual contents.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Task Manager");
```

:::tip Descriptive Labels
Use concise and descriptive labels that reflect the purpose of the `Drawer`. Avoid generic terms like “Menu” or “Panel” when a more specific name can be used.
:::

## Size

To control the size of a `Drawer`, set a value for the CSS custom property `--dwc-drawer-size`. This sets the width of the `Drawer` for left/right placement or height for top/bottom placement.

You can define the value using any valid CSS unit such as a percentage, pixels, or vw/vh, using either Java or CSS:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

To prevent the `Drawer` from growing too large, use `--dwc-drawer-max-size` alongside it:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Placement {#placement}

The `setPlacement()` method controls where the `Drawer` appears in the viewport.

Available placement options:

<!-- vale off -->
- **TOP**: Positions the drawer at the top edge of the viewport.
- **TOP_CENTER**: Aligns the drawer horizontally centered at the top of the viewport.
- **BOTTOM**: Places the drawer at the bottom of the viewport.
- **BOTTOM_CENTER**: Horizontally centers the drawer at the bottom of the viewport.
- **LEFT**: Positions the drawer along the left edge of the viewport.
- **RIGHT**: Positions the drawer along the right edge of the viewport.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Event handling

The `Drawer` component emits lifecycle events that can be used to trigger app logic in response to changes in its open or closed state. 

Supported events:

- `DrawerOpenEvent`: Fired when the drawer is fully opened.
- `DrawerCloseEvent`: Fired when the drawer is fully closed.

You can attach listeners to these events to run logic when the state of the `Drawer` changes.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Handle drawer opened event
});

drawer.addCloseListener(e -> {
  // Handle drawer closed event
});
```

## Example: Contact picker

The `Drawer` component exposes additional content without disrupting the current view. This example places a drawer at the bottom center, containing a scrollable contact list.

Each contact displays an avatar, name, location, and action button for quick access to details or communication. This approach works well for building compact tools like contact pickers, settings panels, or notifications.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Example: Task manager

This example uses a `Drawer` as a task manager. You can add tasks, check them off, and clear completed ones. The `Drawer` footer includes form controls to interact with the task list, and the “Add Task” [`Button`](../components/button) disables itself if 50 tasks are reached.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />