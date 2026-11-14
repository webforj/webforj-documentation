---
title: Daten validieren und binden
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
Ihre App aus [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters) kann `FormView` verwenden, um vorhandene Kundendaten zu bearbeiten. In diesem Schritt kommt [Datenbindung](/docs/data-binding/overview) zum Einsatz. Sie verbindet UI-Komponenten direkt mit dem Datenmodell und synchronisiert Werte automatisch. Dadurch benötigt Ihre App weniger Boilerplate-Code. Außerdem können Sie der Spring-Entität `Customer` Validierungsprüfungen hinzufügen, sodass Ihre Benutzer beim Ausfüllen von Formularen vollständige und korrekte Angaben machen. In diesem Schritt lernen Sie die folgenden Konzepte kennen:

- [Jakarta-Validierung](https://beanvalidation.org)
- Verwendung der Klasse [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)

Nach Abschluss dieses Schritts haben Sie eine Version von [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) erstellt.

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als Vergleich heranziehen. So können Sie die App ausprobieren:

1. Navigieren Sie zum Verzeichnis der obersten Ebene, das die Datei `pom.xml` enthält. Wenn Sie der Version auf GitHub folgen, ist dies `5-validating-and-binding-data`.

2. Führen Sie den folgenden Maven-Befehl aus, um die Spring-Boot-App lokal zu starten:
    ```bash
    mvn
    ```

Beim Start der App wird automatisch ein neues Browserfenster unter `http://localhost:8080` geöffnet.

## Validierungsregeln festlegen {#defining-validation-rules}

Bei der Entwicklung einer App mit bearbeitbaren Daten sollte eine Validierung vorgesehen werden. Validierungsprüfungen tragen dazu bei, dass von Benutzern eingegebene Daten aussagekräftig und korrekt sind. Ohne solche Prüfungen kann es zu Problemen kommen. Deshalb ist es wichtig, Fehler, die Benutzern beim Ausfüllen eines Formulars unterlaufen können, in Echtzeit zu erkennen.

Da die Anforderungen an gültige Werte je nach Eigenschaft unterschiedlich sein können, müssen Sie festlegen, welche Werte für die jeweilige Eigenschaft gültig sind, und Benutzer auf ungültige Eingaben hinweisen. Mit [Jakarta Validation](https://beanvalidation.org) geht das ganz einfach: Sie können Eigenschaften mithilfe von Annotationen mit Einschränkungen versehen.

In diesem Tutorial kommen zwei Jakarta-Annotationen zum Einsatz: `@NotEmpty` und `@Pattern`. `@NotEmpty` prüft, ob ein Wert `null` oder eine leere Zeichenfolge ist. `@Pattern` prüft, ob die Eigenschaft einem regulären Ausdruck entspricht, den Sie festlegen. Mit beiden Annotationen können Sie eine Meldung angeben, die angezeigt wird, wenn der Wert der Eigenschaft ungültig ist.

Damit Vor- und Nachname Pflichtfelder sind und nur Buchstaben enthalten dürfen, während die Firmenbezeichnung optional ist und Buchstaben, Zahlen sowie Leerzeichen enthalten darf, fügen Sie der Entität `Customer` die folgenden Annotationen hinzu:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
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

</ExpandableCode>
<!-- vale on -->

Eine vollständige Liste der Validierungen finden Sie in der Referenz zu den [Jakarta-Bean-Validation-Einschränkungen](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html). Weitere Informationen enthält der Artikel zu [Jakarta Validation in webforJ](/docs/data-binding/validation/jakarta-validation).

## Felder binden {#binding-the-fields}

Damit `FormView` die Validierungsprüfungen aus `Customer` für die UI verwenden kann, erstellen Sie einen `BindingContext` für die Datenbindung. Vor der Datenbindung benötigte jedes Feld in `FormView` einen Ereignis-Listener, um manuell mit der Spring-Entität `Customer` synchronisiert zu werden. Ein in `FormView` erstellter `BindingContext` bindet das Datenmodell `Customer` an die UI-Komponenten und synchronisiert sie automatisch.

### Einen `BindingContext` erstellen {#creating-a-bindingcontext}

Eine Instanz von `BindingContext` benötigt die Spring-Bean, mit der die Bindungen synchronisiert werden. Deklarieren Sie in `FormView` einen `BindingContext` mit der Entität `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Verwenden Sie anschließend `BindingContext.of()` mit den folgenden Parametern, um UI-Komponenten anhand ihrer Namen automatisch an Bean-Eigenschaften zu binden:

- **`this`**: Zuvor haben Sie `context` als `BindingContext` deklariert. Der erste Parameter legt fest, welches Objekt die bindbaren Komponenten enthält.
- **`Customer.class`**: Der zweite Parameter ist die Klasse der Bean, die für die Bindung verwendet werden soll.
- **`true`**: Der dritte Parameter aktiviert die Jakarta-Validierung. Dadurch kann der Kontext die für `Customer` festgelegten Validierungen verwenden. Ungültige Komponenten werden daraufhin anders dargestellt und die festgelegten Meldungen angezeigt.

Der vollständige Code sieht so aus:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Das Formular responsiv gestalten {#making-the-form-responsive}

Mit der Datenbindung führt Ihre App nun automatisch Validierungsprüfungen durch. Wenn Sie diesen Prüfungen einen Ereignis-Listener hinzufügen, können Sie verhindern, dass Benutzer ein ungültiges Formular absenden. Fügen Sie den folgenden Code hinzu, damit die Schaltfläche zum Absenden nur aktiviert ist, wenn das Formular gültig ist:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Ereignis-Listener für Komponenten entfernen {#removing-event-listeners-for-components}

Jede Änderung an der UI wird nun automatisch mit dem `BindingContext` synchronisiert. Deshalb können Sie die Ereignis-Listener für die einzelnen Felder entfernen:

**Vorher**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Nachher**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Bindung anhand von Eigenschaftsnamen {#binding-by-property-names}

Da der Name jeder Komponente mit dem des Datenmodells übereinstimmte, verwendete webforJ die [automatische Bindung](/docs/data-binding/automatic-binding). Bei abweichenden Namen könnten Sie sie mithilfe der Annotation `@UseProperty` zuordnen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Daten in der Methode `fillForm()` lesen {#reading-data-in-the-fillForm()-method}

Bisher haben Sie in der Methode `fillForm()` den Wert jeder Komponente initialisiert, indem Sie die Daten manuell aus der Kopie von `Customer` abgerufen haben. Da Sie jetzt einen `BindingContext` verwenden, können Sie stattdessen die Methode `read()` aufrufen. Sie füllt jede gebundene Komponente mit dem Wert der entsprechenden Eigenschaft aus den Daten der `Customer`-Kopie.

Ersetzen Sie in der Methode `fillForm()` die Methoden `setValue()` durch `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### Validierung zu `submitCustomer()` hinzufügen {#adding-validation-to-submitcustomer}

Als letzte Änderung an `FormView` in diesem Schritt fügen Sie der Methode `submitCustomer()` eine Schutzmaßnahme hinzu. Bevor Änderungen in der H2-Datenbank gespeichert werden, führt die App mithilfe der Methode `write()` eine abschließende Validierung der Ergebnisse des gebundenen Kontexts durch.

Die Methode `write()` aktualisiert die Eigenschaften einer Bean anhand der gebundenen UI-Komponenten im `BindingContext` und gibt ein `ValidationResult` zurück.

Verwenden Sie die Methode `write()`, um die gebundenen Komponenten in `FormView` in die `Customer`-Kopie zu schreiben. Wenn das zurückgegebene `ValidationResult` gültig ist, aktualisieren Sie die H2-Datenbank mit den geschriebenen Daten.


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

### Vollständige Implementierung von `FormView` {#completed-formview}

Nach diesen Änderungen sieht `FormView` wie folgt aus. Die App unterstützt nun Datenbindung und Validierung mit Spring Boot und webforJ. Formulareingaben werden automatisch mit dem Modell synchronisiert und anhand der Validierungsregeln geprüft.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
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

</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Im nächsten Schritt, [Ein App-Layout integrieren](/docs/introduction/tutorial/integrating-an-app-layout), erfahren Sie, wie Sie mit `AppLayout` ein Seitenmenü hinzufügen, das Benutzern sowohl auf der Kundentabellen- als auch auf der Kundenformularseite zur Verfügung steht. Außerdem lernen Sie eine weitere Layout-Komponente kennen: `FlexLayout`.
