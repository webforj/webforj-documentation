---
sidebar_position: 26 
title: Drawer
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';

The drawer is a container that slides into the viewport to expose additional options and information. Multiple drawers can be created in an application, and they will be stacked above each other.

### Constructors

The Drawer component contains a single default constructor which will create a new instance of the Drawer class.

```java
Drawer newDrawer = new Drawer()
```

<ComponentDemo
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.drawerdemos.DrawerDemo'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/drawerdemos/DrawerDemo.java'
height='600px'
/>

Various properties exist that allow for the customization of various attributes of the Drawer component. Below are those properties with examples for their modification.

### Autofocus

The Auto-Focus property is designed to enhance accessibility and usability by automatically focusing on the first item within a drawer when it is opened. This feature eliminates the need for users to manually navigate to the desired item, saving time and effort.

When the drawer is triggered to open, either through an event, by default or any other interaction, the user's focus is directed to the first item within the drawer. This first item could be a button, a link, a menu option, or any other focusable element.

:::tip
By automatically focusing on the first item, the developer ensures that users can immediately engage with the most relevant or frequently used option without having to tab or scroll through the entire drawer. This behavior streamlines the user experience and promotes efficient navigation within the UI.
:::

This property can also be particularly beneficial for individuals who rely on keyboard navigation or assistive technologies such as screen readers. It provides a clear starting point within the drawer and allows users to access the desired functionality without unnecessary manual input.

<ComponentDemo
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.drawerdemos.DrawerAutoFocus'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/drawerdemos/DrawerAutoFocus.java'
height='600px'
/>

<!-- Example -->

### Label

The Drawer Label property is a feature designed to enhance accessibility and provide descriptive context for a drawer within a user interface. This property allows developers to assign a label to a drawer, primarily for accessibility purposes, ensuring that screen readers and other assistive technologies can accurately convey the drawer's purpose and content to users.

When the Drawer Label property is utilized, the assigned label becomes an integral part of the drawer's accessibility infrastructure. It enables users who rely on assistive technologies to understand the drawer's function and navigate through the interface more effectively.

By providing a label for the drawer, developers ensure that screen readers announce the purpose of the drawer to visually impaired users. This information empowers individuals to make informed decisions about interacting with the drawer, as they can understand its content and relevance within the broader user interface.

The Label property can be customized to suit the specific context and design requirements of the application. Developers have the flexibility to provide concise and descriptive labels that accurately represent the drawer's content or functionality.

<!-- Example -->

### Size

The `size` property of the Drawer component enables developers to control and specify the dimensions of the drawer within the user interface. This property allows for fine-tuning the size of the drawer, ensuring it aligns with the desired layout and design requirements.

When utilizing the `size` property, developers have the flexibility to define the width and height of the drawer based on their specific needs. Unlike the `maxSize` property, which sets a maximum limit, the `size` property provides explicit control over the actual size of the drawer.

Developers can customize the `size` property based on the available screen real estate, the amount of content to be displayed, and the overall design aesthetic. This level of control allows for creating visually balanced and functional interfaces.

The `size` property can be defined using various units such as pixels, percentages, or other appropriate CSS measurement values. This versatility ensures that the drawer's size can be adjusted precisely to fit different screen sizes, resolutions, and device types.

By utilizing the `size` property effectively, developers can create responsive interfaces that adapt to different viewports and screen orientations. For instance, a smaller size can be chosen for mobile devices to optimize space utilization, while larger sizes can be used for desktop displays to take advantage of the available screen area.

<!-- Example: -->

### Max Size
<!-- CAN THIS USE OTHER THAN PERCENTAGES? -->

The Drawer max size property is a versatile feature designed to control the maximum width or height of a drawer within a user interface, based on the specified placement. This property allows developers to define the maximum size of the drawer, ensuring optimal presentation and layout while accommodating varying screen sizes and device resolutions.

:::info
To set the size of the drawer, modify the `size` property - `maxSize` is used to ensure a drawer never grows larger than a certain value.
:::

When utilizing the Drawer max size property, developers can set a maximum size value expressed as pixels, percentages, or other appropriate CSS measurement values. This value represents the maximum width when the drawer is placed on the left or right side of the interface or the maximum height when placed on the top or bottom.

By defining a maximum size for the drawer, developers maintain control over its dimensions and prevent it from becoming excessively wide or tall, which could hinder the overall user experience. The CSS measurement approach allows for responsiveness, adapting the size of the drawer dynamically in relation to the available screen space.

The Drawer's max size property is particularly beneficial when dealing with responsive and adaptive designs. It ensures that the drawer remains visually pleasing and functional across different devices, screen orientations, and viewports.

When the drawer's content exceeds the defined maximum size, developers can implement appropriate techniques to handle overflow, such as scrolling within the drawer or utilizing additional UI patterns like tabs or accordions. This helps maintain a clean and organized interface while accommodating larger amounts of content.

<ComponentDemo
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.drawerdemos.DrawerSize'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/drawerdemos/DrawerSize.java'
height='600px'
/>

### Placement

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
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.drawerdemos.DrawerPlacement'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/drawerdemos/DrawerPlacement.java'
height='600px'
/>

## Events


There are two supported events that can be used with the Drawer component, open and close events. These events are essential components of the Drawer component, providing developers with the means to respond and react to the opening and closing actions of the drawer within the user interface.

### Drawer Open

Use the `addOpenListener()` method to add an open event to your Drawer component.

The Drawer Open Event is triggered when the drawer is initiated to open, either through user interaction or programmatically. This event allows developers to execute specific actions, animations, or updates that should occur when the drawer becomes visible and accessible to the user. To add an open event listener, use the appropriate method:

```java
myDrawer.addOpenListener( e -> {
  //Executed when the event fires
});
```

By leveraging the Drawer Open Event, developers can synchronize the opening of the drawer with other UI elements, trigger transitions or animations, and update the interface to reflect the change in state. This event can also be used to initiate fetching data, loading content, or any other necessary operations related to the appearance of the drawer.

:::info
The `onOpen() method is similarly available for use, and simple calls the addOpenListener()` method.
:::

### Drawer Close

Use the `addCloseListener()` method to add an open event to your Drawer component.

The Drawer Close Event is triggered when the drawer is initiated to close. This event provides developers with the opportunity to perform actions or updates that should take place when the drawer is no longer visible or accessible to the user. To add a close event listener, use the appropriate method:

```java
myDrawer.addCloseListener( e -> {
  //Executed when the event fires
});
```

With the Drawer Close Event, developers can synchronize the closing of the drawer with other UI elements, initiate animations or transitions, and update the interface to reflect the change in state. Additionally, this event can be utilized to save user preferences, persist data, or perform any cleanup operations associated with the closure of the drawer.

:::info
The `onClose()` method is similarly available for use, and simple calls the `addOpenListener()` method.
:::

### Toggling a Drawer Open or Closed

The `toggle()` method allows developers to control the opening and closing behavior of the drawer based on a boolean value. This function provides a programmatic way to toggle the visibility of the drawer. This can be shorthanded by using the `open()` and `close()` methods, which call the `toggle()` method with the appropriate boolean argument.
<!-- One Example is fine here -->


### Removing an Event 

To remove either an open or close event from the Drawer, simply use the appropriate remove event listener method.

```java
myDrawer.removeOpenListener(listener);
```

## Example Usage

The Drawer component can be used in many different situations, such as by providing a navigation menu that can be toggled, a panel that displays supplementary or contexual information, or to optimize usage on a mobile device. The following example will show a mobile application that uses the DWCJ's AppLayout component, and displays a "Welcome Popup" drawer at the bottom when first loaded. Additionally, a navigational Drawer component can be toggled in the application by clicking on the hamburger menu.

<AppLayoutViewer url='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.drawerdemos.DrawerWelcome' mobile='true'/>

<ComponentDemo 
frame="hidden"
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/drawerdemos/DrawerWelcome.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/drawerstyles/drawer_welcome.css'
/>

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Drawer} />