---
title: Terminal
sidebar_position: 126
_i18n_hash: a4c442c62c748f82d75133db075054af
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het behandelt tekstuitvoer, gebruikersinvoer, controle-sequenties en schermbuffers, waardoor het geschikt is voor het bouwen van externe toegangshulpmiddelen, tekstdashboards, ingebedde opdracht-shells of debug-consoles.

<!-- INTRO_END -->

## Een terminal maken {#creating-a-terminal}

:::info Het importeren van Terminal
Om de `Terminal` component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve opdracht-shell met getypte opdrachten, geschiedenisnavigatie en aangepaste uitvoer.

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

De terminal beheert een rooster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekstselectie. Het interpreteert automatisch controle-tekens en escape-sequenties voor cursorbeweging, kleurveranderingen en schermwissing.

De kernfunctionaliteiten omvatten:

- **Gegevensinvoer**: Het schrijven van gegevens naar de terminal werkt het scherm bij, waarbij zowel tekst als controle-sequenties worden behandeld.
- **Gegevensuitvoer**: Vangt gebruikerstoetsen en geeft ze als gestructureerde gebeurtenissen af.
- **Schermbeheer**: Onderhoudt een scrollbare geschiedenisbuffer en de huidige schermstatus.
- **Cursorbeheer**: Houdt de cursorpositie bij voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat het correct meervoudige bytesymbolen reconstrueert en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal gestuurd met de `write` en `writeln` methoden:

- `write(Object data)`: Stuur gegevens in de terminalstroom.
- `writeln(Object data)`: Stuur gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16** strings. Het behandelt automatisch meervoudige bytesymbolen, zelfs wanneer invoer in gefragmenteerde stukjes arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevenspakket is verwerkt:

```java
terminal.write("Lang opdrachtoutput", e -> {
  System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen van gebruikersinvoer {#receiving-user-input}

De terminal vangt gebruikersgegenereerde invoer via twee gebeurtenissen:

- **Gegevensgebeurtenis (`onData`)**: Wordt geactiveerd wanneer er tekstinvoer plaatsvindt, verstuurt Unicode-tekens.
- **Toetsgebeurtenis (`onKey`)**: Wordt geactiveerd voor elke toetsaanslag, inclusief informatie over toetscodes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze gebeurtenissen kunnen worden gebruikt om gebruikersinvoer naar een backend door te sturen, UI-elementen bij te werken of aangepaste acties te triggeren.

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

Alle gebruikersinvoer die door de terminal wordt vastgelegd (zoals vanuit `onData` gebeurtenissen) wordt afgegeven als UTF-16 strings.  
Als je backend een andere codering verwacht (zoals UTF-8 bytes), moet je de gegevens handmatig transcoderen.

:::info Legacy encodings
De terminal **ondersteunt geen legacy encodings** zoals `ISO-8859`.  
Als je compatibiliteit nodig hebt met systemen die geen UTF-8 gebruiken, gebruik dan een externe transcoder (bijvoorbeeld, [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar de terminal schrijft of ervan leest.
:::

## Omgaan met grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan weergeven, onderhoudt het een interne invoerbuffer. Als deze buffer te groot wordt (standaard rond de `50MB`), kunnen nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snel gegevensbronnen goed te beheren, moet je **flowcontrole** implementeren.

### Basisflowcontrolevoorbeeld {#basic-flow-control-example}

Pauzeer je backend totdat de terminal een gegevenspakket heeft verwerkt:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Watermerk-flowcontrolevoorbeeld {#watermark-flow-control-example}

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
- Scrollback buffer grootte.
- Regelhoogte en letterafstand.
- Toegankelijkheidsinstellingen (schermlezer-modus).

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

Je kunt de terminal stylen met `TerminalTheme`, die het volgende definieert:

- Achtergrond- en letterkleur.
- Standaard `ANSI` kleurenpalet.
- Cursor- en selecties achtergrondkleuren.

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

De terminal ondersteunt een breed scala van standaard controle-sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstopmaak.

Herkenbare groepen:

- **`C0` controlecodes** (één-byte 7-bit opdrachten, `\x00`, `\x1F`, zoals backspace en line feed)
- **`C1` controlecodes** (één-byte 8-bit opdrachten, `\x80`, `\x9F`)
- **`ESC` sequenties** (begint met `ESC` (`\x1B`), zoals het opslaan/herstellen van de cursor, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van windowtitel, hyperlinks en kleuren)

:::info Omgaan met exotische en aangepaste sequenties
Sommige exotische sequentietypen zoals `APC`, `PM` en `SOS` worden herkend maar stilzwijgend genegeerd.  
Aangepaste sequenties kunnen worden ondersteund via integraties indien nodig.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
