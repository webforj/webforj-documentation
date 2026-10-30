---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 4e36be1fd55d6c17c810ace6ef3f1fd8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout`-komponentti tarjoaa valmiin sivurakenteen, jossa on kiinteä ylä- ja alareuna, liukuva vetolaatikko ja vieritettävä sisältöalue. Yhdessä nämä osat kattavat hallintapaneelien, admin-paneelien ja useimpien moniosioisten käyttöliittymien asettelutarpeet.

<!-- INTRO_END -->

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusten asetteluiden rakentamisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Reagoiva suunnittelu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tummassa Tilassa</li>
</ul>

Se tarjoaa pään, jalan, vetolaatikon ja sisältöosion, jotka kaikki on rakennettu tehokkaasti yhteen mukautettavaan komponenttiin nopeaa mukautettavaa yhteistä sovelluslayoutia, kuten hallintapaneelia varten. Pää ja jalka ovat kiinteitä, vetolaatikko liukuu näkymä-alueen sisään ja ulos, ja sisältö on vieritettävissä.

Jokainen osio asettelusta on `Div`, joka voi sisältää minkä tahansa voimassa olevan webforJ-ohjaimen. Parhaan tuloksen saavuttamiseksi sovelluksen tulisi sisältää näkymämeta-tagi, joka sisältää viewport-fit=cover. Meta-tagit aiheuttavat näkymän koon sopeutuvan laitteen näyttöön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraava koodiesimerkki tuottaa sovelluksen, jossa on taitettava sivupalkki, joka sisältää logon ja välilehdet eri sisältövaihtoehdoille sekä pään. Esimerkki käyttää dwc-icon-button -webkomponenttia luodakseen vetolaatikon vaihto painiketta. Painikkeella on data-drawer-toggle-atribuutti, joka ohjaa DwcAppLayoutia kuuntelemaan tuosta komponentista tulevia napsautustapahtumia vaihtamaan vetolaatikon tilaa.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Täysileveys navigointipalkki {#full-width-navbar}

Oletuksena AppLayout renderoi pään ja jalan näkymättömään tilaan. Näkymättömän tilan tarkoittaa, että pään ja jalan sijainti siirtyy sopimaan avattavan vetolaatikon viereen. Tämän tilan poistaminen käytöstä johtaa siihen, että pää ja jalka vievät koko käytettävissä olevan tilan ja siirtävät vetolaatikon ylä- ja alapuolista sijaintia sopimaan pään ja jalan kanssa.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Useita työkaluja {#multiple-toolbars}

Navigointipalkissa ei ole rajoitusta työkaluja, joita voit lisätä. `Toolbar` on vaakasuuntainen säilökomponentti, joka sisältää joukon toimintopainikkeita, ikoneita tai muita ohjaimia. Lisätäksesi ylimääräisen työkalun, käytä yksinkertaisesti `addToHeader()`-menetelmää lisäämään toinen `Toolbar`-komponentti.

Seuraava esimerkki näyttää, kuinka käyttää kahta työkalua; ensimmäinen sisältää vetolaatikon vaihto painikkeen ja sovelluksen nimen. Toinen työkalupalkki sisältää toissijaisen navigointivalikon.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Sticky työkalut {#sticky-toolbars}

Sticky-työkalu on työkalu, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alas, mutta navigointipalkin korkeus on romahtanut, jotta enemmän tilaa olisi käytettävissä sivun sisällölle. Tämäntyyppinen työkalu sisältää yleensä kiinteän navigointivalikon, joka on relevantti nykyiselle sivulle.

Sticky-työkalujen luominen on mahdollista käyttämällä CSS:n mukautettua omaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` asetetaan, pää näkyy ensiksi, ja se piilotetaan, kun käyttäjä alkaa vierittää alas. Kun käyttäjä alkaa jälleen vierittää ylös, pää paljastuu.

CSS:n mukautetun omaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista hallita, kuinka paljon pään navigointipalkista piilotetaan.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiili navigointirakenne {#mobile-navigation-layout}

Alhaalla oleva navigointipalkki voidaan käyttää tarjoamaan erilaisen version navigoinnista sovelluksen alaosassa. Tällainen navigointi on erityisen suosittu mobiilisovelluksissa.

Huomaa, että vetolaatikko on piilotettu seuraavassa esimerkissä. AppLayout-widget tukee kolmea vetolaatikon sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Samoin kuin `AppLayout.setHeaderReveal()`, myös `AppLayout.setFooterReveal()` on tuettu. Kun `AppLayout.setFooterReveal(true)` kutsutaan, jalka näkyy ensiksi ja piilotetaan, kun käyttäjä alkaa vierittää ylös. Kun käyttäjä alkaa jälleen vierittää alas, jalka paljastuu.

Oletuksena, kun näytön leveys on 800px tai vähemmän, vetolaatikko vaihdetaan popover-tilaan. Tämä tunnetaan katkaisupisteenä. Popover-tila tarkoittaa, että vetolaatikko tulee sisällön alueen päälle peittämättömällä alueella. Katkaisupisteen konfiguroiminen on mahdollista käyttämällä `setDrawerBreakpoint()`-menetelmää ja katkaisupisteen on oltava voimassa oleva [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Vetolaatikon apuohjelmat {#drawer-utilities}

`AppLayout`-vetolaatikon apuohjelmat on suunniteltu integroitua navigointia ja konteksti-menuja varten pääasettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) komponentit tarjoavat joustavia, itsenäisiä liukuviin paneeleja, joita voidaan käyttää missä tahansa sovelluksessa lisäsisällölle, suodattimille tai ilmoituksille. Tämä osa keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin vetolaatikon ominaisuuksiin ja apuohjelmiin.

### Vetolaatikon katkaisupiste {#drawer-breakpoint}

Oletuksena, kun näytön leveys on 800px tai vähemmän, vetolaatikko vaihdetaan popover-tilaan. Tämä tunnetaan katkaisupisteenä. Popover-tila tarkoittaa, että vetolaatikko tulee sisällön alueen päälle peittämättömällä alueella. Katkaisupisteen konfiguroiminen on mahdollista käyttämällä `setDrawerBreakpoint()`-menetelmää ja katkaisupisteen on oltava voimassa oleva media query.

Esimerkiksi seuraavassa esimerkissä vetolaatikon katkaisupiste on konfiguroitu olemaan 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Vetolaatikon otsikko {#drawer-title}

`AppLayout`-komponentti tarjoaa `addToDrawerTitle()`-menetelmän mukautetun otsikon määrittelemiseksi, joka näytetään vetolaatikon yläreunassa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Vetolaatikon toiminnot {#drawer-actions}

`AppLayout`-komponentti sallii mukautettujen komponenttien, kuten painikkeiden tai ikoneiden sijoittamisen **vetolaatikon ylätason toimintojen alueelle** käyttäen `addToDrawerHeaderActions()`-menetelmää.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

Useiden komponenttien siirtäminen argumentteina on mahdollista:

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Profiili")
);
```

Vetolaatikon toiminnot näkyvät **oikea-aligned-alueella** vetolaatikon ylätasossa.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='name' label="dwc-icon-button" /> <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) -komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointivetolaatikon näkyvyyden vaihtamiseen `AppLayout`-ohjelmassa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiä ja on oletuksena tyylitetty käyttäytymään kuten perinteinen hampurilaisvalikko-ikoni, tätä käyttäytymistä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton` ja käyttää oletuksena "menu-2" -ikonia Tabler-ikonikannasta. Se soveltaa automaattisesti `data-drawer-toggle` -attribuuttia integroituaakseen asiakaspuolen vetolaatikon käyttäytymiseen.

```java
// Tapahtumarekisteröintiä ei tarvita:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Vetolaatikon vaihto toimii heti — ei tarvitse manuaalisia tapahtumankuuntelijoita.
```
## Tyylitys {#styling}

<TableBuilder name="AppLayout" />
