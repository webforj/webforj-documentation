---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI Components | User Interface Application Building Components</title>
</Head>

In webforJ, applications are created using modular units known as Components, which facilitate quick and efficient UI development. The framework offers a range of essential components like buttons, input elements, and layout containers. After mastering the fundamentals, you can consult the [JavaDocs](https://javadoc.io/doc/com.webforj) for a detailed overview of all components and their functionalities.

## Layouts {#layouts}

Layout components provide the foundation for structuring user interfaces, enabling developers to organize content efficiently. These components offer various ways to control the arrangement of child components, whether for simple or complex layouts.

The following layout components are designed to handle a wide range of use cases, from responsive design to advanced content management

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>A container component that provides a structured layout for top-level app navigation and content organization.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>A horizontal container component that holds a set of action buttons, icons, or other controls, typically used for performing tasks related to the current context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>A layout component that arranges its children using flexible box (flexbox) rules for responsive design and alignment.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>A layout component that arranges its children into multiple vertical columns, useful for creating forms and grid-like structures.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>A layout component that divides the available space between two child components, allowing users to resize them by dragging the splitter bar.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>A sliding panel component typically used for side navigation or housing additional content that can be shown or hidden.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>A modal window component that overlays content to display important information or prompt user interaction, often requiring user action to close.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>A component that provides a pre-built UI for user authentication, typically including fields for username and password along with a submit button.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>A vertically stacked set of collapsible panels, each with a clickable header that toggles the visibility of its body content.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>A container component that organizes content into multiple tabs, allowing users to switch between different views or sections.</p>
  </GalleryCard>
</GalleryGrid>

## Data entry {#data-entry}

Data entry components provide essential tools for capturing user input and managing interactions within your app. These components are versatile, making it easy to build interactive forms and collect various types of data.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>A single-line input component for entering and editing text data.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>A text input component that restricts user input to a specific format or pattern, typically used for fields like phone numbers, dates, or credit card numbers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>A component that provides a default browser-based input field for entering numeric values, with built-in controls for incrementing or decrementing the value.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>A numeric input component that restricts user input to a specific numeric format or pattern, ensuring valid number entry such as for currency, percentages, or other formatted numbers.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>A single-line input component for securely entering and masking password data.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>A component that provides a default browser-based date picker for selecting a date through an input field.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>A date input component that enforces a specific date format or pattern, ensuring the user enters a valid date according to the defined mask</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>A component that provides a default browser-based time picker for selecting a time value through an input field.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>A time input component that enforces a specific time format or pattern, ensuring the user inputs a valid time according to the defined mask</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>A component that provides a default browser-based date and time picker for selecting both date and time through a single input field.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>A component that provides a default browser-based color picker, allowing users to select a color from an input field.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>A multi-line text input component that allows users to enter or edit larger blocks of text.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>A component that represents a binary option, allowing users to toggle between a checked (true) or unchecked (false) state.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>A component that allows users to select a single option from a group of mutually exclusive choices.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>A toggle component that allows users to switch between two states, such as on/off or true/false, with a sliding action.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>A component that provides a dropdown list of predefined options, allowing users to select one option from the list.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>A component that combines a dropdown list with an editable text input, allowing users to either select an option from the list or enter a custom value.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>A component that displays a scrollable list of options, allowing users to select one or more items from the list.</p>
  </GalleryCard>
</GalleryGrid>

## Option dialogs {#option-dialogs}

Option dialogs provide a way to present users with choices or prompt them for confirmation before proceeding with an action. These components are essential for creating interactive, decision-driven workflows, enabling users to confirm, cancel, or choose from various options in a clear and structured manner.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>A dialog component used to display informational messages or alerts to the user, typically with a single `OK` button to acknowledge the message.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>A dialog component that asks the user to confirm or cancel an action, typically providing `Yes` and `No` or `OK` and `Cancel` buttons.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>A dialog component that prompts the user to input text or data, typically providing an input field along with action buttons like `OK` and `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>A dialog component that allows users to browse and select files from server filesystem.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>A dialog component that enables users to upload files from their local filesystem to the app.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>A dialog component that allows users to save a file to a specified location on the server file system.</p>
  </GalleryCard>
</GalleryGrid>

## Interaction and display {#interaction-and-display}

This category includes components that facilitate user interactions and visually display data or app states. These components help users navigate the app, trigger actions, and understand progress or results through dynamic visual elements.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p> A component used to display data in a structured, tabular format with rows and columns, supporting features like sorting and pagination.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>A component that integrates with Google Charts to display various types of charts and visual data representations in the app.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>A clickable component that triggers an action or event when pressed.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>A lightweight, non-blocking notification component that briefly displays a message to the user before automatically disappearing.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>A component that displays important messages or warnings in a noticeable format to capture user attention.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>A small label component for displaying counts, statuses, or short metadata, with support for themes, sizes, and icons.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>A component that leverages the browser’s native Notification API to alert users with custom desktop notifications.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>A customizable pagination component for navigating through data sets, supporting layouts with first, last, next, previous buttons, and quick jump fields.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>A component that visually represents the progress of a task or process, typically displayed as a horizontal bar that fills as progress is made.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>A component that allows users to select a value from a defined range by dragging a handle along a track.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p> An app-wide visual indicator, typically a spinner, signaling that a global process is ongoing.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>A scoped loading indicator that shows within a specific parent component, indicating that content or data is being loaded in that section.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>A component that displays a rotating animation, typically used to indicate that a process or action is in progress.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>A component that provides a navigation menu for the app, typically used for listing links or navigation items for switching between different sections or views.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>A component that displays a graphical symbol or image, often used to represent an action, status, or category in the user interface.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>A component that simulates a command-line interface (CLI) within the app, allowing users to input and execute text-based commands.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>A component that loads more items on scroll, shows a loader, and tracks when all content is fetched.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>A component that allows a pull-to-refresh interaction within scrollable containers—ideal for dynamic data loading.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>A component for displaying hierarchical data, letting users expand, collapse, and interact with nested items.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>A component for displaying user profile pictures or initials, with support for different sizes, shapes, and themes.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>A component for displaying markdown content with progressive character-by-character rendering, ideal for AI chat interfaces and streaming text.</p>
  </GalleryCard>
  
</GalleryGrid>


