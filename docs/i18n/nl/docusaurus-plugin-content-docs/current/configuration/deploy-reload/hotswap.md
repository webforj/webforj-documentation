---
title: Hot swapping
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# Hotswap <DocChip chip='since' label='26.02' />

Een hotswap-tool past wijzigingen in gecompileerde klassen toe op de actieve app, zonder deze opnieuw te starten. De app behoudt de status tussen updates. De tool wordt opgegeven in de configuratie van de [webforJ-buildplug-in](/docs/configuration/build-plugin) en wordt gekoppeld wanneer de build de app start. De opdracht om de app uit te voeren blijft hetzelfde en het project hoeft er geen afhankelijkheid voor op te geven.

Er worden twee tools ondersteund:

- **HotswapAgent** is opensource. De buildplug-in downloadt de agent bij de eerste uitvoering en slaat deze op in de cache.
- **JRebel** is een commercieel product. Hiervoor heb je je eigen installatie en licentie nodig.

Configureer precies één tool. Een build waarin beide zijn opgegeven, mislukt met een foutmelding waarin beide worden genoemd.

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
| `version` | Een specifieke agentversie in plaats van de versie die de plug-in selecteert. |
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

Wijzigingen in method bodies worden toegepast op elke Java virtual machine. Voor wijzigingen in de structuur van een klasse, zoals een nieuw veld of een nieuwe methode, is een virtual machine nodig die de optie `-XX:+AllowEnhancedClassRedefinition` accepteert. De [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) biedt deze mogelijkheid. De build detecteert de mogelijkheid en schakelt deze in. Zie [Vereisten](/docs/introduction/prerequisites#java-development-kit-jdk-21) voor informatie over het installeren van een JetBrains Runtime.

Zonder deze mogelijkheid worden wijzigingen in method bodies nog steeds toegepast, maar wordt een wijziging in de klassenstructuur pas zichtbaar in de actieve app nadat deze opnieuw is gestart. Het buildlogboek toont een waarschuwing waarin de vereiste wordt vermeld en de browser geeft één keer een melding weer.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) is een commercieel product waarvoor je een licentie van de leverancier nodig hebt. webforJ levert het niet mee, downloadt het niet en is niet betrokken bij de licentieverlening. De build leest het geconfigureerde pad, controleert of het bestand bestaat en koppelt het ongewijzigd.

Geef in de configuratie het pad op naar de agent uit je JRebel-installatie, een native bibliotheek of een jar:

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

Het pad is verplicht. Een build die JRebel selecteert zonder dit pad op te geven, mislukt met een foutmelding waarin de ontbrekende instelling wordt vermeld.

Met JRebel worden alle wijzigingen in klassen, inclusief wijzigingen in de structuur, toegepast op elke Java-runtime.

## Selectie via de opdrachtregel {#command-line-selection}

De eigenschap `webforj.hotswap` overschrijft het buildbestand voor één uitvoering. De geaccepteerde waarden zijn `hotswapAgent`, `jrebel` en `off`. Elke andere waarde zorgt ervoor dat de build mislukt met een foutmelding waarin de geldige waarden worden vermeld. Als je `jrebel` selecteert, moet het agentpad nog steeds in de configuratie zijn opgegeven.

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

Compileer een wijziging om deze toe te passen op de actieve app. Sla het bestand op in een IDE die bij het opslaan compileert, of voer een compilatie uit in een tweede terminal.

Als alle gewijzigde klassen horen bij wat de huidige pagina weergeeft, wordt het betreffende deel opnieuw opgebouwd en blijft de status van de app behouden. Anders wordt de volledige pagina opnieuw geladen: bij een app zonder routing, bij een klasse buiten de weergegeven routes of wanneer opnieuw opbouwen niet mogelijk is. Eén gecompileerde wijziging resulteert in één update in de browser.

## Herlaadacties versnellen in IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA compileert alleen bij het opslaan als automatische builds zijn ingeschakeld, en wacht voordat elke stap wordt uitgevoerd. Verkort de wachttijden zodat wijzigingen sneller worden toegepast op de actieve app.

### Automatische builds inschakelen {#enable-automatic-builds}

Open de instellingen en schakel de volgende opties in:

1. Schakel **Build, Execution, Deployment** > **Compiler** > **Build project automatically** in.
<!-- vale off -->
2. Schakel **Advanced Settings** > **Compiler** > **Allow auto-make to start even if developed application is currently running** in.
<!-- vale on -->
3. Ga naar **Appearance & Behavior** > **System Settings** en schakel onder **Autosave** de optie **Save files if the IDE is idle for** in. Stel de waarde in op `1` seconde.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Druk op <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd>, of kies **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Druk op <kbd>Cmd</kbd>+<kbd>+</kbd>, of kies **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### De buildvertragingen verkorten {#shorten-the-build-delays}

Stel de volgende registersleutels in op `0`:

| Sleutel | Beschrijving |
|-----|-------------|
| `compiler.automake.trigger.delay` | De vertraging tussen een bestandswijziging en het begin van een build. |
| `compiler.automake.build.while.idle.timeout` | De inactieve tijd voordat een build start. |
| `compiler.automake.postpone.when.idle.less.than` | De inactieve tijd waaronder IntelliJ IDEA een build uitstelt. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Druk op <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> om **Find Action** te openen.
2. Typ `Registry...` en selecteer deze optie.

</TabItem>
<TabItem value="macos" label="macOS">

1. Druk op <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> om **Find Action** te openen.
2. Typ `Registry...` en selecteer deze optie.

</TabItem>
</Tabs>

Je kunt ook twee keer op <kbd>Shift</kbd> drukken om **Search Everywhere** te openen, naar het tabblad **Actions** gaan en `Registry` typen.

Typ in het dialoogvenster **Registry** een sleutelnaam om de lijst te filteren en stel de waarde ervan in op `0`.

:::warning
Registervermeldingen zijn geavanceerde, niet-ondersteunde instellingen van IntelliJ IDEA. Selecteer **Restore Defaults** in het dialoogvenster **Registry** om ze terug te zetten.
:::
