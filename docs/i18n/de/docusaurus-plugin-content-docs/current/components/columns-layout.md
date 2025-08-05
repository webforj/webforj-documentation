---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: ed7626149e8b31e663de874e83935567
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente in webforJ ermöglicht es Entwicklern, Layouts mit einem flexiblen und responsiven vertikalen Layout zu erstellen. Dieses Layout bietet dynamische Spalten, die sich basierend auf der verfügbaren Breite anpassen. Diese Komponente vereinfacht die Erstellung von Mehrspalten-Layouts, indem sie automatisch mit Breakpoints und Ausrichtungen umgeht.

:::info Horizontale Layouts
Dies kann anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Grundlagen {#basics}

Bei der ersten Instanziierung verwendet die `ColumnsLayout` zwei Spalten, um die hinzugefügten Elemente anzuzeigen. Standardmäßig nimmt sie die gesamte Breite ihrer übergeordneten Elemente ein und wächst nach Bedarf, um zusätzlichen Inhalt unterzubringen. Die Anzeige der hinzugefügten Elemente kann weiter kalibriert werden, indem die Einstellungen [`Breakpoint`](./columns-layout#breakpoints) und [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) verwendet werden, die in den folgenden Abschnitten näher erläutert werden.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breakpoints {#breakpoints}

Im Kern ist die `ColumnsLayout` so konzipiert, dass sie ein flüssiges, gitterähnliches System bietet, das sich an die Breite des übergeordneten Containers anpasst. Im Gegensatz zu herkömmlichen festen Gitter-Systemen ermöglicht dieses Layout den Entwicklern, eine Anzahl von Spalten bei einer gegebenen Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf den festgelegten `Breakpoint`-Objekten.

Dies ermöglicht es der `ColumnsLayout`, sich geschmeidig von einem eingeschränkten Raum auf kleinen Bildschirmen zu einem breiteren Bereich auf größeren Bildschirmen anzupassen und bietet damit ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann unter Verwendung der `Breakpoint`-Klasse angegeben werden, die drei Parameter benötigt:

1. **Name (optional)**:
Die Benennung eines Breakpoints ermöglicht es, ihn in zukünftigen Konfigurationen zu referenzieren.

2. **Mindestbreite**:
Jeder Breakpoint hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die Mindestbreite wird explizit definiert, und der nächste Breakpoint bestimmt die Höchstbreite, falls vorhanden. Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` zu spezifizieren.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben soll, indem Sie diese ganze Zahl verwenden.

:::info `Breakpoint`-Evaluation
Breakpoints werden in aufsteigender Reihenfolge der Breite evaluiert, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
:::

### Anwendung von Breakpoints {#applying-breakpoints}

Breakpoints werden auf eine `ColumnsLayout` auf eine von zwei Arten angewendet: während der Konstruktion oder durch Verwendung der Methode `addBreakpoint(Breakpoint)`, wie unten gezeigt.

```java
ColumnsLayout layout = new ColumnsLayout()
    // Eine Spalte bei Breiten >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Zwei Spalten bei Breiten >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Vier Spalten bei Breiten >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

Die folgende Demonstration zeigt ein Beispiel für die Festlegung mehrerer Breakpoints beim Bau, die Verwendung von Breakpoints zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente und demonstriert die Größenänderungsfähigkeiten der `ColumnsLayout`, wenn die App resized wird:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Spalten `Span` und Spannen pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spaltenspannen in `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, was Ihnen mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten gibt. Dies ist besonders nützlich, wenn Sie bestimmten Elementen je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz geben möchten.

### Verwaltung von Spaltenspannen {#managing-column-spans}

Standardmäßig nimmt jedes Element in der `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie die Spaltenspanne für einzelne Elemente festlegen. Eine Spanne gibt an, wie viele Spalten ein Element einnehmen sollte.

```java
Button button = new Button("Klicken Sie mich");
layout.addComponent(button);
// Element spannt zwei Spalten
layout.setSpan(button, 2);
```

In dem obigen Beispiel nimmt der Button zwei Spalten ein, anstatt der standardmäßigen einen. Die Methode `setSpan()` ermöglicht es Ihnen anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts überstreichen soll.

### Anpassung von Spaltenspannen mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch die Spaltenspannen dynamisch basierend auf Breakpoints anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element je nach Bildschirmgröße unterschiedlich viele Spalten überstreicht. Beispielsweise möchten Sie möglicherweise, dass ein Element auf mobilen Geräten eine einzige Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten überdeckt.

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
//E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassbarkeit stellt sicher, dass Ihr Layout responsiv bleibt und über verschiedene Geräte hinweg angemessen strukturiert ist.

## Platzieren von Elementen innerhalb von Spalten {#placing-items-within-columns}

`ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, was Ihnen mehr Kontrolle über die Anordnung der Elemente gibt. Sie können manuell angeben, wo ein Element innerhalb des Layouts erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie gewünscht angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, von links nach rechts gefüllt. Sie können jedoch dieses Verhalten überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er im Verhältnis zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Absenden");
layout.addComponent(button);
// Platzieren Sie das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassung der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genau wie bei Spaltenspannen verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout umzustellen oder neu zu platzieren, während sich der Ansichtsbereich ändert.

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
//E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In der folgenden Demonstration beachten Sie, dass das `email`-Feld, wenn der `"medium"`-Breakpoint ausgelöst wird, beide Spalten überspannt, und das `confirmPassword`-Feld in der ersten Spalte platziert wird, anstatt in der standardmäßigen zweiten Spalte:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vermeiden Sie Kollisionen
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spannen und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spannen und Platzierungen der Elemente in einer Reihe sich nicht überschneiden. Das Layout versucht, den Abstand automatisch gelenkt zu verwalten, doch ein sorgfältiges Design von Spannen und Breakpoints verhindert unbeabsichtigte Anzeigen von Elementen.
:::

## Vertikale und horizontale Elementausrichtungen {#vertical-and-horizontal-item-alignments}

Jedes Element in der `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber gibt, wie der Inhalt im Layout positioniert wird.

**Horizontale Ausrichtung** eines Elements wird mit der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Vertikale Ausrichtung** gibt an, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie die Elemente vertikal verteilt werden.

Verfügbare `Alignment`-Optionen umfassen:

- `START`: Richtet das Element links in der Spalte aus (Standard).
- `CENTER`: Zentriert das Element horizontal in der Spalte.
- `END`: Richtet das Element rechts in der Spalte aus.
- `STRETCH`: Dehnt die Komponente, um das Layout auszufüllen.
- `BASELINE`: Richtet sich nach dem Text oder dem Inhalt in der Spalte aus, indem Elemente nach der Textbasislinie und nicht nach anderen Ausrichtungsoptionen ausgerichtet werden.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In der obigen Demo hat der `Absenden`-Button `ColumnsLayout.Alignment.END` erhalten, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Elementabstände {#item-spacing}

Die Kontrolle des Abstands zwischen Spalten in der `ColumnsLayout` zwischen Spalten (horizontale Abstände) und zwischen Reihen (vertikale Abstände) hilft Entwicklern, das Layout fein abzustimmen.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setzen Sie 20px Abstand zwischen den Spalten
layout.setHorizontalSpacing(20);  
```

Ebenso verwenden Sie die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setzen Sie 15px Abstand zwischen den Reihen
layout.setVerticalSpacing(15);  
```

:::tip CSS-Einheiten
Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren oder einen `String` verwenden, um andere Einheiten wie `vw`, `%` oder `em` zu spezifizieren.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Das Erstellen von responsiven und attraktiven Layouts ist möglich, indem sowohl die [`FlexLayout`](./flex-layout)-Komponente als auch die `ColumnsLayout`-Komponente sowie eine Kombination von beiden verwendet werden. Unten finden Sie ein Beispiel für das im Artikel [Formular im FlexLayout](./flex-layout#example-form) erstellte Formular, aber stattdessen mit einem `ColumnLayout`-Schema:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
