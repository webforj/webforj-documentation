---
title: Von Swing kommend
sidebar_position: 5
description: >-
  Map Java Swing concepts such as JFrame, layout managers, listeners, the event
  dispatch thread, table models, JOptionPane, and CardLayout to their webforJ
  counterparts.
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
Diese Seite ordnet die Konzepte einer Java-Swing-Desktop-App ihren Entsprechungen in webforJ zu. In beiden Fällen wird die UI in reinem Java erstellt: Komponenten sind Java-Objekte, Ereignisse werden von Listenern verarbeitet und Layouts werden im Code zusammengesetzt. webforJ wird auf dem Server ausgeführt, und das Framework hält den Browser mit dem Zustand der Komponenten synchron. Unter [Komponentenarchitektur](/docs/architecture/controls-components) erfahren Sie, wie das funktioniert.

Die webforJ-API ist mit Swing vergleichbar, aber nicht mit Swing kompatibel. Jeder Bildschirm wird für die webforJ-API neu geschrieben. Klassen, die nicht von `javax.swing` oder `java.awt` abhängen, etwa Services, Entitäten und Validierungsregeln, können unverändert von den neuen Ansichten aufgerufen werden.

## Fenster und Frames {#windows-and-frames}

Eine Swing-App erstellt ein `JFrame`, fügt Inhalte hinzu und zeigt es an. In webforJ ist die Browserseite das Fenster. Die App-Klasse erweitert `App`, und das Framework stellt einen `Frame` bereit, der die sichtbaren Inhalte enthält. Ist Routing über `@Routify` aktiviert, ist jeder Bildschirm eine mit `@Route` annotierte Klasse, die der Router im Frame rendert.

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `JFrame` | `App` und `Frame`, mit Bildschirmen als `@Route`-Klassen | [Routable Apps](/docs/routing/routable-apps), [Routing](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` oder `HasFrameTitle` | [Titel für Navigations-Frames](/docs/routing/navigational-frame-titles) |
| Hauptmethode, die die UI erstellt | `App`-Unterklasse mit `@Routify` | [App-Grundlagen](/docs/introduction/basics) |

## Panels und Layout-Manager {#panels-and-layout-managers}

Ein `JPanel` mit einem Layout-Manager wird zu einem `Composite`, das an eine Layout-Komponente gebunden ist. `FlexLayout` ordnet untergeordnete Komponenten in einer Zeile oder Spalte an, mit Abständen, Ausrichtung und Umbruch. Damit lassen sich die meisten Bildschirme umsetzen, die mit `BoxLayout` oder `FlowLayout` erstellt wurden. Eine Fensterstruktur mit `BorderLayout` entspricht `AppLayout`; andere Panels mit `BorderLayout` lassen sich als verschachtelte `FlexLayout`-Container umsetzen.

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `JPanel`-Unterklasse | `Composite<T>`, gebunden an einen Container wie `FlexLayout` oder `Div` | [Komponenten zusammensetzen](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | `AppLayout` für eine Fensterstruktur, verschachtelte `FlexLayout`-Container für andere Panels | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| Formulare mit `GridLayout`, `GridBagLayout` | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Hauptfenster mit Kopfzeile, Seitenmenü und Inhaltsbereich | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Ein Swing-Panel mit einem Textfeld und einer Schaltfläche, die auf Klicks reagiert, wird zur folgenden Ansicht:

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

## Komponenten {#components}

Die meisten Swing-Komponenten haben ein direktes Gegenstück. Textkomponenten verwenden `setText()` für ihre Beschriftung, Eingabekomponenten verwenden `getValue()` und `setValue()` für ihren Inhalt, wie unter [Komponenten verwenden](/docs/building-ui/using-components) beschrieben.

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Komponenten verwenden](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` und die übrigen maskierten Felder | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` und `ButtonGroup` | `RadioButton` und `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` für eine feste Auswahl, `ComboBox`, wenn Benutzer einen Wert eingeben können | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Listener und Ereignisse {#listeners-and-events}

Swing registriert Listener mit Methoden wie `addXxxListener`. webforJ-Komponenten verwenden dasselbe Muster: eine `addXxxListener`-Methode und in den meisten Fällen einen kürzeren `on`-Alias. Jede Registrierung gibt eine `ListenerRegistration` zurück. Der Aufruf von `remove()` darauf ersetzt Swing's `removeXxxListener`. Siehe [Ereignisse](/docs/building-ui/events).

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `ActionListener` für eine Schaltfläche | `onClick` oder `addClickListener` | [Button](/docs/components/button) |
| `DocumentListener` für ein Textfeld | `onModify` | [Ereignisse](/docs/building-ui/events) |
| `ItemListener` für ein `JCheckBox` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| `ChangeListener` und Property-Change-Listener für Werte | `onValueChange` | [Ereignisse](/docs/building-ui/events) |
| `FocusListener` | `onFocus` und `onBlur` | [Ereignisse](/docs/building-ui/events) |
| `KeyListener` für ein Feld | `onKeypress` | [Ereignisse](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Ein Ereignis entfernen](/docs/building-ui/events#removing-an-event) |

## Der Event-Dispatch-Thread und UI-Aktualisierungen {#event-dispatch-thread-and-ui-updates}

Swing verlangt, dass alle UI-Arbeiten im Event-Dispatch-Thread ausgeführt werden. In webforJ gilt dieselbe Regel: Jede Benutzersitzung hat einen `Environment`-Thread, und alle UI-Aktualisierungen müssen in diesem Thread erfolgen. Konstruktoren, Event-Handler und Lifecycle-Methoden werden bereits dort ausgeführt. Ein Hintergrund-Thread muss UI-Änderungen mit `Environment.runLater(Environment, Runnable)` zurückgeben.

Lesen Sie das Ziel mit `Environment.getCurrent()` im `Environment`-Thread aus, bevor die Hintergrundarbeit beginnt. In einem Hintergrund-Thread gibt `getCurrent()` `null` zurück. Unter [Asynchrone Aktualisierungen](/docs/advanced/asynchronous-updates) erfahren Sie mehr über das Thread-Modell und das Abbrechen von Aufgaben mit `PendingResult`.

| Swing | webforJ | Referenz |
| --- | --- | --- |
| Event-Dispatch-Thread | `Environment`-Thread der Sitzung | [Asynchrone Aktualisierungen](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Asynchrone Aktualisierungen](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Hintergrundarbeit, zum Beispiel mit `CompletableFuture`, gefolgt von `Environment.runLater` | [Asynchrone Aktualisierungen](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Timer bei jedem Tastendruck neu starten | `Debouncer` | [Debouncing](/docs/advanced/debouncing) |

Der Inhalt von `doInBackground` in einem `SwingWorker` wird zur Hintergrundaufgabe, und die Methode `done` wird zu den `runLater`-Blöcken: einem für das Ergebnis und einem für einen Fehler:

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

## Modelle und Datenbindung {#models-and-data-binding}

Swing trennt Daten und Ansichten mithilfe von Modellschnittstellen wie `TableModel` und `ListModel` sowie durch Code, der Werte manuell zwischen Feldern und Objekten kopiert. webforJ verwendet ein `Repository` als Datenquelle für Komponenten wie `Table` und einen `BindingContext`, um Formularfelder bidirektional mit Bean-Eigenschaften zu verbinden.

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `TableModel` | `Repository`, zum Beispiel `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | `ListItem`-Einträge, die einem `ListBox`, `ChoiceBox` oder `ComboBox` hinzugefügt werden | [Listen](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | `commit()` und `commit(entity)` für das Repository | [Datensynchronisierung](/docs/advanced/repository/overview#data-synchronization) |
| Feldwerte in ein Objekt kopieren und daraus zurücklesen | `BindingContext` mit `read(bean)` und `write(bean)` | [Datenbindung](/docs/data-binding/overview), [Automatische Bindung](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Validatoren für Bindings oder Jakarta Validation | [Validierung](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` bindet Komponentenfelder an Bean-Eigenschaften mit demselben Namen:

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

## Dialoge {#dialogs}

Optionsdialoge sind modal und blockieren die Ausführung, bis der Benutzer antwortet – genau wie `JOptionPane`. Der Code nach dem Aufruf liest das Ergebnis direkt aus, ganz ohne Callback. Siehe [Optionsdialoge](/docs/components/option-dialogs/overview).

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Nachrichtendialog](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, gibt `ConfirmDialog.Result` zurück | [Bestätigungsdialog](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Eingabedialog](/docs/components/option-dialogs/input) |
| `JFileChooser` zum Öffnen einer Datei auf dem Computer des Benutzers | `OptionDialog.showFileUploadDialog` | [Datei-Upload-Dialog](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` zum Durchsuchen von Serverdateien | `OptionDialog.showFileChooserDialog` | [Dateiauswahldialog](/docs/components/option-dialogs/file-chooser) |
| `JDialog` mit benutzerdefiniertem Inhalt | `Dialog`, geöffnet mit `open()` und geschlossen mit `close()` | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Tabellen {#tables}

Ein `JTable` liest Daten aus einer `TableModel`-Unterklasse. Eine webforJ-`Table<T>` enthält eine Liste von Entitäten und bindet jede Spalte an einen Getter. Daher muss keine Modellklasse geschrieben werden. `setItems` verpackt eine Collection in ein Repository, und `setRepository` akzeptiert ein Repository, das auf Ihrer Datenschicht basiert. Siehe [Table](/docs/components/table/overview).

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `JTable` mit einem `TableModel` | `Table<T>` mit `setItems` oder `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` und `getValueAt` | `addColumn(id, getter)` | [Spalten](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Auswahl](/docs/components/table/selection) |
| `MouseListener`, der auf einen Doppelklick prüft | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Sortierung](/docs/components/table/sorting) |
| `RowFilter` | `setBaseFilter(Predicate)` für ein `CollectionRepository`, angewendet beim Aufruf von `commit()` | [Filtern](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer`, gesetzt mit `Column.setRenderer` | [Rendering](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navigation {#navigation}

Swing-Apps wechseln zwischen Bildschirmen, indem sie Panels austauschen, häufig mit `CardLayout`, oder neue Fenster öffnen. In webforJ entspricht jeder Bildschirm einer Route mit eigener URL, sodass Browserverlauf, Lesezeichen und Deep Links funktionieren. Siehe [Routing](/docs/routing/overview).

| Swing | webforJ | Referenz |
| --- | --- | --- |
| `CardLayout.show` oder das Inhalts-Panel ersetzen | `@Route`-Ansichten und `Router.getCurrent().navigate(View.class)` | [Routen definieren](/docs/routing/defining-routes), [Routennavigation](/docs/routing/route-navigation) |
| `JMenu` oder eine Seitenliste von Bildschirmen | `AppNav` mit `AppNavItem`-Einträgen, die mit Routen verknüpft sind | [AppNav](/docs/components/appnav) |
| Einen Bildschirm mit Parametern öffnen | Routenparameter und Query-Parameter | [Routenmuster](/docs/routing/route-patterns), [Query-Parameter](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Bildschirme schrittweise migrieren {#moving-screen-by-screen}

Die Swing-App muss nicht in einem einzigen Schritt neu geschrieben werden. Webswing führt eine bestehende Swing-, JavaFX- oder SWT-App unverändert im Browser aus, und die Komponente `WebswingConnector` bindet sie in eine webforJ-Ansicht ein. Nur für die Kommunikation in beide Richtungen sind kleine Ergänzungen an der Swing-App erforderlich, die weiter unten beschrieben werden. So können alte und neue Bildschirme in einer App nebeneinander bestehen, während sie nach und nach migriert werden.

Der Connector ermöglicht Kommunikation in beide Richtungen. `performAction` sendet Aktionen von webforJ an die Swing-App, und `onAction` empfängt Aktionen, die die Swing-App über die Webswing-Java-API sendet. Zum Zurücksenden von Aktionen muss diese API zur Swing-App hinzugefügt werden.

Auf der Swing-Seite stellt die Abhängigkeit `webswing-api` aus dem Webswing-Repository `WebswingUtil.getWebswingApi()` bereit. Wenn die App als normale Desktop-App ausgeführt wird, gibt die Methode `null` zurück. Derselbe Code funktioniert also weiterhin auf dem Desktop. In diesem Beispiel sendet ein Doppelklick auf eine Tabellenzeile den ausgewählten Kunden an webforJ, anstatt einen Swing-Dialog zu öffnen:

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) erklärt die Architektur und die schrittweise Modernisierung.
- [Einrichtung und Konfiguration](/docs/integrations/webswing/setup) behandelt den Webswing-Server und CORS.
- [Kommunikation](/docs/integrations/webswing/communication) behandelt `performAction` und `onAction`.
- Im [Modernisierungs-Tutorial](/docs/integrations/webswing/tutorial) wird ein Swing-Bearbeitungsdialog durch ein webforJ-Formular ersetzt, während die Swing-Tabelle bestehen bleibt.
