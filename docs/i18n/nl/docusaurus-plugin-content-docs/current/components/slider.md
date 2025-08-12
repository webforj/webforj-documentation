---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component in webforJ biedt een interactieve controle waarmee gebruikers een waarde binnen een specifiek bereik kunnen selecteren door een knop te verplaatsen. Deze functie is bijzonder nuttig voor apps die nauwkeurige of intuïtieve invoer vereisen, zoals het selecteren van volumes, percentages of andere aanpasbare waarden.

## Basisprincipes {#basics}

De `Slider` is ontworpen om recht uit de verpakking te werken, zonder extra configuratie om effectief te functioneren. Standaard beslaat het een bereik van 0 tot 100 met een startwaarde van 50, waardoor het ideaal is voor snelle integratie in elke app. Voor meer specifieke toepassingen kan de `Slider` worden gepersonaliseerd met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` waarmee gebruikers het volume binnen een vooraf gedefinieerd bereik kunnen aanpassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en is gedefinieerd als een gehele waarde binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt terwijl de gebruiker interactie heeft met de slider, waardoor het een essentiële eigenschap is voor het bijhouden van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, aangenomen dat het standaardbereik van 0 tot 100 is.
:::

### Het instellen en ophalen van de waarde {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of later bijwerken met de `setValue()` methode. Om de huidige waarde op te halen, gebruik je de `getValue()` methode.

```java
Slider slider = new Slider();  
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();  
System.out.println("Huidige sliderwaarde: " + value);
```

## Minimale en maximale waarden {#minimum-and-maximum-values}

De minimale en maximale waarden definiëren het toegestane bereik van de `Slider`, dat de grenzen bepaalt waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan je behoeften.

De intervallen op de `Slider` hebben een standaardstap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld over het slidertrack, met hun ruimte afhankelijk van de afmetingen van de `Slider`.

Hier is een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-configuratie {#tick-configuration}

De `Slider` component biedt flexibele tick-configuratie, waarmee je kunt aanpassen hoe tickmarks worden weergegeven en hoe de knop van de slider ermee interactie heeft. Dit omvat het aanpassen van de ruimte tussen grote en kleine ticks, het weergeven/verbergen van tickmarks en het inschakelen van het vastklikken aan tickmarks voor nauwkeurige gebruikersinvoer.

### Ruimte tussen grote en kleine ticks {#major-and-minor-tick-spacing}

Je kunt de ruimte voor grote en kleine tickmarks definiëren, wat bepaalt hoe vaak ze verschijnen op het slidertrack:

- Grote ticks zijn groter en vaak gelabeld om belangrijke waarden weer te geven.
- Kleine ticks zijn kleiner en verschijnen tussen grote ticks om fijnere intervallen aan te bieden.

Stel de tickruimte in met de `setMajorTickSpacing()` en `setMinorTickSpacing()` methoden:
```java
slider.setMajorTickSpacing(10); // Grote ticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Kleine ticks elke 2 eenheden
```

### Ticks weergeven of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks toggelen met de `setTicksVisible()` methode. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toont tickmarks
slider.setTicksVisible(false); // Verbergt tickmarks
```

### Vastklikken {#snapping}

Om ervoor te zorgen dat de knop van de `Slider` uitlijnt met de dichtstbijzijnde tickmark tijdens gebruikersinteractie, schakel je vastklikken in met de `setSnapToTicks()` methode:

```java
slider.setSnapToTicks(true); // Vastklikken inschakelen
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die grote en kleine tickinstellingen toont, samen met de vastklikcapaciteit voor nauwkeurige aanpassingen:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan te sluiten bij je UI-indeling en app-vereisten.

Naast oriëntatie kan de `Slider` ook worden omgekeerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Wanneer omgekeerd, is deze richting omgekeerd. Gebruik de `setInverted(true)` methode om inversie in te schakelen.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste, en je kunt hun zichtbaarheid indien nodig toggelen.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven bij grote tickmarks. Deze waarden worden bepaald door de `setMajorTickSpacing()` instelling. Om standaardlabels in te schakelen, gebruik je:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door tekst met behulp van de `setLabels()` methode. Dit is nuttig wanneer je meer betekenisvolle waarden wilt weergeven (bijv. temperatuur, valuta of categorieën).

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

### Zichtbaarheid van labels toggelen {#toggling-label-visibility}

Of je nu standaard- of aangepaste labels gebruikt, je kunt hun zichtbaarheid regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, waardoor gebruikers nauwkeurigere aanpassingen kunnen maken. Je kunt het gedrag, de zichtbaarheid en het formaat van de tooltip configureren om aan je behoeften te voldoen.

Om tooltips in te schakelen, gebruik je de `setTooltipVisible()` methode. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook worden geconfigureerd om alleen te verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de `setTooltipVisibleOnSlideOnly()` methode om dit gedrag in te schakelen. Dit is vooral nuttig om visuele rommel te verminderen terwijl je nog steeds nuttige feedback biedt tijdens interactie.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:


### Tooltip-aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als je deze tekst wilt aanpassen, gebruik je de `setTooltipText()` methode. Dit is nuttig wanneer je wilt dat de tooltip statische of beschrijvende tekst toont in plaats van de live waarde.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als je expressie het `return` sleutelwoord bevat, wordt het zoals het is gebruikt. Zo niet, wordt het automatisch ingepakt met `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollar teken
slider.setTooltipText("return x + '$'"); 
```

Of eenvoudig:

```java
// Geïntrepreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'"); 
```

## Stijling {#styling}

### Thema's {#themes}

De `Slider` wordt geleverd met 6 thema's die zijn ingebouwd voor snelle styling zonder gebruik te maken van CSS. Theming wordt ondersteund door een ingebouwde enum-klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
