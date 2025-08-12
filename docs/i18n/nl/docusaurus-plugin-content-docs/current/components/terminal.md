---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component biedt een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het stelt applicaties in staat om een tekstgebaseerde interface weer te geven en te manipuleren, waarbij tekstuitvoer wordt afgehandeld, gebruikersinvoer wordt ontvangen, controle-sequenties worden geïnterpreteerd en schermbuffers worden onderhouden.

Deze terminal is ontworpen om betrouwbare prestaties te leveren in verschillende gebruiksscenario's, zoals het bouwen van tools voor externe toegang, tekstdashboards, ingebedde opdracht-shells of interactieve debug-console's.

:::info Importeren van Terminal
Om de `Terminal` component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

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
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## Hoe het werkt {#how-it-works}

De terminal beheert een grid van tekstcellen, verwerkt binnenkomende karakterstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleurveranderingen en schermwissen.

De kernfunctionaliteiten omvatten:

- **Gegevensinvoer**: Schrijven van gegevens naar de terminal werkt het scherm bij en verwerkt zowel tekst als controle-sequenties.
- **Gegevensuitvoer**: Vangt gebruikers toetsaanslagen en geeft deze door als gestructureerde gebeurtenissen.
- **Schermbeheer**: Beheert een scrollbare geschiedenisbuffer en de huidige schermstatus.
- **Cursorbeheer**: Volgt de cursorpositie voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat het correct multibyte-tekens reconstrueert en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal gestuurd via de `write` en `writeln` methoden:

- `write(Object data)`: Stuurt gegevens naar de terminalstroom.
- `writeln(Object data)`: Stuurt gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16** strings. Het verwerkt automatisch multibyte-tekens, zelfs wanneer de invoer in gefragmenteerde stukken arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevenspakket is verwerkt:

```java
terminal.write("Langere opdrachtuitvoer", e -> {
    System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen van gebruikersinvoer {#receiving-user-input}

De terminal vangt door de gebruiker gegenereerde invoer via twee gebeurtenissen:

- **Gegevensgebeurtenis (`onData`)**: Vindt plaats wanneer tekstinvoer gebeurt, waarbij Unicode-tekens worden verzonden.
- **Toetsgebeurtenis (`onKey`)**: Vindt plaats voor elke toetsaanslag, inclusief informatie over toetscodes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze gebeurtenissen kunnen worden gebruikt om gebruikersinvoer naar een backend door te geven, UI-elementen bij te werken of aangepaste acties te activeren.

### Voorbeeld {#example-1}
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

Alle door de terminal gevangen gebruikersinvoer (zoals van `onData` gebeurtenissen) wordt uitgezonden als UTF-16 strings.  
Als je backend een andere codering verwacht (zoals UTF-8 bytes), moet je de gegevens handmatig transcodereren.

:::info Legacy Encodings
De terminal **ondersteunt geen legacy coderingen** zoals `ISO-8859`.  
Als je compatibiliteit met niet-UTF-8 systemen nodig hebt, gebruik dan een externe transcoder (bijvoorbeeld, [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar de terminal schrijft of ervan leest.
:::

## Behandelen van grote gegevensstromen {#handling-large-data-streams}

Aangezien de terminal niet in staat is om onbeperkte invoer onmiddellijk weer te geven, behoudt het een interne invoerbuffer. Als deze buffer te groot wordt (standaard ongeveer `50MB`), kunnen nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snel gegevensbronnen goed te beheren, moet je **flow control** implementeren.

### Basisflow control voorbeeld {#basic-flow-control-example}

Pauzeer je backend totdat de terminal klaar is met het verwerken van een gegevenspakket:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Watermark flow control voorbeeld {#watermark-flow-control-example}

Voor efficiëntere controle, gebruik hoge/lage watermerken:

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

## Aanpassing {#customization}

### Terminalopties {#terminal-options}

De `TerminalOptions` klasse stelt je in staat om het gedrag te configureren:

- Cursor knipperen.
- Lettertype-instellingen (familie, grootte, gewicht).
- Scrollback-buffergrootte.
- Regelhoogte en letterafstand.
- Toegankelijkheidsinstellingen (mode voor schermlezers).

Voorbeeld:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminalthema {#terminal-theme}

Je kunt de terminal stylen met behulp van `TerminalTheme`, dat het volgende definieert:

- Achtergrond- en voortgronds kleuren.
- Standaard `ANSI` kleurenpalet.
- Cursor- en selectieachtergrondkleuren.

Voorbeeld:
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

## Ondersteunde sequenties {#supported-sequences}

De terminal ondersteunt een breed scala aan standaard controle-sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstopmaak.

Herkenbare groepen:

- **`C0` controlecodes** (eén-byte 7-bit commando's, `\x00`, `\x1F`, zoals backspace en line feed)
- **`C1` controlecodes** (één-byte 8-bit commando's, `\x80`, `\x9F`)
- **`ESC` sequenties** (begonnen met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor bewerkingen zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitels, hyperlinks en kleuren)

:::info Omgaan met exotische en aangepaste sequenties
Sommige exotische sequentietypen zoals `APC`, `PM` en `SOS` worden herkend maar stilzwijgend genegeerd.  
Aangepaste sequenties kunnen indien nodig worden ondersteund via integraties.
:::

## Stijling {#styling}

<TableBuilder name="Terminal" />
