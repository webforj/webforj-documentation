---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: a08d56654914719e12d1401d263c7956
---
Navigatieblokkering voegt een of meerdere lagen van controle toe aan de gehele onderliggende router-API. Als er blokkeringhandlers aanwezig zijn, wordt navigatie als volgt voorkomen:

Als de navigatie wordt geactiveerd door iets dat op routerniveau wordt gecontroleerd, kun je elke taak uitvoeren of een UI-prompt aan de gebruiker tonen om de actie te bevestigen. Elke component die de `WillLeaveObserver` in de [routeboom](../route-hierarchy/overview) implementeert, zal worden aangeroepen. De implementator moet `accept` aanroepen om de navigatie voort te zetten of `reject` om deze te blokkeren. Als meerdere componenten de `WillLeaveObserver` in de boom van de route implementeren, worden de veto-handlers sequentieel in omgekeerde volgorde uitgevoerd.

:::info Praktisch Voorbeeld van Veto-Verwerking
Om te zien hoe vetoën in de praktijk werken, kijk naar de [Voorbeelden van het gebruik van Lifecycle Observers](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

Voor pagina-evenementen die niet rechtstreeks kunnen worden gecontroleerd, interfereert de router niet of handhaaft geen specifiek gedrag. Ontwikkelaars kunnen echter nog steeds luisteren naar het [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) evenement om een laatste poging te doen om de gebruiker te waarschuwen voor niet-opgeslagen gegevens indien nodig.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Terugknop van de browser {#browser-back-button}

De terugknop werkt buiten de controle van webtoepassingen, waardoor het moeilijk is om de actie consistent in alle browsers te onderscheppen of te voorkomen. In plaats van te proberen de terugknop te blokkeren, is het effectiever om je UI/UX zo te ontwerpen dat de impact wordt verminderd. Overweeg strategieën zoals het opslaan van niet-opgeslagen gegevens in [sessie-opslag](../../advanced/web-storage#session-storage), zodat als een gebruiker weg navigeert en terugkeert, hun voortgang veilig wordt hersteld. Deze aanpak zorgt voor gegevensbescherming zonder te vertrouwen op onbetrouwbaar browsergedrag.
