---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Die `Terminal`-Komponente bietet einen interaktiven Terminalemulator, der sich ähnlich wie eine traditionelle Systemkonsole verhält. Sie ermöglicht Anwendungen, eine textbasierte Benutzeroberfläche anzuzeigen und zu manipulieren, behandelt Textausgaben, empfängt Benutzereingaben, interpretiert Steuersequenzen und verwaltet Bildschirmpuffer.

Dieser Terminal wurde entwickelt, um eine zuverlässige Leistung über eine Vielzahl von Anwendungsfällen hinweg zu bieten, wie z. B. beim Erstellen von Remote-Zugriffstools, Text-Dashboards, eingebetteten Befehlszeilen oder interaktiven Debug-Konsolen.

:::info Importieren von Terminal
Um die `Terminal`-Komponente in Ihrer App zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einfügen.

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

Der Terminal verwaltet ein Raster von Textzellen, verarbeitet eingehende Zeichendatenströme und reagiert auf Benutzeraktionen wie Tippen oder Textauswahl. Er interpretiert automatisch Steuerzeichen und Escape-Sequenzen für die Cursorbewegung, Farbänderungen und das Löschen des Bildschirms.

Die grundlegenden Verhaltensweisen umfassen:

- **Dateninput**: Daten in den Terminal zu schreiben, aktualisiert den Bildschirm und verarbeitet sowohl Text- als auch Steuersequenzen.
- **Datenoutput**: Erfasst Benutzereingaben und gibt sie als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Hält einen blätterbaren Verlaufsbuffer und den aktuellen Bildschirmstatus.
- **Cursorverwaltung**: Verfolgt die Cursorposition für Texteingaben und Reaktionen auf Steuersequenzen.

Der Terminal ist zustandsbehaftet, was bedeutet, dass er mehrbyte Zeichen korrekt rekonstruieren kann und die Kontinuität über fragmentierte Eingaben hinweg aufrechterhält.

## Daten an den Terminal senden {#sending-data-to-the-terminal}

Daten werden an den Terminal mit den Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstrom.
- `writeln(Object data)`: Sendet Daten gefolgt von einem Zeilenumbruch.

Der Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Er verarbeitet automatisch mehrbyte Zeichen, selbst wenn die Eingaben in fragmentierten Stücken ankommen.

### Beispiel {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Bereit.");
```

Sie können auch einen Callback angeben, der einmal ausgeführt wird, wenn das Datenpaket verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
    System.out.println("Daten verarbeitet.");
});
```

## Benutzereingaben empfangen {#receiving-user-input}

Der Terminal erfasst benutzergenerierte Eingaben über zwei Ereignisse:

- **Datenereignis (`onData`)**: Tritt auf, wenn Texteingaben erfolgen, und sendet Unicode-Zeichen.
- **Tastenevent (`onKey`)**: Tritt bei jedem Tastendruck auf und enthält Informationen über Tastencodes und Modifikatoren wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

Diese Ereignisse können verwendet werden, um Benutzereingaben an einen Backend zu übermitteln, UI-Elemente zu aktualisieren oder benutzerdefinierte Aktionen auszulösen.

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

Alle Benutzereingaben, die vom Terminal erfasst werden (wie z. B. von `onData`-Ereignissen), werden als UTF-16-Strings ausgegeben.  
Wenn Ihr Backend eine andere Kodierung erwartet (z. B. UTF-8-Bytes), müssen Sie die Daten manuell umkodieren.

:::info Alte Kodierungen
Der Terminal **unterstützt keine alten Kodierungen** wie `ISO-8859`.  
Wenn Sie eine Kompatibilität mit nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (z. B. [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://en.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben oder Lesen vom Terminal zu konvertieren.
:::

## Verarbeitung großer Datenströme {#handling-large-data-streams}

Da der Terminal nicht sofort unbegrenzte Eingaben rendern kann, verwaltet er einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (Standardgröße etwa `50MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnellere Datenquellen richtig zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Beispiel für grundlegende Flusskontrolle {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis der Terminal ein Datenpaket verarbeitet hat:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Beispiel für Flusskontrolle mit Wasserzeichen {#watermark-flow-control-example}

Für eine effizientere Kontrolle verwenden Sie Hoch-/Niedrigwasserzeichen:

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
- Barrierefreiheitseinstellungen (Bildschirmlesermodus).

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

Sie können den Terminal mithilfe von `TerminalTheme` gestalten, das definiert:

- Hintergrund- und Vordergrundfarben.
- Standard-`ANSI`-Farbenpalette.
- Cursor- und Auswahlhintergrundfarben.

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

Der Terminal unterstützt eine breite Palette von standardmäßigen Steuersequenzen, die für die Cursorbewegung, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0`-Steuercodes** (einzelne Byte 7-Bit-Befehle, `\x00`, `\x1F`, wie Rücktaste und Zeilenumbruch)
- **`C1`-Steuercodes** (einzelne Byte 8-Bit-Befehle, `\x80`, `\x9F`)
- **`ESC`-Sequenzen** (beginnt mit `ESC` (`\x1B`), wie speichern/wiederherstellen des Cursors, Bildschirmausrichtung)
- **`CSI`-Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Styling)
- **`DCS`-Sequenzen** (Device Control Strings, `ESC P` oder `DCS (\x90)`)
- **`OSC`-Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können bei Bedarf durch Integrationen unterstützt werden.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
