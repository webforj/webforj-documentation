---
title: JRebel
_i18n_hash: e0a60884cfab5835f788e6f225047d2c
---
JRebel on Java-kehitystyökalu, joka integroitui JVM:ään havaitsemaan koodimuutoksia ja korvaamaan muokatut luokat suoraan muistissa, jolloin kehittäjät voivat nähdä koodimuutokset välittömästi ilman palvelimen uudelleenkäynnistämistä.

Kun luokkaan, menetelmään tai kenttään tehdään muutos, JRebel kääntää ja injektoi päivitetyn tavukoodin lennossa, mikä eliminoi tarpeen täydelle palvelimen uudelleenkäynnistykselle. Soveltamalla muutoksia suoraan käynnissä olevaan sovellukseen, JRebel virtaviivaistaa kehitysprosessia, säästäen aikaa ja säilyttäen sovelluksen tilan, mukaan lukien käyttäjäistunnot.

## Asennus {#installation}

Virallisella JRebel-sivustolla on [pikakäynnistysohjeet](https://www.jrebel.com/products/jrebel/learn) tuotteen käyttöönottoon eri suosituissa IDE:issä. Noudata näitä ohjeita integroidaksesi JRebel kehitysympäristöösi.

Kun asennus on valmis, avaa webforJ-projekti ja varmista, että jetty `scan` -ominaisuus `pom.xml` -tiedostossa on asetettu arvoon `0` palvelimen automaattisen uudelleenkäynnistyksen poistamiseksi käytöstä. Kun tämä on tehty, käytä seuraavaa komentoa:

```bash
mvn jetty:run
```

Jos kaikki on tehty oikein, JRebel tulostaa lokitietoja terminaaliin, ja ohjelmaasi tehdyt muutokset näkyvät pyynnöstä.

:::info Näe muutoksesi
Jos muutos tehdään näkymään tai komponenttiin, joka on jo näkyvillä, JRebel ei pakota sivun uudelleenlatausta, koska palvelinta ei käynnistetä uudelleen.
:::
