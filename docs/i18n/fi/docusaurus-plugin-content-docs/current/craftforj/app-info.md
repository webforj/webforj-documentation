---
title: App Info
sidebar_position: 10
description: >-
  Read the versions, Java runtime, and project root of the app craftforJ is
  attached to.
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
App info raportoi, mitä sovelluksesi oikeasti käyttää, mikä ei aina vastaa sitä, mitä `pom.xml` sanoo sen käyttävän. Sen lisäksi, että se kattaa webforJ:n ja BBj Services -version, se kattaa myös Java-ajonaika, käyttöjärjestelmän ja mistä sovellus sijaitsee levyllä.

![Sovelluksen tietotab](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Kaksi näistä arvosta vaikuttaa siihen, miten craftforJ käyttäytyy:

- **Projektin juurihakemisto** on se paikka, josta craftforJ etsii lähteitäsi. [Lähteisiin kirjoittaminen](/docs/craftforj/source-changes) ei voi toimia, kun se on väärin, joten aseta [`project-root`](/docs/craftforj/configuration#project-root), jos ilmoitettu arvo ei vastaa projektiasi.
- **Java-ajonaika** määrää, kuinka perusteellisesti avustajan [Java-muutoksia](/docs/craftforj/ai#it-writes-java) arvioidaan, koska täydellinen arviointi tarvitsee kääntäjän.

:::tip Ongelman ilmoittaminen
Sisällytä kaikki tällä sivulla, yhdessä lokin kanssa, joka on ladattu craftforJ:n vianetsintäasetuksista.
:::
