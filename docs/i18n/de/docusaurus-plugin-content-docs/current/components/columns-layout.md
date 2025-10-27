---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 25558ea9869bae96974e292e7cc1939d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Die `ColumnsLayout`-Komponente in webforJ ermöglicht Entwicklern die Erstellung von Layouts mit einem flexiblen und responsiven vertikalen Layout. Dieses Layout bietet dynamische Spalten, die je nach verfügbarer Breite angepasst werden. Diese Komponente vereinfacht die Erstellung von Mehrspaltenlayouts, indem sie Breakpoints und Ausrichtungen automatisch verwaltet.

:::info Horizontale Layouts 
Dies kann anstelle von oder in Kombination mit der [`FlexLayout`](./flex-layout) Komponente verwendet werden - einem ebenso leistungsstarken Werkzeug für horizontale Layouts.
:::

## Grundlagen {#basics}

Bei der ersten Instanziierung verwendet die `ColumnsLayout` zwei Spalten, um die hinzugefügten Elemente anzuzeigen. Standardmäßig nimmt sie die volle Breite ihrer Elternelemente ein und wächst nach Bedarf, um zusätzlichen Inhalt aufzunehmen. Die Anzeige der hinzugefügten Elemente kann weiter kalibriert werden, indem die Einstellungen für [`Breakpoint`](./columns-layout#breakpoints) und [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) verwendet werden, die in den folgenden Abschnitten ausführlicher behandelt werden.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breakpoints {#breakpoints}

Im Kern ist die `ColumnsLayout` so konzipiert, dass sie ein flüssiges, rasterähnliches System bietet, das sich an die Breite des übergeordneten Containers anpasst. Im Gegensatz zu traditionellen festen Rastersystemen ermöglicht dieses Layout Entwicklern, eine Anzahl von Spalten bei einer bestimmten Breite anzugeben und berechnet dynamisch die Anzahl der angezeigten Spalten basierend auf festgelegten `Breakpoint`-Objekten. 

Dies ermöglicht es einer `ColumnsLayout`, sich reibungslos von einem eingeschränkten Raum auf kleinen Bildschirmen zu einem größeren Bereich auf größeren Bildschirmen anzupassen und bietet einem Entwickler ein responsives Design, ohne dass eine benutzerdefinierte Implementierung erforderlich ist.

### Verständnis eines `Breakpoint` {#understanding-a-breakpoint}

Ein `Breakpoint` kann unter Verwendung der `Breakpoint`-Klasse angegeben werden, die drei Parameter akzeptiert:

1. **Name (optional)**:
Die Benennung eines Breakpoints ermöglicht es, ihn in zukünftigen Konfigurationen zu referenzieren.

2. **Minimale Breite**:
Jeder Breakpoint hat einen spezifischen Bereich, der bestimmt, wann sein Layout angewendet wird. Die minimale Breite wird explizit definiert, und der nächste Breakpoint bestimmt die maximale Breite, falls vorhanden. Sie können eine ganze Zahl verwenden, um die minimale Breite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.

3. **Anzahl der Spalten**:
Geben Sie an, wie viele Spalten ein Breakpoint haben sollte, indem Sie diese ganze Zahl angeben.

:::info `Breakpoint`-Bewertung
Breakpoints werden in aufsteigender Reihenfolge der Breite bewertet, was bedeutet, dass das Layout den ersten passenden Breakpoint verwendet.
:::


### Anwendung von Breakpoints {#applying-breakpoints}

Breakpoints werden auf eine `ColumnsLayout` auf eine von zwei Arten angewendet: während der Konstruktion oder in einer `List` unter Verwendung der Methode `setBreakpoints()`: 

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

Die folgende Demonstration zeigt ein Beispiel für das Setzen mehrerer Breakpoints bei der Konstruktion, die Verwendung von Breakpoints zur Konfiguration des [`Span`](#column-span-and-spans-per-breakpoint) einer Komponente und demonstriert die Resize-Fähigkeiten der `ColumnsLayout`, wenn die App verkleinert wird:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Spalte `Span` und Spans pro `Breakpoint` {#column-span-and-spans-per-breakpoint}

Spalten-Span in der `ColumnsLayout` ermöglicht es Ihnen, zu steuern, wie viele Spalten ein Element einnimmt, was Ihnen mehr Kontrolle über das Erscheinungsbild Ihres Layouts bei unterschiedlichen Breiten gibt. Dies ist besonders nützlich, wenn Sie möchten, dass bestimmte Elemente mehr oder weniger Platz einnehmen, abhängig von der Bildschirmgröße oder den Designanforderungen.

### Verwalten von Spalten-Spans {#managing-column-spans}

Standardmäßig nimmt jedes Element in der `ColumnsLayout` genau eine Spalte ein. Sie können jedoch dieses Verhalten anpassen, indem Sie den Spalten-Span für einzelne Elemente festlegen. Ein Span gibt an, wie viele Spalten ein Element einnehmen sollte.

```java
Button button = new Button("Klicken Sie mich");
layout.addComponent(button);
// Element spannt zwei Spalten
layout.setSpan(button, 2);
```

Im obigen Beispiel nimmt der Button zwei Spalten ein statt der Standardspalte. Die Methode `setSpan()` erlaubt es Ihnen anzugeben, wie viele Spalten eine Komponente innerhalb des Layouts spannen sollte.

### Anpassen von Spalten-Spans mit Breakpoints {#adjusting-column-spans-with-breakpoints}

Sie können die Spalten-Spans auch dynamisch basierend auf Breakpoints anpassen. Dieses Feature ist nützlich, wenn Sie möchten, dass ein Element je nach Bildschirmgröße unterschiedlich viele Spalten einnimmt. Beispielsweise möchten Sie vielleicht, dass ein Element auf Mobilgeräten eine einzelne Spalte einnimmt, aber auf größeren Bildschirmen mehrere Spalten einnimmt.

```java
TextField email = new TextField("E-Mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("standard", 0 , 1),
  new ColumnsLayout.Breakpoint("klein", "20em", 1),
  new ColumnsLayout.Breakpoint("mittel", "40em", 2),
  new ColumnsLayout.Breakpoint("groß", "60em", 3)
)
//...
// E-Mail-Feld wird zwei Spalten einnehmen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setSpan(email, "mittel", 2);
//...
```

Dieses Maß an Anpassung stellt sicher, dass Ihr Layout responsiv und angemessen strukturiert bleibt, auch auf unterschiedlichen Geräten.

## Platzierung von Elementen innerhalb der Spalten {#placing-items-within-columns}

`ColumnsLayout` bietet die Möglichkeit, Elemente in bestimmten Spalten zu platzieren, wodurch Sie mehr Kontrolle über die Anordnung von Elementen haben. Sie können manuell angeben, wo ein Element im Layout erscheinen soll, um sicherzustellen, dass wichtige Komponenten wie beabsichtigt angezeigt werden.

### Grundlegende Spaltenplatzierung {#basic-column-placement}

Standardmäßig werden Elemente in der nächsten verfügbaren Spalte platziert, gefüllt von links nach rechts. Sie können jedoch dieses Verhalten überschreiben und die genaue Spalte angeben, in der ein Element platziert werden soll. Um ein Element in einer bestimmten Spalte zu platzieren, verwenden Sie die Methode `setColumn()`. In diesem Beispiel wird der Button in die zweite Spalte des Layouts platziert, unabhängig von der Reihenfolge, in der er im Verhältnis zu anderen Komponenten hinzugefügt wurde:

```java
Button button = new Button("Abschicken");
layout.addComponent(button);
// Platziere das Element in der zweiten Spalte
layout.setColumn(button, 2);  
```

### Anpassen der Platzierung pro Breakpoint {#adjusting-placement-per-breakpoint}

Genau wie bei den Spalten-Spans können Sie Breakpoints verwenden, um die Platzierung von Elementen basierend auf der Bildschirmgröße anzupassen. Dies ist nützlich, um Elemente im Layout zu reorganisieren oder zu verlagern, wenn sich die Ansicht ändert.

```java
TextField email = new TextField("E-Mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("standard", 0 , 1),
  new ColumnsLayout.Breakpoint("klein", "20em", 1),
  new ColumnsLayout.Breakpoint("mittel", "40em", 2),
  new ColumnsLayout.Breakpoint("groß", "60em", 3)
)
//...
//E-Mail-Feld wird in der zweiten Spalte erscheinen, wenn der mittlere Breakpoint aktiv ist
columnsLayout.setColumn(email, "mittel", 2); 
//...
```

In der folgenden Demonstration beachten Sie, dass wenn der `"mittel"` Breakpoint ausgelöst wird, das `email`-Feld beide Spalten einnimmt und das `confirmPassword`-Feld in die erste Spalte platziert wird, anstatt in seine Standardplatzierung in der zweiten Spalte:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Kollisionen vermeiden
Wenn mehrere Elemente in einem Layout mit unterschiedlichen Spans und/oder Spaltenzuweisungen platziert werden, stellen Sie sicher, dass die kombinierten Spans und Platzierungen der Elemente in einer Reihe sich nicht überschneiden. Das Layout versucht, den Abstand automatisch zu verwalten, aber ein sorgfältiges Design von Spans und Breakpoints verhindert eine ungewollte Anzeige von Elementen.
:::

## Vertikale und horizontale Ausrichtungen von Elementen {#vertical-and-horizontal-item-alignments}

Jedes Element in der `ColumnsLayout` kann sowohl horizontal als auch vertikal innerhalb seiner Spalte ausgerichtet werden, was die Kontrolle darüber gibt, wie Inhalte im Layout positioniert werden.

**Horizontale Ausrichtung** eines Elements wird mit der Methode `setHorizontalAlignment()` gesteuert. Diese Eigenschaft bestimmt, wie ein Element entlang der horizontalen Achse in seiner Spalte ausgerichtet wird.

**Vertikale Ausrichtung** gibt an, wie ein Element innerhalb seiner Spalte entlang der vertikalen Achse positioniert ist. Dies ist nützlich, wenn Spalten unterschiedliche Höhen haben und Sie steuern möchten, wie Elemente vertikal verteilt werden. 

Verfügbare `Alignment`-Optionen sind:

- `START`: Platziert das Element links in der Spalte (Standard).
- `CENTER`: Zentriert das Element horizontal innerhalb der Spalte.
- `END`: Platziert das Element rechts in der Spalte.
- `STRETCH`: Dehnt die Komponente, um das Layout auszufüllen.
- `BASELINE`: richtet sich nach dem Text oder Inhalt innerhalb der Spalte und richtet die Elemente an der Textbasislinie aus, anstatt andere Ausrichtungsoptionen zu verwenden.
- `AUTO`: Automatische Ausrichtung.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In der obigen Demo wurde der `Abschicken`-Button `ColumnsLayout.Alignment.END` zugewiesen, um sicherzustellen, dass er am Ende, oder in diesem Fall rechts, seiner Spalte erscheint.

## Elementabstände {#item-spacing}

Die Steuerung des Abstands zwischen Spalten in der `ColumnsLayout` zwischen Spalten (horizontaler Abstand) und zwischen Reihen (vertikaler Abstand) hilft Entwicklern, das Layout fein abzustimmen.

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
Sie können eine ganze Zahl verwenden, um die minimale Breite in Pixeln zu definieren, oder einen `String`, um andere Einheiten wie `vw`, `%` oder `em` anzugeben.
:::

## Horizontale und vertikale Layouts {#horizontal-and-vertical-layouts}

Es ist möglich, responsive und ansprechende Layouts sowohl mit der [`FlexLayout`](./flex-layout) Komponente als auch mit der `ColumnsLayout` Komponente sowie einer Kombination aus beiden zu erstellen. Unten sehen Sie ein Beispiel des [in der FlexLayout](./flex-layout#example-form) Artikel erstellten Formulars, jedoch unter Verwendung eines `ColumnLayout`-Schemas:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
