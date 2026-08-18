---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: f32a8552d85a9c85b565fe6f026c93bb
---
Tot nu toe was deze tutorial alleen een single-page app. Deze stap verandert dat.
Je verplaatst de UI die je in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data) hebt gemaakt naar zijn eigen pagina en creëert een andere pagina voor het toevoegen van nieuwe klanten.
Vervolgens verbind je deze pagina's, zodat je app in staat is om tussen hen te navigeren door deze concepten toe te passen:

- [Routering](/docs/routing/overview)
- [Componeren van componenten](/docs/building-ui/composing-components)
- De [`ColumnsLayout`](/docs/components/columns-layout) component

Het voltooien van deze stap creëert een versie van [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## De app uitvoeren {#running-the-app}

Tijdens de ontwikkeling van je app kun je [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenste directory die het `pom.xml` bestand bevat; dit is `3-routing-and-composites` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het uitvoeren van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Routerbare apps {#routable-apps}

Voorheen had je app een enkele functie: het weergeven van een tabel met bestaande klantgegevens.
In deze stap zal je app ook in staat zijn om de klantgegevens te wijzigen door nieuwe klanten toe te voegen.
Het scheiden van de UIs voor weergave en wijziging is voordelig voor langdurig onderhoud en testen, dus je voegt deze functie toe als een aparte pagina.
Je zult je app [routerbaar](/docs/routing/overview) maken, zodat webforJ toegang kan krijgen tot en de twee UIs afzonderlijk kan laden.

Een routerbare app geeft de UI weer op basis van de URL. Het annoteren van de klasse die de `App` klasse uitbreidt met [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) stelt routering in staat, en het `packages` element vertelt webforJ welke pakketten UI-componenten bevatten.

Wanneer je de `@Routify` annotatie aan `Application` toevoegt, verwijder je de `run()` methode. Je verplaatst de componenten van die methode naar een klasse die je maakt in het `com.webforj.tutorial.views` pakket. Je bijgewerkte `Application.java` bestand zou er als volgt uit moeten zien:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
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
Het behouden van de `@BundleEntry` annotatie in `Application` voegt het CSS-bestand toe aan de app-niveau frontend bundel, zodat de stijlen beschikbaar blijven over de gerouteerde weergaven.
:::

### Routes aanmaken {#creating-routes}

Het toevoegen van de `@Routify` annotatie maakt je app routerbaar. Zodra het routerbaar is, zal je app in het `com.webforj.tutorial.views` pakket zoeken naar routes.
Je moet de routes voor je UIs aanmaken en ook hun [Route Types](/docs/routing/route-hierarchy/route-types) specificeren. Het routetype bepaalt hoe de UI-inhoud aan de URL wordt gekoppeld.

Het eerste routetype is `View`. Dit soort routes map direct naar een specifiek URL-segment in je app. De UIs voor de tabel en het formulier voor nieuwe klanten zullen beide `View` routes zijn.

Het tweede routetype is `Layout`, dat UI bevat die op meerdere pagina's verschijnt, zoals een header of zijbalk. Layout-routes wikkelen ook kindweergaven zonder bij te dragen aan de URL.

Om het routetype van een klasse op te geven, voeg je het routetype als achtervoegsel aan de naam van de klasse toe.
Bijvoorbeeld, `MainView` is een `View` routetype.

Om de twee functies van de app gescheiden te houden, moet je de UIs koppelen aan twee unieke `View` routes: één voor de tabel en één voor het klantenformulier. Maak in `/src/main/java/com/webforj/tutorial/views` twee klassen aan met een `View` achtervoegsel:

- **`MainView`**: Deze weergave zal de `Table` bevatten die eerder in de `Application` klasse stond.
- **`FormView`**: Deze weergave zal een formulier bevatten voor het toevoegen van nieuwe klanten.

### URL's aan componenten koppelen {#mapping-urls-to-components}

Je app is routerbaar en weet om naar twee `View` routes te kijken, `MainView` en `FormView`, maar het heeft geen specifieke URL om ze bij te laden. Door de `@Route` annotatie op een weergaveklasse te gebruiken, kun je webforJ vertellen waar deze geladen moet worden op basis van een gegeven URL-segment. Bijvoorbeeld, het gebruik van `@Route("about")` in een weergave maakt lokaal de klasse aan als `http://localhost:8080/about`.

Zoals de naam al aangeeft, is `MainView` de klasse die je wilt laten laden wanneer de app wordt uitgevoerd. Om dit te bereiken, voeg je een `@Route` annotatie toe die `MainView` aan de root-URL van je app koppelt:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Voor de `FormView`, koppel de weergave zodat deze laadt wanneer een gebruiker naar `http://localhost:8080/customer` gaat:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standaardgedrag
Als je niet expliciet een waarde toekent aan de `@Route` annotatie, is het URL-segment de klassenaam omgezet naar kleine letters, met het `View` achtervoegsel verwijderd.

- `MainView` zou worden gemapt naar `/main`
- `FormView` zou worden gemapt naar `/form`
:::

## Gedeelde kenmerken {#shared-characteristics}

Behalve dat ze beide view-routes zijn, delen `MainView` en `FormView` aanvullende kenmerken. Sommige van deze gedeelde eigenschappen, zoals het gebruik van `Composite` componenten, zijn fundamenteel voor het gebruik van webforJ-apps, terwijl andere het gemakkelijker maken om je app te beheren.

### Het gebruik van `Composite` componenten {#using-composite-components}

Toen de app een enkele pagina had, bewaarde je de componenten binnen een `Frame`. Voortaan, met een app met meerdere weergaven, moet je die UI-componenten wikkelen binnen [`Composite` componenten](/docs/building-ui/composing-components).

`Composite` componenten zijn wrappers die het eenvoudig maken om herbruikbare componenten te creëren.
Om een `Composite` component te maken, breid je de `Composite` klasse uit met een gespecificeerd gebonden component dat als de basis van de klasse dient, bijvoorbeeld, `Composite<FlexLayout>`.

Deze tutorial gebruikt `Div` elementen als de gebonden componenten, maar ze kunnen elk component zijn, zoals [`FlexLayout`](/docs/components/flex-layout) of [`AppLayout`](/docs/components/app-layout). Met behulp van de `getBoundComponent()` methode, kun je de gebonden component verwijzen en toegang krijgen tot de methoden ervan. Dit stelt je in staat om de grootte in te stellen, een CSS-klasse naam toe te voegen, componenten toe te voegen die je wilt weergeven in de `Composite` component, en toegang te krijgen tot component-specifieke methoden.

Voor `MainView` en `FormView`, breid `Composite` uit met `Div` als de gebonden component. Verwijs vervolgens naar die gebonden component, zodat je later de UIs kunt toevoegen. Beide weergaven zouden er vergelijkbaar uit moeten zien met de volgende structuur:

```java
// Breid Composite uit met een gebonden component
public class MainView extends Composite<Div> {

  // Verkrijg toegang tot de gebonden component
  private Div self = getBoundComponent();

  // Maak een UI component
  private Button submit = new Button("Indienen");

  public MainView() {

    // Voeg het UI component toe aan de gebonden component
    self.add(submit);
  }
}
```

### De titel van het frame instellen {#setting-the-frame-tile}

Wanneer een gebruiker meerdere tabbladen in zijn browser heeft, helpt een unieke frame-titel hen snel te identificeren welk deel van de app ze hebben geopend.

De [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) annotatie definieert wat er verschijnt in de titel van de browser of het tabblad van de pagina. Voor beide weergaven, voeg een frame-titel toe met behulp van de `@FrameTitle` annotatie:

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

Met een gebonden component die je kunt refereren in `MainView` en `FormView`, kun je deze stylen met CSS.
Je kunt de CSS gebruiken van de eerste stap, [Een Basisapp maken](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), om beide weergaven identieke UI-containerstijlen te geven.
Voeg de CSS-klassenaam `card` toe aan de gebonden component in elke weergave:

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

Het laatste gedeelde kenmerk voor de weergaven is het gebruik van de `CustomerService` klasse.
De `Table` in `MainView` geeft elke klant weer, terwijl `FormView` nieuwe klanten toevoegt. Aangezien beide weergaven interactie hebben met klantgegevens, moeten ze toegang hebben tot de bedrijfslogica van de app.

De weergaven krijgen toegang via de Spring-service die is gemaakt in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Om de Spring-service in elke weergave te gebruiken, maak je `CustomerService` een constructorparameter:

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

## `MainView` aanmaken {#creating-mainview}

Nadat je je app routerbaar hebt gemaakt, de weergaven `Composite` component wrappers hebt gegeven en de `CustomerService` hebt opgenomen, ben je klaar om de UIs uniek voor elke weergave te bouwen. Zoals eerder vermeld, bevat `MainView` de UI-componenten die oorspronkelijk in `Application` stonden. Deze klasse heeft ook een manier nodig om naar `FormView` te navigeren.

### Groeperen van de `Table` methoden {#grouping-the-table-methods}

Terwijl je de componenten van `Application` naar `MainView` verplaatst, is het een goed idee om te beginnen met het segmenteren van delen van je app, zodat één aangepaste methode wijzigingen aan de `Table` in één keer kan aanbrengen. Het segmenteren van je code maakt het nu beheersbaarder naarmate de app complexer wordt.

Nu zou de constructor van `MainView` slechts één `buildTable()` methode moeten aanroepen die de kolommen toevoegt, de afmetingen instelt en de repository verwijst:

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

### Navigeren naar `FormView` {#navigating-to-formview}

Gebruikers hebben een manier nodig om van `MainView` naar `FormView` te navigeren met behulp van de UI.

In webforJ kun je direct naar een nieuwe weergave navigeren door gebruik te maken van de klasse van de weergave. Routeren via een klasse in plaats van een URL-segment garandeert dat webforJ het juiste pad zal nemen om de weergave te laden.

Om naar een andere weergave te navigeren, gebruik je de [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) klasse om de huidige locatie te krijgen met `getCurrent()`, en gebruik vervolgens de `navigate()` methode met de klasse van de weergave als parameter:

```java
Router.getCurrent().navigate(FormView.class);
```

Deze code zal gebruikers programmatisch naar het formulier voor nieuwe klanten sturen, maar de navigatie moet aan een gebruikersactie worden gekoppeld.
Om gebruikers in staat te stellen een nieuwe klant toe te voegen, kun je de informatieknop van `Application` aanpassen of vervangen. In plaats van een berichtendialoog te openen, kan de knop navigeren naar de `FormView` klasse:

```java
private Button addCustomer = new Button("Klant toevoegen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Voltooid `MainView` {#completed-mainview}

Met de navigatie naar `FormView` en gegroepeerde tabelmethoden, zou `MainView` er als volgt uit moeten zien voordat je verder gaat met het maken van `FormView`:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Klantentabel")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Klant toevoegen", ButtonTheme.PRIMARY,
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

## `FormView` aanmaken {#creating-formview}

`FormView` zal een formulier weergeven om nieuwe klanten toe te voegen. Voor elke eigenschap van de klant zal `FormView` een bewerkbare component hebben voor gebruikers om mee te interageren. Daarnaast zal het een knop hebben voor gebruikers om de gegevens in te dienen en een annuleerknop om deze te verwerpen.

### Een `Customer` instantie aanmaken {#creating-a-customer-instance}

Wanneer een gebruiker gegevens voor een nieuwe klant aanpast, moeten wijzigingen pas op de repository worden toegepast wanneer ze klaar zijn om het formulier in te dienen. Het gebruik van een instantie van het `Customer` object is een handige manier om de nieuwe gegevens te bewerken en te behouden zonder de repository rechtstreeks te bewerken. Maak een nieuwe `Customer` aan binnen `FormView` om te gebruiken voor het formulier:

```java
private Customer customer = new Customer();
```

Om de `Customer` instantie bewerkbaar te maken, moet elke eigenschap, behalve de `id`, worden geassocieerd met een bewerkbare component. De wijzigingen die een gebruiker in de UI aanbrengt, moeten worden weerspiegeld in de `Customer` instantie.

### `TextField` componenten toevoegen {#adding-textfield-components}

De eerste drie bewerkbare eigenschappen in `Customer` (`firstName`, `lastName` en `company`) zijn allemaal `String` waarden en moeten worden weergegeven met een enkele regel teksteditor. [`TextField`](/docs/components/fields/textfield) componenten zijn een uitstekende keuze om deze eigenschappen weer te geven.

Met de `TextField` component kun je een label en een gebeurtenislistener toevoegen die wordt geactiveerd telkens wanneer de veldwaarde verandert. Elke gebeurtenislistener moet de `Customer` instantie bijwerken voor de overeenkomstige eigenschap.

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

:::tip Gedeelde naamgevingconventie
Het benoemen van de componenten hetzelfde als de eigenschappen die ze vertegenwoordigen voor de `Customer` entiteit maakt het gemakkelijker om gegevens in een toekomstige stap te binden, [Valideren en Data Binden](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Een `ChoiceBox` component toevoegen {#adding-a-choicebox-component}

Het gebruik van een `TextField` voor de eigenschap `country` zou niet ideaal zijn, omdat de eigenschap slechts een van vijf enumwaarden kan zijn: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` en `USA`.

Een betere component om uit een vooraf gedefinieerde lijst van opties te selecteren is de [`ChoiceBox`](/docs/components/lists/choicebox).

Elke optie voor een `ChoiceBox` component wordt weergegeven als een `ListItem`. Elke `ListItem` heeft twee waarden, een `Object` sleutel en een `String` tekst om in de UI weer te geven. Het hebben van twee waarden voor elke optie stelt je in staat om de `Object` intern te verwerken terwijl je tegelijkertijd een meer leesbare optie voor gebruikers presenteert in de UI.

Bijvoorbeeld, de `Object` sleutel zou een International Standard Book Number (ISBN) kunnen zijn, terwijl de `String` tekst de titel van het boek is, wat meer menselijk leesbaar is.

```java
new ListItem(isbn, bookTitle);
```

Echter, deze app heeft te maken met een lijst van landsnamen, geen boeken. Voor elke `ListItem`, wil je dat de `Object` de `Customer.Country` enum is, terwijl de tekst de `String` representatie kan zijn.

Om alle `country` opties toe te voegen aan een `ChoiceBox`, kun je een iterator gebruiken om een `ListItem` voor elke `Customer.Country` enum te maken en deze in een `ArrayList<ListItem>` te plaatsen. Vervolgens kun je die `ArrayList<ListItem>` in een `ChoiceBox` component invoegen:

```java
// Maak de ChoiceBox component aan
private ChoiceBox country = new ChoiceBox("Land");

// Maak een ArrayList van ListItem objecten
ArrayList<ListItem> listCountries = new ArrayList<>();

// Voeg een iterator toe die een ListItem voor elke Customer.Country optie creëert
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Voeg de ingevulde ArrayList toe aan de ChoiceBox
country.insert(listCountries);

// Maakt de eerste `ListItem` de standaard wanneer het formulier laadt
country.selectIndex(0);
```

Wanneer de gebruiker een optie selecteert in de `ChoiceBox`, moet de `Customer` instantie worden bijgewerkt met de sleutel van het geselecteerde item, wat een `Customer.Country` waarde is.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Om de code schoon te houden, moet de iterator die de `ArrayList<ListItem>` aanmaakt en deze aan de `ChoiceBox` toevoegt in een aparte methode staan.
Nadat je een `ChoiceBox` hebt toegevoegd die de gebruiker toestaat om de `country` eigenschap te kiezen, zou `FormView` er als volgt uit moeten zien:

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

Bij het gebruik van het nieuwe klantenformulier moeten gebruikers in staat zijn om hun wijzigingen op te slaan of te verwerpen.
Maak twee `Button` componenten om deze functie te implementeren:

```java
private Button submit = new Button("Indienen");
private Button cancel = new Button("Annuleren");
```

Zowel de indienen- als de annuleerknoppen moeten de gebruiker terugbrengen naar `MainView`.
Dit stelt de gebruiker in staat om onmiddellijk de resultaten van hun actie te zien, of ze nu een nieuwe klant in de tabel zien of deze onveranderd blijft.
Aangezien meerdere invoeren in `FormView` gebruikers naar `MainView` brengen, moet de navigatie in een aanroepbare methode worden geplaatst:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Annuleerknop**

Het verwerpen van de wijzigingen in het formulier vereist geen aanvullende code voor de gebeurtenis, behalve het terugkeren naar `MainView`. Echter, aangezien annuleren geen primaire actie is, geeft het thema van de knop met een omtreklijn de indienenknop meer prominente aandacht.
De [Thema's](/docs/components/button#themes) sectie van de `Button` componentpagina geeft een lijst van alle beschikbare thema's.

```java
private Button cancel = new Button("Annuleren", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Indienenknop**

Wanneer een gebruiker op de indienenknop drukt, moeten de waarden in de `Customer` instantie worden gebruikt om een nieuwe vermelding in de repository te maken.

Met behulp van de `CustomerService`, kun je de `Customer` instantie gebruiken om de H2-database bij te werken. Wanneer dit gebeurt, wordt een nieuwe en unieke `id` toegewezen aan die `Customer`. Na het bijwerken van de repository kun je gebruikers omleiden naar `MainView`, waar ze de nieuwe klant in de tabel kunnen zien.

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

Met het toevoegen van de `TextField`, `ChoiceBox` en `Button` componenten, heb je nu alle interactieve delen van het formulier. De laatste verbetering aan `FormView` in deze stap is om de zes componenten visueel te organiseren.

Dit formulier kan een [`ColumnsLayout`](/docs/components/columns-layout) gebruiken om de componenten in twee kolommen te scheiden zonder de breedte van enige interactieve componenten in te stellen.
Om een `ColumnsLayout` te creëren, specificeer je elke component die binnen de lay-out moet staan:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Om het aantal kolommen voor een `ColumnsLayout` in te stellen, gebruik je een `List` van `Breakpoint` objecten. Elke `Breakpoint` vertelt de `ColumnsLayout` de minimale breedte die het moet hebben om een opgegeven aantal kolommen toe te passen. Door gebruik te maken van de `ColumnsLayout`, kun je een formulier met twee kolommen maken, maar alleen als het scherm breed genoeg is om twee kolommen weer te geven. Op kleinere schermen worden de componenten in een enkele kolom weergegeven.

De [Breakpoints](/docs/components/columns-layout#breakpoints) sectie in het `ColumnsLayout` artikel legt breakpoints in meer detail uit.

Om de code beheersbaar te houden, stel je de breakpoints in een aparte methode in. In die methode kun je ook de horizontale en verticale ruimte tussen de componenten binnen de `ColumnsLayout` beheersen met de `setSpacing()` methode.

```java
private void setColumnsLayout() {

  // Heb twee kolommen in de ColumnsLayout als het breder is dan 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Voeg de lijst van breakpoints toe
  layout.setBreakpoints(breakpoints);

  // Stel de ruimte tussen componenten in met een DWC CSS variabele
  layout.setSpacing("var(--dwc-space-l)")
}
```

Tot slot kun je de nieuw aangemaakte `ColumnsLayout` toevoegen aan de gebonden component van `FormView`, terwijl je ook de maximale breedte instelt en de klassenaam van eerder toevoegt:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Voltooid `FormView` {#completed-formview}

Na het toevoegen van een `Customer` instantie, de interactieve componenten, en de `ColumnsLayout`, zou je `FormView` er als volgt uit moeten zien:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
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
```

</ExpandableCode>
<!-- vale on -->

## Volgende stap {#next-step}

Aangezien gebruikers nu klanten kunnen toevoegen, moet je app in staat zijn om bestaande klanten te bewerken met hetzelfde formulier. In de volgende stap, [Observers en Routerparameters](/docs/introduction/tutorial/observers-and-route-parameters), zul je de klant `id` toelaten als een initiële parameter voor `FormView`, zodat het formulier kan worden ingevuld met de gegevens van die klant en gebruikers in staat kan stellen de eigenschappen te wijzigen.
