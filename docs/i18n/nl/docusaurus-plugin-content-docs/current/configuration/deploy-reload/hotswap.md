---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# Hotswap <DocChip chip='since' label='26.02' />

Een hotswap-tool past wijzigingen in gecompileerde klassen toe op de actieve app zonder opnieuw op te starten. De app behoudt zijn status tussen updates. De tool wordt opgegeven in de configuratie van de [webforJ-buildplugin](/docs/configuration/build-plugin) en wordt gekoppeld zodra de build de app start. De runopdracht blijft hetzelfde en het project declareert er geen afhankelijkheid voor.

Er worden twee tools ondersteund:

- **HotswapAgent** is opensource. De buildplugin downloadt de agent bij de eerste uitvoering en slaat deze in de cache op.
- **JRebel** is een commercieel product. Je hebt hiervoor je eigen installatie en licentie nodig.

Configureer precies één tool. Een build waarin beide worden genoemd, mislukt met een foutmelding waarin ze allebei worden vermeld.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Een leeg element is een volledige configuratie:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

Twee opties verfijnen de koppeling:

| Optie | Beschrijving |
|--------|-------------|
| `version` | Een specifieke agentversie in plaats van de versie die de plugin selecteert. |
| `path` | Een agent-jar op schijf, die rechtstreeks wordt gebruikt zonder download. Voor machines zonder netwerktoegang of voor een aangepaste agentbuild. |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### Wijzigingen in de klassenstructuur {#class-structure-changes}

Wijzigingen in de inhoud van methoden worden op elke Java-virtuele machine toegepast. Voor wijzigingen in de structuur van een klasse, zoals een nieuw veld of een nieuwe methode, is een virtuele machine nodig die de optie `-XX:+AllowEnhancedClassRedefinition` accepteert. De [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) biedt deze mogelijkheid. De build detecteert of deze mogelijkheid beschikbaar is en schakelt deze in. Zie [Vereisten](/docs/introduction/prerequisites#java-development-kit-jdk-21) voor informatie over het installeren van een JetBrains Runtime.

Zonder deze mogelijkheid worden wijzigingen in de inhoud van methoden nog steeds toegepast, maar is een herstart nodig voordat een wijziging in de klassenstructuur de actieve app bereikt. Het buildlogboek geeft een waarschuwing met de vereiste weer en de browser toont één keer een melding.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) is een commercieel product waarvoor een licentie van de leverancier vereist is. webforJ levert het niet mee, downloadt het niet en speelt geen rol bij de licentieverlening. De build leest het geconfigureerde pad, controleert of het bestand bestaat en koppelt het ongewijzigd.

Verwijs in de configuratie naar de agent uit je JRebel-installatie, een native bibliotheek of een jar:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

Het pad is verplicht. Een build die JRebel selecteert zonder dit pad op te geven, mislukt met een foutmelding waarin de ontbrekende instelling wordt genoemd.

Met JRebel worden alle wijzigingen in klassen, inclusief wijzigingen in de structuur, op elke Java-runtime toegepast.

## Selectie via de opdrachtregel {#command-line-selection}

De eigenschap `webforj.hotswap` overschrijft voor één uitvoering de buildconfiguratie. De toegestane waarden zijn `hotswapAgent`, `jrebel` en `off`. Elke andere waarde laat de build mislukken met een foutmelding waarin de geldige waarden worden vermeld. Als je `jrebel` selecteert, moet het agentpad nog steeds in de configuratie zijn opgegeven.

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## Een wijziging toepassen {#applying-a-change}

Compileer een wijziging en deze wordt toegepast op de actieve app. Sla het bestand op in een IDE die bij het opslaan compileert, of voer een compilatie uit in een tweede terminal.

Als alle gewijzigde klassen behoren tot wat de huidige pagina weergeeft, wordt het betreffende deel ter plekke opnieuw opgebouwd en blijft de status van de app behouden. Anders wordt de pagina volledig opnieuw geladen: bij een app zonder routing, voor een klasse buiten de weergegeven routes of wanneer de heropbouw niet kan worden uitgevoerd. Eén gecompileerde wijziging leidt tot één update in de browser.

## Herladen versnellen in IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA compileert alleen bij het opslaan als automatisch bouwen is ingeschakeld, en wacht voor elke stap. Verkort de wachttijden zodat een wijziging sneller de actieve app bereikt.

### Automatisch bouwen inschakelen {#enable-automatic-builds}

Open de instellingen en schakel deze opties in:

1. Schakel **Build project automatically** in via **Build, Execution, Deployment** > **Compiler**.
<!-- vale off -->
2. Schakel **Allow auto-make to start even if developed application is currently running** in via **Advanced Settings** > **Compiler**.
<!-- vale on -->
3. Schakel onder **Autosave** de optie **Save files if the IDE is idle for** in via **Appearance & Behavior** > **System Settings** en stel deze in op `1` seconde.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Druk op <kbd>Ctrl+ALt+S</kbd> of kies **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Druk op <kbd>Cmd+,</kbd> of kies **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### De buildvertragingen verkorten {#shorten-the-build-delays}

Stel deze registersleutels in op `0`:

| Sleutel | Beschrijving |
|-----|-------------|
| `compiler.automake.trigger.delay` | De vertraging tussen een bestandswijziging en het begin van een build. |
| `compiler.automake.build.while.idle.timeout` | De tijd dat het systeem inactief moet zijn voordat een build begint. |
| `compiler.automake.postpone.when.idle.less.than` | De tijd dat het systeem inactief is waaronder IntelliJ IDEA een build uitstelt. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Druk op `Ctrl+Shift+A` om **Find Action** te openen.
2. Typ `Registry...` en selecteer het.

</TabItem>
<TabItem value="macos" label="macOS">

1. Druk op `Cmd+Shift+A` om **Find Action** te openen.
2. Typ `Registry...` en selecteer het.

</TabItem>
</Tabs>

Je kunt ook tweemaal op `Shift` drukken om **Search Everywhere** te openen, naar het tabblad **Actions** gaan en `Registry` typen.

Typ in het dialoogvenster **Registry** een sleutelnaam om de lijst te filteren en stel de waarde ervan vervolgens in op `0`.

:::warning
Registervermeldingen zijn geavanceerde, niet-ondersteunde instellingen van IntelliJ IDEA. Selecteer **Restore Defaults** in het dialoogvenster **Registry** om ze te herstellen.
:::
