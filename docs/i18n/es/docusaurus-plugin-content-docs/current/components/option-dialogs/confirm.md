---
sidebar_position: 5
title: Confirm
_i18n_hash: 1a5d5c10371b3d751853eb3c3bcbe66f
---
# Confirm Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Un `ConfirmDialog` es un cuadro de diálogo modal diseñado para permitir al usuario elegir una de un conjunto de hasta 3 opciones. El diálogo bloquea la ejecución de la aplicación hasta que el usuario interactúe con él o se cierre debido a un tiempo de espera.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "¿Confirma?",
    "Confirmación",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Usages {#usages}

El `ConfirmDialog` proporciona una forma de pedir a los usuarios confirmación o elegir entre múltiples opciones, como `Sí/No` o `OK/Cancelar`, asegurando que reconozcan y confirmen sus acciones.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types {#types}

### Option type {#option-type}

El `ConfirmDialog` admite los siguientes tipos de opción, que determinan los botones que se muestran en el diálogo:

1. **`OK`**: Muestra un botón `OK`.
2. **`OK_CANCEL`**: Muestra botones `OK` y `Cancelar`.
3. **`ABORT_RETRY_IGNORE`**: Muestra botones `Abortar`, `Reintentar` e `Ignorar`.
4. **`YES_NO_CANCEL`**: Muestra botones `Sí`, `No` y `Cancelar`.
5. **`YES_NO`**: Muestra botones `Sí` y `No`.
6. **`RETRY_CANCEL`**: Muestra botones `Reintentar` y `Cancelar`.
7. **`CUSTOM`**: Muestra botones personalizados según se especifique.

### Message type {#message-type}

El `ConfirmDialog` admite los siguientes tipos de mensaje. Cuando configures un tipo, el diálogo muestra un ícono al lado del mensaje, y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, utilizando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error al lado del mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de signo de interrogación al lado del mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia al lado del mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información al lado del mensaje, utilizando el tema de información.

En el siguiente ejemplo, el código configura un cuadro de diálogo de confirmación de tipo `CUSTOM` con un título y mensaje personalizados.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Result {#result}

El `ConfirmDialog` devuelve un resultado basado en la interacción del usuario con el diálogo. Este resultado indica qué botón hizo clic el usuario o si el diálogo se cerró debido a un tiempo de espera.

:::important
El resultado se devolverá del método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación. 
:::

La enumeración `ConfirmDialog.Result` incluye los siguientes resultados posibles:

1. **`OK`**: El usuario hizo clic en el botón `OK`.
2. **`CANCEL`**: El usuario hizo clic en el botón `CANCEL`.
3. **`YES`**: El usuario hizo clic en el botón `YES`.
4. **`NO`**: El usuario hizo clic en el botón `NO`.
5. **`ABORT`**: El usuario hizo clic en el botón `ABORT`.
6. **`RETRY`**: El usuario hizo clic en el botón `RETRY`.
7. **`IGNORE`**: El usuario hizo clic en el botón `IGNORE`.
8. **`FIRST_CUSTOM_BUTTON`**: El usuario hizo clic en el primer botón personalizado.
9. **`SECOND_CUSTOM_BUTTON`**: El usuario hizo clic en el segundo botón personalizado.
10. **`THIRD_CUSTOM_BUTTON`**: El usuario hizo clic en el tercer botón personalizado.
11. **`TIMEOUT`**: El diálogo se cerró por tiempo de espera.
12. **`UNKNOWN`**: Un resultado desconocido, típicamente utilizado como estado predeterminado o de error.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Cambios descartados", "Descartados", "Entendido");
} else {
    OptionDialog.showMessageDialog(
        "Cambios guardados", "Guardado", "Entendido", MessageDialog.MessageType.INFO);
}
```

## Default button {#default-button}

El `ConfirmDialog` te permite especificar un botón predeterminado que está preseleccionado cuando se muestra el diálogo. Esto mejora la experiencia del usuario al proporcionar una acción sugerida que puede confirmarse rápidamente presionando la tecla <kbd>Enter</kbd>.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "¿Estás seguro?", "Confirmar", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // segundo botón
dialog.show();
```

## Buttons text {#buttons-text}

Puedes configurar el texto de los botones utilizando el método `setButtonText(ConfirmDialog.Button button, String text)`.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "¿Estás seguro?", "Confirmar", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolutamente");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nope");
dialog.show();
```

## HTML processing {#html-processing}

Por defecto, el cuadro de diálogo de confirmación procesa y renderiza contenido HTML. Puedes desactivar esta función configurándolo para que muestre texto sin formato en su lugar.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>¿Estás seguro?</b>", "Confirmar",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

El `ConfirmDialog` te permite establecer una duración de tiempo de espera después de la cual el diálogo se cierra automáticamente. Esta función es útil para confirmaciones o acciones que no requieren la interacción inmediata del usuario.

Puedes configurar el tiempo de espera para el diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si transcurre el tiempo especificado sin ninguna interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "¿Estás seguro?", "Confirmar", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Te tomó demasiado tiempo decidir", "Tiempo de espera", "Entendido",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Hiciste clic en Sí", "Sí", "Entendido",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Hiciste clic en No", "No", "Entendido",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices {#best-practices}

1. **Indicaciones Claras y Concisas**: Asegúrate de que el mensaje de indicación explique claramente qué acción se le pide al usuario que confirme. Evita ambigüedades.
2. **Tipos de Opción Apropiados**: Elige tipos de opción que coincidan con el contexto de la acción. Para decisiones simples de sí/no, usa opciones directas. Para escenarios más complejos, proporciona botones adicionales como "Cancelar" para permitir que los usuarios se echen atrás sin tomar una decisión.
3. **Botón Predeterminado Lógico**: Establece un botón predeterminado que se alinee con la acción del usuario más probable o recomendada para agilizar la toma de decisiones.
4. **Tematización Consistente**: Alinea los temas del diálogo y los botones con el diseño de tu aplicación para una experiencia de usuario cohesiva.
5. **Uso Juicioso de Tiempo de Espera**: Establece tiempos de espera para confirmaciones no críticas, asegurándote de que los usuarios tengan suficiente tiempo para leer y comprender la indicación.
6. **Minimizar el Uso Excesivo**: Usa cuadros de diálogo de confirmación con moderación para evitar la frustración del usuario. Resérvalos para acciones críticas que requieran una confirmación explícita del usuario.
