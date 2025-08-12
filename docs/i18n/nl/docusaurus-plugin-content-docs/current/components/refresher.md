---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

De `Refresher` component in webforJ maakt een pull-to-refresh interactie mogelijk binnen scrollbare containers—ideaal voor het dynamisch laden van gegevens op mobiele of touch-vriendelijke interfaces. Terwijl gebruikers naar beneden vegen voorbij een configureerbare drempel, doorloopt de refresher visuele toestanden: `pull`, `release` en `refreshing`. Elke toestand presenteert een aanpasbaar pictogram en gelokaliseerde tekst om duidelijk feedback te communiceren.

Je kunt `Refresher` gebruiken in combinatie met componenten zoals [`InfiniteScroll`](../components/infinitescroll) om inhoud opnieuw te laden of de staat te resetten via eenvoudige gebaargebaseerde invoer. De component is volledig configureerbaar wat betreft interactiegedrag, uiterlijk, lokalisatie en integratie met de rest van je gebruikersinterface.

## Instantiatie en internationalisatie {#instantiation-and-internationalization}

Voeg een `Refresher` toe door deze te instantiëren en een refresh listener te registreren. Wanneer de refreshbewerkingen zijn voltooid, roep je `finish()` aan om de component opnieuw naar zijn idle-toestand te resetten.

:::info Hoe je de `Refresher` activeert
Om de `Refresher` te activeren, **klik en sleep naar beneden** vanaf de bovenkant van het scrollbare gebied. Hoewel deze gebaar bekend is op mobiele apparaten, is het minder gebruikelijk op desktop—zorg ervoor dat je met de muis vasthoudt en trekt.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Deze aanpak wordt vaak gebruikt om gepagineerde lijsten te vernieuwen of het oneindige scrollen opnieuw te starten.

### Internationalisatie {#internationalization}

Elke statuslabel kan ook gelokaliseerd worden met behulp van het `RefresherI18n` object. De drie staten zijn:

- Pull: Initiële gebaartekst (bijv. "Trek naar beneden om te verversen")
- Release: Triggerdrempel bereikt (bijv. "Laat los om te verversen")
- Refresh: Laadstatus (bijv. "Verfrissen")

Dit maakt meertalig ondersteuning en aanpassingen aan branding mogelijk indien nodig.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pictogramaanpassing {#icon-customization}

Je kunt de [`Icons`](../components/icon) die worden gebruikt voor de `pull`/`release` en `refreshing` fasen wijzigen met behulp van een vooraf gedefinieerde [`Icon`](../components/icon) of een [Icon-URL](../managing-resources/assets-protocols). Deze zijn nuttig wanneer je branding of een aangepaste animatie wilt toepassen.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull-gedrag configuratie {#pull-behavior-configuration}

### Drempel {#threshold}

Stel in hoe ver de gebruiker naar beneden moet trekken (in pixels) voordat de refresh wordt geactiveerd:

```java
refresher.setThreshold(80); // standaard: 80px
```

### Maximale drempel {#threshold-maximum}

Om de maximale pull-afstand die is toegestaan te definiëren, gebruik de `setThresholdMax()` methode:

```java
refresher.setThresholdMax(160);
```

Deze drempels bepalen de gevoeligheid van de gebaren en de weerstandscurve.

## Statusbeheer {#state-management}

De `Refresher` component behoudt zijn eigen interne status en communiceert statuswijzigingen via evenementen. Wanneer een gebruiker naar beneden trekt voorbij de gedefinieerde drempel, genereert de `Refresher` een vernieuwingsevenement waar je op kunt reageren door een `onRefresh()` listener te registreren.

Binnen deze listener wordt van je verwacht dat je de benodigde bewerking uitvoert—zoals het ophalen van nieuwe gegevens of het resetten van een lijst—en vervolgens expliciet aanroept:

```java
refresher.finish();
```
:::warning Ontbrekende `finish()`
Als je vergeet `finish()` aan te roepen, blijft de refresher eindeloos in de laadstatus.
:::

Je kunt de `Refresher` ook op elk moment programmatisch uitschakelen om te voorkomen dat de gebruiker de refresh-gedraging activeert:

```java
refresher.setEnabled(false);
```

Dit is nuttig wanneer refreshen tijdelijk niet is toegestaan—bijvoorbeeld tijdens een laadscherm of terwijl een ander kritisch proces actief is.

## Stijlgeving {#styling}

### Thema's {#themes}

De `Refresher` component ondersteunt meerdere thema's om verschillende staten visueel te onderscheiden of om aan te sluiten bij de uitstraling van je app. Thema's kunnen worden toegepast met behulp van de `setTheme()` methode.

Het volgende voorbeeld doorloopt alle beschikbare thema's elke keer dat je trekt om te verversen, waardoor je een live voorbeeld krijgt van hoe de `Refresher` eruitziet in verschillende thema's:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
