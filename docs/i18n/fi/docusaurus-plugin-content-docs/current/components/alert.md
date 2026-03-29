---
title: Alert
sidebar_position: 5
_i18n_hash: 38960017df0c1f8f10c67372e8422bee
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert`-komponentti webforJ:ssä tarjoaa kontekstiin perustuvia palautteita käyttäjille. Se on monipuolinen tapa esittää tärkeitä tietoja, varoituksia tai ilmoituksia sovelluksessasi.

Ilmoitukset auttavat kiinnittämään huomiota avaininformaatioon häiritsemättä käyttäjän työnkulkua. Ne sopivat erinomaisesti järjestelmätiedotteisiin, lomakevalidoinnin palautteeseen tai tilapäivityksiin, jotka on oltava selkeästi näkyvissä mutta ei häiritseviä.

<!-- INTRO_END -->

## Hälytyksien luominen {#creating-alerts}

`Alert` voi sisältää rikkaita sisältöjä, kuten tekstiä, painikkeita ja muita komponentteja. Aseta teema, jotta voit visuaalisesti erottaa näytettävän viestin tyypin.

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Hälytyksien sulkeminen {#dismissing-alerts}

Jos haluat antaa käyttäjille mahdollisuuden sulkea `Alert`:in, voit tehdä siitä suljettavan kutsumalla `setClosable()`-metodia.

```java 
Alert alert = new Alert("Huomio! Tämä hälytys voidaan sulkea.");
closableAlert.setClosable(true);
```
Hälytykset tekevät usein enemmän kuin vain näyttävät viestejä—ne voivat laukaista jatkotoimia, kun ne suljetaan. Käytä `AlertCloseEvent`:iä käsittelemään näitä tapauksia ja reagoimaan, kun käyttäjä sulkee `Alert`:in.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Uudelleenkäytettävä hälytys komponentti
Hälytyksen sulkeminen piilottaa sen vain—se ei tuhoa komponenttia, joten voit käyttää sitä uudelleen myöhemmin tarvittaessa.
:::

## Tyylittely {#styling}

### Teemat {#themes}

`Alert`-komponentti tukee useita <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, joilla voidaan visuaalisesti erottaa eri tyyppisiä viestejä—esimerkiksi onnistuminen, virhe, varoitus tai tieto. Nämä teemat voidaan määrittää käyttämällä `setTheme()`-metodia tai suoraan konstruktorissa.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>

### Laajuudet {#expanses}

Laajuus määrittelee `Alert`-komponentin visuaalisen koon. Voit asettaa sen käyttämällä `setExpanse()`-metodia tai välittämällä sen suoraan konstruktorille. Saatavilla olevat vaihtoehdot tulevat Expanse-enumista: `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
