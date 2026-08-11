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

`Terminal`-komponentti on interaktiivinen terminaalisimulaattori, joka käyttäytyy kuten perinteinen järjestelmäkonsoli. Se käsittelee tekstilähtöä, käyttäjän syötettä, ohjaussekvenssejä ja näytön puskureita, mikä tekee siitä sopivan etäyhteystyökalujen, tekstipaneelien, upotettujen komentokonsolien tai virheenkorjauskonsolien rakentamiseen.

<!-- INTRO_END -->

## Luodaan terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Käyttääksesi `Terminal`-komponenttia sovelluksessasi, varmista, että olet ottanut mukaan seuraavan riippuvuuden pom.xml-tiedostossasi.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki rakentaa interaktiivisen komentorivin kirjoitetuilla komennoilla, historiateksteillä ja mukautetulla lähtöarvolla.

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

## Kuinka se toimii {#how-it-works}

Terminaali hallitsee tekstisoluja, käsittelee saapuvia merkkivirtoja ja reagoi käyttäjätoimiin, kuten kirjoittamiseen tai tekstin valitsemiseen. Se tulkitsee automaattisesti ohjausmerkkejä ja pakenemissekvenssejä osoitinkäytölle, värimuutoksille ja näytön tyhjentämiselle.

Keskeiset toiminnat sisältävät:

- **Tietojen syöttö**: Tietojen kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä tekstiä että ohjaussekvenssejä.
- **Tietojen lähtö**: Tallentaa käyttäjän näppäinpainallukset ja lähettää ne strukturoituna tapahtumana.
- **Näytön hallinta**: Säilyttää vieritettävän historian ja nykyisen näytön tilan.
- **Osoittimen hallinta**: Seuraa osoittimen sijaintia tekstisyötössä ja ohjaussekvenssivastauksissa.

Terminaali on tilallinen, mikä tarkoittaa, että se rakentaa oikein monta tavuista merkkejä ja ylläpitää jatkuvuutta fragmentoiduissa syötteissä.

## Datan lähettäminen terminaaliin {#sending-data-to-the-terminal}

Data lähetetään terminaaliin `write`- ja `writeln`-menetelmien avulla:

- `write(Object data)`: Lähettää dataa terminaalivirtaan.
- `writeln(Object data)`: Lähettää dataa, jota seuraa rivinvaihto.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16**-merkkijonoina. Se käsittelee automaattisesti monta tavuista merkkejä, jopa silloin kun syöte saapuu fragmentoituna.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää takaisinsoiton, joka suoritetaan, kun datakuormaa on käsitelty:

```java
terminal.write("Pitkä komennon lähtö", e -> {
  System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali tallentaa käyttäjän tuottaman syötteen kahden tapahtuman kautta:

- **Data-tapahtuma (`onData`)**: Laajentuu kun tekstisyöttö tapahtuu, lähettäen Unicode-merkkejä.
- **Näppäintapahtuma (`onKey`)**: Laajentuu jokaiselle näppäinpainallukselle, mukaan lukien tietoa näppäinkoodista ja muuntajista kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Näitä tapahtumia voidaan käyttää käyttäjän syötteen välittämiseen taustalle, käyttöliittymäelementtien päivittämiseen tai mukautettujen toimintojen käynnistämiseen.

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

Kaikki terminaalissa tallennettu käyttäjän syöte (esimerkiksi `onData`-tapahtumista) lähetetään UTF-16 merkkijonoina.
Jos taustasi odottaa erilaista koodausta (kuten UTF-8-tavupaketteja), sinun on käsiteltävä data manuaalisesti.

:::info Vanhan ajan koodaukset
Terminaali **ei tue vanhan ajan koodauksia** kuten `ISO-8859`.
Jos tarvitset yhteensopivuutta ei-UTF-8-järjestelmien kanssa, käytä ulkoista koodauskonvertteria (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntaaksesi data ennen sen kirjoittamista tai lukemista terminaalista.
:::

## Suurten datavirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi heti renderöidä rajatonta syötettä, se ylläpitää sisäistä syöttöpuskuria. Jos tämä puskuri kasvaa liian suureksi (oletuksena noin `50MB`), uudet saapuvat tiedot voidaan hylätä järjestelmän suorituskyvyn suojaamiseksi.

Jotta hallita nopeasti liikkuvia datalähteitä, sinun tulisi toteuttaa **virranhallinta**.

### Perusvirranhallintaesimerkki {#basic-flow-control-example}

Keskeytä taustasi, kunnes terminaali on käsitellyt tietyn datakuorman:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Vesileima-virranhallintaesimerkki {#watermark-flow-control-example}

Tehokkaampaa hallintaa varten käytä korkea/ matala vesileima:

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

### Terminal-optsiot {#terminal-options}

`TerminalOptions`-luokka antaa sinun konfiguroida käyttäytymistä:

- Osoittimen vilkkuminen.
- Fonttisäädöt (perhe, koko, paino).
- Palautushistorian koko.
- Rivikorkeus ja kirjaimen väli.
- Esteettömyyssäädöt (ruudunlukuohjelman tila).

Esimerkki:
```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

### Terminal-teema {#terminal-theme}

Voit tyylitellä terminaalia käyttämällä `TerminalTheme`, joka määrittelee:

- Tausta- ja etuplanan värit.
- Vakiot `ANSI`-väripaletti.
- Osoittimen ja valinnan taustavärit.

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

Terminaali tukee laajaa valikoimaa vakio-ohjaussekuensseja, joita käytetään osoittimen liikuttamiseen, näytön päivityksiin ja tekstin muotoiluun.

Tunnistetut ryhmät:

- **`C0` ohjauskoodit** (yksi tavu 7-bittiset komennot, `\x00`, `\x1F`, kuten taaksepäin meneminen ja rivinvaihto)
- **`C1` ohjauskoodit** (yksi tavu 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkaen `ESC` (`\x1B`), kuten osoittimen tallentaminen / palauttaminen, näytön kohdistaminen)
- **`CSI` sekvenssit** (Ohjaussekuenssien esittely, `ESC [` tai `CSI (\x9B)`, toiminnoille kuten vierittäminen, tyhjentäminen ja tyylittely)
- **`DCS` sekvenssit** (Laitteen Ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, kuten ikkunan otsikon asettaminen, hyperlinkit ja värit)

:::info Eksoottisten ja mukautettujen sekvenssien käsittely
Joidenkin eksoottisten sekvenssityyppien, kuten `APC`, `PM` ja `SOS`, tunnistavat mutta hiljaisesti ignoroi.
Mukautettuja sekvenssejä voidaan tukea tarvitsematta integraatioita.
:::

## Tyylittely {#styling}

<TableBuilder name="Terminal" />
