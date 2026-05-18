---
title: Terminal
sidebar_position: 126
_i18n_hash: 513f4970da96e2e9f36a80739e60cd9c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

De `Terminal` component is een interactieve terminalemulator die zich gedraagt als een traditionele systeemconsole. Het beheert tekstuitvoer, gebruikersinvoer, controle- en escape-sequenties, en schermbuffers, waardoor het geschikt is voor het bouwen van hulpmiddelen voor externe toegang, tekstdashboards, ingebedde commando-shells of debugconsoles.

<!-- INTRO_END -->

## Een terminal aanmaken {#creating-a-terminal}

:::info Terminal importeren
Om de `Terminal` component in je app te gebruiken, zorg ervoor dat je de volgende afhankelijkheid in je pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Het volgende voorbeeld bouwt een interactieve opdracht-shell met getypte opdrachten, navigatie in de geschiedenis en aangepaste uitvoer.

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

## Hoe het werkt {#how-it-works}

De terminal beheert een grid van tekstcellen, verwerkt inkomende tekenstromen en reageert op gebruikersacties zoals typen of tekst selecteren. Het interpreteert automatisch controlekarakters en escape-sequenties voor cursorbeweging, kleuraanpassingen en het wissen van het scherm.

De kernfunctionaliteiten omvatten:

- **Gegevensinvoer**: Het schrijven van gegevens naar de terminal werkt het scherm bij, waarbij zowel tekst als controle-sequenties worden verwerkt.
- **Gegevensuitvoer**: Vangt gebruikersinvoer van toetsen en geeft deze weer als gestructureerde gebeurtenissen.
- **Schermbeheer**: Behoudt een scrollbare geschiedenisbuffer en de huidige schermstatus.
- **Cursorbeheer**: Houdt de cursorpositie bij voor tekstinvoer en reacties op controle-sequenties.

De terminal is stateful, wat betekent dat deze multibyte-tekens goed reconstrueert en continuïteit behoudt bij gefragmenteerde invoer.

## Gegevens naar de terminal sturen {#sending-data-to-the-terminal}

Gegevens worden naar de terminal gestuurd met de methoden `write` en `writeln`:

- `write(Object data)`: Stuurt gegevens in de terminalstroom.
- `writeln(Object data)`: Stuurt gegevens gevolgd door een newline.

De terminal verwerkt alle inkomende gegevens als **UTF-16**-strings. Het verwerkt automatisch multibyte-tekens, zelfs wanneer de invoer in gefragmenteerde stukken arriveert.

### Voorbeeld {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Klaar.");
```

Je kunt ook een callback toevoegen die wordt uitgevoerd zodra het gegevensfragment is verwerkt:

```java
terminal.write("Lang uitvoer van opdracht", e -> {
  System.out.println("Gegevens verwerkt.");
});
```

## Ontvangen van gebruikersinvoer {#receiving-user-input}

De terminal vangt door de gebruiker gegenereerde invoer via twee evenementen:

- **Gegevens evenement (`onData`)**: Vindt plaats wanneer textinvoer plaatsvindt, met Unicode-tekens.
- **Toetsenbord evenement (`onKey`)**: Vindt plaats bij elke toetsdruk, inclusief informatie over toetsaanslagen en modifiers zoals <kbd>Ctrl</kbd> of <kbd>Alt</kbd>.

Deze evenementen kunnen worden gebruikt om gebruikersinvoer naar een backend door te geven, UI-elementen bij te werken, of aangepaste acties te activeren.

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

Alle door de terminal vastgelegde gebruikersinvoer (zoals van `onData`-evenementen) wordt uitgezonden als UTF-16-strings.  
Als je backend een andere codering verwacht (zoals UTF-8-bytes), moet je de gegevens handmatig transcoderend.

:::info Legacy encodings
De terminal **ondersteunt geen legacy coderingen** zoals `ISO-8859`.  
Als je compatibiliteit nodig hebt met niet-UTF-8-systemen, gebruik dan een externe transcoder (bijvoorbeeld [`luit`](https://linux.die.net/man/1/luit) of [`iconv`](https://en.wikipedia.org/wiki/Iconv)) om de gegevens te converteren voordat je ze naar de terminal schrijft of deze uitleest.
:::

## Behandelen van grote gegevensstromen {#handling-large-data-streams}

Omdat de terminal niet in staat is om onbeperkte invoer onmiddellijk weer te geven, onderhoudt het een interne invoerbuffer. Als deze buffer te groot groeit (standaard ongeveer `50MB`), kunnen nieuwe inkomende gegevens worden verworpen om de systeemperformance te beschermen.

Om snel datastromen goed te beheren, moet je **flow control** implementeren.

### Basisvoorbeeld van flow control {#basic-flow-control-example}

Pauzeer je backend totdat de terminal de verwerking van een fragment heeft voltooid:

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Aanpassing {#customization}

### Terminal opties {#terminal-options}

De `TerminalOptions` klasse stelt je in staat om gedrag te configureren:

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

Je kunt de terminal stylen met `TerminalTheme`, dat het volgende definieert:

- Achtergrond- en voorgrondkleuren.
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
path='/webforj/terminalthemepicker'
files={['src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java']}
height='500px'
/>

## Ondersteunde sequenties {#supported-sequences}

De terminal ondersteunt een breed scala aan standaard controle sequenties die worden gebruikt voor cursorbeweging, schermupdates en tekstformattering.

Herkenbare groepen:

- **`C0` controlecodes** (enkele byte 7-bits commando's, `\x00`, `\x1F`, zoals backspace en line feed)
- **`C1` controlecodes** (enkele byte 8-bits commando's, `\x80`, `\x9F`)
- **`ESC` sequenties** (beginnen met `ESC` (`\x1B`), zoals cursor opslaan/herstellen, schermuitlijning)
- **`CSI` sequenties** (Control Sequence Introducer, `ESC [` of `CSI (\x9B)`, voor operaties zoals scrollen, wissen, en stylen)
- **`DCS` sequenties** (Device Control Strings, `ESC P` of `DCS (\x90)`)
- **`OSC` sequenties** (Operating System Commands, `ESC ]` of `OSC (\x9D)`, voor het instellen van venstertitel, hyperlinks, en kleuren)

:::info Exotic en aangepaste sequenties afhandelen
Sommige exotische sequentietypes zoals `APC`,`PM`, en `SOS` worden herkend maar stilletjes genegeerd.  
Aangepaste sequenties kunnen indien nodig worden ondersteund via integraties.
:::

## Styling {#styling}

<TableBuilder name="Terminal" />
