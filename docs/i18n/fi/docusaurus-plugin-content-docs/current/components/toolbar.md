---
title: Toolbar
sidebar_position: 145
_i18n_hash: a0f2d1a3d39ff0d195a5150ea6130710
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalurivit tarjoavat käyttäjille nopean pääsyn ydintoimintoihin ja navigointielementteihin. webforJ `Toolbar` -komponentti on vaakasuora kontti, joka voi sisältää joukon toimintopainikkeita, kuvakkeita tai muita komponentteja. Se sopii hyvin sivun hallintakontrollien hallintaan ja keskeisten toimintojen, kuten hakupalkin tai ilmoituspainikkeen, sijoittamiseen.

<!-- INTRO_END -->

## Työkalurivin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saavutettavaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se vie koko tyypin vanhemman elementtinsä leveyden ja tarjoaa neljä sijoitusaluetta tai _slotia_ komponenttien järjestämistä varten:

- **Alku**: Yleensä sisältää <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> -painikkeen tai kotipainikkeen.
- **Otsikko**: Käytetään sovelluksen nimille tai logoille.
- **Sisältö**: Korkean huomion toimintoja, kuten haku tai navigointi.
- **Loppu**: Harvemmin käytettyjä toimintoja, kuten käyttäjäprofiili tai apua.

Jokaisella slotilla on menetelmä komponenttien lisäämiseksi: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava esimerkki osoittaa, miten voit lisätä `Toolbar`-komponentin [AppLayout](./app-layout) -sovellukseen ja hyödyntää kaikkia tuettuja slotteja tehokkaasti. Lisätietoja työkalurivien toteuttamisesta `AppLayout`-sovelluksessa saat lukemalla [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
cssURL='/css/toolbar/toolbar-slots-view.css'
height='300px'
/>

## Tiivistetty tila {#compact-mode}

Käytä `setCompact(true)` pienentääksesi väliä `Toolbar`-komponentin ympärillä. Tämä on hyödyllistä, kun sinun on sovitettavissa enemmän sisältöä näyttöön, erityisesti sovelluksissa, joissa on kerrostettuja työkaluja tai rajoitettua tilaa. Työkalurivi toimii edelleen samalla tavalla—vain korkeus on pienennetty. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkissa tai asetteluissa, joissa tila on tiukka.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` työkaluissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina käynnissä olevista prosesseista, kuten tietojen lataamisesta, tiedostojen lataamisesta tai vaiheiden suorittamisesta prosessissa. Kun se sijoitetaan `Toolbar`-komponentin sisään, `ProgressBar` kohdistuu kauniisti alareunaan, jolloin se ei häiritse, mutta viestittää silti käyttäjille edistymisestä.

Voit yhdistää sen muihin komponentteihin työkalurivissä, kuten painikkeisiin tai etiketteihin, ilman, että asettelu häiriintyy.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Tyylit {#styling}

### Teemat {#themes}

`Toolbar`-komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink>, jotka mahdollistavat nopean visuaalisen mukauttamisen:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
