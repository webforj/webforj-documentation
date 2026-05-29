---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

De <JavadocLink type="foundation" location="com/webforj/Interval" code='true'>Interval</JavadocLink> klasse vertegenwoordigt een timer die een [evenement](../building-ui/events) met een vaste tijdsvertraging tussen elke trigger activeert.

Je kunt een `Interval` naar behoefte [starten, stoppen en herstarten](#managing-interval-states-starting-stopping-and-restart) en meerdere [luisteraars](#adding-listeners) voor het verstreken evenement toevoegen. In webforJ heeft een `Interval` [betere prestaties](#performance) in vergelijking met een standaard Java-timer of Swing-timer.

## Usages {#usages}
De `Interval` klasse triggert evenementen met een vaste tijdsvertraging. Door Intervals creatief te gebruiken, kun je dynamische en interessante ervaringen in je app creëren:

1. **Controleer op Inactiviteit**: Toon een [`Dialog`](../components/dialog) component als er gedurende een bepaalde tijd geen interactie op een formulier is geweest.

2. **Uitgelichte Inhoud**: Draai door uitgelichte artikelen, producten of promoties op je homepage bij elke Interval. Dit houdt de inhoud dynamisch en boeiend.

3. **Live Gegevens**: Vernieuw gegevens op je app, zoals aandelenprijzen, nieuwsfeeds of weersupdates, bij elke Interval om de gegevens actueel te houden.

## Beheren van `Interval` statussen: starten, stoppen en herstarten {#managing-interval-states-starting-stopping-and-restart}
Een Interval vereist handmatige activatie; gebruik de `start()` methode om het te initiëren. Gebruik de `stop()` methode om een Interval te stoppen. De `restart()` methode kan worden gebruikt om het Interval opnieuw te starten.

## De vertraging van het `Interval` aanpassen {#adjusting-the-interval-delay}

Om de vertraging van een Interval te wijzigen, gebruik je de `setDelay(float delay)` methode. De nieuwe vertraging waarde wordt toegepast nadat het Interval is gestopt of opnieuw is gestart.


```java
//Verander de Vertraging
Interval.setDelay(2f);
Interval.restart();
```

:::tip
De vertraging kan fractionele seconden tot milliseconden zijn, maar een zeer kleine time-outwaarde veroorzaakt een overvloed aan evenementen sneller dan het programma erop kan reageren.
:::

## Luisteraars toevoegen {#adding-listeners}

Je kunt extra luisteraars aan een Interval toevoegen met de `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` methode. Zodra een luisteraar is toegevoegd, wordt deze automatisch getriggerd bij de volgende interval als het Interval al actief is.

```java
// Luisteraars toevoegen
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Uitvoerbare code
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Uitvoerbare code
});

interval.addElapsedListener(secondListener);
```

## Prestaties {#performance}

De `Interval` klasse is speciaal ontworpen om betere prestaties en betrouwbaarheid te bieden voor de grote belasting die webapps tegenkomen. In Java Swing kan hetzelfde gedrag voldoende worden beheerd door een `Timer` of een nieuwe thread, maar die aanpak schaalt niet goed voor webapps. Webapps hebben waarschijnlijk veel gelijktijdige gebruikers, en als elke gebruiker een nieuwe Timer of thread aanmaakt, kan het systeem snel falen wanneer het zonder threads komt te zitten.

Er zijn verschillende levensvatbare opties die werken op deze schaal: [**virtuele threads**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor en TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html), en **`Interval`**. Afhankelijk van je app en gebruiksscenario kan een van deze de beste optie voor jou zijn. Als standaard is `Interval` een betrouwbare keuze die speciaal is ontworpen om te werken met webforJ, en vereist geen aanvullende configuratie.
