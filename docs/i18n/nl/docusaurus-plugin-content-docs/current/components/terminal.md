---
title: Terminal
sidebar_position: 126
_i18n_hash: 4b7abaef79b0511bbbb796171ddd8c07
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component biedt een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het stelt applicaties in staat om een tekstgebaseerde interface weer te geven en te manipuleren, textoutput te verwerken, gebruikersinvoer te ontvangen, controle sequenties te interpreteren en schermbuffers te onderhouden.

Deze terminal is ontworpen om betrouwbare prestaties te bieden in een scala aan gebruikstoepassingen, zoals het bouwen van hulpmiddelen voor externe toegang, tekstdashboards, ingebedde opdracht-shells of interactieve debugconsole.

:::info Het importeren van Terminal
Om de `Terminal` component in je applicatie te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

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

## Hoe het werkt {#how-it-works}

De terminal beheert een raster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleurveranderingen en scherm wissen.

De belangrijkste gedragingen zijn:

- **Gegevensinvoer**: Het schrijven van gegevens naar de terminal werkt het scherm bij en verwerkt zowel tekst- als controle-sequenties.
- **Gegevensuitvoer**: Vangt gebruikersinvoer en geeft deze als gestructureerde evenementen weer.
- **Schermbeheer**: Beheert een scrollbare geschiedenisbuffer en de huidige schermstatus.
- **Cursorbeheer**: Volgt de cursorpositie voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat deze multibyte-tekens correct reconstrueert en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal verzonden met de `write` en `writeln` methoden:

- `write(Object data)`: Verzendt gegevens in de terminalstroom. 
- `writeln(Object data)`: Verzendt gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16** strings. Het verwerkt automatisch multibyte-tekens, zelfs wanneer de invoer in gefragmenteerde stukken aankomt.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevenschunk is verwerkt:

```java
terminal.write("Lang opdrachtoutput", e -> {
    System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen gebruikersinvoer {#receiving-user-input}

De terminal vangt gebruikersinvoer via twee evenementen:

- **Gegevens evenement (`onData`)**: Vindt plaats wanneer tekstinvoer plaatsvindt, verzendt Unicode-tekens.
- **Toets evenement (`onKey`)**: Vindt plaats voor elke toetsaanslag, inclusief informatie over toetscodes en modificatoren zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze evenementen kunnen worden gebruikt om gebruikersinvoer naar een backend door te geven, UI-elementen bij te werken of aangepaste acties te triggeren.

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

Alle gebruikersinvoer die door de terminal wordt gevangen (zoals van `onData` evenementen) wordt uitgegeven als UTF-16 strings.  
Als jouw backend een andere codering verwacht (zoals UTF-8 bytes), moet je de gegevens handmatig transcoderen.

:::info Erfgoed Coderingen
De terminal **ondersteunt geen erfgoedcoderingen** zoals `ISO-8859`.  
Als je compatibiliteit met niet-UTF-8-systemen nodig hebt, gebruik dan een externe transcoder (bijvoorbeeld, [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar of van de terminal schrijft of leest.
:::

## Behandelen van grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan weergeven, beheert deze een interne invoerbuffer. Als deze buffer te groot wordt (standaard rond `50MB`), kunnen nieuwe binnenkomende gegevens worden weggelaten om de systeemprestaties te beschermen.

Om snel gegevensbronnen goed te beheren, moet je **stroomcontrole** implementeren.

### Basis voorbeeld van stroomcontrole {#basic-flow-control-example}

Pauzeer je backend totdat de terminal klaar is met het verwerken van een chunk:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Voorbeeld van stroomcontrole met watermark {#watermark-flow-control-example}

Gebruik voor efficiëntere controle hoge/lage watermerken:

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

### Terminal opties {#terminal-options}

De `TerminalOptions` klasse stelt je in staat om gedragingen te configureren:

- Cursor knipperen.
- Lettertype instellingen (familie, grootte, gewicht).
- Scrollback buffer grootte.
- Regelhoogte en letterspatiëring.
- Toegankelijkheidsinstellingen (schermlezer modus).

Voorbeeld:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminal thema {#terminal-theme}

Je kunt de terminal stylen met `TerminalTheme`, dat definieert:

- Achtergrond- en voorgrondkleuren.
- Standaard `ANSI` kleurenpalet.
- Cursor- en selecteerkleur achtergronden.

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

De terminal ondersteunt een breed scala aan standaard controle sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstopmaak.

Herkenbare groepen:

- **`C0` controlecodes** (één-byte 7-bit commando's, `\x00`, `\x1F`, zoals backspace en line feed)
- **`C1` controlecodes** (één-byte 8-bit commando's, `\x80`, `\x9F`)
- **`ESC` sequenties** (beginnend met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen en stylen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitel, hyperlinks en kleuren)

:::info Het afhandelen van exotische en aangepaste sequenties
Sommige exotische sequentietypen zoals `APC`, `PM` en `SOS` worden herkend maar stilzwijgend genegeerd.  
Aangepaste sequenties kunnen indien nodig door integraties worden ondersteund.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
