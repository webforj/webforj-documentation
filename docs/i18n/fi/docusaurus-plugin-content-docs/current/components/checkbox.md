---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

`CheckBox`-luokka luo komponentin, joka voidaan valita tai poistaa valinnasta, ja joka näyttää tilansa käyttäjälle. Kun siihen klikataan, ruutuun ilmestyy rastimerkki, mikä osoittaa myönteisen valinnan (päällä). Kun siihen klikataan uudelleen, rastimerkki katoaa, mikä osoittaa kielteisen valinnan (pois). 

Tarjoamalla selkeän ja yksinkertaisen visuaalisen osoituksen valintatilasta, valintaruudut parantavat käyttäjävuorovaikutusta ja päätöksentekoa, mikä tekee niistä välttämättömiä elementtejä nykyaikaisissa käyttöliittymissä.

## Käytöt {#usages}

`CheckBox`-komponenttia on parasta käyttää tilanteissa, joissa käyttäjien on tehtävä useita valintoja vaihtoehtojen luettelosta. Tässä on joitakin esimerkkejä tilanteista, jolloin `CheckBox`-komponenttia kannattaa käyttää:

1. **Tehtävien tai ominaisuuksien valinta**: Valintaruutuja käytetään yleisesti, kun käyttäjien on valittava useita tehtäviä tai ominaisuuksia, jotta he voivat suorittaa tiettyjä toimintoja tai asetuksia.

2. **Mieltymyksien asetukset**: Sovelluksissa, joissa on mieltymys- tai asetuspaneeleja, käytetään usein valintaruutuja, jotta käyttäjät voivat valita useita vaihtoehtoja vaihtoehtokokoelmasta. Tämä on parasta vaihtoehdoille, jotka eivät ole keskenään poissulkevia. Esimerkiksi:

> - Ilmoitusten mahdollistaminen tai poistaminen
> - Tumma tai vaalean tilan teeman valitseminen
> - Sähköpostilähetysten valinta

3. **Suodattaminen tai lajittelu**: `CheckBox`-komponenttia voidaan käyttää sovelluksissa, joissa käyttäjien on valittava useita suodattimia tai kategorioita, kuten hakutulosten suodattamisessa tai useiden kohteiden valitsemisessa jatkotoimia varten.

4. **Lomakekentät**: Valintaruutujaa käytetään yleisesti lomakkeissa, jotta käyttäjät voivat valita useita vaihtoehtoja tai tehdä binäärisiä valintoja. Esimerkiksi:
   > - Tilaa uutiskirje
   > - Hyväksy ehdot ja säännöt
   > - Valitse ostettavat tai varattavat kohteet

## Teksti ja sijoittaminen {#text-and-positioning}

Valintaruudut voivat hyödyntää <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> -metodia, joka sijoitetaan valintaruudun lähelle sisäänrakennetun <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> mukaan.

Valintaruutuilla on sisäänrakennettu toiminnallisuus asettaa teksti, joka näytetään joko ruudun oikealla tai vasemmalla puolella. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Tekstin sijoittamista tuetaan käyttämällä <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> -enumia. Alla on esitetty kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Epävarmuus {#indeterminism}

`CheckBox`-komponentti tukee epävarmuutta, joka on käyttöliittymäkuvio, jota käytetään yleisesti lomakkeissa ja listoissa osoittamaan, että ryhmässä valintaruutuja on sekä valittuja että valitsemattomia tiloja. Tämä tila esitetään kolmantena visuaalisena tilana, jota tavallisesti näytetään täytettynä neliönä tai viivana valintaruudussa. On olemassa muutamia yleisiä käyttötapauksia, jotka liittyvät epävarmuuteen:

- **Useiden kohteiden valinta**: Epävarmuus on hyödyllinen, kun käyttäjät tarvitsevat useiden kohteiden valitsemista luettelosta tai vaihtoehtojen joukosta. Se mahdollistaa käyttäjien ilmaisemisen, että he haluavat valita joitakin, mutta eivät kaikkia saatavilla olevista vaihtoehdoista.

- **Hierarkkinen data**: Epävarmuutta voidaan käyttää tilanteissa, joissa valintaruutujen välillä on hierarkkinen suhde. Esimerkiksi, kun valitaan kategorioita ja alikategorioita, epävarmuus voi edustaa, että jotkut alikategoriat on valittu, kun taas toiset eivät, ja vanhempi komponentti on epävarmassa tilassa.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Tyylit {#styling}

### Ulottuvuudet {#expanses}

Seuraavat <JavadocLink type="foundation" location="com/webforj/component/Expanse">ulottuvuudet</JavadocLink> mahdollistavat nopean tyylittelyn ilman CSS:ää. Ulottuvuuksia tuetaan käyttämällä `Expanse`-enum-luokkaa. Alla on luettelo ulottuvuuksista, joita tuetaan valintaruutukomponentille: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Parhaat käytännöt {#best-practices}

Jotta `Checkbox`-komponentin käytön käyttäjäkokemus olisi optimaalinen, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeästi merkitty vaihtoehdot**: Tarjoa selkeät ja ytimekkäät etiketit jokaiselle `CheckBox`-vaihtoehdolle, jotta valinta voidaan kuvata tarkasti. Etiketit tulisi olla helposti ymmärrettäviä ja erottuvia toisistaan.

2. **Ryhmittele valintaruudut**: Ryhmittele liittyvät valintaruudut yhteen osoittamaan niiden suhde. Tämä auttaa käyttäjiä ymmärtämään, että useita vaihtoehtoja voidaan valita tietyn ryhmän sisällä.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista valintaruutuille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäistä kertaa. Oletusvalinnan tulisi vastata yleisintä tai suosittua valintaa.

4. **Epävarmuus**: Jos vanhemmalla `CheckBox`-komponentilla on useita siihen kuuluvia komponentteja siten, että jotkut voidaan valita ja toiset poistaa valinnasta, käytä epävarmaa ominaisuutta osoittaaksesi, että ei kaikkia `CheckBox`-komponentteja ole valittu tai poistettu valinnasta.
