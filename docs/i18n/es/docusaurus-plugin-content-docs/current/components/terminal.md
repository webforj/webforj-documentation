---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

El componente `Terminal` proporciona un emulador de terminal interactivo que se comporta de manera similar a una consola de sistema tradicional. Permite a las aplicaciones mostrar y manipular una interfaz basada en texto, manejando la salida de texto, recibiendo la entrada del usuario, interpretando secuencias de control y manteniendo búferes de pantalla.

Este terminal está diseñado para ofrecer un comportamiento confiable en una variedad de casos de uso, como la construcción de herramientas de acceso remoto, paneles de control de texto, shells de comando integrados o consolas de depuración interactivas.

:::info Importando Terminal
Para usar el componente `Terminal` en tu aplicación, asegúrate de incluir la siguiente dependencia en tu pom.xml.

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

El terminal gestiona una cuadrícula de celdas de texto, procesa flujos de caracteres entrantes y reacciona a las acciones del usuario, como escribir o seleccionar texto. Interpreta automáticamente los caracteres de control y las secuencias de escape para el movimiento del cursor, cambios de color y limpieza de pantalla.

Los comportamientos principales incluyen:

- **Entrada de datos**: Escribir datos en el terminal actualiza la pantalla, manejando tanto texto como secuencias de control.
- **Salida de datos**: Captura las pulsaciones de teclas del usuario y las emite como eventos estructurados.
- **Gestión de pantalla**: Mantiene un búfer de historial desplazable y el estado actual de la pantalla.
- **Manejo del cursor**: Rastrea la posición del cursor para la entrada de texto y respuestas a secuencias de control.

El terminal es con estado, lo que significa que reconstruye correctamente caracteres multibyte y mantiene la continuidad a través de entradas fragmentadas.

## Enviando datos al terminal {#sending-data-to-the-terminal}

Los datos se envían al terminal utilizando los métodos `write` y `writeln`:

- `write(Object data)`: Envía datos al flujo del terminal.
- `writeln(Object data)`: Envía datos seguidos de una nueva línea.

El terminal procesa todos los datos entrantes como cadenas **UTF-16**. Maneja automáticamente caracteres multibyte, incluso cuando la entrada llega en trozos fragmentados.

### Ejemplo {#example}
```java
terminal.write("echo Hola Mundo\n");
terminal.writeln("Listo.");
```

También puedes adjuntar un callback que se ejecuta una vez que se ha procesado el bloque de datos:

```java
terminal.write("Salida de comando larga", e -> {
    System.out.println("Datos procesados.");
});
```

## Recibiendo entrada del usuario {#receiving-user-input}

El terminal captura la entrada generada por el usuario a través de dos eventos:

- **Evento de datos (`onData`)**: Se activa cuando ocurre la entrada de texto, enviando caracteres Unicode.
- **Evento de tecla (`onKey`)**: Se activa por cada pulsación de tecla, incluyendo información sobre códigos de tecla y modificadores como <kbd>Ctrl</kbd> o <kbd>Alt</kbd>.

Estos eventos se pueden usar para transmitir la entrada del usuario a un backend, actualizar elementos de la interfaz de usuario o activar acciones personalizadas.

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
El terminal **no soporta codificaciones heredadas** como `ISO-8859`.  
Si necesitas compatibilidad con sistemas que no son UTF-8, utiliza un transcodificador externo (por ejemplo, [`luit`](https://linux.die.net/man/1/luit) o [`iconv`](https://en.wikipedia.org/wiki/Iconv)) para convertir los datos antes de escribirlos o leerlos desde el terminal.
:::

## Manejando grandes flujos de datos {#handling-large-data-streams}

Debido a que el terminal no puede renderizar instantáneamente una entrada ilimitada, mantiene un búfer de entrada interno. Si este búfer crece demasiado (por defecto alrededor de `50MB`), los nuevos datos entrantes pueden ser descartados para proteger el rendimiento del sistema.

Para gestionar adecuadamente fuentes de datos rápidas, deberías implementar **control de flujo**.

### Ejemplo básico de control de flujo {#basic-flow-control-example}

Pausa tu backend hasta que el terminal termine de procesar un bloque:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Ejemplo de control de flujo con marcas de agua {#watermark-flow-control-example}

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

La clase `TerminalOptions` te permite configurar el comportamiento:

- Parpadeo del cursor.
- Configuraciones de fuente (familia, tamaño, grosor).
- Tamaño del búfer de retroceso.
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

El terminal soporta una amplia gama de secuencias de control estándar utilizadas para el movimiento del cursor, actualizaciones de pantalla y formateo de texto.

Grupos reconocidos:

- **códigos de control `C0`** (comandos de un solo byte 7 bits, `\x00`, `\x1F`, como retroceso y avance de línea)
- **códigos de control `C1`** (comandos de un solo byte 8 bits, `\x80`, `\x9F`)
- **secuencias `ESC`** (comenzando con `ESC` (`\x1B`), como guardar/restaurar cursor, alineación de pantalla)
- **secuencias `CSI`** (Introducción de Secuencia de Control, `ESC [` o `CSI (\x9B)`, para operaciones como desplazamiento, borrado y estilo)
- **secuencias `DCS`** (Cadenas de Control de Dispositivo, `ESC P` o `DCS (\x90)`)
- **secuencias `OSC`** (Comandos del Sistema Operativo, `ESC ]` o `OSC (\x9D)`, para establecer título de ventana, hiperenlaces y colores)

:::info Manejo de Secuencias Exóticas y Personalizadas
Algunos tipos de secuencias exóticas como `APC`, `PM` y `SOS` son reconocidos pero ignorados silenciosamente.  
Las secuencias personalizadas se pueden soportar a través de integraciones si es necesario.
:::

## Estilizando {#styling}

<TableBuilder name="Terminal" />
