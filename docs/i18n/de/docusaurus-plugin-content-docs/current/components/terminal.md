---
title: Terminal
sidebar_position: 126
_i18n_hash: 4b7abaef79b0511bbbb796171ddd8c07
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Die `Terminal`-Komponente bietet einen interaktiven Terminalemulator, der sich ähnlich wie eine traditionelle Systemkonsole verhält. Sie ermöglicht es Anwendungen, eine textbasierte Schnittstelle anzuzeigen und zu manipulieren, handhabt die Textausgabe, empfängt Benutzereingaben, interpretiert Steuersequenzen und verwaltet Bildschirmspeicher.

Dieser Terminal ist darauf ausgelegt, zuverlässiges Verhalten über eine Vielzahl von Anwendungsfällen hinweg zu liefern, wie beim Erstellen von Fernzugriffs-Tools, Text-Dashboards, eingebetteten Befehlszeilen oder interaktiven Debugging-Konsolen.

:::info Importieren des Terminals
Um die `Terminal`-Komponente in Ihrer Anwendung zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihre pom.xml aufnehmen.

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

## Wie es funktioniert {#how-it-works}

Der Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichendatenströme und reagiert auf Benutzeraktionen wie Tippen oder Textauswahl. Er interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbänderungen und das Leeren des Bildschirms.

Die grundlegenden Verhaltensweisen sind:

- **Daten-Eingabe**: Das Schreiben von Daten in das Terminal aktualisiert den Bildschirm und bearbeitet sowohl Text- als auch Steuersequenzen.
- **Daten-Ausgabe**: Erfassen von Benutzereingaben und Ausgeben dieser als strukturierte Ereignisse.
- **Bildschirmverwaltung**: Verwaltet einen scrollbaren Verlaufs-Puffer und den aktuellen Bildschirmzustand.
- **Cursor-Verwaltung**: Verfolgt die Cursorposition für die Texteingabe und Antworten auf Steuersequenzen.

Der Terminal ist zustandsbehaftet, das heißt, er stellt multibyte Zeichen korrekt wieder her und erhält die Kontinuität über fragmentierte Eingaben.

## Daten an das Terminal senden {#sending-data-to-the-terminal}

Daten werden an das Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstrom.
- `writeln(Object data)`: Sendet Daten, gefolgt von einer neuen Zeile.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es verarbeitet automatisch multibyte Zeichen, selbst wenn Eingaben in fragmentierten Stücken ankommen.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch einen Callback anhängen, der ausgeführt wird, sobald das Datenstück verarbeitet wurde:

```java
terminal.write("Längere Befehlsausgabe", e -> {
    System.out.println("Daten verarbeitet.");
});
```

## Empfang von Benutzereingaben {#receiving-user-input}

Der Terminal erfasst benutzergenerierte Eingaben durch zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Texteingaben erfolgen, und sendet Unicode-Zeichen.
- **Tastenevent (`onKey`)**: Wird für jeden Tastendruck ausgelöst, einschließlich Informationen über Tastencodes und Modifikatoren wie <kbd>Strg</kbd> oder <kbd>Alt</kbd>.

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

Alle Benutzereingaben, die vom Terminal erfasst werden (z.B. von `onData`-Ereignissen), werden als UTF-16-Strings ausgegeben.  
Wenn Ihr Backend eine andere Kodierung (wie UTF-8-Bytes) erwartet, müssen Sie die Daten manuell umkodieren.

:::info Veraltete Kodierungen
Der Terminal **unterstützt keine veralteten Kodierungen** wie `ISO-8859`.  
Wenn Sie Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (z.B. [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://de.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben oder Lesen im Terminal zu konvertieren.
:::

## Verarbeitung großer Datenströme {#handling-large-data-streams}

Da der Terminal nicht unbegrenzte Eingaben sofort rendern kann, hält er einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), kann neue eingehende Daten abgeworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen richtig zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Beispiel für grundlegende Flusskontrolle {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis der Terminal ein Datenstück verarbeitet hat:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Beispiel für Flusskontrolle mit Wasserzeichen {#watermark-flow-control-example}

Für effizientere Kontrolle verwenden Sie Hoch-/Tiefwasserzeichen:

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

### Terminalthema {#terminal-theme}

Sie können das Terminal mit `TerminalTheme` gestalten, das definiert:

- Hintergrund- und Vordergrundfarben.
- Standard-`ANSI`-Farbenpalette.
- Hintergrundfarben von Cursor und Auswahl.

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

Der Terminal unterstützt eine breite Palette von standardmäßigen Steuersequenzen, die für Cursorbewegung, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0`-Steuerzeichen** (einzelbyte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rückschritt und Zeilenumbruch)
- **`C1`-Steuerzeichen** (einzelbyte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC`-Sequenzen** (beginnt mit `ESC` (`\x1B`), wie Cursor speichern/wiederherstellen, Bildschirmausrichtung)
- **`CSI`-Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Styling)
- **`DCS`-Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC`-Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, Hyperlinks und Farben)

:::info Verarbeitung exotischer und benutzerdefinierter Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können bei Bedarf durch Integrationen unterstützt werden.
:::

## Gestaltung {#styling}

<TableBuilder name="Terminal" />
