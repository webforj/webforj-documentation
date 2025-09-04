---
title: Drawer
sidebar_position: 35
sidebar_class_name: updated-content
_i18n_hash: a19d1b8c8e0b74cecee529e86649d449
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukuvan paneelin, joka ilmestyy näytön reunalta, paljastaen lisäsisältöä poistumatta nykyisestä näkymästä. Sitä käytetään yleisesti sivuavigaatioon, suodatinvalikkoihin, käyttäjäasetuksiin tai tiivistettyihin ilmoituksiin, jotka tarvitsevat ilmestyä tilapäisesti häiritsemättä pääkäyttöliittymää.

`Drawers`-paneelit pinoutuvat automaattisesti, kun useita on avoinna, mikä tekee niistä joustavan vaihtoehdon tilarajoitteisille käyttöliittymille.

Esimerkki alla esittää tätä käyttäytymistä [`AppLayout`](../components/app-layout) -komponentissa. Hampurilaisvalikon aiheuttama navigointiluukku on rakennettu [`AppLayout`](../components/app-layout) -komponenttiin, kun taas alareunassa oleva tervetulotoivotuspomppupaneeli käyttää erillistä `Drawer`-instanssia. Molemmat ovat olemassa rinnakkain ja pinoutuvat itsenäisesti, todistaen kuinka `Drawers`-paneeleja voidaan integroida asettelukomponentteihin tai käyttää itsenäisinä elementteinä.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer`-komponentti tukee automaattista fokusta, joka asettaa fokus heti ensimmäiselle fokusoitavalle elementille, kun `Drawer` avautuu. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toiminnalliseen elementtiin.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Esimerkki -->

## Label {#label}

`setLabel()`-metodi voi tarjota merkityksellisen kuvauksen `Drawer`-paneelin sisällöstä. Kun etiketti on asetettu, apuvälineet kuten ruudunlukuohjelmat voivat ilmoittaa sen, auttaen käyttäjiä ymmärtämään `Drawer`-paneelin tarkoituksen näkemättä sen visuaalista sisältöä.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävien hallinta");
```

:::tip Kuvalliset Etiketit
Käytä tiiviitä ja kuvaavia etikettejä, jotka heijastavat `Drawer`-paneelin tarkoitusta. Vältä geneerisiä termejä kuten "Valikko" tai "Paneeli", kun tarkempi nimi voi olla käytettävissä.
:::

## Koko

`Drawer`-paneelin koon hallitsemiseksi aseta arvo CSS:n mukautetulle ominaisuudelle `--dwc-drawer-size`. Tämä määrittää `Drawer`-paneelin leveyden vasemmalle/oikealle sijoittamiselle tai korkeuden ylös/alaspäin sijoittamiselle.

Voit määrittää arvon käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten prosentteja, pikseleitä tai vw/vh, joko Java- tai CSS-muodossa:

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

Estääksesi `Drawer`-paneelin kasvamasta liian suureksi, käytä `--dwc-drawer-max-size` -arvoa yhdessä sen kanssa:

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

## Sijoitus

`setPlacement()`-metodi hallitsee, missä kohtaa `Drawer`-paneeli näkyy näkymässä.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **YLÄOSA**: Asettelu paneeli yläpään reunalle näkymässä.
- **YLÄKESKI**: Kohdistaa paneelin vaaka-keskitetysti yläpään reunalle näkymässä.
- **ALHAOSA**: Asettelu paneeli alareunaan näkymässä.
- **ALHAKESKI**: Vaaka-keskittää paneelin alaspäin näkymässä.
- **VASEN**: Asettelu paneeli vasemmalle reunalle näkymässä.
- **OIKEA**: Asettelu paneeli oikealle reunalle näkymässä.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Tapahtumien käsittely

`Drawer`-komponentti tuottaa elinkaaritapahtumia, joita voidaan käyttää sovelluksen logiikan suorittamiseen `Drawer`-paneelin avoimuuden tai sulkemisen tilan muuttuessa.

Tuetut tapahtumat:

- `DrawerOpenEvent`: Lausutaan, kun paneeli on täysin avattu.
- `DrawerCloseEvent`: Lausutaan, kun paneeli on täysin suljettu.

Voit liittää kuuntelijoita näihin tapahtumiin suoraan logiikan suorittamiseksi, kun `Drawer`-paneelin tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele avattua paneelia
});

drawer.addCloseListener(e -> {
  // Käsittele suljettua paneelia
});
```

## Esimerkki: Yhteystietojen valitsija

`Drawer`-komponentti tuo esiin lisäsisältöä häiritsemättä nykyistä näkymää. Tämä esimerkki sijoittaa paneelin alareunaan keskelle, sisältäen vieritettävän yhteystietoluettelon.

Jokainen yhteystieto näyttää avatarin, nimen, sijainnin ja toimintopainikkeen nopeaa pääsyä varten yksityiskohtiin tai viestintään. Tämä lähestymistapa toimii hyvin kompaktien työkalujen, kuten yhteystietovalitsijoiden, asetuspaneelien tai ilmoitusten rakentamiseen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Esimerkki: Tehtävien hallinta

Tämä esimerkki käyttää `Drawer`-paneelia tehtävien hallintana. Voit lisätä tehtäviä, rastittaa ne ja tyhjentää suoritetut. `Drawer`-paneelin alatuki sisältää lomakeohjaimia vuorovaikutukseen tehtäväluettelon kanssa, ja "Lisää tehtävä" [`Button`](../components/button) poistaa itsensä käytöstä, jos 50 tehtävää saavutetaan.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Tyylit {#styling}

<TableBuilder name="Drawer" />
