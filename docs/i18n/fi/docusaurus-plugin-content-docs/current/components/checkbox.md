---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

`CheckBox` voi olla valittuna tai poistettuna, ja se näyttää nykyisen tilansa rastin muodossa. Valintaruudut toimivat hyvin yksittäisten asetusten kytkemisessä tai käyttäjien valitessa useita vaihtoehtoja joukosta.

<!-- INTRO_END -->

## Käytöt {#usages}

`CheckBox` on parasta käyttää tilanteissa, joissa käyttäjien on tehtävä useita valintoja vaihtoehtoluettelosta. Tässä on joitakin esimerkkejä siitä, milloin `CheckBox`-komponenttia kannattaa käyttää:

1. **Tehtävän tai ominaisuuden valinta**: Valintaruutuja käytetään yleisesti, kun käyttäjien on valittava useita tehtäviä tai ominaisuuksia tiettyjen toimien tai asetusten suorittamiseksi.

2. **Asetusten määrittäminen**: Sovelluksissa, jotka sisältävät asetusten tai mieltymysten paneeleja, käytetään usein valintaruuduilta, jotta käyttäjät voivat valita useita vaihtoehtoja joukosta. Tämä on parasta vaihtoehdoille, jotka eivät sulje toisiaan pois. Esimerkiksi:

> - Ilmoitusten kytkeminen päälle tai pois päältä
> - Tumma tai vaalea teema
> - Sähköposti-ilmoitusasetusten valinta

3. **Suodatus tai lajittelu**: `CheckBox`-komponenttia voidaan käyttää sovelluksissa, joissa käyttäjien on valittava useita suodattimia tai kategorioita, kuten hakutulosten suodattaminen tai useiden kohteiden valinta jatotoimia varten.

4. **Lomakekentät**: Valintaruutuja käytetään yleisesti lomakkeissa, jotta käyttäjät voivat valita useita vaihtoehtoja tai tehdä binaarisia valintoja. Esimerkiksi:
   > - Tilaa uutiskirje
   > - Hyväksy käyttöehdot
   > - Valitse ostettavat tai varattavat kohteet

## Teksti ja sijoittelu {#text-and-positioning}

Valintaruudut voivat hyödyntää <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>-metodia, joka sijoitetaan valintaruudun vierelle sisäänrakennetun <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Sijoitus</JavadocLink>-säännön mukaisesti.

Valintaruuduilla on sisäänrakennettu toiminto tekstin asettamiseksi, joka näytetään joko oikealla tai vasemmalla puolella ruutua. Oletuksena teksti näytetään komponentin oikealla puolella. Tekstin sijoittamista tuetaan käyttämällä <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>-enumia. Alla on kaksi asetusta: <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## Epävarmuus {#indeterminism}

`CheckBox`-komponentti tukee epävarmuutta, joka on käyttöliittymämalli, jota käytetään yleisesti lomakkeissa ja luetteloissa osoittamaan, että ryhmässä valintaruudut on sekoitettu valittuja ja valitsemattomia tiloja. Tämä tila esitetään kolmannella visuaalisella tilalla, joka tyypillisesti näytetään täytettynä neliönä tai viivana valintaruudussa. Epävarmuudella on joitakin yleisiä käyttötapauksia:

- **Useiden kohtien valitseminen**: Epävarmuus on hyödyllinen, kun käyttäjien on valittava useita kohtia luettelosta tai vaihtoehtojoukosta. Se antaa käyttäjille mahdollisuuden osoittaa, että he haluavat valita joitakin, mutta ei kaikkia saatavilla olevia vaihtoehtoja.

- **Hierarkkinen data**: Epävarmuutta voidaan käyttää tilanteissa, joissa on hierarkkinen suhde valintaruudun välillä. Esimerkiksi, kun valitaan kategorioita ja alakategorioita, epävarmuus voi edustaa sitä, että jotkut alakategoriat on valittu, kun taas toisia ei ole, ja vanhempi komponentti on epävarmassa tilassa.

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## Tyylitteleminen {#styling}

### Laajuudet {#expanses}

Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse">Laajuudet-arvot</JavadocLink> sallivat nopean tyylittelyn ilman CSS:n käyttöä. Laajuudet tukevat `Expanse`-enum-luokan käyttöä. Alla ovat laajuudet, joita tuetaan valintaruudukkomponentille: <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Parhaat käytännöt {#best-practices}

Jotta voit varmistaa optimaalisen käyttäjäkokemuksen `Checkbox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät vaihtoehtojen merkit**: Tarjoa selkeät ja ytimekkäät merkit kullekin `CheckBox`-vaihtoehdolle, jotta valinta kuvataan tarkasti. Merkit tulisi olla helppo ymmärtää ja erottua toisistaan.

2. **Ryhmittele valintaruudut**: Ryhmittele samankaltaiset valintaruudut yhteen osoittamaan niiden yhteys. Tämä auttaa käyttäjiä ymmärtämään, että useita vaihtoehtoja voidaan valita tietyssä ryhmässä.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista valintaruuduille ohjaamaan käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäistä kertaa. Oletusvalinnan tulee olla linjassa yleisimmän tai mieluisimman valinnan kanssa.

4. **Epävarmuus**: Jos vanhempi `CheckBox`-komponentti sisältää useita siihen kuuluvia komponentteja siten, että osia voidaan valita päälle ja toisia pois, käytä epävarmaa ominaisuutta osoittamaan, että kaikki `CheckBox`-komponentit eivät ole valittuja tai poissa valinnasta.
