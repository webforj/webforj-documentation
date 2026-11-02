---
title: Frontend watch
sidebar_position: 20
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 065e4f5ad8868a6758e682f38e2dd3b9
---
De frontend watch rebuildt de bronnen onder `src/main/frontend` terwijl de app draait en stuurt de output naar de browser. Het is de ontwikkelingszijde van de [frontend bundler](/docs/managing-resources/bundler/overview) en vereist dat `webforj.devtools.livereload.enabled` aan staat, zie de [instellingen](/docs/configuration/deploy-reload/overview#settings).

## Het uitvoeren van de watch {#running-the-watch}

Voer het `watch` doel uit vóór het doel dat de app start. Een archetype-project stelt dit in als zijn standaarddoel, zodat `mvn` zonder argumenten beide uitvoert:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

In BBjServices start de watch zelf de [ontwikkelingsrun](/docs/configuration/deploy-reload/bbjservices):

```bash
mvn compile webforj:watch
```

Om de watch als een zelfstandige bouwstap uit te voeren, zie [Bouw en tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Hoe de output wordt toegepast {#how-the-output-applies}

De actie in de browser hangt af van de geproduceerde output, niet van het bewerkte bestand:

| Output | Browseractie |
|---|---|
| Stylesheet, van een `.css`, `.scss`, `.sass` of `.less` bron | Wordt ter plaatse toegepast. Geen herlading, formulierdata en scrollpositie blijven. |
| Afbeelding | Wordt ter plaatse verwisseld. Geen herlading. |
| Elke andere output, zoals gecompileerde `.ts`, `.tsx` of `.js` | De weergave wordt herladen. |

Wanneer één rebuild meerdere bestanden produceert, past de browser deze ter plaatse toe alleen als elk bestand kwalificeert. Anders wordt er één keer herladen, zodat een wijziging nooit gedeeltelijk wordt toegepast.

## Tijdens een serverherstart {#during-a-server-restart}

Een Java-wijziging zonder een [hotswap tool](/docs/configuration/deploy-reload/hotswap) herstart de server. Tijdens de herstart:

- Toegepaste stijlen blijven op de pagina.
- Eenindicator toont aan dat de server niet actief is. Dit verschijnt alleen voor een herstart, niet voor een handmatige herlaad.
- De pagina herlaadt wanneer de app klaar is, niet eerder.

Een toevoeging of verwijdering van `@BundleEntry` gaat in werking wanneer die herstart is voltooid.
