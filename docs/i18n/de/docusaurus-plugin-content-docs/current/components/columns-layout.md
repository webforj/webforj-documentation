---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente arrangiert Elemente in einem responsiven, spaltenbasierten Layout, das sich basierend auf der verfügbaren Breite anpasst. Breakpoints und Ausrichtungen werden automatisch verwaltet, sodass der Aufbau von mehrspaltigen Formularen und Inhaltsgittern keine benutzerdefinierte responsive Logik erfordert.

<!-- INTRO_END -->

## Standardverhalten {#default-behavior}

Standardmäßig arrangiert eine `ColumnsLayout`-Komponente Elemente in zwei Spalten und nimmt die volle Breite ihres übergeordneten Elements ein. Die Anzeige kann weiter mit Breakpoints und Ausrichtungsoptionen angepasst werden, die in den folgenden Abschnitten behandelt werden.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale Layouts 
Dies kann anstelle von, oder in Kombination mit, der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Breakpoints {#breakpoints}

Im Kern wurde die `ColumnsLayout` entwickelt, um ein flüssiges, gitterartiges System bereitzustellen, das sich an die Breite seines übergeordneten Containers anpasst. Im Gegensatz zu traditionellen festen Gittersystemen ermöglicht dieses Layout Entwicklern, eine bestimmte Anzahl von Spalten bei einer gegebenen Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Dies erlaubt es einer `ColumnsLayout`, sich nahtlos von einem eingeschränkten Raum auf kleinen Bildschirmen zu einem größeren Bereich auf größeren Bildschirmen anzupassen und bietet Designern ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann unter Verwendung der `Breakpoint`-Klasse spezifiziert werden, die drei Parameter benötigt:

1. **Name (optional)**:
Einen Breakpoint zu benennen ermöglicht es, ihn in zukünftigen Konfigurationen zu referenzieren.

2. **Minimale Breite**:
Jeder Breakpoint hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die minimale Breite wird explizit definiert, und der nächste Breakpoint bestimmt die maximalen Breite, falls vorhanden. Sie können eine ganze Zahl verwenden, um die minimale Breite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben soll, indem Sie diese ganze Zahl angeben.

:::info `Breakpoint`-Auswertung
Breakpoints werden in aufsteigender Reihenfolge der Breite ausgewertet, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
:::

### Anwendung von Breakpoints {#applying-breakpoints}

Breakpoints werden auf eine `ColumnsLayout` auf zwei Arten angewendet: während der Konstruktion oder in einer `List` mithilfe der Methode `setBreakpoints()`:

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

Die folgende Demonstration zeigt ein Beispiel zur Festlegung mehrerer Breakpoints bei der Konstruktion, zur Konfiguration der [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente und veranschaulicht die Größenveränderungsfähigkeiten der `ColumnsLayout`, wenn die App verkleinert wird:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Spalte `Span` und Spans pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Spans in der `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, sodass Sie mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten haben. Dies ist besonders nützlich, wenn Sie möchten, dass bestimmte Elemente je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen.

### Verwaltung der Spalten-Spans {#managing-column-spans}

Standardmäßig nimmt jedes Element in der `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie den Spalten-Span für einzelne Elemente festlegen. Ein Span gibt an, wie viele Spalten ein Element einnehmen sollte.

```java
Button button = new Button("Klicke mich");
layout.addComponent(button);
// Element nimmt zwei Spalten ein
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten anstelle der standardmäßigen einen ein. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts spannen soll.

### Anpassung der Spalten-Spans mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch Spalten-Spans dynamisch basierend auf Breakpoints anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element eine unterschiedliche Anzahl von Spalten je nach Bildschirmgröße einnimmt. Beispielsweise möchten Sie vielleicht, dass ein Element auf mobilen Geräten eine einzelne Spalte belegt, aber auf größeren Bildschirmen mehrere Spalten spannt.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsiv und angemessen strukturiert bleibt über verschiedene Geräte hinweg.

## Platzierung von Elementen innerhalb von Spalten {#placing-items-within-columns}

Die `ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, was mehr Kontrolle über die Anordnung der Elemente ermöglicht. Sie können manuell angeben, wo ein Element innerhalb des Layouts erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie beabsichtigt angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, die von links nach rechts gefüllt wird. Sie können jedoch dieses Verhalten überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er relativ zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Einreichen");
layout.addComponent(button);
// Platziere das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassung der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genauso wie bei den Spalten-Spans verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout neu anzuordnen oder zu verlagern, während sich der Ansichtsbereich ändert.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In der folgenden Demonstration ist zu beachten, dass wenn der `"medium"` Breakpoint aktiviert wird, das `email`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in die erste Spalte platziert wird, anstatt in die zweite Spalte, wo es standardmäßig platziert wird:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spans und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spans und Platzierungen von Elementen in einer Reihe sich nicht überschneiden. Das Layout versucht automatisch, den Abstand elegant zu verwalten, aber eine sorgfältige Gestaltung von Spans und Breakpoints verhindert eine unbeabsichtigte Anzeige von Elementen.
:::

## Vertikale und horizontale Elemente Ausrichtungen {#vertical-and-horizontal-item-alignments}

Jedes Element in der `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber gibt, wie Inhalte im Layout positioniert sind.

**Die horizontale Ausrichtung** eines Elements wird über die Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Die vertikale Ausrichtung** gibt an, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen beinhalten:

- `START`: Richtet das Element an der linken Seite der Spalte aus (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Richtet das Element an der rechten Seite der Spalte aus.
- `STRETCH`: Dehnt die Komponente, um das Layout auszufüllen.
- `BASELINE`: richtet sich nach dem Text oder Inhalt in der Spalte aus und richtet die Elemente nach der Textbasislinie aus, anstatt nach anderen Ausrichtungsoptionen.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In der obigen Demo wurde dem `Einreichen`-Button `ColumnsLayout.Alignment.END` zugewiesen, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Elementabstände {#item-spacing}

Die Kontrolle des Abstands zwischen Spalten in der `ColumnsLayout`, zwischen Spalten (horizontaler Abstand) und zwischen Reihen (vertikaler Abstand) hilft Entwicklern, das Layout zu optimieren.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setze 20px Abstand zwischen den Spalten
layout.setHorizontalSpacing(20);  
```

Ebenso verwenden Sie die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setze 15px Abstand zwischen den Reihen
layout.setVerticalSpacing(15);  
```

:::tip CSS-Einheiten
Sie können eine ganze Zahl verwenden, um die minimale Breite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Es ist möglich, responsive und ansprechende Layouts sowohl mit der [`FlexLayout`](./flex-layout)-Komponente als auch der `ColumnsLayout`-Komponente sowie einer Kombination aus beiden zu erstellen. Unten finden Sie ein Beispiel des [in der FlexLayout](./flex-layout#example-form) Artikel erstellten Formulars, jedoch mit einem `ColumnLayout`-Schema:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
