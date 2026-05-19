---
title: Terminal
sidebar_position: 126
_i18n_hash: 513f4970da96e2e9f36a80739e60cd9c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal`-komponentti on interaktiivinen terminaalimalline, joka käyttäytyy kuten perinteinen järjestelmäkonsoli. Se käsittelee tekstilähtöä, käyttäjän syötettä, ohjaussekvenssejä ja näyttöpuskureita, mikä tekee siitä sopivan etäyhteystyökalujen, tekstinäyttöjen, upotettujen komentokehien tai vianetsintäkonsolien rakentamiseen.

<!-- INTRO_END -->

## Luo terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Voit käyttää `Terminal`-komponenttia sovelluksessasi varmistamalla, että sisällytät seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki rakentaa interaktiivisen komentokehän kirjoitettuina komennoilla, histori navigoinnilla ja mukautetulla lähdöllä.

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

## Kuinka se toimii {#how-it-works}

Terminaali hallitsee tekstisolujen ruudukkoa, käsittelee saapuvia merkistön virtoja ja reagoi käyttäjätoimiin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjausmerkkejä ja pakonopeuksia suurennuslasin liikuttamiseen, värimuutoksiin ja näytön tyhjentämiseen.

Keskeiset toiminnot sisältävät:

- **Tieto syöttö**: kirjoittamalla dataa terminaaliin, näyttö päivittyy ottaen huomioon sekä teksti- että ohjaussekvenssit.
- **Tieto lähtö**: tallentaa käyttäjän näppäinpainallukset ja lähettää ne rakenteisina tapahtumina.
- **Näytön hallinta**: ylläpitää vieritettävää historiapuskurointia ja nykyistä näytön tilaa.
- **Kursori hallinta**: seuraa kursorin sijaintia tekstisyöttöä ja ohjaussekvenssien vastauksia varten.

Terminaali on tilallinen, mikä tarkoittaa, että se kootaan oikein monibittiset merkit ja ylläpitää jatkuvuutta fragmentoituneessa syötteessä.

## Datan lähettäminen terminaaliin {#sending-data-to-the-terminal}

Data lähetetään terminaaliin käyttämällä `write` ja `writeln` -metodeja:

- `write(Object data)`: lähettää tietoa terminaalivirtaan.
- `writeln(Object data)`: lähettää tietoa, jota seuraa uusi rivi.

Terminaali käsittelee kaiken saapuvan datan **UTF-16** -merkkijonoina. Se käsittelee automaattisesti monibittiset merkit, jopa silloin kun syöte saapuu fragmentoituneina palasina.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Valmis.");
```

Voit myös liittää palautekutsun, joka suoritetaan heti, kun datapalja on käsitelty:

```java
terminal.write("Pitkä komennon lähtö", e -> {
  System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali tallentaa käyttäjän luoman syötteen kahden tapahtuman kautta:

- **Datan tapahtuma (`onData`)**: lauettuaan, kun tekstisyöttö tapahtuu, lähettäen Unicode-merkkejä.
- **Näppäintapahtuma (`onKey`)**: lauettaa jokaiselle näppäinpainallukselle, mukaan lukien tietoa näppäinkoodista ja muuntimista, kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Näitä tapahtumia voidaan käyttää käyttäjän syötteen välittämiseen taustapalvelimeen, käyttöliittymäelementtien päivittämiseen tai mukautettujen toimintojen käynnistämiseen.

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

Kaikki terminaalin kiinniottamat käyttäjän syötteet (kuten `onData`-tapahtumista) lähetetään UTF-16-merkkeinä.  
Jos taustapalvelimesi odottaa eri koodausta (kuten UTF-8 -tavuna), sinun on manuaalisesti muunneltava dataa.

:::info Vanhanajan koodaukset
Terminaali **ei tue vanhanajan koodauksia** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8 -järjestelmien kanssa, käytä ulkoista muunninta (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntaaksesi data ensin ennen sen kirjoittamista tai lukemista terminaalista.
:::

## Suurten datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajatonta syötettä, se ylläpitää sisäistä syötepuskurointia. Jos tämä puskuri kasvaa liian suureksi (oletusarvo noin `50MB`), uusi saapuva data voidaan pudottaa järjestelmän suorituskyvyn suojaamiseksi.

Nopeiden tietolähteiden asianmukaiseksi hallitsemiseksi sinun tulisi toteuttaa **virtaohjaus**.

### Perusvirran ohjausesimerkki {#basic-flow-control-example}

Pysäytä taustapalvelin, kunnes terminaali on valmis käsittelemään paljan:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Vedenjakajavirra ohjausesimerkki {#watermark-flow-control-example}

Tehokkaamman hallinnan vuoksi käytä korkeaa/matalan vedenjakajaa:

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

### Terminaali asetukset {#terminal-options}

`TerminalOptions`-luokka mahdollistaa käyttäytymisen määrittämisen:

- Kursorin vilkkuminen.
- Fonttiasetukset (perhe, koko, paino).
- Välitön puskurin koko.
- Riviväli ja kirjamäärä.
- Esteettömyysasetukset (näyttöruudun lukija tila).

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

- Tausta- ja etu värit.
- Standardi `ANSI` -väripaletti.
- Kursorin ja valinnan taustavärit.

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

Terminaali tukee laajaa valikoimaa vakiokontrollisekvenssejä, joita käytetään kursorin liikuttamiseen, näytön päivityksiin ja tekstimuotoiluun.

Tunnistettu ryhmät:

- **`C0` ohjauskoodit** (yksisäikeiset 7-bittiset komennot, `\x00`, `\x1F`, kuten backspace ja rivinvaihto)
- **`C1` ohjauskoodit** (yksisäikeiset 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkavat `ESC` (`\x1B`), kuten kursorin tallentaminen/palauttaminen, näytön kohdistus)
- **`CSI` sekvenssit** (Ohjaussekvenssin esittelijä, `ESC [` tai `CSI (\x9B)`, operaatioihin kuten vierittäminen, poistaminen ja tyylitys)
- **`DCS` sekvenssit** (Laitteen ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, kuten ikkunan otsikon, hyperlinkkien ja värien asettaminen)

:::info Eksoottisten ja mukautettujen sekvenssien käsittely
Jotkin eksoottiset sekvenssityypit, kuten `APC`, `PM` ja `SOS`, tunnistetaan, mutta niitä ei käsitellä.  
Mukautettuja sekvenssejä voidaan tukea integrointien kautta tarvittaessa.
:::

## Tyylitys {#styling}

<TableBuilder name="Terminal" />
