---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-komponentti edustaa yhtä vaihtoehtoa, joka voidaan valita tai poistaa valinnasta. Radiosäätimet on tyypillisesti ryhmitelty yhteen siten, että valitseminen poistaa automaattisesti muiden valinnan, mahdollistaen käyttäjille yhden valinnan tekemisen keskenään eksklusiivisista vaihtoehdoista.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

`RadioButton`-komponenttia käytetään parhaiten tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennalta määritetystä vaihtoehtojen joukosta. Tässä on joitakin esimerkkejä, milloin käyttää `RadioButton`-komponenttia:

1. **Kyselyt tai Lomakkeet**: Radiosäätimiä käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen luettelosta.

2. **Preferenssiasetukset**: Sovelluksissa, jotka sisältävät asetuspaneeleita, käytetään usein radiosäätimiä, jotta käyttäjät voivat valita yhden vaihtoehdon keskenään eksklusiivisten valintojen joukosta.

3. **Suodatus tai Lajittelu**: `RadioButton`-komponenttia voidaan käyttää sovelluksissa, jotka edellyttävät käyttäjien valitsevan yhden suodatus- tai lajitteluvaihtoehdon, kuten luettelon lajitteleminen eri kriteerien mukaan.

:::tip `RadioButton`-komponenttien ryhmittely
Käytä [`RadioButtonGroup`](/docs/components/radiobuttongroup)-komponenttia hallitaksesi radiosäätimiä, kun haluat käyttäjien valitsevan yhden vaihtoehdon.
:::

## Teksti ja sijoittelu {#text-and-positioning}

Radiosäätimet voivat hyödyntää ```setText(String text)```-metodia, johon teksti sijoitetaan radiosäätimen läheisyyteen sisäänrakennetun `Position`-toiminnallisuuden mukaan. Radiosäätimillä on sisäänrakennettu toiminnallisuus asettaa teksti näytettäväksi joko komponentin oikealla tai vasemmalla puolella. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Vaakasuoran tekstin sijoittelu tuetaan `HorizontalAlignment`-enum-luokan avulla. Alla on esitetty kaksi asetusta: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Aktivointi {#activation}

Radiosäätimiä voidaan ohjata kahdella aktivointityypillä: manuaalinen aktivointi ja automaattinen aktivointi. Nämä määrittävät, milloin `RadioButton` vaihtaa tilaansa.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiosäädin on asetettu manuaaliseen aktivointiin, se tarkoittaa, että se ei tarkisteta automaattisesti, kun se saa fokuksen. Manuaalinen aktivointi mahdollistaa käyttäjän navigoida radiosäätimien vaihtoehdoissa näppäimistön tai muiden syöttömenetelmien avulla ilman, että valittua vaihtoehtoa vaihdetaan välittömästi.

Jos radiosäädin on osa ryhmää, toisen radiosäätimen valitseminen ryhmässä poistaa automaattisesti aikaisemmin valitun radiosäätimen valinnan. Manuaalinen aktivointi tarjoaa tarkempaa hallintaa valintaprosessissa, jotta käyttäjän on suoritettava selkeä toiminto valitun vaihtoehdon muuttamiseksi.

### Automaattinen aktivointi {#auto-activation}

Automaattinen aktivointi on `RadioButton`-komponentin oletustila, ja se tarkoittaa, että nappi tarkistetaan aina, kun se saa fokuksen mistä tahansa syystä. Tämä tarkoittaa, että
ei vain klikkaaminen, vaan myös automaattinen fokusoituminen tai välilehden navigointi tarkistaa napin.

:::tip Huomio
Oletusaktivointiarvo on **`MANUAL`** aktivointi.
:::

## Kytkimiä {#switches}

`RadioButton` voidaan myös asettaa näyttämään kytkimenä, mikä tarjoaa vaihtoehtoisen visuaalisen esityksen vaihtoehtojen valitsemiseksi. Normaalisti radiosäätimet ovat ympyrän tai pyöreän muotoisia ja osoittavat yksittäistä valintaa vaihtoehtojen ryhmästä.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton` voidaan muuttaa kytkimeksi, joka muistuttaa kytkin- tai liukusäätöä käyttämällä kahta eri menetelmää:

1. **Tehdasmetodi**: Radiosäädin voidaan luoda käyttämällä seuraavia tehdasmetodeja:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä metodit vastaavat `RadioButton`-konstruktorin toimintaa ja luovat komponentin, jonka kytkin-ominaisuus on jo kytketty päälle.

2. **Setter**: On myös mahdollista muuttaa jo olemassa oleva `RadioButton` kytkimeksi käyttämällä sopivaa setteriä:

```java
myRadioButton.setSwitch(true);
```

Kun `RadioButton` esitetään kytkimenä, se näyttää yleensä pitkältä muodolta, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys tarjoaa käyttäjille intuitiivisemman ja tutumman käyttöliittymän, joka muistuttaa fyysisistä kytkimistä, joita löytyy yleisesti elektronisista laitteista.

Asettaessasi `RadioButton`-komponentin esitettäväksi kytkimenä voidaan parantaa käyttäjäkokemusta selkeällä ja suoraviivaisella tavalla valita vaihtoehtoja. Se voi parantaa visuaalista vetovoimaa ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, jotka vaativat useita valintoja.

:::info
`RadioButton`-komponentin toiminta on sama, kun se renderöidään kytkimeksi, eli vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen näköinen ulkonäkö on visuaalinen muunnos, joka säilyttää `RadioButton`-komponentin toimintallisuuden.
:::

<br/>

## Tyylittely {#styling}

### Laajennukset {#expanses}
On olemassa viisi valintaruutuvaihtoehtoa, joita tuetaan ja jotka mahdollistavat nopean tyylittelyn ilman CSS:ää. Laajennukset tuetaan `Expanse`-enum-luokan avulla. Alla ovat valintaruudelle tuetut laajennukset: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Jotta `RadioButton`-komponentin käyttö tarjoaisi optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät vaihtoehtojen merkinnät**: Tarjoa selkeä ja ytimekäs teksti jokaiselle `RadioButton`-vaihtoehdolle, joka kuvaa tarkasti valintaa. Tekstin tulisi olla helppolukuista ja erottua toisistaan.

2. **Ryhmän radiosäätimet**: Ryhmittele liittyvät radiosäätimet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että vain yksi vaihtoehto voidaan valita tietyssä ryhmässä. Tämä voidaan tehdä tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup)-komponenttia.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiosäätimille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäistä kertaa. Oletusvalinnan tulisi vastata yleisintä tai toivottua valintaa.
