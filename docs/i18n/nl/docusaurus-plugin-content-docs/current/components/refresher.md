---
title: Refresher
sidebar_position: 101
_i18n_hash: de00fad980f74bdd18544409408de0b8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

De `Refresher` component in webforJ maakt een pull-to-refresh interactie mogelijk binnen scrollbare containers—ideaal voor dynamisch gegevens laden in mobiele of tap-vriendelijke interfaces. Terwijl gebruikers naar beneden vegen voorbij een configureerbare drempel, doorloopt de refresher visuele toestanden: `pull`, `release`, en `refreshing`. Elke staat presenteert een aanpasbaar pictogram en gelokaliseerde tekst om duidelijk feedback te geven.

Je kunt `Refresher` gebruiken in combinatie met componenten zoals [`InfiniteScroll`](../components/infinitescroll) om inhoud opnieuw te laden of de status te resetten via eenvoudige gebaar-gebaseerde invoer. De component is volledig configureerbaar wat betreft interactiegedrag, uiterlijk, lokalisatie en integratie met de rest van je UI.

## Instantie en internationalisering {#instantiation-and-internationalization}

Voeg een `Refresher` toe door het te instantiëren en een refresh listener te registreren. Wanneer de refreshoperaties zijn voltooid, roep je `finish()` aan om de component opnieuw in zijn idle-toestand te zetten.

:::info Hoe de `Refresher` te activeren
Om de `Refresher` te activeren, **klik en sleep naar beneden** vanaf de bovenkant van het scrollbare gebied. Terwijl deze gebaar bekend is op mobiel, is het minder gebruikelijk op desktop—zorg ervoor dat je vasthoudt en trekt met je muis.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Deze benadering wordt vaak gebruikt om gepagineerde lijsten te vernieuwen of het laden van oneindige scroll te herstarten.

### Internationalisering {#internationalization}

Elke statuslabel kan ook gelokaliseerd worden met behulp van het `RefresherI18n` object. De drie staten zijn:

- Pull: Initiële gebaartekst (bijv. "Trek naar beneden om te vernieuwen")
- Release: Drempel bereikt (bijv. "Laat los om te vernieuwen")
- Refresh: Laadtoestand (bijv. "Bezig met vernieuwen")

Dit maakt meertalige ondersteuning en branding aanpassingen mogelijk indien nodig.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pictogram aanpassing {#icon-customization}

Je kunt de [`Icons`](../components/icon) die worden gebruikt voor de `pull`/`release` en `refreshing` fasen veranderen met behulp van een vooraf gedefinieerde [`Icon`](../components/icon) of een [Icon URL](../managing-resources/assets-protocols). Deze zijn nuttig wanneer je branding of een aangepaste animatie wilt toepassen.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull gedrag configuratie {#pull-behavior-configuration}

### Drempel {#threshold}

Stel in hoe ver de gebruiker naar beneden moet trekken (in pixels) voordat de refresh wordt geactiveerd:

```java
refresher.setThreshold(80); // standaard: 80px
```

### Maximale drempel {#threshold-maximum}

Om de maximale trekafstand die is toegestaan te definiëren, gebruik de `setThresholdMax()` methode:

```java
refresher.setThresholdMax(160);
```

Deze drempels regelen de gevoeligheid van de gebaren en de weerstandscurve.

## Statusbeheer {#state-management}

De `Refresher` component behoudt zijn eigen interne status en communiceert statusveranderingen via evenementen. Wanneer een gebruiker naar beneden trekt voorbij de gedefinieerde drempel, zendt de `Refresher` een refresh-evenement uit waar je op kunt reageren door een `onRefresh()` listener te registreren.

Binnen deze listener wordt verwacht dat je de benodigde operatie uitvoert—zoals het ophalen van nieuwe gegevens of het resetten van een lijst—en dan expliciet aanroept:

```java
refresher.finish();
```
:::warning Mislopende `finish()`
Als je vergeet `finish()` aan te roepen, blijft de refresher in de laadtoestand voor onbepaalde tijd.
:::

Je kunt ook de `Refresher` programmatechnisch op elk moment uitschakelen om te voorkomen dat de gebruiker de refresh functie activeert:

```java
refresher.setEnabled(false);
```

Dit is nuttig wanneer refreshes tijdelijk niet moeten worden toegestaan—bijvoorbeeld tijdens een laadscreen of terwijl een ander kritisch proces draait.

## Stijling {#styling}

### Thema's {#themes}

De `Refresher` component ondersteunt meerdere thema's om visueel verschillende staten te onderscheiden of om te passen bij de look en feel van je app. Thema's kunnen worden toegepast met behulp van de `setTheme()` methode.

Het volgende voorbeeld cycled door alle beschikbare thema's elke keer dat je trekt om te vernieuwen, waarmee je een live voorbeeld krijgt van hoe de `Refresher` eruit ziet in verschillende thema's:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
