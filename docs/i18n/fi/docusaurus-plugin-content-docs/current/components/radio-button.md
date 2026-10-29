---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`-komponentti edustaa yhtä vaihtoehtoa, joka voidaan valita tai poistaa käytöstä. Radiopainikkeet ryhmitellään yleensä yhteen siten, että yhden valitseminen poistaa automaattisesti muiden valinnan, jolloin käyttäjä voi tehdä ainoan valinnan keskenään poissulkevista vaihtoehdoista.

<!-- INTRO_END -->

## Käytöt {#usages}

`RadioButton`-komponenttia käytetään parhaiten tilanteissa, joissa käyttäjien on tehtävä yksi valinta ennalta määritellystä vaihtoehtojen joukosta. Tässä on joitakin esimerkkejä siitä, milloin käyttää `RadioButton`-komponenttia:

1. **Kyselyt tai kyselylomakkeet**: Radiopainikkeita käytetään yleisesti kyselyissä tai kyselylomakkeissa, joissa käyttäjien on valittava yksi vastaus vaihtoehtojen listasta.

2. **Asetusvalinnat**: Sovelluksissa, jotka sisältävät asetuksia tai preferenssejä, käytetään usein radiopainikkeita, jotta käyttäjät voivat valita yhden vaihtoehdon keskenään poissulkevista valinnoista.

3. **Suodatus tai lajittelu**: `RadioButton` voi olla mukana sovelluksissa, jotka vaativat käyttäjiä valitsemaan yhden suodatus- tai lajitteluvaihtoehdon, kuten erilaisten kriteerien mukaan lajittelun.

:::tip `RadioButton`-komponenttien ryhmittely
Käytä [`RadioButtonGroup`](/docs/components/radiobuttongroup) ryhmittääksesi joukko radiopainikkeita, kun haluat käyttäjien valitsevan yhden vaihtoehdon.
:::

## Teksti ja asettelu {#text-and-positioning}

Käytä `setText(String text)` asettaaksesi radiopainikkeen labelin. Oletuksena teksti näkyy komponentin oikealla puolella. 
Muuta sen sijaintia käyttämällä `setTextPosition(Position.LEFT)` tai `setTextPosition(Position.RIGHT)` yhdessä `HasTextPosition.Position` -enumeroinnin kanssa.

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Aktivointi {#activation}

Radiopainikkeiden aktivointia voidaan hallita kahdella aktivointityypillä: manuaalisella aktivoinnilla ja automaattisella aktivoinnilla. Nämä määrittävät, milloin `RadioButton` muuttaa tilaansa. Oletus on `Activation.MANUAL` `RadioButton.Activation` -enumeroinnista.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuaalinen aktivointi {#manual-activation}

Kun radiopainike on asetettu manuaaliseen aktivointiin, se ei tarkoita, että se automaattisesti tarkastetaan saadessaan fokuksen. Manuaalinen aktivointi mahdollistaa käyttäjän navigoida radiopainikkeiden vaihtoehtojen välillä näppäimistön tai muiden syöttötapojen avulla muuttamatta heti valittua vaihtoehtoa.

Jos radiopainike on osa ryhmää, toisen radiopainikkeen valitseminen ryhmän sisällä poistaa automaattisesti aikaisemmin valitun radiopainikkeen valinnan. Manuaalinen aktivointi antaa tarkempaa hallintaa valintaprosessiin, vaatiessaan käyttäjältä eksplisiittistä toimintaa valitun vaihtoehdon muuttamiseksi.

### Automaattinen aktivointi {#auto-activation}

Ota käyttöön automaattinen aktivointi käytämällä `setActivation(Activation.AUTO)`. Tässä tilassa radiopainike tarkastetaan aina, kun se saa fokuksen, mukaan lukien napsautuksen, automaattisen fokuksen tai tabulaattorin navigoinnin kautta.

## Kytkimet {#switches}

`RadioButton` voidaan myös asettaa näyttämään kytkimenä, mikä tarjoaa vaihtoehtoisen visuaalisen esityksen vaihtoehtojen valitsemiseksi. Normaalisti radiopainikkeet ovat pyöreitä tai kaarevia ja ilmoittavat yhdestä valinnasta vaihtoehtoryhmästä.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton` voidaan muuttaa kytkimeksi, joka muistuttaa kytkintä tai liukusäädintä, käyttämällä yhtä kahdesta menetelmästä:

1. **Tehdasmenetelmä**: Radiopainike voidaan luoda seuraavilla tehdasmenetelmillä:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Nämä menetelmät vastaavat `RadioButton`-konstruktoria ja luovat komponentin, jolla on kytkinominaisuus jo päällä.

2. **Setter**: On myös mahdollista muuttaa olemassa oleva `RadioButton` kytkimeksi käyttämällä asianmukaista setteria:

```java
myRadioButton.setSwitch(true);
```

Kun `RadioButton` näytetään kytkimenä, se näkyy tyypillisesti pitkänomaisena muotona, jossa on merkkivalo, jota voidaan kytkeä päälle tai pois. Tämä visuaalinen esitys tarjoaa käyttäjille intuitiivisemman ja tutumman käyttöliittymän, joka muistuttaa sähköisissä laitteissa yleisesti esiintyviä fyysisiä kytkimiä.

`RadioButton`-näytön asettaminen kytkimeksi voi parantaa käyttäjäkokemusta tarjoamalla selkeän ja yksinkertaisen tavan valita vaihtoehtoja. Se voi parantaa visuaalista vetovoimaa ja käytettävyyttä lomakkeissa, asetuspaneeleissa tai muissa käyttöliittymäelementeissä, joissa tarvitaan useita valintoja.

:::info
`RadioButton`-käyttäytyminen pysyy samana, vaikka se renderöidään kytkimenä, mikä tarkoittaa, että vain yksi vaihtoehto voidaan valita kerrallaan ryhmässä. Kytkimen kaltainen ulkoasu on visuaalinen muunnos, joka säilyttää `RadioButton`-toiminnallisuuden.
:::

<br/>

## Tyylittely {#styling}

### Laajuudet {#expanses}

Käytä `setExpanse()` yhdessä `Expanse`-enumeroinnin kanssa säätääksesi `RadioButton`-koon ilman CSS:n kirjoittamista. Saatavilla olevat arvot ovat `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE` ja `NONE`.

<TableBuilder name="RadioButton" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen käytettäessä `RadioButton`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät vaihtoehto-otsikot**: Anna jokaiselle `RadioButton`-vaihtoehdolle selkeä ja tiivis teksti, joka kuvaa valintaa tarkasti. Tekstin tulisi olla helposti ymmärrettävää ja erottuvaa toisistaan.

2. **Ryhmittele radiopainikkeet**: Ryhmittele liittyvät radiopainikkeet yhteen osoittaaksesi niiden yhteyden. Tämä auttaa käyttäjiä ymmärtämään, että vain yksi vaihtoehto voidaan valita tietyssä ryhmässä. Tämä voidaan tehdä tehokkaasti käyttämällä [`RadioButtonGroup`](/docs/components/radiobuttongroup) -komponenttia.

3. **Tarjoa oletusvalinta**: Jos mahdollista, harkitse oletusvalinnan tarjoamista radiopainikkeille ohjataksesi käyttäjiä, kun he kohtaavat vaihtoehdot ensimmäisen kerran. Oletusvalinnan tulisi olla linjassa yleisimmän tai suosituimman valinnan kanssa.
