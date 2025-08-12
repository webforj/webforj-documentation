---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que indica el procesamiento o carga en curso en segundo plano. Se utiliza a menudo para mostrar que el sistema está recuperando datos o cuando un proceso tarda tiempo en completarse. El spinner ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

## Basics {#basics}

Para crear un `Spinner`, puedes especificar el tema y la extensión. La sintaxis básica implica crear una instancia de `Spinner` y definir su apariencia y comportamiento mediante métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Managing speed and pausing {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad.

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, los `Spinners` más rápidos son adecuados para tareas pequeñas, mientras que los `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere acción o confirmación del usuario antes de continuar con el proceso.

### Adjusting speed {#adjusting-speed}

Puedes controlar la velocidad a la que rota el `Spinner` ajustando su velocidad en milisegundos mediante el método `setSpeed()`. Un valor más bajo hace que el `Spinner` rote más rápido, mientras que los valores más altos lo ralentizan.

```java
spinner.setSpeed(500); // Rota más rápido
```

:::info Velocidad por defecto
Por defecto, el `Spinner` tardará 1000 milisegundos en completar una rotación completa.
:::

### Pausing and resuming {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Permite a los usuarios saber que el programa está en pausa, en lugar de estar ejecutándose activamente, lo que mejora la claridad durante los procesos de múltiples pasos.

Para pausar y reanudar el Spinner, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación de giro.

```java
spinner.setPaused(true);  // Pausa el spinner
spinner.setPaused(false); // Reanuda el spinner
```

Este ejemplo muestra cómo configurar la velocidad y cómo pausar/reanudar el `Spinner`:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Spin direction {#spin-direction}

La dirección del `Spinner` puede controlarse para girar **en sentido horario** o **en sentido antihorario**. Puedes especificar este comportamiento utilizando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Rota en sentido antihorario
spinner.setClockwise(true);   // Rota en sentido horario
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección del giro puede ayudar a diferenciar entre tipos de procesos, como progreso vs. reversa, o proporcionar una señal visual distintiva en contextos específicos.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Styling {#styling}

### Themes {#themes}

El componente `Spinner` viene con varios temas integrados que te permiten aplicar estilos rápidamente sin necesitar CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos asegura consistencia en el estilo a lo largo de tu aplicación.

Si bien los spinners sirven para diversas situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primary**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como al enviar un formulario o procesar una acción importante.
  
- **Success**: Útil para representar procesos en segundo plano exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.
  
- **Danger**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o realizar cambios irreversible, donde se necesita un indicador visual de urgencia o precaución.
  
- **Warning**: Utiliza esto para indicar un proceso cauteloso o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere una acción inmediata.

- **Gray**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como al recuperar datos complementarios que no impactan directamente la experiencia del usuario.
  
- **Info**: Adecuado para escenarios de carga donde estás proporcionando información adicional o aclaraciones al usuario, como mostrar un spinner junto a un mensaje que explica el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando señales visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento utilizando el método `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expanses {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para adaptarse al espacio visual que necesitas. El spinner admite varios tamaños, incluyendo `Expanse.SMALL`, `Expanse.MEDIUM`, y `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
