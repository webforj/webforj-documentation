---
title: Drawer
sidebar_position: 35
description: >-
  Slide panels in from any screen edge with the Drawer component, ideal for side
  navigation, filter menus, settings, and stackable popovers.
_i18n_hash: c04ddb6b8576656535eb1fa09ea587e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukupaneelin, joka ilmestyy näytön reunasta paljastaen lisäsisältöä keskeyttämättä nykyistä näkymää. Sitä käytetään yleisesti sivunavigaatioon, suodatinvalikoihin, käyttäjäasetuksiin tai tiiviisiin ilmoituksiin, jotka tarvitsevat tilaa hetkellisesti ilman pääliittymän häiritsemistä.

<!-- INTRO_END -->

## Pinoaminen {#stacking}

Laatikot pinoutuvat automaattisesti, kun useita on avattu, mikä tekee niistä joustavan valinnan tilarajoitetuissa käyttöliittymissä.

Alla oleva esimerkki näyttää tämän käyttäytymisen [`AppLayout`](../components/app-layout) komponentin sisällä. Hampurilaisvalikolla aktivointi navigaatiolaatikko on integroitu [`AppLayout`](../components/app-layout):iin, kun taas alaosassa oleva tervetuloponnahdus käyttää erillistä `Drawer`-instanssia. Molemmat ovat samanaikaisesti läsnä ja pinoituvat itsenäisesti, osoittaen kuinka laatikoita voidaan integroida asettelukomponentteihin tai käyttää erillisinä elementteinä.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/frontend/css/drawer/drawerWelcome.css',
]}
/>

## Automaattinen keskittyminen {#autofocus}

`Drawer`-komponentti tukee automaattista keskittymistä, joka asettaa automaattisesti fokuksen ensimmäiseen fokusoituvaan elementtiin, kun `Drawer` avautuu. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toiminnalliseen elementtiin.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Esimerkki -->

## Otsikko {#label}

`setLabel()`-menetelmällä voidaan antaa merkityksellinen kuvaus `Drawer`:n sisällöstä. Kun otsikko asetetaan, apuvälineet kuten ruudunlukijat voivat ilmoittaa sen, auttaen käyttäjiä ymmärtämään `Drawer`:n tarkoituksen näkemättä sen visuaalista sisältöä.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävien hallinta");
```

:::tip Kuvailevat otsikot
Käytä tiiviitä ja kuvailevia otsikoita, jotka heijastavat `Drawer`:n tarkoitusta. Vältä geneerisiä termejä kuten "Valikko" tai "Paneeli", kun tarkempi nimi voidaan käyttää.
:::

## Koko {#size}

`Drawer`:n koon hallitsemiseksi aseta arvo CSS:n mukautetulle ominaisuudelle `--dwc-drawer-size`. Tämä asettaa `Drawer`:n leveyden vasemman/oikean sijoittamisen tai korkeuden ylhäältä/alhaalta sijoittamisen osalta.

Voit määrittää arvon käyttämällä mitä tahansa hyväksyttävää CSS-yksikköä, kuten prosentteja, pikseliä tai vw/vh, käyttäen joko Javaa tai CSS:ää:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

Estääksesi `Drawer`:n kasvamasta liian suureksi, käytä `--dwc-drawer-max-size`-ominaisuutta sen kanssa:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Sijoitus {#placement}

`setPlacement()`-menetelmä hallitsee, minne `Drawer` ilmestyy näkymään.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **YLÄOSA**: Asettaa laatikon näkymän yläreunaan.
- **YLÄKESKIOSSA**: Keskittää laatikon vaakasuunnassa näytön yläreunaan.
- **ALHAALLA**: Asettaa laatikon näkymän alaosaan.
- **ALHAISKESKIOSSA**: Keskittää laatikon vaakasuunnassa näkymän alaosaan.
- **VASEN**: Asettaa laatikon vasemman reunan varaan.
- **OIKEA**: Asettaa laatikon oikean reunan varaan.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Tapahtumien käsittely {#event-handling}

`Drawer`-komponentti lähettää elinkaaritapahtumia, joita voidaan käyttää sovelluslogiikan laukaisemiseen sen avautumisen tai sulkeutumisen tilan muutoksen yhteydessä.

Tuetut tapahtumat:

- `DrawerOpenEvent`: Lähetetään, kun laatikko on täysin avattu.
- `DrawerCloseEvent`: Lähetetään, kun laatikko on täysin suljettu.

Voit liittää kuuntelijoita näille tapahtumille suorittaaksesi logiikan, kun `Drawer`:n tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele laatikon avaus tapahtuma
});

drawer.addCloseListener(e -> {
  // Käsittele laatikon sulkemis tapahtuma
});
```

## Esimerkki: Yhteydenottovalitsin {#example-contact-picker}

`Drawer`-komponentti tuo esille lisäsisältöä keskeyttämättä nykyistä näkymää. Tämä esimerkki sijoittaa laatikon alaosaan keskelle, sisältäen vieritettävän yhteystietoluettelon.

Jokaisessa yhteystiedossa näkyy avatar, nimi, sijainti ja toimintopainike nopeaa pääsyä yksityiskohtiin tai viestintään varten. Tämä lähestymistapa toimii hyvin tiivien työkalujen luomisessa, kuten yhteydenottovalitsijat, asetuspaneelit tai ilmoitukset.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Esimerkki: Tehtävien hallinta {#example-task-manager}

Tämä esimerkki käyttää `Drawer`:ia tehtävien hallintana. Voit lisätä tehtäviä, merkitä ne suoritetuiksi ja tyhjentää valmiit. `Drawer`:n alatunniste sisältää lomakeohjaimia, joilla voidaan vuorovaikuttaa tehtäväluettelon kanssa, ja "Lisää tehtävä" [`Button`](../components/button) poistaa itsensä käytöstä, jos 50 tehtävään saavutetaan.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/frontend/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Tyylitys {#styling}

<TableBuilder name="Drawer" />
