---
title: Dialog
sidebar_position: 30
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

The webforJ dialog component is built to allow a developer to quickly and easily display a dialog on their application, for instances such as a login menu or information box.

The component is built with three sections, each of which are `Panel` components: the **header**, the **content**, and the **footer**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usages {#usages}

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

## Backdrop and blur {#backdrop-and-blur}

By enabling the backdrop attribute of the webforJ `Dialog` component, a backdrop will be displayed behind the `Dialog`. Additionally, when enabled, the Dialog's blurred attribute will blur the backdrop of the `Dialog`. Modifying these settings can help users by providing depths, visual hierarchy, and context, leading to more clear guidance for a user.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Opening and closing the `Dialog` {#opening-and-closing-the-dialog}

After creating a new `Dialog` object, use the `open()` method to display the dialog. Then, the `Dialog` component can close from one of these actions:
- Using the `close()` method
- Hitting the <kbd>ESC</kbd> key
- Clicking outside of the `Dialog`

Developers can choose which interactions close the `Dialog` with `setCancelOnEscKey()` and `setCancelOnOutsideClick()`. Additionally, the `setClosable()` method can prevent or allow both hitting the <kbd>ESC</kbd> key and clicking outside the `Dialog` to close the component.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-focus {#auto-focus}

When enabled, auto-focus will automatically give focus to the first element within the dialog that can be focused. This is useful in helping to direct the attention of users, and is customizable via the `setAutoFocus()` method.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Draggable {#draggable}

The `Dialog` has built in functionality to be draggable, allowing the user to relocate the `Dialog` window by clicking and dragging. The position of the `Dialog` can be manipulated from any of the fields within it: the header, content or footer.

### Snap to edge {#snap-to-edge}
It is also possible to calibrate this behavior to snap to the edge of the screen, meaning the `Dialog` will automatically align itself with the edge of the display when released from its drag and drop date. Snapping can be changed via the `setSnapToEdge()` method. The `setSnapThreshold()` takes a number of pixels, which will set how far the `Dialog` should be from the sides of the screen before it will automatically snap to the edges.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positioning {#positioning}

The dialog's position can be manipulated using the built-in `setPosx()` and `setPosy()` methods. These methods take a string argument which can represent any applicable CSS unit of length, such as pixels or view height/width. A list of these measurements [can be found at this link](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Vertical alignment {#vertical-alignment}

In addition to manual assignment of a dialog's X and Y position, it is possible to use the dialog's built-in enum class to align the `Dialog`. There are three possible values, `TOP`, `CENTER` and `BOTTOM`, each of which can be used with the `setAlignment()` method. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Full screen and breakpoints {#full-screen-and-breakpoints}

The `Dialog` can be set to enter full screen mode. When full screen is enabled, the `Dialog` cannot be moved or positioned. This mode can be manipulated with the breakpoint attribute of the `Dialog`. The breakpoint is a media query which components when the `Dialog` will automatically flip to full screen mode. When the query matches, the `Dialog` changes to full screen - otherwise it is positioned.

## Styling {#styling}

### Themes {#themes}

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
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />