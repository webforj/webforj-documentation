---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukuvan paneelin, joka ilmestyy näytön reunasta, paljastaen lisäsisältöä ilman, että näkymää tarvitsee vaihtaa. Sitä käytetään yleisesti sivuvalikoissa, suodatinvalikoissa, käyttäjäasetuksissa tai kompaktille ilmoituksille, jotka tarvitsevat tilaa tilapäisesti ilman, että pääkäyttöliittymä keskeytyy.

<!-- INTRO_END -->

## Stacking {#stacking}

Laatikot pinoutuvat automaattisesti, kun useampi avataan, mikä tekee niistä joustavan valinnan rajoitetuissa käyttöliittymissä.

Alla oleva esimerkki näyttää tämän käyttäytymisen [`AppLayout`](../components/app-layout) -komponentin sisällä. Hampurilaisvalikosta laukaistu navigointilaatikko on rakennettu [`AppLayout`](../components/app-layout) -komponenttiin, kun taas alareunassa oleva tervetulopup-up käyttää itsenäistä `Drawer`-instanssia. Molemmat ovat rinnakkain ja pinoutuvat itsenäisesti, mikä osoittaa, kuinka laatikoita voidaan integroida asettelukomponentteihin tai käyttää itsenäisinä elementteinä.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer`-komponentti tukee automaattista fokusta, joka asettaa katseen ensimmäiseen fokusoituvaan elementtiin, kun `Drawer` avautuu. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toimivaan elementtiin.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Example -->

## Label {#label}

`setLabel()` -menetelmä voi tarjota merkityksellisen kuvauksen `Drawer`-sisällöstä. Kun etiketti asetetaan, apuvälineet, kuten ruudunlukuohjelmat, voivat ilmoittaa sen, mikä auttaa käyttäjiä ymmärtämään `Drawer`-komponentin tarkoituksen ilman, että sen visuaalista sisältöä tarvitsee nähdä.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävien hallinta");
```

:::tip Kuvailevat etiketit
Käytä ytimekkäitä ja kuvailevia etikettejä, jotka heijastavat `Drawer`-komponentin tarkoitusta. Vältä yleisiä termejä, kuten "Valikko" tai "Paneeli", kun tarkempi nimi voidaan käyttää.
:::

## Size

`Drawer`-koon hallitsemiseksi aseta arvo CSS:n mukautettavalle ominaisuudelle `--dwc-drawer-size`. Tämä asettaa `Drawer`-komponentin leveyden vasemmalle/oikealle sijoittamiselle tai korkeuden ylhäällä/alhaalla sijoittamiselle.

Voit määrittää arvon käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten prosenttia, pikseleitä tai vw/vh, joko Java- tai CSS-kielellä:

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

`Drawer`-komponentin liian suuriksi kasvamisen estämiseksi käytä `--dwc-drawer-max-size` -yhdessä sen kanssa:

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

## Placement {#placement}

`setPlacement()` -menetelmä hallitsee, mihin `Drawer` ilmestyy näkymäruudussa.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **TOP**: Asettuu näytön yläreunaan.
- **TOP_CENTER**: Kohdistaa laatikon vaaka-suunnassa keskelle näytön yläosaa.
- **BOTTOM**: Asettuu näytön ala-reunaan.
- **BOTTOM_CENTER**: Kohdistaa laatikon vaaka-suunnassa keskelle näytön alaosaa.
- **LEFT**: Asettuu näytön vasempaan reunaan.
- **RIGHT**: Asettuu näytön oikeaan reunaan.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Event handling

`Drawer`-komponentti tuottaa elinkaaritapahtumia, joita voidaan käyttää sovelluslogiikan laukaisemiseen sen avatussa tai suljetussa tilassa tapahtuvien muutosten vuoksi.

Tuettuja tapahtumia:

- `DrawerOpenEvent`: Luodaan, kun laatikko on täysin avattu.
- `DrawerCloseEvent`: Luodaan, kun laatikko on täysin suljettu.

Voit liittää kuuntelijoita näihin tapahtumiin, jotta logiikkaa voidaan suorittaa, kun `Drawer`-komponentin tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele, kun laatikko on avattu
});

drawer.addCloseListener(e -> {
  // Käsittele, kun laatikko on suljettu
});
```

## Esimerkki: Yhteystietojen valitsin

`Drawer`-komponentti paljastaa lisäsisältöä ilman, että nykyistä näkymää häiriintyy. Tässä esimerkissä laatikko sijoitetaan ala-keskelle, ja se sisältää vieritettävän kontaktin luettelon.

Jokainen yhteystieto näyttää avatarin, nimen, sijainnin ja toimintopainikkeen nopeaan pääsyyn tietosiin tai viestintään. Tämä lähestymistapa toimii hyvin kompakti työkalujen, kuten yhteystietojen valitsimien, asetuspaneelien tai ilmoitusten rakentamisessa.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Esimerkki: Tehtävien hallinta

Tässä esimerkissä käytetään `Drawer`-komponenttia tehtävien hallinnassa. Voit lisätä tehtäviä, merkitä ne suoritetuiksi ja tyhjentää valmiit. `Drawer`-komponentin alatunniste sisältää lomakeelementtejä, joilla voidaan vuorovaikuttaa tehtäväluettelon kanssa, ja “Lisää tehtävä” [`Button`](../components/button) deaktivoi itsensä, jos 50 tehtävää saavutetaan.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
