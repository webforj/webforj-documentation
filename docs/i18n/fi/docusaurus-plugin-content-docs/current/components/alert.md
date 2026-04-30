---
title: Alert
sidebar_position: 5
_i18n_hash: b69c428a86cd23667ade00afb734aeec
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstuaalisia palautteita käyttäjille. Se on monikäyttöinen tapa esittää tärkeitä tietoja, varoituksia tai ilmoituksia sovelluksessasi.

Ilmoitukset auttavat kiinnittämään huomiota keskeisiin tietoihin ilman, että ne häiritsevät käyttäjän työskentelyä. Ne ovat täydellisiä järjestelmälähetyksille, lomakkeen vahvistuspalautteelle tai tilapäivityksille, jotka on nähtävä selvästi mutta eivät ole häiritseviä.

<!-- INTRO_END -->

## Ilmoitusten luominen {#creating-alerts}

`Alert`-komponentti voi sisältää rikkaita sisällöitä, kuten tekstiä, painikkeita ja muita komponentteja. Aseta teema erottamaan visuaalisesti esitettävän viestin tyyppi.

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '110px'
/>

## Ilmoitusten sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`-ilmoituksen, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia. 

```java 
Alert alert = new Alert("Heads up! This alert can be dismissed.");
closableAlert.setClosable(true);
```
Ilmoitukset tekevät usein enemmän kuin vain näyttävät viestejä ne voivat laukaista jatkotoimenpiteitä, kun ne suljetaan. Käytä `AlertCloseEvent`-tapahtumaa käsitelläksesi näitä tapauksia ja vastataksesi, kun käyttäjä sulkee `Alert`-ilmoituksen.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Uudelleen käytettävä Alert-komponentti
Ilmoituksen sulkeminen vain piilottaa sen—se ei tuhoa komponenttia, joten voit käyttää sitä myöhemmin uudelleen, jos tarpeen.
:::


## Tyylitys {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink> erottamaan visuaalisesti erilaisia viestityyppejä—kuten onnistuminen, virhe, varoitus tai tieto. Nämä teemat voidaan soveltaa käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '650px'
/>


### Laajuudet {#expanses}

Laajuus määrittää `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai siirtämällä sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '600px'
/>

<TableBuilder name="Alert" />
