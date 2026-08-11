---
title: Terminal
sidebar_position: 126
description: >-
  Embed an interactive terminal emulator with the Terminal component for shells,
  dashboards, debug consoles, and remote access tools.
_i18n_hash: 231986360b04eb43ad3b6fecc9f02816
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

El componente `Terminal` es un emulador de terminal interactivo que se comporta como una consola de sistema tradicional. Maneja la salida de texto, la entrada del usuario, las secuencias de control y los buffers de pantalla, lo que lo hace adecuado para construir herramientas de acceso remoto, paneles de texto, shells de comandos integrados o consolas de depuración.

<!-- INTRO_END -->

## Creando un terminal {#creating-a-terminal}

:::info Importando Terminal
Para usar el componente `Terminal` en tu aplicación, asegúrate de incluir la siguiente dependencia en tu pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

El siguiente ejemplo construye un shell de comandos interactivo con comandos escritos, navegación por el historial y salida personalizada.

<ComponentDemo
path='/webforj/terminal'
files={[
  'src/main/java/com/webforj/samples/views/terminal/TerminalView.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ConfirmCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java',
  'src/main/frontend/css/terminal/terminal-view.css',
]}
height='400px'
/>

## Cómo funciona {#how-it-works}

El terminal gestiona una cuadrícula de celdas de texto, procesa flujos de caracteres entrantes y reacciona a acciones del usuario como escribir o seleccionar texto. Interpreta automáticamente los caracteres de control y las secuencias de escape para el movimiento del cursor, cambios de color y limpieza de pantalla.

Los comportamientos principales incluyen:

- **Entrada de datos**: Escribir datos en el terminal actualiza la pantalla, manejando tanto texto como secuencias de control.
- **Salida de datos**: Captura las pulsaciones de teclas del usuario y las emite como eventos estructurados.
- **Gestión de pantalla**: Mantiene un buffer de historial desplazable y el estado actual de la pantalla.
- **Manejo del cursor**: Rastrea la posición del cursor para la entrada de texto y las respuestas a secuencias de control.

El terminal es stateful, lo que significa que reconstruye adecuadamente caracteres multibyte y mantiene continuidad a través de entradas fragmentadas.

## Enviando datos al terminal {#sending-data-to-the-terminal}

Los datos se envían al terminal utilizando los métodos `write` y `writeln`:

- `write(Object data)`: Envía datos al flujo del terminal.
- `writeln(Object data)`: Envía datos seguidos de un salto de línea.

El terminal procesa todos los datos entrantes como cadenas **UTF-16**. Maneja automáticamente caracteres multibyte, incluso cuando la entrada llega en fragmentos.

### Ejemplo {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Listo.");
```

También puedes adjuntar un callback que se ejecute una vez que el fragmento de datos se haya procesado:

```java
terminal.write("Salida de comando largo", e -> {
  System.out.println("Datos procesados.");
});
```

## Recibiendo entrada del usuario {#receiving-user-input}

El terminal captura la entrada del usuario a través de dos eventos:

- **Evento de datos (`onData`)**: Se activa cuando ocurre una entrada de texto, enviando caracteres Unicode.
- **Evento de tecla (`onKey`)**: Se activa para cada pulsación de tecla, incluyendo información sobre códigos de tecla y modificadores como <kbd>Ctrl</kbd> o <kbd>Alt</kbd>.

Estos eventos se pueden usar para relatar la entrada del usuario a un backend, actualizar elementos de la interfaz de usuario o activar acciones personalizadas.

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

Toda la entrada del usuario capturada por el terminal (como desde eventos `onData`) se emite como cadenas UTF-16.
Si tu backend espera una codificación diferente (como bytes UTF-8), debes transcodificar manualmente los datos.

:::info Codificaciones heredadas
El terminal **no admite codificaciones heredadas** como `ISO-8859`.
Si necesitas compatibilidad con sistemas que no son UTF-8, usa un transcodificador externo (por ejemplo, [`luit`](https://linux.die.net/man/1/luit) o [`iconv`](https://en.wikipedia.org/wiki/Iconv)) para convertir los datos antes de escribirlos o leerlos desde el terminal.
:::

## Manejo de flujos de datos grandes {#handling-large-data-streams}

Debido a que el terminal no puede renderizar instantáneamente una entrada ilimitada, mantiene un buffer de entrada interno. Si este buffer crece demasiado (por defecto alrededor de `50MB`), nuevos datos entrantes pueden ser descartados para proteger el rendimiento del sistema.

Para gestionar adecuadamente fuentes de datos rápidas, debes implementar **control de flujo**.

### Ejemplo básico de control de flujo {#basic-flow-control-example}

Pausa tu backend hasta que el terminal termine de procesar un fragmento:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Ejemplo de control de flujo con marcas de agua {#watermark-flow-control-example}

Para un control más eficiente, usa marcas de agua alta/baja:

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Personalización {#customization}

### Opciones de terminal {#terminal-options}

La clase `TerminalOptions` te permite configurar el comportamiento:

- Parpadeo del cursor.
- Configuración de fuentes (familia, tamaño, peso).
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

### Tema del terminal {#terminal-theme}

Puedes estilizar el terminal usando `TerminalTheme`, que define:

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
path='/webforj/terminalthemepicker'
files={['src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java']}
height='500px'
/>

## Secuencias soportadas {#supported-sequences}

El terminal soporta una amplia gama de secuencias de control estándar utilizadas para el movimiento del cursor, actualizaciones de pantalla y formateo de texto.

Grupos reconocidos:

- **Códigos de control `C0`** (comandos de un solo byte de 7 bits, `\x00`, `\x1F`, como retroceso y avance de línea)
- **Códigos de control `C1`** (comandos de un solo byte de 8 bits, `\x80`, `\x9F`)
- **Secuencias `ESC`** (comenzando con `ESC` (`\x1B`), como guardar/restaurar cursor, alineación de pantalla)
- **Secuencias `CSI`** (Introducción de secuencias de control, `ESC [` o `CSI (\x9B)`, para operaciones como desplazamiento, borrado y estilo)
- **Secuencias `DCS`** (Cadenas de control de dispositivo, `ESC P` o `DCS (\x90)`)
- **Secuencias `OSC`** (Comandos del sistema operativo, `ESC ]` o `OSC (\x9D)`, para establecer títulos de ventana, hipervínculos y colores)

:::info Manejo de secuencias exóticas y personalizadas
Algunos tipos de secuencias exóticas como `APC`, `PM` y `SOS` son reconocidos pero ignorados silenciosamente.
Las secuencias personalizadas pueden ser soportadas a través de integraciones si es necesario.
:::

## Estilo {#styling}

<TableBuilder name="Terminal" />
