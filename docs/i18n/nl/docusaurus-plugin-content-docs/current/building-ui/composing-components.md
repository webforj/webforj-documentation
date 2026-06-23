---
sidebar_position: 4
title: Composite Components
_i18n_hash: 7e40c0b9a2feae4f8e56829bb2c8889b
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite` component combineert bestaande webforJ componenten in zelf-contained, herbruikbare componenten met aangepaste functionaliteit. Gebruik het om interne webforJ componenten te verpakken in herbruikbare eenheden van bedrijfslogica, herbruik componentpatronen in je app en combineer meerdere componenten zonder implementatiedetails bloot te stellen.

Een `Composite` component heeft een sterke associatie met een onderliggend gebonden component. Dit geeft je controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele overerving waarbij alles wordt blootgesteld.

Als je webcomponenten van een andere bron moet integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigendombeheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die geslotende inhoud accepteren

<AISkillTip skill="webforj-creating-components" />

## Gebruik {#usage}

Om een `Composite` component te definiëren, breid je de `Composite` klasse uit en specificeer je het type component dat het beheert. Dit wordt je gebonden component, dat de hoofdcontainer is die je interne structuur bevat:

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

De `getBoundComponent()` methode biedt toegang tot je onderliggende component, zodat je de eigenschappen kunt configureren, kindcomponenten kunt toevoegen en het gedrag rechtstreeks kunt beheren.

Het gebonden component kan elke [webforJ component](/docs/components/overview) of [HTML element component](/docs/components/html-elements) zijn. Voor flexibele lay-outs kun je overwegen om [`FlexLayout`](/docs/components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als je gebonden component te gebruiken.

:::note Componentuitbreiding
Verleng nooit `Component` of `DwcComponent` direct. Gebruik altijd samenstellingspatronen met `Composite` om aangepaste componenten te bouwen.
:::

Overschrijf `initBoundComponent()` wanneer je meer flexibiliteit nodig hebt bij het creëren en beheren van het gebonden component, zoals het gebruik van geparameteriseerde constructeurs in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer het gebonden component vereist dat componenten naar de constructor worden doorgegeven in plaats van later toegevoegd te worden.

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

## Componentlevenscyclus {#component-lifecycle}

webforJ beheert automatische alle levenscyclusbeheer voor `Composite` componenten. Door de `getBoundComponent()` methode te gebruiken, kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, basis lay-outconfiguratie en evenementregistratie.

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
   // Zoek logica hier
 }
}
```

Als je aanvullende specifieke opstelling of opruimvereisten hebt, moet je mogelijk de optionele levenscyclus hooks `onDidCreate()` en `onDidDestroy()` gebruiken:

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
   // Maak bronnen schoon
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Gegevens update logica
 }
}
```

Als je acties moet uitvoeren nadat het component aan de DOM is gehecht, gebruik dan de `whenAttached()` methode:

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

Het volgende voorbeeld demonstreert een Todo-app waarbij elk item een `Composite` component is dat bestaat uit een [`RadioButton`](../components/radiobutton) gestyled als een schakelaar en een Div met tekst:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/resources/static/composite/composite.css',
]}
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wil je misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen in een enkele eenheid, zelfs wanneer herbruikbaarheid niet de belangrijkste zorg is:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/resources/static/composite/analyticscomposite.css',
]}
height='500px'
/>
