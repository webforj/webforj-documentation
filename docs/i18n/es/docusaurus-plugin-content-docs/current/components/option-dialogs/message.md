---
sidebar_position: 30
title: Message
_i18n_hash: 633e8c1297144da8b39cfd7ca2e77e5c
---
# Diálogo de Mensaje

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Un `MessageDialog` es un diálogo modal diseñado para mostrar un mensaje al usuario con un botón de `OK` para cerrar el diálogo. Bloquea la ejecución de la aplicación hasta que el usuario interactúe con él o se cierre debido a un tiempo de espera.

```java
OptionDialog.showMessageDialog("¡Hola Mundo!");
```

## Usos {#usages}

El Diálogo de Mensaje proporciona una manera de mostrar alertas informativas, tales como notificaciones, actualizaciones o mensajes simples que solo requieren que el usuario los reconozca sin proporcionar ninguna entrada.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "¡Hola Mundo!", "¡Hola Mundo!", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Tipo de mensaje {#message-type}

El `MessageDialog` admite los siguientes tipos de mensajes. Cuando configuras un tipo, el diálogo muestra un ícono junto al mensaje, y el tema del diálogo se actualiza de acuerdo con las reglas del sistema de diseño de webforJ.

1. `PLAIN`: Muestra el mensaje sin un ícono, usando el tema predeterminado.
2. `ERROR`: Muestra un ícono de error junto al mensaje con el tema de error aplicado.
3. `QUESTION`: Muestra un ícono de signo de interrogación al lado del mensaje, utilizando el tema primario.
4. `WARNING`: Muestra un ícono de advertencia junto al mensaje con el tema de advertencia aplicado.
5. `INFO`: Muestra un ícono de información al lado del mensaje, utilizando el tema de información.

En el siguiente ejemplo, el código configura un diálogo de mensaje de tipo `WARNING`, con un título y mensaje personalizados.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Tema del Diálogo y Botón
Por defecto, el diálogo determina el tema basado en el tipo de mensaje. Puedes personalizar el tema del diálogo usando el método `setTheme(Theme theme)` y ajustar de manera independiente el tema del botón con el método `setButtonTheme(ButtonTheme theme)` para crear diferentes variaciones.
:::

## Texto del botón {#button-text}

Puedes configurar el texto del botón del diálogo utilizando el `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("¡Hola Mundo!", "Título", "Entendido");
```

## Procesamiento de HTML {#html-processing}

Por defecto, el diálogo de mensajes procesa y renderiza contenido HTML. Puedes desactivar esta característica configurándolo para mostrar texto sin formato en su lugar.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>¡Hola Mundo!</b>", "¡Hola Mundo!", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Tiempo de espera {#timeout}

El `MessageDialog` te permite establecer una duración de tiempo de espera después de la cual el diálogo se cierra automáticamente. Esta característica es útil para notificaciones o información no crítica que no requiera la interacción inmediata del usuario.

Puedes configurar el tiempo de espera del diálogo usando el método `setTimeout(int timeout)`. La duración del tiempo de espera está en segundos. Si el tiempo especificado transcurre sin ninguna interacción del usuario, el diálogo se cierra automáticamente.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Este diálogo se cerrará pronto", "Tiempo de espera");
dialog.setTimeout(2);
dialog.show();
```

## Mejores prácticas {#best-practices}

1. **Mensajes claros y concisos**: Mantén los mensajes cortos y directos, evitando jerga técnica; usa un lenguaje amigable para el usuario.
2. **Tipos de mensajes apropiados**:
   - Usa `ERROR` para problemas críticos.
   - Usa `WARNING` para avisos de precaución.
   - Usa `INFO` para información general.
3. **Tematización consistente**: Alinea los temas del diálogo y del botón con el diseño de tu aplicación.
4. **Uso judicioso del tiempo de espera**: Establece tiempos de espera para notificaciones no críticas y asegúrate de que los usuarios tengan suficiente tiempo para leer el mensaje.
5. **Evitar el uso excesivo**: Usa diálogos con moderación para prevenir la frustración del usuario y resérvalos para mensajes importantes que requieran acción o reconocimiento del usuario.
