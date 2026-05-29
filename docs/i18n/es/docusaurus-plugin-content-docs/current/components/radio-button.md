---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

El componente `RadioButton` representa una única opción que puede ser seleccionada o deseleccionada. Los botones de opción suelen ser agrupados para que seleccionar uno deseleccione automáticamente a los otros, permitiendo a los usuarios hacer una única elección de un conjunto de opciones mutuamente excluyentes.

<!-- INTRO_END -->

## Usos {#usages}

El `RadioButton` es mejor utilizado en escenarios donde los usuarios necesitan hacer una sola selección de un conjunto de opciones predefinidas. Aquí hay algunos ejemplos de cuándo usar el `RadioButton`:

1. **Encuestas o Cuestionarios**: Los botones de opción se utilizan comúnmente en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que involucran paneles de preferencias o configuraciones a menudo utilizan botones de opción para permitir a los usuarios elegir una sola opción de un conjunto de elecciones mutuamente excluyentes.

3. **Filtrado o Clasificación**: Un `RadioButton` puede ser utilizado en aplicaciones que requieren que los usuarios seleccionen un único filtro o opción de clasificación, como clasificar una lista de elementos por diferentes criterios.

:::tip Agrupación de componentes `RadioButton`
Use un [`RadioButtonGroup`](/docs/components/radiobuttongroup) para gestionar un conjunto de botones de opción cuando desee que los usuarios elijan una sola opción.
:::

## Texto y posicionamiento {#text-and-positioning}

Los botones de opción pueden utilizar el método ```setText(String text)```, que se posicionará cerca del botón de opción de acuerdo con la `Posición` incorporada.
Los botones de opción tienen funcionalidad incorporada para establecer texto que se mostrará ya sea a la derecha o a la izquierda del componente. Por defecto, el texto se mostrará a la derecha del componente. El posicionamiento del texto horizontal es soportado por el uso de la clase enumerada `HorizontalAlignment`. A continuación se muestran las dos configuraciones: <br/>

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

Cuando un botón de opción está configurado para activación manual, significa que no se marcará automáticamente cuando gane foco.
La activación manual permite al usuario navegar a través de las opciones del botón de opción utilizando el teclado u otros métodos de entrada sin cambiar inmediatamente la opción seleccionada.

Si el botón de opción es parte de un grupo, seleccionar un botón de opción diferente dentro del grupo desmarcará automáticamente el botón de opción previamente seleccionado.
La activación manual proporciona un control más fino sobre el proceso de selección, requiriendo una acción explícita del usuario para cambiar la opción seleccionada.


### Activación automática {#auto-activation}

La activación automática es el estado predeterminado para un `RadioButton`, y significa que el botón se marcará siempre que gane foco por cualquier motivo. Esto significa que no solo el clic, sino también el enfoque automático o la navegación por pestañas marcarán el botón.

:::tip Nota
El valor de activación predeterminado es **`MANUAL`** activación.
:::


## Interruptores {#switches}

Un `RadioButton` también puede configurarse para mostrarse como un interruptor que proporciona una representación visual alternativa para seleccionar opciones. Normalmente, los botones de opción son circulares o redondeados en forma e indican una única elección de un grupo de opciones.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Un `RadioButton` puede transformarse en un interruptor que se asemeja a un interruptor de palanca o deslizador utilizando uno de dos métodos:

1. **El Método de Fábrica**: El RadioButton puede ser creado utilizando los siguientes métodos de fábrica:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Estos métodos reflejan un constructor de `RadioButton`, y crearán el componente con la propiedad de interruptor ya activada.

2. **Setter**: También es posible cambiar un `RadioButton` ya existente en un interruptor utilizando el setter apropiado:

```java
myRadioButton.setSwitch(true);
```


Cuando un `RadioButton` se muestra como un interruptor, típicamente aparece como una forma oblonga con un indicador que puede activarse o desactivarse. Esta representación visual ofrece a los usuarios una interfaz más intuitiva y familiar, similar a los interruptores físicos que se encuentran comúnmente en dispositivos electrónicos.

Configurar un `RadioButton` para mostrarse como un interruptor puede mejorar la experiencia del usuario al proporcionar una manera clara y directa de seleccionar opciones. Puede aumentar el atractivo visual y la usabilidad de formularios, paneles de configuración, o cualquier otro elemento de interfaz que requiera múltiples elecciones.

:::info
El comportamiento del `RadioButton` permanece igual cuando se renderiza como un Interruptor, lo que significa que solo se puede seleccionar una opción a la vez dentro de un grupo. La apariencia similar a un interruptor es una transformación visual que retiene la funcionalidad de un `RadioButton`.
:::

<br/>

## Estilización {#styling}

### Expansiones {#expanses}
Hay cinco expansiones de casillas de verificación que son soportadas, que permiten un estilizado rápido sin utilizar CSS.
Las expansiones son soportadas por el uso de la clase enumerada `Expanse`. A continuación se muestran las expansiones soportadas para el componente de casilla de verificación: <br/>

<TableBuilder name="RadioButton" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima al usuario al utilizar el componente RadioButton, considere las siguientes mejores prácticas:

1. **Etiquetar Claramente las Opciones**: Proporcione texto claro y conciso para cada opción de `RadioButton` para describir con precisión la elección. El texto debe ser fácil de entender y distinguir entre sí.

2. **Agrupar los botones de opción**: Agrupe los botones de opción relacionados para indicar su asociación. Esto ayuda a los usuarios a entender que solo se puede seleccionar una opción dentro de un grupo específico. Esto se puede hacer de manera efectiva utilizando el componente [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para los botones de opción para guiar a los usuarios cuando se encuentran por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.
