---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: dd158594bca6d722983b03ecf8321f90
---
Je app van [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) kan `FormView` gebruiken om bestaande klantgegevens te bewerken. Deze stap maakt gebruik van [Data binding](/docs/data-binding/overview), wat gebruikersinterfacecomponenten direct verbindt met het datamodel voor automatische waarde-synchronisatie. Dit vermindert boilerplate in je app en stelt je in staat om validatiecontroles toe te voegen aan de Spring-entiteit `Customer`, waardoor je gebruikers verplicht wordt om volledige en nauwkeurige informatie te verstrekken bij het invullen van formulieren. Deze stap behandelt de volgende concepten:

- [Jakarta validatie](https://beanvalidation.org)
- Gebruik van de [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) klasse

Het voltooien van deze stap creëert een versie van [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenliggende map die het bestand `pom.xml` bevat, dat is `5-validating-and-binding-data` als je de versie op GitHub volgt.

2. Gebruik het volgende Maven-commando om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het uitvoeren van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Validatieregels definiëren {#defining-validation-rules}

Het ontwikkelen van een app met bewerkbare gegevens moet validatie omvatten. Validatiecontroles helpen bij het handhaven van betekenisvolle en nauwkeurige door gebruikers ingediende gegevens. Als dit niet gecontroleerd wordt, kan dit leiden tot problemen, dus het is belangrijk om de soorten fouten die gebruikers kunnen maken bij het invullen van een formulier in realtime op te vangen.

Aangezien wat als geldig wordt beschouwd kan verschillen tussen eigenschappen, moet je definiëren wat elke eigenschap geldig maakt en de gebruiker informeren als er iets ongeldig is. Gelukkig kun je dit eenvoudig doen met [Jakarta Validatie](https://beanvalidation.org). Jakarta validatie stelt je in staat om beperkingen aan eigenschappen toe te voegen als annotaties.

Deze tutorial maakt gebruik van twee Jakarta-annotaties, `@NotEmpty` en `@Pattern`. `@NotEmpty` controleert op null- en lege strings, terwijl `@Pattern` controleert of de eigenschap overeenkomt met een reguliere expressie die je instelt. Beide annotaties stellen je in staat een bericht toe te voegen dat wordt weergegeven wanneer de eigenschap ongeldig wordt.

Om te vereisen dat zowel voor- als achternamen verplicht zijn en alleen letters bevatten, terwijl de bedrijfsnaam optioneel is en letters, cijfers en spaties toestaat, pas je de volgende annotaties toe op de `Customer`-entiteit:

<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // highlight-next-line
    @NotEmpty(message = "Voornaam van de klant is verplicht")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Achternaam van de klant is verplicht")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
    private String lastName = "";

  // highlight-next-line
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
`}
</ExpandableCode>

Zie de [Jakarta Bean Validatie beperkingen referentie](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) voor een volledige lijst van validaties, of leer meer van het [webforJ Jakarta Validatie artikel](/docs/data-binding/validation/jakarta-validation).

## De velden binden {#binding-the-fields}

Om de validatiecontroles in `Customer` voor de UI in `FormView` te gebruiken, maak je een `BindingContext` voor data binding. Voor data binding vereiste elk veld in `FormView` een gebeurtenisluisternaars om handmatig met een Spring-entiteit `Customer` te synchroniseren. Het creëren van een `BindingContext` in `FormView` bindt en synchroniseert automatisch het `Customer` datamodel met de UI-componenten.

### Een `BindingContext` creëren {#creating-a-bindingcontext}

Een instantie van `BindingContext` heeft de Spring-bean nodig waarmee de bindings zijn gesynchroniseerd. In `FormView`, declareer een `BindingContext` met behulp van de `Customer`-entiteit:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Gebruik vervolgens `BindingContext.of()` met de volgende parameters om automatisch UI-componenten te binden aan bean-eigenschappen op basis van hun namen:

- **`this`** : Eerder declareerde je `context` als de `BindingContext`. De eerste parameter stelt in welk object de bindbare componenten zich bevinden.
- **`Customer.class`** : De tweede parameter is de klasse van de bean die je voor binding wilt gebruiken.
- **`true`** : De derde parameter inschakelen van Jakarta validatie, zodat de context de validaties kan gebruiken die je voor `Customer` hebt ingesteld. Dit zal de stijl van ongeldige componenten veranderen en de ingestelde berichten weergeven.

Alles samen zal het er als volgt uitzien:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Het formulier responsief maken {#making-the-form-responsive}

Met data binding voert je app nu automatisch validatiecontroles uit. Door een gebeurtenisluisternaars aan de controles toe te voegen, kun je voorkomen dat gebruikers een ongeldig formulier indienen. Voeg het volgende toe om de verzendknop alleen actief te maken wanneer het formulier geldig is:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Gebeurtenisluisternaars voor componenten verwijderen {#removing-event-listeners-for-components}

Elke wijziging in de UI wordt nu automatisch gesynchroniseerd met de `BindingContext`. Dit betekent dat je nu de gebeurtenisluisternaars van elk veld kunt verwijderen:

**Voor**
```java title="FormView.java"
// Zonder data binding
TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Na**
```java title="FormView.java"
// Met data binding
TextField firstName = new TextField("Voornaam");
TextField lastName = new TextField("Achternaam");
TextField company = new TextField("Bedrijf");
ChoiceBox country = new ChoiceBox("Land");
```

### Binden op basis van property-namen {#binding-by-property-names}

Aangezien de naam van elke component overeenkomt met het datamodel, heeft webforJ [Automatische Binding](/docs/data-binding/automatic-binding) toegepast. Als de namen niet overeenkomen, kun je de annotatie `@UseProperty` gebruiken om ze te koppelen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Voornaam");
```

### Gegevens lezen in de `fillForm()`-methode {#reading-data-in-the-fillForm()-method}

Eerder, in de `fillForm()`-methode, initialiseerde je de waarde van elke component door handmatig de gegevens uit de `Customer`-kopie op te halen. Maar nu, aangezien je een `BindingContext` gebruikt, kun je de `read()`-methode gebruiken. Deze methode vult elke gebonden component met de bijbehorende eigenschap uit de gegevens in de `Customer`-kopie.

Vervang in de `fillForm()`-methode de `setValue()`-methoden door `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Verwijderde elke setValue() methode voor de UI-componenten
    
    context.read(customer);
  }
```

### Validatie toevoegen aan `submitCustomer()` {#adding-validation-to-submitcustomer}

De laatste wijziging aan `FormView` voor deze stap is het toevoegen van een beveiliging aan de `submitCustomer()`-methode. Voordat wijzigingen aan de H2-database worden doorgevoerd, voert de app een laatste validatie uit op de resultaten van de gebonden context met behulp van de `write()`-methode.

De `write()`-methode werkt de eigenschappen van een bean bij met behulp van de gebonden UI-componenten in de `BindingContext` en retourneert een `ValidationResult`.

Gebruik de `write()`-methode om naar de `Customer`-kopie te schrijven met behulp van de gebonden componenten in `FormView`. Als het geretourneerde `ValidationResult` geldig is, werk dan de H2-database bij met de geschreven gegevens.

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

### Voltooid `FormView`

Met deze wijzigingen ziet `FormView` er als volgt uit. De app ondersteunt nu data binding en validatie met behulp van Spring Boot en webforJ. Formuliervelden worden automatisch gesynchroniseerd met het model en gecontroleerd op validatieregels.

<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
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
`}
</ExpandableCode>

:::info Volgende stappen
Op zoek naar meer manieren om je app uit deze tutorial te verbeteren? Je kunt proberen de [`AppLayout`](/docs/components/app-layout)-component als wrapper te gebruiken om je klantentabel toe te voegen en meer functies toe te voegen.
:::
