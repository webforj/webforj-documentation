---
title: Toast
sidebar_position: 140
_i18n_hash: 7350867dde3a34f2c5fe2e40c4d745c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` -ilmoitus on hienovarainen ja huomaamaton ponnahdusilmoitus, joka on suunniteltu tarjoamaan käyttäjille reaaliaikaista palautetta ja tietoa. Näitä ilmoituksia käytetään tyypillisesti ilmoittamaan käyttäjille toiminnoista, kuten onnistuneista toimista, varoituksista tai virheistä, ilman että niiden työnkulku katkeaa. `Toast` -ilmoitukset häviävät yleensä tietyn ajan kuluttua eivätkä vaadi käyttäjän reaktiota.

webforJ:n `Toast` -komponentin avulla voit helposti toteuttaa nämä ilmoitukset parantaaksesi käyttäjäkokemusta tarjoamalla olennaista tietoa tutuilla, huomaamattomilla ja saumattomilla tavoilla. 

## Perusasiat {#basics}

webforJ tarjoaa nopean ja helpon tavan luoda `Toast` -komponentti yhdellä koodirivillä `Toast.show()` -menetelmällä, joka luo `Toast` -komponentin, lisää sen `Frame`-elementtiin ja näyttää sen. Voit siirtää parametreja `show`-menetelmälle konfiguroidaksesi näytettävää `Toast`-ilmoitusta:

```java
Toast.show("Toiminto suoritettu onnistuneesti!", Theme.SUCCESS);
```

Jos haluat enemmän tarkkuutta komponentin hallintaan, voit myös luoda `Toast`-ilmoituksen standardikonstruktori ja käyttää `open()`-menetelmää sen näyttämiseksi.

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
Toisin kuin muut komponentit, `Toast` -ilmoituksen ei tarvitse olla erikseen lisätty säiliöön, kuten `Frame`-elementtiin. Kun kutsut `open()`-menetelmää, `Toast`-ilmoitus liitetään automaattisesti ensimmäiseen sovellus `Frame` -elementtiin.
:::

Toasts ovat monipuolisia ja tarjoavat hienovaraisia ilmoituksia reaaliaikaisesta palautteesta. Esimerkiksi:

- **Reaaliaikainen palaute** toiminnoista, kuten lomakkeiden lähettämisestä, tietojen tallentamisesta tai virheistä.
- **Mukautettavat teemat** onnistumisen, virheen, varoituksen tai tiedotuksen erottamiseksi.
- **Joustavat sijoitusvaihtoehdot** ilmoitusten näyttämiseksi eri alueilla näytössä ilman, että käyttäjän työnkulku katkeaa.

## Kesto {#duration}

Voit määrittää, että `Toast`-ilmoitukset häipyvät tietyn ajan kuluttua tai pysyvät näytöllä, kunnes ne hylätään, tarpeidesi mukaan. Voit mukauttaa kestoa `setDuration()`-menetelmällä tai yksinkertaisesti siirtää kesto-parametrin konstruktoriin tai `show()`-menetelmään.

:::info Oletuskesto
Oletusarvoisesti `Toast` sulkeutuu automaattisesti 5000 millisekunnin kuluttua.
:::

```java
Toast toast = new Toast("Esimerkkisilmoitus");
toast.setDuration(10000);
toast.open();
```

### Pysyvät toasts {#persistent-toasts}

Voit luoda pysyvän `Toast`-ilmoituksen asettamalla negatiivisen keston. Pysyvät `Toast`-ilmoitukset eivät sulkeudu automaattisesti, mikä voi olla hyödyllistä kriittisissä hälytyksissä tai tapauksissa, joissa käyttäjältä vaaditaan vuorovaikutusta tai vahvistusta.

:::caution
Ole varovainen pysyvien `Toast`-ilmoitusten kanssa, ja varmista, että käyttäjälle on tarjottu tapa hylätä ilmoitus. Käytä `close()`-menetelmää piilottaaksesi `Toast`-ilmoituksen, kun käyttäjä on tunnustanut sen tai suorittanut tarvittavan vuorovaikutuksen.
:::

```java
Toast toast = new Toast("Toiminto suoritettu onnistuneesti!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Sijoittaminen {#placement}

webforJ:n `Toast` -komponentilla voit valita, mihin ilmoitus ilmestyy näytölle sovelluksesi suunnittelun ja käytettävyyden vaatimusten mukaan. Oletusarvoisesti `Toast` -ilmoitukset ilmestyvät näytön alaosaan keskelle.

Voit asettaa `Toast` -ilmoituksen sijoittamisen `setPlacement` -menetelmän avulla käyttämällä `Toast.Placement` enumia ja jotakin seuraavista arvoista:

- **BOTTOM**: Asettuu ilmoitus näytön alaosaan keskelle.
- **BOTTOM_LEFT**: Asettuu ilmoitus näytön vasempaan alaosaan.
- **BOTTOM_RIGHT**: Asettuu ilmoitus näytön oikeaan alaosaan.
- **TOP**: Asettuu ilmoitus näytön yläosaan keskelle.
- **TOP_LEFT**: Asettuu ilmoitus näytön vasempaan yläkulmaan.
- **TOP_RIGHT**: Asettuu ilmoitus näytön oikeaan yläkulmaan.

Nämä vaihtoehdot mahdollistavat `Toast`-ilmoituksen sijoituksen hallinnan sovelluksesi suunnittelun ja käytettävyyden tarpeiden mukaan.

```java
Toast toast = new Toast("Esimerkkisilmoitus");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Mukauttamalla `Toast`-ilmoitustesi sijoitusta voit varmistaa, että käyttäjät saavat tietoa tavalla, joka on sopiva missä tahansa sovelluksessa, näytön asettelussa ja kontekstissa.

## Pinoaminen {#stacking}

`Toast` -komponentti voi näyttää useita ilmoituksia samanaikaisesti, pinoamalla niitä pysty suuntaan niiden sijoituspaikan mukaan. Uudemmat ilmoitukset ilmestyvät lähempänä sijoitusreunaa, työntäen vanhempia ilmoituksia kauemmaksi. Tämä varmistaa, ettei käyttäjät jää tärkeitä tietoja huomaamatta, jopa silloin, kun paljon tapahtuu.

## Toiminnot ja vuorovaikutus {#actions-and-interactivity}

Vaikka `Toast` -ilmoitukset eivät vaadi käyttäjän vuorovaikutusta oletusarvoisesti, webforJ antaa sinun lisätä painikkeita tai muita vuorovaikutteisia elementtejä, jotta ne olisivat hyödyllisempia kuin pelkät ilmoitukset.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Lisäämällä tällaista vuorovaikutteisuutta voit antaa käyttäjille mahdollisuuden hoitaa tehtäviä ja suorittaa toimintoja ilman, että heidän tarvitsee poistua nykyiseltä näytöltään, muuttamalla `Toast`-ilmoituksen arvokkaaksi vuorovaikutus- ja sitoutumiskanavaksi.

## Tyylittely {#styling}

Voit tyylittää `Toast`-ilmoituksia teemoilla aivan kuten muidenkin webforJ-komponenttien kanssa, antaen käyttäjille arvokasta kontekstia siitä, millaisista tiedoista on kyse, ja luoden johdonmukaisen tyylin koko sovelluksessasi. Voit joko asettaa teeman, kun luot `Toast`-ilmoituksen tai käyttää `setTheme()`-menetelmää.

```java
Toast toast = new Toast("Esimerkkisilmoitus", Theme.INFO);
```

```java
Toast toast = new Toast("Esimerkkisilmoitus");
toast.setTheme(Theme.INFO);
```

### Mukautetut teemat {#custom-themes}

Lisäksi voit luoda omia mukautettuja teemoja `Toast`-ilmoituksille. Tämä mahdollistaa henkilökohtaisemman ja brändätyn käyttäjäkokemuksen, antaen sinulle täyden hallinnan `Toast`-ilmoituksen yleisestä tyylittelystä.

Voit lisätä mukautetun teeman `Toast`-ilmoitukseen määrittelemällä mukautettuja CSS-muuttujia, jotka muokkaavat komponentin ulkoasua. Seuraavassa esimerkissä näytetään, kuinka luodaan `Toast`, jossa on mukautettu teema webforJ:n avulla.

:::info `Toast` -kohdistaminen
Koska `Toast` ei sijaitse tiettyyn sijaintiin DOM:ssa, voit kohdistaa sen CSS-muuttujien avulla. Nämä muuttujat helpottavat johdonmukaisten mukautettujen tyyliin soveltamista kaikille Toast-ilmoituksille.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
