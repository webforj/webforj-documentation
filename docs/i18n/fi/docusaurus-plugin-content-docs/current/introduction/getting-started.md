---
title: Aloittaminen
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: ea669974a5f1b3a02a162f89bb16c6e3
---
Tässä artikkelissa kuvataan vaiheet uuden webforJ-sovelluksen luomiseksi käyttämällä webforJ [archetypeja](../building-ui/archetypes/overview.md). Archetypet tarjoavat valmiiksi määritettyjä projektirakenteita ja aloituskoodia, jotta voit saada projektin nopeasti käyntiin. 
Voit luoda uuden webforJ-sovelluksen archetypesta käyttämällä [startforJ](#using-startforj) tai [komentoriviä](#using-the-command-line).

:::tip Edellytykset
Ennen aloitusta tarkista tarvittavat [edellytykset](./prerequisites) webforJ:n asentamiseksi ja käyttämiseksi.
:::

## Käyttäen startforJ {#using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitsemallesi webforJ archetypelle. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiin asettelun, jolloin voit alkaa rakentaa sen päälle heti.

### Mukauttaminen startforJ:llä {#customizing-with-startforj}

Kun luot sovellusta [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metadata (Sovelluksen nimi, Ryhmän ID, Artifact ID)
- webforJ-versio ja Java-versio
- Teeman väri ja ikoni
- Archetype
- Käyttöympäristö

**Käyttöympäristö** -valinta päättää, missä sovellus toimii, oletuksena **Servlet-kontti**:
  - **Servlet-kontti**: WAR Jettylle, Tomcatille tai muulle servlet-kontille
  - **Spring Boot**: suoritettavan JAR-tiedosto, jossa on upotettu palvelin
  - **BBjServices**: sovellus, joka toimii BBjServicesin kautta [BBjShip](/docs/configuration/bbjship/overview)

:::tip Saatavilla olevat archetypet
webforJ:ssa on useita ennalta määriteltyjä archetypeja, jotka auttavat sinua pääsemään nopeasti alkuun. Katso täydellinen luettelo saatavilla olevista archetypeista [archetypet-katalogista](../building-ui/archetypes/overview).
:::

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä valitsemillasi mukautuksilla.
Voit valita ladata projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

Kun olet ladannut projektisi, avaa projektikansio IDE:ssäsi.

## Käyttäen komentoriviä {#using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda projektin suoraan käyttämällä Maven-archetypea:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
