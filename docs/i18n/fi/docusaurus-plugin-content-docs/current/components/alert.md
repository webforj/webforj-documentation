---
title: Alert
sidebar_position: 5
_i18n_hash: 32072a9b5fdae80b41d77ee1d9742ea5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalisia palautteita käyttäjille. Se on monipuolinen tapa näyttää tärkeitä tietoja, varoituksia tai ilmoituksia sovelluksessasi.

Varoitukset auttavat kiinnittämään huomiota tärkeään tietoon häiritsemättä käyttäjän työskentelyä. Ne ovat täydellisiä järjestelmälainteille, lomakevalidointipalautteelle tai tilapäivityksille, jotka on helppo nähdä mutta eivät häiritse.

<!-- INTRO_END -->

## Luodaan varoituksia {#creating-alerts}

`Alert` voi sisältää rikkaita sisältöjä, kuten tekstiä, painikkeita ja muita komponentteja. Aseta teema erottaaksesi visuaalisesti esitettävän viestin tyypin.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Varoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia.

```java 
Alert alert = new Alert("Heads up! This alert can be dismissed.");
closableAlert.setClosable(true);
```
Varoitukset tekevät usein enemmän kuin vain näyttävät viestejä—ne voivat laukaista seuratoimia, kun ne suljetaan. Käytä `AlertCloseEvent`-tapahtumaa hallitaksesi näitä tapauksia ja vastataksesi, kun käyttäjä sulkee `Alert`:in.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Uudelleenkäytettävä Alert-komponentti
Varoituksen sulkeminen piilottaa sen vain—se ei tuhoa komponenttia, joten voit käyttää sitä uudelleen myöhemmin tarvittaessa.
:::

## Tyylittäminen {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, jotka erottavat visuaalisesti eri tyyppiset viestit—kuten onnistuminen, virhe, varoitus tai tieto. Näitä teemoja voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>

### Laajuudet {#expanses}

Laajuus määrittelee `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai siirtämällä sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
