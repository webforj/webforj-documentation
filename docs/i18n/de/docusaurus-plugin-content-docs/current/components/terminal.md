---
title: Terminal
sidebar_position: 126
_i18n_hash: 513f4970da96e2e9f36a80739e60cd9c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Die `Terminal`-Komponente ist ein interaktiver Terminalemulator, der sich wie eine traditionelle Systemkonsole verhält. Sie verarbeitet Textausgaben, Benutzereingaben, Steuersequenzen und Bildschirmpuffer und ist somit geeignet für den Aufbau von Remote-Access-Tools, Text-Dashboards, eingebetteten Befehlszeilen oder Debug-Konsolen.

<!-- INTRO_END -->

## Erstellen eines Terminals {#creating-a-terminal}

:::info Importieren von Terminal
Um die `Terminal`-Komponente in Ihrer Anwendung zu verwenden, stellen Sie sicher, dass Sie die folgende Abhängigkeit in Ihrer pom.xml einschließen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Das folgende Beispiel erstellt eine interaktive Befehlszeile mit eingegebenen Befehlen, Verlaufsnavigation und benutzerdefinierten Ausgaben.

<ComponentDemo
path='/webforj/terminal'
files={[
  'src/main/java/com/webforj/samples/views/terminal/TerminalView.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java',
  'src/main/resources/static/css/terminal/terminal-view.css',
]}
height='400px'
/>

## Wie es funktioniert {#how-it-works}

Das Terminal verwaltet ein Gitter von Textzellen, verarbeitet eingehende Zeichenströme und reagiert auf Benutzeraktionen wie das Tippen oder Auswählen von Text. Es interpretiert automatisch Steuerzeichen und Escape-Sequenzen für den Cursorbeweger, Farbänderungen und das Leeren des Bildschirms.

Die grundlegenden Verhaltensweisen umfassen:

- **Daten Eingabe**: Das Schreiben von Daten ins Terminal aktualisiert den Bildschirm und verarbeitet sowohl Text- als auch Steuersequenzen.
- **Daten Ausgabe**: Erfasst Benutzereingaben und gibt diese als strukturierte Ereignisse aus.
- **Bildschirmverwaltung**: Pflegt einen scrollbaren Verlaufspuffer und den aktuellen Bildschirmzustand.
- **Cursorverwaltung**: Verfolgt die Cursorposition für die Texteingabe und die Antworten auf Steuersequenzen.

Das Terminal ist zustandsbehaftet, was bedeutet, dass es mehrbyteige Zeichen ordnungsgemäß rekonstruieren und die Kontinuität über fragmentierte Eingaben hinweg aufrechterhalten kann.

## Senden von Daten an das Terminal {#sending-data-to-the-terminal}

Daten werden an das Terminal mithilfe der Methoden `write` und `writeln` gesendet:

- `write(Object data)`: Sendet Daten in den Terminalstream.
- `writeln(Object data)`: Sendet Daten gefolgt von einem Zeilenumbruch.

Das Terminal verarbeitet alle eingehenden Daten als **UTF-16**-Strings. Es behandelt automatisch mehrbyteige Zeichen, selbst wenn die Eingabe in fragmentierten Chunks eintrifft.

### Beispiel {#example}
```java
terminal.write("echo Hallo Welt\n");
terminal.writeln("Bereit.");
```

Sie können auch eine Rückruffunktion anhängen, die ausgeführt wird, sobald der Datenchunk verarbeitet wurde:

```java
terminal.write("Lange Befehlsausgabe", e -> {
  System.out.println("Daten verarbeitet.");
});
```

## Empfang von Benutzereingaben {#receiving-user-input}

Das Terminal erfasst Benutzereingaben über zwei Ereignisse:

- **Datenereignis (`onData`)**: Wird ausgelöst, wenn Texteingaben erfolgen, und sendet Unicode-Zeichen.
- **Tasteneingabeereignis (`onKey`)**: Wird für jeden Tastendruck ausgelöst und enthält Informationen über Tastencodes und Modifikatoren wie <kbd>Ctrl</kbd> oder <kbd>Alt</kbd>.

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

Alle vom Terminal erfassten Benutzereingaben (zum Beispiel von `onData`-Ereignissen) werden als UTF-16-Strings ausgegeben.  
Wenn Ihr Backend eine andere Kodierung (wie UTF-8-Bytes) erwartet, müssen Sie die Daten manuell transkodieren.

:::info Veraltete Kodierungen
Das Terminal **unterstützt keine veralteten Kodierungen** wie `ISO-8859`.  
Wenn Sie Kompatibilität mit Nicht-UTF-8-Systemen benötigen, verwenden Sie einen externen Transcoder (zum Beispiel [`luit`](https://linux.die.net/man/1/luit) oder [`iconv`](https://de.wikipedia.org/wiki/Iconv)), um die Daten vor dem Schreiben oder Lesen im Terminal zu konvertieren.
:::

## Verarbeitung großer Datenströme {#handling-large-data-streams}

Da das Terminal unbegrenzte Eingaben nicht sofort rendern kann, unterhält es einen internen Eingabepuffer. Wenn dieser Puffer zu groß wird (standardmäßig etwa `50MB`), können neue eingehende Daten verworfen werden, um die Systemleistung zu schützen.

Um schnelle Datenquellen ordnungsgemäß zu verwalten, sollten Sie **Flusskontrolle** implementieren.

### Einfaches Beispiel für Flusskontrolle {#basic-flow-control-example}

Pausieren Sie Ihr Backend, bis das Terminal mit der Verarbeitung eines Chunks fertig ist:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Beispiel für Flusskontrolle mit Wasserzeichen {#watermark-flow-control-example}

Für eine effizientere Kontrolle verwenden Sie hohe/niedrige Wasserzeichen:

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

Die Klasse `TerminalOptions` ermöglicht Ihnen die Konfiguration des Verhaltens:

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

### Terminal-Design {#terminal-theme}

Sie können das Terminal mit `TerminalTheme` gestalten, das die folgenden Eigenschaften definiert:

- Hintergrund- und Vordergrundfarben.
- Standard-Farbenpalette `ANSI`.
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

Das Terminal unterstützt eine Vielzahl standardmäßiger Steuersequenzen, die für Cursorbewegungen, Bildschirmaktualisierungen und Textformatierungen verwendet werden.

Erkannte Gruppen:

- **`C0`-Steuercodes** (einzelbyte 7-bit Befehle, `\x00`, `\x1F`, wie Rückschritt und Zeilenumbruch)
- **`C1`-Steuercodes** (einzelbyte 8-bit Befehle, `\x80`, `\x9F`)
- **`ESC`-Sequenzen** (beginnend mit `ESC` (`\x1B`), wie Cursor speichern/wiederherstellen, Bildschirmausrichtung)
- **`CSI`-Sequenzen** (Control Sequence Introducer, `ESC [` oder `CSI (\x9B)`, für Operationen wie Scrollen, Löschen und Stylisieren)
- **`DCS`-Sequenzen** (Gerätesteuerungszeichenfolgen, `ESC P` oder `DCS (\x90)`)
- **`OSC`-Sequenzen** (Betriebssystembefehle, `ESC ]` oder `OSC (\x9D)`, zum Setzen des Fenstertitels, von Hyperlinks und Farben)

:::info Umgang mit exotischen und benutzerdefinierten Sequenzen
Einige exotische Sequenztypen wie `APC`, `PM` und `SOS` werden erkannt, aber stillschweigend ignoriert.  
Benutzerdefinierte Sequenzen können nach Bedarf über Integrationen unterstützt werden.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
