---
title: AppLayout
sidebar_position: 5
_i18n_hash: e6da714fff4ce713ceb5b486b8ab0026
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` on kattava responsiivinen asettelukomponentti, joka tarjoaa yläpalkin, alatunnisteen, laatikon ja sisällön osion. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu näkökenttään ja sieltä pois, ja sisältö on vieritettävissä.

Tätä komponenttia voidaan käyttää yleisten sovellusasetteluiden, kuten hallintapaneelin, rakentamiseen.

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusasetteluiden rakentamisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Responsiivinen muotoilu</li>
    <li>Useita asetteluvalintoja</li>
    <li>Toimii webforJ Tummassa Tilassa</li>
</ul>

Se tarjoaa yläpalkin, alatunnisteen, laatikon ja sisältöosion, jotka kaikki on rakennettu responsiiviseksi komponentiksi, jota voidaan helposti mukauttaa yleisten sovellusasetteluiden, kuten hallintapaneelin, nopeaksi rakentamiseksi. Yläpalkki ja alatunniste ovat kiinteitä, laatikko liukuu näkökenttään ja sieltä pois, ja sisältö on vieritettävissä.

Jokainen asettelun osa on `Div`, joka voi sisältää minkä tahansa kelvollisen webforJ-ohjaimen. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää näkökenttämeta-tag, joka sisältää viewport-fit=cover. Meta-tag saa näkökentän skaalautumaan täyttämään laitteen näyttö.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraava koodiesimerkki tuottaa sovelluksen, jossa on suljettava sivupalkki, joka sisältää logon ja välilehdet eri sisältövaihtoehtoja varten, sekä yläpalkin. Esimerkissämme käytetään dwc-icon-button -webkomponenttia laatikkonapituskytkimen luomiseen. Nappulassa on data-drawer-toggle-attribuutti, joka käskee DwcAppLayoutin kuuntelemaan napsautustapahtumia, jotka tulevat kyseisestä komponentista laatikon tilan vaihtamiseksi.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Kokonainen leveys navigointipalkki {#full-width-navbar}

Oletusarvoisesti AppLayout renderöi yläpalkin ja alatunnisteen off-screen-tilassa. Off-screen-tila tarkoittaa, että yläpalkin ja alatunnisteen sijainti siirretään sopimaan avattavan laatikon viereen. Tämän tilan poistaminen käytöstä saa yläpalkin ja alatunnisteen ottamaan koko käytettävissä olevan tilan ja siirtämään laatikon ylä- ja alareunan sopimaan yläpalkin ja alatunnisteen kanssa.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Useita työkaluja {#multiple-toolbars}

Navigointipalkilla ei ole rajoitusta lisättävien työkalujen määrälle. `Toolbar` on vaakasuuntainen säiliökomponentti, joka pitää sisällään joukon toimintanappuloita, kuvakkeita tai muita ohjaimia. Toisen työkalun lisääminen tapahtuu yksinkertaisesti käyttämällä `addToHeader()`-menetelmää lisäämään toinen `Toolbar`-komponentti.

Seuraavassa esimerkissä näytetään, kuinka käyttää kahta työkalua. Ensimmäinen sisältää laatikon kytkinnapin ja sovelluksen nimen. Toinen työkalu sisältää toissijaisen navigointivalikon.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Kiinteät työkalut {#sticky-toolbars}

Kiinteä työkalu on työkalu, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alaspäin, mutta navigointipalkin korkeus on supistunut, jotta enemmän tilaa on saatavilla sivun sisällölle. Tällainen työkalu sisältää yleensä kiinteän navigointivalikon, joka liittyy nykyiseen sivuun.

On mahdollista luoda kiinteitä työkaluja käyttämällä CSS: n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()` -vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)`-asetusta kutsutaan, yläpalkki on näkyvissä ensimmäisellä renderoinnilla, ja se piilotetaan, kun käyttäjä alkaa vierittää alaspäin. Kun käyttäjä alkaa vierittää ylöspäin, yläpalkki paljastuu jälleen.

CSS: n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista määritellä, kuinka paljon yläpalkin navigointisivusta piilotetaan.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobiili navigoinnin asettelu {#mobile-navigation-layout}

Alareunan navigointipalkkia voidaan käyttää tarjoamaan erilainen versio navigoinnista sovelluksen alareunassa. Tällainen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että laatikko on piilotettu seuraavassa esimerkissä. AppLayout-widget tukee kolmea laatikkosijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, ja `DrawerPlacement.HIDDEN`.

Samanlaisena kuin `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` tuetaan. Kun `AppLayout.setFooterReveal(true)` -asetusta kutsutaan, alatunniste on näkyvissä ensimmäisellä renderoinnilla ja piilotetaan, kun käyttäjä alkaa vierittää ylöspäin. Kun käyttäjä alkaa vierittää alaspäin uudelleen, alatunniste paljastuu.

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihdetaan popover-tilaan. Tätä kutsutaan "katkoksi". Popover-tila tarkoittaa, että laatikko hypähtää sisällön alueen päälle, jossa on päällekkäisyys. Katkosta voidaan määrittää käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisu on oltava kelvollinen [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
/>

## Laatikon työkalut {#drawer-utilities}

`AppLayout` laatikon työkalut on suunniteltu integroidun navigoinnin ja kontekstuaalisten valikoiden tarjoamiseksi pääsovelluksen asettelussa, samalla kun itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) -komponentit tarjoavat joustavia, itsenäisiä liukupaneeleja, joita voidaan käyttää missä tahansa sovelluksessasi lisäsisällön, suodattimien tai ilmoitusten näyttämiseen. Tämä osa keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin laatikkotoimintoihin ja -työkaluihin.

### Laatikon katkaisu {#drawer-breakpoint}

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihdetaan popover-tilaan. Tätä kutsutaan "katkoksi". Popover-tila tarkoittaa, että laatikko hypähtää sisällön alueen päälle, jossa on päällekkäisyys. Katkosta voidaan määrittää käyttämällä `setDrawerBreakpoint()` -menetelmää, ja katkaisu on oltava kelvollinen media query.

Esimerkiksi seuraavassa esimerkissä laatikon katkaisu on määritetty olevan 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Laatikon otsikko {#drawer-title}

`AppLayout` komponentti tarjoaa `addToDrawerTitle()`-menetelmän määrittääkseen mukautetun otsikon, joka näytetään laatikon yläosassa. 

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Laatikon toiminnot {#drawer-actions}

`AppLayout` komponentti mahdollistaa mukautettujen komponenttien, kuten nappeja tai kuvakkeita, sijoittamisen **laatikon yläosan toiminnot -alueelle** käyttäen `addToDrawerHeaderActions()` -menetelmää.

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

Laatikon toiminnot näkyvät **oikealla puolella** laatikon yläosassa.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, jota käytetään navigointilaadikon näkyvyyden vaihtamiseen `AppLayout`-komponentissa. Se vastaa asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiä ja on oletuksena muotoiltu käyttäytymään kuin perinteinen hamburger-valikkokuvake, tätä käyttäytymistä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton`-luokkaa ja käyttää oletuksena "menu-2"-kuvaketta Tabler-ikonikokoelmasta. Se soveltaa automaattisesti `data-drawer-toggle`-attribuuttia integroituakseen asiakaspuolen laatikkokäyttäytymiseen.

```java
// Ei tarvitse rekisteröidä tapahtumaa:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Laatikkokytkin toimii heti—ei tarvitse manuaalisia tapahtumakuuntelijoita.
```
## Muotoilu {#styling}

<TableBuilder name="AppLayout" />
