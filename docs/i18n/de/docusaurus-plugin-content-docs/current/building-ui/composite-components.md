---
sidebar_position: 4
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: df54783e5555a595f5cceb28849f3787
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Die `Composite`-Komponente kombiniert bestehende webforJ-Komponenten zu eigenständigen, wiederverwendbaren Komponenten mit benutzerdefiniertem Verhalten. Verwenden Sie sie, um interne webforJ-Komponenten in wiederverwendbare Geschäftseinheiten zu verpacken, Komponentenmuster in Ihrer App wiederzuverwenden und mehrere Komponenten zu kombinieren, ohne Implementierungsdetails offenzulegen.

Eine `Composite`-Komponente hat eine starke Beziehung zu einer zugrunde liegenden gebundenen Komponente. Dies gibt Ihnen die Kontrolle darüber, auf welche Methoden und Eigenschaften Benutzer zugreifen können, im Gegensatz zur traditionellen Vererbung, bei der alles offenbart wird.

Wenn Sie web-Komponenten aus einer anderen Quelle integrieren müssen, verwenden Sie spezialisierte Alternativen:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Für web-Komponenten mit typensicherem Property-Management
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Für web-Komponenten, die Slot-Inhalte akzeptieren

## Verwendung {#usage}

Um eine `Composite`-Komponente zu definieren, erweitern Sie die `Composite`-Klasse und geben Sie den Typ der Komponente an, die sie verwaltet. Dies wird Ihre gebundene Komponente, die der Wurzelcontainer ist, der Ihre interne Struktur hält:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Zugriff auf die gebundene Komponente, um sie zu konfigurieren
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Abschicken"));
  }
}
```

Die Methode `getBoundComponent()` bietet Zugriff auf Ihre zugrunde liegende Komponente, sodass Sie deren Eigenschaften konfigurieren, Kindkomponenten hinzufügen und ihr Verhalten direkt verwalten können.

Die gebundene Komponente kann jede [webforJ-Komponente](/docs/components/overview) oder [HTML-Elementkomponente](/docs/components/html-elements) sein. Für flexible Layouts sollten Sie [`FlexLayout`](/docs/components/flex-layout) oder [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als Ihre gebundene Komponente in Betracht ziehen.

:::note Komponenten Erweiterung
Erweitern Sie niemals `Component` oder `DwcComponent` direkt. Verwenden Sie immer Kompositionsmuster mit `Composite`, um benutzerdefinierte Komponenten zu erstellen.
:::

Überschreiben Sie `initBoundComponent()`, wenn Sie mehr Flexibilität beim Erstellen und Verwalten der gebundenen Komponente benötigen, z.B. durch die Verwendung von parametrierten Konstruktoren anstelle des Standardkonstruktors ohne Argumente. Verwenden Sie dieses Muster, wenn die gebundene Komponente Komponenten benötigt, die ihrem Konstruktor übergeben werden müssen, anstatt sie anschließend hinzuzufügen.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("E-Mail");
   submitButton = new Button("Abschicken");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Komponentenlebenszyklus {#component-lifecycle}

webforJ verwaltet das gesamte Lebenszyklusmanagement für `Composite`-Komponenten automatisch. Durch die Verwendung der Methode `getBoundComponent()` können die meisten benutzerdefinierten Verhaltensweisen im Konstruktor behandelt werden, einschließlich der Hinzufügung von Kindkomponenten, dem Setzen von Eigenschaften, der grundlegenden Layout-Einrichtung und der Ereignisregistrierung.

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

Wenn Sie spezifische Einrichtungs- oder Bereinigungsanforderungen haben, müssen Sie möglicherweise die optionalen Lebenszyklus-Hooks `onDidCreate()` und `onDidDestroy()` verwenden:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialisieren Sie Komponenten, die eine DOM-Anbindung erfordern
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

Wenn Sie nach dem Anfügen der Komponente an das DOM Aktionen durchführen müssen, verwenden Sie die Methode `whenAttached()`:

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

Das folgende Beispiel demonstriert eine Todo-App, bei der jedes Element eine `Composite`-Komponente ist, die aus einem [`RadioButton`](../components/radiobutton) besteht, der als Schalter gestylt ist, und einem Div mit Text: 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Beispiel: Komponenten Gruppierung {#example-component-grouping}

Manchmal möchten Sie möglicherweise eine `Composite` verwenden, um verwandte Komponenten zu einer einzigen Einheit zusammenzufassen, selbst wenn die Wiederverwendbarkeit nicht das Hauptanliegen ist:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
