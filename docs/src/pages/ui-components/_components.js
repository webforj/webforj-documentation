// Every component in the docs gallery (docs/components/overview.md), with
// the preview image the gallery uses and the docs page it links to. The
// short descriptions are trimmed from the gallery cards. The groups follow
// what each component does on a screen.

import { translate } from '@docusaurus/Translate';

const component = (name, path, width, height, text) => ({ name, path, width, height, text });

// A function, so the descriptions are translated while the page renders.
export function getGroups() {
  return [
    {
      id: 'layout',
      title: translate({ id: 'page.uiComponents.group.layout.title', message: 'Layout' }),
      lead: translate({
        id: 'page.uiComponents.group.layout.lead',
        message:
          'The frame of a screen: app shells, toolbars, flex and column layouts, split panes and surfaces.',
      }),
      items: [
        component(
          'AppLayout',
          'app-layout',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.appLayout',
            message: 'A structured layout for top-level app navigation and content.',
          }),
        ),
        component(
          'FlexLayout',
          'flex-layout',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.flexLayout',
            message: 'Arranges its children with flexbox rules for responsive alignment.',
          }),
        ),
        component(
          'ColumnsLayout',
          'columns-layout',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.columnsLayout',
            message: 'Arranges children into columns, useful for forms and grid-like structures.',
          }),
        ),
        component(
          'Toolbar',
          'toolbar',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.toolbar',
            message: 'A horizontal container for action buttons, icons and other controls.',
          }),
        ),
        component(
          'Splitter',
          'splitter',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.splitter',
            message: 'Divides space between two components that users resize by dragging.',
          }),
        ),
        component(
          'Accordion',
          'accordion',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.accordion',
            message: 'A stacked set of collapsible panels with clickable headers.',
          }),
        ),
        component(
          'Card',
          'card',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.card',
            message: 'A surface with regions for media, headers, body content and footers.',
          }),
        ),
        component(
          'Dialog',
          'dialog',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.dialog',
            message: 'A modal window that overlays content and prompts the user.',
          }),
        ),
      ],
    },
    {
      id: 'forms',
      title: translate({ id: 'page.uiComponents.group.forms.title', message: 'Forms and inputs' }),
      lead: translate({
        id: 'page.uiComponents.group.forms.lead',
        message:
          'Everything a user types, picks or uploads, from text and numbers to dates, masks, lists and files.',
      }),
      items: [
        component(
          'TextField',
          'fields/textfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.textField',
            message: 'A single-line input for entering and editing text.',
          }),
        ),
        component(
          'MaskedTextField',
          'fields/masked/textfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.maskedTextField',
            message: 'Restricts text to a pattern, such as a phone number.',
          }),
        ),
        component(
          'NumberField',
          'fields/numberfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.numberField',
            message: 'A numeric input with controls to step the value up or down.',
          }),
        ),
        component(
          'MaskedNumberField',
          'fields/masked/numberfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.maskedNumberField',
            message: 'Enforces a numeric format such as currency or percentages.',
          }),
        ),
        component(
          'PasswordField',
          'fields/passwordfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.passwordField',
            message: 'A single-line input that masks password data.',
          }),
        ),
        component(
          'DateField',
          'fields/datefield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.dateField',
            message: 'A browser-based date picker in an input field.',
          }),
        ),
        component(
          'MaskedDateField',
          'fields/masked/datefield',
          620,
          448,
          translate({
            id: 'page.uiComponents.item.maskedDateField',
            message: 'A date input that enforces a defined date format.',
          }),
        ),
        component(
          'TimeField',
          'fields/timefield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.timeField',
            message: 'A browser-based time picker in an input field.',
          }),
        ),
        component(
          'MaskedTimeField',
          'fields/masked/timefield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.maskedTimeField',
            message: 'A time input that enforces a defined time format.',
          }),
        ),
        component(
          'DateTimeField',
          'fields/datetimefield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.dateTimeField',
            message: 'Picks a date and a time in one input field.',
          }),
        ),
        component(
          'ColorField',
          'fields/colorfield',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.colorField',
            message: 'A browser-based color picker in an input field.',
          }),
        ),
        component(
          'TextArea',
          'textarea',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.textArea',
            message: 'A multi-line input for larger blocks of text.',
          }),
        ),
        component(
          'CheckBox',
          'checkbox',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.checkBox',
            message: 'A binary option that toggles between checked and unchecked.',
          }),
        ),
        component(
          'RadioButton',
          'radiobutton',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.radioButton',
            message: 'Selects one option from a group of exclusive choices.',
          }),
        ),
        component(
          'Switch',
          'radiobutton#switches',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.switch',
            message: 'Toggles between two states, such as on and off, with a sliding action.',
          }),
        ),
        component(
          'ChoiceBox',
          'lists/choicebox',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.choiceBox',
            message: 'A dropdown list of predefined options.',
          }),
        ),
        component(
          'ComboBox',
          'lists/combobox',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.comboBox',
            message: 'A dropdown list combined with an editable text input.',
          }),
        ),
        component(
          'ListBox',
          'lists/listbox',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.listBox',
            message: 'A scrollable list for selecting one or more items.',
          }),
        ),
        component(
          'Slider',
          'slider',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.slider',
            message: 'Selects a value from a range by dragging a handle along a track.',
          }),
        ),
        component(
          'Upload',
          'upload',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.upload',
            message: 'An inline file picker with drag-and-drop, filters and per-file events.',
          }),
        ),
      ],
    },
    {
      id: 'data',
      title: translate({ id: 'page.uiComponents.group.data.title', message: 'Data and content' }),
      lead: translate({
        id: 'page.uiComponents.group.data.lead',
        message:
          'Show records, hierarchies, charts and streamed text, and load more as the user scrolls.',
      }),
      items: [
        component(
          'GoogleCharts',
          'google-charts',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.googleCharts',
            message: 'Charts and visual data representations through Google Charts.',
          }),
        ),
        component(
          'Tree',
          'tree',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.tree',
            message: 'Hierarchical data that users expand, collapse and interact with.',
          }),
        ),
        component(
          'InfiniteScroll',
          'infinitescroll',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.infiniteScroll',
            message: 'Loads more items on scroll and tracks when all content is fetched.',
          }),
        ),
        component(
          'Refresher',
          'refresher',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.refresher',
            message: 'A pull-to-refresh interaction inside scrollable containers.',
          }),
        ),
        component(
          'MarkdownViewer',
          'markdownviewer',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.markdownViewer',
            message: 'Renders markdown progressively, ideal for AI chat and streaming text.',
          }),
        ),
        component(
          'Terminal',
          'terminal',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.terminal',
            message: 'A command-line interface inside the app for text-based commands.',
          }),
        ),
        component(
          'Avatar',
          'avatar',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.avatar',
            message: 'Profile pictures or initials in different sizes, shapes and themes.',
          }),
        ),
        component(
          'Badge',
          'badge',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.badge',
            message: 'A small label for counts, statuses or short metadata.',
          }),
        ),
      ],
    },
    {
      id: 'navigation',
      title: translate({
        id: 'page.uiComponents.group.navigation.title',
        message: 'Navigation and actions',
      }),
      lead: translate({
        id: 'page.uiComponents.group.navigation.lead',
        message:
          'Move between views and sections, page through data and trigger what happens next.',
      }),
      items: [
        component(
          'AppNav',
          'appnav',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.appNav',
            message: 'A navigation menu for switching between sections and views.',
          }),
        ),
        component(
          'Drawer',
          'drawer',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.drawer',
            message: 'A sliding panel for side navigation or extra content.',
          }),
        ),
        component(
          'TabbedPane',
          'tabbedpane',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.tabbedPane',
            message: 'Organizes content into tabs that users switch between.',
          }),
        ),
        component(
          'Navigator',
          'navigator',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.navigator',
            message: 'Pagination with first, last, next and previous buttons and quick jump.',
          }),
        ),
        component(
          'Button',
          'button',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.button',
            message: 'A clickable component that triggers an action when pressed.',
          }),
        ),
        component(
          'Icon',
          'icon',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.icon',
            message: 'A graphical symbol for an action, status or category.',
          }),
        ),
      ],
    },
    {
      id: 'feedback',
      title: translate({
        id: 'page.uiComponents.group.feedback.title',
        message: 'Feedback and dialogs',
      }),
      lead: translate({
        id: 'page.uiComponents.group.feedback.lead',
        message:
          'Tell users what is happening, ask them to confirm, and collect a value, a file or a login.',
      }),
      items: [
        component(
          'Toast',
          'toast',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.toast',
            message: 'A non-blocking notification that disappears on its own.',
          }),
        ),
        component(
          'Alert',
          'alert',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.alert',
            message: 'Important messages or warnings in a noticeable format.',
          }),
        ),
        component(
          'DesktopNotification',
          'desktop-notification',
          800,
          600,
          translate({
            id: 'page.uiComponents.item.desktopNotification',
            message: 'Custom desktop notifications through the browser Notification API.',
          }),
        ),
        component(
          'ProgressBar',
          'progressbar',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.progressBar',
            message: 'A bar that fills as a task makes progress.',
          }),
        ),
        component(
          'BusyIndicator',
          'busyindicator',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.busyIndicator',
            message: 'An app-wide indicator that a global process is ongoing.',
          }),
        ),
        component(
          'Loading',
          'loading',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.loading',
            message: 'A loading indicator scoped to one parent component.',
          }),
        ),
        component(
          'Spinner',
          'spinner',
          600,
          448,
          translate({
            id: 'page.uiComponents.item.spinner',
            message: 'A rotating animation that shows an action is in progress.',
          }),
        ),
        component(
          'MessageDialog',
          'option-dialogs/message',
          900,
          680,
          translate({
            id: 'page.uiComponents.item.messageDialog',
            message: 'An informational message the user acknowledges with OK.',
          }),
        ),
        component(
          'ConfirmDialog',
          'option-dialogs/confirm',
          900,
          680,
          translate({
            id: 'page.uiComponents.item.confirmDialog',
            message: 'Asks the user to confirm or cancel an action.',
          }),
        ),
        component(
          'InputDialog',
          'option-dialogs/input',
          900,
          680,
          translate({
            id: 'page.uiComponents.item.inputDialog',
            message: 'Prompts the user for a value with OK and Cancel.',
          }),
        ),
        component(
          'FileUploadDialog',
          'option-dialogs/file-upload',
          900,
          680,
          translate({
            id: 'page.uiComponents.item.fileUploadDialog',
            message: 'Uploads files from the local filesystem to the app.',
          }),
        ),
        component(
          'Login',
          'login',
          1200,
          900,
          translate({
            id: 'page.uiComponents.item.login',
            message: 'A pre-built sign-in UI with username, password and submit.',
          }),
        ),
      ],
    },
  ];
}

export function indexByName(groups) {
  return Object.fromEntries(
    groups.flatMap((group) => group.items).map((item) => [item.name, item]),
  );
}
