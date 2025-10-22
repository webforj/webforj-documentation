---
title: Getting Started
sidebar_position: 2
_i18n_hash: 24c0a494b270fb4ea83106005e173ae8
---
Tässä artikkelissa käsitellään vaiheita uuden webforJ-sovelluksen luomiseksi käyttäen webforJ [archetypet](../building-ui/archetypes/overview.md). Archetypet tarjoavat esikonfiguroituja projektirakenteita ja aloituskoodia, jotta voit saada projektisi nopeasti käyntiin. 
Uuden webforJ-sovelluksen luomiseksi archetypestä voit käyttää [startforJ](#using-startforj) tai [komentoriviä](#using-the-command-line).

:::tip Esivaatimukset
Ennen aloittamista tarkista tarvittavat [esivaatimukset](./prerequisites) webforJ:n asentamiseksi ja käyttämiseksi.
:::

## Käyttämällä startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka generoi minimiaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiin ulkoasun, jotta voit alkaa rakentaa sitä heti.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Mukauttaminen startforJ:n avulla {#customizing-with-startforj}

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmän ID, Artefaktin ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- Flavor

Valittavana on kaksi flavor-vaihtoehtoa, joista "webforJ Only" on oletus:
  - **webforJ Only**: Standardi webforJ-sovellus
  - **webforJ + Spring Boot**: webforJ-sovellus Spring Boot -tuella

:::caution Spring Boot -tuki
Spring Boot -flavor on saatavilla vain webforJ-versiossa 25.02 ja korkeammissa. Jos valitset tämän vaihtoehdon, varmista, että valitset yhteensopivan version.
:::

:::tip Saatavilla olevat Archetypet
webforJ sisältää useita ennalta määriteltyjä archetypeja auttaakseen sinua pääsemään nopeasti alkuun. Täydellisen luettelon saatavilla olevista archetypeista löydät [archetype-katalogista](../building-ui/archetypes/overview).
:::

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä ja valitsemillasi mukautuksilla. 
Voit valita, haluatko ladata projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi ja siirry eteenpäin [sovelluksen ajamiseen](#running-the-app).

## Käyttämällä komentoriviä {#using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda projektin suoraan käyttäen Maven-archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Sovelluksen ajaminen {#running-the-app}

Ennen sovelluksesi käynnistämistä asenna [esivaatimukset](./prerequisites.md), jos et ole vielä tehnyt niin. 
Siirry sitten projektin juurihakemistoon ja suorita seuraava komento:

```bash
# standardille webforj-sovellukselle
mvn jetty:run

# webforj + Spring Boot
mvn spring-boot:run
```

Kun palvelin on käynnissä, avaa selain ja siirry osoitteeseen [http://localhost:8080](http://localhost:8080) nähdäksesi sovelluksen.
