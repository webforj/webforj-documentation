---
title: Slider
sidebar_position: 101
_i18n_hash: 490cb925a92ffd4860f74b00491402e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

De `Slider` component biedt gebruikers de mogelijkheid om een numerieke waarde te selecteren door een knop over een spoor te slepen tussen een minimum- en maximumgrens. Stapintervallen, tickmarks en labels kunnen worden geconfigureerd om de selectie te begeleiden.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `Slider` is ontworpen om direct uit de doos te werken, zonder extra configuratie om effectief te functioneren. Standaard strekt het zich uit van 0 tot 100 met een begingetal van 50, wat het ideaal maakt voor snelle integratie in elke app. Voor meer specifieke gebruikstoepassingen kan de `Slider` worden aangepast met eigenschappen zoals oriëntatie, tickmarks, labels en tooltips.

Hier is een voorbeeld van een `Slider` die gebruikers in staat stelt om geluidsniveaus binnen een gedefinieerd bereik aan te passen:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` waarde {#slider-value}

De `Slider` waarde vertegenwoordigt de huidige positie van de knop op de slider en wordt gedefinieerd als een geheel getal binnen het bereik van de `Slider`. Deze waarde wordt dynamisch bijgewerkt terwijl de gebruiker met de slider interactie heeft, waardoor het een essentiële eigenschap is voor het volgen van gebruikersinvoer.

:::tip Standaardwaarde
Standaard begint de `Slider` met een waarde van 50, ervan uitgaande dat het standaardbereik van 0 tot 100 is.
:::

### Het instellen en ophalen van de waarde {#setting-and-getting-the-value}

U kunt de waarde van de `Slider` tijdens de initialisatie instellen of later bijwerken met de `setValue()` methode. Om de huidige waarde op te halen, gebruikt u de `getValue()` methode.

```java
Slider slider = new Slider();  
slider.setValue(25); // Stelt de slider in op 25

Integer value = slider.getValue();  
System.out.println("Huidige Slider Waarde: " + value);
```

## Minimale en maximale waarden {#minimum-and-maximum-values}

De minimale en maximale waarden definieert het toegestane bereik van de `Slider`, wat de grenzen bepaalt waarbinnen de knop van de `Slider` kan bewegen. Standaard is het bereik ingesteld van 0 tot 100, maar u kunt deze waarden aanpassen aan uw behoeften.

De intervallen op de `Slider` hebben een standaardstap van 1, wat betekent dat het aantal intervallen wordt bepaald door het bereik. Bijvoorbeeld:
- Een Slider met een bereik van 0 tot 10 heeft 10 intervallen.
- Een Slider met een bereik van 0 tot 100 heeft 100 intervallen.

Deze intervallen zijn gelijkmatig verdeeld over het slider spoor, met de spacering afhankelijk van de afmetingen van de `Slider`.

Hieronder staat een voorbeeld van het maken van een `Slider` met een aangepast bereik:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tickconfiguratie {#tick-configuration}

De `Slider` component biedt flexibele tickconfiguratie, waardoor u kunt aanpassen hoe tickmarks worden weergegeven en hoe de knop van de slider ermee interacteert. Dit omvat het aanpassen van de spacing van grote en kleine ticks, het weergeven/verbergen van tickmarks en het inschakelen van snap-to-tickmarks voor nauwkeurige gebruikersinvoer.

### Grote en kleine tickspacing {#major-and-minor-tick-spacing}

U kunt de spacing voor grote en kleine tickmarks definiëren, wat bepaalt hoe vaak ze op het `Slider` spoor verschijnen:

- Grote ticks zijn groter en vaak gelabeld om belangrijke waarden voor te stellen.
- Kleine ticks zijn kleiner en verschijnen tussen grote ticks om fijnere intervallen aan te bieden.

Stel de tickspacing in met de volgende `setMajorTickSpacing()` en `setMinorTickSpacing()` methoden:
```java
slider.setMajorTickSpacing(10); // Grote ticks elke 10 eenheden
slider.setMinorTickSpacing(2);  // Kleine ticks elke 2 eenheden
```

### Ticks weergeven of verbergen {#show-or-hide-ticks}

U kunt de zichtbaarheid van tickmarks toggelen met de `setTicksVisible()` methode. Standaard zijn tickmarks verborgen.

```java
slider.setTicksVisible(true); // Toont tickmarks
slider.setTicksVisible(false); // Verbergt tickmarks
```

### Snapping {#snapping}

Om ervoor te zorgen dat de knop van de `Slider` uitlijnt met de dichtstbijzijnde tickmark tijdens de interactie van de gebruiker, schakelt u snapping in met de `setSnapToTicks()` methode:

```java
slider.setSnapToTicks(true); // Snapping inschakelen
```

Hier is een voorbeeld van een volledig geconfigureerde `Slider` die de instellingen voor grote en kleine ticks toont, samen met de snap-functionaliteit voor nauwkeurige aanpassingen:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Oriëntatie en inversie {#orientation-and-inversion}

De `Slider` component ondersteunt twee oriëntaties: horizontaal (standaard) en verticaal. U kunt de oriëntatie wijzigen om te voldoen aan uw UI-indeling en app-eisen.

Naast oriëntatie kan de `Slider` ook worden omgekeerd. Standaard:

- Een horizontale `Slider` gaat van minimum (links) naar maximum (rechts).
- Een verticale `Slider` gaat van minimum (onder) naar maximum (boven).

Bij inversie is deze richting omgekeerd. Gebruik de `setInverted(true)` methode om inversie in te schakelen.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Labels {#labels}

De `Slider` component ondersteunt labels op de tickmarks om gebruikers te helpen de waarden gemakkelijker te interpreteren. U kunt standaard numerieke labels gebruiken of aangepaste labels bieden, en u kunt hun zichtbaarheid naar behoefte in- of uitschakelen.

### Standaardlabels {#default-labels}

Standaard kan de slider numerieke labels weergeven op grote tickmarks. Deze waarden worden bepaald door de instelling `setMajorTickSpacing()`. Om standaardlabels in te schakelen, gebruikt u:

```java
slider.setLabelsVisible(true);
```

### Aangepaste labels {#custom-labels}

U kunt de standaard numerieke labels vervangen door aangepaste tekst met behulp van de `setLabels()` methode. Dit is nuttig wanneer u meer betekenisvolle waarden wilt weergeven (bijv. temperatuur, valuta of categorieën).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Koud",
  30, "Koel",
  50, "Gematigd",
  80, "Warm",
  100, "Heet"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Het wisselen van de zichtbaarheid van labels {#toggling-label-visibility}

Of u nu standaard- of aangepaste labels gebruikt, u kunt hun zichtbaarheid regelen met `setLabelsVisible(true)` of ze verbergen met `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Tooltips verbeteren de bruikbaarheid door de waarde van de `Slider` direct boven of onder de knop weer te geven, waardoor gebruikers nauwkeurigere aanpassingen kunnen maken. U kunt het gedrag, de zichtbaarheid en het formaat van de tooltip configureren naar wens.

Om tooltips in te schakelen, gebruikt u de `setTooltipVisible()` methode. Standaard zijn tooltips uitgeschakeld:

```java
slider.setTooltipVisible(true); // Tooltips inschakelen
slider.setTooltipVisible(false); // Tooltips uitschakelen
```

Tooltips kunnen ook worden geconfigureerd om alleen te verschijnen wanneer de gebruiker interactie heeft met de `Slider`. Gebruik de `setTooltipVisibleOnSlideOnly()` methode om dit gedrag in te schakelen. Dit is vooral nuttig om visuele drukte te verminderen en toch nuttige feedback te geven tijdens interactie.

Hier is een voorbeeld van een volledig geconfigureerde `Slider` met tooltips:


### Tooltip-aanpassing {#tooltip-customization}

Standaard toont de `Slider` een tooltip met zijn huidige waarde. Als u deze tekst wilt aanpassen, gebruikt u de `setTooltipText()` methode. Dit is nuttig wanneer u wilt dat de tooltip statische of beschrijvende tekst toont in plaats van de live waarde.

U kunt ook een JavaScript-expressie gebruiken om de tooltip dynamisch te formatteren. Als uw expressie het `return`- sleutelwoord bevat, wordt deze zoals deze is gebruikt. Als dat niet het geval is, wordt deze automatisch omgeven door `return` en `;` om een geldige functie te vormen. Bijvoorbeeld:

```java
// Toont waarde gevolgd door een dollar-teken
slider.setTooltipText("return x + '$'"); 
```

Of simpelweg:

```java
// Geïntrepreteerd als: return x + ' eenheden';
slider.setTooltipText("x + ' eenheden'"); 
```


## Styling {#styling}

### Thema's {#themes}

De `Slider` wordt geleverd met 6 ingebouwde thema's voor snelle styling zonder het gebruik van CSS. Theming wordt ondersteund door een ingebouwde enumklasse.
Hieronder staan sliders met elk van de ondersteunde thema's toegepast:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
