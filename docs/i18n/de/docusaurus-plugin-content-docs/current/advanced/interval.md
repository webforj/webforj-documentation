---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Die <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink>-Klasse repräsentiert einen Timer, der ein [Ereignis](../building-ui/events) mit einer festen Zeitverzögerung zwischen jedem Auslösen auslöst.

Die `Interval`-Klasse bietet eine einfache Möglichkeit, Ereignisse nach einer festgelegten Verzögerung auszulösen. Es ist möglich, ein `Interval` nach Bedarf zu starten, zu stoppen und neu zu starten. 
In webforJ bietet ein `Interval` eine bessere Leistung im Vergleich zu einem Standard-Java-Timer oder Swing-Timer.
Es unterstützt auch mehrere Listener für das vergangene Ereignis.

## Verwendungsmöglichkeiten {#usages}
Die `Interval`-Klasse löst Ereignisse mit einer festen Zeitverzögerung aus. Durch kreativen Einsatz von Intervallen können Sie dynamische und interessante Erlebnisse in Ihrer App schaffen:

1. **Überprüfung der Inaktivität**: Anzeigen eines [`Dialog`](../components/dialog)-Komponenten, wenn es innerhalb einer bestimmten Zeit keine Interaktion mit einem Formular gegeben hat.

2. **Ausgewählte Inhalte**: Drehen Sie an ausgewählten Artikeln, Produkten oder Aktionen auf Ihrer Startseite bei jedem Intervall. Dies hält den Inhalt dynamisch und ansprechend.

3. **Echtzeitdaten**: Aktualisieren Sie Daten in Ihrer App, wie Aktienkurse, Nachrichtenfeeds oder Wetterupdates, bei jedem Intervall, um die Daten aktuell zu halten.

## Verwaltung der `Interval`-Zustände: Starten, Stoppen und Neustarten {#managing-interval-states-starting-stopping-and-restart}
Ein Interval benötigt eine manuelle Aktivierung; verwenden Sie die Methode `start()`, um es zu starten. Um ein Interval zu stoppen, verwenden Sie die Methode `stop()`. Die Methode `restart()` kann verwendet werden, um das Interval neu zu starten.

## Anpassen der `Interval`-Verzögerung {#adjusting-the-interval-delay}

Um die Verzögerung eines Intervals zu ändern, verwenden Sie die Methode `setDelay(float delay)`. Der neue Verzögerungswert wird angewendet, nachdem das Interval entweder gestoppt oder neu gestartet wurde.

```java
//Ändern der Verzögerung
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Die Verzögerung kann Bruchteile von Sekunden mit Millisekundenauflösung betragen, aber ein sehr kleiner Timeout-Wert verursacht eine Flut von Ereignissen, schneller als das Programm darauf reagieren kann.
:::

## Hinzufügen von Listenern {#adding-listeners}

Sie können zusätzliche Listener zu einem Interval hinzufügen, indem Sie die Methode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` verwenden. Sobald ein Listener hinzugefügt wurde, wird er automatisch beim nächsten Intervall ausgelöst, wenn das Interval bereits läuft.

```java
// Hinzufügen von Listenern
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
