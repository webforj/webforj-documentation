---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: e56d98e67ff6ee74a4dc1ee81346350d
---
In deze stap haal je alle onderdelen van je app samen in een samenhangende app-indeling. Aan het einde van deze stap zal de structuur van je app nauw aansluiten bij de [SideMenu archetype](/docs/building-ui/archetypes/sidemenu), en krijg je een beter begrip van hoe de volgende componenten en concepten werken:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) gebruiken als vergelijking. Om de app in actie te zien:

1. Navigeer naar de bovenliggende directory die het `pom.xml`-bestand bevat; dit is `6-integrating-an-app-layout` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Een herbruikbare component maken {#creating-a-reusable-component}

In een vorige stap, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), heb je twee samengestelde componenten gemaakt die de inhoud van de klantentabel en het klantenformulier bevatten. Als onderdeel van deze stap maak je een kleinere, herbruikbare samengestelde component om de naam van de app in het zijmenu en een over ons-pagina weer te geven. Als je in de toekomst de naam van de app wilt wijzigen, hoef je deze alleen in deze component bij te werken.

In `src/main/java/com/webforj/tutorial/components`, maak een klasse genaamd `AppTitle`. De gebonden component voor `AppTitle` is een `FlexLayout`, een containercomponent die door deze stap heen wordt gebruikt om je te laten zien hoe je complexere lay-outs maakt. Voor deze `FlexLayout` arrangeer je de richting van de items en de ruimte tussen items. Dit wordt gedaan met de methodes `setDirection()` en `setSpacing()`.

```java title='AppTitle.java'
// Maak de gebonden component een FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Arrangeer de items verticaal
    self.setDirection(FlexDirection.COLUMN);

    // Stel de ruimte tussen items in
    self.setSpacing("0px");
  }
}
```

Gebruik vervolgens standaard HTML-elementen om de titel en subtitel te creëren. De ondermarge van een koptekstelement instellen op `0px` brengt de elementen dichterbij elkaar, en je kunt de subtitel stylen met behulp van [DWC CSS-variabelen](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Klantbeheer");
  private Paragraph subTitle = new Paragraph("Een eenvoudig registratiesysteem");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Optionele weergave {#optional-rendering}

Hoewel `AppTitle` eenvoudig is, laat het toevoegen van een boolean argument aan de constructor toe om te bepalen wanneer bepaalde delen van de component, zoals de subtitel, worden weergegeven.

```java title='AppTitle.java'
// Voeg een boolean argument toe
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Voeg de titel standaard toe
      .add(title);

  // Toon optioneel de subtitel
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Voltooide `AppTitle` {#completed-app-title}

Samen moet de herbruikbare component er als volgt uitzien:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Klantbeheer");
  private Paragraph subTitle = new Paragraph("Een eenvoudig registratiesysteem");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Een over ons-pagina maken {#creating-an-about-page}

De eerste plaats om de nieuw gemaakte `AppTitle`-component toe te voegen, is een over ons-pagina. Deze pagina bevat een afbeelding en de `AppTitle`-component, gecentreerd op de pagina met behulp van een andere `FlexLayout`.

### Inhoud centreren met een `FlexLayout` {#centering-content-using-a-flexlayout}

Het doel is de inhoud van de over ons-pagina te centreren met behulp van de `FlexLayout`. De `FlexLayout`-component volgt het [CSS flexbox-indelingsmodel](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden voor de `FlexLayout`, zoals de eerder gebruikte methodes om de items in een kolom te oriënteren, zijn verschillende manieren om de items te rangschikken.

De methoden voor het rangschikken van items in een `FlexLayout` gebruiken een relatieve directionele indeling. In plaats van na te denken over de horizontale en verticale assen, is het beter om de as die parallel aan de items loopt als de hoofd as te beschouwen, en de as die haaks op de items staat als de kruis as.

Door zowel de eigenschappen `FlexJustifyContent` als `FlexAlignment` in te stellen op `CENTER`, worden de items langs zowel de hoofd- als kruisassen in de `FlexLayout` gecentreerd, en door de `FlexLayout` de hele ruimte van zijn bovenliggende container te laten innemen, wordt deze gecentreerd op de pagina.

```java
private final FlexLayout layout = new FlexLayout();

// Vul de volledige ruimte van het bovenliggende element
layout.setSize("100%", "100%");

// Maak de hoofd as verticaal
layout.setDirection(FlexDirection.COLUMN);

// Centreer de items langs de kruis as
layout.setAlignment(FlexAlignment.CENTER);

// Centreer de items langs de hoofd as
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Om te helpen visualiseren hoe de verschillende methoden werken, kijk naar het blogbericht [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Bronnen toevoegen {#adding-resources}

Een van de items die in de gecentreerde `FlexLayout` komen, is een afbeelding. Voor deze tutorial kun je de [afbeelding van de over ons-pagina](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) op GitHub bekijken en downloaden. Zodra deze is gedownload, voeg je deze toe aan de statische map van je project in `src/main/resources/static/images` en noem je deze `Files.svg`.

Deze afbeelding in de statische map plaatsen stelt je in staat om deze te verwijzen met behulp van het Webserverprotocol, zoals je deed bij het verwijzen naar het CSS-bestand in de eerste stap, [Een basisapp maken](/docs/introduction/tutorial/creating-a-basic-app). Vervolgens kun je deze binnen je app gebruiken als een HTML-element, zoals:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView` maken {#creating-about-view}

Net als de twee bestaande app-pagina's, zal de over ons-pagina een routable view zijn. In `src/main/java/com/webforj/tutorial/views`, voeg een klasse genaamd `AboutView` toe. Gebruik een `FlexLayout` voor de gebonden component, zoals je deed voor `AppTitle`.

Aangezien je de klasse `AboutView` hebt genoemd, is er geen behoefte om een aangepaste waarde voor de URL-mapping op te geven; deze pagina wordt standaard weergegeven op `http://localhost:8080/about`.

Hier is hoe het eruit ziet wanneer je de concepten uit de vorige stappen met de nieuw gemaakte componenten gebruikt om een nieuwe weergave met gecentreerde inhoud te creëren:

```java title='AboutView.java'
@Route()
@FrameTitle("Over")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## De `Layout`-route maken {#creating-the-layout-route}

Het werd kort vermeld in de stap [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), maar er zijn twee [route-types](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` en `AboutView` zijn allemaal `View`-routes, terwijl het route-type dat je gebruikt om het zijmenu van de app te creëren een `Layout`-route is.

Layout-routes wikkelen kindweergaven en stellen bepaalde UI-onderdelen in staat om over weergaven heen persistent te zijn, zoals een zijmenu. In `src/main/java/com/webforj/tutorial/layouts`, maak een klasse genaamd `MainLayout`.

### Route-uitlaten {#route-outlets}

Net als de view-routes, heeft `MainLayout` een `@Route`-annotatie nodig. Omdat het echter `Layout` als suffix heeft en layout-routes niet bijdragen aan de URL, hoeft deze annotatie geen argumenten te hebben.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

De app weet welke weergaven in `MainLayout` moeten worden weergegeven door de lay-outklasse als de [route-uitlaat](/docs/routing/route-hierarchy/route-outlets) in elke weergave te verklaren. De vorige stappen hebben alleen een `value`-eigenschap ingesteld in de `@Route`-annotaties, dus nu moet je expliciet aangeven wat de `value`- en `outlet`-eigenschappen zijn voor de weergaveklassen.

<!-- vale Google.Quotes = NO -->
<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>
<!-- vale Google.Quotes = YES -->

:::note Laatste details
Dit is de laatste wijziging die vereist is voor `FormView` en `AboutView` in deze stap, dus vergeet niet om de `@Route`-annotatie voor die weergaven bij te werken voordat je je app uitvoert.
:::

## De `AppLayout`-component gebruiken {#using-the-app-layout-component}

Nu je app de weergaven binnen `MainLayout` weergeeft, kun je kiezen waar die componenten worden weergegeven. Het kiezen van de `AppLayout` als de gebonden component voor `MainLayout` stelt je in staat om de weergaven standaard in een hoofdinhoudsgedeelte op te slaan, terwijl je ook verschillende gebieden hebt om items voor de koptekst en zijmenu toe te voegen.

### Slots {#slots}

Voor veel webforJ-containers voegt het gebruik van de `add()`-methodes UI-componenten toe aan de hoofdinhoudsruimte. In de `AppLayout`-component zijn er meerdere gebieden voor het toevoegen van UI-componenten, elk in een aparte slot. Door `MainLayout` als een layout-route te markeren en de gebonden component als een `AppLayout` in te stellen, worden weergaven automatisch weergegeven in de hoofdinhoudslot.

In deze stap gebruik je de `drawer-title` en `drawer` slots om een zijmenu te creëren, en de `header` slot om weer te geven op welke pagina de gebruiker zich bevindt en een schakelaar voor het zijmenu.

### Een zijmenu maken {#making-a-side-menu}

Wanneer er voldoende schermruimte op het apparaat is, toont de `AppLayout`-component een lade. Hier voeg je de `AppTitle` opnieuw toe en items die gebruikers in staat stellen om te navigeren in de app.

Standaard toont `AppLayout` geen ladekoptekst, maar met de methode `setDrawerHeaderVisible()` kun je items weergeven die zich binnen de `drawer-title`-slot bevinden, welke de `AppTitle` met de subtitel is.

```java
private AppLayout appLayout = new AppLayout();

// Toon de ladekoptekst
appLayout.setDrawerHeaderVisible(true);

// Voeg de AppTitle toe aan de ladekoptekst met de subtitel
appLayout.addToDrawerTitle(new AppTitle(true));
```

De `drawer`-slot zou vervolgens de componenten moeten bevatten die gebruikers in staat stellen om in de app te navigeren. Het gebruik van de [`AppNav`](/docs/components/appnav) component maakt het eenvoudig om nieuwe navigatieopties te creëren. Voor elke link hoef je alleen maar een `AppNavItem` te maken. De `AppNavItem`-componenten in deze tutorial gebruiken drie parameters:

- Het label voor de link
- De doelweergave
- Een optionele [`Icon`](/docs/components/icon) component, met afbeeldingen van [Tabler](https://tabler.io/icons)

Het groeperen van alle lade-instellingen in `MainLayout` ziet er als volgt uit:

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Over", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Een koptekst maken {#making-a-header}

De `header`-slot moet twee items bevatten: een schakelaar om het zijmenu te tonen of te verbergen en een manier om de kader titel weer te geven. Beide items zullen binnen een [Toolbar](/docs/components/toolbar) component worden georganiseerd.

Je kunt de schakelaar voor de `AppLayout`-lade opnemen met de `AppDrawerToggle`-component. Deze component is al gestyled met een veelgebruikt pictogram voor verborgen menu-opties en richt zich op de lade om deze te openen en te sluiten.

```java
// Maak de containercomponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Voeg de Toolbar toe aan de koptekst van de AppLayout
appLayout.addToHeader(toolbar);

// Voeg de AppDrawerToggle toe aan de toolbar
toolbar.addToStart(new AppDrawerToggle());
```

De koptekst kan ook de frame titel weergeven door het navigatie-evenement te gebruiken om details over de binnenkomende component te verkrijgen, terwijl je een event listener hebt om de registratie te verwijderen om geheugenlekken te voorkomen.

```java
// Maak het H1-element en de navigatieregistratie
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Registreer de gebeurtenis tijdens navigeren
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Verwijder luisteraars voordat MainLayout wordt vernietigd
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Verkrijg de frame titel van de binnenkomende viewklasse
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Voltooide `MainLayout` {#completed-mainlayout}

Hier is `MainLayout` met gemaakte inhoud voor de lade en de koptekst binnen een `AppLayout`:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
  public class MainLayout extends Composite<AppLayout> {
    private AppLayout self = getBoundComponent();
    private H1 title = new H1("");
    private ListenerRegistration<NavigateEvent> navigateRegistration;
    private Toolbar toolbar = new Toolbar();
    private AppNav appNav = new AppNav();

    public MainLayout() {
      setHeader();
      setDrawer();
      navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
    }

    private void setHeader() {
      self.addToHeader(toolbar);

      toolbar.addToStart(new AppDrawerToggle());
      toolbar.addToTitle(title);
    }

    private void setDrawer() {
      self.setDrawerHeaderVisible(true)
          .addToDrawerTitle(new AppTitle(true));

      appNav.addItem(new AppNavItem("Dashboard", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("Over", AboutView.class,
          TablerIcon.create("info-circle")));
      self.addToDrawer(appNav);
    }

    @Override
    protected void onDidDestroy() {
      if (navigateRegistration != null) {
        navigateRegistration.remove();
      }
    }

    private void onNavigate(NavigateEvent ev) {
      Component component = ev.getContext().getComponent();
      if (component != null) {
        FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
        title.setText(frameTitle != null ? frameTitle.value() : "");
      }
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## `FormView` bijwerken {#updating-form-view}

Zoals eerder vermeld, was de enige wijziging aan `FormView` de `@Route`-annotatie.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView` bijwerken {#updating-main-view}

Voor `MainView` verander je de gebonden component van een `Div` naar een `FlexLayout`. Dit stelt je in staat om de tabel te centreren, terwijl je ook specifieke componenten binnen de lay-out verplaatst. Met de methode `setItemAlignment()` kun je een component in de lay-out kiezen en verplaatsen, zodat je de tabel gecentreerd kunt houden terwijl je de knop klant toevoegen aan de rechterbovenhoek van de lay-out verankert.

```java
// Verander de gebonden component in een FlexLayout
private FlexLayout self = getBoundComponent();

// Aligneer de knop aan het einde van de kruis-as
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Een andere verbetering die je hier kunt maken, is de breedte van de tabel. In plaats van een vaste breedte kun je deze instellen om overeen te komen met zijn bovenliggende container, de `FlexLayout`. Dan kan die `FlexLayout` een maximale breedte hebben, zodat deze niet overrekt op grotere schermen.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Door deze samen te voegen en een andere methode te maken om de `FlexLayout` gecentreerd te krijgen zoals de vorige, ziet `MainView` eruit met de gemarkeerde veranderingen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Klantentabel")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Klant toevoegen", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      // highlight-next-line
      setFlexLayout();
      // highlight-next-line
      self.add(addCustomer, table);
      // highlight-next-line
      self.setItemAlignment(FlexAlignment.END, addCustomer);
    }

    private void buildTable() {
      // highlight-next-line
      table.setSize("100%", "294px");
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

    // highlight-next-line
    private void setFlexLayout() {
      // highlight-next-line
      self.setSize("100%", "100%")
          // highlight-next-line
          .setMargin("auto")
          // highlight-next-line
          .setMaxWidth(2000)
          // highlight-next-line
          .setDirection(FlexDirection.COLUMN)
          // highlight-next-line
          .setAlignment(FlexAlignment.CENTER);
          // highlight-next-line
    }

    private void editCustomer(TableItemClickEvent<Customer> e) {
      Router.getCurrent().navigate(FormView.class,
          ParametersBag.of("id=" + e.getItemKey()));
    }
  }
`}
</ExpandableCode>
<!-- vale on -->
