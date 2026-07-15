---
title: Observers and Route Parameters
sidebar_position: 5
description: Step 4 - Use route parameters to control what content loads.
_i18n_hash: a1b1dbf791378ed2fd17db610223475e
---
De app van [Routing en Composities](/docs/introduction/tutorial/routing-and-composites) kan alleen nieuwe klanten aan de database toevoegen. Met behulp van de volgende concepten geef je gebruikers ook de mogelijkheid om de gegevens van bestaande klanten te bewerken:

- Routepatronen
- Het doorgeven van parameterwaarden via een URL
- Levenscycluswaarnemers

Het voltooien van deze stap creëert een versie van [4-observers-and-route-parameters](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters).

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [4-observers-and-route-parameters](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de bovenste directory die het `pom.xml`-bestand bevat, dit is `4-observers-and-route-parameters` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Het gebruik van de klant `id` {#using-the-customers-id}

Om `FormView` te gebruiken om bestaande klanten te bewerken, heb je een manier nodig om aan te geven welke klant je wilt bewerken. Dit kun je doen door een initiële parameter aan `FormView` te geven die de klant-ID vertegenwoordigt. In [Werken met Gegevens](/docs/introduction/tutorial/working-with-data) heb je een `Customer`-entiteit gemaakt die een numerieke `Long`-waarde toekent als een unieke `id` aan klanten wanneer ze aan de database worden toegevoegd.

```java
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
```

In deze stap breng je wijzigingen aan in `FormView`, zodat het een `id` als initiële parameter gebruikt voordat er iets wordt geladen. Vervolgens laat je `FormView` de `id` evalueren om te bepalen of het formulier bedoeld is voor het toevoegen van een nieuwe klant of het bijwerken van een bestaande. Ten slotte pas je `MainView` aan, zodat het een `id`-waarde verzendt wanneer het naar `FormView` navigeert.

## Een routepatroon aan `FormView` toevoegen {#adding-a-route-pattern}

In de vorige stap werd de route in `FormView` ingesteld op `@Route(customer)`, wat de klasse lokaal mapte naar `http://localhost:8080/customer`. Een routepatroon toevoegen stelt je in staat om een `id` als een initiële parameter aan `FormView` toe te voegen.

Een [Routepatroon](/docs/routing/route-patterns) stelt je in staat om een parameter in de URL toe te voegen, deze optioneel te maken en beperkingen op geldige patronen in te stellen. Met behulp van de annotatie `@Route`, zorgt het volgende ervoor dat `id` een optionele routeparameter voor `FormView` is:

- **`/:id`** geeft de route een genummerde parameter `id`, zodat het laden van `http://localhost:8080/customer/6` `FormView` laadt met een `id`-parameter van `6`.

- **`?`** maakt de parameter `id` optioneel. Standaard zijn parameters vereist, maar door de `id` optioneel te maken, kun je `FormView` gebruiken voor het toevoegen van nieuwe klanten die nog geen `id` hebben.

- **`<[0-9]+>`** beperkt `id` tot een positief nummer. In hoekige haken, `<>`, kun je een beperking als een reguliere expressie aan de parameter toevoegen. Als de `id` niet aan de beperking voldoet, bijvoorbeeld `http://localhost:8080/customer/john-smith`, stuurt het de gebruiker naar een 404-pagina.

Om de optionele routeparameter aan `FormView` toe te voegen, verander je de annotatie `@Route` naar dit:

```java
@Route("customer/:id?<[0-9]+>")
```

## Routeren naar `FormView` {#routing-to-formview}

`FormView` accepteert nu een optionele `id`-parameter en laadt alleen als de `id` een geheel positief nummer is.

Echter, `FormView` kan nog steeds laden wanneer een gebruiker handmatig een URL voor een niet-bestaande klant invoert, zoals `http://localhost:8080/customer/5000`. Een levenscycluswaarnemer toevoegen voordat je naar `FormView` gaat, stelt je app in staat om te bepalen hoe om te gaan met de binnenkomende `id`-waarde.

### Voorwaardelijk routeren {#conditional-routing}

Levenscycluswaarnemers stellen componenten in staat om te reageren op levenscyclusgebeurtenissen op specifieke momenten. Het artikel [Levenscycluswaarnemers](/docs/routing/navigation-lifecycle/observers) noemt beschikbare waarnemers, maar deze stap maakt alleen gebruik van de `WillEnterObserver`.

De timing van de `WillEnterObserver` vindt plaats voordat de routering van de component is voltooid. Het gebruik van deze waarnemer stelt je in staat om de binnenkomende `id` te evalueren. Als de `id` niet overeenkomt met een bestaande klant, kun je de gebruiker terugsturen naar `MainView` om een geldige klant te vinden om te bewerken.

Voordat we de code voor de `WillEnterObserver` bespreken, toont de volgende flowchart welke mogelijke uitkomsten er moeten zijn bij het routeren naar `FormView`:

```mermaid
flowchart TD
    A[Ga naar FormView] --> B{Is er een id-parameter?}
    B -->|Nee| C[Ga naar een leeg FormView]
    B -->|Ja| D{Komt die id-waarde overeen met een klant-id?}
    D -->|Ja| E[Ga naar een gevuld FormView]
    D -->|Nee| F[Redirect naar MainView]
```

### De `WillEnterObserver` gebruiken {#using-the-willenterobserver}

Met behulp van de levenscycluswaarnemer die wordt geactiveerd voordat de component volledig wordt geladen, `WillEnterObserver`, kun je voorwaarden toevoegen om te bepalen of de app moet doorgaan naar `FormView`, of dat deze de gebruikers moet omleiden naar `MainView`.

Elke levenscycluswaarnemer is een interface, dus implementeer `WillEnterObserver` als onderdeel van de declaratie voor `FormView`:

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
```

De `WillEnterObserver` heeft de methode `onWillEnter()` die webforJ aanroept voordat er naar de component wordt gerouteerd. Deze methode heeft twee parameters: het `WillEnterEvent` en de `ParametersBag`.

Het `WillEnterEvent` bepaalt of de routering naar de component moet doorgaan met de methode `accept()`, of de routering moet stoppen met de methode `reject()`. Na het afwijzen van de huidige route, moet je de gebruiker ergens anders omleiden.

De `ParametersBag` bevat de routerparameters uit de URL. Je zult de `ParametersBag` in de volgende sectie gebruiken om de voorwaardelijke logica voor `onWillEnter()` te creëren met behulp van de `id`-parameter.

De volgende `onWillEnter()` is een voorbeeld met slechts twee uitkomsten:

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Voeg voorwaardelijke logica toe
  if (<condition>) {

    // Sta routeren naar FormView toe om door te gaan
    event.accept();

  } else {

    // Stop met routeren naar FormView
    event.reject();

    // Stuur de gebruiker naar MainView
    navigateToMain();
  }
}
```

### De `ParametersBag` gebruiken {#using-the-parametersbag}

Zoals kort vermeld in de vorige sectie, bevat de `ParametersBag` de routerparameter uit de URL. Elke levenscycluswaarnemer heeft toegang tot dit object, en het gebruik ervan in je app stelt je in staat om de `id`-waarde te krijgen.

Het `ParametersBag`-object biedt verschillende querymethoden om een parameter als een specifiek objecttype op te halen. Bijvoorbeeld, `getInt()` kan je een parameter als een `Integer` geven.

Echter, omdat sommige parameters optioneel zijn, wat `getInt()` daadwerkelijk retourneert is `Optional<Integer>`. Het gebruik van de methode `ifPresentOrElse()` op de `Optional<Integer>` stelt je in staat om een variabele in te stellen met behulp van de `Integer`.

Wanneer er geen `id` aanwezig is, kan de gebruiker naar `FormView` blijven gaan om een nieuwe klant toe te voegen.

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Bepaal welke parameter je moet ophalen en controleer of deze aanwezig is of niet
  parameters.getInt("id").ifPresentOrElse(id -> {

    // Gebruik de id als een variabele
    customerId = Long.valueOf(id);

  // Wanneer er geen id aanwezig is, ga verder naar FormView voor een nieuwe klant
  }, () -> event.accept());

}
```

### Is de `id` geldig? {#is-the-id-valid}

Op dit moment accepteert de `WillEnterObserver` uit de vorige sectie alleen de routering wanneer er geen `id` aanwezig is. De waarnemer moet nog een verificatie uitvoeren alvorens door te gaan naar `FormView`: verifiëren dat de `id` overeenkomt met een bestaande klant.

Nu kan `FormView` de `CustomerService` gebruiken om de aanwezigheid van een klant te bevestigen met behulp van de methode `doesCustomerExist()`. Als er geen match is, kan de app de huidige routering afwijzen en de gebruiker omleiden naar `MainView` met `navigateToMain()`.

Bij een geldige `id` kan de app `accept()` gebruiken om door te gaan met routeren naar `FormView`. Maak een `fillForm()`-methode aan om de `customer`-variabele toe te wijzen aan de klant met de bijbehorende `id` in de database en stel de waarden van de velden in:

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  firstName.setValue(customer.getFirstName());
  lastName.setValue(customer.getLastName());
  company.setValue(customer.getCompany());
  country.selectKey(customer.getCountry());
}
```

Net als bij het toevoegen van een nieuwe klant, stelt het gebruik van de werkende kopie gebruikers in staat om klantgegevens in de UI te bewerken zonder direct de repository te bewerken.

### Voltooide `onWillEnter()` {#completed-onwillenter}

De laatste twee secties gingen in detail in op hoe je elke uitkomst voor de routering naar `FormView` moet afhandelen met behulp van de `ParametersBag` en de `CustomerService`.

Hier is de voltooide `onWillEnter()` voor `FormView` die de `ParametersBag` gebruikt om ofwel de binnenkomende route te weigeren of te accepteren, en andere methoden aanroept om ofwel het formulier in te vullen of de gebruiker naar `MainView` te sturen:

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  // Bepaal welke parameter je moet ophalen en controleer of deze aanwezig is of niet
  parameters.getInt("id").ifPresentOrElse(id -> {
    customerId = Long.valueOf(id);
    if (customerService.doesCustomerExist(customerId)) {
        // Deze klant bestaat, dus ga verder naar FormView en initialiseer de velden met behulp van de id
        event.accept();
        fillForm(customerId);
      } else {
        // Deze klant bestaat niet, dus omleiden naar MainView
        event.reject();
        navigateToMain();
      }

  // Er was geen id aanwezig, dus ga verder naar FormView voor een nieuwe klant
  }, () -> event.accept());

}
```

## Een klant toevoegen of bewerken {#adding-or-editing-a-customer}

De vorige versie van deze app voegde alleen nieuwe klanten toe wanneer de gebruiker het formulier indiende. Nu gebruikers bestaande klanten kunnen bewerken, moet de methode `submitCustomer()` verifiëren of de klant al bestaat voordat de database wordt bijgewerkt.

Aanvankelijk was het niet nodig om een variabele voor de klant `id` in `FormView` toe te wijzen, omdat nieuwe klanten een unieke `id` krijgen wanneer ze in de database worden ingediend. Echter, als je `customerId` declareert als een initiële variabele in `FormView` met een `id`-waarde die niet in gebruik is, blijft deze onaangeroerd voor nieuwe klanten en wordt deze overschreven in `onWillEnter()` voor bestaande.

Dit stelt je in staat om `doesCustomerExist()` te gebruiken om te verifiëren of je een nieuwe klant moet toevoegen of een bestaande moet bijwerken.

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

## Voltooide `FormView` {#completed-formview}

Hier is hoe `FormView` eruit zou moeten zien, nu het klanten bewerken kan:

<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
  {`@Route("customer/:id?<[0-9]+>")
  @FrameTitle("Klantformulier")
  public class FormView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Long customerId = 0L;
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
    private TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
    private TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));
    private ChoiceBox country = new ChoiceBox("Land",
        e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
    private Button submit = new Button("Indienen", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
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
`}
</ExpandableCode>

## Navigeren van `MainView` naar `FormView` om klanten te bewerken {#navigating-from-mainview-to-formview-to-edit-customers}

Eerder in deze stap gebruikte je een bestaande `ParametersBag` om de waarde van een `id` te bepalen. Het creëren van een nieuwe `ParametersBag` laat je toe om direct tussen klassen te navigeren met de parameters van jouw keuze. Het gebruik van de gegevens in de `Table` is een haalbare optie om gebruikers naar `FormView` te sturen met een klant `id`.

Net als bij de knop, laat het koppelen van de navigatie aan een door de gebruiker gekozen actie hen beslissen wanneer ze naar `FormView` gaan. Het toevoegen van een gebeurtenisluis aan de `Table` stelt je in staat om de gebruiker naar `FormView` te sturen met een `ParametersBag`:

```java
table.addItemClickListener(this::editCustomer);

private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
      ParametersBag.of("id=" + e.getItemKey()));
}
```

Echter, de sleutel van de `Table`-items wordt standaard automatisch gegenereerd. Je kunt expliciet elke sleutel maken die samenhangt met een klant `id` door de methode `setKeyProvider()` te gebruiken:

```java
table.setKeyProvider(Customer::getId);
```

In `MainView`, voeg je de methoden `addItemClickListener()` en `setKeyProvider()` toe aan `buildTable()`, en voeg daarna de methode die de gebruiker naar `FormView` stuurt met een waarde voor de `id` in de `ParametersBag` op basis van waar in de tabel de gebruiker klikte:

```java title="MainView.java" {30-31,34-37}
@Route("/")
@FrameTitle("Klanten Tabel")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Voeg Klant Toe", ButtonTheme.PRIMARY,
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
    table.addColumn("firstName", Customer::getFirstName).setLabel("Voornaam");
    table.addColumn("lastName", Customer::getLastName).setLabel("Achternaam");
    table.addColumn("company", Customer::getCompany).setLabel("Bedrijf");
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

## Volgende stap {#next-step}

Nu gebruikers klantgegevens rechtstreeks kunnen bewerken, moet je app de wijzigingen valideren voordat deze in de repository worden opgeslagen. In [Valideren en Koppelen van Gegevens](/docs/introduction/tutorial/validating-and-binding-data) ga je validatieregels creëren en direct het gegevensmodel met de UI associëren, zodat de componenten foutmeldingen kunnen weergeven wanneer de gegevens ongeldig zijn.
