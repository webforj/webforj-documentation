---
title: Terminal
sidebar_position: 126
description: >-
  Embed an interactive terminal emulator with the Terminal component for shells,
  dashboards, debug consoles, and remote access tools.
_i18n_hash: 231986360b04eb43ad3b6fecc9f02816
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal`-component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Hij verwerkt tekstuitvoer, gebruikersinvoer, controle-sequenties en schermbuffers, waardoor hij geschikt is voor het bouwen van tools voor externe toegang, tekstdashboards, ingebedde opdracht-shells of debugconsoles.

<!-- INTRO_END -->

## Een terminal maken {#creating-a-terminal}

:::info Terminal importeren
Om de `Terminal`-component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve opdracht-shell met getypte opdrachten, geschiedenisnavigatie en aangepaste uitvoer.

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
  'src/main/frontend/css/terminal/terminal-view.css',
]}
height='400px'
/>

## Hoe het werkt {#how-it-works}

De terminal beheert een raster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Hij interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleurveranderingen en het wissen van het scherm.

De belangrijkste functionaliteiten zijn:

- **Gegevensinvoer**: Gegevens schrijven naar de terminal werkt het scherm bij, inclusief zowel tekst als controle-sequenties.
- **Gegevensuitvoer**: Vangt toetsaanslagen van de gebruiker en geeft deze door als gestructureerde evenementen.
- **Schermbeheer**: Onderhoudt een scrollbare geschiedenisbuffer en huidige schermstatus.
- **Cursorbeheer**: Houdt de cursorpositie bij voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat hij correct multibyte-tekens reconstrueert en continuïteit behoudt over gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal verzonden met de methods `write` en `writeln`:

- `write(Object data)`: Verzendt gegevens naar de terminalstroom.
- `writeln(Object data)`: Verzendt gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16**-tekens. Hij verwerkt automatisch multibyte-tekens, zelfs wanneer de invoer in gefragmenteerde stukken arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevenspakket is verwerkt:

```java
terminal.write("Lang opdrachtuitvoer", e -> {
  System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen gebruikersinvoer {#receiving-user-input}

De terminal vangt door de gebruiker gegenereerde invoer via twee evenementen:

- **Gegevens evenement (`onData`)**: Treedt op wanneer tekstinvoer plaatsvindt, en stuurt Unicode-tekens.
- **Toets evenement (`onKey`)**: Treedt op voor elke toetsaanslag, inclusief informatie over toetsencodes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze evenementen kunnen worden gebruikt om gebruikersinvoer naar een backend door te geven, UI-elementen bij te werken of aangepaste acties te activeren.

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

Alle door de terminal vastgelegde gebruikersinvoer (zoals van `onData`-evenementen) wordt uitgezonden als UTF-16-tekens.
Als je backend een andere codering verwacht (zoals UTF-8-bytes), moet je de gegevens handmatig transcoderien.

:::info Legacy coderingen
De terminal **ondersteunt geen legacy coderingen** zoals `ISO-8859`.
Als je compatibiliteit nodig hebt met niet-UTF-8-systemen, gebruik dan een externe transcoder (bijvoorbeeld, [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar of van de terminal schrijft of leest.
:::

## Grote gegevensstromen beheren {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan weergeven, houdt hij een interne invoerbuffer bij. Als deze buffer te groot wordt (standaard ongeveer `50MB`), kan nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snelle gegevensbronnen goed te beheren, moet je **stroomcontrole** implementeren.

### Basis voorbeeld van stroomcontrole {#basic-flow-control-example}

Pauzeer je backend totdat de terminal klaar is met het verwerken van een pakket:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Voorbeeld van watermerkstroomcontrole {#watermark-flow-control-example}

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Aanpassing {#customization}

### Terminal opties {#terminal-options}

De `TerminalOptions`-klasse stelt je in staat om het gedrag te configureren:

- Cursor knipperen.
- Lettertype-instellingen (familie, grootte, gewicht).
- Scrollback buffer grootte.
- Regelhoogte en letterspatiëring.
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

Je kunt de terminal opmaken met behulp van `TerminalTheme`, die definieert:

- Achtergrond- en voorgrondkleuren.
- Standaard `ANSI` kleurpalet.
- Cursor- en selectiesachtergrondkleuren.

Voorbeeld:
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

## Ondersteunde sequenties {#supported-sequences}

De terminal ondersteunt een breed scala aan standaard controle-sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstopmaak.

Herkenbare groepen:

- **`C0` controlecodes** (een-byte 7-bits opdrachten, `\x00`, `\x1F`, zoals backspace en line feed)
- **`C1` controlecodes** (een-byte 8-bits opdrachten, `\x80`, `\x9F`)
- **`ESC` sequenties** (beginnend met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitels, hyperlinks en kleuren)

:::info Exotische en aangepaste sequenties beheren
Sommige exotische sequentietypes zoals `APC`, `PM` en `SOS` worden herkend maar stilzwijgend genegeerd.
Aangepaste sequenties kunnen indien nodig worden ondersteund via integraties.
:::

## Stijling {#styling}

<TableBuilder name="Terminal" />
