---
title: Frontend-tarkkailu
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
Frontend-watch rakentaa lähteet `src/main/frontend` -hakemistossa sovelluksen ollessa käynnissä ja lähettää tulosten selaimelle. Se on [frontend-bundler](/docs/managing-resources/bundler/overview) kehityspuoli ja edellyttää, että `webforj.devtools.livereload.enabled` on päällä, katso [asetukset](/docs/configuration/deploy-reload/overview#settings).

## Running the watch {#running-the-watch}

Suorita `watch` -tavoite ennen tavoitetta, joka käynnistää sovelluksen. Arkkitehti-projekti asettaa tämän oletustavoitteekseen, joten ilman argumentteja `mvn` suorittaa molemmat:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

Voit suorittaa watchin erillisenä rakennusvaiheena, katso [Build and tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## How the output applies {#how-the-output-applies}

Selaimen toiminta riippuu tuotetusta tuloksesta, ei muokatusta tiedostosta:

| Output | Browser action |
|---|---|
| Tyylitiedosto, joka tulee `.css`, `.scss`, `.sass` tai `.less` -lähteestä | Sovelletaan paikalleen. Ei latausta, lomaketiedot ja vierityspaikka pysyvät. |
| Kuva | Vaihdetaan paikalleen. Ei latausta. |
| Mikä tahansa muu tulos, kuten käännetty `.ts`, `.tsx` tai `.js` | Näkymä lataa uudelleen. |

Kun yksi uudelleenrakennus tuottaa useita tiedostoja, selain soveltaa niitä paikalleen vain, jos jokainen tiedosto täyttää vaatimukset. Muuten se lataa kerran, joten muutos ei koskaan päivity osittain.

## During a server restart {#during-a-server-restart}

Java-muutos ilman [hotswap-työkalua](/docs/configuration/deploy-reload/hotswap) käynnistää palvelimen uudelleen. Uudelleenkäynnistyksen aikana:

- Sovelletut tyylit pysyvät sivulla.
- Indikaattori näkyy, kun palvelin on alas. Se ilmestyy vain uudelleenkäynnistyksen ajaksi, ei manuaaliselle lataukselle.
- Sivu lataa uudelleen, kun sovellus on valmis, ei ennen.

`@BundleEntry` -lisäys tai poisto tulee voimaan, kun tämä uudelleenkäynnistys on valmis.
