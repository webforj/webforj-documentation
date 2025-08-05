---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

Die <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> Klasse repräsentiert einen Timer, der ein [Ereignis](../building-ui/events) mit einer festen Zeitverzögerung zwischen jeder Auslösung auslöst.

Die `Interval` Klasse bietet eine einfache Möglichkeit, Ereignisse nach einer bestimmten Verzögerung auszulösen. Es ist möglich, ein `Interval` nach Bedarf zu starten, zu stoppen und neu zu starten. Darüber hinaus können Intervalle mehrere Listener für das vergangene Ereignis unterstützen. Optimiert für das webforJ Framework bietet es eine bessere Leistung im Vergleich zum стандартmäßigen Java-Timer oder dem Swing-Timer.

## Anwendungen {#usages}
Die `Interval` Klasse löst Ereignis(e) mit einer festen Zeitverzögerung aus. Durch kreativen Einsatz von Intervallen können Sie die Benutzerinteraktion und das Engagement auf Ihrer Website fördern und gleichzeitig die Erfahrung dynamisch und interessant gestalten:

1. **Überprüfung auf Inaktivität**: Zeigen Sie eine [`Dialog`](../components/dialog) Komponente an, wenn es innerhalb einer bestimmten Zeit keine Interaktion mit einem Formular gegeben hat.

2. **Hervorgehobene Inhalte**: Rotieren Sie durch hervorgehobene Artikel, Produkte oder Angebote auf Ihrer Startseite bei jedem Intervall. Das hält den Inhalt dynamisch und ansprechend.

3. **Live-Daten**: Aktualisieren Sie die Daten in Ihrer App, wie Aktienkurse, News-Feeds oder Wetterupdates, bei jedem Intervall, um die Daten aktuell zu halten.

## Verwaltung von `Interval` Zuständen: Starten, Stoppen und Neustarten {#managing-interval-states-starting-stopping-and-restart}
Ein Intervall erfordert eine manuelle Aktivierung; verwenden Sie die Methode `start()`, um es zu starten. Um ein Intervall zu stoppen, verwenden Sie die Methode `stop()`. Die Methode `restart()` kann verwendet werden, um das Intervall neu zu starten.

## Anpassung der `Interval` Verzögerung {#adjusting-the-interval-delay}

Um die Verzögerung eines Intervalls zu ändern, verwenden Sie die Methode `setDelay(float delay)`. Der neue Verzögerungswert wird angewendet, nachdem das Intervall entweder gestoppt oder neu gestartet wurde.

```java
//Ändern der Verzögerung
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Die Verzögerung kann Bruchteile von Sekunden bis hin zu Millisekunden betragen, aber ein sehr kleiner Timeout-Wert führt zu einer Flut von Ereignissen, schneller als das Programm darauf reagieren kann.
:::

## Hinzufügen von Listenern {#adding-listeners}

Sie können zusätzliche Listener an ein Intervall anhängen, indem Sie die Methode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` verwenden. Sobald ein Listener hinzugefügt wird, wird er automatisch beim nächsten Intervall ausgelöst, wenn das Intervall bereits läuft.

```java
// Hinzufügen von Listenern
float delay = 2f;

EventListener<Interval.ElapsedEvent> ersterListener = (e -> {
// Ausführbarer Code
});

Interval interval = new Interval(delay, ersterListener);

EventListener<Interval.ElapsedEvent> zweiterListener = (e -> {
// Ausführbarer Code
});

interval.addElapsedListener(zweiterListener);
```
