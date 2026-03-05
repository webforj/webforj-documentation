---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: f4d9229ae204894cda7263a6dc09ba0c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente arrangiert Elemente in einem responsiven, spaltenbasierten Layout, das sich je nach verfügbarer Breite anpasst. Breakpoints und Ausrichtungen werden automatisch verwaltet, sodass der Aufbau von mehrspaltigen Formularen und Inhaltsraster keine benutzerdefinierte responsive Logik erfordert.

<!-- INTRO_END -->

## Standardverhalten {#default-behavior}

Standardmäßig arrangiert ein `ColumnsLayout` Elemente in zwei Spalten und nimmt die gesamte Breite seines übergeordneten Elements ein. Die Anzeige kann weiter mit Breakpoints und Ausrichtungsoptionen angepasst werden, die in den folgenden Abschnitten behandelt werden.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Horizontale Layouts 
Diese können anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout)-Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Breakpoints {#breakpoints}

Im Kern ist das `ColumnsLayout` so konzipiert, dass es ein flüssiges, gitterähnliches System bietet, das sich an die Breite seines übergeordneten Containers anpasst. Im Gegensatz zu traditionellen festen Gitter-Systemen ermöglicht dieses Layout Entwicklern, eine Anzahl von Spalten bei einer gegebenen Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten.

Das erlaubt einem `ColumnsLayout`, sich reibungslos von einem engeren Raum auf kleinen Bildschirmen zu einem größeren Bereich auf größeren Bildschirmen anzupassen und bietet ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann unter Verwendung der `Breakpoint`-Klasse angegeben werden, die drei Parameter annimmt:

1. **Name (optional)**:
Ein Breakpoint-Namen ermöglicht es Ihnen, darauf in zukünftigen Konfigurationen zu verweisen.

2. **Minimale Breite**:
Jeder Breakpoint hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die minimale Breite wird explizit definiert, und der nächste Breakpoint bestimmt die maximale Breite, wenn er vorhanden ist. Sie können eine Ganzzahl verwenden, um die minimale Breite in Pixeln zu definieren, oder eine `String` verwenden, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben sollte, mit dieser Ganzzahl.


:::info `Breakpoint`-Evaluierung
Breakpoints werden in aufsteigender Reihenfolge der Breite bewertet, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
:::


### Anwendung von Breakpoints {#applying-breakpoints}

Breakpoints werden in einem `ColumnsLayout` auf eine von zwei Arten angewendet: während des Aufbaus oder in einer `List` unter Verwendung der Methode `setBreakpoints()`:

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

Die nachfolgende Demonstration zeigt ein Beispiel dafür, mehrere Breakpoints beim Aufbau festzulegen, die Breakpoints zum Konfigurieren des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente zu verwenden und die Größenänderungsfähigkeiten des `ColumnsLayout` zu demonstrieren, wenn die App verkleinert wird:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Spalten `Span` und Spans pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Spans in `ColumnsLayout` erlauben Ihnen zu steuern, wie viele Spalten ein Element einnimmt, wodurch Sie mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten erhalten. Dies ist besonders nützlich, wenn Sie bestimmte Elemente benötigen, die je nach Bildschirmgröße oder Designanforderungen mehr oder weniger Platz einnehmen sollen.

### Verwaltung von Spalten-Spans {#managing-column-spans}

Standardmäßig nimmt jedes Element im `ColumnsLayout` genau eine Spalte ein. Sie können jedoch dieses Verhalten anpassen, indem Sie den Spalten-Span für einzelne Elemente festlegen. Ein Span gibt an, wie viele Spalten ein Element einnehmen sollte.

```java
Button button = new Button("Klicken Sie mich");
layout.addComponent(button);
// Element nimmt zwei Spalten ein
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten ein, statt der Standard-einen. Die Methode `setSpan()` ermöglicht es Ihnen, anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts einnehmen soll.

### Anpassung von Spalten-Spans mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können auch Spalten-Spans dynamisch basierend auf Breakpoints anpassen. Diese Funktion ist nützlich, wenn Sie möchten, dass ein Element unterschiedliche Anzahl von Spalten einnimmt, abhängig von der Bildschirmgröße. Beispielsweise möchten Sie, dass ein Element auf mobilen Geräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten einnimmt.

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
//die E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsiv und angemessen strukturiert bleibt, je nach Gerät.

## Platzierung von Elementen innerhalb von Spalten {#placing-items-within-columns}

`ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, was mehr Kontrolle über die Anordnung der Elemente ermöglicht. Sie können manuell angeben, wo ein Element im Layout erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie beabsichtigt angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächstverfügbaren Spalte platziert, wobei von links nach rechts gefüllt wird. Sie können jedoch dieses Verhalten überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in der zweiten Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er im Verhältnis zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Einreichen");
layout.addComponent(button);
// Platziert das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassung der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genauso wie bei Spalten-Spans verwenden Sie Breakpoints, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout umzulagern oder neu zu platzieren, während sich der Ansichtsbereich ändert.

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
//E-Mail-Feld wird in der zweiten Spalte angezeigt, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In der folgenden Demonstration wird deutlich, dass, wenn der `"medium"`-Breakpoint ausgelöst wird, das `email`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in die erste Spalte platziert wird, anstatt in seiner standardmäßigen Platzierung in der zweiten Spalte:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spans und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spans und Platzierungen von Elementen in einer Reihe sich nicht überlappen. Das Layout versucht, den Abstand automatisch zu verwalten, aber eine sorgfältige Gestaltung der Spans und Breakpoints verhindert ein unbeabsichtigtes Anzeigen von Elementen.
:::

## Vertikale und horizontale Ausrichtungen von Elementen {#vertical-and-horizontal-item-alignments}

Jedes Element im `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was Kontrolle darüber gibt, wie der Inhalt im Layout positioniert ist.

**Die horizontale Ausrichtung** eines Elements wird mithilfe der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element innerhalb seiner Spalte entlang der horizontalen Achse ausgerichtet ist.

**Die vertikale Ausrichtung** gibt an, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie Elemente vertikal verteilt sind.

Verfügbare `Alignment`-Optionen sind:

- `START`: Richtet das Element links in der Spalte aus (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Richtet das Element rechts in der Spalte aus.
- `STRETCH`: Dehnt die Komponente, um das Layout zu füllen.
- `BASELINE`: Richtet basierend auf dem Text oder Inhalt innerhalb der Spalte aus, sodass Elemente im Vergleich zu anderen Ausrichtungsoptionen zum Text-Baseline ausgerichtet sind.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In der obigen Demo wurde dem `Submit`-Button die `ColumnsLayout.Alignment.END`-Einstellung zugewiesen, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Abstände von Elementen {#item-spacing}

Die Steuerung des Abstands zwischen Spalten im `ColumnsLayout` sowie zwischen Zeilen (horizontale Abstände) und zwischen Spalten (vertikale Abstände) hilft Entwicklern, das Layout feinabzustimmen.

Um den horizontalen Abstand des Layouts festzulegen, verwenden Sie die Methode `setHorizontalSpacing()`:

```java
// Setzt 20px Abstand zwischen den Spalten
layout.setHorizontalSpacing(20);  
```

Verwenden Sie entsprechend die Methode `setVerticalSpacing()`, um den Abstand zwischen den Reihen des Layouts zu konfigurieren:

```java
// Setzt 15px Abstand zwischen den Reihen
layout.setVerticalSpacing(15);  
```

:::tip CSS-Einheiten
Sie können eine Ganzzahl verwenden, um die minimale Breite in Pixeln zu definieren, oder eine `String` verwenden, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Der Bau von responsiven und ansprechenden Layouts ist sowohl mit der [`FlexLayout`](./flex-layout)-Komponente als auch mit der `ColumnsLayout`-Komponente sowie einer Kombination der beiden möglich. Im Folgenden finden Sie ein Beispiel des [Formulars, das im FlexLayout](./flex-layout#example-form)-Artikel erstellt wurde, jedoch mit einem `ColumnLayout`-Schema:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Stilgestaltung {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
