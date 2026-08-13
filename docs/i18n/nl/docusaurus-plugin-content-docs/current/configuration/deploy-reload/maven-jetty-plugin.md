---
title: Jetty
sidebar_position: 40
description: >-
  Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin,
  with live reload and hotswap during development.
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
De Maven Jetty-plugin draait de app in een ingebedde Jetty-server recht vanuit het project. Een archetype-project stelt `compile webforj:watch jetty:run` in als zijn standaard Maven-doel, zodat `mvn` zonder argumenten de app compileert, de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) start, en de app op Jetty serveert.

## Vereisten {#requirements}

Een Jetty-project declareert zelf de ontwikkeltools, in het profiel dat wordt gebruikt voor ontwikkelrun:

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

De versie komt van de webforJ Bill of Materials (BOM). Het profiel houdt de afhankelijkheid uit de verpakte war. Een project dat is gemaakt vanuit een [archetype](/docs/introduction/getting-started) heeft dit profiel.

## Live reload inschakelen {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

De sleutels zijn dezelfde als die een Spring Boot-app instelt in `application.properties`, vermeld in de [instellingen](/docs/configuration/deploy-reload/overview#settings).

## Klassenwijzigingen {#class-changes}

Met een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) geconfigureerd, past de tool klassenwijzigingen toe en Jetty redeploys niks. Twee Jetty-eigenschappen ondersteunen dit, en een archetype-project stelt beide in:

- `scan` is `0`, wat de bestandsscan van Jetty uitschakelt.
- `deployMode` blijft niet ingesteld. Hotswap vereist de geforkte modus, en de plugin selecteert deze. Een build die `deployMode` op een andere waarde instelt, start zonder de tool en logt dit.

Zonder een hotswap-tool, stel `scan` in op een interval in seconden en Jetty redeploys de app wanneer gecompileerde klassen of hulpmiddelen veranderen:

| Eigenschap | Beschrijving | Standaard |
|------------|--------------|-----------|
| `scan`     | Interval in seconden tussen scans van de gecompileerde output, ingesteld als de `jetty.scan` eigenschap. `0` schakelt scanning uit. Langere intervallen verlagen de belasting en vertragen de redeploy. | `1` |

## Gebruiksoverwegingen {#usage-considerations}

- **Geheugen en CPU**: lage `scan`-waarden verhogen het resourceverbruik op grote projecten. Langere intervallen verlagen het en vertragen de redeploy.
- **Alleen ontwikkeling**: de Jetty-plugin is niet bedoeld voor productie-implementaties.
- **Sessies**: een redeploy kan gebruikerssessies beëindigen. Een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) past wijzigingen toe zonder een redeploy, en de sessie blijft behouden.
