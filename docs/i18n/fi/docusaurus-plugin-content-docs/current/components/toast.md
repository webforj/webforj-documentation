---
title: Toast
sidebar_position: 140
_i18n_hash: c98ff64ae02ebe46d84c803492685d05
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` on pieni, tilapäinen ilmoitus, joka ilmestyy antaakseen käyttäjille palautetta toiminnasta tai tapahtumasta. Toastit esittävät viestejä, kuten onnistumisen vahvistuksia, varoituksia tai virheitä keskeyttämättä nykyistä työnkulkuasi, ja ne katoavat automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

## Perusteet {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast`-komponentti yhdellä koodirivillä `Toast.show()`-menetelmällä, joka luo `Toast`-komponentin, lisää sen `Frame`-elementtiin ja näyttää sen. Voit välittää parametreja `show`-menetelmään konfiguroidaksesi näytettävän `Toast`in:

```java
Toast.show("Toiminto suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän hienosäätöä komponentille, voit myös luoda `Toast`-komponentin tavallisella konstruktorilla ja käyttää `open()`-menetelmää sen näyttämiseen.

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

:::info Oletuskäytös
Toisin kuin muut komponentit, `Toast`-komponenttia ei tarvitse erikseen lisätä astiaan, kuten `Frame`:een. Kun kutsut `open()`-metodia, `Toast` liitetään automaattisesti ensimmäiseen sovellus-`Frame`:en.
:::

Toastit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toiminnoista, kuten lomakeaineiston lähettämisestä, tietojen tallentamisesta tai virheistä.
- **Mukautettavat teemat** onnistumisen, virheen, varoituksen tai tiedottavan viestin erottamiseksi.
- **Joustavat sijoitusvaihtoehdot** ilmoitusten näyttämiseksi eri paikoissa näytöllä keskeyttämättä käyttäjän työnkulkua.

## Kesto {#duration}

Voit konfiguroida `Toast`-ilmoitukset katoamaan asetetun ajan jälkeen tai pysymään näytöllä kunnes ne suljetaan, tarpeidesi mukaan. Voit mukauttaa keston `setDuration()`-menetelmällä tai yksinkertaisesti tarjota keston parametrina konstruktorille tai `show()`-menetelmälle.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin jälkeen.
:::

```java
Toast toast = new Toast("Esimerkki-ilmoitus");
toast.setDuration(10000);
toast.open();
```

### Kestävä toast {#persistent-toasts}

Voit luoda kestävän `Toast`-komponentin asettamalla negatiivisen keston. Kestävät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisten hälytysten tai tapausten yhteydessä, joissa käyttäjältä tarvitaan jonkinlaista vuorovaikutusta tai vahvistusta.

:::caution
Ole varovainen kestävien `Toast`-ilmoitusten kanssa ja varmista, että tarjoat käyttäjälle tavan sulkea ilmoitus. Käytä `close()`-menetelmää, kun käyttäjä on vahvistanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoitus {#placement}

webforJ:n `Toast`-komponentilla voit valita, mihin ilmoitus ilmestyy näytöllä sovelluksesi suunnittelu- ja käyttökelpoisuusvaatimusten mukaan. Oletuksena `Toast`-ilmoitukset ilmestyvät näytön alaosaan keskelle.

Voit määrittää `Toast`-ilmoituksen sijainnin `setPlacement`-menetelmällä käyttäen `Toast.Placement`-enumia ja yhtä seuraavista arvoista:

- **BOTTOM**: Asettaa ilmoituksen näytön alaosaan keskelle.
- **BOTTOM_LEFT**: Asettaa ilmoituksen näytön vasempaan alaosaan.
- **BOTTOM_RIGHT**: Asettaa ilmoituksen näytön oikeaan alaosaan.
- **TOP**: Asettaa ilmoituksen näytön yläosaan keskelle.
- **TOP_LEFT**: Asettaa ilmoituksen näytön vasempaan yläosaan.
- **TOP_RIGHT**: Asettaa ilmoituksen näytön oikeaan yläosaan.

Nämä vaihtoehdot antavat sinulle mahdollisuuden hallita `Toast`-ilmoituksen sijaintia sovelluksesi suunnittelun ja käyttökelpoisuuden tarpeiden mukaan.

```java
Toast toast = new Toast("Esimerkki-ilmoitus");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='600px'
/>

Mukauttamalla `Toast`-ilmoitusten sijoitusta voit varmistaa, että käyttäjät saavat tietoa tavalla, joka on sopivaa jokaisessa sovelluksessa, näytön asettelussa ja kontekstissa.

## Pinoaminen {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samaan aikaan, pinouttamalla niitä pystysuoraan niiden sijoituksen perusteella. Uudemmat ilmoitukset ilmestyvät lähempänä sijoitusreunaa, työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät missaa tärkeitä tietoja, vaikka toimintaa olisi paljon.

## Toimet ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjävuorovaikutusta oletuksena, webforJ mahdollistaa painikkeiden tai muiden vuorovaikutteisten elementtien lisäämisen, jotta ne olisivat hyödyllisempiä kuin pelkät ilmoitukset.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Tämänkaltaisen vuorovaikutuksen lisääminen antaa käyttäjille mahdollisuuden hallita tehtäviä ja suorittaa toimintoja siirtymättä pois nykyiseltä näytöltään, muuttaen `Toast`-ilmoituksen arvokkaaksi vuorovaikutus- ja sitoutumiskanavaksi.

## Tyylittely {#styling}

Voit tyylitellä `Toast`-ilmoituksia teemoilla aivan kuten muutkin webforJ-komponentit, tarjoten käyttäjille arvokasta kontekstia näytettävän tiedon tyypistä ja luoden johdonmukaisen tyylin koko sovelluksessasi. Voit joko asettaa teeman, kun luot Toastin, tai käyttää `setTheme()`-menetelmää.

```java
Toast toast = new Toast("Esimerkki-ilmoitus", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkki-ilmoitus");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit käyttää sisäänrakennettuja teemoja, voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`-ilmoituksen yleiseen tyylittelyyn.

Voit lisätä mukautetun teeman `Toast`iisi määrittelemällä mukautettuja CSS-muuttujia, jotka muuttavat komponentin ulkoasua. Seuraava esimerkki havainnollistaa, miten luoda `Toast` mukautetulla teemalla käyttäen webforJ:ta.

:::info `Toast`-kohdistaminen
Koska `Toast` ei sijaitse tietyssä paikassa DOM:ssa, voit kohdistaa siihen käyttäen CSS-muuttujia. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikille Toast-ilmoituksille.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
