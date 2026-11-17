---
title: Coming from Swing
sidebar_position: 5
description: Map Java Swing concepts such as JFrame, layout managers, listeners, the event dispatch thread, table models, JOptionPane, and CardLayout to their webforJ counterparts.
---

This page maps the concepts of a Java Swing desktop app to their webforJ counterparts. In both, the UI is plain Java: components are Java objects, events are handled by listeners, and layouts are composed in code. webforJ runs on the server, and the framework keeps the browser in sync with the component state. See [Component architecture](/docs/architecture/controls-components) for how that works.

The webforJ API is analogous to Swing, not compatible with it. Each screen is rewritten against the webforJ API, while classes that don't depend on `javax.swing` or `java.awt`, such as services, entities, and validation rules, are called from the new views unchanged.

## Windows and frames {#windows-and-frames}

A Swing app creates a `JFrame`, adds content, and shows it. In webforJ, the browser page is the window. The app class extends `App`, and the framework provides a `Frame` that holds the visible content. With routing enabled through `@Routify`, each screen is a class annotated with `@Route`, and the router renders it into the frame.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JFrame` | `App` and `Frame`, with screens as `@Route` classes | [Routable apps](/docs/routing/routable-apps), [Routing](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` or `HasFrameTitle` | [Navigational frame titles](/docs/routing/navigational-frame-titles) |
| Main method that builds the UI | `App` subclass with `@Routify` | [App basics](/docs/introduction/basics) |

## Panels and layout managers {#panels-and-layout-managers}

A `JPanel` with a layout manager becomes a `Composite` bound to a layout component. `FlexLayout` arranges children in a row or a column, with spacing, alignment, and wrapping, and covers most screens built with `BoxLayout` or `FlowLayout`. A `BorderLayout` window shell maps to `AppLayout`, and other `BorderLayout` panels to nested `FlexLayout` containers.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JPanel` subclass | `Composite<T>` bound to a container such as `FlexLayout` or `Div` | [Composing components](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | `AppLayout` for a window shell, nested `FlexLayout` containers for other panels | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| `GridLayout`, `GridBagLayout` forms | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Main window with header, side menu, and content area | `AppLayout` | [AppLayout](/docs/components/app-layout) |

A Swing panel with a text field and a button that reacts to clicks becomes the following view:

```java
@Route("customers/new")
@FrameTitle("New customer")
public class NewCustomerView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);

  public NewCustomerView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    save.onClick(e -> OptionDialog.showMessageDialog("Saved " + name.getValue(), "Customer"));
    self.add(name, save);
  }
}
```

## Components {#components}

Most Swing components have a direct counterpart. Text components use `setText()` for their caption and input components use `getValue()` and `setValue()` for their content, as described in [Using components](/docs/building-ui/using-components).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Using components](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` and the other masked fields | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` and `ButtonGroup` | `RadioButton` and `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` for a fixed set, `ComboBox` when users can type a value | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Listeners and events {#listeners-and-events}

Swing registers listeners with `addXxxListener` methods. webforJ components expose the same pattern: an `addXxxListener` method and, in most cases, a shorter `on` alias. Each registration returns a `ListenerRegistration`, and calling `remove()` on it replaces Swing's `removeXxxListener`. See [Events](/docs/building-ui/events).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `ActionListener` on a button | `onClick` or `addClickListener` | [Button](/docs/components/button) |
| `DocumentListener` on a text field | `onModify` | [Events](/docs/building-ui/events) |
| `ItemListener` on a `JCheckBox` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| `ChangeListener` and property change listeners on values | `onValueChange` | [Events](/docs/building-ui/events) |
| `FocusListener` | `onFocus` and `onBlur` | [Events](/docs/building-ui/events) |
| `KeyListener` on a field | `onKeypress` | [Events](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Removing an event](/docs/building-ui/events#removing-an-event) |

## The event dispatch thread and UI updates {#event-dispatch-thread-and-ui-updates}

Swing requires all UI work on the event dispatch thread. webforJ has the same rule: each user session has an `Environment` thread, and all UI updates must happen on it. Constructors, event handlers, and lifecycle methods already run there. A background thread must hand its UI changes back with `Environment.runLater(Environment, Runnable)`.

Read the target with `Environment.getCurrent()` on the `Environment` thread, before the background work starts. On a background thread, `getCurrent()` returns `null`. See [Asynchronous updates](/docs/advanced/asynchronous-updates) for the thread model and task cancellation through `PendingResult`.

| Swing | webforJ | Reference |
| --- | --- | --- |
| Event dispatch thread | `Environment` thread of the session | [Asynchronous updates](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Asynchronous updates](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Background work, for example `CompletableFuture`, then `Environment.runLater` | [Asynchronous updates](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Restarting a timer on every keystroke | `Debouncer` | [Debouncing](/docs/advanced/debouncing) |

The `doInBackground` body of a `SwingWorker` becomes the background task, and the `done` method becomes the `runLater` blocks, one for the result and one for a failure:

```java
public class ReportView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final CustomerService service = new CustomerService();
  private final Button export = new Button("Export");
  private final Label status = new Label();

  public ReportView() {
    self.add(export, status);
    export.onClick(e -> {
      Environment env = Environment.getCurrent();
      CompletableFuture.supplyAsync(service::exportReport)
          .thenAccept(path -> Environment.runLater(env, () -> { status.setText("Saved to " + path); }))
          .exceptionally(error -> {
            Environment.runLater(env, () -> { status.setText("Export failed"); });
            return null;
          });
    });
  }
}
```

## Models and data binding {#models-and-data-binding}

Swing separates data from views with model interfaces such as `TableModel` and `ListModel`, and code that copies values between fields and objects by hand. webforJ uses a `Repository` as the data source for components such as `Table`, and a `BindingContext` to connect form fields to bean properties in both directions.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `TableModel` | `Repository`, such as `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | `ListItem` entries added to a `ListBox`, `ChoiceBox`, or `ComboBox` | [Lists](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | `commit()` and `commit(entity)` on the repository | [Data synchronization](/docs/advanced/repository/overview#data-synchronization) |
| Copying field values to and from an object | `BindingContext` with `read(bean)` and `write(bean)` | [Data binding](/docs/data-binding/overview), [Automatic binding](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Validators on bindings, or Jakarta Validation | [Validation](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` binds component fields to bean properties with the same name:

```java
public class CustomerForm extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final BindingContext<Customer> context;

  public CustomerForm(Customer customer) {
    self.setDirection(FlexDirection.COLUMN).add(name, email);
    context = BindingContext.of(this, Customer.class);
    context.read(customer);
  }

  public boolean save(Customer customer) {
    return context.write(customer).isValid();
  }
}
```

## Dialogs {#dialogs}

Option dialogs are modal and block execution until the user responds, like `JOptionPane`. The code after the call reads the result directly, with no callback. See [Option dialogs](/docs/components/option-dialogs/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Message dialog](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, returning `ConfirmDialog.Result` | [Confirm dialog](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Input dialog](/docs/components/option-dialogs/input) |
| `JFileChooser` for opening a file on the user's machine | `OptionDialog.showFileUploadDialog` | [File upload dialog](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` for browsing server files | `OptionDialog.showFileChooserDialog` | [File chooser dialog](/docs/components/option-dialogs/file-chooser) |
| `JDialog` with custom content | `Dialog`, opened with `open()` and closed with `close()` | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Tables {#tables}

A `JTable` reads from a `TableModel` subclass. A webforJ `Table<T>` holds a list of entities and binds each column to a getter, so there is no model class to write. `setItems` wraps a collection in a repository, and `setRepository` accepts one backed by your data layer. See [Table](/docs/components/table/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JTable` with a `TableModel` | `Table<T>` with `setItems` or `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` and `getValueAt` | `addColumn(id, getter)` | [Columns](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Selection](/docs/components/table/selection) |
| `MouseListener` checking for a double click | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Sorting](/docs/components/table/sorting) |
| `RowFilter` | `setBaseFilter(Predicate)` on a `CollectionRepository`, applied when you call `commit()` | [Filtering](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer` set with `Column.setRenderer` | [Rendering](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navigation {#navigation}

Swing apps switch screens by swapping panels, often with `CardLayout`, or by opening new windows. In webforJ each screen is a route with its own URL, so browser history, bookmarks, and deep links work. See [Routing](/docs/routing/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `CardLayout.show` or replacing the content panel | `@Route` views and `Router.getCurrent().navigate(View.class)` | [Defining routes](/docs/routing/defining-routes), [Route navigation](/docs/routing/route-navigation) |
| `JMenu` or a side list of screens | `AppNav` with `AppNavItem` entries linked to routes | [AppNav](/docs/components/appnav) |
| Opening a screen with parameters | Route parameters and query parameters | [Route patterns](/docs/routing/route-patterns), [Query parameters](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Moving screen by screen {#moving-screen-by-screen}

The Swing app doesn't have to be rewritten in one pass. Webswing runs an existing Swing, JavaFX, or SWT app in the browser without changes to its source, and the `WebswingConnector` component embeds it inside a webforJ view. Only two-way messages need a small addition to the Swing app, described below. Old and new screens then live in one app while screens move over one at a time.

The connector is two-way. `performAction` sends actions from webforJ to the Swing app, and `onAction` receives actions the Swing app sends through the Webswing Java API. Sending actions back requires adding that API to the Swing app.

On the Swing side, the `webswing-api` dependency from the Webswing repository provides `WebswingUtil.getWebswingApi()`. It returns `null` when the app runs as a regular desktop app, so the same code keeps working on the desktop. Here double-clicking a table row sends the selected customer to webforJ instead of opening a Swing dialog:

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) explains the architecture and progressive modernization.
- [Setup and configuration](/docs/integrations/webswing/setup) covers the Webswing server and CORS.
- [Communication](/docs/integrations/webswing/communication) covers `performAction` and `onAction`.
- [Modernization tutorial](/docs/integrations/webswing/tutorial) replaces a Swing edit dialog with a webforJ form while the Swing table stays.
