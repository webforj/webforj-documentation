---
title: Browser History
sidebar_position: 30
_i18n_hash: e0426f58e099d38fa58fa2b722ec0605
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

Die `BrowserHistory`-Klasse in webforJ bietet eine hochgradige API, um mit der Historie des Browsers zu interagieren. Die Browser-Historie ermöglicht es Webanwendungen, die Navigation des Benutzers innerhalb der App zu verfolgen. Durch die Verwendung der Browser-Historie können Entwickler Funktionen wie Zurück- und Vorwärtsnavigation, Zustandserhaltung und dynamisches URL-Management aktivieren, ohne vollständige Seitenneuladevorgänge zu benötigen.

## Navigieren durch die Historie {#navigating-through-history}

Die Verwaltung der Browser-Historie ist eine Kernfunktion der meisten Webanwendungen. Die `BrowserHistory`-API ermöglicht es Entwicklern, zu steuern, wie Benutzer durch die Seiten und Zustände ihrer Anwendungen navigieren, wobei das Standardverhalten des Browsers nachgeahmt oder geändert werden kann.

### Initialisieren oder Abrufen einer Historieninstanz {#initializing-or-retrieving-a-history-instance}

Um die `BrowserHistory`-API zu verwenden, haben Sie zwei Hauptoptionen, um eine Historieninstanz zu erhalten:

1) **Erstellen eines neuen Geschichtsobjekts**: Wenn Sie unabhängig von einem Routing-Kontext arbeiten, können Sie direkt eine neue Instanz der `BrowserHistory`-Klasse erstellen.

```java
BrowserHistory history = new BrowserHistory();
```
Dieser Ansatz ist geeignet für Szenarien, in denen Sie die Historie explizit außerhalb eines Routing-Frameworks verwalten müssen.

2) **Abrufen der Historie vom `Router`**: Wenn Ihre App die Routing-Lösung von webforJ verwendet, erstellt und verwaltet die Komponente `Router` eine gemeinsame `BrowserHistory`-Instanz. Sie können auf diese Instanz direkt vom Router aus zugreifen, um einen konsistenten Ansatz zur Verwaltung der Historie in Ihrer App zu gewährleisten.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Diese Methode wird empfohlen, wenn Ihre App auf Routing angewiesen ist, da sie die Konsistenz in der Verwaltung der Historie über alle Ansichten und Navigationsaktionen aufrechterhält.

### Verwaltung der Historie {#managing-history}
Die folgenden Methoden können für die Historiennavigation in einer webforJ-App verwendet werden:

- `back()`: Bewegt die Browserhistorie um einen Schritt zurück und simuliert, dass der Benutzer die Zurück-Taste in seinem Browser drückt. Wenn keine weiteren Einträge im Historienstapel vorhanden sind, bleibt er auf der aktuellen Seite.

  ```java
  history.back();
  ```

- `forward()`: Bewegt die Browserhistorie um einen Schritt vorwärts und simuliert, dass der Benutzer die Vorwärts-Taste in seinem Browser drückt. Dies funktioniert nur, wenn ein Eintrag im Historienstapel vorhanden ist.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigiert zu einem bestimmten Punkt im Historienstapel basierend auf einem Index. Eine positive Zahl bewegt sich vorwärts, eine negative Zahl bewegt sich rückwärts, und null lädt die aktuelle Seite neu. Diese Methode bietet eine granularere Kontrolle im Vergleich zu `back()` und `forward()`.

  ```java
  history.go(-2); // Bewegt sich um zwei Einträge im Historienstapel zurück
  ```

- `size()`: Ruft die Gesamtzahl der Einträge im Sitzungshistorienstapel ab, einschließlich der aktuell geladenen Seite. Dies kann nützlich sein, um den Navigationspfad des Benutzers zu verstehen oder benutzerdefinierte Navigationskontrollen zu implementieren.

  ```java
  int historySize = history.size();
  System.out.println("Historienlänge: " + historySize);
  ```

- `getLocation()`: Gibt den aktuellen URL-Pfad relativ zur Herkunft der App zurück. Diese Methode hilft Entwicklern, den aktuellen Pfad abzurufen, was beim Verwalten der url-basierten Navigation in Single-Page-Anwendungen nützlich ist.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
  ```

Zu verstehen, wie man effizient navigiert, ist das Fundament für den Aufbau dynamischer Anwendungen. Sobald Sie die Grundlagen der Navigation beherrschen, ist es wichtig zu wissen, wie Sie auf die URLs zugreifen und diese aktualisieren können, die mit diesen Navigationsevents verbunden sind.

## Zugriff auf und Aktualisierung der URL {#accessing-and-updating-url}

Ein wesentlicher Aspekt der Navigation und Verwaltung der Browserhistorie ist die Fähigkeit, den aktuellen URL-Pfad effizient abzurufen und zu aktualisieren. Dies ist in modernen Web-Apps wichtig, in denen URL-Änderungen verschiedenen Ansichten oder Zuständen innerhalb der App entsprechen. Die `BrowserHistory`-API bietet eine einfache Möglichkeit, den aktuellen Pfad relativ zum Stamm der App abzurufen und zu manipulieren.

:::tip webforJ `Router`
Siehe den [Artikel `Router`](../routing/overview), um mehr über umfassendes URL- und Routenmanagement zu erfahren
:::

`getLocation()` ruft den aktuellen URL-Pfad relativ zur Herkunft der App ab. Die Methode `getLocation()` gibt ein `Optional<Location>` zurück, das es Ihnen ermöglicht, den Pfadteil der URL ohne die Domain zu erhalten.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
```

## Verwaltung des Zustands {#managing-state}

`BrowserHistory` ermöglicht es Ihnen, benutzerdefinierte Zustandsinformationen mit den Methoden `pushState()` und `replaceState()` zu speichern und zu verwalten. Durch die Verwendung von Zustandsverwaltungs-Methoden können Sie steuern, welche Informationen als Teil des Historieneintrags gespeichert werden, was hilft, eine konsistente Benutzererfahrung beim Navigieren zurück und vorwärts innerhalb Ihrer App aufrechtzuerhalten. Die folgenden Methoden können verwendet werden, um den Zustand in Ihrer webforJ-App zu verwalten.

- `pushState(Object state, Location location)`: Fügt einen neuen Eintrag zum Historienstapel hinzu. Akzeptiert ein Zustandsobjekt und ein `Location`-Objekt, das die neue URL darstellt.

```java
Location location = new Location("/neue-seite");
history.pushState(myStateObject, location);
```


- `replaceState(Object state, Location location)`: Ersetzt den aktuellen Historieneintrag. Dies erstellt keinen neuen Eintrag im Stapel wie die <!-- valeoff -->obige<!-- valeon --> Methode.

```java
Location location = new Location("/aktualisierte-seite");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Ruft das Zustandsobjekt ab, das mit dem aktuellen Historieneintrag verknüpft ist. Diese Methode gibt ein Optional zurück, das das Zustandsobjekt enthält, das in die angegebene Klasse deserialisiert wird.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Aktuelle Seite: " + state.getViewName()));
```

### Zuhören auf Zustandsänderungen {#listening-for-state-changes}
Die `BrowserHistory`-Klasse bietet die Möglichkeit, Ereignislistener zu registrieren, die auf Änderungen im Zustand der Historie reagieren.

Die `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registriert einen Listener, der ausgelöst wird, wenn sich der Zustand ändert, z. B. wenn der Benutzer die Zurück- oder Vorwärtstaste des Browsers klickt. Diese Methode richtet einen Listener für das `popstate`-Ereignis des Browsers ein, der es Ihrer App ermöglicht, auf Benutzeraktionen oder programmatisch ausgelöste Zustandsänderungen zu reagieren.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Historienzustand geändert zu: " + event.getLocation().getFullURI());
});
```

Die effektive Verwaltung des Zustands ermöglicht es Ihnen, Apps zu erstellen, die dynamisch auf Benutzeraktionen reagieren. Benutzer können durch Ihre App navigieren, ohne den Kontext zu verlieren, was zu einer reibungsloseren und intuitiveren Erfahrung führt. Darüber hinaus ermöglicht das Speichern des Zustands erweiterte Funktionen wie das Wiederherstellen von Ansichtpositionen, das Beibehalten von Filter- oder Sortiereinstellungen und die Unterstützung von Deep Linking – alles Aspekte, die zu einer fesselnden und zuverlässigen App beitragen.
