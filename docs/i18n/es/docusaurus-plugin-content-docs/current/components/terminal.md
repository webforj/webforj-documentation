---
title: Terminal
sidebar_position: 126
_i18n_hash: d25dd721593cf2850f9b8c1b7dd742ee
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

El componente `Terminal` es un emulador de terminal interactivo que se comporta como una consola de sistema tradicional. Maneja la salida de texto, la entrada del usuario, las secuencias de control y los búferes de pantalla, lo que lo hace adecuado para construir herramientas de acceso remoto, tableros de texto, shells de comandos embebidos o consolas de depuración.

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

El siguiente ejemplo construye un shell de comandos interactivo con comandos tipeados, navegación por el historial y salida personalizada.

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
cssURL='/css/terminal/terminal-view.css'
height='400px'
/>

## Cómo funciona {#how-it-works}

El terminal gestiona una cuadrícula de celdas de texto, procesa flujos de caracteres entrantes y reacciona a acciones del usuario como tipear o seleccionar texto. Interpreta automáticamente caracteres de control y secuencias de escape para movimiento de cursor, cambios de color y limpieza de pantalla.

Los comportamientos centrales incluyen:

- **Entrada de datos**: Escribir datos en el terminal actualiza la pantalla, manejando tanto texto como secuencias de control.
- **Salida de datos**: Captura las pulsaciones de teclas del usuario y las emite como eventos estructurados.
- **Gestión de pantalla**: Mantiene un búfer de historial desplazable y el estado actual de la pantalla.
- **Manejo de cursor**: Rastrea la posición del cursor para la entrada de texto y las respuestas a las secuencias de control.

El terminal es con estado, lo que significa que reconstruye adecuadamente los caracteres multibyte y mantiene continuidad a través de entradas fragmentadas.

## Enviando datos al terminal {#sending-data-to-the-terminal}

Los datos se envían al terminal utilizando los métodos `write` y `writeln`:

- `write(Object data)`: Envía datos al flujo del terminal.
- `writeln(Object data)`: Envía datos seguidos por un salto de línea.

El terminal procesa todos los datos entrantes como cadenas **UTF-16**. Maneja automáticamente caracteres multibyte, incluso cuando la entrada llega en fragmentos.

### Ejemplo {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

También puedes adjuntar un callback que se ejecute una vez que el fragmento de datos ha sido procesado:

```java
terminal.write("Long command output", e -> {
  System.out.println("Data processed.");
});
```

## Recibiendo entrada del usuario {#receiving-user-input}

El terminal captura la entrada generada por el usuario a través de dos eventos:

- **Evento de datos (`onData`)**: Se activa cuando ocurre una entrada de texto, enviando caracteres Unicode.
- **Evento de tecla (`onKey`)**: Se activa por cada pulsación de tecla, incluyendo información sobre códigos de teclas y modificadores como <kbd>Ctrl</kbd> o <kbd>Alt</kbd>.

Estos eventos se pueden utilizar para enviar la entrada del usuario a un backend, actualizar elementos de la interfaz de usuario o desencadenar acciones personalizadas.

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

Toda la entrada del usuario capturada por el terminal (como de eventos `onData`) se emite como cadenas UTF-16.  
Si tu backend espera una codificación diferente (como bytes UTF-8), debes transcodificar los datos manualmente.

:::info Codificaciones Legadas
El terminal **no soporta codificaciones legadas** como `ISO-8859`.  
Si necesitas compatibilidad con sistemas que no son UTF-8, utiliza un transcodificador externo (por ejemplo, [`luit`](https://linux.die.net/man/1/luit) o [`iconv`](https://en.wikipedia.org/wiki/Iconv)) para convertir los datos antes de escribirlos en o leerlos desde el terminal.
:::

## Manejo de grandes flujos de datos {#handling-large-data-streams}

Debido a que el terminal no puede renderizar instantáneamente entradas ilimitadas, mantiene un búfer de entrada interno. Si este búfer crece demasiado (alrededor de `50MB` por defecto), se puede descartar nueva información entrante para proteger el rendimiento del sistema.

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

### Ejemplo de control de flujo de marca de agua {#watermark-flow-control-example}

Para un control más eficiente, utiliza marcas de agua alta/baja:

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

### Opciones del terminal {#terminal-options}

La clase `TerminalOptions` te permite configurar comportamientos:

- Parpadeo del cursor.
- Ajustes de fuente (familia, tamaño, peso).
- Tamaño del búfer de retroceso.
- Altura de línea y espaciado de letras.
- Ajustes de accesibilidad (modo de lector de pantalla).

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

Puedes estilizar el terminal utilizando `TerminalTheme`, que define:

- Colores de fondo y primer plano.
- Paleta de colores `ANSI` estándar.
- Colores de fondo del cursor y selección.

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

## Secuencias soportadas {#supported-sequences}

El terminal soporta una amplia gama de secuencias de control estándar utilizadas para movimiento del cursor, actualizaciones de pantalla y formato de texto.

Grupos reconocidos:

- **Códigos de control `C0`** (comandos de un solo byte de 7 bits, `\x00`, `\x1F`, como retroceso y avance de línea)
- **Códigos de control `C1`** (comandos de un solo byte de 8 bits, `\x80`, `\x9F`)
- **Secuencias `ESC`** (que comienzan con `ESC` (`\x1B`), como guardar/restaurar cursor, alineación de pantalla)
- **Secuencias `CSI`** (Introductor de Secuencia de Control, `ESC [` o `CSI (\x9B)`, para operaciones como desplazamiento, borrado y estilo)
- **Secuencias `DCS`** (Cadenas de Control de Dispositivo, `ESC P` o `DCS (\x90)`)
- **Secuencias `OSC`** (Comandos del Sistema Operativo, `ESC ]` o `OSC (\x9D)`, para establecer título de ventana, hipervínculos y colores)

:::info Manejo de Secuencias Exóticas y Personalizadas
Algunas secuencias de tipo exótico como `APC`, `PM` y `SOS` son reconocidas pero se ignoran silenciosamente.  
Las secuencias personalizadas pueden ser soportadas a través de integraciones si es necesario.
:::

## Estilo {#styling}

<TableBuilder name="Terminal" />
