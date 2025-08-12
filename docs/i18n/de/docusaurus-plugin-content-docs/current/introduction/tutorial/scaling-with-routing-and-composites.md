---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
Dieser Schritt konzentriert sich auf die Implementierung von Routing, um die Skalierbarkeit und Organisation der App-Struktur zu verbessern. Um dies zu erreichen, wird die App aktualisiert, um mehrere Ansichten zu verwalten, die die Navigation zwischen verschiedenen Funktionen wie dem Bearbeiten und Erstellen von Kundeneinträgen ermöglichen. Es wird erläutert, wie Ansichten für diese Funktionen erstellt werden, unter Verwendung von Komponenten wie `Composite`, um modulare und wiederverwendbare Layouts zu erstellen.

Die im [vorangegangenen Schritt](./working-with-data) erstellte App wird ein Routing-Setup haben, das mehrere Ansichten unterstützt, sodass die Nutzer Kundendaten effektiver verwalten können, während eine saubere und skalierbare Codebasis beibehalten wird. Um die App auszuführen:

- Gehe in das Verzeichnis `3-scaling-with-routing-and-composites`
- Führe den Befehl `mvn jetty:run` aus

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routing {#routing}

[Routing](../../routing/overview) ist der Mechanismus, der es deiner App ermöglicht, die Navigation zwischen verschiedenen Ansichten oder Seiten zu verwalten. Anstatt alle Logik und das Verhalten an einem einzigen Ort zu behalten, ermöglicht Routing, dass du deine App in kleinere, fokussierte Komponenten aufteilst.

Im Kern verbindet Routing spezifische URLs mit den Ansichten oder Komponenten, die diese URLs verarbeiten. Wenn ein Benutzer mit deiner App interagiert—zum Beispiel, indem er auf einen Button klickt oder eine URL direkt in seinem Browser eingibt—wird die URL vom Router auf die entsprechende Ansicht aufgelöst, initialisiert und auf dem Bildschirm angezeigt. Dieser Ansatz erleichtert es, die Navigation zu verwalten und den Status der App aufrechtzuerhalten.

Dieser Schritt konzentriert sich auf die Änderung der `App`-Klasse, das Erstellen von Dateien für die Ansichten und das Konfigurieren von Routen, um eine reibungslose Navigation zwischen verschiedenen Teilen deiner App zu ermöglichen.

Anstatt die gesamte Logik innerhalb der Methode `run()` von `App` zu platzieren, werden Ansichten wie `DemoView` und `FormView` als separate Klassen implementiert. Dieser Ansatz entspricht eher den gängigen Java-Praktiken.

- **DemoView**: Zuständig für die Anzeige der Tabelle und die Navigation zu `FormView`.
- **FormView**: Verwalten des Hinzufügens und Bearbeitens von Kundendaten.

### Ändern der `App`-Klasse {#changing-the-app-class}

Um Routing in deiner App zu aktivieren, aktualisiere die `App`-Klasse mit der `@Routify`-Annotation. Dies weist webforJ an, das Routing zu aktivieren und die angegebenen Pakete nach routenfähigen Ansichten zu durchsuchen.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Gibt an, welche Pakete nach Ansichten durchsucht werden, die Routen definieren.
- **`debug`**: Aktiviert den Debugging-Modus für einfacheres Troubleshooting während der Entwicklung.

### Erstellen von Dateien für die Ansichten und Konfigurieren von Routen {#creating-files-for-the-views-and-configuring-routes}

Sobald das Routing aktiviert ist, werden separate Java-Dateien für jede Ansicht, die die App enthalten wird, erstellt, in diesem Fall `DemoView.java` und `FormView.java`. Einzigartige Routen werden diesen Ansichten unter Verwendung der `@Route`-Annotation zugewiesen. Dadurch wird sichergestellt, dass jede Ansicht über eine spezifische URL zugänglich ist.

Wenn die `@Route`-Annotation, die mit einer Klasse mit einem dieser Suffixe verknüpft ist, keinen Wert hat, weist webforJ der Klassenbezeichnung ohne das Suffix automatisch die Route zu. Beispielsweise wird `DemoView` standardmäßig der Route `/demo` zugeordnet. Da `DemoView` in diesem Fall die Standardroute sein soll, wird ihm eine Route zugewiesen.

Die Route `/` dient als Standardentrypoint für deine App. Die Zuweisung dieser Route zu einer Ansicht stellt sicher, dass dies die erste Seite ist, die Benutzer sehen, wenn sie auf die App zugreifen. In den meisten Fällen wird ein Dashboard oder eine Zusammenfassungsansicht der Route `/` zugewiesen.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView-Logik
}
```

:::info 
Weitere Informationen zu den verschiedenen Routentypen sind [hier](../../routing/defining-routes) verfügbar.
:::

Für die `FormView` verwendet die Route `customer/:id?` einen optionalen Parameter `id`, um den Modus der `FormView` zu bestimmen.

- **Hinzufügen-Modus**: Wenn `id` nicht bereitgestellt wird, wird die `FormView` mit einem leeren Formular zur Eingabe neuer Kundendaten initialisiert.
- **Bearbeiten-Modus**: Wenn `id` bereitgestellt wird, ruft die `FormView` die entsprechenden Kundendaten über `Service` ab und füllt das Formular voraus, sodass Änderungen am bestehenden Eintrag vorgenommen werden können.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Kundenformular")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView-Logik
}
```

:::info 
Weitere Informationen zu den verschiedenen Möglichkeiten zur Implementierung dieser Routenmuster sind [hier](../../routing/route-patterns) verfügbar.
:::

## Verwendung von `Composite`-Komponenten zur Anzeige von Seiten {#using-composite-components-to-display-pages}

Composite-Komponenten in webforJ, wie `Composite<Div>`, ermöglichen es dir, UI-Logik und -Struktur innerhalb eines wiederverwendbaren Containers zu kapseln. Durch die Erweiterung von `Composite` beschränkst du die Methoden und Daten, die dem Rest der App zugänglich sind, was zu sauberem Code und besserer Kapselung führt.

Beispielsweise erweitert `DemoView` `Composite<Div>` anstelle von direkt `Div` zu erweitern:

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## Verbinden der Routen {#connecting-the-routes}

Nachdem das Routing konfiguriert und die Ansichten eingerichtet wurden, verbinde die Ansichten und Daten mithilfe von Ereignis-Listenern und Servicemethoden. Der erste Schritt besteht darin, ein oder mehrere UI-Elemente hinzuzufügen, um von einer Ansicht zur anderen zu navigieren.

### Button-Navigation {#button-navigation}

Die `Button`-Komponente löst ein Navigationsereignis aus, um von einer Ansicht zur anderen zu wechseln, indem die Klasse `Router` verwendet wird. Zum Beispiel:

```java title="DemoView.java"
private Button add = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Die Router-Klasse verwendet die angegebene Klasse, um die Route aufzulösen und eine URL zum Navigieren zu erstellen. Alle Browser-Navigationen werden dann verwaltet, sodass die Verwaltung der Historie und die Initialisierung der Ansicht kein Anliegen sind.
Für weitere Details zur Navigation siehe den [Artikel zur Routennavigation](../../routing/route-navigation).
:::

### Tabellenbearbeitung {#table-editing}

Neben der Navigation über einen Button-Klick erlauben viele Apps auch die Navigation zu anderen Teilen einer App, wenn ein Element in einer `Table` doppelt angeklickt wird. Die folgenden Änderungen werden vorgenommen, um es den Benutzern zu ermöglichen, ein Element in der Tabelle doppelt anzuklicken, um zu einem Formular zu navigieren, das mit den Details des Elements vorausgefüllt ist.

Sobald die Details auf dem entsprechenden Bildschirm bearbeitet wurden, werden die Änderungen gespeichert und die `Table` aktualisiert, um die geänderten Daten des ausgewählten Elements anzuzeigen.

Um diese Navigation zu ermöglichen, werden Elementklicks in der Tabelle vom Listener `TableItemClickEvent<Customer>` behandelt. Das Ereignis enthält die `id` des angeklickten Kunden, die es an die `FormView` weitergibt, indem die Methode `navigate()` mit einer `ParametersBag` verwendet wird:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Handhabung der Initialisierung mit `onDidEnter` {#handling-initialization-with-ondidenter}

Die Methode `onDidEnter` in webforJ ist Teil des Routing-Lebenszyklus und wird ausgelöst, wenn eine Ansicht aktiv wird. 

Wenn der `Router` zu einer Ansicht navigiert, wird `onDidEnter` als Teil des Lebenszyklus ausgelöst, um:
- **Daten zu laden**: Daten zu initialisieren oder abzurufen, die für die Ansicht basierend auf den Routenparametern erforderlich sind.
- **Die Ansicht einzurichten**: UI-Elemente dynamisch basierend auf dem Kontext zu aktualisieren.
- **Auf Statusänderungen zu reagieren**: Aktionen durchzuführen, die von der aktiven Ansicht abhängen, z. B. das Zurücksetzen von Formularen oder das Hervorheben von Komponenten.

Die Methode `onDidEnter` in `FormView` prüft auf das Vorhandensein eines `id`-Parameters in der Route und passt das Verhalten des Formulars entsprechend an:

- **Bearbeiten-Modus**: Wenn eine `id` bereitgestellt wird, ruft die Methode die entsprechenden Kundendaten über `Service` ab und füllt die Formularfelder voraus. Der `Submit`-Button ist so konfiguriert, dass der bestehende Eintrag aktualisiert wird.
- **Hinzufügen-Modus**: Wenn keine `id` vorhanden ist, bleibt das Formular leer, und der `Submit`-Button ist so konfiguriert, dass ein neuer Kunde erstellt wird.

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```

### Datenübermittlung {#submitting-data}

Nachdem die Daten bearbeitet wurden, ist es notwendig, sie an den Service zu übermitteln, der das Repository verwaltet. Daher muss die 
`Service`-Klasse, die bereits im vorherigen Schritt dieses Tutorials eingerichtet wurde, nun mit zusätzlichen Methoden erweitert werden, die es den Nutzern ermöglichen, Kunden hinzuzufügen und zu bearbeiten.

Der folgende Snippet zeigt, wie dies erreicht werden kann:

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### Verwendung von `commit()` {#using-commit}

Die Methode `commit()` in der `Repository`-Klasse hält die Daten und die UI der App synchron. Sie bietet einen Mechanismus, um die im `Repository` gespeicherten Daten zu aktualisieren, sodass der neueste Stand in der App widergespiegelt wird.

Diese Methode kann auf zwei Arten verwendet werden:

1) **Aktualisierung aller Daten:**
  Der Aufruf von `commit()` ohne Argumente lädt alle Entitäten aus der zugrunde liegenden Datenquelle des Repositorys, wie einer Datenbank oder einer Serviceklasse, neu.

2) **Aktualisierung einer einzelnen Entität:**
  Der Aufruf von `commit(T entity)` lädt eine bestimmte Entität neu und stellt sicher, dass ihr Zustand mit den neuesten Änderungen der Datenquelle übereinstimmt.

Rufe `commit()` auf, wenn sich die Daten im `Repository` ändern, z. B. nach dem Hinzufügen oder Ändern von Entitäten in der Datenquelle.

```java
// Aktualisiere alle Kundendaten im Repository
customerRepository.commit();

// Aktualisiere eine einzelne Kundenentität
Customer updatedCustomer = ...; // Von einer externen Quelle aktualisiert
customerRepository.commit(updatedCustomer);
```

Mit diesen Änderungen wurden folgende Ziele erreicht:

  1. Routing implementiert und so eingerichtet, dass zukünftige Ansichten mit wenig Aufwand integriert werden können.
  2. UI-Implementierungen aus der `App` entfernt und in eine separate Ansicht verschoben.
  3. Eine zusätzliche Ansicht hinzugefügt, um die Daten zu manipulieren, die in der Kundentabelle angezeigt werden.

Mit der Modifikation der Kundendetails und dem Routing abgeschlossen, wird der nächste Schritt darauf abzielen, Datenbindung zu implementieren und sie zur Validierung zu verwenden.
