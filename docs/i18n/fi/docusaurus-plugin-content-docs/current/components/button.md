---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Nappi` on napsittava elementti, joka laukaisee toiminnon, kun sitä painetaan. Se voi näyttää tekstiä, kuvakkeita tai niiden yhdistelmää. Napit tukevat useita visuaalisia teemoja ja kokoja, ja ne voidaan poistaa käytöstä, jotta estetään vuorovaikutus pitkäkestoisten operaatioiden aikana tai kun tietyt ehdot eivät täyty.

<!-- INTRO_END -->

## Käytöt {#usages}

`Nappi`-luokka on monikäyttöinen komponentti, jota käytetään yleisesti eri tilanteissa, joissa käyttäjän vuorovaikutuksia ja toimintoja täytyy laukaista. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat tarvita painiketta sovelluksessasi:

1. **Lomakkeen lähettäminen**: Nappeja käytetään usein lomakedatan lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä" -painiketta tietojen lähettämiseen palvelimelle
  > - "Tyhjennä" -painiketta poistaaksesi lomakkeeseen jo syötetyt tiedot


2. **Käyttäjän toiminnot**: Nappeja käytetään, jotta käyttäjät voivat suorittaa erityisiä toimintoja sovelluksessa. Esimerkiksi voit olla painike, jossa lukee:

  > - "Poista" aloittaaksesi valitun kohteen poistamisen
  > - "Tallenna" tallentaaksesi muutokset asiakirjaan tai sivulle.

3. **Vahvistusdialogit**: Nappeja sisältyy usein [`Dialog`](../components/dialog) -komponentteihin, jotka on rakennettu eri tarkoituksiin tarjotakseen käyttäjille vaihtoehtoja vahvistaa tai peruuttaa toiminto tai muu toiminnallisuus, joka on rakennettu käyttämääsi [`Dialog`](../components/dialog) -komponenttiin.

4. **Vuorovaikutuksen laukaisijat**: Nappeja voidaan käyttää laukaisemaan vuorovaikutuksia tai tapahtumia sovelluksessa. Painamalla painiketta käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigaatio**: Nappeja voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osioiden tai sivujen välillä sovelluksessa. Navigointia varten tarkoitetut napit voivat sisältää:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivulla.
  > - "Edellinen" - palauttaa käyttäjän edelliselle sivulle sovelluksessa tai osioon, jossa he ovat.
  > - "Takaisin" - palauttaa käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa he ovat.

Seuraava esimerkki näyttää painikkeet, joita käytetään lomakkeen lähettämiseen ja syötteen tyhjentämiseen:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Kuvakkeiden lisääminen nappeihin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen lisääminen painikkeeseen voi parantaa sovelluksesi suunnittelua, jolloin käyttäjät voivat nopeasti tunnistaa toimivat elementit näytöllä. [`Ikoni`](./icon.md) -komponentti tarjoaa laajan valikoiman kuvakkeita, joista valita.

Käyttämällä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja voit päättää, tuleeko `Ikoni` ennen vai jälkeen napin tekstin. Vaihtoehtoisesti `setIcon()`-metodia voidaan käyttää lisämään `Ikoni` tekstin jälkeen, mutta ennen napin `suffix`-paikkaa.

<!-- Add this back in once Icon has been merged -->
<!-- Katso [Ikoni komponentti](../components/icon) sivulta lisätietoja kuvakkeiden määrittelystä ja mukauttamisesta. -->

:::tip
Oletusarvoisesti `Ikoni` perii napin teeman ja koon.
:::

Alla on esimerkkejä painikkeista, joissa teksti on vasemmalla ja oikealla, sekä painike, jossa on vain kuvake:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Nimet {#names}

`Nappi`-komponentti käyttää nimeämistä, joka on käytössä saavutettavuudelle. Kun nimeä ei ole erikseen asetettu, `Nappi`-komponentin etiketti käytetään sen sijaan. Kuitenkin jotkin kuvakkeet eivät sisällä etikettejä, vaan vain näyttävät ei-tekstielementtejä, kuten kuvakkeita. Tässä tapauksessa on järkevä käyttää `setName()` -metodia varmistaaksesi, että luotu `Nappi`-komponentti noudattaa saavutettavuusstandardeja.

## Napin poistaminen käytöstä {#disabling-a-button}

Nappikomponentteja, kuten monia muita, voidaan poistaa käytöstä osoittamaan käyttäjälle, että tietty toiminto ei ole vielä tai ei enää saatavilla. Poistettu nappi vähentää napin läpinäkyvyyttä, ja se on käytettävissä kaikilla napin teemoilla ja kokoilla.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Napin poistaminen käytöstä voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -toimintoa. Käytännön mukavuuden vuoksi nappi voidaan myös poistaa käytöstä napsautettaessa käyttämällä rakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -toimintoa.

Joissakin tapauksissa napin napsautus laukaisee pitkäkestoisen toiminnon. Napin poistaminen käytöstä, kun sovelluksesi käsittelee toimintoa, estää käyttäjää napsauttamasta nappia useita kertoja, erityisesti korkealaatuisten ympäristöjen aikana.

:::tip
Napsauttamisen yhteydessä poistaminen käytöstä ei vain optimoi toimintojen käsittelyä, vaan myös estää kehittäjää toteuttamasta tätä toimintoa itsenäisesti, koska tämä menetelmä on optimoitu vähentämään palautusviestintöjä.
:::

## Tyylittely {#styling}

### Teemat {#themes}

`Nappi`-komponentit tarjoavat <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erilaista teemaa</JavadocLink>, jotka on sisäänrakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa nappeihin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa nappejen ulkoasua sovelluksessa.

Vaikka eri teemoille on monia käyttötarkoituksia, joitakin esimerkkejä ovat:

  - **Vaarallinen**: Paras toiminnoille, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
  - **Oletus**: Sopii sovelluksessa käytettäville toiminnoille, joilla ei ole erityistä huomiota vaativia ominaisuuksia ja jotka ovat yleisiä, kuten asetusten vaihtaminen.
  - **Pääasiallinen**: Sopii pääasialliseksi "toimintokutsuksi" sivulla, kuten rekisteröitymiselle, muutosten tallentamiselle tai siirtymiselle toiselle sivulle.
  - **Onnistuminen**: Erinomainen kuvaamaan jotakin, joka on onnistuneesti saatu päätökseen sovelluksessa, kuten lomakkeen lähettäminen tai rekisteröitymisprosessin loppuun saattaminen. Onnistumisteema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Hyödyllinen osoittamaan, että käyttäjä on aikeissa suorittaa mahdollisesti riskialtista toimintoa, kuten siirtymistä sivulta, jolla ei ole tallennettuja muutoksia. Nämä toiminnot ovat usein vähemmän vaikutusvaltaisia kuin ne, jotka käyttäisivät vaarallista teemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten vähäisille asetuksille tai toimille, jotka ovat enemmän lisätoimintoja sivulla, eivätkä ole osa päätoiminnallisuutta.
  - **Tiedot**: Hyvä tarjoamaan käyttäjälle lisäselvyyksiä.

Alla on esimerkkejä painikkeista, joihin on sovellettu kaikkia tuettuja teemoja: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Koot {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Koot </JavadocLink> -arvot mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Tämä mahdollistaa napin ulottuvuuksien muokkaamisen ilman, että niitä tarvitsee asettaa erikseen. Tyylittelyn yksinkertaistamisen lisäksi se auttaa luomaan ja ylläpitämään yhtenäisyyttä sovelluksessasi. Oletusarvoinen `Nappi`-koko on `Expanse.MEDIUM`.

Eri kokoja käytetään usein eri tarkoituksiin:
  - **Suuremmat** kokoarvot sopivat painikkeille, joiden tulisi kiinnittää huomiota, korostaa toiminnallisuutta tai olla keskeisiä sovelluksen tai sivun toiminnallisuudessa.
  - **Keskikokoiset** napit, oletuskoko, tulisi olla painikkeiden standardikoko. Näiden nappien toiminnot eivät tulisi olla enempää eivätkä vähempää kriittisiä kuin vastaavat komponentit.
  - **Pienemmät** kokoarvot tulisi varata painikkeille, joilla ei ole keskeisiä käytöksiä sovelluksessa, ja jotka palvelevat enemmän lisä- tai käyttökelpoisia rooleja, eivätkä ole tärkeitä käyttäjien vuorovaikutuksessa. Tämä sisältää `Nappi`-komponentit, joita käytetään vain kuvakkeiden kanssa käyttökelpoisiin tarkoituksiin.

Alla ovat eri koot, joita tuetaan `Nappi`-komponentille: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Nappi" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalinen käyttäjäkokemus käytettäessä `Nappi`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja ytimekästä tekstiä `Nappi`-komponentissasi, jotta se antaa selkeän käsityksen sen tarkoituksesta.

2. **Sopiva visuaalinen tyylittely**: Harkitse napin visuaalista tyyliä ja teemaa varmistaaksesi johdonmukaisuuden sovelluksesi suunnittelussa. Esimerkiksi:
  > - "Peruuta" `Nappi`-komponentin tulisi olla tyyliltään sopiva teema tai CSS-tyyli, jotta käyttäjät ovat varmoja siitä, että he haluavat peruuttaa toiminnon
  > - "Vahvista" -nappilla olisi erilainen tyyli kuin "Peruuta" -nappilla, mutta se erottuu silti varmistaakseen, että käyttäjät tietävät tämän olevan erityinen toiminto.

3. **Tehokas tapahtumankäsittely**: Käsittele `Nappi`-tapahtumia tehokkaasti ja tarjoa käyttäjille asianmukaista palautetta. Viittaa [Tapahtumat](../building-ui/events) tarkastellaksesi tehokkaita tapahtumien lisäämis käytäntöjä.

4. **Testaus ja saavutettavuus**: Testaa napin käyttäytymistä eri skenaarioissa, kuten silloin, kun se on poistettu käytöstä tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata saavutettavuusohjeita varmistaaksesi, että `Nappi` on käytettävissä kaikille käyttäjille, mukaan lukien ne, jotka käyttävät apuvälineitä.
