---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) on työkalu, jolla voit suorittaa Java-koodia skripteinä ilman koontitiedostoja, projektin alustamista tai manuaalista kääntämistä. webforJ:n JBang-integraation avulla voit luoda webforJ-sovelluksia nopeasti. Se sopii erityisesti nopeaan prototypointiin, oppimiseen ja pikademoihin ilman täysimittaisen Java-ohjelman perinteisiä riippuvuuksia ja infrastruktuuria.

## Miksi käyttää JBangia webforJ:n kanssa {#why-use-jbang}

Perinteisissä webforJ-projekteissa käytetään Mavenia tai Gradlea, joihin liittyy useita määritystiedostoja ja vakiomuotoinen projektirakenne. Tämä on tuotantosovelluksissa tavallista, mutta voi tuntua raskaalta yksinkertaisissa kokeiluissa tai demoissa.

JBangin avulla voit:

- **Aloittaa heti**: Kirjoita yksi `.java`-tiedosto ja suorita se saman tien
- **Ohittaa projektin alustamisen**: Ei `pom.xml`- tai `build.gradle`-tiedostoa eikä hakemistorakennetta
- **Jakaa helposti**: Lähetä jollekulle yksi tiedosto, jonka hän voi suorittaa yhdellä komennolla
- **Oppia nopeammin**: Keskity webforJ-konsepteihin ilman koontityökalujen monimutkaisuutta

Integraatio sammuttaa palvelimen automaattisesti, kun suljet selaimen välilehden, mikä pitää kehitystyönkulun siistinä.

## Edellytykset {#prerequisites}

### JBangin asentaminen {#install-jbang}

Valitse haluamasi asennustapa:

```bash
# Universal (Linux/macOS/Windows with bash)
curl -Ls https://sh.jbang.dev | bash -s - app setup

# SDKMan
sdk install jbang

# Homebrew (macOS)
brew install jbangdev/tap/jbang

# Chocolatey (Windows)
choco install jbang

# Scoop (Windows)
scoop install jbang
```

Varmista asennus:

```bash
jbang --version
```

:::info[Java-oletusversio]
Kun suoritat JBangin ensimmäisen kerran ilman asennettua JDK:ta, JBang lataa sellaisen automaattisesti. Voit määrittää JDK-version ja toimittajan ennen JBangin suorittamista:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Lisätietoja JBangista]
Kattavat JBang-dokumentit ovat saatavilla seuraavissa lähteissä:
- [JBangin käytön aloittaminen](https://www.jbang.dev/documentation/jbang/latest/index.html) – asennus ja perusteet
- [Skriptidirektiivien viite](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) – kaikki käytettävissä olevat direktiivit
- [Riippuvuudet](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) – riippuvuuksien hallinnan lisäominaisuudet
:::

## webforJ-skriptin luominen {#creating-a-script}

Luo tiedosto nimeltä `HelloWorld.java` ja lisää siihen seuraava sisältö:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Skriptin rakenteen ymmärtäminen {#script-structure}

| Rivi | Tarkoitus |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-rivi, jonka ansiosta skriptin voi suorittaa suoraan Unix-järjestelmissä |
| `//JAVA 21` | Määrittää vaaditun Java-version vähimmäistason; JBang lataa sen tarvittaessa automaattisesti |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Määrittää webforJ JBang -aloituspaketin riippuvuudeksi Maven-koordinaateilla |
| `@SpringBootApplication` | Ottaa Spring Bootin automaattisen määrityksen käyttöön |
| `extends App` | Määrittää tämän luokan webforJ-sovellukseksi |

`webforj-jbang-starter`-riippuvuus sisältää kaiken webforJ-sovelluksen suorittamiseen tarvittavan: Spring Boot -aloituspaketin, kehitystyökalut ja selaimen automaattisen avaamisen.

:::note[Versio]
Korvaa `25.11` webforJ:n uusimmalla versiolla. Tarkista [Maven Centralista](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) uusin julkaisu.
:::
### Riippuvuuksien lisääminen {#adding-dependencies}

Voit lisätä muita Maven-riippuvuuksia useilla `//DEPS`-riveillä:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Riippuvuuksissa käytetään Mavenin vakiomuotoisia koordinaatteja (`groupId:artifactId:version`). JBang noutaa ne automaattisesti Maven Centralista ensimmäisellä suorituskerralla.

## Skriptin suorittaminen {#running-your-script}

Suorita skripti JBangilla:

```bash
jbang HelloWorld.java
```

JBang:

1. Lataa riippuvuudet (vain ensimmäisellä suorituskerralla)
2. Kääntää skriptin
3. Käynnistää upotetun palvelimen satunnaisessa käytettävissä olevassa portissa
4. Avaa sovelluksen oletusselaimessasi

### Skriptin määrittäminen suoritettavaksi {#executable-script}

Unix-järjestelmissä voit määrittää skriptin suoraan suoritettavaksi:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Tämä toimii tiedoston alussa olevan shebang-rivin ansiosta.

## IDE-tuki {#ide-support}

JBang integroituu suosittuihin Java-IDE-ympäristöihin, kuten VS Codeen, IntelliJ IDEAan, Eclipseen ja muihin. Integraatiot tarjoavat muun muassa direktiivien automaattisen täydennyksen, riippuvuuksien automaattisen ratkaisemisen sekä mahdollisuuden suorittaa ja virheenkorjata skriptejä suoraan IDE-ympäristössä.

Katso asennusohjeet ja tuetut editorit [JBangin IDE-integraation dokumentaatiosta](https://www.jbang.dev/documentation/jbang/latest/editing.html).

## Määritys {#configuration}

webforJ JBang -aloituspaketti sisältää skriptikäyttöön optimoidut järkevät oletusarvot. Voit mukauttaa toimintaa järjestelmäominaisuuksien avulla.

### Automaattinen sammutus {#auto-shutdown}

Oletusarvoisesti palvelin sammuu automaattisesti, kun kaikki sovellukseen yhdistetyt selaimen välilehdet suljetaan. Näin kehitystyönkulku pysyy siistinä, eikä käynnissä ole irrallisia palvelimia.

| Ominaisuus | Oletusarvo | Kuvaus |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Ottaa automaattisen sammutuksen käyttöön tai poistaa sen käytöstä |
| `webforj.jbang.idle-timeout` | `5` | Odotusaika sekunteina viimeisen selainyhteyden katkeamisen jälkeen ennen sammutusta |

Voit poistaa automaattisen sammutuksen käytöstä:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Voit muuttaa joutokäynnin aikakatkaisua:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Oletusasetukset {#default-settings}

JBang-aloituspaketti määrittää seuraavat oletusarvot:

| Asetus | Arvo | Kuvaus |
|---------|-------|-------------|
| `server.port` | `0` | Määrittää satunnaisen portin, jotta useiden skriptien suorittaminen ei aiheuta ristiriitoja |
| `server.shutdown` | `immediate` | Nopea sammutus skriptin pikaista päättämistä varten |
| `spring.main.banner-mode` | `off` | Piilottaa Spring Boot -bannerin tulosteen selkeyttämiseksi |
| `logging.level.root` | `ERROR` | Pitää konsolitulosteen siistinä vähentämällä lokitiedot minimiin |
| `logging.level.com.webforj` | `WARN` | Näyttää vain webforJ:n varoitukset ja virheet |
| `webforj.devtools.browser.open` | `true` | Avaa selaimen automaattisesti sovelluksen käynnistyessä |

### Uudelleenkäyttöönotto ja live reload {#development-workflow}

JBang-skriptit eivät tue live reloadia. Näet muutokset seuraavasti:

1. Pysäytä käynnissä oleva skripti (sulje selaimen välilehti tai paina <kbd>Ctrl</kbd>+<kbd>C</kbd>)
2. Muokkaa koodia
3. Suorita `jbang HelloWorld.java` uudelleen

Jos haluat automaattisen uudelleenkäyttöönoton kehityksen aikana, harkitse [täysimittaista Maven-projektia, jossa käytetään Spring DevToolsia](/docs/integrations/spring/spring-boot). Lisätietoja on [live reload -dokumentaatiossa](/docs/configuration/deploy-reload/overview).

## Täysimittaiseen projektiin siirtyminen {#transitioning}

Kun prototyyppisi kasvaa yhtä tiedostoa suuremmaksi, luo varsinainen projekti käyttämällä [startforJ:tä](https://docs.webforj.com/startforj) tai [Maven-arkkityyppiä](./spring/spring-boot#option-2-using-the-command-line). Voit kopioida skriptin logiikan suoraan luodun projektin rakenteeseen.
