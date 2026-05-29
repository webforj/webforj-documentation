---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-komponentti edustaa yhtä vaihtoehtoa, joka voidaan valita tai poistaa valinnasta. Radiopainikkeet ryhmitellään yleensä niin, että yhden valitseminen poistaa automaattisesti muiden valinnat, jolloin käyttäjät voivat tehdä yhden valinnan keskenään yhteensopivasta vaihtoehtosarjasta.

<!-- INTRO_END -->

## Käyttöesimerkit {#usages}

`RadioButton`-komponenttia käytetään parhaiten tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennaltamääritetystä vaihtoehtosarjasta. Tässä on joitain esimerkkejä, milloin käyttää `RadioButton`-komponenttia:

1. **Kyselyt tai Lomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtoluettelosta.

2. **Mieltymysasetukset**: Sovelluksissa, jotka sisältävät mieltymys- tai asetuspaneeleja, käytetään usein radiopainikkeita, joiden avulla käyttäjät voivat valita yhden vaihtoehdon keskenään poisjätettävistä vaihtoehdoista.

3. **Suodatus tai Lajittelu**: `RadioButton`-komponenttia voidaan käyttää sovelluksissa, jotka vaativat käyttäjiä valitsemaan yhden suodatus- tai lajittelu vaihtoehdon, kuten esineiden luettelon lajittelu eri kriteerien mukaan.

:::tip Ryhmittely `RadioButton`-komponenteille
Käytä [`RadioButtonGroup`](/docs/components/radiobuttongroup) hallitaksesi sarjaa radiopainikkeita, kun haluat käyttäjien valitsevan yhden vaihtoehdon.
:::

## Teksti ja sijoittelu {#text-and-positioning}

Radiopainikkeet voivat hyödyntää ```setText(String text)``` -metodia, jonka avulla teksti sijoitetaan radiopainikkeen lähelle sisäänrakennetun `Position`-asetuksen mukaan.
Radiopainikkeilla on sisäänrakennettu toiminnallisuus, joka asettaa näytettävän tekstin joko komponentin oikealle tai vasemmalle puolelle. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Vaakasuoran tekstin sijoittelua tuetaan käyttämällä `HorizontalAlignment`-kokoelma-luokkaa. Alla on esitetty kaksi asetusta: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Aktivointi {#activation}

Radiopainikkeita voidaan ohjata kahdella aktivointityypillä: manuaalinen aktivointi ja automaattinen aktivointi. Nämä määrittävät, milloin `RadioButton` muuttaa tilaansa.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike on asetettu manuaaliseen aktivointiin, se tarkoittaa, että se ei tarkisteta automaattisesti, kun se saa fokuksen.
Manuaalinen aktivointi mahdollistaa käyttäjän navigoida radiopainikevaihtoehtojen välillä näppäimistön tai muiden syöttömenetelmien avulla ilman, että valittu vaihtoehto muuttuu välittömästi.

Jos radiopainike on osa ryhmää, toisen radiopainikkeen valitseminen ryhmässä poistaa automaattisesti aiemmin valitun radiopainikkeen valinnan.
Manuaalinen aktivointi tarjoaa tarkempaa hallintaa valintaprosessille, vaatimalla käyttäjältä eksplisiittistä toimintaa valitun vaihtoehdon muuttamiseksi.


### Automaattinen aktivointi {#auto-activation}

Automaattinen aktivointi on `RadioButton`-komponentin oletustila, ja tarkoittaa, että painike tarkistetaan, kun se saa fokuksen mistä syystä tahansa. Tämä tarkoittaa, että
ei vain napsautus, vaan myös automaattinen keskittyminen tai välilehtinavigointi tarkistaa painikkeen.

:::tip Huomio
Oletusarvoinen aktivointiarvo on **`MANUAL`** aktivointi.
:::


## Kytkimet {#switches}

`RadioButton` voidaan myös asettaa näkymään kytkimenä, joka tarjoaa vaihtoehtoisen visuaalisen esityksen vaihtoehtojen valitsemiseksi. Tavallisesti radiopainikkeet ovat pyöreitä tai kulmikkaita, ja ne osoittavat yksittäistä valintaa vaihtoehtoryhmästä. 

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton` voidaan muuntaa kytkimeksi, joka muistuttaa vaihtokytkintä tai liukusäädintä, käyttäen yhtä kahta menetelmää:

1. **Tehdasmetodi**: Radiopainike voidaan luoda seuraavilla tehdasmetodeilla:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä menetelmät vastaavat `RadioButton`-konstruktorin, ja luovat komponentin, jonka kytkinominaisuus on jo kytketty päälle.

2. **Setter**: On myös mahdollista muuttaa jo olemassa oleva `RadioButton` kytkimeksi käyttämällä soveltuvaa asetinta:

```java
myRadioButton.setSwitch(true);
```


Kun `RadioButton` näytetään kytkimenä, se näkyy tyypillisesti pitkänomaisena muotona, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys antaa käyttäjille selkeämmän ja tutun käyttöliittymän, joka on samanlainen kuin fyysiset kytkimet, joita tavallisesti löytyy elektroniikkalaitteista.

Asettaessasi `RadioButton`-komponenttia kytkimestä voi parantaa käyttäjäkokemusta tarjoamalla selkeän ja suoraviivaisen tavan valita vaihtoehtoja. Se voi parantaa visuaalista houkuttelevuutta ja käytettävyyttä lomakkeissa, asetuspaneeleissa, tai missä tahansa muussa käyttöliittymäelementissä, joka vaatii useita valintoja.

:::info
`RadioButton`-komponentin käyttäytyminen pysyy samana, kun se renderöidään kytkimenä, mikä tarkoittaa, että vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen kaltainen ulkoasu on visuaalinen muunnos, joka säilyttää `RadioButton`-komponentin toiminnallisuuden.
:::

<br/>

## Tyylittely {#styling}

### Laajuudet {#expanses}
Radiopainike-komponentilla on viisi tukemaa laajuutta, jotka mahdollistavat nopean tyylittelyn ilman CSS:ää.
Laajuudet tukevat `Expanse`-enum-luokan käyttö. Alla on esitetty radiopainikekomponenttia varten tuetut laajuudet: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Jotta `RadioButton`-komponentin käyttö takaisi optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeästi merkitse vaihtoehtoja**: Tarjoa selkeää ja tiivistä tekstiä jokaiselle `RadioButton`-vaihtoehdolle, joka kuvaa tarkasti valintaa. Tekstin tulisi olla helppoa ymmärtää ja erottamattomia toisistaan.

2. **Ryhmittele radiopainikkeet**: Ryhmittele liittyvät radiopainikkeet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että vain yksi vaihtoehto voidaan valita tietyssä ryhmässä. Tämä voidaan tehdä tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille käyttäjien ohjaamiseksi, kun he ensimmäistä kertaa kohtaavat vaihtoehdot. Oletusvalinnan tulisi olla linjassa yleisimmän tai halutuimman vaihtoehdon kanssa.
