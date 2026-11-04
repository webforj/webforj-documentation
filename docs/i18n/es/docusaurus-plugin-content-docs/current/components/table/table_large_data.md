---
sidebar_position: 25
title: Grandes conjuntos de datos
slug: data
description: >-
  Handle large Table datasets efficiently with virtual scrolling, dynamic row
  loading, DOM recycling, and overscan tuning.
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## Desplazamiento virtual {#virtual-scrolling}

El componente `Table` está diseñado para gestionar grandes conjuntos de datos de forma eficiente mediante el desplazamiento virtual, una técnica que se utiliza en aplicaciones web para optimizar la representación y el rendimiento de listas o tablas grandes, mostrando solo los elementos visibles en la pantalla.

### Representación inicial {#initial-render}

El desplazamiento virtual es un patrón de diseño en el que, inicialmente, solo se representa un pequeño subconjunto de elementos que cabe dentro del área visible del contenedor desplazable. Esto minimiza la cantidad de elementos DOM que se crean y acelera el proceso de representación inicial.

### Carga dinámica {#dynamic-loading}
A medida que el usuario se desplaza hacia arriba o hacia abajo, se cargan dinámicamente nuevos elementos en la vista. Por lo general, estos elementos se obtienen del origen de datos según la posición actual del desplazamiento.

### Reciclaje de elementos {#item-recycling}
En lugar de crear un nuevo elemento DOM para cada elemento, el desplazamiento virtual suele reutilizar los elementos DOM existentes. Cuando un elemento sale del área visible, su elemento DOM se recicla y se reutiliza para un nuevo elemento que entra en el área visible. Este proceso de reciclaje ayuda a reducir el uso de memoria y mejora el rendimiento.

### Ventajas de rendimiento: {#performance-benefits}

La principal ventaja del desplazamiento virtual es el mejor rendimiento, especialmente cuando se trabaja con una gran cantidad de elementos. Reduce la manipulación del DOM y mejora la capacidad de respuesta general de la interfaz de usuario.

La siguiente `Table` muestra todos los ganadores olímpicos: un gran conjunto de datos que se beneficia enormemente de la funcionalidad de desplazamiento virtual de la tabla:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Overscan {#overscan}

La propiedad `Overscan` de la tabla determina cuántas filas se representan fuera del área visible. Este valor se puede configurar con el método `setOverscan(double value)`.

Un valor de overscan más alto puede reducir la frecuencia de representación durante el desplazamiento, pero a costa de representar más filas de las que están visibles en un momento dado. Esto implica un equilibrio entre el rendimiento de la representación y la fluidez del desplazamiento.
