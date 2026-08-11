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

Die `Terminal`-Komponente ist ein interaktiver Terminalemulator, der sich wie eine traditionelle Systemkonsole verhält. Sie verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer und eignet sich daher hervorragend zum Erstellen von Remote-Access-Tools, Text-Dashboards, eingebetteten Befehlszeilen oder Debug-Konsole.

<!-- INTRO_END -->

## Erstellung eines Terminals {#creating-a-terminal}

:::info Importieren von Terminal
Um die `Terminal`-Komponente in Ihrer Anwendung zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einfügen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Im folgenden Beispiel wird eine interaktive Befehlszeile mit eingegebenen Befehlen, History-Navigation und benutzerdefinierter Ausgabe erstellt.

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

## Funktionsweise {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichenströme und reagiert auf Benutzeraktionen wie das Tippen oder Auswählen von Text. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbänderungen und das Löschen des Bildschirms.

Die grundlegenden Funktionen umfassen:

- **Daten-Eingabe**: Das Schreiben von Daten ins Terminal aktualisiert den Bildschirm und behandelt sowohl Text- als auch Steuersequenzen.
- **Daten-Ausgabe**: Erfasst Benutzeranschläge und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Hält einen scrollbaren Verlaufs-Puffer und den aktuellen Bildschirmstatus.
- **Cursorverwaltung**: Verfolgt die Cursorposition für die Texteingabe und die Antworten auf Steuersequenzen.

Das Terminal ist zustandsbehaftet, das bedeutet, dass es mehrbyteige Zeichen richtig rekonstruiert und die Kontinuität über fragmentierte Eingaben aufrechterhält.

## Senden von Daten an das Terminal {#sending-data-to-the-terminal}

Daten werden an das Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstrom.
- `writeln(Object data)`: Sendet Daten gefolgt von einer neuen Zeile.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es verarbeitet automatisch mehrbyteige Zeichen, selbst wenn die Eingabe in fragmentierten Abschnitten ankommt.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch einen Callback anhängen, der ausgeführt wird, sobald das Datenchunk verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
  System.out.println("Daten verarbeitet.");
});
```

## Empfang von Benutzereingaben {#receiving-user-input}

Das Terminal erfasst benutzergenerierte Eingaben über zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn eine Texteingabe erfolgt, und sendet Unicode-Zeichen.
- **Tastaturereignis (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen über Tasten-Codes und Modifikatoren wie <kbd>Strg</kbd> oder <kbd>Alt</kbd>.

Diese Ereignisse können verwendet werden, um Benutzereingaben an ein Backend weiterzuleiten, UI-Elemente zu aktualisieren oder benutzerdefinierte Aktionen auszulösen.

### Beispiel {#example-1}
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

Alle von dem Terminal erfassten Benutzereingaben (z. B. von `onData`-Ereignissen) werden als UTF-16-Strings ausgegeben.
Wenn Ihr Backend eine andere Kodierung (wie UTF-8-Bytes) erwartet, müssen Sie die Daten manuell umkodieren.

:::info Legacy Encodings
Das Terminal **unterstützt keine veralteten Kodierungen** wie `ISO-8859`.
Wenn Sie eine Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (z. B. [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://en.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben in oder Lesen aus dem Terminal zu konvertieren.
:::

## Verarbeitung großer Datenströme {#handling-large-data-streams}

Da das Terminal nicht unbegrenzte Eingaben sofort rendern kann, verwaltet es einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen richtig zu verwalten, sollten Sie **Flusssteuerung** implementieren.

### Beispiel für grundlegende Flusssteuerung {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis das Terminal ein Chunk verarbeitet hat:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Beispiel für Flusssteuerung mit Wasserzeichen {#watermark-flow-control-example}

Für eine effizientere Steuerung verwenden Sie Hoch-/Niedrig-Wasserzeichen:

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

## Anpassung {#customization}

### Terminal-Optionen {#terminal-options}

Die Klasse `TerminalOptions` ermöglicht es Ihnen, das Verhalten zu konfigurieren:

- Cursorblinken.
- Schriftarteinstellungen (Familie, Größe, Gewicht).
- Größe des Scrollback-Puffers.
- Zeilenhöhe und Buchstabenabstand.
- Barrierefreiheitseinstellungen (Bildschirmlesemodus).

Beispiel:
```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

### Terminal-Thema {#terminal-theme}

Sie können das Terminal mit `TerminalTheme` gestalten, das definiert:

- Hintergrund- und Schriftfarben.
- Standard `ANSI` Farbpalette.
- Hintergrundfarben für Cursor und Auswahl.

Beispiel:
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

## Unterstützte Sequenzen {#supported-sequences}

Das Terminal unterstützt eine Vielzahl von standardmäßigen Steuersequenzen, die für Cursorbewegungen, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0` Steuercodes** (einzelbyte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rückschritt und Zeilenumbruch)
- **`C1` Steuercodes** (einzelbyte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC` Sequenzen** (beginnend mit `ESC` (`\x1B`), wie Speichern/Wiederherstellen des Cursors, Bildschirmausrichtung)
- **`CSI` Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Styling)
- **`DCS` Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC` Sequenzen** (Operating System Commands, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.
Benutzerdefinierte Sequenzen können bei Bedarf über Integrationen unterstützt werden.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
