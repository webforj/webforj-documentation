---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Die `FlexLayout`-Komponente arrangiert untergeordnete Komponenten in einer Reihe oder Spalte mittels des CSS Flexbox-Modells. Sie gibt Ihnen die Kontrolle über Ausrichtung, Abstände, Umbruch und darüber, wie Elemente wachsen oder schrumpfen, um den verfügbaren Platz auszufüllen.

<!-- INTRO_END -->

## Flex Layout Eigenschaften {#flex-layout-properties}

Die Eigenschaften des Flex Layouts können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das Flex Layout oder das Elternelement ist eine Box/Container, die eine oder mehrere Komponenten enthalten kann. Alles, was sich innerhalb eines Flex Layouts befindet, wird als Element oder Kind-Element bezeichnet. Das Flex Layout bietet einige robuste Ausrichtungsfähigkeiten, die entweder mit Hilfe von Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die Layout-Komponente von webforJ folgt dem Muster des [CSS Flexbox-Layouts](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Diese Werkzeuge sind jedoch vollständig zur Nutzung in Java gedacht und erfordern keine Anwendung von CSS außerhalb der bereitgestellten Java-API-Methoden.
:::

## Container Eigenschaften {#container-properties}

Container-Eigenschaften gelten für alle Komponenten innerhalb eines Containers und nicht für das Layout selbst. Sie beeinflussen nicht die Ausrichtung oder Platzierung des Elternteils - nur die untergeordneten Komponenten innerhalb.

### Richtung {#direction}

Das Flex Layout fügt Komponenten entsprechend der Richtung hinzu, die der Entwickler gewählt hat - entweder horizontal oder vertikal. Verwenden Sie beim Erstellen den Builder und die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()`, wenn Sie die Methode `create()` auf einem `FlexLayout`-Objekt aufrufen, um dieses Layout während der Erstellung der Objekts zu konfigurieren.

Alternativ können Sie die Methode `setDirection()` verwenden. Die horizontalen Optionen sind entweder `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind entweder `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben). Dies geschieht mit dem FlexLayout-Objekt und nicht mit dem Builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionierung {#positioning}

Komponenten, die horizontal hinzugefügt werden, können sowohl horizontal als auch vertikal positioniert werden. Nutzen Sie die Methoden `justify()`, `align()` und `contentAlign()` des Flex Layout Builders, um die Positionierung beim Erstellen eines neuen Flex Layouts zu konfigurieren.

Alternativ können Sie auf dem eigentlichen FlexLayout-Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()`, um die vertikale Positionierung zu konfigurieren. Um den Bereich um Komponenten entlang der Querachse (y-Achse für horizontale Layouts) zu modifizieren, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` steuert, wie Elemente entlang der Querachse im Container angezeigt werden, und ist effektiv für einzeilige Layouts.

Die Methoden `setAlignContent()` steuern den Raum um die Querachse und haben nur Wirkung, wenn ein Layout mehrere Zeilen hat.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Umbruch {#wrapping}

Um das Flex Layout-Komponente weiter anzupassen, können Sie das Verhalten des Flex Layouts festlegen, wenn hinzugefügte Komponenten nicht mehr in die Anzeige passen. Um dies mit dem Builder zu konfigurieren, verwenden Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()` zur Konfiguration des Umbruchs.

Alternativ, wenn Ihr Layout bereits existiert, verwenden Sie die Methode `setWrap()`, um festzulegen, wie sich die Komponenten verhalten, wenn sie nicht mehr auf eine einzige Linie passen.

### Abstände {#spacing}

Um minimale Abstände zwischen den Elementen anzuwenden, können Sie die Gap-Eigenschaft festlegen. Sie wendet diesen Abstand nur zwischen den Elementen und nicht an den äußeren Rändern an. 

Das Verhalten der Gap-Eigenschaft kann als Mindestabstand zwischen Elementen angesehen werden - diese Eigenschaft tritt nur in Kraft, wenn sie der größte berechnete Abstand zwischen den Elementen ist. Wenn der Abstand zwischen den Elementen aufgrund einer anderen berechneten Eigenschaft (z. B. `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`) größer wäre, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Der Flex-Fluss, eine Kombination aus den Richtungs- und Umbrucheigenschaften, kann mit der Methode `setFlow()` auf einem Flex Layout-Objekt festgelegt werden. 

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Richtungs- und Umbruchmethoden. Zum Beispiel, um einen Spaltenumbruch-Fluss zu erstellen, verwenden Sie die Kombination `.vertical().wrap()`.
:::

### Container Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten Flex-Eigenschaften aus den verschiedenen Menüs auszuwählen. Dieses Tool kann nicht nur verwendet werden, um ein visuelles Beispiel der verschiedenen Methoden zu erstellen, sondern auch als Werkzeug, um Ihre eigenen Layouts mit den gewünschten Eigenschaften zu erstellen. Um ein von Ihnen angepasstes Layout zu verwenden, kopieren Sie einfach den Ausgabe-Code und fügen Sie Ihre gewünschten Elemente für die Verwendung in Ihrem Programm hinzu.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Elementeigenschaften {#item-properties}

Die Eigenschaften von Elementen wirken sich nicht auf untergeordnete Elemente innerhalb des Flex Layouts aus, sondern auf das eigentliche Layout selbst. Dies ist nützlich zur Stilisierung eines einzelnen Flex Layout-Elements, das ein Kind eines größeren Flex Layout-Elements ist, unabhängig von Stilen, die auf alle Kinder angewendet werden.

### Reihenfolge {#order}

Die `ItemOrder`-Eigenschaft bestimmt, wie Komponenten innerhalb des Flex Layouts angezeigt werden, und wenn sie auf einem Flex Layout verwendet wird, wird einer Komponente die spezifische Reihenfolge in diesem Layout zugewiesen. Dies überschreibt die standardmäßige "Quellreihenfolge", die in jedes Element integriert ist (die Reihenfolge, in der eine Komponente zu ihrem Elternteil hinzugefügt wird), und bedeutet, dass es vor Elementen mit einer höheren Reihenfolge und nach Elementen mit einer niedrigeren Reihenfolge angezeigt wird.

Diese Eigenschaft akzeptiert einen ganzzahligen Wert ohne Einheit, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Zum Beispiel wird ein Element mit einem Reihenfolgewert von 1 vor einem Element mit einem Reihenfolgewert von 2 erscheinen.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht deren tatsächliche Position im DOM. Das bedeutet, dass Bildschirmleser und andere Hilfstechnologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, und nicht in der visuellen Reihenfolge.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Selbst-Ausrichtung {#self-alignment}

Die Selbst-Ausrichtung des Flex Layouts bezieht sich darauf, wie ein einzelnes Flex Layout-Objekt innerhalb seines übergeordneten Flex Containers entlang der Querachse ausgerichtet ist, die senkrecht zur Hauptachse verläuft. Die Ausrichtung entlang der Querachse wird durch die `Alignment`-Eigenschaft gesteuert.

Die `align-self`-Eigenschaft gibt die Ausrichtung eines einzigen Flex-Elements entlang der Querachse an und überschreibt die Standardausrichtung, die durch die `AlignContent`-Eigenschaft in einem Flex Layout-Objekt festgelegt ist. Dadurch können Sie einzelne Flex Layout-Objekte anders als die anderen im Container ausrichten.

:::info
Die Selbst-Ausrichtung verwendet dieselben Werte wie die Inhaltsausrichtung
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders ausrichten möchten als die anderen Elemente im Container. Sehen Sie sich die Beispiel unten an, um ein Beispiel für die Ausrichtung eines einzelnen Elements zu sehen:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex Basis {#flex-basis}

`Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung des Flex Layouts verwendet wird, um die anfängliche Größe eines Flex-Elements festzulegen, bevor verbleibender Raum verteilt wird.

Die `Item Basis`-Eigenschaft gibt die standardmäßige Größe eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (bei einer Zeilenrichtung) oder vertikal (bei einer Spaltenrichtung) ist. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements in Abhängigkeit vom Wert der Eigenschaft `flex-direction` fest.

:::info
Standardmäßig ist die `Item Basis`-Eigenschaft auf auto gesetzt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine spezifische Größe für das Element unter Verwendung verschiedener Einheiten wie Pixel (px), Ems (em), Prozentsätze (%) oder jede andere CSS-Längeneinheit festlegen.
:::

Die folgende Demo ermöglicht es Ihnen, eine oder mehrere Boxen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex Wachsen / Schrumpfen {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die in Verbindung miteinander und mit der `Item Basis`-Eigenschaft verwendet werden, um zu bestimmen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Raum innerhalb eines Flex Layout-Objekts zu füllen.

Die `Item Grow`-Eigenschaft gibt an, wie sehr das Flex-Element relativ zu den anderen Elementen im Container wachsen kann. Sie akzeptiert einen einheitlichen Wert, der einem Anteil des verfügbaren Raums entspricht, der dem Element zugewiesen werden soll. Zum Beispiel, wenn ein Element einen `Item Grow`-Wert von 1 hat und ein anderes einen Wert von 2, wird das zweite Element doppelt so viel wachsen wie das erste Element.

Die `Item Shrink`-Eigenschaft gibt hingegen an, wie sehr das Flex-Element relativ zu den anderen Elementen im Container schrumpfen kann. Sie akzeptiert ebenfalls einen einheitlichen Wert, der einen Anteil des verfügbaren Raums darstellt, der dem Element zugewiesen werden soll. Zum Beispiel, wenn ein Element einen `Item Shrink`-Wert von 1 hat und ein anderes einen Wert von 2, wird das zweite Element doppelt so viel schrumpfen wie das erste Element.

Wenn ein Container mehr Platz hat, als erforderlich ist, um seine Inhalte unterzubringen, werden Flex-Elemente mit einem `Item Grow`-Wert größer als 0 den verfügbaren Platz ausfüllen. Der Betrag des Raums, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow`-Wertes zum gesamten `Item Grow`-Wert aller Elemente im Container bestimmt.

Ebenso werden, wenn ein Container nicht genügend Platz hat, um seine Inhalte unterzubringen, Flex-Elemente mit einem `Item Shrink`-Wert größer als 0 so weit schrumpfen, dass sie in den verfügbaren Raum passen. Der Betrag des Raums, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink`-Wertes zum gesamten `Item Shrink`-Wert aller Elemente im Container bestimmt.

## Beispiel-Form {#example-form}
Das folgende Formular zeigt, wie `FlexLayout` Eingabefelder in einem strukturierten Layout organisiert. 

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, überprüfen Sie die ColumnsLayout-Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout), um zu sehen, wie es im Vergleich steht.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
