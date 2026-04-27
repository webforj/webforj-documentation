---
title: Terminal
sidebar_position: 126
_i18n_hash: d25dd721593cf2850f9b8c1b7dd742ee
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het behandelt tekstuitvoer, gebruikersinvoer, controle-sequenties en schermbuffers, waardoor het geschikt is voor het bouwen van hulpmiddelen voor externe toegang, tekstdashboarden, ingebedde opdracht-shells of debug-consoles.

<!-- INTRO_END -->

## Een terminal creëren {#creating-a-terminal}

:::info Terminal importeren
Om de `Terminal` component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve command-shell met getypte opdrachten, navigatie door de geschiedenis en aangepaste uitvoer.

<ComponentDemo 
path='/webforj/terminal?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
urls={[
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
cssURL='/css/terminal/terminal-view.css'
height='400px'
/>

## Hoe het werkt {#how-it-works}

De terminal beheert een raster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleurveranderingen en het wissen van het scherm.

De kernfunctionaliteiten omvatten:

- **Gegevensinvoer**: Gegevens naar de terminal schrijven bijgewerkt het scherm, met inbegrip van zowel tekst als controle-sequenties.
- **Gegevensuitvoer**: Vangt gebruikersinvoeren en geeft deze door als gestructureerde evenementen.
- **Schermbeheer**: Onderhoudt een scrollbare geschiedenisbuffer en de huidige schermtoestand.
- **Cursorverwerking**: Volgt de cursorpositie voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat het correct meervoudige byte-tekens reconstrueert en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal verzonden met behulp van de `write` en `writeln` methoden:

- `write(Object data)`: Stuurt gegevens in de terminalstroom.
- `writeln(Object data)`: Stuurt gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16** strings. Het verwerkt automatisch meervoudige byte-tekens, zelfs wanneer invoer in gefragmenteerde stukken arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback bijvoegen die wordt uitgevoerd zodra het stuk gegevens is verwerkt:

```java
terminal.write("Langere opdracht uitvoer", e -> {
  System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen van gebruikersinvoer {#receiving-user-input}

De terminal vangt gebruikersgegenereerde invoer via twee evenementen:

- **Gegevensevenement (`onData`)**: Wordt geactiveerd wanneer er tekstinvoer plaatsvindt, en stuurt Unicode-tekens.
- **Toetsenbordevenement (`onKey`)**: Wordt geactiveerd voor elke toetsaanslag, inclusief informatie over toetscodes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze evenementen kunnen worden gebruikt om gebruikersinvoer door te geven aan een backend, UI-elementen bij te werken of aangepaste acties te triggeren.

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

Alle door de terminal gevangen gebruikersinvoer (zoals van `onData` evenementen) worden uitgegeven als UTF-16 strings.  
Als je backend een andere codering verwacht (zoals UTF-8 bytes), moet je de gegevens handmatig transcodereren.

:::info Legacy Encodings
De terminal **ondersteunt geen legacy encoderingen** zoals `ISO-8859`.  
Als je compatibiliteit nodig hebt met niet-UTF-8-systemen, gebruik dan een externe transcoder (bijvoorbeeld [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar of vanuit de terminal schrijft of leest.
:::

## Omgaan met grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan renderen, onderhoudt deze een interne invoerbuffer. Als deze buffer te groot wordt (standaard rond de `50MB`), kunnen nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snel gegevensbronnen goed te beheren, moet je **flow control** implementeren.

### Voorbeeld van basis flow control {#basic-flow-control-example}

Pauzeer je backend totdat de terminal klaar is met het verwerken van een stuk:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Voorbeeld van waterniveau flow control {#watermark-flow-control-example}

Voor efficiëntere controle gebruik je hoge/lage watermerken:

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

De `TerminalOptions` klasse stelt je in staat om gedrag te configureren:

- Cursor knipperen.
- Lettertype-instellingen (familie, grootte, gewicht).
- Scrollback buffer grootte.
- Regelhoogte en letterafstand.
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

### Terminalthema {#terminal-theme}

Je kunt de terminal stylen met `TerminalTheme`, dat definieert:

- Achtergrond- en voorgrondkleuren.
- Standaard `ANSI` kleurpalette.
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

De terminal ondersteunt een breed scala aan standaard controle sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstopmaak.

Herkenbare groepen:

- **`C0` controlecodes** (enkel-byte 7-bits commando's, `\x00`, `\x1F`, zoals terugtocht en regelvoeding)
- **`C1` controlecodes** (enkel-byte 8-bits commando's, `\x80`, `\x9F`)
- **`ESC` sequenties** (beginnend met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Controle Sequentie Inleider, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Apparaat Controle String, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Besturingssysteemopdrachten, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitels, hyperlinks en kleuren)

:::info Omgaan met exotische en aangepaste sequenties
Sommige exotische sequentietypes zoals `APC`,`PM`, en `SOS` worden herkend maar stilletjes genegeerd.  
Aangepaste sequenties kunnen indien nodig worden ondersteund via integraties.
:::

## Stijlen {#styling}

<TableBuilder name="Terminal" />
