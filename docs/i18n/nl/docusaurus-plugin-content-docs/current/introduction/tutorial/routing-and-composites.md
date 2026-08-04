---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 6016bff3255689b6be8a69997542a372
---
Tot nu toe is deze tutorial slechts een single-page app geweest. Deze stap verandert dat. Je zult de UI die je hebt gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data) naar zijn eigen pagina verplaatsen en een andere pagina creëren voor het toevoegen van nieuwe klanten. Vervolgens maak je deze pagina's met elkaar verbonden zodat je app in staat is om tussen hen te navigeren door deze concepten toe te passen:

- [Routing](/docs/routing/overview)
- [Componetteren](/docs/building-ui/composing-components)
- De [`ColumnsLayout`](/docs/components/columns-layout) component

Het voltooien van deze stap creëert een versie van [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Voeg hier video in -->

## De app uitvoeren {#running-the-app}

Tijdens de ontwikkeling van je app kun je [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de top-level directory die het `pom.xml` bestand bevat; dit is `3-routing-and-composites` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Routeerbare apps {#routable-apps}

Voorheen had je app slechts een functie: het weergeven van een tabel met bestaande klantgegevens. In deze stap zal je app ook in staat zijn om de klantgegevens te wijzigen door nieuwe klanten toe te voegen. Het scheiden van de UI's voor weergave en wijziging is voordelig voor langdurig onderhoud en testen, dus je voegt deze functie toe als een aparte pagina. Je zult je app [routeerbaar](/docs/routing/overview) maken zodat webforJ toegang kan krijgen tot en de twee UI's afzonderlijk kan laden.

Een routeerbare app rendert de UI op basis van de URL. Het annoteren van de klasse die de `App` klasse uitbreidt met [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) maakt routing mogelijk, en het `packages` element vertelt webforJ welke pakketten UI-componenten bevatten.

Wanneer je de `@Routify` annotatie toevoegt aan `Application`, verwijder je de `run()` methode. Je verplaatst de componenten uit die methode naar een klasse die je maakt in het `com.webforj.tutorial.views` pakket. Je bijgewerkte `Application.java` bestand zou er als volgt uit moeten zien:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")

// Toegevoegde @Routify annotatie
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Verwijderde overschreven App.run() methode

}
```

:::tip Globale CSS
Het behouden van de `@BundleEntry` annotatie in `Application` voegt het CSS-bestand toe aan de app-level frontend bundel, zodat de stijlen beschikbaar blijven in gerouteerde weergaven.
:::

### Routes maken {#creating-routes}

Het toevoegen van de `@Routify` annotatie maakt je app routeerbaar. Zodra het routeerbaar is, zal je app in het `com.webforj.tutorial.views` pakket naar routes zoeken. Je moet de routes voor je UI's creëren en ook hun [Route Types](/docs/routing/route-hierarchy/route-types) specificeren. Het type route bepaalt hoe de UI-inhoud aan de URL kan worden gekoppeld.

Het eerste type route is `View`. Dit soort routes koppelt direct naar een specifiek URL-segment in je app. De UI's voor de tabel en het formulier voor nieuwe klanten zullen beide `View` routes zijn.

Het tweede type route is `Layout`, dat UI bevat die op meerdere pagina's verschijnt, zoals een koptekst of zijbalk. Lay-outroutes omhullen ook child views zonder bij te dragen aan de URL.

Om het routetype van een klasse te specificeren, voeg je het routetype als een suffix aan de naam van de klasse toe. Bijvoorbeeld, `MainView` is een `View` routetype.

Om de twee functies van de app gescheiden te houden, moet je app de UI's aan twee unieke `View` routes toewijzen: één voor de tabel en één voor het klantenformulier. Maak in `/src/main/java/com/webforj/tutorial/views` twee klassen aan met een `View` suffix:

- **`MainView`**: Deze weergave zal de `Table` bevatten die eerder in de `Application` klasse stond.
- **`FormView`**: Deze weergave zal een formulier hebben voor het toevoegen van nieuwe klanten.

### URLs aan componenten koppelen {#mapping-urls-to-components}

Je app is routeerbaar en weet te zoeken naar twee `View` routes, `MainView` en `FormView`, maar het heeft geen specifieke URL om ze te laden. Gebruikmakend van de `@Route` annotatie op een view-klasse, kun je webforJ vertellen waar deze geladen moet worden op basis van een gegeven URL-segment. Bijvoorbeeld, door `@Route("about")` in een view te gebruiken, koppel je de klasse lokaal aan `http://localhost:8080/about`.

Zoals de naam al aangeeft, is `MainView` de klasse die je aanvankelijk wilt laden wanneer de app draait. Om dit te bereiken, voeg je een `@Route` annotatie toe die `MainView` aan de root-URL van je app koppelt:

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

:::tip Standaard gedrag
Als je expliciet geen waarde toekent aan de `@Route` annotatie, is het URL-segment de naam van de klasse in kleine letters, met de `View` suffix verwijderd.

- `MainView` zou worden gekoppeld aan `/main`
- `FormView` zou worden gekoppeld aan `/form`
:::

## Gedeelde kenmerken {#shared-characteristics}

Naast dat beide view-routes zijn, delen `MainView` en `FormView` aanvullende kenmerken. Sommige van deze gedeelde eigenschappen, zoals het gebruik van `Composite` componenten, zijn fundamenteel voor het gebruik van webforJ-apps, terwijl andere het gewoon gemakkelijker maken om je app te beheren.

### Gebruik makend van `Composite` componenten {#using-composite-components}

Toen de app een enkele pagina was, bewaarde je de componenten binnen een `Frame`. Voortgaand met een app met meerdere weergaven, moet je deze UI-componenten omhullen binnen [`Composite` componenten](/docs/building-ui/composing-components).

`Composite` componenten zijn wrappers die het gemakkelijk maken om herbruikbare componenten te creëren. Om een `Composite` component te creëren, breid je de `Composite` klasse uit met een gespecificeerd gebonden component dat als fundament van de klasse dient, bijvoorbeeld, `Composite<FlexLayout>`.

Deze tutorial gebruikt `Div` elementen als de gebonden componenten, maar ze kunnen elk component zijn, zoals [`FlexLayout`](/docs/components/flex-layout) of [`AppLayout`](/docs/components/app-layout). Met de `getBoundComponent()` methode kun je de gebonden component refereren en heb je toegang tot zijn methoden. Dit stelt je in staat om de grootte in te stellen, een CSS-klasse toe te voegen, componenten toe te voegen die je in de `Composite` component wilt weergeven, en toegang te krijgen tot component-specifieke methoden.

Voor `MainView` en `FormView`, breid `Composite` uit met `Div` als de gebonden component. Referentie dan die gebonden component zodat je later de UI's kunt toevoegen. Beide weergaven zouden er vergelijkbaar uit moeten zien met de volgende structuur:

```java
// Extends Composite with a bound component
public class MainView extends Composite<Div> {

  // Access the bound component
  private Div self = getBoundComponent();

  // Create a component UI
  private Button submit = new Button("Indienen");

  public MainView() {

    // Add the UI component to the bound component
    self.add(submit);
  }
}
```

### De titel van het frame instellen {#setting-the-frame-tile}

Wanneer een gebruiker meerdere tabbladen in zijn browser heeft, helpt een unieke frame-titel hen om snel te identificeren welk deel van de app ze hebben geopend.

De [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotatie definieert wat er verschijnt in de titel van de browser of het tabblad van de pagina. Voor beide weergaven, voeg een frame-titel toe met de `@FrameTitle` annotatie:

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

Met een gebonden component waar je naar kunt verwijzen in `MainView` en `FormView`, kun je het stylen met CSS. Je kunt de CSS gebruiken van de eerste stap, [Een Basisapp maken](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), om beide weergaven identieke UI-container stijlen te geven. Voeg de CSS-klasse naam `card` toe aan de gebonden component in elke view:

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

### Gebruik makend van `CustomerService` {#using-customerservice}

De laatste gedeelde eigenschap voor de weergaven is het gebruik van de `CustomerService` klasse. De `Table` in `MainView` toont elke klant, terwijl `FormView` nieuwe klanten toevoegt. Aangezien beide weergaven interactie hebben met klantgegevens, hebben ze toegang nodig tot de bedrijfslogica van de app.

De weergaven krijgen toegang via de Spring-service die is aangemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Om de Spring-service in elke weergave te gebruiken, maak je `CustomerService` een constructorparameter:

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

Nadat je je app routeerbaar hebt gemaakt, de weergaven `Composite` component wrappers hebt gegeven en de `CustomerService` hebt opgenomen, ben je klaar om de UI's uniek te maken voor elke weergave. Zoals eerder vermeld, bevat `MainView` de UI-componenten die aanvankelijk in `Application` stonden. Deze klasse heeft ook een manier nodig om naar `FormView` te navigeren.

### De `Table` methoden groeperen {#grouping-the-table-methods}

 Terwijl je de componenten van `Application` naar `MainView` verplaatst, is het een goed idee om onderdelen van je app te secties, zodat één aangepaste methode veranderingen aan de `Table` in één keer kan aanbrengen. Het secties nu maakt je code beheersbaarder naarmate de app complexer wordt.

Je `MainView` constructor zou nu alleen één `buildTable()` methode moeten aanroepen die de kolommen toevoegt, de grootte instelt en de repository referentieert:

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

In webforJ kun je rechtstreeks navigeren naar een nieuwe weergave door gebruik te maken van de klasse van de weergave. Routeren via een klasse in plaats van een URL-segment garandeert dat webforJ het juiste pad volgt om de weergave te laden.

Om naar een andere weergave te navigeren, gebruik je de [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) klasse om de huidige locatie op te vragen met `getCurrent()`, en gebruik dan de `navigate()` methode met de klasse van de weergave als parameter:

```java
Router.getCurrent().navigate(FormView.class);
```

Deze code zal gebruikers programmatisch naar het formulier voor nieuwe klanten sturen, maar de navigatie moet met een gebruikersactie verbonden zijn. Om gebruikers in staat te stellen een nieuwe klant toe te voegen, kun je de infoknop uit `Application` aanpassen of vervangen. In plaats van een berichtdialoog te openen, kan de knop navigeren naar de `FormView` klasse:

```java
private Button addCustomer = new Button("Klant Toevoegen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Voltooid `MainView` {#completed-mainview}

Met de navigatie naar `FormView` en gegroepeerde tabelmethoden, zou `MainView` er als volgt uit moeten zien voordat je verder gaat met het creëren van `FormView`:

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

`FormView` toont een formulier voor het toevoegen van nieuwe klanten. Voor elke klant eigenschap zal `FormView` een bewerkbaar component hebben voor gebruikers om mee te interageren. Bovendien zal het een knop hebben voor gebruikers om de gegevens in te dienen en een annuleerknop om deze te negeren.

### Een `Customer` instantie creëren {#creating-a-customer-instance}

Wanneer een gebruiker gegevens voor een nieuwe klant bewerkt, moeten wijzigingen pas op de repository worden toegepast wanneer ze klaar zijn om het formulier in te dienen. Het gebruik van een instantie van het `Customer` object is een handige manier om de nieuwe gegevens te bewerken en te onderhouden zonder de repository direct te bewerken. Maak een nieuwe `Customer` aan binnen `FormView` om voor het formulier te gebruiken:

```java
private Customer customer = new Customer();
```

Om de `Customer` instantie bewerkbaar te maken, moet elke eigenschap, behalve de `id`, worden geassocieerd met een bewerkbaar component. De wijzigingen die een gebruiker maakt in de UI moeten worden weerspiegeld in de `Customer` instantie.

### `TextField` componenten toevoegen {#adding-textfield-components}

De eerste drie bewerkbare eigenschappen in `Customer` (`firstName`, `lastName` en `company`) zijn allemaal `String` waarden en moeten worden weergegeven met een enkele regel teksteditor. [`TextField`](/docs/components/fields/textfield) componenten zijn een geweldige keuze om deze eigenschappen weer te geven.

Met het `TextField` component kun je een label toevoegen en een gebeurtenisluisteraar die afgaat telkens wanneer de waarde van het veld verandert. Elke gebeurtenisluisteraar moet de `Customer` instantie bijwerken voor de overeenkomstige eigenschap.

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
De componenten dezelfde naam geven als de eigenschappen die ze vertegenwoordigen voor de `Customer` entiteit maakt het gemakkelijker om gegevens te binden in een toekomstige stap, [Gegevens Valideren en Binden](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Een `ChoiceBox` component toevoegen {#adding-a-choicebox-component}

Het gebruik van een `TextField` voor de `country` eigenschap zou niet ideaal zijn, omdat de eigenschap slechts één van vijf enumwaarden kan zijn: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` en `USA`.

Een betere component voor het selecteren uit een vooraf gedefinieerde lijst van opties is de [`ChoiceBox`](/docs/components/lists/choicebox).

Elke optie voor een `ChoiceBox` component wordt vertegenwoordigd als een `ListItem`. Elke `ListItem` heeft twee waarden, een `Object` sleutel en een `String` tekst om in de UI weer te geven. Het hebben van twee waarden voor elke optie stelt je in staat om het `Object` intern te behandelen terwijl je tegelijkertijd een betere optie voorstelt voor gebruikers in de UI.

Bijvoorbeeld, de `Object` sleutel kan een International Standard Book Number (ISBN) zijn, terwijl de `String` tekst de titel van het boek is, wat meer menselijk leesbaar is.

```java
new ListItem(isbn, bookTitle);
```

Echter, deze app heeft te maken met een lijst van landnamen, niet met boeken. Voor elke `ListItem` wil je dat de `Object` de `Customer.Country` enum is, terwijl de tekst de `String` representatie ervan kan zijn.

Om alle `country` opties in een `ChoiceBox` toe te voegen, kun je een iterator gebruiken om een `ListItem` voor elke `Customer.Country` enum te maken en deze in een `ArrayList<ListItem>` te plaatsen. Vervolgens kun je die `ArrayList<ListItem>` in een `ChoiceBox` component invoegen:

```java
// Maak de ChoiceBox component aan
private ChoiceBox country = new ChoiceBox("Land");

// Maak een ArrayList van ListItem objecten
ArrayList<ListItem> listCountries = new ArrayList<>();

// Voeg een iterator toe die een ListItem voor elke Customer.Country optie maakt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Voeg de gevulde ArrayList in de ChoiceBox
country.insert(listCountries);

// Zorgt ervoor dat de eerste `ListItem` de standaard is wanneer het formulier wordt geladen
country.selectIndex(0);
```

Dan, wanneer de gebruiker een optie in de `ChoiceBox` selecteert, moet de `Customer` instantie worden bijgewerkt met de sleutel van het geselecteerde item, wat een waarde is van `Customer.Country`.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Om de code schoon te houden, moet de iterator die de `ArrayList<ListItem>` aanmaakt en deze toevoegt aan de `ChoiceBox` in een aparte methode staan. Nadat je een `ChoiceBox` hebt toegevoegd waarmee de gebruiker de `country` eigenschap kan kiezen, zou `FormView` er als volgt uit moeten zien:

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

Wanneer je het formulier voor nieuwe klanten gebruikt, moeten gebruikers in staat zijn om ofwel hun wijzigingen op te slaan of te annuleren. Maak twee `Button` componenten aan om deze functionaliteit te implementeren:

```java
private Button submit = new Button("Indienen");
private Button cancel = new Button("Annuleren");
```

Zowel de indienen- als de annuleerknoppen moeten de gebruiker terug naar `MainView` brengen. Dit stelt de gebruiker in staat om onmiddellijk de resultaten van hun actie te zien, of ze nu een nieuwe klant in de tabel zien of dat deze ongewijzigd blijft. Aangezien meerdere invoeren in `FormView` gebruikers naar `MainView` nemen, moet de navigatie in een aanroepbare methode worden geplaatst:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Annuleerknop**

Het negeren van de wijzigingen in het formulier vereist geen aanvullende code voor de gebeurtenis, behalve dat het terugkeert naar `MainView`. Aangezien annuleren echter niet een primaire actie is, geeft het het thema van de knop een outline, waardoor de indienen-knop meer prominent is. De [Thema's](/docs/components/button#themes) sectie van de `Button` component pagina somt alle beschikbare themas op.

```java
private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Indienen-knop**

Wanneer een gebruiker op de indienen-knop drukt, moeten de waarden in de `Customer` instantie worden gebruikt om een nieuwe entry in de repository te creëren.

Met behulp van de `CustomerService` kun je de `Customer` instantie nemen om de H2-database bij te werken. Wanneer dit gebeurt, wordt er een nieuwe en unieke `id` toegewezen aan die `Customer`. Na het bijwerken van de repository kun je gebruikers omleiden naar `MainView`, waar ze de nieuwe klant in de tabel kunnen zien.

```java
private Button submit = new Button("Indienen", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Gebruik makend van een `ColumnsLayout` {#using-a-columnslayout}

Door de `TextField`, `ChoiceBox`, en `Button` componenten toe te voegen, heb je nu alle interactieve delen van het formulier. De laatste verbetering aan `FormView` in deze stap is om de zes componenten visueel te organiseren.

Dit formulier kan een [`ColumnsLayout`](/docs/components/columns-layout) gebruiken om de componenten in twee kolommen te scheiden zonder de breedte van enige interactieve componenten in te stellen. Om een `ColumnsLayout` te creëren, specificeer je elke component die in de lay-out moet zitten:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Om het aantal kolommen voor een `ColumnsLayout` in te stellen, gebruik je een `List` van `Breakpoint` objecten. Elke `Breakpoint` vertelt de `ColumnsLayout` de minimale breedte die hij moet hebben om een bepaald aantal kolommen toe te passen. Door gebruik te maken van de `ColumnsLayout`, kun je een formulier met twee kolommen maken, maar alleen als het scherm breed genoeg is om twee kolommen weer te geven. Op kleinere schermen worden de componenten in een enkele kolom weergegeven.

De [Breakpoints](/docs/components/columns-layout#breakpoints) sectie in het `ColumnsLayout` artikel legt breakpoints in meer detail uit.

Om de code beheersbaar te houden, stel je de breakpoints in een aparte methode in. In die methode kun je ook de horizontale en verticale ruimte tussen de componenten binnen de `ColumnsLayout` controleren met de `setSpacing()` methode.

```java
private void setColumnsLayout() {

  // Heb twee kolommen in de ColumnsLayout als deze breder is dan 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Voeg de lijst van breakpoints toe
  layout.setBreakpoints(breakpoints);

  // Stel de ruimte tussen componenten in met een DWC CSS variabele
  layout.setSpacing("var(--dwc-space-l)");
}
```

Tot slot, kun je de nieuw gemaakte `ColumnsLayout` aan de gebonden component van `FormView` toevoegen, terwijl je ook de maximale breedte instelt en de klasse naam van eerder toevoegt:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Voltooid `FormView` {#completed-formview}

Na het toevoegen van een `Customer` instantie, de interactieve componenten, en de `ColumnsLayout`, zou je `FormView` er als volgt uit moeten zien:

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

Aangezien gebruikers nu klanten kunnen toevoegen, zou je app in staat moeten zijn om bestaande klanten te bewerken met behulp van hetzelfde formulier. In de volgende stap, [Observers en Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), zul je toestaan dat de klant `id` een initiële parameter voor `FormView` is, zodat deze het formulier kan invullen met de gegevens van die klant en gebruikers in staat kan stellen de eigenschappen te wijzigen.
