---
title: Swingistä tuleville
sidebar_position: 5
description: >-
  Map Java Swing concepts such as JFrame, layout managers, listeners, the event
  dispatch thread, table models, JOptionPane, and CardLayout to their webforJ
  counterparts.
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
Tällä sivulla Java Swing -työpöytäsovelluksen käsitteet yhdistetään niitä vastaaviin webforJ-käsitteisiin. Kummassakin käyttöliittymä kirjoitetaan tavallisena Javana: komponentit ovat Java-olioita, tapahtumia käsitellään kuuntelijoilla ja asettelut koostetaan koodissa. webforJ toimii palvelimella, ja kehys pitää selaimen synkronoituna komponenttien tilan kanssa. Katso, miten tämä toimii, kohdasta [Komponenttiarkkitehtuuri](/docs/architecture/controls-components).

webforJ-rajapinta vastaa periaatteiltaan Swingiä, mutta ei ole sen kanssa yhteensopiva. Jokainen näkymä kirjoitetaan uudelleen webforJ-rajapintaa käyttäen. Luokkia, jotka eivät riipu `javax.swing`- tai `java.awt`-paketeista, kuten palveluita, entiteettejä ja validointisääntöjä, kutsutaan uusista näkymistä sellaisinaan.

## Ikkunat ja kehykset {#windows-and-frames}

Swing-sovellus luo `JFrame`-kehyksen, lisää siihen sisältöä ja näyttää sen. webforJ:ssä selainikkuna toimii ikkunana. Sovellusluokka laajentaa `App`-luokkaa, ja kehys tarjoaa näkyvän sisällön säilyttävän `Frame`-kehyksen. Kun reititys on otettu käyttöön `@Routify`-merkinnällä, kukin näkymä on `@Route`-merkinnällä varustettu luokka, jonka reititin renderöi kehykseen.

| Swing | webforJ | Viite |
| --- | --- | --- |
| `JFrame` | `App` ja `Frame`; näkymät ovat `@Route`-luokkia | [Reititettävät sovellukset](/docs/routing/routable-apps), [Reititys](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` tai `HasFrameTitle` | [Navigointikehyksen otsikot](/docs/routing/navigational-frame-titles) |
| Käyttöliittymän rakentava päämetodi | `App`-aliluokka, jossa on `@Routify` | [Sovelluksen perusteet](/docs/introduction/basics) |

## Paneelit ja asettelunhallintaohjelmat {#panels-and-layout-managers}

Asettelunhallintaohjelmaa käyttävä `JPanel` muuttuu `Composite`-komponentiksi, joka on sidottu asettelukomponenttiin. `FlexLayout` järjestää lapset riviin tai sarakkeeseen ja tukee välejä, kohdistusta ja rivitystä. Se sopii useimpien `BoxLayout`- tai `FlowLayout`-asettelulla rakennettujen näkymien tilalle. `BorderLayout`-ikkunakehys vastaa `AppLayout`-komponenttia, ja muut `BorderLayout`-paneelit vastaavat sisäkkäisiä `FlexLayout`-säiliöitä.

| Swing | webforJ | Viite |
| --- | --- | --- |
| `JPanel`-aliluokka | `Composite<T>`, joka on sidottu säiliöön, kuten `FlexLayout`- tai `Div`-komponenttiin | [Komponenttien koostaminen](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | Ikkunakehyksessä `AppLayout`, muissa paneeleissa sisäkkäiset `FlexLayout`-säiliöt | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| `GridLayout`- ja `GridBagLayout`-lomakkeet | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Pääikkuna, jossa on otsake, sivuvalikko ja sisältöalue | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Swing-paneeli, jossa on tekstikenttä ja napsautuksiin reagoiva painike, muuttuu seuraavaksi näkymäksi:

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

## Komponentit {#components}

Useimmille Swing-komponenteille on suora vastine. Tekstikomponenteissa käytetään otsikkotekstiin `setText()`-metodia, ja syötekomponenteissa sisältöön käytetään `getValue()`- ja `setValue()`-metodeja, kuten kohdassa [Komponenttien käyttö](/docs/building-ui/using-components) kuvataan.

| Swing | webforJ | Viite |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Komponenttien käyttö](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` ja muut peitetyt kentät | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` ja `ButtonGroup` | `RadioButton` ja `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` kiinteälle valikoimalle, `ComboBox`, kun käyttäjä voi kirjoittaa arvon | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Kuuntelijat ja tapahtumat {#listeners-and-events}

Swingissä kuuntelijat rekisteröidään `addXxxListener`-metodeilla. webforJ-komponentit noudattavat samaa mallia: niissä on `addXxxListener`-metodi ja useimmissa tapauksissa myös lyhyempi `on`-alias. Kukin rekisteröinti palauttaa `ListenerRegistration`-olion, ja sen `remove()`-metodin kutsuminen vastaa Swingin `removeXxxListener`-metodia. Katso lisätietoja kohdasta [Tapahtumat](/docs/building-ui/events).

| Swing | webforJ | Viite |
| --- | --- | --- |
| Painikkeen `ActionListener` | `onClick` tai `addClickListener` | [Button](/docs/components/button) |
| Tekstikentän `DocumentListener` | `onModify` | [Tapahtumat](/docs/building-ui/events) |
| `JCheckBox`-komponentin `ItemListener` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| Arvojen `ChangeListener` ja ominaisuuksien muutoskuuntelijat | `onValueChange` | [Tapahtumat](/docs/building-ui/events) |
| `FocusListener` | `onFocus` ja `onBlur` | [Tapahtumat](/docs/building-ui/events) |
| Kentän `KeyListener` | `onKeypress` | [Tapahtumat](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Tapahtuman poistaminen](/docs/building-ui/events#removing-an-event) |

## Tapahtumien käsittelysäie ja käyttöliittymän päivitykset {#event-dispatch-thread-and-ui-updates}

Swing edellyttää, että kaikki käyttöliittymään liittyvä työ tehdään tapahtumien käsittelysäikeessä. webforJ:ssä pätee sama sääntö: kullakin käyttäjäistunnolla on `Environment`-säie, ja kaikki käyttöliittymän päivitykset on tehtävä siinä. Konstruktorit, tapahtumankäsittelijät ja elinkaarimetodit suoritetaan jo kyseisessä säikeessä. Taustasäikeen on siirrettävä käyttöliittymään kohdistuvat muutokset takaisin kutsumalla `Environment.runLater(Environment, Runnable)`-metodia.

Hae kohde `Environment.getCurrent()`-metodilla `Environment`-säikeessä ennen taustatyön aloittamista. Taustasäikeessä `getCurrent()` palauttaa arvon `null`. Katso säiemallin ja `PendingResult`-tehtävien peruutuksen lisätiedot kohdasta [Asynkroniset päivitykset](/docs/advanced/asynchronous-updates).

| Swing | webforJ | Viite |
| --- | --- | --- |
| Tapahtumien käsittelysäie | Istunnon `Environment`-säie | [Asynkroniset päivitykset](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Asynkroniset päivitykset](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Taustatyö, esimerkiksi `CompletableFuture`, ja sen jälkeen `Environment.runLater` | [Asynkroniset päivitykset](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Ajastimen käynnistäminen uudelleen jokaisen näppäinpainalluksen yhteydessä | `Debouncer` | [Debouncing](/docs/advanced/debouncing) |

`SwingWorker`-luokan `doInBackground`-runko muuttuu taustatehtäväksi, ja `done`-metodi muuttuu `runLater`-lohkoiksi: yhdeksi tulosta ja toiseksi virhettä varten:

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

## Mallit ja tietojen sidonta {#models-and-data-binding}

Swing erottaa tiedot näkymistä mallirajapintojen, kuten `TableModel`- ja `ListModel`-rajapintojen, avulla. Lisäksi koodi kopioi arvoja kenttien ja olioiden välillä käsin. webforJ käyttää `Repository`-tietolähdettä esimerkiksi `Table`-komponenteille ja `BindingContext`-kontekstia lomakekenttien ja papuominaisuuksien yhdistämiseen molempiin suuntiin.

| Swing | webforJ | Viite |
| --- | --- | --- |
| `TableModel` | `Repository`, kuten `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | `ListBox`-, `ChoiceBox`- tai `ComboBox`-komponenttiin lisätyt `ListItem`-alkiot | [Listat](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | Tietovaraston `commit()`- ja `commit(entity)`-metodit | [Tietojen synkronointi](/docs/advanced/repository/overview#data-synchronization) |
| Kenttäarvojen kopiointi olioon ja takaisin | `BindingContext` sekä `read(bean)`- ja `write(bean)`-metodit | [Tietojen sidonta](/docs/data-binding/overview), [Automaattinen sidonta](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Sidosten validoijat tai Jakarta Validation | [Validointi](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` sitoo komponenttien kentät samannimisiin papuominaisuuksiin:

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

## Valintaikkunat {#dialogs}

Valintaikkunat ovat modaalisia ja pysäyttävät suorituksen, kunnes käyttäjä vastaa, kuten `JOptionPane`. Kutsun jälkeinen koodi voi lukea tuloksen suoraan ilman takaisinkutsua. Katso lisätietoja kohdasta [Valintaikkunat](/docs/components/option-dialogs/overview).

| Swing | webforJ | Viite |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Viesti-ikkuna](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, joka palauttaa `ConfirmDialog.Result`-arvon | [Vahvistusikkuna](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Syöteikkuna](/docs/components/option-dialogs/input) |
| `JFileChooser` käyttäjän koneella olevan tiedoston avaamiseen | `OptionDialog.showFileUploadDialog` | [Tiedoston latausikkuna](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` palvelimen tiedostojen selaamiseen | `OptionDialog.showFileChooserDialog` | [Tiedostonvalintaikkuna](/docs/components/option-dialogs/file-chooser) |
| `JDialog`, jossa on mukautettua sisältöä | `Dialog`, joka avataan `open()`- ja suljetaan `close()`-metodilla | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Taulukot {#tables}

`JTable` lukee tietoja `TableModel`-aliluokasta. webforJ:n `Table<T>` sisältää entiteettiluettelon ja sitoo kunkin sarakkeen hakijametodiin, joten malliluokkaa ei tarvitse kirjoittaa. `setItems` käärii kokoelman tietovarastoon, ja `setRepository` ottaa vastaan tietovaraston, jonka taustalla on oma datakerroksesi. Katso lisätietoja kohdasta [Table](/docs/components/table/overview).

| Swing | webforJ | Viite |
| --- | --- | --- |
| `JTable` ja `TableModel` | `Table<T>` sekä `setItems` tai `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` ja `getValueAt` | `addColumn(id, getter)` | [Sarakkeet](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Valinta](/docs/components/table/selection) |
| Kaksoisnapsautuksen tarkistava `MouseListener` | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Lajittelu](/docs/components/table/sorting) |
| `RowFilter` | `CollectionRepository`-tietovaraston `setBaseFilter(Predicate)`, jota sovelletaan, kun kutsut `commit()`-metodia | [Suodatus](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer`, joka asetetaan `Column.setRenderer`-metodilla | [Renderöinti](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navigointi {#navigation}

Swing-sovellukset vaihtavat näkymiä korvaamalla paneeleita, usein `CardLayout`-asettelun avulla, tai avaamalla uusia ikkunoita. webforJ:ssä jokaisella näkymällä on oma URL-osoitteensa, joten selaushistoria, kirjanmerkit ja syvälinkit toimivat. Katso lisätietoja kohdasta [Reititys](/docs/routing/overview).

| Swing | webforJ | Viite |
| --- | --- | --- |
| `CardLayout.show` tai sisältöpaneelin vaihtaminen | `@Route`-näkymät ja `Router.getCurrent().navigate(View.class)` | [Reittien määrittäminen](/docs/routing/defining-routes), [Reittinavigointi](/docs/routing/route-navigation) |
| `JMenu` tai näkymien sivuluettelo | `AppNav` ja `AppNavItem`-alkiot, jotka linkittävät reitteihin | [AppNav](/docs/components/appnav) |
| Näkymän avaaminen parametreilla | Reittiparametrit ja kyselyparametrit | [Reittikuviot](/docs/routing/route-patterns), [Kyselyparametrit](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Näkymä kerrallaan siirtyminen {#moving-screen-by-screen}

Swing-sovellusta ei tarvitse kirjoittaa uudelleen kerralla. Webswing ajaa olemassa olevaa Swing-, JavaFX- tai SWT-sovellusta selaimessa ilman lähdekoodiin tehtäviä muutoksia, ja `WebswingConnector`-komponentti upottaa sen webforJ-näkymään. Swing-sovellukseen tarvitaan vain pieni lisäys kaksisuuntaiseen viestintään; sitä käsitellään alla. Vanhat ja uudet näkymät toimivat sitten samassa sovelluksessa, kun näkymiä siirretään yksi kerrallaan.

Yhdistin tukee viestintää molempiin suuntiin. `performAction` lähettää toimintoja webforJ:stä Swing-sovellukseen, ja `onAction` vastaanottaa Swing-sovelluksen Webswing Java API:n kautta lähettämiä toimintoja. Toimintojen lähettäminen takaisin edellyttää kyseisen API:n lisäämistä Swing-sovellukseen.

Swing-puolella Webswing-tietovaraston `webswing-api`-riippuvuus tarjoaa `WebswingUtil.getWebswingApi()`-metodin. Se palauttaa arvon `null`, kun sovellus suoritetaan tavallisena työpöytäsovelluksena, joten sama koodi toimii edelleen myös työpöydällä. Tässä esimerkissä taulukkorivin kaksoisnapsautus lähettää valitun asiakkaan webforJ:hin sen sijaan, että avaisi Swing-valintaikkunan:

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) esittelee arkkitehtuurin ja vaiheittaisen modernisoinnin.
- [Asennus ja määritys](/docs/integrations/webswing/setup) käsittelee Webswing-palvelinta ja CORS-asetuksia.
- [Viestintä](/docs/integrations/webswing/communication) käsittelee `performAction`- ja `onAction`-metodeja.
- [Modernisoinnin opas](/docs/integrations/webswing/tutorial) korvaa Swingin muokkausikkunan webforJ-lomakkeella, mutta Swing-taulukko säilyy.
