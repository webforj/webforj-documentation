---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh is een veelvoorkomend patroon in mobiele en aanraakvriendelijke interfaces, en de `Refresher` component brengt dit naar scrollbare containers in webforJ. Terwijl gebruikers omlaag vegen voorbij een configureerbare drempel, verandert het door visuele toestanden: `pull`, `release` en `refreshing`, elk met een aanpasbaar pictogram en gelokaliseerde tekst. Het werkt goed samen met [`InfiniteScroll`](../components/infinitescroll) voor het herladen of opnieuw instellen van inhoud via gebaar-gebaseerde input.

<!-- INTRO_END -->

## Instantiatie en internationalisering {#instantiation-and-internationalization}

Voeg een `Refresher` toe door deze te instantiëren en een refresh listener te registreren. Wanneer refresh-operaties zijn voltooid, roep je `finish()` aan om de component naar de idle-toestand te resetten.

:::info Hoe je de `Refresher` activeert
Om de `Refresher` te activeren, **klik en sleep omlaag** vanaf de bovenkant van het scrollbare gebied. Terwijl dit gebaar vertrouwd is op mobiel, is het minder gebruikelijk op desktop—zorg ervoor dat je met je muis vasthoudt en trekt.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Deze benadering wordt vaak gebruikt om gepagineerde lijsten te vernieuwen of de loading van infinite scroll opnieuw te starten.

### Internationalisering {#internationalization}

Elke statuslabel kan ook gelokaliseerd worden met behulp van het `RefresherI18n` object. De drie staten zijn:

- Pull: Initiële gebaar tekst (bijv. "Trek omlaag om te vernieuwen")
- Release: Drempel bereikt (bijv. "Laat los om te vernieuwen")
- Refresh: Laadtoestand (bijv. "Bezig met vernieuwen")

Dit stelt meertalige ondersteuning en branding-aanpassingen mogelijk wanneer nodig.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pictogram aanpassing {#icon-customization}

Je kunt de [`Icons`](../components/icon) die worden gebruikt voor de `pull`/`release` en `refreshing` stadia wijzigen met behulp van een vooraf gedefinieerd [`Icon`](../components/icon) of een [Icon URL](../managing-resources/assets-protocols). Deze zijn nuttig wanneer je branding of een aangepaste animatie wilt toepassen.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull-gedrag configuratie {#pull-behavior-configuration}

### Drempel {#threshold}

Stel in hoe ver de gebruiker moet omlaag trekken (in pixels) voordat de refresh wordt geactiveerd:

```java
refresher.setThreshold(80); // standaard: 80px
```

### Maximale drempel {#threshold-maximum}

Om de maximale trekafstand te definiëren, gebruik je de `setThresholdMax()` methode:

```java
refresher.setThresholdMax(160);
```

Deze drempels regelen de gevoeligheid van het gebaar en de weerstandcurve.

## Toestandsbeheer {#state-management}

De `Refresher` component onderhoudt zijn eigen interne status en communiceert statuswijzigingen via evenementen. Wanneer een gebruiker omlaag trekt voorbij de gedefinieerde drempel, zendt de `Refresher` een refresh-evenement uit waar je op kunt reageren door een `onRefresh()` listener te registreren.

Binnen deze listener wordt van je verwacht dat je de vereiste operatie uitvoert—zoals het ophalen van nieuwe data of het opnieuw instellen van een lijst—en vervolgens expliciet aanroept:

```java
refresher.finish();
```
:::warning Ontbrekende `finish()`
Als je vergeet `finish()` aan te roepen, blijft de refresher in de laadtoestand tot in de eeuwigheid.
:::

Je kunt ook op elk moment de `Refresher` programmatig uitschakelen om te voorkomen dat de gebruiker de verversingsgedrag activeert:

```java
refresher.setEnabled(false);
```

Dit is nuttig wanneer verversingen tijdelijk niet mogen worden toegestaan—bijvoorbeeld tijdens een laadscherm of terwijl een ander kritisch proces aan het draaien is.

## Stijlen {#styling}

### Thema's {#themes}

De `Refresher` component ondersteunt meerdere thema's om verschillende toestanden visueel te onderscheiden of om overeen te komen met de look en feel van jouw app. Thema's kunnen worden toegepast met de `setTheme()` methode.

Het volgende voorbeeld doorloopt alle beschikbare thema's elke keer dat je sleept om te vernieuwen, zodat je een live preview krijgt van hoe de `Refresher` eruitziet in verschillende thema's:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
