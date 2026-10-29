---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: d46665cad03749940ad45e5be8b3991d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` on pieni, tilapäinen ilmoitus, joka ilmestyy antamaan käyttäjille palautetta toiminnosta tai tapahtumasta. Toastit näyttävät viestejä, kuten onnistumisen vahvistuksia, varoituksia tai virheitä keskeyttämättä nykyistä työnkulkua, ja ne katoavat automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

`Toast.show()` -metodi luo `Toast`-ilmoituksen, lisää sen `Frame`-elementtiin ja näyttää sen yhdellä koodirivillä. Siirrä parametreja `show()`-metodiin konfiguroidaksesi näkyvän `Toast`-ilmoituksen:

```java
Toast.show("Toiminto completed successfully!", Theme.SUCCESS);
```

Jos haluat tarkempaa hallintaa komponentista, voit myös luoda `Toast`-ilmoituksen standardirakentajalla ja käyttää `open()`-metodia sen näyttämiseksi.

```java
Toast toast = new Toast("Toiminto completed successfully!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Oletuskäyttäytyminen
Toisin kuin muut komponentit, `Toast`-ilmoitusta ei tarvitse lisätä erikseen konteinerin, kuten `Frame`, sisään. Kun kutsut `open()`-metodia, `Toast` liitetään automaattisesti ensimmäiseen sovelluksen `Frame`-elementtiin.
:::

Toastit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toiminnoista, kuten lomakkeiden lähettämisestä, datan tallennuksesta tai virheistä.
- **Mukautettavat teemat** erottamaan menestys-, virhe-, varoitus- tai informatiiviset viestit.
- **Joustavat sijoitus** vaihtoehdot ilmoitusten näyttämiseksi eri alueilla näyttöä ilman käyttäjän työnkulun keskeyttämistä.

## Kesto {#duration}

Voit konfiguroida `Toast`-ilmoitukset katoamaan asetetun ajan kuluttua tai pysymään näytöllä, kunnes ne poistetaan, tarpeidesi mukaan. Voit mukauttaa kestoa `setDuration()`-metodilla tai yksinkertaisesti antaa kesto-parametrin rakentajaan tai `show()`-metodiin.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin kuluttua.
:::

```java
Toast toast = new Toast("Esimerkkiviesti");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toastit {#persistent-toasts}

Voit luoda pysyvän `Toast`-ilmoituksen asettamalla negatiivisen keston. Pysyvät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisissä hälytyksissä tai tapauksissa, joissa käyttäjältä vaaditaan vuorovaikutusta tai vahvistusta.

:::caution
Ole varovainen pysyvän `Toast`-ilmoituksen kanssa ja varmista, että tarjoat tavan käyttäjän sulkea ilmoitus. Käytä `close()`-metodia piilottaaksesi `Toast`, kun käyttäjä on vahvistanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toiminto completed successfully!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoittaminen {#placement}

webforJ:n `Toast`-komponentilla voit valita, minne ilmoitus ilmestyy näytölle näyttösi muotoilun ja käytettävyyden vaatimusten mukaan. Oletuksena `Toast`-ilmoitukset ilmestyvät näytön alaosaan keskelle.

Voit asettaa `Toast`-ilmoituksen `placement`-parametrin `setPlacement`-metodilla käyttämällä `Toast.Placement`-enumia seuraavilla arvoilla:

- **BOTTOM**: Asettaa ilmoituksen näytön alaosaan keskelle.
- **BOTTOM_LEFT**: Asettaa ilmoituksen näytön vasempaan alakulmaan.
- **BOTTOM_RIGHT**: Asettaa ilmoituksen näytön oikeaan alakulmaan.
- **CENTER**: Asettaa ilmoituksen näytön keskelle.
- **TOP**: Asettaa ilmoituksen näytön yläosaan keskelle.
- **TOP_LEFT**: Asettaa ilmoituksen näytön vasempaan yläkulmaan.
- **TOP_RIGHT**: Asettaa ilmoituksen näytön oikeaan yläkulmaan.

Nämä vaihtoehdot antavat sinulle mahdollisuuden hallita `Toast`-ilmoituksen sijoittamista sovelluksesi muotoilun ja käytettävyyden tarpeiden mukaan.

```java
Toast toast = new Toast("Esimerkkiviesti");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Mukauttamalla `Toast`-ilmoituksesi sijoittamista voit varmistaa, että käyttäjät saavat tietoja asianmukaisella tavalla minkä tahansa sovelluksen, näyttöasettelun ja kontekstin mukaan.

## Pinoaminen {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samanaikaisesti pinoamalla niitä pystysuorasti niiden sijoituspaikan mukaan. Uudemmat ilmoitukset ilmestyvät lähempänä sijoitusreunaa, työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät jää ilman tärkeitä tietoja, vaikka paljon tapahtuisikin.

## Toiminnot ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletusarvoisesti, webforJ sallii sinun lisätä painikkeita tai muita interaktiivisia elementtejä tehdäksesi niistä hyödyllisempiä kuin pelkät ilmoitukset.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Tämänkaltaisen vuorovaikutuksen lisääminen antaa käyttäjille mahdollisuuden käsitellä tehtäviä ja suorittaa toimintoja siirtymättä pois nykyisestä näytöstään, muuttaen `Toast`-ilmoituksen arvokkaaksi vuorovaikutus- ja sitoutumiskanavaksi.

## Tyylittely {#styling}

Voit tyylittää `Toast`-ilmoituksia teemoilla aivan kuten muutkin webforJ-komponentit, antaen käyttäjille arvokasta kontekstia näytettävän tiedon tyypistä ja luoden johdonmukaisen tyylin koko sovelluksessa. Voit joko asettaa teeman, kun luot `Toast`-ilmoituksen, tai käyttää `setTheme()`-metodia.

```java
Toast toast = new Toast("Esimerkkiviesti", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkiviesti");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täydellisen hallinnan `Toast`-ilmoituksen yleiseen tyylittelyyn.

Lisätäksesi mukautetun teeman `Toast`:iin, voit määrittää mukautettuja CSS-muuttujia, jotka muokkaavat komponentin ulkoasua. Seuraava esimerkki osoittaa, kuinka luoda `Toast` mukautetulla teemalla käyttäen webforJ:ta.

:::info `Toast` kohdistaminen
Koska `Toast` ei sijaitse tietyssä paikassa DOM:ssa, voit kohdistaa sen käyttäen CSS-muuttujia. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikkiin Toast-ilmoituksiin.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
