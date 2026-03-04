---
title: Splitter
sidebar_position: 115
_i18n_hash: 2f66a9093a3c1f6e339df8fb42048a55
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

El componente `Splitter`, diseñado para dividir y redimensionar contenido dentro de tu aplicación, encapsula dos componentes redimensionables: el componente maestro y el componente detalle. Un divisor separa estos componentes, permitiendo a los usuarios ajustar dinámicamente el tamaño de cada componente según sus preferencias.

<!-- INTRO_END -->

## Creando un splitter {#creating-a-splitter}

Crea un `Splitter` pasando dos componentes a su constructor. El primero se convierte en el panel maestro y el segundo en el panel detalle.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Tamaño mínimo y máximo {#min-and-max-size}

El componente `Splitter` proporciona métodos para establecer tamaños mínimos y máximos para sus paneles, permitiéndote controlar el comportamiento de redimensionamiento de los componentes dentro del `Splitter`. Cuando los usuarios intentan redimensionar paneles más allá de los tamaños mínimos o máximos especificados, el componente splitter hace cumplir estas restricciones, asegurando que los paneles permanezcan dentro de los límites definidos.

### Estableciendo tamaños {#setting-sizes}

El método `setMasterMinSize(String masterMinSize)` especifica el tamaño mínimo para el panel maestro del splitter. Asimismo, el método `setMasterMaxSize(String masterMaxSize)` especifica el tamaño máximo para el panel maestro.

Puedes especificar tamaños usando cualquier unidad CSS válida, como se muestra a continuación:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Orientación {#orientation}

Puedes configurar la orientación en el componente `Splitter`, lo que te permite crear diseños adaptados a requisitos de diseño específicos. Al especificar la orientación, el componente organiza los paneles horizontal o verticalmente, proporcionando versatilidad en el diseño del layout.

Para configurar la orientación, utiliza el enumerador de orientaciones soportadas para especificar si el `Splitter` debe renderizarse horizontal o verticalmente:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Posición relativa {#relative-position}

Para establecer la posición inicial de la barra divisoria en el componente `Splitter`, utiliza `setPositionRelative`. Este método toma un valor numérico de `0` a `100` que representa el porcentaje del espacio dado en el `Splitter`, y muestra el divisor en el porcentaje dado del ancho total:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Anidamiento {#nesting}

El anidamiento de splitters permite crear diseños complejos con niveles de paneles redimensionables. Esto habilita la creación de interfaces de usuario sofisticadas con control granular sobre la disposición y el redimensionamiento del contenido.

Para anidar componentes Splitter, instancia nuevas instancias de `Splitter` y agrégalas como hijos de componentes `Splitter` existentes. Esta estructura jerárquica permite la creación de diseños de múltiples niveles con capacidades de redimensionamiento flexible. El siguiente programa demuestra esto:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Auto guardar {#auto-save}

El componente `Splitter` incluye una opción de AutoGuardar, que guarda el estado de los tamaños de los paneles en el almacenamiento local para mantener las dimensiones consistentes entre recargas.

Cuando configuras la opción de auto-guardar, el componente `Splitter` almacena automáticamente el estado de los tamaños de los paneles en el almacenamiento local del navegador web. Esto asegura que los tamaños que los usuarios eligen para los paneles persistan a través de recargas de página o sesiones de navegador, reduciendo la necesidad de ajustes manuales.

### Limpiando el estado {#cleaning-the-state}

Para revertir programáticamente el `Splitter` a la configuración y dimensiones por defecto, llama al método `cleanState()` para eliminar cualquier dato de estado guardado relacionado con el componente `Splitter` del almacenamiento local del navegador web.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

En la demostración anterior, cada instancia de Splitter activa la característica de AutoGuardar llamando al método `setAutosave`. Esto asegura que los tamaños de los paneles se guarden automáticamente en el almacenamiento local. Así, al recargar el navegador, los tamaños de estos splitters permanecen igual.

Hacer clic en el botón "Limpiar Estado" llama al método `cleanState()` y actualiza la ventana del navegador para mostrar las dimensiones originales.

## Estilo {#styling}

<TableBuilder name="Splitter" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al utilizar el componente `Splitter`, considera las siguientes mejores prácticas: 

- **Ajustar según el contenido**: Al decidir sobre la orientación y los tamaños iniciales de los paneles, considera la prioridad del contenido. Por ejemplo, en un diseño con una barra lateral de navegación y un área de contenido principal, la barra lateral debería permanecer típicamente más estrecha con un tamaño mínimo establecido para una navegación clara.

- **Anidamiento estratégico**: Anidar splitters puede crear diseños versátiles, pero puede complicar la interfaz de usuario y afectar el rendimiento. Planifica tus diseños anidados para asegurar que sean intuitivos y mejoren la experiencia del usuario.

- **Recordar las preferencias del usuario**: Utiliza la función de AutoGuardar para recordar los ajustes de los usuarios entre sesiones, mejorando la experiencia del usuario. Proporciona una opción para permitir a los usuarios restablecer a la configuración por defecto.
