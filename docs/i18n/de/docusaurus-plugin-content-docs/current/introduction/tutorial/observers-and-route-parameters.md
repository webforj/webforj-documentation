---
title: Observer und Routenparameter
sidebar_position: 5
description: Step 4 - Use route parameters to control what content loads.
_i18n_hash: c87796ee04dafe840b3903ae8a1fa0ab
---
Die App aus [Routing und Composites](/docs/introduction/tutorial/routing-and-composites) kann nur neue Kunden zur Datenbank hinzufügen. Mit den folgenden Konzepten geben Sie den Nutzern die Möglichkeit, auch die Daten vorhandener Kunden zu bearbeiten:

- Routenmuster
- Übergeben von Parameterwerten über eine URL
- Lebenszyklusbeobachter

Das Abschließen dieses Schrittes erstellt eine Version von [4-observers-and-route-parameters](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters).

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [4-observers-and-route-parameters](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält. Dies ist `4-observers-and-route-parameters`, wenn Sie mit der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Beim Ausführen der App wird automatisch ein neuer Browser unter `http://localhost:8080` geöffnet.

## Verwendung der Kunden-ID {#using-the-customers-id}

Um `FormView` zu verwenden, um vorhandene Kunden zu bearbeiten, benötigen Sie eine Möglichkeit, ihr zu sagen, welchen Kunden sie bearbeiten soll. Sie können dies tun, indem Sie einen anfänglichen Parameter an `FormView` übergeben, der die Kunden-ID darstellt. In [Working with Data](/docs/introduction/tutorial/working-with-data) haben Sie eine `Customer`-Entität erstellt, die einen numerischen `Long`-Wert als eindeutige `id` zu Kunden zuweist, wenn diese zur Datenbank hinzugefügt werden.

```java
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
```

In diesem Schritt werden Sie Änderungen an `FormView` vornehmen, damit es eine `id` als anfänglichen Parameter verwendet, bevor irgendetwas geladen wird. Dann wird `FormView` die `id` auswerten, um zu bestimmen, ob das Formular zum Hinzufügen eines neuen Kunden oder zum Aktualisieren eines vorhandenen Kunden dient. Schließlich werden Sie `MainView` ändern, damit es beim Navigieren zu `FormView` einen `id`-Wert sendet.

## Hinzufügen eines Routenmusters zu `FormView` {#adding-a-route-pattern}

Im vorherigen Schritt wurde die Route in `FormView` auf `@Route(customer)` gesetzt, wodurch die Klasse lokal auf `http://localhost:8080/customer` abgebildet wird. Hinzufügen eines Routenmusters ermöglicht es Ihnen, eine `id` als anfänglichen Parameter an `FormView` hinzuzufügen.

Ein [Routenmuster](/docs/routing/route-patterns) erlaubt es Ihnen, einen Parameter in der URL hinzuzufügen, ihn optional zu machen und Einschränkungen für gültige Muster festzulegen. Mit der `@Route`-Annotation sieht das, was `id` zu einem optionalen Routenparameter für `FormView` macht, so aus:

- **`/:id`** gibt der Route einen benannten Parameter von `id`, sodass der Zugriff auf `http://localhost:8080/customer/6` `FormView` mit einem `id`-Parameter von `6` lädt.

- **`?`** macht den `id`-Parameter optional. Standardmäßig sind Parameter erforderlich, aber das Optional machen des `id` ermöglicht es Ihnen, `FormView` zum Hinzufügen neuer Kunden zu verwenden, die noch keine `id` haben.

- **`<[0-9]+>`** schränkt `id` ein, um eine positive Zahl zu sein. In spitzen Klammern `<>` können Sie eine Einschränkung als regulären Ausdruck für den Parameter hinzufügen. Wenn die `id` nicht der Einschränkung entspricht, z.B. `http://localhost:8080/customer/john-smith`, wird der Benutzer zu einer 404-Seite weitergeleitet.

Um den optionalen Routenparameter zu `FormView` hinzuzufügen, ändern Sie die `@Route`-Annotation wie folgt:

```java
@Route("customer/:id?<[0-9]+>")
```

## Routing zu `FormView` {#routing-to-formview}

`FormView` akzeptiert jetzt einen optionalen `id`-Parameter und lädt nur, wenn die `id` eine ganze positive Zahl ist.

Allerdings kann `FormView` auch geladen werden, wenn ein Benutzer manuell eine URL für einen nicht vorhandenen Kunden eingibt, wie `http://localhost:8080/customer/5000`. Das Hinzufügen eines Lebenszyklusbeobachters vor dem Betreten von `FormView` ermöglicht es Ihrer App zu bestimmen, wie der eingehende `id`-Wert behandelt werden soll.

### Bedingtes Routing {#conditional-routing}

Lebenszyklusbeobachter erlauben es Komponenten, auf Lebensereignisse zu bestimmten Zeitpunkten zu reagieren. Der Artikel [Lifecycle Observers](/docs/routing/navigation-lifecycle/observers) listet verfügbare Beobachter auf, aber in diesem Schritt wird nur der `WillEnterObserver` verwendet.

Der `WillEnterObserver` wird aufgerufen, bevor das Routing der Komponente abgeschlossen ist. Mit diesem Beobachter können Sie den eingehenden `id` evaluieren. Wenn die `id` nicht mit einem vorhandenen Kunden übereinstimmt, können Sie den Benutzer zurück zur `MainView` umleiten, um einen gültigen Kunden zu finden, den Sie bearbeiten können.

Bevor wir über den Code für den `WillEnterObserver` sprechen, zeigt das folgende Flussdiagramm die möglichen Ergebnisse beim Routing zu `FormView`:

```mermaid
flowchart TD
    A[Gehe zu FormView] --> B{Gibt es einen id-Parameter?}
    B -->|Nein| C[Gehe zu einem leeren FormView]
    B -->|Ja| D{Entspricht dieser id-Wert einer Kunden-ID?}
    D -->|Ja| E[Gehe zu einem ausgefüllten FormView]
    D -->|Nein| F[Umleiten zu MainView]
```

### Verwendung des `WillEnterObserver` {#using-the-willenterobserver}

Die Verwendung des Lebenszyklusbeobachters, der vor dem vollständigen Laden der Komponente ausgelöst wird, `WillEnterObserver`, ermöglicht es Ihnen, Bedingungen hinzuzufügen, um zu bestimmen, ob die App zu `FormView` fortfahren soll oder ob Benutzer zur `MainView` umgeleitet werden müssen.

Jeder Lebenszyklusbeobachter ist ein Interface, also implementieren Sie `WillEnterObserver` als Teil der Deklaration für `FormView`:

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
```

Der `WillEnterObserver` hat die Methode `onWillEnter()`, die webforJ vor dem Routing zur Komponente aufruft. Diese Methode hat zwei Parameter: das `WillEnterEvent` und das `ParametersBag`.

Das `WillEnterEvent` entscheidet, ob das Routing zur Komponente mit der Methode `accept()` fortgesetzt oder das Routing mit der Methode `reject()` gestoppt wird. Nachdem das aktuelle Routing abgelehnt wurde, müssen Sie den Benutzer irgendwo anders umleiten.

Das `ParametersBag` enthält die Routerparameter aus der URL. Sie verwenden das `ParametersBag` im nächsten Abschnitt, um die bedingte Logik für `onWillEnter()` unter Verwendung des `id`-Parameters zu erstellen.

Das folgende `onWillEnter()` ist ein Beispiel mit nur zwei Ergebnissen:

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Bedingte Logik hinzufügen
  if (<bedingung>) {

    //Routing zu FormView fortsetzen
    event.accept();

  } else {

    //Routing zu FormView stoppen
    event.reject();

    //Benutzer zu MainView umleiten
    navigateToMain();
  }
}
```

### Verwendung des `ParametersBag` {#using-the-parametersbag}

Wie im vorherigen Abschnitt kurz erwähnt, enthält das `ParametersBag` den Routerparameter aus der URL. Jeder Lebenszyklusbeobachter hat Zugriff auf dieses Objekt, und die Verwendung in Ihrer App ermöglicht es Ihnen, den `id`-Wert abzurufen.

Das `ParametersBag`-Objekt bietet mehrere Abfragemethoden, um einen Parameter als speziellen Objekttyp abzurufen. Zum Beispiel kann `getInt()` einen Parameter als `Integer` abrufen.

Da einige Parameter jedoch optional sind, gibt `getInt()` tatsächlich `Optional<Integer>` zurück. Die Verwendung der Methode `ifPresentOrElse()` auf `Optional<Integer>` ermöglicht es Ihnen, eine Variable mithilfe von `Integer` festzulegen.

Wenn keine `id` vorhanden ist, kann der Benutzer zu `FormView` fortfahren, um einen neuen Kunden hinzuzufügen.

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Bestimmen, welchen Parameter abzurufen ist, und überprüfen, ob er vorhanden ist oder nicht
  parameters.getInt("id").ifPresentOrElse(id -> {

    // Verwenden Sie die id als Variable
    customerId = Long.valueOf(id);

  // Wenn keine id vorhanden ist, fahren Sie mit FormView für einen neuen Kunden fort
  }, () -> event.accept());

}
```

### Ist die `id` gültig? {#is-the-id-valid}

Aktuell akzeptiert der `WillEnterObserver` aus dem letzten Abschnitt nur das Routing, wenn keine `id` vorhanden ist. Der Beobachter muss eine weitere Überprüfung durchführen, bevor er zu `FormView` fortsetzt: Überprüfen Sie, ob die `id` mit einem vorhandenen Kunden übereinstimmt.

Jetzt kann `FormView` `CustomerService` verwenden, um die Existenz eines Kunden mit der Methode `doesCustomerExist()` zu bestätigen. Wenn es keinen Treffer gibt, kann die App das aktuelle Routing ablehnen und den Benutzer mit `navigateToMain()` zur `MainView` umleiten.

Wenn eine gültige `id` vorliegt, kann die App `accept()` verwenden, um das Routing zu `FormView` fortzusetzen. Erstellen Sie eine Methode `fillForm()`, um die Variable `customer` dem Kunden mit der entsprechenden `id` in der Datenbank zuzuweisen und die Werte der Felder festzulegen:

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  firstName.setValue(customer.getFirstName());
  lastName.setValue(customer.getLastName());
  company.setValue(customer.getCompany());
  country.selectKey(customer.getCountry());
}
```

Wie beim Hinzufügen eines neuen Kunden ermöglicht die Verwendung der Arbeitskopie den Nutzern, Kundendaten in der UI zu bearbeiten, ohne das Repository direkt zu ändern.

### Vollständiges `onWillEnter()` {#completed-onwillenter}

Die letzten beiden Abschnitte behandelten im Detail, wie mit jedem Ergebnis für das Routing in `FormView` mit dem `ParametersBag` und dem `CustomerService` umgegangen wird.

Das folgende ist das vollständige `onWillEnter()` für `FormView`, das das `ParametersBag` verwendet, um entweder die eingehende Route abzulehnen oder zu akzeptieren, und andere Methoden aufruft, um entweder das Formular auszufüllen oder den Benutzer zur `MainView` zu senden:

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Bestimmen, welchen Parameter abzurufen ist, und überprüfen, ob er vorhanden ist oder nicht
  parameters.getInt("id").ifPresentOrElse(id -> {
    customerId = Long.valueOf(id);

    // Überprüfen, ob es einen Kunden mit dieser ID gibt
    if (customerService.doesCustomerExist(customerId)) {
      // Dieser Kunde existiert, also fortfahren zu FormView und die Felder mit der ID initialisieren
      event.accept();
      fillForm(customerId);
    } else {
      // Dieser Kunde existiert nicht, also umleiten zu MainView
      event.reject();
      navigateToMain();
    }

  // Es war keine ID vorhanden, also fortfahren zu FormView für einen neuen Kunden
  }, () -> event.accept());

}
```

## Hinzufügen oder Bearbeiten eines Kunden {#adding-or-editing-a-customer}

Die vorherige Version dieser App fügte nur neue Kunden hinzu, wenn der Benutzer das Formular absendete. Da Nutzer nun vorhandene Kunden bearbeiten können, muss die Methode `submitCustomer()` überprüfen, ob der Kunde bereits existiert, bevor die Datenbank aktualisiert wird.

Anfänglich war es nicht nötig, eine Variable für die Kunden-ID in `FormView` zuzuweisen, da neuen Kunden beim Einreichen in die Datenbank eine eindeutige `id` zugewiesen wird. Wenn Sie jedoch `customerId` als anfängliche Variable in `FormView` mit einem nicht verwendeten `id`-Wert deklarieren, bleibt sie für neue Kunden unberührt und wird in `onWillEnter()` für vorhandene überschrieben.

Dies ermöglicht Ihnen die Verwendung von `doesCustomerExist()`, um zu überprüfen, ob ein neuer Kunde hinzugefügt oder ein vorhandener aktualisiert werden soll.

```java
private Long customerId = 0L;

//...

private void submitCustomer() {
  if (customerService.doesCustomerExist(customerId)) {
    customerService.updateCustomer(customer);
  } else {
    customerService.createCustomer(customer);
  }
  navigateToMain();
}
```

## Vollständiges `FormView` {#completed-formview}

So sollte `FormView` aussehen, jetzt, da es das Bearbeiten vorhandener Kunden handhaben kann:

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Kundenformular")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));
  private ChoiceBox country = new ChoiceBox("Land",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
  private Button submit = new Button("Absenden", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    fillCountries();
    setColumnsLayout();
    self.setMaxWidth(600)
        .addClassName("card")
        .add(layout);
    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");
  }

  private void setColumnsLayout() {
    List<Breakpoint> breakpoints = List.of(
        new Breakpoint(600, 2));
    layout.setSpacing("var(--dwc-space-l)")
        .setBreakpoints(breakpoints);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

  private void submitCustomer() {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(id -> {
      customerId = Long.valueOf(id);
      if (customerService.doesCustomerExist(customerId)) {
        event.accept();
        fillForm(customerId);
      } else {
        event.reject();
        navigateToMain();
      }

    }, () -> event.accept());
  }

  public void fillForm(Long customerId) {
    customer = customerService.getCustomerByKey(customerId);
    firstName.setValue(customer.getFirstName());
    lastName.setValue(customer.getLastName());
    company.setValue(customer.getCompany());
    country.selectKey(customer.getCountry());
  }
}
```

## Navigieren von `MainView` zu `FormView`, um Kunden zu bearbeiten {#navigating-from-mainview-to-formview-to-edit-customers}

Früher in diesem Schritt haben Sie ein vorhandenes `ParametersBag` verwendet, um den Wert einer `id` zu bestimmen. Das Erstellen eines neuen `ParametersBag` erlaubt es Ihnen, direkt zwischen Klassen mit den Parametern Ihrer Wahl zu navigieren. Die Verwendung der Daten in der `Table` ist eine praktikable Option, um die Benutzer zu `FormView` mit einer Kunden-ID zu senden.

Ähnlich wie bei der Schaltfläche ermöglicht es das Binden der Navigation an eine vom Benutzer gewählte Aktion, dass der Benutzer entscheidet, wann er zu `FormView` gehen möchte. Das Hinzufügen eines Ereignis hörenden zu der `Table` ermöglicht es Ihnen, den Benutzer zu `FormView` mit einem `ParametersBag` zu senden:

```java
table.addItemClickListener(this::editCustomer);

private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
      ParametersBag.of("id=" + e.getItemKey()));
}
```

Der Schlüssel der `Table`-Elemente wird jedoch standardmäßig automatisch generiert. Sie können explizit jeden Schlüssel so konfigurieren, dass er einer Kunden-ID entspricht, indem Sie die Methode `setKeyProvider()` verwenden:

```java
table.setKeyProvider(Customer::getId);
```

Fügen Sie in `MainView` die Methoden `addItemClickListener()` und `setKeyProvider()` zu `buildTable()` hinzu, und fügen Sie dann die Methode hinzu, die den Benutzer zu `FormView` mit einem Wert für die `id` im `ParametersBag` basierend darauf sendet, wo auf der Tabelle der Benutzer geklickt hat:

```java title="MainView.java" {30-31,34-37}
@Route("/")
@FrameTitle("Kundentabelle")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;
    addCustomer.setWidth(200);
    buildTable();
    self.setWidth("fit-content")
        .addClassName("card")
        .add(table, addCustomer);
  }

  private void buildTable() {
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
    table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
    table.addColumn("company", Customer::getCompany).setLabel("Firma");
    table.addColumn("country", Customer::getCountry).setLabel("Land");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());
    table.setKeyProvider(Customer::getId);
    table.addItemClickListener(this::editCustomer);
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
```

## Nächster Schritt {#next-step}

Jetzt, da die Benutzer Kundendaten direkt bearbeiten können, sollte Ihre App Änderungen validieren, bevor sie sie im Repository speichert. In [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data) werden Sie Validierungsregeln erstellen und das Datenmodell direkt mit der UI verknüpfen, sodass die Komponenten Fehlermeldungen anzeigen, wenn die Daten ungültig sind.
