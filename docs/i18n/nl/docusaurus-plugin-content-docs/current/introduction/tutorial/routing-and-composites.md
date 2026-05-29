---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 673861b579764a7f9b81512fc0b1e576
---
Tot nu toe was deze tutorial slechts een single-page app. Deze stap verandert dat. 
Je verplaatst de UI die je hebt gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data) naar zijn eigen pagina en maakt een andere pagina voor het toevoegen van nieuwe klanten.
Vervolgens verbind je deze pagina's zodat je app in staat is om tussen hen te navigeren door deze concepten toe te passen:

- [Routing](/docs/routing/overview)
- [Samenstellende componenten](/docs/building-ui/composite-components)
- De [`ColumnsLayout`](/docs/components/columns-layout) component

Het voltooien van deze stap creëert een versie van [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenliggende map die het `pom.xml` bestand bevat; dit is `3-routing-and-composites` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Routeerbare apps {#routable-apps}

Voorheen had je app één functie: het weergeven van een tabel met bestaande klantgegevens. 
In deze stap kan je app ook de klantgegevens wijzigen door nieuwe klanten toe te voegen.
Het scheiden van de UI's voor weergave en wijziging is gunstig voor langdurig onderhoud en testen, dus je voegt deze functie toe als een aparte pagina.
Je maakt je app [routeerbaar](/docs/routing/overview) zodat webforJ de twee UI's afzonderlijk kan benaderen en laden.

Een routeerbare app rendert de UI op basis van de URL. Het annoteren van de klasse die de `App` klasse uitbreidt met [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) maakt routing mogelijk, en het `packages` element vertelt webforJ welke pakketten UI-componenten bevatten.

Wanneer je de `@Routify` annotatie aan `Application` toevoegt, verwijder je de `run()` methode. Je verplaatst de componenten uit die methode naar een klasse die je maakt in het `com.webforj.tutorial.views` pakket. Je bijgewerkte `Application.java` bestand zou er als volgt uit moeten zien:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//Toegevoegde @Routify annotatie
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Verwijderde de overschreven App.run() methode

}
```

:::tip Globale CSS
Het behouden van de `@StyleSheet` annotatie in `Application` past die CSS globaal toe.
:::

### Routes aanmaken {#creating-routes}

Het toevoegen van de `@Routify` annotatie maakt je app routeerbaar. Zodra het routeerbaar is, zal je app in het `com.webforj.tutorial.views` pakket naar routes zoeken. 
Je moet de routes voor je UI's creëren en ook hun [Route Types](/docs/routing/route-hierarchy/route-types) specificeren. Het type route bepaalt hoe de UI-inhoud aan de URL wordt gekoppeld.

Het eerste roettype is `View`. Dit soort routes worden rechtstreeks aan een specifiek URL-segment in je app gekoppeld. De UI's voor de tabel en het formulier voor nieuwe klanten zijn beide `View` routes.

Het tweede roettype is `Layout`, dat UI bevat die op meerdere pagina's verschijnt, zoals een header of zijbalk. Layout-routes omsluiten ook kindweergaven zonder bij te dragen aan de URL.

Om het type route van een klasse te specificeren, voeg je het roettype als een suffix aan het einde van de klassenaam toe.
Bijvoorbeeld, `MainView` is een `View` roettype.

Om de twee functies van de app gescheiden te houden, moet je app de UI's aan twee unieke `View` routes koppelen: één voor de tabel en één voor het klantformulier. Maak in `/src/main/java/com/webforj/tutorial/views` twee klassen met een `View` suffix:

- **`MainView`**: Deze view zal de `Table` hebben die eerder in de `Application` klasse zat.
- **`FormView`**: Deze view zal een formulier hebben voor het toevoegen van nieuwe klanten.

### URLs aan componenten toewijzen {#mapping-urls-to-components}

Je app is routeerbaar en weet dat het naar twee `View` routes moet kijken, `MainView` en `FormView`, maar het heeft geen specifieke URL om ze te laden. Met behulp van de `@Route` annotatie op een weergaveklasse, kun je webforJ vertellen waar het deze moet laden op basis van een gegeven URL-segment. Bijvoorbeeld, het gebruik van `@Route("about")` in een view koppelt de klasse lokaal aan `http://localhost:8080/about`.

Zoals de naam al aangeeft, is `MainView` de klasse die je wilt laden wanneer de app draait. Om dit te bereiken, voeg je een `@Route` annotatie toe die `MainView` koppelt aan de root-URL van je app:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Voor de `FormView`, koppel de view zodat deze wordt geladen wanneer een gebruiker naar `http://localhost:8080/customer` gaat:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standaardgedrag
Als je geen waarde voor de `@Route` annotatie toekent, is het URL-segment de klassenaam die naar kleine letters is omgezet, met de `View` suffix verwijderd.

- `MainView` zou worden gekoppeld aan `/main`
- `FormView` zou worden gekoppeld aan `/form`
:::

## Gedeelde kenmerken {#shared-characteristics}

Naast dat beide view-routes zijn, delen `MainView` en `FormView` aanvullende kenmerken. Sommige van deze gedeelde eigenschappen, zoals het gebruik van `Composite` componenten, zijn fundamenteel voor het gebruik van webforJ-apps, terwijl andere het gemakkelijker maken om je app te beheren.

### Het gebruik van `Composite` componenten {#using-composite-components}

Toen de app een enkele pagina was, bewaarde je de componenten binnen een `Frame`. Voortaan, met een app met meerdere views, moet je die UI-componenten binnen [`Composite` componenten](/docs/building-ui/composite-components) wikkelen.

`Composite` componenten zijn wrappers die het gemakkelijk maken om herbruikbare componenten te creëren. 
Om een `Composite` component te maken, breid je de `Composite` klasse uit met een opgegeven gebonden component dat als basis van de klasse dient, bijv. `Composite<FlexLayout>`. 

Deze tutorial gebruikt `Div` elementen als de gebonden componenten, maar ze kunnen elk component zijn, zoals [`FlexLayout`](/docs/components/flex-layout) of [`AppLayout`](/docs/components/app-layout). Met de `getBoundComponent()` methode kun je de gebonden component verwijzen en toegang krijgen tot zijn methoden. Dit stelt je in staat om de afmetingen in te stellen, een CSS-klassenaam toe te voegen, componenten toe te voegen die je in de `Composite` component wilt weergeven, en toegang te krijgen tot component-specifieke methoden.

Voor `MainView` en `FormView`, breid `Composite` uit met `Div` als de gebonden component. Vervolgens, verwijs die gebonden component zodat je later de UI's kunt toevoegen. Beide views zouden er ongeveer zo uit moeten zien:

```java
// Breid Composite uit met een gebonden component
public class MainView extends Composite<Div> {

  // Toegang tot de gebonden component
  private Div self = getBoundComponent();

  // Maak een component UI
  private Button submit = new Button("Indienen");

  public MainView() {

    // Voeg de UI-component toe aan de gebonden component
    self.add(submit);
  }
}
```

### De frametitel instellen {#setting-the-frame-tile}

Wanneer een gebruiker meerdere tabbladen in hun browser heeft, helpt een unieke frametitel hen snel te identificeren welk deel van de app ze hebben geopend.

De [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotatie definieert wat er in de titel van de browser of het tabblad van de pagina verschijnt. Voor beide views voeg je een frametitel toe met behulp van de `@FrameTitle` annotatie:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Klantentabel")
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
  @FrameTitle("Klantformulier")
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
Je kunt de CSS van de eerste stap, [Een Basis App Creëren](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), gebruiken om beide views identieke UI-containerstijlen te geven. 
Voeg de CSS-klassenaam `card` toe aan de gebonden component in elke view:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Klantentabel")
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
    @FrameTitle("Klantformulier")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### Het gebruik van `CustomerService` {#using-customerservice}

De laatste gedeelde eigenschap voor de views is het gebruik van de `CustomerService` klasse.
De `Table` in `MainView` toont elke klant, terwijl `FormView` nieuwe klanten toevoegt. Aangezien beide views interactie hebben met klantgegevens, hebben ze toegang nodig tot de bedrijfslogica van de app. 

De views krijgen toegang via de Spring-service die is gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Om de Spring-service in elke view te gebruiken, maak je `CustomerService` een constructorparameter:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Klantentabel")
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
    @FrameTitle("Klantformulier")
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

## `MainView` creëren {#creating-mainview}

Nadat je je app routeerbaar hebt gemaakt, de views een `Composite` component wrapper hebt gegeven, en `CustomerService` hebt ingesloten, ben je klaar om de UI's te bouwen die specifiek zijn voor elke view. Zoals eerder genoemd, bevat `MainView` de UI-componenten die aanvankelijk in `Application` zaten. Deze klasse heeft ook een manier nodig om naar `FormView` te navigeren.

### De `Table` methoden groeperen {#grouping-the-table-methods}

Terwijl je de componenten uit `Application` naar `MainView` verplaatst, is het een goed idee om delen van je app te segmenteren, zodat één aangepaste methode wijzigingen aan de `Table` in één keer kan aanbrengen. Het segmenteren van je code maakt het nu beheersbaarder naarmate de app complexer wordt.

Nu moet je `MainView` constructor alleen één `buildTable()` methode aanroepen die de kolommen toevoegt, de afmetingen instelt en de repository verwijst:

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

Gebruikers hebben een manier nodig om van `MainView` naar `FormView` te navigeren via de UI.

In webforJ kun je direct naar een nieuwe view navigeren door de klasse van de view te gebruiken. Routeren via een klasse in plaats van een URL-segment garandeert dat webforJ het juiste pad volgt om de view te laden. 

Om naar een andere view te navigeren, gebruik je de [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) klasse om de huidige locatie op te halen met `getCurrent()`, en gebruik vervolgens de `navigate()` methode met de klasse van de view als parameter: 

```java
Router.getCurrent().navigate(FormView.class);
```

Deze code zal gebruikers programatisch naar het nieuwe klantformulier sturen, maar de navigatie moet aan een gebruikersactie worden verbonden.
Om gebruikers in staat te stellen een nieuwe klant toe te voegen, kun je de info-knop van `Application` wijzigen of vervangen. In plaats van een berichtdialoog te openen, kan de knop naar de `FormView` klasse navigeren:

```java
private Button addCustomer = new Button("Klant Toevoegen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Voltooid `MainView` {#completed-mainview}

Met de navigatie naar `FormView` en gegroepeerde tabelmethoden, zou `MainView` er als volgt uit moeten zien voordat je verdergaat met het creëren van `FormView`:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
  @FrameTitle("Klantentabel")
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
`}
</ExpandableCode>
<!-- vale on -->

## `FormView` creëren {#creating-formview}

`FormView` zal een formulier tonen om nieuwe klanten toe te voegen. Voor elke klant-eigenschap zal `FormView` een bewerkbaar component hebben voor gebruikers om mee te interageren. Daarnaast zal het een knop hebben voor gebruikers om de gegevens in te dienen en een annuleerknop om deze te verwerpen.

### Een `Customer` instantie aanmaken {#creating-a-customer-instance}

Wanneer een gebruiker gegevens voor een nieuwe klant bewerkt, moeten wijzigingen pas op de repository worden toegepast wanneer ze klaar zijn om het formulier in te dienen. Het gebruik van een instantie van het `Customer` object is een handige manier om de nieuwe gegevens te bewerken en te onderhouden zonder de repository direct te bewerken. Maak een nieuwe `Customer` binnen `FormView` om voor het formulier te gebruiken:

```java
private Customer customer = new Customer();
```

Om de `Customer` instantie bewerkbaar te maken, moet elke eigenschap, met uitzondering van de `id`, worden geassocieerd met een bewerkbaar component. De wijzigingen die een gebruiker in de UI aanbrengt, moeten worden weerspiegeld in de `Customer` instantie.

### `TextField` componenten toevoegen {#adding-textfield-components}

De eerste drie bewerkbare eigenschappen in `Customer` (`firstName`, `lastName`, en `company`) zijn allemaal `String` waarden en moeten worden weergegeven met een eenregelige teksteditor. [`TextField`](/docs/components/fields/textfield) componenten zijn een uitstekende keuze om deze eigenschappen weer te geven.

Met de `TextField` component kun je een label toevoegen en een gebeurtenislistener die wordt geactiveerd telkens wanneer de waarde van het veld verandert. Elke gebeurtenislistener moet de `Customer` instantie bijwerken voor de bijbehorende eigenschap.

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
Componenten dezelfde naam geven als de eigenschappen die ze vertegenwoordigen voor de `Customer` entiteit maakt het gemakkelijker om gegevens te binden in een toekomstige stap, [Valideren en Binden van Gegevens](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Een `ChoiceBox` component toevoegen {#adding-a-choicebox-component}

Het gebruik van een `TextField` voor de `country` eigenschap zou niet ideaal zijn, omdat de eigenschap slechts één van vijf enum-waarden kan zijn: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY`, en `USA`.

Een beter component voor het selecteren uit een vooraf gedefinieerde lijst van opties is de [`ChoiceBox`](/docs/components/lists/choicebox).

Elke optie voor een `ChoiceBox` component wordt vertegenwoordigd als een `ListItem`. Elke `ListItem` heeft twee waarden, een `Object` sleutel en een `String` tekst om in de UI weer te geven. Het hebben van twee waarden voor elke optie stelt je in staat om het `Object` intern te beheren terwijl je tegelijkertijd een meer leesbare optie voor gebruikers in de UI presenteert.

Bijvoorbeeld, de `Object` sleutel kan een Internationale Standaard Boeknummer (ISBN) zijn, terwijl de `String` tekst de titel van het boek is, die begrijpelijker is voor mensen.

```java
new ListItem(isbn, bookTitle);
```

Echter, deze app gaat over een lijst van landen, niet boeken. Voor elke `ListItem` wil je dat het `Object` de `Customer.Country` enum is, terwijl de tekst de stringrepresentatie ervan kan zijn.

Om alle `country` opties aan een `ChoiceBox` toe te voegen, kun je een iterator gebruiken om een `ListItem` voor elke `Customer.Country` enum te maken en deze in een `ArrayList<ListItem>` te plaatsen. Vervolgens kun je die `ArrayList<ListItem>` in een `ChoiceBox` component invoegen:

```java
//Creëer de ChoiceBox component
private ChoiceBox country = new ChoiceBox("Land");

//Creëer een ArrayList van ListItem objecten
ArrayList<ListItem> listCountries = new ArrayList<>();

//Voeg een iterator toe die een ListItem voor elke Customer.Country optie aanmaakt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Plaats de ingevulde ArrayList in de ChoiceBox
country.insert(listCountries);

//Maak het eerste `ListItem` de standaard wanneer het formulier wordt geladen
country.selectIndex(0);
```

Wanneer de gebruiker een optie in de `ChoiceBox` selecteert, moet de `Customer` instantie worden bijgewerkt met de sleutel van het geselecteerde item, wat een waarde van `Customer.Country` is.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Om de code georganiseerd te houden, moet de iterator die de `ArrayList<ListItem>` aanmaakt en deze aan de `ChoiceBox` toevoegt in een aparte methode staan. 
Nadat je een `ChoiceBox` hebt toegevoegd die de gebruiker in staat stelt de `country` eigenschap te kiezen, zou `FormView` er als volgt uit moeten zien:

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

### Componenten van het type `Button` toevoegen {#adding-button-components}

Bij het gebruik van het nieuwe klantformulier, moeten gebruikers in staat zijn om hun wijzigingen op te slaan of te annuleren.
Maak twee `Button` componenten om deze functie te implementeren:

```java
private Button submit = new Button("Indienen");
private Button cancel = new Button("Annuleren");
```

Zowel de indienen- als de annuleerknop moeten de gebruiker terug naar `MainView` brengen.
Dit stelt de gebruiker in staat om onmiddellijk de resultaten van hun actie te zien, of ze nu een nieuwe klant in de tabel zien of dat deze ongewijzigd blijft. 
Aangezien meerdere invoer in `FormView` de gebruiker naar `MainView` brengen, moet de navigatie in een oproepbare methode worden geplaatst:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Annuleerknop**

Het afbreken van de wijzigingen op het formulier vereist geen extra code voor de gebeurtenis buiten het terugkeren naar `MainView`. Aangezien annuleren echter geen primaire actie is, geeft het instellen van het thema van de knop op een outline de indienen-knop meer prominente aandacht. 
De [Thema's](/docs/components/button#themes) sectie van de `Button` component pagina bevat een lijst van alle beschikbare thema's.


```java
private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Indienen-knop**

Wanneer een gebruiker op de indienen-knop drukt, moeten de waarden in de `Customer` instantie worden gebruikt om een nieuwe invoer in de repository te creëren.

Met behulp van de `CustomerService` kun je de `Customer` instantie gebruiken om de H2-database bij te werken. Wanneer dit gebeurt, wordt er een nieuwe en unieke `id` aan die `Customer` toegewezen. Nadat de repository is bijgewerkt, kun je de gebruikers omleiden naar `MainView`, waar ze de nieuwe klant in de tabel kunnen zien.

```java
private Button submit = new Button("Indienen", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Het gebruik van een `ColumnsLayout` {#using-a-columnslayout}

Door de `TextField`, `ChoiceBox`, en `Button` componenten toe te voegen, heb je nu alle interactieve onderdelen van het formulier. De laatste verbetering aan `FormView` in deze stap is om de zes componenten visueel te organiseren.

Dit formulier kan een [`ColumnsLayout`](/docs/components/columns-layout) gebruiken om de componenten in twee kolommen te scheiden zonder de breedte van de interactieve componenten in te stellen.
Om een `ColumnsLayout` te creëren, geef elke component op die binnen de layout moet worden geplaatst:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Om het aantal kolommen voor een `ColumnsLayout` in te stellen, gebruik je een `List` van `Breakpoint` objecten. Elke `Breakpoint` vertelt de `ColumnsLayout` de minimale breedte die het moet hebben om een opgegeven aantal kolommen toe te passen. Door gebruik te maken van de `ColumnsLayout`, kun je een formulier met twee kolommen maken, maar alleen als het scherm breed genoeg is om twee kolommen weer te geven. Op kleinere schermen worden de componenten in een enkele kolom weergegeven.

De [Breakpoints](/docs/components/columns-layout#breakpoints) sectie in het `ColumnsLayout` artikel legt breakpoints in meer detail uit.

Om de code onderhoudbaar te houden, stel je de breakpoints in een aparte methode in. In die methode kun je ook de horizontale en verticale ruimte tussen de componenten binnen de `ColumnsLayout` regelen met de `setSpacing()` methode.

```java
private void setColumnsLayout() {

  //Heb twee kolommen in de ColumnsLayout als het breder is dan 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Voeg de lijst van breakpoints toe
  layout.setBreakpoints(breakpoints);

  //Stel de ruimte tussen componenten in met behulp van een DWC CSS-variabele
  layout.setSpacing("var(--dwc-space-l)")
}
```

Ten slotte kun je de nieuw gemaakte `ColumnsLayout` toevoegen aan de gebonden component van `FormView`, terwijl je ook de maximale breedte instelt en de klassenaam van eerder toevoegt:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Voltooid `FormView` {#completed-formview}

Nadat je een `Customer` instantie, de interactieve componenten en de `ColumnsLayout` hebt toegevoegd, zou je `FormView` er als volgt uit moeten zien:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("Klantformulier")
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
`}
</ExpandableCode>
<!-- vale on -->

## Volgende stap {#next-step}

Aangezien gebruikers nu klanten kunnen toevoegen, moet je app ook in staat zijn om bestaande klanten te bewerken met behulp van hetzelfde formulier. In de volgende stap, [Observers en Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), laat je de klant `id` een initiële parameter voor `FormView` zijn, zodat het formulier kan worden ingevuld met de gegevens van die klant en gebruikers de eigenschappen kunnen wijzigen.
