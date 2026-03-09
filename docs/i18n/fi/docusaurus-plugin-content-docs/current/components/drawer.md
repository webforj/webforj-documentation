---
title: Drawer
sidebar_position: 35
_i18n_hash: 1ac0b2efc50e748c9fd18f92de8d0e6e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukupaneelin, joka ilmestyy näytön reunasta, paljastaen lisäsisältöä ilman, että käyttäjä poistuu nykyisestä näkymästä. Sitä käytetään yleisesti sivuvalikoissa, suodatinvalikoissa, käyttäjäasetuksissa tai tiiviissä ilmoituksissa, jotka tarvitsevat väliaikaista näyttämistä ilman pääkäyttöliittymän häiritsemistä.

`Drawers`-paneelit pinoutuvat automaattisesti useamman ollessa auki, mikä tekee niistä joustavan vaihtoehdon tilarajoitteisille käyttöliittymille.

Alla oleva esimerkki näyttää tämän käyttäytymisen [`AppLayout`](../components/app-layout) komponentin sisällä. Hampurilaisvalikosta käynnistettävä navigointipaneeli on rakennettu [`AppLayout`](../components/app-layout):iin, kun taas alareunassa oleva tervetuloponnahdus käyttää erillistä `Drawer`-instanssia. Molemmat ovat samanaikaisesti olemassa ja pinoutuvat itsenäisesti, mikä osoittaa, kuinka `Drawers`-paneeleita voidaan integroida asettelukomponentteihin tai käyttää itsenäisinä elementteinä.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

`Drawer`-komponentti tukee automaattista fokusta, joka asettaa fokuksen ensimmäiseen fokusoitavaan elementtiin, kun `Drawer` avataan. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toimintaan liittyvään elementtiin.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Esimerkki -->

## Label {#label}

`setLabel()`-metodilla voidaan antaa merkityksellinen kuvaus `Drawer`:n sisällöstä. Kun etiketti on asetettu, apuvälineet, kuten ruudunlukijat, voivat ilmoittaa sen, mikä auttaa käyttäjiä ymmärtämään `Drawer`-paneelin tarkoituksen ilman, että he näkevät sen visuaaliset sisällöt.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävämanageri");
```

:::tip Kuvastavat etiketit
Käytä tiiviitä ja kuvaavia etikettejä, jotka heijastavat `Drawer`-paneelin tarkoitusta. Vältä yleisiä termejä kuten "Valikko" tai "Paneeli", kun tarkempi nimi voidaan käyttää.
:::

## Koko

Voit hallita `Drawer`-paneelin kokoa asettamalla arvon CSS-muokattavalle ominaisuudelle `--dwc-drawer-size`. Tämä asettaa `Drawer`-paneelin leveyden vasemmalle/oikealle sijoitettuna tai korkeuden ylös/alas sijoitettuna.

Voit määritellä arvon käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten prosentteja, pikseleitä tai vw/vh, käyttäen joko Javaa tai CSS:ää:

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

Vältäksesi `Drawer`-paneelin kasvamista liian suureksi, käytä `--dwc-drawer-max-size` sen rinnalla:

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

## Sijoittaminen {#placement}

`setPlacement()`-metodi hallitsee, mihin `Drawer` ilmestyy näkymäalueella.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **YLÄOSA**: Asettelu paneelin yläreunaan.
- **YLÄ_KESKI**: Asettelu paneelin vaakasuorasti keskelle yläreunaa.
- **ALAOSA**: Paneelin sijoittaminen alaosaan näkymäaluetta.
- **ALA_KESKI**: Paneelin vaakasuora keskitys alaosassa näkymäaluetta.
- **VASEN**: Paneelin sijoittaminen näkymäalueen vasempaan reunaan.
- **OIKEA**: Paneelin sijoittaminen näkymäalueen oikeaan reunaan.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Tapahtumien käsittely

`Drawer`-komponentti lähettää elinkaaritapahtumia, joita voidaan käyttää sovelluslogiikan käynnistämiseen vastauksena sen avointen tai suljettujen tilojen muutoksiin. 

Tuetut tapahtumat:

- `DrawerOpenEvent`: Laukeaa, kun paneeli on täysin avattu.
- `DrawerCloseEvent`: Laukeaa, kun paneeli on täysin suljettu.

Voit liittää kuuntelijoita näihin tapahtumiin suorittamaan logiikkaa, kun `Drawer`-paneelin tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele avattu tapahtuma
});

drawer.addCloseListener(e -> {
  // Käsittele suljettu tapahtuma
});
```

## Esimerkki: Yhteystietojen valitsin

`Drawer`-komponentti tuo esiin lisäsisältöä häiritsemättä nykyistä näkymää. Tämä esimerkki sijoittaa drawerin ala keskelle, sisällyttäen skrollattavan yhteystietoluettelon.

Jokainen yhteystieto näyttää avatarin, nimen, sijainnin ja toimintopainikkeen nopeaa pääsyä yksityiskohtiin tai viestintään varten. Tämä lähestymistapa toimii hyvin tiiviiden työkalujen, kuten yhteystietovalitsimien, asetuspaneelien tai ilmoitusten, rakentamisessa.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Esimerkki: Tehtävämanageri

Tässä esimerkissä käytetään `Drawer`-paneelia tehtävämanagerina. Voit lisätä tehtäviä, merkitä ne suoritetuiksi ja tyhjentää valmiit. `Drawer`-paneelin alatunniste sisältää lomakeohjaimia tehtäväluettelon kanssa vuorovaikuttamiseksi, ja "Lisää tehtävä" [`Button`](../components/button) ei ole käytettävissä, jos 50 tehtävää on saavutettu.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Tyylitys {#styling}

<TableBuilder name="Drawer" />
