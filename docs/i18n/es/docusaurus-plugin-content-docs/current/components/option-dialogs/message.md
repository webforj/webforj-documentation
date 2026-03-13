---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Un `MessageDialog` es un diálogo modal diseñado para mostrar un mensaje al usuario con un botón `OK` para cerrar el diálogo. Bloquea la ejecución de la aplicación hasta que el usuario interactúe con él o se cierre debido a un tiempo de espera.

<!-- INTRO_END -->

## Usos {#usages}

Utiliza el método estático `showMessageDialog` para mostrar un mensaje básico.

```java
OptionDialog.showMessageDialog("¡Hola Mundo!");
```

Para tener más control sobre la apariencia y el comportamiento del diálogo, crea una instancia de `MessageDialog` directamente.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "¡Hola Mundo!", "¡Hola Mundo!", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Tipo de mensaje {#message-type}

El `MessageDialog` soporta los siguientes tipos de mensajes. Cuando configuras un tipo, el diálogo muestra un ícono junto al mensaje y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, utilizando el tema por defecto.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de signo de interrogación junto al mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información junto al mensaje, utilizando el tema de información.

En el siguiente ejemplo, el código configura un diálogo de mensaje de tipo `WARNING` con un título y un mensaje personalizados.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Tema del Diálogo y Botón
Por defecto, el diálogo determina el tema en función del tipo de mensaje. Puedes personalizar el tema del diálogo utilizando el método `setTheme(Theme theme)` y ajustar independientemente el tema del botón con el método `setButtonTheme(ButtonTheme theme)` para crear diferentes variaciones.
:::

## Texto del botón {#button-text}

Puedes configurar el texto del botón del diálogo usando `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("¡Hola Mundo!", "Título", "¡Entendido!");
```

## Procesamiento HTML {#html-processing}

Por defecto, el diálogo de mensaje procesa y renderiza contenido HTML. Puedes desactivar esta función configurándolo para mostrar texto sin formato en su lugar.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>¡Hola Mundo!</b>", "¡Hola Mundo!", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Tiempo de espera {#timeout}

El `MessageDialog` te permite establecer una duración de tiempo de espera después de la cual el diálogo se cierra automáticamente. Esta función es útil para notificaciones o información no crítica que no requiere la interacción inmediata del usuario.

Puedes configurar el tiempo de espera para el diálogo utilizando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si transcurre el tiempo especificado sin interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Este diálogo se cerrará pronto", "Tiempo de espera");
dialog.setTimeout(2);
dialog.show();
```

## Mejores prácticas {#best-practices}

1. **Mensajes claros y concisos**: Mantén los mensajes breves y directos y evita jerga técnica; usa un lenguaje amigable para el usuario.
2. **Tipos de mensaje apropiados**:
   - Usa `ERROR` para problemas críticos.
   - Usa `WARNING` para avisos de precaución.
   - Usa `INFO` para información general.
3. **Tematización consistente**: Alinea los temas del diálogo y del botón con el diseño de tu aplicación.
4. **Uso prudente del tiempo de espera**: Establece tiempos de espera para notificaciones no críticas y asegúrate de que los usuarios tengan suficiente tiempo para leer el mensaje.
5. **Evitar el uso excesivo**: Usa diálogos con moderación para evitar la frustración del usuario y resérvalos para mensajes importantes que requieran acción o reconocimiento del usuario.
