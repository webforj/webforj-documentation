---
title: Terminal
sidebar_position: 126
description: >-
  Embed an interactive terminal emulator with the Terminal component for shells,
  dashboards, debug consoles, and remote access tools.
_i18n_hash: 8480d045ce597a8a24d6fd9760a72935
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

El componente `Terminal` es un emulador de terminal interactivo que se comporta como una consola de sistema tradicional. Maneja la salida de texto, la entrada del usuario, las secuencias de control y los búferes de pantalla, lo que lo hace adecuado para construir herramientas de acceso remoto, tableros de texto, shells de comandos integrados o consolas de depuración.

<!-- INTRO_END -->

## Creando una terminal {#creating-a-terminal}

:::info Importando Terminal
Para usar el componente `Terminal` en tu aplicación, asegúrate de incluir la siguiente dependencia en tu pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

El siguiente ejemplo construye un shell de comando interactivo con comandos tipeados, navegación por historial y salida personalizada.

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
  'src/main/resources/static/css/terminal/terminal-view.css',
]}
height='400px'
/>

## Cómo funciona {#how-it-works}

La terminal gestiona una cuadrícula de celdas de texto, procesa flujos de caracteres entrantes y reacciona a acciones del usuario como escribir o seleccionar texto. Interpreta automáticamente los caracteres de control y las secuencias de escape para el movimiento del cursor, cambios de color y limpieza de la pantalla.

Los comportamientos principales incluyen:

- **Entrada de datos**: Escribir datos en la terminal actualiza la pantalla, manejando tanto texto como secuencias de control.
- **Salida de datos**: Captura las pulsaciones de teclas del usuario y las emite como eventos estructurados.
- **Gestión de pantalla**: Mantiene un búfer de historial desplazable y el estado actual de la pantalla.
- **Manejo del cursor**: Rastrea la posición del cursor para la entrada de texto y las respuestas a las secuencias de control.

La terminal es con estado, lo que significa que reconstruye correctamente los caracteres multibyte y mantiene la continuidad a través de entradas fragmentadas.

## Enviando datos a la terminal {#sending-data-to-the-terminal}

Los datos se envían a la terminal usando los métodos `write` y `writeln`:

- `write(Object data)`: Envía datos a la corriente de la terminal.
- `writeln(Object data)`: Envía datos seguidos de un salto de línea.

La terminal procesa todos los datos entrantes como cadenas **UTF-16**. Maneja automáticamente los caracteres multibyte, incluso cuando la entrada llega en trozos fragmentados.

### Ejemplo {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

También puedes adjuntar un callback que se ejecuta una vez que el trozo de datos ha sido procesado:

```java
terminal.write("Long command output", e -> {
  System.out.println("Data processed.");
});
```

## Recibiendo entrada del usuario {#receiving-user-input}

La terminal captura la entrada generada por el usuario a través de dos eventos:

- **Evento de datos (`onData`)**: Se activa cuando se produce entrada de texto, enviando caracteres Unicode.
- **Evento de tecla (`onKey`)**: Se activa por cada pulsación de tecla, incluyendo información sobre códigos de tecla y modificadores como <kbd>Ctrl</kbd> o <kbd>Alt</kbd>.

Estos eventos se pueden usar para transmitir la entrada del usuario a un backend, actualizar elementos de la interfaz de usuario o disparar acciones personalizadas.

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

Toda la entrada del usuario capturada por la terminal (como de eventos `onData`) se emite como cadenas UTF-16.  
Si tu backend espera una codificación diferente (como bytes UTF-8), debes transcodificar manualmente los datos.

:::info Codificaciones Legadas
La terminal **no soporta codificaciones legadas** como `ISO-8859`.  
Si necesitas compatibilidad con sistemas que no sean UTF-8, usa un transcodificador externo (por ejemplo, [`luit`](https://linux.die.net/man/1/luit) o [`iconv`](https://es.wikipedia.org/wiki/Iconv)) para convertir los datos antes de escribirlos o leerlos desde la terminal.
:::

## Manejo de flujos de datos grandes {#handling-large-data-streams}

Dado que la terminal no puede renderizar instantáneamente una entrada ilimitada, mantiene un búfer de entrada interno. Si este búfer crece demasiado (por defecto alrededor de `50MB`), nuevos datos entrantes pueden ser descartados para proteger el rendimiento del sistema.

Para gestionar adecuadamente fuentes de datos rápidas, deberías implementar **control de flujo**.

### Ejemplo básico de control de flujo {#basic-flow-control-example}

Pausa tu backend hasta que la terminal termine de procesar un trozo:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Ejemplo de control de flujo con marca de agua {#watermark-flow-control-example}

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Personalización {#customization}

### Opciones de la terminal {#terminal-options}

La clase `TerminalOptions` te permite configurar el comportamiento:

- Parpadeo del cursor.
- Configuración de fuente (familia, tamaño, peso).
- Tamaño del búfer de retroceso.
- Altura de línea y espaciado de letras.
- Configuraciones de accesibilidad (modo de lector de pantalla).

Ejemplo:
```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

### Tema de la terminal {#terminal-theme}

Puedes estilizar la terminal utilizando `TerminalTheme`, que define:

- Colores de fondo y primer plano.
- Paleta de colores `ANSI` estándar.
- Colores de fondo del cursor y de selección.

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

La terminal soporta una amplia gama de secuencias de control estándar utilizadas para el movimiento del cursor, actualizaciones de pantalla y formateo de texto.

Grupos reconocidos:

- **Códigos de control `C0`** (comandos de 7 bits de un solo byte, `\x00`, `\x1F`, como retroceso y avance de línea)
- **Códigos de control `C1`** (comandos de 8 bits de un solo byte, `\x80`, `\x9F`)
- **Secuencias `ESC`** (comenzando con `ESC` (`\x1B`), como guardar/restaurar cursor, alineación de pantalla)
- **Secuencias `CSI`** (Introducer Secuencia de Control, `ESC [` o `CSI (\x9B)`, para operaciones como desplazamiento, borrado y estilo)
- **Secuencias `DCS`** (Cadenas de Control de Dispositivo, `ESC P` o `DCS (\x90)`)
- **Secuencias `OSC`** (Comandos del Sistema Operativo, `ESC ]` o `OSC (\x9D)`, para establecer título de ventana, hipervínculos y colores)

:::info Manejo de Secuencias Exóticas y Personalizadas
Algunos tipos de secuencias exóticas como `APC`,`PM` y `SOS` son reconocidos pero se ignoran silenciosamente.  
Las secuencias personalizadas pueden ser soportadas a través de integraciones si es necesario.
:::

## Estilizando {#styling}

<TableBuilder name="Terminal" />
