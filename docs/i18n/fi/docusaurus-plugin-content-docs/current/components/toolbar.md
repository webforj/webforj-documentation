---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 166b39dabe94c73ecf6310dfdc1ba626
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn keskeisiin toimintoihin ja navigointielementteihin. webforJ `Toolbar` -komponentti on vaakasuora säiliö, joka voi sisältää joukon toimintopainikkeita, kuvakkeita tai muita komponentteja. Se on hyvin soveltuva sivun ohjausten hallintaan ja tärkeiden toimintojen, kuten hakupalkin tai ilmoituspainikkeen, ylläpitoon.

<!-- INTRO_END -->

## Työkalupalkin sisällön organisointi {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saavutettavaan ja johdonmukaiseen asetteluun. Oletuksena se ottaa täyden leveyden vanhempaansa nähden ja tarjoaa neljä sijaintialuetta, tai _paikkaa_, komponenttien järjestämiseen:

- **Alku**: Sisältää yleensä <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> -painikkeen tai kotipainikkeen.
- **Otsikko**: Käytetään sovellusten nimille tai logoille.
- **Sisältö**: Erittäin huomiota herättäville toiminnoille, kuten haku tai navigointi.
- **Loppu**: Vähemmän käytetyt toiminnot, kuten käyttäjäprofiili tai apu.

Jokaisessa paikassa on menetelmä komponenttien lisäämiseksi: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraavassa demonstroidaan, kuinka lisätä `Toolbar` [AppLayout](./app-layout) -komponenttiin ja hyödyntää kaikkia tuettuja paikkoja tehokkaasti. Lisätietoja työkalupalkkien toteuttamisesta `AppLayout`-komponentin sisällä löytyy osoitteista [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java']}
/>

## Tiivistetty tila {#compact-mode}

Käytä `setCompact(true)` -menetelmää vähentääksesi tyhjää tilaa `Toolbar`-komponentin ympärillä. Tämä on hyödyllistä, kun sinun tarvitsee mahduttaa enemmän sisältöä näytölle, erityisesti sovelluksissa, joissa on päällekkäin olevia työkalupalkkeja tai rajoitettua tilaa. Työkalupalkki toimii silti samalla tavalla—vain korkeus on pienempi. Tämä tila on yleisesti käytössä yläosissa, sivupalkissa tai asetteluissa, joissa tila on tiukka.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` työkalupalkissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina meneillään oleville prosesseille, kuten tietojen lataamiselle, tiedostojen lataamiselle tai vaiheiden suorittamiselle prosessissa. Kun se sijoitetaan `Toolbar`-komponentin sisälle, `ProgressBar` asettuu siististi alareunaan, mikä tekee siitä huomaamattoman, mutta silti selvästi viestii käyttäjille edistymisestä.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, ilman asettelun häiritsemistä.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Tyylit {#styling}

### Teemat {#themes}

`Toolbar`-komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista räätälöintiä varten:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
