---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button` on napsittava elementti, joka laukaisee toiminnon, kun sitä painetaan. Se voi näyttää tekstiä, ikoneita tai molempia yhdistelmänä. Painikkeet tukevat useita visuaalisia teemoja ja kokoja, ja niitä voidaan estää vuorovaikutuksen estämiseksi pitkäkestoisten toimintojen aikana tai kun tietyt ehdot eivät täyty.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

`Button`-luokka on monipuolinen komponentti, jota käytetään yleisesti eri tilanteissa, joissa käyttäjän vuorovaikutuksia ja toimintoja on laukaistava. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat tarvita painiketta sovelluksessasi:

1. **Lomakkeen lähetys**: Painikkeita käytetään usein lomaketietojen lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä" -painiketta lähettääksesi tiedot palvelimelle
  > - "Tyhjennä" -painiketta poistaaksesi lomakkeessa jo esillä olevan tiedon

2. **Käyttäjän toiminnot**: Painikkeita käytetään antamaan käyttäjille mahdollisuus suorittaa erityisiä toimintoja sovelluksessa. Esimerkiksi voit olla painike, jonka merkintä on:

  > - "Poista" aloittaaksesi valitun kohteen poistamisen
  > - "Tallenna" tallentaaksesi tehdyt muutokset asiakirjaan tai sivulle.

3. **Vahvistusdilogit**: Painikkeita sisältyy usein [`Dialog`](../components/dialog) komponentteihin, jotka on rakennettu erilaisiin tarkoituksiin, jotta käyttäjille tarjotaan vaihtoehtoja vahvistaa tai peruuttaa toiminto, tai muu toiminnallisuus, joka on rakennettu käyttämääsi [`Dialog`](../components/dialog).

4. **Vuorovaikutuksen laukaisijat**: Painikkeet voivat toimia laukaisijoina vuorovaikutuksille tai tapahtumille sovelluksessa. Painamalla painiketta käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaisuja, kuten sisällön päivittämistä tai näytön päivittämistä.

5. **Navigointi**: Painikkeita voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osien tai sivujen välillä sovelluksessa. Navigointipainikkeita voivat olla:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivulla.
  > - "Edellinen" - palauttaa käyttäjän edelliselle sivulle sovelluksessa tai osioon, jossa he ovat.
  > - "Takaisin" - palauttaa käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa he ovat.
  
Seuraava esimerkki osoittaa painikkeita, joita käytetään lomakkeen lähettämiseen ja syötteen tyhjentämiseen:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Kuvakkeiden lisääminen painikkeisiin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen lisääminen painikkeeseen voi parantaa sovelluksesi muotoilua, jolloin käyttäjät voivat nopeasti tunnistaa toiminnalliset tiedot näytöllä. [`Icon`](./icon.md) komponentti tarjoaa laajan valikoiman kuvakkeita valittavaksi.

Hyödyntämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä, sinulla on joustavuutta päättää, tulisiko `Icon` näkyä ennen vai jälkeen painikkeen tekstin. Vaihtoehtoisesti `setIcon()` -menetelmää voidaan käyttää lisäämään `Icon` tekstin jälkeen, mutta ennen painikkeen `suffix`-slotia.

<!-- Lisää tämä takaisin, kun kuvake on yhdistetty -->
<!-- Katso [Icon component](../components/icon) -sivua saadaksesi lisätietoja kuvakkeiden määrittämisestä ja mukauttamisesta. -->

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

`Button`-komponentti hyödyntää nimeämistä, jota käytetään esteettömyyden varmistamiseksi. Kun nimeä ei ole nimenomaisesti asetettu, painikkeen merkintää käytetään sen sijaan. Kuitenkin jotkut ikonit eivät sisällä merkintöjä, ja näyttävät vain ei-tekstuaalisia elementtejä, kuten kuvakkeita. Tällöin on hyödyllistä käyttää `setName()` -menetelmää varmistaaksesi, että luotu `Button`-komponentti täyttää esteettömyysstandardit.

## Painikkeen poistaminen käytöstä {#disabling-a-button}

Painike komponentit, kuten monet muut, voidaan poistaa käytöstä, jotta käyttäjä tietää, että tietty toiminto ei ole vielä tai ei ole enää saatavilla. Poistettu painike vähentää painikkeen opasiteettia, ja se on saatavilla kaikille painiketeemoille ja -laajuuksille.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Painikkeen poistaminen käytöstä voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -toimintoa. Lisäksi painike voidaan myös poistaa käytöstä napsautettaessa käyttämällä sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -toimintoa.

Joissakin sovelluksissa painikkeen napsauttaminen laukaisee pitkäkestoisen toiminnon. Useimmissa tapauksissa sovellus saattaa haluta varmistaa, että vain yksi napsautus käsitellään. Tämä voi olla ongelma suurten viiveiden ympäristöissä, kun käyttäjä napsauttaa painiketta useita kertoja ennen kuin sovellus ehtii aloittaa tuloksena olevan toiminnon käsittelyn. 

:::tip
Poistaminen käytöstä napsautettaessa auttaa optimoimaan toimintojen käsittelyä, mutta myös estää kehittäjää toteuttamasta tätä käyttäytymistä itse, koska tämä menetelmä on optimoitu vähentämään pyörömatka-viestintää.
:::

## Tyylittely {#styling}

### Teemat {#themes}

`Button`-komponentit tarjoavat <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erillistä teemaa</JavadocLink> nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennakkoon määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua koko sovelluksessa. 

Vaikka jokaiselle eri teema käytölle on monia käyttötilanteita, joitakin esimerkkejä käytöstä ovat:

  - **Vaarallinen**: Parhaat toiminnot, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
  - **Oletus**: Sopiva toimintoille koko sovelluksessa, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Päällimmäinen**: Sopii pää "toimintakehotteeseen" sivulla, kuten rekisteröitymiseen, muutosten tallentamiseen tai siirtymiseen toiseen sivuun.
  - **Onnistuminen**: Erinomainen visualisoimaan onnistunutta toimintoa sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröintiprosessin toteutumista. Onnistumisteema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Hyödyllinen ilmoittaaksesi, että käyttäjä aikoo suorittaa potentiaalisesti riskialttiin toiminnon, kuten siirtymisen pois sivulta, jolla on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät vaarateemaa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän lisäosa sivulle, eikä ne ole osa päätoimintoja.
  - **Tietoa**: Hyvä tarjoamaan käyttäjälle lisätietoa selventäviä tietoja.

Alla on esimerkkejä painikkeista, joihin kaikki tuetut teemat on sovellettu: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Laajuudet {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse">Laajuusarvot</JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Tämä mahdollistaa painikkeen mittojen muokkaamisen ilman, että niitä on tarvitsevinaan erikseen määrittää. Styylittelyn yksinkertaistamisen lisäksi se auttaa myös luomaan ja ylläpitämään yhtenäisyyttä sovelluksessa. Oletusarvoinen `Button`-laajuus on `Expanse.MEDIUM`.

Eri kokoja on usein sopivia eri käyttötarkoituksiin:
  - **Suuremmat** laajuusarvot soveltuvat painikkeille, jotka tulisi saada huomiota, korostaa toiminnallisuutta tai ovat keskeisiä sovelluksen tai sivun toiminnallisuudelle.
  - **Keskikokoiset** laajuuspainikkeet, oletuskoko, tulisi käyttää "normaalina kokona", kun painikkeen käyttäytyminen ei ole enemmän tai vähemmän tärkeää kuin muilla samanlaisilla komponenteilla.
  - **Pienemmän** laajuuden arvot tulisi käyttää painikkeille, joilla ei ole keskeisiä toimintoja sovelluksessa, ja jotka palvelevat enemmän lisäosan tai käytännöllisen roolin, eivätkä pelaa tärkeää roolia käyttäjän vuorovaikutuksessa. Tämä sisältää `Button`-komponentit, joita käytetään vain kuvakkeilla käytännön tarkoituksiin.

Alla ovat erilaiset laajuudet, joita tuetaan `Button`-komponentille: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Painike" />

## Parhaat käytännöt {#best-practices}

Jotta varmistat optimaalisen käyttäjäkokemuksen `Button`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja tiivistä tekstiä `Button`-komponentissasi, jotta sen tarkoitus on selkeä.

2. **Sopiva visuaalinen tyylittely**: Harkitse painikkeen visuaalista tyylittelyä ja teemaa varmistaaksesi, että se on yhdenmukainen sovelluksesi muotoilun kanssa. Esimerkiksi:
  > - "Peruuta" `Button`-komponentin tulisi olla tyylitelty asianmukaisella teemalla tai CSS-tyylillä, jotta käyttäjät ovat varmoja, että he haluavat peruuttaa toiminnon
  > - "Vahvista" `Button` olisi erilaisten tyylien omaava verrattuna "Peruuta" -painikkeeseen, mutta sen tulisi myös erottua varmistaakseen, että käyttäjät tietävät, että tämä on erityinen toiminto.

3. **Tehokas tapahtumakäsittely**: Käsittele `Button`-tapahtumat tehokkaasti ja tarjoa käyttäjille asianmukaista palautetta. Katso [Tapahtumat](../building-ui/events) tarkistaaksesi tehokkaita tapahtumien lisäämiskäyttäytymisiä.

4. **Testaus ja esteettömyys**: Testaa painikkeen käyttäytymistä erilaisissa skenaarioissa, kuten kun se on poistettu käytöstä tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Seuraa esteettömyysohjeita, jotta `Button` on käytettävissä kaikille käyttäjille, mukaan lukien ne, jotka käyttävät apuvälineitä.
