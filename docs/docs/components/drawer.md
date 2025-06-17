---
title: Drawer
sidebar_position: 35
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

The `Drawer` component creates a sliding panel that appears from the edge of the screen to show additional content or options. When you have multiple drawers open, they stack on top of each other automatically.

`Drawers` are handy for different interface needs: toggleable navigation menus, panels with extra information, or mobile-friendly layouts. The example below shows a mobile app using the webforJ [`AppLayout`](../components/app-layout) component, with a welcome popup `Drawer` at the bottom and a navigation `Drawer` that opens from the hamburger menu.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerWelcome.css'
/>

## Usages

1. **Navigation Menu**: One common usage of a `Drawer` component is as a navigation menu. It provides a space-efficient way to display links to various sections or pages of your app, especially in mobile or responsive layouts. Users can open and close the `Drawer` to access navigation options without cluttering the main content area.

2. **Filter and Sidebar**: A `Drawer` can be used as a filter or sidebar in applications that display a list of items. Users can expand the `Drawer` to reveal filter options, sort controls, or additional information related to the list items. This keeps the main content focused on the list while providing advanced features in an accessible way.

3. **User Profile or Settings**: You can use a `Drawer` to show user profile information or app settings. This keeps such information easily accessible but hidden when not needed, maintaining a clean and uncluttered interface. Users can open the `Drawer` to update their profiles or adjust settings.

4. **Notifications**: For applications with notifications or alerts, a `Drawer` can slide in to display new messages or updates. Users can quickly check and dismiss notifications without leaving their current view.

<ComponentDemo
path='/webforj/drawer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawer.css'
height='600px'
/>

## Customization

Various properties exist that allow for the customization of various attributes of the `Drawer` component. This section outlines those properties with examples for their modification.

### Autofocus

The Auto-Focus property is designed to enhance accessibility and usability by automatically focusing on the first item within a `Drawer` when it's opened. This feature eliminates the need for users to manually navigate to the desired item, saving time and effort.

When the `Drawer` is triggered to open, either through an event, by default or any other interaction, the user's focus is directed to the first item within the `Drawer`. This first item could be a button, a link, a menu option, or any other focusable element.

:::tip
By automatically focusing on the first item, the developer ensures that users can immediately engage with the most relevant or frequently used option without having to tab or scroll through the entire `Drawer`. This behavior streamlines the user experience and promotes efficient navigation within the UI.
:::

This property can also be particularly beneficial for individuals who rely on keyboard navigation or assistive technologies such as screen readers. It provides a clear starting point within the drawer and allows users to access the desired functionality without unnecessary manual input.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Example -->

### Label

The `setLabel()` method improves accessibility by providing a meaningful description of the `Drawer’s` content. When a label is set, assistive technologies like screen readers can announce it, helping users understand the purpose of the `Drawer` without seeing its visual contents.

This is especially useful in applications where `Drawers` contain dynamic or contextual information. A good label offers clarity without overwhelming detail.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Task Manager");
```

This label helps screen readers announce the `Drawer` as a “Task Manager,” making the interface more accessible for users with visual impairments.

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

### Placement

The Placement property of the `Drawer` component defines where the `Drawer` appears in the viewport. It accepts a range of enum values, giving developers control over both vertical and horizontal positioning to best fit the layout and user flow.

Available Placement Options:

- **TOP**: Positions the drawer at the top edge of the viewport.

- **TOP_CENTER**: Aligns the drawer horizontally centered at the top of the viewport.

- **BOTTOM**: Places the drawer at the bottom of the viewport.

- **BOTTOM_CENTER**: Horizontally centers the drawer at the bottom of the viewport.

- **LEFT**: Positions the drawer along the left edge of the viewport.

- **RIGHT**: Positions the drawer along the right edge of the viewport.


By leveraging the Placement property, developers can create intuitive and efficient user interfaces. For example, placing the `Drawer` on the left or right side allows for quick access to additional functionalities or navigation options, while top or bottom placements are well-suited for contextual information or supplementary content.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styling

<!-- <TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} /> -->

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table='parts' />

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table='properties'/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-drawer' table="dependencies"/>

## Best practices 

To ensure an optimal user experience when using the `Drawer` component, consider the following best practices:

1. **Placement**: Decide whether the drawer should slide in from the left, right, top, or bottom, based on your application's layout and user experience considerations. Consider user preferences and design conventions.

2. **Accessibility**: Pay special attention to accessibility. Ensure that users can open and close the drawer using keyboard controls and that screen readers can announce its presence and state. Provide ARIA roles and labels as necessary.

3. **Swipe Gestures**: On touch-enabled devices, support swipe gestures for opening and closing the drawer. This is an intuitive way for users to interact with it.