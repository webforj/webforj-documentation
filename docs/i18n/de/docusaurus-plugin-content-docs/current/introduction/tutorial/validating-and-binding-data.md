---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: b99d289f94de614d85524e9846bdcd92
---
Ihre App aus [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) kann `FormView` verwenden, um vorhandene Kundendaten zu bearbeiten. Dieser Schritt verwendet [Data binding](/docs/data-binding/overview), das UI-Komponenten direkt mit dem Datenmodell verbindet, um eine automatische Wert-Synchronisierung zu ermöglichen. Dies verringert Boilerplate in Ihrer App und ermöglicht es Ihnen, Validierungsprüfungen für die Spring-Entity `Customer` hinzuzufügen, wodurch Ihre Benutzer vollständige und genaue Informationen beim Ausfüllen von Formularen bereitstellen müssen. Dieser Schritt behandelt die folgenden Konzepte:

- [Jakarta validation](https://beanvalidation.org)
- Verwendung der [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) Klasse

Durch den Abschluss dieses Schrittes erstellen Sie eine Version von [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Ausführen der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält; dies ist `5-validating-and-binding-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Validierungsregeln definieren {#defining-validation-rules}

Die Entwicklung einer App mit bearbeitbaren Daten sollte Validierung umfassen. Validierungsprüfungen helfen, sinnvolle und genaue, von Benutzern eingereichte Daten aufrechtzuerhalten. Wenn sie nicht überprüft werden, kann dies zu Problemen führen, daher ist es wichtig, die Arten von Fehlern zu erfassen, die Benutzer beim Ausfüllen eines Formulars in Echtzeit machen können.

Da das, was als gültig angesehen wird, zwischen den Eigenschaften variieren kann, müssen Sie definieren, was jede Eigenschaft gültig macht, und den Benutzer informieren, wenn etwas ungültig ist. Glücklicherweise können Sie dies ganz einfach mit [Jakarta Validation](https://beanvalidation.org) tun. Jakarta-Validierung ermöglicht es Ihnen, Einschränkungen für Eigenschaften als Annotationen hinzuzufügen.

Dieses Tutorial verwendet zwei Jakarta-Annotationen, `@NotEmpty` und `@Pattern`. `@NotEmpty` prüft auf null und leere Strings, während `@Pattern` überprüft, ob die Eigenschaft einem regulären Ausdruck entspricht, den Sie festlegen. Beide Annotationen ermöglichen es Ihnen, eine Nachricht hinzuzufügen, die angezeigt wird, wenn die Eigenschaft ungültig wird.

Um zu verlangen, dass sowohl Vor- als auch Nachname obligatorisch sind und nur Buchstaben enthalten, während der Firmenname optional ist und Buchstaben, Zahlen und Leerzeichen zulässt, wenden Sie die folgenden Annotationen auf die `Customer`-Entität an:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // highlight-next-line
    @NotEmpty(message = "Der Vorname des Kunden ist erforderlich")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Der Nachname des Kunden ist erforderlich")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
    private String lastName = "";

  // highlight-next-line
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
`}
</ExpandableCode>
<!-- vale on -->

Siehe das [Referenzhandbuch für Jakarta Bean Validation Constraints](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) für eine vollständige Liste der Validierungen oder erfahren Sie mehr im [webforJ Jakarta Validation-Artikel](/docs/data-binding/validation/jakarta-validation).

## Binding der Felder {#binding-the-fields}

Um die Validierungsprüfungen in `Customer` für die UI in `FormView` zu verwenden, müssen Sie einen `BindingContext` für die Datenbindung erstellen. Vor der Datenbindung benötigte jedes Feld in `FormView` einen Ereignis-Listener, um manuell mit einer Spring-Entity `Customer` synchronisiert zu werden. Die Erstellung eines `BindingContext` in `FormView` bindet und synchronisiert automatisch das `Customer`-Datenmodell mit den UI-Komponenten.

### Erstellen eines `BindingContext` {#creating-a-bindingcontext}

Eine Instanz von `BindingContext` benötigt das Spring-Bean, mit dem die Bindungen synchronisiert werden. In `FormView` deklarieren Sie einen `BindingContext`, der die `Customer`-Entität verwendet:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Um dann die UI-Komponenten automatisch mit den Bean-Eigenschaften basierend auf ihren Namen zu binden, verwenden Sie `BindingContext.of()` mit den folgenden Parametern:

- **`this`** : Zuvor haben Sie `context` als `BindingContext` deklariert. Der erste Parameter legt fest, welches Objekt die bindbaren Komponenten enthält.
- **`Customer.class`** : Der zweite Parameter ist die Klasse des Beans, die für die Bindung verwendet werden soll.
- **`true`** : Der dritte Parameter aktiviert die Jakarta-Validierung, sodass der Kontext die Validierungen verwenden kann, die Sie für `Customer` festgelegt haben. Dies verändert den Stil ungültiger Komponenten und zeigt die festgelegten Nachrichten an.

Zusammen sieht die folgende Codezeile so aus:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Formular reaktionsfähig machen {#making-the-form-responsive}

Mit der Datenbindung führt Ihre App jetzt automatisch Validierungsprüfungen durch. Durch das Hinzufügen eines Ereignis-Listeners zu den Prüfungen können Sie verhindern, dass Benutzer ein ungültiges Formular einreichen. Fügen Sie Folgendes hinzu, um die Schaltfläche "Absenden" nur dann aktiv zu machen, wenn das Formular gültig ist:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Entfernen von Ereignis-Listenern für Komponenten {#removing-event-listeners-for-components}

Jede UI-Änderung wird nun automatisch mit dem `BindingContext` synchronisiert. Das bedeutet, dass Sie die Ereignis-Listener für jedes Feld jetzt entfernen können:

**Vorher**
```java title="FormView.java"
// Ohne Datenbindung
TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Nachher**
```java title="FormView.java"
// Mit Datenbindung
TextField firstName = new TextField("Vorname");
TextField lastName = new TextField("Nachname");
TextField company = new TextField("Firma");
ChoiceBox country = new ChoiceBox("Land");
```

### Binding nach Eigenschaftsnamen {#binding-by-property-names}

Da der Name jeder Komponente zum Datenmodell übereinstimmte, wendete webforJ [Automatische Bindung](/docs/data-binding/automatic-binding) an. Wenn die Namen nicht übereinstimmten, könnten Sie die Annotation `@UseProperty` verwenden, um sie zuzuordnen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Vorname");
```

### Daten im `fillForm()`-Methoden lesen {#reading-data-in-the-fillForm()-method}

Früher haben Sie in der `fillForm()`-Methode den Wert jeder Komponente manuell initialisiert, indem Sie die Daten aus der `Customer`-Kopie abgerufen haben. Aber jetzt, da Sie einen `BindingContext` verwenden, können Sie die `read()`-Methode verwenden. Diese Methode füllt jede gebundene Komponente mit der zugehörigen Eigenschaft aus den Daten in der `Customer`-Kopie.

In der `fillForm()`-Methode ersetzen Sie die `setValue()`-Methoden durch `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Entfernte jede setValue() Methode für die UI-Komponenten

    context.read(customer);
  }
```

### Validierung zur `submitCustomer()` hinzufügen {#adding-validation-to-submitcustomer}

Die letzte Änderung an `FormView` für diesen Schritt besteht darin, eine Sicherheitsmaßnahme zur Methode `submitCustomer()` hinzuzufügen. Bevor die Änderungen in die H2-Datenbank übertragen werden, führt die App eine letzte Validierung der Ergebnisse des gebundenen Kontexts mithilfe der `write()`-Methode durch.

Die `write()`-Methode aktualisiert die Eigenschaften eines Beans mithilfe der gebundenen UI-Komponenten im `BindingContext` und gibt ein `ValidationResult` zurück.

Verwenden Sie die `write()`-Methode, um in die `Customer`-Kopie mit den gebundenen Komponenten in `FormView` zu schreiben. Wenn das zurückgegebene `ValidationResult` gültig ist, aktualisieren Sie die H2-Datenbank mit den geschriebenen Daten.

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

Mit diesen Änderungen sieht `FormView` jetzt so aus. Die App unterstützt nun Datenbindung und Validierung mithilfe von Spring Boot und webforJ. Formulareingaben werden automatisch mit dem Modell synchronisiert und gegen Validierungsregeln überprüft.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
  @FrameTitle("Kundenformular")
  public class FormView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService customerService;
    private BindingContext<Customer> context;
    private Customer customer = new Customer();
    private Long customerId = 0L;
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Vorname");
    private TextField lastName = new TextField("Nachname");
    private TextField company = new TextField("Firma");
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
`}
</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Der nächste Schritt, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), konzentriert sich darauf, ein `AppLayout` zu verwenden, um ein Seitennavigation hinzuzufügen, das für Benutzer sowohl auf der Kundenübersicht als auch auf den Kundendetailseiten verfügbar ist. Sie lernen auch ein weiteres Layout-Tool kennen, die `FlexLayout`-Komponente.
