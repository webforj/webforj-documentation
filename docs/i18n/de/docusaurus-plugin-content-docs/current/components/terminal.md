---
title: Terminal
sidebar_position: 126
_i18n_hash: a4c442c62c748f82d75133db075054af
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Die `Terminal`-Komponente ist ein interaktiver Terminalemulator, der sich wie eine traditionelle Systemkonsole verhält. Sie verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer, was sie geeignet macht für den Bau von Fernzugriffstools, Text-Dashboards, eingebetteten Kommandozeilen oder Debug-Konsolen.

<!-- INTRO_END -->

## Erstellung eines Terminals {#creating-a-terminal}

:::info Importieren von Terminal
Um die `Terminal`-Komponente in Ihrer App zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml aufnehmen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Im folgenden Beispiel wird eine interaktive Befehlszeile mit eingegebenen Befehlen, Verlaufsnavigation und benutzerdefinierter Ausgabe erstellt.

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

## Wie es funktioniert {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichenströme und reagiert auf Benutzeraktionen wie Tippen oder Textauswahl. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbänderungen und Bildschirmlöschen.

Die Kernverhalten umfassen:

- **Dateninput**: Das Schreiben von Daten an das Terminal aktualisiert den Bildschirm und verarbeitet sowohl Text als auch Steuersequenzen.
- **Datenoutput**: Erfasst Benutzertasteneingaben und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Pflegt einen scrollbaren Verlaufsbuffer und den aktuellen Bildschirmstatus.
- **Cursorverwaltung**: Verfolgt die Cursorposition für Texteingaben und Reaktionen auf Steuersequenzen.

Das Terminal ist zustandsbehaftet, was bedeutet, dass es mehrbyte Zeichen ordnungsgemäß rekonstruiert und die Kontinuität über fragmentierte Eingaben hinweg aufrechterhält.

## Daten an das Terminal senden {#sending-data-to-the-terminal}

Daten werden an das Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstream.
- `writeln(Object data)`: Sendet Daten gefolgt von einem Zeilenumbruch.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es verarbeitet automatisch mehrbyte Zeichen, selbst wenn die Eingabe in fragmentierten Chunks ankommt.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch eine Rückruffunktion anhängen, die ausgeführt wird, sobald der Datenchunk verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
  System.out.println("Daten verarbeitet.");
});
```

## Benutzerinput empfangen {#receiving-user-input}

Das Terminal erfasst benutzergenerierte Eingaben über zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Text eingegeben wird, und sendet Unicode-Zeichen.
- **Tastaturereignis (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen über Tasten- und Modifikatorkodierungen wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

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

Alle Benutzereingaben, die vom Terminal erfasst werden (zum Beispiel von `onData`-Ereignissen), werden als UTF-16-Strings ausgegeben.  
Wenn Ihr Backend eine andere Kodierung erwartet (z. B. UTF-8-Bytes), müssen Sie die Daten manuell transkodieren.

:::info Legacy Encodings
Das Terminal **unterstützt keine klassischen Kodierungen** wie `ISO-8859`.  
Wenn Sie die Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (zum Beispiel [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://de.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben in oder Lesen aus dem Terminal zu konvertieren.
:::

## Umgang mit großen Datenströmen {#handling-large-data-streams}

Da das Terminal nicht sofort unbegrenzte Eingaben rendern kann, wird ein interner Eingabepuffer aufrechterhalten. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen ordnungsgemäß zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Basis-Flusskontrollbeispiel {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis das Terminal hat, einen Chunk verarbeitet:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Wasserstand-Flusskontrollbeispiel {#watermark-flow-control-example}

Zur effizienteren Kontrolle verwenden Sie Hoch-/Niedrigwasserstände:

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

Die `TerminalOptions`-Klasse ermöglicht es Ihnen, das Verhalten zu konfigurieren:

- Cursorblinken.
- Schriftarteinstellungen (Familie, Größe, Gewicht).
- Größe des Scrollback-Puffers.
- Zeilenhöhe und Buchstabenabstand.
- Barrierefreiheitseinstellungen (Vorlesemodus).

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
- Standardfarbenpalette `ANSI`.
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

Das Terminal unterstützt eine Vielzahl von standardmäßigen Steuersequenzen, die für die Cursorbewegung, Bildschirmaktualisierungen und Textformatierung verwendet werden.

Erkannte Gruppen:

- **`C0` Steuerzeichen** (einzelbyte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rücktaste und Zeilenumbruch)
- **`C1` Steuerzeichen** (einzelbyte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC` Sequenzen** (beginnt mit `ESC` (`\x1B`), wie Cursor speichern/wiederherstellen, Bildschirmausrichtung)
- **`CSI` Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Styling)
- **`DCS` Sequenzen** (Gerätesteuerzeichen, `ESC P` oder `DCS (\x90)`)
- **`OSC` Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können über Integrationen unterstützt werden, wenn erforderlich.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
