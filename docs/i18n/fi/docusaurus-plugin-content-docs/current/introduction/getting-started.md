---
title: Getting Started
sidebar_position: 2
_i18n_hash: 32a742a43fe6dd9e983eaf428e04e06d
---
Tämä artikkeli kuvaa vaiheet uuden webforJ-sovelluksen luomiseksi käyttäen webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes tarjoavat valmiiksi konfiguroituja projektirakenteita ja aloituskoodia, jotta voit saada projektin nopeasti käyntiin. 
Uuden webforJ-sovelluksen luomiseksi archetypesta voit käyttää [startforJ](#using-startforj) tai [komentoriviltä](#using-the-command-line). 

:::tip Esivaatimukset
Ennen kuin aloitat, tarkista tarvittavat [esivaatimukset](./prerequisites) webforJ:n asentamiselle ja käytölle.
:::


## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ archetypen perusteella. Tämä aloitusprojekti sisältää kaikki vaaditut riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa rakentamisen heti.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Mukauttaminen startforJ:n avulla {#customizing-with-startforj}

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmätunnus, Artefaktitunnus)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- Flavor

Valittavana on kaksi makuvalintaa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Tavanomainen webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus Spring Boot -tuen kanssa

:::caution Spring Boot -tuki
Spring Boot -maku on saatavilla vain webforJ-versiossa 25.02 ja korkeammissa. Jos valitset tämän vaihtoehdon, varmista, että valitset yhteensopivan version.
:::

:::tip Saatavilla olevat Archetypes
webforJ sisältää useita ennalta määriteltyjä archetypeja, jotka auttavat sinua pääsemään nopeasti alkuun. Saat täydellisen luettelon saatavilla olevista archetypeista, katso [archetypes-luettelo](../building-ui/archetypes/overview).
:::

Käyttäen näitä tietoja, startforJ luo perustason projektin valitsemastasi archetypesta tehdyillä mukautuksilla. 
Voit valita projektisi lataamisen ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi ja siirry vaiheeseen [sovelluksen suorittaminen](#running-the-app).

## Käyttäen komentoriviä {#using-the-command-line}


Jos suosittelet komentorivin käyttöä, voit luoda projektin suoraan käyttäen Maven archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Sovelluksen suorittaminen {#running-the-app}

Ennen kuin suoritat sovelluksesi, asenna [esivaatimukset](./prerequisites.md), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juurikansioon ja suorita seuraava komento:

```bash
# tavanomaiselle webforj-sovellukselle
mvn jetty:run

# webforj + Spring Boot -sovellukselle
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.

:::info Lisensointi ja vesileima
Tietoa käyttämättömissä projekteissa olevasta vesileimasta, katso [Lisensointi ja vesileima](../configuration/licensing-and-watermark).
:::
