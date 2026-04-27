---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: bb0d88755455ff4e639e598c104b6d68
---
Ihre App aus [Beobachtern und Routenparametern](/docs/introduction/tutorial/observers-and-route-parameters) kann `FormView` verwenden, um vorhandene Kundendaten zu bearbeiten. Dieser Schritt nutzt [Datenbindung](/docs/data-binding/overview), die UI-Komponenten direkt mit dem Datenmodell zur automatischen Wert-Synchronisierung verbindet. Dies reduziert Boilerplate in Ihrer App und ermöglicht es Ihnen, Validierungsprüfungen für die Spring-Entität `Customer` hinzuzufügen, sodass Ihre Benutzer vollständige und genaue Informationen beim Ausfüllen von Formularen bereitstellen. In diesem Schritt werden die folgenden Konzepte behandelt:

- [Jakarta-Validierung](https://beanvalidation.org)
- Verwendung der [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)-Klasse

Wenn Sie diesen Schritt abgeschlossen haben, wird eine Version von [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) erstellt.

## Die App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die Datei `pom.xml` enthält, dies ist `5-validating-and-binding-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Validierungsregeln definieren {#defining-validation-rules}

Die Entwicklung einer App mit bearbeitbaren Daten sollte Validierung einschließen. Validierungsprüfungen helfen, sinnvolle und genaue von Benutzern eingereichte Daten aufrechtzuerhalten. Wenn sie unkontrolliert bleiben, könnte dies zu Problemen führen, weshalb es wichtig ist, die Arten von Fehlern, die Benutzer beim Ausfüllen eines Formulars machen können, in Echtzeit zu erfassen.

Da das, was als gültig gilt, je nach Eigenschaften variieren kann, müssen Sie festlegen, was jede Eigenschaft gültig macht und den Benutzer informieren, wenn etwas ungültig ist. Glücklicherweise können Sie dies ganz einfach mit [Jakarta Validierung](https://beanvalidation.org) tun. Jakarta-Validierung ermöglicht es Ihnen, Eigenschaften als Annotationen Einschränkungen hinzuzufügen.

Dieses Tutorial verwendet zwei Jakarta-Annotationen, `@NotEmpty` und `@Pattern`. `@NotEmpty` prüft auf null und leere Zeichenfolgen, während `@Pattern` prüft, ob die Eigenschaft mit einem regulären Ausdruck übereinstimmt, den Sie festlegen. Beide Annotationen ermöglichen es Ihnen, eine Nachricht hinzuzufügen, die angezeigt wird, wenn die Eigenschaft ungültig wird.

Um zu verlangen, dass sowohl Vor- als auch Nachname obligatorisch sind und nur Buchstaben enthalten, während der Firmenname optional ist und Buchstaben, Zahlen und Leerzeichen zulässt, wenden Sie die folgenden Annotationen auf die `Customer`-Entität an:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // Highlight-Nächste Zeile
    @NotEmpty(message = "Der Vorname des Kunden ist erforderlich")
  // Highlight-Nächste Zeile
    @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
    private String firstName = "";

  // Highlight-Nächste Zeile
    @NotEmpty(message = "Der Nachname des Kunden ist erforderlich")
  // Highlight-Nächste Zeile
    @Pattern(regexp = "[a-zA-Z]*", message = "Ungültige Zeichen")
    private String lastName = "";

  // Highlight-Nächste Zeile
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

Siehe die [Jakarta Bean Validation Constraints-Referenz](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) für eine vollständige Liste von Validierungen oder erfahren Sie mehr aus dem [webforJ Jakarta Validation-Artikel](/docs/data-binding/validation/jakarta-validation).

## Die Felder binden {#binding-the-fields}

Um die Validierungsprüfungen in `Customer` für die UI in `FormView` zu verwenden, erstellen Sie einen `BindingContext` für die Datenbindung. Vor der Datenbindung erforderte jedes Feld in `FormView` einen Ereignis-Listener, um manuell mit einer Spring-Entität `Customer` synchronisiert zu werden. Das Erstellen eines `BindingContext` in `FormView` bindet und synchronisiert automatisch das Datenmodell `Customer` mit den UI-Komponenten.

### Erstellen eines `BindingContext` {#creating-a-bindingcontext}

Eine Instanz von `BindingContext` benötigt die Spring-Bean, mit der die Bindungen synchronisiert werden. In `FormView` deklarieren Sie einen `BindingContext` mit der `Customer`-Entität:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Verwenden Sie dann, um UI-Komponenten automatisch an Bean-Eigenschaften basierend auf ihren Namen zu binden, `BindingContext.of()` mit den folgenden Parametern:

- **`this`** : Zuvor haben Sie `context` als `BindingContext` deklariert. Der erste Parameter legt das Objekt fest, das die bindbaren Komponenten enthält.
- **`Customer.class`** : Der zweite Parameter ist die Klasse der zu bindenden Bean.
- **`true`** : Der dritte Parameter aktiviert die Jakarta-Validierung, wodurch der Kontext die für `Customer` festgelegten Validierungen nutzen kann. Dies ändert den Stil ungültiger Komponenten und zeigt die festgelegten Nachrichten an.

Insgesamt sieht das folgende Zeile Code so aus:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Das Formular responsiv gestalten {#making-the-form-responsive}

Durch die Datenbindung führt Ihre App jetzt automatisch Validierungsprüfungen durch. Indem Sie einen Ereignis-Listener zu den Prüfungen hinzufügen, können Sie verhindern, dass Benutzer ein ungültiges Formular absenden. Fügen Sie Folgendes hinzu, um die Schaltfläche "Absenden" nur dann zu aktivieren, wenn das Formular gültig ist:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Ereignis-Listener für Komponenten entfernen {#removing-event-listeners-for-components}

Jede UI-Änderung wird jetzt automatisch mit dem `BindingContext` synchronisiert. Das bedeutet, dass Sie jetzt die Ereignis-Listener für jedes Feld entfernen können:

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

### Nach Eigenschaften benennen {#binding-by-property-names}

Da der Name jeder Komponente dem Datenmodell zugeordnet ist, hat webforJ [Automatische Bindung](/docs/data-binding/automatic-binding) angewendet. Wenn die Namen nicht übereinstimmten, konnten Sie die Annotation `@UseProperty` verwenden, um sie zuzuordnen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Vorname");
```

### Daten in der Methode `fillForm()` lesen {#reading-data-in-the-fillForm()-method}

Früher haben Sie in der Methode `fillForm()` den Wert jeder Komponente initialisiert, indem Sie die Daten aus der `Customer`-Kopie manuell abgerufen haben. Aber jetzt, da Sie einen `BindingContext` verwenden, können Sie die Methode `read()` verwenden. Diese Methode füllt jede gebundene Komponente mit der zugehörigen Eigenschaft aus den Daten in der `Customer`-Kopie.

In der Methode `fillForm()` ersetzen Sie die `setValue()`-Methoden durch `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Entfernte jede setValue()-Methode für die UI-Komponenten
    
    context.read(customer);
  }
```

### Validierung zu `submitCustomer()` hinzufügen {#adding-validation-to-submitcustomer}

Die letzte Änderung an `FormView` für diesen Schritt besteht darin, einen Schutzmechanismus zur Methode `submitCustomer()` hinzuzufügen. Bevor Änderungen an der H2-Datenbank vorgenommen werden, führt die App eine abschließende Validierung der Ergebnisse des gebundenen Kontexts unter Verwendung der Methode `write()` durch.

Die Methode `write()` aktualisiert die Eigenschaften einer Bean mit den gebundenen UI-Komponenten im `BindingContext` und gibt ein `ValidationResult` zurück.

Verwenden Sie die Methode `write()`, um in die `Customer`-Kopie mit den gebundenen Komponenten in `FormView` zu schreiben. Wenn das zurückgegebene `ValidationResult` gültig ist, aktualisieren Sie die H2-Datenbank mit den geschriebenen Daten.

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

### Vollständige `FormView`

Mit diesen Änderungen sieht `FormView` so aus. Die App unterstützt jetzt Datenbindung und Validierung mit Spring Boot und webforJ. Formulareingaben werden automatisch mit dem Modell synchronisiert und gegen Validierungsregeln überprüft.

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
`}
</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Der nächste Schritt, [Eine App-Layout integrieren](/docs/introduction/tutorial/integrating-an-app-layout), konzentriert sich auf die Verwendung eines `AppLayout`, um ein Menü an der Seite hinzuzufügen, das Benutzern sowohl auf der Kundenübersichtsseite als auch auf der Kundenformularseite zur Verfügung steht. Sie werden auch mehr über ein weiteres Layout-Tool, die `FlexLayout`-Komponente, lernen.
