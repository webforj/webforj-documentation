---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

De <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> klasse vertegenwoordigt een timer die een [evenement](../building-ui/events) met een vaste tijdsvertraging tussen elke trigger oproept.

De `Interval` klasse biedt een eenvoudige manier om evenementen na een gespecificeerde vertraging te triggeren. Het is mogelijk om een `Interval` indien nodig te starten, te stoppen en opnieuw te starten. Daarnaast kunnen Intervals meerdere luisteraars voor het verlopen evenement ondersteunen. Geoptimaliseerd voor het webforJ-framework, biedt het betere prestaties in vergelijking met de standaard Java-timer of de Swing-timer.

## Usages {#usages}
De `Interval` klasse triggert evenement(en) met een vaste tijdsvertraging. Door Intervals creatief te gebruiken, kun je de interactie en betrokkenheid van gebruikers op je website verbeteren terwijl de ervaring dynamisch en interessant blijft:

1. **Controleer op inactiviteit**: Toont een [`Dialog`](../components/dialog) component als er gedurende een bepaalde tijd geen interactie met een formulier heeft plaatsgevonden.

2. **Uitgelichte inhoud**: Draai door uitgelichte artikelen, producten of promoties op je homepage bij elke Interval. Dit houdt de inhoud dynamisch en boeiend.

3. **Live gegevens**: Vernieuw gegevens op je app, zoals aandelenprijzen, nieuwsfeeds of weersupdates, bij elke Interval om de gegevens actueel te houden.

## Beheren van `Interval` toestanden: starten, stoppen en opnieuw starten {#managing-interval-states-starting-stopping-and-restart}
Een Interval vereist handmatige activatie; gebruik de `start()` methode om deze te starten. Gebruik de `stop()` methode om een Interval te stoppen. De `restart()` methode kan worden gebruikt om de Interval opnieuw te starten.

## Het aanpassen van de `Interval` vertraging {#adjusting-the-interval-delay}

Om de vertraging van een Interval te wijzigen, gebruik je de `setDelay(float delay)` methode. De nieuwe vertraging waarde wordt toegepast nadat de Interval is gestopt of opnieuw is gestart.

```java
//Veranderen van de vertraging
Interval.setDelay(2f);
Interval.restart();
```

:::tip
De vertraging kan fractionele seconden tot milliseconden in resolutie zijn, maar een zeer kleine timeoutwaarde veroorzaakt een overvloed aan evenementen sneller dan het programma erop kan reageren.
:::

## Luisteraars toevoegen {#adding-listeners}

Je kunt extra luisteraars aan een Interval koppelen met de `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` methode. Zodra een luisteraar is toegevoegd, triggert deze automatisch bij de volgende interval als de Interval al draait.

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
