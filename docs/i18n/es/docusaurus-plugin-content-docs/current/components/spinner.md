---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

El componente `Spinner` proporciona un indicador visual que muestra que se está procesando o cargando en segundo plano. Se usa a menudo para indicar que el sistema está obteniendo datos o cuando un proceso tarda en completarse. El `Spinner` ofrece retroalimentación al usuario, señalando que el sistema está trabajando activamente.

<!-- INTRO_END -->

## Básicos {#basics}

Para crear un `Spinner`, puedes especificar el tema y la extensión. La sintaxis básica implica crear una instancia de `Spinner` y definir su apariencia y comportamiento a través de métodos como `setTheme()` y `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestión de velocidad y pausas {#managing-speed-and-pausing}

Es posible establecer la velocidad en milisegundos para el `Spinner` y pausar/reanudar la animación con facilidad.

Los casos de uso para establecer velocidades incluyen diferenciar entre procesos de carga. Por ejemplo, `Spinners` más rápidos son adecuados para tareas más pequeñas, mientras que `Spinners` más lentos son mejores para tareas más grandes. Pausar es útil cuando se requiere una acción o confirmación del usuario antes de continuar con el proceso.

### Ajustar velocidad {#adjusting-speed}

Puedes controlar cuán rápido gira el `Spinner` ajustando su velocidad en milisegundos utilizando el método `setSpeed()`. Un valor más bajo hace que el `Spinner` gire más rápido, mientras que los valores más altos lo ralentizan.

```java
spinner.setSpeed(500); // Gira más rápido
```

:::info Velocidad Predeterminada
Por defecto, el `Spinner` tomará 1000 milisegundos para completar una rotación completa.
:::

### Pausar y reanudar {#pausing-and-resuming}

Pausar el `Spinner` es útil cuando un programa está temporalmente detenido o esperando la entrada del usuario. Le hace saber a los usuarios que el programa está en espera, en lugar de estar ejecutándose activamente, lo que mejora la claridad durante los procesos de múltiples pasos.

Para pausar y reanudar el Spinner, utiliza el método `setPaused()`. Esto es particularmente útil cuando necesitas detener temporalmente la animación de giro.

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

La dirección del `Spinner` puede ser controlada para girar **horario** o **antihorario**. Puedes especificar este comportamiento usando el método `setClockwise()`.

```java
spinner.setClockwise(false);  // Gira antihorario
spinner.setClockwise(true);   // Gira horario
```

Esta opción indica visualmente un estado especial o sirve como una elección de diseño única. Cambiar la dirección de giro puede ayudar a diferenciar entre tipos de procesos, como progreso vs. reversa, o proporcionar una señal visual distintiva en contextos específicos.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Estilización {#styling}

### Temas {#themes}

El componente `Spinner` viene con varios temas incorporados que te permiten aplicar estilos rápidamente sin necesidad de CSS personalizado. Estos temas cambian la apariencia visual del spinner, haciéndolo apropiado para diferentes casos de uso y contextos. Usar estos temas predefinidos asegura consistencia en el estilo a lo largo de tu aplicación.

Si bien los spinners sirven para varias situaciones, aquí hay algunos ejemplos de casos de uso para los diferentes temas:

- **Primario**: Ideal para enfatizar un estado de carga que es una parte clave del flujo del usuario, como al enviar un formulario o procesar una acción importante.
  
- **Éxito**: Útil para representar procesos de fondo exitosos, como cuando un usuario envía un formulario y la aplicación está realizando los pasos finales del proceso.
  
- **Peligro**: Usa esto para operaciones arriesgadas o de alto riesgo, como eliminar datos importantes o hacer cambios irreversibles, donde un indicador visual de urgencia o precaución es necesario.
  
- **Advertencia**: Úsalo para indicar un proceso cauteloso o menos urgente, como cuando el usuario espera la validación de datos, pero no requiere acción inmediata.

- **Gris**: Funciona bien para procesos de fondo sutiles, como tareas de carga de baja prioridad o pasivas, como cuando se obtienen datos suplementarios que no impactan directamente en la experiencia del usuario.
  
- **Información**: Adecuado para escenarios de carga donde estás proporcionando información adicional o aclaración al usuario, como mostrar un spinner junto a un mensaje que explica el proceso en curso.

Puedes aplicar estos temas programáticamente al spinner, proporcionando señales visuales que se alinean con el contexto y la importancia de la operación.

Puedes especificar este comportamiento utilizando el método `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Extensiones {#expanses}

Puedes ajustar el tamaño del spinner, conocido como **expansión**, para adaptarlo al espacio visual que necesitas. El spinner soporta varios tamaños incluyendo `Expanse.SMALL`, `Expanse.MEDIUM`, y `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
