---
sidebar_position: 4
title: Composite Components
_i18n_hash: fb15eb19cfe0ca1aebb77a67b10c9ecd
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

De `Composite` component combineert bestaande webforJ componenten tot zelf-contained, herbruikbare componenten met aangepaste functionaliteit. Gebruik het om interne webforJ componenten te verpakken in herbruikbare logica-eenheden, componentpatronen door uw app heen te hergebruiken en meerdere componenten te combineren zonder implementatiedetails bloot te geven.

Een `Composite` component heeft een sterke associatie met een onderliggend gebonden component. Dit geeft u controle over welke methoden en eigenschappen gebruikers kunnen benaderen, in tegenstelling tot traditionele overerving waarbij alles blootgelegd is.

Als u webcomponenten van een andere bron wilt integreren, gebruik dan gespecialiseerde alternatieven:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Voor webcomponenten met type-veilige eigenschapsbeheer
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Voor webcomponenten die inhoud met slots accepteren

<AISkillTip skill="webforj-creating-components" />

## Gebruik {#usage}

Om een `Composite` component te definiëren, breidt u de `Composite` klasse uit en specificeert u het type component dat het beheert. Dit wordt uw gebonden component, welke de hoofdomslag is die uw interne structuur bevat:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Toegang tot het gebonden component om het te configureren
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Verstuur"));
  }
}
```

De `getBoundComponent()` methode biedt toegang tot uw onderliggend component, waarmee u zijn eigenschappen kunt configureren, kindcomponenten kunt toevoegen en zijn gedrag direct kunt beheren.

Het gebonden component kan elk [webforJ component](/docs/components/overview) of [HTML elementcomponent](/docs/components/html-elements) zijn. Voor flexibele lay-outs, overweeg om [`FlexLayout`](/docs/components/flex-layout) of [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als uw gebonden component te gebruiken.

:::note Componentuitbreiding
Verleng nooit `Component` of `DwcComponent` direct. Gebruik altijd samenstellingspatronen met `Composite` om aangepaste componenten te bouwen.
:::

Overschrijf `initBoundComponent()` wanneer u meer flexibiliteit nodig heeft bij het creëren en beheren van het gebonden component, zoals het gebruik van parameterized constructors in plaats van de standaard constructor zonder argumenten. Gebruik dit patroon wanneer het gebonden component vereist dat componenten aan de constructeur worden doorgegeven in plaats van er later aan te worden toegevoegd.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Naam");
   emailField = new TextField("E-mail");
   submitButton = new Button("Verstuur");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Componentlevenscyclus {#component-lifecycle}

webforJ beheert alle lifecycle management voor `Composite` componenten automatisch. Door gebruik te maken van de `getBoundComponent()` methode kan het meeste aangepaste gedrag in de constructor worden afgehandeld, inclusief het toevoegen van kindcomponenten, het instellen van eigenschappen, basislay-outopzet en registratie van evenementen.

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

Als u aanvullende specifieke instellingen of opruimvereisten heeft, moet u mogelijk de optionele lifecycle hooks `onDidCreate()` en `onDidDestroy()` gebruiken:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiseer componenten die DOM-hechting vereisen
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
   // Logica voor gegevensupdate
 }
}
```

Als u acties moet uitvoeren nadat de component aan de DOM is gehecht, gebruik dan de `whenAttached()` methode:

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

Het volgende voorbeeld demonstreert een Todo-app waarin elk item een `Composite` component is, bestaande uit een [`RadioButton`](../components/radiobutton) gestyled als een schakelaar en een Div met tekst:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Voorbeeld: Componentgroepering {#example-component-grouping}

Soms wilt u misschien een `Composite` gebruiken om gerelateerde componenten samen te groeperen tot een enkele eenheid, zelfs wanneer herbruikbaarheid niet de belangrijkste zorg is:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
