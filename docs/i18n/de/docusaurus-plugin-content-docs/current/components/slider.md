---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Die `Slider`-Komponente in webforJ bietet eine interaktive Steuerung, mit der Benutzer einen Wert innerhalb eines bestimmten Bereichs auswählen können, indem sie einen Knopf bewegen. Diese Funktion ist besonders nützlich für Apps, die präzise oder intuitive Eingaben erfordern, z. B. bei der Auswahl von Lautstärken, Prozentsätzen oder anderen veränderbaren Werten.

## Grundlagen {#basics}

Der `Slider` ist so konzipiert, dass er sofort einsatzbereit ist und keine zusätzliche Einrichtung benötigt, um effektiv zu funktionieren. Standardmäßig erstreckt er sich über einen Bereich von 0 bis 100 mit einem Startwert von 50, was ihn ideal für eine schnelle Integration in jede App macht. Für spezifischere Anwendungsfälle kann der `Slider` mit Eigenschaften wie Orientierung, Tick-Markierungen, Labels und Tooltips angepasst werden.

Hier ist ein Beispiel für einen `Slider`, der es Benutzern ermöglicht, Lautstärkepegel innerhalb eines vordefinierten Bereichs anzupassen:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-Wert {#slider-value}

Der `Slider`-Wert repräsentiert die aktuelle Position des Knopfes auf dem Slider und wird als Ganzzahl innerhalb des Bereichs des `Sliders` definiert. Dieser Wert wird dynamisch aktualisiert, während der Benutzer mit dem Slider interagiert, was ihn zu einer wesentlichen Eigenschaft zur Verfolgung der Benutzereingabe macht.

:::tip Standardwert
Standardmäßig beginnt der `Slider` mit einem Wert von 50, der den Standardbereich von 0 bis 100 übernimmt.
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

Die minimalen und maximalen Werte definieren den zulässigen Bereich des `Sliders`, der die Grenzen bestimmt, innerhalb derer sich der `Slider`-Knopf bewegen kann. Standardmäßig ist der Bereich von 0 bis 100 eingestellt, aber Sie können diese Werte an Ihre Bedürfnisse anpassen.

Die Intervalle auf dem `Slider` haben einen Standardabstand von 1, was bedeutet, dass die Anzahl der Intervalle durch den Bereich bestimmt wird. Zum Beispiel:
- Ein Slider mit einem Bereich von 0 bis 10 hat 10 Intervalle.
- Ein Slider mit einem Bereich von 0 bis 100 hat 100 Intervalle.

Diese Intervalle sind gleichmäßig entlang des Slider-Tracks verteilt, wobei der Abstand von den Abmessungen des `Sliders` abhängt.

Im Folgenden ein Beispiel für die Erstellung eines `Sliders` mit einem benutzerdefinierten Bereich:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tick-Konfiguration {#tick-configuration}

Die `Slider`-Komponente bietet eine flexible Tick-Konfiguration, mit der Sie anpassen können, wie Tick-Markierungen angezeigt werden und wie der Slider-Knopf mit ihnen interagiert. Dies umfasst die Anpassung des Abstands zwischen den Haupt- und Nebenmarkierungen, das Anzeigen/Verstecken von Tick-Markierungen und die Möglichkeit, am Tick zu schnappen, um präzise Benutzereingaben zu ermöglichen.

### Haupt- und Nebenmarkierungsabstand {#major-and-minor-tick-spacing}

Sie können den Abstand für Haupt- und Nebenmarkierungen definieren, der bestimmt, wie häufig sie auf dem `Slider`-Track erscheinen:

- Hauptmarkierungen sind größer und oft beschriftet, um Schlüsselwerte darzustellen.
- Nebenmarkierungen sind kleiner und erscheinen zwischen den Hauptmarkierungen, um feinere Intervalle anzubieten.

Setzen Sie den Tick-Abstand mit den folgenden Methoden `setMajorTickSpacing()` und `setMinorTickSpacing()`:
```java
slider.setMajorTickSpacing(10); // Hauptmarkierungen alle 10 Einheiten
slider.setMinorTickSpacing(2);  // Nebenmarkierungen alle 2 Einheiten
```

### Tick-Markierungen anzeigen oder ausblenden {#show-or-hide-ticks}

Sie können die Sichtbarkeit von Tick-Markierungen mit der Methode `setTicksVisible()` umschalten. Standardmäßig sind Tick-Markierungen ausgeblendet.

```java
slider.setTicksVisible(true); // Tick-Markierungen anzeigen
slider.setTicksVisible(false); // Tick-Markierungen ausblenden
```

### Schnappen {#snapping}

Um sicherzustellen, dass der `Slider`-Knopf während der Benutzereingabe mit der nächstgelegenen Tick-Markierung ausgerichtet ist, aktivieren Sie das Schnappen mit der Methode `setSnapToTicks()`:

```java
slider.setSnapToTicks(true); // Schnappen aktivieren
```

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider`, der Haupt- und Nebenmarkierungseinstellungen sowie die Schnappfähigkeit für präzise Anpassungen zeigt:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientierung und Inversion {#orientation-and-inversion}

Die `Slider`-Komponente unterstützt zwei Orientierungen: horizontal (Standard) und vertikal. Sie können die Orientierung ändern, um Ihrem UI-Layout und den Anforderungen der App gerecht zu werden.

Zusätzlich zur Orientierung kann der `Slider` auch invertiert werden. Standardmäßig:

- Ein horizontaler `Slider` reicht von minimal (links) bis maximal (rechts).
- Ein vertikaler `Slider` reicht von minimal (unten) bis maximal (oben).

Im umgekehrten Fall wird diese Richtung umgekehrt. Verwenden Sie die Methode `setInverted(true)`, um die Inversion zu aktivieren.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Labels {#labels}

Die `Slider`-Komponente unterstützt Labels auf Tick-Markierungen, um Benutzern das Interpretieren der Werte zu erleichtern. Sie können Standardnumerische Labels verwenden oder benutzerdefinierte verwenden und ihre Sichtbarkeit nach Bedarf umschalten.

### Standard-Labels {#default-labels}

Standardmäßig kann der Slider numerische Labels an Hauptmarkierungen anzeigen. Diese Werte werden durch die Einstellung `setMajorTickSpacing()` bestimmt. Um Standard-Labels zu aktivieren, verwenden Sie:

```java
slider.setLabelsVisible(true);
```

### Benutzerdefinierte Labels {#custom-labels}

Sie können die Standardnumerischen Labels durch benutzerdefinierten Text ersetzen, indem Sie die Methode `setLabels()` verwenden. Dies ist hilfreich, wenn Sie aussagekräftigere Werte anzeigen möchten (z. B. Temperatur, Währung oder Kategorien).

```java
Map<Integer, String> customLabels = Map.of(
    0, "Kalt",
    30, "Kühl",
    50, "Mittel",
    80, "Warm",
    100, "Heiß"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Sichtbarkeit der Labels umschalten {#toggling-label-visibility}

Egal, ob Sie Standard- oder benutzerdefinierte Labels verwenden, Sie können ihre Sichtbarkeit mit `setLabelsVisible(true)` steuern oder sie mit `setLabelsVisible(false)` ausblenden.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Tooltips {#tooltips}

Tooltips verbessern die Benutzerfreundlichkeit, indem sie den Wert des `Sliders` direkt über oder unter dem Knopf anzeigen und den Benutzern helfen, genauere Anpassungen vorzunehmen. Sie können das Verhalten, die Sichtbarkeit und das Format des Tooltips nach Ihren Bedürfnissen konfigurieren.

Um Tooltips zu aktivieren, verwenden Sie die Methode `setTooltipVisible()`. Standardmäßig sind Tooltips deaktiviert:

```java
slider.setTooltipVisible(true); // Tooltips aktivieren
slider.setTooltipVisible(false); // Tooltips deaktivieren
```

Tooltips können auch so konfiguriert werden, dass sie nur erscheinen, wenn der Benutzer mit dem `Slider` interagiert. Verwenden Sie die Methode `setTooltipVisibleOnSlideOnly()`, um dieses Verhalten zu aktivieren. Dies ist besonders nützlich, um visuelle Unordnung zu reduzieren und gleichzeitig hilfreiches Feedback während der Interaktion bereitzustellen.

Hier ist ein Beispiel für einen vollständig konfigurierten `Slider` mit Tooltips:

### Tooltip-Anpassung {#tooltip-customization}

Standardmäßig zeigt der `Slider` einen Tooltip mit seinem aktuellen Wert. Wenn Sie diesen Text anpassen möchten, verwenden Sie die Methode `setTooltipText()`. Dies ist nützlich, wenn der Tooltip statischen oder beschreibenden Text anstelle des aktuellen Wertes anzeigen soll.

Sie können auch einen JavaScript-Ausdruck verwenden, um den Tooltip dynamisch zu formatieren. Wenn Ihr Ausdruck das Schlüsselwort `return` enthält, wird es unverändert verwendet. Andernfalls wird er automatisch mit `return` und `;` umschlossen, um eine gültige Funktion zu bilden. Zum Beispiel:

```java
// Zeigt Wert gefolgt von einem Dollarzeichen
slider.setTooltipText("return x + '$'"); 
```

Oder einfach:

```java
// Wird interpretiert als: return x + ' Einheiten';
slider.setTooltipText("x + ' Einheiten'"); 
```

## Styling {#styling}

### Themen {#themes}

Der `Slider` wird mit 6 integrierten Themen geliefert, um eine schnelle Stilisierung ohne CSS zu ermöglichen. Die Themen werden durch die Verwendung einer integrierten Enum-Klasse unterstützt. 
Unten sind Slider mit jedem der unterstützten Themen angewendet:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
