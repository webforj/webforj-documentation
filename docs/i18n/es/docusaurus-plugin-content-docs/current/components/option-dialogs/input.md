---
sidebar_position: 25
title: Input Dialog
_i18n_hash: 60c8f92b63b241996eda4f5a08df8027
---
# Diálogo de Entrada

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` es un cuadro de diálogo modal diseñado para solicitar la entrada del usuario. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporcione la entrada o cierre el diálogo.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Usos {#usages}

El `InputDialog` proporciona una forma de solicitar la entrada de los usuarios, como texto, números u otros datos, asegurando que proporcionen la información necesaria antes de continuar.

## Tipos {#types}

### Tipos de entrada {#input-types}

El `InputDialog` admite diferentes tipos de campos de entrada, lo que te permite adaptar el método de entrada a tus necesidades específicas:

1. **TEXTO**: Una entrada de texto estándar de una sola línea.
2. **CONTRASEÑA**: Un campo de entrada para contraseña que oculta la entrada del usuario.
3. **NÚMERO**: Un campo de entrada numérico.
4. **CORREO ELECTRÓNICO**: Un campo de entrada para direcciones de correo electrónico.
5. **URL**: Un campo de entrada para URLs.
6. **BÚSQUEDA**: Un campo de entrada de texto de búsqueda.
7. **FECHA**: Un campo de entrada para seleccionar fechas.
8. **HORA**: Un campo de entrada para seleccionar la hora.
9. **FECHA_HORA_LOCAL**: Un campo de entrada para seleccionar la fecha y hora locales.
10. **COLOR**: Un campo de entrada para seleccionar un color.

### Tipo de mensaje {#message-type}

El `InputDialog` admite los siguientes tipos de mensaje. Cuando configuras un tipo, el diálogo muestra un ícono junto al mensaje y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño de webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de signo de interrogación junto al mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información junto al mensaje, utilizando el tema de información.

En el siguiente ejemplo, se le pide al usuario que ingrese su contraseña para acceder a la aplicación. Si el inicio de sesión falla, se le pedirá al usuario nuevamente.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Resultado {#result}

El `InputDialog` devuelve la entrada del usuario como una cadena. Si el usuario cierra el diálogo sin proporcionar entrada, el resultado será `null`.

:::important
La cadena resultante se devolverá desde el método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Por favor, ingresa tu edad:", "Entrada de Edad", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Ingresaste: " + result, "Entrada Recibida");
} else {
    OptionDialog.showMessageDialog("No se recibió entrada", "Entrada Cancelada");
}
```

## Valor predeterminado {#default-value}

El `InputDialog` permite especificar un valor predeterminado que aparece en el campo de entrada cuando se muestra el diálogo. Esto puede proporcionar a los usuarios una sugerencia o un valor ingresado anteriormente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor, ingresa tu nombre:", "Entrada de Nombre", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Tiempo de espera {#timeout}

El `InputDialog` te permite establecer una duración de tiempo de espera después de la cual el diálogo se cierra automáticamente. Esta característica es útil para solicitudes de entrada no críticas o acciones que no requieren la interacción inmediata del usuario.

Puedes configurar el tiempo de espera para el diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si transcurre el tiempo especificado sin ninguna interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor, ingresa tu nombre:", "Entrada de Nombre", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Ingresaste: " + result, "Entrada Recibida", "OK", MessageDialog.MessageType.INFO);
```

## Mejores prácticas {#best-practices}

1. **Indicaciones Claras y Concisas**: Asegúrate de que el mensaje de indicación explique claramente qué información se le está pidiendo al usuario.
2. **Tipos de Entrada Apropiados**: Elige tipos de entrada que coincidan con el dato requerido para asegurar una entrada de usuario precisa y relevante.
3. **Valores Predeterminados Lógicos**: Establece valores predeterminados que proporcionen sugerencias útiles o entradas anteriores para agilizar la entrada del usuario.
4. **Uso Juicioso del Tiempo de Espera**: Establece tiempos de espera para solicitudes de entrada no críticas, asegurando que los usuarios tengan suficiente tiempo para proporcionar la información requerida.
5. **Minimizar el Uso Excesivo**: Usa diálogos de entrada con moderación para evitar la frustración del usuario. Resérvalos para acciones que requieran una entrada específica del usuario.
