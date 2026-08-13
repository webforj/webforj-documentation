---
title: Local BLS License
sidebar_class_name: new-content
sidebar_position: 25
description: >-
  Configure a webforJ project to use a locally installed BLS certificate and
  client configuration.
_i18n_hash: 981d7333984cb25b65a90d27775eab9f
---
Paikallinen BASIS License Service (BLS) mahdollistaa webforJ-sovelluksen lisenssin pyytämisen palvelimelta, joka toimii kehityskoneellasi tai sisäverkossasi. Tämä asetus on hyödyllinen, kun sinulla on sarjanumero ja valtuutusnumero, ja haluat projektin käyttävän tuotettuja paikallisia lisenssitiedostoja oletuslisenssiasetusten sijaan.

WebforJ-projekti, joka on luotu [startforJ](https://docs.webforj.com/startforj) avulla, sisältää kaksi lisenssitiedostoa hakemistossa `src/main/resources`:

```
src/main/resources/blsclient.conf
src/main/resources/certificate.bls
```

Tässä on, miten voit vaihtaa oletuslisenssitiedostot paikallisen BLS-asennuksen tuottamiin tiedostoihin:

## Edellytykset {#prerequisites}

Ennen kuin alat, varmista, että sinulla on:

- Java 21 tai Java 25 saatavilla BLS 26 -asentajan suorittamiseen.
- Sarjanumero ja valtuutusnumero.
- WebforJ-projekti, jossa on `src/main/resources` -hakemisto.
- Pääsy koneeseen, jossa BLS:a ajetaan.

## 1. Lataa BLS-asentaja {#1-download-bls}

Saadaksesi BLS-asentajan, siirry sivulle [BASIS-tuoteperheen lataukset](https://basis.cloud/bbj-downloads/).
Kun olet valinnut haluamasi kielen lomakkeelle, siirry **Valitse tuote** -osioon. **Tuote**-valikosta valitse `BLS`, ja **Revision**-valikosta valitse uusin versio. Vaaditut Java-versiot BLS:n suorittamiseen löytyvät **Revision**-valikosta.

Täytä sitten lomake **Yhteystiedot** -osassa ja valitse valintaruudut **Latausehdot** -osassa.
Kun olet täyttänyt lomakkeen, valitse `Lataa`-painike ladataksesi BLS-asentajan JAR-tiedoston.

![Latauslomake, jossa BLS valittuna tuotteeksi](/img/configuration/local-bls-license/download-bls.png#rounded-border)

*Latauslomake, jossa `BLS` valittuna tuotteeksi.*

## 2. Asenna ja konfiguroi BLS {#2-install-andc-onfig-bls}

Ladatun suoritettavan JAR-tiedoston nimimuoto on seuraava: `BLS<revision><date>_<time>.jar`. Etsi JAR-tiedosto ja kaksoisklikkaa sitä käynnistääksesi asentajan, tai aja se komentokehoteesta:

```bash
java -jar <ladattu-bls-asentaja>.jar
```

Seuraa asentajan ohjeita ja täytä vaaditut tiedot.

Oletusarvoisesti BLS asennetaan tiettyihin hakemistoihin käyttöjärjestelmästä riippuen, mutta sen voi muuttaa **Hakemiston valinta** -ikkunassa. Tästä eteenpäin `<blshome>` viittaa BLS:n asennuspaikkaan.

- **Windows**: `C:\bls`
- **macOS**: `/Applications/bls`
- **Muut käyttöjärjestelmät**: `/usr/local/bls`

Kun olet asentanut BLS:n, se avaa **Lisenssin rekisteröintiohjelman**.

### Lisenssin rekisteröinti {#license-registration}

1. Lisenssin rekisteröintiohjelmassa valitse `Hae lisenssi` -vaihtoehto:

![Lisenssin rekisteröintiohjelma, jossa Hae lisenssi valittuna](/img/configuration/local-bls-license/retrieve-license.png#rounded-border)

*Lisenssin rekisteröintiohjelma, jossa `Hae lisenssi` valittuna.*

2. Seuraavissa ikkunoissa syötä yhteystietosi, sarjanumerosi ja valtuutusnumerosi.

3. Kun pääset **Lisenssin rekisteröinti- ja toimitustavat** -ikkunaan, valitse `Rekisteröi ja asenna lisenssi automaattisesti`.

Lisenssisi rekisteröinnin jälkeen viimeistele paikallisen BLS:n konfigurointi tarpeen mukaan. Jos myöhemmin tarvitset muutoksia BLS-asetuksiisi tai haluat hankkia toisen lisenssin, käytä BLS Adminia kohdassa `<blshome>/bin/BLSAdmin`.

## 3. Kopioi luodut lisenssitiedostot {#3-copy-the-generated-license-files}

Siirry nyt `<blshome>/cfg` -hakemistoon ja etsi luodut lisenssitiedostot `blsclient.conf` ja `certificate.bls`:

![BLS cfg-hakemisto, joka sisältää luodun asiakaskonfiguraation ja sertifioinnin](/img/configuration/local-bls-license/bls-cfg-folder.png#rounded-border)

*BLS-asennuksen `cfg`-hakemisto, joka sisältää luodun asiakaskonfiguraation ja sertifioinnin.*

Kopioi `blsclient.conf` ja `certificate.bls` webforJ-projektiisi ja vaihda kaikki aiemmin olemassa olevat samannimiset tiedostot resursseissa. Nyt, kun paikallinen BLS on käynnissä, webforJ-sovelluksesi pyytää lisenssiä kyseiseltä palvelulta.

```
src
├───main
│   ├───java/
│   └───resources
│       ├───icons/
│       ├───static/
│       ├───application.properties
│       ├───banner.txt
// highlight-next-line
│       ├───blsclient.conf
// highlight-next-line
│       └───certificate.bls
```

:::tip
Jos lisenssitiedostosi sijaitsevat oletuswebforJ-konfiguraatiohakemiston ulkopuolella, voit konfiguroida lisenssihakemiston käyttäen [`webforj.license.cfg`](./properties#configuration-options).
:::"
