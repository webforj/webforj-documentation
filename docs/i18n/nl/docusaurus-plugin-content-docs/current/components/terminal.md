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

De `Terminal` component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het beheert tekstuitvoer, gebruikersinvoer, controle-sequenties en schermbuffers, waardoor het geschikt is voor het bouwen van tools voor externe toegang, tekstdashboards, ingebedde opdracht_shells of foutopsporingsconsoles.

<!-- INTRO_END -->

## Een terminal maken {#creating-a-terminal}

:::info Terminal importeren
Om de `Terminal` component in uw app te gebruiken, zorgt u ervoor dat u de volgende afhankelijkheid in uw pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve opdracht_shell met getypte opdrachten, navigatie door de geschiedenis en aangepaste uitvoer.

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

## Hoe het werkt {#how-it-works}

De terminal beheert een raster van tekstcellen, verwerkt binnenkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleurveranderingen en schermwissen.

De kernfunctionaliteiten zijn:

- **Gegevensinvoer**: Het schrijven van gegevens naar de terminal werkt het scherm bij, wat zowel tekst als controle-sequenties afhandelt.
- **Gegevensuitvoer**: Vangt gebruikersinvoer en geeft deze door als gestructureerde gebeurtenissen.
- **Schermbeheer**: Houdt een scrollbare geschiedenisbuffer en de huidige schermstatus bij.
- **Cursorbeheer**: Volgt de cursorpositie voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat deze meerdere-byte tekenreconstructies correct afhandelt en continuïteit waarborgt bij gefragmenteerde invoer.

## Gegevens naar de terminal verzenden {#sending-data-to-the-terminal}

Gegevens worden naar de terminal verzonden met de `write` en `writeln` methoden:

- `write(Object data)`: Stuur gegevens naar de terminalstream.
- `writeln(Object data)`: Stuur gegevens gevolgd door een nieuwe regel.

De terminal verwerkt alle binnenkomende gegevens als **UTF-16**-strings. Het handelt automatisch meerdere-byte tekens af, zelfs wanneer de invoer in gefragmenteerde stukken arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

U kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevensblok is verwerkt:

```java
terminal.write("Lang commando uitvoer", e -> {
  System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen van gebruikersinvoer {#receiving-user-input}

De terminal vangt door de gebruiker gegenereerde invoer via twee evenementen:

- **Gegevensevenement (`onData`)**: Wordt geactiveerd wanneer tekstinvoer plaatsvindt, verzendt Unicode-tekens.
- **Toetsenbordgebeurtenis (`onKey`)**: Wordt geactiveerd bij elke toetsaanslag, inclusief informatie over toetscodes en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

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

Alle door de terminal vastgelegde gebruikersinvoer (zoals van `onData` evenementen) wordt uitgegeven als UTF-16-strings.  
Als uw backend een andere codering verwacht (zoals UTF-8 bytes), moet u de gegevens handmatig transcoden.

:::info Legacy Coderingen
De terminal **ondersteunt geen legacy coderingen** zoals `ISO-8859`.  
Als u compatibiliteit nodig heeft met niet-UTF-8-systemen, gebruik dan een externe transcoder (bijvoorbeeld, [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat u ze naar de terminal schrijft of eruit leest.
:::

## Omgaan met grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet onmiddellijk onbeperkte invoer kan renderen, onderhoudt deze een interne invoerbuffer. Als deze buffer te groot wordt (standaard rond de `50MB`), kunnen nieuwe binnenkomende gegevens worden verworpen om de systeemprestaties te beschermen.

Om snelle gegevensbronnen goed te beheren, moet u **stroomregeling** implementeren.

### Basis stroomregeling voorbeeld {#basic-flow-control-example}

Pauzeer uw backend totdat de terminal klaar is met het verwerken van een blok:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Watermerk stroomregeling voorbeeld {#watermark-flow-control-example}

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Aanpassing {#customization}

### Terminal opties {#terminal-options}

De `TerminalOptions` class stelt u in staat om het gedrag te configureren:

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

### Terminal thema {#terminal-theme}

U kunt de terminal stylen met `TerminalTheme`, dat definieert:

- Achtergrond- en foregroundkleuren.
- Standaard `ANSI` kleurpalet.
- Achtergrondkleuren voor cursor en selectie.

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

De terminal ondersteunt een breed scala aan standaard controle-sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstformatering.

Erkenningsgroepen:

- **`C0` controlecodes** (enkele-byte 7-bit commando's, `\x00`, `\x1F`, zoals terugspoelen en regelvoeding)
- **`C1` controlecodes** (enkele-byte 8-bit commando's, `\x80`, `\x9F`)
- **`ESC` sequenties** (begint met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor bewerkingen zoals scrollen, wissen en stijlen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitels, hyperlinks en kleuren)

:::info Omgaan met Exotische en Aangepaste Sequenties
Sommige exotische sequentietypen zoals `APC`, `PM` en `SOS` worden herkend maar stilzwijgend genegeerd.  
Aangepaste sequenties kunnen worden ondersteund via integraties indien nodig.
:::

## Stijling {#styling}

<TableBuilder name="Terminal" />
