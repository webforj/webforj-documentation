---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

El componente `RadioButton` es un objeto que puede ser seleccionado o deseleccionado, y que muestra su estado al usuario. Los botones de opción se utilizan comúnmente cuando están disponibles opciones mutuamente exclusivas, permitiendo al usuario elegir una sola opción de un conjunto de elecciones.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip Agrupando componentes `RadioButton`
Usa un [`RadioButtonGroup`](/docs/components/radiobuttongroup) para gestionar un conjunto de botones de opción cuando quieras que los usuarios seleccionen una sola opción.
:::

## Usos {#usages}

El `RadioButton` es mejor utilizado en escenarios donde los usuarios necesitan hacer una única selección de un conjunto predefinido de opciones. Aquí hay algunos ejemplos de cuándo usar el `RadioButton`:

1. **Encuestas o Cuestionarios**: Los botones de opción se utilizan comúnmente en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que implican paneles de preferencias o configuraciones a menudo utilizan botones de opción para permitir a los usuarios elegir una sola opción de un conjunto de elecciones mutuamente exclusivas.

3. **Filtrado o Clasificación**: Un `RadioButton` puede ser utilizado en aplicaciones que requieren que los usuarios seleccionen una única opción de filtrado o clasificación, como clasificar una lista de elementos por diferentes criterios.

## Texto y posicionamiento {#text-and-positioning}

Los botones de opción pueden utilizar el método ```setText(String text)```, que se posicionará cerca del botón de opción de acuerdo con la `Position` incorporada. Los botones de opción tienen funcionalidad incorporada para establecer texto que se mostrará ya sea a la derecha o a la izquierda del componente. Por defecto, el texto se mostrará a la derecha del componente. El posicionamiento del texto horizontal es compatible mediante el uso de la clase enumerada `HorizontalAlignment`. A continuación se muestran las dos configuraciones: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Activación {#activation}

Los botones de opción pueden ser controlados utilizando dos tipos de activación: activación manual y activación automática. Estos determinan cuándo un `RadioButton` cambiará su estado.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activación manual {#manual-activation}

Cuando un botón de opción está configurado para la activación manual, significa que no se marcará automáticamente cuando reciba el foco. La activación manual permite al usuario navegar a través de las opciones del botón de opción utilizando el teclado u otros métodos de entrada sin cambiar inmediatamente la opción seleccionada.

Si el botón de opción es parte de un grupo, seleccionar un botón de opción diferente dentro del grupo desmarcará automáticamente el botón de opción previamente seleccionado. La activación manual proporciona un control más preciso sobre el proceso de selección, requiriendo una acción explícita del usuario para cambiar la opción seleccionada.

### Activación automática {#auto-activation}

La activación automática es el estado predeterminado para un `RadioButton`, y significa que el botón se marcará siempre que reciba el foco por cualquier motivo. Esto significa que no solo al hacer clic, sino que también el enfoque automático o la navegación por tabulaciones marcarán el botón.

:::tip Nota
El valor de activación predeterminado es **`MANUAL`**.
:::

## Interruptores {#switches}

Un `RadioButton` también puede configurarse para mostrarse como un interruptor, que proporciona una representación visual alternativa para seleccionar opciones. Normalmente, los botones de opción son circulares o redondeados en forma e indican una sola elección de un grupo de opciones.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` se puede transformar en un interruptor que se asemeja a un interruptor de palanca o deslizante utilizando uno de los dos métodos:

1. **El Método de Fábrica**: El botón de opción puede ser creado utilizando los siguientes métodos de fábrica:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Estos métodos reflejan un constructor `RadioButton`, y crearán el componente con la propiedad de interruptor ya activada.

2. **Setter**: También es posible cambiar un `RadioButton` ya existente en un interruptor utilizando el setter apropiado:

```java
myRadioButton.setSwitch(true);
```

Cuando un `RadioButton` se muestra como un interruptor, típicamente aparece como una forma alargada con un indicador que puede ser activado o desactivado. Esta representación visual brinda a los usuarios una interfaz más intuitiva y familiar, similar a los interruptores físicos que se encuentran comúnmente en dispositivos electrónicos.

Configurar un `RadioButton` para mostrarse como un interruptor puede mejorar la experiencia del usuario al proporcionar una forma clara y directa de seleccionar opciones. Puede aumentar el atractivo visual y la usabilidad de formularios, paneles de configuraciones o cualquier otro elemento de interfaz que requiera múltiples elecciones.

:::info
El comportamiento del `RadioButton` permanece igual cuando se representa como un Interruptor, lo que significa que solo se puede seleccionar una opción a la vez dentro de un grupo. La apariencia similar a un interruptor es una transformación visual que mantiene la funcionalidad de un `RadioButton`.
:::

<br/>

## Estilo {#styling}

### Expansiones {#expanses}
Hay cinco expansiones de checkbox que se admiten, las cuales permiten un estilizado rápido sin usar CSS. Las expansiones se admiten mediante el uso de la clase enumerada `Expanse`. A continuación se presentan las expansiones soportadas para el componente checkbox: <br/>

<TableBuilder name="RadioButton" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente RadioButton, considera las siguientes mejores prácticas:

1. **Etiquetar claramente las opciones**: Proporciona texto claro y conciso para cada opción `RadioButton` que describa con precisión la elección. El texto debe ser fácil de entender y distinguir entre sí.

2. **Agrupar botones de opción**: Agrupa los botones de opción relacionados para indicar su asociación. Esto ayuda a los usuarios a comprender que solo se puede seleccionar una opción dentro de un grupo específico. Esto se puede hacer de manera efectiva utilizando el componente [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Proporcionar selección predeterminada**: Si es aplicable, considera proporcionar una selección predeterminada para los botones de opción para guiar a los usuarios cuando se encuentren por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.
