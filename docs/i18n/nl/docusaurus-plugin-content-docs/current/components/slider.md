---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 06f08c2c7500c5fb8d50a1dcfd8488da
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component biedt gebruikers de mogelijkheid om een numerieke waarde te selecteren door een knop langs een spoor te slepen tussen een minimum- en maximumgrens. Stapintervallen, tickmarks en labels kunnen worden geconfigureerd om de selectie te begeleiden.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `Slider` is ontworpen om direct uit de doos te werken, zonder dat er aanvullende instellingen nodig zijn om effectief te functioneren. Standaard bestrijkt deze een bereik van 0 tot 100 met een startwaarde van 50, waardoor het ideaal is voor snelle integratie in elke app. Voor specifiekere gebruiksscenario's kan de `Slider` worden aangepast met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` die gebruikers in staat stelt om het volume binnen een gedefinieerd bereik aan te passen:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en wordt gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt wanneer de gebruiker interactie heeft met de slider, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, uitgaande van het standaardbereik van 0 tot 100.
:::

### Waarde instellen en ophalen {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of later bijwerken met de methode `setValue()`. Om de huidige waarde op te halen, gebruik je de methode `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimum- en maximumwaarden {#minimum-and-maximum-values}

De minimum- en maximumwaarden definiëren het toegestane bereik van de `Slider`, waarmee de grenzen worden bepaald waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan jouw behoeften.

De intervallen op de `Slider` hebben een standaardstap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld langs het slider-spoor, waarbij de afstand afhankelijk is van de afmetingen van de `Slider`.

Hieronder staat een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tick-configuratie {#tick-configuration}

De `Slider` component biedt flexibele tick-configuratie, waarmee je kunt aanpassen hoe tickmarks worden weergegeven en hoe de knop van de slider ermee interacteert. Dit omvat het aanpassen van de afstand tussen belangrijke en minder belangrijke ticks, het tonen/verbergen van tickmarks, en het inschakelen van vastklikken op tickmarks voor nauwkeurige gebruikersinvoer.

### Afstand tussen belangrijke en minder belangrijke ticks {#major-and-minor-tick-spacing}

Je kunt de afstand voor belangrijke en minder belangrijke tickmarks definiëren, wat bepaalt hoe vaak ze op het `Slider`-spoor verschijnen:

- Belangrijke ticks zijn groter en vaak gelabeld om sleutelwaarden weer te geven.
- Minder belangrijke ticks zijn kleiner en verschijnen tussen belangrijke ticks om fijnere intervallen aan te bieden.

Stel de tickafstand in met de volgende methoden `setMajorTickSpacing()` en `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Belangrijke ticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Minder belangrijke ticks elke 2 eenheden
```

### Ticks tonen of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks in- of uitschakelen met de methode `setTicksVisible()`. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toon tickmarks
slider.setTicksVisible(false); // Verberg tickmarks
```

### Vastklikken {#snapping}

Om ervoor te zorgen dat de knop van de `Slider` uitgelijnd is met de dichtstbijzijnde tickmark tijdens interactie door de gebruiker, schakel je vastklikken in met de methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Vastklikken inschakelen
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die belangrijke en minder belangrijke tickinstellingen toont, samen met de vastklikcapaciteit voor nauwkeurige aanpassingen:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan jouw UI-indeling en app-eisen te voldoen.

Naast de oriëntatie kan de `Slider` ook worden omgekeerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Bij omkering wordt deze richting omgekeerd. Gebruik de methode `setInverted(true)` om inversie in te schakelen.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste labels bieden, en je kunt de zichtbaarheid ervan naar behoefte in- of uitschakelen.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven op belangrijke tickmarks. Deze waarden worden bepaald door de instelling `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruik je:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door aangepaste tekst met de methode `setLabels()`. Dit is nuttig wanneer je meer betekenisvolle waarden wilt weergeven (bijvoorbeeld temperatuur, valuta of categorieën).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Koud",
  30, "Koel",
  50, "Gemiddeld",
  80, "Warm",
  100, "Heet"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Zichtbaarheid van labels in- of uitschakelen {#toggling-label-visibility}

Of je nu standaard of aangepaste labels gebruikt, je kunt hun zichtbaarheid regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, wat gebruikers helpt om nauwkeurigere aanpassingen te maken. Je kunt de gedrag, zichtbaarheid en opmaak van de tooltip configureren om aan jouw behoeften te voldoen.

Om tooltips in te schakelen, gebruik je de methode `setTooltipVisible()`. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook zo worden geconfigureerd dat ze alleen verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de methode `setTooltipVisibleOnSlideOnly()` om dit gedrag in te schakelen. Dit is vooral nuttig om visuele rommel te verminderen terwijl je toch nuttige feedback tijdens de interactie biedt.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:

### Tooltip-aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als je deze tekst wilt aanpassen, gebruik dan de methode `setTooltipText()`. Dit is nuttig wanneer je wilt dat de tooltip statische of beschrijvende tekst toont in plaats van de live waarde.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als je expressie het `return`-sleutelwoord bevat, wordt deze onveranderd gebruikt. Als dat niet het geval is, wordt deze automatisch omgeven door `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollarteken
slider.setTooltipText("return x + '$'");
```

Of eenvoudig:

```java
// Geïnterpreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'");
```

## Stijlen {#styling}

### Thema's {#themes}

De `Slider` wordt geleverd met 6 ingebouwde thema's voor snelle styling zonder het gebruik van CSS. Theming wordt ondersteund door een ingebouwde enum-klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
