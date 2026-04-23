---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
sidebar_class_name: new-content
_i18n_hash: 66c364d83c3b6d574acaca5156bbb018
---
In deze stap haal je alle onderdelen van je app samen in een samenhangende app-indeling. Tegen het einde van deze stap zal de structuur van je app sterk lijken op het [SideMenu-archetype](/docs/building-ui/archetypes/sidemenu), en je zult een beter begrip krijgen van hoe de volgende componenten en concepten werken:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de topdirectory met het `pom.xml`-bestand, dit is `6-integrating-an-app-layout` als je de versie van GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Het uitvoeren van de app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Een herbruikbare component maken {#creating-a-reusable-component}

In een vorige stap, [Routing en Composities](/docs/introduction/tutorial/routing-and-composites), heb je twee samengestelde componenten gemaakt die de inhoud van de klantentabel en het klantenformulier bevatten. Als onderdeel van deze stap maak je een kleinere, herbruikbare samengestelde component om de naam van de app in het zijmenu en een informatiepagina weer te geven. Als je in de toekomst besluit om de naam van de app te veranderen, hoef je deze alleen in deze component bij te werken.

In `src/main/java/com/webforj/tutorial/components`, maak een klasse genaamd `AppTitle`. De gebonden component voor `AppTitle` zal een `FlexLayout` zijn, een containercomponent die gedurende deze stap wordt gebruikt om je te laten zien hoe je complexere indelingen kunt maken. Voor deze `FlexLayout` rangschik je de richting van de items en de ruimte tussen de items. Dat gebeurt door respectievelijk de methoden `setDirection()` en `setSpacing()` te gebruiken.

```java title='AppTitle.java'
// Maak de gebonden component een FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  
  public AppTitle() {

    // Rangschik de items verticaal
    self.setDirection(FlexDirection.COLUMN);

    // Stel de ruimte tussen items in
    self.setSpacing("0px");
  }
}
```

Vervolgens gebruik je standaard HTML-elementen om de titel en subtitel te maken. Het instellen van de ondermarge van een koptekstobject op `0px` brengt de elementen dichter bij elkaar, en je kunt de subtitel stijlen met behulp van [DWC CSS-variabelen](/docs/styling/css-variables).

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

Ook al is `AppTitle` eenvoudig, het toevoegen van een boolean-argument aan de constructor stelt je in staat om te regelen wanneer bepaalde onderdelen van de component, zoals de subtitel, moeten worden weergegeven.

```java title='AppTitle.java'
// Voeg een boolean-argument toe
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Voeg de titel standaard toe
      .add(title);
  
  // Weergeven van de subtitel indien gewenst
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Voltooid `AppTitle` {#completed-app-title}

Alles samen zou de herbruikbare component er als volgt uit moeten zien:

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

## Een informatiepagina maken {#creating-an-about-page}

De eerste plaats om de nieuw gemaakte `AppTitle`-component toe te voegen, is een informatiepagina. Deze pagina bevat een afbeelding en de `AppTitle`-component, gecentreerd op de pagina door gebruik te maken van een andere `FlexLayout`.

### Inhoud centreren met behulp van een `FlexLayout` {#centering-content-using-a-flexlayout}

Het doel is om de inhoud van de informatiepagina te centreren met behulp van de `FlexLayout`. De `FlexLayout`-component volgt het [CSS flexbox-layoutmodel](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden voor de `FlexLayout`, zoals de eerder gebruikte om de items in een kolom te oriënteren, zijn verschillende manieren om de items te rangschikken.

De methoden voor het rangschikken van items in een `FlexLayout` gebruiken een relatief richting systeem. In plaats van te denken in horizontale en verticale assen, is het beter om de as parallel aan de items als de hoofd-as te beschouwen, en de as die perpendicular is aan de items als de kruisas.

Door zowel de `FlexJustifyContent`- als de `FlexAlignment`-eigenschappen in te stellen op `CENTER` worden de items gecentreerd langs zowel de hoofd- als de kruisas in de `FlexLayout`, en door de `FlexLayout` het volledige oudercontainer te laten innemen, wordt het gecentreerd op de pagina. 

```java
private final FlexLayout layout = new FlexLayout();

// Vul de gehele ruimte van het ouder element
layout.setSize("100%", "100%");

// Maak de hoofd-as verticaal
layout.setDirection(FlexDirection.COLUMN);

// Centreer de items langs de kruisas
layout.setAlignment(FlexAlignment.CENTER);

// Centreer de items langs de hoofdas
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Om te helpen visualiseren hoe de verschillende methoden werken, neem een kijkje op het blogbericht [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Bronnen toevoegen {#adding-resources}

Een van de items die in de gecentreerde `FlexLayout` zal komen, is een afbeelding. Voor deze tutorial kun je de [informatiepagina-afbeelding](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) op GitHub bekijken en downloaden. Zodra je deze hebt gedownload, voeg je deze toe aan de statische map van je project in `src/main/resources/static/images` en noem deze `Files.svg`.

Door deze afbeelding in de statische map te plaatsen, kun je deze refereren met behulp van het Webserver-protocol, zoals je deed bij het verwijzen naar het CSS-bestand in de eerste stap, [Een basisapp maken](/docs/introduction/tutorial/creating-a-basic-app). Vervolgens kun je het binnen je app gebruiken als een HTML-element, zoals:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView` maken {#creating-about-view}

Net als de twee bestaande app-pagina's zal de informatiepagina een routable view zijn. In `src/main/java/com/webforj/tutorial/views`, voeg een klasse toe met de naam `AboutView`. Gebruik een `FlexLayout` voor de gebonden component, zoals je deed voor `AppTitle`.

Aangezien je de klasse `AboutView` hebt genoemd, is er geen behoefte om een aangepaste waarde voor de URL-mapping op te geven; deze pagina wordt standaard weergegeven op `http://localhost:8080/about`.

Hier is hoe het eruit ziet wanneer je de concepten uit de vorige stappen met de nieuw gemaakte componenten gebruikt om een nieuwe view met gecentreerde inhoud te creëren: 

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

Het wordt kort genoemd in de stap [Routing en Composities](/docs/introduction/tutorial/routing-and-composites), maar er zijn twee [route types](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` en `AboutView` zijn allemaal `View`-routes, terwijl het route-type dat je zult gebruiken om het zijmenu van de app te maken een `Layout`-route is.

Layout-routes omhullen child views en laten bepaalde UI-onderdelen behouden over views heen, zoals een zijmenu. In `src/main/java/com/webforj/tutorial/layouts`, maak een klasse genaamd `MainLayout`.

### Route outlets {#route-outlets}

Net als de view-routes heeft `MainLayout` een `@Route`-annotatie nodig. Omdat het echter `Layout` als achtervoegsel heeft en layout-routes niet bijdragen aan de URL, hoeft deze annotatie geen argumenten te hebben.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

De app weet welke views binnen `MainLayout` moeten worden weergegeven door de layoutklasse als de [route outlet](/docs/routing/route-hierarchy/route-outlets) in elke view te verklaren. De vorige stappen hebben alleen een `value`-eigenschap ingesteld in de `@Route`-annotaties, dus nu moet je expliciet aangeven wat de `value`- en `outlet`-eigenschappen zijn voor de viewklassen.

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

:::note Laatste aanpassingen
Dit is de laatste wijziging die nodig is voor `FormView` en `AboutView` in deze stap, dus vergeet niet de `@Route`-annotatie voor die views bij te werken voordat je je app uitvoert.
:::

## De `AppLayout`-component gebruiken {#using-the-app-layout-component}

Nu je app de views binnen `MainLayout` weergeeft, kun je kiezen waar die componenten worden weergegeven. Het kiezen van de `AppLayout` als de gebonden component voor `MainLayout` stelt je in staat om de views standaard op te slaan in een hoofdinhoudsgebied, terwijl je ook verschillende gebieden hebt om items voor de koptekst en het zijmenu toe te voegen.

### Slots {#slots}

Voor veel webforJ-containers voegt het gebruik van de `add()`-methoden UI-componenten toe aan het hoofdinhoudsgebied. In de `AppLayout`-component zijn er meerdere gebieden voor het toevoegen van UI-componenten, elk in een aparte slot. Door `MainLayout` als een layout-route te markeren en de gebonden component in te stellen als een `AppLayout`, worden views automatisch in de hoofdinhoudsslot weergegeven.

In deze stap gebruik je de `drawer-title` en `drawer`-slots om een zijmenu te creëren, en de `header`-slot om weer te geven welke pagina de gebruiker bekijkt en een toggle voor het zijmenu.

### Een zijmenu maken {#making-a-side-menu}

Wanneer er voldoende schermruimte op het apparaat is, toont de `AppLayout`-component een lade. Hier voeg je de `AppTitle` opnieuw toe en items die gebruikers in staat stellen om door de app te navigeren.

Standaard toont `AppLayout` geen ladekop, maar door de methode `setDrawerHeaderVisible()` te gebruiken, kun je items die zich in de `drawer-title`-slot bevinden, tonen, wat de `AppTitle` is met zijn subtitel weergegeven.

```java
private AppLayout appLayout = new AppLayout();

// Toon de ladekop
appLayout.setDrawerHeaderVisible(true);

// Voeg de AppTitle toe aan de ladekop met zijn subtitel
appLayout.addToDrawerTitle(new AppTitle(true));
```

De `drawer`-slot moet dan de componenten bevatten die gebruikers in staat stellen om in de app te navigeren. Het gebruiken van de [`AppNav`](/docs/components/appnav) component maakt het eenvoudig om nieuwe navigatie-opties te creëren. Voor elke link hoef je alleen maar een `AppNavItem` te maken. De `AppNavItem`-componenten in deze tutorial gebruiken drie parameters:

- Het label voor de link
- De doelview
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

De `header`-slot moet twee items bevatten: een toggle om het zijmenu te tonen of te verbergen en een manier om de titel van het kader weer te geven. Beide items zullen binnen een [Toolbar](/docs/components/toolbar) component zitten, een andere manier om componenten te organiseren.

Je kunt de toggle voor de `AppLayout`-lade opnemen met de component `AppDrawerToggle`. Deze component is al gestyled met een veelgebruikt pictogram voor verborgen menu-opties en richt zich op de lade om deze te openen en te sluiten.

```java
// Maak de containercomponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Voeg de Toolbar toe aan de koptekst van de AppLayout
appLayout.addToHeader(toolbar);

// Voeg de AppDrawerToggle toe aan de toolbar
toolbar.addToStart(new AppDrawerToggle());
```

De koptekst kan ook de titel van het kader weergeven door het navigatie-evenement te gebruiken om details over de binnenkomende component op te halen, terwijl er een gebeurtenislistener is om de registratie te verwijderen om geheugenlekken te voorkomen.

```java
// Maak het H1-element en de navigatieregistratie
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Registreer het evenement bij navigatie
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Verwijder luisteraars voordat MainLayout wordt vernietigd
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Haal de titel van het kader op uit de binnenkomende view-klasse
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Voltooid `MainLayout`

Hier is `MainLayout` met de gemaakte inhoud voor de lade en de koptekst binnen een `AppLayout`:

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

Zoals eerder vermeld, was de enige wijziging in `FormView` de `@Route`-annotatie.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView` bijwerken {#updating-main-view}

Voor `MainView` zul je de gebonden component van een `Div` naar een `FlexLayout` veranderen. Dit stelt je in staat om de tabel te centreren, terwijl je ook specifieke componenten binnen de indeling verplaatst. Het gebruik van de methode `setItemAlignment()` stelt je in staat om een component in de indeling te kiezen en deze te verplaatsen, zodat je de tabel gecentreerd kunt houden terwijl je de knop voor het toevoegen van klanten in de rechterbovenhoek van de indeling plaatst.

```java
// Verander de gebonden component in een FlexLayout
private FlexLayout self = getBoundComponent();

// Stel de knop aan het einde van de kruisas uit te lijnen
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Een andere verbetering die je hier kunt aanbrengen, is de breedte van de tabel. In plaats van een vaste breedte kun je deze instellen om de breedte van zijn bovenliggende container, de `FlexLayout`, te evenaren. Vervolgens kan die `FlexLayout` een maximale breedte hebben, zodat deze op grotere schermen niet overstretched. 

```java 
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Door deze samen te voegen en een andere methode te maken om de `FlexLayout` gecentreerd te krijgen zoals de vorige, ziet `MainView` met de gemarkeerde wijzigingen er als volgt uit:

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
    private Button addCustomer = new Button("Voeg Klant Toe", ButtonTheme.PRIMARY,
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
