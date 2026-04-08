---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que indica el procesamiento o la carga en curso en segundo plano. Se utiliza a menudo para mostrar que el sistema está obteniendo datos o cuando un proceso tarda en completarse. El `Spinner` ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

<!-- INTRO_END -->

## Básicos {#basics}

Para crear un `Spinner`, puedes especificar el tema y la expansión. La sintaxis básica implica crear una instancia de `Spinner` y definir su apariencia y comportamiento a través de métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## Gestión de velocidad y pausa {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad. 

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, los `Spinners` más rápidos son adecuados para tareas pequeñas, mientras que los `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere acción o confirmación del usuario antes de continuar con el proceso.

### Ajustando la velocidad {#adjusting-speed}

Puedes controlar lo rápido que rota el `Spinner` ajustando su velocidad en milisegundos utilizando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` rote más rápido, mientras que los valores más altos lo ralentizarán.

```java
spinner.setSpeed(500); // Rota más rápido
```

:::info Velocidad por defecto
Por defecto, el `Spinner` tardará 1000 milisegundos en completar una rotación completa.
:::

### Pausando y reanudando {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa se detiene temporalmente o espera la entrada del usuario. Le permite a los usuarios saber que el programa está en espera, en lugar de ejecutarse activamente, lo que mejora la claridad durante procesos de múltiples pasos.

Para pausar y reanudar el Spinner, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación de giro.      

```java
spinner.setPaused(true);  // Pausa el spinner
spinner.setPaused(false); // Reanuda el spinner
```

Este ejemplo muestra cómo establecer la velocidad y cómo pausar/reanudar el `Spinner`:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## Dirección de giro {#spin-direction}

La dirección del `Spinner` puede controlarse para girar **en el sentido de las agujas del reloj** o **en contra de las agujas del reloj**. Puedes especificar este comportamiento utilizando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira en contra de las agujas del reloj
spinner.setClockwise(true);   // Gira en el sentido de las agujas del reloj
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección del giro puede ayudar a diferenciar entre tipos de procesos, como progreso frente a reverso, o proporcionar una pista visual distinta en contextos específicos.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Estilizando {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas integrados que te permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos garantiza consistencia en el estilo a lo largo de tu aplicación.

Si bien los spinners sirven para diversas situaciones, aquí hay algunos ejemplos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como durante el envío de un formulario o procesando una acción importante.
  
- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.
  
- **Peligro**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o hacer cambios irreversibles, donde se necesita un indicador visual de urgencia o precaución.
  
- **Advertencia**: Usa esto para indicar un proceso precautorio o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere una acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como cuando se obtienen datos suplementarios que no impactan directamente la experiencia del usuario.
  
- **Información**: Adecuado para escenarios de carga donde estás proporcionando información adicional o aclaraciones al usuario, como mostrar un spinner junto a un mensaje que explica el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando pistas visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento utilizando el método `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### Expansiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para adaptarse al espacio visual que necesites. El spinner admite varios tamaños, incluidos `Expanse.SMALL`, `Expanse.MEDIUM` y `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
