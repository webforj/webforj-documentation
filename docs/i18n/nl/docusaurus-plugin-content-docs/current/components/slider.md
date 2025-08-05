---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component in webforJ biedt een interactieve controle waarmee gebruikers een waarde binnen een specifiek bereik kunnen selecteren door een knop te verschuiven. Deze functie is bijzonder nuttig voor apps die nauwkeurige of intuïtieve invoer vereisen, zoals het selecteren van volumes, percentages of andere instelbare waarden.

## Basisprincipes {#basics}

De `Slider` is ontworpen om direct te functioneren, zonder extra instellingen voor effectieve werking. Standaard heeft hij een bereik van 0 tot 100 met een startwaarde van 50, waardoor hij ideaal is voor snelle integratie in elke app. Voor meer specifieke toepassingen kan de `Slider` worden aangepast met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` die gebruikers in staat stelt om volumenniveaus binnen een vooraf gedefinieerd bereik aan te passen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en is gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt terwijl de gebruiker met de slider interactie heeft, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, uitgaande van het standaard bereik van 0 tot 100.
:::

### Waarde instellen en ophalen {#setting-and-getting-the-value}

Je kunt de waarde van de `Slider` tijdens de initialisatie instellen of deze later bijwerken met de `setValue()`-methode. Om de huidige waarde op te halen, gebruik je de `getValue()`-methode.

```java
Slider slider = new Slider();  
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();  
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimale en maximale waarden {#minimum-and-maximum-values}

De minimale en maximale waarden definiëren het toegestane bereik van de `Slider`, waarbij de grenzen worden bepaald waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar je kunt deze waarden aanpassen aan jouw behoeften.

De intervallen op de `Slider` hebben een standaard stapgrootte van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld langs de sliderbaan, met de tussenruimtes afhankelijk van de afmetingen van de `Slider`.

Hieronder staat een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick configuratie {#tick-configuration}

De `Slider` component biedt flexibele tickconfiguratie, waarmee je kunt aanpassen hoe tickmarks worden weergegeven en hoe de sliderknop ermee omgaat. Dit omvat het aanpassen van de afstand tussen grote en kleine ticks, het tonen/verbergen van tickmarks en het inschakelen van snappen aan tickmarks voor nauwkeurige gebruikersinvoer.

### Grote en kleine tickafstand {#major-and-minor-tick-spacing}

Je kunt de afstand voor grote en kleine tickmarks definiëren, wat bepaalt hoe vaak ze op de `Slider`-baan verschijnen:

- Grote ticks zijn groter en vaak gelabeld om belangrijke waarden te vertegenwoordigen.
- Kleine ticks zijn kleiner en verschijnen tussen grote ticks om fijnere intervallen aan te bieden.

Stel de tickafstand in met behulp van de `setMajorTickSpacing()` en `setMinorTickSpacing()` methoden:
```java
slider.setMajorTickSpacing(10); // Grote ticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Kleine ticks elke 2 eenheden
```

### Ticks tonen of verbergen {#show-or-hide-ticks}

Je kunt de zichtbaarheid van tickmarks schakelen met de `setTicksVisible()`-methode. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toon tickmarks
slider.setTicksVisible(false); // Verberg tickmarks
```

### Snappen {#snapping}

Om ervoor te zorgen dat de `Slider` knop uitgelijnd is met de dichtstbijzijnde tickmark tijdens gebruikersinteractie, schakel je snappen in met de `setSnapToTicks()`-methode:

```java
slider.setSnapToTicks(true); // Schakel snappen in
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die grote en kleine tickinstellingen laat zien, samen met de snapfunctie voor nauwkeurige aanpassingen:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. Je kunt de oriëntatie wijzigen om aan te sluiten bij jouw UI-layout en appvereisten.

Naast oriëntatie kan de `Slider` ook geïnverteerd worden. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Wanneer deze is geïnverteerd, wordt deze richting omgekeerd. Gebruik de `setInverted(true)`-methode om inversie in te schakelen.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. Je kunt standaard numerieke labels gebruiken of aangepaste labels invoeren, en je kunt hun zichtbaarheid indien nodig schakelen.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven bij de grote tickmarks. Deze waarden worden bepaald door de instelling van `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruik:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

Je kunt de standaard numerieke labels vervangen door aangepaste tekst met de `setLabels()`-methode. Dit is nuttig wanneer je meer betekenisvolle waarden wilt weergeven (bijv. temperatuur, valuta of categorieën).

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

### Zichtbaarheid van labels schakelen {#toggling-label-visibility}

Of je nu standaard- of aangepaste labels gebruikt, je kunt hun zichtbaarheid regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, waardoor gebruikers nauwkeurigere aanpassingen kunnen doen. Je kunt het gedrag, de zichtbaarheid en de indeling van de tooltip configureren om aan jouw behoeften te voldoen.

Om tooltips in te schakelen, gebruik de `setTooltipVisible()`-methode. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Schakel tooltips in
slider.setTooltipVisible(false); // Schakel tooltips uit
```

Tooltips kunnen ook worden geconfigureerd om alleen te verschijnen wanneer de gebruiker met de `Slider` interactie heeft. Gebruik de `setTooltipVisibleOnSlideOnly()`-methode om dit gedrag in te schakelen. Dit is vooral handig voor het verminderen van visuele rommel terwijl je nog steeds nuttige feedback tijdens de interactie biedt.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:

### Tooltip aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als je deze tekst wilt aanpassen, gebruik dan de `setTooltipText()`-methode. Dit is nuttig wanneer je wilt dat de tooltip statische of beschrijvende tekst weergeeft in plaats van de live waarde.

Je kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als je expressie het `return`-trefwoord bevat, wordt deze zoals het is gebruikt. Als dat niet zo is, wordt deze automatisch omwikkeld met `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollarteken
slider.setTooltipText("return x + '$'"); 
```

Of eenvoudig:

```java
// Geïnterpreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'"); 
```

## Styling {#styling}

### Thema's {#themes}

De `Slider` heeft 6 thema's ingebouwd voor snelle styling zonder gebruik van CSS. Theming wordt ondersteund door gebruik te maken van een ingebouwde enum-klasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
