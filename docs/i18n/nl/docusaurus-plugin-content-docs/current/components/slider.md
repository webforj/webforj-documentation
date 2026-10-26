---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 88cace5ce1650eaaf33dfc4535125dc0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component biedt gebruikers een manier om een numerieke waarde te selecteren door een knop langs een track te slepen tussen een minimum en maximum grens. Stapintervallen, tickmarks en labels kunnen worden geconfigureerd om de selectie te begeleiden.

<!-- INTRO_END -->

Een nieuwe `Slider` beslaat een bereik van 0 tot 100 met een startwaarde van 50, waardoor deze zonder enige instelling werkt. Eigenschappen zoals oriëntatie, tickmarks, labels en tooltip kunnen meer specifieke gevallen dekken, zoals de volumeregelaar hieronder.

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en is gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt wanneer de gebruiker interactie heeft met de slider, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, ervan uitgaande dat het standaard bereik van 0 tot 100 is.
:::

### Waarde instellen en ophalen {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of deze later bijwerken met de methode `setValue()`. Om de huidige waarde op te halen, gebruik je de methode `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimale en maximale waarden {#minimum-and-maximum-values}

De minimale en maximale waarden definiëren het toegestane bereik van de `Slider`, wat de grenzen bepaalt waarbinnen de `Slider` knop kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan je behoeften.

De intervallen op de `Slider` hebben een standaard stap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld langs de slider track, waarbij de ruimte afhankelijk is van de afmetingen van de `Slider`.

Hieronder staat een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tick configuratie {#tick-configuration}

De `Slider` component biedt flexibele tickconfiguratie, waarmee je kunt aanpassen hoe tickmarks worden weergegeven en hoe de sliderknop ermee omgaat. Dit omvat het aanpassen van de afstand tussen grote en kleine ticks, het tonen/verbergen van tickmarks, en het mogelijk maken van snappen naar tickmarks voor nauwkeurige gebruikersinvoer.

### Grote en kleine tickafstand {#major-and-minor-tick-spacing}

Je kunt de afstand voor grote en kleine tickmarks definiëren, die bepaalt hoe vaak ze op de `Slider` track verschijnen:

- Grote ticks zijn groter en vaak gemarkeerd om belangrijke waarden voor te stellen.
- Kleine ticks zijn kleiner en verschijnen tussen grote ticks om fijnere intervallen te bieden.

Stel de tickafstand in met de volgende methoden `setMajorTickSpacing()` en `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Grote ticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Kleine ticks elke 2 eenheden
```

### Ticks tonen of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks in- of uitschakelen met de methode `setTicksVisible()`. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toont tickmarks
slider.setTicksVisible(false); // Verbergt tickmarks
```

### Snappen {#snapping}

Om ervoor te zorgen dat de `Slider` knop uitgelijnd wordt met de dichtstbijzijnde tickmark tijdens gebruikersinteractie, schakel je snappen in met de methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Schakel snappen in
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die grote en kleine tickinstellingen toont samen met de snapfunctionaliteit voor nauwkeurige aanpassingen:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan je UI-indeling en app-vereisten te voldoen.

Naast de oriëntatie kan de `Slider` ook worden geïnverteerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Wanneer deze is geïnverteerd, wordt deze richting omgekeerd. Gebruik de methode `setInverted(true)` om inversie in te schakelen.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste labels geven, en je kunt hun zichtbaarheid in- of uitschakelen wanneer dat nodig is.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven op de grote tickmarks. Deze waarden worden bepaald door de instelling `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruik je:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door aangepaste tekst met de methode `setLabels()`. Dit is nuttig wanneer je meer betekenisvolle waarden wilt weergeven (bijvoorbeeld temperatuur, valuta of categorieën).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Koud",
  30, "Cool",
  50, "Gemiddeld",
  80, "Warm",
  100, "Heet"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Zichtbaarheid van labels in- of uitschakelen {#toggling-label-visibility}

Of je nu standaard- of aangepaste labels gebruikt, je kunt de zichtbaarheid ervan regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, wat gebruikers helpt om nauwkeurigere aanpassingen te maken. Je kunt het gedrag, de zichtbaarheid en het formaat van de tooltip configureren om aan je behoeften te voldoen.

Om tooltips in te schakelen, gebruik je de methode `setTooltipVisible()`. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook zo worden ingesteld dat ze alleen verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de methode `setTooltipVisibleOnSlideOnly()` om dit gedrag in te schakelen. Dit is vooral nuttig om visuele rommel te verminderen en tegelijkertijd nuttige feedback te geven tijdens interactie.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:

### Tooltip aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met de huidige waarde. Als je deze tekst wilt aanpassen, gebruik je de methode `setTooltipText()`. Dit is nuttig wanneer je wilt dat de tooltip statische of beschrijvende tekst toont in plaats van de live waarde.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch op te maken. Als je expressie het `return`-sleutelwoord bevat, wordt deze zoals het is gebruikt. Als dat niet het geval is, wordt deze automatisch omhuld met `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollar teken
slider.setTooltipText("return x + '$'");
```

Of eenvoudig:

```java
// Geïnterpreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'");
```

## Stijl {#styling}

### Thema's {#themes}

De `Slider` wordt geleverd met 6 thema's die al zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Theming wordt ondersteund door gebruik te maken van een ingebouwde enum-klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
