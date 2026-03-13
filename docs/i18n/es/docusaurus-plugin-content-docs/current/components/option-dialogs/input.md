---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` es un cuadro de diálogo modal diseñado para solicitar la entrada del usuario. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona la entrada o cierra el cuadro de diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `InputDialog` solicita a los usuarios que ingresen datos, como texto, números u otra información. Dado que el cuadro de diálogo es modal, la aplicación espera que el usuario responda antes de continuar:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Tipos {#types}

### Tipos de entrada {#input-types}

El `InputDialog` admite diferentes tipos de campos de entrada, lo que permite ajustar el método de entrada a sus necesidades específicas:

1. **TEXTO**: Una entrada de texto estándar de una sola línea.
2. **CONTRASEÑA**: Un campo de entrada de contraseña que oculta la entrada del usuario.
3. **NÚMERO**: Un campo de entrada numérica.
4. **EMAIL**: Un campo de entrada para direcciones de correo electrónico.
5. **URL**: Un campo de entrada para URLs.
6. **BUSQUEDA**: Un campo de entrada de texto para búsquedas.
7. **FECHA**: Un campo de entrada para seleccionar fechas.
8. **HORA**: Un campo de entrada para seleccionar la hora.
9. **DATETIME_LOCAL**: Un campo de entrada para seleccionar la fecha y hora local.
10. **COLOR**: Un campo de entrada para seleccionar un color.

### Tipo de mensaje {#message-type}

El `InputDialog` admite los siguientes tipos de mensajes. Cuando configura un tipo, el diálogo muestra un ícono junto al mensaje, y el tema del cuadro de diálogo se actualiza de acuerdo con las reglas del sistema de diseño webforJ.

1. `PLANO`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `PREGUNTA`: Muestra un ícono de signo de interrogación junto al mensaje, utilizando el tema primario.
4. `ADVERTENCIA`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información junto al mensaje, utilizando el tema de info.

En el siguiente ejemplo, se solicita al usuario que ingrese su contraseña para acceder a la aplicación. Si el inicio de sesión falla, se le pedirá al usuario que intente de nuevo.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Resultado {#result}

El `InputDialog` devuelve la entrada del usuario como una cadena. Si el usuario cierra el cuadro de diálogo sin proporcionar entrada, el resultado será `null`.

:::important
La cadena resultante se devolverá del método `show()`, o del método equivalente de `OptionDialog` como se muestra a continuación. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Por favor ingrese su edad:", "Entrada de Edad", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Ingresaste: " + result, "Entrada Recibida");
} else {
    OptionDialog.showMessageDialog("No se recibió entrada", "Entrada Cancelada");
}
```

## Valor predeterminado {#default-value}

El `InputDialog` le permite especificar un valor predeterminado que aparece en el campo de entrada cuando se muestra el cuadro de diálogo. Esto puede proporcionar a los usuarios una sugerencia o un valor ingresado anteriormente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor ingrese su nombre:", "Entrada de Nombre", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Tiempo de espera {#timeout}

El `InputDialog` le permite establecer una duración de tiempo de espera después de la cual el cuadro de diálogo se cierra automáticamente. Esta característica es útil para solicitudes de entrada no críticas o acciones que no requieren la interacción inmediata del usuario.

Puede configurar el tiempo de espera para el cuadro de diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si el tiempo especificado transcurre sin ninguna interacción del usuario, el cuadro de diálogo se cierra automáticamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor ingrese su nombre:", "Entrada de Nombre", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Ingresaste: " + result, "Entrada Recibida", "OK", MessageDialog.MessageType.INFO);
```

## Mejores prácticas {#best-practices}

1. **Solicitudes Claras y Concisas**: Asegúrese de que el mensaje de solicitud explique claramente qué información se le pide al usuario.
2. **Tipos de Entrada Apropiados**: Elija tipos de entrada que coincidan con los datos requeridos para garantizar una entrada precisa y relevante del usuario.
3. **Valores Predeterminados Lógicos**: Establezca valores predeterminados que proporcionen sugerencias útiles o entradas anteriores para agilizar la entrada del usuario.
5. **Uso Juicioso del Tiempo de Espera**: Establezca tiempos de espera para solicitudes de entrada no críticas, asegurándose de que los usuarios tengan suficiente tiempo para proporcionar la información requerida.
6. **Minimizar el Uso Excesivo**: Use cuadros de diálogo de entrada con moderación para evitar la frustración del usuario. Resérvelos para acciones que requieran una entrada específica del usuario.
