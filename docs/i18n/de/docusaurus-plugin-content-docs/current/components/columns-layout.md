---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: 3705994e0ddda1a54c0582b290942549
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente ordnet Elemente in einem responsiven spaltenbasierten Layout an, das sich basierend auf der verfügbaren Breite anpasst. Breakpoints und Ausrichtungen werden automatisch verwaltet, sodass der Aufbau von mehrspaltigen Formularen und Inhaltsraster keine benutzerdefinierte responsive Logik erfordert.

<!-- INTRO_END -->

## Standardverhalten {#default-behavior}

Standardmäßig nimmt ein `ColumnsLayout` die volle Breite seines Elternteils ein und ordnet die Elemente in einer einzigen Spalte an und wechselt bei Breiten von `40em` und mehr in zwei Spalten.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale Layouts
Dies kann anstelle von, oder in Kombination mit, der [`FlexLayout`](./flex-layout) Komponente verwendet werden - ein ebenso leistungsfähiges Werkzeug für horizontale Layouts.
:::

## Breakpoints {#breakpoints}

Im Kern wurde das `ColumnsLayout` für ein flüssiges, gitterähnliches System konzipiert, das sich an die Breite seines Container-Elternteils anpasst. Im Gegensatz zu traditionellen festen Gitter-Systemen ermöglicht dieses Layout Entwicklern die Angabe einer Anzahl von Spalten bei einer bestimmten Breite und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Dies ermöglicht einem `ColumnsLayout`, sich reibungslos von einem engeren Raum auf kleinen Bildschirmen zu einem breiteren Bereich auf größeren Bildschirmen anzupassen und bietet einem Entwickler ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann unter Verwendung der `Breakpoint`-Klasse spezifiziert werden, die drei Parameter benötigt:

1. **Name (optional)**:
Ein `Breakpoint` zu benennen erlaubt Ihnen, ihn in zukünftigen Konfigurationen zu referenzieren.

2. **Minimale Breite**:
Jeder `Breakpoint` hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die minimale Breite wird explizit definiert, und der nächste `Breakpoint` bestimmt die maximale Breite, wenn sie vorhanden ist. Sie können eine Ganzzahl verwenden, um die Mindestbreite in Pixeln festzulegen oder eine `String`-Angabe verwenden, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein `Breakpoint` haben soll, mit dieser Ganzzahl.

:::info `Breakpoint`-Auswertung
Breakpoints werden in aufsteigender Reihenfolge der Breite ausgewertet, was bedeutet, dass das Layout den ersten passenden `Breakpoint` verwendet.
:::

### Anwenden von Breakpoints {#applying-breakpoints}

Breakpoints werden auf ein `ColumnsLayout` auf eine von zwei Arten angewendet: während der Konstruktion oder in einer `List` unter Verwendung der Methode `setBreakpoints()`:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Eine Spalte bei Breiten >= 0px
  new Breakpoint(0, 1),
  // Zwei Spalten bei Breiten >= 600px
  new Breakpoint(600, 2),
  // Vier Spalten bei Breiten >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

Die folgende Demonstration zeigt ein Beispiel für das Setzen mehrerer Breakpoints bei der Konstruktion, verwendet Breakpoints zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente und demonstriert die Resize-Fähigkeiten des `ColumnsLayout`, wenn die App verkleinert wird:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Spalten `Span` und Spannen pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Spannen im `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, wodurch Sie mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten erhalten. Dies ist besonders nützlich, wenn Sie möchten, dass bestimmte Elemente je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen.

### Verwaltung von Spalten-Spannen {#managing-column-spans}

Standardmäßig nimmt jedes Element im `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie die Spalten-Spanne für einzelne Elemente festlegen. Eine Spanne gibt die Anzahl der Spalten an, die ein Element einnehmen sollte.

```java
Button button = new Button("Klick mich");
layout.add(button);
// Element spannt über zwei Spalten
layout.setSpan(button, 2);
```

Im obigen Beispiel belegt der Button zwei Spalten anstelle der Standard von einer. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente im Layout einnehmen soll.

### Anpassung der Spalten-Spannen mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch die Spalten-Spannen dynamisch basierend auf Breakpoints anpassen. Dieses Feature ist nützlich, wenn Sie möchten, dass ein Element unterschiedliche Anzahl an Spalten je nach Bildschirmgröße einnimmt. Zum Beispiel möchten Sie, dass ein Element auf mobilen Geräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten.

```java
TextField email = new TextField("E-Mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// E-Mail-Feld wird zwei Spalten einnehmen, wenn der Medium-Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsiv und angemessen strukturiert auf verschiedenen Geräten bleibt.

## Platzierung von Elementen innerhalb der Spalten {#placing-items-within-columns}

Das `ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, was mehr Kontrolle über die Anordnung der Elemente ermöglicht. Sie können manuell angeben, wo ein Element innerhalb des Layouts erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie beabsichtigt angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, wobei von links nach rechts gefüllt wird. Sie können dieses Verhalten jedoch überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er relativ zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Absenden");
layout.add(button);
// Platzieren Sie das Element in der zweiten Spalte
layout.setColumn(button, 2);
```

### Anpassung der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genau wie bei den Spalten-Spannen verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout neu anzuordnen oder zu verschieben, während sich die Ansicht ändert.

```java
TextField email = new TextField("E-Mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der Medium-Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2);
//...
```

In der folgenden Demonstration stellen Sie fest, dass wenn der `"medium"`-Breakpoint aktiviert wird, das `E-Mail`-Feld beide Spalten einnimmt:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spannen und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spannen und Platzierungen der Elemente in einer Reihe sich nicht überlappen. Das Layout versucht, den Abstand automatisch ansprechend zu verwalten, aber eine sorgfältige Gestaltung der Spannen und Breakpoints verhindert eine unbeabsichtigte Anzeige von Elementen.
:::

## Vertikale und horizontale Elementeinrichtungen {#vertical-and-horizontal-item-alignments}

Jedes Element im `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle über die Positionierung des Inhalts im Layout gibt.

**Die horizontale Ausrichtung** eines Elements wird mit der Methode `setHorizontalAlignment()` gesteuert. Dieses Attribut bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Die vertikale Ausrichtung** legt fest, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen sind:

- `START`: Richtet das Element links in der Spalte aus.
- `CENTER`: Zentriert das Element horizontal in der Spalte.
- `END`: Richtet das Element rechts in der Spalte aus.
- `STRETCH`: Dehnt die Komponente, um das Layout auszufüllen.
- `BASELINE`: Richtet basierend auf dem Text oder Inhalt innerhalb der Spalte aus und richtet die Elemente an der Textbasislinie an, anstatt an anderen Ausrichtungsoptionen.
- `AUTO`: Automatische Ausrichtung (Standard).

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In der obigen Demo hat der `Absenden`-Button die `ColumnsLayout.Alignment.END`-Ausrichtung erhalten, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte angezeigt wird.

## Elementabstand {#item-spacing}

Die Kontrolle über den Abstand im `ColumnsLayout` zwischen den Spalten (horizontaler Abstand) und zwischen den Reihen (vertikaler Abstand) hilft Entwicklern, das Layout feinzutunen.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Legen Sie 20px Abstand zwischen den Spalten fest
layout.setHorizontalSpacing(20);
```

Verwenden Sie ebenso die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Legen Sie 15px Abstand zwischen den Reihen fest
layout.setVerticalSpacing(15);
```

:::tip CSS-Einheiten
Abstände akzeptieren eine Ganzzahl für Pixel oder eine `String`-Angabe für andere Einheiten wie `vw` oder `%`.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Das Erstellen responsiver und attraktiver Layouts ist sowohl mit der [`FlexLayout`](./flex-layout) Komponente als auch mit der `ColumnsLayout`-Komponente sowie in Kombination von beiden möglich. Nachfolgend finden Sie ein Beispiel des [in der FlexLayout erstellten Formulars](./flex-layout#example-form), jedoch anstelle dessen ein `ColumnLayout`-Schema verwendet:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
