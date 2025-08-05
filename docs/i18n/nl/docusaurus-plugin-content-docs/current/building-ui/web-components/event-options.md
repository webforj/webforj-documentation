---
sidebar_position: 4
title: Event Options
_i18n_hash: 8bf57e40eec8e571f3d62266e388f114
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` is een veelzijdige webforJ-tool die is ontworpen om configuratie-instellingen voor `Element`-evenementen binnen webforJ-toepassingen te encapsuleren en te beheren. Als een container voor verschillende opties stelt het ontwikkelaars in staat om precies te bepalen hoe evenementen die met elementen zijn geassocieerd, moeten worden verwerkt.

## Evenementgegevens {#event-data}

Evenementgegevens zijn een belangrijke functie van `ElementEventOptions`, waardoor ontwikkelaars specifieke informatie aan de evenementopties kunnen toevoegen. Deze functionaliteit vergemakkelijkt het doorgeven van aangepaste gegevens van de client naar de server wanneer een evenement wordt geactiveerd. Deze mogelijkheid is essentieel voor het overbrengen van extra context of parameters die aan het evenement zijn verbonden, en zorgt ervoor dat informatie kan worden geraadpleegd en gebruikt zonder dat extra trips naar de client gemaakt hoeven te worden.

Neem bijvoorbeeld een scenario waarin u een knopklikevenement heeft en u de gebruikersnaam van de huidige gebruiker samen met het evenement wilt doorgeven. In plaats van telkens de gebruikersnaam van de client op te vragen, kunt u deze informatie samen met het evenement als gegevens verzenden.

:::tip
Voor meer informatie, zie de [evenementen](../../building-ui/events) en [Client/Server Interactie](../../architecture/client-server) pagina's.
:::

Om gegevens toe te voegen aan de evenementopties, kunt u de `addData()`-methode gebruiken.

<!-- ### Voorbeeld -->

## JavaScript uitvoeren {#executing-javascript}

De `ElementEventOptions`-klasse stelt ontwikkelaars in staat om JavaScript-code op te geven die aan de clientzijde moet worden geëvalueerd voordat het bijbehorende evenement wordt geactiveerd. Deze functie stelt cliënten in staat om evenementgegevens voor te bereiden of extra evenementen te triggeren indien nodig. Dit is nuttig in veel gevallen, bijvoorbeeld wanneer u formuliergegevens aan de clientzijde wilt valideren voordat u deze verzendt via een formulierverzendingsgebeurtenis.

### Gebruik {#usage}
Om de evenementcode in te stellen, gebruikt u de `setCode()`-methode.

## Evenementen filteren {#filtering-events}

`ElementEventOptions` bevat een functie voor het instellen van een filterexpressie die aan de client moet worden geëvalueerd voordat het evenement wordt geactiveerd. Deze filterexpressie stelt de client in staat om te bepalen of het evenement moet doorgaan of moet worden gestopt op basis van bepaalde voorwaarden. Overweeg een invoerveld waarin u een evenement wilt activeren alleen als de ingevoerde tekst aan specifieke criteria voldoet, zoals een minimale lengte.

### Gebruik {#usage-1}
Om de evenementfilter in te stellen, gebruikt u de `setFilter()`-methode.

## Debouncing en throttling {#debouncing-and-throttling}

### Doel {#purpose}
`ElementEventOptions` biedt mechanismen voor debouncing en throttling van evenementen. Deze functies zijn nuttig voor het controleren van de frequentie van evenementlisteners, zodat ze alleen onder bepaalde voorwaarden worden geactiveerd.

### Gebruik {#usage-2}
- Om debounce in te stellen, gebruikt u de `setDebounce`-methode.
- Om throttle in te stellen, gebruikt u de `setThrottle`-methode.

### Voorbeeld {#example}
In scenario's waarin u snel gebruikersinvoer wilt verwerken, zoals in zoekinvoervelden, kunt u debounce gebruiken om de uitvoering te vertragen totdat de gebruiker is gestopt met typen.

## Evenementopties samenvoegen {#merging-event-options}

De `ElementEventOptions`-klasse ondersteunt het samenvoegen met andere instanties, waardoor ontwikkelaars verschillende opties kunnen aggregeren. Deze functie is nuttig bij het combineren van instellingen uit verschillende bronnen.

## Annotaties {#annotations}

### Doel {#purpose-1}
Voor gemak kan `ElementEventOptions` worden geconfigureerd met behulp van annotaties. Deze annotaties bieden een meer beknopte en expressieve manier om evenementopties in te stellen.

### Voorbeeld {#example-1}
Overweeg de volgende voorbeeldannotatie:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
