---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: dfb60fdaf7a9ffd31ee6fb920e0e8289
---
JRebel on Java-kehitystyökalu, joka integroituu JVM:ään havaitakseen koodimuutoksia ja vaihtaakseen muokatut luokat suoraan muistiin, jolloin kehittäjät voivat nähdä koodimuutokset heti ilman palvelimen uudelleenkäynnistämistä.

Kun muutoksia tehdään luokkaan, metodiin tai kenttään, JRebel kääntää ja injektoi päivitetyn bytekoodin lennossa, mikä eliminoi täyden palvelimen uudelleenkäynnistyksen tarpeen. Soveltamalla muutoksia suoraan käynnissä olevaan sovellukseen, JRebel virtaviivaistaa kehitysprosessia, säästää aikaa ja säilyttää sovelluksen tilan, mukaan lukien käyttäjäsessiot.

:::tip Frontend-muutokset
Muutokset `src/main/frontend` -kansiossa käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -toiminnolla, joka uudelleenrakentaa ne ja päivittää selaimen palvelimen ohella.
:::

## Asennus {#installation}

Virallisella JRebel-sivustolla on [nopeat aloitusohjeet](https://www.jrebel.com/products/jrebel/learn), joiden avulla saat tuotteen toimimaan eri suosituissa IDE:issä. Seuraa näitä ohjeita integroidaksesi JRebelin kehitysympäristöösi.

Kun asetukset on saatu päätökseen, avaa webforJ-projekti ja varmista, että jettyn `scan` -ominaisuus `pom.xml` -tiedostossa on asetettu arvoon `0` palvelimen automaattisen uudelleenkäynnistyksen estämiseksi. Kun tämä on tehty, käytä seuraavaa komentoa:

```bash
mvn jetty:run
```

Jos kaikki on tehty oikein, JRebel tulostaa lokitietoja terminaaliin, ja ohjelmassasi tekemäsi muutokset tulisi näkyä pyydettäessä.

:::info Näe muutoksesi
Jos muutos tehdään näkymään tai komponenttiin, joka on jo esillä, JRebel ei pakota sivun uudelleenlatausta, koska palvelinta ei ole uudelleenkäynnistetty.
:::
