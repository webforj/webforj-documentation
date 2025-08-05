---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

La clase `RadioButton` crea un objeto que puede ser seleccionado o deseleccionado, y que muestra su estado al usuario. Por convención, solo un botón de radio en un grupo puede estar seleccionado a la vez. Los botones de radio son comúnmente utilizados cuando hay opciones mutuamente exclusivas disponibles, permitiendo al usuario elegir una única opción de un conjunto de elecciones.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Usos {#usages}

El `RadioButton` se utiliza mejor en situaciones donde los usuarios necesitan hacer una única selección de un conjunto de opciones predeterminadas. Aquí hay algunos ejemplos de cuándo usar el `RadioButton`:

1. **Encuestas o Cuestionarios**: Los botones de radio son comúnmente utilizados en encuestas o cuestionarios donde los usuarios necesitan seleccionar una sola respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que implican paneles de preferencias o configuraciones a menudo utilizan botones de radio para permitir a los usuarios elegir una sola opción de un conjunto de elecciones mutuamente exclusivas.

3. **Filtrado o Clasificación**: Un `RadioButton` puede ser utilizado en aplicaciones que requieren que los usuarios seleccionen una única opción de filtrado o clasificación, como ordenar una lista de elementos por diferentes criterios.

## Texto y posicionamiento {#text-and-positioning}

Los botones de radio pueden utilizar el método ```setText(String text)```, que se posicionará cerca del botón de radio según la `Position` incorporada. Los botones de radio tienen funcionalidad incorporada para establecer el texto que se mostrará ya sea a la derecha o a la izquierda del componente. Por defecto, el texto se mostrará a la derecha del componente. La posición del texto horizontal se admite mediante el uso de la clase enumerada `HorizontalAlignment`. A continuación se muestran las dos configuraciones: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Activación {#activation}

Los botones de radio pueden ser controlados utilizando dos tipos de activación: activación manual y activación automática. Estas dictan cuándo un `RadioButton` cambiará su estado.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Activación manual {#manual-activation}

Cuando un botón de radio se establece en activación manual, significa que no se marcará automáticamente cuando gane el foco. La activación manual permite al usuario navegar a través de las opciones del botón de radio utilizando el teclado u otros métodos de entrada sin cambiar inmediatamente la opción seleccionada.

Si el botón de radio es parte de un grupo, seleccionar un botón de radio diferente dentro del grupo desmarcará automáticamente el botón de radio que fue seleccionado previamente. La activación manual proporciona un control más fino sobre el proceso de selección, requiriendo una acción explícita del usuario para cambiar la opción seleccionada.

### Activación automática {#auto-activation}

La activación automática es el estado predeterminado para un `RadioButton`, y significa que el botón se marcará cada vez que gane el foco por cualquier motivo. Esto significa que no solo al hacer clic, sino también al autoenfocar o navegar con tabulador se marcará el botón.

:::tip Nota
El valor de activación predeterminado es **`MANUAL`**.
:::

## Interruptores {#switches}

Un `RadioButton` también puede configurarse para mostrarse como un interruptor que proporciona una representación visual alternativa para seleccionar opciones. Normalmente, los botones de radio tienen forma circular o redondeada e indican una única elección de un grupo de opciones.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Un `RadioButton` puede transformarse en un interruptor que se asemeja a un interruptor de palanca o deslizante utilizando uno de dos métodos:

1. **El Método de Fábrica**: El botón de radio puede ser creado utilizando los siguientes métodos de fábrica:

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

Cuando un `RadioButton` se muestra como un interruptor, típicamente aparece como una forma oblonga con un indicador que puede ser alternado encendido o apagado. Esta representación visual brinda a los usuarios una interfaz más intuitiva y familiar, similar a los interruptores físicos comúnmente encontrados en dispositivos electrónicos.

Configurar un `RadioButton` para mostrarse como un interruptor puede mejorar la experiencia del usuario al proporcionar una forma clara y directa de seleccionar opciones. Puede mejorar el atractivo visual y la usabilidad de formularios, paneles de configuración, o cualquier otro elemento de interfaz que requiera múltiples elecciones.

:::info
El comportamiento del `RadioButton` sigue siendo el mismo cuando se representa como un Interruptor, lo que significa que solo se puede seleccionar una opción a la vez dentro de un grupo. La apariencia tipo interruptor es una transformación visual que mantiene la funcionalidad de un `RadioButton`.
:::

<br/>

## Estilización {#styling}

### Expanses {#expanses}
Hay cinco expansiones de casillas de verificación que son compatibles y que permiten un rápido estilo sin usar CSS. Las expansiones son compatibles mediante el uso de la clase enumerada `Expanse`. A continuación se presentan las expansiones compatibles para el componente de casilla de verificación: <br/>

<TableBuilder name="RadioButton" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente RadioButton, considera las siguientes mejores prácticas:

1. **Etiquetar Claramente las Opciones**: Proporciona texto claro y conciso para cada opción del `RadioButton` para describir con precisión la elección. El texto debe ser fácil de entender y distinguir entre sí.

2. **Agrupar Botones de Radio**: Agrupa botones de radio relacionados para indicar su asociación. Esto ayuda a los usuarios a comprender que solo se puede seleccionar una opción dentro de un grupo específico. Esto se puede hacer de manera efectiva utilizando el componente [`RadioButtonGroup`](/docs/components/radiobuttongroup).

3. **Proporcionar Selección Predeterminada**: Si es aplicable, considera proporcionar una selección predeterminada para los botones de radio que guíe a los usuarios cuando se encuentran por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.
