---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 99def78151a30c5c7fef7106b2efcb5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn keskeisiin toimintoihin ja navigointielementteihin. webforJ `Toolbar` komponentti on horisontaalinen säiliö, joka voi pitää sarjan toimintapainikkeita, kuvakkeita tai muita komponentteja. Se soveltuu hyvin sivun ohjausten hallintaan ja tärkeiden toimintojen, kuten hakupalkin tai ilmoituspainikkeen, sijoittamiseen.

<!-- INTRO_END -->

## Organizing toolbar content {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saatavilla olevaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se vie koko vanhemman elementin leveyden ja tarjoaa neljä sijoitusaluetta, tai _paikkaa_, komponenttien järjestämiseen:

- **Alku**: Sisältää yleensä <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> tai kotipainikkeen.
- **Otsikko**: Käytetään sovellusten nimille tai logoille.
- **Sisältö**: Suuri huomioarvoisten toimintojen, kuten haun tai navigoinnin, kohdalle.
- **Loppu**: Harvemmin käytettävät toiminnot, kuten käyttäjäprofiili tai apu.

Jokaisella paikalla on menetelmä komponenttien lisäämiseksi: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava demo näyttää, miten `Toolbar` lisätään [AppLayout](./app-layout) ja kuinka käyttää kaikkia tuettuja paikkoja tehokkaasti. 
Jos haluat lukea lisää työkalupalkkien toteuttamisesta `AppLayout`-rakenteessa, katso [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Compact mode {#compact-mode}

Käytä `setCompact(true)` vähentääksesi tyhjää tilaa `Toolbar` ympärillä. Tämä on hyödyllistä, kun sinun tarvitsee mahtua enemmän sisältöä näyttöön, erityisesti sovelluksissa, joissa on pinottuja työkalupalkkeja tai rajallista tilaa. Työkalupalkki käyttäytyy silti samalla tavalla—vain korkeus on pienempi. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkissa tai asetteluissa, joissa tila on tiukka.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` in toolbars {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina meneillään olevista prosesseista, kuten tietojen lataamisesta, tiedostojen lataamisesta tai vaiheiden suorittamisesta prosessissa. Kun se asetetaan `Toolbar`:in sisälle, `ProgressBar` asettuu siististi alemman reunan mukaan, tehden siitä huomaamattoman samalla, kun se selvästi viestittää käyttäjille edistymistä.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin häiritsemättä asettelua.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Styling {#styling}

### Themes {#themes}

`Toolbar` komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista mukauttamista varten:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
