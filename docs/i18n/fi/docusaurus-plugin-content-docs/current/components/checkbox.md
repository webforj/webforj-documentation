---
title: CheckBox
sidebar_position: 20
_i18n_hash: c2be55222401962b275faf28ff6ddba3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

`CheckBox`-luokka luo komponentin, joka voidaan valita tai kytkeä pois, ja joka näyttää tilansa käyttäjälle. Kun komponenttia napsautetaan, ruudussa näkyy rasti, joka indikoi myönteistä valintaa (päällä). Kun sitä napsautetaan jälleen, rasti katoaa, mikä osoittaa kielteistä valintaa (pois).

Antamalla selkeän ja yksinkertaisen visuaalisen osoituksen valintatilasta, valintaruutu parantaa käyttäjävuorovaikutusta ja päätöksentekoa, mikä tekee siitä olennaisen osan nykyaikaisia käyttöliittymiä.

## Käytöt {#usages}

`CheckBox`-komponenttia käytetään parhaiten tilanteissa, joissa käyttäjät tarvitsevat useita valintoja vaihtoehtoista luetteloa. Tässä on joitain esimerkkejä siitä, milloin `CheckBox`-komponenttia tulisi käyttää:

1. **Tehtävä- tai ominaisuusvalinta**: Valintaruutujaa käytetään yleisesti, kun käyttäjät tarvitsevat useiden tehtävien tai ominaisuuksien valitsemista tiettyjen toimien tai asetusten suorittamiseksi.

2. **Asetusvalinnat**: Sovellukset, jotka sisältävät asetuksia tai suosikki-paneeleja, käyttävät usein valintaruutuja mahdollistamaan käyttäjille useiden vaihtoehtojen valitsemisen valintojen joukosta. Tämä sopii parhaiten vaihtoehdoille, jotka eivät ole keskenään pois sulkevia. Esimerkiksi:

> - Ilmoitusten salliminen tai estäminen
> - Tumma tai vaalea teema
> - Sähköpostihälytysten valitseminen

3. **Suodatus tai lajittelu**: `CheckBox`-komponenttia voidaan käyttää sovelluksissa, joissa käyttäjät tarvitsevat useiden suodattimien tai kategorioiden valitsemista, kuten hakutulosten suodattaminen tai useiden kohteiden valitseminen jatkotoimia varten.

4. **Lomakekentät**: Valintaruutujaa käytetään yleisesti lomakkeissa antamaan käyttäjille mahdollisuus valita useita vaihtoehtoja tai tehdä binäärisiä valintoja. Esimerkiksi:
   > - Tilaa uutiskirje
   > - Hyväksy käyttöehdot
   > - Valitse ostettavat tai varattavat kohteet

## Teksti ja sijoittelu {#text-and-positioning}

Valintaruudut voivat hyödyntää <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>-metodia, joka sijoitetaan valintaruudun läheisyyteen sisäänrakennetun <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Sijoitus</JavadocLink> periaatteen mukaan.

Valintaruuduissa on sisäänrakennettu toiminnallisuus asettaa teksti näytettäväksi joko ruudun oikealla tai vasemmalla puolella. Oletuksena teksti näytetään komponentin oikealla puolella. Texin sijoittaminen on tuettu <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Sijoitus</JavadocLink>-enumeroinnilla. Alla on kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Epätietoisuus {#indeterminism}

`CheckBox`-komponentti tukee epätietoisuutta, joka on käyttöliittymäkuvio, jota käytetään usein lomakkeissa ja luetteloissa osoittamaan, että ryhmällä valintaruutuja on yhdistelmä valittuja ja valitsemattomia tiloja. Tämä tila esitetään kolmannessa visuaalisessa tilassa, joka yleensä näkyy täytettynä neliönä tai viivana valintaruutu sisällä. Epätietoisuuteen liittyy useita yleisiä käyttötarkoituksia:

- **Useiden kohteiden valitseminen**: Epätietoisuus on hyödyllistä, kun käyttäjien on tarkoitus valita useita kohteita luettelosta tai vaihtoehtojen joukosta. Se mahdollistaa käyttäjät osoittamaan, että he haluavat valita joitain, mutta ei kaikkia, käytettävissä olevista vaihtoehdoista.

- **Hierarkkinen data**: Epätietoisuutta voidaan käyttää skenaarioissa, joissa on hierarkkinen suhde valintaruudun välillä. Esimerkiksi, kun valitaan kategorioita ja alikategorioita, epätietoisuus voi osoittaa, että jotkut alikategoriat on valittu ja toiset eivät, ja vanhempi komponentti on epätietoinen tila.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Tyylit {#styling}

### Laajuudet {#expanses}

Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse">Laajuusarvot</JavadocLink> mahdollistavat nopean tyylin ilman CSS:ää. Laajuuksia tuetaan `Expanse`-enumeroinnin avulla. Alla on laajuudet, jotka ovat tuettu valintaruutu komponentille: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Checkbox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeästi merkitse vaihtoehtoja**: Anna selkeät ja ytimekkäät etiketit jokaiselle `CheckBox`-vaihtoehdolle kuvastaaksesi oikein valintaa. Etiketit tulisi olla helppo ymmärtää ja erottaa toisistaan.

2. **Ryhmittele valintaruudut**: Ryhmittele liittyvät valintaruudut yhdessä osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että useita vaihtoehtoja voidaan valita tietyssä ryhmässä.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista valintaruuduille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehtoja ensimmäisen kerran. Oletusvalinnan tulisi olla linjassa yleisimpien tai haluttujen valintojen kanssa.

4. **Epätietoisuus**: Jos vanhempi `CheckBox`-komponentti sisältää useita siihen kuuluvia komponentteja, joissa osa voidaan valita päälle ja osa pois, käytä epätietoista ominaisuutta osoittaaksesi, että kaikkia `CheckBox`-komponentteja ei ole valittu tai poistettu.
