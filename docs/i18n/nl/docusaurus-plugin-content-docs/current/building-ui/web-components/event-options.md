---
sidebar_position: 4
title: Event Options
_i18n_hash: d780e41b809f0e3df55f65a1c71983a0
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is een veelzijdig webforJ hulpmiddel dat is ontworpen om configuratie-instellingen voor `Element`-gebeurtenissen binnen webforJ-toepassingen te encapsuleren en te beheren. Als een container voor verschillende opties stelt het ontwikkelaars in staat om nauwkeurig te bepalen hoe gebeurtenissen die met elementen zijn verbonden, moeten worden verwerkt.

## Gegevens van gebeurtenissen {#event-data}

Gegevens van gebeurtenissen zijn een belangrijk kenmerk van `ElementEventOptions`, waardoor ontwikkelaars specifieke informatie aan de gebeurtenisopties kunnen koppelen. Deze functionaliteit vergemakkelijkt het doorgeven van aangepaste gegevens van de client naar de server wanneer een gebeurtenis wordt getriggerd. Deze mogelijkheid is cruciaal om extra context of parameters die met de gebeurtenis zijn geassocieerd over te brengen, en maakt het mogelijk om informatie toegankelijk te maken en te gebruiken zonder dat er extra trips naar de client hoeven te worden gemaakt.

Overweeg bijvoorbeeld een scenario waarin je een knopklikgebeurtenis hebt en je de huidige gebruikersnaam van de gebruiker samen met de gebeurtenis wilt doorgeven. In plaats van elke keer de gebruikersnaam van de client te query'en, stuur je deze informatie samen met de gebeurtenis als gegevens.

:::tip
Voor meer informatie, zie de [gebeurtenissen](../../building-ui/events) en [Client/Server Interactie](../../architecture/client-server) pagina's.
:::

Om gegevens aan de gebeurtenisopties toe te voegen, kun je de `addData()` methode gebruiken.

## JavaScript uitvoeren {#executing-javascript}

De `ElementEventOptions` klasse stelt ontwikkelaars in staat om JavaScript-code op te geven die aan de clientzijde moet worden geëvalueerd voordat de bijbehorende gebeurtenis wordt geactiveerd. Deze functie stelt cliënten in staat om gegevens van gebeurtenissen voor te bereiden of extra gebeurtenissen te triggeren wanneer dat nodig is. Dit is nuttig in veel gevallen, bijvoorbeeld wanneer je formuliergegevens aan de clientzijde wilt valideren voordat je ze indient via een formulierindieningsgebeurtenis.

### Gebruik {#usage}
Om de gebeurteniscodes in te stellen, gebruik je de `setCode()` methode.

## Gebeurtenissen filteren {#filtering-events}

`ElementEventOptions` bevat een functie voor het instellen van een filterexpressie die aan de clientzijde moet worden geëvalueerd voordat de gebeurtenis wordt geactiveerd. Deze filterexpressie stelt de client in staat om te bepalen of de gebeurtenis moet doorgaan of moet worden gestopt, afhankelijk van bepaalde voorwaarden. Overweeg een invoerveld waar je een gebeurtenis alleen wilt triggeren als de ingevoerde tekst aan specifieke criteria voldoet, zoals een minimale lengte.

### Gebruik {#usage-1}
Om het gebeurtenisfilter in te stellen, gebruik je de `setFilter()` methode.

## Debouncing en throttling {#debouncing-and-throttling}

### Doel {#purpose}
`ElementEventOptions` biedt mechanismen voor debouncing en throttling van gebeurtenissen. Deze functies zijn nuttig voor het beheersen van de frequentie van gebeurtenisluisteraars, zodat ze alleen onder bepaalde voorwaarden worden geactiveerd.

### Gebruik {#usage-2}
- Om debounce in te stellen, gebruik je de `setDebounce` methode.
- Om throttle in te stellen, gebruik je de `setThrottle` methode.

### Voorbeeld {#example}
In scenario's waarin je snel gebruikersinvoer wilt verwerken, zoals zoekinvoervelden, kun je debounce gebruiken om de uitvoering uit te stellen totdat de gebruiker is gestopt met typen.

## Gebeurtenisopties samenvoegen {#merging-event-options}

De `ElementEventOptions` klasse ondersteunt samenvoegen met andere instanties, waardoor ontwikkelaars verschillende opties kunnen aggregeren. Deze functie is nuttig bij het combineren van instellingen vanuit verschillende bronnen.

## Annotaties {#annotations}

### Doel {#purpose-1}
Voor gemak kan `ElementEventOptions` worden geconfigureerd met behulp van annotaties. Deze annotaties bieden een beknoptere en expressievere manier om gebeurtenisopties in te stellen.

### Voorbeeld {#example-1}
Overweeg het volgende voorbeeld van een annotatie:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
