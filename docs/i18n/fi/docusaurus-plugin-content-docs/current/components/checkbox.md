---
title: CheckBox
sidebar_position: 20
_i18n_hash: e5ace9c598a0892cfa456f376035c87a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

`CheckBox` voidaan valita tai poistaa valinnasta, ja se näyttää nykyisen tilansa rastina. Valintaruudut toimivat hyvin yksittäisten asetusten kytkemiseen tai käyttäjille useiden vaihtoehtojen valitsemiseen joukosta.

<!-- INTRO_END -->

## Käytöt {#usages}

`CheckBox` on parasta käyttää skenaarioissa, joissa käyttäjien tulee tehdä useita valintoja vaihtoehtolistasta. Tässä on joitakin esimerkkejä siitä, milloin käyttää `CheckBox`:

1. **Tehtävän tai ominaisuuden valinta**: Valintaruutuja käytetään yleisesti, kun käyttäjien on valittava useita tehtäviä tai ominaisuuksia suorittaakseen tiettyjä toimintoja tai määrityksiä.

2. **Mieltymysasetukset**: Sovellukset, joissa on mieltymys- tai asetuspaneeleja, käyttävät usein valintaruutuja, jotta käyttäjät voivat valita useita vaihtoehtoja valikoimasta. Tämä on parasta vaihtoehdoille, jotka eivät ole toisiaan poissulkevia. Esimerkiksi:

> - Ilmoitusten käyttöönottaminen tai poistaminen käytöstä
> - Tumman tai vaalean teeman valitseminen
> - Sähköposti-ilmoitusten mieltymyksien valitseminen

3. **Suodattaminen tai lajittelu**: `CheckBox` voidaan käyttää sovelluksissa, joissa käyttäjien on valittava useita suodattimia tai kategorioita, kuten hakutulosten suodattamisessa tai useiden kohteiden valitsemisessa jatkotoimille.

4. **Lomakekäytännöt**: Valintaruutuja käytetään yleisesti lomakkeissa, jotta käyttäjät voivat valita useita vaihtoehtoja tai tehdä binääri valintoja. Esimerkiksi:
   > - Tilaudu uutiskirjeeseen
   > - Hyväksy ehdot ja edellytykset
   > - Valitse oston tai varauksen kohteet

## Teksti ja asettelu {#text-and-positioning}

Valintaruudut voivat käyttää <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> -metodia, joka sijoitetaan lähelle valintaruutua sisäänrakennetun <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Asettelu</JavadocLink> mukaan.

Valintaruuduilla on sisäänrakennettua toiminnallisuutta tekstin asettamiseen, joka voidaan näyttää joko laatikon oikealla tai vasemmalla puolella. Oletuksena teksti näytetään komponentin oikealla puolella. Tekstin asettelua tuetaan käyttämällä <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Asettelu</JavadocLink> enumia. Alla näytetään kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Epävarmuus {#indeterminism}

`CheckBox`-komponentti tukee epävarmuutta, joka on käyttöliittymäkuvio, jota käytetään yleisesti lomakkeissa ja listoissa osoittamaan, että ryhmä valintaruutuja sisältää sekoituksen valittuja ja valitsemattomia tiloja. Tämä tila esitetään kolmannella visuaalisella tilalla, joka yleensä näkyy täytettynä neliöinä tai viivana valintaruudun sisällä. Epävarmuuden kanssa on muutamia yleisiä käyttötapauksia:

- **Useiden kohteiden valinta**: Epävarmuus on hyödyllistä, kun käyttäjien on valittava useita kohteita luettelosta tai vaihtoehtosarjasta. Se mahdollistaa käyttäjien osoittaa, että he haluavat valita joitakin, mutta eivät kaikkia, saatavilla olevista vaihtoehdoista.

- **Hierarkkinen tieto**: Epävarmuutta voidaan hyödyntää tilanteissa, joissa valintaruutujen välillä on hierarkkinen suhde. Esimerkiksi, kun valitaan kategorioita ja alikategorioita, epävarmuus voi edustaa sitä, että jotkin alikategoriat on valittu, kun taas toiset eivät, ja vanhempi komponentti on epävarma tila.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Tyylit {#styling}

### Laajuudet {#expanses}

Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Laajuus-arvot </JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:ää.
Laajuudet tukevat `Expanse` enum-luokan käyttöä. Alla on laajuudet, joita tuetaan valintaruutu komponentissa: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Paras käytäntö {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi `Checkbox`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Selvitä vaihtoehdot**: Anna selkeät ja ytimekkäät etiketit jokaiselle `CheckBox` vaihtoehdolle, jotta voit tarkasti kuvata valintaa. Etiketit tulisi olla helposti ymmärrettäviä ja erottuvia toisistaan.

2. **Ryhmittele valintaruudut**: Ryhmittele samankaltaiset valintaruudut yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että useita vaihtoehtoja voidaan valita tietyssä ryhmässä.

3. **Tarjoa oletusvalinta**: Jos soveltuu, harkitse oletusvalinnan tarjoamista valintaruuduille, jotta voit ohjata käyttäjiä, kun he ensimmäisen kerran kohtaavat vaihtoehdot. Oletusvalinnan tulisi vastata yleisintä tai toivottua valintaa.

4. **Epävarmuus**: Jos vanhempi `CheckBox`-komponentti sisältää useita siihen kuuluu komponenttia siten, että jotkin voidaan valita päälle ja toiset pois, käytä epävarmaa ominaisuutta näyttämään, että kaikki `CheckBox`-komponentit eivät ole valittuja tai valitsemattomia.
