---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que indica un procesamiento o carga en curso en segundo plano. Se utiliza a menudo para mostrar que el sistema está recuperando datos o cuando un proceso tarda en completarse. El spinner ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

## Básicos {#basics}

Para crear un `Spinner`, puedes especificar el tema y la expansión. La sintaxis básica implica crear una instancia de `Spinner` y definir su apariencia y comportamiento a través de métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Gestionando la velocidad y la pausa {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad.

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, los `Spinners` más rápidos son adecuados para tareas más pequeñas, mientras que los `Spinners` más lentos son mejores para tareas más grandes. La pausa es útil cuando se requiere acción o confirmación del usuario antes de continuar con el proceso.

### Ajustando la velocidad {#adjusting-speed}

Puedes controlar la velocidad a la que gira el `Spinner` ajustando su velocidad en milisegundos usando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` gire más rápido, mientras que los valores más altos lo ralentizarán.

```java
spinner.setSpeed(500); // Gira más rápido
```

:::info Velocidad por defecto
Por defecto, el `Spinner` tardará 1000 milisegundos en completar una rotación completa.
:::

### Pausando y reanudando {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Informa a los usuarios que el programa está en espera, en lugar de ejecutándose activamente, lo que mejora la claridad durante procesos de múltiples pasos.

Para pausar y reanudar el Spinner, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación de giro.

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

La dirección del `Spinner` puede controlarse para girar **en sentido horario** o **en sentido antihorario**. Puedes especificar este comportamiento utilizando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira en sentido antihorario
spinner.setClockwise(true);   // Gira en sentido horario
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección de giro puede ayudar a diferenciar entre tipos de procesos, como progreso frente a reversa, o proporcionar una señal visual distinta en contextos específicos.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Estilizando {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas incorporados que te permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos asegura consistencia en el estilo a lo largo de tu aplicación.

Si bien los spinners sirven para diversas situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como durante el envío de un formulario o el procesamiento de una acción importante.
  
- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.
  
- **Peligro**: Úsalo para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o hacer cambios irreversibles, donde un indicador visual de urgencia o precaución es necesario.
  
- **Advertencia**: Úsalo para indicar un proceso cautelar o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como cuando se obtienen datos suplementarios que no impactan directamente en la experiencia del usuario.
  
- **Información**: Adecuado para escenarios de carga donde proporcionas información o aclaración adicional al usuario, como mostrar un spinner junto a un mensaje que explique el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando señales visuales que se alineen con el contexto y la importancia de la operación.

Puedes especificar este comportamiento usando el método `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expansiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para adaptarse al espacio visual que necesitas. El spinner soporta varios tamaños, incluyendo `Expanse.SMALL`, `Expanse.MEDIUM`, y `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
