---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is een veelzijdige webforJ-hulpmiddel ontworpen om configuratie-instellingen voor `Element`-gebeurtenissen binnen webforJ-toepassingen in te kapselen en te beheren. Als een container voor verschillende opties stelt het ontwikkelaars in staat om nauwkeurig te bepalen hoe gebeurtenissen die zijn gekoppeld aan elementen moeten worden verwerkt.

## Gegevens van evenementen {#event-data}

Gegevens van evenementen zijn een belangrijke functie van `ElementEventOptions`, waarmee ontwikkelaars specifieke informatie aan de evenementopties kunnen koppelen. Deze functionaliteit vergemakkelijkt het doorgeven van aangepaste gegevens van de client naar de server wanneer een evenement wordt geactiveerd. Deze mogelijkheid is van cruciaal belang voor het overbrengen van extra context of parameters die aan het evenement zijn gekoppeld en maakt het mogelijk om informatie te verkrijgen en te gebruiken zonder dat extra verzoeken naar de client hoeven te worden gedaan.

Bijvoorbeeld, beschouw een scenario waarin u een knopklikgebeurtenis heeft, en u wilt de gebruikersnaam van de huidige gebruiker doorgeven samen met het evenement. In plaats van elke keer de gebruikersnaam van de client op te vragen, stuurt u deze informatie samen met het evenement als gegevens.

:::tip
Voor meer informatie, zie de [events](/docs/building-ui/events) en [Client/Server Interaction](/docs/architecture/client-server) pagina's.
:::

Om gegevens aan de evenementopties toe te voegen, kunt u de `addData()`-methode gebruiken.

<!-- ### Voorbeeld -->

## JavaScript uitvoeren {#executing-javascript}

De `ElementEventOptions`-klasse stelt ontwikkelaars in staat om JavaScript-code op te geven die aan de clientzijde moet worden geëvalueerd voordat het bijbehorende evenement wordt geactiveerd. Deze functie stelt clients in staat om evenementgegevens voor te bereiden of extra evenementen te triggeren indien nodig. Dit is in veel gevallen nuttig, bijvoorbeeld wanneer u formuliergegevens aan de clientzijde wilt valideren voordat u deze indient via een formulierindieningsevenement.

### Gebruik {#usage}
Om de evenementcode in te stellen, gebruikt u de `setCode()`-methode.

## Evenementen filteren {#filtering-events}

`ElementEventOptions` bevat een functie voor het instellen van een filterexpressie die aan de clientzijde moet worden geëvalueerd voordat het evenement wordt geactiveerd. Deze filterexpressie stelt de client in staat om te bepalen of het evenement moet doorgaan of moet worden gestopt op basis van bepaalde voorwaarden. Overweeg een invoerveld waar u een evenement alleen wilt triggeren als de ingevoerde tekst aan specifieke criteria voldoet, zoals een minimale lengte.

### Gebruik {#usage-1}
Om de evenementfilter in te stellen, gebruikt u de `setFilter()`-methode.

## Debouncing en throttling {#debouncing-and-throttling}

### Doel {#purpose}
`ElementEventOptions` biedt mechanismen voor het debouncen en throttlen van evenementen. Deze functies zijn nuttig voor het controleren van de frequentie van gebeurtenislokators, zodat ze alleen onder bepaalde voorwaarden worden geactiveerd.

### Gebruik {#usage-2}
- Om debounce in te stellen, gebruikt u de `setDebounce`-methode.
- Om throttle in te stellen, gebruikt u de `setThrottle`-methode.

### Voorbeeld {#example}
In scenario's waarin u snel gebruikersinvoer wilt verwerken, zoals zoekinvoervelden, kunt u debounce gebruiken om de uitvoering uit te stellen totdat de gebruiker klaar is met typen.

## Evenementopties samenvoegen {#merging-event-options}

De `ElementEventOptions`-klasse ondersteunt het samenvoegen met andere instanties, waardoor ontwikkelaars verschillende opties kunnen aggregeren. Deze functie is nuttig bij het combineren van instellingen vanuit verschillende bronnen.

## Annotaties {#annotations}

### Doel {#purpose-1}
Voor het gemak kan `ElementEventOptions` worden geconfigureerd met behulp van annotaties. Deze annotaties bieden een beknoptere en expressievere manier om evenementopties in te stellen.

### Voorbeeld {#example-1}
Overweeg de volgende voorbeeldannotatie:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
