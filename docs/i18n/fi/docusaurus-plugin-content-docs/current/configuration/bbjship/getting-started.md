---
title: Aloittaminen
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
Tämä opas luo webforJ-sovelluksen, joka toimii BBjServicesin kanssa, ajaa sen paikallisessa BBj:ssä kehitysvaiheessa ja julkaisee sen BBjShipin avulla.

:::tip Edellytykset
Tarvitset Maven-projektin ja BBj-asennuksen, jonka versio vastaa webforJ:ää, esimerkiksi BBj 26.03 webforJ 26.03:lle, ja BBjServices -palvelun, joka on käynnissä. BBjShip ei tue Gradlea. Tarkista [edellytykset](/docs/introduction/prerequisites) muiden työkalujen osalta.
:::

## Luo BBj-sovellus {#create-a-bbj-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen, joka toimii BBjServicesin kanssa: käyttää graafista startforJ-työkalua tai Maven-käskyä.

<!-- vale off -->
### Vaihtoehto 1: käyttäen startforJ:ää {#option-1-using-startforj}
<!-- vale on -->

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Group ID, Artifact ID)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Arkkityyppi
- **Toimii** - Valitse **BBjServices** luodaksesi projektin, joka toimii BBjServicesin kanssa

### Vaihtoehto 2: käyttäen komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, luo projekti virallisten webforJ-arkkitehtuurien avulla:

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

`flavor`-parametrilla arkkityyppi luo projektin, joka toimii BBjServicesin kanssa.

Tämä luo projektin, jossa on:
- `webforj`-riippuvuus ilman webforJ-moottoria, jonka BBjServices tarjoaa
- webforJ Maven -laajennus, joka on asetettu julkaisemaan BBjShipiin paikallisessa BBj:ssä artefakti-ID:n mukaan sovelluksen nimeksi
- HotswapAgent kehitysvaiheen ajoin
- Esimerkki-näkymiä
- webforJ-konfiguraatiotiedostot kehitykseen ja tuotantoon

## Aja sovellus {#run-the-app}

Arkkityyppiprojekti asettaa oletus-Maven-tavoitteensa, joten `mvn` ilman argumentteja kääntää sovelluksen ja käynnistää [kehitysvaiheen](/docs/configuration/deploy-reload/bbjservices) paikallisessa BBjServicesissä:

```bash
mvn
```

Ensimmäinen ajokerta valmistaa paikallisen BBj:si. Se asentaa BBjShipin ja lisää HotswapAgentin BBjServicesiin, mikä käynnistää BBjServicesin uudestaan kerran. Rakennus tulostaa sitten sovelluksesi osoitteen:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press Ctrl+C to stop the app and the watch
```

Kääntämäsi luokat tavoittavat käynnissä olevan sovelluksen ilman uudelleenkäynnistystä. Pysäytä rakennus `Ctrl+C`:llä pysäyttääksesi sovelluksen.

## Julkaise sovellus {#deploy-the-app}

Julkaistaksesi pakatun sovelluksen, suorita:

```bash
mvn install
```

Sovellus julkaistaan sitten osoitteessa `http://localhost:8888/webapp/my-app`, kunnes julkaiset sen uudelleen tai poistat sen. Katso [Julkaiseminen](/docs/configuration/bbjship/deploying) julkaistaksesi toiselle BBj-palvelimelle, esimerkiksi CI-putkesta.
