---
title: Frontendin seuranta
sidebar_position: 20
description: >-
  Rakenna src/main/frontend-kansion lähteet uudelleen webforJ-sovelluksen
  käydessä: tyylit ja kuvat päivittyvät suoraan, skriptit lataavat näkymän
  uudelleen.
_i18n_title_hash: 87d66a6e5b9e657405313f309833cd07
_i18n_description_hash: d5b96b8ea22c6930a214e543b2de01da
_i18n_hash: 065e4f5ad8868a6758e682f38e2dd3b9
---
Frontend-watch rakennuslähteet `src/main/frontend`-kansiossa, kun sovellus on käynnissä, ja lähettää ulostuloa selaimeen. Se on [frontend-bundlerin](/docs/managing-resources/bundler/overview) kehityspuoli ja vaatii `webforj.devtools.livereload.enabled`-asetuksen olevan päällä, katso [asetukset](/docs/configuration/deploy-reload/overview#settings).

## Katselun ajaminen {#running-the-watch}

Suorita `watch`-tavoite ennen tavoitetta, joka käynnistää sovelluksen. Archetype-projekti asettaa tämän oletustavoitteekseen, joten `mvn` ilman argumentteja suorittaa molemmat:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

BBjServices-sovelluksessa katselu aloittaa [kehityksen](/docs/configuration/deploy-reload/bbjservices) itse:

```bash
mvn compile webforj:watch
```

Jos haluat suorittaa katselun itsenäisenä rakennusvaiheena, katso [Rakennus ja testit](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Kuinka ulostulo vaikuttaa {#how-the-output-applies}

Selaimen toiminta riippuu tuotetusta ulostulosta, ei muuratusta tiedostosta:

| Ulostulo | Selaimen toiminta |
|---|---|
| Tyylitiedosto, lähteinä `.css`, `.scss`, `.sass` tai `.less` | Kohdistuu paikalleen. Ei latausta, lomaketiedot ja vierityspaikka pysyvät. |
| Kuva | Vaihdetaan paikalleen. Ei latausta. |
| Kaikki muut ulostulot, kuten käännetyt `.ts`, `.tsx` tai `.js` | Näkymä latautuu uudelleen. |

Kun yksi uudelleenrakennus tuottaa useita tiedostoja, selain kohdistaa ne paikalleen vain, jos jokainen tiedosto täyttää vaatimukset. Muussa tapauksessa se latautuu kerran, joten muutos ei koskaan päivity osittain.

## Palvelimen uudelleenkäynnistyksen aikana {#during-a-server-restart}

Java-muutos ilman [hotswap-työkalua](/docs/configuration/deploy-reload/hotswap) käynnistää palvelimen uudelleen. Uudelleenkäynnistyksen aikana:

- Käytössä olevat tyylit säilyvät sivulla.
- Indikaattori näyttää, kun palvelin on pois päältä. Se ilmestyy vain uudelleenkäynnistystä varten, ei manuaalista latausta varten.
- Sivua latautuu, kun sovellus on valmis, ei ennen sitä.

`@BundleEntry`-lisäys tai -poisto tulee voimaan, kun kyseinen uudelleenkäynnistys on valmis.
