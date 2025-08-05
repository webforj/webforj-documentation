---
sidebar_position: 25
title: Input Dialog
_i18n_hash: ba46203db1b4c35878c6509a514a70e5
---
# Diálogo de Entrada

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` es un diálogo modal diseñado para solicitar la entrada del usuario. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporcione la entrada o cierre el diálogo.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Usos {#usages}

El `InputDialog` proporciona una forma de solicitar información de los usuarios, como texto, números u otros datos, asegurando que proporcionen la información necesaria antes de continuar.

## Tipos {#types}

### Tipos de entrada {#input-types}

El `InputDialog` admite diferentes tipos de campos de entrada, lo que le permite adaptar el método de entrada a sus necesidades específicas:

1. **TEXTO**: Una entrada de texto estándar de una sola línea.
2. **CONTRASEÑA**: Un campo de entrada de contraseña que oculta la entrada del usuario.
3. **NÚMERO**: Un campo de entrada numérica.
4. **EMAIL**: Un campo de entrada para direcciones de correo electrónico.
5. **URL**: Un campo de entrada para URLs.
6. **BUSQUEDA**: Un campo de entrada de texto de búsqueda.
7. **FECHA**: Un campo de entrada para seleccionar fechas.
8. **HORA**: Un campo de entrada para seleccionar la hora.
9. **FECHA_HORA_LOCAL**: Un campo de entrada para seleccionar fecha y hora local.
10. **COLOR**: Un campo de entrada para seleccionar un color.

### Tipo de mensaje {#message-type}

El `InputDialog` admite los siguientes tipos de mensajes. Cuando configura un tipo, el diálogo muestra un ícono junto al mensaje, y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error al lado del mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de interrogación junto al mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia al lado del mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información al lado del mensaje, utilizando el tema de información.

En el siguiente ejemplo, se solicita al usuario que ingrese su contraseña para acceder a la aplicación. Si el inicio de sesión falla, se le pedirá al usuario nuevamente.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Resultado {#result}

El `InputDialog` devuelve la entrada del usuario como una cadena. Si el usuario cierra el diálogo sin proporcionar entrada, el resultado será `null`.

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

El `InputDialog` le permite especificar un valor predeterminado que aparece en el campo de entrada cuando se muestra el diálogo. Esto puede proporcionar a los usuarios una sugerencia o un valor previamente ingresado.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor ingrese su nombre:", "Entrada de Nombre", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Tiempo de espera {#timeout}

El `InputDialog` le permite establecer una duración de tiempo de espera después de la cual el diálogo se cerrará automáticamente. Esta función es útil para solicitudes de entrada no críticas o acciones que no requieren la interacción inmediata del usuario.

Puede configurar el tiempo de espera para el diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si transcurre el tiempo especificado sin ninguna interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Por favor ingrese su nombre:", "Entrada de Nombre", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Ingresaste: " + result, "Entrada Recibida", "OK", MessageDialog.MessageType.INFO);
```

## Mejores prácticas {#best-practices}

1. **Prompts claros y concisos**: Asegúrese de que el mensaje del prompt explique claramente qué información se le está pidiendo al usuario.
2. **Tipos de entrada apropiados**: Elija tipos de entrada que coincidan con los datos requeridos para garantizar una entrada de usuario precisa y relevante.
3. **Valores predeterminados lógicos**: Establezca valores predeterminados que proporcionen sugerencias útiles o entradas anteriores para agilizar la entrada del usuario.
5. **Uso juicioso del tiempo de espera**: Establezca tiempos de espera para solicitudes de entrada no críticas, asegurando que los usuarios tengan suficiente tiempo para proporcionar la información requerida.
6. **Minimizar el uso excesivo**: Use los diálogos de entrada de manera moderada para evitar la frustración del usuario. Resérvelos para acciones que requieran entradas específicas del usuario.
