---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Die <JavadocLink type="foundation" location="com/webforj/Interval" code='true'>Interval</JavadocLink>-Klasse repräsentiert einen Timer, der ein [Ereignis](../building-ui/events) mit einer festen Zeitverzögerung zwischen jedem Triggern auslöst.

Sie können ein `Interval` nach Bedarf [starten, stoppen und neu starten](#managing-interval-states-starting-stopping-and-restart) und mehrere [Listener](#adding-listeners) für das verstrichene Ereignis hinzufügen.
In webforJ hat ein `Interval` [bessere Leistung](#performance) im Vergleich zu einem standardmäßigen Java-Timer oder Swing-Timer.

## Verwendungen {#usages}
Die `Interval`-Klasse löst Ereignisse mit einer festen Zeitverzögerung aus. Durch die kreative Nutzung von Intervallen können Sie dynamische und interessante Erlebnisse in Ihrer App schaffen:

1. **Inaktivität überprüfen**: Zeigen Sie eine [`Dialog`](../components/dialog)-Komponente an, wenn innerhalb eines bestimmten Zeitraums keine Interaktion in einem Formular stattgefunden hat.

2. **Hervorgehobene Inhalte**: Rotieren Sie durch hervorgehobene Artikel, Produkte oder Aktionen auf Ihrer Startseite bei jedem Intervall. Dadurch bleibt der Inhalt dynamisch und ansprechend.

3. **Echtzeitdaten**: Aktualisieren Sie Daten in Ihrer App, wie Aktienkurse, Nachrichtenfeeds oder Wetterupdates, bei jedem Intervall, um die Daten aktuell zu halten.

## Verwaltung von `Interval`-Zuständen: Starten, Stoppen und Neustarten {#managing-interval-states-starting-stopping-and-restart}
Ein Interval muss manuell aktiviert werden; verwenden Sie die Methode `start()`, um es zu starten. Um ein Interval zu stoppen, verwenden Sie die Methode `stop()`. Die Methode `restart()` kann verwendet werden, um das Interval neu zu starten.

## Anpassung der `Interval`-Verzögerung {#adjusting-the-interval-delay}

Um die Verzögerung eines Intervals zu ändern, verwenden Sie die Methode `setDelay(float delay)`. Der neue Verzögerungswert wird angewendet, nachdem das Interval entweder gestoppt oder neu gestartet wurde.

```java
//Verzögerung ändern
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Die Verzögerung kann in Bruchteilen von Sekunden bis zu Millisekunden messbar sein, aber ein sehr kleiner Timeout-Wert verursacht einen Fluss von Ereignissen, schneller als das Programm darauf reagieren kann.
:::

## Hinzufügen von Listenern {#adding-listeners}

Sie können zusätzliche Listener an ein Interval anhängen, indem Sie die Methode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` verwenden. Sobald ein Listener hinzugefügt wurde, wird er automatisch beim nächsten Intervall ausgelöst, wenn das Interval bereits läuft.

```java
// Listener hinzufügen
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Ausführbarer Code
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Ausführbarer Code
});

interval.addElapsedListener(secondListener);
```

## Leistung {#performance}

Die `Interval`-Klasse wurde speziell entwickelt, um bessere Leistung und Zuverlässigkeit für die hohen Lasten zu bieten, die von Web-Apps getroffen werden.
In Java Swing kann dasselbe Verhalten ausreichend von einem `Timer` oder einem neuen Thread verwaltet werden, aber dieser Ansatz skaliert nicht gut für Web-Apps.
Web-Apps haben wahrscheinlich viele gleichzeitige Benutzer, und wenn jeder Benutzer einen neuen Timer oder Thread erstellt, kann das System schnell zusammenbrechen, wenn es keine Threads mehr hat.

Es gibt mehrere praktikable Optionen, die in dieser Größenordnung funktionieren: [**virtuelle Threads**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor und TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html) und **`Interval`**.
Je nach Ihrer App und Ihrem Anwendungsfall kann jede dieser Optionen die beste für Sie sein.
Standardmäßig ist `Interval` eine zuverlässige Wahl, die speziell für die Arbeit mit webforJ entwickelt wurde und keine zusätzliche Konfiguration erfordert.
