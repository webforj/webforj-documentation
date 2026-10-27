---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que muestra que hay un proceso en curso o carga en segundo plano. Se utiliza a menudo para mostrar que el sistema está obteniendo datos o cuando un proceso toma tiempo para completarse. El `Spinner` ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

<!-- INTRO_END -->

Crea una instancia de `Spinner`, luego define su apariencia y comportamiento con métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestión de velocidad y pausado {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad.

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, los `Spinners` más rápidos son adecuados para tareas pequeñas, mientras que los `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere acción o confirmación del usuario antes de continuar con el proceso.

### Ajustando la velocidad {#adjusting-speed}

Puedes controlar qué tan rápido rota el `Spinner` ajustando su velocidad en milisegundos usando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` gire más rápido, mientras que valores más altos lo ralentizan.

```java
spinner.setSpeed(500); // Rota más rápido
```

:::info Velocidad predeterminada
Por defecto, el `Spinner` tomará 1000 milisegundos para completar una rotación completa.
:::

### Pausando y reanudando {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Esto permite que los usuarios sepan que el programa está en espera, en lugar de estar en ejecución activa, lo que mejora la claridad durante procesos de múltiples pasos.

Para pausar y reanudar el Spinner, usa el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación de giro.

```java
spinner.setPaused(true);  // Pausa el spinner
spinner.setPaused(false); // Reanuda el spinner
```

Este ejemplo muestra cómo configurar la velocidad y cómo pausar/reanudar el `Spinner`:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Dirección de giro {#spin-direction}

La dirección del `Spinner` puede controlarse para girar **en sentido horario** o **en sentido antihorario**. Puedes especificar este comportamiento usando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira en sentido antihorario
spinner.setClockwise(true);   // Gira en sentido horario
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección del giro puede ayudar a diferenciar entre tipos de procesos, como progreso frente a reversión, o proporcionar una señal visual distinta en contextos específicos.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Estilizando {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas integrados que permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos asegura consistencia en el estilo a lo largo de tu aplicación.

Si bien los spinners sirven para diversas situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es parte clave del flujo del usuario, como al enviar un formulario o procesar una acción importante.

- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.

- **Peligro**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o realizar cambios irreversibles, donde un indicador visual de urgencia o precaución es necesario.

- **Advertencia**: Usa esto para indicar un proceso de precaución o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como al obtener datos suplementarios que no impactan directamente la experiencia del usuario.

- **Información**: Adecuado para escenarios de carga donde estás proporcionando información adicional o aclaraciones al usuario, como mostrar un spinner junto a un mensaje que explica el proceso en curso.

Puedes aplicar estos temas de forma programática al spinner, proporcionando señales visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento usando el método `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para ajustarse al espacio visual que necesitas. El spinner admite varios tamaños, incluyendo `Expanse.SMALL`, `Expanse.MEDIUM`, y `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
