---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: 07365e349ec9393e79a13969504861bd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

A `Toast` on pieni, väliaikainen ilmoitus, joka ilmestyy antamaan käyttäjille palautetta toiminnasta tai tapahtumasta. Toastit näyttävät viestejä, kuten onnistumisvahvistuksia, varoituksia tai virheitä, keskeyttämättä nykyistä työskentelyä, ja ne häipyvät automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

## Perusteet {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast`-komponentti yhdellä koodirivillä `Toast.show()`-metodin avulla, joka luo `Toast`-komponentin, lisää sen `Frame`:en ja näyttää sen. Voit välittää parametreja `show`-metodille näyttäessäsi `Toast`-ilmoitusta:

```java
Toast.show("Toimenpide suoritettiin onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän tarkkuutta komponentin hallintaan, voit myös luoda `Toast`in standardin rakentajan avulla ja käyttää `open()`-metodia sen näyttämiseen.

```java
Toast toast = new Toast("Toimenpide suoritettiin onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
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
Toisin kuin muut komponentit, `Toast` ei tarvitse olla erikseen lisättynä säiliöön, kuten `Frame`:en. Kun kutsut `open()`-metodia, `Toast` liitetään automaattisesti ensimmäiseen sovellus `Frame`:en.
:::

Toastit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toimille, kuten lomakkeiden lähettämisille, tietojen tallennuksille tai virheille.
- **Mukautettavat teemat** erottamaan onnistumiset, virheet, varoitukset tai informatiiviset viestit.
- **Joustavat sijoitus**vaihtoehdot ilmoitusten näyttämiseen eri alueilla näytöllä ilman, että käyttäjän työskentely keskeytyy.

## Kesto {#duration}

Voit konfiguroida `Toast`-ilmoituksia häipymään asetetun ajan kuluttua tai pysymään ruudulla, kunnes ne suljetaan, tarpeidesi mukaan. Voit mukauttaa kestot `setDuration()`-metodilla tai yksinkertaisesti antaa kestoparametrin rakentajalle tai `show()`-metodille.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin kuluttua.
:::

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toastit {#persistent-toasts}

Voit luoda pysyvän `Toast`in asettamalla negatiivisen keston. Pysyvät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisissä hälytyksissä tai tapauksissa, joissa käyttäjältä vaaditaan vuorovaikutusta tai tunnustamista.

:::caution
Ole varovainen pysyvien `Toast`-ilmoitusten kanssa, ja varmista, että käyttäjälle on tarjottu tapa sulkea ilmoitus. Käytä `close()`-metodia piilottaaksesi `Toast`in, kun käyttäjä on tunnustanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toimenpide suoritettiin onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoitus {#placement}

webforJ:n `Toast`-komponentin avulla voit valita, missä ilmoitus näkyy näytöllä, sopiaksesi sovelluksesi suunnittelu- ja käytettävyyden vaatimuksiin. Oletuksena `Toast`-ilmoitukset näkyvät näytön alaosassa keskellä.

Voit asettaa `Toast`-ilmoituksen `placement`-arvon `setPlacement`-metodilla käyttäen `Toast.Placement` enumia seuraavilla arvoilla:

- **BOTTOM**: Asettaa ilmoituksen alaosaan keskelle näyttöä.
- **BOTTOM_LEFT**: Asettaa ilmoituksen ala-vasempaan kulmaan.
- **BOTTOM_RIGHT**: Asettaa ilmoituksen ala-oikeaan kulmaan.
- **TOP**: Asettaa ilmoituksen yläosaan keskelle näyttöä.
- **TOP_LEFT**: Asettaa ilmoituksen ylä-vasempaan kulmaan.
- **TOP_RIGHT**: Asettaa ilmoituksen ylä-oikeaan kulmaan.

Nämä vaihtoehdot antavat sinulle hallintaa `Toast`-ilmoituksen sijoittelusta sovelluksesi suunnittelu- ja käytettävyyden tarpeiden mukaan.

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Mukauttamalla `Toast`-ilmoitustesi sijoittelua voit varmistaa, että käyttäjät saavat tietoa sopivalla tavalla kussakin sovelluksessa, näytön asettelussa ja kontekstissa.

## Pinottaminen {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinoten niitä pystysuoraan sijoittelunsa mukaan. Uudemmat ilmoitukset näkyvät lähempänä sijoittelun reunaa, työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät jää ilman tärkeitä tietoja, vaikka paljon tapahtuu.

## Toiminnot ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletusarvoisesti, webforJ sallii lisätä painikkeita tai muita interaktiivisia elementtejä, jotta ne olisivat käyttökelpoisempia kuin pelkät ilmoitukset.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Lisäämällä tällaista vuorovaikutusta voit antaa käyttäjille mahdollisuuden hoitaa tehtäviä ja suorittaa toimintoja siirtymättä pois nykyiseltä näytöltään, muuttaen `Toast`-ilmoituksesta arvokkaan vuorovaikutus- ja sitoutumiskanavan.

## Tyylittely {#styling}

Voit tyylittää `Toast`-ilmoituksia teemoilla kuten muutkin webforJ-komponentit, tarjoten käyttäjille arvokasta kontekstia esitettävän tiedon tyypistä ja luoden johdonmukaisen tyylin koko sovellukseen. Voit joko asettaa teeman luodessasi `Toast`ia tai käyttää `setTheme()`-metodia.

```java
Toast toast = new Toast("Esimerkkihälytys", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`:n kokonestyylin yli.

Voit lisätä mukautetun teeman `Toast`:lle määrittämällä mukautettuja CSS-muuttujia, jotka muokkaavat komponentin ulkoasua. Seuraava esimerkki havainnollistaa, kuinka luoda `Toast` mukautetulla teemalla käyttäen webforJ:ta.

:::info `Toast` Kohdistaminen
Koska `Toast` ei sijaitse tietyssä paikassa DOM:ssa, voit kohdistaa sen CSS-muuttujilla. Nämä muuttujat helpottavat yhdenmukaisten mukautettujen tyylien soveltamista kaikkiin `Toast`-ilmoituksiin.
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
