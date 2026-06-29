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

Die `Terminal`-Komponente ist ein interaktiver Terminal-Emulator, der sich wie eine traditionelle Systemkonsole verhält. Sie verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer, was sie für den Aufbau von Fernzugriffs-Tools, Text-Dashboards, eingebetteten Kommandozeilen oder Debug-Konsolen geeignet macht.

<!-- INTRO_END -->

## Erstellen eines Terminals {#creating-a-terminal}

:::info Importieren des Terminals
Um die `Terminal`-Komponente in Ihrer App zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihre pom.xml aufnehmen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Das folgende Beispiel erstellt eine interaktive Eingabeaufforderung mit eingegebenen Befehlen, Verlaufnavigation und benutzerdefinierter Ausgabe.

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

## Wie es funktioniert {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichendatenströme und reagiert auf Benutzeraktionen wie Tippen oder Auswahl von Text. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbänderungen und Bildschirmlöschen.

Die Kernfunktionen umfassen:

- **Dateninput**: Das Schreiben von Daten in das Terminal aktualisiert den Bildschirm und verarbeitet sowohl Text als auch Steuersequenzen.
- **Datenoutput**: Erfasst die Tastenanschläge des Benutzers und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Hält einen scrollbar gespeicherten Verlaufspuffer und den aktuellen Bildschirmstatus.
- **Cursormanagement**: Verfolgt die Cursorposition für die Texteingabe und die Reaktionen auf Steuersequenzen.

Das Terminal ist zustandsbehaftet, was bedeutet, dass es mehrbyteige Zeichen korrekt rekonstruiert und die Kontinuität über fragmentierte Eingaben aufrechterhält.

## Daten an das Terminal senden {#sending-data-to-the-terminal}

Daten werden an das Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstream.
- `writeln(Object data)`: Sendet Daten gefolgt von einem Zeilenumbruch.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es verarbeitet automatisch mehrbyteige Zeichen, selbst wenn die Eingabe in fragmentierten Chunks ankommt.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch eine Callback-Funktion anfügen, die ausgeführt wird, nachdem der Datenchunk verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
  System.out.println("Daten verarbeitet.");
});
```

## Benutzereingaben empfangen {#receiving-user-input}

Das Terminal erfasst benutzergenerierte Eingaben über zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Texteingaben erfolgen, wobei Unicode-Zeichen gesendet werden.
- **Tastenevent (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen über Tasten-Codes und Modifikatoren wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

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
Wenn Ihr Backend eine andere Codierung erwartet (wie UTF-8-Bytes), müssen Sie die Daten manuell transkodieren.

:::info Veraltete Codierungen
Das Terminal **unterstützt keine veralteten Codierungen** wie `ISO-8859`.  
Wenn Sie Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (zum Beispiel [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://en.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben in oder Lesen vom Terminal zu konvertieren.
:::

## Handhabung großer Datenströme {#handling-large-data-streams}

Da das Terminal nicht sofort unbegrenzte Eingaben rendern kann, unterhält es einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), kann neue eingehende Daten abgeworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen richtig zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Einfaches Flusssteuerungsbeispiel {#basic-flow-control-example}

Halten Sie Ihr Backend an, bis das Terminal einen Chunk verarbeitet hat:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Wasserstandsteuerungsbeispiel {#watermark-flow-control-example}

Für eine effizientere Kontrolle verwenden Sie Hoch-/Tiefwasserzeichen:

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

### Terminaloptionen {#terminal-options}

Die Klasse `TerminalOptions` ermöglicht es Ihnen, das Verhalten zu konfigurieren:

- Cursorblinken.
- Schriftarteinstellungen (Familie, Größe, Gewicht).
- Größe des Rücklaufpuffers.
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

### Terminaldesign {#terminal-theme}

Sie können das Terminal mit `TerminalTheme` stylen, das definiert:

- Hintergrund- und Vordergrundfarben.
- Standard `ANSI`-Farbschema.
- Cursor- und Auswahlhintergrundfarben.

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

- **`C0`-Steuercodes** (einbyteige 7-Bit-Befehle, `\x00`, `\x1F`, wie Backspace und Zeilenumbruch)
- **`C1`-Steuercodes** (einbyteige 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC`-Sequenzen** (beginnen mit `ESC` (`\x1B`), wie Cursor speichern/wiederherstellen, Bildschirmausrichtung)
- **`CSI`-Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Formatierung)
- **`DCS`-Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC`-Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen von Fenstertiteln, Hyperlinks und Farben)

:::info Handhabung exotischer und benutzerdefinierter Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert. 
Benutzerdefinierte Sequenzen können bei Bedarf durch Integrationen unterstützt werden.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
