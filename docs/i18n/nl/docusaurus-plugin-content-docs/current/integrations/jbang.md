---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) is een tool waarmee je Java-code als scripts kunt uitvoeren, zonder bouwbestanden, projectopzet of handmatige compilatie. De webforJ JBang-integratie stelt je in staat om snel webforJ-apps te maken, het beste geschikt voor snelle prototyping, leren en korte demo's, zonder de traditionele afhankelijkheden en infrastructuur van een volledig Java-programma.

## Waarom JBang gebruiken met webforJ {#why-use-jbang}

Traditionele webforJ-projecten gebruiken Maven of Gradle met meerdere configuratiebestanden en een standaard projectstructuur. Deze opzet is standaard voor productie-apps, maar kan zwaar aanvoelen voor eenvoudige experimenten of demo's.

Met JBang kun je:

- **Direct starten**: Schrijf een enkel `.java`-bestand en voer het onmiddellijk uit
- **Projectopzet overslaan**: Geen `pom.xml`, geen `build.gradle`, geen directorystructuur
- **Eenvoudig delen**: Stuur iemand een enkel bestand dat hij/zij met één commando kan uitvoeren
- **Sneller leren**: Focus op webforJ-concepten zonder de complexiteit van bouwtools

De integratie omvat automatische serverafsluiting wanneer je het browsertabblad sluit, waardoor je ontwikkelworkflow schoon blijft.

## Vereisten {#prerequisites}

### JBang installeren {#install-jabang}

Kies je voorkeursinstallatiemethode:

```bash
# Universeel (Linux/macOS/Windows met bash)
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
Wanneer je JBang voor de eerste keer uitvoert zonder een JDK geïnstalleerd, downloadt JBang automatisch een. Je kunt de JDK-versie en vendor instellen voordat je JBang uitvoert:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Leer meer over JBang]
Voor uitgebreide JBang-documentatie, zie:
- [JBang Aan de Slag](https://www.jbang.dev/documentation/jbang/latest/index.html) - Installatie en basisprincipes
- [Script Directives Referentie](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Alle beschikbare richtlijnen
- [Afhankelijkheden](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Geavanceerd afhankelijkheidsbeheer
:::

## Een webforJ-script maken {#creating-a-script}

Maak een bestand genaamd `HelloWorld.java` met de volgende inhoud:

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
  private TextField hello = new TextField("Wat is jouw naam?");
  private Button btn = new Button("Zeg Hallo");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welkom bij webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### De scriptstructuur begrijpen {#script-structure}

| Regel | Doel |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang-regel waarmee het script direct kan worden uitgevoerd op Unix-systemen |
| `//JAVA 21` | Geeft de minimum vereiste Java-versie aan; JBang downloadt deze automatisch indien nodig |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Verklaart de webforJ JBang-starter als een afhankelijkheid met Maven-coördinaten |
| `@SpringBootApplication` | Activeert de automatische configuratie van Spring Boot |
| `extends App` | Maakt deze klasse een webforJ-app |

De afhankelijkheid van `webforj-jbang-starter` omvat alles wat nodig is om een webforJ-app uit te voeren: de Spring Boot-starter, ontwikkeltools en automatische browseropening.

:::note[Versie]
Vervang `25.11` door de meest recente webforJ-versie. Bekijk [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) voor de meest recente release.
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

Afhankelijkheden gebruiken standaard Maven-coördinaten (`groupId:artifactId:version`). JBang haalt ze automatisch op van Maven Central bij de eerste uitvoering.

## Je script uitvoeren {#running-your-script}

Voer het script uit met JBang:

```bash
jbang HelloWorld.java
```

JBang zal:

1. Afhankelijkheden downloaden (slechts bij de eerste uitvoering)
2. Het script compileren
3. De ingebedde server starten op een willekeurig beschikbaar poort
4. Je standaardbrowser openen naar de app

### Het script uitvoerbaar maken {#executable-script}

Op Unix-systemen kun je het script direct uitvoerbaar maken:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Dit werkt vanwege de shebang-regel aan de bovenkant van het bestand.

## IDE-ondersteuning {#ide-support}

JBang integreert met populaire Java IDE's, waaronder VS Code, IntelliJ IDEA, Eclipse en anderen. Deze integraties bieden functies zoals richtlijn-autocompletion, automatische afhankelijkheidsresolutie en de mogelijkheid om scripts rechtstreeks vanuit de IDE uit te voeren en te debuggen.

Zie de [JBang IDE-integratiedocumentatie](https://www.jbang.dev/documentation/jbang/latest/editing.html) voor installatie-instructies en ondersteunde editors.

## Configuratie {#configuration}

De webforJ JBang-starter omvat redelijke standaardinstellingen die zijn geoptimaliseerd voor scripting. Je kunt het gedrag aanpassen met systeeminstellingen.

### Auto-afsluiting {#auto-shutdown}

Standaard sluit de server automatisch af wanneer alle browsertabbladen die met de app zijn verbonden worden gesloten. Dit houdt je ontwikkelworkflow schoon door geen verlaten servers te laten draaien.

| Eigenschap | Standaard | Beschrijving |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Auto-afsluiting in- of uitschakelen |
| `webforj.jbang.idle-timeout` | `5` | Seconden wachten na de laatste browserontkoppeling voordat het afsluit |

Om auto-afsluiting uit te schakelen:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Om de idle-timeout te wijzigen:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Standaardinstellingen {#default-settings}

De JBang-starter configureert de volgende standaardinstellingen:

| Instelling | Waarde | Beschrijving |
|---------|-------|-------------|
| `server.port` | `0` | Willekeurige poorttoewijzing om conflicten te voorkomen bij het uitvoeren van meerdere scripts |
| `server.shutdown` | `immediate` | Snelle afsluiting voor snelle scriptterminatie |
| `spring.main.banner-mode` | `off` | Verbergt de Spring Boot-banner voor een schoner output |
| `logging.level.root` | `ERROR` | Minimale logging om de console-output schoon te houden |
| `logging.level.com.webforj` | `WARN` | Toont alleen waarschuwingen en fouten van webforJ |
| `webforj.devtools.browser.open` | `true` | Opent automatisch de browser wanneer de app start |

### Heruitrol en live herladen {#development-workflow}

JBang-scripts ondersteunen geen live herladen. Om wijzigingen te zien:

1. Stop het draaiende script (sluit het browsertabblad of druk op `Ctrl+C`)
2. Bewerk je code
3. Voer `jbang HelloWorld.java` opnieuw uit

Voor automatische heruitrol tijdens ontwikkeling, overweeg het gebruik van een [volledig Maven-project met Spring DevTools](/docs/integrations/spring/spring-boot). Zie de [documentatie voor live herladen](/docs/configuration/deploy-reload/overview) voor meer details.

## Overgang naar een volledig project {#transitioning}

Wanneer je prototype groter wordt dan een enkel bestand, maak dan een goed project met [startforJ](https://docs.webforj.com/startforj) of de [Maven-archetype](./spring/spring-boot#option-2-using-the-command-line). Je kunt je scriptlogica rechtstreeks kopiëren in de gegenereerde projectstructuur.
