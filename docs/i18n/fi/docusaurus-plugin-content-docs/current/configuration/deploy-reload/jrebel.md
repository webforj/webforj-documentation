---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel on Java-kehitystyökalu, joka integroituu JVM:ään havaitakseen koodimuutokset ja korvatakseen muokatut luokat suoraan muistissa, mikä mahdollistaa kehittäjien nähdä koodimuutokset heti ilman palvelimen uudelleenkäynnistystä.

Kun luokkaan, metodiin tai kenttään tehdään muutos, JRebel kääntää ja injektoi päivitetyn byte-koodin lennossa, jolloin koko palvelimen uudelleenkäynnistystä ei tarvita. Kohdistamalla muutokset suoraan käynnissä olevaan sovellukseen JRebel virtaviivaistaa kehitystyön prosessia, säästäen aikaa ja säilyttäen sovelluksen tilan, mukaan lukien käyttäjäistunnot.

:::tip Frontend-muutokset
Muutokset `src/main/frontend`-hakemistossa käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -toiminnolla, joka jälleenrakentaa ne ja päivittää selaimen palvelimen rinnalla.
:::

## Asennus {#installation}

Virallisella JRebel-sivustolla on [pikakäynnistysohjeet](https://www.jrebel.com/products/jrebel/learn), joiden avulla voit saada tuotteen toimimaan erilaisissa suosituissa IDE:issä. Noudata näitä ohjeita integroidaksesi JRebel kehitysympäristöösi.

Kun asetukset on viimeistelty, avaa webforJ-projekti ja varmista, että jetty `scan` -ominaisuus `pom.xml` tiedostossa on asetettu arvoon `0`, jotta palvelimen automaattinen uudelleenkäynnistys poistuu käytöstä. Kun tämä on tehty, käytä seuraavaa komentoa:

```bash
mvn jetty:run
```

Jos kaikki on tehty oikein, JRebel tulostaa lokitietoja terminaaliin, ja ohjelmaan tehdyt muutokset pitäisi näkyä pyynnöstä.

:::info Muutosten näkeminen
Jos muutos tehdään näkymään tai komponenttiin, joka on jo näkyvissä, JRebel ei pakota sivun uudelleenlatausta, koska palvelinta ei uudelleenkäynnistetä.
:::
