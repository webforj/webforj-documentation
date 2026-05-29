---
title: FlexLayout
sidebar_position: 45
_i18n_hash: cf7ba76f1e13488c6fa3a419ba6ceaca
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Die `FlexLayout`-Komponente ordnet Kindkomponenten in einer Reihe oder Spalte unter Verwendung des CSS Flexbox-Modells an. Sie gibt Ihnen Kontrolle über Ausrichtung, Abstände, Umbrüche und darüber, wie Elemente wachsen oder schrumpfen, um den verfügbaren Platz auszufüllen.

<!-- INTRO_END -->

## `FlexLayout` Eigenschaften {#flex-layout-properties}

Die `FlexLayout`-Eigenschaften können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das `FlexLayout` oder das Elternelement ist eine Box/Container, die ein oder mehrere Komponenten enthalten kann. Alles innerhalb eines `FlexLayout` wird als Element oder Kind-Element bezeichnet. Das `FlexLayout` bietet einige Ausrichtungsfähigkeiten, die entweder mit Hilfe von Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die `FlexLayout`-Komponente folgt dem Muster des [CSS-Flexbox-Layouts](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Allerdings ist `FlexLayout` so gestaltet, dass es vollständig in Java verwendet werden kann und die Verwendung von CSS außerhalb der bereitgestellten Java-API-Methoden nicht erforderlich ist.
:::

## Container-Eigenschaften {#container-properties}

Container-Eigenschaften gelten für alle Komponenten innerhalb eines Containers und nicht für das Layout selbst. Sie beeinflussen nicht die Orientierung oder Platzierung des Elternteils, sondern nur die Kindkomponenten.

### Richtung {#direction}

Das `FlexLayout` fügt Komponenten entsprechend seiner Richtung, entweder horizontal oder vertikal, nebeneinander hinzu. Beim Verwenden des Builders verketten Sie die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()` mit der Methode `FlexLayout.create()`, um das Layout beim Erstellen des Objekts zu konfigurieren.

Um die Richtung für ein vorhandenes `FlexLayout`-Objekt festzulegen, verwenden Sie die Methode `setDirection()`. Die horizontalen Optionen sind `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Positionierung {#positioning}

Horizontal hinzugefügte Komponenten können sowohl horizontal als auch vertikal positioniert werden. Verwenden Sie die Methoden `justify()`, `align()` und `contentAlign()` des `FlexLayout`-Builders, um die Positionierung beim Erstellen eines neuen `FlexLayout` zu konfigurieren.

Alternativ können Sie an dem tatsächlichen `FlexLayout`-Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()`, um die vertikale Positionierung zu konfigurieren. Um den Bereich um Komponenten entlang der Querachse (y-Achse für horizontale Layouts) zu ändern, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` steuert, wie die Elemente entlang der Querachse insgesamt innerhalb des Containers angezeigt werden, und ist wirksam für einzeilige Layouts.

Die Methode `setAlignContent()` steuert den Raum um die Querachse und tritt nur in Kraft, wenn ein Layout mehrere Linien hat.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Umbruch {#wrapping}

Um die `FlexLayout`-Komponente weiter anzupassen, können Sie ihr Verhalten angeben, wenn Komponenten hinzugefügt werden, die nicht mehr in die Anzeige passen. Um dies mit dem Builder zu konfigurieren, verwenden Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()`, um den Umbruch zu konfigurieren. Um dies an einem vorhandenen `FlexLayout`-Objekt zu konfigurieren, verwenden Sie die Methode `setWrap()`.

### Abstände {#spacing}

Um Mindestabstände zwischen Elementen anzuwenden, können Sie die `gap`-Eigenschaft festlegen. Sie wendet diesen Abstand nur zwischen Elementen an, nicht an den äußeren Kanten.

Das Verhalten der Gap-Eigenschaft kann als Mindestabstand zwischen den Elementen betrachtet werden, sodass sie nur wirksam wird, wenn sie der größte berechnete Abstand zwischen den Elementen ist. Wenn der Abstand zwischen den Elementen aus anderen berechneten Eigenschaften, wie z. B. `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, andernfalls größer wäre, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Flex-Fluss, der eine Kombination aus den Richtungs- und Umbruch-Eigenschaften ist, kann mit der Methode `setFlow()` an einem `FlexLayout`-Objekt festgelegt werden.

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Richtungs- und Umbruchmethoden. Zum Beispiel, um einen Spaltenumbruchfluss zu erstellen, verwenden Sie die Kombination `.vertical().wrap()`.
:::

### Container-Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten flex-Eigenschaften aus den verschiedenen Menüs auszuwählen. Dieses Tool kann nicht nur verwendet werden, um ein visuelles Beispiel der verschiedenen Methoden zu erstellen, sondern auch, um Ihre eigenen Layouts mit den gewünschten Eigenschaften zu erstellen. Um ein von Ihnen angepasstes Layout zu verwenden, kopieren Sie einfach den Ausgabecode und fügen Sie Ihre gewünschten Elemente zur Verwendung in Ihrem Programm hinzu.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Elementeigenschaften {#item-properties}

Die Elementeigenschaften wirken sich nicht auf die Kind-Elemente innerhalb des `FlexLayout` aus, sondern beeinflussen das Layout selbst. Dies ist nützlich, um ein einzelnes `FlexLayout`-Element zu stylen, das ein Kind eines größeren `FlexLayout`-Elements ist, unabhängig von den Stilen, die auf alle Kinder angewendet werden.

### Reihenfolge {#order}

Die `ItemOrder`-Eigenschaft bestimmt die Reihenfolge, in der Komponenten innerhalb des `FlexLayout` angezeigt werden. Wenn sie auf ein `FlexLayout` angewendet wird, wird einem Element die spezifische Ordnungsnummer dieses Layouts zugewiesen. Dies überschreibt die standardmäßige "Quellreihenfolge", die in jedes Element integriert ist (die Reihenfolge, in der ein Element zu seinem Elternteil hinzugefügt wird), und bedeutet, dass es vor Elementen mit einer höheren Reihenfolge und nach Elementen mit einer niedrigeren Reihenfolge angezeigt wird.

Diese Eigenschaft akzeptiert einen ganzzahligen Wert ohne Einheit, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Ein Element mit einem Reihenwert von 1 wird beispielsweise vor einem Element mit einem Reihenwert von 2 erscheinen.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht ihre tatsächliche Position im DOM. Das bedeutet, dass Bildschirmleser und andere unterstützende Technologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, nicht in der visuellen Reihenfolge.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Selbst-Ausrichtung {#self-alignment}

Die Selbst-Ausrichtung von `FlexLayout` bezieht sich darauf, wie ein einzelnes `FlexLayout`-Objekt innerhalb seines übergeordneten Flex-Containers entlang der Querachse ausgerichtet ist, die senkrecht zur Hauptachse verläuft. Die Ausrichtung der Querachse wird durch die `Alignment`-Eigenschaft gesteuert.

Die align-self-Eigenschaft gibt die Ausrichtung eines einzelnen Flex-Elements entlang der Querachse an und überschreibt die standardmäßige Ausrichtung, die von der `AlignContent`-Eigenschaft in einem `FlexLayout`-Objekt festgelegt wurde. Dadurch können Sie einzelne `FlexLayout`-Objekte anders ausrichten als die anderen im Container.

:::info
Die Selbst-Ausrichtung verwendet die gleichen Werte wie die Inhaltsausrichtung.
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders ausrichten müssen als die anderen Elemente im Container. Siehe das Beispiel unten für ein Beispiel zur Ausrichtung eines einzelnen Elements:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex-Basis {#flex-basis}

`Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung von `FlexLayout` verwendet wird, um die Anfangsgröße eines Flex-Elements festzulegen, bevor der verbleibende Platz verteilt wird.

Die `Item Basis`-Eigenschaft gibt die Standardgröße eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (für eine Reihenrichtung) oder vertikal (für eine Spaltenrichtung) ist. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements fest, abhängig vom Wert der flex-direction-Eigenschaft.

:::info
Standardmäßig ist die `Item Basis`-Eigenschaft auf `auto` eingestellt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine bestimmte Größe für das Element mit verschiedenen Einheiten wie Pixel (px), ems (em), Prozent (%) oder jede andere CSS-Längeneinheit festlegen.
:::

Die folgende Demo ermöglicht es Ihnen, ein oder mehrere Kästen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex-Wachstum und -Schrumpfung {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die zusammenarbeiten und mit der `Item Basis`-Eigenschaft dazu dienen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Platz innerhalb eines `FlexLayout`-Objekts zu füllen.

Die `Item Grow`-Eigenschaft gibt an, wie viel das Flex-Element relativ zu den anderen Elementen im Container wachsen kann. Sie akzeptiert einen wertlosen Wert, der einen Anteil des verfügbaren Platzes darstellt, der dem Element zugewiesen werden sollte. Wenn beispielsweise ein Element einen `Item Grow`-Wert von 1 hat und ein anderes einen Wert von 2, dann wird das zweite Element doppelt so viel wachsen wie das erste Element.

Die `Item Shrink`-Eigenschaft hingegen gibt an, wie viel das Flex-Element relativ zu den anderen Elementen im Container schrumpfen kann. Sie nimmt ebenfalls einen wertlosen Wert an, der einen Anteil des verfügbaren Platzes darstellt, der dem Element zugewiesen werden sollte. Wenn beispielsweise ein Element einen `Item Shrink`-Wert von 1 hat und ein anderes einen Wert von 2, wird das zweite Element doppelt so viel schrumpfen wie das erste Element.

Wenn ein Container mehr Platz hat, als benötigt wird, um seinen Inhalt unterzubringen, werden die Flex-Elemente mit einem `Item Grow`-Wert größer als 0, um den verfügbaren Platz auszufüllen. Der Betrag des Platzes, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow`-Wertes zum gesamten `Item Grow`-Wert aller Elemente im Container bestimmt.

Ebenso, wenn ein Container nicht genügend Platz hat, um seinen Inhalt unterzubringen, werden Flex-Elemente mit einem `Item Shrink`-Wert größer als 0 schrumpfen, um in den verfügbaren Platz zu passen. Der Betrag des Platzes, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink`-Wertes zum gesamten `Item Shrink`-Wert aller Elemente im Container bestimmt.

## Beispielformular {#example-form}
Das folgende Formular demonstriert, wie `FlexLayout` Eingabefelder in einem strukturierten Layout organisiert.

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, sehen Sie sich die `ColumnsLayout`-Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout) an, um zu sehen, wie sie sich vergleicht.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/resources/static/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
