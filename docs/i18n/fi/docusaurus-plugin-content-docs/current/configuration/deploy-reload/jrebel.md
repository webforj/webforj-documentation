---
title: JRebel
_i18n_hash: 9e2b7ce994eb40e656cf61dc4967ec7e
---
JRebel on Java-kehitystyökalu, joka integroituu JVM:ään havaitakseen koodimuutokset ja korvatakseen muutetut luokat suoraan muistissa, jolloin kehittäjät voivat nähdä koodimuutokset heti ilman palvelimen uudelleenkäynnistystä.

Kun muutoksia tehdään luokkaan, metodiin tai kenttään, JRebel kääntää ja injektoi päivitetyn tavukoodin lennossa, jolloin täyden palvelimen uudelleenkäynnistyksen tarve kielletään. Sovellukseen tehdyt muutokset tunnistetaan suoraan, mikä tehostaa kehitystyönkulkuja, säästää aikaa ja säilyttää sovellustilan, mukaan lukien käyttäjäistunnot.

## Asennus {#installation}

Virallisella JRebel-sivustolla on [nopeat aloitusohjeet](https://www.jrebel.com/products/jrebel/learn) tuotteen käynnistämiseen eri suosituissa IDE:issä. Seuraa näitä ohjeita integroidaksesi JRebel kehitysympäristöösi.

Kun asennus on valmis, avaa webforJ-projekti ja varmista, että jetty `scan` -ominaisuus `pom.xml`-tiedostossa on asetettu arvoon `0`, jotta palvelimen automaattinen uudelleenkäynnistys estetään. Kun tämä on tehty, käytä seuraavaa komentoa:

```bash
mvn jetty:run
```

Jos kaikki on tehty oikein, JRebel antaa lokitietoja terminaaliin, ja ohjelmassasi tehdyt muutokset tulisi näkyä pyynnöstä.

:::info Näe muutoksesi
Jos muutos tehdään näkymään tai komponenttiin, joka on jo näkyvissä, JRebel ei pakota sivun uudelleenlatausta, koska palvelinta ei käynnistetä uudelleen.
:::
