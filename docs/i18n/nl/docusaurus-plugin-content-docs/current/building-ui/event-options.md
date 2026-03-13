---
sidebar_position: 7
title: Event Options
_i18n_hash: 4311668d9a6bb9e9cebcf988e515d91a
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is een veelzijdige webforJ-tool die is ontworpen om configuratie-instellingen voor `Element`-evenementen binnen webforJ-toepassingen te encapsuleren en te beheren. Als een container voor verschillende opties stelt het ontwikkelaars in staat om precies te bepalen hoe evenementen die met elementen zijn geassocieerd, moeten worden verwerkt.

## Evenementgegevens {#event-data}

Evenementgegevens zijn een key feature van `ElementEventOptions`, waarmee ontwikkelaars specifieke informatie aan de evenementopties kunnen koppelen. Deze functionaliteit vergemakkelijkt het doorgeven van aangepaste gegevens van de cliënt naar de server wanneer een evenement wordt geactiveerd. Deze mogelijkheid is van cruciaal belang voor het overbrengen van aanvullende context of parameters die aan het evenement zijn gekoppeld, en stelt in staat om informatie toegankelijk te maken en te gebruiken zonder dat er extra verzoeken vanuit de cliënt nodig zijn.

Bijvoorbeeld, beschouw een scenario waarin je een knopklikevenement hebt, en je wilt de gebruikersnaam van de huidige gebruiker samen met het evenement doorgeven. In plaats van elke keer de gebruikersnaam van de cliënt op te vragen, stuur je deze informatie samen met het evenement als gegevens.

:::tip
Voor meer informatie, zie de [evenementen](/docs/building-ui/events) en [Cliënt/Server Interactie](/docs/architecture/client-server) pagina's.
:::

Om gegevens aan de evenementopties toe te voegen, kun je de `addData()`-methode gebruiken.

<!-- ### Voorbeeld -->

## JavaScript uitvoeren {#executing-javascript}

De `ElementEventOptions`-klasse stelt ontwikkelaars in staat om JavaScript-code op te geven die aan de cliëntzijde moet worden geëvalueerd voordat het bijbehorende evenement wordt geactiveerd. Deze functie stelt cliënten in staat om evenementgegevens voor te bereiden of aanvullende evenementen te triggeren indien nodig. Dit is handig in veel gevallen, bijvoorbeeld wanneer je formuliergegevens aan de cliëntzijde wilt valideren voordat je deze indient via een formulierindieningsevenement.

### Gebruik {#usage}
Om de evenementcode in te stellen, gebruik je de `setCode()`-methode.

## Evenementen filteren {#filtering-events}

`ElementEventOptions` bevat een functie voor het instellen van een filterexpressie die aan de cliënt moet worden geëvalueerd voordat het evenement wordt geactiveerd. Deze filterexpressie stelt de cliënt in staat te bepalen of het evenement moet doorgaan of moet worden gestopt op basis van bepaalde voorwaarden. Overweeg een invoerveld waarbij je een evenement alleen wilt triggeren als de ingevoerde tekst aan specifieke criteria voldoet, zoals een minimale lengte.

### Gebruik {#usage-1}
Om de evenementfilter in te stellen, gebruik je de `setFilter()`-methode.

## Debounce en throttling {#debouncing-and-throttling}

### Doel {#purpose}
`ElementEventOptions` biedt mechanismen voor het debouncen en throttlen van evenementen. Deze functies zijn nuttig voor het beheersen van de frequentie van evenementlisteners, zodat ze alleen onder bepaalde voorwaarden worden geactiveerd.

### Gebruik {#usage-2}
- Om debounce in te stellen, gebruik je de `setDebounce`-methode.
- Om throttle in te stellen, gebruik je de `setThrottle`-methode.

### Voorbeeld {#example}
In scenario's waarin je snelle gebruikersinvoer wilt verwerken, zoals zoekinvoervelden, kun je debounce gebruiken om de uitvoering uit te stellen totdat de gebruiker is gestopt met typen.

## Evenementopties samenvoegen {#merging-event-options}

De `ElementEventOptions`-klasse ondersteunt het samenvoegen met andere instanties, zodat ontwikkelaars verschillende opties kunnen aggregeren. Deze functie is nuttig bij het combineren van instellingen uit verschillende bronnen.

## Annotaties {#annotations}

### Doel {#purpose-1}
Voor gemak kan `ElementEventOptions` worden geconfigureerd met behulp van annotaties. Deze annotaties bieden een beknoptere en expressieve manier om evenementopties in te stellen.

### Voorbeeld {#example-1}
Overweeg de volgende voorbeeldannotatie:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
