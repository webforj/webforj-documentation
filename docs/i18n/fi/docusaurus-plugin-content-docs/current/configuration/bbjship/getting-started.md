---
title: Aloittaminen
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 84b3ad107540f450350f754730522e0e
---
Tässä oppaassa luodaan webforJ-sovellus, joka toimii BBjServices-palvelussa. Opas näyttää, miten sovellusta ajetaan paikallisessa BBj-ympäristössä kehityksen aikana ja miten se otetaan käyttöön BBjShipillä.

:::tip Edellytykset
Tarvitset Maven-projektin ja webforJ:n kanssa saman version BBj-asennuksen. Esimerkiksi webforJ 26.03 edellyttää BBj 26.03:a, jossa BBjServices on käynnissä. BBjShip ei tue Gradlea. Tutustu [muihin edellytyksiin](/docs/introduction/prerequisites) ja tarvittaviin työkaluihin.
:::

## BBj-sovelluksen luominen {#create-a-bbj-app}

Voit luoda uuden BBjServices-palvelussa toimivan webforJ-sovelluksen kahdella tavalla: graafisella startforJ-työkalulla tai Maven-komentoriviltä.

<!-- vale off -->
### Vaihtoehto 1: startforJ-työkalun käyttäminen {#option-1-using-startforj}
<!-- vale on -->

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj)-työkalulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Projektin perustiedot (sovelluksen nimi, ryhmätunnus, artefaktitunnus)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Arkkityyppi
- **Runs on** – luo BBjServices-palvelussa toimiva projekti valitsemalla **BBjServices**

### Vaihtoehto 2: komentorivin käyttäminen {#option-2-using-the-command-line}

Jos käytät mieluummin komentoriviä, luo projekti virallisilla webforJ-arkkityypeillä:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

`flavor`-parametrilla arkkityyppi luo BBjServices-palvelussa toimivan projektin.

Projektissa on seuraavat osat:
- `webforj`-riippuvuus ilman webforJ-moottoria, jonka BBjServices tarjoaa
- webforJ Maven -liitännäinen, joka on määritetty ottamaan sovellus käyttöön paikallisessa BBj-ympäristössäsi BBjShipillä. Sovelluksen nimenä käytetään artefaktitunnusta.
- HotswapAgent kehitysaikoja varten
- Esimerkkienäkymät
- webforJ-määritystiedostot kehitystä ja tuotantoa varten

## Sovelluksen ajaminen {#run-the-app}

Arkkityyppiprojekti määrittää Mavenin oletustavoitteen, joten `mvn` ilman argumentteja kääntää sovelluksen ja käynnistää [kehitysajon](/docs/configuration/deploy-reload/bbjservices) paikallisessa BBjServices-palvelussasi:

```bash
mvn
```

Ensimmäinen ajo valmistelee paikallisen BBj-ympäristösi. Se asentaa BBjShipin ja lisää HotswapAgentin BBjServices-palveluun, minkä jälkeen BBjServices käynnistyy kerran uudelleen. Tämän jälkeen kooste tulostaa sovelluksen osoitteen:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Kääntämäsi luokat siirtyvät käynnissä olevaan sovellukseen ilman uudelleenkäynnistystä. Pysäytä sovellus painamalla <kbd>Ctrl</kbd>+<kbd>C</kbd>, jolloin myös kooste pysähtyy.

## Sovelluksen käyttöönotto {#deploy-the-app}

Ota paketoitu sovellus käyttöön suorittamalla:

```bash
mvn install
```

Sovellus julkaistaan osoitteessa `http://localhost:8888/webapp/my-app`, kunnes otat sen uudelleen käyttöön tai poistat sen. Katso kohdasta [Käyttöönotto](/docs/configuration/bbjship/deploying), miten sovellus otetaan käyttöön toisessa BBj-palvelimessa, esimerkiksi CI-putkesta.
