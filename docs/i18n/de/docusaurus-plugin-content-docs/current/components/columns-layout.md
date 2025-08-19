---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: fb5b6ef5a20567d8a86d04c022a0449e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente in webforJ ermöglicht es Entwicklern, Layouts mit einem flexiblen und responsiven vertikalen Layout zu erstellen. Dieses Layout bietet dynamische Spalten, die sich basierend auf der verfügbaren Breite anpassen. Diese Komponente vereinfacht die Erstellung von mehrspaltigen Layouts, indem sie automatisch Breakpoints und Ausrichtungen verwaltet.

:::info Horizontale Layouts 
Dies kann anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Grundlagen {#basics}

Bei der ersten Instanziierung verwendet die `ColumnsLayout` zwei Spalten, um Elemente anzuzeigen, die dem Layout hinzugefügt wurden. Standardmäßig nimmt sie die volle Breite ihrer Elternelemente ein und wächst bei Bedarf, um zusätzlichen Inhalt aufzunehmen. Die Anzeige hinzugefügter Elemente kann weiter abgestimmt werden mit Hilfe von [`Breakpoint`](./columns-layout#breakpoints) und [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments)-Einstellungen, die in den folgenden Abschnitten näher erläutert werden.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breakpoints {#breakpoints}

Im Kern wurde die `ColumnsLayout` entwickelt, um ein flüssiges, gitterartiges System bereitzustellen, das sich an die Breite seines übergeordneten Containers anpasst. Im Gegensatz zu traditionellen Fixgitter-Systemen ermöglicht dieses Layout Entwicklern, eine Anzahl von Spalten bei einer bestimmten Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Dies ermöglicht es einer `ColumnsLayout`, sich sanft von einem engeren Raum auf kleinen Bildschirmen zu einem breiteren Bereich auf größeren Bildschirmen anzupassen und bietet einem Entwickler ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann mithilfe der `Breakpoint`-Klasse angegeben werden, die drei Parameter akzeptiert:

1. **Name (optional)**:
Die Benennung eines Breakpoints ermöglicht es, ihn in zukünftigen Konfigurationen zu referenzieren.

2. **Mindestbreite**:
Jeder Breakpoint hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die Mindestbreite wird explizit definiert, und der nächste Breakpoint bestimmt die Höchstbreite, falls vorhanden. Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder eine `String`-Angabe machen, um andere Einheiten wie `vw`, `%` oder `em` zu spezifizieren.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben soll, mit dieser Ganzzahl.

:::info `Breakpoint`-Bewertung
Breakpoints werden in aufsteigender Reihenfolge der Breite ausgewertet, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
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

Die folgende Demonstration zeigt ein Beispiel für die Festlegung mehrerer Breakpoints bei der Konstruktion, die Verwendung von Breakpoints zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) eines Elements und demonstriert die Größenänderungsfähigkeiten der `ColumnsLayout`, wenn die App verkleinert wird:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Spalte `Span` und Spannen pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Spannen in der `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, was Ihnen mehr Kontrolle über das Aussehen Ihres Layouts bei variierenden Breiten gibt. Dies ist besonders nützlich, wenn bestimmte Elemente je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen sollen.

### Verwaltung von Spalten-Spannen {#managing-column-spans}

Standardmäßig nimmt jedes Element in der `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie die Spalten-Spanne für einzelne Elemente festlegen. Eine Spanne gibt an, wie viele Spalten ein Element einnehmen soll.

```java
Button button = new Button("Klick mich");
layout.addComponent(button);
// Element nimmt zwei Spalten ein
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten ein, anstatt der Standard-Eins. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts einnehmen soll.

### Anpassung der Spalten-Spannen mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch die Spalten-Spannen dynamisch basierend auf Breakpoints anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element je nach Bildschirmgröße eine unterschiedliche Anzahl von Spalten einnimmt. Beispielsweise möchten Sie möglicherweise, dass ein Element auf mobilen Geräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten einnimmt.

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
//E-Mail-Feld wird zwei Spalten einnehmen, wenn der Medium-Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsive und angemessen strukturiert ist, um auf verschiedenen Geräten zu funktionieren.

## Platzierung von Elementen innerhalb von Spalten {#placing-items-within-columns}

Die `ColumnsLayout` bietet die Möglichkeit, Elemente in spezifischen Spalten zu platzieren, sodass Sie mehr Kontrolle über die Anordnung von Elementen haben. Sie können manuell angeben, wo ein Element innerhalb des Layouts erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie gewünscht angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert und füllen von links nach rechts. Sie können dieses Verhalten jedoch überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er im Vergleich zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Einreichen");
layout.addComponent(button);
// Platziere das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassung der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genau wie bei den Spalten-Spannen verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout neu zu ordnen oder zu verlagern, wenn sich die Ansicht ändert.

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
//E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der Medium-Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In der folgenden Demonstration beachten Sie, dass, wenn der `"medium"`-Breakpoint aktiviert wird, das `E-Mail`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in die erste Spalte platziert wird, anstatt seiner Standardplatzierung in der zweiten Spalte:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spannen und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spannen und Platzierungen von Elementen in einer Reihe sich nicht überschneiden. Das Layout versucht, den Abstand automatisch zu verwalten, aber eine sorgfältige Gestaltung von Spannen und Breakpoints verhindert ungewollte Anzeigen von Elementen.
:::

## Vertikale und horizontale Ausrichtungen von Elementen {#vertical-and-horizontal-item-alignments}

Jedes Element in der `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber gibt, wie der Inhalt innerhalb des Layouts positioniert ist.

**Die horizontale Ausrichtung** eines Elements wird mit der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie sich ein Element horizontal innerhalb seiner Spalte ausrichtet.

**Die vertikale Ausrichtung** gibt an, wie ein Element vertikal innerhalb seiner Spalte positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie die Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen umfassen:

- `START`: Richtet das Element nach links in der Spalte aus (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Richtet das Element nach rechts in der Spalte aus.
- `STRETCH`: Dehnt die Komponente aus, um das Layout auszufüllen.
- `BASELINE`: Richtet sich nach dem Text oder Inhalt innerhalb der Spalte aus und richtet die Elemente nach der Textbasislinie aus, anstatt nach anderen Ausrichtungsoptionen.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In der obigen Demo hat der `Einreichen`-Button `ColumnsLayout.Alignment.END` gegeben worden, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Elementabstände {#item-spacing}

Die Kontrolle des Abstands zwischen Spalten in der `ColumnsLayout` zwischen den Spalten (horizontaler Abstand) und zwischen den Reihen (vertikaler Abstand) hilft Entwicklern, das Layout zu optimieren.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setze 20px Abstand zwischen den Spalten
layout.setHorizontalSpacing(20);  
```

Verwenden Sie ähnlicherweise die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setze 15px Abstand zwischen den Reihen
layout.setVerticalSpacing(15);  
```

:::tip CSS-Einheiten
Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder eine `String`-Angabe machen, um andere Einheiten wie `vw`, `%` oder `em` zu spezifizieren.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Das Erstellen von responsiven und attraktiven Layouts ist möglich mit sowohl der [`FlexLayout`](./flex-layout)-Komponente als auch der `ColumnsLayout`-Komponente sowie einer Kombination aus beiden. Unten ist ein Beispiel des im [`FlexLayout`](./flex-layout#example-form)-Artikel erstellten Formulars, jedoch mit einem `ColumnLayout`-Schema stattdessen:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
