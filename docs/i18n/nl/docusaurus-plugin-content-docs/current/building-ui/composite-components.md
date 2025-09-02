---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: 997bb40968c4f4ede5eccb00c27e5305
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite` component combineert bestaande webforJ-componenten in zelf-contained, herbruikbare componenten met aangepaste gedrag. Gebruik het om interne webforJ-componenten te omhulllen in herbruikbare bedrijfslogica-eenheden, componentpatronen door uw app heen te hergebruiken, en meerdere componenten te combineren zonder implementatiedetails bloot te stellen.

Een `Composite` component heeft een sterke associatie met een onderliggend gebonden component. Dit geeft je controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele overerving waarbij alles wordt blootgesteld.

Als je webcomponenten van een andere bron wilt integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigendombeheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die geadresseerde inhoud accepteren

## Gebruik {#usage}

Om een `Composite` component te definiëren, verleng je de `Composite` klasse en specificeer je het type component dat het beheert. Dit wordt jouw gebonden component, dat de hoofdcontainer is die jouw interne structuur vasthoudt:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // Toegang tot het gebonden component om het te configureren
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Verzenden"));
  }
}
```

De `getBoundComponent()` methode biedt toegang tot jouw onderliggende component, waardoor je zijn eigenschappen kunt configureren, kindcomponenten kunt toevoegen en zijn gedrag direct kunt beheren.

Het gebonden component kan elke [webforJ component](../components/overview) of [HTML-elementcomponent](/docs/building-ui/web-components/html-elements) zijn. Voor flexibele lay-outs, overweeg het gebruik van [`FlexLayout`](../components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als jouw gebonden component.

:::note Componentuitbreiding
Verleng nooit `Component` of `DwcComponent`direct. Gebruik altijd compositiepatronen met `Composite` om aangepaste componenten te bouwen.
:::

Overschrijf `initBoundComponent()` wanneer je meer flexibiliteit nodig hebt bij het creëren en beheren van het gebonden component, zoals het gebruik van parameterized constructors in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer het gebonden component vereist dat componenten aan zijn constructor worden doorgegeven in plaats van erachter te worden toegevoegd.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Naam");
   emailField = new TextField("Email");
   submitButton = new Button("Verzenden");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Componentlevenscyclus {#component-lifecycle}

webforJ beheert automatisch alle levenscyclusbeheer voor `Composite` componenten. Door gebruik te maken van de `getBoundComponent()` methode kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, basis lay-outinstellingen en eventregistratie.

```java
public class UserDashboard extends Composite<FlexLayout> {
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

Als je aanvullende specifieke opstelling of opruimvereisten hebt, moet je mogelijk de optionele levenscyclushooks `onDidCreate()` en `onDidDestroy()` gebruiken:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiseer componenten die DOM-bevestiging vereisen
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Opruimen van bronnen
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Gegevensupdate logica
 }
}
```

Als je acties moet uitvoeren na de component aan de DOM is gehecht, gebruik dan de `whenAttached()` methode:

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

Het volgende voorbeeld demonstreert een Todo-app waarin elk item een `Composite` component is dat bestaat uit een [`RadioButton`](../components/radiobutton) gestileerd als een schakelaar en een Div met tekst: 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wil je misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen in één eenheid, zelfs wanneer herbruikbaarheid niet de belangrijkste zorg is:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
