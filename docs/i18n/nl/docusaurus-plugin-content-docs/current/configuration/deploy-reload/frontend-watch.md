---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
De frontend watch rebuildt de bronnen onder `src/main/frontend` terwijl de app draait en verzendt de uitvoer naar de browser. Het is de ontwikkelingszijde van de [frontend bundler](/docs/managing-resources/bundler/overview) en vereist dat `webforj.devtools.livereload.enabled` aan staat, zie de [instellingen](/docs/configuration/deploy-reload/overview#settings).

## Running the watch {#running-the-watch}

Voer het `watch` doel uit voordat het doel dat de app start. Een archetypeproject stelt dit in als zijn standaarddoel, dus `mvn` zonder argumenten voert beide uit:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

Om de watch als een standalone build-stap uit te voeren, zie [Build and tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## How the output applies {#how-the-output-applies}

De browseracties zijn afhankelijk van de geproduceerde uitvoer, niet van het bewerkte bestand:

| Uitvoer | Browseractie |
|---|---|
| Stylesheet, van een `.css`, `.scss`, `.sass`, of `.less` bron | Toegepast ter plaatse. Geen herlaad, formuliergegevens en scrollpositie blijven. |
| Afbeelding | Gewisseld ter plaatse. Geen herlaad. |
| Elke andere uitvoer, zoals gecompileerde `.ts`, `.tsx`, of `.js` | De weergave herlaadt. |

Wanneer één rebuild meerdere bestanden produceert, past de browser ze ter plaatse alleen toe als elk bestand kwalificeert. Anders herlaadt het één keer, zodat een wijziging nooit gedeeltelijk wordt toegepast.

## During a server restart {#during-a-server-restart}

Een Java-wijziging zonder een [hotswap tool](/docs/configuration/deploy-reload/hotswap) herstart de server. Tijdens de herstart:

- Toegepaste stijlen blijven op de pagina.
- Een indicator geeft aan terwijl de server niet beschikbaar is. Het verschijnt alleen voor een herstart, niet voor een handmatige herlading.
- De pagina herlaadt wanneer de app klaar is, niet eerder.

Een toevoeging of verwijdering van `@BundleEntry` wordt van kracht wanneer die herstart is voltooid.
