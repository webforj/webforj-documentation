---
title: Splitter
sidebar_position: 115
_i18n_hash: 7a830c81311c3830e4d1c36bd08903c5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

El componente `Splitter`, diseñado para dividir y redimensionar contenido dentro de su aplicación, encapsula dos componentes redimensionables: el componente maestro y el componente de detalle. Un separador divide estos componentes, permitiendo a los usuarios ajustar dinámicamente el tamaño de cada componente de acuerdo a sus preferencias.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Min y max size {#min-and-max-size}

El componente `Splitter` proporciona métodos para establecer tamaños mínimos y máximos para sus paneles, lo que le permite controlar el comportamiento de redimensionamiento de los componentes dentro del `Splitter`. Cuando los usuarios intentan redimensionar paneles más allá de los tamaños mínimos o máximos especificados, el componente splitter impone estas restricciones, asegurando que los paneles permanezcan dentro de los límites definidos.

### Establecer tamaños {#setting-sizes}

El método `setMasterMinSize(String masterMinSize)` especifica el tamaño mínimo para el panel maestro del splitter. Del mismo modo, el método `setMasterMaxSize(String masterMaxSize)` especifica el tamaño máximo para el panel maestro.

Puede especificar tamaños utilizando cualquier unidad CSS válida, como se muestra a continuación:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Orientación {#orientation}

Puede configurar la orientación en el componente `Splitter`, lo que le permite crear diseños adaptados a requisitos de diseño específicos. Al especificar la orientación, el componente organiza los paneles horizontal o verticalmente, proporcionando versatilidad en el diseño.

Para configurar la orientación, utilice el Enum de orientaciones soportadas para especificar si el `Splitter` debe renderizarse horizontal o verticalmente:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Posición relativa {#relative-position}

Para establecer la posición inicial de la barra divisoria en el componente `Splitter`, utilice `setPositionRelative`. Este método toma un valor numérico de `0` a `100` que representa el porcentaje del espacio dado en el `Splitter`, y muestra el divisor en el porcentaje dado del ancho total:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Anidación {#nesting}

La anidación de Splitter permite crear diseños complejos con niveles de paneles redimensionables. Esto permite la creación de interfaces de usuario sofisticadas con un control granular sobre la disposición y el redimensionamiento del contenido.

Para anidar componentes Splitter, instancie nuevas instancias de `Splitter` y agréguelas como hijas de los componentes `Splitter` existentes. Esta estructura jerárquica permite la creación de diseños de múltiples niveles con capacidades de redimensionamiento flexible. El siguiente programa demuestra esto:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Guardado automático {#auto-save}

El componente `Splitter` incluye una opción de AutoSave, que guarda el estado de los tamaños de los paneles en el almacenamiento local para mantener las dimensiones consistentes entre recargas.

Cuando configura la configuración de auto-guardar, el componente `Splitter` almacena automáticamente el estado de los tamaños de los paneles en el almacenamiento local del navegador web. Esto asegura que los tamaños que los usuarios elijan para los paneles persistan entre recargas de página o sesiones del navegador, reduciendo la necesidad de ajustes manuales.

### Limpiar el estado {#cleaning-the-state}

Para revertir programáticamente el `Splitter` a la configuración y dimensiones predeterminadas, llame al método `cleanState()` para eliminar cualquier dato de estado guardado relacionado con el componente `Splitter` del almacenamiento local del navegador web.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

En la demostración anterior, cada instancia de Splitter activa la función de AutoSave llamando al método `setAutosave`. Esto asegura que los tamaños de los paneles se guarden automáticamente en el almacenamiento local. Por lo tanto, al recargar el navegador, los tamaños de estos splitters permanecen iguales.

Hacer clic en el botón "Limpiar Estado" llama al método `cleanState()` y refresca la ventana del navegador para mostrar las dimensiones originales.

## Estilo {#styling}

<TableBuilder name="Splitter" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente `Splitter`, considere las siguientes mejores prácticas:

- **Ajustar según el contenido**: Al decidir sobre la orientación y los tamaños iniciales de los paneles, considere la prioridad del contenido. Por ejemplo, en un diseño con una barra lateral de navegación y un área de contenido principal, la barra lateral debe permanecer típicamente más estrecha con un tamaño mínimo establecido para una navegación clara.

- **Anidación estratégica**: Anidar splitters puede crear diseños versátiles, pero puede complicar la interfaz de usuario y afectar el rendimiento. Planifique sus diseños anidados para garantizar que sean intuitivos y mejoren la experiencia del usuario.

- **Recordar las preferencias del usuario**: Utilice la función de AutoSave para recordar los ajustes del usuario en sesiones, mejorando la experiencia del usuario. Proporcione una opción que permita a los usuarios restablecer a la configuración predeterminada.
