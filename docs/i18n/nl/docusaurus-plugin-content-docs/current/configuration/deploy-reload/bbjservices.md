---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
In BBjServices start de build geen server op zijn eigen. `mvn compile webforj:watch` draait de app in de BBjServices van je eigen machine via [BBjShip](/docs/configuration/bbjship/overview), rechtstreeks vanuit het project. Een archetype project met de BBj-smaak stelt dit in als zijn standaard Maven-doel, zodat `mvn` zonder argumenten de app compileert, de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) start, en de app uitvoert.

## Vereisten {#requirements}

Een Maven-project ingesteld voor BBjShip, zie [Beginnen](/docs/configuration/bbjship/getting-started) of [Bestaande projecten](/docs/configuration/bbjship/existing-projects), met BBj geïnstalleerd op dezelfde machine als het project. BBjServices laadt de klassen uit de mappen waar de build in compileert, zodat een BBj-server op een andere machine alleen [deployments](/docs/configuration/bbjship/deploying) ontvangt, zonder live reload of hotswap.

## Live reload inschakelen {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

De overige sleutels zijn vermeld in de [instellingen](/docs/configuration/deploy-reload/overview#settings).

## Klassed wijzigingen {#class-changes}

In BBjServices bereidt een wijziging in een klasse de draaiende app alleen voor via een [hotswap-tool](/docs/configuration/deploy-reload/hotswap). Zonder één toepast een gecompileerde wijziging niet en de pagina herlaadt niet totdat de watch opnieuw start. Een archetype project configureert HotswapAgent:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

De tool hecht zich aan BBjServices, niet aan de build. De eerste run voegt het toe aan de Java-argumenten van BBjServices en herstart BBjServices één keer. Lere runs starten zonder een herstart. Op macOS en Linux, en op Windows wanneer BBjServices niet is geregistreerd als een Windows-service, draait de herstartte BBjServices buiten de servicemanager van het besturingssysteem tot de machine opnieuw opstart. [Probleemoplossing](/docs/configuration/bbjship/troubleshooting#development-runs) vermeldt de berichten van een ontwikkelingsrun en wat je met elk moet doen.

:::warning Herstarts BBjServices
De eerste run verandert de Java-instellingen van BBjServices in `<BBj home>/cfg/BBj.properties` en herstart BBjServices. De herstart beëindigt elke draaiende BBj-sessie op die machine, niet alleen webforJ-sessies, dus voer het uit wanneer niemand anders die BBj gebruikt. Voor de eerste wijziging wordt een kopie van het bestand bewaard als `BBj.properties.before-webforj` in de map `<BBj home>/plugins/.webforj-bbjship-deployments`. Maak zelf eerst een back-up van het bestand als je het met de hand hebt gewijzigd. `mvn webforj:bbj-setup` maakt dezelfde wijziging.
:::

Een wijziging in afhankelijkheden vereist een nieuwe start van de watch.

### Java van BBjServices {#java-of-bbjservices}

Met HotswapAgent zijn [wijzigingen in de klassenstructuur](/docs/configuration/deploy-reload/hotswap#class-structure-changes) afhankelijk van de Java waarop BBjServices draait, niet van de Java van de build. De watch verandert dit nooit. Om BBjServices te draaien op een [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases):

1. Ga in Enterprise Manager naar **BBjServices**, **Java-instellingen**.
2. Selecteer **BBjServices** onder **Applicatie** en stel het Java-home in.
3. Herstart BBjServices. **Java-details** in Enterprise Manager toont dan de JDK en de versie waarop het draait.

Zie [Vereisten](/docs/introduction/prerequisites#java-development-kit-jdk-21) voor het installeren van een JetBrains Runtime.

## Productie builds {#production-builds}

`mvn install` deployt de verpakte app zonder ontwikkelingstools, zie [Deployen](/docs/configuration/bbjship/deploying). De eigenschap `webforj.devtools.livereload.enabled` heeft geen effect in een verpakte app.
