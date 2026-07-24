---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: ddf62eb6d62a711c38f9ddaf9caeabad
---
In deze stap haal je alle onderdelen van je app samen in een samenhangende app-indeling. Aan het einde van deze stap zal de structuur van je app nauw samenvallen met het [SideMenu-archetype](/docs/building-ui/archetypes/sidemenu), en zul je een beter begrip hebben van hoe de volgende componenten en concepten werken:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route-uitgangen](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## De app uitvoeren {#running-the-app}

Terwijl je aan je app ontwikkelt, kun je [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de bovenliggende directory die het `pom.xml`-bestand bevat, dit is `6-integrating-an-app-layout` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Een herbruikbare component maken {#creating-a-reusable-component}

In een vorige stap, [Routing en Composities](/docs/introduction/tutorial/routing-and-composites), heb je twee samenstelcomponenten gemaakt die de inhoud van de klantentabel en het klantenformulier bevatten. Als onderdeel van deze stap maak je een kleinere, herbruikbare samengestelde component om de naam van de app in het zijmenu en een informatiepagina weer te geven. Als je in de toekomst de naam van de app wilt wijzigen, hoef je deze alleen in deze component bij te werken.

In `src/main/java/com/webforj/tutorial/components`, maak een klasse genaamd `AppTitle`. De gebonden component voor `AppTitle` zal een `FlexLayout` zijn, een containercomponent die door deze stap heen wordt gebruikt om je te laten zien hoe je complexere indelingen kunt maken. Voor deze `FlexLayout` regel je de richting van de items en de afstand tussen de items. Dat doe je door de methoden `setDirection()` en `setSpacing()` respectievelijk te gebruiken.

```java title='AppTitle.java'
// Maak de gebonden component een FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Rangschik de items verticaal
    self.setDirection(FlexDirection.COLUMN);

    // Stel de afstand tussen items in
    self.setSpacing("0px");
  }
}
```

Gebruik vervolgens standaard HTML-elementen om de titel en subtitel te maken. Door de ondermarge van een header-element in te stellen op `0px`, komen de elementen dichterbij elkaar, en je kunt de subtitel stylen met [DWC CSS-variabelen](/docs/styling/css-variables).

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

Ook al is `AppTitle` eenvoudig, het toevoegen van een booleaanse parameter aan de constructor stelt je in staat om te regelen wanneer bepaalde delen van de component, zoals de subtitel, moeten worden weergegeven.

```java title='AppTitle.java'
// Voeg een booleaanse parameter toe
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Voeg de titel standaard toe
      .add(title);

  // Optioneel de subtitel weergeven
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Voltooid `AppTitle` {#completed-app-title}

In totaal moet de herbruikbare component er als volgt uitzien:

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

De eerste plaats waar je de nieuw gemaakte `AppTitle`-component kunt toevoegen, is een informatiepagina. Deze pagina bevat een afbeelding en de `AppTitle`-component, gecentreerd op de pagina door een andere `FlexLayout` te gebruiken.

### Inhoud centreren met een `FlexLayout` {#centering-content-using-a-flexlayout}

Het doel is de inhoud van de informatiepagina te centreren met behulp van de `FlexLayout`. De `FlexLayout`-component volgt het [CSS flexbox-layoutmodel](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden voor de `FlexLayout`, zoals de eerder gebruikte om de items in een kolom te oriënteren, zijn verschillende manieren om de items te rangschikken.

De methoden voor het rangschikken van items in een `FlexLayout` gebruiken een relatief richtingssysteem. In plaats van na te denken over de horizontale en verticale assen, is het beter om na te denken over de as parallel aan de items als de hoofd-as, en de as loodrecht op de items als de kruis-as.

Het instellen van zowel de `FlexJustifyContent`- als de `FlexAlignment`-eigenschappen op `CENTER` zal de items centreren langs zowel de hoofd- als de kruis-as in de `FlexLayout`, en ervoor zorgen dat de `FlexLayout` de volledige ruimte van zijn bovenliggende container in beslag neemt, zodat deze gecentreerd op de pagina staat.

```java
private final FlexLayout layout = new FlexLayout();

// Vul de volledige ruimte van het bovenliggende element
layout.setSize("100%", "100%");

// Maak de hoofd-as verticaal
layout.setDirection(FlexDirection.COLUMN);

// Centreer de items langs de kruis-as
layout.setAlignment(FlexAlignment.CENTER);

// Centreer de items langs de hoofd-as
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Om te visualiseren hoe de verschillende methoden werken, kijk naar de blogpost [FlexWrap je geest rond de FlexLayout van webforJ](/blog/2025/08/26/flexlayout-container).

### Hulpbronnen toevoegen {#adding-resources}

Een van de items die in de gecentreerde `FlexLayout` zullen komen, is een afbeelding. Voor deze tutorial kun je de [afbeelding van de informatiepagina](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) op GitHub bekijken en downloaden. Zodra je deze hebt gedownload, voeg je deze toe aan de statische map van je project in `src/main/resources/static/images` en noem deze `Files.svg`.

Door deze afbeelding in de statische map te plaatsen, kun je deze refereren met behulp van het [Webserverprotocol](/docs/managing-resources/assets-protocols#the-webserver-protocol). Vervolgens kun je deze binnen je app gebruiken als een HTML-element, zoals:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView` maken {#creating-about-view}

Net als de twee bestaande app-pagina's, zal de informatiepagina een navigeerbare weergave zijn. In `src/main/java/com/webforj/tutorial/views`, voeg een klasse genaamd `AboutView` toe. Gebruik een `FlexLayout` voor de gebonden component, zoals je deed voor `AppTitle`.

Omdat je de klasse `AboutView` hebt genoemd, is er geen behoefte om een aangepaste waarde voor de URL-mapping te geven; deze pagina wordt standaard weergegeven op `http://localhost:8080/about`.

Hier is hoe het eruit ziet wanneer je de concepten van de vorige stappen gebruikt met de nieuw gemaakte componenten om een nieuwe weergave met gecentreerde inhoud te creëren:

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

Dit wordt kort genoemd in de stap [Routing en Composities](/docs/introduction/tutorial/routing-and-composites), maar er zijn twee [route-types](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` en `AboutView` zijn allemaal `View`-routes, terwijl het route-type dat je gebruikt om het zijmenu van de app te maken een `Layout`-route is.

Layout-routes omringen kindweergaven en stellen bepaalde UI-onderdelen in staat om consistent te blijven tussen weergaven, zoals een zijmenu. In `src/main/java/com/webforj/tutorial/layouts`, maak een klasse genaamd `MainLayout`.

### Route-uitgangen {#route-outlets}

Net als de weergave-routes, heeft `MainLayout` een `@Route`-annotatie nodig. Omdat het echter `Layout` als achtervoegsel heeft en layout-routes geen bijdrage leveren aan de URL, heeft deze annotatie geen argumenten nodig.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

De app weet welke weergaven moeten worden weergegeven binnen `MainLayout` door de layout-klasse als de [route-uitgang](/docs/routing/route-hierarchy/route-outlets) in elke weergave te declareren. De vorige stappen hebben alleen een `value`-eigenschap ingesteld in de `@Route`-annotaties, dus nu moet je expliciet vermelden wat de `value`- en `outlet`-eigenschappen zijn voor de weergaveklassen.

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
Dit is de laatste aanpassing die nodig is voor `FormView` en `AboutView` in deze stap, dus vergeet niet de `@Route`-annotatie voor die weergaven bij te werken voordat je je app uitvoert.
:::

## De `AppLayout`-component gebruiken {#using-the-app-layout-component}

Nu je app de weergaven binnen `MainLayout` weergeeft, kun je kiezen waar die componenten worden weergegeven. Het kiezen van de `AppLayout` als de gebonden component voor `MainLayout` stelt je in staat om de weergaven standaard in een hoofdinhoudsgebied op te slaan, terwijl je ook verschillende gebieden hebt om items voor de koptekst en het zijmenu toe te voegen.

### Slots {#slots}

Voor veel webforJ-containers voegt het gebruik van de `add()`-methoden UI-componenten toe aan het hoofdinhoudsgebied. In de `AppLayout`-component zijn er meerdere gebieden voor het toevoegen van UI-componenten, elk in een aparte slot. Door `MainLayout` als een layout-route te markeren en de gebonden component in te stellen als een `AppLayout`, worden weergaven automatisch weergegeven in de hoofdinhoudsslot.

In deze stap gebruik je de `drawer-title`- en `drawer`-slots om een zijmenu te maken, en de `header`-slot om weer te geven welke pagina de gebruiker is en een toggle voor het zijmenu.

### Een zijmenu maken {#making-a-side-menu}

Wanneer er voldoende schermruimte op het apparaat is, toont de `AppLayout`-component een lade. Hier voeg je de `AppTitle` opnieuw toe en items die gebruikers in staat stellen om door de app te navigeren.

Standaard toont de `AppLayout` geen ladekoptekst, maar door de methode `setDrawerHeaderVisible()` te gebruiken, kun je items tonen die binnen de `drawer-title`-slot zitten, wat de `AppTitle` met zijn subtitel zal zijn.

```java
private AppLayout appLayout = new AppLayout();

// Toon de ladekop
appLayout.setDrawerHeaderVisible(true);

// Voeg de AppTitle toe aan de ladekop met zijn subtitel
appLayout.addToDrawerTitle(new AppTitle(true));
```

De `drawer`-slot moet dan de componenten bevatten die gebruikers in staat stellen om in de app te navigeren. Door de [`AppNav`](/docs/components/appnav) component te gebruiken, maak je eenvoudig nieuwe navigatie-opties. Voor elke link hoef je alleen een `AppNavItem` aan te maken. De `AppNavItem`-componenten in deze tutorial gebruiken drie parameters:

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

De `header`-slot moet twee items bevatten: een toggle om het zijmenu te tonen of verbergen en een manier om de frame titel weer te geven. Beide items zullen binnen een [Toolbar](/docs/components/toolbar) component zitten, een andere manier om componenten te organiseren.

Je kunt de toggle voor de `AppLayout`-lade toevoegen met de component `AppDrawerToggle`. Deze component is al gestyled met een veelgebruikt pictogram voor verborgen menu-opties, en richt zich op de lade om deze te openen en te sluiten.

```java
// Maak de containercomponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Voeg de Toolbar toe aan de kop van de AppLayout
appLayout.addToHeader(toolbar);

// Voeg de AppDrawerToggle toe aan de toolbar
toolbar.addToStart(new AppDrawerToggle());
```

De koptekst kan ook de frame titel weergeven door het navigatie-evenement te gebruiken om details over de binnenkomende component op te halen, terwijl je een eventlistener hebt om de registratie te verwijderen om geheugenlekken te voorkomen.

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

// Haal de frame titel op uit de binnenkomende weergaveklasse
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Voltooid `MainLayout` {#completed-mainlayout}

Hier is `MainLayout` met de gemaakte inhoud voor de lade en de kop binnen een `AppLayout`:

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

Voor `MainView` wijzig je de gebonden component van een `Div` naar een `FlexLayout`. Dit stelt je in staat om de tabel te centreren, terwijl je ook specifieke componenten binnen de indeling verplaatst. Het gebruik van de methode `setItemAlignment()` laat je een component in de indeling selecteren en verplaatsen, zodat je de tabel gecentreerd kunt houden terwijl je de knop "klant toevoegen" in de rechterbovenhoek van de indeling verankert.

```java
// Wijzig de gebonden component naar een FlexLayout
private FlexLayout self = getBoundComponent();

// Positioneer de knop aan het einde van de kruis-as
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Een andere verbetering die je hier kunt aanbrengen, is de breedte van de tabel. In plaats van een vaste breedte, kun je deze instellen om overeen te komen met zijn bovenliggende container, de `FlexLayout`. Vervolgens kan die `FlexLayout` een maximale breedte hebben zodat deze bij grotere schermen niet uitrekt.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Door deze samen te voegen en een andere methode te maken om de `FlexLayout` gecentreerd te krijgen zoals de vorige, ziet `MainView` er met de gemarkeerde wijzigingen als volgt uit:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Klanten Tabel")
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
