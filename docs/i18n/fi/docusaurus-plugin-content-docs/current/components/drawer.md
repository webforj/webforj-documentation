---
title: Drawer
sidebar_position: 35
_i18n_hash: d0c9ff09e591673c99918aa6875af28a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukupaneelin, joka ilmestyy näytön reunasta, paljastaen lisää sisältöä ilman, että nykyistä näkymää poistutaan. Sitä käytetään usein sivunavigaatiossa, suodatinvalikoissa, käyttäjäasetuksissa tai kompakteissa ilmoituksissa, jotka tarvitsevat ilmestyä tilapäisesti keskeyttämättä pääkäyttöliittymää.

<!-- INTRO_END -->

## Pinoutuminen {#stacking}

Laatikot pinoutuvat automaattisesti, kun useita avataan, mikä tekee niistä joustavan valinnan tilarajoitetuissa käyttöliittymissä.

Esimerkki alla näyttää tämän käyttäytymisen [`AppLayout`](../components/app-layout) -komponentin sisällä. Hampurilaisvalikosta laukaistu navigointilaatikko on rakennettu oscillating [`AppLayout`](../components/app-layout) -komponenttiin, kun taas alareunassa oleva vastaanottopiste käyttää itsenäistä `Drawer`-instanssia. Molemmat elävät rinnakkain ja pinoutuvat itsenäisesti, osoittaen, kuinka laatikoita voidaan integroida asettelukomponentteihin tai käyttää itsenäisinä elementteinä.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer`-komponentti tukee automaattista keskittymistä, mikä asettaa keskittymisen ensimmäiseen keskityttävään elementtiin, kun `Drawer` avautuu. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toimivaan elementtiin.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Esimerkki -->

## Label {#label}

`setLabel()`-menetelmä voi tarjota merkityksellisen kuvauksen `Drawer`:n sisällöstä. Kun etiketti on asetettu, esteettömyysteknologiat, kuten ruudunlukijat, voivat ilmoittaa sen, mikä auttaa käyttäjiä ymmärtämään `Drawer`:n tarkoituksen näkemättä sen visuaalisia sisältöjä.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävien hallinta");
```

:::tip Kuvastavat etiketit
Käytä lyhyitä ja kuvaavia etikettejä, jotka heijastavat `Drawer`:n tarkoitusta. Vältä geneerisiä termejä kuten "Valikko" tai "Paneeli", kun enemmän spesifinen nimi voidaan käyttää.
:::

## Koko

Säätääksesi `Drawer`:n kokoa, aseta arvo CSS:n mukautetulle ominaisuudelle `--dwc-drawer-size`. Tämä asettaa `Drawer`:n leveyden vasemmalle/oikealle tai korkeuden ylös/alhaalle asennossa.

Voit määrittää arvon käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten prosenttia, pikseliä tai vw/vh, käyttäen joko Javaa tai CSS:ää:

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

Estääksesi `Drawer`:n kasvamasta liian suureksi, käytä `--dwc-drawer-max-size` -parametria yhdessä sen kanssa:

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

`setPlacement()`-menetelmä hallitsee, mihin `Drawer` ilmestyy näkymässä.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **YLÄOSA**: Asettaa laatikon näkymän yläreunaan.
- **YLÄKESKITYS**: Kohdistaa laatikon vaaka-suuntaisesti keskelle näkymän yläosassa.
- **ALHAOSA**: Asettaa laatikon näkymän alareunaan.
- **ALHAKESKITYS**: Kohdistaa laatikon vaaka-suuntaisesti keskelle näkymän alareunassa.
- **VASEN**: Asettaa laatikon näkymän vasempaan reunaan.
- **OIKOINEN**: Asettaa laatikon näkymän oikeaan reunaan.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Tapahtumien käsittely

`Drawer`-komponentti lähettää elinkaaritapahtumia, joita voidaan käyttää sovelluslogiikan laukaisemiseen sen avonaisessa tai suljetussa tilassa tapahtuvien muutosten myötä. 

Tuetut tapahtumat:

- `DrawerOpenEvent`: Laukaisee, kun laatikko on täysin avattu.
- `DrawerCloseEvent`: Laukaisee, kun laatikko on täysin suljettu.

Voit liittää kuuntelijoita näihin tapahtumiin suorittaaksesi logiikkaa, kun `Drawer`:n tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele laatikon avattu tapahtuma
});

drawer.addCloseListener(e -> {
  // Käsittele laatikon suljettu tapahtuma
});
```

## Esimerkki: Yhteystietovalitsin

`Drawer`-komponentti paljastaa lisäsisältöä keskeyttämättä nykyistä näkymää. Tämä esimerkki asettaa laatikon alhaiseen keskikohtaan, sisältäen vieritettävän yhteystietoluettelon.

Jokainen yhteystieto näyttää avatarin, nimen, sijainnin ja toimintopainikkeen nopeaa pääsyä tietojen tai viestinnän. Tämä lähestymistapa toimii hyvin kompaktien työkalujen, kuten yhteystietovalitsimien, asetuspaneelien tai ilmoitusten rakentamiseen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Esimerkki: Tehtävien hallinta

Tässä esimerkissä käytetään `Drawer`:ia tehtävien hallintaan. Voit lisätä tehtäviä, merkitä ne suoritettavaksi ja tyhjentää suoritetut. `Drawer`:n alatunniste sisältää lomakkeen ohjaimia tehtäväluettelon käsittelemiseksi, ja "Lisää tehtävä" [`Button`](../components/button) poistaa toimintansa, jos 50 tehtävää saavutetaan.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Tyylit {#styling}

<TableBuilder name="Drawer" />
