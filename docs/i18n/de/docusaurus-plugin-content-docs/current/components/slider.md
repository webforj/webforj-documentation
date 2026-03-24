---
title: Slider
sidebar_position: 101
_i18n_hash: 16e62c6e021597448e33a04ebfd6f201
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente bietet den Nutzern die Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Knopf entlang einer Spur zwischen einem Minimum und Maximum ziehen. Schrittintervalle, Tickmarken und Beschriftungen können konfiguriert werden, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `Slider` ist so konzipiert, dass er sofort einsatzbereit ist und keine zusätzliche Einrichtung erforderlich ist, um effektiv zu funktionieren. Standardmäßig erstreckt er sich über einen Bereich von 0 bis 100 mit einem Anfangswert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Ausrichtung, Tickmarken, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es den Benutzern ermöglicht, die Lautstärke innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfes auf dem Slider und wird als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, wenn der Benutzer mit dem Slider interagiert, wodurch er eine wesentliche Eigenschaft zur Verfolgung der Benutzereingaben darstellt.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, unter der Annahme, dass der Standardbereich von 0 bis 100 reicht.
:::

### Wert festlegen und abrufen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung festlegen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();  
System.out.println("Aktueller Slider-Wert: " + value);
```

## Minimale und maximale Werte {#minimum-and-maximum-values}

Die minimalen und maximalen Werte definieren den zulässigen Bereich des `Sliders` und bestimmen die Grenzen, innerhalb derer sich der `Slider`-Knopf bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 eingestellt, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben ein Standard-Schritt von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang der Slider-Spur verteilt, wobei der Abstand von den Dimensionen des `Sliders` abhängt.

Unten sehen Sie ein Beispiel für die Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Tickmarken angezeigt werden und wie der Slider-Knopf mit ihnen interagiert. Dazu gehört das Anpassen des Abstands zwischen großen und kleinen Tickmarken, das Anzeigen/Verstecken von Tickmarken und das Aktivieren des Snapping zu Tickmarken für eine präzise Benutzereingabe.

### Abstände zwischen großen und kleinen Tickmarken {#major-and-minor-tick-spacing}

Sie können den Abstand für große und kleine Tickmarken definieren, der bestimmt, wie häufig sie auf der `Slider`-Spur erscheinen:

- Große Tickmarken sind größer und oft beschriftet, um Schlüsselwerte darzustellen.
- Kleine Tickmarken sind kleiner und erscheinen zwischen großen Tickmarken, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Große Tickmarken alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Kleine Tickmarken alle 2 Einheiten
```

### Tickmarken anzeigen oder verbergen {#show-or-hide-ticks}

Sie können die Sichtbarkeit von Tickmarken mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tickmarken verborgen.

```java
slider.setTicksVisible(true); // Tickmarken anzeigen
slider.setTicksVisible(false); // Tickmarken verbergen
```

### Snapping {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf während der Benutzerinteraktion mit der nächsten Tickmarke ausgerichtet ist, aktivieren Sie das Snapping mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Snapping aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der die Einstellungen für große und kleine Ticks sowie die Snapping-Funktionalität für präzise Anpassungen zeigt:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Ausrichtung und Umkehrung {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Ausrichtungen: horizontal (Standard) und vertikal. Sie können die Ausrichtung ändern, um Ihrem UI-Layout und den Anforderungen Ihrer App gerecht zu werden.

Zusätzlich zur Ausrichtung kann der `Slider` auch umgekehrt werden. Standardmäßig:

- Ein horizontaler `Slider` erstreckt sich von Minimum (links) nach Maximum (rechts).
- Ein vertikaler `Slider` erstreckt sich von Minimum (unten) nach Maximum (oben).

Bei einer Umkehrung wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Umkehrung zu aktivieren.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen auf den Tickmarken, um den Benutzern zu helfen, die Werte leichter zu interpretieren. Sie können standardmäßige numerische Beschriftungen verwenden oder benutzerdefinierte Beschriftungen bereitstellen und deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an großen Tickmarken anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardbeschriftungen zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die standardmäßigen numerischen Beschriftungen durch benutzerdefinierten Text mit der Methode `setLabels()` ersetzen. Dies ist hilfreich, wenn Sie bedeutungsvollere Werte (z. B. Temperatur, Währung oder Kategorien) anzeigen möchten.

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

Egal, ob Sie Standard- oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` verbergen.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Knopf anzeigen und den Benutzern helfen, genauere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips an Ihre Bedürfnisse anpassen.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur erscheinen, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und gleichzeitig hilfreiches Feedback während der Interaktion zu bieten.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:

### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn der Tooltip statischen oder beschreibenden Text anstelle des Live-Wertes anzeigen soll.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird er unverändert verwendet. Wenn nicht, wird er automatisch mit `return` und `;` umwickelt, um eine gültige Funktion zu bilden. Zum Beispiel:

```java
// Zeigt Wert gefolgt von einem Dollarzeichen an
slider.setTooltipText("return x + '$'"); 
```

Oder einfach:

```java
// Wird interpretiert als: return x + ' Einheiten';
slider.setTooltipText("x + ' Einheiten'"); 
```

## Styling {#styling}

### Themen {#themes}

Der `Slider` verfügt über 6 integrierte Themen für schnelles Styling ohne den Einsatz von CSS. Das Thema wird durch die Verwendung einer integrierten Enum-Klasse unterstützt. Unten sind Slider mit jeweils aufgetragenen unterstützten Themen gezeigt:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
