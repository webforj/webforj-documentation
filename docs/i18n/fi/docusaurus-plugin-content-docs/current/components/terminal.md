---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Komponentti `Terminal` tarjoaa interaktiivisen terminaali-emuloinnin, joka toimii paljon kuten perinteinen järjestelmäkonsoli. Se mahdollistaa sovellusten näyttävän ja käsittelevän tekstipohjaista käyttöliittymää, käsitellen tekstilähtöä, vastaanottaen käyttäjän syötettä, tulkitsemalla ohjaussekvenssejä ja ylläpitäen näyttöhistoriaa.

Tämä terminaali on suunniteltu tarjoamaan luotettavaa toimintaa erilaisissa käyttötarkoituksissa, kuten etäkäyttötyökalujen rakentamisessa, tekstipohjaisten hallintapaneelien, upotettujen komento-shellien tai interaktiivisten virheenkorjauskonsoleiden luomisessa.

:::info Terminalin tuonti
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

Terminaali hallitsee tekstisolujen ruudukkoa, käsittelee saapuvia merkistöjä ja reagoi käyttäjän toimintoihin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjausmerkit ja pakoteseqvenssit osoittimen liikkumista, väri muuttumista ja näytön tyhjentämistä varten.

Ydinkäyttäytymisiin sisältyy:

- **Datan syöttö**: Datan kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä tekstiä että ohjausseqvenssejä.
- **Datan tuotto**: Tallentaa käyttäjän näppäinyhdistelmät ja lähettää ne rakenteellisina tapahtumina.
- **Näytön hallinta**: Ylläpitää vieritettävää historiaa ja nykyistä näyttötilaa.
- **Osoittimen hallinta**: Seuraa osoittimen sijaintia tekstin syöttöä ja ohjaussekvenssivastauksia varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rekonstruoi oikein monibittiset merkit ja ylläpitää jatkuvuutta fragmentoitujen syötteiden välillä.

## Datavirran lähettäminen terminaaliin {#sending-data-to-the-terminal}

Data lähetetään terminaaliin käyttämällä `write` ja `writeln` menetelmiä:

- `write(Object data)`: Lähettää dataa terminaalivirtaan.
- `writeln(Object data)`: Lähettää dataa, jota seuraa rivinvaihto.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16** merkkijonoina. Se käsittelee automaattisesti monibittiset merkit, vaikka syöttö saapuisi fragmentoituneina paloina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää palautteen, joka suoritetaan, kun datapalautus on käsitelty:

```java
terminal.write("Pitkä komento lähtö", e -> {
    System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali tallentaa käyttäjän tuottaman syötteen kahden tapahtuman kautta:

- **Data Event (`onData`)**: Laukaisee, kun tekstisyöttö tapahtuu, lähettäen Unicode-merkkejä.
- **Key Event (`onKey`)**: Laukaisee jokaisesta näppäinpainalluksesta, mukaan lukien tietoa näppäin-koodeista ja -muokkaimista, kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Nämä tapahtumat voidaan käyttää käyttäjän syötteen välittämiseen taustajärjestelmälle, UI-elementtien päivittämiseen tai mukautettujen toimintojen käynnistämiseen.

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
Jos taustajärjestelmäsi odottaa eri koodausta (kuten UTF-8 tavuina), sinun on käsiteltävä data manuaalisesti.

:::info Vanha koodaus
Terminaali **ei tue vanhoja koodeja** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8 järjestelmien kanssa, käytä ulkoista kääntäjää (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuttaaksesi data ennen sen kirjoittamista tai lukemista terminaalista.
:::

## Suuri datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajatonta syötettä, se ylläpitää sisäistä syöttöbuffettia. Jos tämä bufferi kasvaa liian suureksi (oletusarvo noin `50MB`), uudet saapuvat tiedot voidaan pudottaa järjestelmän suorituskyvyn suojelemiseksi.

Huolellista hallintaa nopeissa datalähteissä varten pitäisi toteuttaa **virtausohjaus**.

### Perus virtausohjauksen esimerkki {#basic-flow-control-example}

Pysäytä taustajärjestelmäsi, kunnes terminaali on käsitellyt datapalautuksen:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Vesileimavirtausohjauksen esimerkki {#watermark-flow-control-example}

Tehokkaampaa hallintaa varten käytä korkea/matala vesileimaa:

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

`TerminalOptions`-luokka mahdollistaa käyttäytymisen konfiguroinnin:

- Osoittimen vilkkuminen.
- Fonttisäännöt (perhe, koko, paksuus).
- Vierityshistorian koko.
- Rivikorkeus ja kirjaintila.
- Esteettömyysasetukset (ruudunluku-tila).

Esimerkki:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminaalitema {#terminal-theme}

Voit tyylitellä terminaalia käyttämällä `TerminalTheme`, joka määrittelee:

- Tausta- ja etu-värit.
- Standardi `ANSI` väriasteikko.
- Osoittimen ja valinnan taustavärit.

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

Terminaali tukee laajaa valikoimaa standardeja ohjausseqvenssejä, joita käytetään osoittimen liikkumiseen, näytön päivityksiin ja tekstin muotoiluun.

Tunnistettuja ryhmiä:

- **`C0` ohjauskoodit** (yksibittiset 7-bittiset komennot, `\x00`, `\x1F`, kuten backspace ja rivinvaihto)
- **`C1` ohjauskoodit** (yksibittiset 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC` (`\x1B`), kuten tallenna/palauta osoitin, näytön tasaaminen)
- **`CSI` sekvenssit** (Ohjaussekvenssien esittelijä, `ESC [` tai `CSI (\x9B)`, toimintojen kuten vierityksen, pyyhkimisen ja tyylittelyn varten)
- **`DCS` sekvenssit** (Laitteen ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, kuten ikkunan otsikon, hyperlinkkien ja värien asettaminen)

:::info Eksoottisten ja mukautettujen sekvenssien käsittely
Jotkut eksoottiset sekvenssityypit, kuten `APC`, `PM` ja `SOS`, tunnistetaan mutta niitä sivuutetaan hiljaa.  
Mukautettuja sekvenssejä voidaan tukea integrointien kautta tarvittaessa.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
