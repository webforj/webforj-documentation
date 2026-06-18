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

`Terminal`-komponentti on interaktiivinen terminaaliemulaattori, joka toimii perinteisen järjestelmäkonsolin tavoin. Se käsittelee tekstilähtöä, käyttäjän syötteitä, ohjausjonoja ja näytön puskureita, mikä tekee siitä soveltuvan etäyhteistyövälineiden, tekstipohjaisten hallintapaneelien, upotettujen komentorivien tai virheenkorjaus konsolien rakentamiseen.

<!-- INTRO_END -->

## Luodaan terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Voit käyttää `Terminal`-komponenttia sovelluksessasi, varmista, että sisällytät seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki luo interaktiivisen komentorivin kirjoitettujen komentojen, historian navigoinnin ja mukautetun lähtösyötteen kanssa.

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

## Kuinka se toimii {#how-it-works}

Terminaali hallitsee tekstisolujen verkkoa, käsittelee saapuvia merkkivirtoja ja reagoi käyttäjän toimiin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjausmerkkejä ja pakojaksoja kohdistimen liikuttamiseksi, väri muutoksiksi ja näytön tyhjentämiseksi.

Ydin käyttäytymiset ovat:

- **Tietojen syöttö**: Aineiston kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä teksti- että ohjausjaksoja.
- **Tietojen lähtö**: Taltioi käyttäjän painalluksia ja lähettää ne rakenteellisina tapahtumia.
- **Näytön hallinta**: Säilyttää vieritettävän historian puskurin ja nykyisen näytön tilan.
- **Kohdistimen käsittely**: Seuraa kohdistimen sijaintia tekstinsyöttöä ja ohjausjaksojen vastauksia varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rakentaa oikein monibittiset merkit ja ylläpitää jatkuvuutta sirpaleisissa syötteissä.

## Tietojen lähettäminen terminaaliin {#sending-data-to-the-terminal}

Tietoja lähetetään terminaaliin käyttäen `write` ja `writeln` -menetelmiä:

- `write(Object data)`: Lähettää tietoja terminaalivirtaan.
- `writeln(Object data)`: Lähettää tietoja rivinvaihdon kera.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16** merkkijonoina. Se käsittelee automaattisesti monibittisiä merkkejä, vaikka syöte saapuisikin sirpaleina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää takaisinsoiton, joka suoritetaan, kun tietojakso on käsitelty:

```java
terminal.write("Pitkä komento lähtö", e -> {
  System.out.println("Tiedot käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali tallentaa käyttäjän luoman syötteen kahden tapahtuman kautta:

- **Tietotapahtuma (`onData`)**: Käynnistyy, kun tekstisyöttö tapahtuu, ja lähettää Unicode-merkkejä.
- **Näppäimistötapahtuma (`onKey`)**: Käynnistyy jokaiselle näppäinpainallukselle, mukaan lukien tiedot näppäinkoodista ja muokkaimista kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Nämä tapahtumat voidaan käyttää välittämään käyttäjän syöte backendiin, päivittämään UI-elementtejä tai laukaisemaan mukautettuja toimintoja.

### Esimerkki {#example-1}
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

Kaikki terminaalin tallentama käyttäjän syöte (kuten `onData` tapahtumista) lähetetään UTF-16 merkkijonoina.  
Jos backend odottaa erilaista koodausta (kuten UTF-8 tavuina), sinun on manuaalisesti muunnettava tiedot.

:::info Perintökoodaukset
Terminaali **ei tue perintökoodauksia**, kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8 järjestelmien kanssa, käytä ulkoista muunninta (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) tietojen muuntamiseksi ennen niiden kirjoittamista tai lukemista terminaalista.
:::

## Suurten tietovirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajatonta syötettä, se ylläpitää sisäistä syöttöpuskurointia. Jos tämä puskuri kasvaa liian suureksi (oletus noin `50 MB`), uudet saapuvat tiedot saattavat jäädä huomiotta suojaamaan järjestelmän suorituskykyä.

Nopeiden tietolähteiden hallitsemiseksi sinun tulisi toteuttaa **virtaohjaus**.

### Perusvirtaohjausesimerkki {#basic-flow-control-example}

Pysäytä backendisi, kunnes terminaali on käsitellyt tiedonjako:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Vedenjakajavirtaohjausesimerkki {#watermark-flow-control-example}

Tehokkaampaa hallintaa varten käytä korkeita/matalia vedenjakajia:

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

## Mukauttaminen {#customization}

### Terminaalivaihtoehdot {#terminal-options}

`TerminalOptions`-luokka mahdollistaa käyttäytymisen konfiguroinnin:

- Kohdistimen vilkkuminen.
- Fonttitiedot (perhe, koko, paino).
- Vierityspuskuri koko.
- Rivikorkeus ja kirjaimen väli.
- Esteettömyysasetukset (näytönluettava tila).

Esimerkki:
```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

### Terminaaliteema {#terminal-theme}

Voit muotoilla terminaalia käyttämällä `TerminalTheme`, joka määrittää:

- Taustaväri ja etualaväri.
- Normaali `ANSI` väri palette.
- Kohdistimen ja valinnan taustavärit.

Esimerkki:
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

## Tuetut sekvenssit {#supported-sequences}

Terminaali tukee laajaa valikoimaa standardin ohjausjaksoja, joita käytetään kohdistimen liikuttamiseen, näyttöpäivityksiin ja tekstin muotoiluun.

Tunnistetuista ryhmistä:

- **`C0` ohjauskoodit** (yksittäiset tavut 7-bittiset komennot, `\x00`, `\x1F`, kuten taaksepäin ja rivinvaihto)
- **`C1` ohjauskoodit** (yksittäiset tavut 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC` (`\x1B`), kuten kohdistimen tallentaminen/palauttaminen, näytön kohdistaminen)
- **`CSI` sekvenssit** (Ohjaus sekvenssin esittimen, `ESC [` tai `CSI (\x9B)`, toiminnot kuten vieritys, tyhjennys ja tyylittely)
- **`DCS` sekvenssit** (Laiteohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, ikkunoiden otsikoiden, hyperlinkkien ja värien asettamiseksi)

:::info Eksoottisten ja Mukautettujen Sekvenssien Käsittely
Jotkin eksoottiset sekvenssityypit, kuten `APC`,`PM` ja `SOS`, tunnistetaan, mutta niitä ei oteta huomioon.  
Mukautettuja sekvenssejä voidaan tukea integroimalla, jos tarpeen.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
