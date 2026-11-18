---
title: Overstappen vanaf Swing
sidebar_position: 5
description: >-
  Koppel Java Swing-concepten zoals JFrame, layout managers, listeners, de event
  dispatch thread, table models, JOptionPane en CardLayout aan hun equivalenten
  in webforJ.
_i18n_title_hash: 4334c9ef532372ad4ec7040646346991
_i18n_description_hash: bb61f39a493a0bd89b7e48af68c639d2
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
Deze pagina koppelt de concepten van een Java Swing-desktopapp aan hun tegenhangers in webforJ. In beide gevallen is de UI gewone Java: componenten zijn Java-objecten, gebeurtenissen worden afgehandeld door listeners en layouts worden in code samengesteld. webforJ draait op de server en het framework houdt de browser synchroon met de componentstatus. Zie [Component architecture](/docs/architecture/controls-components) voor meer informatie over hoe dat werkt.

De webforJ-API is vergelijkbaar met Swing, maar is er niet compatibel mee. Elk scherm wordt opnieuw geschreven met de webforJ-API. Klassen die niet afhankelijk zijn van `javax.swing` of `java.awt`, zoals services, entiteiten en validatieregels, kunnen ongewijzigd vanuit de nieuwe weergaven worden aangeroepen.

## Vensters en frames {#windows-and-frames}

Een Swing-app maakt een `JFrame`, voegt inhoud toe en toont het. In webforJ is de browserpagina het venster. De app-klasse breidt `App` uit en het framework biedt een `Frame` dat de zichtbare inhoud bevat. Als routering is ingeschakeld via `@Routify`, is elk scherm een klasse met de annotatie `@Route`, die de router in het frame weergeeft.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JFrame` | `App` en `Frame`, met schermen als `@Route`-klassen | [Routable apps](/docs/routing/routable-apps), [Routing](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` of `HasFrameTitle` | [Navigational frame titles](/docs/routing/navigational-frame-titles) |
| Hoofdmethode die de UI opbouwt | `App`-subklasse met `@Routify` | [App basics](/docs/introduction/basics) |

## Panelen en layoutmanagers {#panels-and-layout-managers}

Een `JPanel` met een layoutmanager wordt een `Composite` die is gekoppeld aan een layoutcomponent. `FlexLayout` rangschikt onderliggende componenten in een rij of kolom, met tussenruimte, uitlijning en terugloop, en is geschikt voor de meeste schermen die met `BoxLayout` of `FlowLayout` zijn gebouwd. Een vensterschil met `BorderLayout` komt overeen met `AppLayout`; andere panelen met `BorderLayout` komen overeen met geneste `FlexLayout`-containers.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JPanel`-subklasse | `Composite<T>` gekoppeld aan een container zoals `FlexLayout` of `Div` | [Composing components](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | `AppLayout` voor een vensterschil, geneste `FlexLayout`-containers voor andere panelen | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| Formulieren met `GridLayout`, `GridBagLayout` | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Hoofdvenster met koptekst, zijmenu en inhoudsgebied | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Een Swing-paneel met een tekstveld en een knop die reageert op klikken, wordt de volgende weergave:

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

## Componenten {#components}

De meeste Swing-componenten hebben een rechtstreeks equivalent. Tekstcomponenten gebruiken `setText()` voor hun bijschrift en invoercomponenten gebruiken `getValue()` en `setValue()` voor hun inhoud, zoals beschreven in [Using components](/docs/building-ui/using-components).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Using components](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` en de andere gemaskeerde velden | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` en `ButtonGroup` | `RadioButton` en `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` voor een vaste set, `ComboBox` als gebruikers een waarde kunnen typen | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Listeners en gebeurtenissen {#listeners-and-events}

Swing registreert listeners met methoden als `addXxxListener`. webforJ-componenten bieden hetzelfde patroon: een `addXxxListener`-methode en in de meeste gevallen een kortere `on`-alias. Elke registratie retourneert een `ListenerRegistration`. Het aanroepen van `remove()` daarop vervangt Swing's `removeXxxListener`. Zie [Events](/docs/building-ui/events).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `ActionListener` op een knop | `onClick` of `addClickListener` | [Button](/docs/components/button) |
| `DocumentListener` op een tekstveld | `onModify` | [Events](/docs/building-ui/events) |
| `ItemListener` op een `JCheckBox` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| `ChangeListener` en property-change-listeners op waarden | `onValueChange` | [Events](/docs/building-ui/events) |
| `FocusListener` | `onFocus` en `onBlur` | [Events](/docs/building-ui/events) |
| `KeyListener` op een veld | `onKeypress` | [Events](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Removing an event](/docs/building-ui/events#removing-an-event) |

## De event-dispatchthread en UI-updates {#event-dispatch-thread-and-ui-updates}

Swing vereist dat al het UI-werk op de event-dispatchthread wordt uitgevoerd. In webforJ geldt dezelfde regel: elke gebruikerssessie heeft een `Environment`-thread en alle UI-updates moeten daarop plaatsvinden. Constructors, eventhandlers en lifecycle-methoden worden al op die thread uitgevoerd. Een achtergrondthread moet UI-wijzigingen terugsturen met `Environment.runLater(Environment, Runnable)`.

Lees het doel op de `Environment`-thread met `Environment.getCurrent()`, voordat het achtergrondwerk begint. Op een achtergrondthread retourneert `getCurrent()` `null`. Zie [Asynchronous updates](/docs/advanced/asynchronous-updates) voor het threadmodel en het annuleren van taken via `PendingResult`.

| Swing | webforJ | Reference |
| --- | --- | --- |
| Event-dispatchthread | `Environment`-thread van de sessie | [Asynchronous updates](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Asynchronous updates](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Achtergrondwerk, bijvoorbeeld `CompletableFuture`, gevolgd door `Environment.runLater` | [Asynchronous updates](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Een timer bij elke toetsaanslag opnieuw starten | `Debouncer` | [Debouncing](/docs/advanced/debouncing) |

De `doInBackground`-body van een `SwingWorker` wordt de achtergrondtaak. De `done`-methode wordt de `runLater`-blokken: één voor het resultaat en één voor een fout:

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

## Modellen en databinding {#models-and-data-binding}

Swing scheidt gegevens van weergaven met modelinterfaces zoals `TableModel` en `ListModel`, en met code die waarden handmatig tussen velden en objecten kopieert. webforJ gebruikt een `Repository` als gegevensbron voor componenten zoals `Table`, en een `BindingContext` om formuliervelden in beide richtingen aan bean-properties te koppelen.

| Swing | webforJ | Reference |
| --- | --- | --- |
| `TableModel` | `Repository`, zoals `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | `ListItem`-items die aan een `ListBox`, `ChoiceBox` of `ComboBox` worden toegevoegd | [Lists](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | `commit()` en `commit(entity)` op de repository | [Data synchronization](/docs/advanced/repository/overview#data-synchronization) |
| Veldwaarden kopiëren van en naar een object | `BindingContext` met `read(bean)` en `write(bean)` | [Data binding](/docs/data-binding/overview), [Automatic binding](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Validators op bindings of Jakarta Validation | [Validation](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` koppelt componentvelden aan bean-properties met dezelfde naam:

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

## Dialogen {#dialogs}

Optiedialogen zijn modaal en blokkeren de uitvoering totdat de gebruiker reageert, net als `JOptionPane`. De code na de aanroep leest het resultaat rechtstreeks uit, zonder callback. Zie [Option dialogs](/docs/components/option-dialogs/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Message dialog](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, retourneert `ConfirmDialog.Result` | [Confirm dialog](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Input dialog](/docs/components/option-dialogs/input) |
| `JFileChooser` om een bestand op de computer van de gebruiker te openen | `OptionDialog.showFileUploadDialog` | [File upload dialog](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` om serverbestanden te doorzoeken | `OptionDialog.showFileChooserDialog` | [File chooser dialog](/docs/components/option-dialogs/file-chooser) |
| `JDialog` met aangepaste inhoud | `Dialog`, geopend met `open()` en gesloten met `close()` | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Tabellen {#tables}

Een `JTable` leest gegevens uit een subklasse van `TableModel`. Een webforJ `Table<T>` bevat een lijst met entiteiten en koppelt elke kolom aan een getter, zodat je geen modelklasse hoeft te schrijven. `setItems` verpakt een collectie in een repository, terwijl `setRepository` een repository accepteert die op je datalaag is gebaseerd. Zie [Table](/docs/components/table/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `JTable` met een `TableModel` | `Table<T>` met `setItems` of `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` en `getValueAt` | `addColumn(id, getter)` | [Columns](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Selection](/docs/components/table/selection) |
| `MouseListener` die controleert op dubbelklikken | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Sorting](/docs/components/table/sorting) |
| `RowFilter` | `setBaseFilter(Predicate)` op een `CollectionRepository`, toegepast wanneer je `commit()` aanroept | [Filtering](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer` ingesteld met `Column.setRenderer` | [Rendering](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navigatie {#navigation}

Swing-apps wisselen van scherm door panelen te verwisselen, vaak met `CardLayout`, of door nieuwe vensters te openen. In webforJ is elk scherm een route met een eigen URL, zodat browsergeschiedenis, bladwijzers en deep links werken. Zie [Routing](/docs/routing/overview).

| Swing | webforJ | Reference |
| --- | --- | --- |
| `CardLayout.show` of het inhoudspaneel vervangen | `@Route`-weergaven en `Router.getCurrent().navigate(View.class)` | [Defining routes](/docs/routing/defining-routes), [Route navigation](/docs/routing/route-navigation) |
| `JMenu` of een zijlijst met schermen | `AppNav` met `AppNavItem`-items die aan routes zijn gekoppeld | [AppNav](/docs/components/appnav) |
| Een scherm openen met parameters | Routeparameters en queryparameters | [Route patterns](/docs/routing/route-patterns), [Query parameters](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Scherm voor scherm migreren {#moving-screen-by-screen}

De Swing-app hoeft niet in één keer te worden herschreven. Webswing voert een bestaande Swing-, JavaFX- of SWT-app ongewijzigd in de browser uit, en de component `WebswingConnector` sluit deze in een webforJ-weergave in. Alleen voor communicatie in twee richtingen is een kleine toevoeging aan de Swing-app nodig, zoals hieronder beschreven. Zo kunnen oude en nieuwe schermen naast elkaar in één app bestaan terwijl schermen één voor één worden gemigreerd.

De connector werkt in twee richtingen. `performAction` verzendt acties van webforJ naar de Swing-app, en `onAction` ontvangt acties die de Swing-app via de Webswing Java-API verzendt. Om acties terug te sturen, moet die API aan de Swing-app worden toegevoegd.

Aan de Swing-kant biedt de afhankelijkheid `webswing-api` uit de Webswing-repository `WebswingUtil.getWebswingApi()`. Deze retourneert `null` wanneer de app als gewone desktopapp draait, zodat dezelfde code op de desktop blijft werken. In dit voorbeeld stuurt dubbelklikken op een tabelrij de geselecteerde klant naar webforJ in plaats van een Swing-dialoog te openen:

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) beschrijft de architectuur en stapsgewijze modernisering.
- [Setup and configuration](/docs/integrations/webswing/setup) behandelt de Webswing-server en CORS.
- [Communication](/docs/integrations/webswing/communication) behandelt `performAction` en `onAction`.
- [Modernization tutorial](/docs/integrations/webswing/tutorial) vervangt een Swing-bewerkingsdialoog door een webforJ-formulier, terwijl de Swing-tabel blijft staan.
