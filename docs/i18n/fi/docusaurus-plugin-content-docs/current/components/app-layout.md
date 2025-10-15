---
title: AppLayout
sidebar_position: 5
_i18n_hash: 7bc8b2a8bfc772644cf2107199615515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` on kattava responsiivinen asettelukomponentti, joka tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöalueen. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu näkymän sisään ja ulos, ja sisältö on vieritettävää.

Tätä komponenttia voidaan käyttää yleisten sovellusten asettelujen, kuten hallintapaneelin, rakentamiseen.

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusasetusten rakentamisen.

<ul>
    <li>Helppokäyttöinen ja mukautettavissa</li>
    <li>Responsiivinen muotoilu</li>
    <li>Useita asetteluoptioita</li>
    <li>Toimii webforJ Tummassa Tilassa</li>
</ul>

Se tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöalueen, jotka kaikki on rakennettu responsiiviseen komponenttiin, jota voidaan helposti mukauttaa yleisten sovellusten, kuten hallintapaneelin, nopeassa rakentamisessa. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu näkymän sisään ja ulos, ja sisältö on vieritettävää.

Jokainen osa asettelusta on `Div`, joka voi sisältää mitä tahansa kelvollista webforJ-ohjainta. Parhaan tuloksen saavuttamiseksi sovelluksen tulisi sisältää näkymämeta-tag, joka sisältää viewport-fit=cover. Meta-tag saa näkymän skaalautumaan täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yhteenveto {#overview}

Seuraava koodiesimerkki tuottaa sovelluksen, jossa on taitettava sivupalkki, joka sisältää logon ja välilehtiä eri sisältövaihtoehdoille sekä yläpalkin. Esimerkki käyttää dwc-icon-button web-komponenttia laatikon kytkentäpainikkeen luomiseen. Painikkeessa on data-drawer-toggle-attribuutti, joka ohjeistaa DwcAppLayoutia kuuntelemaan napsautustapahtumia tuosta komponentista vaihtamaan laatikon tilaa.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Kokonainen leveä navigointipalkki {#full-width-navbar}

Oletusarvoisesti AppLayout renderoi yläpalkin ja alatunnisteen näkymän ulkopuolella. Näkymän ulkopuolella tarkoittaa, että yläpalkin ja alatunnisteen sijainti siirtyy sopimaan avattuun laatikkoon. Tämän tilan poistaminen käytöstä saa yläpalkin ja alatunnisteen viemään koko saatavilla olevan tilan ja siirtämään laatikon ylä- ja alaosaa sopimaan yläpalkin ja alatunnisteen kanssa.

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

Navigointipalkissa ei ole rajaa työkaluilla, joita voit lisätä. `Toolbar` on vaakasuuntainen säiliökomponentti, joka sisältää joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia. Lisätäksesi toisen työkalupalkin, käytä yksinkertaisesti `addToHeader()`-menetelmää lisätäksesi toisen `Toolbar`-komponentin.

Seuraava esimerkki näyttää kuinka käyttää kahta työkalupalkkia. Ensimmäinen sisältää laatikon kytkentäpainikkeen ja sovelluksen osoitteen. Toinen työkalupalkki sisältää toissijaisen navigointivalikon.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Kiinteät työkalupalkit {#sticky-toolbars}

Kiinteä työkalupalkki on työkalupalkki, joka pysyy näkyvänä sivun yläosassa, kun käyttäjä vierittää alas, mutta navigointipalkin korkeus on romahtanut, jotta sivun sisällölle on saatavilla enemmän tilaa. Tällaisessa työkalupalkissa on yleensä kiinteä navigointivalikko, joka liittyy nykyiseen sivuun.

Voit luoda kiinteitä työkalupalkkeja käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()`-vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` asetetaan, yläpalkki on näkyvissä ensimmäisellä renderöinnillä, ja se piilotetaan, kun käyttäjä alkaa vierittää alas. Kun käyttäjä alkaa vierittää ylöspäin, yläpalkki paljastuu.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla voit hallita, kuinka paljon yläpalkin navigointipalkista piilotetaan.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiiliviimeistely {#mobile-navigation-layout}

Alatunnisteen navigointipalkkia voidaan käyttää tarjoamaan erilainen versio navigoinnista sovelluksen alareunassa. Tällainen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että laatikko on piilotettu seuraavassa demossa. AppLayout-widget tukee kolmea laatikon sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Samoin kuin `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` on tuettu. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alatunniste on näkyvissä ensimmäisellä renderöinnillä ja sitten piilotetaan, kun käyttäjä alkaa vierittää ylöspäin. Kun käyttäjä alkaa vierittää alas, alatunniste paljastuu.

Oletusarvoisesti, kun näytön leveys on 800 pikseliä tai vähemmän, laatikko vaihtuu popover-tilaan. Tätä kutsutaan katkaisualueeksi. Popover-tila tarkoittaa, että laatikko pomppaa sisällön alueelle peitteen kanssa. Katkaisualueen voi konfiguroida käyttämällä `setDrawerBreakpoint()`-menetelmää, ja katkaisualueen on oltava voimassa oleva [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Laatikkotyökalut {#drawer-utilities}

`AppLayout` laatikkotyökalut on suunniteltu integroituun navigointiin ja kontekstuaalisiin valikoihin pääsovelluksen asettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) komponentit tarjoavat joustavia, itsenäisiä liukupaneeleja, joita voidaan käyttää missä tahansa sovelluksessasi lisäsisällön, suodattimien tai ilmoitusten esittämiseen. Tämä osio keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin laatikkotoimintoihin ja -työkaluihin.

### Laatikkokatkaisu {#drawer-breakpoint}

Oletusarvoisesti, kun näytön leveys on 800 pikseliä tai vähemmän, laatikko vaihtuu popover-tilaan. Tätä kutsutaan katkaisualueeksi. Popover-tila tarkoittaa, että laatikko pomppaa sisällön alueelle peitteen kanssa. Katkaisualueen voi konfiguroida käyttämällä `setDrawerBreakpoint()`-menetelmää, ja katkaisualueen on oltava voimassa oleva media query.

Esimerkiksi seuraavassa esimerkissä laatikkokatkaisu on määritetty 500 pikseliksi tai vähemmäksi.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Laatikkonimi {#drawer-title}

`AppLayout` komponentti tarjoaa `addToDrawerTitle()`-menetelmän määrittelemään mukautetun nimen näytettäväksi laatikon yläosassa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Laatikkotoimet {#drawer-actions}

`AppLayout` komponentti sallii mukautettujen komponenttien, kuten painikkeiden tai kuvakkeiden, sijoittamisen **laatikon yläpalkin toimintojen alueeseen** käyttämällä `addToDrawerHeaderActions()`-menetelmää.

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

Laatikkotoimet näkyvät laatikon yläpalkin **oikealla puolella**.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointilaatikon näkyvyyden vaihtamiseen `AppLayout`-sovelluksessa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiä ja on oletuksena tyylitelty käyttäytymään perinteisen hampurilaisvalikon kuvakkeen tapaan, tätä käyttäytymistä voidaan mukauttaa.

### Yhteenveto {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-luokkaa ja käyttää oletuksena Tabler-kuvakesarjasta "menu-2" -kuvaketta. Se soveltaa automaattisesti `data-drawer-toggle`-attribuuttia integroidakseen asiakaspuolen laatikkokäyttäytymiseen.

```java
// Ei vaadi tapahtuman rekisteröintiä:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Laatikkokytkin toimii suoraan—ei tarvitse manuaalisia tapahtumakuuntelijoita.
```
## Tyylit {#styling}

<TableBuilder name="AppLayout" />
