---
title: Terminal
sidebar_position: 126
_i18n_hash: a4c442c62c748f82d75133db075054af
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal`-komponentti on interaktiivinen terminaaliemulaattori, joka käyttäytyy kuten perinteinen järjestelmäkonsoli. Se käsittelee tekstilähtöä, käyttäjän syötettä, ohjausmerkkejä ja näyttöpuskuria, mikä tekee siitä sopivan etäyhteystoimintojen, tekstidashbordien, upotettujen komentorivien tai virheenkorjauskonsolien rakentamiseen.

<!-- INTRO_END -->

## Luodaan terminaali {#creating-a-terminal}

:::info Terminalin tuonti
Käyttääksesi `Terminal`-komponenttia sovelluksessasi, varmista, että olet lisännyt seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

Seuraava esimerkki rakentaa interaktiivisen komentorivin, jossa on kirjoitettuja komentoja, historiaseikkailua ja mukautettua lähtöä.

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

Terminaali hallitsee tekstisolujen ruudukkoa, käsittelee saapuvia merkistöitä ja reagoi käyttäjän toimiin, kuten kirjoittamiseen tai tekstin valintaan. Se tulkitsee automaattisesti ohjausmerkkejä ja pakomerkkejä, jotka liittyvät kursorin liikkumiseen, värin muutokseen ja näytön tyhjentämiseen.

Peruskäyttäytyminen sisältää:

- **Data Input**: Tietojen kirjoittaminen terminaaliin päivittää näyttöä, käsitellen sekä tekstiä että ohjausmerkkejä.
- **Data Output**: Tallentaa käyttäjän näppäilyt ja lähettää ne rakenteellisina tapahtumina.
- **Näytön hallinta**: Säilyttää vieritettävän historiaosan ja nykyisen näyttötilan.
- **Kursoria käsittely**: Seuraa kursorin sijaintia tekstinsyöttöä ja ohjausmerkkejä varten.

Terminaali on tilallinen, mikä tarkoittaa, että se rekonstruoi oikein monibittiset merkit ja ylläpitää jatkuvuutta fragmentoitujen syötteiden välillä.

## Tietojen lähettäminen terminaaliin {#sending-data-to-the-terminal}

Tietoja lähetetään terminaaliin `write`- ja `writeln`-menetelmien avulla:

- `write(Object data)`: Lähettää tietoja terminaalivirtaan.
- `writeln(Object data)`: Lähettää tietoja uuden rivin kanssa.

Terminaali käsittelee kaikki saapuvat tiedot **UTF-16** merkkijonoina. Se käsittelee automaattisesti monibittisiä merkkejä, vaikka syöte saapuu fragmentoituna.

### Esimerkki {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

Voit myös liittää palautteen, joka suoritetaan, kun tietomäärä on käsitelty:

```java
terminal.write("Pitkä komennon lähtö", e -> {
  System.out.println("Data käsitelty.");
});
```

## Käyttäjän syötteen vastaanottaminen {#receiving-user-input}

Terminaali tallentaa käyttäjän tuottaman sisällön kahden tapahtuman kautta:

- **Data Event (`onData`)**: Tulipalo kun tekstisyöttö tapahtuu, lähettäen Unicode-merkkejä.
- **Key Event (`onKey`)**: Tulipalo jokaisen näppäimen painalluksessa, sisältäen tietoa näppäinkoodista ja modifieroista kuten <kbd>Ctrl</kbd> tai <kbd>Alt</kbd>.

Näitä tapahtumia voidaan käyttää käyttäjän syötteen välittämiseen taustajärjestelmälle, käyttöliittymäelementtien päivittämiseen tai mukautettujen toimintojen käynnistämiseen.

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

Kaikki terminaalilta vangittu käyttäjän syöte (kuten `onData`-tapahtumista) lähetetään UTF-16 merkkijonoina.  
Jos taustajärjestelmäsi odottaa eri koodisarjaa (kuten UTF-8 tavuina), sinun on muunnettava tiedot käsin.

:::info Perinteiset koodisarjat
Terminaali **ei tue perinteisiä koodisarjoja** kuten `ISO-8859`.  
Jos tarvitset yhteensopivuutta ei-UTF-8-järjestelmien kanssa, käytä ulkoista muunninta (esimerkiksi [`luit`](https://linux.die.net/man/1/luit) tai [`iconv`](https://en.wikipedia.org/wiki/Iconv)) muuntaaksesi tiedot ennen niiden kirjoittamista tai lukemista terminaalilta.
:::

## Suurten tietovirtojen käsittely {#handling-large-data-streams}

Koska terminaali ei voi välittömästi renderöidä rajatonta syötettä, se ylläpitää sisäistä syöttöpuskurointia. Jos tämä puskurikasvaa liian suureksi (oletusarvoisesti noin `50MB`), uudet saapuvat tiedot voidaan hylätä järjestelmän suorituskyvyn suojelemiseksi.

Nopeiden tietolähteiden hallitsemiseksi sinun tulisi toteuttaa **virranhallinta**.

### Perusvirranhallintaesimerkki {#basic-flow-control-example}

Pysäytä taustajärjestelmäsi, kunnes terminaali on valmis käsittelemään osan:

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Vesileimana virranhallintaesimerkki {#watermark-flow-control-example}

Tehokkaamman hallinnan vuoksi käytä korkea/matala vesileimaa:

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

- Kursori vilkkuminen.
- Fonttiasetukset (perhe, koko, paino).
- Vierityspuskuri koko.
- Rivikorkeus ja kirjainten väli.
- Esteettömyysasetukset (näytönlukijan tila).

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

Voit tyylitellä terminaalia käyttäen `TerminalTheme`, joka määrittää:

- Tausta- ja etu värit.
- Vakiot `ANSI` väri paletti.
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

Terminaali tukee laajaa valikoimaa vakiokontrollisekvenssejä, joita käytetään kursorin siirtoon, näyttöpäivityksiin ja tekstiformaattiin.

Tunnetut ryhmät:

- **`C0` ohjauskoodit** (yksi-bittiset 7-bittiset komennot, `\x00`, `\x1F`, kuten backspace ja rivinvaihto)
- **`C1` ohjauskoodit** (yksi-bittiset 8-bittiset komennot, `\x80`, `\x9F`)
- **`ESC` sekvenssit** (alkaen `ESC` (`\x1B`), kuten tallenna/palauta kursori, näytön kohdistus)
- **`CSI` sekvenssit** (Kontrollisekvenssin esittely, `ESC [` tai `CSI (\x9B)`, kuten vierittämiseen, pyyhkimiseen ja tyylittelyyn)
- **`DCS` sekvenssit** (Laitteen ohjausmerkit, `ESC P` tai `DCS (\x90)`)
- **`OSC` sekvenssit** (Käyttöjärjestelmän komennot, `ESC ]` tai `OSC (\x9D)`, esimerkiksi ikkunan otsikon, hyperlinkkien ja värien asettamiseen)

:::info Eksoottisten ja mukautettujen sekvenssien käsittely
Joitan eksoottisia sekvenssityyppejä kuten `APC`, `PM` ja `SOS` tunnistetaan, mutta niitä ei huomioida.  
Mukautettuja sekvenssejä voidaan tukea integraatioiden kautta tarvittaessa.
:::

## Tyylittäminen {#styling}

<TableBuilder name="Terminal" />
