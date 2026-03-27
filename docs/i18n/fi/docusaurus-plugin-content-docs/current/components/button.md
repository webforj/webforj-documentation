---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Button` on napsautettava elementti, joka laukaisee toiminnon, kun sitä painetaan. Se voi näyttää tekstiä, kuvakkeita tai molempia yhdistelminä. Nappulat tukevat useita visuaalisia teemoja ja kokoja, ja ne voidaan estää, jotta estetään vuorovaikutus pitkien operaatioiden aikana tai kun tietyt ehdot eivät täyty.

<!-- INTRO_END -->

## Käytännöt {#usages}

`Button`-luokka on monipuolinen komponentti, jota käytetään yleisesti eri tilanteissa, joissa käyttäjävuorovaikutuksia ja toimintoja on tarpeen laukaista. Tässä on joitakin tyypillisiä tilanteita, joissa saatat tarvita nappia sovelluksessasi:

1. **Lomakkeen lähettäminen**: Nappeja käytetään usein lomaketietojen lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä"-painiketta tietojen lähettämiseen palvelimelle
  > - "Tyhjennä"-painiketta poistamaan lomakkeella jo olevat tiedot


2. **Käyttäjän toiminnot**: Nappeja käytetään antamaan käyttäjille mahdollisuus suorittaa tiettyjä toimintoja sovelluksessa. Esimerkiksi voit olla napin merkinnällä:

  > - "Poista", joka aloittaa valitun kohteen poistamisen
  > - "Tallenna" muuttaakseen asiakirjan tai sivun muutoksia.

3. **Vahvistusdialogit**: Nappeja sisältyy usein [`Dialog`](../components/dialog) -komponentteihin, jotka on rakennettu eri tarkoituksia varten, jotta käyttäjille tarjotaan vaihtoehtoja vahvistaa tai peruuttaa toiminto, tai mitä tahansa muuta toimintoa, joka on rakennettu käyttämällesi [`Dialog`](../components/dialog).

4. **Vuorovaikutuksen laukaisijat**: Nappeja voidaan käyttää vuorovaikutusten tai tapahtumien laukaisemiseen sovelluksessa. Napsauttamalla nappia käyttäjät voivat aloittaa monimutkaisia toimintoja tai laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigaatio**: Nappeja voidaan käyttää navigointitarkoituksiin, kuten siirtymiseen eri osien tai sivujen välillä sovelluksessa. Navigointinappeja voisivat olla:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivulla.
  > - "Edellinen" - palauttaa käyttäjän edelliselle sivulle sovelluksessa tai osioon, jossa he ovat.
  > - "Takaisin" - palauttaa käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa he ovat.
  
Seuraavassa esimerkissä demonstroidaan nappien käyttö lomakkeen lähettämisessä ja syötteen tyhjentämisessä:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Kuvakkeiden lisääminen nappeihin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen sisällyttäminen napin yhteyteen voi parantaa sovelluksesi suunnittelua huomattavasti, mikä mahdollistaa käyttäjien nopean tunnistamisen toimivista kohteista näytöllä. [`Icon`](./icon.md) komponentti tarjoaa laajan valikoiman valittavia kuvakkeita.

Hyödyntämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä, sinulla on joustavuutta päättää, näytetäänkö `Icon` ennen tai jälkeen tekstin napissa. Vaihtoehtoisesti `setIcon()` -menetelmää voidaan käyttää `Icon`-kuvakkeen lisäämiseksi tekstin jälkeen, mutta ennen napin `suffix`-slotia.

<!-- Lisää tämä takaisin, kun Icon on yhdistetty -->
<!-- Katso [Icon komponentti](../components/icon) -sivua lisätietoja kuvakkeiden määrittämisestä ja mukauttamisesta. -->

:::tip
Oletusarvoisesti `Icon` perii napin teeman ja koon.
:::

Alla on esimerkkejä napeista, joissa teksti on vasemmalla ja oikealla, sekä nappi, jossa on vain kuvake:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nimet {#names}

`Button`-komponentti hyödyntää nimeämistä, jota käytetään saavutettavuudessa. Kun nimeä ei ole asetettu erikseen, käytetään nappulan etikettiä. Kuitenkin jotkin kuvakkeet eivät omaa etikettejä, ja näyttävät vain ei-tekstimäisiä osia, kuten kuvakkeita. Tässä tapauksessa on tarpeellista käyttää `setName()` -menetelmää varmistaaksesi, että luotu `Button`-komponentti täyttää saavutettavuusstandardit.

## Napin estäminen {#disabling-a-button}

Nappikomponentit, kuten monet muut, voidaan estää ilmoittamaan käyttäjälle, että tietty toiminto ei ole vielä tai ei enää käytettävissä. Estetty nappi vähentää napin opasiteettia, ja se on saatavilla kaikille napin teemoille ja kokoille.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Napin estäminen voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> -funktiota. Lisämukavuutta varten nappi voidaan myös estää napsauttamalla käyttäen sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> -funktiota.

Joissakin tapauksissa napin napsauttaminen laukaisee pitkään kestäviä toimintoja. Napin estäminen, kun sovelluksesi käsittelee toimintoa, estää käyttäjää napsauttamasta nappia useita kertoja, erityisesti korkealaatuisten ympäristöjen aikana.

:::tip
Napsautustapahtuman estäminen ei ainoastaan optimoi toimintojen käsittelyä, vaan myös estää kehittäjää toteuttamasta tätä käyttäytymistä itse, sillä tämä menetelmä on optimoitu vähentämään takaisinmatkaviestintää.
:::

## Tyylit {#styling}

### Teemat {#themes}

`Button`-komponentit tulevat <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erilaista teemaa</JavadocLink> valmiina nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylit, jotka voidaan soveltaa nappeihin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopeat ja johdonmukaiset tavat mukauttaa nappien ulkoasua koko sovelluksessa.

Vaikka jokaiselle eri teeman käyttötarkoitukselle on monia mahdollisuuksia, joitakin esimerkkejä käytöstä ovat:

  - **Vaarallinen**: Paras toimille, joilla on vakavia seurauksia, kuten täytetyn tiedon tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
  - **Oletus**: Sopii sovelluksessa käytettäville toiminnoille, jotka eivät vaadi erityistä huomiota ja ovat geneerisiä, kuten asetuksen vaihtaminen.
  - **Pääasiallinen**: Sopii pää "toimintaan" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Erinomainen visualisoimaan onnistunutta loppuosa-asiaa sovelluksessa, kuten lomakkeen tai rekisteröitymisprosessin lopettamista. Onnistuminen-teeman voi ohjelmallisesti soveltaa, kun onnistunut toiminta on suoritettu.
  - **Varoitus**: Kätevä indikoimaan, että käyttäjä on aikeissa suorittaa potentiaalisesti riskialttiina toimintoja, kuten siirtyä sivulta, jossa on tallentamattomia muutoksia. Nämä toiminnot ovat yleensä vähemmän vaikuttavia kuin vaaralliset teemat.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä eivätkä tha-osa päätoiminnallisuudesta.
  - **Tieto**: Hyvä lisäävän selventävän tiedon tarjoamiseen käyttäjälle.

Alla olevat esimerkit esittelevät nappeja, joihin on sovellettu jokainen tuettu teema: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Koot {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Koot arvot </JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Tämä mahdollistaa napin ulottuvuuden manipuloinnin ilman, että sitä tarvitsee säättää eksplisiittisesti minkään tyyli-menetelmän kautta. Yhdessä yksinkertaistuksen tyylittelyn kanssa se auttaa myös luomaan ja ylläpitämään yhtenäisyyttä sovelluksessasi. Oletusarvoinen `Button`-koko on `Expanse.MEDIUM`.

Eri kokoja on usein sopivampi käyttää eri tarkoituksiin:
  - **Suuremmat** koon arvot sopivat nappeille, jotka ovat huomion kiinnittämiseksi, korostavat toiminnallisuutta tai ovat keskeisiä sovelluksen tai sivun toiminnalle.
  - **Keskikokoiset** napit, oletuskoko, olisivat nappien standardikoko. Näiden napin toimintojen tulisi olla yhtä tärkeitä kuin samankaltaisten komponenttien.
  - **Pienemmät** koon arvot tulisi käyttää nappeille, joilla ei ole keskeisiä toimintoja sovelluksessa, ja jotka palvelevat enemmän täydentäviä tai utilitaarisia rooleja, eikä tärkeitä osia käyttäjän vuorovaikutuksessa. Tämä sisältää `Button`-komponentit, joita käytetään vain kuvakkeilla utilitaarisista syistä.

Alla ovat erilaiset koot, jotka ovat tuettu `Button`-komponentille: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käytettäessä `Button`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selkeää ja ytimekästä tekstiä `Button`-komponentissasi, jotta sen tarkoituksen ymmärtäminen on selkeää.

2. **Sopiva visuaalinen tyylitys**: Huomioi napin visuaalinen tyyli ja teema varmistaaksesi johdonmukaisuuden sovelluksesi suunnittelun kanssa. Esimerkiksi:
  > - "Peruuta" `Button`-komponentti tulisi tyylittää asianmukaisella teemalla tai CSS-tyylillä, jotta käyttäjät ovat varmoja siitä, että he haluavat peruuttaa toiminnon.
  > - "Vahvista" `Button`-komponentti olisi erilainen tyyliltään kuin "Peruuta" nappi, mutta erottuisi myös varmistaakseen, että käyttäjät tietävät, että tämä on erityinen toiminto.

3. **Tehokas tapahtumakäsittely**: Käsittele `Button`-tapahtumat tehokkaasti ja anna käyttäjille asianmukaista palautetta. Viittaa [Tapahtumat](../building-ui/events) tarkastellaksesi tehokkuuden lisäämistä tapahtumakäyttäytymiseen.

4. **Testaus ja saavutettavuus**: Testaa napin käyttäytymistä eri tilanteissa, kuten silloin, kun se on estetty tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata saavutettavuusohjeita varmistaaksesi, että `Button` on käytettävissä kaikille käyttäjille, mukaan lukien ne, jotka luottavat apuvälineisiin.
