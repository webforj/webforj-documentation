---
title: Slider
sidebar_position: 101
_i18n_hash: 77c71bf27e728d68c1e3381628b37a27
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component biedt gebruikers een manier om een numerieke waarde te selecteren door een knop langs een spoor te slepen tussen een minimum- en maximumgrens. Stapintervallen, tickmarks en labels kunnen worden geconfigureerd om de selectie te begeleiden.

<!-- INTRO_END -->

## Basics {#basics}

De `Slider` is ontworpen om direct te werken, zonder aanvullende instellingen om effectief te functioneren. Standaard is het bereik van 0 tot 100 met een startwaarde van 50, waardoor het ideaal is voor snelle integratie in elke app. Voor specifiekere gebruikssituaties kan de `Slider` worden gepersonaliseerd met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` die gebruikers in staat stelt om volumewaarden aan te passen binnen een vooraf gedefinieerd bereik:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en wordt gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt naarmate de gebruiker interactie heeft met de slider, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, uitgaande van het standaard bereik van 0 tot 100.
:::

### Instellen en ophalen van de waarde {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of later bijwerken met de methode `setValue()`. Om de huidige waarde op te halen, gebruik je de methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Zet de slider op 25

Integer value = slider.getValue();  
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimum- en maximumwaarden {#minimum-and-maximum-values}

De minimum- en maximumwaarden definiëren het toegestane bereik van de `Slider`, waarmee de grenzen worden bepaald waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan jouw behoeften.

De intervallen op de `Slider` hebben een standaardstap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld langs het slider spoor, met hun afstand afhankelijk van de afmetingen van de `Slider`.

Hier is een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick configuratie {#tick-configuration}

De `Slider` component biedt flexibele tickconfiguratie, waarmee je kunt aanpassen hoe tickmarks worden weergegeven en hoe de schuifknop daarmee omgaat. Dit omvat het aanpassen van de afstand tussen de hoofdticks en de subticks, het tonen/verbergen van tickmarks en het inschakelen van uitlijning op tickmarks voor nauwkeurige gebruikersinvoer.

### Hoofd- en subtickafstand {#major-and-minor-tick-spacing}

Je kunt de afstand voor hoofdtickmarks en subtickmarks definiëren, die bepaalt hoe vaak ze op het `Slider`-spoor verschijnen:

- Hoofdticks zijn groter en vaak gelabeld om sleutelwaarden weer te geven.
- Subticks zijn kleiner en verschijnen tussen de hoofdticks om fijnere intervallen aan te bieden.

Stel de tickafstand in met de volgende `setMajorTickSpacing()` en `setMinorTickSpacing()` methoden:
```java
slider.setMajorTickSpacing(10); // Hoofdticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Subticks elke 2 eenheden
```

### Tickmarks tonen of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks toggelen met de `setTicksVisible()` methode. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toon tickmarks
slider.setTicksVisible(false); // Verberg tickmarks
```

### Uitlijning {#snapping}

Om ervoor te zorgen dat de knop van de `Slider` uitgelijnd is met de dichtstbijzijnde tickmark tijdens gebruikersinteractie, schakel je de uitlijning in met de `setSnapToTicks()` methode:

```java
slider.setSnapToTicks(true); // Schakel uitlijning in
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die de instellingen voor hoofdticks en subticks toont, samen met de uitlijnfunctionaliteit voor nauwkeurige aanpassingen:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan jouw UI-indeling en app-eisen te voldoen.

Naast de oriëntatie kan de `Slider` ook worden omgekeerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Bij inversie is deze richting omgekeerd. Gebruik de methode `setInverted(true)` om inversie in te schakelen.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '440px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste labels opgeven, en je kunt hun zichtbaarheid indien nodig toggelen.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven op de hoofdtickmarks. Deze waarden worden bepaald door de instelling `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruik je:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door aangepaste tekst met de methode `setLabels()`. Dit is handig wanneer je meer betekenisvolle waarden wilt weergeven (bijvoorbeeld temperatuur, valuta of categorieën).

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

Of je nu standaard of aangepaste labels gebruikt, je kunt hun zichtbaarheid regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, waardoor gebruikers nauwkeurigere aanpassingen kunnen maken. Je kunt het gedrag, de zichtbaarheid en het formaat van de tooltip configureren om aan jouw behoeften te voldoen.

Om tooltips in te schakelen, gebruik je de `setTooltipVisible()` methode. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook worden geconfigureerd om alleen te verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de methode `setTooltipVisibleOnSlideOnly()` om dit gedrag in te schakelen. Dit is vooral nuttig om visuele rommel te verminderen terwijl je nog steeds nuttige feedback tijdens interactie biedt.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:


### Tooltip aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als je deze tekst wilt aanpassen, gebruik je de methode `setTooltipText()`. Dit is nuttig wanneer je wilt dat de tooltip vaste of beschrijvende tekst toont in plaats van de live waarde.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als jouw expressie het `return`-trefwoord bevat, wordt het ongefilterd gebruikt. Zo niet, wordt het automatisch omgeven door `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollarteken
slider.setTooltipText("return x + '$'"); 
```

Of eenvoudig:

```java
// Geïntrepreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'"); 
```


## Styling {#styling}

### Thema's {#themes}

De `Slider` komt met 6 ingebouwde thema's voor snelle styling zonder het gebruik van CSS. Theming wordt ondersteund door het gebruik van een ingebouwde enum klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
