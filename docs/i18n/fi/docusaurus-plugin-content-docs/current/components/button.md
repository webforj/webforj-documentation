---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button`-komponentti on perus käyttöliittymäelementti, jota käytetään sovelluskehityksessä luomaan interaktiivisia elementtejä, jotka laukaisevat toimintoja tai tapahtumia, kun niitä napsautetaan tai aktivoidaan. Se toimii napsautettavana elementtinä, jonka kanssa käyttäjät voivat olla vuorovaikutuksessa suorittaakseen erilaisia toimintoja sovelluksessa tai verkkosivustolla.

`Button`-komponentin ensisijainen tarkoitus on tarjota selkeä ja intuitiivinen toimintakehotus käyttäjille, ohjaten heitä suorittamaan tiettyjä tehtäviä, kuten lomakkeen lähettäminen, siirtyminen toiseen sivuun, toiminnon laukaiseminen tai prosessin aloittaminen. Painikkeet ovat olennaisia käyttäjävuorovaikutusten parantamiseksi, saavutettavuuden parantamiseksi ja houkuttelevamman käyttäjäkokemuksen luomiseksi.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Käyttötarkoitukset {#usages}

`Button`-luokka on monikäyttöinen komponentti, jota käytetään yleisesti erilaisissa tilanteissa, joissa käyttäjävuorovaikutuksia ja toimintoja on laukaistava. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat tarvita painiketta sovelluksessasi:

1. **Lomakkeen lähettäminen**: Painikkeita käytetään usein lomakkeen tietojen lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä" -painiketta tietojen lähettämiseen palvelimelle
  > - "Tyhjennä" -painiketta poistaaksesi lomakkeeseen jo syötetyt tiedot

2. **Käyttäjän toiminnot**: Painikkeita käytetään antamaan käyttäjille mahdollisuus suorittaa erityisiä toimintoja sovelluksessa. Esimerkiksi voit käyttää painiketta, joka on nimetty:

  > - "Poista" aloittaakseen valitun kohteen poistamisen
  > - "Tallenna" tallentaakseen asiakirjaan tai sivulle tehdyt muutokset.

3. **Vahvistusvalinta**: Painikkeita lisätään usein [`Dialog`](../components/dialog) -komponentteihin eri tarkoituksia varten, jotta käyttäjille tarjotaan vaihtoehtoja vahvistaa tai peruuttaa toiminto tai muita toimintoja, joita käytät [`Dialog`](../components/dialog) -komponentissa.

4. **Vuorovaikutuslaukaisijat**: Painikkeet voivat toimia laukaisijoina vuorovaikutuksille tai tapahtumille sovelluksessa. Napsauttamalla painiketta käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigointi**: Painikkeita voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osien tai sivujen välillä sovelluksessa. Navigointipainikkeet voivat sisältää:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivussa.
  > - "Edellinen" - vie käyttäjän takaisin aikaisemmalle sivulle sovelluksessa tai osioon, jossa he ovat.
  > - "Takaisin" - vie käyttäjän sovelluksen tai sivun ensimmäiseen osaan.

## Kuvakkeiden lisääminen painikkeisiin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen sisällyttäminen painikkeeseen voi parantaa sovelluksesi suunnittelua merkittävästi, jolloin käyttäjät voivat nopeasti tunnistaa toiminnallisia kohteita näytöllä. [`Icon`](./icon.md) -komponentti tarjoaa laajan valikoiman kuvakkeita valittavaksi.

Hyödyntämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä sinulla on mahdollisuus määrätä, tuleeko `Icon` kuvake ennen vai jälkeen painikkeen tekstin. Vaihtoehtoisesti `setIcon()`-menetelmää voidaan käyttää lisäämään `Icon` tekstin jälkeen, mutta ennen painikkeen `suffix`-slotia.

<!-- Lisää tämä takaisin, kun Icon on yhdistetty -->
<!-- Katso [Icon komponentti](../components/icon) sivua saadaksesi lisätietoja kuvakkeiden määrittelystä ja mukauttamisesta. -->

:::tip
Oletusarvoisesti `Icon` perii painikkeen teeman ja laajuuden.
:::

Alla on esimerkkejä painikkeista, joissa teksti on vasemmalla ja oikealla, sekä painike, jossa on vain kuvake:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nimet {#names}

`Button`-komponentti hyödyntää nimeämistä, jota käytetään saavutettavuuden varmistamiseksi. Kun nimeä ei ole määritetty, painikkeen etiketti käytetään sen sijaan. Kuitenkin, jotkut kuvakkeet eivät sisällä etikettejä ja näyttävät vain ei-tekstielementtejä, kuten kuvakkeita. Tässä tapauksessa on hyödyllistä käyttää `setName()`-menetelmää varmistaaksesi, että luotu `Button`-komponentti noudattaa saavutettavuusstandardeja.

## Painikkeen poistaminen käytöstä {#disabling-a-button}

Painikekomponentit, kuten monet muut, voidaan poistaa käytöstä viestittääkseen käyttäjälle, että tietty toiminto ei ole vielä käytettävissä tai ei ole enää käytettävissä. Poistettu painike vähentää painikkeen läpinäkyvyyttä, ja se on saatavilla kaikille painiketeemoille ja laajuuksille.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Painikkeen poistaminen käytöstä voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -toimintoa. Lisämukavuuden vuoksi painike voidaan myös poistaa käytöstä napsautettaessa käyttämällä sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -toimintoa.

Jotkin sovellukset laukaisevat pitkään kestävän toiminnon napsautettaessa painiketta. Useimmissa tapauksissa sovelluksen on ehkä varmistettava, että vain yksi napsautus käsitellään. Tämä voi olla ongelma korkean viiveen ympäristöissä, kun käyttäjä napsauttaa painiketta useita kertoja ennen kuin sovellus on saanut mahdollisuuden aloittaa tuloksena olevan toiminnon käsittelyn.

:::tip
Klikkauksen poistaminen käytöstä ei vain auta optimoimaan toimintojen käsittelyä, vaan myös estää kehittäjää toteuttamasta tätä käyttäytymistä itse, koska tämä menetelmä on optimoitu vähentämään matkaviestintää.
:::

## Tyylit {#styling}

### Teemat {#themes}

`Button`-komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erilaista teemaa</JavadocLink>, jotka mahdollistavat nopean tyylin ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua sovelluksessa.

Vaikka eri teemoille on monia käyttötilanteita, joitakin esimerkkejä käyttötarkoituksista ovat:

  - **Vaara**: Paras toimille, joilla on vakavia seurauksia, kuten tietojen poistamisen tai tilin/tietojen pysyvä poistaminen.
  - **Oletus**: Sopiva toimille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetusten vaihtaminen.
  - **Pääasiallinen**: Sopiva pääasialliseksi "toimintakehotukseksi" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Erinomainen visuaaliseen onnistuneen toiminnon näyttämiseen sovelluksessa, kuten lomakkeen lähettämisen tai rekisteröitymisprosessin loppuunsaattamisen. Onnistuneen teeman voi ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Hyödyllinen merkkinä siitä, että käyttäjä on tekemässä potentiaalisesti riskialtista toimintoa, kuten siirtymään pois sivulta opiskelmatta muutoksia. Nämä toimet ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät Vaara-teemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä sivun osia eivätkä osa päätoimintoja.
  - **Tiedot**: Hyvä käyttäjälle tarjoamaan lisäselventävää tietoa.

Alla on esimerkkejä painikkeista, joihin on sovellettu jokainen tuettu teema: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Laajuudet {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Laajuusarvot </JavadocLink> mahdollistavat nopean tyylin ilman CSS:n käyttöä. Tämä mahdollistaa painikkeen mittasuhteiden muokkaamisen ilman, että niitä olisi määritettävä erikseen minkään tyylin avulla. Tyylin yksinkertaistamisen lisäksi se auttaa myös luomaan ja ylläpitämään yhtenäisyyttä sovelluksessasi. Oletusarvoinen `Button`-laajuus on `Expanse.MEDIUM`.

Eri kokoja on usein sopivia erilaisiin käyttötarkoituksiin:
  - **Suuremmat** laajuusarvot soveltuvat painikkeille, jotka tulisi saada enemmän huomiota, korostaa toiminnallisuutta tai ovat keskeisiä sovelluksen tai sivun toiminnalle.
  - **Keskikokoiset** laajuuspainikkeet, oletuskoko, tulisi käyttää "standardikokoina", kun painikkeen käyttäytyminen ei ole tärkeämpää tai vähemmän tärkeää kuin muilla samankaltaisilla komponenteilla.
  - **Pienempiä** laajuusarvoja tulisi käyttää painikkeille, jotka eivät omaa olennaisia käyttäytymisiä sovelluksessa, ja jotka palvelevat enemmän täydentävää tai utilitaristista roolia kuin että niillä olisi tärkeä rooli käyttäjävuorovaikutuksessa. Tämä sisältää `Button`-komponentit, jotka käytetään vain kuvakkeiden kanssa utilitaristisiin tarkoituksiin.

Alla on erilaiset laajuudet, joita tuetaan `Button`-komponentille: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Button`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja ytimekästä tekstiä `Button`-komponentissasi, jotta sen tarkoitus on selkeä.

2. **Sopiva visuaalinen tyyli**: Harkitse `Button`-painikkeen visuaalista tyyliä ja teemaa varmistaaksesi, että ne ovat johdonmukaisia sovelluksesi suunnittelun kanssa. Esimerkiksi:
  > - "Peruuta" `Button`-komponentin tulisi olla tyylitelty sopivalla teemalla tai CSS-tyylillä, jotta käyttäjät ovat varmoja siitä, että he haluavat peruuttaa toiminnon
  > - "Vahvista" `Button` olisi tyyliltään erilainen kuin "Peruuta" painike, mutta joka myös erottuisi varmistaakseen, että käyttäjät tietävät, että tämä on erityinen toiminto.

3. **Tehokas tapahtumien käsittely**: Käsittele `Button`-tapahtumia tehokkaasti ja tarjoa käyttäjille asianmukaista palautetta. Katso [Tapahtumat](../building-ui/events) tarkistaaksesi tehokkaita tapahtumien lisäämiskäytäntöjä.

4. **Testaus ja saavutettavuus**: Testaa painikkeen käyttäytyminen eri skenaarioissa, kuten silloin, kun se on poistettu käytöstä tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata saavutettavuusohjeita tehdäksesi `Button`-komponentista käytettävän kaikille käyttäjille, mukaan lukien niille, jotka luottavat apuvälineisiin.
