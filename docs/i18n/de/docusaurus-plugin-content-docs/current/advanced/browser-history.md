---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

Die `BrowserHistory`-Klasse in webforJ bietet eine hochgradig abstrahierte API zur Interaktion mit dem Verlauf des Browsers. Der Browserverlauf ermöglicht es Webanwendungen, die Navigation des Benutzers innerhalb der App zu verfolgen. Durch die Nutzung des Browserverlaufs können Entwickler Funktionen wie Rück- und Vorausnavigation, Zustandsbewahrung und dynamisches URL-Management aktivieren, ohne vollständige Seitenaktualisierungen erfordern zu müssen.

## Navigieren im Verlauf {#navigating-through-history}

Das Verwalten des Browserverlaufs ist eine Kernfunktion der meisten Web-Apps. Die `BrowserHistory`-API ermöglicht es Entwicklern, zu steuern, wie Benutzer durch die Seiten und Zustände ihrer Anwendungen navigieren, sodass das Standardverhalten des Browsers nachgeahmt oder verändert werden kann.

### Initialisieren oder Abrufen einer Verlauf-Instanz {#initializing-or-retrieving-a-history-instance}

Um die `BrowserHistory`-API zu verwenden, haben Sie zwei Hauptoptionen zum Abrufen einer Verlauf-Instanz:

1) **Erstellen eines neuen Verlauf-Objekts**: Wenn Sie unabhängig von einem Routing-Kontext arbeiten, können Sie direkt eine neue Instanz der `BrowserHistory`-Klasse erstellen.

```java
BrowserHistory history = new BrowserHistory();
```
Dieser Ansatz ist geeignet für Szenarien, in denen Sie den Verlauf explizit außerhalb eines Routing-Frameworks verwalten müssen.

2) **Abrufen des Verlaufs vom `Router`**: Wenn Ihre App die [Routing-Lösung von webforJ](../routing/overview) verwendet, erstellt und verwaltet die `Router`-Komponente eine gemeinsame Instanz von `BrowserHistory`. Sie können auf diese Instanz direkt vom Router zugreifen, um einen konsistenten Ansatz für das Verwalten des Verlaufs in Ihrer App sicherzustellen.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Diese Methode wird empfohlen, wenn Ihre App auf Routing angewiesen ist, da sie die Konsistenz im Verlauf-Management über alle Ansichten und Navigationen hinweg aufrechterhält.

### Verlauf verwalten {#managing-history}
Die folgenden Methoden können für die Verlauf-Navigation in einer webforJ-App verwendet werden:

- `back()`: Bewegt den Browserverlauf um einen Schritt zurück und simuliert das Drücken der Zurück-Schaltfläche im Browser. Wenn es keine weiteren Einträge im Verlauf gibt, bleibt die aktuelle Seite bestehen.

  ```java
  history.back();
  ```

- `forward()`: Bewegt den Browserverlauf um einen Schritt vorwärts und simuliert das Drücken der Vorwärts-Schaltfläche im Browser. Dies funktioniert nur, wenn es einen Eintrag im Verlauf vorwärts gibt.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigiert zu einem bestimmten Punkt im Verlauf basierend auf einem Index. Eine positive Zahl bewegt vorwärts, eine negative Zahl zurück und null lädt die aktuelle Seite neu. Diese Methode bietet mehr feinere Kontrolle im Vergleich zu `back()` und `forward()`.

  ```java
  history.go(-2); // Bewegt sich um zwei Einträge im Verlauf zurück
  ```

- `size()`: Gibt die Gesamtzahl der Einträge im Sitzungsverlauf einschließlich der derzeit geladenen Seite zurück. Dies kann nützlich sein, um den Navigationspfad des Benutzers zu verstehen oder benutzerdefinierte Navigationselemente zu implementieren.

  ```java
  int historySize = history.size();
  System.out.println("Verlaufslänge: " + historySize);
  ```

- `getLocation()`: Gibt den aktuellen URL-Pfad relativ zur ursprünglichen URL der App zurück. Diese Methode hilft Entwicklern, den aktuellen Pfad abzurufen, was nützlich ist, um URL-basiertes Routing in einseitigen Anwendungen zu verwalten.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
  ```

Effizientes Navigieren zu verstehen, ist das Fundament für den Aufbau dynamischer Anwendungen. Sobald Sie die Grundlagen der Navigation beherrschen, ist es wichtig zu wissen, wie Sie auf die mit diesen Navigationsevents verbundenen URLs zugreifen und sie aktualisieren.

## Zugriff und Aktualisierung der URL {#accessing-and-updating-url}

Ein wesentlicher Aspekt der Navigation und des Managements des Browserverlaufs besteht darin, den aktuellen URL-Pfad effizient abrufen und aktualisieren zu können. Dies ist in modernen Web-Apps von entscheidender Bedeutung, in denen URL-Änderungen verschiedenen Ansichten oder Zuständen innerhalb der App entsprechen. Die `BrowserHistory`-API bietet eine einfache Möglichkeit, den aktuellen Pfad relativ zur Wurzel der App abzurufen und zu manipulieren.

:::tip webforJ `Router`
Siehe den [`Router`-Artikel](../routing/overview), um mehr über umfassendes URL- und Routemanagement zu erfahren
:::

`getLocation()` ruft den aktuellen URL-Pfad relativ zur ursprünglichen URL der App ab. Die Methode `getLocation()` gibt ein `Optional<Location>` zurück, sodass Sie den Pfadteil der URL ohne die Domain erhalten können.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
```

## Zustand verwalten {#managing-state}

`BrowserHistory` ermöglicht es Ihnen, benutzerdefinierte Zustandsinformationen mit den Methoden `pushState()` und `replaceState()` zu speichern und zu verwalten. Durch die Verwendung von Methoden zur Zustandsverwaltung können Sie steuern, welche Informationen als Teil des Verlaufseintrags gespeichert werden, was zur Aufrechterhaltung einer konsistenten Benutzererfahrung beim Navigieren innerhalb Ihrer App beiträgt. Die folgenden Methoden können verwendet werden, um den Zustand in Ihrer webforJ-App zu verwalten.

- `pushState(Object state, Location location)`: Fügt dem Verlauf einen neuen Eintrag hinzu. Akzeptiert ein Zustandsobjekt und ein `Location`-Objekt, das die neue URL darstellt.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Ersetzt den aktuellen Verlaufseintrag. Dies erstellt keinen neuen Eintrag im Verlauf wie die oben genannte Methode.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Ruft das Zustandsobjekt ab, das mit dem aktuellen Verlaufseintrag verknüpft ist. Diese Methode gibt ein Optional zurück, das das Zustandsobjekt enthält, das in die angegebene Klasse deserialisiert wird.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Aktuelle Seite: " + state.getViewName()));
```

### Auf Zustandänderungen hören {#listening-for-state-changes}
Die `BrowserHistory`-Klasse ermöglicht es Ihnen, Ereignislistener zu registrieren, die auf Änderungen im Verlaufzustand reagieren.

Der `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registriert einen Listener, der ausgelöst wird, wenn sich der Zustand ändert, z. B. wenn der Benutzer auf die Zurück- oder Vorwärts-Schaltfläche des Browsers klickt. Diese Methode richtet einen Listener für das `popstate`-Ereignis des Browsers ein, sodass Ihre App auf Benutzeraktionen oder programmatisch ausgelöste Zustandsänderungen reagieren kann.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Verlaufzustand geändert zu: " + event.getLocation().getFullURI());
});
```

Einen Zustand effektiv zu verwalten, ermöglicht es Ihnen, Apps zu erstellen, die dynamisch auf Benutzeraktionen reagieren. Benutzer können durch Ihre App navigieren, ohne den Kontext zu verlieren, was zu einer reibungsloseren und intuitiveren Erfahrung führt. Darüber hinaus ermöglicht das Speichern des Zustands erweiterte Funktionen wie das Wiederherstellen von Sichtpositionen, das Beibehalten von Filter- oder Sortiereinstellungen und das Unterstützen von Deep Linking – all dies trägt zu einer ansprechenderen und verlässlicheren App bei.
