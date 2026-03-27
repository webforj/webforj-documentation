---
title: Terminal
sidebar_position: 126
_i18n_hash: 72b2270f024a64687440deef9c6d82c4
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het verwerkt tekstuitvoer, gebruikersinvoer, controle-sequenties en schermbuffers, waardoor het geschikt is voor het bouwen van tools voor externe toegang, tekstdashboards, ingebedde commando-shells of foutopsporingsconsole.

<!-- INTRO_END -->

## Een terminal maken {#creating-a-terminal}

:::info Terminal importeren
Om de `Terminal` component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve opdrachten-shell met getypte commando's, geschiedenisnavigatie en aangepaste uitvoer.

<ComponentDemo 
path='/webforj/terminal?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
urls={[
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## Hoe het werkt {#how-it-works}

De terminal beheert een raster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controle-tekens en escape-sequenties voor cursorbeweging, kleurveranderingen en schermwissen.

De belangrijkste gedragingen zijn:

- **Gegevensinvoer**: Gegevens naar de terminal schrijven, bijgewerkt het scherm en verwerkt zowel tekst als controle-sequenties.
- **Gegevensuitvoer**: Vangt gebruikersinvoeren op en geeft deze weer als gestructureerde evenementen.
- **Schermbeheer**: Onderhoudt een scrollbare geschiedenisbuffer en de huidige schermstatus.
- **Cursorbeheer**: Volgt de cursorpositie voor tekstinvoer en reacties op controle-sequenties.

De terminal is statusgebonden, wat betekent dat het multibyte-tekens correct herbouwt en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal verzonden met de methoden `write` en `writeln`:

- `write(Object data)`: Stuurt gegevens in de terminalstroom.
- `writeln(Object data)`: Stuurt gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16**-strings. Het verwerkt automatisch multibyte-tekens, zelfs wanneer de invoer in gefragmenteerde stukjes arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het stuk gegevens is verwerkt:

```java
terminal.write("Lange opdrachtuitvoer", e -> {
    System.out.println("Gegevens verwerkt.");
});
```

## Gebruikersinvoer ontvangen {#receiving-user-input}

De terminal vangt gebruikersinvoer via twee evenementen:

- **Gegevens Evenement (`onData`)**: Gaat af wanneer tekstinvoer plaatsvindt en verzendt Unicode-tekens.
- **Toets Evenement (`onKey`)**: Gaat af voor elke toetsaanslag, inclusief informatie over toets codes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

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

Alle gebruikersinvoer die door de terminal wordt vastgelegd (zoals van `onData`-evenementen) wordt uitgegeven als UTF-16-strings.  
Als je backend een andere codering verwacht (zoals UTF-8-bytes), moet je de gegevens handmatig transcodereren.

:::info Legacy Coderingen
De terminal **ondersteunt geen legacy coderingen** zoals `ISO-8859`.  
Als je compatibiliteit nodig hebt met niet-UTF-8-systemen, gebruik een externe transcoder (bijvoorbeeld [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar de terminal schrijft of ervan leest.
:::

## Omgaan met grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan weergeven, onderhoudt het een interne invoerbuffer. Als deze buffer te groot wordt (standaard rond de `50MB`), kan nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snel gegevensbronnen goed te beheren, zou je **flowcontrol** moeten implementeren.

### Basis flowcontrol voorbeeld {#basic-flow-control-example}

Pauzeer je backend totdat de terminal klaar is met het verwerken van een stuk:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Watermark flowcontrol voorbeeld {#watermark-flow-control-example}

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

### Terminal opties {#terminal-options}

De `TerminalOptions` klasse stelt je in staat om het gedrag te configureren:

- Cursor knipperen.
- Lettertype-instellingen (familie, grootte, gewicht).
- Scrollback-buffer grootte.
- Regelhoogte en letterafstand.
- Toegankelijkheidsinstellingen (modi voor schermlezers).

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

Je kunt de terminal stijlen met `TerminalTheme`, dat definiëert:

- Achtergrond- en voorgrondkleuren.
- Standaard `ANSI` kleurpalet.
- Cursor- en selectie-achtergrondkleuren.

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

Herkennen groepen:

- **`C0` controle codes** (één-byte 7-bits opdrachten, `\x00`, `\x1F`, zoals backspace en regeleinde)
- **`C1` controle codes** (één-byte 8-bits opdrachten, `\x80`, `\x9F`)
- **`ESC` sequenties** (begint met `ESC` (`\x1B`), zoals het opslaan/herstellen van de cursor, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitels, hyperlinks en kleuren)

:::info Omgaan met Exotische en Aangepaste Sequenties
Sommige exotische sequentietypen zoals `APC`, `PM` en `SOS` worden herkend maar stilletjes genegeerd.  
Aangepaste sequenties kunnen indien nodig worden ondersteund via integraties.
:::

## Stijling {#styling}

<TableBuilder name="Terminal" />
