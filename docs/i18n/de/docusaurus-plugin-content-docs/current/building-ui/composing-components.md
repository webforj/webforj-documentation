---
sidebar_position: 4
title: Komponenten Zusammenstellen
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 7ca404aa73a9fd445ce7cd3da09b8155
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Die `Composite`-Komponente kombiniert vorhandene webforJ-Komponenten in eigenständige, wiederverwendbare Komponenten mit benutzerdefiniertem Verhalten. Verwenden Sie sie, um interne webforJ-Komponenten in wiederverwendbare Geschäftsanlogeeinheiten zu kapseln, Komponentenmuster in Ihrer Anwendung wiederzuverwenden und mehrere Komponenten zu kombinieren, ohne Implementierungsdetails offenzulegen.

Eine `Composite`-Komponente hat eine starke Zuordnung zu einer zugrunde liegenden gebundenen Komponente. Dies gibt Ihnen die Kontrolle darüber, auf welche Methoden und Eigenschaften Benutzer zugreifen können, im Gegensatz zur traditionellen Vererbung, bei der alles offengelegt ist.

Wenn Sie web-Komponenten aus einer anderen Quelle integrieren müssen, verwenden Sie spezialisierte Alternativen:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Für web-Komponenten mit typsicherer Eigenschaftenverwaltung
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Für web-Komponenten, die slotfähigen Inhalt akzeptieren

<AISkillTip skill="webforj-creating-components" />

## Verwendung {#usage}

Um eine `Composite`-Komponente zu definieren, erweitern Sie die `Composite`-Klasse und geben Sie den Typ der zu verwaltenden Komponente an. Dies wird Ihre gebundene Komponente, die der Wurzelcontainer ist, der Ihre interne Struktur hält:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Zugriff auf die gebundene Komponente zur Konfiguration
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Absenden"));
  }
}
```

Die Methode `getBoundComponent()` bietet Zugriff auf Ihre zugrunde liegende Komponente, sodass Sie deren Eigenschaften konfigurieren, untergeordnete Komponenten hinzufügen und ihr Verhalten direkt verwalten können.

Die gebundene Komponente kann jede [webforJ-Komponente](/docs/components/overview) oder [HTML-Elementkomponente](/docs/components/html-elements) sein. Für flexible Layouts sollten Sie [`FlexLayout`](/docs/components/flex-layout) oder [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) als Ihre gebundene Komponente in Betracht ziehen.

:::note Komponentenerweiterung
Erweitern Sie niemals `Component` oder `DwcComponent` direkt. Verwenden Sie immer Kompositionsmuster mit `Composite`, um benutzerdefinierte Komponenten zu erstellen.
:::

Überschreiben Sie `initBoundComponent()`, wenn Sie mehr Flexibilität bei der Erstellung und Verwaltung der gebundenen Komponente benötigen, z. B. bei der Verwendung von parametrisierten Konstruktoren anstelle des Standardkonstruktors ohne Argumente. Verwenden Sie dieses Muster, wenn die gebundene Komponente Komponenten benötigt, die an ihren Konstruktor übergeben werden, statt später hinzugefügt zu werden.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("E-Mail");
   submitButton = new Button("Absenden");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Lebenszyklus der Komponente {#component-lifecycle}

webforJ verwaltet das gesamte Lebenszyklusmanagement für `Composite`-Komponenten automatisch. Durch die Verwendung der Methode `getBoundComponent()` kann das meiste benutzerdefinierte Verhalten im Konstruktor behandelt werden, einschließlich Hinzufügen von untergeordneten Komponenten, Festlegen von Eigenschaften, grundlegende Layout-Einrichtung und Ereignisanmeldung.

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

Wenn Sie spezifische Einrichtungs- oder Bereinigungsanforderungen haben, müssen Sie möglicherweise die optionalen Lebenszyklus-Haken `onDidCreate()` und `onDidDestroy()` verwenden:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialisieren von Komponenten, die eine DOM-Anbindung erfordern
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Ressourcenbereinigung
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Datenaktualisierungslogik
 }
}
```

Wenn Sie nach dem Anfügen der Komponente an das DOM Aktionen ausführen müssen, verwenden Sie die Methode `whenAttached()`:

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

Das folgende Beispiel zeigt eine To-do-Liste, bei der jedes Element eine `Composite`-Komponente ist, die einen [`RadioButton`](/docs/components/radiobutton), einen `Div` mit Text und einen [`Button`](/docs/components/button) enthält.

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Beispiel: Gruppierung von Komponenten {#example-component-grouping}

Manchmal möchten Sie möglicherweise eine `Composite` verwenden, um verwandte Komponenten zu einer einzigen Einheit zusammenzufassen, auch wenn Wiederverwendbarkeit nicht das Hauptanliegen ist:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
