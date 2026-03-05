---
title: Terminal
sidebar_position: 126
_i18n_hash: 72b2270f024a64687440deef9c6d82c4
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal`-komponentti on interaktiivinen terminaalimallinnus, joka käyttäytyy kuten perinteinen järjestelmäkonsoli. Se käsittelee tekstilähtöä, käyttäjän syötteitä, ohjaussekvenssejä ja näyttöpuskuria, mikä tekee siitä sopivan etäyhteystyökalujen, tekstipaneelien, upotettujen komento-shellien tai virheenkorjauskonsolien rakentamiseen.

<!-- INTRO_END -->

## Luodaan terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Käyttääksesi `Terminal`-komponenttia sovelluksessasi, varmista, että sisällytät seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki rakentaa interaktiivisen komentoshellin, jossa on kirjoitettuja komentoja, historiayhteys ja mukautettu lähtö.

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

## Miten se toimii {#how-it-works}

Terminaali hallitsee tekstisolujen ruudukkoa, käsittelee saapuvia merkkivirtoja ja reagoi käyttäjätoimiin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjauskirjaimet ja pakoteseinät kursorin liikkumista, väri vaihtumista ja näyttöjen tyhjentämistä varten.

Ydin käyttäytymiset sisältävät:

- **Tietojen syöttö**: Datankirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä tekstiä että ohjaussekvenssejä.
- **Tietojen lähtö**: Tallentaa käyttäjäpainalluksia ja lähettää niitä rakenteellisin tapahtumina.
- **Näyttöhallinta**: Pitää yllä vieritettävää historiapuskurointia ja nykyistä näyttötilaa.
- **Kursori käsittely**: Seuraa kurssorin sijaintia tekstinsyöttöä ja ohjaussekvenssivastauksia varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rekonstruoi oikein monibittiset merkit ja ylläpitää jatkuvuutta katkenneissa syötteissä.

## Tietojen lähettäminen terminaaliin {#sending-data-to-the-terminal}

Tietoja lähetetään terminaaliin `write` ja `writeln` -menetelmien avulla:

- `write(Object data)`: Lähettää tietoja terminaalivirtaan.
- `writeln(Object data)`: Lähettää tietoja, jota seuraa uusi rivi.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16** -merkkijonoina. Se käsittelee automaattisesti monibittiset merkit, vaikka syöte saapuu paloina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää palautteen, joka suoritetaan, kun datapalautus on käsitelty:

```java
terminal.write("Long command output", e -> {
    System.out.println("Data processed.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali captures käyttäjähankkeen syötteen kahden tapahtuman kautta:

- **Tietotapahtuma (`onData`)**: Käynnistyy, kun tekstisyöttö tapahtuu, lähettäen Unicode-merkkejä.
- **Näppäintapahtuma (`onKey`)**: Käynnistyy jokaisesta näppäinpainalluksesta, mukaan lukien tietoa näppäinkoodeista ja modifierista kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Nämä tapahtumat voidaan käyttää välittämään käyttäjän syötteitä taustajärjestelmään, päivittämään käyttöliittymän elementtejä tai käynnistämään mukautettuja toimintoja.

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

Kaikki terminaalista siepattu käyttäjän syöte (kuten `onData`-tapahtumista) lähetetään UTF-16 -merkkijonoina.  
Jos taustajärjestelmä odottaa erilaista koodausta (kuten UTF-8 tavuina), sinun on käsiteltävä tiedot käsin.

:::info Vanhat koodaukset
Terminaali **ei tue vanhoja koodauksia** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8 -järjestelmien kanssa, käytä ulkoista kooderinta (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntamaan tiedot ennen niiden kirjoittamista tai lukemista terminaalista.
:::

## Suurten datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajoimatonta syötettä, se ylläpitää sisäistä syöttöpuskurointia. Jos tämä puskurointi kasvaa liian suureksi (oletus noin `50MB`), uudet saapuvat tiedot voidaan hylätä järjestelmän suorituskyvyn suojaamiseksi.

Sopivan hallinnan varmistamiseksi nopeilta datalähteiltä sinun on toteutettava **virtaohjaus**.

### Perusvirtaohjausesimerkki {#basic-flow-control-example}

Pysäytä taustajärjestelmä, kunnes terminaali on valmis käsittelemään datapaluu:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Vesileiman virtaohjausesimerkki {#watermark-flow-control-example}

Tehokkaampaa valvontaa varten käytä korkeita/matalia vesileimoja:

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

## Mukauttaminen {#customization}

### Terminaalivaihtoehdot {#terminal-options}

`TerminalOptions`-luokka mahdollistaa käyttäytymisen määrittämisen:

- Kurssorin vilkkuminen.
- Fonttiasetukset (perhe, koko, paino).
- Vierityspuskuroinnin koko.
- Rivikorkeus ja kirjaintila.
- Esteettömyysasetukset (ruudunlukijatila).

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

Voit muotoilla terminaalia käyttäen `TerminalTheme`, joka määrittelee:

- Tausta- ja etuvalot.
- Vakio `ANSI` -väripaletti.
- Kursorin ja valinnan taustavärit.

Esimerkki:
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

## Tuetut sekvenssit {#supported-sequences}

Terminaali tukee laajaa valikoimaa vakiosuojauksen sekvenssejä, joita käytetään kursorin liikkumiseen, näyttöpäivityksiin ja tekstin muotoiluun.

Tunnustetut ryhmät:

- **`C0` ohjauskoodit** (yksibittiset 7-bittiset komennot, `\x00`, `\x1F`, kuten backspace ja rivinvaihto)
- **`C1` ohjauskoodit** (yksibittiset 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC` (`\x1B`), kuten tallentaminen/palauttaminen, näytön kohdistus)
- **`CSI` sekvenssit** (Ohjaussekvenssin esittäjä, `ESC [` tai `CSI (\x9B)`, toiminnoille kuten vieritys, pyyhkiminen ja tyylittely)
- **`DCS` sekvenssit** (Laitteen ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, ikkunoiden otsikon, hyperlinkkien ja värien asettamiseen)

:::info Eksottisten ja mukautettujen sekvenssien käsittely
Joitan eksottisia sekvenssityyppejä, kuten `APC`, `PM`, ja `SOS` tunnustetaan, mutta niitä ei käsitellä.  
Mukautettuja sekvenssejä voidaan tukea integraatioiden kautta, jos tarpeen.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
