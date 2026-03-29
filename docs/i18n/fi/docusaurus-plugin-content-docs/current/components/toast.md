---
title: Toast
sidebar_position: 140
_i18n_hash: 0ac4df1a045e2706f2e9309327ba4683
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` on pieni, tilapäinen ilmoitus, joka näkyy antaakseen käyttäjille palautetta toiminnasta tai tapahtumasta. Toastit esittävät viestejä, kuten onnistuneita vahvistuksia, varoituksia tai virheitä keskeyttämättä nykyistä työskentelyä, ja häviävät automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

## Perusasiat {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast`-komponentti yhdellä koodirivillä `Toast.show()`-menetelmällä, joka luo `Toast`-komponentin, lisää sen `Frame`:en ja näyttää sen. Voit välittää parametreja `show`-menetelmään määrittääksesi näytettävän `Toast`in:

```java
Toast.show("Toiminto suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän tarkkuutta komponentin hallintaan, voit myös luoda `Toast`in normaalin konstruktorin avulla ja käyttää `open()`-metodia sen näyttämiseen.

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Oletuskäyttäytyminen
Toisin kuin muut komponentit, `Toast`-komponenttia ei tarvitse lisätä erikseen säiliöön, kuten `Frame`:en. Kun kutsut `open()`-metodia, `Toast` liitetään automaattisesti ensimmäiseen sovellus `Frame`:en.
:::

Toastit ovat monikäyttöisiä ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toimista, kuten lomakkeiden lähettämisestä, tietojen tallentamisesta tai virheistä.
- **Mukautettavat teemat** onnistuneiden, virheellisten, varoitus- tai tiedottavien viestien erottamiseksi.
- **Joustavat sijoitus** vaihtoehdot ilmoitusten näyttämiseksi eri alueilla näytöllä keskeyttämättä käyttäjän työskentelyä.

## Kesto {#duration}

Voit määrittää `Toast`-ilmoitusten häviävän asetetun keston kuluttua tai pysyvän näytöllä, kunnes ne poistetaan, tarpeidesi mukaan. Voit mukauttaa keston `setDuration()`-menetelmällä tai yksinkertaisesti toimittaa keston parametrin konstruktorille tai `show()`-menetelmälle.

:::info Oletuskesto
Oletusarvoisesti `Toast` sulkeutuu automaattisesti 5000 millisekunnin kuluttua.
:::

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toastit {#persistent-toasts}

Voit luoda pysyvän `Toast`-ilmoituksen asettamalla negatiivisen keston. Pysyvät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisissä varoituksissa tai tapauksissa, joissa käyttäjältä vaaditaan jonkinlaista vuorovaikutusta tai tunnustamista.

:::caution
Ole varovainen pysyvien `Toast`-ilmoitusten kanssa ja varmista, että käyttäjälle on tarjolla tapa sulkea ilmoitus. Käytä `close()`-menetelmää piilottaaksesi `Toast`in, kun käyttäjä on tunnustanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoitus {#placement}

webforJ:n `Toast`-komponentilla voit valita, mihin ilmoitus ilmestyy näytöllä sovelluksesi suunnittelun ja käytettävyyden vaatimusten mukaan. Oletusarvoisesti `Toast`-ilmoitukset näkyvät näytön alalaidassa keskellä. 

Voit asettaa `Toast`-ilmoituksen sijoituksen `setPlacement`-menetelmällä käyttäen `Toast.Placement`-enumia, jossa on seuraavat arvot:

- **BOTTOM**: Asettaa ilmoituksen näytön alaosaan keskelle.
- **BOTTOM_LEFT**: Asettaa ilmoituksen näytön vasempaan alakulmaan.
- **BOTTOM_RIGHT**: Asettaa ilmoituksen näytön oikeaan alakulmaan.
- **TOP**: Asettaa ilmoituksen näytön yläosaan keskelle.
- **TOP_LEFT**: Asettaa ilmoituksen näytön vasempaan yläkulmaan.
- **TOP_RIGHT**: Asettaa ilmoituksen näytön oikeaan yläkulmaan.

Nämä vaihtoehdot mahdollistavat `Toast`-ilmoituksen sijoituksen hallitsemisen sovelluksesi suunnittelun ja käytettävyyden tarpeiden mukaan.

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

Mukauttamalla `Toast`-ilmoitustesi sijoitusta voit varmistaa, että käyttäjät saavat tietoa tavalla, joka on sopiva mihin tahansa sovellukseen, näyttöasetteluun ja konteksteihin.

## Pinoaminen {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinomalla niitä pystysuunnassa niiden sijoituksen mukaan. Uudemmat ilmoitukset näkyvät lähempänä sijoitusreunaa, työntäen vanhempia ilmoituksia kauemmaksi. Tämä varmistaa, että käyttäjät eivät jää ilman tärkeitä tietoja, vaikka paljon tapahtuisi.

## Toiminnot ja vuorovaikutteisuus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletusarvoisesti, webforJ sallii sinun lisätä nappeja tai muita vuorovaikutteisia elementtejä tehdäksesi niistä hyödyllisempää kuin pelkkä ilmoitus.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Lisäämällä tällaista vuorovaikutteisuutta voit antaa käyttäjille mahdollisuuden hoitaa tehtäviä ja suorittaa toimintoja siirtymättä pois nykyiseltä näytöltään, muuttaen `Toast`-ilmoituksen arvokkaaksi vuorovaikutuksen ja sitoutumisen kanavaksi.

## Tyylit {#styling}

Voit muokata `Toast`-ilmoituksia teemoilla kuten muiden webforJ-komponenttien, tarjoten käyttäjille arvokasta kontekstia siitä, millaista tietoa näytetään, ja luoden johdonmukaisen tyylin koko sovelluksessasi. Voit joko määrittää teeman, kun luot Toastin tai käyttää `setTheme()`-menetelmää.

```java
Toast toast = new Toast("Esimerkkihälytys", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`-ilmoituksen yleisessä tyylissä.

Lisätäksesi mukautetun teeman `Toast`-ilmoitukseen voit määritellä mukautettuja CSS-muuttujia, jotka muuttavat komponentin ulkoasua. Seuraava esimerkki havainnollistaa, kuinka luoda `Toast` mukautetulla teemalla webforJ:n avulla.

:::info `Toast` Kohdistaminen
Koska `Toast` ei sijaitse tietyssä paikassa DOM:ssa, voit kohdistaa sen CSS-muuttujien avulla. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikkiin Toast-ilmoituksiin.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
