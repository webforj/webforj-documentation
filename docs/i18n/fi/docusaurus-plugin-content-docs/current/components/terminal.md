---
title: Terminal
sidebar_position: 126
_i18n_hash: d25dd721593cf2850f9b8c1b7dd742ee
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal`-komponentti on interaktiivinen terminaaliemulaattori, joka käyttäytyy kuin perinteinen järjestelmäkonsoli. Se käsittelee tekstilähtöä, käyttäjän syötettä, ohjaussekvenssejä ja näyttöpuskuria, mikä tekee siitä soveltuvan etäyhteystyökalujen, tekstidashbordien, upotettujen komentokehien tai virheenkorjauskonsolien rakentamiseen.

<!-- INTRO_END -->

## Luodaan terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Jotta voit käyttää `Terminal`-komponenttia sovelluksessasi, varmista, että lisäät seuraavan riippuvuuden pom.xml-tiedostoosi.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki rakentaa interaktiivisen komentokehyksen kirjoitetuilla komennoilla, historianselaamisella ja mukautetulla lähtöselvityksellä.

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
cssURL='/css/terminal/terminal-view.css'
height='400px'
/>

## Kuinka se toimii {#how-it-works}

Terminaali hallitsee tekstisolujen ruudukkoa, käsittelee saapuvia merkistön virtoja ja reagoi käyttäjän toimintoihin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjaushahmot ja pako-sekvenssit kursorin liikuttamiseksi, väriensäätämiseksi ja näytön tyhjentämiseksi.

Keskus käyttäytymiset sisältävät:

- **Tietojen syöttö**: Datan kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä teksti- että ohjaussekin.
- **Tietojen lähtö**: Kaappaa käyttäjän näppäinpainallukset ja tuottaa ne rakenteisina tapahtumina.
- **Näytön hallinta**: Säilyttää vieritettävän historia puskurin ja nykyisen näyttötilan.
- **Kursorin käsittely**: Seuraa kursorin sijaintia tekstisyöttöä ja ohjaussekvenssin vastauksia varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rekonstruoi oikein monibittiset merkit ja ylläpitää jatkuvuutta sirpaleisissa syötteissä.

## Datan lähettäminen terminaaliin {#sending-data-to-the-terminal}

Data lähetetään terminaaliin käyttämällä `write` ja `writeln` -menetelmiä:

- `write(Object data)`: Lähettää dataa terminaalivirtaan.
- `writeln(Object data)`: Lähettää dataa, jota seuraa uusi rivi.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16**-merkkijonoina. Se käsittelee automaattisesti monibittiset merkit, jopa kun syöte saapuu sirpaleisina palasina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää takaisinsoiton, joka suoritetaan, kun data on käsitelty:

```java
terminal.write("Pitkä komento lähtö", e -> {
  System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali kaappaa käyttäjän tuottaman syötteen kahden tapahtuman kautta:

- **Data-tapahtuma (`onData`)**: Käynnistyy, kun tekstiä syötetään, lähettäen Unicode-merkkejä.
- **Näppäintapahtuma (`onKey`)**: Käynnistyy jokaisesta näppäinpainalluksesta, mukaan lukien tiedot näppäinkoodista ja modifierista, kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Näitä tapahtumia voidaan käyttää käyttäjän syötteen välittämiseen taustajärjestelmään, käyttöliittymän elementtien päivittämiseen tai mukautettujen toimintojen laukaisemiseen.

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

Kaikki terminaalien kaappaamat käyttäjän syötteet (kuten `onData`-tapahtumista) lähetetään UTF-16-merkijonoina.  
Jos taustajärjestelmäsi odottaa eri koodausta (kuten UTF-8-tavuja), sinun on koodattava data käsin.

:::info Perinteiset koodaukset
Terminaali **ei tue perinteisiä koodauksia** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8-järjestelmien kanssa, käytä ulkoista kooderitinta (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntaaksesi tai lukemassasi dataa terminaalilta.
:::

## Suurten datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajattomasti syötettä, se ylläpitää sisäistä syötepuskurointia. Jos tämä puskuri kasvaa liian suureksi (oletusarvoisesti noin `50MB`), uudet saapuvat tiedot voidaan pudottaa järjestelmäsuorituskyvyn suojaamiseksi.

Nopeiden datalähteiden hallitsemiseksi sinun tulisi toteuttaa **virtaohjaus**.

### Perusvirtaohjausesimerkki {#basic-flow-control-example}

Pysäytä taustajärjestelmäsi, kunnes terminaali on valmis käsittelemään datan palan:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Watermark-virtaohjausesimerkki {#watermark-flow-control-example}

Tehokkaampaa hallintaa varten käytä korkeita/matalia vesileimauksia:

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

### Terminaaliasetukset {#terminal-options}

`TerminalOptions`-luokka mahdollistaa käyttäytymisen konfiguroimisen:

- Kursorin vilkkuminen.
- Fontin asetukset (perhe, koko, paino).
- Scrollback-puskurin koko.
- Rivikorkeus ja kirjainten väli.
- Esteettömyysohjelmat (näytönlukuohjelman tila).

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

Voit tyylitellä terminaalia käyttämällä `TerminalTheme`, joka määrittelee:

- Taustavärit ja etu-värit.
- Standardi `ANSI`-väripaletti.
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

Terminaali tukee laajaa valikoimaa standardiohjaukseja, joita käytetään kursorin liikuttamiseen, näytön päivityksiin ja tekstin muotoiluun.

Tunnistetut ryhmät:

- **`C0` ohjauskoodit** (yksitimeinen 7-bittinen komento, `\x00`, `\x1F`, kuten taaksepäin ja rivinvaihto)
- **`C1` ohjauskoodit** (yksitimeinen 8-bittinen komento, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC`:llä (`\x1B`), kuten tallenna/palauta kursori, näytön kohdistaminen)
- **`CSI` sekvenssit** (Ohjaussekunttiesitteli, `ESC [` tai `CSI (\x9B)`, operaatioita kuten vieritys, pyyhkiminen ja tyylittely)
- **`DCS` sekvenssit** (Laitehallinta merkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, ikkunan otsikon, hyperlinkkien ja värien asettamiseen)

:::info Eksoottisten ja räätälöityjen sekvenssien käsittely
Joillakin eksoottisilla sekvenssityypeillä, kuten `APC`, `PM` ja `SOS`, on vastineet, mutta ne jätetään huomiotta.  
Räätälöityjä sekvenssejä voidaan varmasti tukea integroimalla, jos tarpeen.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
