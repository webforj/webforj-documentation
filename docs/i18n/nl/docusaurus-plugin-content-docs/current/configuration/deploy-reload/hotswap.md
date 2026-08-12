---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

Een hotswap-tool past gecompileerde klassewijzigingen toe op de draaiende app zonder een herstart. De app behoudt zijn status tussen updates. De tool wordt genoemd in de [webforJ build-plugin](/docs/configuration/build-plugin) configuratie en wordt gekoppeld wanneer de build de app start. De run-opdracht blijft hetzelfde, en het project verklaart geen afhankelijkheid hiervoor.

Er worden twee tools ondersteund:

- **HotswapAgent** is open source. De build-plugin downloadt de agent bij de eerste uitvoering en cachet deze.
- **JRebel** is een commercieel product. Het vereist je eigen installatie en licentie.

Configureer exact één. Een build die beide benoemt faalt met een foutmelding.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Een leeg element is een complete configuratie:

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
|-------|--------------|
| `version` | Een specifieke agentversie in plaats van de versie die de plugin selecteert. |
| `path` | Een agent jar op disk, rechtstreeks gebruikt zonder downloaden. Voor machines zonder netwerkverbinding of voor een aangepaste agentbuild. |

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

Bewerking van de methode-inhoud is toepasbaar op elke Java-virtuele machine. Wijzigingen aan de structuur van een klasse, zoals een nieuw veld of een nieuwe methode, vereisen een virtuele machine die de optie `-XX:+AllowEnhancedClassRedefinition` accepteert, die door de [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) wordt geleverd. De build detecteert de mogelijkheid en schakelt deze in. Zie [Vereisten](/docs/introduction/prerequisites#java-development-kit-jdk-21) voor het installeren van een JetBrains Runtime.

Zonder de mogelijkheid zijn bewerkingen van de methode-inhoud nog steeds toepasbaar, en een wijziging in de klassenstructuur bereikt de draaiende app pas na een herstart. Het buildlog print een waarschuwing die de vereiste benoemt, en de browser toont eenmaal een melding.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) is een commercieel product, gelicentieerd door de verkoper. webforJ levert het niet, downloadt het niet en neemt geen deel aan de licentieverlening. De build leest het geconfigureerde pad, controleert of het bestand bestaat en koppelt het onveranderd.

Wijs de configuratie naar de agent van je JRebel-installatie, een native bibliotheek of een jar:

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

Het pad is vereist. Een build die JRebel zonder het pad selecteert, faalt met een foutmelding die de ontbrekende instelling benoemt.

Met JRebel zijn alle klassewijzigingen, inclusief structuurwijzigingen, toepasbaar op elke Java-runtime.

## Commando-regelselectie {#command-line-selection}

De `webforj.hotswap` eigenschap overschrijft het buildbestand voor een enkele uitvoering. Geaccepteerde waarden zijn `hotswapAgent`, `jrebel`, en `off`. Elke andere waarde faalt de build met een fout die de geldige vermeldt. Het selecteren van `jrebel` vereist nog steeds het agentpad in de configuratie.

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

Compileer een wijziging en deze bereikt de draaiende app. Sla op in een IDE die compileert bij opslaan, of voer een compilatie uit in een tweede terminal.

Wanneer elke gewijzigde klasse behoort tot wat de huidige pagina weergeeft, wordt het getroffen deel ter plekke opnieuw opgebouwd en blijft de app-status behouden. Anders wordt de pagina volledig opnieuw geladen: voor een app zonder routering, voor een klasse buiten de weergegeven routes, of wanneer de herbouw niet kan worden uitgevoerd. Een gecompileerde wijziging produceert één update in de browser.
