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

Die `Slider`-Komponente bietet Benutzern die Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Knopf entlang einer Spur zwischen einem Mindest- und Höchstwert ziehen. Schrittintervalle, Tickmarkierungen und Beschriftungen können konfiguriert werden, um die Auswahl zu unterstützen.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `Slider` ist so konzipiert, dass er sofort einsatzbereit ist und keine zusätzliche Einrichtung benötigt, um effektiv zu funktionieren. Standardmäßig reicht er von 0 bis 100 mit einem Anfangswert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für speziellere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Tickmarkierungen, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es Benutzern ermöglicht, Lautstärkepegel innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfes auf dem Slider und wird als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, was ihn zu einer wesentlichen Eigenschaft zur Verfolgung der Benutzereingabe macht.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, vorausgesetzt, der Standardbereich reicht von 0 bis 100.
:::

### Wert festlegen und abrufen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung festlegen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();
System.out.println("Aktueller Slider-Wert: " + value);
```

## Mindest- und Höchstwerte {#minimum-and-maximum-values}

Die Mindest- und Höchstwerte definieren den zulässigen Bereich des `Sliders` und bestimmen die Grenzen, innerhalb derer sich der Slider-Knopf bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 gesetzt, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle am `Slider` haben einen Standardwert von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang der Slider-Spur verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Im Folgenden sehen Sie ein Beispiel für die Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, mit der Sie anpassen können, wie Tickmarkierungen angezeigt werden und wie der Slider-Knopf mit ihnen interagiert. Dazu gehört das Anpassen des Abstands zwischen großen und kleinen Ticks, das Anzeigen/Ausblenden von Tickmarkierungen und das Aktivieren des Einrasten an Tickmarkierungen für präzise Benutzereingaben.

### Abstände bei großen und kleinen Ticks {#major-and-minor-tick-spacing}

Sie können den Abstand für große und kleine Tickmarkierungen definieren, was bestimmt, wie häufig sie auf der Slider-Spur erscheinen:

- Große Ticks sind größer und oft beschriftet, um wichtige Werte darzustellen.
- Kleine Ticks sind kleiner und erscheinen zwischen großen Ticks, um feinere Intervalle anzubieten.

Setzen Sie den Tickabstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Große Ticks alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Kleine Ticks alle 2 Einheiten
```

### Ticks anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit der Tickmarkierungen mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tickmarkierungen ausgeblendet.

```java
slider.setTicksVisible(true); // Tickmarkierungen anzeigen
slider.setTicksVisible(false); // Tickmarkierungen ausblenden
```

### Einrasten {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf während der Benutzerinteraktion mit der nächsten Tickmarkierung ausgerichtet wird, aktivieren Sie das Einrasten mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Einrasten aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der große und kleine Tick-Einstellungen sowie die Einrastfähigkeit für präzise Anpassungen zeigt:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientierung und Umkehrung {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung ändern, um sie an Ihr UI-Layout und Ihre Anforderungen anzupassen.

Zusätzlich zur Orientierung kann der `Slider` auch umgekehrt werden. Standardmäßig:

- Ein horizontaler `Slider` reicht von Minimum (links) nach Maximum (rechts).
- Ein vertikaler `Slider` reicht von Minimum (unten) nach Maximum (oben).

Bei einer Umkehrung wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Umkehrung zu aktivieren.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen an Tickmarkierungen, um Benutzern das Interpretieren der Werte zu erleichtern. Sie können Standardnumeric-Beschriftungen oder benutzerdefinierte verwenden und deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an großen Tickmarkierungen anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardbeschriftungen zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die Standardnumeric-Beschriftungen durch benutzerdefinierte Texte mit der Methode `setLabels()` ersetzen. Dies ist hilfreich, wenn Sie bedeutungsvollere Werte anzeigen möchten (z.B. Temperatur, Währung oder Kategorien).

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

Egal, ob Sie Standard- oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder mit `setLabelsVisible(false)` ausblenden.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Knopf anzeigen und den Benutzern helfen, präzisere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips an Ihre Bedürfnisse anpassen.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur erscheinen, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelles Durcheinander zu reduzieren, während dennoch hilfreiches Feedback während der Interaktion bereitgestellt wird.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:


### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anstelle des Live-Wertes anzeigt.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird er so verwendet. Andernfalls wird er automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

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

Der `Slider` kommt mit 6 integrierten Themen für schnelles Styling ohne CSS. Die Themen werden durch eine eingebaute Enum-Klasse unterstützt.
Hier sind Slider mit jedem der unterstützten Themen angewendet:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
