---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5a051bf7c5a9494b21ba5df3629c35b4
---
Tässä artikkelissa kuvataan vaiheet uuden webforJ-sovelluksen luomiseksi käyttäen webforJ [archetypseja](../building-ui/archetypes/overview.md). Archetypit tarjoavat esikonfiguroituja projektiarkkitehtuureja ja aloituskoodia, jotta voit saada projektin nopeasti toimintaan.
Uuden webforJ-sovelluksen luomiseksi archetypestä voit käyttää [startforJ](#using-startforj) tai [komentorivikäskyä](#using-the-command-line).

:::tip Edellytykset
Ennen kuin aloitat, tarkista tarvittavat [edellytykset](./prerequisites) webforJ:n määritykselle ja käytölle.
:::


## Käyttäminen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetyypin perusteella. Tämä aloitusprojekti sisältää kaikki vaatimukset, määritystiedostot ja valmiiksi tehdyn asettelun, jotta voit alkaa työstämään sitä heti.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Mukauttaminen startforJ:n avulla {#customizing-with-startforj}

Kun luot sovellusta [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojekti metadata (Sovelluksen nimi, Ryhmä ID, Artefakti ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja ikoni
- Archetype
- Flavor

Valittavana on kaksi flavor-vaihtoehtoa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Standardi webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus Spring Boot -tuella

:::tip Saatavilla olevat Archetypet
webforJ:ssä on useita ennakkoon määriteltyjä archetyyppejä, joiden avulla pääset käynnistämään nopeasti. Täydellisen luettelon saatavilla olevista archetypeista löydät [archetypes-katalogista](../building-ui/archetypes/overview).
:::

Näiden tietojen avulla startforJ luo perusprojektin valitsemastasi archetypestä valitsemillasi mukautuksilla. Voit valita projektisi lataamisen ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi.

## Käyttäminen komentoriviltä {#using-the-command-line}

Jos suosittelet komentorivipohjaista työskentelyä, voit luoda projektin suoraan käyttämällä Maven-archetypeta:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
