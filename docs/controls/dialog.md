---
sidebar_position: 25 
title: Dialog
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/dialog/Dialog.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

The DWCJ's dialog component is built to allow a developer to quickly and easily display a dialog on their application, for instances such as a login menu or information box.

The component is built with three sections, each of which are `Div` components: the header, the content, and the footer.

To show the dialog, call the `show()` method on the object itself. This 

<ComponentDemo 
path='/demos/dialog-demos/sections.html' 
showCSS='true'
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/listboxdemos/ListboxLabel.java'
javaHighlight='{47-50}'
height = '225px'
/>

### Backdrop and Blur

By enabling the backdrop attribute of the DWCJ's dialog component, a backdrop will be displayed behind the dialog. Additionally, when enabled, the dialog's blurred attribute will blur the backdrop of the dialog.

<ComponentDemo 
path='/demos/dialog-demos/backdrop_blur.html' 
showCSS='true'
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/listboxdemos/ListboxLabel.java'
javaHighlight='{47-50}'
height = '225px'
/>

### Closing the Dialog

The dialog supports multiple cancellation methods for closure of the control: hitting the `ESC` key, clicking outside of the dialog, or using the `hide()` method. The first two properties are customizable via their respective methods:
`setCancelOnEscKey()` and `setCancelOnOutsideClick()`. The `hide()` method allows for the dialog to be closed programmatically, such as by clicking a button on the dialog, for example after saving data. A method to quickly enable or disable users' ability to close the dialog, `setClosable()` will prevent or allow both escape and click closure of the dialog.

### Auto-Focus

When enabled, auto-focus will automatically give focus to the first element within the dialog that can be focused. This is useful in helping to direct the attention of users, and is customizable via the `setAutoFocus()` method.

## Draggable

The dialog has built in functionality to be draggable, allowing the user to relocate the dialog window by clicking and dragging. The position of the dialog can be manipulated from any of the fields within it: the header, content or footer.

### Snap to Edge
It is also possible to calibrate this behavior to snap to the edge of the screen, meaning the dialog will automatically align itself with the edge of the display when released from its drag and drop date. Snapping can be changed via the `setSnapToEdge()` method. The `setSnapThreshold()` takes a number of pixels, which will set how far the dialog should be from the sides of the screen before it will automatically snap to the edges.  


## Positioning

The dialog's position can be manipulated using the built-in `setPosx()` and `setPosy()` methods. These methods take a string argument which can represent any applicable CSS unit of length, such as pixels or view height/width. A list of these measurements [can be found at this link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

### Vertical Alignment

In addition to manual assignment of a dialog's X and Y position, it is possible to use the dialog's built-in enum class to align the dialog. There are three possible values, `TOP`, `CENTER` and `BOTTOM`, each of which can be used with the `setAlignment()` method. 

### Full Screen and Breakpoints

The dialog can be set to enter full screen mode. When full screen is enabled, the dialog cannot be moved or positioned. This mode can be manipulated with the breakpoint attribute of the dialog. The breakpoint is a media query which controls when the dialog will automatically flip to full screen mode. When the query matches, the dialog changes to full screen - otherwise it is positioned.