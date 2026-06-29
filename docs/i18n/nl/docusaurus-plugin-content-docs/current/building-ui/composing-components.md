---
sidebar_position: 4
title: Composing Components
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: e740e537ffcccd1f316f30c21ceb2a4e
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite` component combineert bestaande webforJ componenten tot zelf-contained, herbruikbare componenten met aangepast gedrag. Gebruik het om interne webforJ componenten in herbruikbare bedrijfslogica-eenheden te wrappen, herbruikbare componentpatronen door je app te gebruiken en meerdere componenten te combineren zonder implementatiedetails bloot te stellen.

Een `Composite` component heeft een sterke associatie met een onderliggende gebonden component. Dit geeft je controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele erfelijkheid waarbij alles wordt blootgesteld.

Als je webcomponenten van een andere bron moet integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigenschapsbeheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die ingesloten inhoud accepteren

<AISkillTip skill="webforj-creating-components" />

## Gebruik {#usage}

Om een `Composite` component te definiëren, breid je de `Composite` klasse uit en geef je het type component op dat het beheert. Dit wordt je gebonden component, wat de hoofddoos is die je interne structuur vasthoudt:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Toegang tot de gebonden component om deze te configureren
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Verzenden"));
  }
}
```

De `getBoundComponent()` methode biedt toegang tot je onderliggende component, waarmee je de eigenschappen kunt configureren, kindcomponenten kunt toevoegen en het gedrag rechtstreeks kunt beheren.

De gebonden component kan een [webforJ component](/docs/components/overview) of [HTML elementcomponent](/docs/components/html-elements) zijn. Voor flexibele lay-outs, overweeg om [`FlexLayout`](/docs/components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als je gebonden component te gebruiken.

:::note Component Extensie
Strek nooit `Component` of `DwcComponent` direct uit. Gebruik altijd compositiepatronen met `Composite` om aangepaste componenten te bouwen.
:::

Overschrijf `initBoundComponent()` wanneer je meer flexibiliteit nodig hebt bij het maken en beheren van de gebonden component, zoals het gebruik van geparameteriseerde constructors in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer de gebonden component vereist dat componenten aan de constructor worden doorgegeven in plaats van er later aan toe te voegen.

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

webforJ beheert alle levenscyclusbeheer voor `Composite` componenten automatisch. Door gebruik te maken van de `getBoundComponent()` methode kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, basislay-outconfiguratie en evenementregistratie.

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

Als je aanvullende specifieke opstellingen of opruimvereisten hebt, moet je mogelijk de optionele levenscyclus-hooks `onDidCreate()` en `onDidDestroy()` gebruiken:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiseer componenten die DOM-koppeling vereisen
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Opruimen van middelen
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Logica voor gegevens bijwerken
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

Het volgende voorbeeld demonstreert een Todo-app waarbij elk item een `Composite` component is dat bestaat uit een [`RadioButton`](../components/radiobutton) gestyld als een schakelaar en een Div met tekst:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/resources/static/composite/composite.css',
]}
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wil je misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen in één eenheid, zelfs wanneer herbruikbaarheid niet de belangrijkste zorg is:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/resources/static/composite/analyticscomposite.css',
]}
height='550px'
/>
