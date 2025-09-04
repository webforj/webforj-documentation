---
sidebar_position: 15
title: Interval
_i18n_hash: dc02bb8f8bb43ee67f300071d3ab4ec7
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

De <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> klasse vertegenwoordigt een timer die een [event](../building-ui/events) met een vaste tijdsverloop tussen elke trigger aanroept.

De `Interval` klasse biedt een eenvoudige manier om events na een gespecificeerde vertraging te triggeren. Het is mogelijk om een `Interval` te starten, stoppen en opnieuw te starten indien nodig. Daarnaast kunnen Intervals meerdere luisteraars voor het verstrijken van het event ondersteunen. Geoptimaliseerd voor het webforJ-framework, biedt het betere prestaties in vergelijking met de standaard Java-timer of de Swing-timer.

## Usages {#usages}
De `Interval` klasse triggert event(s) met een vaste tijdsverloop. Door Intervals creatief te benutten, kun je de interactie en betrokkenheid van gebruikers op je website verbeteren en tegelijkertijd de ervaring dynamisch en interessant houden:

1. **Controleer op Inactiviteit**: Toon een [`Dialog`](../components/dialog) component als er gedurende een bepaalde tijd geen interactie met een formulier heeft plaatsgevonden.

2. **Uitgelichte Inhoud**: Roteren door uitgelichte artikelen, producten of aanbiedingen op je homepage bij elke Interval. Dit houdt de inhoud dynamisch en boeiend.

3. **Live Gegevens**: Vernieuw gegevens op je app, zoals aandelenprijzen, nieuwsfeeds of weersupdates, bij elke Interval om de gegevens actueel te houden.

## Beheren van `Interval`-statussen: starten, stoppen en opnieuw starten {#managing-interval-states-starting-stopping-and-restart}
Een Interval vereist handmatige activatie; gebruik de `start()` methode om deze te starten. Gebruik de `stop()` methode om een Interval te stoppen. De `restart()` methode kan worden gebruikt om de Interval opnieuw te starten.

## Aanpassen van de `Interval` vertraging {#adjusting-the-interval-delay}

Om de vertraging van een Interval te wijzigen, gebruik de `setDelay(float delay)` methode. De nieuwe vertragingwaarde wordt toegepast nadat de Interval is gestopt of opnieuw gestart.

```java
//Veranderen van de Vertraging
Interval.setDelay(2f);
Interval.restart();
```

:::tip
De vertraging kan fractie-seconden zijn tot milliseconde-resolutie, maar een zeer kleine time-outwaarde veroorzaakt een overvloed aan events sneller dan het programma erop kan reageren.
:::

## Luisteraars toevoegen {#adding-listeners}

Je kunt extra luisteraars aan een Interval koppelen met behulp van de `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` methode. Zodra een luisteraar is toegevoegd, wordt deze automatisch geactiveerd bij de volgende interval als de Interval al actief is.

```java
// Luisteraars Toevoegen
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
