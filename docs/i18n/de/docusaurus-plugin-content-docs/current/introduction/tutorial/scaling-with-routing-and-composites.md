---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: fdfd4b4255de20775bb12bcd863630f7
---
Dieser Schritt konzentriert sich auf die Implementierung von Routing, um die Skalierbarkeit und Organisation der App-Struktur zu verbessern. Um dies zu erreichen, wird die App so aktualisiert, dass sie mehrere Ansichten verwaltet und die Navigation zwischen verschiedenen Funktionen wie dem Bearbeiten und Erstellen von Kundeneinträgen ermöglicht. Es wird beschrieben, wie Ansichten für diese Funktionen erstellt werden, wobei Komponenten wie `Composite` verwendet werden, um modulare und wiederverwendbare Layouts zu erstellen.

Die in [dem vorherigen Schritt](./working-with-data) erstellte App wird mit einem Routing-Setup versehen, das mehrere Ansichten unterstützt und es den Benutzern ermöglicht, Kundendaten effektiver zu verwalten und gleichzeitig eine saubere und skalierbare Codebasis zu erhalten. Um die App auszuführen:

- Gehe in das Verzeichnis `3-scaling-with-routing-and-composites`
- Führe den Befehl `mvn jetty:run` aus

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## Routing {#routing}

[Routing](../../routing/overview) ist der Mechanismus, der es deiner App ermöglicht, die Navigation zwischen verschiedenen Ansichten oder Seiten zu verwalten. Anstatt alle Logik und das Verhalten an einem einzigen Ort zu halten, ermöglicht Routing, deine App in kleinere, fokussierte Komponenten aufzuteilen.

Grundsätzlich verbindet Routing spezifische URLs mit den Ansichten oder Komponenten, die diese URLs behandeln. Wenn ein Benutzer mit deiner App interagiert – wie das Klicken auf einen Button oder das direkte Eingeben einer URL in seinem Browser – löst der Router die URL zur entsprechenden Ansicht auf, initialisiert sie und zeigt sie auf dem Bildschirm an. Dieser Ansatz erleichtert die Verwaltung der Navigation und die Aufrechterhaltung des App-Zustands.

Dieser Schritt konzentriert sich auf die Änderung der `App`-Klasse, das Erstellen von Dateien für die Ansichten und das Konfigurieren von Routen, um eine reibungslose Navigation zwischen verschiedenen Teilen deiner App zu ermöglichen.

Anstatt die gesamte Logik innerhalb der `run()`-Methode der `App` zu platzieren, werden ansichten wie `DemoView` und `FormView` als separate Klassen implementiert. Dieser Ansatz entspricht eher den Standardpraktiken in Java.

- **DemoView**: Verantwortlich für die Anzeige der Tabelle und die Navigation zu `FormView`.
- **FormView**: Verwalten des Hinzufügens und Bearbeitens von Kundendaten.

### Ändern der `App`-Klasse {#changing-the-app-class}

Um Routing in deiner App zu ermöglichen, aktualisiere die `App`-Klasse mit der `@Routify`-Annotation. Dies sagt webforJ, dass Routing aktiviert und bestimmte Pakete nach routenfähigen Ansichten durchsucht werden.

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**: Gibt an, welche Pakete nach Ansichten durchsucht werden, die Routen definieren.
- **`debug`**: Aktiviert den Debugging-Modus, um die Fehlersuche während der Entwicklung zu erleichtern.

### Erstellen von Dateien für die Ansichten und Konfigurieren von Routen {#creating-files-for-the-views-and-configuring-routes}

Sobald das Routing aktiviert ist, werden separate Java-Dateien für jede Ansicht erstellt, die die App enthalten wird, in diesem Fall `DemoView.java` und `FormView.java`. Einzigartige Routen werden diesen Ansichten mit der Annotation `@Route` zugewiesen. Dies stellt sicher, dass jede Ansicht über eine spezifische URL zugänglich ist.

Wenn die Annotation `@Route` einer Klasse ohne Wert zugeordnet ist, weist webforJ der Klasse ohne den Suffix automatisch den Namen der Route zu. Zum Beispiel wird `DemoView` standardmäßig der Route `/demo` zugeordnet. Da `DemoView` in diesem Fall als Standardroute vorgesehen ist, weist du ihr eine Route zu.

Die Route `/` dient als standardmäßiger Einstiegspunkt für deine App. Diese Route einer Ansicht zuzuweisen, stellt sicher, dass dies die erste Seite ist, die Benutzer beim Zugriff auf die App sehen. In den meisten Fällen wird ein Dashboard oder eine Zusammenfassungsansicht der Route `/` zugeordnet.

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView-Logik
}
```

:::info
Weitere Informationen zu den verschiedenen Routenarten findest du [hier](../../routing/defining-routes).
:::

Für die `FormView` verwendet die Route `customer/:id?` einen optionalen Parameter `id`, um den Modus der `FormView` zu bestimmen.

- **Add-Modus**: Wenn `id` nicht angegeben ist, wird `FormView` mit einem leeren Formular zum Hinzufügen neuer Kundendaten initialisiert.
- **Bearbeitungsmodus**: Wenn `id` angegeben ist, ruft `FormView` die entsprechenden Kundendaten über `Service` ab und füllt das Formular vor, sodass Änderungen an dem vorhandenen Eintrag vorgenommen werden können.

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Kundenformular")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView-Logik
}
```

:::info
Weitere Informationen zu den verschiedenen Möglichkeiten zur Implementierung dieser Routenmuster findest du [hier](../../routing/route-patterns).
:::

## Verwendung von `Composite`-Komponenten zur Anzeige von Seiten {#using-composite-components-to-display-pages}

Composite-Komponenten in webforJ, wie `Composite<Div>`, ermöglichen es dir, UI-Logik und -Struktur in einem wiederverwendbaren Container zu kapseln. Durch das Erweitern von `Composite` beschränkst du die Methoden und Daten, die für den Rest der App zugänglich sind, was zu cleanerem Code und besserer Kapselung führt.

Zum Beispiel erweitert `DemoView` `Composite<Div>` anstelle von `Div` direkt:

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

Nachdem das Routing konfiguriert und die Ansichten eingerichtet wurden, verbinde die Ansichten und Daten mithilfe von Ereignis-Listenern und Service-Methoden. Der erste Schritt besteht darin, ein oder mehrere UI-Elemente hinzuzufügen, um von einer Ansicht zur anderen zu navigieren.

### Button-Navigation {#button-navigation}

Die `Button`-Komponente löst ein Navigationsereignis aus, um von einer Ansicht zur anderen zu wechseln, mithilfe der `Router`-Klasse. Zum Beispiel:

```java title="DemoView.java"
private Button add = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Die Router-Klasse verwendet die angegebene Klasse, um die Route zu lösen und eine URL zu erstellen, zu der navigiert werden kann. Alle Browser-Navigationen werden dann so behandelt, dass das Verwalten der Historie und die Initialisierung der Ansichten kein Thema sind.
Für weitere Details zur Navigation siehe den [Artikel zur Routen-Navigation](../../routing/route-navigation).
:::

### Tabellenbearbeitung {#table-editing}

Zusätzlich zur Navigation durch einen Button-Klick ermöglichen viele Apps auch die Navigation zu anderen Teilen einer App, wenn in einer `Table` doppelt geklickt wird. Die folgenden Änderungen werden vorgenommen, um den Benutzern das Doppelklicken auf einen Eintrag in der Tabelle zu ermöglichen, um zu einem Formular zu navigieren, das mit den Details des Eintrags vorbefüllt ist.

Sobald die Details auf dem entsprechenden Bildschirm bearbeitet wurden, werden die Änderungen gespeichert, und die `Table` wird aktualisiert, um die geänderten Daten des ausgewählten Eintrags anzuzeigen.

Um diese Navigation zu erleichtern, werden Item-Klicks in der Tabelle von dem Listener `TableItemClickEvent<Customer>` verarbeitet. Das Ereignis enthält die `id` des geklickten Kunden, die es an die `FormView` über die `navigate()`-Methode mit einem `ParametersBag` weitergibt:

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### Handhabung der Initialisierung mit `onDidEnter` {#handling-initialization-with-ondidenter}

Die Methode `onDidEnter` in webforJ ist Teil des Routing-Lebenszyklus und wird ausgelöst, wenn eine Ansicht aktiv wird.

Wenn der `Router` zu einer Ansicht navigiert, wird `onDidEnter` als Teil des Lebenszyklus wie folgt ausgelöst:
- **Daten laden**: Initialisiere oder hole die für die Ansicht benötigten Daten basierend auf den Routenparametern.
- **Die Ansicht einrichten**: Aktualisiere UI-Elemente dynamisch basierend auf dem Kontext.
- **Auf Zustandsänderungen reagieren**: Führe Aktionen aus, die davon abhängen, dass die Ansicht aktiv ist, z. B. Formulare zurücksetzen oder Komponenten hervorheben.

Die Methode `onDidEnter` in `FormView` prüft das Vorhandensein eines `id`-Parameters in der Route und passt das Verhalten des Formulars entsprechend an:

- **Bearbeitungsmodus**: Wenn eine `id` angegeben ist, ruft die Methode die entsprechenden Kundendaten über `Service` ab und füllt die Formularfelder vor. Die Schaltfläche `Absenden` ist so konfiguriert, dass sie den bestehenden Eintrag aktualisiert.
- **Add-Modus**: Wenn keine `id` vorhanden ist, bleibt das Formular leer, und die Schaltfläche `Absenden` ist so konfiguriert, dass sie einen neuen Kunden erstellt.

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

### Daten übermitteln {#submitting-data}

Nachdem die Daten bearbeitet wurden, ist es notwendig, sie an den Service zu übermitteln, der das Repository verwaltet. Daher muss die `Service`-Klasse, die bereits im vorherigen Schritt dieses Tutorials eingerichtet wurde, mit zusätzlichen Methoden erweitert werden, die es den Benutzern ermöglichen, Kunden hinzuzufügen und zu bearbeiten.

Der folgende Code zeigt, wie dies erreicht werden kann:

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

Die Methode `commit()` in der `Repository`-Klasse hält die Daten der App und die UI synchron. Sie bietet einen Mechanismus zum Aktualisieren der Daten, die im `Repository` gespeichert sind, und stellt sicher, dass der neueste Zustand in der App angezeigt wird.

Diese Methode kann auf zwei Arten verwendet werden:

1) **Aktualisieren aller Daten**:
  Das Aufrufen von `commit()` ohne Argumente lädt alle Entitäten aus der zugrunde liegenden Datenquelle des Repositories, z. B. aus einer Datenbank oder einer Service-Klasse, neu.

2) **Aktualisieren einer einzelnen Entität**:
  Das Aufrufen von `commit(T entity)` lädt eine bestimmte Entität neu und stellt sicher, dass ihr Zustand den neuesten Änderungen in der Datenquelle entspricht.

Rufe `commit()` auf, wenn sich Daten im `Repository` ändern, z. B. nach dem Hinzufügen oder Ändern von Entitäten in der Datenquelle.

```java
// Alle Kundendaten im Repository aktualisieren
customerRepository.commit();

// Eine einzelne Kundenentität aktualisieren
Customer updatedCustomer = ...; // Aus einer externen Quelle aktualisiert
customerRepository.commit(updatedCustomer);
```

Mit diesen Änderungen wurden die folgenden Ziele erreicht:

1. Routing implementiert und so konfiguriert, dass zukünftige Ansichten mit wenig Aufwand integriert werden können.
2. UI-Implementierungen aus der `App` entfernt und in eine separate Ansicht verschoben.
3. Eine zusätzliche Ansicht hinzugefügt, um die Daten zu manipulieren, die in der Kundentabelle angezeigt werden.

Mit der Modifikation der Kundendetails und dem Routing abgeschlossen, wird der nächste Schritt die Implementierung des Datenbindens und der Nutzung dessen zur Erleichterung der Validierung sein.
