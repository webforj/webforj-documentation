---
title: Toolbar
sidebar_position: 145
_i18n_hash: 171c46f92903112a08194d130d89f2c7
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn ydintoimintoihin ja navigaatiotaelementteihin. webforJ `Toolbar` -komponentti on vaakasuuntainen säiliö, joka voi pitää setin toimintopainikkeita, kuvakkeita tai muita komponentteja. Se soveltuu hyvin sivun hallintojen hallintaan ja avaintoimintojen, kuten hakupalkin tai ilmoituspainikkeen, pitämiseen.

## Työkalupalkin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saatavilla olevaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se vie koko sen vanhemman elementin leveyden ja tarjoaa neljä sijoitusaluetta tai _paikkaa_ komponenttien järjestämiseen:

- **Alku**: Sisältää yleensä <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> -painikkeen tai kotipainikkeen.
- **Otsikko**: Käytetään sovellusten nimille tai logoille.
- **Sisältö**: Korkean huomion herättäville toiminnoille, kuten haku tai navigointi.
- **Loppu**: Harvemmin käytettäville toiminnoille, kuten käyttäjäprofiili tai apua.

Jokaisessa paikassa on menetelmiä komponenttien lisäämiseen: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava demo näyttää, kuinka lisätä `Toolbar` [AppLayout:iin](./app-layout) ja käyttää kaikkia tuettuja paikkoja tehokkaasti. Lisätietoja työkalupalkkien toteuttamisesta `AppLayout:issa`, katso [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Kompakti tila {#compact-mode}

Käytä `setCompact(true)` vähentääksesi väliä `Toolbar`:n ympärillä. Tämä on hyödyllistä, kun haluat mahtua enemmän sisältöä näyttöön, erityisesti sovelluksissa, joissa on pinottuja työkalupalkkeja tai rajoitetusti tilaa. Työkalupalkki käyttäytyy edelleen samalla tavalla—vain korkeus on vähennetty. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkkeissa tai asetteluissa, joissa tila on tiukka.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` työkalupalkissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina käynnissä olevista prosesseista, kuten tietojen lataamisesta, tiedostojen lähettämisestä tai vaiheiden suorittamisesta prosessissa. Kun se sijoitetaan `Toolbar`:iin, `ProgressBar` asettuu siististi alareunaan, mikä tekee siitä huomaamattoman samalla, kun se viestii edistymisestä käyttäjille selkeästi.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, häiritsemättä asettelua.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Toolbar` -komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista muokkausta varten:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
