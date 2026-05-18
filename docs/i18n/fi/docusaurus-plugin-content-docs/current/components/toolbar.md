---
title: Toolbar
sidebar_position: 145
_i18n_hash: 54bfdf481b7e149762dc4544c192c6e6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Työkalupalkit tarjoavat käyttäjille nopean pääsyn ydin-toimintoihin ja navigointielementteihin. webforJ `Toolbar` komponentti on vaakasuuntaan oleva säiliö, joka voi sisältää joukon toimintopainikkeita, ikoneita tai muita komponentteja. Se sopii hyvin sivun hallintapainikkeiden hallintaan ja keskeisten toimintojen, kuten hakupalkin tai ilmoituspainikkeen, sijoittamiseen.

<!-- INTRO_END -->

## Työkalupalkin sisällön järjestäminen {#organizing-toolbar-content}

`Toolbar` järjestää olennaiset komponentit helposti saavutettavaan ja johdonmukaiseen asetteluun. Oletusarvoisesti se vie koko vanhemman elementin leveyden ja tarjoaa neljä sijoitusaluetta, tai _paikkaa_, komponenttien järjestämiseen:

- **Alku**: Yleensä sisältää <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> tai kotipainikkeen.
- **Otsikko**: Käytetään sovelluksen nimille tai logoille.
- **Sisältö**: Korkean huomion saavat toiminnot, kuten haku tai navigointi.
- **Loppu**: Harvemmin käytettävät toiminnot, kuten käyttäjäprofiili tai apu.

Jokaisella paikalla on menetelmä komponenttien lisäämiseen: `addToStart()`, `addToTitle()`, `addToContent()`, ja `addToEnd()`.

Seuraava demo näyttää, kuinka lisätä `Toolbar` [AppLayout](./app-layout) ja käyttää kaikkia tuettuja paikkoja tehokkaasti.
Lisätietoja työkalupalkkien toteuttamisesta `AppLayout`-sovelluksessa saa kohdista [Sticky toolbars](./app-layout#sticky-toolbars) ja [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/resources/static/css/toolbar/toolbar-slots-view.css',
]}
/>

## Kompakti tila {#compact-mode}

Käytä `setCompact(true)` pienentämään välistystä `Toolbar`-komponentin ympärillä. Tämä on hyödyllistä, kun tarvitset enemmän sisältöä mahtumaan ruudulle, erityisesti sovelluksissa, joissa on päällekkäisiä työkalupalkkeja tai rajallista tilaa. Työkalupalkki käyttäytyy edelleen samoin—vain korkeus on pienentynyt. Tätä tilaa käytetään yleisesti otsikoissa, sivupalkeissa tai asetteluissa, joissa tila on tiukka.

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

`ProgressBar` toimii visuaalisena indikaattorina käynnissä olevista prosesseista, kuten tietojen lataamisesta, tiedostojen lataamisesta tai vaiheiden suorittamisesta prosessissa. Kun se sijoitetaan `Toolbar`-komponenttiin, `ProgressBar` asettuu siististi alareunaan, tehden siitä huomaamattoman, mutta silti selkeästi viestittävän edistymisestä käyttäjille.

Voit yhdistää sen muihin komponentteihin työkalupalkissa, kuten painikkeisiin tai etiketteihin, häiritsemättä asettelua.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Toolbar` komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/Theme">seitsemän sisäänrakennettua teemaa</JavadocLink> nopeaa visuaalista mukauttamista varten:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='475px'
/>

<TableBuilder name="Toolbar" />
