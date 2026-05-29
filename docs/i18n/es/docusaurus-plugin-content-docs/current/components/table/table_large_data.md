---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 9431d33c6fea2dd9d4ff4b165877e7d5
---
## Desplazamiento virtual {#virtual-scrolling}

El componente `Table` está diseñado para manejar grandes conjuntos de datos de manera eficiente utilizando el desplazamiento virtual, que es una técnica utilizada en aplicaciones web para optimizar la representación y el rendimiento de listas o tablas grandes al renderizar solo los elementos visibles en la pantalla.

### Renderizado inicial {#initial-render}

El desplazamiento virtual es un patrón de diseño en el cual, inicialmente, solo se renderiza un pequeño subconjunto de elementos que encajan dentro del área visible del contenedor desplazable. Esto minimiza la cantidad de elementos DOM creados y acelera el proceso de renderizado inicial.

### Carga dinámica {#dynamic-loading}
A medida que el usuario desplaza hacia abajo o hacia arriba, nuevos elementos se cargan dinámicamente en la vista. Estos elementos se obtienen típicamente de la fuente de datos en función de la posición actual del desplazamiento.

### Reciclaje de elementos {#item-recycling}
En lugar de crear un nuevo elemento DOM para cada elemento, el desplazamiento virtual a menudo reutiliza elementos DOM existentes. A medida que un elemento se mueve fuera del área visible, su elemento DOM se recicla y se reutiliza para un nuevo elemento que entra en el área visible. Este proceso de reciclado ayuda a reducir el uso de memoria y mejora el rendimiento.

### Beneficios de rendimiento: {#performance-benefits}

La principal ventaja del desplazamiento virtual es la mejora del rendimiento, especialmente al tratar con un gran número de elementos. Reduce la cantidad de manipulación del DOM y mejora la capacidad de respuesta general de la interfaz de usuario.

La siguiente `Table` muestra a todos los ganadores olímpicos - un conjunto de datos grande que se beneficia enormemente de la funcionalidad de desplazamiento virtual de la tabla:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={[
  'src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sobrerenderizado {#overscan}

Configurar la propiedad `Overscan` de la tabla determina cuántas filas se renderizan fuera del área visible. Esta configuración se puede ajustar con el método `setOverscan(double value)`.

Un valor de sobrerenderizado más alto puede reducir la frecuencia de renderizado al desplazarse, pero a costa de renderizar más filas de las que son visibles en un momento dado. Este puede ser un compromiso entre el rendimiento de renderizado y la suavidad del desplazamiento.
