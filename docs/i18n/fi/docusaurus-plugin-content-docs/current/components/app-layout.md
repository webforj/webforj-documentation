---
title: AppLayout
sidebar_position: 5
_i18n_hash: 07c685c4fce66e48d5a4e6660b7bc991
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` -komponentti tarjoaa valmiin sivurakenteen, jossa on kiinteä ylätunniste ja alatunniste, liukuva laatikko ja vieritettävä sisältöalue. Nämä osiot yhdessä kattavat hallintapaneelien, hallintasivujen ja useimpien moniosioisten käyttöliittymien asettelutarpeet.

<!-- INTRO_END -->

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusten asettelujen luomisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Reagoiva muotoilu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tummassa tilassa</li>
</ul>

Se tarjoaa ylätunnisteen, alatunnisteen, liukuvan laatikon ja sisältöalueen, kaikki rakennettuna reagoivaksi komponentiksi, joka voidaan helposti mukauttaa yleisten sovellusasetusten, kuten hallintapaneelin, nopeaan rakentamiseen. Ylätunniste ja alatunniste ovat kiinteitä, liukuva laatikko liikkuu näkymän sisällä ja sisältö on vieritettävää.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa kelvollista webforJ-ohjainta. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää näkymämeta-tag, joka sisältää viewport-fit=cover. Meta-tag saa näkymän täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraava koodiesimerkki tuottaa sovelluksen, jossa on taitettava sivupalkki, joka sisältää logon ja välilehtiä eri sisältövaihtoehdoille sekä ylätunnisteen. Esimerkki käyttää dwc-icon-button -web-komponenttia luodakseen liukuvapainikkeen. Painikkeessa on data-drawer-toggle -attribuutti, joka ohjaa DwcAppLayoutia kuuntelemaan tuosta komponentista tulevia klikkaustapahtumia liukuvan tilan vaihtamiseksi.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Täysimittainen navigointipalkki {#full-width-navbar}

Oletusarvoisesti AppLayout renderoi ylätunnisteen ja alatunnisteen näkymän ulkopuolelle. Näkymän ulkopuolinen tila tarkoittaa, että ylätunnisteen ja alatunnisteen paikka siirtyy sopimaan avattavan laatikon viereen. Tämän tilan poistaminen aiheuttaa ylätunnisteen ja alatunnisteen käyttävän koko käytettävissä olevaa tilaa ja siirtävän liukuvan laatikon ylä- ja alareunat sopimaan ylätunnisteen ja alatunnisteen kanssa.

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
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Useita työpöytiä {#multiple-toolbars}

Navigointipalkilla ei ole rajoituksia työpöytien määrälle, jotka voit lisätä. `Toolbar` on vaakasuuntainen säiliökomponentti, joka pitää sisällään joukon toimintapainikkeita, ikoneita tai muita ohjaimia. Lisätäksesi ylimääräisen työpöydän, käytä yksinkertaisesti `addToHeader()` -menetelmää lisätäksesi toisen `Toolbar`-komponentin.

Seuraava esimerkki näyttää, kuinka käyttää kahta työpöytää. Ensimmäinen sisältää liukuvalaatikon kytkinpainikkeen ja sovelluksen nimen. Toinen työpöytä sisältää toissijaisen navigointivalikon.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Kiinteät työpöydät {#sticky-toolbars}

Kiinteä työpöytä on työpöytä, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alas, mutta navigointipalkin korkeus on kutistunut, jotta sivun sisällölle saadaan enemmän tilaa. Tämäntyyppinen työpöytä sisältää yleensä kiinteän navigointivalikon, joka on relevantti nykyiselle sivulle.

On mahdollista luoda kiinteitä työpöytiä käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -valintaa.

Kun `AppLayout.setHeaderReveal(true)` -asetusta kutsutaan, ylätunniste on näkyvissä ensimmäisellä renderöinnillä, ja se piilotetaan, kun käyttäjä alkaa vierittää alaspäin. Kun käyttäjä alkaa vierittää ylös päin uudelleen, ylätunniste tulee näkyviin.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista hallita, kuinka paljon ylätunnisteen navigointipalkista piilotetaan.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiilinavigointiasettelu {#mobile-navigation-layout}

Alareunan navigointipalkkia voidaan käyttää tarjoamaan eri version navigoinnista sovelluksen alareunassa. Tämän tyyppinen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että liukuva laatikko on piilotettu seuraavassa esimerkissä. AppLayout-widget tukee kolmea liukuvan laatikon sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Kuten `AppLayout.setHeaderReveal()`, myös `AppLayout.setFooterReveal()` tuetaan. Kun `AppLayout.setFooterReveal(true)` -asetusta kutsutaan, alatunniste on näkyvissä ensimmäisellä renderöinnillä ja sitten piilotettu, kun käyttäjä alkaa vierittää ylös päin. Kun käyttäjä alkaa vierittää alaspäin jälleen, alatunniste tulee näkyviin.

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, liukuva laatikko vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että liukuva laatikko kohoaa sisältöalueen ylle päällekkäin. Katkaisupistettä voidaan konfiguroida käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisupisteen on oltava kelvollinen [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Liukuvan laatikon työkalut {#drawer-utilities}

`AppLayout` -liukuvan laatikon työkalut on suunniteltu integroituun navigointiin ja kontekstitietoisiin valikoihin pääsovelluksen asettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) -komponentit tarjoavat joustavia, riippumattomia liukuva paneeleja, joita voidaan käyttää missä tahansa sovelluksessa lisäsisältöön, suodattimiin tai ilmoituksiin. Tämä osa keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin liukuvan laatikon ominaisuuksiin ja työkaluihin.

### Liukuvan laatikon katkaisupiste {#drawer-breakpoint}

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, liukuva laatikko vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että liukuva laatikko kohoaa sisältöalueen ylle päällekkäin. Katkaisupistettä voidaan konfiguroida käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisupisteen on oltava kelvollinen media query.

Esimerkiksi seuraavassa esimerkissä liukuvan laatikon katkaisupistettä on konfiguroitu olemaan 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Liukuvan laatikon otsikko {#drawer-title}

`AppLayout` -komponentti tarjoaa `addToDrawerTitle()` -menetelmän, jonka avulla voidaan määrittää mukautettu otsikko, joka näytetään liukuvan laatikon ylätunnisteessa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Liukuvan laatikon toiminnot {#drawer-actions}

`AppLayout` -komponentti mahdollistaa mukautettujen komponenttien, kuten painikkeiden tai ikoneiden, sijoittamisen **liukuvan laatikon ylätunnisteen toiminnot** -alueeseen käyttäen `addToDrawerHeaderActions()` -menetelmää.

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

Liukuvan laatikon toiminnot näkyvät liukuvan laatikon ylätunnisteen **oikean reunan** osassa.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) -komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointilaudun näkyvyyden vaihtamiseen `AppLayout`-sovelluksessa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>` -elementtiä ja on oletusarvoisesti muotoiltu käyttäytymään perinteisen hampurilaisvalikon kuvakeen tavoin, tätä käyttäytymistä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-komponenttia ja käyttää oletusarvoisesti Tabler-ikonisarjan "menu-2" -ikonia. Se automaattisesti lisää `data-drawer-toggle` -attribuutin integroidakseen asiakaspuolen liukuvan laatikon käyttäytymisen.

```java
// Ei tarvita tapahtuman rekisteröintiä:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Liukuvan laatikon kytkin toimii suoraan - ei tarvita manuaalisia tapahtumakuuntelijoita.
```
## Muotoilu {#styling}

<TableBuilder name="AppLayout" />
