---
title: Toast
sidebar_position: 140
_i18n_hash: 563743d9f91aff0002f8965cbf719d99
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` on pieni, tilapäinen ilmoitus, joka ilmestyy antaakseen käyttäjille palautetta toiminnasta tai tapahtumasta. Toastit näyttävät viestejä, kuten onnistumisvahvistuksia, varoituksia tai virheitä ilman, että nykyinen työskentelykatko keskeytyy, ja ne katoavat automaattisesti asetetun ajan kuluttua.

<!-- INTRO_END -->

## Perusteet {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast`-komponentti yhdellä koodirivillä `Toast.show()`-menetelmällä, joka luo `Toast`-komponentin, lisää sen `Frame`-komponenttiin ja näyttää sen. Voit antaa parametreja `show`-menetelmälle näyttämääsi `Toast`:iin liittyen:

```java
Toast.show("Toiminto suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän hienosäätöä komponenttiin, voit myös luoda `Toast`-komponentin käytännön konstruktorilla ja käyttää `open()`-menetelmää sen esittämiseen.

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Oletuskäyttäytyminen
Toisin kuin muut komponentit, `Toast`-komponenttia ei tarvitse erikseen lisätä säilöön, kuten `Frame`:een. Kun kutsut `open()`-menetelmää, `Toast` liitetään automaattisesti ensimmäiseen sovelluksen `Frame`:een.
:::

Toasit ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisista palautteista. Esimerkiksi:

- **Reaaliaikainen palaute** toimista, kuten lomakkeiden lähettämisestä, tietojen tallentamisesta tai virheistä.
- **Mukautettavat teemat** erottamaan onnistuneet, virheelliset, varoittavat tai tiedottavat viestit.
- **Joustavat sijoittamisvaihtoehdot** ilmoitusten näyttämiseksi eri paikoissa näytöllä ilman, että käyttäjän työskentely keskeytyy.

## Kesto {#duration}

Voit määrittää `Toast`-ilmoitusten katoamisen tapahtuvan asetetun keston jälkeen tai pysyvän näytöllä, kunnes ne hylätään, tarpeidesi mukaan. Voit mukauttaa kestoa `setDuration()`-menetelmällä tai yksinkertaisesti toimittamalla keston parametrin konstruktorille tai `show()`-menetelmälle.

:::info Oletuskesto
Oletuksena `Toast` sulkeutuu automaattisesti 5000 millisekunnin jälkeen.
:::

```java
Toast toast = new Toast("Esimerkkiviesti");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toastit {#persistent-toasts}

Voit luoda pysyvän `Toast`-ilmoituksen asettamalla negatiivisen keston. Pysyvät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisten hälytysten tai tilanteissa, joissa käyttäjältä vaaditaan jonkinlaista vuorovaikutusta tai tunnustamista.

:::caution
Ole varovainen pysyvien `Toast`-ilmoitusten kanssa ja varmista, että tarjoat tavan käyttäjän sulkea ilmoitus. Käytä `close()`-menetelmää piilottaaksesi `Toast`:in, kun käyttäjä on tunnustanut sen tai suorittanut tarvittavat vuorovaikutukset.
:::

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoittaminen {#placement}

webforJ:n `Toast`-komponentilla voit valita, missä ilmoitus näkyy näytöllä sovelluksesi suunnittelun ja käytettävyyden vaatimusten mukaisesti. Oletuksena `Toast`-ilmoitukset näkyvät näytön alaosassa keskellä.

Voit määrittää `Toast`-ilmoituksen `placement`-asetuksen `setPlacement`-menetelmällä käyttämällä `Toast.Placement`-enumerointia seuraavilla arvoilla:

- **ALUE**: Asettuu ilmoitus näytön alaosaan keskelle.
- **ALUE_VASEN**: Asettuu ilmoitus näytön vasempaan alakulmaan.
- **ALUE_OIKEA**: Asettuu ilmoitus näytön oikeaan alakulmaan.
- **YLÄ**: Asettuu ilmoitus näytön yläosaan keskelle.
- **YLÄ_VASEN**: Asettuu ilmoitus näytön vasempaan yläkulmaan.
- **YLÄ_OIKEA**: Asettuu ilmoitus näytön oikeaan yläkulmaan.

Nämä vaihtoehdot mahdollistavat `Toast`-ilmoituksen sijoittamisen hallinnan sovelluksesi suunnittelun ja käytettävyyden tarpeiden mukaan.

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

Mukauttamalla `Toast`-ilmoitusten sijoittamista voit varmistaa, että käyttäjät saavat tietoa tavalla, joka on sopiva annettuna sovellukselle, näyttöasettelulle ja kontekstille.

## Pinottaminen {#stacking}

`Toast`-komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinoamalla ne pystysuunnassa niiden sijoittamisen mukaan. Uudemmat ilmoitukset ilmestyvät lähemmäs sijoitusreunaa työntäen vanhempia ilmoituksia kauemmas. Tämä varmistaa, että käyttäjät eivät jää paitsi tärkeistä tiedoista, vaikka näytöllä olisi paljon tapahtumaa.

## Toiminnot ja vuorovaikutteisuus {#actions-and-interactivity}

Vaikka `Toast`-ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletuksena, webforJ mahdollistaa painikkeiden tai muiden vuorovaikutteisten elementtien lisäämisen, jotta ne olisivat hyödyllisempia kuin pelkkä ilmoitus.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Tällaisen vuorovaikutteisuuden lisäämisen avulla voit antaa käyttäjille mahdollisuuden hoitaa tehtäviä ja suorittaa toimintoja ilman, että heidän tarvitsee siirtyä pois nykyiseltä näytöltään, muuttamalla `Toast`-ilmoitus arvokkaaksi vuorovaikutuksen ja sitoutumisen väyläksi.

## Tyylittely {#styling}

Voit tyylittää `Toast`-ilmoituksia teemoilla kuten muutkin webforJ-komponentit, antaen käyttäjille arvokasta kontekstia siitä, minkä tyyppistä tietoa näytetään, ja luoden johdonmukaisen tyylin koko sovelluksessasi. Voit joko asettaa teeman, kun luot Toastin tai käyttää `setTheme()`-menetelmää.

```java
Toast toast = new Toast("Esimerkkiviesti", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkiviesti");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen tarjoamisen ja antaa sinulle täydellisen hallinnan `Toast`-ilmoituksen kokonaisilmeestä.

Voit lisätä mukautetun teeman `Toast`:iin määrittelemällä mukautettuja CSS-muuttujia, jotka muokkaavat komponentin ulkonäköä. Seuraava esimerkki havainnollistaa, kuinka luoda `Toast` mukautetulla teemalla webforJ:lla.

:::info `Toast` Kohdistaminen
Koska `Toast` ei sijaitse tiettyyn kohtaan DOM:issa, voit kohdistaa sen käyttämällä CSS-muuttujia. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyylien soveltamista kaikkiin Toast-ilmoituksiin.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
