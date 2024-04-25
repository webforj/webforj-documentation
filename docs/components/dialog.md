---
title: Dialog
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" target="_blank" clickable={false} iconName='shadow' />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-dialog" clickable={false} iconName='code'/>

<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

The webforJ dialog component is built to allow a developer to quickly and easily display a dialog on their application, for instances such as a login menu or information box.

The component is built with three sections, each of which are `Panel` components: the **header**, the **content**, and the **footer**.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogSections' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Sections.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogSections.java'
height = '225px'
/>

## Usages

1. **User Feedback and Confirmation**: `Dialog` components are often used to provide feedback or ask for user confirmation. They can display various important pieces of feedback to a user, such as:

  >- Success messages 
  >- Error alerts
  >- Confirmation submissions

2. **Form Input and Editing**: You can use dialogs to collect user input or allow them to edit information in a controlled and focused manner. For example, a dialog can pop up to edit user profile details or complete a multi-step form.

3. **Contextual Information**: Displaying additional contextual information or tooltips in a dialog can help users understand complex features or data. Dialogs can provide in-depth explanations, charts, or help documentation.

4. **Image and Media Previews**: When users need to view pieces of media, a `Dialog` can be used to show larger previews or galleries, such as when interacting with:
  >- Images
  >- Videos
  >- Other media


## Constructors

The Dialog can be constructed using the default `Dialog()` constructor. Once the object has been instantiated, call the `show()` method on the object itself to display the dialog.


## Backdrop and Blur

By enabling the backdrop attribute of the webforJ `Dialog` component, a backdrop will be displayed behind the `Dialog`. Additionally, when enabled, the Dialog's blurred attribute will blur the backdrop of the `Dialog`. Modifying these settings can help users by providing depths, visual hierarchy, and context, leading to more clear guidance for a user.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogBackdropBlur' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Blur.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogBackdropBlur.java'
height = '300px'
/>

## Closing the Dialog

The `Dialog` supports multiple cancellation methods for closure of the component: hitting the `ESC` key, clicking outside of the `Dialog`, or using the `hide()` method. The first two properties are customizable via their respective methods:
`setCancelOnEscKey()` and `setCancelOnOutsideClick()`. The `hide()` method allows for the `Dialog` to be closed programmatically, such as by clicking a button on the `Dialog` after saving data. A method to quickly enable or disable users' ability to close the `Dialog`, `setClosable()` will prevent or allow both escape and click closure of the `Dialog`.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogClose' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Close.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogClose.java'
height = '350px'
/>

## Auto-Focus

When enabled, auto-focus will automatically give focus to the first element within the dialog that can be focused. This is useful in helping to direct the attention of users, and is customizable via the `setAutoFocus()` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogAutoFocus' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/AutoFocus.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogAutoFocus.java'
height = '350px'
/>

## Draggable

The `Dialog` has built in functionality to be draggable, allowing the user to relocate the `Dialog` window by clicking and dragging. The position of the `Dialog` can be manipulated from any of the fields within it: the header, content or footer.

### Snap to Edge
It is also possible to calibrate this behavior to snap to the edge of the screen, meaning the `Dialog` will automatically align itself with the edge of the display when released from its drag and drop date. Snapping can be changed via the `setSnapToEdge()` method. The `setSnapThreshold()` takes a number of pixels, which will set how far the `Dialog` should be from the sides of the screen before it will automatically snap to the edges.  

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogDraggable' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Draggable.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogDraggable.java'
height = '350px'
/>

## Positioning

The dialog's position can be manipulated using the built-in `setPosx()` and `setPosy()` methods. These methods take a string argument which can represent any applicable CSS unit of length, such as pixels or view height/width. A list of these measurements [can be found at this link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogPositioning' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Positioning.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogPositioning.java'
height = '350px'
/>

### Vertical Alignment

In addition to manual assignment of a dialog's X and Y position, it is possible to use the dialog's built-in enum class to align the `Dialog`. There are three possible values, `TOP`, `CENTER` and `BOTTOM`, each of which can be used with the `setAlignment()` method. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogAlignments' 
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/dialog/Alignments.txt'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogAlignments.java'
height = '550px'
/>

### Full Screen and Breakpoints

The `Dialog` can be set to enter full screen mode. When full screen is enabled, the `Dialog` cannot be moved or positioned. This mode can be manipulated with the breakpoint attribute of the `Dialog`. The breakpoint is a media query which components when the `Dialog` will automatically flip to full screen mode. When the query matches, the `Dialog` changes to full screen - otherwise it is positioned.

## Styling

### Themes

`Dialog` components come with <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 discrete themes </JavadocLink> built in for quick styling without the use of CSS. These themes are pre-defined styles that can be applied to buttons to change their appearance and visual presentation. They offer a quick and consistent way to customize the look of buttons throughout an application. 

While there are many use cases for each of the various themes, some examples uses are:

  - **Danger**: Actions with severe consequences, such as clearing filled-out information, or permanently deleting an account/data is represents a good use case for dialogs with the Danger theme.
  - **Default**: The default theme is appropriate for actions throughout an application that do not require special attention and that are generic, such as toggling a setting.
  - **Primary**: This theme is appropriate as a main "call-to-action" on a page, such as signing up, saving changes, or continuing to another page.
  - **Success**: Success themed dialogs are excellent for visualizing successful completion of an element in an application, such as the submission of a form or completion of a sign-up process. The success theme can by programmatically applied once a successful action has been completed.
  - **Warning**: Warning dialogs are useful to indicate users they are about to perform a potentially risky action, such as when navigating away from a page with unsaved changes. These actions are often less impactful than those that would use the Danger theme.
  - **Gray**: Good for subtle actions, such as minor settings or actions that are more supplementary to a page, and not part of the main functionality.
  - **Info**: The Info theme is a good choice to provide clarifying, additional information to a user when pushed.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.dialogdemos.DialogThemes' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/dialogdemos/DialogThemes.java'
height = '500px'
/>

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Dialog} table='parts'/>

### Slots

Listed below are the slots available for utilization within the `Button` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Dialog} table='slots' />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Dialog} table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Dialog} table="reflects" />