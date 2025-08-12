---
title: Browser History
sidebar_position: 20
_i18n_hash: 9b05a2e65e60a737d341a6bc37e9546f
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

Die `BrowserHistory`-Klasse in webforJ bietet eine hochgradig API, um mit der Verlauf des Browsers zu interagieren. Der Browserverlauf ermöglicht es Webanwendungen, die Navigation des Benutzers innerhalb der App nachverfolgen. Durch die Nutzung des Browserverlaufs können Entwickler Funktionen wie Vor- und Zurücknavigation, Zustandserhaltung und dynamische URL-Verwaltung aktivieren, ohne vollständige Seitenaktualisierungen erforderlich zu machen.

## Navigieren durch den Verlauf {#navigating-through-history}

Das Verwalten des Browserverlaufs ist eine Kernfunktion für die meisten Webanwendungen. Die `BrowserHistory`-API ermöglicht es Entwicklern, zu steuern, wie Benutzer durch die Seiten und Zustände ihrer Anwendungen navigieren, wodurch das Standardverhalten des Browsers nachgeahmt oder verändert wird.

### Initialisieren oder Abrufen einer Verlauf-Instanz {#initializing-or-retrieving-a-history-instance}

Um die `BrowserHistory`-API zu verwenden, haben Sie zwei Hauptoptionen zum Erhalten einer Verlauf-Instanz:

1) **Erstellen eines neuen Verlauf-Objekts**: Wenn Sie unabhängig von einem Routing-Kontext arbeiten, können Sie direkt eine neue Instanz der `BrowserHistory`-Klasse erstellen.

```java
BrowserHistory history = new BrowserHistory();
```
Dieses Vorgehen ist geeignet für Szenarien, in denen Sie den Verlauf explizit außerhalb eines Routing-Frameworks verwalten müssen.

2) **Abrufen des Verlaufs von dem `Router`**: Wenn Ihre App die [Routing-Lösung](../routing/overview) von webforJ verwendet, erstellt und verwaltet die `Router`-Komponente eine gemeinsame `BrowserHistory`-Instanz. Sie können diese Instanz direkt vom Router aus abrufen, um eine konsistente Verlauf-Verwaltung in Ihrer App sicherzustellen.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Diese Methode wird empfohlen, wenn Ihre App auf Routing angewiesen ist, da sie die Konsistenz in der Verlauf-Verwaltung über alle Ansichten und Navigationsaktionen hinweg aufrechterhält.

### Verlauf verwalten {#managing-history}
Die folgenden Methoden können für die Verlauf-Navigation in einer WebforJ-App verwendet werden:

- `back()`: Bewegt den Browserverlauf um einen Schritt zurück, wodurch simuliert wird, dass der Benutzer die Zurück-Taste in seinem Browser drückt. Wenn es keine weiteren Einträge im Verlauf-Stack gibt, bleibt er auf der aktuellen Seite.

  ```java
  history.back();
  ```

- `forward()`: Bewegt den Browserverlauf um einen Schritt vorwärts, wodurch simuliert wird, dass der Benutzer die Vorwärts-Taste in seinem Browser drückt. Dies funktioniert nur, wenn ein Eintrag im Verlauf-Stack vorausliegt.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigiert zu einem bestimmten Punkt im Verlauf-Stack basierend auf einem Index. Eine positive Zahl bewegt vorwärts, eine negative Zahl rückwärts, und null lädt die aktuelle Seite neu. Diese Methode bietet eine detailliertere Kontrolle im Vergleich zu `back()` und `forward()`.

  ```java
  history.go(-2); // Bewegt sich um zwei Einträge im Verlauf-Stack zurück
  ```

- `size()`: Ruft die Gesamtzahl der Einträge im Sitzungshistorien-Stack ab, einschließlich der derzeit geladenen Seite. Dies kann nützlich sein, um den Navigationspfad des Benutzers zu verstehen oder um benutzerdefinierte Navigationssteuerungen zu implementieren.

  ```java
  int historySize = history.size();
  System.out.println("Verlaufslänge: " + historySize);
  ```

- `getLocation()`: Gibt den aktuellen URL-Pfad relativ zur Herkunft der App zurück. Diese Methode hilft Entwicklern, den aktuellen Pfad abzurufen, was nützlich ist, um URL-basierte Navigation in einseitigen Anwendungen zu verwalten.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
  ```

Zu verstehen, wie man effizient navigiert, ist der Grundpfeiler für den Bau dynamischer Anwendungen. Sobald Sie die Grundlagen der Navigation verstanden haben, ist es wichtig zu wissen, wie man die mit diesen Navigationsevents verbundenen URLs abruft und aktualisiert.

## Zugriff auf und Aktualisierung von URLs {#accessing-and-updating-url}

Ein wesentlicher Aspekt des Navigierens und Verwalten des Browserverlaufs besteht darin, den aktuellen URL-Pfad effizient zuzugreifen und zu aktualisieren. Dies ist in modernen Webanwendungen unerlässlich, in denen URL-Änderungen unterschiedlichen Ansichten oder Zuständen innerhalb der App entsprechen. Die `BrowserHistory`-API bietet eine einfache Möglichkeit, den aktuellen Pfad relativ zur Wurzel der App abzurufen und zu manipulieren.

:::tip webforJ `Router`
Siehe den [Artikel `Router`](../routing/overview), um mehr über umfassendes URL- und Routenmanagement zu erfahren.
:::

`getLocation()` ruft den aktuellen URL-Pfad relativ zur Herkunft der App ab. Die Methode `getLocation()` gibt ein `Optional<Location>` zurück, das es Ihnen ermöglicht, den Pfadteil der URL ohne die Domain zu erhalten.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Aktueller Pfad: " + loc.getFullURI()));
```

## Zustand verwalten {#managing-state}

`BrowserHistory` ermöglicht es Ihnen, benutzerdefinierte Zustandsinformationen mithilfe der Methoden `pushState()` und `replaceState()` zu speichern und zu verwalten. Durch die Verwendung der Zustandverwaltungs-Methoden können Sie steuern, welche Informationen als Teil des Verlaufseintrags gespeichert werden, was hilft, ein konsistentes Benutzererlebnis beim Vor- und Zurücknavigieren innerhalb Ihrer App aufrechtzuerhalten. Die folgenden Methoden können zur Zustandsverwaltung in Ihrer WebforJ-App verwendet werden.

- `pushState(Object state, Location location)`: Fügen Sie einen neuen Eintrag zum Verlauf-Stack hinzu. Akzeptiert ein Zustandsobjekt und ein `Location`-Objekt, das die neue URL darstellt.

```java
Location location = new Location("/neue-seite");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Ersetzt den aktuellen Verlaufseintrag. Dies erstellt keinen neuen Eintrag im Stack wie die oben genannte Methode.

```java
Location location = new Location("/aktualisierte-seite");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Ruft das Zustandsobjekt ab, das mit dem aktuellen Verlaufseintrag verknüpft ist. Diese Methode gibt ein Optional zurück, das das Zustandsobjekt enthält, das in die angegebene Klasse deserialisiert wird.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Aktuelle Seite: " + state.getViewName()));
```

### Überwachen von Zustandsänderungen {#listening-for-state-changes}
Die `BrowserHistory`-Klasse bietet die Möglichkeit, Ereignis-Listener zu registrieren, die auf Änderungen im Verlauf-Zustand reagieren.

Die Methode `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registriert einen Listener, der ausgelöst wird, wenn sich der Zustand ändert, z.B. wenn der Benutzer die Zurück- oder Vorwärts-Tasten des Browsers klickt. Diese Methode richtet einen Listener für das `popstate`-Ereignis des Browsers ein, was es Ihrer App ermöglicht, auf Benutzeraktionen oder programmatisch ausgelöste Zustandsänderungen zu reagieren.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Verlaufzustand geändert zu: " + event.getLocation().getFullURI());
});
```

Effektives Zustandsmanagement ermöglicht es Ihnen, Apps zu erstellen, die dynamisch auf Benutzeraktionen reagieren. Benutzer können durch Ihre App navigieren, ohne den Kontext zu verlieren, was für ein reibungsloseres und intuitiveres Erlebnis sorgt. Darüber hinaus ermöglicht das Speichern von Zustand erweiterte Funktionen wie das Wiederherstellen von Ansichtspositionen, das Beibehalten von Filter- oder Sortiereinstellungen und das Unterstützen von Deep Linking – all dies trägt zu einer ansprechenderen und zuverlässigeren App bei.
