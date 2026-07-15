---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 559d0c63a8e61e2e3d79086aa08922c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout`-komponentti tarjoaa valmiin sivurakenteen, jossa on kiinteä ylä- ja alaosa, liukuva työkalupakki sekä vieritettävä sisältöalue. Nämä osiot katettavat hallintapaneelien, admin-paneelien ja useimpien moniosioisten käyttöliittymien asettelutarpeet.

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusten asettelujen rakentamisen.

<ul>
    <li>Helppo käyttää ja muokata</li>
    <li>Vastaava suunnittelu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tummassa Režiimissä</li>
</ul>

Se tarjoaa kiinteän yläosan, alaosan, työkalupakin ja sisältöosion, jotka kaikki on rakennettu vastaavaksi komponentiksi ja joita voidaan helposti muokata yhteisten sovellusten asettelujen, kuten hallintapaneelin, rakentamiseksi. Yläosa ja alaosa ovat kiinteitä, työkalupakki liukuu näkymän sisään ja ulos, ja sisältö on vieritettävissä.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa voimassa olevaa webforJ-ohjainta. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää viewportin meta-tag, joka sisältää viewport-fit=cover. Meta-tagi aiheuttaa näkymän skaalaantuvan täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraava koodinäyte tuottaa sovelluksen, jossa on kokoontaitettava sivupalkki, joka sisältää logon ja välilehdet eri sisältövaihtoehdoille sekä yläosan. Demo käyttää dwc-icon-button-webkomponenttia luodakseen työkalupakin vaihtonapin. Napissa on data-drawer-toggle-attribuutti, joka käskee DwcAppLayoutia kuuntelemaan napsautustapahtumia, jotka tulevat tuosta komponentista työkalupakin tilan vaihtamiseksi.

## Koko leveys navigointipalkki {#full-width-navbar}

Oletuksena AppLayout renderöi ylä- ja alaosan näkymän ulkopuolella. Näkymän ulkopuolinen tila tarkoittaa, että ylä- ja alaosan sijainti siirtyy sopimaan avattavan työkalupakin viereen. Tämän tilan poistaminen käytöstä saa ylä- ja alaosan ottamaan koko käytettävissä olevan tilan ja siirtämään työkalupakin ylä- ja alaosaa sovittamaan ylä- ja alaosan.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

## Useita työkaluja {#multiple-toolbars}

Navigointipalkkiin voi lisätä rajattoman määrän työkaluja. `Toolbar` on vaakasuuntainen säilökomponentti, joka pitää sisällään joukon toimintanappeja, kuvakkeita tai muita ohjaimia. Lisätäksesi ylimääräisen työkalupakin, käytä yksinkertaisesti `addToHeader()`-menetelmää toisen `Toolbar`-komponentin lisäämiseksi.

Seuraava demo näyttää, kuinka käyttää kahta työkalupakkia. Ensimmäisessä on työkalupakin vaihtonappi ja sovelluksen nimi. Toinen työkalupakki sisältää toissijaisen navigointivalikon.

## Sticky työkalupakit {#sticky-toolbars}

Sticky-työkalupakki on työkalu, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alaspäin, mutta navigointipalkin korkeus on romahtanut, jotta sivun sisällölle on enemmän tilaa. Tavallisesti tämäntyyppisessä työkalupakissa on kiinteä navigointivalikko, joka on relevantti nykyiselle sivulle.

On mahdollista luoda sticky-työkalupakkeja käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -valintaa.

Kun `AppLayout.setHeaderReveal(true)` kutsutaan, yläosa on näkyvissä ensimmäisellä renderöinnillä ja piilotettu, kun käyttäjä alkaa vierittää alaspäin. Kun käyttäjä alkaa vierittää uudelleen ylöspäin, yläosa paljastuu.

CSS-mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista säätää, kuinka paljon yläosasta navigointipalkista piilotetaan.

## Mobiili navigointi asettelu {#mobile-navigation-layout}

Alhaalla oleva navigointipalkki voidaan käyttää tarjoamaan erilaista versiota navigoinnista sovelluksen alaosassa. Tämäntyyppinen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että työkalupakki on piilotettu seuraavassa demossa. AppLayout-widget tukee kolmea työkalupakin sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Kuten `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` tuetaan. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alaosa näkyy ensimmäisellä renderöinnillä ja piilotetaan, kun käyttäjä alkaa vierittää ylöspäin. Kun käyttäjä alkaa vierittää uudelleen alaspäin, alaosa paljastuu.

Oletuksena, kun näytön leveys on 800px tai vähemmän, työkalupakki vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että työkalupakki ponnahtaa sisällön alueelle päällekkäin. Katkaisupisteen voi määrittää käyttämällä `setDrawerBreakpoint()`-menetelmää ja katkaisupisteen on oltava voimassa oleva [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

## Työkalupakin työkalut {#drawer-utilities}

`AppLayout`-työkalupakin työkalut on suunniteltu integroituun navigointiin ja kontekstuaalisiin valikoihin pääsovelluksen asettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) komponentit tarjoavat joustavia, itsenäisiä liukupaneeleja, joita voidaan käyttää missä tahansa sovelluksessa lisäsisältöön, suodattimiin tai ilmoituksiin. Tämä osio keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin työkalupakko-ominaisuuksiin ja -työkaluihin.

### Työkalupakin katkaisupiste {#drawer-breakpoint}

Oletuksena, kun näytön leveys on 800px tai vähemmän, työkalupakki vaihdetaan popover-tilaan. Tämä on katkaisupiste. Popover-tila tarkoittaa, että työkalupakki ponnahtaa sisällön alueelle päällekkäin. Katkaisupisteen voi määrittää käyttämällä `setDrawerBreakpoint()`-menetelmää ja katkaisupisteen on oltava voimassa oleva media query.

Esimerkiksi seuraavassa näytteessä työkalupakin katkaisupiste on määritetty olevan 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Työkalupakin otsikko {#drawer-title}

`AppLayout`-komponentti tarjoaa `addToDrawerTitle()`-menetelmän mukautetun otsikon määrittämiseksi, joka näkyy työkalupakin yläosassa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Työkalupakin toiminnot {#drawer-actions}

`AppLayout`-komponentti mahdollistaa mukautettujen komponenttien, kuten nappien tai kuvakkeiden, sijoittamisen **työkalupakin yläosan toimintotilaan** käyttämällä `addToDrawerHeaderActions()` -menetelmää.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

On mahdollista siirtää useita komponentteja argumentteina:

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Profiili")
);
```

Työkalupakin toiminnot näkyvät **oikealla puolella** työkalupakin yläosassa.

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) -komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointityökalupakin näkyvyyden vaihtamiseen `AppLayout`:ssa. Se vastaa asiakaspään `<dwc-app-drawer-toggle>` -elementtiä ja on oletusarvoisesti tyylitelty toimimaan kuin perinteinen hampurilaisvalikon kuvake, tätä käytöstä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-luokkaa ja käyttää oletusarvoisesti "menu-2" kuvaketta Tabler-ikona-sarjasta. Se lisää automaattisesti `data-drawer-toggle` -attribuutin integroitumiseen asiakaspään työkalupakin käytökseen.

```java
// Ei tarvitse rekisteröidä tapahtumaa:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Työkalupakin vaihtaminen toimii heti—ei tarvetta manuaalisille tapahtumakuuntelijoille.
```
## Tyylitys {#styling}

<TableBuilder name="AppLayout" />
