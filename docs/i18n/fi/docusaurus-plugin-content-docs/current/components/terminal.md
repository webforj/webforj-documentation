---
title: Terminal
sidebar_position: 126
_i18n_hash: 4b7abaef79b0511bbbb796171ddd8c07
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal`-komponentti tarjoaa interaktiivisen terminaalielokuvan, joka käyttäytyy aivan kuten perinteinen järjestelmäkonsoli. Se mahdollistaa sovellusten näyttävän ja manipulointi tekstipohjaista käyttöliittymää, joka käsittelee tekstilähtöä, vastaanottaa käyttäjän syötteitä, tulkitsee ohjaussekvenssejä ja ylläpitää näyttöpuskuria.

Tämä terminaali on suunniteltu tarjoamaan luotettavaa käyttäytymistä eri käyttötapauksissa, kuten etäyhteystyökalujen, tekstipohjaisten hallintapaneelien, upotettujen komentokuorien tai interaktiivisten virheenkorjauksenkonsolien rakentamisessa.

:::info Importing Terminal
Käyttääksesi `Terminal`-komponenttia sovelluksessasi, varmista, että sisällytät seuraavan riippuvuuden pom.xml-tiedostoon.

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

## Kuinka se toimii {#how-it-works}

Terminaali hallitsee tekstisolujen verkkoa, käsittelee saapuvia merkkivirtoja ja reagoi käyttäjän toimiin, kuten kirjoittamiseen tai tekstin valitsemiseen. Se tulkitsee automaattisesti ohjausmerkkejä ja pakotesequenceja kursoriin, väriin vaihtoihin ja näytön tyhjentämiseen.

Ydintoiminnot sisältävät:

- **Datasyöttö**: Tietojen kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä tekstiä että ohjaussekvenssejä.
- **Datan ulostulo**: Kaappaa käyttäjän näppäimistön syötteet ja lähettää ne strukturoituna tapahtumina.
- **Näytön hallinta**: Ylläpitää vieritettävää historiaa ja nykyistä näyttötilaa.
- **Kursorin käsittely**: Seuraa kursorin sijaintia tekstisyöttöä ja ohjaussekvenssireaktioita varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rakentaa oikein monibittiset merkit ja ylläpitää jatkuvuutta pirstaloiduissa syötteissä.

## Datan lähettäminen terminaaliin {#sending-data-to-the-terminal}

Data lähetetään terminaaliin käyttäen `write` ja `writeln` -menetelmiä:

- `write(Object data)`: Lähetetään data terminaalivirtaan.
- `writeln(Object data)`: Lähetetään data, jota seuraa uusi rivi.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16**-merkkijonoina. Se käsittelee automaattisesti monibittisiä merkkejä, jopa kun syöte saapuu pirstaloituina osina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää palautteen, joka suoritetaan, kun datapaluu on käsitelty:

```java
terminal.write("Pitkä komentosarjan tulos", e -> {
    System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali kaappaa käyttäjän luonteen syötteen kahden tapahtuman kautta:

- **Data Event (`onData`)**: Käynnistyy, kun tekstisyöttö tapahtuu, lähettäen Unicode-merkit.
- **Key Event (`onKey`)**: Käynnistyy jokaiselle näppäinpainallukselle, mukaan lukien tietoa näppäinkoodeista ja muokkaimista kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Näitä tapahtumia voidaan käyttää käyttäjän syötteen välittämiseen taustajärjestelmään, käyttöliittymäelementtien päivittämiseen tai mukautettujen toimintojen laukaisemiseen.

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

Kaikki terminaalissa kaapattu käyttäjän syöte (kuten `onData` -tapahtumista) lähetetään UTF-16 -merkkijonoina.  
Jos taustajärjestelmä odottaa erilaista koodausta (kuten UTF-8 tavuina), sinun on käsiteltävä data manuaalisesti.

:::info Legacy Encodings
Terminaali **ei tue perinteisiä koodauksia** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8 -järjestelmien kanssa, käytä ulkoista muuntajaa (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntaaksesi tiedot ennen niiden kirjoittamista tai lukemista terminaalilta.
:::

## Suurten datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajatonta syötettä, se ylläpitää sisäistä syötepuskurointia. Jos tämä puskurointi kasvaa liian suureksi (oletettavasti noin `50MB`), uusi saapuva data voidaan pudottaa järjestelmän suorituskyvyn suojelemiseksi.

Nopeiden tietolähteiden asianmukaiseksi hallitsemiseksi sinun on toteutettava **virtaohjaus**.

### Perus virtaohjausesimerkki {#basic-flow-control-example}

Pysäytä taustajärjestelmä, kunnes terminaali on käsitellyt datapaluu:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Vesileima virtaohjausesimerkki {#watermark-flow-control-example}

Tehokkaamman hallinnan takaamiseksi käytä korkeaa/matalan vesileimaa:

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

### Terminaali vaihtoehdot {#terminal-options}

`TerminalOptions`-luokka mahdollistaa käyttäytymisen konfiguroimisen:

- Kursorin vilkkuminen.
- Fonttiasetukset (perhe, koko, paino).
- Puskurointi (scrollback) koko.
- Rivikorkeus ja kirjainten väli.
- Esteettömyysasetukset (näytönlukuohjelmamoodi).

Esimerkki:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminaali teema {#terminal-theme}

Voit tyylitellä terminaalia käyttämällä `TerminalTheme`, joka määrittelee:

- Tausta- ja etuosa värit.
- Vakiot `ANSI`-väripaletit.
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

Terminaali tukee laajaa valikoimaa standardia ohjaussequenjoita, joita käytetään kursorin liikuttamiseen, näytön päivityksiin ja tekstin muotoiluun.

Tunnetut ryhmät:

- **`C0` ohjauskoodit** (yksibittiset 7-bittiset komennot, `\x00`, `\x1F`, kuten backspace ja rivinvaihto)
- **`C1` ohjauskoodit** (yksibittiset 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC` (`\x1B`), kuten kursorin tallentaminen/ palauttaminen, näytön kohdistaminen)
- **`CSI` sekvenssit** (Kontrolliseqvenssin esitttelijä, `ESC [` tai `CSI (\x9B)`, toimintojen kuten vierityksen, tyhjentämisen ja tyylin osalta)
- **`DCS` sekvenssit** (Laitteen ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, ikkunan otsikon, hyperlinkkien ja värien asettamiseen)

:::info Eksoottisten ja mukautettujen sekvenssien käsittely
Joidenkin eksoottisten sekvenssit, kuten `APC`, `PM`, ja `SOS` tunnistetaan, mutta niitä ei käsitellä.  
Mukautettuja sekvenssejä voidaan tukea integraatioiden kautta, jos tarpeen.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
