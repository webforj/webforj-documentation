---
title: Slider
sidebar_position: 101
_i18n_hash: 77c71bf27e728d68c1e3381628b37a27
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente bietet den Benutzern die Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Knopf entlang einer Spur zwischen einem minimalen und maximalen Grenzwert ziehen. Schrittintervalle, Tickmarkierungen und Beschriftungen können konfiguriert werden, um die Auswahl zu leiten.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `Slider` wurde entwickelt, um sofort einsatzbereit zu sein und benötigt keine zusätzliche Konfiguration, um effektiv zu funktionieren. Standardmäßig erstreckt er sich über einen Bereich von 0 bis 100 mit einem Startwert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Tickmarkierungen, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es den Benutzern ermöglicht, die Lautstärke innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert stellt die aktuelle Position des Knopfes auf dem Slider dar und wird als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, und ist eine wesentliche Eigenschaft zur Nachverfolgung der Benutzereingabe.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, vorausgesetzt, der Standardbereich reicht von 0 bis 100.
:::

### Wert setzen und abrufen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung setzen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();  
System.out.println("Aktueller Slider-Wert: " + value);
```

## Mindest- und Höchstwerte {#minimum-and-maximum-values}

Die Mindest- und Höchstwerte definieren den zulässigen Bereich des `Sliders` und bestimmen die Grenzen, innerhalb derer sich der Knopf des `Sliders` bewegen kann. Standardmäßig liegt der Bereich bei 0 bis 100, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben standardmäßig einen Schritt von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Beispielsweise:
- Ein Slider mit einem Bereich von 0 bis 10 wird 10 Intervalle haben.
- Ein Slider mit einem Bereich von 0 bis 100 wird 100 Intervalle haben.

Diese Intervalle sind gleichmäßig entlang des Slider-Tracks verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Nachfolgend ein Beispiel zur Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Tickmarkierungen angezeigt werden und wie der Slider-Knopf mit ihnen interagiert. Dazu gehört das Anpassen des Abstands zwischen den großen und kleinen Tickmarkierungen, das Anzeigen/Verstecken von Tickmarkierungen und das Aktivieren des Snappings zu Tickmarkierungen für präzise Benutzereingaben.

### Abstand zwischen großen und kleinen Tickmarkierungen {#major-and-minor-tick-spacing}

Sie können den Abstand für große und kleine Tickmarkierungen festlegen, was bestimmt, wie häufig sie auf der `Slider`-Spur erscheinen:

- Große Tickmarkierungen sind größer und oft beschriftet, um wichtige Werte darzustellen.
- Kleine Tickmarkierungen sind kleiner und erscheinen zwischen großen Tickmarkierungen, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Große Tickmarkierungen alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Kleine Tickmarkierungen alle 2 Einheiten
```

### Tickmarkierungen anzeigen oder verstecken {#show-or-hide-ticks}

Sie können die Sichtbarkeit von Tickmarkierungen mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tickmarkierungen ausgeblendet.

```java
slider.setTicksVisible(true); // Tickmarkierungen anzeigen
slider.setTicksVisible(false); // Tickmarkierungen verstecken
```

### Snapping {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf während der Benutzerinteraktion mit der nächstgelegenen Tickmarkierung ausgerichtet ist, aktivieren Sie das Snapping mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Snapping aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der die Einstellungen für große und kleine Tickmarkierungen zusammen mit der Snapping-Funktionalität für präzise Anpassungen zeigt:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientierung und Inversion {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung ändern, um Ihren UI-Layout- und App-Anforderungen gerecht zu werden.

Neben der Orientierung kann der `Slider` auch invertiert werden. Standardmäßig:

- Ein horizontaler `Slider` geht von minimal (links) nach maximal (rechts).
- Ein vertikaler `Slider` geht von minimal (unten) nach maximal (oben).

Bei einer Inversion wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Inversion zu aktivieren.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '440px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen auf Tickmarkierungen, um den Benutzern zu helfen, die Werte leichter zu interpretieren. Sie können Standardnumerische Beschriftungen oder benutzerdefinierte Beschriftungen verwenden und deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an großen Tickmarkierungen anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardbeschriftungen zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die Standardnumerischen Beschriftungen durch benutzerdefinierten Text ersetzen, indem Sie die Methode `setLabels()` verwenden. Dies ist hilfreich, wenn Sie bedeutungsvollere Werte (z. B. Temperatur, Währung oder Kategorien) anzeigen möchten.

```java
Map<Integer, String> customLabels = Map.of(
  0, "Kalt",
  30, "Kühl",
  50, "Gemäßigt",
  80, "Warm",
  100, "Heiß"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Sichtbarkeit der Beschriftungen umschalten {#toggling-label-visibility}

Egal, ob Sie Standard- oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

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

Tooltips können auch so konfiguriert werden, dass sie nur angezeigt werden, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und dennoch hilfreiches Feedback während der Interaktion bereitzustellen.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:

### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` ein Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anstelle des aktuellen Wertes anzeigt.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird er so verwendet, wie er ist. Andernfalls wird er automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

```java
// Zeigt den Wert gefolgt von einem Dollarzeichen an
slider.setTooltipText("return x + '$'"); 
```

Oder einfach:

```java
// Interpretiert als: return x + ' Einheiten';
slider.setTooltipText("x + ' Einheiten'"); 
```


## Styling {#styling}

### Themen {#themes}

Der `Slider` bietet 6 integrierte Themen für eine schnelle Gestaltung ohne die Verwendung von CSS. Das Thema wird durch die Verwendung einer integrierten Enum-Klasse unterstützt.
Nachfolgend sind Slider mit jeweils angewendeten unterstützten Themen dargestellt:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
