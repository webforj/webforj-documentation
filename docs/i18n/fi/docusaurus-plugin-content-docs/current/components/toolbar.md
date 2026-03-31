---
title: Toolbar
sidebar_position: 145
_i18n_hash: e3329a54fd8feccd96e15e1b19c3c97d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn keskeisiin toimintoihin ja navigointielementteihin. webforJ `Toolbar` -komponentti on vaakasuora kontti, joka voi sisältää joukon toimintopainikkeita, kuvakkeita tai muita komponentteja. Se sopii hyvin sivun ohjausten hallintaan ja avainfunktioiden, kuten hakupalkin tai ilmoituspainikkeen, sijoittamiseen.

<!-- INTRO_END -->

## Työkalupalkin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saavutettavaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se vie koko leveytensä vanhempielementistään ja tarjoaa neljä sijoitusaluetta tai _slotia_, joihin komponentteja voi järjestää:

- **Alku**: Yleensä sisältää <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> -painikkeen tai kotipainikkeen.
- **Otsikko**: Käytetään sovellusten nimille tai logolle.
- **Sisältö**: Korkean huomion toiminnoille, kuten hakuun tai navigointiin.
- **Loppu**: Vähemmän toistuville toiminnoille, kuten käyttäjäprofiilille tai avulle.

Jokaisella slotilla on menetelmä komponenttien lisäämiseksi: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava esimerkki näyttää, kuinka lisätä `Toolbar` [AppLayout](./app-layout) -komponenttiin ja hyödyntää kaikkia tukemia slotteja tehokkaasti. Lisätietoja työkalupalkkien toteuttamisesta `AppLayout`-komponentissa löytyy kohdista [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Kompakti tila {#compact-mode}

Käytä `setCompact(true)` vähentääksesi tyhjää tilaa `Toolbar`-komponentin ympärillä. Tämä on hyödyllistä, kun tarvitset enemmän sisältöä mahtumaan näyttöön, erityisesti sovelluksissa, joissa on päällekkäisiä työkaluja tai rajoitettua tilaa. Työkalupalkki toimii edelleen samalla tavalla—vain korkeus on vähennetty. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkissa tai asetteluissa, joissa tila on tiukkaa.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` työkalupalkissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina meneillään olevista prosesseista, kuten datan lataamisesta, tiedostojen lataamisesta tai vaiheiden täydentämisestä prosessissa. Kun se sijoitetaan `Toolbar`-komponentin sisälle, `ProgressBar` kohdistuu siististi alareunaan, mikä tekee siitä huomaamattoman samalla kun se välittää käyttäjille selvästi edistystä.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, ilman asettelun häiritsemistä.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Muotoilu {#styling}

### Teemat {#themes}

`Toolbar`-komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän valmiiksi rakennettua teemaa</JavadocLink> nopeaa visuaalista mukauttamista varten:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
