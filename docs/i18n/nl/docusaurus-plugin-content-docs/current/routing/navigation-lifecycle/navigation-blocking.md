---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
Navigatieblokkering voegt een of meer lagen van controle toe aan de hele onderliggende router-API. Als er blokkerende handlers aanwezig zijn, wordt de navigatie als volgt verhinderd:

Als de navigatie wordt geactiveerd door iets dat op routerniveau wordt gecontroleerd, kun je elke taak uitvoeren of een UI-prompt aan de gebruiker tonen om de actie te bevestigen. Elk component dat de `WillLeaveObserver` implementeert in de [routeboom](../route-hierarchy/overview) zal worden aangeroepen. De implementator moet `accept` aanroepen om de navigatie voort te zetten of `reject` om deze te blokkeren. Als meerdere componenten de `WillLeaveObserver` in de boom van de route implementeren, worden de veto-handlers sequentieel in omgekeerde volgorde uitgevoerd.

:::info Praktisch Voorbeeld van Veto-afhandeling
Om te zien hoe vetoën in de praktijk werken, verwijs naar de [Voorbeelden van het Gebruik van Lifecycle Observers](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

Voor pagina-evenementen die niet direct kunnen worden gecontroleerd, interfereert de router niet of legt geen specifiek gedrag op. Ontwikkelaars kunnen echter nog steeds luisteren naar het [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) evenement om een laatste poging te doen om de gebruiker te waarschuwen over niet-opgeslagen gegevens indien nodig.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Terugknop van de browser {#browser-back-button}

De terugknop opereert buiten de controle van webapplicaties, waardoor het moeilijk is om zijn actie in alle browsers consistent te onderscheppen of te voorkomen. In plaats van te proberen de terugknop te blokkeren, is het effectiever om je UI/UX zo te ontwerpen dat de impact wordt gemitigeerd. Overweeg strategieën zoals het opslaan van niet-opgeslagen gegevens in [sessieopslag](../../advanced/web-storage#session-storage), zodat als een gebruiker navigatie verlaat en terugkeert, hun voortgang veilig wordt hersteld. Deze aanpak zorgt voor gegevensbescherming zonder te vertrouwen op onbetrouwbaar browsergedrag.
