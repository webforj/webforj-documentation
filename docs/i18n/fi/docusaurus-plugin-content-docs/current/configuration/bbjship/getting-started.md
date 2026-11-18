---
title: BBjShipin käytön aloittaminen
sidebar_label: Aloittaminen
sidebar_position: 2
description: >-
  Luo webforJ-sovellus, joka käyttää BBjServices-palveluita startforJ:n tai
  Maven-arkkityyppien avulla. Suorita se paikallisessa BBj:ssäsi ja ota käyttöön
  BBjShipillä.
_i18n_title_hash: d9e3e5ac3e2403d5be0e47a35ce72383
_i18n_sidebar_label_hash: bf647454e36069fd16f1a7a35cf6a865
_i18n_description_hash: 26154c9b4b2e58bb9de84893fd1f1763
_i18n_hash: 913c105077586054f3af87216fe848e0
---
Tässä oppaassa luodaan webforJ-sovellus, joka toimii BBjServices-palvelussa, suoritetaan kehityksen aikana paikallisessa BBj:ssä ja otetaan käyttöön BBjShipillä.

:::tip Edellytykset
Tarvitset Maven-projektin ja webforJ:n kanssa samaa versiota olevan BBj-asennuksen, esimerkiksi BBj 26.03:n webforJ 26.03:lle, sekä käynnissä olevan BBjServices-palvelun. BBjShip ei tue Gradlea. Katso muiden tarvittavien työkalujen osalta [edellytykset](/docs/introduction/prerequisites).
:::

## Luo BBj-sovellus {#create-a-bbj-app}

Voit luoda uuden BBjServices-palvelussa toimivan webforJ-sovelluksen kahdella tavalla: graafisella startforJ-työkalulla tai Mavenin komentorivillä.

<!-- vale off -->
### Vaihtoehto 1: startforJ:n käyttäminen {#option-1-using-startforj}
<!-- vale on -->

Kun luot sovelluksen [startforJ:llä](https://docs.webforj.com/startforj), voit mukauttaa sitä antamalla seuraavat tiedot:

- Projektin perustiedot (sovelluksen nimi, ryhmätunnus, artefaktin tunnus)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Arkkityyppi
- **Toimii ympäristössä** – Valitse **BBjServices**, jos haluat luoda BBjServices-palvelussa toimivan projektin

### Vaihtoehto 2: komentorivin käyttäminen {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, luo projekti virallisilla [webforJ-arkkityypeillä](/docs/building-ui/archetypes/overview):

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
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
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
    ```powershell {8}
    mvn -B archetype:generate `
    -DarchetypeGroupId="com.webforj" `
    -DarchetypeArtifactId="webforj-archetype-blank" `
    -DarchetypeVersion="LATEST" `
    -DgroupId="org.example" `
    -DartifactId="my-app" `
    -Dversion="1.0-SNAPSHOT" `
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
    ```console {8}
    mvn -B archetype:generate ^
    -DarchetypeGroupId="com.webforj" ^
    -DarchetypeArtifactId="webforj-archetype-blank" ^
    -DarchetypeVersion="LATEST" ^
    -DgroupId="org.example" ^
    -DartifactId="my-app" ^
    -Dversion="1.0-SNAPSHOT" ^
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

`flavor`-parametrin avulla arkkityyppi luo projektin, joka toimii BBjServices-palvelussa.

Näin syntyvään projektiin sisältyy:
- `webforj`-riippuvuus ilman webforJ-moottoria, jonka BBjServices tarjoaa
- webforJ-Maven-laajennus, joka on määritetty ottamaan sovellus käyttöön paikallisessa BBj:ssä BBjShipillä artefaktin tunnuksen mukaisella sovelluksen nimellä
- HotswapAgent kehitysaikaista suorittamista varten
- Esimerkkinäkymät
- webforJ-määritystiedostot kehitystä ja tuotantoa varten

## Suorita sovellus {#run-the-app}

Arkkityyppiprojekti määrittää Mavenin oletustavoitteen, joten komento `mvn` ilman argumentteja kääntää sovelluksen ja käynnistää [kehitysajon](/docs/configuration/deploy-reload/bbjservices) paikallisessa BBjServices-palvelussa:

```bash
mvn
```

Ensimmäinen suoritus valmistelee paikallisen BBj:n. Se asentaa BBjShipin ja lisää HotswapAgentin BBjServices-palveluun, joka käynnistyy tämän jälkeen kerran uudelleen. Koonti tulostaa sitten sovelluksen osoitteen:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Kääntämäsi luokat tulevat käynnissä olevan sovelluksen käyttöön ilman uudelleenkäynnistystä. Pysäytä sovellus painamalla <kbd>Ctrl</kbd>+<kbd>C</kbd>, jolloin myös koonti pysähtyy.

## Ota sovellus käyttöön {#deploy-the-app}

Ota paketoitu sovellus käyttöön suorittamalla:

```bash
mvn install
```

Sovellus julkaistaan osoitteessa `http://localhost:8888/webapp/my-app`, kunnes otat sen uudelleen käyttöön tai poistat sen. Katso kohdasta [Käyttöönotto](/docs/configuration/bbjship/deploying), miten sovellus otetaan käyttöön toisella BBj-palvelimella, esimerkiksi CI-putkesta.
