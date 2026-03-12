---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: 3e783061967931c25ff55499a3139122
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) on työkalu, joka mahdollistaa Java-koodin suorittamisen skripteinä ilman build-tiedostoja, projektin asetuksia tai manuaalista käännöstä. webforJ JBang -integraatio mahdollistaa webforJ-sovellusten nopean luomisen, mikä sopii parhaiten nopeaan prototyyppaukseen, oppimiseen ja nopeisiin demonstrointeihin ilman tarvetta perinteisille riippuvuuksille ja infrastruktuurille täysimittaisessa Java-ohjelmassa.

## Miksi käyttää JBangia webforJ:n kanssa {#why-use-jbang}

Perinteiset webforJ-projektit käyttävät Mavenia tai Gradlea useilla konfiguraatiotiedostoilla ja standardilla projektirakenteella. Tämä asettelu on tavanomainen tuotantosovelluksille, mutta voi tuntua raskaalta yksinkertaisille kokeiluille tai demonstroinneille.

JBangin avulla voit:

- **Aloittaa heti**: Kirjoita yksi `.java`-tiedosto ja suorita se heti
- **Ohittaa projektin asetuksen**: Ei `pom.xml`, ei `build.gradle`, ei hakemistorakennetta
- **Jakaa helposti**: Lähetä jollekin yksi tiedosto, jonka hän voi suorittaa yhdellä komennolla
- **Oppia nopeammin**: Keskity webforJ-konsepteihin ilman build-työkalujen monimutkaisuutta

Integraatio sisältää automaattisen palvelimen sammutuksen, kun suljet selainvälilehden, pitäen kehitystyösi siistinä.

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

Varmista asennus:

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

:::tip[Opi lisää JBangista]
Katso kattava JBang-dokumentaatio:
- [JBang Aloitusopas](https://www.jbang.dev/documentation/jbang/latest/index.html) - Asennus ja perusteet
- [Skripti-direktiivien viite](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Kaikki käytettävissä olevat direktiivit
- [Riippuvuudet](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Edistyksellinen riippuvuuden hallinta
:::

## WebforJ-skripti luominen {#creating-a-script}

Luo tiedosto nimeltä `HelloWorld.java`, jossa on seuraava sisältö:

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

### Skriptin rakenteen ymmärtäminen {#script-structure}

| Rivi | Tavoite |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-rivi, joka mahdollistaa skriptin suorittamisen suoraan Unix-järjestelmissä |
| `//JAVA 21` | Määrittää vähimmäis Java-version, joka vaaditaan; JBang lataa sen automaattisesti tarvittaessa |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Julistaa webforJ JBang -starterin riippuvuudeksi Maven-koodistolla |
| `@SpringBootApplication` | Mahdollistaa Spring Bootin automaattisen konfiguroinnin |
| `extends App` | Tekee tästä luokasta webforJ-sovelluksen |

`webforj-jbang-starter`-riippuvuus sisältää kaiken, mitä tarvitset webforJ-sovelluksen suorittamiseen: Spring Boot -starterin, kehitystyökalut ja automaattisen selaimen avaamisen.

:::note[Versio]
Korvaa `25.11` uusimmalla webforJ-versiolla. Tarkista [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) viimeisimmän julkaisun vuoksi.
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

Riippuvuudet käyttävät standardeja Maven-koordinaatteja (`groupId:artifactId:version`). JBang noutaa ne automaattisesti Maven Centralista ensikäynnillä.

## Skriptisi suorittaminen {#running-your-script}

Suorita skripti JBangilla:

```bash
jbang HelloWorld.java
```

JBang tekee seuraavaa:

1. Lataa riippuvuudet (ensimmäisellä käynnillä)
2. Kääntää skriptin
3. Käynnistää upotetun palvelimen satunnaisella käytettävissä olevalla portilla
4. Avaa oletusselaimesi sovelluksessa

### Skriptin tekeminen suoritettavaksi {#executable-script}

Unix-järjestelmissä voit tehdä skriptistä suoraan suoritettavan:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Tämä toimii sen vuoksi, että tiedoston yläreunassa on shebang-rivi.

## IDE-tuki {#ide-support}

JBang integroituu suosittuihin Java-IDE:ihin, mukaan lukien VS Code, IntelliJ IDEA, Eclipse ja muihin. Nämä integraatiot tarjoavat ominaisuuksia, kuten direktiivien automaattisen täydentämisen, automaattisen riippuvuuksien ratkaisun ja kyvyn suorittaa ja virheenkorjata skriptejä suoraan IDE:stä.

Katso [JBang IDE -integraatiodokumentaatio](https://www.jbang.dev/documentation/jbang/latest/editing.html) asennusohjeita ja tuettuja muokkaimia varten.

## Konfigurointi {#configuration}

webforJ JBang -starter sisältää järkevät oletukset, jotka on optimoitu skriptausta varten. Voit mukauttaa käyttäytymistä käyttämällä järjestelmäominaisuuksia.

### Automaattinen sammuttaminen {#auto-shutdown}

Oletuksena palvelin sammuu automaattisesti, kun kaikki selainvälilehdet, joihin sovellus on yhteydessä, on suljettu. Tämä pitää kehitystyösi siistinä ilman, että orvot palvelimet jäävät päälle.

| Ominaisuus | Oletus | Kuvaus |
|------------|--------|--------|
| `webforj.jbang.auto-shutdown` | `true` | Kytke automaattinen sammutus päälle tai pois |
| `webforj.jbang.idle-timeout` | `5` | Sekuntia odottaa viimeisen selainyhteyden katkaisemisen jälkeen ennen sammuttamista |

Kytkeäksesi automaattisen sammutuksen pois päältä:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Vaihtaaksesi tyhjien aikarajojen:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Oletusasetukset {#default-settings}

JBang-starter määrittää seuraavat oletukset:

| Asetus | Arvo | Kuvaus |
|--------|-------|--------|
| `server.port` | `0` | Satunnainen porttijako välttääkseen konflikteja useita skriptejä suoritettaessa |
| `server.shutdown` | `immediate` | Nopeaa sammutusta, jotta skripti voidaan lopettaa nopeasti |
| `spring.main.banner-mode` | `off` | Piilottaa Spring Boot -bannerin siistimmän tulostuksen vuoksi |
| `logging.level.root` | `ERROR` | Vähäinen lokitus pitää konsolitulostuksen siistinä |
| `logging.level.com.webforj` | `WARN` | Näyttää vain varoitukset ja virheet webforJ:stä |
| `webforj.devtools.browser.open` | `true` | Avaa selaimen automaattisesti, kun sovellus käynnistyy |

### Käyttöönotto ja live-päivitys {#development-workflow}

JBang-skriptit eivät tue live-päivityksiä. Jotta näet muutokset:

1. Lopeta käynnissä oleva skripti (sulje selainvälilehti tai paina `Ctrl+C`)
2. Muokkaa koodiasi
3. Suorita `jbang HelloWorld.java` uudelleen

Automaattista käyttöönottoa kehityksen aikana varten harkitse [täydellistä Maven-projektia Spring DevToolsin kanssa](/docs/integrations/spring/spring-boot). Katso [live-päivitysdokumentaatio](/docs/configuration/deploy-reload/overview) lisätietoja varten.

## Siirtyminen täysimittaiseen projektiin {#transitioning}

Kun prototyyppisi kasvaa yhden tiedoston yli, luo oikea projekti käyttäen [startforJ](https://docs.webforj.com/startforj) tai [Maven arkkityyppi](./spring/spring-boot#option-2-using-the-command-line). Voit kopioida skriptisi logiikan suoraan luotuun projektirakenteeseen.
