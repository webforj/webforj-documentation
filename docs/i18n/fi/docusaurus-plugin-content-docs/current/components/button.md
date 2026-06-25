---
title: Button
sidebar_position: 15
_i18n_hash: 4f84dd4c618dafe32cbeb47c7dcbbe36
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

`Nappi` on klikattava elementti, joka käynnistää toiminnon, kun sitä painetaan. Se voi näyttää tekstiä, kuvakkeita tai molempia. Napit tukevat useita visuaalisia teemoja ja kokoja, ja ne voidaan poistaa käytöstä estämään vuorovaikutuksen pitkäkestoisten toimintojen aikana tai kun tietyt ehdot eivät täyty.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`Nappi`-luokka on monikäyttöinen komponentti, jota käytetään yleisesti erilaisissa tilanteissa, joissa käyttäjävuorovaikutuksia ja toimintoja on käynnistettävä. Tässä on joitakin tyypillisiä tilanteita, joissa saatat tarvita nappia sovelluksessasi:

1. **Lomakkeen lähetys**: Napit käytetään usein lomaketietojen lähettämiseen. Esimerkiksi sovelluksessa voit käyttää:

  > - "Lähetä" nappia tietojen lähettämiseksi palvelimelle
  > - "Tyhjennä" nappia kaiken lomakkeessa jo olevan tiedon poistamiseksi

2. **Käyttäjätoiminnot**: Napit käytetään antamaan käyttäjille mahdollisuus suorittaa tiettyjä toimintoja sovelluksessa. Esimerkiksi sinulla voi olla nappi, jossa on otsikko:

  > - "Poista" aloittamaan valitun kohteen poistaminen
  > - "Tallenna" tallentamaan dokumenttiin tai sivulle tehdyt muutokset.

3. **Vahvistusdialogit**: Napit sisältyvät usein [`Dialog`](../components/dialog) komponentteihin, joita on rakennettu erilaisiin tarkoituksiin, tarjoten käyttäjille vaihtoehtoja vahvistaa tai peruuttaa toimenpide tai muita toimintoja, jotka ovat rakennettu sisälle käyttämääsi [`Dialog`](../components/dialog).

4. **Vuorovaikutuksen laukaisijat**: Napit voivat toimia vuorovaikutuksen tai tapahtumien laukaisijoina sovelluksessa. Nappia klikkaamalla käyttäjät voivat aloittaa monimutkaisia toimintoja, laukaista animaatioita, päivittää sisältöä tai päivittää näyttöä.

5. **Navigointi**: Napit voivat olla navigointitarkoituksiin, kuten siirtyminen sovelluksen eri osien tai sivujen välillä. Navigointinapit voivat sisältää:

  > - "Seuraava" - vie käyttäjän seuraavalle sivulle tai osioon nykyisessä sovelluksessa tai sivulla.
  > - "Edellinen" - palauttaa käyttäjän edelliselle sivulle sovelluksessa tai osioon, jossa he ovat.
  > - "Takaisin" palauttaa käyttäjän sovelluksen tai sivun ensimmäiseen osaan, jossa he ovat.

Seuraava esimerkki demonstroi nappeja lomakkeen lähettämiseen ja tietojen tyhjentämiseen:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Kuvakkeiden lisääminen nappeihin <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Kuvakkeen liittäminen nappiin voi parantaa sovelluksesi suunnittelua merkittävästi, ja se antaa käyttäjille mahdollisuuden nopeasti tunnistaa, mitä toimintoja on käytettävissä näytöllä. [`Icon`](./icon.md) komponentti tarjoaa laajan valikoiman kuvakkeita valittavaksi.

Käyttämällä `setPrefixComponent()` ja `setSuffixComponent()` menetelmiä sinulla on joustavuus määrittää, tulisiko `Icon`-kuvakkeen näkyä ennen vai jälkeen napin tekstin. Alternatiivisesti `setIcon()` menetelmää voidaan käyttää lisäämään `Icon`-kuvakkeita tekstin jälkeen, mutta ennen napin `suffix`-slotia.

:::tip
Oletusarvoisesti `Icon` perii napin teeman ja koon.
:::

Alla on esimerkkejä napuista, joilla on tekstiä vasemmalla ja oikealla, sekä nappi, jossa on vain kuvake:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Nimitykset {#names}

`Nappi`-komponentti käyttää nimeämistä, joka on käytössä saavutettavuutta varten. Kun nimeä ei ole eksplisiittisesti asetettu, `Nappi`-komponentin etiketti käytetään sen sijaan. Kuitenkin jotkut kuvakkeet eivät omaa etikettejä, ja näyttävät vain ei-tekstielementtejä, kuten kuvakkeita. Tässä tapauksessa on hyödyllistä käyttää `setName()` menetelmää varmistaaksesi, että luotu `Nappi`-komponentti noudattaa saavutettavuusstandardeja.

## Napin poistaminen käytöstä {#disabling-a-button}

Nappikomponentteja, kuten monia muita, voidaan poistaa käytöstä viestittääkseen käyttäjälle, että tietty toiminto ei ole vielä käytettävissä tai se ei ole enää käytettävissä. Poistettu nappi vähentää napin opasiteettia, ja se on saatavilla kaikille napiteemoille ja -kokoille.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Napin poistaminen käytöstä voidaan tehdä milloin tahansa koodissa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> toimintoa. Lisämukavuuden vuoksi nappi voidaan myös poistaa käytöstä napsautettaessa käyttämällä sisäänrakennettua <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> toimintoa.

Joissakin tapauksissa napin napsauttaminen laukaisee pitkäkestoisen toiminnon. Napin poistaminen käytöstä kunnes sovelluksesi käsittelee toiminnon estää käyttäjiä napsauttamasta nappia useita kertoja, erityisesti korkean viiveen ympäristöissä.

:::tip
Napsautuksessa poistaminen käytöstä ei vain optimoi toimintojen prosessointia, vaan myös estää kehittäjää toteuttamasta tätä käyttäytymistä itse, sillä tämä menetelmä on optimoitu vähentämään kierrosmatka viestintää.
:::

## Tyylit {#styling}

### Teemat {#themes}

`Nappi`-komponentit tulevat mukana <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 erillisellä teemalla</JavadocLink>, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat esiasetettuja tyylejä, joita voidaan soveltaa nappeihin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja yhdenmukaisen tavan mukauttaa nappeja koko sovelluksessa.

Vaikka jokaiselle erilaiselle teemalle on monia käyttötarkoituksia, joitakin esimerkkejä käytöstä ovat:

  - **Vaarallinen**: Parhaiten toimenpiteille, joilla on vakavat seuraukset, kuten täytetyn tiedon tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
  - **Oletusarvo**: Sopiva koko sovelluksessa, jossa ei ole erityistä huomiota vaativia toimia ja jotka ovat geneerisiä, kuten asetuksen vaihtaminen.
  - **Pää**: Sopiva tärkeiksi "toimintakutsujeksi" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Erinomainen visualisoimaan elementin onnistunut täydentäminen sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin valmistumista. Onnistumistheema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Käytetään ilmoittamaan käyttäjälle, että hän on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toimenpiteet ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät Vaaralliselle teemalle.
  - **Harmaa**: Hyvä hienovaraisille toimenpiteille, kuten pienille asetuksille tai toimenpiteille, jotka ovat enemmän täydentäviä sivulle, eivätkä ole osa päätoiminnallisuutta.
  - **Tieto**: Hyvä lisätieto selventämään käyttäjälle.

Alla on esimerkkipainikkeet, joilla on jokainen tuettu teema käytössä: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Koot {#expanses}
Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse">Koot</JavadocLink> arvot mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Tämä mahdollistaa napin mittojen manipuloinnin ilman, että niitä täytyy eksplisiittisesti asettaa käyttäen mitään tyylittelyä. Tyylittelyn yksinkertaistamisen lisäksi se auttaa myös luomaan ja ylläpitämään yhdenmukaisuutta sovelluksessa. Oletusarvoinen `Nappi`-koko on `Expanse.MEDIUM`.

Eri kokoja on usein sopivaa käyttää eri käyttötarkoituksiin:
  - **Suuremmat** kokoarvot soveltuvat nappeihin, joilla tulisi olla huomion herättäminen, korostaa toiminnallisuutta tai olla keskeisiä sovelluksen tai sivun toiminnallisuudelle.
  - **Keskikokoiset** napit, oletuskoko, tulisi olla napin standardikoko. Näiden napin toimintojen ei tulisi olla sen tärkeämpiä kuin vastaavien komponenttien.
  - **Pienemmät** kokoarvot tulisi käyttää nappeihin, joilla ei ole keskeisiä toimintoja sovelluksessa, ja jotka palvelevat enemmän täydentävää tai utilitaristista roolia, sen sijaan, että ne olisivat tärkeä osa käyttäjävuorovaikutusta. Tämä sisältää `Nappi`-komponentteja, joita käytetään vain kuvakkeilla utilitaristisiin tarkoituksiin.

Alla ovat erilaiset koot, joita `Nappi`-komponentti tukee: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Nappi" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `Nappi`-komponentin käytön aikana, harkitse seuraavia parhaita käytäntöjä:

1. **Oikea teksti**: Käytä selvää ja tiivistä tekstiä `Nappi`-komponentissasi antaaksesi selvä viesti sen tarkoituksesta.

2. **Sopiva visuaalinen tyyli**: Ota huomioon `Nappi`-komponentin visuaalinen tyyli ja teema varmistaaksesi yhdenmukaisuuden sovelluksesi suunnittelussa. Esimerkiksi:
  > - "Peru" `Nappi`-komponentti tulisi tyylitellä oikealla teemalla tai CSS-tyylillä varmistaaksesi, että käyttäjät tietävät varmasti haluavansa peruuttaa toiminnon
  > - "Vahvista" `Nappi`-komponentti olisi tyyliltään erilainen kuin "Peru" nappi, mutta myös samalla tavalla erottuva, jotta käyttäjät ymmärtävät, että tämä on erityinen toiminto.

3. **Tehokas tapahtumakäsittely**: Käsittele `Nappi`-tapahtumia tehokkaasti ja anna käyttäjille asianmukaista palautetta. Viittaa [Tapahtumat](../building-ui/events) tarkastellaksesi tehokkaita tapahtumien lisäämis käytäntöjä.

4. **Testaus ja saavutettavuus**: Testaa napin käyttäytymistä eri tilanteissa, kuten silloin kun se on poistettu käytöstä tai saa fokuksen, varmistaaksesi sujuvan käyttäjäkokemuksen. Noudata saavutettavuusohjeita, jotta `Nappi` on käytettävissä kaikille käyttäjille, mukaan lukien niille, jotka luottavat apuvälineisiin.
