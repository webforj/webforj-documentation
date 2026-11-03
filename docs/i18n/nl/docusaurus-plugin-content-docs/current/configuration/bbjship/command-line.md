---
title: Opdrachtregel
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# Commandoregel <DocChip chip='since' label='26.03' /> {#command-line}

De opdrachtregel `bbjship` beheert BBjShip op de BBj-machine. De opdracht staat in `<BBj home>/plugins/webforj-bbjship` en heet `bbjship.cmd` op Windows. Elke opdracht wordt uitgevoerd binnen BBjServices. Daarom moet BBjServices voor elke opdracht actief zijn.

```text
bbjship <group> <command> [options]
```

## Opdrachten {#commands}

| Opdracht | Beschrijving |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Schakelt het implementatie-eindpunt in of werkt het bij en geeft de adressen weer. |
| [`remote disable`](#remote-disable) | Verwijdert het implementatie-eindpunt. Geïmplementeerde apps blijven actief. |
| [`remote status`](#remote-status) | Geeft de eindpuntadressen weer, of ontwikkeluitvoeringen zijn ingeschakeld en of niet-versleutelde aanvragen vanaf een andere machine zijn toegestaan. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Implementeert een implementatiearchief. |
| [`apps list [--long]`](#apps-list) | Geeft de apps weer die BBjShip heeft geïmplementeerd. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Verwijdert één of alle apps die BBjShip heeft geïmplementeerd. |
| [`development enable`, `development disable`](#development) | Schakelt [ontwikkeluitvoeringen](/docs/configuration/deploy-reload/bbjservices) op deze machine in of uit. |
| [`reset [--yes] [--properties FILE]`](#reset) | Verwijdert alle apps en alles wat BBjShip heeft ingesteld. |

## Gedeelde opties {#options}

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--username USER` | Niet ingesteld | Gebruikersnaam van de BBj-beheerder. |
| `--password PASSWORD` | Niet ingesteld | Wachtwoord van de BBj-beheerder. |
| `--password-stdin` | Uit | Leest het wachtwoord uit de eerste regel van standaardinvoer. Niet beschikbaar op Windows. |
| `--help`, `-h` | | Geeft het gebruik van de opdrachtregel of een afzonderlijke opdracht weer. |
| `--version`, `-V` | | Geeft de BBjShip-versie weer. |

Een wachtwoord dat met `--password` wordt opgegeven, is tijdens het uitvoeren van de opdracht zichtbaar voor andere gebruikers van de machine in de proceslijst. Op macOS en Linux kan een script het wachtwoord in plaats daarvan via standaardinvoer doorgeven:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Op elk systeem houden de variabelen het wachtwoord buiten de opdrachtregel:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Op Windows is standaardinvoer niet beschikbaar. Daarom eindigt `--password-stdin` met afsluitcode `2`. Geef het wachtwoord op als waarde of via de variabelen:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Als je zowel `--password` als `--password-stdin` opgeeft, een gebruikersnaam zonder wachtwoord of een wachtwoord zonder gebruikersnaam, eindigt de opdracht met afsluitcode `2` en een bericht dat aangeeft wat je moet opgeven.

Zonder aanmeldgegevens gebruikt de opdracht het fabrieksaccount `admin` met het wachtwoord `admin123` en geeft een waarschuwing weer.

## Omgevingsvariabelen {#environment-variables}

| Variabele | Standaard | Beschrijving |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Niet ingesteld | Gebruikersnaam als `--username` ontbreekt. |
| `WEBFORJ_BBJ_PASSWORD` | Niet ingesteld | Wachtwoord als noch `--password`, noch `--password-stdin` is opgegeven. |
| `NO_COLOR` | Niet ingesteld | Schakelt gekleurde uitvoer uit. |

## Extern {#remote}

De opdrachten `remote` beheren het implementatie-eindpunt.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Schakelt het implementatie-eindpunt in of werkt het bij en geeft de adressen weer.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--port PORT` | `8888` | HTTP-poort van de BBjServices-webserver voor het eindpunt. |
| `--ssl-port PORT` | Geen | SSL-poort van de BBjServices-webserver voor het eindpunt. Zonder deze optie heeft het eindpunt geen HTTPS-adres. |
| `--allow-unencrypted` | Uit | Accepteert de beheerdersaanmeldgegevens via HTTP vanaf een andere machine. Zonder deze optie moet een aanvraag vanaf een andere machine de SSL-poort gebruiken en antwoordt het eindpunt daarop met HTTP 403. Aanvragen vanaf de BBj-machine zelf gebruiken in beide gevallen HTTP. |

De poorten moeten poorten zijn waarop de BBjServices-webserver al luistert. Anders eindigt `remote enable` met afsluitcode `1`, wordt aangegeven welke poort niet reageert en blijft het vorige eindpunt behouden.

Elke uitvoering van `remote enable` slaat de poorten en de instelling `--allow-unencrypted` opnieuw op. Een uitvoering zonder de optie schakelt de instelling uit en geeft `Refused unencrypted requests from another machine` weer. Een uitvoering met de optie geeft `Allowed unencrypted requests from another machine` weer. De build die via HTTP implementeert vanaf een andere machine, stelt ook [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine) in.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Verwijdert het implementatie-eindpunt. Geïmplementeerde apps blijven actief.

<!-- vale Google.WordListCase = NO -->
:::warning Builds kunnen niet implementeren
`remote disable` verwijdert het implementatie-eindpunt. Builds en CI-taken die ernaar implementeren, mislukken totdat je `remote enable` opnieuw uitvoert. Noteer de poorten en het eindpuntadres voordat je het uitschakelt. Geïmplementeerde apps blijven actief.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Geeft de eindpuntadressen weer, of ontwikkeluitvoeringen zijn ingeschakeld en of niet-versleutelde aanvragen vanaf een andere machine zijn toegestaan.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Apps {#apps}

De opdrachten `apps` geven alleen apps weer en verwijderen alleen apps die BBjShip heeft geïmplementeerd. Apps die zijn geregistreerd in Enterprise Manager worden niet beïnvloed.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Implementeert een implementatiearchief. `apps deploy` installeert een archief dat is gemaakt door [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), zonder het implementatie-eindpunt. De bestandsnaam zonder `.zip` is de [publicatienaam](/docs/configuration/bbjship/deploying#publish-names). BBjServices leest het archief, dus de besturingssysteemgebruiker van BBjServices moet leestoegang hebben.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Vervangt de app
Als je een archief implementeert met de naam van een app die al is geïmplementeerd, wordt die app vervangen. De vorige implementatiemap wordt verwijderd en de actieve sessies van de app worden beëindigd. Bewaar het vorige archief voor het geval je wilt terugkeren naar de vorige versie.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Geeft de apps weer die BBjShip heeft geïmplementeerd.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--long`, `-l` | Uit | Voegt de implementatiemap en de klassenmappen van elke app toe. |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Verwijdert één of alle apps die BBjShip heeft geïmplementeerd.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--all` | Uit | Maakt de implementatie ongedaan van alle apps die BBjShip heeft geïmplementeerd. |

:::warning Kan niet ongedaan worden gemaakt
`apps undeploy` verwijdert de app uit Enterprise Manager, beëindigt de actieve sessies en verwijdert de implementatiemap. `apps undeploy --all` doet dit voor alle apps die BBjShip heeft geïmplementeerd. Dit kan niet ongedaan worden gemaakt. Bewaar het implementatiearchief van elke app, bijvoorbeeld `target/bbj/<publish name>.zip`, zodat je de app opnieuw kunt implementeren.
:::

## Development {#development}

Ontwikkeluitvoeringen zijn uitgeschakeld bij een nieuwe installatie. De eerste `webforj:watch` op dezelfde machine schakelt ze in. `bbjship development disable` schakelt ze uit totdat de volgende `webforj:watch` op deze machine ze weer inschakelt. Een ontwikkeluitvoering vanaf een andere machine wordt geweigerd, omdat de installatie die als eerste wordt uitgevoerd alleen op de BBj-machine werkt.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Schakelt [ontwikkeluitvoeringen](/docs/configuration/deploy-reload/bbjservices) in op deze machine.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Schakelt [ontwikkeluitvoeringen](/docs/configuration/deploy-reload/bbjservices) uit op deze machine.

## Reset {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` maakt de implementatie ongedaan van alle apps die BBjShip heeft geïmplementeerd, verwijdert het implementatie-eindpunt, de ontwikkelschakelaar en de instelling `--allow-unencrypted`, herstelt het BBjServices-eigenschappenbestand dat door een ontwikkeluitvoering is gewijzigd en verwijdert de bestanden die BBjShip heeft geschreven. De opdracht stopt BBjServices nooit. Nadat het eigenschappenbestand is hersteld, moet je BBjServices opnieuw starten om de wijzigingen toe te passen. De plug-inmap blijft behouden.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--yes` | Uit | Slaat de bevestiging over. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | Het BBjServices-eigenschappenbestand dat `reset` herstelt. |

De opdracht geeft weer wat deze gaat doen en vraagt om bevestiging, tenzij `--yes` is opgegeven. Op Windows eindigt `reset` zonder `--yes` met afsluitcode `2` voordat de opdracht zich aanmeldt.

:::warning Maak eerst een back-up
`reset` kan niet ongedaan worden gemaakt. De opdracht verwijdert de map van elke app die BBjShip heeft geïmplementeerd en beëindigt de actieve sessies. Bewaar voordat je de opdracht uitvoert het implementatiearchief van elke app, bijvoorbeeld het bestand `target/bbj/<publish name>.zip` van de build, en maak een back-up van `<BBj home>/cfg/BBj.properties`. Als er een kopie van het bestand is bewaard in de runtime-map van BBjShip, zet `reset` die kopie terug over `cfg/BBj.properties`. Eventuele wijzigingen die je na de eerste ontwikkeluitvoering in het bestand hebt aangebracht, gaan daardoor verloren. Start BBjServices daarna opnieuw om de herstelde Java-instellingen toe te passen.
:::

## Afsluitcodes {#exit-codes}

| Code | Betekenis |
|---|---|
| `0` | De opdracht is geslaagd. |
| `1` | De opdracht is mislukt en heeft de oorzaak weergegeven, of `reset` is geannuleerd. |
| `2` | De argumenten zijn ongeldig of de opdracht heeft standaardinvoer nodig op Windows. Het bericht geeft aan wat je moet opgeven. |

[Probleemoplossing](/docs/configuration/bbjship/troubleshooting#command-line) bevat de berichten en wat je bij elk bericht kunt doen.
