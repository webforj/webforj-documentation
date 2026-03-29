---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que indica el procesamiento o la carga en segundo plano. A menudo se utiliza para mostrar que el sistema está recuperando datos o cuando un proceso tarda en completarse. El `Spinner` ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

<!-- INTRO_END -->

## Fundamentos {#basics}

Para crear un `Spinner`, puedes especificar el tema y la expansión. La sintaxis básica implica crear una instancia de `Spinner` y definir su apariencia y comportamiento a través de métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Gestión de velocidad y pausa {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad. 

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, `Spinners` más rápidos son adecuados para tareas más pequeñas, mientras que `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere la acción del usuario o confirmación antes de continuar con el proceso.

### Ajustando la velocidad {#adjusting-speed}

Puedes controlar la rapidez con la que gira el `Spinner` ajustando su velocidad en milisegundos utilizando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` gire más rápido, mientras que valores más altos lo ralentizarán.

```java
spinner.setSpeed(500); // Gira más rápido
```

:::info Velocidad Predeterminada
Por defecto, el `Spinner` tardará 1000 milisegundos en completar una rotación completa.
:::

### Pausando y reanudando {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Permite a los usuarios saber que el programa está en pausa, en lugar de ejecutándose activamente, lo que mejora la claridad durante procesos de múltiples pasos.

Para pausar y reanudar el Spinner, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación giratoria.      

```java
spinner.setPaused(true);  // Pausar el spinner
spinner.setPaused(false); // Reanudar el spinner
```

Este ejemplo muestra cómo establecer la velocidad y cómo pausar/reanudar el `Spinner`:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Dirección de giro {#spin-direction}

La dirección del `Spinner` se puede controlar para girar **en el sentido de las agujas del reloj** o **en sentido contrario**. Puedes especificar este comportamiento utilizando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira en sentido contrario
spinner.setClockwise(true);   // Gira en el sentido de las agujas del reloj
```

Esta opción indica visualmente un estado especial o sirve como una opción de diseño única. Cambiar la dirección de giro puede ayudar a diferenciar entre tipos de procesos, como progreso frente a reversión, o proporcionar una señal visual distinta en contextos específicos.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Estilización {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas integrados que te permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos garantiza consistencia en la estilización a lo largo de tu aplicación.

Mientras los spinners sirven para varias situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como al enviar un formulario o procesar una acción importante.
  
- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.
  
- **Peligro**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o hacer cambios irreversibles, donde es necesaria un indicador visual de urgencia o precaución.
  
- **Advertencia**: Utiliza esto para indicar un proceso de precaución o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como cuando se recuperan datos suplementarios que no impactan directamente en la experiencia del usuario.
  
- **Información**: Adecuado para escenarios de carga donde estás proporcionando información adicional o aclaración al usuario, como mostrar un spinner junto a un mensaje que explique el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando señales visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento utilizando el método `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expansiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para adaptarse al espacio visual que necesites. El spinner admite varios tamaños, incluyendo `Expanse.SMALL`, `Expanse.MEDIUM` y `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
