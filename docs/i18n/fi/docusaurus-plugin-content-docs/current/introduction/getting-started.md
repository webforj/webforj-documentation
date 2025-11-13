---
title: Aloitus
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
Tässä artikkelissa kuvataan vaiheet uuden webforJ-sovelluksen luomiseksi käyttäen webforJ [archetypeseja](../building-ui/archetypes/overview.md). Archetypet tarjoavat esikonfiguroituja projektirakenteita ja aloituskoodia, jotta voit saada projektin nopeasti toimimaan.
Voit luoda uuden webforJ-sovelluksen archetypestä käyttämällä [startforJ](#using-startforj) tai [komentoriviä](#using-the-command-line).

:::tip Edellytykset
Ennen kuin aloitat, tarkista tarvittavat [edellytykset](./prerequisites) webforJ:n asentamiseksi ja käyttämiseksi.
:::


## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin ulkoasun, jotta voit aloittaa rakentamisen heti.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Mukauttaminen startforJ:llä {#customizing-with-startforj}

Kun luot sovellusta [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmätunnus, Artefaktitunnus)  
- webforJ-versio ja Java-versio
- Teema väri ja kuvake
- Archetype
- Maku

Valittavissa on kaksi makuvaihtoehtoa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Standardi webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus Spring Boot -tuella

:::tip Saatavilla olevat Archetypet
webforJ sisältää useita esimäärättyjä archetypeseja, jotka auttavat sinua aloittamaan nopeasti. Saat täydellisen luettelon saatavilla olevista archetypeseista, katso [archetype-katalogi](../building-ui/archetypes/overview).
:::

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä valitsemiesi mukautusten kanssa.
Voit valita projektisi lataamisen ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi ja siirry eteenpäin [sovelluksen suorittamiseen](#running-the-app).

## Käyttäen komentoriviä {#using-the-command-line}

Jos mieluummin käytät komentoriviä, voit luoda projektin suoraan käyttämällä Maven-archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
