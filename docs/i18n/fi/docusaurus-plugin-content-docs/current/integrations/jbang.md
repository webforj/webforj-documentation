---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) on työkalu, jonka avulla voit suorittaa Java-koodia skripteinä ilman rakennustiedostoja, projektin asetuksia tai manuaalista kääntämistä. webforJ JBang -integraatio mahdollistaa webforJ-sovellusten nopean luomisen, mikä sopii parhaiten nopeaan prototyyppien tekemiseen, oppimiseen ja nopeisiin demoesityksiin ilman tarpeettomia perinteisiä riippuvuuksia ja infrastruktuuria täysimittaisessa Java-ohjelmassa.

## Miksi käyttää JBangia webforJ:n kanssa {#why-use-jbang}

Perinteiset webforJ-projektit käyttävät Mavenia tai Gradlea, joissa on useita konfiguraatiotiedostoja ja standardi projektirakenne. Tämä asetelma on vakiintunut tuotantosovelluksille, mutta se voi tuntua raskaalta yksinkertaisille kokeille tai demoesityksille.

JBangin avulla voit:

- **Aloittaa heti**: Kirjoita yksi `.java`-tiedosto ja suorita se välittömästi
- **Hyväksyä projektiasetukset**: Ei `pom.xml`, ei `build.gradle`, ei hakemistorakennetta
- **Jakaa helposti**: Lähetä jollekin yksi tiedosto, jota he voivat käyttää yhdellä komennolla
- **Oppia nopeammin**: Keskity webforJ-käsitteisiin ilman rakennustyökalujen monimutkaisuutta

Integraatio sisältää automaattisen palvelimen sammutuksen, kun suljet selainvälilehden, mikä pitää kehitystyön virtasi puhtaana.

## Ennakkoedellytykset {#prerequisites}

### Asenna JBang {#install-jbang}

Valitse haluamasi asennustapa:

```bash
# Yleinen (Linux/macOS/Windows bashilla)
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

Vahvista asennus:

```bash
jbang --version
```

:::info[Oletus Java-versio]
Kun suoritat JBangin ensimmäistä kertaa ilman asennettua JDK:ta, JBang lataa sen automaattisesti. Voit määrittää JDK-version ja toimittajan ennen JBangin suorittamista:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Opettele lisää JBangista]
Kattavan JBang-dokumentaation löytämiseksi katso:
- [JBang Aloitusopas](https://www.jbang.dev/documentation/jbang/latest/index.html) - Asennus ja perusteet
- [Skripti Direktiivit Viittaus](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Kaikki käytettävissä olevat direktiivit
- [Riippuvuudet](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Kehittynyt riippuvuuksien hallinta
:::

## webforJ-skriptin luominen {#creating-a-script}

Luo tiedosto nimeltä `HelloWorld.java` seuraavalla sisällöllä:

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

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Mikä on nimesi?");
  private Button btn = new Button("Sano Hei");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Tervetuloa webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Skriptirakenteen ymmärtäminen {#script-structure}

| Rivi | Tarkoitus |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-rivi, joka mahdollistaa skriptin suorittamisen suoraan Unix-järjestelmissä |
| `//JAVA 21` | Määrittää vaaditun vähimmäis Java-version; JBang lataa sen automaattisesti tarvittaessa |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Ilmoittaa webforJ JBang -starterin riippuvuudeksi käyttäen Maven-koordinaatteja |
| `@SpringBootApplication` | Aktivoi Spring Bootin automaattisen konfiguroinnin |
| `extends App` | Tekee tästä luokasta webforJ-sovelluksen |

`webforj-jbang-starter`-riippuvuus sisältää kaiken tarvittavan webforJ-sovelluksen suorittamiseen: Spring Boot -starterin, kehitystyökalut ja automaattisen selaimen avaamisen.

:::note[Vaihe]
Korvaa `25.11` uusimmalla webforJ-versiolla. Tarkista [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) viimeisimmät julkaisut varten.
:::

### Riippuvuuksien lisääminen {#adding-dependencies}

Voit lisätä lisä Maven-riippuvuuksia useilla `//DEPS`-riveillä:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Riippuvuudet käyttävät standardeja Maven-koordinaatteja (`groupId:artifactId:version`). JBang hankkii ne automaattisesti Maven Centralista ensimmäisellä ajolla.

## Skriptin suorittaminen {#running-your-script}

Suorita skripti JBangilla:

```bash
jbang HelloWorld.java
```

JBang:

1. Lataa riippuvuudet (vain ensimmäisellä ajolla)
2. Kääntää skriptin
3. Käynnistää upotetun palvelimen satunnaiselle saatavilla olevalle portille
4. Avaa oletusselaimesi sovellukseen

### Tee skriptistä suoritettavissa oleva {#executable-script}

Unix-järjestelmissä voit tehdä skriptistä suoraan suoritettavan:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Tämä toimii, koska tiedoston yläosassa on shebang-rivi.

## IDE-tuki {#ide-support}

JBang integroituu suosittuihin Java-IDE:ihin kuten VS Code, IntelliJ IDEA, Eclipse ja muihin. Nämä integraatiot tarjoavat ominaisuuksia, kuten direktiivien automaattisen täydennksen, automaattisen riippuvuuksien ratkaisun sekä mahdollisuuden suorittaa ja virheenkorjata skriptejä suoraan IDE:stä.

Katso [JBang IDE -integraatiosdokumentoida](https://www.jbang.dev/documentation/jbang/latest/editing.html) asetusten ohjeita ja tuettuja editoria.

## Konfigurointi {#configuration}

webforJ JBang -starterissa on järkevät oletusasetukset, jotka on optimoitu skriptejä varten. Voit mukauttaa käyttäytymistä järjestelmäominaisuuksien avulla.

### Automaattinen sammutus {#auto-shutdown}

Oletuksena palvelin sammutetaan automaattisesti, kun kaikki sovellukseen liitetyt selainvälilehdet on suljettu. Tämä pitää kehitystyön virtasi puhtaana, eikä jätä orpoja palvelimia käynnissä.

| Ominaisuus | Oletus | Kuvaus |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Kytke automaattinen sammutus päälle tai pois |
| `webforj.jbang.idle-timeout` | `5` | Sekuntia, odota viimeisen selainyhteyden katkeamisen jälkeen ennen sammutusta |

Poista automaattinen sammutus käytöstä:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Muuta käyttämättömiä aikarajoja:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Oletusasetukset {#default-settings}

JBang-starter konfiguroi seuraavat oletusasetukset:

| Asetus | Arvo | Kuvaus |
|---------|-------|-------------|
| `server.port` | `0` | Satunnainen portin määritys konfliktien välttämiseksi useiden skriptien suorittamisessa |
| `server.shutdown` | `immediate` | Nopea sammutus nopean skriptin päättämiseksi |
| `spring.main.banner-mode` | `off` | Piilottaa Spring Boot -bannerin puhtaamman tulostuksen vuoksi |
| `logging.level.root` | `ERROR` | Minimalistinen lokitus, jotta konsoliin tulostus on puhdasta |
| `logging.level.com.webforj` | `WARN` | Näyttää vain varoitukset ja virheet webforJ:stä |
| `webforj.devtools.browser.open` | `true` | Avaa selaimen automaattisesti, kun sovellus käynnistyy |

### Uudelleenasennus ja live-reload {#development-workflow}

JBang-skriptit eivät tue live-reloadia. Jotta näet muutoksia:

1. Pysäytä käynnissä oleva skripti (sulje selainvälilehti tai paina `Ctrl+C`)
2. Muokkaa koodiasi
3. Suorita `jbang HelloWorld.java` uudelleen

Automaattiseen uudelleenasentamiseen kehityksen aikana harkitse [täysimittaisen Maven-projektin käyttöä Spring DevToolsin kanssa](/docs/integrations/spring/spring-boot). Katso [live-reload dokumentaatio](/docs/configuration/deploy-reload/overview) lisätietoja varten.

## Siirtyminen täysimittaiseen projektiin {#transitioning}

Kun prototyypisi kasvaa yhden tiedoston yli, luo asianmukainen projekti käyttämällä [startforJ](https://docs.webforj.com/startforj) tai [Maven-archetypea](./spring/spring-boot#option-2-using-the-command-line). Voit kopioida skriptisi logiikan suoraan luotuun projektirakenteeseen.
