---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 46ea0f38e27d84ef944e7a26fd0c5666
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` on kattava responsiivinen asettelu-komponentti, joka tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöosaston. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu sisään ja ulos näkymästä, ja sisältö on vieritettävissä.

Tätä komponenttia voidaan käyttää yleisten sovellusasetteluiden, kuten kojelaudan, rakentamiseen.

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusasetteluiden rakentamisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Responsiivinen muotoilu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tummassa Tilassa</li>
</ul>

Se tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöosaston, jotka kaikki on rakennettu responsiiviseksi komponentiksi, jota voidaan helposti mukauttaa yleisten sovellusasetteluiden, kuten kojelaudan, nopeaksi rakentamiseksi. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu sisään ja ulos näkymästä, ja sisältö on vieritettävissä.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa kelpaavaa webforJ-ohjainta. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää näkymämeta-tunniste, joka sisältää viewport-fit=cover. Meta-tunniste saa näkymän skaalautumaan täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yhteenveto {#overview}

Seuraava koodinäyte tuottaa sovelluksen, jossa on taitettava sivupalkki, joka sisältää logon ja välilehdet eri sisältövaihtoehtoja varten sekä yläpalkin. Esittely käyttää dwc-icon-button web-komponenttia laatikon vaihdon painikkeen luomiseksi. Painikkeella on data-drawer-toggle-attribuutti, joka käskee DwcAppLayoutia kuuntelemaan tuosta komponentista tulevia napsautustapahtumia laatikon tilan vaihtamiseksi.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Kokonaisleveyden yläpalkki {#full-width-navbar}

Oletusarvoisesti AppLayout renderöi yläpalkin ja alatunnisteen näkymättömällä tilassa. Näkymättömän tilan tarkoittaa, että yläpalkin ja alatunnisteen asento siirtyy sopimaan avautuvan laatikon viereen. Tämän tilan poistaminen käytöstä aiheuttaa yläpalkin ja alatunnisteen ottavan koko käytettävissä olevan tilan ja siirtävän laatikon ylä- ja alaosat sopeutuakseen yläpalkkiin ja alatunnisteeseen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/> 

## Useita työkaluja {#multiple-toolbars}

Yläpalkissa ei ole rajaa sille määrälle työkaluja, jotka voit lisätä. `Toolbar` on vaakasuuntainen säilökomponentti, joka pitää sisällään joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia. Lisääksesi toisen työkalun, käytä yksinkertaisesti `addToHeader()` -menetelmää toisen `Toolbar`-komponentin lisäämiseksi.

Seuraava esittely näyttää, kuinka käyttää kahta työkalua. Ensimmäinen sisältää laatikon vaihto painikkeen ja sovelluksen nimen. Toinen työkalu sisältää toissijaisen navigointimenun.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/> 

## Tarttuvat työkalut {#sticky-toolbars}

Tarttuva työkalu on työkalu, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alaspäin, mutta yläpalkin korkeus on kutistettu, jotta sivun sisällölle on saatavana enemmän tilaa. Yleensä tämäntyyppinen työkalu sisältää kiinteän navigointivalikon, joka on relevantti nykyiselle sivulle.

On mahdollista luoda tarttuvia työkaluja käyttäen CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` asetetaan, yläpalkki tulee näkyville ensimmäisellä renderöinnillä ja piilotetaan, kun käyttäjä alkaa vierittää alas. Kun käyttäjä alkaa vierittää ylös, yläpalkki paljastuu taas.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista hallita, kuinka paljon yläpalkin navbarista piilotetaan.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/> 

## Mobiilinavigointi-asettelu {#mobile-navigation-layout}

Alapalkkia voidaan käyttää tarjoamaan erilainen versio navigoinnista sovelluksen alaosassa. Tällainen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että laatikkoa on piilotettu seuraavassa esittelyssä. AppLayout-widget tukee kolmea laatikkopositiota: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Samanlainen kuin `AppLayout.setHeaderReveal()`, myös `AppLayout.setFooterReveal()` on tuettu. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alatunniste tulee näkyviin ensimmäisellä renderöinnillä ja piilotetaan, kun käyttäjä alkaa vierittää ylös. Kun käyttäjä alkaa vierittää alas, alatunniste paljastuu taas.

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihtuu popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että laatikko ponnahtaa sisältöalueen päälle peittävällä kerroksella. Katkaisupistettä on mahdollista konfiguroida käyttämällä `setDrawerBreakpoint()` -menetelmää ja katkaisupisteen on oltava kelpaava [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Laatikkoapuohjelmat {#drawer-utilities}

`AppLayout` -laatikkoapuohjelmat on suunniteltu integroidulle navigoinnille ja kontekstuaalisille valikoille pääasettelu-sovelluksessa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) -komponentit tarjoavat joustavia, itsenäisiä liukuovia, joita voidaan käyttää missä tahansa sovelluksessasi lisäsisällön, suodattimien tai ilmoitusten varten. Tässä osiossa keskitytään AppLayoutin tarjoamiin sisäänrakennettuihin laatikko-ominaisuuksiin ja -apuohjelmiin.

### Laatikon katkaisupiste {#drawer-breakpoint}

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihtuu popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että laatikko ponnahtaa sisältöalueen päälle peittävällä kerroksella. Katkaisupisteen konfiguroiminen on mahdollista käyttämällä `setDrawerBreakpoint()` -menetelmää ja katkaisupisteen on oltava kelpaava media query.

Esimerkiksi seuraavassa näytteessä laatikon katkaisupisteeksi on konfiguroitu 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Laatikon otsikko {#drawer-title}

`AppLayout` -komponentti tarjoaa `addToDrawerTitle()` -menetelmän mukautetun otsikon määrittämiseksi, joka näytetään laatikon yläpalkissa. 

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Laatikon toiminnot {#drawer-actions}

`AppLayout` -komponentti mahdollistaa mukautettujen komponenttien, kuten painikkeiden tai kuvakkeiden, sijoittamisen **laatikon yläpalkin toimintojen alueeseen** käyttäen `addToDrawerHeaderActions()` -menetelmää.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

On mahdollista välittää useita komponentteja argumentteina:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profiili")
);
```

Laatikon toiminnot näkyvät **oikealle kohdistetussa** laatikon yläpalkin osassa.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointilaadikon näkyvyyden vaihtamiseen `AppLayout`-sovelluksessa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiä ja on oletuksena tyylitelty käyttäytymään kuin perinteinen hampurilaisvalikkokuvake, tätä käyttäytymistä voidaan mukauttaa.

### Yhteenveto {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-luokkaa ja käyttää oletuksena Tabler-ikkunakokoelman "menu-2" -kuvaketta. Se lisää automaattisesti `data-drawer-toggle`-attribuutin integroituakseen asiakaspuolen laatikon käyttäytymiseen.

```java
// Tapahtuman rekisteröinti ei ole tarpeen:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Laatikon vaihto toimii valmiiksi – ei tarvita manuaalisia tapahtumankäsittelijöitä.
```
## Tyylitys {#styling}

<TableBuilder name="AppLayout" />
