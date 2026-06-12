---
title: Drawer
sidebar_position: 35
_i18n_hash: 7edd08525f20625cb8d891316111ebb3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

`Drawer`-komponentti webforJ:ssä luo liukuvan paneelin, joka ilmestyy näytön reunalta, paljastaen lisäsisältöä ilman, että nykyinen näkymä poistuu. Sitä käytetään yleisesti sivunavigaatiossa, suodatinvalikoissa, käyttäjäasetuksissa tai kompakteissa ilmoituksissa, jotka tarvitsevat tilapäistä ilmestymistä ilman pääkäyttöliittymän häiritsemistä.

<!-- INTRO_END -->

## Stacking {#stacking}

Drawerit pinoavat automaattisesti useita avattuna, mikä tekee niistä joustavan valinnan tilarajoitteisille käyttöliittymille.

Alla oleva esimerkki osoittaa tämän käyttäytymisen [`AppLayout`](../components/app-layout) -komponentin sisällä. Hammburger-valikon laukaisema navigointidrawer on rakennettu osaksi [`AppLayout`](../components/app-layout), kun taas alareunassa oleva tervetuloa-popup käyttää erillistä `Drawer`-instanssia. Molemmat ovat olemassa ja pinoutuvat itsenäisesti, osoittaen, kuinka Drawerit voivat olla integroituna asettelukomponentteihin tai käytettävissä itsenäisinä elementteinä.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/resources/static/css/drawer/drawerWelcome.css',
]}
/>

## Autofocus

`Drawer`-komponentti tukee automaattista fokusta, joka asettaa automaattisesti kohdistuksen ensimmäiseen fokusoituvaan elementtiin, kun `Drawer` avataan. Tämä parantaa käytettävyyttä tuomalla huomion suoraan ensimmäiseen toimiin.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Esimerkki -->

## Label {#label}

`setLabel()`-metodi voi tarjota merkityksellisen kuvauksen `Drawer`-sisällöstä. Kun etiketti asetetaan, apuvälineet kuten näytönlukijat voivat ilmoittaa sen, auttaen käyttäjiä ymmärtämään `Drawer`-komponentin tarkoituksen näkemättä sen visuaalisia sisältöjä.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Tehtävämanageri");
```

:::tip Kuvaavat etiketit
Käytä tiiviitä ja kuvaavia etikettejä, jotka heijastavat `Drawer`-komponentin tarkoitusta. Vältä geneerisiä termejä kuten "Valikko" tai "Paneeli", kun tarkempi nimi voidaan käyttää.
:::

## Koko

Hallita `Drawer`-komponentin kokoa asettamalla arvo CSS:n mukautetulle ominaisuudelle `--dwc-drawer-size`. Tämä asettaa `Drawer`-komponentin leveyden vasen/oikea sijoituksessa tai korkeuden ylös/alas sijoituksessa.

Voit määrittää arvon käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten prosentteja, pikseleitä tai vw/vh, käyttäen joko Javaa tai CSS:ää:

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

Estääksesi `Drawer`-komponentin kasvamasta liian suureksi, käytä `--dwc-drawer-max-size` sen rinnalla:

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

`setPlacement()`-metodi hallitsee, mihin `Drawer`-komponentti ilmaisee itsensä näkymässä.

Saatavilla olevat sijoitusvaihtoehdot:

<!-- vale off -->
- **TOP**: Asettaa drawerin näkymän yläpisteeseen.
- **TOP_CENTER**: Kohdistaa drawerin suorakulmaisesti keskelle ylhäällä näkymässä.
- **BOTTOM**: Asettaa drawerin näkymän alaosaan.
- **BOTTOM_CENTER**: Kohdistaa drawerin suorakulmaisesti keskelle alhaalla näkymässä.
- **LEFT**: Asettaa drawerin vasenta reunaa myöten näkymässä.
- **RIGHT**: Asettaa drawerin oikeaa reunaa myöten näkymässä.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Tapahtumankäsittely

`Drawer`-komponentti lähettää elinkaaritapahtumia, joita voidaan käyttää sovelluslogiikan käynnistämiseen sen avatussa tai suljetussa tilassa.

Tuetut tapahtumat:

- `DrawerOpenEvent`: Lausutaan, kun drawer on täysin avattu.
- `DrawerCloseEvent`: Lausutaan, kun drawer on täysin suljettu.

Voit liittää kuuntelijoita näihin tapahtumiin suorittaaksesi logiikkaa, kun `Drawer`-komponentin tila muuttuu.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Käsittele drawerin avattu tapahtuma
});

drawer.addCloseListener(e -> {
  // Käsittele drawerin suljettu tapahtuma
});
```

## Esimerkki: Yhteystietojen valitsin

`Drawer`-komponentti tuo esiin lisäsisältöä häiritsemättä nykyistä näkymää. Tässä esimerkissä drawer asetetaan alhaalla keskelle, sisältäen vieritettävän yhteystietoluettelon.

Jokainen yhteystieto näyttää avatarin, nimen, sijainnin ja toimintopainikkeen, jolla pääsee nopeasti yksityiskohtiin tai viestintään. Tämä lähestymistapa toimii hyvin kompaktien työkalujen, kuten yhteystietojen valitsinten, asetuspaneelien tai ilmoitusten rakentamisessa.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Esimerkki: Tehtävämanageri

Tässä esimerkissä käytetään `Drawer`-komponenttia tehtävämanagerina. Voit lisätä tehtäviä, merkitä ne valituiksi ja tyhjentää valmiit tehtävät. `Drawer`-komponentin alatunniste sisältää lomakeohjaimia tehtävälistaan vuorovaikutukseen, ja "Lisää tehtävä" [`Button`](../components/button) estää itsensä, jos 50 tehtävää on saavutettu.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/resources/static/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
