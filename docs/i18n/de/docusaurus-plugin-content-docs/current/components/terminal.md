---
title: Terminal
sidebar_position: 126
_i18n_hash: d25dd721593cf2850f9b8c1b7dd742ee
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Die `Terminal`-Komponente ist ein interaktiver Terminalemulator, der wie eine traditionelle Systemkonsole funktioniert. Sie verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer, was sie geeignet macht, um Remote-Access-Tools, Text-Dashboards, eingebettete Befehlsaufforderungen oder Debug-Konsole zu erstellen.

<!-- INTRO_END -->

## Erstellen eines Terminals {#creating-a-terminal}

:::info Importieren von Terminal
Um die `Terminal`-Komponente in Ihrer Anwendung zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einfügen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Das folgende Beispiel erstellt eine interaktive Befehlszeile mit eingegebenen Befehlen, Navigationsverlauf und benutzerdefinierten Ausgaben.

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

## Wie es funktioniert {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichendatenströme und reagiert auf Benutzeraktionen wie Tippen oder Textauswahl. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für Cursorbewegungen, Farbänderungen und Bildschirmlöschen.

Die grundlegenden Funktionen umfassen:

- **Daten Eingabe**: Das Schreiben von Daten in das Terminal aktualisiert den Bildschirm und verarbeitet sowohl Text- als auch Steuersequenzen.
- **Daten Ausgabe**: Erfasst Benutzereingaben und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Beinhaltet einen scrollbaren Verlaufsbuffer und den aktuellen Bildschirmstatus.
- **Cursorverarbeitung**: Verfolgt die Cursorposition für Texteingaben und Antworten auf Steuersequenzen.

Das Terminal ist zustandsorientiert, was bedeutet, dass es mehrbyte Zeichen korrekt rekonstruiert und eine Kontinuität über fragmentierte Eingaben hinweg aufrechterhält.

## Daten an das Terminal senden {#sending-data-to-the-terminal}

Daten werden an das Terminal über die Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstrom.
- `writeln(Object data)`: Sendet Daten gefolgt von einer neuen Zeile.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es verarbeitet automatisch mehrbyte Zeichen, auch wenn die Eingabe in fragmentierten Stücken eintrifft.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch einen Callback anhängen, der ausgeführt wird, sobald das Datenstück verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
  System.out.println("Daten verarbeitet.");
});
```

## Empfang von Benutzereingaben {#receiving-user-input}

Das Terminal erfasst die vom Benutzer erzeugte Eingabe über zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Texteingabe erfolgt, und sendet Unicode-Zeichen.
- **Tastenevent (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen zu Tastencodes und Modifikatoren wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

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
Wenn Ihr Backend eine andere Codierung erwartet (wie UTF-8-Bytes), müssen Sie die Daten manuell umkodieren.

:::info Alte Codierungen
Das Terminal **unterstützt keine alten Codierungen** wie `ISO-8859`.  
Wenn Sie Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (z. B. [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://de.wikipedia.org/wiki/Iconv)), um die Daten zu konvertieren, bevor Sie sie ins Terminal schreiben oder vom Terminal lesen.
:::

## Umgang mit großen Datenströmen {#handling-large-data-streams}

Da das Terminal nicht sofort unbegrenzte Eingaben rendern kann, verwaltet es einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen angemessen zu verwalten, sollten Sie eine **Flusskontrolle** implementieren.

### Einfaches Flusskontrollbeispiel {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis das Terminal einen Block verarbeitet hat:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Wasserstands-Flusskontrollbeispiel {#watermark-flow-control-example}

Für effizientere Steuerung verwenden Sie hohe/niedrige Wasserstände:

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

### Terminal Optionen {#terminal-options}

Die Klasse `TerminalOptions` ermöglicht es Ihnen, das Verhalten zu konfigurieren:

- Cursorblinken.
- Schriftarteinstellungen (Familie, Größe, Gewicht).
- Größe des Rückwärtsbuffers.
- Zeilenhöhe und Buchstabenabstand.
- Barrierefreiheitsoptionen (Bildschirmlesemodus).

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

Sie können das Terminal mithilfe von `TerminalTheme` gestalten, das definiert:

- Hintergrund- und Vordergrundfarben.
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
path='/webforj/terminalthemepicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java'
height='500px'
/>

## Unterstützte Sequenzen {#supported-sequences}

Das Terminal unterstützt eine breite Palette standardisierter Steuersequenzen, die für Cursorbewegungen, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0` Steuercodes** (einzelbyte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rückschritt und Zeilenumbruch)
- **`C1` Steuercodes** (einzelbyte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC`-Sequenzen** (beginnend mit `ESC` (`\x1B`), wie Cursor speichern/wiederherstellen, Bildschirmausrichtung)
- **`CSI`-Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Stil)
- **`DCS`-Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC`-Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können bei Bedarf über Integrationen unterstützt werden.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
