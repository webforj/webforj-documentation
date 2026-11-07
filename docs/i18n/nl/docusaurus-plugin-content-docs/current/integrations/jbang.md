---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) is een tool waarmee je Java-code als scripts kunt uitvoeren, zonder buildbestanden, projectconfiguratie of handmatige compilatie. Met de webforJ JBang-integratie kun je snel webforJ-apps maken. De integratie is vooral geschikt voor rapid prototyping, leren en snelle demo's, zonder de traditionele afhankelijkheden en infrastructuur van een volledig Java-programma.

## Waarom JBang gebruiken met webforJ {#why-use-jbang}

Traditionele webforJ-projecten gebruiken Maven of Gradle, met meerdere configuratiebestanden en een standaardprojectstructuur. Deze opzet is gebruikelijk voor productie-apps, maar kan omslachtig aanvoelen voor eenvoudige experimenten of demo's.

Met JBang kun je:

- **Direct aan de slag**: schrijf één `.java`-bestand en voer het meteen uit
- **Projectconfiguratie overslaan**: geen `pom.xml`, geen `build.gradle`, geen mappenstructuur
- **Eenvoudig delen**: stuur iemand één bestand dat diegene met één opdracht kan uitvoeren
- **Sneller leren**: focus op webforJ-concepten zonder de complexiteit van buildtools

De integratie sluit de server automatisch af wanneer je het browsertabblad sluit, zodat je ontwikkelworkflow overzichtelijk blijft.

## Vereisten {#prerequisites}

### JBang installeren {#install-jbang}

Kies de gewenste installatiemethode:

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

Controleer de installatie:

```bash
jbang --version
```

:::info[Standaard Java-versie]
Wanneer je JBang voor het eerst uitvoert zonder dat er een JDK is geïnstalleerd, downloadt JBang er automatisch een. Je kunt de JDK-versie en -leverancier instellen voordat je JBang uitvoert:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Meer informatie over JBang]
Raadpleeg de volgende bronnen voor uitgebreide documentatie over JBang:
- [Aan de slag met JBang](https://www.jbang.dev/documentation/jbang/latest/index.html) - installatie en basisprincipes
- [Naslag voor scriptdirectives](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - alle beschikbare directives
- [Afhankelijkheden](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - geavanceerd afhankelijkhedenbeheer
:::

## Een webforJ-script maken {#creating-a-script}

Maak een bestand met de naam `HelloWorld.java` en de volgende inhoud:

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

### De scriptstructuur begrijpen {#script-structure}

| Regel | Doel |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-regel waarmee het script rechtstreeks op Unix-systemen kan worden uitgevoerd |
| `//JAVA 21` | Geeft de minimaal vereiste Java-versie aan; JBang downloadt deze automatisch indien nodig |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Declareert de webforJ JBang-starter als afhankelijkheid met Maven-coördinaten |
| `@SpringBootApplication` | Schakelt de automatische configuratie van Spring Boot in |
| `extends App` | Maakt van deze klasse een webforJ-app |

De afhankelijkheid `webforj-jbang-starter` bevat alles wat nodig is om een webforJ-app uit te voeren: de Spring Boot-starter, ontwikkeltools en het automatisch openen van de browser.

:::note[Versie]
Vervang `25.11` door de nieuwste webforJ-versie. Kijk op [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) voor de meest recente release.
:::
### Afhankelijkheden toevoegen {#adding-dependencies}

Je kunt extra Maven-afhankelijkheden toevoegen met meerdere `//DEPS`-regels:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Afhankelijkheden gebruiken de standaard Maven-coördinaten (`groupId:artifactId:version`). JBang haalt ze bij de eerste uitvoering automatisch op van Maven Central.

## Je script uitvoeren {#running-your-script}

Voer het script uit met JBang:

```bash
jbang HelloWorld.java
```

JBang zal:

1. Afhankelijkheden downloaden (alleen de eerste keer)
2. Het script compileren
3. De ingesloten server starten op een willekeurige beschikbare poort
4. Je standaardbrowser openen met de app

### Het script uitvoerbaar maken {#executable-script}

Op Unix-systemen kun je het script rechtstreeks uitvoerbaar maken:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Dit werkt dankzij de shebang-regel bovenaan het bestand.

## IDE-ondersteuning {#ide-support}

JBang kan worden geïntegreerd met populaire Java-IDE's, waaronder VS Code, IntelliJ IDEA, Eclipse en andere. Deze integraties bieden functies zoals automatisch aanvullen van directives, automatische afhankelijkhedenresolutie en de mogelijkheid om scripts rechtstreeks vanuit de IDE uit te voeren en te debuggen.

Raadpleeg de [documentatie over IDE-integratie van JBang](https://www.jbang.dev/documentation/jbang/latest/editing.html) voor installatie-instructies en ondersteunde editors.

## Configuratie {#configuration}

De webforJ JBang-starter bevat passende standaardinstellingen die zijn geoptimaliseerd voor scripting. Je kunt het gedrag aanpassen met behulp van systeemeigenschappen.

### Automatisch afsluiten {#auto-shutdown}

Standaard wordt de server automatisch afgesloten wanneer alle browsertabbladen die met de app zijn verbonden, worden gesloten. Zo blijft je ontwikkelworkflow overzichtelijk en blijven er geen verweesde servers actief.

| Eigenschap | Standaard | Beschrijving |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Automatisch afsluiten in- of uitschakelen |
| `webforj.jbang.idle-timeout` | `5` | Het aantal seconden dat na de verbinding van de laatste browser wordt gewacht voordat de server wordt afgesloten |

Schakel automatisch afsluiten uit:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Wijzig de time-out voor inactiviteit:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Standaardinstellingen {#default-settings}

De JBang-starter configureert de volgende standaardinstellingen:

| Instelling | Waarde | Beschrijving |
|---------|-------|-------------|
| `server.port` | `0` | Willekeurige poorttoewijzing om conflicten te voorkomen wanneer meerdere scripts worden uitgevoerd |
| `server.shutdown` | `immediate` | Snel afsluiten zodat scripts vlot kunnen worden beëindigd |
| `spring.main.banner-mode` | `off` | Verbergt de Spring Boot-banner voor een overzichtelijkere uitvoer |
| `logging.level.root` | `ERROR` | Minimale logging om de console-uitvoer overzichtelijk te houden |
| `logging.level.com.webforj` | `WARN` | Toont alleen waarschuwingen en fouten van webforJ |
| `webforj.devtools.browser.open` | `true` | Opent de browser automatisch wanneer de app start |

### Opnieuw implementeren en live herladen {#development-workflow}

JBang-scripts ondersteunen geen live herladen. Zo bekijk je wijzigingen:

1. Stop het actieve script (sluit het browsertabblad of druk op <kbd>Ctrl</kbd>+<kbd>C</kbd>)
2. Bewerk je code
3. Voer `jbang HelloWorld.java` opnieuw uit

Overweeg voor automatische herimplementatie tijdens de ontwikkeling een [volledig Maven-project met Spring DevTools](/docs/integrations/spring/spring-boot). Raadpleeg de [documentatie over live herladen](/docs/configuration/deploy-reload/overview) voor meer informatie.

## Overstappen naar een volledig project {#transitioning}

Wanneer je prototype uitgroeit tot meer dan één bestand, maak je een project aan met [startforJ](https://docs.webforj.com/startforj) of de [Maven-archetype](./spring/spring-boot#option-2-using-the-command-line). Je kunt de logica uit je script rechtstreeks kopiëren naar de gegenereerde projectstructuur.
