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

The `Drawer` component supports autofocus, which automatically sets focus on the first focusable element when the `Drawer` opens. This enhances usability by bringing attention directly to the first actionable element.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Example -->

## Label

The `setLabel()` method can provide a meaningful description of the `Drawer’s` content. When a label is set, assistive technologies like screen readers can announce it, helping users understand the purpose of the `Drawer` without seeing its visual contents.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Task Manager");
```

:::tip Descriptive Labels
Use concise and descriptive labels that reflect the `Drawer’s` purpose. Avoid generic terms like “Menu” or “Panel” when a more specific name can be used.
:::

<!-- Example -->

<!-- ### Size

The `size` property of the `Drawer` component enables developers to control and specify the dimensions of the drawer within the user interface. This property allows for fine-tuning the size of the drawer, ensuring it aligns with the desired layout and design requirements.

When utilizing the `size` property, developers have the flexibility to define the width and height of the drawer based on their specific needs. Unlike the `maxSize` property, which sets a maximum limit, the `size` property provides explicit control over the actual size of the drawer.

Developers can customize the `size` property based on the available screen real estate, the amount of content to be displayed, and the overall design aesthetic. This level of control allows for creating visually balanced and functional interfaces.

The `size` property can be defined using various units such as pixels, percentages, or other appropriate CSS measurement values. This versatility ensures that the drawer's size can be adjusted precisely to fit different screen sizes, resolutions, and device types.

By utilizing the `size` property effectively, developers can create responsive interfaces that adapt to different viewports and screen orientations. For instance, a smaller size can be chosen for mobile devices to optimize space utilization, while larger sizes can be used for desktop displays to take advantage of the available screen area.


### Max Size

The Drawer max size property is a versatile feature designed to control the maximum width or height of a drawer within a user interface, based on the specified placement. This property allows developers to define the maximum size of the drawer, ensuring optimal presentation and layout while accommodating varying screen sizes and device resolutions.

:::info
To set the size of the drawer, modify the `size` property - `maxSize` is used to ensure a drawer never grows larger than a certain value.
:::

When utilizing the Drawer max size property, developers can set a maximum size value expressed as pixels, percentages, or other appropriate CSS measurement values. This value represents the maximum width when the drawer is placed on the left or right side of the interface or the maximum height when placed on the top or bottom.

By defining a maximum size for the drawer, developers maintain control over its dimensions and prevent it from becoming excessively wide or tall, which could hinder the overall user experience. The CSS measurement approach allows for responsiveness, adapting the size of the drawer dynamically in relation to the available screen space.

The Drawer's max size property is particularly beneficial when dealing with responsive and adaptive designs. It ensures that the drawer remains visually pleasing and functional across different devices, screen orientations, and viewports.

When the drawer's content exceeds the defined maximum size, developers can implement appropriate techniques to handle overflow, such as scrolling within the drawer or utilizing additional UI patterns like tabs or accordions. This helps maintain a clean and organized interface while accommodating larger amounts of content. -->

## Placement

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

You can attach listeners to these events to run logic when the `Drawer’s` state changes:

<ComponentDemo
path='/webforj/drawerevent?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerEventView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawer.css'
height='600px'
/>

## Example: Contact picker

The `Drawer` component exposes additional content without disrupting the current view. This example places a drawer at the bottom center, containing a scrollable contact list.

Each contact displays an avatar, name, location, and action button for quick access to details or communication. This approach works well for building compact tools like contact pickers, settings panels, or notifications.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>


## Styling

<TableBuilder name="Drawer" />

## Best practices 

To ensure an optimal user experience when using the `Drawer` component, consider the following best practices:

1. **Placement**: Decide whether the `Drawer` should slide in from the left, right, top, or bottom, based on your app's layout and user experience considerations. Consider user preferences and design conventions.

2. **Accessibility**: Pay special attention to accessibility. Ensure that users can open and close the `Drawer` using keyboard controls and that screen readers can announce its presence and state. Provide ARIA (Accessible Rich Internet Applications) roles and labels as necessary.

3. **Swipe Gestures**: On tap-enabled devices, support swipe gestures for opening and closing the `Drawer`. This is an intuitive way for users to interact with it.