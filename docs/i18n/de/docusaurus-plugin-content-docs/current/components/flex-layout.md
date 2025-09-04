---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ bietet Entwicklern eine effiziente und intuitive Möglichkeit, ihre verschiedenen Anwendungen und Komponenten zu layouten - das Flex Layout. Dieses Toolset ermöglicht es, Elemente entweder vertikal oder horizontal anzuzeigen.

## Flex-layout-Eigenschaften {#flex-layout-properties}

Die Eigenschaften des Flex Layouts können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das Flex Layout, oder das übergeordnete Element, ist ein Container, der ein oder mehrere Komponenten enthalten kann. Alles in einem Flex Layout wird als Element oder Kind-Element bezeichnet. Das Flex Layout bietet einige robuste Ausrichtungsfähigkeiten, die mit Hilfe von Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die Layout-Komponente von webforJ folgt dem Muster von [CSSs Flexbox-Layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Diese Werkzeuge sollen jedoch vollständig in Java verwendet werden und erfordern die Anwendung von CSS außerhalb der bereitgestellten Java-API-Methoden nicht.
:::

## Container-Eigenschaften {#container-properties}

Container-Eigenschaften gelten für alle Komponenten innerhalb eines Containers und nicht für das Layout selbst. Sie beeinflussen nicht die Orientierung oder Platzierung des übergeordneten Elements - nur die Kind-Komponenten innerhalb.

### Richtung {#direction}

Das Flex Layout fügt Komponenten nebeneinander entsprechend der vom Entwickler gewählten Richtung hinzu - entweder horizontal oder vertikal. Beim Verwenden des Builders nutzen Sie entweder die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()`, wenn Sie die Methode `create()` für ein `FlexLayout`-Objekt aufrufen, um dieses Layout beim Erstellen des Objekts zu konfigurieren.

Alternativ können Sie die Methode `setDirection()` verwenden. Die horizontalen Optionen sind entweder `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind entweder `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben). Dies geschieht mit dem FlexLayout-Objekt und nicht mit dem Builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionierung {#positioning}

Komponenten, die horizontal hinzugefügt werden, können sowohl horizontal als auch vertikal positioniert werden. Verwenden Sie die Methoden `justify()`, `align()` und `contentAlign()` aus dem Flex Layout Builder, um die Positionierung beim Erstellen eines neuen Flex Layouts zu konfigurieren.

Alternativ können Sie auf dem tatsächlichen FlexLayout-Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()`, um die vertikale Positionierung zu konfigurieren. Um den Bereich um die Komponenten entlang der Querachse (y-Achse für horizontale Layouts) zu ändern, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` beeinflusst, wie Elemente entlang der Querachse als Ganzes innerhalb des Containers angezeigt werden, und ist effektiv für Layouts mit einer einzigen Zeile.

Die Methoden `setAlignContent()` beeinflussen den Raum um die Querachse und wirken sich nur aus, wenn ein Layout mehrere Zeilen hat.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Umbruch {#wrapping}

Um das Flex Layout weiter anzupassen, können Sie das Verhalten des Flex Layouts angeben, wenn hinzugefügte Komponenten nicht mehr in die Anzeige passen. Um dies mithilfe des Builders zu konfigurieren, verwenden Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()`, um den Umbruch zu konfigurieren.

Alternativ, wenn Ihr Layout bereits existiert, verwenden Sie die Methode `setWrap()`, um festzulegen, wie sich Komponenten verhalten, wenn sie nicht mehr in eine einzige Zeile passen.

### Abstände {#spacing}

Um minimale Abstände zwischen Elementen anzuwenden, können Sie die Gap-Eigenschaft festlegen. Sie wird nur zwischen den Elementen, nicht an den äußeren Rändern, angewendet.

Das Verhalten der Gap-Eigenschaft kann als minimale Distanz zwischen Elementen betrachtet werden – diese Eigenschaft hat nur dann Wirkung, wenn sie der größte berechnete Abstand zwischen den Elementen ist. Wenn der Abstand zwischen den Elementen aufgrund einer anderen berechneten Eigenschaft, wie zum Beispiel aufgrund von `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, größer wäre, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Der Flex-Fluss, der eine Kombination aus den Richtungs- und Umbrucheigenschaften ist, kann mithilfe der Methode `setFlow()` auf einem Flex Layout-Objekt festgelegt werden.

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Richtungs- und Umbruchmethoden. Beispielsweise verwenden Sie die Kombination `.vertical().wrap()` um einen Spaltenumbruchfluss zu erstellen.
:::

### Container-Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten Flex-Eigenschaften aus verschiedenen Menüs zu erstellen. Dieses Werkzeug kann nicht nur verwendet werden, um ein visuelles Beispiel der verschiedenen Methoden zu erstellen, sondern auch, um Ihre eigenen Layouts mit Ihren gewünschten Eigenschaften zu erstellen. Um ein Layout zu verwenden, das Sie angepasst haben, kopieren Sie einfach den Ausgabe-Code und fügen Sie Ihre gewünschten Elemente zur Verwendung in Ihrem Programm hinzu.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- GROßES CODE-SNIPPET, DAS DEN CONTAINER ANZEIGT -->
## Eigenschaften der Elemente {#item-properties}

Die Eigenschaften der Elemente beeinflussen keine untergeordneten Elemente innerhalb des Flex Layouts, sondern das eigentliche Layout selbst. Dies ist nützlich zum Stylen eines einzelnen Flex Layout-Elements, das ein Kind eines größeren Flex Layout-Elements ist, unabhängig von den Stilen, die auf alle Kinder angewendet werden.

### Reihenfolge {#order}

Die Eigenschaft `ItemOrder` bestimmt, wie Komponenten innerhalb des Flex Layouts angezeigt werden. Wenn sie auf ein Flex Layout angewendet wird, weist sie einem Element die spezifische Bestellnummer dieses Layouts zu. Dies überschreibt die standardmäßige "Quellreihenfolge", die in jedes Element eingebaut ist (die Reihenfolge, in der eine Komponente ihrem Elternteil hinzugefügt wird), und bedeutet, dass es vor Elementen mit einer höheren Reihenfolge und nach Elementen mit einer niedrigeren Reihenfolge angezeigt wird.

Diese Eigenschaft akzeptiert einen wertlosen ganzzahligen Wert, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Zum Beispiel wird ein Element mit einem Reihenfolge-Wert von 1 vor einem Element mit einem Reihenfolge-Wert von 2 angezeigt.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht ihre tatsächliche Position im DOM. Das bedeutet, dass Bildschirmleser und andere Hilfstechnologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, und nicht in der visuellen Reihenfolge.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Selbst-Ausrichtung {#self-alignment}

Die Selbst-Ausrichtung des Flex Layouts bezieht sich auf die Ausrichtung eines einzelnen Flex Layout-Objekts innerhalb seines übergeordneten Flex-Containers entlang der Querachse, die senkrecht zur Hauptachse steht. Die Ausrichtung der Querachse wird durch die Eigenschaft `Alignment` gesteuert.

Die `align-self`-Eigenschaft gibt die Ausrichtung eines einzelnen Flex-Elements entlang der Querachse an und überschreibt die standardmäßige Ausrichtung, die durch die Eigenschaft `AlignContent` in einem Flex Layout-Objekt festgelegt wurde. Dies ermöglicht es Ihnen, einzelne Flex Layout-Objekte anders auszurichten als die anderen im Container.

:::info
Die Selbst-Ausrichtung verwendet dieselben Werte wie die Inhaltsausrichtung.
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders als die anderen Elemente im Container ausrichten möchten. Siehe das folgende Beispiel zum Ausrichten eines einzelnen Elements:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-Basis {#flex-basis}

`Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung des Flex Layouts verwendet wird, um die anfängliche Größe eines Flex-Elements festzulegen, bevor der verbleibende Platz verteilt wird.

Die `Item Basis`-Eigenschaft gibt die Standardgröße eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (für eine Zeilenrichtung) oder vertikal (für eine Spaltenrichtung) ist. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements fest, abhängig vom Wert der flex-direction-Eigenschaft.

:::info
Standardmäßig ist die `Item Basis`-Eigenschaft auf auto gesetzt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine spezifische Größe für das Element mit verschiedenen Einheiten wie Pixel (px), ems (em), Prozent (%) oder einer anderen CSS-Längeneinheit festlegen.
:::

Die folgende Demo ermöglicht es Ihnen, eine oder mehrere Boxen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex wachsen / schrumpfen {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die in Verbindung miteinander und mit der `Item Basis`-Eigenschaft arbeiten, um zu bestimmen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Platz innerhalb eines Flex Layout-Objekts zu füllen.

Die `Item Grow`-Eigenschaft gibt an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container wachsen kann. Sie nimmt einen wertlosen Wert an, der einen Anteil des verfügbaren Platzes darstellt, der diesem Element zugewiesen werden soll. Wenn ein Element beispielsweise einen `Item Grow`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel wachsen wie das erste Element.

Die `Item Shrink`-Eigenschaft hingegen gibt an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container schrumpfen kann. Sie nimmt ebenfalls einen wertlosen Wert an, der einen Anteil des verfügbaren Platzes darstellt, der diesem Element zugewiesen werden soll. Wenn ein Element beispielsweise einen `Item Shrink`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel schrumpfen wie das erste Element.

Wenn ein Container mehr Platz hat, als benötigt wird, um seinen Inhalt unterzubringen, werden Flex-Elemente mit einem `Item Grow`-Wert größer als 0 den verfügbaren Platz ausfüllen. Der Betrag des Platzes, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow`-Wertes zu dem gesamten `Item Grow`-Wert aller Elemente im Container bestimmt.

Ebenso, wenn ein Container nicht genügend Platz hat, um seinen Inhalt unterzubringen, werden Flex-Elemente mit einem `Item Shrink`-Wert größer als 0 schrumpfen, um den verfügbaren Platz zu nutzen. Der Betrag des Platzes, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink`-Wertes zu dem gesamten `Item Shrink`-Wert aller Elemente im Container bestimmt.

## Beispiel-Formular {#example-form}
Das Formular unten demonstriert, wie `FlexLayout` Eingabefelder in einem strukturierten Layout organisiert.

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, sehen Sie sich die ColumnsLayout-Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout) an, um zu sehen, wie es sich verhält.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
