---
title: Slider
sidebar_position: 101
_i18n_hash: 56140362edd92adde8d6114a8e6652c9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente bietet den Benutzern eine Möglichkeit, einen numerischen Wert auszuwählen, indem sie einen Knopf entlang einer Spur zwischen einer minimalen und maximalen Grenze ziehen. Schrittintervalle, Tickmarkierungen und Beschriftungen können konfiguriert werden, um die Auswahl zu unterstützen.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `Slider` wurde so gestaltet, dass er sofort einsatzbereit ist und keine zusätzliche Einrichtung benötigt, um effektiv zu funktionieren. Standardmäßig erstreckt er sich über einen Bereich von 0 bis 100 mit einem Startwert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Tickmarken, Beschriftungen und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der den Benutzern ermöglicht, die Lautstärke innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfes auf dem Slider und wird als ganzzahliger Wert im Bereich des `Sliders` definiert. Dieser Wert aktualisiert sich dynamisch, während der Benutzer mit dem Slider interagiert, was ihn zu einer wichtigen Eigenschaft zur Verfolgung der Benutzereingaben macht.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, wobei der Standardbereich von 0 bis 100 angenommen wird.
:::

### Wert setzen und abrufen {#setting-and-getting-the-value}

Sie können den Wert des `Sliders` während der Initialisierung setzen oder ihn später mit der Methode `setValue()` aktualisieren. Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Setzt den Slider auf 25

Integer value = slider.getValue();  
System.out.println("Aktueller Slider-Wert: " + value);
```

## Minimale und maximale Werte {#minimum-and-maximum-values}

Die minimalen und maximalen Werte definieren den zulässigen Bereich des `Sliders` und bestimmen die Grenzen, innerhalb derer sich der Knopf des `Sliders` bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 eingestellt, jedoch können Sie diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben einen Standard-Schritt von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang der Slider-Spur verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Hier ist ein Beispiel zur Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, die es Ihnen ermöglicht, anzupassen, wie Tickmarken angezeigt werden und wie der Slider-Knopf mit ihnen interagiert. Dazu gehört die Anpassung des Abstands zwischen Haupt- und Nebenmarkierungen, das Anzeigen/Verstecken von Tickmarken und die Aktivierung des Einrasts an Tickmarken für eine präzise Benutzereingabe.

### Haupt- und Nebentick-Abstände {#major-and-minor-tick-spacing}

Sie können den Abstand für Haupt- und Nebentickmarken festlegen, was bestimmt, wie häufig sie auf der Slider-Spur erscheinen:

- Hauptticks sind größer und oft beschriftet, um wichtige Werte darzustellen.
- Nebenticks sind kleiner und erscheinen zwischen Hauptticks, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Hauptticks alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Nebenticks alle 2 Einheiten
```

### Ticks anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit der Tickmarken mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tickmarken ausgeblendet.

```java
slider.setTicksVisible(true); // Tickmarken anzeigen
slider.setTicksVisible(false); // Tickmarken ausblenden
```

### Einrasten {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf während der Benutzerinteraktion mit der nächsten Tickmarke ausgerichtet wird, aktivieren Sie das Einrasten mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Einrasten aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der Haupt- und Nebentick-Einstellungen zusammen mit der Einrastfunktion für präzise Anpassungen zeigt:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientierung und Inversion {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung an Ihr UI-Layout und Ihre App-Anforderungen anpassen.

Neben der Orientierung kann der `Slider` auch invertiert werden. Standardmäßig:

- Ein horizontaler `Slider` reicht von Minimum (links) bis Maximum (rechts).
- Ein vertikaler `Slider` reicht von Minimum (unten) bis Maximum (oben).

Bei einer Inversion wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Inversion zu aktivieren.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Beschriftungen {#labels}

Die `Slider`-Komponente unterstützt Beschriftungen auf Tickmarken, um den Benutzern zu helfen, die Werte leichter zu interpretieren. Sie können standardmäßige numerische Beschriftungen verwenden oder benutzerdefinierte angeben und deren Sichtbarkeit nach Bedarf umschalten.

### Standardbeschriftungen {#default-labels}

Standardmäßig kann der Slider numerische Beschriftungen an den Haupttickmarken anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standardbeschriftungen zu aktivieren, verwenden Sie:

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

### Sichtbarkeit der Beschriftungen umschalten {#toggling-label-visibility}

Ob Sie Standard- oder benutzerdefinierte Beschriftungen verwenden, Sie können deren Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Knopf anzeigen und den Benutzern helfen, präzisere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips an Ihre Bedürfnisse anpassen.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur angezeigt werden, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und gleichzeitig hilfreiches Feedback während der Interaktion zu bieten.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:

### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn Sie möchten, dass der Tooltip statischen oder beschreibenden Text anzeigt, anstatt den Live-Wert.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird er unverändert verwendet. Andernfalls wird er automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

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

Der `Slider` verfügt über 6 integrierte Themen für eine schnelle Gestaltung ohne die Verwendung von CSS. Die Themen werden durch eine eingebaute Enum-Klasse unterstützt.
Nachfolgend sind Slider mit jeweils angewendeten unterstützten Themen angezeigt:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
