---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 19e51a9c57a6524781ac008abcebc790
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-komponentti edustaa yhtä valintaa, joka voidaan valita tai mitätöidä. Radiopainikkeet ryhmitellään yleensä yhteen siten, että yhden valitseminen mitätöi automaattisesti muut, jolloin käyttäjät voivat tehdä yhden valinnan keskenään poissulkevista vaihtoehdoista.

<!-- INTRO_END -->

## Käytöt {#usages}

`RadioButton`ia käytetään parhaiten tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennalta määritellyistä vaihtoehdoista. Tässä on joitakin esimerkkejä siitä, milloin `RadioButton`ia tulisi käyttää:

1. **Kyselyt tai lomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen luettelosta.

2. **Asetusvaihtoehdot**: Sovellukset, joissa on mieltymys- tai asetuspaneeleja, käyttävät usein radiopainikkeita sallimaan käyttäjien valita yksi vaihtoehto keskenään poissulkevista valinnoista.

3. **Suodatus tai lajittelu**: `RadioButton`ia voidaan käyttää sovelluksissa, joissa käyttäjien on valittava yksi suodatus- tai lajitteluoptio, kuten lajittelemalla esineita eri kriteerien mukaan.

:::tip Ryhmittely `RadioButton` komponentteja
Käytä [`RadioButtonGroup`](/docs/components/radiobuttongroup) hallitsemaan joukkoa radiopainikkeita, kun haluat käyttäjien valitsevan yhden vaihtoehdon.
:::

## Teksti ja paikannus {#text-and-positioning}

Radiopainikkeet voivat hyödyntää ```setText(String text)```-metodia, joka sijoitetaan lähelle radiopainiketta sen mukaan, mikä on sisäänrakennettu `Position`.
Radiopainikkeissa on sisäänrakennettu toiminto asettaa teksti, joka näytetään joko komponentin oikealla tai vasemmalla puolella. Oletusarvoisesti teksti näytetään komponentin oikealla puolella. Vaakasuoran tekstin paikannusta tuetaan `HorizontalAlignment`-enum-luokan avulla. Alla on esitetty kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Aktivointi {#activation}

Radiopainikkeita voidaan ohjata kahden tyyppisen aktivoinnin avulla: manuaalinen aktivointi ja automaattinen aktivointi. Nämä määrittävät, milloin `RadioButton` vaihtaa tilaansa.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike asetetaan manuaaliseen aktivointiin, se tarkoittaa, että se ei tarkisteta automaattisesti, kun se saa fokuksen.
Manuaalinen aktivointi antaa käyttäjän navigoida radiopainikevaihtoehtojen välillä näppäimistön tai muiden syöttötapojen avulla ilman, että valittua vaihtoehtoa muutetaan heti.

Jos radiopainike on osa ryhmää, toisen radiopainikkeen valitseminen ryhmässä poistaa automaattisesti valitun radiopainikkeen valinnan.
Manuaalinen aktivointi tarjoaa hienojakoisempaa hallintaa valintaprosessissa, jolloin käyttäjän on tehtävä eksplisiittinen toiminta muuttaakseen valittua vaihtoehtoa.


### Automattinen aktivointi {#auto-activation}

Automaattinen aktivointi on `RadioButton`in oletustila, ja se tarkoittaa, että painike tarkistetaan aina, kun se saa fokuksen mistä tahansa syystä. Tämä tarkoittaa, että
ei vain napsautus, vaan myös automaattinen fokus tai tab-navigointi tarkistaa painikkeen.

:::tip Huomautus
Oletusarvoinen aktivointiarvo on **`MANUAL`** aktivointi.
:::


## Kytkimet {#switches}

`RadioButton` voidaan myös asettaa näytettäväksi kytkimenä, joka tarjoaa vaihtoehtoisen visuaalisen esityksen valintojen valitsemiseksi. Yleensä radiopainikkeet ovat pyöreitä tai pyöristettyjä, ja ne osoittavat yksittäistä valintaa vaihtoehtojen ryhmästä.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` voidaan muuttaa kytkimeksi, joka muistuttaa kytkentäkytkintä tai liukusäädintä, käyttäen yhtä kahta menetelmää:

1. **Tehdasmetodi**: Radiopainike voidaan luoda seuraavilla tehdasmetodeilla:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä menetelmät vastaavat `RadioButton`-konstruktoria ja luovat komponentin, jossa kytkinominaisuus on jo kytketty päälle.

2. **Asettaja**: On myös mahdollista muuttaa jo olemassa olevaa `RadioButton`-painiketta kytkimeksi käyttämällä sopivaa asetinta:

```java
myRadioButton.setSwitch(true);
```


Kun `RadioButton` näytetään kytkimenä, se näkyy tyypillisesti pitkänomaisena muotona, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys antaa käyttäjille intuitiivisemman ja tutumman käyttöliittymän, joka muistuttaa fyysisiä kytkimiä, joita tavallisesti löytyy elektroniikkalaitteista.

`RadioButton`-painikkeen asettaminen kytkimeksi voi parantaa käyttäjäkokemusta tarjoamalla selkeän ja suoraviivaisen tavan valita vaihtoehtoja. Se voi parantaa visuaalista viehätystä ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, jotka vaativat useita valintoja.

:::info
`RadioButton`-käyttäytyminen pysyy samana, kun se renderöidään kytkimeksi, mikä tarkoittaa, että vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen kaltainen ulkonäkö on visuaalinen muunnos, joka säilyttää `RadioButton`in toiminnallisuuden.
:::

<br/>

## Tyylit {#styling}

### Laajuudet {#expanses}
On olemassa viisi tarkistusruudukon laajuutta, joita tuetaan, jotka mahdollistavat nopean tyylittelyn ilman CSS:ää.
Laajuudet tuetaan `Expanse`-enum-luokan avulla. Alla on esitetty tarkistusruudukon komponentille tuetut laajuudet: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Jotta varmistettaisiin optimaalinen käyttäjäkokemus käytettäessä RadioButton-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeästi merkitty vaihtoehdot**: Tarjoa selkeä ja ytimekäs teksti jokaiselle `RadioButton`-vaihtoehdolle, jotta valinta kuvastaa tarkasti vaihtoehtoa. Tekstin tulisi olla helppoa ymmärtää ja erottua toisistaan.

2. **Ryhmittele radiopainikkeet**: Ryhmittele samankaltaiset radiopainikkeet yhteen osoittamaan niiden yhteys. Tämä auttaa käyttäjiä ymmärtämään, että tietyssä ryhmässä voidaan valita vain yksi vaihtoehto. Tämä voidaan tehdä tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa oletusvalinta**: Jos se on mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille, jotta käyttäjät saavat ohjeet, kun he kohtaavat vaihtoehdot ensimmäistä kertaa. Oletusvalinnan tulisi vastata yleisintä tai toivottua vaihtoehtoa.
