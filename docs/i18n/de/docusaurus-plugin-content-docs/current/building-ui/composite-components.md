---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: 997bb40968c4f4ede5eccb00c27e5305
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Die `Composite`-Komponente kombiniert bestehende webforJ-Komponenten zu eigenständigen, wiederverwendbaren Komponenten mit benutzerdefiniertem Verhalten. Verwenden Sie sie, um interne webforJ-Komponenten in wiederverwendbare Geschäftseinheiten zu verpacken, Komponentenmuster in Ihrer Anwendung wiederzuverwenden und mehrere Komponenten zusammenzuführen, ohne Implementierungsdetails offenzulegen.

Eine `Composite`-Komponente hat eine starke Assoziation mit einer zugrunde liegenden gebundenen Komponente. Dies gibt Ihnen die Kontrolle darüber, auf welche Methoden und Eigenschaften Benutzer zugreifen können, im Gegensatz zu traditioneller Vererbung, bei der alles offengelegt wird.

Wenn Sie web Komponenten aus einer anderen Quelle integrieren müssen, verwenden Sie spezialisierte Alternativen:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Für web Komponenten mit typsicherem Eigenschaftenmanagement
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Für web Komponenten, die eingesetzte Inhalte akzeptieren

## Nutzung {#usage}

Um eine `Composite`-Komponente zu definieren, erweitern Sie die `Composite`-Klasse und geben Sie den Typ der Komponente an, die sie verwaltet. Dies wird Ihre gebundene Komponente, die das Wurzelcontainer ist, das Ihre interne Struktur hält:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // Zugriff auf die gebundene Komponente zur Konfiguration
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Einreichen"));
  }
}
```

Die Methode `getBoundComponent()` bietet Zugriff auf Ihre zugrunde liegende Komponente, sodass Sie deren Eigenschaften konfigurieren, untergeordnete Komponenten hinzufügen und deren Verhalten direkt verwalten können.

Die gebundene Komponente kann jede [webforJ-Komponente](../components/overview) oder [HTML-Elementkomponente](/docs/building-ui/web-components/html-elements) sein. Für flexible Layouts ziehen Sie in Betracht, [`FlexLayout`](../components/flex-layout) oder [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als Ihre gebundene Komponente zu verwenden.

:::note Komponenten-Erweiterung
Erweitern Sie niemals `Component` oder `DwcComponent` direkt. Verwenden Sie immer Kompositionsmuster mit `Composite`, um benutzerdefinierte Komponenten zu erstellen.
:::

Überschreiben Sie `initBoundComponent()`, wenn Sie mehr Flexibilität bei der Erstellung und Verwaltung der gebundenen Komponente benötigen, z. B. durch die Verwendung von parametrierten Konstruktoren anstelle des Standard-Konstruktors ohne Argumente. Verwenden Sie dieses Muster, wenn die gebundene Komponente erfordert, dass Komponenten an ihren Konstruktor übergeben werden, anstatt nachträglich hinzugefügt zu werden.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("E-Mail");
   submitButton = new Button("Einreichen");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Lebenszyklus der Komponente {#component-lifecycle}

webforJ übernimmt automatisch die gesamte Lebenszyklusverwaltung für `Composite`-Komponenten. Durch die Verwendung der Methode `getBoundComponent()` kann das meiste benutzerdefinierte Verhalten im Konstruktor behandelt werden, einschließlich des Hinzufügens untergeordneter Komponenten, des Setzens von Eigenschaften, der grundlegenden Layout-Einrichtung und der Ereignisregistrierung.

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
   searchField = new TextField("Benutzer suchen...");
   searchButton = new Button("Suchen");
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
   // Suchlogik hier
 }
}
```

Wenn Sie zusätzliche spezifische Anforderungen für die Einrichtung oder Bereinigung haben, müssen Sie möglicherweise die optionalen Lebenszyklus-Hooks `onDidCreate()` und `onDidDestroy()` verwenden:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialisieren Sie Komponenten, die eine DOM-Anhängigkeit erfordern
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Ressourcen bereinigen
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Datenaktualisierungslogik
 }
}
```

Wenn Sie nach dem Anfügen der Komponente an das DOM weitere Aktionen ausführen müssen, verwenden Sie die Methode `whenAttached()`:

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

## Beispiel `Composite`-Komponente {#example-composite-component}

Das folgende Beispiel zeigt eine Todo-App, bei der jedes Element eine `Composite`-Komponente ist, die aus einem [`RadioButton`](../components/radiobutton) besteht, der als Schalter gestylt ist, und einem Div mit Text: 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Beispiel: Komponenten Gruppierung {#example-component-grouping}

Manchmal möchten Sie vielleicht ein `Composite` verwenden, um verwandte Komponenten zu einer einzigen Einheit zusammenzufassen, selbst wenn Wiederverwendbarkeit nicht das Hauptanliegen ist:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
