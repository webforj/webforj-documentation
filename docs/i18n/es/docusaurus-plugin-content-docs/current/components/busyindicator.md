---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

El `BusyIndicator` proporciona señales visuales para asegurar que los usuarios estén al tanto de los procesos en curso, evitando que interactúen con el sistema prematuramente. Típicamente cubre toda la interfaz de la aplicación para operaciones globales.

Mientras que el componente [`Loading`](../components/loading) se enfoca en áreas o componentes específicos dentro de la aplicación, el `BusyIndicator` maneja procesos globales a nivel de aplicación y bloquea la interacción en toda la interfaz. Esta diferencia en el alcance hace que el componente [`Loading`](../components/loading) sea ideal para escenarios más localizados y específicos de componentes, como cargar datos en una sección particular de una página. En contraste, el `BusyIndicator` es adecuado para operaciones a nivel de sistema que afectan a toda la aplicación, como inicializar la aplicación o realizar una sincronización de datos importante.

## Basics {#basics}

El `BusyIndicator` en webforJ se muestra como un simple spinner, lo que facilita su uso sin configuración. Sin embargo, puedes personalizarlo agregando un mensaje, ajustando el tema del spinner o modificando las configuraciones de visibilidad. Esto te permite proporcionar más contexto o estilo mientras mantienes una solución funcional lista para usar.

En este ejemplo, el `BusyIndicator` previene cualquier acción del usuario en toda la interfaz hasta que la operación se complete.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

El componente `BusyIndicator` en webforJ te permite mostrar un backdrop para bloquear la interacción del usuario mientras un proceso está en curso. Por defecto, el componente habilita el backdrop, pero tienes la opción de desactivarlo si es necesario.

El `BusyIndicator` muestra un backdrop por defecto. Puedes controlar la visibilidad del backdrop utilizando el método `setBackdropVisible()`, como se muestra a continuación:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Desactiva el backdrop
busyIndicator.open();
```
:::info Desactivando el Backdrop
Incluso cuando desactivas el backdrop, el componente `BusyIndicator` continúa bloqueando la interacción del usuario para asegurar que el proceso subyacente se complete sin interrupciones. El backdrop simplemente controla la superposición visual, no el comportamiento de bloqueo de interacción.
:::

## `Spinner` {#spinner}

El componente `BusyIndicator` en webforJ incluye un `Spinner` que indica visualmente que una operación de fondo está en progreso. Puedes personalizar este spinner con varias opciones, incluyendo su tamaño, velocidad, dirección, tema y visibilidad.

Aquí hay un ejemplo de cómo puedes personalizar el spinner dentro de un componente `BusyIndicator`:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **Procesamiento a Nivel de Página**  
   El `BusyIndicator` es adecuado para operaciones más grandes, de nivel de página, como cuando un usuario inicia una tarea que afecta a toda la página, como subir un archivo o procesar datos en múltiples secciones. Puede informar a los usuarios que toda la aplicación está trabajando, evitando más interacciones hasta que el proceso se complete.

- **Operaciones Críticas del Sistema**  
   Al realizar tareas críticas del sistema, como sincronizar datos, aplicar actualizaciones a nivel de sistema o procesar información sensible, el `BusyIndicator` proporciona una clara retroalimentación visual de que una operación importante está en curso, permitiendo al usuario esperar hasta que se complete.

- **Cargas de Datos Asíncronas**  
   En escenarios donde se involucra el procesamiento de datos asíncronos, como al llamar a múltiples APIs o esperar cálculos complejos, el componente `BusyIndicator` indica activamente que el sistema está ocupado, instando a los usuarios a esperar antes de realizar acciones adicionales.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
