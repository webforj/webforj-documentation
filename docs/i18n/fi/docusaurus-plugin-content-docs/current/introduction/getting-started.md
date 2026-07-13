---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
Tämä artikkeli kuvaa vaiheet uuden webforJ-sovelluksen luomiseksi käyttäen webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes tarjoavat esikonfiguroituja projektirakenteita ja aloituskoodia, joten voit saada projektin nopeasti toimintaan. 
Uuden webforJ-sovelluksen luomiseksi archetypesta voit käyttää [startforJ](#using-startforj) tai [komentoriviltä](#using-the-command-line).

:::tip Edellytykset
Ennen aloittamista, tarkista tarvittavat [edellytykset](./prerequisites) webforJ:n asentamiseksi ja käytöksi.
:::


## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimialoitusprojektin valitun webforJ archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa sen kehittämisen heti.

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div> -->


### Mukauttaminen startforJ:llä {#customizing-with-startforj}

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä-ID, Artefakti-ID)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- Flavor

Valittavana on kaksi flavor-vaihtoehtoa, joista "webforJ Only" on oletusarvo:
  - **webforJ Only**: Vakiovesi webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus, jossa on Spring Boot -tuki

:::tip Saatavilla olevat archetypet
webforJ tulee useiden ennalta määritettyjen archetypien kanssa auttaakseen sinua aloittamaan nopeasti. Täydellisen luettelon saatavilla olevista archetypeista löydät [archetypes-katalogista](../building-ui/archetypes/overview).
:::

Näiden tietojen avulla startforJ luo perusprojektin valitsemastasi archetypesta ja valitsemiesi mukautusten kanssa. 
Voit valita lataavasi projektisi ZIP-tiedostona tai julkaisevat sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi.

## Käyttäen komentoriviä {#using-the-command-line}


Jos haluat käyttää komentoriviä, voit luoda projektin suoraan käyttämällä Maven archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
