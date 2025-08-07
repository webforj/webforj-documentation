---
title: Alert
sidebar_position: 5
_i18n_hash: e876e23a7ee171611e8747deef02d93c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalisia palautteita käyttäjille. Se on monipuolinen tapa näyttää tärkeitä tietoja, varoituksia tai ilmoituksia sovelluksessasi.

Alerts auttavat kiinnittämään huomion tärkeisiin tietoihin häiritsemättä käyttäjän työskentelyä. Ne ovat täydellisiä järjestelmäviesteille, lomakevalidointipalautteelle tai tilapäivityksille, jotka on tarpeen näyttää selkeästi mutta ei häiritsevästi.

Tässä on esimerkki ilmoituskomponentista:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height='100px'
/>

## Ilmoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`-komponentin, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia.

```java
Alert alert = new Alert("Heads up! Tämä ilmoitus voidaan sulkea.");
closableAlert.setClosable(true);
```
Ilmoitukset usein tekevät enemmän kuin vain näyttävät viestejä—ne voivat laukaista seuranta toimintoja sulkemisen yhteydessä. Käytä `AlertCloseEvent`-tapahtumaa hallitaksesi näitä tapauksia ja vastataksesi kun käyttäjä sulkee `Alert`-komponentin.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height='100px'
/>

:::tip Uudelleenkäytettävä ilmoituskomponentti
Ilmoituksen sulkeminen vain piilottaa sen—se ei tuhoa komponenttia, joten voit käyttää sitä uudelleen myöhemmin, jos tarpeen.
:::

## Tyylit {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink> erilaisten viestien visuaaliseksi erottamiseksi—kuten onnistuminen, virhe, varoitus tai tieto. Nämä teemat voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height='475px'
/>

### Laajuudet {#expanses}

Laajuus määrittelee `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai siirtää sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height='400px'
/>

<TableBuilder name="Alert" />
