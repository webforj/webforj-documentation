---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: a1c9e9a41325f2f1ffb75fd07204106a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente organisiert Elemente in einem responsiven, spaltenbasierten Layout, das sich basierend auf der verfügbaren Breite anpasst. Breakpoints und Ausrichtungen werden automatisch verwaltet, sodass der Aufbau von mehrspaltigen Formularen und Inhaltsgittern keine kundenspezifische responsive Logik erfordert.

<!-- INTRO_END -->

## Standardverhalten {#default-behavior}

Standardmäßig arrangiert ein `ColumnsLayout` Elemente in zwei Spalten und nimmt die volle Breite seines Elternelements ein. Die Anzeige kann mit Breakpoints und Ausrichtungsparametern weiter angepasst werden, die in den folgenden Abschnitten behandelt werden.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Horizontale Layouts 
Dies kann anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - ein ebenso leistungsstarkes Werkzeug für horizontale Layouts.
:::

## Breakpoints {#breakpoints}

Im Kern ist das `ColumnsLayout` so konzipiert, dass es ein flüssiges, gitterähnliches System bietet, das sich an die Breite seines Elternelements anpasst. Im Gegensatz zu traditionellen festen Gittersystemen ermöglicht dieses Layout Entwicklern, eine bestimmte Anzahl von Spalten bei einer bestimmten Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Dies ermöglicht es einem `ColumnsLayout`, sich nahtlos von einem eingeschränkten Raum auf kleinen Bildschirmen zu einem breiteren Bereich auf größeren Bildschirmen anzupassen und bietet einem Entwickler ein responsives Design, ohne dass eine kundenspezifische Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann mithilfe der `Breakpoint`-Klasse angegeben werden, die drei Parameter akzeptiert:

1. **Name (optional)**:
Die Benennung eines Breakpoints ermöglicht es Ihnen, darauf in zukünftigen Konfigurationen zu verweisen.

2. **Mindestbreite**:
Jeder Breakpoint hat einen bestimmten Bereich, der bestimmt, wann sein Layout angewendet wird. Die Mindestbreite ist ausdrücklich definiert, und der nächste Breakpoint bestimmt die maximale Breite, falls vorhanden. Sie können eine Ganzzahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben soll, mit dieser Ganzzahl.

:::info `Breakpoint`-Bewertung
Breakpoints werden in aufsteigender Reihenfolge der Breite bewertet, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
:::

### Anwendung von Breakpoints {#applying-breakpoints}

Breakpoints werden auf ein `ColumnsLayout` auf eine von zwei Arten angewendet: während der Konstruktion oder in einer `List` mit der Methode `setBreakpoints()`:

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

Die folgende Demonstration zeigt ein Beispiel, wie mehrere Breakpoints bei der Konstruktion eingestellt werden, und verwendet Breakpoints zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente. Außerdem wird die Resize-Fähigkeit des `ColumnsLayout` demonstriert, wenn die App verkleinert wird:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Spalten `Span` und Spannen pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Spannen in `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, wodurch Sie mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei variierenden Breiten haben. Dies ist besonders nützlich, wenn Sie möchten, dass bestimmte Elemente je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen.

### Verwaltung von Spalten-Spannen {#managing-column-spans}

Standardmäßig nimmt jedes Element im `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie die Spaltenspanne für einzelne Elemente festlegen. Eine Spanne gibt an, wie viele Spalten ein Element einnehmen soll.

```java
Button button = new Button("Klicken Sie mich");
layout.addComponent(button);
// Element spannt zwei Spalten
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten ein, anstelle der Standardansicht von einer. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts einnehmen soll.

### Anpassen der Spalten-Spannen mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch die Spalten-Spannen dynamisch basierend auf Breakpoints anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element unterschiedliche Anzahl an Spalten je nach Bildschirmgröße einnimmt. Beispielsweise möchten Sie möglicherweise, dass ein Element auf Mobilgeräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten einnimmt.

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
//Das E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout über verschiedene Geräte hinweg responsiv und angemessen strukturiert bleibt.

## Platzieren von Elementen innerhalb von Spalten {#placing-items-within-columns}

`ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, was mehr Kontrolle über die Anordnung der Elemente ermöglicht. Sie können manuell angeben, wo ein Element im Layout erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie gewünscht angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, von links nach rechts. Sie können dieses Verhalten jedoch überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er im Verhältnis zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Abschicken");
layout.addComponent(button);
// Platzieren Sie das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassen der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Ebenso wie bei den Spalten-Spannen verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout neu anzuordnen oder zu verlagern, während sich die Ansicht ändert.

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
//Das E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In der folgenden Demonstration beachten Sie, dass, wenn der `"medium"`-Breakpoint ausgelöst wird, das `email`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in der ersten Spalte platziert wird, anstatt in seiner Standardplatzierung in der zweiten Spalte:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spannen und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spannen und Platzierungen von Elementen in einer Reihe sich nicht überschneiden. Das Layout versucht, den Abstand automatisch gepflegt zu verwalten, aber eine sorgfältige Gestaltung von Spannen und Breakpoints verhindert eine unbeabsichtigte Anzeige von Elementen.
:::

## Vertikale und horizontale Elemente ausrichten {#vertical-and-horizontal-item-alignments}

Jedes Element im `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber bietet, wie der Inhalt im Layout positioniert ist.

**Die horizontale Ausrichtung** eines Elements wird mithilfe der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Die vertikale Ausrichtung** spezifiziert, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen sind:

- `START`: Richtet das Element links in der Spalte aus (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Richtet das Element rechts in der Spalte aus.
- `STRETCH`: Dehnt die Komponente aus, um das Layout zu füllen.
- `BASELINE`: Richtet basierend auf dem Text oder Inhalt innerhalb der Spalte aus und richtet Elemente an der Textbasislinie aus, anstatt an anderen Ausrichtungsoptionen.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In der obigen Demo wurde dem `Submit`-Button `ColumnsLayout.Alignment.END` zugewiesen, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts von seiner Spalte erscheint.

## Elementabstand {#item-spacing}

Die Kontrolle des Abstands zwischen den Spalten im `ColumnsLayout` zwischen den Spalten (horizontaler Abstand) und zwischen den Reihen (vertikaler Abstand) hilft Entwicklern, das Layout zu verfeinern.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setzen Sie 20px Abstand zwischen Spalten
layout.setHorizontalSpacing(20);  
```

Ebenso verwenden Sie die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setzen Sie 15px Abstand zwischen Reihen
layout.setVerticalSpacing(15);  
```

:::tip CSS-Einheiten
Sie können eine Ganzzahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Der Aufbau responsiver und ansprechender Layouts ist sowohl mit der [`FlexLayout`](./flex-layout)-Komponente als auch mit der `ColumnsLayout`-Komponente sowie einer Kombination aus beiden möglich. Im Folgenden sehen Sie ein Beispiel des im Artikel [Formular erstellt in der FlexLayout](./flex-layout#example-form), jedoch mit einem `ColumnLayout`-Schema:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
