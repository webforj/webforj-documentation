---
sidebar_position: 4
title: Composite Components
_i18n_hash: 7e40c0b9a2feae4f8e56829bb2c8889b
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Die `Composite`-Komponente kombiniert vorhandene webforJ-Komponenten zu eigenständigen, wiederverwendbaren Komponenten mit benutzerdefiniertem Verhalten. Verwenden Sie sie, um interne webforJ-Komponenten in wiederverwendbare Geschäftsanwendungseinheiten zu verpacken, Komponentenmuster in Ihrer Anwendung wiederzuverwenden und mehrere Komponenten zu kombinieren, ohne Implementierungsdetails offenzulegen.

Eine `Composite`-Komponente hat eine starke Assoziation zu einer zugrunde liegenden gebundenen Komponente. Dies gibt Ihnen die Kontrolle darüber, auf welche Methoden und Eigenschaften Benutzer zugreifen können, im Gegensatz zu herkömmlicher Vererbung, bei der alles offengelegt wird.

Wenn Sie web-Komponenten aus einer anderen Quelle integrieren müssen, verwenden Sie spezialisierte Alternativen:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Für web-Komponenten mit typsicherer Eigenschaftsverwaltung
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Für web-Komponenten, die geslottene Inhalte akzeptieren

<AISkillTip skill="webforj-creating-components" />

## Verwendung {#usage}

Um eine `Composite`-Komponente zu definieren, erweitern Sie die `Composite`-Klasse und geben Sie den Typ der Komponente an, die sie verwaltet. Dies wird Ihre gebundene Komponente, die der Wurzelcontainer ist, der Ihre interne Struktur enthält:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Zugriff auf die gebundene Komponente zur Konfiguration
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Einreichen"));
  }
}
```

Die Methode `getBoundComponent()` bietet Zugriff auf Ihre zugrunde liegende Komponente, sodass Sie deren Eigenschaften konfigurieren, untergeordnete Komponenten hinzufügen und deren Verhalten direkt verwalten können.

Die gebundene Komponente kann jede [webforJ-Komponente](/docs/components/overview) oder [HTML-Elementkomponente](/docs/components/html-elements) sein. Für flexible Layouts sollten Sie [`FlexLayout`](/docs/components/flex-layout) oder [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als Ihre gebundene Komponente in Betracht ziehen.

:::note Komponenten-Erweiterung
Erweitern Sie niemals `Component` oder `DwcComponent` direkt. Verwenden Sie immer Kompositionsmuster mit `Composite`, um benutzerdefinierte Komponenten zu erstellen.
:::

Überschreiben Sie `initBoundComponent()`, wenn Sie mehr Flexibilität beim Erstellen und Verwalten der gebundenen Komponente benötigen, beispielsweise bei der Verwendung von parametrierbaren Konstruktoren anstelle des Standard-konstrukteurs ohne Argumente. Verwenden Sie dieses Muster, wenn die gebundene Komponente Komponenten benötigt, die in ihren Konstruktor übergeben werden müssen, anstatt später hinzugefügt zu werden.

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

## Komponentenlebenszyklus {#component-lifecycle}

webforJ verwaltet das gesamte Lebenszyklusmanagement für `Composite`-Komponenten automatisch. Durch die Verwendung der Methode `getBoundComponent()` kann das meiste benutzerdefinierte Verhalten im Konstruktor behandelt werden, einschließlich des Hinzufügens untergeordneter Komponenten, des Setzens von Eigenschaften, der grundlegenden Layout-Setup und der Ereignisregistrierung.

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

Wenn Sie zusätzliche spezifische Einrichtungs- oder Bereinigungsanforderungen haben, müssen Sie möglicherweise die optionalen Lebenszyklus-Hooks `onDidCreate()` und `onDidDestroy()` verwenden:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Komponenten initialisieren, die eine DOM-Anbindung erfordern
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
   // Logik zur Datenaktualisierung
 }
}
```

Wenn Sie Aktionen durchführen müssen, nachdem die Komponente an das DOM angehängt wurde, verwenden Sie die Methode `whenAttached()`:

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

Das folgende Beispiel zeigt eine Todo-App, in der jedes Element eine `Composite`-Komponente ist, die aus einem [`RadioButton`](../components/radiobutton) besteht, der als Schalter gestylt ist, und einem Div mit Text: 

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/resources/static/composite/composite.css',
]}
height='500px'
/>

## Beispiel: Komponenten gruppieren {#example-component-grouping}

Manchmal möchten Sie möglicherweise eine `Composite` verwenden, um verwandte Komponenten zu einer einzigen Einheit zusammenzufassen, selbst wenn die Wiederverwendbarkeit nicht das Hauptanliegen ist:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/resources/static/composite/analyticscomposite.css',
]}
height='500px'
/>
