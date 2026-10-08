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

Die `Slider`-Komponente bietet den Benutzern die Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Regler entlang einer Spur zwischen einem minimalen und einem maximalen Wert ziehen. Schrittintervalle, Tick-Marken und Beschriftungen können konfiguriert werden, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

Ein neuer `Slider` erstreckt sich über einen Bereich von 0 bis 100 mit einem Startwert von 50, sodass er ohne weitere Einrichtung funktioniert. Eigenschaften wie Orientierung, Tick-Marken, Beschriftungen und Tooltips decken spezifischere Fälle ab, wie die Lautstärkeregelung unten.

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert stellt die aktuelle Position des Reglers auf dem Slider dar und wird als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, sodass er eine wesentliche Eigenschaft zur Verfolgung der Benutzereingabe darstellt.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, wobei der Standardbereich von 0 bis 100 angenommen wird.
:::

### Werte setzen und abrufen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` bei der Initialisierung festlegen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();
System.out.println("Aktueller Slider-Wert: " + value);
```

## Minimal- und Maximalwerte {#minimum-and-maximum-values}

Die Minimal- und Maximalwerte definieren den zulässigen Bereich des `Sliders` und bestimmen die Grenzen, innerhalb derer sich der Regler bewegen kann. Standardmäßig ist der Bereich von 0 bis 100, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben einen Standardwert von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang der Slider-Spur verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Unten finden Sie ein Beispiel zur Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Tick-Marken angezeigt werden und wie der Regler mit ihnen interagiert. Dies umfasst das Anpassen des Abstands zwischen den Haupt- und Unterticks, das Anzeigen/Verstecken von Tick-Marken und das Aktivieren von Snapping zu den Tick-Marken für eine präzise Benutzereingabe.

### Abstände zwischen Haupt- und Untertickets {#major-and-minor-tick-spacing}

Sie können den Abstand für Haupt- und Untertickets definieren, der bestimmt, wie häufig sie auf der `Slider`-Spur erscheinen:

- Hauptticks sind größer und oft mit Schlüsselwerten beschriftet.
- Unterticks sind kleiner und erscheinen zwischen den Hauptticks, um feinere Intervalle zu bieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Hauptticks alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Unterticks alle 2 Einheiten
```

### Ticks anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit von Tick-Marken mithilfe der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tick-Marken ausgeblendet.

```java
slider.setTicksVisible(true); // Tick-Marken anzeigen
slider.setTicksVisible(false); // Tick-Marken ausblenden
```

### Snapping {#snapping}

Um sicherzustellen, dass der `Slider`-Regler während der Benutzereingabe mit der nächstgelegenen Tick-Marke ausgerichtet ist, aktivieren Sie das Snapping mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Snapping aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der die Einstellungen für Haupt- und Unterticks sowie die Snapping-Funktionalität für präzise Anpassungen zeigt:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientierung und Inversion {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung ändern, um Ihrer UI-Anordnung und den Anforderungen Ihrer App gerecht zu werden.

Zusätzlich zur Orientierung kann der `Slider` auch invertiert werden. Standardmäßig:

- Ein horizontaler `Slider` verläuft von minimal (links) nach maximal (rechts).
- Ein vertikaler `Slider` verläuft von minimal (unten) nach maximal (oben).

Bei Inversion wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Inversion zu aktivieren.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen auf den Tick-Marken, um den Benutzern zu helfen, die Werte leichter zu interpretieren. Sie können standardmäßige numerische Beschriftungen verwenden oder benutzerdefinierte anbieten, und Sie können deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an den Haupt-Tick-Marken anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um standardmäßige Beschriftungen zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die standardmäßigen numerischen Beschriftungen durch benutzerdefinierten Text mit der Methode `setLabels()` ersetzen. Dies ist hilfreich, wenn Sie bedeutungsvollere Werte (z. B. Temperatur, Währung oder Kategorien) anzeigen möchten.

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

### Sichtbarkeit von Beschriftungen umschalten {#toggling-label-visibility}

Ob Sie standardmäßige oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Regler anzeigen, was den Benutzern hilft, präzisere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips an Ihre Bedürfnisse anpassen.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur erscheinen, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und dennoch während der Interaktion hilfreiches Feedback zu geben.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:

### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anzeigt, anstatt den aktuellen Wert.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird es unverändert verwendet. Andernfalls wird er automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

```java
// Zeigt den Wert gefolgt von einem Dollarzeichen an
slider.setTooltipText("return x + '$'");
```

Oder einfach:

```java
// Wird interpretiert als: return x + ' Einheiten';
slider.setTooltipText("x + ' Einheiten'");
```

## Styling {#styling}

### Themen {#themes}

Der `Slider` verfügt über 6 integrierte Themen für eine schnelle Stilisierung ohne die Verwendung von CSS. Das Styling wird durch die Verwendung einer integrierten Enum-Klasse unterstützt.
Unten sind Slider mit jedem unterstützten Thema zu sehen:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
