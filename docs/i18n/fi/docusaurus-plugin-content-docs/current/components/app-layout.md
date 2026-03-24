---
title: AppLayout
sidebar_position: 5
_i18n_hash: 8b9351e865e2651e84f0ae16ef5efc21
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout`-komponentti tarjoaa valmiin sivurakenteen kiinteällä yläpalkilla ja alatunnisteella, liukuvalle laatikolle, joka avautuu ja sulkeutuu, sekä vieritettävälle sisältöalueelle. Nämä osiot kattavat hallintapaneelien, hallintapaneelien ja useimpien moniosioisten käyttöliittymien asettelutarpeet.

<!-- INTRO_END -->

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusten asettelujen rakentamisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Responsiivinen muotoilu</li>
    <li>Useita asettelu vaihtoehtoja</li>
    <li>Toimii webforJ Tumma tila -toiminnon kanssa</li>
</ul>

Se tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöosaston, jotka kaikki on rakennettu responsiiviseen komponenttiin, jota voidaan helposti mukauttaa yleisten sovellusten asettelujen, kuten hallintapaneelien, nopeaan rakentamiseen. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu näkymän sisälle ja ulos, ja sisältö on vieritettävää.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa kelvollista webforJ-ohjainta. Parhaita tuloksia varten sovelluksen tulisi sisältää näkymämeta-tunniste, joka sisältää viewport-fit=cover. Meta-tunniste saa näkymän muunnettavaksi täyttämään laitteen näytön.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraavan koodiesimerkin tuloksena on sovellus, jossa on taitettava sivupalkki, joka sisältää logon ja välilehdet eri sisältövaihtoehdoille sekä yläpalkin. Demo käyttää dwc-icon-button-web-komponenttia luodakseen laatikon vaihtonapin. Nappi sisältää data-drawer-toggle -attribuutin, joka ohjaa DwcAppLayoutin kuuntelemaan kyseisestä komponentista tulevia napsautustapahtumia, jotta laatikon tila vaihtuu.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Täysleveä navigointipalkki {#full-width-navbar}

Oletuksena AppLayout renderöi yläpalkin ja alatunnisteen off-screen-tilassa. Off-screen-tila tarkoittaa, että yläpalkin ja alatunnisteen sijaintia siirretään sopimaan avattavan laatikon viereen. Tämän tilan poistaminen käytöstä aiheuttaa yläpalkin ja alatunnisteen täyttävän koko käytettävissä olevan tilan ja siirtämään laatikon ylä- ja alareunat sopimaan yläpalkin ja alatunnisteen kanssa.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Useita työkaluja {#multiple-toolbars}

Navigointipalkilla ei ole rajoituksia työkaluja, joita voit lisätä. `Toolbar` on vaakasuuntainen säilytysosa, joka sisältää joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia. Lisätäksesi ylimääräisen työkalun, käytä vain `addToHeader()` -menetelmää lisätäksesi toinen `Toolbar`-komponentti.

Seuraava demo näyttää, miten käyttää kahta työkalua. Ensimmäinen sisältää laatikon vaihtonapin ja sovelluksen nimen. Toinen työkalu sisältää toisen tason valikkorakenteen.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Liikkuvat työkalut {#sticky-toolbars}

Kiinteä työkalu on työkalu, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alas, mutta navigointipalkin korkeus romahtaa, jotta sivun sisällölle saadaan enemmän tilaa. Yleensä tällaisessa työkalussa on kiinteä navigointivalikko, joka liittyy nykyiseen sivuun.

On mahdollista luoda liikuteltavia työkaluja käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` on asetettu, yläpalkki on näkyvissä ensimmäisessä renderöinnissä ja sitten piilotettu, kun käyttäjä alkaa vierittää alas. Kun käyttäjä alkaa vierittää ylös, yläpalkki paljastuu jälleen.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla voidaan hallita, kuinka paljon yläpalkin navigointipalkista piilotetaan.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobiilinavigaatioasettelu {#mobile-navigation-layout}

Alareunan navigointipalkkia voidaan käyttää tarjoamaan erilainen versio navigoinnista sovelluksen alareunassa. Tämän tyyppinen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että laatikko on piilotettu seuraavassa demossa. AppLayout-widget tukee kolmea laatikon sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Samoin kuin `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` on tuettu. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alatunniste on näkyvissä ensimmäisessä renderöinnissä ja sitten piilotettu, kun käyttäjä alkaa vierittää ylös. Kun käyttäjä alkaa vierittää alas jälleen, alatunniste paljastuu.

Oletuksena, kun näytön leveys on 800 px tai vähemmän, laatikko siirtyy popover-tilaan. Tämä tunnetaan katkaisupisteenä. Popover-tila tarkoittaa, että laatikko ilmestyy sisällön alueen päälle ylikalusteella. Katkaisupisteet voidaan määrittää käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisupisteen on oltava kelvollinen [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Laatikkotyökalut {#drawer-utilities}

`AppLayout`-laatikon työkalut on suunniteltu integroiduksi navigointiin ja kontekstuaalisiin valikoihin pääsovelluksen asettelussa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) -komponentit tarjoavat joustavia, itsenäisiä liuku-paneloita, joita voidaan käyttää missä tahansa sovelluksessa lisäsisältöön, suodattimiin tai ilmoituksiin. Tämä osio keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin laatikoiden ominaisuuksiin ja työkaluihin.

### Laatikon katkaisupiste {#drawer-breakpoint}

Oletuksena, kun näytön leveys on 800 px tai vähemmän, laatikko siirtyy popover-tilaan. Tämä tunnetaan katkaisupisteenä. Popover-tila tarkoittaa, että laatikko ilmestyy sisällön alueen päälle ylikalusteella. Katkaisupiste voidaan konfiguroida käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisupisteen on oltava kelvollinen media query.

Esimerkiksi seuraavassa esimerkissä laatikon katkaisupiste on määritetty 500 px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Laatikon otsikko {#drawer-title}

`AppLayout`-komponentti tarjoaa `addToDrawerTitle()` -menetelmän mukautetun otsikon määrittämiseksi, joka näytetään laatikon otsikossa. 

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Laatikon toiminnot {#drawer-actions}

`AppLayout`-komponentti mahdollistaa mukautettujen komponenttien, kuten painikkeiden tai kuvakkeiden, sijoittamisen **laatikon yläpalkkiin** käyttäen `addToDrawerHeaderActions()` -menetelmää.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

Useita komponentteja voidaan välittää argumentteina:

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Profiili")
);
```

Laatikon toiminnot näkyvät **oikealla puolella** laatikon yläpalkissa.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html)-komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointilaatikon näkyvyyden vaihtamiseen `AppLayout`:ssa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiä ja on oletuksena tyylitelty käyttäytymään kuin perinteinen hampurilaisvalikkokuvake, tätä käyttäytymistä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-komponenttia ja käyttää oletuksena "menu-2" -kuvaketta Tabler-kuvastoista. Se lisää automaattisesti `data-drawer-toggle` -attribuutin integroitumaan asiakaspuolen laatikon käyttäytymiseen.

```java
// Ei tarvita tapahtuman rekisteröintiä:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Laatikkon vaihto toimii suoraan—ei tarvita manuaalisia tapahtumakuuntelijoita.
```
## Tyylitys {#styling}

<TableBuilder name="AppLayout" />
