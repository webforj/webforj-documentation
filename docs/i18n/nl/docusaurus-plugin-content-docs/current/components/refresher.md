---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh is een veelvoorkomend patroon in mobiele en tap-vriendelijke interfaces, en de `Refresher`-component brengt het naar scrollbare containers in webforJ. Terwijl gebruikers naar beneden vegen voorbij een instelbare drempel, schakelt het tussen visuele toestanden: `pull`, `release` en `refreshing`, elk met een aanpasbaar pictogram en gelokaliseerde tekst. Het werkt goed samen met [`InfiniteScroll`](../components/infinitescroll) voor het herladen of opnieuw instellen van inhoud via gebaar-gebaseerde invoer.

<!-- INTRO_END -->

## Instantiatie en internationalisering {#instantiation-and-internationalization}

Voeg een `Refresher` toe door deze te instantiëren en een refresh listener te registreren. Wanneer de refresh-operaties zijn voltooid, roep dan `finish()` aan om de component weer in zijn inactieve staat te zetten.

:::info Hoe de `Refresher` te activeren
Om de `Refresher` te activeren, **klik en sleep naar beneden** vanaf de bovenkant van het scrollbare gebied. Hoewel deze gebaar bekend is op mobiel, is het minder gebruikelijk op desktop—zorg ervoor dat je met je muis vasthoudt en trekt.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

Deze aanpak wordt vaak gebruikt om gepagineerde lijsten te vernieuwen of de laadfunctie van oneindig scrollen te herstarten.

### Internationalisering {#internationalization}

Elk statuslabel kan ook worden gelokaliseerd met behulp van het `RefresherI18n`-object. De drie staten zijn:

- Pull: Initiële gebaartekst (bijv. "Trek naar beneden om te vernieuwen")
- Release: Activeringsdrempel bereikt (bijv. "Laat los om te vernieuwen")
- Refresh: Laadtoestand (bijv. "Bezig met vernieuwen")

Dit zorgt voor meertalige ondersteuning en aanpassingen voor branding indien nodig.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Pictogramaanpassing {#icon-customization}

Je kunt de gebruikte [`Icons`](../components/icon) voor de `pull`/`release` en `refreshing` stadia wijzigen met behulp van een vooraf gedefinieerde [`Icon`](../components/icon) of een [Icon URL](../managing-resources/assets-protocols). Deze zijn nuttig wanneer je branding of een aangepaste animatie wilt toepassen.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Pullgedrag configuratie {#pull-behavior-configuration}

### Drempel {#threshold}

Stel in hoe ver de gebruiker moet trekken (in pixels) voordat de refresh wordt geactiveerd:

```java
refresher.setThreshold(80); // standaard: 80px
```

### Maximale drempel {#threshold-maximum}

Om de maximale trekafstand te definiëren die is toegestaan, gebruik de methode `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Deze drempels regelen de gevoeligheid van de gebaren en de weerstandscurve.

## Toestandbeheer {#state-management}

De `Refresher`-component behoudt zijn eigen interne toestand en communiceert toestandwijzigingen via gebeurtenissen. Wanneer een gebruiker naar beneden trekt voorbij de gedefinieerde drempel, stoot de `Refresher` een refreshgebeurtenis uit waarop je kunt reageren door een `onRefresh()`-listener te registreren.

Binnen deze listener wordt van je verwacht dat je de benodigde operatie uitvoert—zoals het ophalen van nieuwe gegevens of het opnieuw instellen van een lijst—en vervolgens expliciet aanroept:

```java
refresher.finish();
```
:::warning Ontbrekende `finish()`
Als je vergeet `finish()` aan te roepen, blijft de refresher in de laadtoestand.
:::

Je kunt de `Refresher` ook programmatisch op elk moment uitschakelen om te voorkomen dat de gebruiker refresh-gedrag activeert:

```java
refresher.setEnabled(false);
```

Dit is handig wanneer vernieuwingen tijdelijk niet zijn toegestaan—bijvoorbeeld tijdens een laadscherm of terwijl een ander kritisch proces wordt uitgevoerd.

## Styling {#styling}

### Thema's {#themes}

De `Refresher`-component ondersteunt meerdere thema's om verschillende toestanden visueel te onderscheiden of om aan de look en feel van je app te voldoen. Thema's kunnen worden toegepast met de methode `setTheme()`.

Het volgende voorbeeld doorloopt alle beschikbare thema's elke keer als je trekt om te vernieuwen, zodat je een live voorbeeld krijgt van hoe de `Refresher` eruit ziet in verschillende thema's:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
