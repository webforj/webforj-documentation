---
title: Terminal
sidebar_position: 126
_i18n_hash: 4b7abaef79b0511bbbb796171ddd8c07
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

El componente `Terminal` proporciona un emulador de terminal interactivo que se comporta de manera similar a una consola de sistema tradicional. Permite a las aplicaciones mostrar y manipular una interfaz basada en texto, manejando la salida de texto, recibiendo la entrada del usuario, interpretando secuencias de control y manteniendo buffers de pantalla.

Esta terminal está diseñada para ofrecer un comportamiento confiable en una variedad de casos de uso, como la creación de herramientas de acceso remoto, paneles de texto, shells de comandos integrados o consolas de depuración interactivas.

:::info Importando Terminal
Para utilizar el componente `Terminal` en tu aplicación, asegúrate de incluir la siguiente dependencia en tu pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

<ComponentDemo 
path='/webforj/terminal?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
urls={[
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## Cómo funciona {#how-it-works}

La terminal gestiona una cuadrícula de celdas de texto, procesa flujos de caracteres entrantes y reacciona a acciones del usuario como escribir o seleccionar texto. Interpreta automáticamente caracteres de control y secuencias de escape para el movimiento del cursor, cambios de color y limpieza de pantalla.

Los comportamientos principales incluyen:

- **Entrada de Datos**: Escribir datos en la terminal actualiza la pantalla, manejando tanto secuencias de texto como de control.
- **Salida de Datos**: Captura las pulsaciones de teclas del usuario y las emite como eventos estructurados.
- **Gestión de Pantalla**: Mantiene un buffer de historia desplazable y el estado actual de la pantalla.
- **Manejo del Cursor**: Rastrear la posición del cursor para la entrada de texto y las respuestas de secuencias de control.

La terminal es Stateful, lo que significa que reconstruye correctamente los caracteres multibyte y mantiene la continuidad a través de entradas fragmentadas.

## Enviando datos a la terminal {#sending-data-to-the-terminal}

Los datos se envían a la terminal utilizando los métodos `write` y `writeln`:

- `write(Object data)`: Envía datos al flujo de la terminal.
- `writeln(Object data)`: Envía datos seguidos de una nueva línea.

La terminal procesa todos los datos entrantes como cadenas **UTF-16**. Maneja automáticamente caracteres multibyte, incluso cuando la entrada llega en fragmentos.

### Ejemplo {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

También puedes adjuntar un callback que se ejecuta una vez que el fragmento de datos ha sido procesado:

```java
terminal.write("Long command output", e -> {
    System.out.println("Data processed.");
});
```

## Recibiendo la entrada del usuario {#receiving-user-input}

La terminal captura la entrada generada por el usuario a través de dos eventos:

- **Evento de Datos (`onData`)**: Se activa cuando se produce una entrada de texto, enviando caracteres Unicode.
- **Evento de Tecla (`onKey`)**: Se activa por cada pulsación de tecla, incluyendo información sobre códigos de tecla y modificadores como <kbd>Ctrl</kbd> o <kbd>Alt</kbd>.

Estos eventos se pueden utilizar para transmitir la entrada del usuario a un backend, actualizar elementos de la interfaz de usuario o activar acciones personalizadas.

### Ejemplo {#example-1}
```java
terminal.onData(event -> {
  String userInput = event.getValue();
  backend.send(userInput);
});

terminal.onKey(event -> {
  if (event.isControlKey() && "C".equals(event.getKey())) {
      backend.send("SIGINT");
  }
});
```

Toda la entrada del usuario capturada por la terminal (como desde eventos `onData`) se emite como cadenas UTF-16.  
Si tu backend espera una codificación diferente (como bytes UTF-8), deberás transcoder manualmente los datos.

:::info Codificaciones Legadas
La terminal **no admite codificaciones legadas** como `ISO-8859`.  
Si necesitas compatibilidad con sistemas que no son UTF-8, utiliza un transcoder externo (por ejemplo, [`luit`](https://linux.die.net/man/1/luit) o [`iconv`](https://en.wikipedia.org/wiki/Iconv)) para convertir los datos antes de escribirlos o leerlos desde la terminal.
:::

## Manejo de grandes flujos de datos {#handling-large-data-streams}

Debido a que la terminal no puede renderizar instantáneamente entradas ilimitadas, mantiene un buffer interno de entrada. Si este buffer crece demasiado (alrededor de `50MB` por defecto), es posible que se pierdan nuevos datos entrantes para proteger el rendimiento del sistema.

Para gestionar correctamente fuentes de datos rápidas, debes implementar **control de flujo**.

### Ejemplo básico de control de flujo {#basic-flow-control-example}

Pausa tu backend hasta que la terminal termine de procesar un fragmento:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Ejemplo de control de flujo mediante marcas de agua {#watermark-flow-control-example}

Para un control más eficiente, usa marcas de agua altas/bajas:

```java
int HIGH_WATERMARK = 100_000;
int LOW_WATERMARK = 10_000;

int bufferedBytes = 0;

pty.onData(chunk -> {
  bufferedBytes += chunk.length;

  terminal.write(chunk, e -> {
    bufferedBytes -= chunk.length;
    if (bufferedBytes < LOW_WATERMARK) {
        pty.resume();
    }
  });

  if (bufferedBytes > HIGH_WATERMARK) {
    pty.pause();
  }
});
```

<ComponentDemo 
path='/webforj/serverlogs?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java'
height='400px'
/>

## Personalización {#customization}

### Opciones de Terminal {#terminal-options}

La clase `TerminalOptions` te permite configurar el comportamiento:

- Parpadeo del cursor.
- Configuraciones de fuente (familia, tamaño, peso).
- Tamaño del buffer de desplazamiento.
- Altura de línea y espaciado de letras.
- Configuraciones de accesibilidad (modo lector de pantalla).

Ejemplo:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Tema de Terminal {#terminal-theme}

Puedes estilizar la terminal utilizando `TerminalTheme`, que define:

- Colores de fondo y primer plano.
- Paleta de colores `ANSI` estándar.
- Colores de fondo del cursor y la selección.

Ejemplo:
```java
TerminalTheme theme = new TerminalTheme();
theme.setBackground("#1e1e1e");
theme.setForeground("#cccccc");
terminal.setTheme(theme);
```
<ComponentDemo 
path='/webforj/terminalthemepicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java'
height='500px'
/>

## Secuencias compatibles {#supported-sequences}

La terminal admite una amplia gama de secuencias de control estándar utilizadas para el movimiento del cursor, actualizaciones de pantalla y formateo de texto.

Grupos reconocidos:

- **Códigos de control `C0`** (comandos de 7 bits de un solo byte, `\x00`, `\x1F`, como retroceso y salto de línea)
- **Códigos de control `C1`** (comandos de 8 bits de un solo byte, `\x80`, `\x9F`)
- **Secuencias `ESC`** (que comienzan con `ESC` (`\x1B`), como guardar/restaurar el cursor, alineación de pantalla)
- **Secuencias `CSI`** (Introducción de Secuencias de Control, `ESC [` o `CSI (\x9B)`, para operaciones como desplazar, borrar y estilizar)
- **Secuencias `DCS`** (Cadenas de Control de Dispositivos, `ESC P` o `DCS (\x90)`)
- **Secuencias `OSC`** (Comandos del Sistema Operativo, `ESC ]` o `OSC (\x9D)`, para establecer el título de la ventana, hiperenlaces y colores)

:::info Manejo de Secuencias Exóticas y Personalizadas
Algunos tipos de secuencias exóticas como `APC`, `PM`, y `SOS` son reconocidos pero ignorados silenciosamente.  
Las secuencias personalizadas pueden ser compatibles a través de integraciones si es necesario.
:::

## Estilo {#styling}

<TableBuilder name="Terminal" />
