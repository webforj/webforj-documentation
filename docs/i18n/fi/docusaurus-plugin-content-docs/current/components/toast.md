---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: e0312bf77de08272221f84c9c231c2df
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` on pieni, tilapäinen ilmoitus, joka ilmestyy antaakseen käyttäjille palautetta toiminnasta tai tapahtumasta. Toastit esittävät viestejä, kuten onnistumisvahvistuksia, varoituksia tai virheitä ilman, että ne keskeyttävät nykyistä työskentelyä, ja häviävät automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

`Toast.show()` -metodi luo `Toast`in, lisää sen `Frameen` ja näyttää sen yhdellä koodirivillä. Anna parametreja `show()`-metodiin konfiguroidaksesi näytettävän `Toast`in:

```java
Toast.show("Toiminto suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän tarkkuutta komponentin hallintaan, voit myös luoda `Toast`in standardirakentajalla ja käyttää `open()`-metodia sen näyttämiseen.

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
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
Toisin kuin muut komponentit, `Toast`ia ei tarvitse erikseen lisätä säilöön, kuten `Frameen`. Kun kutsut `open()`-metodia, `Toast` liitetään automaattisesti ensimmäiseen sovelluksen `Frameen`.
:::

Toastit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toimille, kuten lomakkeiden lähettämisille, datan tallentamisille tai virheille.
- **Mukautettavat teemat** onnistumisten, virheiden, varoitusten tai tiedottavien viestien erottamiseksi.
- **Joustavat sijaintivaihtoehdot** ilmoitusten näyttämiseen eri paikoissa näytöllä ilman, että käyttäjän työskentely keskeytyy.

## Kesto {#duration}

Voit konfiguroida `Toast`-ilmoitukset häviämään asetetun keston jälkeen tai pysymään näytöllä, kunnes ne hylätään, tarpeidesi mukaan. Voit mukauttaa kestoa `setDuration()`-metodilla tai yksinkertaisesti määrittää kesto-parametrin rakentajalle tai `show()`-metodille.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin jälkeen.
:::

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setDuration(10000);
toast.open();
```

### Kestävät toastit {#persistent-toasts}

Voit luoda kestävän `Toast`in asettamalla negatiivisen kestön. Kestävät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisille hälytyksille tai tilanteissa, joissa vaaditaan käyttäjän vuorovaikutusta tai tunnustamista.

:::caution
Ole varovainen kestävien `Toast`-ilmoitusten kanssa, ja varmista, että käyttäjälle on tarjottu tapa hylätä ilmoitus. Käytä `close()`-metodia piilottaaksesi `Toast` sen jälkeen, kun käyttäjä on tunnustanut sen tai suorittanut vaaditun vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijainti {#placement}

webforJ:n `Toast`-komponentin avulla voit valita, mihin ilmoitus ilmestyy näytöllä sovelluksesi ulkoasun ja käytettävyyden vaatimusten mukaan. Oletuksena `Toast`-ilmoitukset näkyvät näytön alhaalla keskellä.

Voit asettaa `Toast`-ilmoituksen sijainnin `setPlacement`-metodilla käyttäen `Toast.Placement`-erotinta, jossa on yksi seuraavista arvoista:

- **BOTTOM**: Asettuu ilmoitus näytön alaosaan keskelle.
- **BOTTOM_LEFT**: Asettuu ilmoitus näytön vasempaan alakulmaan.
- **BOTTOM_RIGHT**: Asettuu ilmoitus näytön oikeaan alakulmaan.
- **TOP**: Asettuu ilmoitus näytön yläosaan keskelle.
- **TOP_LEFT**: Asettuu ilmoitus näytön vasempaan yläkulmaan.
- **TOP_RIGHT**: Asettuu ilmoitus näytön oikeaan yläkulmaan.

Nämä vaihtoehdot mahdollistavat `Toast`-ilmoituksen sijainnin hallinnan sovelluksesi ulkoasun ja käytettävyyden tarpeiden mukaan.

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

Mukauttamalla `Toast`-ilmoitustesi sijaintia voit varmistaa, että käyttäjät saavat informaatiota tavalla, joka on sopiva mille tahansa sovellukselle, näytön asettelulle ja kontekstille.

## Pinout {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinottuna pystysuunnassa niiden sijainnin perusteella. Uudemmat ilmoitukset ilmestyvät lähempänä sijaintireunaa, työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät jää paitsi tärkeästä tiedosta, vaikka ympärillä tapahtuisi paljon.

## Toiminnot ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletusarvoisesti, webforJ antaa sinun lisätä painikkeita tai muita vuorovaikutteisia elementtejä, jotka tekevät niistä hyödyllisemmiksi kuin pelkät ilmoitukset.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Lisäämällä tällaisia vuorovaikutteisuuksia voit antaa käyttäjille mahdollisuuden käsitellä tehtäviä ja suorittaa toimintoja ilman, että heidän tarvitsee siirtyä pois nykyiseltä näytöltään, muuttaen `Toast`-ilmoituksen arvokkaaksi vuorovaikutus- ja sitoutumiskanavaksi.

## Tyylittely {#styling}

Voit tyylitellä `Toast`-ilmoituksia teemoilla aivan kuten muutkin webforJ-komponentit, tarjoten käyttäjille arvokasta kontekstiä näytettävän tiedon luonteesta ja luoden johdonmukaisen ulkoasun koko sovelluksessa. Voit joko asettaa teeman luodessasi Toastin tai käyttää `setTheme()`-metodia.

```java
Toast toast = new Toast("Esimerkkihälytys", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkihälytys");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Sisäänrakennettujen teemojen lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`in kokonaisulkoasusta.

Lisätäksesi mukautetun teeman `Toast`ille, voit määrittää mukautettuja CSS-muuttujia, jotka muokkaavat komponentin ulkoasua. Seuraava esimerkki havainnollistaa, kuinka luodaan `Toast`, jossa on mukautettu teema webforJ:n avulla.

:::info `Toast`-kohdistus
Koska `Toast` ei sijaitse tietyssä paikassa DOM:ssa, voit kohdistaa sen käyttämällä CSS-muuttujia. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikille `Toast`-ilmoituksille.
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
