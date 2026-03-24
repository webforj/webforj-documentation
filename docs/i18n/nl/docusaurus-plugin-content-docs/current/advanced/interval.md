---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

De <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> klasse vertegenwoordigt een timer die een [evenement](../building-ui/events) met een vaste tijdsvertraging tussen elke triggering activeert.

De `Interval` klasse biedt een eenvoudige manier om evenementen te activeren na een opgegeven vertraging. Het is mogelijk om een `Interval` te starten, stoppen en opnieuw te starten wanneer nodig. In webforJ heeft een `Interval` een betere prestatie in vergelijking met een standaard Java-timer of Swing-timer. Het ondersteunt ook meerdere luisteraars voor het verstreken evenement.

## Usages {#usages}
De `Interval` klasse activeert evenementen met een vaste tijdsvertraging. Door Intervals creatief te gebruiken, kun je dynamische en interessante ervaringen in je app creëren:

1. **Controle op Inactiviteit**: Toon een [`Dialog`](../components/dialog) component als er gedurende een bepaalde tijd geen interactie met een formulier heeft plaatsgevonden.

2. **Uitgelichte Inhoud**: Wissel tussen uitgelichte artikelen, producten of promoties op jehomepage bij elke Interval. Dit houdt de inhoud dynamisch en boeiend.

3. **Live Gegevens**: Vernieuw gegevens in je app, zoals aandelenprijzen, nieuwsfeeds of weersupdates, bij elke Interval om de gegevens actueel te houden.

## Beheren van `Interval` staten: starten, stoppen en opnieuw starten {#managing-interval-states-starting-stopping-and-restart}
Een Interval vereist handmatige activatie; gebruik de `start()` methode om deze te initiëren. Om een Interval te stoppen, gebruik de `stop()` methode. De `restart()` methode kan worden gebruikt om de Interval opnieuw te starten.

## Aanpassen van de `Interval` vertraging {#adjusting-the-interval-delay}

Om de vertraging van een Interval te wijzigen, gebruik de `setDelay(float delay)` methode. De nieuwe vertragingwaarde wordt toegepast nadat de Interval ofwel is gestopt of opnieuw is gestart.


```java
//Verandering van de Vertraging
Interval.setDelay(2f);
Interval.restart();
```

:::tip
De vertraging kan fracties van een seconde zijn met een resolutie tot milliseconden, maar een zeer kleine time-outwaarde kan een overvloed aan evenementen veroorzaken die sneller zijn dan het programma kan reageren.
:::

## Luisteraars toevoegen {#adding-listeners}

Je kunt extra luisteraars aan een Interval toevoegen met behulp van de `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` methode. Zodra een luisteraar is toegevoegd, wordt deze automatisch geactiveerd bij de volgende interval als de Interval al actief is.

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
