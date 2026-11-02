---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que señala que un procesamiento o carga está en curso en segundo plano. Se utiliza frecuentemente para mostrar que el sistema está obteniendo datos o cuando un proceso tarda tiempo en completarse. El `Spinner` ofrece retroalimentación al usuario, indicando que el sistema está trabajando activamente.

<!-- INTRO_END -->

Crea una instancia del `Spinner`, luego define su apariencia y comportamiento con métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestión de velocidad y pausa {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad.

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, `Spinners` más rápidos son adecuados para tareas más pequeñas, mientras que `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere una acción o confirmación del usuario antes de continuar con el proceso.

### Ajustar velocidad {#adjusting-speed}

Puedes controlar qué tan rápido gira el `Spinner` ajustando su velocidad en milisegundos utilizando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` gire más rápido, mientras que valores más altos lo harán más lento.

```java
spinner.setSpeed(500); // Gira más rápido
```

:::info Velocidad por defecto
Por defecto, el `Spinner` tardará 1000 milisegundos en completar una rotación completa.
:::

### Pausar y reanudar {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Permite a los usuarios saber que el programa está en espera, en lugar de ejecutándose activamente, lo que mejora la claridad durante procesos de múltiples pasos.

Para pausar y reanudar el `Spinner`, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación giratoria.

```java
spinner.setPaused(true);  // Pausa el spinner
spinner.setPaused(false); // Reanuda el spinner
```

Este ejemplo muestra cómo establecer la velocidad y cómo pausar/reanudar el `Spinner`:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Dirección de giro {#spin-direction}

La dirección del `Spinner` puede ser controlada para girar **en sentido horario** o **en sentido antihorario**. Puedes especificar este comportamiento utilizando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira en sentido antihorario
spinner.setClockwise(true);   // Gira en sentido horario
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección de giro puede ayudar a diferenciar entre tipos de procesos, como progreso frente a reversa, o proporcionar una señal visual distinta en contextos específicos.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Estilo {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas integrados que te permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Utilizar estos temas predefinidos asegura consistencia en el estilo a lo largo de tu aplicación.

Mientras los spinners sirven para diversas situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como al enviar un formulario o procesar una acción importante.

- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.

- **Peligro**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o hacer cambios irreversibles, donde es necesaria una indicación visual de urgencia o precaución.

- **Advertencia**: Usa esto para indicar un proceso cauteloso o menos urgente, como cuando el usuario espera la validación de datos, pero no se requiere acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como al recuperar datos complementarios que no afectan directamente la experiencia del usuario.

- **Información**: Adecuado para escenarios de carga en los que proporcionas información adicional o aclaraciones al usuario, como mostrar un spinner junto con un mensaje que explica el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando señales visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento utilizando el método `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, utilizando `setExpanse()` con la enumeración `SpinnerExpanse`. Los nueve tamaños disponibles son `XXXSMALL`, `XXSMALL`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, `XXLARGE`, y `XXXLARGE`. El valor por defecto es `SpinnerExpanse.NONE`.

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
