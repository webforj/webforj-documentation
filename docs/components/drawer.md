---
title: Drawer
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

The drawer is a container that slides into the viewport to expose additional options and information. Multiple drawers can be created in an application, and they will be stacked above each other.

The Drawer component can be used in many different situations, such as by providing a navigation menu that can be toggled, a panel that displays supplementary or contextual information, or to optimize usage on a mobile device. The following example will show a mobile application that uses the webforJ AppLayout component, and displays a "Welcome Popup" drawer at the bottom when first loaded. Additionally, a navigational Drawer component can be toggled in the application by clicking on the hamburger menu.

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.drawerdemos.DrawerWelcome' mobile='true'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/drawerdemos/DrawerWelcome.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/drawerstyles/drawer_welcome.css'
/>

## Usages

1. **Navigation Menu**: One common usage of a drawer component is as a navigation menu. It provides a space-efficient way to display links to various sections or pages of your application, especially in mobile or responsive layouts. Users can open and close the drawer to access navigation options without cluttering the main content area.

2. **Filter and Sidebar**: A drawer can be used as a filter or sidebar in applications that display a list of items. Users can expand the drawer to reveal filter options, sort controls, or additional information related to the list items. This keeps the main content focused on the list while providing advanced features in an accessible way.

3. **User Profile or Settings**: You can use a drawer to show user profile information or application settings. This keeps such information easily accessible but hidden when not needed, maintaining a clean and uncluttered interface. Users can open the drawer to update their profiles or adjust settings.

4. **Notifications**: For applications with notifications or alerts, a drawer can slide in to display new messages or updates. Users can quickly check and dismiss notifications without leaving their current view.

## Constructors

The Drawer component contains a single default constructor which will create a new instance of the Drawer class.

```java
Drawer newDrawer = new Drawer()
```

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.drawerdemos.DrawerDemo'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/drawerdemos/DrawerDemo.java'
height='600px'
/>

Various properties exist that allow for the customization of various attributes of the Drawer component. Below are those properties with examples for their modification.

## Autofocus

The Auto-Focus property is designed to enhance accessibility and usability by automatically focusing on the first item within a drawer when it is opened. This feature eliminates the need for users to manually navigate to the desired item, saving time and effort.

When the drawer is triggered to open, either through an event, by default or any other interaction, the user's focus is directed to the first item within the drawer. This first item could be a button, a link, a menu option, or any other focusable element.

:::tip
By automatically focusing on the first item, the developer ensures that users can immediately engage with the most relevant or frequently used option without having to tab or scroll through the entire drawer. This behavior streamlines the user experience and promotes efficient navigation within the UI.
:::

This property can also be particularly beneficial for individuals who rely on keyboard navigation or assistive technologies such as screen readers. It provides a clear starting point within the drawer and allows users to access the desired functionality without unnecessary manual input.

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.drawerdemos.DrawerAutoFocus'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/drawerdemos/DrawerAutoFocus.java'
height='600px'
/>

<!-- Example -->

## Label

The Drawer Label property is a feature designed to enhance accessibility and provide descriptive context for a drawer within a user interface. This property allows developers to assign a label to a drawer, primarily for accessibility purposes, ensuring that screen readers and other assistive technologies can accurately convey the drawer's purpose and content to users.

When the Drawer Label property is utilized, the assigned label becomes an integral part of the drawer's accessibility infrastructure. It enables users who rely on assistive technologies to understand the drawer's function and navigate through the interface more effectively.

By providing a label for the drawer, developers ensure that screen readers announce the purpose of the drawer to visually impaired users. This information empowers individuals to make informed decisions about interacting with the drawer, as they can understand its content and relevance within the broader user interface.

The Label property can be customized to suit the specific context and design requirements of the application. Developers have the flexibility to provide concise and descriptive labels that accurately represent the drawer's content or functionality.

<!-- Example -->

<!-- ## Size

The `size` property of the Drawer component enables developers to control and specify the dimensions of the drawer within the user interface. This property allows for fine-tuning the size of the drawer, ensuring it aligns with the desired layout and design requirements.

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
<!-- 
<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.drawerdemos.DrawerSize'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/drawerdemos/DrawerSize.java'
height='600px'
/> -->

## Placement

The placement property of the Drawer UI Component allows developers to specify the position and alignment of the drawer within the viewport. This property offers a range of enum values that provide flexibility in determining where the drawer appears in relation to the main content.

The available enum values for the placement property are as follows:

- **TOP**: This value places the drawer at the top of the viewport, allowing it to occupy the uppermost region.

- **TOP_CENTER**: With this value, the drawer is positioned at the center of the top portion of the viewport. It is aligned horizontally in the middle, creating a balanced layout.

- **BOTTOM**: When using this value, the drawer is situated at the bottom of the viewport, appearing below the main content.

- **BOTTOM_CENTER**: This value centers the drawer horizontally at the bottom of the viewport. It provides a visually balanced composition.

- **LEFT**: Selecting this value causes the drawer to be positioned on the left side of the viewport, adjacent to the main content.

- **RIGHT**: By using this value, the drawer is placed on the right side of the viewport, maintaining a close proximity to the main content


The placement property allows developers to choose the most appropriate position for the drawer based on the specific design and user experience requirements. The enum values offer a variety of placement options to accommodate different interface layouts and visual hierarchies.

By leveraging the placement property, developers can create intuitive and efficient user interfaces. For example, placing the drawer on the left or right side allows for quick access to additional functionalities or navigation options, while top or bottom placements are well-suited for contextual information or supplementary content.

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.drawerdemos.DrawerPlacement'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/drawerdemos/DrawerPlacement.java'
height='600px'
/>

## Styling

<!-- <TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} /> -->

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table='parts' />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Drawer} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-drawer' table="dependencies"/>

## Best Practices 

To ensure an optimal user experience when using the `Drawer` component, consider the following best practices:

1. **Placement**: Decide whether the drawer should slide in from the left, right, top, or bottom, based on your application's layout and user experience considerations. Consider user preferences and design conventions.

2. **Accessibility**: Pay special attention to accessibility. Ensure that users can open and close the drawer using keyboard controls and that screen readers can announce its presence and state. Provide ARIA roles and labels as necessary.

3. **Swipe Gestures**: On touch-enabled devices, support swipe gestures for opening and closing the drawer. This is an intuitive way for users to interact with it.