---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 5b2523a6cc740389f43f68bfd55a1675
---
Ihre App aus [Beobachtern und Routenparametern](/docs/introduction/tutorial/observers-and-route-parameters) kann `FormView` verwenden, um bestehende Kundendaten zu bearbeiten. Dieser Schritt verwendet [Datenbindung](/docs/data-binding/overview), die UI-Komponenten direkt mit dem Datenmodell verbindet und eine automatische Wert-Synchronisation ermöglicht. Dies reduziert den Boilerplate-Code in Ihrer App und ermöglicht es Ihnen, Validierungsprüfungen für die Spring-Entität `Customer` hinzuzufügen, sodass Ihre Benutzer vollständige und genaue Informationen beim Ausfüllen von Formularen bereitstellen. Dieser Schritt behandelt die folgenden Konzepte:

- [Jakarta Validierung](https://beanvalidation.org)
- Verwendung der [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) Klasse

Durch den Abschluss dieses Schrittes wird eine Version von [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) erstellt.

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum Stammverzeichnis, das die Datei `pom.xml` enthält. Dies ist `5-validating-and-binding-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Validierungsregeln definieren {#defining-validation-rules}

Die Entwicklung einer App mit bearbeitbaren Daten sollte Validation beinhalten. Validierungsprüfungen helfen dabei, bedeutungsvolle und genaue vom Benutzer übermittelte Daten aufrechtzuerhalten. Wenn sie nicht überprüft werden, kann dies zu Problemen führen, daher ist es wichtig, die Arten von Fehlern zu erfassen, die Benutzer beim Ausfüllen eines Formulars in Echtzeit machen können.

Da das, was als gültig angesehen wird, zwischen den Eigenschaften unterschiedlich sein kann, müssen Sie definieren, was jede Eigenschaft gültig macht und den Benutzer informieren, wenn etwas ungültig ist. Glücklicherweise können Sie dies ganz einfach mit [Jakarta Validierung](https://beanvalidation.org) tun. Jakarta Validierung ermöglicht es Ihnen, Einschränkungen für Eigenschaften als Annotationen hinzuzufügen.

Dieses Tutorial verwendet zwei Jakarta-Annotationen, `@NotEmpty` und `@Pattern`. `@NotEmpty` überprüft auf null und leere Strings, während `@Pattern` überprüft, ob die Eigenschaft einem regulären Ausdruck entspricht, den Sie festlegen. Beide Annotationen ermöglichen es Ihnen, eine Nachricht anzuzeigen, wenn die Eigenschaft ungültig wird.

Um zu verlangen, dass sowohl Vor- als auch Nachnamen obligatorisch sind und nur Buchstaben enthalten, während der Firmenname optional ist und Buchstaben, Zahlen und Leerzeichen erlaubt, wenden Sie die folgenden Annotationen auf die Entität `Customer` an:

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Der Vorname des Kunden ist erforderlich")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
  private String firstName = "";

  @NotEmpty(message = "Der Nachname des Kunden ist erforderlich")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
  private String lastName = "";

  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Ungültige Zeichen")
  private String company = "";

  private Country country = Country.UNKNOWN;

  public enum Country {
    UNKNOWN,
    GERMANY,
    ENGLAND,
    ITALY,
    USA
  }

  public Customer(String firstName, String lastName, String company, Country country) {
    setFirstName(firstName);
    setLastName(lastName);
    setCompany(company);
    setCountry(country);
  }

  public Customer(String firstName, String lastName, String company) {
    this(firstName, lastName, company, Country.UNKNOWN);
  }

  public Customer(String firstName, String lastName) {
    this(firstName, lastName, "");
  }

  public Customer(String firstName) {
    this(firstName, "");
  }

  public Customer() {
  }

  public void setFirstName(String newName) {
    firstName = newName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String newName) {
    lastName = newName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setCompany(String newCompany) {
    company = newCompany;
  }

  public String getCompany() {
    return company;
  }

  public void setCountry(Country newCountry) {
    country = newCountry;
  }

  public Country getCountry() {
    return country;
  }

  public Long getId() {
    return id;
  }

}
```

Siehe die [Jakarta Bean Validation Constraints Reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) für eine vollständige Liste der Validierungen oder erfahren Sie mehr aus dem [WebforJ Jakarta Validierung Artikel](/docs/data-binding/validation/jakarta-validation).

## Die Felder binden {#binding-the-fields}

Um die Validierungsprüfungen in `Customer` für die UI in `FormView` zu verwenden, müssen Sie einen `BindingContext` für die Datenbindung erstellen. Vor der Datenbindung musste jedes Feld in `FormView` einen Ereignislistener haben, um manuell mit der Spring-Entität `Customer` zu synchronisieren. Das Erstellen eines `BindingContext` in `FormView` bindet und synchronisiert automatisch das Datenmodell `Customer` mit den UI-Komponenten.

### Erstellen eines `BindingContext` {#creating-a-bindingcontext}

Eine Instanz von `BindingContext` benötigt die Spring-Bean, mit der die Bindungen synchronisiert sind. In `FormView` deklarieren Sie einen `BindingContext` mit der Entität `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Verwenden Sie dann, um UI-Komponenten basierend auf ihren Namen automatisch an Bean-Eigenschaften zu binden, `BindingContext.of()` mit den folgenden Parametern:

- **`this`** : Zuvor haben Sie `context` als `BindingContext` deklariert. Der erste Parameter setzt, welches Objekt die bindbaren Komponenten enthält.
- **`Customer.class`** : Der zweite Parameter ist die Klasse der Bean, die für die Bindung verwendet werden soll.
- **`true`** : Der dritte Parameter aktiviert die Jakarta-Validierung und ermöglicht dem Kontext, die Validierungen zu verwenden, die Sie für `Customer` festgelegt haben. Dies ändert den Stil ungültiger Komponenten und zeigt die festgelegten Nachrichten an.

Alles zusammen wird es wie die folgende Zeile Code aussehen:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Das Formular responsiv machen {#making-the-form-responsive}

Mit der Datenbindung führt Ihre App nun automatisch Validierungsprüfungen durch. Durch das Hinzufügen eines Ereignislisteners zu den Prüfungen können Sie verhindern, dass Benutzer ein ungültiges Formular übermitteln. Fügen Sie Folgendes hinzu, um die Schaltfläche "Absenden" nur aktiv zu machen, wenn das Formular gültig ist:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Ereignislistener für Komponenten entfernen {#removing-event-listeners-for-components}

Jede UI-Änderung wird jetzt automatisch mit dem `BindingContext` synchronisiert. Das bedeutet, dass Sie die Ereignislistener für jedes Feld jetzt entfernen können:

**Vorher**
```java title="FormView.java"
// Ohne Datenbindung
TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Unternehmen", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Nachher**
```java title="FormView.java"
// Mit Datenbindung
TextField firstName = new TextField("Vorname");
TextField lastName = new TextField("Nachname");
TextField company = new TextField("Unternehmen");
ChoiceBox country = new ChoiceBox("Land");
```

### Bindung nach Eigenschaftsnamen {#binding-by-property-names}

Da der Name jeder Komponente mit dem Datenmodell übereinstimmte, wendete webforJ [Automatische Bindung](/docs/data-binding/automatic-binding) an. Wenn die Namen nicht übereinstimmten, könnten Sie die Annotation `@UseProperty` verwenden, um diese zuzuordnen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Vorname");
```

### Daten im `fillForm()`-Methoden lesen {#reading-data-in-the-fillForm()-method}

Früher, in der Methode `fillForm()`, haben Sie den Wert jeder Komponente initialisiert, indem Sie die Daten manuell aus der `Customer`-Kopie abgerufen haben. Jetzt, da Sie einen `BindingContext` verwenden, können Sie die Methode `read()` verwenden. Diese Methode füllt jede gebundene Komponente mit der zugehörigen Eigenschaft aus den Daten in der `Customer`-Kopie.

In der Methode `fillForm()` ersetzen Sie die `setValue()`-Methoden durch `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Entfernte jede setValue()-Methode für die UI-Komponenten

    context.read(customer);
  }
```

### Validierung zu `submitCustomer()` hinzufügen {#adding-validation-to-submitcustomer}

Die letzte Änderung an `FormView` für diesen Schritt wird darin bestehen, eine Sicherheitsmaßnahme zur Methode `submitCustomer()` hinzuzufügen. Bevor Änderungen an der H2-Datenbank vorgenommen werden, wird die App eine abschließende Validierung der Ergebnisse des gebundenen Kontexts mithilfe der Methode `write()` durchführen.

Die Methode `write()` aktualisiert die Eigenschaften einer Bean mithilfe der gebundenen UI-Komponenten im `BindingContext` und gibt ein `ValidationResult` zurück.

Verwenden Sie die Methode `write()`, um in die `Customer`-Kopie zu schreiben, die die gebundenen Komponenten in `FormView` verwendet. Wenn das zurückgegebene `ValidationResult` gültig ist, aktualisieren Sie die H2-Datenbank mit den geschriebenen Daten.

```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### Vollständige `FormView` {#completed-formview}

Mit diesen Änderungen sieht `FormView` wie folgt aus. Die App unterstützt jetzt Datenbindung und Validierung mit Spring Boot und webforJ. Formulareingaben werden automatisch mit dem Modell synchronisiert und können gegen Validierungsregeln überprüft werden.

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Kundenformular")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Vorname");
  private TextField lastName = new TextField("Nachname");
  private TextField company = new TextField("Unternehmen");
  private ChoiceBox country = new ChoiceBox("Land");
  private Button submit = new Button("Absenden", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));
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
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerService.doesCustomerExist(customerId)) {
        customerService.updateCustomer(customer);
      } else {
        customerService.createCustomer(customer);
      }
      navigateToMain();
    }
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
    context.read(customer);
  }
}
```

## Nächster Schritt {#next-step}

Der nächste Schritt, [Integration eines App-Layouts](/docs/introduction/tutorial/integrating-an-app-layout), konzentriert sich auf die Verwendung eines `AppLayout`, um ein Seitenmenü hinzuzufügen, das für Benutzer sowohl auf der Kundenübersicht als auch auf den Kundendatenformularen verfügbar ist. Sie lernen auch ein weiteres Layout-Tool, die `FlexLayout`-Komponente, kennen.
