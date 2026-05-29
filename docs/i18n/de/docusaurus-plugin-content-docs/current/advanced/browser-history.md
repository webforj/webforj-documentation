---
title: Browser History
sidebar_position: 30
_i18n_hash: 918006c1e505baa4bbffbfb32eb3d9d7
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

Die `BrowserHistory`-Klasse in webforJ bietet eine hochlevelige API, um mit dem Verlauf des Browsers zu interagieren. Der Browserverlauf ermöglicht es Webanwendungen, die Navigation des Benutzers innerhalb der App nachverfolgen. Durch die Verwendung des Browserverlaufs können Entwickler Funktionen wie Zurück- und Vorwärtsnavigation, Zustandserhaltung und dynamisches URL-Management aktivieren, ohne vollständige Seitenaktualisierungen erforderlich zu machen.

## Navigation durch den Verlauf {#navigating-through-history}

Das Verwalten des Browserverlaufs ist eine Kernfunktion der meisten Web-Apps. Die `BrowserHistory`-API ermöglicht es Entwicklern, zu steuern, wie Benutzer durch die Seiten und Zustände ihrer Anwendungen navigieren, und dabei das Standardverhalten des Browsers nachzuahmen oder zu ändern.

### Initialisieren oder Abrufen einer Verlauf-Instanz {#initializing-or-retrieving-a-history-instance}

Um die `BrowserHistory`-API zu verwenden, haben Sie zwei Hauptoptionen, um eine Verlauf-Instanz zu erhalten:

1) **Erstellen eines neuen Verlauf-Objekts**: Wenn Sie unabhängig von einem Routing-Kontext arbeiten, können Sie direkt eine neue Instanz der `BrowserHistory`-Klasse erstellen.

```java
BrowserHistory history = new BrowserHistory();
```
Dieser Ansatz ist geeignet für Szenarien, in denen Sie den Verlauf explizit außerhalb eines Routing-Frameworks verwalten müssen.

2) **Abrufen des Verlaufs vom `Router`**: Wenn Ihre App die [Routing-Lösung](../routing/overview) von webforJ verwendet, erstellt und verwaltet die `Router`-Komponente eine gemeinsame `BrowserHistory`-Instanz. Sie können auf diese Instanz direkt vom Router zugreifen, um einen konsistenten Verlauf-Management-Ansatz in Ihrer App zu gewährleisten.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Diese Methode wird empfohlen, wenn Ihre App auf Routing angewiesen ist, da sie Konsistenz im Verlauf-Management über alle Ansichten und Navigationsaktionen hinweg aufrechterhält.

### Verlauf verwalten {#managing-history}
Die folgenden Methoden können für die Verlauf-Navigation in einer webforJ-App verwendet werden:

- `back()`: Bewegt den Browserverlauf um einen Schritt zurück und simuliert, dass ein Benutzer die Zurück-Taste in seinem Browser drückt. Wenn keine weiteren Einträge im Verlauf-Stack vorhanden sind, bleibt die aktuelle Seite erhalten.

  ```java
  history.back();
  ```

- `forward()`: Bewegt den Browserverlauf um einen Schritt vorwärts und simuliert, dass ein Benutzer die Vorwärts-Taste in seinem Browser drückt. Dies funktioniert nur, wenn es einen Eintrag im Verlauf-Stack gibt.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigiert zu einem bestimmten Punkt im Verlauf-Stack basierend auf einem Index. Eine positive Zahl bewegt vorwärts, eine negative Zahl rückwärts, und null lädt die aktuelle Seite neu. Diese Methode bietet eine granularere Kontrolle im Vergleich zu `back()` und `forward()`.

  ```java
  history.go(-2); // Bewegt sich um zwei Einträge im Verlauf-Stack zurück
  ```

- `size()`: Ruft die gesamte Anzahl der Einträge im Sitzungshistorien-Stack ab, einschließlich der aktuell geladenen Seite. Dies kann nützlich sein, um den Navigationspfad des Benutzers zu verstehen oder um benutzerdefinierte Navigationskontrollen zu implementieren.

  ```java
  int historySize = history.size();
  System.out.println("Verlauf Länge: " + historySize);
  ```

- `getLocation()`: Gibt den aktuellen URL-Pfad relativ zur Herkunft der App zurück. Diese Methode hilft Entwicklern, den aktuellen Pfad abzurufen, was nützlich ist, um URL-basiertes Routing in Single-Page-Anwendungen zu verwalten.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
  ```

Das Verständnis, wie man effizient navigiert, ist das Fundament für den Aufbau dynamischer Anwendungen. Sobald Sie die Grundlagen der Navigation verstanden haben, ist es wichtig zu wissen, wie man auf die URLs zugreift und sie aktualisiert, die mit diesen Navigationsereignissen verbunden sind.

## Zugriff und Aktualisierung der URL {#accessing-and-updating-url}

Ein Kernaspekt der Navigation und des Managements des Browser Verlaufs besteht darin, effizient auf den aktuellen URL-Pfad zuzugreifen und ihn zu aktualisieren. Dies ist in modernen Web-Apps unerlässlich, in denen URL-Änderungen verschiedenen Ansichten oder Zuständen innerhalb der App entsprechen. Die `BrowserHistory`-API bietet eine einfache Möglichkeit, den aktuellen Pfad relativ zur Wurzel der App abzurufen und zu manipulieren.

:::tip webforJ `Router`
Siehe den [`Router`-Artikel](../routing/overview), um mehr über umfassendes URL- und Routenmanagement zu erfahren
:::

`getLocation()` ruft den aktuellen URL-Pfad relativ zur Herkunft der App ab. Die `getLocation()`-Methode gibt ein `Optional<Location>` zurück, sodass Sie den Pfadteil der URL ohne die Domain abrufen können.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
```

## Zustand verwalten {#managing-state}

`BrowserHistory` ermöglicht es Ihnen, benutzerdefinierte Zustandsinformationen mithilfe der `pushState()`- und `replaceState()`-Methoden zu speichern und zu verwalten. Durch die Verwendung von Zustandsverwaltungs-Methoden können Sie steuern, welche Informationen als Teil des Verlaufseintrags gespeichert werden, was hilft, ein konsistentes Benutzererlebnis beim Navigieren zurück und vorwärts innerhalb Ihrer App aufrechtzuerhalten. Die folgenden Methoden können verwendet werden, um den Zustand in Ihrer webforJ-App zu verwalten.

- `pushState(Object state, Location location)`: Fügt einen neuen Eintrag zum Verlauf-Stack hinzu. Akzeptiert ein Zustandsobjekt und ein `Location`-Objekt, das die neue URL darstellt.

```java
Location location = new Location("/neue-seite");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Ersetzt den aktuellen Verlaufseintrag. Dies erstellt keinen neuen Eintrag im Stack, wie die oben genannte Methode.

```java
Location location = new Location("/aktualisierte-seite");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Ruft das Zustandsobjekt ab, das mit dem aktuellen Verlaufseintrag verknüpft ist. Diese Methode gibt ein Optional zurück, das das Zustandsobjekt enthält, das in die angegebene Klasse deserialisiert wird.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Aktuelle Seite: " + state.getViewName()));
```

### Auf Zustandsänderungen hören {#listening-for-state-changes}
Die `BrowserHistory`-Klasse bietet die Möglichkeit, Ereignis-Listener zu registrieren, die auf Änderungen des Verlaufszustands reagieren.

Die `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registriert einen Listener, der ausgelöst wird, wenn sich der Zustand ändert, zum Beispiel wenn der Benutzer die Zurück- oder Vorwärts-Buttons des Browsers klickt. Diese Methode richtet einen Listener für das `popstate`-Ereignis des Browsers ein, sodass Ihre App auf Benutzeraktionen oder programmgesteuert ausgelöste Zustandsänderungen reagieren kann.

```java
history.addHistoryStateChangeListener(event -> {
  System.out.println("Verlaufzustand geändert zu: " + event.getLocation().getFullURI());
});
```

Das effektive Verwalten von Zuständen ermöglicht es Ihnen, Apps zu erstellen, die dynamisch auf Benutzeraktionen reagieren. Benutzer können in Ihrer App navigieren, ohne den Kontext zu verlieren, was zu einem reibungsloseren und intuitiveren Erlebnis führt. Darüber hinaus ermöglicht das Speichern von Zuständen fortgeschrittene Funktionen wie das Wiederherstellen von Ansichtspositionen, das Beibehalten von Filter- oder Sortiereinstellungen und das Unterstützen von Deep Linking, was alles zu einer ansprechenderen und zuverlässigeren App beiträgt.
