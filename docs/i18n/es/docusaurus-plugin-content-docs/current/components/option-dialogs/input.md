---
title: Input Dialog
sidebar_position: 25
description: >-
  Prompt users for text, numbers, dates, colors, or other typed values with the
  modal InputDialog and message-type styling.
_i18n_hash: b797a58a2e413b1be6d2cfd814d74efa
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` es un cuadro de diálogo modal diseñado para solicitar al usuario que proporcione una entrada. El cuadro de diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona la entrada o cierra el cuadro de diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `InputDialog` solicita a los usuarios que ingresen datos, como texto, números u otra información. Debido a que el cuadro de diálogo es modal, la aplicación espera que el usuario responda antes de continuar:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Tipos {#types}

### Tipos de entrada {#input-types}

El `InputDialog` admite diferentes tipos de campos de entrada, lo que te permite adaptar el método de entrada a tus necesidades específicas:

1. **TEXTO**: Una entrada de texto estándar de una sola línea.
2. **CONTRASEÑA**: Un campo de entrada de contraseña que oculta la entrada del usuario.
3. **NÚMERO**: Un campo de entrada numérico.
4. **EMAIL**: Un campo de entrada para direcciones de correo electrónico.
5. **URL**: Un campo de entrada para URLs.
6. **BUSQUEDA**: Un campo de entrada de texto para búsqueda.
7. **FECHA**: Un campo de entrada para seleccionar fechas.
8. **HORA**: Un campo de entrada para seleccionar la hora.
9. **FECHAINLOCAL**: Un campo de entrada para seleccionar la fecha y hora local.
10. **COLOR**: Un campo de entrada para seleccionar un color.

### Tipo de mensaje {#message-type}

El `InputDialog` admite los siguientes tipos de mensaje. Cuando configuras un tipo, el cuadro de diálogo muestra un ícono junto al mensaje, y el tema del cuadro de diálogo se actualiza de acuerdo con las reglas del sistema de diseño de webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de signo de interrogación junto al mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información junto al mensaje, utilizando el tema de información.

En el siguiente ejemplo, se le pide al usuario que ingrese su contraseña para acceder a la aplicación. Si falla el inicio de sesión, se le pedirá al usuario que lo intente de nuevo.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Resultado {#result}

El `InputDialog` devuelve la entrada del usuario como una cadena. Si el usuario cierra el cuadro de diálogo sin proporcionar una entrada, el resultado será `null`.

:::important
La cadena resultante será devuelta desde el método `show()`, o el método equivalente `OptionDialog` como se muestra a continuación.
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

El `InputDialog` te permite especificar un valor predeterminado que aparece en el campo de entrada cuando se muestra el cuadro de diálogo. Esto puede proporcionar a los usuarios una sugerencia o un valor previamente ingresado.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Por favor, ingresa tu nombre:", "Entrada de Nombre", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Tiempo de espera {#timeout}

El `InputDialog` te permite establecer una duración de tiempo de espera después de la cual el cuadro de diálogo se cierra automáticamente. Esta función es útil para solicitudes de entrada que no son críticas o acciones que no requieren la interacción inmediata del usuario.

Puedes configurar el tiempo de espera para el cuadro de diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si transcurre el tiempo especificado sin interacción del usuario, el cuadro de diálogo se cierra automáticamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Por favor, ingresa tu nombre:", "Entrada de Nombre", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Ingresaste: " + result, "Entrada Recibida", "OK", MessageDialog.MessageType.INFO);
```

## Mejores prácticas {#best-practices}

1. **Indicaciones claras y concisas**: Asegúrate de que el mensaje de indicación explique claramente qué información se le está solicitando al usuario.
2. **Tipos de entrada apropiados**: Elige tipos de entrada que coincidan con los datos requeridos para garantizar una entrada del usuario precisa y relevante.
3. **Valores predeterminados lógicos**: Establece valores predeterminados que brinden sugerencias útiles o entradas anteriores para agilizar la entrada del usuario.
4. **Uso juicioso del tiempo de espera**: Establece tiempos de espera para solicitudes de entrada no críticas, asegurándote de que los usuarios tengan suficiente tiempo para proporcionar la información requerida.
5. **Minimiza el uso excesivo**: Usa cuadros de diálogo de entrada con moderación para evitar la frustración del usuario. Resérvalos para acciones que requieran una entrada específica del usuario.
