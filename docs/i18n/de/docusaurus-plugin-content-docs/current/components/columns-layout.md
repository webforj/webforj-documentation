---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: d75bb3fcf3260672e15ef9acbb38e295
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente ordnet Elemente in ein responsives, spaltenbasiertes Layout an, das sich je nach verfügbarer Breite anpasst. Brechen und Ausrichtungen werden automatisch verwaltet, sodass der Aufbau von mehrspaltigen Formularen und Inhaltsgittern keine benutzerdefinierte responsive Logik erfordert.

<!-- INTRO_END -->

## Standardverhalten {#default-behavior}

Standardmäßig ordnet ein `ColumnsLayout` Elemente in zwei Spalten an und nimmt die volle Breite seines Elternteils ein. Die Anzeige kann mithilfe von Brechpunkten und Ausrichtungseinstellungen weiter angepasst werden, die in den folgenden Abschnitten behandelt werden.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale Layouts
Dies kann anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Brechpunkte {#breakpoints}

Im Kern wurde das `ColumnsLayout` entwickelt, um ein flüssiges, gitterähnliches System bereitzustellen, das sich an die Breite seines Container-Elternteils anpasst. Im Gegensatz zu herkömmlichen festen Gitter-Systemen ermöglicht dieses Layout Entwicklern, eine Anzahl von Spalten bei einer bestimmten Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Dadurch kann sich ein `ColumnsLayout` von einem stärker eingeschränkten Raum auf kleinen Bildschirmen auf ein breiteres Areal auf größeren Bildschirmen reibungslos anpassen und bietet einem Entwickler ein responsives Design, ohne eine benutzerdefinierte Implementierung zu benötigen.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann mit der `Breakpoint`-Klasse angegeben werden, die drei Parameter annimmt:

1. **Name (optional)**:
Durch die Benennung eines Brechpunktes können Sie ihn in zukünftigen Konfigurationen referenzieren.

2. **Minimale Breite**:
Jeder Brechpunkt hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die Mindestbreite wird explizit definiert, und der nächste Brechpunkt bestimmt die maximale Breite, falls vorhanden. Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Brechpunkt haben soll, mit dieser Zahl.


:::info `Breakpoint`-Bewertung
Brechpunkte werden in aufsteigender Reihenfolge der Breite bewertet, was bedeutet, dass das Layout den ersten übereinstimmenden Brechpunkt verwenden wird.
:::


### Anwenden von Brechpunkten {#applying-breakpoints}

Brechpunkte werden auf ein `ColumnsLayout` auf eine von zwei Arten angewendet: während der Konstruktion oder in einer `List` mit der Methode `setBreakpoints()`:

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

Die Demonstration unten zeigt ein Beispiel für das Setzen mehrerer Brechpunkte während der Konstruktion, die Verwendung von Brechpunkten zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente und demonstriert die Größenänderungsfähigkeiten des `ColumnsLayout`, wenn die App verkleinert wird:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Spalten `Span` und Spannen pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spaltungen in `ColumnsLayout` ermöglichen es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, wodurch Sie mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten erhalten. Dies ist besonders nützlich, wenn Sie möchten, dass bestimmte Elemente je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen.

### Verwaltung von Spalten-Sprüngen {#managing-column-spans}

Standardmäßig nimmt jedes Element im `ColumnsLayout` genau eine Spalte ein. Sie können dieses Verhalten jedoch anpassen, indem Sie den Spalten-Sprung für einzelne Elemente festlegen. Ein Sprung gibt die Anzahl der Spalten an, die ein Element einnehmen sollte.

```java
Button button = new Button("Klicken Sie mich");
layout.addComponent(button);
// Element nimmt zwei Spalten ein
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten ein, anstatt der standardmäßigen einen. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts einnehmen sollte.

### Anpassen der Spalten-Sprünge mit Brechpunkten {#adjusting-column-spans-with-breakpoints}

Sie können auch die Spalten-Sprünge dynamisch basierend auf Brechpunkten anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element unterschiedlich viele Spalten je nach Bildschirmgröße einnimmt. Beispielsweise möchten Sie vielleicht, dass ein Element auf mobilen Geräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten einnimmt.

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
//Das E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Brechpunkt aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsiv und angemessen strukturiert bleibt über verschiedene Geräte hinweg.

## Platzieren von Elementen innerhalb von Spalten {#placing-items-within-columns}

`ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren und mehr Kontrolle über die Anordnung der Elemente zu erhalten. Sie können manuell angeben, wo ein Element innerhalb des Layouts erscheinen soll, und sicherstellen, dass wichtige Komponenten wie beabsichtigt angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, die von links nach rechts gefüllt wird. Sie können jedoch dieses Verhalten überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er relativ zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Einreichen");
layout.addComponent(button);
// Platzieren Sie das Element in der zweiten Spalte
layout.setColumn(button, 2);
```

### Anpassen der Platzierung pro Brechpunkt {#adjusting-placement-per-breakpoint}

Ähnlich wie bei den Spalten-Sprüngen verwenden Sie Brechpunkte, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout neu anzuordnen oder zu verschieben, während sich der Ansichtsbereich ändert.

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
//Das E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der mittlere Brechpunkt aktiv ist
columnsLayout.setColumn(email, "medium", 2);
//...
```

In der folgenden Demonstration beachten Sie, dass wenn der `"medium"` Brechpunkt aktiviert wird, das `email`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in die erste Spalte platziert wird, anstatt in seiner standardmäßigen Platzierung in der zweiten Spalte:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Sprüngen und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Sprünge und Platzierungen von Elementen in einer Zeile sich nicht überschneiden. Das Layout versucht automatisch, den Abstand harmonisch zu verwalten, doch ein sorgfältiges Design von Sprüngen und Brechpunkten verhindert unbeabsichtigte Anzeige von Elementen.
:::

## Vertikale und horizontale Ausrichtungen von Elementen {#vertical-and-horizontal-item-alignments}

Jedes Element im `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber gibt, wie Inhalte innerhalb des Layouts positioniert sind.

**Horizontale Ausrichtung** eines Elements wird mit der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Vertikale Ausrichtung** gibt an, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie die Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen sind:

- `START`: Aligniert das Element links in der Spalte (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Aligniert das Element rechts in der Spalte.
- `STRETCH`: Dehnt die Komponente zur Füllung des Layouts aus.
- `BASELINE`: Aligniert basierend auf dem Text oder Inhalt innerhalb der Spalte, wobei Elemente an der Textbasis und nicht an anderen Ausrichtungsoptionen ausgerichtet werden.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In der obigen Demo wurde dem `Einreichen`-Button `ColumnsLayout.Alignment.END` gegeben, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Elementabstand {#item-spacing}

Die Kontrolle über den Abstand zwischen Spalten im `ColumnsLayout` zwischen Spalten (horizontaler Abstand) und zwischen Reihen (vertikaler Abstand) hilft Entwicklern, das Layout zu optimieren.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setzen Sie 20px Abstand zwischen Spalten
layout.setHorizontalSpacing(20);
```

Verwenden Sie ähnlich die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setzen Sie 15px Abstand zwischen den Reihen
layout.setVerticalSpacing(15);
```

:::tip CSS-Einheiten
Sie können eine ganze Zahl verwenden, um die Mindestbreite in Pixeln zu definieren, oder einen `String` verwenden, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Es ist möglich, responsive und attraktive Layouts mit sowohl der [`FlexLayout`](./flex-layout)-Komponente als auch der `ColumnsLayout`-Komponente zu erstellen, sowie einer Kombination aus beidem. Unten befindet sich ein Beispiel des [Formulars, das im FlexLayout](./flex-layout#example-form)-Artikel erstellt wurde, aber stattdessen mit einem `ColumnLayout`-Schema:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
