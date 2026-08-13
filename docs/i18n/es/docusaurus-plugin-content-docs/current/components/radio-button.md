---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

El componente `RadioButton` representa una opción única que puede ser seleccionada o deseleccionada. Los botones de opción suelen agruparse para que seleccionar uno deseleccione automáticamente los otros, permitiendo a los usuarios tomar una sola decisión de un conjunto de opciones mutuamente exclusivas.

<!-- INTRO_END -->

## Usos {#usages}

El `RadioButton` es mejor utilizado en escenarios donde los usuarios necesitan hacer una única selección de un conjunto de opciones predefinidas. Aquí hay algunos ejemplos de cuándo usar el `RadioButton`:

1. **Encuestas o Cuestionarios**: Los botones de opción se utilizan comúnmente en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que incluyen paneles de preferencias o configuraciones a menudo usan botones de opción para permitir a los usuarios elegir una única opción de un conjunto de elecciones mutuamente exclusivas.

3. **Filtrado o Clasificación**: Un `RadioButton` puede ser utilizado en aplicaciones que requieren que los usuarios seleccionen una única opción de filtrado o clasificación, como clasificar una lista de elementos por diferentes criterios.

:::tip Agrupando componentes `RadioButton`
Utiliza un [`RadioButtonGroup`](/docs/components/radiobuttongroup) para gestionar un conjunto de botones de opción cuando desees que los usuarios elijan una única opción.
:::

## Texto y posicionamiento {#text-and-positioning}

Los botones de opción pueden utilizar el ```setText(String text)``` método, que se posicionará cerca del botón de opción de acuerdo con la `Posición` incorporada.
Los botones de opción tienen funcionalidad incorporada para establecer el texto que se mostrará ya sea a la derecha o a la izquierda del componente. Por defecto, el texto se mostrará a la derecha del componente. El posicionamiento del texto horizontal se soporta mediante el uso de la clase enumeración `HorizontalAlignment`. A continuación se muestran las dos configuraciones: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Activación {#activation}

Los botones de opción pueden ser controlados utilizando dos tipos de activación: activación manual y activación automática. Estos dictan cuándo un `RadioButton` cambiará su estado.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Activación manual {#manual-activation}

Cuando un botón de opción está configurado para activación manual, significa que no se marcará automáticamente cuando obtenga foco.
La activación manual permite al usuario navegar a través de las opciones de los botones de opción utilizando el teclado u otros métodos de entrada sin cambiar inmediatamente la opción seleccionada.

Si el botón de opción es parte de un grupo, seleccionar un botón de opción diferente dentro del grupo desmarcará automáticamente el botón de opción seleccionado anteriormente.
La activación manual proporciona un control más fino sobre el proceso de selección, requiriendo una acción explícita del usuario para cambiar la opción seleccionada.


### Activación automática {#auto-activation}

La activación automática es el estado por defecto para un `RadioButton`, y significa que el botón se marcará cada vez que obtenga foco por cualquier motivo. Esto significa que no solo un clic, sino también la auto-focalización o la navegación por pestañas también marcará el botón.

:::tip Nota
El valor de activación por defecto es **`MANUAL`** activación.
:::


## Interruptores {#switches}

Un `RadioButton` también puede configurarse para mostrarse como un interruptor que proporciona una representación visual alternativa para seleccionar opciones. Normalmente, los botones de opción son circulares o redondeados y indican una única elección de un grupo de opciones.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` puede transformarse en un interruptor que se asemeja a un interruptor de palanca o deslizante utilizando uno de dos métodos:

1. **El Método de Fábrica**: El botón de opción puede ser creado utilizando los siguientes métodos de fábrica:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Estos métodos reflejan un constructor de `RadioButton`, y crearán el componente con la propiedad de interruptor ya activada.

2. **Setter**: También es posible cambiar un `RadioButton` existente en un interruptor utilizando el setter adecuado:

```java
myRadioButton.setSwitch(true);
```


Cuando un `RadioButton` se muestra como un interruptor, normalmente aparece como una forma oblonga con un indicador que puede activarse o desactivarse. Esta representación visual ofrece a los usuarios una interfaz más intuitiva y familiar, similar a los interruptores físicos que se encuentran comúnmente en dispositivos electrónicos.

Configurar un `RadioButton` para mostrarse como un interruptor puede mejorar la experiencia del usuario al proporcionar una manera clara y directa de seleccionar opciones. Puede aumentar el atractivo visual y la usabilidad de formularios, paneles de configuración, o cualquier otro elemento de interfaz que requiera múltiples elecciones.

:::info
El comportamiento del `RadioButton` permanece igual cuando se representa como un Interruptor, lo que significa que solo se puede seleccionar una opción a la vez dentro de un grupo. La apariencia similar a un interruptor es una transformación visual que conserva la funcionalidad de un `RadioButton`.
:::

<br/>

## Estilo {#styling}

### Expansiones {#expanses}
Hay cinco expansiones de casilla de verificación que son compatibles y que permiten un estilo rápido sin usar CSS.
Las expansiones son compatibles mediante el uso de la clase enumeración `Expanse`. A continuación se presentan las expansiones admitidas para el componente de casilla de verificación: <br/>

<TableBuilder name="RadioButton" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al usar el componente RadioButton, considera las siguientes mejores prácticas:

1. **Etiquetar claramente las opciones**: Proporciona texto claro y conciso para cada opción de `RadioButton` que describa con precisión la elección. El texto debe ser fácil de entender y distinguir entre sí.

2. **Agrupar botones de opción**: Agrupa botones de opción relacionados para indicar su asociación. Esto ayuda a los usuarios a entender que solo se puede seleccionar una opción dentro de un grupo específico. Esto se puede hacer de manera efectiva utilizando el componente [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Proporcionar selección por defecto**: Si es aplicable, considera proporcionar una selección por defecto para los botones de opción para guiar a los usuarios cuando se encuentren por primera vez con las opciones. La selección por defecto debe alinearse con la opción más común o preferida.
