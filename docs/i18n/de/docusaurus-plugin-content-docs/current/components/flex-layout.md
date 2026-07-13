---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Die `FlexLayout`-Komponente arrangiert untergeordnete Komponenten in einer Reihe oder Spalte und verwendet dabei das CSS Flexbox-Modell. Sie gibt Ihnen die Kontrolle über Ausrichtung, Abstände, Zeilenumbrüche und darüber, wie Elemente wachsen oder schrumpfen, um den verfügbaren Platz auszufüllen.

<!-- INTRO_END -->

## `FlexLayout`-Eigenschaften {#flex-layout-properties}

Die `FlexLayout`-Eigenschaften können in zwei Kategorien unterteilt werden: Eigenschaften, die auf die Elemente innerhalb eines Layouts angewendet werden, und Eigenschaften, die auf das Layout selbst angewendet werden. Das `FlexLayout`, oder das Elternelement, ist eine Box/Container, die eine oder mehrere Komponenten enthalten kann. Alles, was sich in einem `FlexLayout` befindet, wird als Element oder untergeordnetes Element bezeichnet. Das `FlexLayout` bietet einige Ausrichtungsfunktionen, die mit Hilfe von Container- oder Elementeigenschaften erreicht werden können.

:::tip
Die `FlexLayout`-Komponente folgt dem Muster des [CSS-Flexbox-Layouts](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Allerdings ist `FlexLayout` dazu gedacht, vollständig in Java verwendet zu werden und erfordert nicht die Verwendung von CSS außerhalb der bereitgestellten Java-API-Methoden.
:::

## Containereigenschaften {#container-properties}

Containereigenschaften gelten für alle Komponenten innerhalb eines Komponenten und nicht für das Layout selbst. Sie beeinflussen nicht die Ausrichtung oder Platzierung des Elternteils, sondern nur die untergeordneten Komponenten.

### Richtung {#direction}

Das `FlexLayout` fügt Komponenten entsprechend seiner Richtung, entweder horizontal oder vertikal, nebeneinander hinzu. Beim Verwenden des Builders verbinden Sie die Methoden `horizontal()`, `horizontalReverse()`, `vertical()` oder `verticalReverse()` mit der Methode `FlexLayout.create()`, um das Layout beim Erstellen des Objekts zu konfigurieren.

Um die Richtung eines bestehenden `FlexLayout`-Objekts festzulegen, verwenden Sie die Methode `setDirection()`. Die horizontalen Optionen sind `FlexDirection.ROW` (von links nach rechts) oder `FlexDirection.ROW_REVERSE` (von rechts nach links), und die vertikalen Optionen sind `FlexDirection.COLUMN` (von oben nach unten) oder `FlexDirection.COLUMN_REVERSE` (von unten nach oben).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Positionierung {#positioning}

Komponenten, die horizontal hinzugefügt werden, können auch sowohl horizontal als auch vertikal positioniert werden. Verwenden Sie die Methoden `justify()`, `align()` und `contentAlign()` des `FlexLayout`-Builders, um die Positionierung beim Erstellen eines neuen `FlexLayout` zu konfigurieren.

Alternativ können Sie am tatsächlichen `FlexLayout`-Objekt die Methode `setJustifyContent()` verwenden, um Elemente horizontal zu positionieren, und die Methode `setAlignment()`, um die vertikale Positionierung zu konfigurieren. Um den Bereich um Komponenten entlang der Querachse (y-Achse für horizontale Layouts) zu ändern, verwenden Sie die Methode `setAlignContent()`.

:::tip
Die Methode `setAlignment()` steuert, wie Elemente entlang der Querachse insgesamt im Container angezeigt werden, und ist effektiv für einzeilige Layouts.

Die Methode `setAlignContent()` steuert den Raum um die Querachse und tritt nur in Kraft, wenn ein Layout mehrere Zeilen hat.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Umbruch {#wrapping}

Um die `FlexLayout`-Komponente weiter anzupassen, können Sie ihr Verhalten festlegen, wenn Komponenten hinzugefügt werden, die nicht mehr in die Anzeige passen. Um dies mithilfe des Builders zu konfigurieren, verwenden Sie die Methoden `nowrap()` (Standard), `wrap()` und `wrapReverse()` zur Konfiguration des Umbruchs. Um dies an einem bestehenden `FlexLayout`-Objekt zu konfigurieren, verwenden Sie die Methode `setWrap()`.

### Abstände {#spacing}

Um einen Mindestabstand zwischen Elementen anzuwenden, können Sie die `gap`-Eigenschaft festlegen. Sie wird nur zwischen den Elementen, nicht an den äußeren Kanten angewendet.

Das Verhalten der Gap-Eigenschaft kann als Mindestabstand zwischen betrachtet werden, sodass sie nur wirksam wird, wenn sie der größte berechnete Abstand zwischen den Elementen ist. Wenn der Abstand zwischen den Elementen anderweitig aufgrund einer anderen berechneten Eigenschaft, wie z. B. durch `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, größer wäre, wird die Gap-Eigenschaft ignoriert.

### Fluss {#flow}

Der Flexfluss, der eine Kombination aus der Richtung und den Umbrucheigenschaften ist, kann mithilfe der Methode `setFlow()` an einem `FlexLayout`-Objekt festgelegt werden.

:::info
Um diese Eigenschaft beim Erstellen des Layouts zu konfigurieren, verwenden Sie die entsprechenden Richtungs- und Umbruchmethoden. Um beispielsweise einen Spaltenumbruchfluss zu erstellen, verwenden Sie die Kombination `.vertical().wrap()`.
:::

### Container-Builder {#container-builder}

Die folgende Demo ermöglicht es Ihnen, einen Container mit den gewünschten Flex-Eigenschaften aus den verschiedenen Menüs auszuwählen. Dieses Tool kann nicht nur verwendet werden, um ein visuelles Beispiel der verschiedenen Methoden zu erstellen, sondern auch, um Ihre eigenen Layouts mit den gewünschten Eigenschaften zu erstellen. Um ein von Ihnen angepasstes Layout zu verwenden, kopieren Sie einfach den ausgegebenen Code und fügen Sie Ihre gewünschten Elemente für die Verwendung in Ihrem Programm hinzu.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Elementeigenschaften {#item-properties}

Die Elementeigenschaften beeinflussen keine untergeordneten Elemente innerhalb des `FlexLayout`, sondern betreffen das tatsächliche Layout selbst. Dies ist nützlich, um ein einzelnes `FlexLayout`-Element zu stylen, das ein Kind eines größeren `FlexLayout`-Elements ist, unabhängig von Stilen, die auf alle Kinder angewendet werden.

### Reihenfolge {#order}

Die `ItemOrder`-Eigenschaft bestimmt die Reihenfolge, in der Komponenten innerhalb des `FlexLayout` angezeigt werden, und ordnet bei Verwendung an einem `FlexLayout` einem Element die spezifische Ordnungsnummer des Layouts zu. Dies überschreibt die standardmäßige "Quellreihenfolge", die in jedes Element eingebaut ist (die Reihenfolge, in der eine Komponente ihrem Elternteil hinzugefügt wird), und bedeutet, dass es vor Elementen mit höherer Ordnung und nach Elementen mit niedrigerer Ordnung angezeigt wird.

Diese Eigenschaft akzeptiert einen ganzzahligen Wert ohne Einheit, der die relative Reihenfolge des Flex-Elements innerhalb des Containers angibt. Je niedriger der Wert, desto früher erscheint das Element in der Reihenfolge. Ein Element mit einem Wert von 1 erscheint beispielsweise vor einem Element mit einem Wert von 2.

:::caution
Es ist wichtig zu beachten, dass die Reihenfolge-Eigenschaft nur die visuelle Reihenfolge der Elemente innerhalb des Containers beeinflusst, nicht ihre tatsächliche Position im DOM. Das bedeutet, dass Bildschirmleser und andere Hilfstechnologien die Elemente weiterhin in der Reihenfolge lesen, in der sie im Quellcode erscheinen, nicht in der visuellen Reihenfolge.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Selbstausrichtung {#self-alignment}

Die Selbstausrichtung des `FlexLayout` bezieht sich darauf, wie ein einzelnes `FlexLayout`-Objekt innerhalb seines übergeordneten Flexcontainers entlang der Querachse ausgerichtet ist, die senkrecht zur Hauptachse verläuft. Die Querachsen-Ausrichtung wird durch die `Alignment`-Eigenschaft gesteuert.

Die `align-self`-Eigenschaft gibt die Ausrichtung eines einzelnen Flex-Elements entlang der Querachse an und überschreibt die standardmäßige Ausrichtung, die durch die `AlignContent`-Eigenschaft in einem `FlexLayout`-Objekt festgelegt wurde. Dadurch können Sie einzelne `FlexLayout`-Objekte anders ausrichten als die anderen im Container.

:::info
Die Selbstausrichtung verwendet die gleichen Werte wie die Inhaltsausrichtung.
:::

Diese Eigenschaft ist besonders nützlich, wenn Sie ein bestimmtes Element anders als die anderen Elemente im Container ausrichten müssen. Siehe das folgende Beispiel für ein Beispiel zur Ausrichtung eines einzelnen Elements:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex-Basis {#flex-basis}

Die `Item Basis` ist eine Eigenschaft, die in Verbindung mit der Richtung des `FlexLayout` verwendet wird, um die anfängliche Größe eines Flex-Elements festzulegen, bevor der verbleibende Platz verteilt wird.

Die `Item Basis`-Eigenschaft gibt die standardmäßige Größe eines Flex-Elements entlang der Hauptachse an, die entweder horizontal (für eine Reihenfolge) oder vertikal (für eine Spaltenanordnung) sein kann. Diese Eigenschaft legt die Breite oder Höhe eines Flex-Elements in Abhängigkeit vom Wert der Flex-Richtung fest.

:::info
Standardmäßig ist die `Item Basis`-Eigenschaft auf `auto` eingestellt, was bedeutet, dass die Größe des Elements durch seinen Inhalt bestimmt wird. Sie können jedoch auch eine spezifische Größe für das Element mithilfe verschiedener Einheiten wie Pixel (px), em, Prozent (%) oder andere CSS-Längeneinheiten festlegen.
:::

Die folgende Demo ermöglicht es Ihnen, eines oder mehrere Kästchen auszuwählen und die `Item Basis` für die ausgewählten Elemente zu ändern.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex wachsen und schrumpfen {#flex-grow--shrink}

`Item Grow` und `Item Shrink` sind zwei Eigenschaften, die in Verbindung miteinander und mit der `Item Basis`-Eigenschaft arbeiten, um zu bestimmen, wie Flex-Elemente wachsen oder schrumpfen, um den verfügbaren Platz innerhalb eines `FlexLayout`-Objekts auszufüllen.

Die `Item Grow`-Eigenschaft gibt an, wie viel das Flex-Element relativ zu den anderen Elementen im Container wachsen kann. Sie nimmt einen wertlosen Wert an, der einen Anteil des verfügbaren Raums darstellt, der dem Element zugewiesen werden sollte. Wenn beispielsweise ein Element einen `Item Grow`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel wachsen wie das erste Element.

Die `Item Shrink`-Eigenschaft gibt dagegen an, wie viel das Flex-Element relativ zu den anderen Elementen im Container schrumpfen kann. Sie nimmt ebenfalls einen wertlosen Wert an, der einen Anteil des verfügbaren Raums darstellt, der dem Element zugewiesen werden sollte. Wenn beispielsweise ein Element einen `Item Shrink`-Wert von 1 hat und ein anderes einen Wert von 2 hat, wird das zweite Element doppelt so viel schrumpfen wie das erste Element.

Wenn ein Container mehr Platz hat, als benötigt wird, um seinen Inhalt unterzubringen, erweitern sich Flex-Elemente mit einem `Item Grow`-Wert größer als 0, um den verfügbaren Platz zu füllen. Der Betrag des Platzes, den jedes Element erhält, wird durch das Verhältnis seines `Item Grow`-Werts zum Gesamt-`Item Grow`-Wert aller Elemente im Container bestimmt.

Ähnlich verhält es sich, wenn ein Container nicht genügend Platz hat, um seinen Inhalt unterzubringen; Flex-Elemente mit einem `Item Shrink`-Wert größer als 0 schrumpfen, um im verfügbaren Platz zu passen. Der Betrag des Platzes, den jedes Element aufgibt, wird durch das Verhältnis seines `Item Shrink`-Werts zum Gesamt-`Item Shrink`-Wert aller Elemente im Container bestimmt.

## Beispielsformular {#example-form}
Das folgende Formular zeigt, wie `FlexLayout` Eingabefelder in einem strukturierten Layout organisiert.

:::tip
Wenn Sie eine spaltenbasierte Struktur bevorzugen, sehen Sie sich die `ColumnsLayout`-Version dieses Formulars im Artikel [`ColumnsLayout`](../components/columns-layout) an, um zu sehen, wie sie sich vergleichen.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
