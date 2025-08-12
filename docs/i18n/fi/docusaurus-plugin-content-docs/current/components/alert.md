---
title: Alert
sidebar_position: 5
_i18n_hash: d6b9cd03da84860fd2768d9633f3b38a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalisia palautetehtäviä käyttäjille. Se on monipuolinen tapa näyttää tärkeää tietoa, varoituksia tai ilmoituksia sovelluksessa.

Ilmoitukset auttavat kiinnittämään huomiota keskeisiin tietoihin häiritsemättä käyttäjän työskentelytapaa. Ne ovat täydellisiä järjestelmäviesteihin, lomakkeen validointipalautteeseen tai tilapäivityksiin, jotka on tarpeen näyttää selkeästi, mutta eivät häiritsevästi.

Tässä on esimerkki ilmoituskomponentista:

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Ilmoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`-ilmoituksen, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia.

```java 
Alert alert = new Alert("Päivitys! Tämä ilmoitus voidaan sulkea.");
closableAlert.setClosable(true);
```
Ilmoitukset tekevät usein enemmän kuin vain näyttävät viestejä—ne voivat laukaista jatkotoimia, kun ne suljetaan. Käytä `AlertCloseEvent`-tapahtumaa käsitelläksesi näitä tapauksia ja reagoidaksesi, kun käyttäjä sulkee `Alert`-ilmoituksen.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Uudelleenkäytettävä ilmoituskomponentti
Ilmoituksen sulkeminen vain piilottaa sen—se ei tuhoa komponenttia, joten voit käyttää sitä myöhemmin, jos tarvitset.
:::


## Tyylit {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, joilla voi visuaalisesti erottaa erilaisia viestityyppejä—kuten onnistuminen, virhe, varoitus tai tieto. Nämä teemat voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>


### Laajuudet {#expanses}

Laajuus määrittää `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai siirtää sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse-enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
