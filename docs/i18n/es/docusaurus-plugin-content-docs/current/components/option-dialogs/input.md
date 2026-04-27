---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 3c045d4085b917bd2f338916cc61d276
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` es un diálogo modal diseñado para solicitar al usuario una entrada. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona la entrada o cierra el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `InputDialog` solicita a los usuarios que ingresen datos, como texto, números u otros datos. Dado que el diálogo es modal, la aplicación espera que el usuario responda antes de continuar:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Tipos {#types}

### Tipos de entrada {#input-types}

El `InputDialog` admite diferentes tipos de campos de entrada, lo que te permite adaptar el método de entrada a tus necesidades específicas:

1. **TEXTO**: Una entrada de texto estándar de una sola línea.
2. **CONTRASEÑA**: Un campo de entrada de contraseña que oculta la entrada del usuario.
3. **NÚMERO**: Un campo de entrada numérica.
4. **CORREO ELECTRÓNICO**: Un campo de entrada para direcciones de correo electrónico.
5. **URL**: Un campo de entrada para URL.
6. **BUSQUEDA**: Un campo de entrada de texto para búsqueda.
7. **FECHA**: Un campo de entrada para seleccionar fechas.
8. **HORA**: Un campo de entrada para seleccionar hora.
9. **FECHA_Y_HORA_LOCAL**: Un campo de entrada para seleccionar fecha y hora local.
10. **COLOR**: Un campo de entrada para seleccionar un color.

### Tipo de mensaje {#message-type}

El `InputDialog` admite los siguientes tipos de mensajes. Cuando configuras un tipo, el diálogo muestra un ícono junto al mensaje, y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño de webforJ.

1. `PLANO`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `PREGUNTA`: Muestra un ícono de signo de interrogación junto al mensaje, utilizando el tema primario.
4. `ADVERTENCIA`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFORMACIÓN`: Muestra un ícono de información junto al mensaje, utilizando el tema de información.

En el siguiente ejemplo, se solicita al usuario que ingrese su contraseña para acceder a la aplicación. Si el inicio de sesión falla, se le pedirá al usuario que lo intente nuevamente.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Resultado {#result}

El `InputDialog` devuelve la entrada del usuario como una cadena. Si el usuario cierra el diálogo sin proporcionar una entrada, el resultado será `null`.

:::important
La cadena resultante será devuelta del método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Por favor ingresa tu edad:", "Entrada de Edad", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Ingresaste: " + result, "Entrada Recibida");
} else {
  OptionDialog.showMessageDialog("No se recibió entrada", "Entrada Cancelada");
}
```

## Valor predeterminado {#default-value}

El `InputDialog` permite especificar un valor predeterminado que aparece en el campo de entrada cuando se muestra el diálogo. Esto puede proporcionar a los usuarios una sugerencia o un valor ingresado previamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Por favor ingresa tu nombre:", "Entrada de Nombre", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Tiempo de espera {#timeout}

El `InputDialog` permite establecer una duración de tiempo de espera después de la cual el diálogo se cierra automáticamente. Esta función es útil para solicitudes de entrada no críticas o acciones que no requieren la interacción inmediata del usuario.

Puedes configurar el tiempo de espera para el diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si el tiempo especificado transcurre sin interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Por favor ingresa tu nombre:", "Entrada de Nombre", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Ingresaste: " + result, "Entrada Recibida", "OK", MessageDialog.MessageType.INFO);
```

## Mejores prácticas {#best-practices}

1. **Solicitudes claras y concisas**: Asegúrate de que el mensaje de solicitud explique claramente qué información se solicita al usuario.
2. **Tipos de entrada apropiados**: Elige tipos de entrada que coincidan con los datos requeridos para asegurar una entrada del usuario precisa y relevante.
3. **Valores predeterminados lógicos**: Establece valores predeterminados que proporcionen sugerencias útiles o entradas anteriores para agilizar la entrada del usuario.
4. **Uso prudente del tiempo de espera**: Establece tiempos de espera para solicitudes de entrada no críticas, asegurándote de que los usuarios tengan suficiente tiempo para proporcionar la información requerida.
5. **Minimiza el uso excesivo**: Utiliza los diálogos de entrada con moderación para evitar frustración en los usuarios. Resérvalos para acciones que requieran una entrada específica del usuario.
