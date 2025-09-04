---
title: Toolbar
sidebar_position: 145
_i18n_hash: 446d71b3e376810254bbbf6ffee43aa9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn ydintoimintoihin ja navigointielementteihin. webforJ `Toolbar` -komponentti on horisontaalinen säiliö, joka voi sisältää joukon toimintopainikkeita, kuvakkeita tai muita komponentteja. Se soveltuu hyvin sivuhallinnan ja tärkeiden toimintojen, kuten hakupalkin tai ilmoituspainikkeen, hallintaan.

## Työkalupalkin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saavutettavassa ja johdonmukaisessa asettelussa. Oletuksena se vie koko vanhempansa leveyden ja tarjoaa neljä sijoitusaluetta, tai _lohkoa_, komponenttien järjestämiseen:

- **Alku**: Yleensä sisältää <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> tai kotipainikkeen.
- **Otsikko**: Käytetään sovellusnimille tai logolle.
- **Sisältö**: Suurille huomiota vaativille toiminnoille, kuten haku tai navigointi.
- **Loppu**: Harvinaisemmat toiminnot, kuten käyttäjäprofiili tai apua.

Jokaisella lohkolla on menetelmä komponenttien lisäämiseen: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava demo näyttää, kuinka `Toolbar` lisätään [AppLayout](./app-layout) ja hyödynnetään kaikkia tuettuja lohkoja tehokkaasti. 
Lisää tietoa työkalupalkkien toteuttamisesta `AppLayout`:ssa, katso [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Kompakti tila {#compact-mode}

Käytä `setCompact(true)`, jotta voit vähentää tyhjää tilaa `Toolbar` ympärillä. Tämä on hyödyllistä, kun tarvitset enemmän sisältöä mahtumaan näytölle, erityisesti sovelluksissa, joissa on päällekkäisiä työkalupalkkeja tai rajallista tilaa. Työkalupalkki toimii silti samalla tavalla—vain korkeus on vähennetty. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkissa tai asetteluissa, joissa tila on tiukka.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` työkalupalkissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina käynnissä olevista prosesseista, kuten tietojen lataamisesta, tiedostojen lataamisesta tai vaiheiden täydentämisestä prosessissa. Kun se sijoitetaan sisään `Toolbar`, `ProgressBar` asettuu siististi alareunaan, mikä tekee siitä huomaamattoman samalla, kun se selkeästi viestii käyttäjille edistymisestä.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, häiritsemättä asettelua.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Toolbar` -komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista mukauttamista varten:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
