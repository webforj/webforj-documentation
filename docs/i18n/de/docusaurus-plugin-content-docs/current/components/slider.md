---
title: Slider
sidebar_position: 101
_i18n_hash: 490cb925a92ffd4860f74b00491402e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente bietet den Benutzern die Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Knopf entlang einer Leiste zwischen einem minimalen und maximalen Grenzwert ziehen. Schrittintervalle, Tick-Marken und Beschriftungen können konfiguriert werden, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `Slider` ist so konzipiert, dass er sofort einsatzbereit ist und keine zusätzliche Einrichtung benötigt, um effektiv zu funktionieren. Im Standardbereich reicht er von 0 bis 100 mit einem Startwert von 50, was ihn ideal für die schnelle Integration in jede Anwendung macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Tick-Marken, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es Benutzern ermöglicht, die Lautstärke innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfes auf dem Slider und wird als ganze Zahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, was ihn zu einer wesentlichen Eigenschaft für die Verfolgung der Benutzereingabe macht.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, vorausgesetzt, der Standardbereich liegt zwischen 0 und 100.
:::

### Wert setzen und abfragen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung setzen oder später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();  
System.out.println("Aktueller Slider-Wert: " + value);
```

## Minimale und maximale Werte {#minimum-and-maximum-values}

Die minimalen und maximalen Werte definieren den erlaubten Bereich des `Sliders`, der die Grenzen bestimmt, innerhalb derer sich der `Slider`-Knopf bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 festgelegt, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben einen Standardwert von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang der Schieberegelleiste verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Im Folgenden ein Beispiel für die Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Tick-Marken angezeigt werden und wie der Schieberegler-Knopf mit ihnen interagiert. Dies umfasst die Anpassung der Abstände zwischen den Haupt- und Neben-Ticks, das Anzeigen/Verstecken von Tick-Marken und das Aktivieren des Einrasts an Tick-Marken für präzise Benutzereingaben.

### Abstände zwischen Haupt- und Neben-Ticks {#major-and-minor-tick-spacing}

Sie können die Abstände für Haupt- und Neben-Tick-Marken definieren, die bestimmen, wie häufig sie auf der `Slider`-Leiste erscheinen:

- Haupt-Ticks sind größer und häufig beschriftet, um Schlüsselwerte zu repräsentieren.
- Neben-Ticks sind kleiner und erscheinen zwischen den Haupt-Ticks, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Haupt-Ticks alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Neben-Ticks alle 2 Einheiten
```

### Tick-Marken anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit der Tick-Marken mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tick-Marken ausgeblendet.

```java
slider.setTicksVisible(true); // Tick-Marken anzeigen
slider.setTicksVisible(false); // Tick-Marken ausblenden
```

### Einrasten {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf beim Benutzereingriff mit der nächsten Tick-Marke ausgerichtet ist, aktivieren Sie das Einrasten mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Einrasten aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der die Einstellungen für Haupt- und Neben-Ticks sowie die Einrastfunktion für präzise Anpassungen zeigt:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientierung und Umkehrung {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Ausrichtung an Ihr UI-Layout und Ihre App-Anforderungen anpassen.

Zusätzlich zur Ausrichtung kann der `Slider` auch umgekehrt werden. Standardmäßig:

- Ein horizontaler `Slider` reicht von minimal (links) bis maximal (rechts).
- Ein vertikaler `Slider` reicht von minimal (unten) bis maximal (oben).

Bei Umkehrung wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Umkehrung zu aktivieren.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen an Tick-Marken, um Benutzern zu helfen, die Werte besser zu interpretieren. Sie können Standardnummern beschriftungen verwenden oder benutzerdefinierte bereitstellen und deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an den Haupt-Tick-Marken anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardschilder zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Beschriftungen {#custom-labels}

Sie können die Standardnummern beschriftungen durch benutzerdefinierte Texte mit der Methode `setLabels()` ersetzen. Dies ist hilfreich, wenn Sie bedeutungsvollere Werte anzeigen möchten (z.B. Temperatur, Währung oder Kategorien).

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

### Sichtbarkeit der Beschriftungen umschalten {#toggling-label-visibility}

Egal, ob Sie Standard- oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

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

Tooltips können auch so konfiguriert werden, dass sie nur angezeigt werden, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelles Durcheinander zu vermeiden, während dennoch hilfreiches Feedback während der Interaktion bereitgestellt wird.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:


### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert an. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anzeigt, anstelle des aktuellen Wertes.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird es unverändert verwendet. Andernfalls wird es automatisch mit `return` und `;` umwickelt, um eine gültige Funktion zu bilden. Zum Beispiel:

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

Der `Slider` verfügt über 6 integrierte Themen für schnelles Styling ohne die Verwendung von CSS. Die Themen werden durch die Verwendung einer integrierten Enum-Klasse unterstützt.
Nachfolgend sind Slider mit jedem der unterstützten Themen dargestellt:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
