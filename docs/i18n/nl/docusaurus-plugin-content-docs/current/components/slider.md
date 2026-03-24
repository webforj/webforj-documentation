---
title: Slider
sidebar_position: 101
_i18n_hash: 16e62c6e021597448e33a04ebfd6f201
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component biedt gebruikers een manier om een numerieke waarde te selecteren door een knop langs een spoor te slepen tussen een minimum en maximum grens. Stapintervallen, tickmarks en labels kunnen worden geconfigureerd om de selectie te begeleiden.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `Slider` is ontworpen om direct uit de doos te werken, zonder extra setup om effectief te functioneren. Standaard bestrijkt hij een bereik van 0 tot 100 met een startwaarde van 50, waardoor hij ideaal is voor snelle integratie in elke app. Voor meer specifieke gebruikssituaties kan de `Slider` worden aangepast met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` die gebruikers in staat stelt om volum niveaus binnen een vooraf gedefinieerd bereik aan te passen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en is gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt terwijl de gebruiker met de slider interacteert, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, met de aanname van het standaardbereik van 0 tot 100.
:::

### Waarde instellen en ophalen {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of later bijwerken met de `setValue()` methode. Om de huidige waarde op te halen, gebruik de `getValue()` methode.

```java
Slider slider = new Slider();  
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();  
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimale en maximale waarden {#minimum-and-maximum-values}

De minimale en maximale waarden definiëren het toegestane bereik van de `Slider`, waarbij de grenzen worden bepaald waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan jouw behoeften.

De intervallen op de `Slider` hebben een standaard stap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld langs het slider-spoor, waarbij hun afstand afhankelijk is van de afmetingen van de `Slider`.

Hieronder staat een voorbeeld van het creëren van een `Slider` met een aangepast bereik:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-configuratie {#tick-configuration}

De `Slider` component biedt flexibele tick-configuratie, waarmee je kunt aanpassen hoe de tickmarks worden weergegeven en hoe de sliderknop ermee interactie heeft. Dit omvat het aanpassen van de afstand tussen belangrijke en kleinere ticks, het tonen/verbergen van tickmarks en het inschakelen van vastklikken op tickmarks voor nauwkeurige gebruikersinvoer.

### Afstand tussen belangrijke en kleinere ticks {#major-and-minor-tick-spacing}

Je kunt de afstand voor belangrijke en kleinere tickmarks definiëren, die bepaalt hoe vaak ze op het `Slider` spoor verschijnen:

- Belangrijke ticks zijn groter en vaak gelabeld om sleutelwaarden weer te geven.
- Kleinere ticks zijn kleiner en verschijnen tussen belangrijke ticks voor fijnere intervallen.

Stel de tickafstand in met de volgende methoden `setMajorTickSpacing()` en `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Belangrijke ticks om de 10 eenheden
slider.setMinorTickSpacing(2);  // Kleinere ticks om de 2 eenheden
```

### Ticks tonen of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks in- of uitschakelen met de `setTicksVisible()` methode. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toon tickmarks
slider.setTicksVisible(false); // Verberg tickmarks
```

### Vastklikken {#snapping}

Om ervoor te zorgen dat de `Slider` knop uitlijnt met de dichtstbijzijnde tickmark tijdens gebruikersinteractie, schakel vastklikken in met de `setSnapToTicks()` methode:

```java
slider.setSnapToTicks(true); // Vastklikken inschakelen
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die de instellingen voor belangrijke en kleinere ticks toont, samen met de vastklikfunctie voor nauwkeurige aanpassingen:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan te sluiten bij jouw UI-indeling en app-eisen.

Naast de oriëntatie kan de `Slider` ook worden omgekeerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Wanneer omgekeerd, wordt deze richting omgekeerd. Gebruik de `setInverted(true)` methode om inversie in te schakelen.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste labels opgeven, en je kunt hun zichtbaarheid indien nodig in- of uitschakelen.

### Standaard labels {#default-labels}

Standaard kan de slider numerieke labels op belangrijke tickmarks weergeven. Deze waarden worden bepaald door de instelling van `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruik:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door aangepaste tekst met de `setLabels()` methode. Dit is nuttig wanneer je meer betekenisvolle waarden wilt weergeven (bijvoorbeeld temperatuur, valuta of categorieën).

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

Of je nu standaard of aangepaste labels gebruikt, je kunt de zichtbaarheid ervan beheren met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, waardoor gebruikers preciezere aanpassingen kunnen maken. Je kunt het gedrag, de zichtbaarheid en het formaat van de tooltip configureren om aan jouw behoeften te voldoen.

Om tooltips in te schakelen, gebruik de `setTooltipVisible()` methode. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook zo worden geconfigureerd dat ze alleen verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de `setTooltipVisibleOnSlideOnly()` methode om dit gedrag in te schakelen. Dit is vooral nuttig om visuele rommel te verminderen terwijl nog steeds nuttige feedback tijdens interactie wordt gegeven.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:


### Aanpassing van tooltips {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als je deze tekst wilt aanpassen, gebruik dan de `setTooltipText()` methode. Dit is nuttig wanneer je wilt dat de tooltip statische of beschrijvende tekst in plaats van de live waarde toont.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als je expressie het `return` sleutelwoord bevat, wordt deze zoals het is gebruikt. Zo niet, dan wordt deze automatisch omgeven door `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollarteken
slider.setTooltipText("return x + '$'"); 
```

Of simpelweg:

```java
// Geïnterpreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'"); 
```


## Stijlen {#styling}

### Thema's {#themes}

De `Slider` wordt geleverd met 6 ingebouwde thema's voor snelle styling zonder het gebruik van CSS. Theming wordt ondersteund door het gebruik van een ingebouwde enum-klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
