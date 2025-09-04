---
title: Toast
sidebar_position: 140
_i18n_hash: 2027a7fa9671b2b8eb47a3f173ca6f41
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` -ilmoitus on hienovarainen ja huomaamaton ponnahdusilmoitus, joka on suunniteltu tarjoamaan käyttäjille reaaliaikaista palautetta ja tietoa. Näitä ilmoituksia käytetään tyypillisesti ilmoittamaan käyttäjille toimista, kuten onnistuneista toiminnoista, varoituksista tai virheistä, ilman että ne keskeyttävät työnkulkua. `Toast` -ilmoitukset katoavat yleensä tietyn ajan kuluttua eivätkä vaadi käyttäjältä vastausta.

webforJ:n `Toast` -komponentin avulla voit helposti toteuttaa nämä ilmoitukset parantaaksesi käyttäjäkokemusta tarjoamalla olennaista tietoa tutulla, ei-häiritsevällä ja saumattomalla tavalla.

## Perusteet {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast` -komponentti yhdellä rivillä koodia käyttämällä `Toast.show()` -metodia, joka luo `Toast` -komponentin, lisää sen `Frame`-elementtiin ja näyttää sen. Voit siirtää parametrejä `show` -metodiin konfiguroidaksesi näkyvää `Toast`:ia:

```java
Toast.show("Toimenpide suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän tarkkuutta komponentin hallintaan, voit myös luoda `Toast` -ilmoituksen standardirakentajalla ja käyttää `open()` -metodia sen näyttämiseen.

```java
Toast toast = new Toast("Toimenpide suoritettu onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Oletuskäyttäytyminen
Toisin kuin muut komponentit, `Toast` -ilmoitusta ei tarvitse erikseen lisätä säiliöön, kuten `Frame`:en. Kun kutsut `open()` -metodia, `Toast` liitetään automaattisesti ensimmäiseen sovelluksen `Frame`-elementtiin.
:::

Toastit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toiminnoista, kuten lomakkeiden lähettämisestä, tietojen tallentamisesta tai virheistä.
- **Mukautettavat teemat** erottamaan onnistumat, virheet, varoitukset tai tiedottavat viestit.
- **Joustavat sijoittamis** -vaihtoehdot ilmoitusten näyttämiseksi eri alueilla näytöllä ilman, että ne keskeyttävät käyttäjän työnkulkua.

## Kesto {#duration}

Voit konfiguroida `Toast` -ilmoitukset katoamaan tietyn keston jälkeen tai pysymään näytöllä, kunnes ne hylätään, tarpeidesi mukaan. Voit mukauttaa kestoa käyttämällä `setDuration()` -metodia tai yksinkertaisesti antamalla keston parametrina rakentajalle tai `show()` -metodille.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin jälkeen.
:::

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toastit {#persistent-toasts}

Voit luoda pysyvän `Toast` -ilmoituksen asettamalla negatiivisen keston. Pysyvät `Toast` -ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisissä hälytyksissä tai tapauksissa, joissa käyttäjältä vaaditaan vuorovaikutusta tai tunnustamista.

:::caution
Ole varovainen pysyvien `Toast` -ilmoitusten kanssa ja varmista, että tarjoaa käyttäjälle tavan sulkea ilmoitus. Käytä `close()` -metodia piilottaaksesi `Toast`in, kun käyttäjä on tunnustanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toimenpide suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoittaminen {#placement}

webforJ:n `Toast` -komponentilla voit valita, mihin ilmoitus ilmestyy näytölle sovelluksesi suunnittelun ja käytettävyyden vaatimusten mukaan. Oletuksena `Toast` -ilmoitukset näkyvät näytön alareunassa keskellä.

Voit asettaa `Toast` -ilmoituksen sijoittamisen käyttämällä `setPlacement` -metodia, käyttäen `Toast.Placement` -enumerointia yhdellä seuraavista arvoista:

- **ALHAALLA**: Asettaa ilmoituksen näytön alareunaan keskelle.
- **ALHAALLE_VASEN**: Asettaa ilmoituksen alavasempaan kulmaan.
- **ALHAALLE_OIKEA**: Asettaa ilmoituksen alaoikeaan kulmaan.
- **YLHÄÄLLÄ**: Asettaa ilmoituksen näytön yläreunaan keskelle.
- **YLHÄÄLLÄ_VASEN**: Asettaa ilmoituksen ylävasempaan kulmaan.
- **YLHÄÄLLÄ_OIKEA**: Asettaa ilmoituksen yläoikeaan kulmaan.

Nämä vaihtoehdot antavat sinulle mahdollisuuden hallita `Toast` -ilmoituksen sijoittamista sovelluksesi suunnittelun ja käytettävyyden tarpeiden mukaan.

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Mukauttamalla `Toast` -ilmoitusten sijoittamista voit varmistaa, että käyttäjät saavat tietoa tavalla, joka sopii mille tahansa sovellukselle, näytön asettelulle ja kontekstiin.

## Pinominen {#stacking}

`Toast` -komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinoamalla niitä pystysuunnassa niiden sijoittamisen perusteella. Uudemmat ilmoitukset ilmestyvät lähempänä sijoitusreunaa työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät menetä tärkeitä tietoja, edes silloin, kun paljon tapahtuu.

## Toiminnot ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast` -ilmoitukset eivät vaadi käyttäjältä vuorovaikutusta oletuksena, webforJ antaa sinun lisätä painikkeita tai muita vuorovaikutteisia elementtejä, mikä tekee niistä hyödyllisempiä kuin pelkät ilmoitukset.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Lisäämällä tällaista vuorovaikutteisuutta voit antaa käyttäjille mahdollisuuden hoitaa tehtäviä ja suorittaa toimintoja siirtymättä pois heidän nykyiseltä näytöltään, muuttaen `Toast` -ilmoituksen arvokkaaksi vuorovaikutuksen ja sitoutumisen kanavaksi.

## Tyylitys {#styling}

Voit tyylittää `Toast` -ilmoituksia teemoilla, kuten muihin webforJ-komponentteihin, tarjoten käyttäjille arvokasta kontekstiä siitä, minkä tyyppistä tietoa näytetään, ja luoden johdonmukaisen tyylin koko sovelluksessasi. Voit joko asettaa teeman, kun luot `Toast`:in tai käyttää `setTheme()` -metodia.

```java
Toast toast = new Toast("Esimerkkihälytys", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Sisäänrakennettujen teemojen lisäksi voit luoda omia mukautettuja teemoja `Toast` -ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`:n yleiseen tyylittelyyn.

Lisätäksesi mukautetun teeman `Toast`-ilmoitukseen voit määrittää mukautettuja CSS-muuttujia, jotka muuttavat komponentin ulkonäköä. Seuraava esimerkki havainnollistaa, kuinka luoda `Toast` mukautetulla teemalla webforJ:lla.

:::info `Toast` Kohdentaminen
Koska `Toast` ei sijaitse tietyssä paikassa DOM:issa, voit kohdistaa sen käyttämällä CSS-muuttujia. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikille `Toast` -ilmoituksille.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
