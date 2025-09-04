---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente in webforJ bietet eine interaktive Steuerung, die es Benutzern ermöglicht, einen Wert innerhalb eines bestimmten Bereichs durch Bewegen eines Knopfs auszuwählen. Diese Funktion ist besonders nützlich für Anwendungen, die präzise oder intuitive Eingaben erfordern, wie z.B. das Auswählen von Lautstärken, Prozenten oder anderen einstellbaren Werten.

## Grundlagen {#basics}

Der `Slider` ist so konzipiert, dass er sofort und ohne zusätzliche Einrichtung effektiv funktioniert. Standardmäßig erstreckt er sich über einen Bereich von 0 bis 100 mit einem Startwert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Teilstrichen, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es Benutzern ermöglicht, die Lautstärke innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfs am Slider und ist als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, was ihn zu einer essenziellen Eigenschaft für die Verfolgung von Benutzereingaben macht.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, assuming the default range of 0 to 100.
:::

### Einstellen und Abrufen des Wertes {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung festlegen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();  
System.out.println("Aktueller Slider-Wert: " + value);
```

## Mindest- und Höchstwerte {#minimum-and-maximum-values}

Die Mindest- und Höchstwerte definieren den zulässigen Bereich des `Sliders`, der die Grenzen festlegt, innerhalb derer sich der Knopf des `Sliders` bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 eingestellt, jedoch können Sie diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle am `Slider` haben einen Standardabstand von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 wird 10 Intervalle haben.
- Ein Slider mit einem Bereich von 0 bis 100 wird 100 Intervalle haben.

Diese Intervalle sind gleichmäßig entlang der Slider-Leiste verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Hier ist ein Beispiel für die Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Teilstriche angezeigt werden und wie der Knopf des Sliders mit ihnen interagiert. Dazu gehört das Anpassen des Abstands zwischen großen und kleinen Teilstrichen, das Anzeigen/Verbergen von Teilstrichen und das Aktivieren des Snap-On-Ticks für präzise Benutzereingaben.

### Abstand zwischen großen und kleinen Teilstrichen {#major-and-minor-tick-spacing}

Sie können den Abstand für große und kleine Teilstriche definieren, was bestimmt, wie häufig sie auf der Slider-Leiste erscheinen:

- Große Teilstriche sind größer und oft beschriftet, um Schlüsselwerte darzustellen.
- Kleine Teilstriche sind kleiner und erscheinen zwischen großen Teilstrichen, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Große Teilstriche alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Kleine Teilstriche alle 2 Einheiten
```

### Teilstriche anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit von Teilstrichen mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Teilstriche verborgen.

```java
slider.setTicksVisible(true); // Zeigt Teilstriche an
slider.setTicksVisible(false); // Versteckt Teilstriche
```

### Snapping {#snapping}

Um sicherzustellen, dass der Knopf des `Sliders` während der Benutzerinteraktion mit dem nächstgelegenen Teilstrich ausgerichtet ist, aktivieren Sie das Snapping mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Snapping aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der die Einstellungen für große und kleine Teilstriche sowie die Snapping-Funktion für präzise Anpassungen zeigt:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientierung und Inversion {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung ändern, um sie an Ihr UI-Layout und Ihre Anwendungsanforderungen anzupassen.

Neben der Orientierung kann der `Slider` auch invertiert werden. Standardmäßig:

- Ein horizontaler `Slider` geht von Minimum (links) nach Maximum (rechts).
- Ein vertikaler `Slider` geht von Minimum (unten) nach Maximum (oben).

Bei umgekehrter Einstellung wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Inversion zu aktivieren.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen an Teilstrichen, um Benutzern zu helfen, die Werte leichter zu interpretieren. Sie können standardmäßige numerische Beschriftungen verwenden oder benutzerdefinierte bereitstellen, und die Sichtbarkeit bei Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an großen Teilstrichen anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardbeschriftungen zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die standardmäßigen numerischen Beschriftungen durch benutzerdefinierte Texte mit der Methode `setLabels()` ersetzen. Dies ist hilfreich, wenn Sie aussagekräftigere Werte anzeigen möchten (z. B. Temperatur, Währung oder Kategorien).

```java
Map<Integer, String> customLabels = Map.of(
    0, "Kalt",
    30, "Kühl",
    50, "Mäßig",
    80, "Warm",
    100, "Heiß"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Sichtbarkeit von Beschriftungen umschalten {#toggling-label-visibility}

Egal, ob Sie standardmäßige oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Knopf anzeigen, was den Benutzern hilft, genauere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips an Ihre Bedürfnisse anpassen.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur erscheinen, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und gleichzeitig hilfreiches Feedback während der Interaktion bereitzustellen.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:


### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anstelle des Live-Wertes anzeigt.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird es so verwendet, wie es ist. Andernfalls wird es automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

```java
// Zeigt Wert gefolgt von einem Dollarzeichen
slider.setTooltipText("return x + '$'"); 
```

Oder einfach:

```java
// Interpretiert als: return x + ' Einheiten';
slider.setTooltipText("x + ' Einheiten'"); 
```


## Styling {#styling}

### Themen {#themes}

Der `Slider` kommt mit 6 integrierten Themen für schnelles Styling ohne den Einsatz von CSS. Das Themen-Management wird durch die Verwendung einer integrierten Enum-Klasse unterstützt.
Unten sind Slider mit jedem der unterstützten Themen angezeigt:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
