---
title: AppLayout
sidebar_position: 5
_i18n_hash: 0aea09dee535e578082dd6df642503d4
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout`-komponentti tarjoaa valmiin sivurakenteen, jossa on kiinteä yläpalkki ja alatunniste, sivupaneeli, joka liukuu sisään ja ulos, sekä vieritettävä sisältöalue. Nämä osiot kattavat hallintapaneelien, admin-paneelien ja useimpien moniosaisen käyttöliittymän asettelutarpeet.

<!-- INTRO_END -->

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusten asetteluiden rakentamisen.

<ul>
    <li>Helppo käyttää ja muokata</li>
    <li>Reagoiva suunnittelu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tummassa tilassa</li>
</ul>

Se tarjoaa yläpalkin, alatunnisteen, sivupaneelin ja sisältöosion, kaikki rakennettuna reagoivaan komponenttiin, jota voidaan helposti mukauttaa yleisten sovellusasetteluiden, kuten hallintapaneelin, nopeaksi rakentamiseksi. Yläpalkki ja alatunniste ovat kiinteitä, sivupaneeli liukuu näkymäruudun sisään ja ulos, ja sisältö on vieritettävissä.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa voimassa olevaa webforJ-ohjainta. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää näkymämeta-tunnus, joka sisältää viewport-fit=cover. Meta-tunnus aiheuttaa näkymäruudun skaalaamisen täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yhteenveto {#overview}

Seuraava koodinäyte tuottaa sovelluksen, jossa on kokoontaitettava sivupaneeli, joka sisältää logon ja välilehdet eri sisältövalintoja varten sekä yläpalkin. Demo käyttää dwc-icon-button-web-komponenttia luodakseen sivupaneelin kytkinpainikkeen. Painikkeella on data-drawer-toggle-tunnus, joka ohjeistaa DwcAppLayoutia kuuntelemaan klikkaustapahtumia tuosta komponentista vaihtaakseen sivupaneelin tilan.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Koko leveä navigointipalkki {#full-width-navbar}

Oletuksena `AppLayout` renderöi yläpalkin ja alatunnisteen pois näytöltä tila. Pois näytöltä tila tarkoittaa, että yläpalkin ja alatunnisteen paikka siirretään sopimaan avoimen sivupaneelin viereen. Tämän tilan poistaminen käytöstä saa yläpalkin ja alatunnisteen ottamaan koko käytettävissä olevan tilan ja siirtää sivupaneelin ylä- ja alareunat sopimaan yläpalkin ja alatunnisteen kanssa.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Useita työkaluja {#multiple-toolbars}

Navigointipalkilla ei ole rajoitusta työkaluerojen määrälle, joita voit lisätä. `Toolbar` on vaaka suuntainen säiliökomponentti, joka sisältää joukon toimintanappiensa, kuvakkeiden tai muiden ohjainten. Lisätäksesi lisätyökalupalkin, käytä yksinkertaisesti `addToHeader()`-metodia lisäämään toinen `Toolbar`-komponentti.

Seuraava demo osoittaa, kuinka käyttää kahta työkalupalkkia. Ensimmäinen niistä sisältää sivupaneelin kytkentäpainikkeen ja sovelluksen nimen. Toinen työkalupalkki sisältää toissijaisen navigointivalikon.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Kiinteät työkalupalkit {#sticky-toolbars}

Kiinteä työkalupalkki on työkalupalkki, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alaspäin, mutta navigointipalkin korkeus on supistettu, jotta enemmän tilaa on käytettävissä sivun sisällölle. Tämäntyyppinen työkalupalkki sisältää yleensä kiinteän navigointivalikon, joka on relevantti nykyiselle sivulle.

On mahdollista luoda kiinteitä työkalupalkkeja käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` asetetaan, yläpalkki näkyy ensimmäisessä renderöinnissä ja sitten piilotetaan, kun käyttäjä alkaa vierittää alaspäin. Kun käyttäjä alkaa vierittää ylöspäin, yläpalkki paljastuu.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla voidaan hallita sitä, kuinka paljon yläpalkin navigointipalkista piilotetaan.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiiliversion navigointi {#mobile-navigation-layout}

Alapalkkia voidaan käyttää tarjoamaan erilaisen version navigoinnista sovelluksen alareunassa. Tämäntyyppinen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että sivupaneeli on piilotettu seuraavassa demossa. `AppLayout`-widget tukee kolmea sivupaneelin sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Aivan kuten `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` on myös tuettu. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alatunniste näkyy ensimmäisessä renderöinnissä ja piilotetaan, kun käyttäjä alkaa vierittää ylöspäin. Kun käyttäjä alkaa vierittää alaspäin uudelleen, alatunniste paljastuu.

Oletuksena, kun näytön leveys on 800px tai vähemmän, sivupaneeli vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että sivupaneeli tulee sisällön alueen päälle peittäen sen. Katkaisupiste voidaan konfiguroida käyttämällä `setDrawerBreakpoint()`-metodia ja katkaisupisteen on oltava voimassa oleva [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Sivupaneelin työkalut {#drawer-utilities}

`AppLayout`-sivupaneelin työkalut on suunniteltu integroituun navigointiin ja kontekstuaalisiin valikoihin pääasiallisessa sovelluksen asettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) komponentit tarjoavat joustavia, itsenäisiä liuku- jaelementtejä, joita voidaan käyttää missä tahansa sovelluksessa lisäsisältöä, suodattimia tai ilmoituksia varten. Tämä osa keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin sivupaneelin ominaisuuksiin ja työkaluihin.

### Sivupaneelin katkaisupiste {#drawer-breakpoint}

Oletuksena, kun näytön leveys on 800px tai vähemmän, sivupaneeli vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että sivupaneeli tulee sisällön alueen päälle peittäen sen. Katkaisupiste voidaan konfiguroida käyttämällä `setDrawerBreakpoint()`-metodia ja katkaisupisteen on oltava voimassa oleva media query.

Esimerkiksi seuraavassa näytteessä sivupaneelin katkaisupiste on konfiguroitu 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Sivupaneelin otsikko {#drawer-title}

`AppLayout`-komponentti tarjoaa `addToDrawerTitle()`-metodin mukautetun otsikon määrittämiseksi, joka näytetään sivupaneelin yläpalkissa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Sivupaneelin toiminnot {#drawer-actions}

`AppLayout`-komponentti sallii mukautettujen komponenttien, kuten painike- tai kuvake-elementtien, sijoittamisen **sivupaneelin yläpalkin toimintosiin** `addToDrawerHeaderActions()`-metodin avulla.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

On mahdollista siirtää useita komponentteja argumentoina:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profiili")
);
```

Sivupaneelin toiminnot näkyvät **oikealle alustetussa osassa** sivupaneelin yläpalkissa.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointipaneelin näkyvyyden vaihtamiseen `AppLayout`:issa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>` -elementtiä ja sitä on oletusarvoisesti tyylitelty käyttäytymään perinteisen hampurilaisvalikon kuvakkeena, tätä käyttäytymistä voidaan mukauttaa.

### Yhteenveto {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-luokkaa ja käyttää oletusarvoisesti Tabler-ikonisarjan "menu-2" kuvaketta. Se soveltaa automaattisesti `data-drawer-toggle`-attribuuttia integraatioon asiakaspuolen sivupaneeli käyttäytymisen kanssa.

```java
// Tapahtumien rekisteröinti ei ole tarpeen:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Sivupaneelin kytkin toimii suoraan - ei tarvita manuaalisia tapahtumankuuntelijoita.
```
## Tyylit {#styling}

<TableBuilder name="AppLayout" />
