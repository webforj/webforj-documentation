---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button` on napsautettava elementti, joka laukaisee toiminnon, kun sitä painetaan. Se voi näyttää tekstiä, kuvakkeita tai molempia. Nappulat tukevat useita visuaalisia teemoja ja kokoja, ja ne voidaan estää estämään vuorovaikutus pitkäkestoisten toimintojen aikana tai kun tietyt ehdot eivät täyty.

<!-- INTRO_END -->

## Käytöt {#usages}

`Button`-luokka on monipuolinen komponentti, jota käytetään usein erilaisissa tilanteissa, joissa käyttäjävuorovaikutuksia ja toimintoja on laukaistava. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat tarvita painiketta sovelluksessasi:

1. **Lomakkeen lähetys**: Nappuloita käytetään usein lomaketietojen lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä" -painiketta tietojen lähettämiseen palvelimelle
  > - "Tyhjennä" -painiketta poistamaan lomakkeessa jo olevat tiedot


2. **Käyttäjän toiminnot**: Nappuloita käytetään sallimaan käyttäjien suorittaa tiettyjä toimintoja sovelluksessa. Esimerkiksi voit käyttää painiketta, jonka etiketti on:

  > - "Poista" aloittaaksesi valitun kohteen poistamisen
  > - "Tallenna" tallentaaksesi asiakirjaan tai sivulle tehdyt muutokset.

3. **Vahvistusdialogit**: Nappuloita sisältyy usein [`Dialog`](../components/dialog) -komponentteihin, jotka on rakennettu erilaisiin tarkoituksiin tarjotakseen käyttäjille vaihtoehtoja vahvistaa tai peruuttaa toiminto tai muuta toiminnallisuutta, joka on rakennettu käyttämääsi [`Dialog`](../components/dialog) -komponenttiin.

4. **Vuorovaikutustapahtumat**: Nappulat voivat toimia vuorovaikutuksen tai tapahtumien laukaisijana sovelluksessa. Napsauttamalla painiketta käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigointi**: Nappuloita voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osioihin tai sivuille sovelluksessa. Navigointiin tarkoitetut painikkeet voisivat sisältää:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivulla.
  > - "Edellinen" - palauttaa käyttäjän edelliselle sivulle sovelluksessa tai osioon, jossa hän on.
  > - "Takaisin" palauttaa käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa hän on.
  
Seuraava esimerkki havainnollistaa painikkeita lomakkeen lähettämisessä ja syötteen tyhjentämisessä:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Kuvakkeiden lisääminen painikkeisiin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen sisällyttäminen painikkeeseen voi parantaa sovelluksesi muotoilua huomattavasti, jotta käyttäjät voivat nopeasti tunnistaa toiminnalliset kohteet näytöllä. [`Icon`](./icon.md) -komponentti tarjoaa laajan valikoiman kuvakkeita valittavaksi.

Hyödyntämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä voit joustavasti määrittää, kuuluuko `Icon` tekstin eteen tai taakse painikkeessa. Voit myös käyttää `setIcon()`-menetelmää lisätäksesi `Icon`-kuvakkeen tekstin jälkeen, mutta ennen painikkeen `suffix`-slotia.

<!-- Lisää tämä takaisin, kun kuvake on yhdistetty -->
<!-- Katso [Icon komponentti](../components/icon) -sivulta lisätietoja kuvakkeiden konfiguroimisesta ja mukauttamisesta. -->

:::tip
Oletusarvoisesti `Icon` perii painikkeen teeman ja laajuuden.
:::

Alla on esimerkkejä painikkeista, joissa teksti on vasemmalla ja oikealla, sekä painike, jossa on vain kuvake:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nimitys {#names}

`Button`-komponentti hyödyntää nimeämistä, joka on käytössä saavutettavuudessa. Kun nimeä ei ole asetettu, painikkeen etiketti käytetään sen sijaan. Jotkin kuvakkeet eivät kuitenkaan sisällä etikettejä ja näyttävät vain ei-tekstielementtejä, kuten kuvakkeita. Tässä tapauksessa on hyödyllistä käyttää `setName()`-menetelmää varmistaaksesi, että luotu `Button`-komponentti täyttää saavutettavuusstandardit.

## Painikkeen estäminen {#disabling-a-button}

Painikekomponentteja, kuten monia muita, voidaan estää viestimään käyttäjälle, että tietty toiminto ei ole vielä saatavilla tai ei ole enää saatavilla. Estetty painike vähentää painikkeen läpinäkyvyyttä ja se on saatavilla kaikille painiketeemoille ja laajuuksille.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Painikkeen estäminen voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -funktiota. Lisää mukavuutta varten painike voidaan myös estää napsautettaessa käyttäen sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -funktiota.

Joissakin tapauksissa painikkeen napsauttaminen laukaisee pitkäkestoisen toiminnon. Painikkeen estäminen, kun sovelluksesi käsittelee toimintoa, estää käyttäjää napsauttamasta painiketta useita kertoja, erityisesti korkean viiveen ympäristöissä.

:::tip
Napsautuksen estäminen ei ainoastaan auta optimoimaan toimintojen käsittelyä, vaan myös estää kehittäjää toteuttamasta tätä käyttäytymistä itse, koska tämä menetelmä on optimoitu vähentämään matkaviestintää.
:::

## Tyylittely {#styling}

### Teemat {#themes}

`Button`-komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erilaista teemaa</JavadocLink>, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:ää. Nämä teemat ovat ennakkoon määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua koko sovelluksessa. 

Vaikka jokaiselle eri teemaesitykselle on monia käyttötarkoituksia, joitakin esimerkkejä ovat:

  - **Vaara**: Paras vaikeiden seurauksien toimille, kuten täytettyjen tietojen tyhjentämiselle tai tilin/tietojen pysyvälle poistamiselle.
  - **Oletus**: Sopii sovelluksen toimille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen kytkemiselle.
  - **Ensisijainen**: Sopii pää "toimintakutsuna" sivulla, kuten rekisteröitymiseen, muutosten tallentamiseen tai siirtymiseen toiselle sivulle.
  - **Onnistuminen**: Erinomainen visualisoimaan jonkin elementin onnistumista sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin loppuunsaattamista. Onnistumisteemaa voidaan ohjelmallisesti soveltaa, kun onnistuva toiminto on suoritettu.
  - **Varoitus**: Hyödyllinen ilmoittamaan, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymään sivulta, jossa on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät Vaarateemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten pienille asetuksille tai toimille, jotka ovat enemmän sivurakennelmia ja eivät ole osa päätoimintoa.
  - **Tiedot**: Hyvä tarjoamaan lisäselvyyttä käyttäjille.

Alla on esimerkkejä painikkeista, joissa on käytössä jokainen tuettu teema: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### Laajuudet {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Laajuus arvot </JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Tämä mahdollistaa painikkeen mittojen muokkaamisen ilman, että sitä tarvitsee määrittää erikseen käyttämällä mitään tyylittelyä. Tyylittelyn yksinkertaistamisen lisäksi se auttaa myös luomaan ja ylläpitämään johdonmukaisuutta sovelluksessa. Oletusarvoinen `Button`-laajuus on `Expanse.MEDIUM`.

Erilaiset koot ovat usein sopivia erilaisiin käyttötarkoituksiin:
  - **Suuremmat** laajuusarvot sopivat painikkeille, joiden tulisi herättää huomiota, korostaa toiminnallisuutta tai olla olennainen osa sovelluksen tai sivun ydintoimintoa.
  - **Keskikokoiset** laajuuspainikkeet, oletuskoko, tulisi olla painikkeiden standardikoko. Näiden painikkeiden toiminnot eivät tulisi olla kriittisempiä eikä vähemmän kriittisiä kuin vastaavien komponenttien.
  - **Pienemmät** laajuusarvot tulisi käyttää painikkeille, joilla ei ole keskeisiä toimintoja sovelluksessa, ja jotka palvelevat enemmän lisä tai utilitaristista roolia, eivätkä ole tärkeitä käyttäjävuorovaikutuksessa. Tämä sisältää `Button`-komponentteja, joita käytetään vain kuvakkeiden kanssa utilitaristisiin tarkoituksiin.

Alla ovat erilaiset laajuudet, joita `Button`-komponentti tukee: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Button`-komponenttia käyttäessäsi, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja ytimekästä tekstiä `Button`-komponentissasi, jotta sen tarkoitus on selkeä.

2. **Sopiva visuaalinen tyylittely**: Ota huomioon painikkeen visuaalinen tyyli ja teema varmistaaksesi johdonmukaisuuden sovelluksesi suunnittelun kanssa. Esimerkiksi:
  > - "Peruuta" `Button`-komponentti tulisi muotoilla oikealla teemalla tai CSS-tyylillä varmistaaksesi, että käyttäjät ovat varmoja siitä, että he haluavat peruuttaa toiminnon
  > - "Vahvista" `Button` olisi eri tyyli kuin "Peruuta"-painike, mutta erottuisi myös varmistaakseen, että käyttäjät tietävät, että tämä on erityinen toimenpide.

3. **Tehokas tapahtumankäsittely**: Käsittele `Button`-tapahtumia tehokkaasti ja tarjoa käyttäjille asianmukaista palautetta. Katso [Tapahtumat](../building-ui/events) tehokkaista tapahtumien lisäämis käytäntöjen tarkastelemiseksi.

4. **Testaus ja saavutettavuus**: Testaa painikkeen käyttäytymistä eri skenaarioissa, kuten silloin, kun se on estetty tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata saavutettavuusohjeita, jotta `Button` olisi käytettävissä kaikille käyttäjille, mukaan lukien ne, jotka luottavat apuvälineisiin.
