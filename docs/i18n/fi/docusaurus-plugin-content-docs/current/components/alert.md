---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: 1008b6fbbcfab4361660689ceb08135c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalisia palautteita käyttäjille. Se on monipuolinen tapa näyttää tärkeitä tietoja, varoituksia tai ilmoituksia sovelluksessasi.

Ilmoitukset auttavat kiinnittämään huomion tärkeisiin tietoihin häiritsemättä käyttäjän työskentelyä. Ne ovat täydellisiä järjestelmäviesteille, lomakevalidoinnin palautteille tai tilapäivityksille, jotka tarvitsevat selvää näkyvyyttä mutta eivät ole häiritseviä.

<!-- INTRO_END -->

## Ilmoitusten luominen {#creating-alerts}

`Alert` voi sisältää rikkaita sisältöjä, kuten tekstiä, painikkeita ja muita komponentteja. Määritä teema erottamaan visuaalisesti sen, minkä tyyppisestä viestistä on kyse.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Ilmoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`:in, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia.

```java
Alert alert = new Alert("Heads up! Tämä ilmoitus voidaan sulkea.");
alert.setClosable(true);
```
Ilmoitukset tekevät usein muutakin kuin näyttävät viestejä—ne voivat laukaista jatkotoimia, kun ne suljetaan. Käytä `AlertCloseEvent`-tapahtumaa käsitelläksesi näitä tapauksia ja reagoidaksesi, kun käyttäjä sulkee `Alert`:in.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Uudelleen käytettävä Alert-komponentti
Ilmoituksen sulkeminen vain piilottaa sen—se ei tuhoa komponenttia, joten voit käyttää sitä uudelleen myöhemmin tarvittaessa.
:::


## Tyylittely {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, joilla voidaan visuaalisesti erottaa eri tyyppiset viestit—kuten onnistuminen, virhe, varoitus tai tieto. Nämä teemat voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Laajuudet {#expanses}

Laajuus määrittelee `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia. Saatavilla olevat vaihtoehdot tulevat Expanse-enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, ja `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
