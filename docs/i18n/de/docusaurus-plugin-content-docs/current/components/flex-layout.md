---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ bietet Entwicklern eine effiziente und intuitive Möglichkeit, ihre verschiedenen Anwendungen und Komponenten anzuordnen - das Flex Layout. Dieses Toolset ermöglicht es, Elemente entweder vertikal oder horizontal anzuzeigen. 

## Flex Layout Eigenschaften {#flex-layout-properties}

Die Eigenschaften des Flex Layouts können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das Flex Layout, oder das Elternelement, ist eine Box/Container, die ein oder mehrere Komponenten enthalten kann. Alles innerhalb eines Flex Layouts wird als Element oder Kind-Element bezeichnet. Das Flex Layout bietet einige robuste Ausrichtungsfähigkeiten, die mithilfe von Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die Layout-Komponente von webforJ folgt dem Muster des [CSS Flexbox-Layouts](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Diese Werkzeuge sind jedoch dafür ausgelegt, vollständig in Java verwendet zu werden und erfordern nicht die Anwendung von CSS außerhalb der bereitgestellten Java-API-Methoden.
:::

## Container Eigenschaften {#container-properties}

Container-Eigenschaften gelten für alle Komponenten innerhalb eines Containers und nicht für das Layout selbst. Sie beeinflussen nicht die Ausrichtung oder Platzierung des Elternteils - nur die Kind-Komponenten innerhalb.

### Richtung {#direction}

Das Flex Layout fügt Komponenten nebeneinander in der vom Entwickler gewählten Richtung hinzu - entweder horizontal oder vertikal. Bei der Verwendung des Builders nutzen Sie entweder die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()`, wenn Sie die Methode `create()` auf einem `FlexLayout`-Objekt aufrufen, um dieses Layout beim Erstellen des Objekts zu konfigurieren.

Alternativ können Sie die Methode `setDirection()` verwenden. Die horizontalen Optionen sind entweder `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind entweder `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben). Dies geschieht mit dem FlexLayout-Objekt, im Gegensatz zum Builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionierung {#positioning}

Komponenten, die horizontal hinzugefügt werden, können sowohl horizontal als auch vertikal positioniert werden. Verwenden Sie die Methoden `justify()`, `align()` und `contentAlign()` aus dem Flex Layout Builder, um die Positionierung beim Erstellen eines neuen Flex Layouts zu konfigurieren.

Alternativ können Sie auf dem tatsächlichen FlexLayout-Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()`, um die vertikale Positionierung zu konfigurieren. Um den Bereich um Komponenten entlang der Kreuzachse (y-Achse für horizontale Layouts) zu ändern, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` bestimmt, wie Elemente entlang der Kreuzachse als Ganzes innerhalb des Containers angezeigt werden, und ist effektiv für einezeilige Layouts.

Die Methode `setAlignContent()` bestimmt den Platz um die Kreuzachse und wirkt nur, wenn ein Layout mehrere Linien hat.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Umbruch {#wrapping}

Um das Flex Layout weiter anzupassen, können Sie das Verhalten des Flex Layouts festlegen, wenn hinzugefügte Komponenten nicht mehr in den Anzeigebereich passen. Um dies mithilfe des Builders zu konfigurieren, nutzen Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()` zur Konfiguration des Umbruchs.

Alternativ, wenn Ihr Layout bereits existiert, verwenden Sie die Methode `setWrap()`, um zu bestimmen, wie sich Komponenten verhalten, wenn sie nicht mehr in eine Zeile passen.

### Abstände {#spacing}

Um Mindestabstände zwischen Elementen anzuwenden, können Sie die Gap-Eigenschaft festlegen. Sie wendet diesen Abstand nur zwischen Elementen an, nicht an den äußeren Kanten.

Das Verhalten der Gap-Eigenschaft kann als Mindestabstand zwischen den Elementen betrachtet werden - diese Eigenschaft hat nur dann Wirkung, wenn sie der größte berechnete Abstand zwischen Elementen ist. Wenn der Abstand zwischen Elementen aufgrund einer anderen berechneten Eigenschaft größer wäre, wie z. B. `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Der Flex-Fluss, eine Kombination aus Richtung und Umbruchseigenschaften, kann mithilfe der Methode `setFlow()` auf einem Flex Layout-Objekt festgelegt werden.

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Methoden für Richtung und Umbruch. Um beispielsweise einen Säulenumbruchfluss zu erstellen, verwenden Sie die Kombination `.vertical().wrap()`.
:::

### Container Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten Flex-Eigenschaften aus den verschiedenen Menüs zu erstellen. Dieses Tool kann nicht nur verwendet werden, um ein visuelles Beispiel für die verschiedenen Methoden zu erstellen, sondern auch, um Ihre eigenen Layouts mit den gewünschten Eigenschaften zu erstellen. Um ein benutzerdefiniertes Layout zu verwenden, kopieren Sie einfach den Ausgabecode und fügen Sie Ihre gewünschten Elemente in Ihrem Programm ein.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## Element Eigenschaften {#item-properties}

Elementeigenschaften beeinflussen keine Kind-Elemente innerhalb des Flex Layouts, sondern eher das eigentliche Layout selbst. Dies ist nützlich, um ein einzelnes Flex Layout-Element zu stylen, das ein Kind eines größeren Flex Layout-Elements ist, unabhängig von den Styles, die auf alle Kinder angewendet werden.

### Reihenfolge {#order}

Die `ItemOrder`-Eigenschaft bestimmt, wie Komponenten innerhalb des Flex Layouts angezeigt werden, und wenn sie auf ein Flex Layout angewendet wird, weist sie einem Element die spezifische Reihenfolgenummer dieses Layouts zu. Dies überschreibt die standardmäßige "Quellreihenfolge", die in jedes Element eingebaut ist (die Reihenfolge, in der eine Komponente zu ihrem Elternteil hinzugefügt wird), und bedeutet, dass sie vor Elementen mit einer höheren Reihenfolge und nach Elementen mit einer niedrigeren Reihenfolge angezeigt wird.

Diese Eigenschaft akzeptiert einen ganzzahligen Wert ohne Einheit, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Beispielsweise wird ein Element mit einem Reihenfolgewert von 1 vor einem Element mit einem Reihenfolgewert von 2 erscheinen.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht ihre tatsächliche Position im DOM. Das bedeutet, dass Screenreader und andere Hilfstechnologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, nicht in der visuellen Reihenfolge.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Selbst-Ausrichtung {#self-alignment}

Die Selbst-Ausrichtung des Flex Layouts bezieht sich darauf, wie ein einzelnes Flex Layout-Objekt innerhalb seines übergeordneten Flex-Containers entlang der Kreuzachse ausgerichtet ist, die senkrecht zur Hauptachse steht. Die Kreuzachsen-Ausrichtung wird durch die `Alignment`-Eigenschaft kontrolliert.

Die `align-self`-Eigenschaft gibt die Ausrichtung eines einzelnen Flex-Elements entlang der Kreuzachse an und überschreibt die standardmäßige Ausrichtung, die durch die `alignContent`-Eigenschaft in einem Flex Layout-Objekt festgelegt wurde. Dies ermöglicht es Ihnen, einzelne Flex Layout-Objekte anders auszurichten als die anderen im Container.

:::info
Die Selbst-Ausrichtung verwendet die gleichen Werte wie die Inhaltsausrichtung.
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders anordnen müssen als die anderen Elemente im Container. Unten sehen Sie ein Beispiel für die Ausrichtung eines einzelnen Elements:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex Basis {#flex-basis}

`Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung des Flex Layouts verwendet wird, um die anfängliche Größe eines Flex-Elements festzulegen, bevor der restliche Platz verteilt wird.

Die `Item Basis`-Eigenschaft gibt die Standardgröße eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (für eine Reihenrichtung) oder vertikal (für eine Säulenrichtung) ist. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements in Abhängigkeit vom Wert der Flex-Direktionseigenschaft fest.

:::info
Standardmäßig ist die `Item Basis`-Eigenschaft auf auto eingestellt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine spezifische Größe für das Element angeben, indem Sie verschiedene Einheiten wie Pixel (px), ems (em), Prozentsätze (%) oder eine andere CSS-Längen Einheit verwenden.
:::

Die folgende Demo ermöglicht es Ihnen, ein oder mehrere Kästchen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex wachsen / schrumpfen {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die zusammenwirken, um zu bestimmen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Platz in einem Flex Layout-Objekt auszufüllen.

Die `Item Grow`-Eigenschaft gibt an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container wachsen kann. Sie nimmt einen wertlosen Wert an, der einen Anteil des verfügbaren Platzes darstellt, der dem Element zugewiesen werden soll. Zum Beispiel, wenn ein Element einen `Item Grow`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel wachsen wie das erste Element.

Die `Item Shrink`-Eigenschaft gibt andererseits an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container schrumpfen kann. Sie nimmt ebenfalls einen wertlosen Wert an, der einen Anteil des verfügbaren Platzes darstellt, der dem Element zugewiesen werden soll. Zum Beispiel, wenn ein Element einen `Item Shrink`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel schrumpfen wie das erste Element.

Wenn ein Container mehr Platz hat, als benötigt wird, um seinen Inhalt aufzunehmen, werden Flex-Elemente mit einem `Item Grow`-Wert größer als 0 sich ausdehnen, um den verfügbaren Platz auszufüllen. Der Betrag des Platzes, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow`-Werts zum Gesamt-`Item Grow`-Wert aller Elemente im Container bestimmt.

Ähnlich wird, wenn ein Container nicht genügend Platz hat, um seinen Inhalt aufzunehmen, Flex-Elemente mit einem `Item Shrink`-Wert größer als 0 schrumpfen, um in den verfügbaren Platz zu passen. Der Betrag des Platzes, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink`-Werts zum Gesamt-`Item Shrink`-Wert aller Elemente im Container bestimmt.

## Beispiel-Form {#example-form}
Das Formular unten zeigt, wie `FlexLayout` Eingabefelder in ein strukturiertes Layout organisiert. 

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, sehen Sie sich die ColumnsLayout-Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout) an, um zu sehen, wie es sich vergleicht.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
