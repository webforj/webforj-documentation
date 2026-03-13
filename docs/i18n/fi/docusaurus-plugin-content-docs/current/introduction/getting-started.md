---
title: Getting Started
sidebar_position: 2
_i18n_hash: e0271a7db26a5c4b3fdc29508119aade
---
Tämä artikkeli kuvaa vaiheet uuden webforJ-sovelluksen luomiseksi käyttämällä webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes tarjoavat esikonfiguroituja projektirakenteita ja aloituskoodia, jotta voit saada projektin nopeasti toimimaan.
Uuden webforJ-sovelluksen luomiseksi archetypesta voit käyttää [startforJ](#using-startforj) tai [komentoriviä](#using-the-command-line). 

:::tip Esivaatimukset
Ennen kuin aloitat, tarkista tarvittavat [esivaatimukset](./prerequisites) webforJ:n asentamiseen ja käyttöön.
:::


## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ archetypen perusteella. Tämä aloitusprojekti sisältää kaikki vaadittavat riippuvuudet, konfiguraatiotiedostot ja valmiin asettelun, jotta voit aloittaa rakentamisen heti.

### Mukauttaminen startforJ:llä {#customizing-with-startforj}

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmän tunnus, Artefaktin tunnus)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- Flavor

Valittavana on kaksi makuvaihtoehtoa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Vakioprojekti webforJ:llä
  - **webforJ + Spring Boot**: webforJ-sovellus Spring Boot -tukea varten

:::tip Saatavilla olevat archetypet
webforJ tulee useiden ennalta määriteltyjen archetyppien kanssa auttaakseen sinua pääsemään nopeasti alkuun. Täydellisen luettelon saatavilla olevista archetyypeistä katso [archetypes-katalogista](../building-ui/archetypes/overview).
:::

Tämän tiedon avulla startforJ luo perustason projektin valitsemastasi archetypesta valitsemillasi mukautuksilla.
Voit valita ladattavaksi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi.

## Käyttäen komentoriviä {#using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda projektin suoraan käyttämällä Maven archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
