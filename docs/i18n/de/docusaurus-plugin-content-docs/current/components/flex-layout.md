---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Die `FlexLayout` Komponente arrangiert Kindkomponenten in einer Reihe oder Spalte unter Verwendung des CSS Flexbox-Modells. Sie gibt Ihnen Kontrolle über Ausrichtung, Abstände, Umbrüche und darüber, wie Elemente wachsen oder schrumpfen, um den verfügbaren Raum zu füllen.

<!-- INTRO_END -->

## `FlexLayout` Eigenschaften {#flex-layout-properties}

Die `FlexLayout` Eigenschaften können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das `FlexLayout`, oder das Elternelement, ist eine Box/Container, die ein oder mehrere Komponenten enthalten kann. Alles innerhalb eines `FlexLayout` wird als Element oder Kind-Element bezeichnet. Das `FlexLayout` bietet einige Ausrichtungsmöglichkeiten, die durch Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die `FlexLayout` Komponente folgt dem Muster des [CSS Flexbox-Layouts](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Allerdings ist `FlexLayout` dafür gemacht, vollständig in Java verwendet zu werden, und benötigt keine CSS-Nutzung außerhalb der bereitgestellten Java-API-Methoden.
:::

## Containereigenschaften {#container-properties}

Container-Eigenschaften gelten für alle Komponenten innerhalb eines Containers und nicht für das Layout selbst. Sie beeinflussen nicht die Ausrichtung oder Platzierung des Elternteils, sondern nur die Kindkomponenten.

### Richtung {#direction}

Das `FlexLayout` fügt Komponenten nebeneinander entsprechend seiner Richtung hinzu, entweder horizontal oder vertikal. Bei der Verwendung des Builders verketten Sie die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()` mit der Methode `FlexLayout.create()`, um das Layout bei der Erstellung des Objekts zu konfigurieren.

Um die Richtung für ein bestehendes `FlexLayout` Objekt festzulegen, verwenden Sie die Methode `setDirection()`. Die horizontalen Optionen sind `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben).

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionierung {#positioning}

Komponenten, die horizontal hinzugefügt werden, können auch sowohl horizontal als auch vertikal positioniert werden. Verwenden Sie die Methoden `justify()`, `align()` und `contentAlign()` aus dem `FlexLayout` Builder, um die Positionierung beim Erstellen eines neuen `FlexLayout` zu konfigurieren.

Alternativ können Sie beim tatsächlichen `FlexLayout` Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()` verwenden, um die vertikale Positionierung zu konfigurieren. Um den Bereich um Komponenten entlang der Querachse (y-Achse für horizontale Layouts) zu ändern, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` steuert, wie Elemente entlang der Querachse insgesamt innerhalb des Containers angezeigt werden, und ist effektiv für einzeilige Layouts.

Die Methode `setAlignContent()` steuert den Raum um die Querachse und wird nur wirksam, wenn ein Layout mehrere Linien hat.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Umbruch {#wrapping}

Um das `FlexLayout` weiter anzupassen, können Sie sein Verhalten angeben, wenn Komponenten hinzugefügt werden, die nicht mehr in die Anzeige passen. Um dies mit dem Builder zu konfigurieren, verwenden Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()`, um Umbrüche zu konfigurieren. Um dies bei einem bestehenden `FlexLayout` Objekt zu konfigurieren, verwenden Sie die Methode `setWrap()`.

### Abstände {#spacing}

Um einen minimalen Abstand zwischen Elementen anzuwenden, können Sie die `gap` Eigenschaft festlegen. Sie gilt nur für die Abstände zwischen Elementen, nicht an den äußeren Kanten.

Das Verhalten der Gap-Eigenschaft kann als minimale Distanz zwischen den Elementen betrachtet werden, sodass sie nur wirksam wird, wenn sie der größte berechnete Raum zwischen den Elementen ist. Wenn der Abstand zwischen den Elementen aufgrund einer anderen berechneten Eigenschaft, wie z.B. `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, größer wäre, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Der Flexfluss, der eine Kombination aus den Richtung- und Umbruch-Eigenschaften ist, kann mit der Methode `setFlow()` auf einem `FlexLayout` Objekt festgelegt werden.

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Richtungs- und Umbruchmethoden. Zum Beispiel, um einen Spaltenumbruchfluss zu erstellen, verwenden Sie die Kombination `.vertical().wrap()`.
:::

### Container-Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten Flex-Eigenschaften aus den verschiedenen Menüs zu erstellen. Dieses Tool kann nicht nur verwendet werden, um ein visuelles Beispiel für die verschiedenen Methoden zu erstellen, sondern auch um Ihre eigenen Layouts mit den gewünschten Eigenschaften zu erstellen. Um ein Layout, das Sie angepasst haben, zu verwenden, kopieren Sie einfach den ausgegebenen Code und fügen Sie Ihre gewünschten Elemente für die Verwendung in Ihrem Programm hinzu.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Elementeigenschaften {#item-properties}

Die Eigenschaften der Elemente haben keinen Einfluss auf irgendwelche Kind-Elemente innerhalb des `FlexLayout`, sondern beeinflussen das tatsächlich Layout selbst. Dies ist nützlich, um ein einzelnes `FlexLayout` Element, das ein Kind eines größeren `FlexLayout` Elements ist, unabhängig von Stilen, die auf alle Kinder angewendet werden, zu stylen.

### Reihenfolge {#order}

Die `ItemOrder` Eigenschaft bestimmt die Reihenfolge, in der Komponenten innerhalb des `FlexLayout` angezeigt werden, und wenn sie auf einem `FlexLayout` verwendet wird, weist dies einem Element die spezifische Ordnungsnummer dieses Layouts zu. Dies überschreibt die Standard-"Quellreihenfolge", die in jedem Element (die Reihenfolge, in der ein Element zu seinem Elternteil hinzugefügt wird) eingebaut ist, und bedeutet, dass es vor Elementen mit einer höheren Ordnung und nach Elementen mit einer niedrigeren Ordnung angezeigt wird.

Diese Eigenschaft akzeptiert einen wertlosen Ganzzahlwert, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Beispielsweise wird ein Element mit einem Ordnungswert von 1 vor einem Element mit einem Ordnungswert von 2 erscheinen.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht ihre tatsächliche Position im DOM. Das bedeutet, dass Screenreader und andere Hilfstechnologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, nicht in der visuellen Reihenfolge.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Selbstausrichtung {#self-alignment}

Die Selbstaufrichtung von `FlexLayout` bezieht sich darauf, wie ein einzelnes `FlexLayout` Objekt innerhalb seines übergeordneten Flexcontainers entlang der Querachse, die senkrecht zur Hauptachse steht, ausgerichtet ist. Die Ausrichtung der Querachse wird durch die `Alignment` Eigenschaft gesteuert.

Die Eigenschaft align-self gibt die Ausrichtung eines einzelnen Flex-Elements entlang der Querachse an und überschreibt die Standardausrichtung, die durch die `AlignContent` Eigenschaft in einem `FlexLayout` Objekt festgelegt wird. Dies ermöglicht es Ihnen, individuelle `FlexLayout` Objekte anders auszurichten als die anderen im Container.

:::info
Die Selbstaufrichtung verwendet die gleichen Werte wie die Inhaltsausrichtung.
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders ausrichten müssen als die anderen Elemente im Container. Siehe das folgende Beispiel für ein Beispiel zur Ausrichtung eines einzelnen Elements:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex-Basis {#flex-basis}

Die `Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung von `FlexLayout` verwendet wird, um die anfängliche Größe eines Flex-Elements festzulegen, bevor zusätzlicher Raum verteilt wird.

Die `Item Basis` Eigenschaft gibt die Standardgröße eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (für eine Reihenrichtung) oder vertikal (für eine Spaltenrichtung) ist. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements fest, je nach dem Wert der Flex-Richtungseigenschaft.

:::info
Standardmäßig ist die `Item Basis` Eigenschaft auf `auto` eingestellt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine spezifische Größe für das Element festlegen, z.B. in Pixeln (px), Ems (em), Prozenten (%) oder jeder anderen CSS-Längeneinheit.
:::

Die folgende Demo ermöglicht es Ihnen, ein oder mehrere Kästchen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex wachsen und schrumpfen {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die in Verbindung miteinander und mit der `Item Basis` Eigenschaft arbeiten, um zu bestimmen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Platz innerhalb eines `FlexLayout` Objekts zu füllen.

Die `Item Grow` Eigenschaft gibt an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container wachsen kann. Sie nimmt einen wertlosen Wert an, der einen Anteil des verfügbaren Raums darstellt, der dem Element zugeordnet werden sollte. Beispielsweise wird ein Element mit einem `Item Grow` Wert von 1 und ein anderes mit einem Wert von 2, wobei das zweite Element doppelt so viel wächst wie das erste Element.

Die `Item Shrink` Eigenschaft hingegen gibt an, wie viel das Flex-Element im Verhältnis zu den anderen Elementen im Container schrumpfen kann. Sie nimmt ebenfalls einen wertlosen Wert an, der einen Anteil des verfügbaren Raums darstellt, der dem Element zugeordnet werden sollte. Beispielsweise wird ein Element mit einem `Item Shrink` Wert von 1 und ein anderes mit einem Wert von 2, wobei das zweite Element doppelt so viel schrumpft wie das erste Element.

Wenn ein Container mehr Platz hat als benötigt, um seinen Inhalt unterzubringen, werden Flex-Elemente mit einem `Item Grow` Wert größer als 0 sich ausdehnen, um den verfügbaren Platz zu füllen. Der Raum, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow` Wertes zum Gesamten `Item Grow` Wert all der Elemente im Container bestimmt.

Ähnlich wird, wenn ein Container nicht genügend Platz hat, um seinen Inhalt unterzubringen, Flex-Elemente mit einem `Item Shrink` Wert größer als 0 schrumpfen, um den verfügbaren Platz zu füllen. Der Raum, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink` Wertes zum Gesamten `Item Shrink` Wert all der Elemente im Container bestimmt.

## Beispiel-Formular {#example-form}
Das folgende Formular zeigt, wie `FlexLayout` Eingabefelder in einem strukturierten Layout organisiert.

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, sehen Sie sich die `ColumnsLayout` Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout) an, um zu sehen, wie es sich vergleicht.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
