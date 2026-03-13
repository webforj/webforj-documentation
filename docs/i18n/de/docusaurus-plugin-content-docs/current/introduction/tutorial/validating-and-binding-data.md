---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: dd158594bca6d722983b03ecf8321f90
---
Ihr App aus [Beobachtern und Routenparametern](/docs/introduction/tutorial/observers-and-route-parameters) kann `FormView` verwenden, um bestehende Kundendaten zu bearbeiten. Dieser Schritt nutzt [Datenbindung](/docs/data-binding/overview), die UI-Komponenten direkt mit dem Datenmodell verbindet, um eine automatische Wert-Synchronisierung zu ermöglichen. Dadurch wird der Boilerplate-Code in Ihrer App reduziert und Sie können Validierungsprüfungen für die Spring-Entität `Customer` hinzufügen, was sicherstellt, dass Ihre Benutzer vollständige und genaue Informationen beim Ausfüllen von Formularen angeben. In diesem Schritt werden die folgenden Konzepte behandelt:

- [Jakarta Validierung](https://beanvalidation.org)
- Verwendung der [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) Klasse

Der Abschluss dieses Schrittes erstellt eine Version von [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Ausführen der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält. Dies ist `5-validating-and-binding-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Definieren von Validierungsregeln {#defining-validation-rules}

Die Entwicklung einer App mit bearbeitbaren Daten sollte Validierung umfassen. Validierungsprüfungen helfen dabei, sinnvolle und genaue Benutzereingaben zu gewährleisten. Wenn dies unüberprüft bleibt, kann dies zu Problemen führen, daher ist es wichtig, die Arten von Fehlern, die Benutzer beim Ausfüllen eines Formulars machen können, in Echtzeit zu erfassen.

Da das, was als gültig betrachtet wird, je nach Eigenschaften unterschiedlich sein kann, müssen Sie definieren, was jede Eigenschaft gültig macht, und den Benutzer informieren, wenn etwas ungültig ist. Glücklicherweise können Sie dies ganz einfach mit [Jakarta Validierung](https://beanvalidation.org) tun. Die Jakarta Validierung ermöglicht es Ihnen, Einschränkungen als Annotationen an Eigenschaften hinzuzufügen.

Dieses Tutorial verwendet zwei Jakarta-Annotationen, `@NotEmpty` und `@Pattern`. `@NotEmpty` prüft auf null und leere Zeichenfolgen, während `@Pattern` überprüft, ob die Eigenschaft mit einem von Ihnen festgelegten regulären Ausdruck übereinstimmt. Beide Annotationen ermöglichen es Ihnen, eine Nachricht hinzuzufügen, die angezeigt wird, wenn die Eigenschaft ungültig wird.

Um sicherzustellen, dass sowohl Vor- als auch Nachnamen erforderlich sind und nur Buchstaben enthalten, während der Firmenname optional ist und Buchstaben, Zahlen und Leerzeichen zulässt, wenden Sie die folgenden Annotationen auf die Entität `Customer` an:

```java
@Entity
  @Table(name = "customers")
  public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Customer first name is required")
    @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
    private String firstName = "";

    @NotEmpty(message = "Customer last name is required")
    @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
    private String lastName = "";

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

Siehe die [Jakarta Bean Validation Constraints-Referenz](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) für eine vollständige Liste der Validierungen oder erfahren Sie mehr in dem [webforJ Jakarta Validation-Artikel](/docs/data-binding/validation/jakarta-validation).

## Binden der Felder {#binding-the-fields}

Um die Validierungsprüfungen in `Customer` für die UI in `FormView` zu verwenden, erstellen Sie einen `BindingContext` für die Datenbindung. Vor der Datenbindung benötigte jedes Feld in `FormView` einen Ereignis-Listener, um manuell mit einer Spring-Entität `Customer` synchronisiert zu werden. Das Erstellen eines `BindingContext` in `FormView` bindet und synchronisiert automatisch das `Customer` Datenmodell mit den UI-Komponenten.

### Erstellen eines `BindingContext` {#creating-a-bindingcontext}

Eine Instanz von `BindingContext` benötigt die Spring-Bean, mit der die Bindungen synchronisiert werden. In `FormView` deklarieren Sie einen `BindingContext` unter Verwendung der Entität `Customer`:

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Um dann die UI-Komponenten automatisch an die Eigenschaften der Bean basierend auf ihren Namen zu binden, verwenden Sie `BindingContext.of()` mit den folgenden Parametern:

- **`this`** : Vorher haben Sie `context` als `BindingContext` deklariert. Der erste Parameter setzt das Objekt, das die bindbaren Komponenten enthält.
- **`Customer.class`** : Der zweite Parameter ist die Klasse der Bean, die für die Bindung verwendet werden soll.
- **`true`** : Der dritte Parameter aktiviert die Jakarta-Validierung, sodass der Kontext die Validierungen verwenden kann, die Sie für `Customer` festgelegt haben. Dadurch wird der Stil ungültiger Komponenten geändert und die festgelegten Nachrichten angezeigt.

Alles zusammen sieht wie die folgende Zeile Code aus:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Formular responsiv machen {#making-the-form-responsive}

Mit der Datenbindung führt Ihre App jetzt automatisch Validierungsprüfungen durch. Indem Sie einen Ereignis-Listener zu den Prüfungen hinzufügen, können Sie verhindern, dass Benutzer ein ungültiges Formular absenden. Fügen Sie Folgendes hinzu, um die Schaltfläche zum Absenden nur zu aktivieren, wenn das Formular gültig ist:

```java
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Entfernen von Ereignis-Listenern für Komponenten {#removing-event-listeners-for-components}

Jede UI-Änderung wird jetzt automatisch mit dem `BindingContext` synchronisiert. Das bedeutet, Sie können die Ereignis-Listener für jedes Feld entfernen:

**Vorher**
```java
// Ohne Datenbindung
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Nachher**
```java
// Mit Datenbindung
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Binden nach Eigenschaftsnamen {#binding-by-property-names}

Da der Name jeder Komponente mit dem Datenmodell übereinstimmte, hat webforJ [Automatische Bindung](/docs/data-binding/automatic-binding) angewendet. Wenn die Namen nicht übereinstimmen, können Sie die Annotation `@UseProperty` verwenden, um sie zuzuordnen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Daten im `fillForm()`-Methode lesen {#reading-data-in-the-fillForm()-method}

Früher haben Sie in der `fillForm()`-Methode den Wert jeder Komponente initialisiert, indem Sie die Daten aus der `Customer`-Kopie manuell abgerufen haben. Aber jetzt, da Sie einen `BindingContext` verwenden, können Sie die Methode `read()` verwenden. Diese Methode füllt jede gebundene Komponente mit der zugehörigen Eigenschaft aus den Daten in der `Customer`-Kopie.

In der `fillForm()`-Methode ersetzen Sie die `setValue()`-Methoden durch `read()`:

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Entfernte jede setValue()-Methode für die UI-Komponenten
    
    context.read(customer);
}
```

### Hinzufügen von Validierung zur `submitCustomer()` {#adding-validation-to-submitcustomer}

Die letzte Änderung an `FormView` für diesen Schritt wird darin bestehen, eine Schutzmaßnahme zur Methode `submitCustomer()` hinzuzufügen. Bevor Änderungen in der H2-Datenbank vorgenommen werden, führt die App eine letzte Validierung der Ergebnisse des gebundenen Kontexts unter Verwendung der Methode `write()` durch.

Die Methode `write()` aktualisiert die Eigenschaften einer Bean mithilfe der gebundenen UI-Komponenten im `BindingContext` und gibt ein `ValidationResult` zurück.

Verwenden Sie die Methode `write()`, um in die `Customer`-Kopie mithilfe der gebundenen Komponenten in `FormView` zu schreiben. Wenn das zurückgegebene `ValidationResult` gültig ist, aktualisieren Sie die H2-Datenbank mit den geschriebenen Daten.

```java
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

### Vollständige `FormView`

Mit diesen Änderungen sieht `FormView` jetzt so aus. Die App unterstützt nun Datenbindung und Validierung mit Spring Boot und webforJ. Formulareingaben werden automatisch mit dem Modell synchronisiert und gegen Validierungsregeln überprüft.

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

:::info Nächste Schritte
Suchen Sie nach weiteren Möglichkeiten, Ihre App aus diesem Tutorial zu verbessern? Sie können versuchen, die [`AppLayout`](/docs/components/app-layout) Komponente als Wrapper zu verwenden, um Ihre Kundentabelle hinzuzufügen und weitere Funktionen hinzuzufügen.
:::
