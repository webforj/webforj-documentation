---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 8dcb4d5bcecce36e656de87218bd3359
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn ydintoimiin ja navigointielementteihin. webforJ `Toolbar` -komponentti on vaakasuuntainen säiliö, joka voi pitää sisällään joukon toimintapainikkeita, kuvakkeita tai muita komponentteja. Se soveltuu hyvin sivun ohjaamiseen ja keskeisten toimintojen, kuten hakupalkin tai ilmoituspainikkeen, hallintaan.

<!-- INTRO_END -->

## Työkalupalkin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saatavilla olevaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se käyttää koko vanhemman elementin leveyttä ja tarjoaa neljä sijoitusaluetta, tai _slotia_, komponenttien järjestämiseksi:

- **Alku**: Yleensä sisältää <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> -komponentin tai kotipainikkeen.
- **Otsikko**: Käytetään sovelluksen nimille tai logoille.
- **Sisältö**: Korkean huomion toiminnoille, kuten haulle tai navigoinnille.
- **Loppu**: Harvinaisemmille toimille, kuten käyttäjäprofiilille tai avustukselle.

Jokaisessa slotissa on menetelmä komponenttien lisäämiseen: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava demo näyttää, kuinka `Toolbar` voidaan lisätä [AppLayout](./app-layout) -komponenttiin ja käyttää kaikkia tuettuja slotteja tehokkaasti. Lisätietoja työkalupalkkien toteuttamisesta `AppLayout`-komponentissa löytyy [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/frontend/css/toolbar/toolbar-slots-view.css',
]}
/>

## Kompakti tila {#compact-mode}

Käytä `setCompact(true)` -metodia vähentääksesi pehmustetta `Toolbar`-komponentin ympäriltä. Tämä on hyödyllistä, kun tarvitset enemmän sisältöä mahtumaan näyttöön, erityisesti sovelluksissa, joissa on pinoiltuja työkalupalkkeja tai rajoitettua tilaa. Työkalupalkki käyttäytyy edelleen samalla tavalla—ainoastaan korkeus on vähennetty. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkkien tai asettelujen yhteydessä, joissa tila on tiukkaa.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` työkalupalkkeissa {#progressbar-in-toolbars}

`ProgressBar` toimii visuaalisena indikaattorina meneillään oleville prosesseille, kuten tietojen lataamiselle, tiedostojen lataamiselle tai vaiheiden suorittamiselle työssä. Kun se sijoitetaan `Toolbar`-komponentin sisään, `ProgressBar` asettuu siististi alas reunoille, tehden siitä huomaamattoman kuitenkaan viestittämättä edistymistä käyttäjille.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, häiritsemättä asettelua.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Toolbar`-komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista mukauttamista varten:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
