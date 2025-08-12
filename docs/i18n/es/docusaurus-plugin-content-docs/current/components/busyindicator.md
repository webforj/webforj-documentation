---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` proporciona señales visuales para asegurarse de que los usuarios estén al tanto de los procesos en curso, evitando que interactúen con el sistema prematuramente. Normalmente cubre toda la interfaz de la aplicación para operaciones globales.

Mientras que el componente [`Loading`](../components/loading) se centra en áreas o componentes específicos dentro de la aplicación, el `BusyIndicator` maneja procesos globales de la aplicación y bloquea la interacción en toda la interfaz. Esta diferencia de alcance hace que el componente [`Loading`](../components/loading) sea ideal para escenarios más localizados y específicos de componentes, como cargar datos en una sección particular de una página. Por el contrario, el `BusyIndicator` es adecuado para operaciones a nivel de sistema que afectan a toda la aplicación, como inicializar la aplicación o realizar una sincronización de datos importante.

## Basics {#basics}

El `BusyIndicator` en webforJ se muestra como un simple spinner, lo que facilita su uso sin configuración. Sin embargo, puede personalizarlo añadiendo un mensaje, ajustando el tema del spinner o modificando las configuraciones de visibilidad. Esto le permite proporcionar más contexto o estilo mientras mantiene una solución funcional lista para usar.

En este ejemplo, el `BusyIndicator` impide cualquier acción del usuario en toda la interfaz hasta que se complete la operación.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

El componente `BusyIndicator` en webforJ le permite mostrar un fondo para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el fondo, pero tiene la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un fondo por defecto. Puede controlar la visibilidad del fondo utilizando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el fondo
busyIndicator.open();
```
:::info Desactivando el fondo
Incluso cuando desactiva el fondo, el componente `BusyIndicator` continúa bloqueando la interacción del usuario para asegurarse de que el proceso subyacente se complete sin interrupciones. El fondo simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `BusyIndicator` en webforJ incluye un `Spinner` que indica visualmente que una operación de fondo está en progreso. Puede personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puede personalizar el spinner dentro de un componente `BusyIndicator`:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **Procesamiento a nivel de página**  
   El `BusyIndicator` es muy adecuado para operaciones más grandes, a nivel de página, como cuando un usuario inicia una tarea que afecta a toda la página, como subir un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está trabajando, evitando interacciones adicionales hasta que el proceso esté completo.

- **Operaciones críticas del sistema**  
   Al realizar tareas críticas del sistema, como sincronizar datos, aplicar actualizaciones a nivel de sistema o procesar información sensible, el `BusyIndicator` proporciona una clara retroalimentación visual de que una operación importante está en curso, permitiendo que el usuario espere hasta que se complete.

- **Cargas de datos asíncronas**  
   En escenarios donde se involucra procesamiento de datos asíncronos, como al llamar a múltiples APIs o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, lo que invita a los usuarios a esperar antes de realizar acciones adicionales.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
