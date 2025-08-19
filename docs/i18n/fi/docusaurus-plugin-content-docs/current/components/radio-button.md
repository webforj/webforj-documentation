---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: efd1171b68ca07b593064abe0366ded7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-luokka luo objektin, joka voidaan valita tai poistaa valinnasta, ja joka näyttää tilansa käyttäjälle. Sopimuksen mukaan vain yksi radiopainike ryhmässä voi olla valittuna kerrallaan. Radiopainikkeita käytetään yleisesti, kun vaihtoehdot ovat keskenään poissulkevia, jolloin käyttäjä voi valita yhden vaihtoehdon joukosta.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Käytöt {#usages}

`RadioButton`-painiketta käytetään parhaiten tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennalta määritetystä vaihtoehtojoukosta. Tässä on joitakin esimerkkejä siitä, milloin `RadioButton`-painiketta kannattaa käyttää:

1. **Kyselyt tai lomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai lomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen luettelosta.

2. **Asetusvalinnat**: Sovelluksissa, jotka sisältävät asetuksia tai valintapaneeleja, käytetään usein radiopainikkeita, jotta käyttäjät voivat valita yhden vaihtoehdon keskenään poissulkevista valinnoista.

3. **Suodattaminen tai lajittelu**: `RadioButton`-painiketta voidaan käyttää sovelluksissa, joissa käyttäjien on valittava yksi suodatin- tai lajitteluvaihtoehto, kuten lajitteluun eri kriteerien mukaan.

## Teksti ja sijoittelu {#text-and-positioning}

Radiopainikkeet voivat hyödyntää `setText(String text)` -metodia, joka sijoittuu lähelle radiopainiketta sisäänrakennetun `Position`-toiminnallisuuden mukaisesti. Radiopainikkeilla on sisäänrakennettu ominaisuus asettaa näytettävä teksti joko komponentin oikealle tai vasemmalle puolelle. Oletuksena teksti näytetään komponentin oikealla puolella. Vaakasuoran tekstin sijoittamisen tukea tarjotaan `HorizontalAlignment`-enum-luokan avulla. Alla on esitelty kaksi asetusta: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Aktivointi {#activation}

Radiopainikkeita voidaan ohjata kahdella aktivointityypillä: manuaalisella aktivoinnilla ja automaattisella aktivoinnilla. Nämä määrittävät, milloin `RadioButton` muuttaa tilaansa.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike on asetettu manuaaliseen aktivointiin, se tarkoittaa, että se ei tarkisteta automaattisesti, kun se saa fokuksen. Manuaalinen aktivointi sallii käyttäjän navigoida radiopainikevaihtoehtojen välillä näppäimistön tai muiden syöttötapojen avulla ilman, että valittua vaihtoehtoa vaihdetaan heti.

Jos radiopainike kuuluu ryhmään, toisen radiopainikkeen valitseminen ryhmässä poistaa automaattisesti aiemmin valitun radiopainikkeen valinnan. Manuaalinen aktivointi tarjoaa tarkempaa hallintaa valintaprosessissa, vaaditaan käyttäjältä eksplisiittinen toiminto valitun vaihtoehdon vaihtamiseksi.

### Automaattinen aktivointi {#auto-activation}

Automaattinen aktivointi on `RadioButton`-painikkeen oletustila ja tarkoittaa, että painike tarkistetaan aina, kun se saa fokuksen syystä riippumatta. Tämä tarkoittaa, että ei vain klikkaaminen, vaan myös automaattinen fokus tai välilehdellä navigointi tarkistaa painikkeen.

:::tip Huom
Oletusaktivointiarvo on **`MANUAL`** aktivointi.
:::

## Kytkimet {#switches}

`RadioButton` voidaan myös asetaa näyttämään kytkimenä, joka tarjoaa vaihtoehtoisen visuaalisen esityksen vaihtoehtojen valitsemiseksi. Tavallisesti radiopainikkeet ovat pyöreitä tai kulmikkaita ja osoittavat yhden valinnan vaihtoehtojoukosta.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` voidaan muuntaa kytkimeksi, joka muistuttaa vaihdettavaa kytkintä tai liukusäädintä käyttämällä yhtä kahta menetelmää:

1. **Tehdasmetodi**: Radiopainike voidaan luoda seuraavien tehdasmetodien avulla:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä metodit vastaavat `RadioButton`-konstruktorin toimintaa, ja luovat komponentin, jossa kytkinominaisuus on jo kytketty päälle.

2. **Setter**: On myös mahdollista muuttaa jo olemassa oleva `RadioButton` kytkimeksi käyttämällä sopivaa setteria:

```java
myRadioButton.setSwitch(true);
```

Kun `RadioButton` näytetään kytkimenä, se näkyy tyypillisesti pitkänomaisena muotona, jossa on indikaattori, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys tarjoaa käyttäjille intuitiivisemman ja tutumman käyttöliittymän, joka muistuttaa fyysisiä kytkimiä, joita löytyy yleisesti sähköisistä laitteista.

`RadioButton`-painikkeen asettaminen näyttämään kytkimenä voi parantaa käyttäjäkokemusta tarjoamalla selkeä ja suoraviivainen tapa valita vaihtoehtoja. Se voi parantaa visuaalista vetovoimaa ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, joissa tarvitaan useita valintoja.

:::info
`RadioButton`-painikkeen toiminta pysyy samana, kun se renderöidään kytkimenä, mikä tarkoittaa, että vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen kaltainen ulkonäkö on visuaalinen muunnos, joka säilyttää `RadioButton`-toiminnallisuuden.
:::

<br/>

## Tyylit {#styling}

### Laajuudet {#expanses}
Radiopainikkeen komponentille on tuettu viittä ruutulaajuutta, jotka sallivat nopean tyylin ilman CSS:ää. Laajuudet tuetaan käyttämällä `Expanse`-enum-luokkaa. Alla on tuetut laajuudet radiopainikekomponentille: <br/>

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Jotta `RadioButton`-komponentin käyttö tarjoaisi optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät vaihtoehtojen etiketit**: Anna jokaiselle `RadioButton`-vaihtoehdolle selkeä ja ytimekäs teksti, joka kuvaa valintaa tarkasti. Tekstin tulisi olla helposti ymmärrettävää ja erottuvaa toisistaan.

2. **Ryhmittele radiopainikkeet**: Ryhmittele samankaltaiset radiopainikkeet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että tietyn ryhmän sisällä vain yksi vaihtoehto voi olla valittuna. Tämä voidaan toteuttaa tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäisen kerran. Oletusvalinnan tulisi vastata yleisintä tai toivottua valintaa.
