---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-komponentti on objekti, joka voidaan valita tai poistaa valinnasta, ja joka näyttää tilansa käyttäjälle. Radiopainikkeita käytetään yleisesti, kun tarjolla on keskenään poissulkevia vaihtoehtoja, jolloin käyttäjä voi valita yhden vaihtoehdon valikoimasta.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip Ryhmittely `RadioButton`-komponenteille
Käytä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia hallitaksesi sarjaa radiopainikkeita, kun haluat käyttäjien valitsevan yhden vaihtoehdon.
:::

## Käytännöt {#usages}

`RadioButton` on parasta käyttää tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennalta määritetystä vaihtoehtojen joukosta. Tässä on joitakin esimerkkejä siitä, milloin käyttää `RadioButton`-komponenttia:

1. **Kyselyt tai kyselylomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai kyselylomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen luettelosta.

2. **Asetusasetukset**: Sovelluksissa, jotka sisältävät asetusten tai preferenssien paneeleja, käytetään usein radiopainikkeita, jotta käyttäjät voivat valita yhden vaihtoehdon keskenään poissulkevista vaihtoehdoista.

3. **Suodatus tai lajittelu**: `RadioButton`-komponenttia voidaan käyttää sovelluksissa, joissa käyttäjien on valittava yksi suodatus- tai lajittelu vaihtoehto, esimerkiksi lajiteltaessa tavaraluetteloa eri kriteerien mukaan.

## Teksti ja sijainti {#text-and-positioning}

Radiopainikkeet voivat hyödyntää ```setText(String text)``` -metodia, joka sijoittaa tekstin radiopainikkeen lähelle sisäänrakennetun `Position`-tyypin mukaan. Radiopainikkeilla on sisäänrakennettu toiminnallisuus asettaa teksti näytettäväksi joko komponentin oikealla tai vasemmalla puolella. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Vaakasuoralle tekstille tuetaan `HorizontalAlignment`-enumerointiluokkaa. Alla on esitetty kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Aktivointi {#activation}

Radiopainikkeita voidaan hallita kahdella erilaisella aktivoinnin tyypillä: manuaalinen aktivointi ja automaattinen aktivointi. Nämä määrittävät, milloin `RadioButton` muuttaa tilaansa.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike on asetettu manuaaliseen aktivointiin, se tarkoittaa, että sitä ei automaattisesti valita, kun se saa fokuksen. Manuaalinen aktivointi mahdollistaa käyttäjän siirtyä radiopainikevaihtoehtojen läpi näppäimistön tai muiden syöttömenetelmien avulla ilman, että valittu vaihtoehto muuttuu heti.

Jos radiopainike kuuluu ryhmään, eri radiopainikkeen valitseminen ryhmässä poistaa automaattisesti valinnan aikaisemmin valitusta radiopainikkeesta. Manuaalinen aktivointi antaa tarkempaa hallintaa valintaprosessista, ja se edellyttää käyttäjältä eksplisiittistä toimintoa valitun vaihtoehdon muuttamiseksi.


### Automaattinen aktivointi {#auto-activation}

Automaattinen aktivointi on `RadioButton`-komponentin oletustila ja tarkoittaa, että painike valitaan aina, kun se saa fokuksen mistä syystä tahansa. Tämä tarkoittaa, että paitsi klikkaaminen, myös automaattinen fokus tai tabulaation navigointi tarkistaa painikkeen.

:::tip Huomautus
Oletusarvoinen aktivointiarvo on **`MANUAL`** aktivointi.
:::


## Kytkimet {#switches}

`RadioButton` voidaan myös asettaa näytettäväksi kytkimenä, mikä tarjoaa vaihtoehtoisen visuaalisen esityksen vaihtoehtojen valinnalle. Yleensä radiopainikkeet ovat pyöreitä tai kulmikkaita ja osoittavat yhden valinnan vaihtoehtojen joukosta. 

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` voidaan muuntaa kytkimeksi, joka muistuttaa kytkin- tai liukusäädintä, käyttämällä kahta menetelmää:

1. **Tehdasmenetelmä**: Radiopainike voidaan luoda seuraavien tehdasmenetelmien avulla:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä menetelmät vastaavat `RadioButton`-konstruktoria ja luovat komponentin, jonka kytkinominaisuus on jo otettu käyttöön.

2. **Setter**: On myös mahdollista muuttaa jo olemassa oleva `RadioButton` kytkimeksi käyttämällä sopivaa setter-metodia:

```java
myRadioButton.setSwitch(true);
```

Kun `RadioButton` näytetään kytkimenä, se näkyy yleensä pitkänomaisena muotona, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys antaa käyttäjille intuitiivisemman ja tutumman käyttöliittymän, joka on samanlainen kuin sähköisten laitteiden fyysisissä kytkimissä usein esiintyvät kytkimet.

`RadioButton` muuttaminen kytkimeksi voi parantaa käyttäjäkokemusta tarjoamalla selkeän ja suoraviivaisen tavan valita vaihtoehtoja. Se voi parantaa visuaalista vetovoimaa ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, jotka vaativat useita valintoja.

:::info
`RadioButton`-komponentin käyttäytyminen pysyy samana, kun se renderöidään kytkimeksi, mikä tarkoittaa, että vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkinmainen ulkonäkö on visuaalinen muunnos, joka säilyttää `RadioButton`-komponentin toiminnallisuuden.
:::

<br/>

## Tyylitys {#styling}

### Laajuudet {#expanses}
On olemassa viisi tukemaa checkbox-laajuutta, jotka mahdollistavat nopean tyylittelyn ilman CSS:ää.
Laajuudet tuetaan `Expanse`-enumerointiluokan avulla. Alla on lueteltu checkbox-komponentille tuetut laajuudet: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käyttäessäsi RadioButton-komponenttia, harkitse seuraavia parhaimpia käytäntöjä:

1. **Selkeästi merkitse vaihtoehdot**: Tarjoa selkeää ja ytimekästä tekstiä jokaiselle `RadioButton`-vaihtoehdolle, joka kuvaa valintaa tarkasti. Tekstin tulisi olla helppoa ymmärtää ja erottua toisistaan.

2. **Ryhmittele radiopainikkeet**: Ryhmittele liittyvät radiopainikkeet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että vain yksi vaihtoehto voidaan valita tietyssä ryhmässä. Tämä voidaan tehdä tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille auttaaksesi käyttäjiä, kun he ensin kohtaavat vaihtoehtoja. Oletusvalinnan tulisi olla linjassa yleisimmän tai toivotun valinnan kanssa.
