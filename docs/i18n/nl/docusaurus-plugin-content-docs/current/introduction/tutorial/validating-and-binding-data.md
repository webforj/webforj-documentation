---
title: Data valideren en binden
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 5b2523a6cc740389f43f68bfd55a1675
---
Je app van [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) kan `FormView` gebruiken om bestaande klantgegevens te bewerken. Deze stap maakt gebruik van [Data binding](/docs/data-binding/overview), dat UI-componenten rechtstreeks verbindt met het gegevensmodel voor automatische waarde-synchronisatie. Dit vermindert boilerplate in je app en stelt je in staat om validatiecontroles toe te voegen aan de Spring-entiteit `Customer`, waardoor je gebruikers volledige en nauwkeurige informatie laat verstrekken bij het invullen van formulieren. Deze stap behandelt de volgende concepten:

- [Jakarta validatie](https://beanvalidation.org)
- Het gebruik van de [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) klasse

Het voltooien van deze stap creëert een versie van [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenliggende map die het `pom.xml`-bestand bevat, dit is `5-validating-and-binding-data` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het uitvoeren van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Validatieregels definiëren {#defining-validation-rules}

Het ontwikkelen van een app met bewerkbare gegevens moet validatie omvatten. Validatiecontroles helpen om betekenisvolle en nauwkeurige door de gebruiker ingediende gegevens te behouden. Als dit niet gecontroleerd wordt, kan dit leiden tot problemen, dus het is belangrijk om de soorten fouten die gebruikers kunnen maken bij het invullen van een formulier in realtime op te vangen.

Aangezien wat als geldig wordt beschouwd kan verschillen tussen eigenschappen, moet je definiëren wat elke eigenschap geldig maakt en de gebruiker informeren als er iets ongeldig is. Gelukkig kun je dit gemakkelijk doen met [Jakarta Validatie](https://beanvalidation.org). Jakarta validatie stelt je in staat om beperkingen aan eigenschappen toe te voegen als annotaties.

Deze handleiding gebruikt twee Jakarta-annotaties, `@NotEmpty` en `@Pattern`. `@NotEmpty` controleert op null en lege strings, terwijl `@Pattern` controleert of de eigenschap overeenkomt met een reguliere expressie die je instelt. Beide annotaties stellen je in staat om een bericht toe te voegen dat weergegeven wordt wanneer de eigenschap ongeldig wordt.

Om te vereisen dat zowel voor- als achternaam verplicht zijn en alleen letters bevatten, terwijl de bedrijfsnaam optioneel is en letters, cijfers en spaties toestaat, pas je de volgende annotaties toe op de entiteit `Customer`:

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Voornaam van klant is verplicht")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
  private String firstName = "";

  @NotEmpty(message = "Achternaam van klant is verplicht")
  @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
  private String lastName = "";

  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Ongeldige tekens")
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

Zie de [Jakarta Bean Validatie constraints referentie](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) voor een volledige lijst van validaties, of leer meer in het [webforJ Jakarta Validatie-artikel](/docs/data-binding/validation/jakarta-validation).

## De velden koppelen {#binding-the-fields}

Om de validatiecontroles in `Customer` voor de UI in `FormView` te gebruiken, maak je een `BindingContext` voor gegevensbinding. Voor gegevensbinding vereiste elk veld in `FormView` een gebeurtenisluisteraar om handmatig met een Spring-entiteit `Customer` te synchroniseren. Het creëren van een `BindingContext` in `FormView` koppelt en synchroniseert automatisch het `Customer`-gegevensmodel met de UI-componenten.

### Een `BindingContext` maken {#creating-a-bindingcontext}

Een instantie van `BindingContext` heeft de Spring-bean nodig waarmee de bindings gesynchroniseerd zijn. In `FormView`, declareer een `BindingContext` met de entiteit `Customer`:

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  Customer customer = new Customer();
```

Gebruik vervolgens `BindingContext.of()` om UI-componenten automatisch aan bean-eigenschappen te koppelen op basis van hun namen, met de volgende parameters:

- **`this`** : Eerder heb je `context` gedeclareerd als de `BindingContext`. De eerste parameter stelt in wat voor object de koppelbare componenten zich bevinden.
- **`Customer.class`** : De tweede parameter is de klasse van de bean die gebruikt moet worden voor de binding.
- **`true`** : De derde parameter schakelt Jakarta validatie in, waardoor de context de validaties kan gebruiken die je voor `Customer` hebt ingesteld. Dit zal de stijl van ongeldig componenten wijzigen en de ingestelde berichten weergeven.

In totaal ziet de volgende regel code er als volgt uit:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Het formulier responsief maken {#making-the-form-responsive}

Met gegevensbinding voert je app nu automatisch validatiecontroles uit. Door een gebeurtenisluisteraar aan de controles toe te voegen, kun je voorkomen dat gebruikers een ongeldig formulier indienen. Voeg het volgende toe om de verzendknop alleen actief te maken wanneer het formulier geldig is:

```java
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Gebeurtenisluisteraars voor componenten verwijderen {#removing-event-listeners-for-components}

Elke UI-wijziging is nu automatisch gesynchroniseerd met de `BindingContext`. Dit betekent dat je eenvoudig de gebeurtenisluisteraars voor elk veld kunt verwijderen:

**Voor**
```java
TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Na**
```java
TextField firstName = new TextField("Voornaam");
TextField lastName = new TextField("Achternaam");
TextField company = new TextField("Bedrijf");
ChoiceBox country = new ChoiceBox("Land");
```

### Binden op basis van eigenschapsnamen {#binding-by-property-names}

Aangezien de naam van elke component overeenkomt met het gegevensmodel, is er [Automatische Binding](/docs/data-binding/automatic-binding) toegepast door webforJ. Als de namen niet overeenkomen, kun je de annotatie `@UseProperty` gebruiken om ze te koppelen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Voornaam");
```

### Gegevens lezen in de `fillForm()` methode {#reading-data-in-the-fillForm()-method}

Eerder initialiseerde je in de `fillForm()`-methode elke componentwaarde door handmatig de gegevens uit de `Customer`-kopie te halen. Maar nu, omdat je gebruikmaakt van een `BindingContext`, kun je de `read()`-methode gebruiken. Deze methode vult elke gekoppelde component met de bijbehorende eigenschap uit de gegevens in de `Customer`-kopie.

In de `fillForm()`-methode vervang je de `setValue()`-methoden door `read()`:

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Verwijder elke setValue() methode voor de UI-componenten

  context.read(customer);
}
```

### Validatie toevoegen aan `submitCustomer()` {#adding-validation-to-submitcustomer}

De laatste wijziging in `FormView` voor deze stap is het toevoegen van een veiligheidsmaatregel aan de `submitCustomer()`-methode. Voordat wijzigingen in de H2-database worden doorgevoerd, voert de app een laatste validatie uit op de resultaten van de gebonden context met behulp van de `write()`-methode.

De `write()`-methode werkt de eigenschappen van een bean bij met behulp van de gebonden UI-componenten in de `BindingContext` en retourneert een `ValidationResult`.

Gebruik de `write()`-methode om naar de `Customer`-kopie te schrijven met behulp van de gebonden componenten in `FormView`. Als het geretourneerde `ValidationResult` geldig is, werk je de H2-database bij met de geschreven gegevens.

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

### Voltooid `FormView` {#completed-formview}

Met deze wijzigingen ziet `FormView` er als volgt uit. De app ondersteunt nu gegevensbinding en validatie met behulp van Spring Boot en webforJ. Formuliervelden worden automatisch gesynchroniseerd met het model en gecontroleerd op validatieregels.

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Klantformulier")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Voornaam");
  private TextField lastName = new TextField("Achternaam");
  private TextField company = new TextField("Bedrijf");
  private ChoiceBox country = new ChoiceBox("Land");
  private Button submit = new Button("Indienen", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
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

## Volgende stap {#next-step}

De volgende stap, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), richt zich op het gebruik van een `AppLayout` om een zijmenu toe te voegen dat beschikbaar is voor gebruikers op zowel de klantentabel als de klantformulierpagina's. Je leert ook over een ander lay-out hulpmiddel, de `FlexLayout` component.
