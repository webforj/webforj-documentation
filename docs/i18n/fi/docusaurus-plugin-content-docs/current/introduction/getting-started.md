---
title: Getting Started
sidebar_position: 2
_i18n_hash: 37b65c983d6210a89474156b10af1e93
---
Tämä artikkeli kuvaa vaiheet uuden webforJ-sovelluksen luomiseksi käyttämällä webforJ [archetypeja](../building-ui/archetypes/overview.md). Archetypet tarjoavat esikonfiguroituja projektirakenteita ja aloituskoodia, joten voit saada projektin nopeasti käyttöön. 
Uuden webforJ-sovelluksen luomiseksi archetypesta voit käyttää [startforJ](#using-startforj) tai [komentoriviä](#using-the-command-line).

:::tip Ehdot
Ennen kuin aloitat, tarkista tarvittavat [ehtoja](./prerequisites) webforJ:n asentamiseksi ja käyttämiseksi.
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin ulkoasun, joten voit aloittaa rakentamisen heti.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Mukauttaminen startforJ:llä {#customizing-with-startforj}

Kun luot sovellusta [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä tarjoamalla seuraavat tiedot:

- Perusprojekti metadata (Sovelluksen nimi, Ryhmän ID, Artefaktin ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- Makuvaihtoehto

Valittavana on kaksi makuvaihtoehtoa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Standardi webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus, jossa on Spring Boot -tuki

:::caution Spring Boot -tuki
Spring Boot -makuvaihtoehto on saatavilla vain webforJ-version 25.02 ja korkeammissa versioissa. Jos valitset tämän vaihtoehdon, varmista, että valitset yhteensopivan version.
:::

:::tip Saatavilla olevat Archetypet
webforJ:llä on useita ennalta määriteltyjä archetypeja, jotka auttavat sinua pääsemään nopeasti alkuun. Täydellisen luettelon saatavilla olevista archetypeista, katso [archetype-katalogi](../building-ui/archetypes/overview).
:::

Tämän tiedon avulla startforJ luo perustason projektin valitsemastasi archetypesta valitsemillasi mukautuksilla. 
Voit valita projektin lataamisen ZIP-tiedostona tai julkaisemisen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi ja siirry [sovelluksen ajamiseen](#running-the-app).

## Käyttäen komentoriviä {#using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda projektin suoraan käyttämällä Maven archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen sovelluksesi ajamista asenna [ehtoja](./prerequisites.md), jos et ole vielä. 
Sitten siirry projektin juurikansioon ja suorita seuraava komento:

```bash
# standardille webforj-sovellukselle
mvn jetty:run

# webforj + Spring Boot -sovellukselle
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.

:::info Lisensointi ja vesileima
Tietoa vesileimasta, joka on läsnä lisensoimattomissa projekteissa, katso [Lisensointi ja vesileima](../configuration/licensing-and-watermark).
:::
