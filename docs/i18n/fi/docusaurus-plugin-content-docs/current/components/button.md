---
title: Button
sidebar_position: 15
_i18n_hash: 186724659f1f66cdb6f85e7a1628def8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

A `Button`-komponentti on perusedellytys käyttäjäliittymäelementti, jota käytetään sovelluskehityksessä interaktiivisten elementtien luomiseen, jotka laukaisevat toimintoja tai tapahtumia napsautettaessa tai aktivoitaessa. Se toimii napsautettavana elementtinä, jonka avulla käyttäjät voivat suorittaa erilaisia toimintoja sovelluksessa tai verkkosivustolla.

`Button`-komponentin ensisijainen tarkoitus on tarjota käyttäjille selkeä ja intuitiivinen kehotus toimintaan, ohjaten heitä suorittamaan tiettyjä tehtäviä, kuten lomakkeen lähettäminen, siirtyminen toiseen sivuun, toiminnon laukaiseminen tai prosessin aloittaminen. Painikkeet ovat olennaisia käyttäjävuorovaikutuksen parantamiseksi, käytettävyyden lisäämiseksi ja houkuttelevamman käyttäjäkokemuksen luomiseksi.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Käytöt {#usages}

`Button`-luokka on monipuolinen komponentti, jota käytetään yleisesti erilaisissa tilanteissa, joissa käyttäjävuorovaikutusta ja -toimia on tarpeen laukaista. Tässä on muutamia tyypillisiä tilanteita, joissa saatat tarvita painiketta sovelluksessasi:

1. **Lomakkeen lähettäminen**: Painikkeita käytetään usein lomakedatan lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä"-painiketta tietojen lähettämiseen palvelimelle
  > - "Tyhjennä"-painiketta poistaaksesi jo lomakkeessa olevat tiedot


2. **Käyttäjän toimet**: Painikkeita käytetään mahdollistamaan käyttäjien suorittavan erityisiä toimia sovelluksessa. Esimerkiksi voit olla painike, jonka nimi on:

  > - "Poista" aloittamaan valitun kohteen poistamisen
  > - "Tallenna" tallentamaan asiakirjaan tai sivulle tehdyt muutokset.

3. **Vahvistusdialogit**: Painikkeita sisällytetään usein [`Dialog`](../components/dialog) komponentteihin, jotka on rakennettu erilaisiin tarkoituksiin, jotta käyttäjille annettaisiin mahdollisuus vahvistaa tai perua toiminto tai mihin muuhun toiminnallisuuteen tahansa, joka on sisällytetty käyttämääsi [`Dialog`](../components/dialog).

4. **Vuorovaikutteiset laukaisimet**: Painikkeet voivat toimia laukaisimina vuorovaikutuksille tai tapahtumille sovelluksessa. Napsauttamalla painiketta käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigointi**: Painikkeita voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osioiden tai sivujen välillä sovelluksessa. Navigointiin käytettävät painikkeet voivat sisältää:

  > - "Seuraava" - vie käyttäjän seuraavaan sivuun tai nykyisen sovelluksen tai sivun osioon.
  > - "Edellinen" - vie käyttäjän sovelluksen edelliselle sivulle tai osioon, jossa he ovat.
  > - "Takaisin" - vie käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa he ovat.

## Kuvakkeiden lisääminen painikkeisiin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen lisääminen painikkeeseen voi parantaa sovelluksen suunnittelua merkittävästi, jolloin käyttäjät voivat nopeasti tunnistaa toiminnalliset kohteet näytöllä. [`Icon`](./icon.md) -komponentti tarjoaa laajan valikoiman kuvakkeita valittavaksi.

Käyttämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä voit päättää, tulee `Icon` kuvakkeen näkyä ennen vai jälkeen painikkeen tekstin. Vaihtoehtoisesti `setIcon()` -menetelmää voidaan käyttää kuvakkeen lisäämiseen tekstin jälkeen, mutta ennen painikkeen `suffix`-slotia.

<!-- Lisää tämä takaisin, kun kuvake on yhdistetty -->
<!-- Viittaa [Kuvakekomponentti](../components/icon) -sivulle saadaksesi lisätietoja kuvakkeiden määrittämisestä ja mukauttamisesta. -->

:::tip
Oletusarvoisesti `Icon` perii painikkeen teeman ja koon.
:::

Alla on esimerkkejä painikkeista, joissa teksti on vasemmalla ja oikealla, sekä painike, jossa on vain kuvake:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nimet {#names}

`Button`-komponentti käyttää nimeämistä, jota käytetään käytettävyyden parantamiseksi. Kun nimeä ei ole selvästi asetettu, painikkeen etiketti käytetään sen sijaan. Jotkin kuvakkeet eivät kuitenkaan sisällä etikettejä ja näyttävät vain ei-tekstielementtejä, kuten kuvakkeita. Tässä tapauksessa on suositeltavaa käyttää `setName()` -menetelmää varmistamaan, että luotu `Button`-komponentti noudattaa käytettävyyden standardeja.

## Painikkeen pois käyttö {#disabling-a-button}

Painikekomponentteja, kuten monia muita, voidaan estää, jotta käyttäjälle viestitään, että tietty toiminto ei ole vielä saatavilla tai se ei ole enää käytettävissä. Estetty painike vähentää painikkeen läpinäkyvyyttä, ja se on saatavilla kaikille painikketeemoille ja -kokoille.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Painikkeen estäminen voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -toimintoa. Lisämukavuuden vuoksi painike voidaan myös estää napsautettaessa käyttämällä sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -toimintoa.

Joissain sovelluksissa painikkeen napsautus laukaisee pitkäkestoisen toiminnan. Useimmissa tapauksissa sovellus haluaisi varmistaa, että vain yksi napsautus käsitellään. Tämä voi olla ongelma suurten viiveiden ympäristöissä, kun käyttäjä napsauttaa painiketta useita kertoja ennen kuin sovellus on ehtinyt aloittaa tuloksena olevan toiminnan käsittelyn.

:::tip
Napsautuksessa estäminen ei vain auta optimoinnissa toiminnan käsittelyssä, vaan estää myös kehittäjää toteuttamasta tätä käyttäytymistä itse, koska tämä menetelmä on optimoitu vähentämään pyöräripartikkeli-viestintää.
:::

## Tyylittely {#styling}

### Teemat {#themes}

`Button`-komponentit tulevat <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erillistä teemaa </JavadocLink>, jotka on rakennettu nopeaan tyylittelyyn ilman CSS:ää. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua koko sovelluksessa.

Vaikka jokaiselle erilaiselle teema on monia käyttötapauksia, jotkut esimerkit ovat:

  - **Vaara**: Paras vakavien seurausten aiheuttamiseen, kuten täytettyjen tietojen tyhjentämiseen tai tilin/tietojen pysyvään poistamiseen.
  - **Oletus**: Sopiva sovelluksessa käytettävissä oleviin toimiin, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Pää**: Sopiva pää "toimintaan kutsuva" -painikkeena sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Erinomainen käytettäväksi onnistuneen toiminnan visualisoimiseen sovelluksessa, kuten lomakkeen lähettämisen tai rekisteröitymisprosessin suorittamisen yhteydessä. Onnistumisteemaa voidaan ohjelmallisesti soveltaa, kun onnistunut toiminta on suoritettu.
  - **Varoitus**: Hyödyllinen viestimään, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymään sivulta, jolla on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikutuksellisia kuin ne, jotka käyttäisivät Vaara-teemaa.
  - **Harmaa**: Hyvä hienovaraisiin toimiin, kuten vähäisiin asetuksiin tai toimintoihin, jotka ovat enemmän lisäyksiä sivulle, eivätkä kuulu keskeiseen toiminnallisuuteen.
  - **Tieto**: Hyvä追加vakiotietojen tarjoamiseen käyttäjälle.

Alla on esimerkkejä painikkeista, joille on sovellettu kutakin tuettua teemaa: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Koot {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Kokoarvot </JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:ää. Tämä mahdollistaa painikkeen mittojen manipuloimisen ilman, että niitä on asetettava erikseen tyylitulosteiden avulla. Tyylittelyn yksinkertaistamisen lisäksi se auttaa myös luomaan ja ylläpitämään yhtenäisyyttä sovelluksessasi. Oletusarvoinen `Button`-koko on `Expanse.MEDIUM`.

Eri kokoja käytetään usein eri käyttötarkoituksiin:
  - **Suuremmat** kokoarvot soveltuvat painikkeille, jotka tulisi saada huomiota, korostaa toiminnallisuutta tai jotka ovat keskeisiä sovelluksen tai sivun toiminnalle.
  - **Keskikokoiset** painikkeet, oletuskoko, tulisi hyödyntää "vakiokokona", kun painikkeen käyttäytyminen ei ole tärkeämpää tai vähemmän tärkeää kuin muilla vastaavilla komponenteilla.
  - **Pienemmät** kokoarvot tulisi käyttää painikkeille, joilla ei ole keskeisiä käyttäytymisiä sovelluksessa, ja jotka palvelevat enemmän lisäkäyttötarkoituksia tai utilitaristista roolia, eivätkä ole tärkeitä käyttäjävuorovaikutuksessa. Tämä sisältää `Button`-komponentit, joita käytetään vain kuvakkeilla utilitaristisia tarkoituksia varten.

Alla on eri kokoja, joita tuetaan `Button`-komponentille: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Parhaat käytännöt {#best-practices}

Jotta varmistaisit optimaalisen käyttäjäkokemuksen `Button`-komponenttia käyttäessäsi, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja ytimekästä tekstiä painikekomponentin sisällä antaaksesi selkeän viittauksen sen tarkoitukseen.

2. **Sopiva visuaalinen tyylittely**: Harkitse painikkeen visuaalista tyylittelyä ja teemaa varmistaaksesi, että se on johdonmukainen sovelluksesi suunnittelun kanssa. Esimerkiksi:
  > - "Peruuta" `Button`-komponentin tulisi olla tyylitelty sopivalla teemalla tai CSS-tyylillä varmistaaksesi, että käyttäjät ovat varmoja siitä, että he haluavat peruuttaa toiminnon
  > - "Vahvista" `Button`-painikkeella olisi eri tyylit kuin "Peruuta"-painikkeella, mutta sen tulisi myös erottua varmistaakseen, että käyttäjät tietävät tämän olevan erityinen toiminto.

3. **Tehokas tapahtumankäsittely**: Käsittele `Button`-tapahtumia tehokkaasti ja anna käyttäjille tarvittaessa palautetta. Viittaa [Tapahtumat](../building-ui/events) tarkastellaksesi tehokkaita tapahtumien lisäämiskäyttäytymisiä.

4. **Testaus ja käytettävyys**: Testaa painikkeen käyttäytyminen eri skenaarioissa, kuten silloin, kun se on estetty tai saa keskipisteen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata käytettävyysohjeita varmistaaksesi, että `Button` on käytettävä kaikille käyttäjille, mukaan lukien ne, jotka luottavat apuvälineisiin.
