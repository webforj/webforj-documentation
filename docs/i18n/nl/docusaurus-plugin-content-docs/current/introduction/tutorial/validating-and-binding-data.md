---
title: Gegevens valideren en binden
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
Je app uit [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) kan `FormView` gebruiken om bestaande klantgegevens te bewerken. In deze stap gebruik je [Data binding](/docs/data-binding/overview), waarmee UI-componenten rechtstreeks met het datamodel worden verbonden, zodat waarden automatisch worden gesynchroniseerd. Hierdoor bevat je app minder boilerplate en kun je validatiecontroles toevoegen aan de Spring-entiteit `Customer`. Zo zorg je ervoor dat gebruikers volledige en juiste informatie invullen in formulieren. In deze stap komen de volgende concepten aan bod:

- [Jakarta-validatie](https://beanvalidation.org)
- De klasse [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) gebruiken

Na afronding van deze stap heb je een versie van [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) als referentie gebruiken. Zo kun je de app uitvoeren:

1. Ga naar de hoofdmap met het bestand `pom.xml`. Als je de versie op GitHub volgt, is dit `5-validating-and-binding-data`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Wanneer je de app uitvoert, wordt automatisch een nieuw browservenster geopend op `http://localhost:8080`.

## Validatieregels definiëren {#defining-validation-rules}

Bij het ontwikkelen van een app met bewerkbare gegevens hoort ook validatie. Validatiecontroles helpen om betekenisvolle en juiste gegevens van gebruikers te behouden. Zonder controles kunnen er problemen ontstaan. Daarom is het belangrijk om mogelijke fouten van gebruikers direct op te sporen wanneer ze een formulier invullen.

Omdat wat als geldig wordt beschouwd per eigenschap kan verschillen, moet je definiëren wanneer elke eigenschap geldig is en de gebruiker informeren als er iets niet klopt. Gelukkig kun je dit eenvoudig doen met [Jakarta Validation](https://beanvalidation.org). Met Jakarta Validation kun je beperkingen als annotaties aan eigenschappen toevoegen.

Deze tutorial gebruikt twee Jakarta-annotaties: `@NotEmpty` en `@Pattern`. `@NotEmpty` controleert op null-waarden en lege tekenreeksen, terwijl `@Pattern` controleert of de eigenschap overeenkomt met een reguliere expressie die je opgeeft. Met beide annotaties kun je een bericht instellen dat wordt weergegeven wanneer de eigenschap ongeldig is.

Gebruik de volgende annotaties voor de entiteit `Customer`, zodat zowel de voor- als achternaam verplicht zijn en alleen letters mogen bevatten, terwijl de bedrijfsnaam optioneel is en letters, cijfers en spaties mag bevatten:

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

Raadpleeg de [referentie voor Jakarta Bean Validation-beperkingen](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) voor een volledige lijst met validaties, of lees meer in het [artikel over Jakarta Validation in webforJ](/docs/data-binding/validation/jakarta-validation).

## De velden binden {#binding-the-fields}

Om de validatiecontroles van `Customer` te gebruiken in de UI van `FormView`, maak je een `BindingContext` voor databinding. Vóór databinding had elk veld in `FormView` een event listener nodig om handmatig te synchroniseren met een Spring-entiteit `Customer`. Door een `BindingContext` aan te maken in `FormView`, bind je het datamodel `Customer` aan de UI-componenten en worden deze automatisch gesynchroniseerd.

### Een `BindingContext` aanmaken {#creating-a-bindingcontext}

Een instantie van `BindingContext` heeft de Spring-bean nodig waarmee de bindings worden gesynchroniseerd. Declareer in `FormView` een `BindingContext` met de entiteit `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Gebruik vervolgens `BindingContext.of()` met de volgende parameters om UI-componenten automatisch te binden aan bean-eigenschappen op basis van hun namen:

- **`this`**: Eerder heb je `context` gedeclareerd als de `BindingContext`. De eerste parameter geeft aan welk object de bindbare componenten bevat.
- **`Customer.class`**: De tweede parameter is de klasse van de bean die voor de binding wordt gebruikt.
- **`true`**: Met de derde parameter schakel je Jakarta-validatie in. Hierdoor kan de context de validaties gebruiken die je voor `Customer` hebt ingesteld. Ongeldige componenten krijgen hierdoor een andere stijl en de ingestelde berichten worden weergegeven.

Samen ziet de regel code er als volgt uit:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Het formulier responsief maken {#making-the-form-responsive}

Met databinding voert je app nu automatisch validatiecontroles uit. Door een event listener aan de controles toe te voegen, kun je voorkomen dat gebruikers een ongeldig formulier verzenden. Voeg de volgende regel toe om de verzendknop alleen actief te maken wanneer het formulier geldig is:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Event listeners voor componenten verwijderen {#removing-event-listeners-for-components}

Elke wijziging in de UI wordt nu automatisch gesynchroniseerd met de `BindingContext`. Je kunt daarom de event listeners van de afzonderlijke velden verwijderen:

**Voor**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Na**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Binden op basis van eigenschapsnamen {#binding-by-property-names}

Omdat de naam van elke component overeenkomt met die van het datamodel, heeft webforJ [automatische binding](/docs/data-binding/automatic-binding) toegepast. Als de namen niet overeenkomen, kun je de annotatie `@UseProperty` gebruiken om ze aan elkaar te koppelen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Gegevens lezen in de methode `fillForm()` {#reading-data-in-the-fillForm()-method}

Eerder initialiseerde je in de methode `fillForm()` de waarde van elke component door de gegevens handmatig op te halen uit de kopie van `Customer`. Omdat je nu een `BindingContext` gebruikt, kun je de methode `read()` gebruiken. Hiermee wordt elke gebonden component gevuld met de bijbehorende eigenschap uit de gegevens in de kopie van `Customer`.

Vervang in de methode `fillForm()` de methoden `setValue()` door `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### Validatie toevoegen aan `submitCustomer()` {#adding-validation-to-submitcustomer}

De laatste wijziging voor deze stap in `FormView` is het toevoegen van een beveiliging aan de methode `submitCustomer()`. Voordat wijzigingen naar de H2-database worden geschreven, voert de app een laatste validatie uit op de resultaten van de gebonden context met de methode `write()`.

De methode `write()` werkt de eigenschappen van een bean bij met behulp van de gebonden UI-componenten in de `BindingContext` en retourneert een `ValidationResult`.

Gebruik de methode `write()` om de kopie van `Customer` bij te werken met de gebonden componenten in `FormView`. Als de geretourneerde `ValidationResult` geldig is, werk je de H2-database bij met de geschreven gegevens.


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

### Voltooide `FormView` {#completed-formview}

Na deze wijzigingen ziet `FormView` er als volgt uit. De app ondersteunt nu databinding en validatie met Spring Boot en webforJ. Formulierinvoer wordt automatisch met het model gesynchroniseerd en aan de validatieregels getoetst.

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

## Volgende stap {#next-step}

De volgende stap, [Een app-layout integreren](/docs/introduction/tutorial/integrating-an-app-layout), gaat over het gebruik van een `AppLayout` om een zijmenu toe te voegen dat beschikbaar is op zowel de klantentabelpagina als de klantformulierpagina. Je leert ook een ander layoutcomponent kennen: `FlexLayout`.
