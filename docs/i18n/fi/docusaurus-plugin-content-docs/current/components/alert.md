---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: ad90f6abef16b17547ddcb2a612f4050
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalista palautetta käyttäjille. Se on monipuolinen tapa näyttää tärkeää tietoa, varoituksia tai ilmoituksia sovelluksessasi.

Ilmoitukset auttavat kiinnittämään huomiota keskeiseen tietoon häiritsemättä käyttäjän työnkulkua. Ne ovat täydellisiä järjestelmäilmoituksiin, lomakevalidointipalautteeseen tai tilapäivityksiin, jotka on oltava selkeästi näkyvissä, mutta eivät häiritse.

<!-- INTRO_END -->

## Ilmoitusten luominen {#creating-alerts}

`Alert` voi sisältää rikasta sisältöä, kuten tekstiä, painikkeita ja muita komponentteja. Aseta teema, jotta voit visuaalisesti erottaa näytettävän viestin tyypin.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Ilmoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`-ilmoituksen, voit tehdä sen suljettavaksi kutsumalla `setClosable()`-metodia.

```java
Alert alert = new Alert("Huomio! Tämä ilmoitus voidaan sulkea.");
closableAlert.setClosable(true);
```
Ilmoitukset tekevät usein enemmän kuin vain näyttävät viestejä—ne voivat laukaista jatkotoimenpiteitä, kun ne suljetaan. Käytä `AlertCloseEvent`-tapahtumaa käsitelläksesi näitä tapauksia ja vastataksesi, kun käyttäjä sulkee `Alert`-ilmoituksen.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Uudelleenkäytettävä ilmoituskomponentti
Ilmoituksen sulkeminen vain piilottaa sen—se ei tuhoa komponenttia, joten voit käyttää sitä uudelleen myöhemmin, jos tarpeen.
:::


## Tyylittely {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, joiden avulla voidaan visuaalisesti erottaa eri tyyppiset viestit—kuten onnistuminen, virhe, varoitus tai tieto. Näitä teemoja voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Laajuudet {#expanses}

Laajuus määrittelee `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai siirtämällä sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse-enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
