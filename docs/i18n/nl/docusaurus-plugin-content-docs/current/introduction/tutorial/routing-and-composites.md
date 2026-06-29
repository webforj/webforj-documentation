---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 1ffb9e9bf7ba8d863dad3bc0c42c11d7
---
Tot nu toe was deze tutorial slechts een single-page app. Deze stap verandert dat. 
Je verplaatst de UI die je hebt gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data) naar zijn eigen pagina en maakt een andere pagina om nieuwe klanten toe te voegen. 
Vervolgens verbind je deze pagina's zodat je app in staat is om tussen hen te navigeren door deze concepten toe te passen:

- [Routing](/docs/routing/overview)
- [Componenten samenstellen](/docs/building-ui/composing-components)
- De [`ColumnsLayout`](/docs/components/columns-layout) component

Het voltooien van deze stap creëert een versie van [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Video hier invoegen -->

## De app draaien {#running-the-app}

Terwijl je je app ontwikkelt, kun je [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de top-level directory met het `pom.xml` bestand; dit is `3-routing-and-composites` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het draaien van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Routeerbare apps {#routable-apps}

Voorheen had je app één functie: het weergeven van een tabel met bestaande klanten. 
In deze stap kan je app ook de klantgegevens aanpassen door nieuwe klanten toe te voegen. 
Het scheiden van de UIs voor weergave en wijziging is voordelig voor langetermijnonderhoud en testen, dus je voegt deze functie toe als een aparte pagina. 
Je maakt je app [routeerbaar](/docs/routing/overview) zodat webforJ de twee UIs afzonderlijk kan openen en laden.

Een routeerbare app render de UI op basis van de URL. Het annoteren van de klasse die de `App` klasse uitbreidt met [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) stelt routing in, en het `packages` element vertelt webforJ welke pakketten UI-componenten bevatten.

Wanneer je de `@Routify` annotatie aan `Application` toevoegt, verwijder dan de `run()` methode. Je verplaatst de componenten van die methode naar een klasse die je maakt in het `com.webforj.tutorial.views` pakket. Je bijgewerkte `Application.java` bestand zou er als volgt uit moeten zien:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//Toegevoegd @Routify annotatie
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Verwijderde overschreven App.run() methode

}
```

:::tip Global CSS
Het behouden van de `@StyleSheet` annotatie in `Application` past die CSS globaal toe.
:::

### Routes maken {#creating-routes}

Het toevoegen van de `@Routify` annotatie maakt je app routeerbaar. Zodra het routeerbaar is, gaat je app in het `com.webforj.tutorial.views` pakket op zoek naar routes. 
Je moet de routes voor je UIs creëren en ook hun [Route Types](/docs/routing/route-hierarchy/route-types) specificeren. Het route type bepaalt hoe de UI-inhoud aan de URL wordt gekoppeld.

Het eerste route type is `View`. Deze soorten routes worden direct gekoppeld aan een specifiek URL-segment in je app. De UIs voor de tabel en het formulier voor nieuwe klanten zullen beide `View` routes zijn.

Het tweede route type is `Layout`, dat UI bevat die op meerdere pagina's voorkomt, zoals een header of zijbalk. Layout-routes wikkelen ook kindzicht zonder bij te dragen aan de URL.

Om het route type van een klasse op te geven, voeg je het route type als een suffix toe aan de naam van de klasse. 
Bijvoorbeeld, `MainView` is een `View` route type.

Om de twee functies van de app gescheiden te houden, moet je de UIs toewijzen aan twee unieke `View` routes: één voor de tabel en één voor het klantenformulier. Maak in `/src/main/java/com/webforj/tutorial/views` twee klassen met een `View` suffix:

- **`MainView`**: Deze view zal de `Table` bevatten die eerder in de `Application` klasse stond.
- **`FormView`**: Deze view zal een formulier bevatten voor het toevoegen van nieuwe klanten.

### URL's aan componenten koppelen {#mapping-urls-to-components}

Je app is routeerbaar en weet om te kijken naar twee `View` routes, `MainView` en `FormView`, maar het heeft geen specifieke URL om ze te laden. Met behulp van de `@Route` annotatie op een view klasse, kun je webforJ vertellen waar het moet laden op basis van een gegeven URL-segment. Bijvoorbeeld, met `@Route("about")` wordt lokaal de klasse gekoppeld aan `http://localhost:8080/about`.

Zoals de naam al aangeeft, is `MainView` de klasse die je aanvankelijk wilt laden wanneer de app draait. Om dit te bereiken, voeg je een `@Route` annotatie toe die `MainView` aan de root-URL van je app koppelt:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Voor de `FormView`, koppel je de view zodat deze laadt wanneer een gebruiker naar `http://localhost:8080/customer` gaat:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standaard gedrag
Als je expliciet geen waarde aan de `@Route` annotatie toewijst, is het URL-segment de naam van de klasse omgezet naar kleine letters, met de `View` suffix verwijderd.

- `MainView` zou worden gekoppeld aan `/main`
- `FormView` zou worden gekoppeld aan `/form`
:::

## Gedeelde kenmerken {#shared-characteristics}

Naast dat ze beide view routes zijn, delen `MainView` en `FormView` aanvullende kenmerken. Sommige van deze gedeelde eigenschappen, zoals het gebruik van `Composite` componenten, zijn fundamenteel voor het gebruik van webforJ apps, terwijl andere het gewoon gemakkelijker maken om je app te beheren.

### Gebruik van `Composite` componenten {#using-composite-components}

Toen de app een single-page was, bewaarde je de componenten binnen een `Frame`. Voortaan, met een app met meerdere views, moet je die UI-componenten wikkelen in [`Composite` componenten](/docs/building-ui/composing-components).

`Composite` componenten zijn wrappers die het gemakkelijk maken om herbruikbare componenten te creëren. 
Om een `Composite` component te creëren, breid je de `Composite` klasse uit met een opgegeven gebonden component die als fundament van de klasse dient, bijv. `Composite<FlexLayout>`. 

Deze tutorial gebruikt `Div` elementen als de gebonden componenten, maar ze kunnen elke component zijn, zoals [`FlexLayout`](/docs/components/flex-layout) of [`AppLayout`](/docs/components/app-layout). Met de `getBoundComponent()` methode kun je naar de gebonden component verwijzen en toegang krijgen tot de methoden ervan. Dit stelt je in staat om de grootte in te stellen, een CSS-klassenaam toe te voegen, componenten toe te voegen die je in de `Composite` component wilt weergeven, en toegang te krijgen tot component-specifieke methoden.

Voor `MainView` en `FormView`, breid je `Composite` uit met `Div` als de gebonden component. Verwijs vervolgens naar die gebonden component zodat je de UIs later kunt toevoegen. Beide views zouden er ongeveer als volgt uit moeten zien:

```java
// Breid Composite uit met een gebonden component
public class MainView extends Composite<Div> {

  // Toegang tot de gebonden component
  private Div self = getBoundComponent();

  // Maak een component UI
  private Button submit = new Button("Indienen");

  public MainView() {

    // Voeg de UI component toe aan de gebonden component
    self.add(submit);
  }
}
```

### De title van het frame instellen {#setting-the-frame-tile}

Wanneer een gebruiker meerdere tabbladen in zijn browser heeft, helpt een unieke title voor het frame hen snel te identificeren welk deel van de app ze hebben geopend.

De [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotatie definieert wat er in de titel van de browser of het tabblad van de pagina verschijnt. Voor beide views voeg je een frame title toe met behulp van de `@FrameTitle` annotatie:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Klanten Tabel")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("Klanten Formulier")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Gedeelde CSS {#shared-css}

Met een gebonden component waar je naar kunt verwijzen in `MainView` en `FormView`, kun je het stylen met CSS. 
Je kunt de CSS uit de eerste stap, [Een Basis App Maken](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), gebruiken om beide views identieke UI-containerstijlen te geven. 
Voeg de CSS-klassenaam `card` toe aan de gebonden component in elke view:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Klanten Tabel")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("Klanten Formulier")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### Gebruik van `CustomerService` {#using-customerservice}

De laatste gedeelde eigenschap voor de views is het gebruik van de `CustomerService` klasse.
De `Table` in `MainView` toont elke klant, terwijl `FormView` nieuwe klanten toevoegt. Omdat beide views met klantgegevens werken, moeten ze toegang hebben tot de bedrijfslogica van de app. 

De views krijgen toegang via de Spring-service die is gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Om de Spring-service in elke view te gebruiken, maak je `CustomerService` een constructorparameter:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Klanten Tabel")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("Klanten Formulier")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## Het maken van `MainView` {#creating-mainview}

Nadat je je app routeerbaar hebt gemaakt, de views hebt voorzien van `Composite` component wrappers en `CustomerService` hebt opgenomen, ben je klaar om de UIs uniek voor elke view te bouwen. Zoals eerder vermeld, bevat `MainView` de UI-componenten die oorspronkelijk in `Application` stonden. Deze klasse heeft ook een manier nodig om naar `FormView` te navigeren.

### Groeperen van de `Table` methoden {#grouping-the-table-methods}

Terwijl je de componenten van `Application` naar `MainView` verplaatst, is het een goed idee om te beginnen met het sectie maken van delen van je app, zodat één aangepaste methode wijzigingen in de `Table` in één keer kan aanbrengen. Het sectioneren van je code maakt het nu gemakkelijker te beheren naarmate de app complexer wordt.

Nu zou de constructor van je `MainView` slechts één `buildTable()` methode moeten aanroepen die de kolommen toevoegt, de grootte instelt en de repository verwijst:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Voornaam");
  table.addColumn("lastName", Customer::getLastName).setLabel("Achternaam");
  table.addColumn("company", Customer::getCompany).setLabel("Bedrijf");
  table.addColumn("country", Customer::getCountry).setLabel("Land");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navigeren naar `FormView`{#navigating-to-formview}

Gebruikers hebben een manier nodig om van `MainView` naar `FormView` te navigeren met de UI.

In webforJ kun je direct naar een nieuwe view navigeren door de klasse van de view te gebruiken. Routeren via een klasse in plaats van een URL-segment garandeert dat webforJ het juiste pad volgt om de view te laden. 

Om naar een andere view te navigeren, gebruik de [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) klasse om de huidige locatie te krijgen met `getCurrent()`, en gebruik vervolgens de `navigate()` methode met de klasse van de view als parameter: 

```java
Router.getCurrent().navigate(FormView.class);
```

Deze code zal gebruikers programatisch naar het nieuwe klantenformulier sturen, maar de navigatie moet worden gekoppeld aan een gebruikersactie.
Om gebruikers toe te staan een nieuwe klant toe te voegen, kun je de info-knop van `Application` wijzigen of vervangen. In plaats van een berichtdialoog te openen, kan de knop naar de `FormView` klasse navigeren:

```java
private Button addCustomer = new Button("Klant Toevoegen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Voltooide `MainView` {#completed-mainview}

Met de navigatie naar `FormView` en gegroepeerde tabelmethoden zou hier eruit moeten zien wat `MainView` eruit zou moeten zien voordat je verder gaat met het maken van `FormView`:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Klanten Tabel")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Klant Toevoegen", ButtonTheme.PRIMARY,
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
  }

}
```

</ExpandableCode>
<!-- vale on -->

## Het maken van `FormView` {#creating-formview}

`FormView` zal een formulier weergeven om nieuwe klanten toe te voegen. Voor elke klant-eigenschap zal `FormView` een bewerkbaar component hebben voor gebruikers om mee te interageren. Daarnaast zal er een knop zijn voor gebruikers om de gegevens in te dienen en een annuleren-knop om ze te verwerpen.

### Een `Customer` instantie maken {#creating-a-customer-instance}

Wanneer een gebruiker gegevens voor een nieuwe klant bewerkt, moeten wijzigingen pas op de repository worden toegepast wanneer ze klaar zijn om het formulier in te dienen. Het gebruik van een instantie van het `Customer` object is een handige manier om de nieuwe gegevens te bewerken en te behouden zonder de repository direct te bewerken. Maak een nieuwe `Customer` in `FormView` om te gebruiken voor het formulier:

```java
private Customer customer = new Customer();
```

Om de `Customer` instantie bewerkbaar te maken, moet elke eigenschap, behalve de `id`, worden geassocieerd met een bewerkbaar component. De wijzigingen die een gebruiker in de UI aanbrengt, moeten worden weerspiegeld in de `Customer` instantie.

### `TextField` componenten toevoegen {#adding-textfield-components}

De eerste drie bewerkbare eigenschappen in `Customer` (`firstName`, `lastName` en `company`) zijn allemaal `String` waarden en moeten worden weergegeven met een enkele-regel tekstverwerker. [`TextField`](/docs/components/fields/textfield) componenten zijn een uitstekende keuze om deze eigenschappen weer te geven.

Met de `TextField` component kun je een label toevoegen en een event listener die wordt geactiveerd telkens wanneer de veldwaarde verandert. Elke event listener moet de `Customer` instantie bijwerken voor de overeenkomstige eigenschap.

Voeg drie `TextField` componenten toe die de `Customer` instantie bijwerken:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Gedeelde naamgevingsconventie
De componenten dezelfde naam geven als de eigenschappen die ze vertegenwoordigen voor de `Customer` entiteit, maakt het gemakkelijker om gegevens te binden in een toekomstige stap, [Valideren en Binden van Gegevens](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Een `ChoiceBox` component toevoegen {#adding-a-choicebox-component}

Het gebruik van een `TextField` voor de `country` eigenschap zou niet ideaal zijn, omdat de eigenschap slechts een van vijf enum waarden kan zijn: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` en `USA`.

Een betere component voor het selecteren uit een vooraf gedefinieerde lijst met opties is de [`ChoiceBox`](/docs/components/lists/choicebox).

Elke optie voor een `ChoiceBox` component wordt weergegeven als een `ListItem`. Elke `ListItem` heeft twee waarden, een `Object` sleutel en een `String` tekst om in de UI weer te geven. Het hebben van twee waarden voor elke optie stelt je in staat om het `Object` intern te verwerken en tegelijkertijd een meer leesbare optie voor gebruikers in de UI te presenteren.

Bijvoorbeeld, de `Object` sleutel zou een Internationale Standaard Boek Nummer (ISBN) kunnen zijn, terwijl de `String` tekst de titel van het boek is, wat meer menselijk leesbaar is.

```java
new ListItem(isbn, bookTitle);
```

Echter, deze app heeft te maken met een lijst van landennamen, niet met boeken. Voor elke `ListItem` wil je dat het `Object` de `Customer.Country` enum is, terwijl de tekst de `String` representatie ervan kan zijn.

Om alle `country` opties in een `ChoiceBox` toe te voegen, kun je een iterator gebruiken om een `ListItem` voor elke `Customer.Country` enum te creëren en deze in een `ArrayList<ListItem>` te plaatsen. Vervolgens kun je die `ArrayList<ListItem>` in een `ChoiceBox` component invoegen:

```java
// Maak de ChoiceBox component
private ChoiceBox country = new ChoiceBox("Land");

// Maak een ArrayList van ListItem objecten
ArrayList<ListItem> listCountries = new ArrayList<>();

// Voeg een iterator toe die een ListItem voor elke Customer.Country optie creëert
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Voeg de ingevulde ArrayList in de ChoiceBox in
country.insert(listCountries);

// Maakt de eerste `ListItem` de standaard wanneer het formulier wordt geladen
country.selectIndex(0);
```

Vervolgens, wanneer de gebruiker een optie in de `ChoiceBox` selecteert, moet de `Customer` instantie worden bijgewerkt met de sleutel van het geselecteerde item, wat een `Customer.Country` waarde is.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Om de code schoon te houden, moet de iterator die de `ArrayList<ListItem>` creëert en eraan toevoegt aan de `ChoiceBox` in een aparte methode zijn. 
Nadat je een `ChoiceBox` hebt toegevoegd waardoor de gebruiker de `country` eigenschap kan kiezen, zou `FormView` er als volgt uit moeten zien:

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Voornaam", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Achternaam", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Bedrijf", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("Land",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### `Button` componenten toevoegen {#adding-button-components}

Wanneer de nieuwe klantenform wordt gebruikt, moeten gebruikers in staat zijn om hun wijzigingen op te slaan of te verwerpen.
Creëer twee `Button` componenten om deze functie te implementeren:

```java
private Button submit = new Button("Indienen");
private Button cancel = new Button("Annuleren");
```

Zowel de indien- als de annuleren-knoppen moeten de gebruiker terug naar `MainView` brengen.
Dit stelt de gebruiker in staat om onmiddellijk de resultaten van hun actie te zien, of ze nu een nieuwe klant in de tabel zien of dat deze onveranderd blijft. 
Aangezien meerdere invoeren in `FormView` gebruikers naar `MainView` brengen, moet de navigatie in een oproepbare methode worden geplaatst:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Annuleerknop**

Het verwerpen van de wijzigingen in het formulier vereist geen extra code voor de gebeurtenis dan het terugkeren naar `MainView`. Echter, aangezien annuleren geen primaire actie is, geeft het thema van de knop een outline om de indien-knop meer nadruk te geven. 
De [Thema's](/docs/components/button#themes) sectie van de `Button` component pagina vermeldt alle beschikbare thema's.


```java
private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Indienenknop**

Wanneer een gebruiker op de indienen-knop drukt, moeten de waarden in de `Customer` instantie worden gebruikt om een nieuwe invoer in de repository te creëren.

Door gebruik te maken van de `CustomerService`, kun je de `Customer` instantie nemen om de H2-database bij te werken. Wanneer dit gebeurt, wordt er een nieuwe en unieke `id` toegewezen aan die `Customer`. Na het bijwerken van de repository kun je gebruikers omleiden naar `MainView`, waar ze de nieuwe klant in de tabel kunnen zien.

```java
private Button submit = new Button("Indienen", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Gebruik van een `ColumnsLayout` {#using-a-columnslayout}

Door de `TextField`, `ChoiceBox` en `Button` componenten toe te voegen, heb je nu alle interactieve onderdelen van het formulier. De laatste verbetering aan `FormView` in deze stap is om de zes componenten visueel te ordenen.

Dit formulier kan een [`ColumnsLayout`](/docs/components/columns-layout) gebruiken om de componenten in twee kolommen te scheiden zonder de breedte van enige interactieve componenten in te stellen.
Om een `ColumnsLayout` te creëren, geef je elke component op die binnen de lay-out moet worden opgenomen:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Om het aantal kolommen voor een `ColumnsLayout` in te stellen, gebruik je een `List` van `Breakpoint` objecten. Elke `Breakpoint` vertelt de `ColumnsLayout` de minimale breedte die deze moet hebben om een bepaald aantal kolommen toe te passen. Door de `ColumnsLayout` te gebruiken, kun je een formulier met twee kolommen maken, maar alleen als het scherm breed genoeg is om twee kolommen weer te geven. Op smallere schermen worden de componenten in één kolom weergegeven.

De [Breakpoints](/docs/components/columns-layout#breakpoints) sectie in het `ColumnsLayout` artikel legt breakpoints in meer detail uit.

Om de code onderhoudbaar te houden, stel je de breakpoints in een aparte methode in. In die methode kun je ook de horizontale en verticale spatiëring tussen de componenten binnen de `ColumnsLayout` beheren met de `setSpacing()` methode.

```java
private void setColumnsLayout() {

  // Heb twee kolommen in de ColumnsLayout als hij breder is dan 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Voeg de lijst van breakpoints toe
  layout.setBreakpoints(breakpoints);

  // Stel de spatiëring tussen componenten in met een DWC CSS-variabele
  layout.setSpacing("var(--dwc-space-l)")
}
```

Ten slotte kun je de nieuw gemaakte `ColumnsLayout` toevoegen aan de gebonden component van `FormView`, waarbij je ook de maximale breedte instelt en de klassenaam van eerder toevoegt:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Voltooide `FormView` {#completed-formview}

Na het toevoegen van een `Customer` instantie, de interactieve componenten en de `ColumnsLayout`, zou je `FormView` er als volgt moeten uitzien:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
@FrameTitle("Klanten Formulier")
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
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
    customerService.createCustomer(customer);
    navigateToMain();
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

}
```

</ExpandableCode>
<!-- vale on -->

## Volgende stap {#next-step}

Aangezien gebruikers nu klanten kunnen toevoegen, moet je app in staat zijn om bestaande klanten te bewerken met hetzelfde formulier. In de volgende stap, [Observers en Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), zul je het mogelijk maken dat de klant `id` een initiële parameter voor `FormView` is, zodat deze het formulier kan vullen met de gegevens van die klant en gebruikers in staat stelt de eigenschappen te wijzigen.
