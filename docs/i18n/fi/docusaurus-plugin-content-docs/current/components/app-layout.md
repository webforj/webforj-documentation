---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 7f842a66a5bcca7efe7ee894a0b001b0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

`AppLayout` on kattava responsiivinen asettelukomponentti, joka tarjoaa yläpalkin, alatason, laatikon ja sisältöalueen. Yläpalkki ja alataso ovat kiinteitä, laatikko liukuu sisään ja ulos näkymästä, ja sisältö on vieritettävissä.

Tätä komponenttia voidaan käyttää yleisten sovellusasetteluiden, kuten ohjauspaneelin, rakentamiseen.

## Ominaisuudet {#features}

webforJ App Layout on komponentti, joka mahdollistaa yleisten sovellusasetteluiden rakentamisen.

<ul>
    <li>Helppo käyttää ja mukauttaa</li>
    <li>Responsiivinen muotoilu</li>
    <li>Useita asetteluvalintoja</li>
    <li>Toimii webforJ Tummassa Tilassa</li>
</ul>

Se tarjoaa yläpalkin, alatason, laatikon ja sisältöalueen, jotka kaikki on rakennettu responsiiviseksi komponentiksi, jota voidaan helposti mukauttaa yleisten sovellusasetteluiden, kuten ohjauspaneelin, nopeaan rakentamiseen. Yläpalkki ja alataso ovat kiinteitä, laatikko liukuu sisään ja ulos näkymästä, ja sisältö on vieritettävissä.

Jokainen asettelun osa on `Div`, joka voi sisältää mitä tahansa voimassa olevaa webforJ-ohjainta. Parhaiden tulosten saavuttamiseksi sovelluksen tulisi sisältää viewport meta -tagi, joka sisältää viewport-fit=cover. Meta-tagi aiheuttaa, että näkymä skaalataan täyttämään laitteen näyttö.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Yleiskatsaus {#overview}

Seuraava koodiesimerkki johtaa sovellukseen, jossa on taitettava sivupalkki, joka sisältää logon ja välilehdet erilaisille sisältövalinnoille sekä yläpalkin. Demo käyttää dwc-icon-button web-komponenttia luodakseen laatikon kytkinpainikkeen. Painikkeella on data-drawer-toggle-attribuutti, joka ohjaa DwcAppLayoutia kuuntelemaan klikkaustapahtumia, jotka tulevat kyseisestä komponentista, kytkeäksesi laatikon tilan.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Täysin leveä navigointipalkki {#full-width-navbar}

Oletusarvoisesti AppLayout renderoi yläpalkin ja alatason off-screen-tilassa. Off-screen-tila tarkoittaa, että yläpalkin ja alatason sijainti siirretään sopimaan avattavan laatikon viereen. Tämän tilan poistaminen käytöstä saa yläpalkin ja alatason ottamaan koko käytettävissä olevan tilan ja siirtämään laatikon ylä- ja alaosien sijaintia sopimaan yläpalkin ja alatason kanssa.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Useat työkalupalkit {#multiple-toolbars}

Navigointipalkissa ei ole rajoitusta siihen, kuinka monta työkalupalkkia voit lisätä. `Toolbar` on vaakasuuntainen säilökomponentti, joka sisältää joukon toimintonäppäimiä, kuvakkeita tai muita ohjaimia. Lisätäksesi ylimääräisen työkalupalkin, käytä yksinkertaisesti `addToHeader()`-metodia, jotta lisätään toinen `Toolbar`-komponentti.

Seuraava demo näyttää, miten käyttää kahta työkalupalkkia. Ensimmäinen sisältää laatikon kytkinpainikkeen ja sovelluksen nimen. Toinen työkalupalkki sisältää toissijaisen navigointivalikon.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Kiinteät työkalupalkit {#sticky-toolbars}

Kiinteä työkalupalkki on työkalupalkki, joka pysyy näkyvissä sivun yläosassa, kun käyttäjä vierittää alaspäin, mutta navigointipalkin korkeus kutistuu, jotta sivun sisällölle on enemmän tilaa. Tällaisessa työkalupalkissa on yleensä kiinteä navigointivalikko, joka on relevantti nykyiselle sivulle.

Kiinteiden työkalupalkkien luominen on mahdollista käyttämällä CSS:n mukautettua ominaisuutta `--dwc-app-layout-header-collapse-height` ja `AppLayout.setHeaderReveal()`-vaihtoehtoa.

Kun `AppLayout.setHeaderReveal(true)` kutsutaan, yläpalkki näkyy ensimmäisellä renderoinnilla ja piilotetaan, kun käyttäjä alkaa vierittää alaspäin. Kun käyttäjä alkaa vierittää ylös taas, yläpalkki paljastetaan.

CSS:n mukautetun ominaisuuden `--dwc-app-layout-header-collapse-height` avulla on mahdollista hallita, kuinka paljon yläpalkin navigointipalkista piilotetaan.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobiilinavigointiasettelu {#mobile-navigation-layout}

Alhaalla oleva navigointipalkki voidaan käyttää tarjoamaan erilaisen version navigoinnista sovelluksen alaosassa. Tällainen navigointi on erityisen suosittua mobiilisovelluksissa.

Huomaa, että laatikkoa ei ole näkyvissä seuraavassa demossa. AppLayout-widget tukee kolmea laatikon sijaintia: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` ja `DrawerPlacement.HIDDEN`.

Samoin kuin `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` tuetaan. Kun `AppLayout.setFooterReveal(true)` kutsutaan, alatason näkyy ensimmäisellä renderoinnilla ja piilotetaan, kun käyttäjä alkaa vierittää ylös. Kun käyttäjä alkaa vierittää alaspäin taas, alatason paljastuu.

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että laatikko poksahtaa sisällön alueen päälle peittäen sen. Katkaisupisteen konfiguroiminen on mahdollista käyttämällä `setDrawerBreakpoint()`-metodia, ja katkaisupisteen on oltava voimassa oleva [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Laatikon työkalut {#drawer-utilities}

`AppLayout` laatikon työkalut on suunniteltu integroituun navigointiin ja kontekstuaalisiin valikoihin pääsovellusasetuksessa, kun taas itsenäiset [`Drawer`](https://docs.webforj.com/docs/components/drawer) -komponentit tarjoavat joustavia, itsenäisiä liukupaneeleja, joita voidaan käyttää missä tahansa sovelluksessasi lisäsisällölle, suodattimille tai ilmoituksille. Tämä osa keskittyy AppLayoutin tarjoamiin sisäänrakennettuihin laatikkotoimintoihin ja työkaluihin.

### Laatikon katkaisupiste {#drawer-breakpoint}

Oletusarvoisesti, kun näytön leveys on 800px tai vähemmän, laatikko vaihdetaan popover-tilaan. Tätä kutsutaan katkaisupisteeksi. Popover-tila tarkoittaa, että laatikko poksahtaa sisällön alueen päälle peittäen sen. Katkaisupisteen konfiguroiminen on mahdollista käyttämällä `setDrawerBreakpoint()`-metodia, ja katkaisupisteen on oltava voimassa oleva media query.

Esimerkiksi seuraavassa esimerkissä laatikon katkaisupiste on määritetty olemaan 500px tai vähemmän.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Laatikon otsikko {#drawer-title}

`AppLayout` komponentti tarjoaa `addToDrawerTitle()`-metodin, joka määrittelee mukautetun otsikon näytettäväksi laatikon yläpalkissa.

```java
layout.addToDrawerTitle(new Div("Valikko"));
```

### Laatikon toiminnot {#drawer-actions}

`AppLayout` komponentti mahdollistaa mukautettujen komponenttien, kuten painikkeiden tai kuvakkeiden, sijoittamisen **laatikon yläpalkin toiminnot -alueeseen** käyttäen `addToDrawerHeaderActions()` -metodia.

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

Laatikon toiminnot näkyvät **oikealla puolella** laatikon yläpalkissa.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

[`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) komponentti on palvelinpuolen webforJ-luokka, joka edustaa painiketta, joka käytetään navigointilaadikon näkyvyyden kytkemiseen `AppLayout`-komponentissa. Se karttuu asiakaspuolen `<dwc-app-drawer-toggle>`-elementtiin ja on tyylitelty käyttäytymään oletusarvoisesti kuin perinteinen hampurilaissämpyläkuvake, tätä käyttäytymistä voidaan mukauttaa.

### Yleiskatsaus {#overview-1}

`AppDrawerToggle` laajentaa `IconButton` ja käyttää oletusarvoisesti "menu-2" kuvaketta Tabler-kuvakesarjasta. Se soveltaa automaattisesti `data-drawer-toggle` attribuuttia integraatiota varten asiakaspuolen laatikkokäyttäytymiseen.

```java
// Ei tapahtumarekisteröintiä tarvita:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Laatikon kytkin toimii suoraan - ei manuaalisia tapahtumakuuntelijoita tarvitse.
```

## Tyylittely {#styling}

<TableBuilder name="AppLayout" />
