---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
Navigatie blokkering voegt een of meer lagen van controle toe aan de gehele onderliggende router-API. Als er blokkering handlers aanwezig zijn, zal navigatie als volgt worden voorkomen:

Als de navigatie wordt getriggerd door iets dat op routerniveau wordt geregeld, kunt u een taak uitvoeren of een UI-prompt aan de gebruiker tonen om de actie te bevestigen. Elke component die de `WillLeaveObserver` in de [routeboom](../route-hierarchy/overview) implementeert, zal worden aangeroepen. De implementator moet `accept` aanroepen om de navigatie voort te zetten of `reject` om deze te blokkeren. Als meerdere componenten de `WillLeaveObserver` in de routeboom implementeren, zullen de veto handlers sequentieel in omgekeerde volgorde worden uitgevoerd.

:::info Praktisch Voorbeeld van Veto Verwerking
Om te zien hoe veto's in de praktijk werken, verwijzen we naar de [Voorbeelden van het Gebruik van Lifecycle Observers](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Voor pagina-evenementen die niet direct kunnen worden gecontroleerd, interfereert de router niet of handhaaft hij geen specifiek gedrag. Ontwikkelaars kunnen echter nog steeds luisteren naar het [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) evenement om een laatste poging te doen om de gebruiker te waarschuwen voor niet-opgeslagen gegevens indien nodig.

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Terugknop van de browser {#browser-back-button}

De terugknop werkt buiten de controle van webapplicaties, waardoor het moeilijk is om de actie in alle browsers consistent te onderscheppen of te voorkomen. In plaats van te proberen de terugknop te blokkeren, is het effectiever om uw UI/UX zo te ontwerpen dat de impact wordt gemitigeerd. Overweeg strategieën zoals het opslaan van niet-opgeslagen gegevens in [sessieopslag](../../advanced/web-storage#session-storage), zodat als een gebruiker weg navigeert en terugkeert, hun voortgang veilig wordt hersteld. Deze benadering zorgt voor gegevensbescherming zonder te vertrouwen op onbetrouwbaar browsergedrag.
