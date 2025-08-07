---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-luokka luo objektin, joka voidaan valita tai poistaa valinnasta, ja joka näyttää tilansa käyttäjälle. Tavan mukaan vain yksi radiopainike ryhmässä voi olla valittuna kerrallaan. Radiopainikkeita käytetään yleisesti, kun vaihtoehdot ovat keskenään poissulkivia, jolloin käyttäjä voi valita yhden vaihtoehdon valikoimasta.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Käytännöt {#usages}

`RadioButton`-komponentti on parhaiten käytössä tilanteissa, joissa käyttäjien tulee tehdä yksi valinta ennalta määritellystä vaihtoehtoja. Tässä on joitakin esimerkkejä siitä, milloin `RadioButton`-komponenttia tulisi käyttää:

1. **Kyselyt tai Lomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjät tarvitsevat valita yhden vastauksen vaihtoehtojen listasta.

2. **Asetusten Valinnat**: Sovelluksissa, joissa on valintoja tai asetuksia, käytetään usein radiopainikkeita antamaan käyttäjille mahdollisuus valita yksi vaihtoehto keskenään poissulkevista valinnoista.

3. **Suodatus tai Järjestäminen**: `RadioButton` voi olla käytössä sovelluksissa, jotka vaativat käyttäjiltä yhden suodatus- tai järjestämisvaihtoehdon valitsemista, kuten erilaisten kriteerien mukaan luettelon lajittelemista.

## Teksti ja sijainti {#text-and-positioning}

Radiopainikkeet voivat käyttää ```setText(String text)```-metodia, jonka avulla teksti sijoitetaan radiopainikkeen lähelle Built-in `Position`-tuen perusteella. Radiopainikkeilla on sisäinen toiminnallisuus asettaa teksti näytettäväksi joko komponentin oikealla tai vasemmalla puolella. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Vaakasuoran tekstin sijaintia tuetaan `HorizontalAlignment`-enum-luokan avulla. Näytetään alla olevat kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Aktivointi {#activation}

Radiopainikkeita voidaan hallita kahdella aktivointityypillä: manuaalinen aktivointi ja automaattinen aktivointi. Nämä määrittävät, milloin `RadioButton` muuttuu tilassaan.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike on asetettu manuaaliseen aktivointiin, se tarkoittaa, että sitä ei automaattisesti tarkisteta, kun se saa fokuksen. Manuaalinen aktivointi antaa käyttäjälle mahdollisuuden navigoida radiopainikevaihtoehtojen välillä näppäimistön tai muiden syöttötapojen avulla ilman, että valittua vaihtoehtoa muutetaan heti.

Jos radiopainike on osa ryhmää, toisen radiopainikkeen valitseminen ryhmässä poistaa automaattisesti valinnan aiemmin valitulta radiopainikkeelta. Manuaalinen aktivointi antaa tarkempaa kontrollia valintaprosessista, vaatimalla käyttäjältä eksplisiittistä toimintaa valitun vaihtoehdon muuttamiseksi.

### Automaattinen aktivointi {#auto-activation}

Automaattinen aktivointi on oletustila `RadioButton`-komponentille, mikä tarkoittaa, että painike tarkistetaan, aina kun se saa fokuksen mistä tahansa syystä. Tämä tarkoittaa, että ei pelkästään napsautus, vaan myös automaattinen fokus tai välilehtinavigointi tarkistaa painikkeen.

:::tip Huomautus
Oletusarvoinen aktivointiarvo on **`MANUAL`** aktivointi.
:::

## Vaihtoehtoasetukset {#switches}

`RadioButton` voidaan myös asettaa näyttämään kytkimenä, joka tarjoaa vaihtoehtoisen visuaalisen esityksen valintojen tekemiseksi. Normaalisti radiopainikkeet ovat pyöreitä tai pyöristettyjä ja osoittavat yhden valinnan joukosta vaihtoehdoista.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` voidaan muuttaa kytkimeksi, joka muistuttaa kytkintä tai liukupainiketta kahdella tavalla:

1. **Tehdasmetodi**: Radiopainike voidaan luoda seuraavien Tehdasmetodien avulla:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä menetelmät jäljittelevät `RadioButton`-konstruktoria, ja luovat komponentin, jolla on kytkinominaisuus jo kytketty päällä.

2. **Setter**: On myös mahdollista muuttaa jo olemassa oleva `RadioButton` kytkimeksi käyttämällä asianmukaista setteriä:

```java
myRadioButton.setSwitch(true);
```

Kun `RadioButton` näytetään kytkimenä, se näyttää tyypillisesti soikealta muodolta, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys antaa käyttäjille intuitiivisemman ja tutun käyttöliittymän, joka on verrattavissa fyysisiin kytkimiin, joita tavallisesti löytyy elektroniikkalaitteista.

`RadioButton`-komponentin näyttäminen kytkimenä voi parantaa käyttäjäkokemusta tarjoamalla selkeän ja yksinkertaisen tavan valita vaihtoehtoja. Se voi lisätä visuaalista houkuttelevuutta ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, jotka vaativat useita valintoja.

:::info
`RadioButton`-komponentin käyttäytyminen pysyy samana, kun sitä esitetään kytkimenä, eli vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen kaltainen ulkonäkö on visuaalinen muunnos, joka säilyttää `RadioButton`-toiminnallisuuden.
:::

<br/>

## Tyylit {#styling}

### Laajuudet {#expanses}
On olemassa viisi tarkistusruudun laajuutta, joita tuetaan ja jotka mahdollistavat nopean tyylittämisen ilman CSS:ää. Laajuuksia tuetaan `Expanse`-enum-luokan avulla. Alla ovat tukemamme laajuudet tarkistusruudukkomponentille: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi RadioButton-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät Vaihtoehtojen Nimitykset**: Anna kullekin `RadioButton`-vaihtoehdolle selkeä ja ytimekäs teksti, joka kuvaa valintaa tarkasti. Tekstin tulisi olla helppoa ymmärtää ja erottua toisistaan.

2. **Ryhmittele Radiopainikkeet**: Ryhmittele samankaltaiset radiopainikkeet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että vain yksi vaihtoehto voidaan valita tietyssä ryhmässä. Tämä voidaan toteuttaa tehokkaasti käyttäen [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa Oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille ohjataksesi käyttäjiä, kun he ensimmäisen kerran kohtaavat vaihtoehdot. Oletusvalinnan tulisi vastata yleisintä tai toivottavinta valintaa.
