---
sidebar_position: 7
title: Events
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
Components, ob custom oder Teil des Frameworks, unterstützen die Ereignisbehandlung. Sie können Ereignislistener hinzufügen, um verschiedene Arten von Ereignissen zu erfassen, wie Benutzerinteraktionen, Änderungen im Zustand oder Ereignisse, die Sie selbst definieren. Diese Listener ermöglichen es Ihnen, spezifisches Verhalten als Reaktion auf das, was in Ihrer App passiert, auszulösen.

## Hinzufügen von Ereignissen {#adding-events}

Fügen Sie einen Listener mit der spezifischen Ereignismethode des Komponenten hinzu. Jede Komponente stellt ein Paar bereit: eine `addXxxListener`-Methode und in den meisten Fällen ein kürzeres `on`-Alias, das dasselbe tut. Ein `Button` stellt beispielsweise sowohl `addClickListener` als auch `onClick` zur Verfügung.

Sie können den Listener als Lambda übergeben:

```java
Button button = new Button("Speichern");
button.onClick(event -> {
  // Handle den Klick
});
```

oder als Methodenreferenz:

```java
button.onClick(this::handleSave);
```

Nicht jedes Ereignis hat ein `on`-Alias. Wertänderungen werden beispielsweise nur mit `addValueChangeListener` hinzugefügt:

```java
TextField name = new TextField("Name");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Handle den neuen Wert
});
```

## Entfernen eines Ereignisses {#removing-an-event}

Das Hinzufügen eines Listeners gibt eine `ListenerRegistration` zurück. Bewahren Sie es auf, um den Listener später zu entfernen.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Handle den Klick
    });

// Später, wenn der Listener nicht mehr benötigt wird
registration.remove();
```

## Verwenden von Ereignispayload {#using-event-payload}

Ereignisse tragen eine Payload mit Informationen darüber, was passiert ist. Das Lesen dieser Payload im Handler gibt Ihnen die relevanten Daten, ohne eine zusätzliche Anfrage an den Client.

Zum Beispiel trägt ein `ModifyEvent` von einem `TextField` den aktuellen Text des Feldes. Sie können es bei der Komponente abfragen:

```java
TextField field = new TextField("Suchen");
field.onModify(event -> {
  String text = field.getText();
  // Verwenden Sie den Text
});
```

Der gleiche Wert ist bereits im Ereignis, sodass das Lesen von der Payload eine Rückkehr zur Komponente vermeidet:

```java
field.onModify(event -> {
  String text = event.getText();
  // Verwenden Sie den Text
});
```

Lesen Sie von der Payload, wo immer ein Ereignis die benötigten Daten bereitstellt. Weitere Informationen dazu finden Sie unter [Client/Server-Interaktion](../architecture/client-server).

## Konfigurieren von Elementereignissen {#configuring-element-events}

Wenn Sie direkt mit einem <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> arbeiten, werden seine Ereignisse mit <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink> konfiguriert. Dies steuert, welche Daten das Ereignis trägt, ob es überhaupt ausgelöst wird und wie oft, alles bewertet auf dem Client, bevor das Ereignis den Server erreicht.

### Ereignisdaten {#event-data}

Ereignisdaten hängen Werte vom Client an das Ereignis an, sodass Informationen auf dem Server ohne zusätzliche Anfrage verfügbar sind. Sie fügen es mit `addData()` hinzu, wobei jeder Eintrag einen Schlüssel und einen JavaScript-Ausdruck erhält, der den Wert produziert.

Zwei Variablen sind in diesen Ausdrücken verfügbar: `event`, das Client-Ereignisobjekt, und `component`, das Element, an das der Listener gebunden ist.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

Auf dem Server wird jeder Wert anhand seines Schlüssels aus dem Ereignis gelesen.

### Ausführen von JavaScript {#executing-javascript}

`setCode()` führt einen JavaScript-Schnipsel auf dem Client aus, bevor das Ereignis ausgelöst wird. Dies ist nützlich, um Ereignisdaten vorzubereiten oder auf dem Client zu reagieren, ohne eine Server-Anfrage.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Filtern von Ereignissen {#filtering-events}

`setFilter()` setzt einen JavaScript-Ausdruck, der entscheidet, ob das Ereignis ausgelöst wird. Wenn er als falsch bewertet wird, erreicht das Ereignis niemals den Server. Dies ist nützlich, wenn Sie sich nur um ein Ereignis unter bestimmten Bedingungen kümmern, z. B. wenn eine Eingabe eine Mindestlänge überschreitet.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Entprellen und Drosseln {#debouncing-and-throttling}

Entprellen und Drosseln beschränken, wie oft ein Ereignis den Server erreicht, was nützlich für schnelle Ereignisse wie Tippen oder Scrollen ist.

Entprellen wartet, bis die Aktivität sich beruhigt hat, bevor es ausgelöst wird. `setDebounce()` nimmt einen Timeout in Millisekunden und eine optionale <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink>: `LEADING` wird zu Beginn des Ansturms ausgelöst, `TRAILING` wird nach dessen Ende ausgelöst, und `BOTH` wird an jedem Rand ausgelöst. Wenn Sie die Phase weglassen, wird standardmäßig `TRAILING` verwendet.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Drosseln löst mit einer konstanten maximalen Rate aus, solange die Aktivität weitergeht. `setThrottle()` nimmt einen Timeout in Millisekunden entgegen.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

Ein Ereignis verwendet entweder das eine oder das andere. Das Setzen einer Entprellung löscht jede Drosselung zu denselben Optionen, und das Setzen einer Drosselung löscht jede Entprellung.

### Anmerkungen {#annotations}

Element-Ereignisoptionen können auch mit Anmerkungen gesetzt werden, was eine prägnantere Möglichkeit darstellt, einen Listener zu konfigurieren. Die `@EventOptions`-Anmerkung enthält die Dateneinträge sowie Filter-, Entprell- und Drosselungseinstellungen.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

Wenn Sie auch `ElementEventOptions` am Aufrufort übergeben, kombiniert dessen Daten mit den Daten der Anmerkung, und dessen Code, Filter, Entprell- und Drosselungseinstellungen überschreiben die Anmerkung.

## Eigene Ereignisse auslösen {#dispatching-your-own-events}

Die bisher behandelten Ereignisse stammen von der Komponente, auf die Sie hören. Eine von Ihnen geschriebene Komponente kann auf die gleiche Weise eigene Ereignisse veröffentlichen, sodass der Code, der sie verwendet, reagieren kann, ohne in die internen Abläufe der Komponente eingreifen zu müssen.

:::tip Wann ein benutzerdefiniertes Ereignis auszulösen
Lösen Sie ein benutzerdefiniertes Ereignis aus, wenn Ihre Komponente entscheidet, dass etwas passiert ist, z. B. wenn ein Formular einen abgeschlossenen Submission oder ein Editor einen gespeicherten Datensatz meldet. Ereignisse, die von einer Client-Interaktion auf einem `Element` ausgehen, werden stattdessen mit [Elementereignisoptionen](#configuring-element-events) konfiguriert.
:::

Komponenten haben keinen Ereignis-Dispatcher, also hält eine Komponente, die ihre eigenen Ereignisse veröffentlicht, ihren eigenen <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> und veröffentlicht darüber.

### Definieren des Ereignisses {#defining-the-event}

Definieren Sie das Ereignis als Klasse, die `EventObject` erweitert. Übergeben Sie die Quelle, das Objekt, das das Ereignis veröffentlicht, an die Superklasse und fügen Sie Zugriffsmethoden für die benötigten Daten hinzu.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Das Lesen der Daten aus dem Ereignis folgt der gleichen Logik wie [Verwendung von Ereignispayload](#using-event-payload). Listener erhalten, was sie benötigen, aus dem Ereignis, anstatt die Quelle danach abzufragen.

### Registrieren und auslösen {#registering-and-dispatching}

Erstellen Sie einen Dispatcher, registrieren Sie Listener für einen Ereignistyp und lösen Sie eine Instanz dieses Typs aus, wenn das Ereignis auftritt. Die Registrierung gibt eine `ListenerRegistration` zurück, die Sie zur späteren Entfernung des Listeners aufbewahren.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Handle das Ereignis
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Jeder für diesen Ereignistyp registrierte Listener wird ausgeführt, wenn das Ereignis ausgelöst wird.

Eine Komponente, die ein Ereignis veröffentlicht, hält den Dispatcher intern und stellt eine `onXxx`-Methode anstelle des Dispatchers selbst zur Verfügung, sodass Anrufer sich auf die gleiche Weise anmelden können, wie sie es bei einem integrierten Ereignis tun würden:

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Entfernen von Listenern {#removing-listeners}

Entfernen Sie einen Listener über seine Registrierung oder indem Sie den Listener an den Dispatcher zurückgeben:

```java
registration.remove();

//ODER

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

Um alle für einen Ereignistyp registrierten Listener auf einmal zu entfernen:

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Vermeidung von Speicherlecks {#avoiding-memory-leaks}

Ein Dispatcher behält seine Listener, und jeder Listener behält, was auch immer er erfasst hat. Ein Lambda oder eine innere Klasse erfasst implizit `this` zusammen mit allen von ihr verwendeten lokalen Variablen, sodass die Objekte hinter einem Listener für die gesamte Zeit, die der Dispatcher sie hält, erreichbar bleiben.

Dies wird zu einem Problem, wenn ein Listener länger lebt als das, worauf er verweist. Wenn ein Dialog einen Listener registriert, der sein eigenes Modell liest und schließt, ohne ihn zu entfernen, hält der Dispatcher weiterhin den Listener, der Listener hält weiterhin den Dialog, und keiner kann garbage collection durchgeführt werden. In einer App, die viele kurzlebige Ansichten erstellt, sammeln sich auf diese Weise erhaltene Listener an.

Entfernen Sie einen Listener, wenn:

- Das Objekt, das ihn registriert hat, abgeschlossen ist, z. B. ein geschlossener Dialog oder eine Ansicht, von der wegg navigiert wurde.
- Das Abonnement war an eine kurzlebige Aufgabe oder einen einmaligen Ablauf gebunden.

Bewahren Sie die zurückgegebene `ListenerRegistration` dort auf, wo Sie während der Bereinigung darauf zugreifen können, anstatt einen Listener zu registrieren, den Sie später nicht entfernen können. In einer Komponente ist `onDidDestroy()` der Bereinigungspunkt.
