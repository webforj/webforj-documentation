---
sidebar_position: 4
title: Composing Components
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 96d22d0dc6ba882867ca35edcf1edcca
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite` component combineert bestaande webforJ componenten tot zelf-contained, herbruikbare componenten met aangepaste functionaliteit. Gebruik het om interne webforJ componenten te omhullen in herbruikbare business logica eenheden, hergebruik componentpatronen door je hele app en combineer meerdere componenten zonder implementatiedetails bloot te stellen.

Een `Composite` component heeft een sterke associatie met een onderliggend gebonden component. Dit geeft je controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele overerving waarbij alles wordt blootgesteld.

Als je webcomponenten van een andere bron moet integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigendom beheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die gebundelde inhoud accepteren

<AISkillTip skill="webforj-creating-components" />

## Gebruik {#usage}

Om een `Composite` component te definiëren, breid je de `Composite` klasse uit en specificeer je het type component dat het beheert. Dit wordt je gebonden component, dat de rootcontainer is die je interne structuur vasthoudt:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Toegang tot het gebonden component om het te configureren
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Verzenden"));
  }
}
```

De `getBoundComponent()` methode biedt toegang tot je onderliggende component, waarmee je zijn eigenschappen kunt configureren, kindcomponenten kunt toevoegen en zijn gedrag direct kunt beheren.

Het gebonden component kan elk [webforJ component](/docs/components/overview) of [HTML element component](/docs/components/html-elements) zijn. Voor flexibele lay-outs, overweeg om [`FlexLayout`](/docs/components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) te gebruiken als je gebonden component.

:::note Componentuitbreiding
Breid nooit `Component` of `DwcComponent` direct uit. Gebruik altijd samenstellingpatronen met `Composite` om aangepaste componenten te bouwen.
:::

Overschrijf `initBoundComponent()` wanneer je meer flexibiliteit nodig hebt bij het creëren en beheren van de gebonden component, zoals het gebruik van parameterized constructors in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer de gebonden component vereist dat componenten aan zijn constructor worden doorgegeven in plaats van later toegevoegd.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Naam");
   emailField = new TextField("E-mail");
   submitButton = new Button("Verzenden");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Component levenscyclus {#component-lifecycle}

webforJ beheert alle levenscyclusbeheer van `Composite` componenten automatisch. Door gebruik te maken van de `getBoundComponent()` methode kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, de basis lay-outconfiguratie en het registreren van evenementen.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private final FlexLayout self = getBoundComponent();
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Zoek gebruikers...");
   searchButton = new Button("Zoeken");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // Zoeklogica hier
 }
}
```

Als je aanvullende specifieke configuratie- of opruimvereisten hebt, moet je mogelijk gebruik maken van de optionele levenscyclus hooks `onDidCreate()` en `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiseer componenten die DOM-attachment vereisen
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Ruim middelen op
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Gegevensupdate logica
 }
}
```

Als je acties moet uitvoeren nadat de component aan de DOM is gehecht, gebruik dan de `whenAttached()` methode:

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## Voorbeeld `Composite` component {#example-composite-component}

Het volgende voorbeeld demonstreert een Todo-app waarin elk item een `Composite` component is dat bestaat uit een [`RadioButton`](../components/radiobutton) gestyled als een schakelaar en een Div met tekst:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wil je misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen in een enkele eenheid, zelfs wanneer herbruikbaarheid niet de belangrijkste zorg is:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
