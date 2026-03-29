---
sidebar_position: 4
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: df54783e5555a595f5cceb28849f3787
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite`-component combineert bestaande webforJ-componenten tot zelfstandig, herbruikbare componenten met aangepaste functionaliteit. Gebruik het om interne webforJ-componenten in herbruikbare eenheden met bedrijfslogica te encapsuleren, componentpatronen door uw app heen te hergebruiken en meerdere componenten te combineren zonder implementatiedetails bloot te stellen.

Een `Composite`-component heeft een sterke verbinding met een onderliggende gebonden component. Dit geeft je controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele erfelijkheid waar alles blootgesteld wordt.

Als je webcomponenten van een andere bron wilt integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigendomsbeheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die ingesloten inhoud accepteren

## Gebruik {#usage}

Om een `Composite`-component te definiëren, breid je de `Composite`-klasse uit en geef je het type component op dat het beheert. Dit wordt je gebonden component, dat de wortelcontainer is die je interne structuur bevat:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Toegang tot de gebonden component om het te configureren
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Verzenden"));
  }
}
```

De `getBoundComponent()`-methode biedt toegang tot je onderliggende component, zodat je de eigenschappen kunt configureren, kindcomponenten kunt toevoegen en het gedrag rechtstreeks kunt beheren.

De gebonden component kan een [webforJ-component](/docs/components/overview) of [HTML-elementcomponent](/docs/components/html-elements) zijn. Voor flexibele lay-outs, overweeg om [`FlexLayout`](/docs/components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als je gebonden component te gebruiken.

:::note Componentuitbreiding
Verleng nooit direct de `Component` of `DwcComponent`. Gebruik altijd compositiepatronen met `Composite` om aangepaste componenten te bouwen.
:::

Override `initBoundComponent()` wanneer je meer flexibiliteit nodig hebt in het creëren en beheren van de gebonden component, zoals het gebruik van geparametriseerde constructors in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer de gebonden component vereist dat componenten aan zijn constructor worden doorgegeven in plaats van later toegevoegd te worden.

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

webforJ beheert automatisch alle levenscyclusbeheer voor `Composite`-componenten. Door gebruik te maken van de `getBoundComponent()`-methode kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, basale lay-outinstelling en gebeurtenisregistratie.

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

Als je aanvullende specifieke instellingen of opruimvereisten hebt, moet je mogelijk de optionele levenscyclushooks `onDidCreate()` en `onDidDestroy()` gebruiken:

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
   // Ruim bronnen op
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Data-update logica
 }
}
```

Als je acties moet uitvoeren nadat de component aan de DOM is gekoppeld, gebruik dan de `whenAttached()`-methode:

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

## Voorbeeld `Composite`-component {#example-composite-component}

Het volgende voorbeeld demonstreert een Todo-app waarbij elk item een `Composite`-component is die bestaat uit een [`RadioButton`](../components/radiobutton) gestyled als een schakelaar en een Div met tekst:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wil je misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen in een enkele eenheid, zelfs wanneer herbruikbaarheid niet de hoogste prioriteit heeft:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
