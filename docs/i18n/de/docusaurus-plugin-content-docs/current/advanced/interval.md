---
sidebar_position: 15
title: Interval
_i18n_hash: dc02bb8f8bb43ee67f300071d3ab4ec7
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Die <JavadocLink type="foundation" location="com/webforj/Interval" code='true'>Interval</JavadocLink>-Klasse repräsentiert einen Timer, der ein [Ereignis](../building-ui/events) mit einer festen Zeitverzögerung zwischen jedem Auslösen auslöst.

Die `Interval`-Klasse bietet eine einfache Möglichkeit, Ereignisse nach einer festgelegten Verzögerung auszulösen. Es ist möglich, ein `Interval` nach Bedarf zu starten, anzuhalten und neu zu starten. Darüber hinaus können Intervalle mehrere Listener für das vergangene Ereignis unterstützen. Optimiert für das webforJ-Framework bietet es eine bessere Leistung im Vergleich zum Standard-Java-Timer oder dem Swing-Timer.

## Usages {#usages}
Die `Interval`-Klasse löst Ereignis(e) mit einer festen Zeitverzögerung aus. Durch die kreative Nutzung von Intervallen können Sie die Interaktion und das Engagement der Benutzer auf Ihrer Website verbessern und das Erlebnis dynamisch und interessant gestalten:

1. **Inaktivitätsüberprüfung**: Zeigen Sie eine [`Dialog`](../components/dialog)-Komponente an, wenn es innerhalb eines bestimmten Zeitraums keine Interaktion mit einem Formular gegeben hat.

2. **Hervorgehobene Inhalte**: Rotieren Sie durch hervorgehobene Artikel, Produkte oder Aktionen auf Ihrer Startseite bei jedem Intervall. Dies hält die Inhalte dynamisch und ansprechend.

3. **Echtzeitdaten**: Aktualisieren Sie Daten in Ihrer App, zum Beispiel Aktienkurse, Nachrichtenfeeds oder Wetterupdates, bei jedem Intervall, um die Daten aktuell zu halten.

## Verwaltung der `Interval`-Zustände: starten, anhalten und neu starten {#managing-interval-states-starting-stopping-and-restart}
Ein Intervall erfordert eine manuelle Aktivierung; verwenden Sie die `start()`-Methode, um es zu starten. Um ein Intervall zu stoppen, verwenden Sie die `stop()`-Methode. Die `restart()`-Methode kann verwendet werden, um das Intervall neu zu starten.

## Anpassung der `Interval`-Verzögerung {#adjusting-the-interval-delay}

Um die Verzögerung eines Intervalls zu ändern, verwenden Sie die `setDelay(float delay)`-Methode. Der neue Verzögerungswert wird angewendet, nachdem das Intervall entweder gestoppt oder neu gestartet wurde.


```java
//Verzögerung ändern
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Die Verzögerung kann Bruchsekunden bis Millisekunden in der Auflösung betragen, aber ein sehr kleiner Timeout-Wert verursacht eine Flut von Ereignissen, schneller als das Programm darauf reagieren kann.
:::

## Hinzufügen von Listenern {#adding-listeners}

Sie können zusätzliche Listener zu einem Intervall mit der Methode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` anhängen. Sobald ein Listener hinzugefügt wird, wird er automatisch beim nächsten Intervall ausgelöst, wenn das Intervall bereits läuft.

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
