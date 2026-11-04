---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

El componente `RadioButton` representa una única opción que puede ser seleccionada o deseleccionada. Los botones de radio suelen agruparse para que al seleccionar uno se deseleccione automáticamente el resto, permitiendo a los usuarios realizar una única elección de un conjunto de opciones mutuamente excluyentes.

<!-- INTRO_END -->

## Usos {#usages}

El `RadioButton` se utiliza mejor en escenarios donde los usuarios necesitan hacer una única selección de un conjunto de opciones predefinidas. Aquí hay algunos ejemplos de cuándo usar el `RadioButton`:

1. **Encuestas o Cuestionarios**: Los botones de radio se utilizan comúnmente en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que involucran paneles de preferencias o configuraciones suelen usar botones de radio para permitir a los usuarios elegir una única opción de un conjunto de elecciones mutuamente excluyentes.

3. **Filtrado o Clasificación**: Un `RadioButton` se puede utilizar en aplicaciones que requieren que los usuarios seleccionen una única opción de filtrado o clasificación, como clasificar una lista de elementos por diferentes criterios.

:::tip Agrupando componentes `RadioButton`
Utilice un [`RadioButtonGroup`](/docs/components/radiobuttongroup) para gestionar un conjunto de botones de radio cuando desee que los usuarios elijan una única opción.
:::

## Texto y posicionamiento {#text-and-positioning}

Use `setText(String text)` para establecer la etiqueta de un botón de radio. Por defecto, el texto aparece a la derecha del componente. Para cambiar su posición, use `setTextPosition(Position.LEFT)` o `setTextPosition(Position.RIGHT)` con el enum `HasTextPosition.Position`.

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## Activación {#activation}

Los botones de radio se pueden controlar utilizando dos tipos de activación: activación manual y activación automática. Estos determinan cuándo un `RadioButton` cambiará su estado. El valor predeterminado es `Activation.MANUAL`, del enum `RadioButton.Activation`.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Activación manual {#manual-activation}

Cuando un botón de radio está configurado para la activación manual, significa que no se marcará automáticamente cuando gane el enfoque. La activación manual permite al usuario navegar a través de las opciones del botón de radio utilizando el teclado u otros métodos de entrada sin cambiar inmediatamente la opción seleccionada.

Si el botón de radio forma parte de un grupo, seleccionar un botón de radio diferente dentro del grupo desmarcará automáticamente el botón de radio seleccionado previamente. La activación manual proporciona un control más preciso sobre el proceso de selección, requiriendo una acción explícita del usuario para cambiar la opción seleccionada.

### Activación automática {#auto-activation}

Habilite la activación automática con `setActivation(Activation.AUTO)`. En este modo, el botón de radio se marca siempre que gane el enfoque, ya sea mediante clic, auto-enfoque o navegación por pestañas.

## Interruptores {#switches}

Un `RadioButton` también se puede configurar para mostrarse como un interruptor, proporcionando una representación visual alternativa para seleccionar opciones. Normalmente, los botones de radio son circulares o redondeados en forma e indican una única elección de un grupo de opciones.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` se puede transformar en un interruptor que se asemeja a un interruptor de palanca o deslizante utilizando uno de dos métodos:

1. **El Método de Fábrica**: El botón de radio se puede crear utilizando los siguientes métodos de fábrica:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Estos métodos reflejan un constructor de `RadioButton` y crearán el componente con la propiedad de interruptor ya activada.

2. **Setter**: También es posible cambiar un `RadioButton` ya existente en un interruptor utilizando el setter correspondiente:

```java
myRadioButton.setSwitch(true);
```

Cuando un `RadioButton` se muestra como un interruptor, generalmente aparece en forma oblonga con un indicador que puede ser activado o desactivado. Esta representación visual ofrece a los usuarios una interfaz más intuitiva y familiar, similar a los interruptores físicos que se encuentran comúnmente en dispositivos electrónicos.

Configurar un `RadioButton` para mostrarse como un interruptor puede mejorar la experiencia del usuario al proporcionar una forma clara y directa de seleccionar opciones. Puede realzar el atractivo visual y la usabilidad de formularios, paneles de configuración o cualquier otro elemento de interfaz que requiera múltiples elecciones.

:::info
El comportamiento del `RadioButton` permanece igual cuando se representa como un Interruptor, lo que significa que solo se puede seleccionar una opción a la vez dentro de un grupo. La apariencia similar a un interruptor es una transformación visual que conserva la funcionalidad de un `RadioButton`.
:::

<br/>

## Estilo {#styling}

### Expansiones {#expanses}

Use `setExpanse()` con el enum `Expanse` para ajustar el tamaño de un `RadioButton` sin escribir CSS. Los valores disponibles son `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, y `NONE`.

<TableBuilder name="RadioButton" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima para el usuario al utilizar el componente RadioButton, considere las siguientes mejores prácticas:

1. **Etiquetar claramente las opciones**: Proporcione texto claro y conciso para cada opción de `RadioButton` para describir con precisión la elección. El texto debe ser fácil de entender y distinguir unos de otros.

2. **Agrupar botones de radio**: Agrupe botones de radio relacionados para indicar su asociación. Esto ayuda a los usuarios a entender que solo se puede seleccionar una opción dentro de un grupo específico. Esto se puede hacer de manera efectiva utilizando el componente [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Proporcionar selección predeterminada**: si es aplicable, considere proporcionar una selección predeterminada para los botones de radio para guiar a los usuarios cuando se encuentren por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.
