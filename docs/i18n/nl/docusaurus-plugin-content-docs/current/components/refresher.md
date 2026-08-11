---
title: Refresher
sidebar_position: 101
description: >-
  Enable pull-to-refresh on scrollable areas with the Refresher component, with
  pull, release, and refreshing states and i18n labels.
_i18n_hash: 9bb531347032e46ccbb9e7fa28c403f8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh is een veelvoorkomend patroon in mobiele en tikvriendelijke interfaces, en de `Refresher` component brengt het naar scrollbare containers in webforJ. Terwijl gebruikers naar beneden swipen voorbij een configureerbare drempel, verandert het door visuele toestanden: `pull`, `release`, en `refreshing`, elk met een aanpasbaar pictogram en gelokaliseerde tekst. Het werkt goed samen met [`InfiniteScroll`](../components/infinitescroll) voor het opnieuw laden of resetten van inhoud via gebaar-gebaseerde invoer.

<!-- INTRO_END -->

## Instantiatie en internationalisatie {#instantiation-and-internationalization}

Voeg een `Refresher` toe door deze te instantiëren en een verversingsluisteraar te registreren. Wanneer de verversingsoperaties zijn voltooid, roep je `finish()` aan om de component weer in de inactieve staat te zetten.

:::info Hoe de `Refresher` te activeren
Om de `Refresher` te activeren, **klik en sleep naar beneden** vanaf de bovenkant van het scrollbare gebied. Terwijl deze gebaar bekend is op mobiel, is het minder gebruikelijk op desktop—zorg ervoor dat je vasthoudt en trekt met je muis.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

Deze benadering wordt vaak gebruikt om gepagineerde lijsten te verversen of de oneindige scroll-lading opnieuw te starten.

### Internationalisatie {#internationalization}

Elke statuslabel kan ook worden gelokaliseerd met behulp van het `RefresherI18n` object. De drie staten zijn:

- Pull: Initiële gebaar tekst (bijv. "Trek naar beneden om te verversen")
- Release: Drempel bereikt (bijv. "Laat los om te verversen")
- Refresh: Laadstatus (bijv. "Bezig met verversen")

Dit maakt meertalige ondersteuning en merkaanpassingen mogelijk indien nodig.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Pictogram aanpassing {#icon-customization}

Je kunt de gebruikte [`Icons`](../components/icon) voor de `pull`/`release` en `refreshing` fasen wijzigen met een vooraf gedefinieerde [`Icon`](../components/icon) of een [Pictogram-URL](../managing-resources/assets-protocols). Deze zijn nuttig wanneer je branding of een aangepaste animatie wilt toepassen.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Pull-gedrag configuratie {#pull-behavior-configuration}

### Drempel {#threshold}

Stel in hoe ver de gebruiker naar beneden moet trekken (in pixels) voordat de verversing wordt geactiveerd:

```java
refresher.setThreshold(80); // standaard: 80px
```

### Maximale drempel {#threshold-maximum}

Om de maximale trekafstand te definiëren, gebruik je de `setThresholdMax()` methode:

```java
refresher.setThresholdMax(160);
```

Deze drempels beheersen de gevoeligheid van de gebaren en de weerstandscurve.

## Statusbeheer {#state-management}

De `Refresher` component behoudt zijn eigen interne status en communiceert statuswijzigingen via gebeurtenissen. Wanneer een gebruiker naar beneden trekt voorbij de gedefinieerde drempel, geeft de `Refresher` een verversingsevent af waar je op kunt reageren door een `onRefresh()` luisteraar te registreren.

Binnen deze luisteraar wordt verwacht dat je de vereiste operatie uitvoert—zoals het ophalen van nieuwe gegevens of het resetten van een lijst—en dan expliciet aanroept:

```java
refresher.finish();
```
:::warning Ontbrekende `finish()`
Als je vergeet `finish()` aan te roepen, blijft de refresher in de laadstatus hangen.
:::

Je kunt ook programmatically de `Refresher` op elk moment uitschakelen om te voorkomen dat de gebruiker het verversingsgedrag activeert:

```java
refresher.setEnabled(false);
```

Dit is nuttig wanneer verversingen tijdelijk niet zijn toegestaan—bijvoorbeeld tijdens een laadscherm of terwijl een ander kritisch proces bezig is.

## Stijlen {#styling}

### Thema's {#themes}

De `Refresher` component ondersteunt meerdere thema's om visueel verschillende staten te onderscheiden of om de uitstraling en het gevoel van je app aan te passen. Thema's kunnen worden toegepast met behulp van de `setTheme()` methode.

Het volgende voorbeeld cycled door alle beschikbare thema's elke keer dat je trekt om te verversen, wat je een live preview geeft van hoe de `Refresher` eruitziet in verschillende thema's:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
