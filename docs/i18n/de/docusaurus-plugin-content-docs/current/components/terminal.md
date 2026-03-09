---
title: Terminal
sidebar_position: 126
_i18n_hash: 72b2270f024a64687440deef9c6d82c4
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Der `Terminal`-Komponente ist ein interaktiver Terminal-Emulator, der sich wie eine traditionelle Systemkonsole verhält. Er verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer, was ihn geeignet macht zum Erstellen von Remote-Access-Tools, Text-Dashboards, eingebetteten Befehlsshells oder Debug-Konsolen.

<!-- INTRO_END -->

## Erstellung eines Terminals {#creating-a-terminal}

:::info Importieren des Terminals
Um die `Terminal`-Komponente in Ihrer App zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einschließen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Das folgende Beispiel erstellt eine interaktive Befehlszeile mit eingegebenen Befehlen, Verlaufsnavigation und benutzerdefinierter Ausgabe.

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

## Funktionsweise {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichenströme und reagiert auf Benutzeraktionen wie Tippen oder Auswählen von Text. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbwechsel und Bildschirmlöschung.

Die wichtigsten Verhaltensweisen umfassen:

- **Dateninput**: Das Schreiben von Daten ins Terminal aktualisiert den Bildschirm und verarbeitet sowohl Text als auch Steuersequenzen.
- **Datenausgabe**: Erfasst Benutzertasteneingaben und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Hält einen scrollbaren Verlaufsbuffer und den aktuellen Bildschirmstatus.
- **Cursorverwaltung**: Verfolgt die Cursorposition für Texteingaben und Antworten auf Steuersequenzen.

Das Terminal ist zustandsbehaftet, was bedeutet, dass es mehrbyte-Zeichen ordnungsgemäß rekonstruiert und die Kontinuität über fragmentierte Eingaben aufrechterhält.

## Daten an das Terminal senden {#sending-data-to-the-terminal}

Daten werden an das Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstream.
- `writeln(Object data)`: Sendet Daten gefolgt von einer neuen Zeile.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es behandelt automatisch mehrbyte-Zeichen, selbst wenn die Eingabe in fragmentierten Chunks ankommt.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch einen Callback anhängen, der ausgeführt wird, sobald der Chunk an Daten verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
    System.out.println("Daten verarbeitet.");
});
```

## Empfang von Benutzereingaben {#receiving-user-input}

Das Terminal erfasst benutzergenerierte Eingaben durch zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Texteingaben erfolgen, und sendet Unicode-Zeichen.
- **Tastenevent (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen zu Tasten-Codes und Modifizierern wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

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

Alle vom Terminal erfassten Benutzereingaben (wie von `onData`-Ereignissen) werden als UTF-16-Strings ausgegeben.  
Wenn Ihr Backend eine andere Kodierung (wie UTF-8-Bytes) erwartet, müssen Sie die Daten manuell transkodieren.

:::info Veraltete Kodierungen
Das Terminal **unterstützt keine veralteten Kodierungen** wie `ISO-8859`.  
Wenn Sie Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (zum Beispiel [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://de.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben in oder Lesen aus dem Terminal zu konvertieren.
:::

## Umgang mit großen Datenströmen {#handling-large-data-streams}

Da das Terminal nicht sofort unbegrenzte Eingaben rendern kann, verwaltet es einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50 MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen ordnungsgemäß zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Beispiel für grundlegende Flusskontrolle {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis das Terminal einen Chunk bearbeitet hat:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Beispiel für Wasserstand-Flusskontrolle {#watermark-flow-control-example}

Für effektivere Kontrolle verwenden Sie Hoch-/Tiefwasserzeichen:

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

## Anpassung {#customization}

### Terminaloptionen {#terminal-options}

Die Klasse `TerminalOptions` ermöglicht es Ihnen, das Verhalten zu konfigurieren:

- Cursorblinken.
- Schriftarteneinstellungen (Familie, Größe, Gewicht).
- Scrollback-Puffergröße.
- Zeilenhöhe und Buchstabenabstand.
- Barrierefreiheitseinstellungen (Screenreader-Modus).

Beispiel:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminaldesign {#terminal-theme}

Sie können das Terminal mit `TerminalTheme` gestalten, das definiert:

- Hintergrund- und Vordergrundfarben.
- Standardes `ANSI`-Farbschema.
- Hintergrundfarben für Cursor und Auswahl.

Beispiel:
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

## Unterstützte Sequenzen {#supported-sequences}

Das Terminal unterstützt eine breite Palette von Standardsteuersequenzen, die für die Cursorbewegung, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0` Steuerzeichen** (einzelbyte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rückschritt und Zeilenumbruch)
- **`C1` Steuerzeichen** (einzelbyte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC` Sequenzen** (beginnen mit `ESC` (`\x1B`), wie Speichern/Wiederherstellen des Cursors, Bildschirmausrichtung)
- **`CSI` Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Styling)
- **`DCS` Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC` Sequenzen** (Operating System Commands, `ESC ]` oder `OSC (\x9D)`, zum Setzen von Fenstertiteln, Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können durch Integrationen unterstützt werden, wenn dies erforderlich ist.
:::

## Stil {#styling}

<TableBuilder name="Terminal" />
