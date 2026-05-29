---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: bb0d88755455ff4e639e598c104b6d68
---
Uw app van [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) kan `FormView` gebruiken om bestaande klantgegevens te bewerken. Deze stap maakt gebruik van [Data binding](/docs/data-binding/overview), wat UI-componenten rechtstreeks verbindt met het datamodel voor automatische waarde-synchronisatie. Dit vermindert boilerplate in uw app en stelt u in staat om validatiecontroles toe te voegen aan de Spring-entiteit `Customer`, waardoor uw gebruikers volledige en nauwkeurige informatie kunnen verstrekken bij het invullen van formulieren. Deze stap behandelt de volgende concepten:

- [Jakarta validatie](https://beanvalidation.org)
- Het gebruik van de [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) klasse

Het voltooien van deze stap creëert een versie van [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## De app uitvoeren {#running-the-app}

Terwijl u uw app ontwikkelt, kunt u [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenste directory met het `pom.xml`-bestand, dit is `5-validating-and-binding-data` als u de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het uitvoeren van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Het definiëren van validatieregels {#defining-validation-rules}

Het ontwikkelen van een app met bewerkbare gegevens moet validatie omvatten. Validatiecontroles helpen zinvolle en nauwkeurige door gebruikers ingediende gegevens te behouden. Als dit niet wordt gecontroleerd, kan dit tot problemen leiden, daarom is het belangrijk om de soorten fouten die gebruikers kunnen maken bij het invullen van een formulier in realtime op te vangen.

Aangezien wat als geldig wordt beschouwd kan verschillen tussen eigenschappen, moet u definiëren wat elke eigenschap geldig maakt en de gebruiker informeren als er iets ongeldig is. Gelukkig kunt u dit eenvoudig doen met [Jakarta Validation](https://beanvalidation.org). Jakarta-validatie stelt u in staat om beperkingen aan eigenschappen toe te voegen als annotaties.

Deze tutorial gebruikt twee Jakarta-annotaties, `@NotEmpty` en `@Pattern`. `@NotEmpty` controleert op null en lege strings, terwijl `@Pattern` controleert of de eigenschap overeenkomt met een reguliere expressie die u instelt. Beide annotaties stellen u in staat om een bericht toe te voegen dat wordt weergegeven wanneer de eigenschap ongeldig wordt.

Om te vereisen dat zowel voor- als achternaam verplicht zijn en alleen letters bevatten, terwijl de bedrijfsnaam optioneel is en letters, cijfers en spaties toestaat, past u de volgende annotaties toe op de entiteit `Customer`:

<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // highlight-next-line
    @NotEmpty(message = "Voornaam klant is vereist")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Ongeldige tekens")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Achternaam klant is vereist")
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

Zie de [Jakarta Bean Validation constraints reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) voor een volledige lijst van validaties, of leer meer in het [webforJ Jakarta Validation-artikel](/docs/data-binding/validation/jakarta-validation).

## De velden binden {#binding-the-fields}

Om de validatiecontroles in `Customer` voor de UI in `FormView` te gebruiken, maakt u een `BindingContext` voor databinding. Voor databinding vereiste elk veld in `FormView` een evenementlistener om handmatig met een Spring-entiteit `Customer` te synchroniseren. Het maken van een `BindingContext` in `FormView` bindt en synchroniseert automatisch het datamodel `Customer` met de UI-componenten.

### Een `BindingContext` maken {#creating-a-bindingcontext}

Een instantie van `BindingContext` heeft de Spring-bean nodig waarmee de bindingen zijn gesynchroniseerd. In `FormView` declareert u een `BindingContext` met de entiteit `Customer`:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Vervolgens, om UI-componenten automatisch te binden aan bean-eigenschappen op basis van hun namen, gebruikt u `BindingContext.of()` met de volgende parameters:

- **`this`** : Eerder heeft u `context` gedeclareerd als de `BindingContext`. De eerste parameter stelt in welk object de bindbare componenten zich bevinden.
- **`Customer.class`** : De tweede parameter is de klasse van de bean die voor binding moet worden gebruikt.
- **`true`** : De derde parameter schakelt Jakarta-validatie in, waardoor de context de validaties die u hebt ingesteld voor `Customer` kan gebruiken. Dit zal de stijl van ongeldige componenten wijzigen en de ingestelde berichten weergeven.

Alles samen ziet het eruit als de volgende regel code:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Formulier responsief maken {#making-the-form-responsive}

Met databinding voert uw app nu automatisch validatiecontroles uit. Door een evenementlistener aan de controles toe te voegen, kunt u voorkomen dat gebruikers een ongeldig formulier indienen. Voeg het volgende toe om de verzendknop alleen actief te maken wanneer het formulier geldig is:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Evenementlisteners voor componenten verwijderen {#removing-event-listeners-for-components}

Elke UI-wijziging is nu automatisch gesynchroniseerd met de `BindingContext`. Dit betekent dat u nu de evenementlisteners voor elk veld kunt verwijderen:

**Voor**
```java title="FormView.java"
// Zonder databinding
TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Na**
```java title="FormView.java"
// Met databinding
TextField firstName = new TextField("Voornaam");
TextField lastName = new TextField("Achternaam");
TextField company = new TextField("Bedrijf");
ChoiceBox country = new ChoiceBox("Land");
```

### Binden op basis van eigenschapsnamen {#binding-by-property-names}

Aangezien de naam van elke component overeenkomt met het datamodel, heeft webforJ [Automatische Binding](/docs/data-binding/automatic-binding) toegepast. Als de namen niet overeenkwamen, kunt u de annotatie `@UseProperty` gebruiken om ze te koppelen.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Voornaam");
```

### Gegevens lezen in de `fillForm()`-methode {#reading-data-in-the-fillForm()-method}

Eerder initieerde u in de `fillForm()`-methode elke componentwaarde door handmatig de gegevens van de kopie van `Customer` op te halen. Maar nu, omdat u een `BindingContext` gebruikt, kunt u de `read()`-methode gebruiken. Deze methode vult elke gebonden component met de bijbehorende eigenschap uit de gegevens in de kopie van `Customer`.

In de `fillForm()`-methode vervangt u de `setValue()`-methoden door `read()`:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Verwijderde elke setValue() methode voor de UI-componenten
    
    context.read(customer);
  }
```

### Validatie toevoegen aan `submitCustomer()` {#adding-validation-to-submitcustomer}

De laatste wijziging in `FormView` voor deze stap zal een beveiliging toevoegen aan de `submitCustomer()`-methode. Voordat wijzigingen aan de H2-database worden doorgevoerd, voert de app een laatste validatie uit op de resultaten van de gebonden context met behulp van de `write()`-methode.

De `write()`-methode werkt de eigenschappen van een bean bij met behulp van de gebonden UI-componenten in de `BindingContext` en retourneert een `ValidationResult`.

Gebruik de `write()`-methode om naar de kopie van `Customer` te schrijven met behulp van de gebonden componenten in `FormView`. Als het geretourneerde `ValidationResult` geldig is, werk dan de H2-database bij met de geschreven gegevens.

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

Met deze wijzigingen ziet `FormView` er als volgt uit. De app ondersteunt nu databinding en validatie met behulp van Spring Boot en webforJ. Formuliervelden worden automatisch gesynchroniseerd met het model en gecontroleerd op validatieregels.

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

## Volgende stap {#next-step}

De volgende stap, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), richt zich op het gebruik van een `AppLayout` om een zijmenu toe te voegen dat beschikbaar is voor gebruikers op zowel de klantentabel als de klantformulierenpagina's. U leert ook over een ander lay-out hulpmiddel, de `FlexLayout`-component.
