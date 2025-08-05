---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 83619419eb87c85aa5e309ee153af7fb
---
## Desplazamiento virtual {#virtual-scrolling}

El componente `Table` está diseñado para manejar eficientemente grandes conjuntos de datos mediante el uso de desplazamiento virtual, que es una técnica utilizada en aplicaciones web para optimizar la representación y el rendimiento de grandes listas o tablas al renderizar solo los elementos visibles en la pantalla.

### Renderizado inicial {#initial-render}

El desplazamiento virtual es un patrón de diseño en el que, inicialmente, solo se renderiza un pequeño subconjunto de elementos que se ajustan al área visible del contenedor desplazable. Esto minimiza la cantidad de elementos DOM creados y acelera el proceso de renderizado inicial.

### Carga dinámica {#dynamic-loading}
A medida que el usuario se desplaza hacia abajo o hacia arriba, se cargan dinámicamente nuevos elementos en la vista. Estos elementos generalmente se obtienen de la fuente de datos según la posición de desplazamiento actual.

### Reciclaje de elementos {#item-recycling}
En lugar de crear un nuevo elemento DOM para cada elemento, el desplazamiento virtual a menudo reutiliza los elementos DOM existentes. A medida que un elemento se mueve fuera del área visible, su elemento DOM se recicla y se reutiliza para un nuevo elemento que entra en el área visible. Este proceso de reciclaje ayuda a reducir el uso de memoria y mejora el rendimiento.

### Beneficios de rendimiento: {#performance-benefits}

La principal ventaja del desplazamiento virtual es el rendimiento mejorado, especialmente al tratar con un gran número de elementos. Reduce la cantidad de manipulación del DOM y mejora la capacidad de respuesta general de la interfaz de usuario.

La siguiente `Table` muestra a todos los ganadores olímpicos - un conjunto de datos grande que se beneficia enormemente de la funcionalidad de desplazamiento virtual de la tabla:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Configurar la propiedad `Overscan` de la tabla determina cuántas filas se renderizan fuera del área visible. Esta configuración se puede ajustar con el método `setOverscan(double value)`.

Un valor de overscan más alto puede reducir la frecuencia de renderizado al desplazarse, pero a costa de renderizar más filas de las visibles en un momento dado. Esto puede ser un compromiso entre el rendimiento de renderizado y la suavidad del desplazamiento.
