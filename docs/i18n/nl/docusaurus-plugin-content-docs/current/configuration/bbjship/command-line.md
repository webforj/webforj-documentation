---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# Opdrachtregel <DocChip chip='since' label='26.03' /> {#command-line}

De `bbjship` opdrachtregel beheert BBjShip op de BBj-machine. Het bevindt zich in `<BBj home>/plugins/webforj-bbjship`, als `bbjship.cmd` op Windows. Elke opdracht draait binnen BBjServices, dus BBjServices moet draaien voor elke opdracht.

```text
bbjship <groeps> <opdracht> [opties]
```

## Opdrachten {#commands}

| Opdracht | Beschrijving |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | Schakelt de uitrol-eindpunt in of werkt deze bij en geeft de adressen af. |
| [`remote disable`](#remote-disable) | Verwijdert het uitrol-eindpunt. Gedeployde apps blijven draaien. |
| [`remote status`](#remote-status) | Geeft de eindpuntadressen en aan of ontwikkeling runs zijn ingeschakeld. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Deployst een uitrolarchief. |
| [`apps list [--long]`](#apps-list) | Lijst de apps op die BBjShip heeft gedeployed. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Verwijdert één of elke app die BBjShip heeft gedeployed. |
| [`development enable`, `development disable`](#development) | Zet [ontwikkeling runs](/docs/configuration/deploy-reload/bbjservices) aan of uit op deze machine. |
| [`reset [--yes] [--properties FILE]`](#reset) | Verwijdert elke app en alles wat BBjShip heeft ingesteld. |

## Gedeelde opties {#options}

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--username USER` | Niet ingesteld | BBj administrator gebruikersnaam. |
| `--password PASSWORD` | Niet ingesteld | BBj administrator wachtwoord. |
| `--password-stdin` | Uit | Leest het wachtwoord van de eerste regel van de standaardinvoer. Niet beschikbaar op Windows. |
| `--help`, `-h` | | Geeft het gebruik van de opdrachtregel of van een opdracht weer. |
| `--version`, `-V` | | Geeft de versie van BBjShip weer. |

Een wachtwoord dat met `--password` wordt opgegeven, is zichtbaar voor andere gebruikers van de machine in de proceslijst tijdens het uitvoeren van de opdracht. Op macOS en Linux geeft een script het wachtwoord via de standaardinvoer door:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Op elk systeem houden de variabelen het wachtwoord uit de opdrachtregel:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Op Windows is standaardinvoer niet beschikbaar, dus eindigt `--password-stdin` met exitcode `2`. Geef het wachtwoord mee als waarde of via de variabelen:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Het geven van zowel `--password` als `--password-stdin`, een gebruikersnaam zonder wachtwoord, of een wachtwoord zonder gebruikersnaam eindigt met exitcode `2` en een bericht dat aangeeft wat moet worden opgegeven.

Zonder referenties gebruikt de opdracht het fabrieksaccount `admin` met `admin123` en geeft een waarschuwing.

## Omgevingsvariabelen {#environment-variables}

| Variabele | Standaard | Beschrijving |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Niet ingesteld | Gebruikersnaam wanneer `--username` afwezig is. |
| `WEBFORJ_BBJ_PASSWORD` | Niet ingesteld | Wachtwoord wanneer noch `--password` noch `--password-stdin` is opgegeven. |
| `NO_COLOR` | Niet ingesteld | Zet gekleurde uitvoer uit. |

## Afstandsbediening {#remote}

De `remote` opdrachten beheren het uitrol-eindpunt.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

Schakelt de uitrol-eindpunt in of werkt deze bij en geeft de adressen af.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--port PORT` | `8888` | HTTP-poort van de BBjServices webserver voor het eindpunt. |
| `--ssl-port PORT` | Geen | SSL-poort van de BBjServices webserver voor het eindpunt. Zonder dit heeft het eindpunt geen HTTPS-adres. |

De poorten moeten poorten zijn waarop de BBjServices webserver al luistert. Anders eindigt `remote enable` met exitcode `1`, benoemt de poort die niet antwoordt, en behoudt het vorige eindpunt.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Verwijdert het uitrol-eindpunt. Gedeployde apps blijven draaien.

<!-- vale Google.WordListCase = NO -->
:::warning Builds kunnen niet worden gedeployed
`remote disable` verwijdert het uitrol-eindpunt. Builds en CI-taken die naar dit eindpunt deploysten, falen totdat je opnieuw `remote enable` uitvoert. Let op de poorten en het eindpuntadres voordat je het uitschakelt. Gedeployde apps blijven draaien.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Geeft de eindpuntadressen en aan of ontwikkeling runs zijn ingeschakeld.

```text
$ bbjship remote status
Ingeschakeld http://localhost:8888/webforj-bbjship
Ingeschakeld https://localhost:8443/webforj-bbjship
Ontwikkeling runs uitgeschakeld
```

## Apps {#apps}

De `apps` opdrachten lijst en verwijderen alleen apps die BBjShip heeft gedeployed. Apps geregistreerd in de Enterprise Manager worden niet beïnvloed.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Deployst een uitrolarchief. `apps deploy` installeert een archief dat is geschreven door [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), zonder het uitrol-eindpunt. De bestandsnaam zonder `.zip` is de [publish name](/docs/configuration/bbjship/deploying#publish-names). BBjServices leest het archief, zodat de besturingssysteemgebruiker van BBjServices leesrechten moet hebben.

```text
$ bbjship apps deploy inventory.zip
Gedeployed inventory /webapp/inventory
```

:::warning Vervangt de app
Het deployst een archief onder de naam van een app die al is gedeployed, vervangt die app. De vorige uitrolmap wordt verwijderd en de draaiende sessies van de app stoppen. Houd het vorige archief als je mogelijk terug wilt.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Lijsten de apps die BBjShip heeft gedeployed.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--long`, `-l` | Uit | Voegt de uitrolmap en de klasse-mappen van elke app toe. |

```text
$ bbjship apps list
NAAM       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAAM
bbjship apps undeploy --all
```

Verwijdert één of elke app die BBjShip heeft gedeployed.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--all` | Uit | Deployst elke app die BBjShip heeft gedeployed. |

:::warning Kan niet ongedaan worden gemaakt
`apps undeploy` verwijdert de app uit de Enterprise Manager, beëindigt de draaiende sessies en verwijdert de uitrolmap. `apps undeploy --all` doet dit voor elke app die BBjShip heeft gedeployed. Dit kan niet ongedaan worden gemaakt. Houd het uitrolarchief van elke app, bijvoorbeeld `target/bbj/<publish name>.zip`, zodat je het opnieuw kunt deploysten.
:::

## Ontwikkeling {#development}

Ontwikkeling runs zijn uitgeschakeld bij een nieuwe installatie, en de eerste `webforj:watch` op dezelfde machine schakelt ze in. `bbjship development disable` schakelt ze uit tot de volgende `webforj:watch` op deze machine ze weer inschakelt. Een ontwikkeling run vanaf een andere machine wordt geweigerd, omdat de setup die het eerst draait, alleen op de BBj-machine werkt.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Zet [ontwikkeling runs](/docs/configuration/deploy-reload/bbjservices) aan op deze machine.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Zet [ontwikkeling runs](/docs/configuration/deploy-reload/bbjservices) uit op deze machine.

## Reset {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` undeploys elke app die BBjShip heeft gedeployed, verwijdert het uitrol-eindpunt en de ontwikkelingsschakelaar, herstelt het BBjServices-eigenschappenbestand dat een ontwikkeling run heeft gewijzigd, en verwijdert de bestanden die BBjShip heeft geschreven. De opdracht stopt nooit de BBjServices. Wanneer het het eigenschappenbestand heeft hersteld, start dan de BBjServices opnieuw om het toe te passen. De pluginmap blijft behouden.

| Instelling | Standaard | Beschrijving |
|---|---|---|
| `--yes` | Uit | Slaat de bevestiging over. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | BBjServices eigenschappenbestand dat `reset` herstelt. |

De opdracht toont wat het doet en vraagt om bevestiging, tenzij `--yes` wordt gegeven. Op Windows eindigt `reset` zonder `--yes` met exitcode `2` voordat het inlogt.

:::warning Maak eerst een back-up
`reset` kan niet ongedaan worden gemaakt. Het verwijdert de map van elke app die BBjShip heeft gedeployed en beëindigt hun draaiende sessies. Voordat je het uitvoert, houd het uitrolarchief van elke app, bijvoorbeeld de `target/bbj/<publish name>.zip` van de build, en maak een back-up van `<BBj home>/cfg/BBj.properties`. Wanneer er een kopie van het bestand in de BBjShip-runtimemap is bewaard, plaatst `reset` die kopie terug over `cfg/BBj.properties`, zodat iedere wijziging die je aan het bestand hebt aangebracht na de eerste ontwikkeling run verloren gaat. Start de BBjServices hierna opnieuw op om de herstelde Java-instellingen toe te passen.
:::

## Exitcodes {#exit-codes}

| Code | Betekenis |
|---|---|
| `0` | De opdracht is geslaagd. |
| `1` | De opdracht is mislukt en heeft de oorzaak weergegeven, of `reset` is geannuleerd. |
| `2` | De argumenten zijn ongeldig, of de opdracht heeft standaardinvoer nodig op Windows. Het bericht geeft aan wat moet worden opgegeven. |

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#command-line) vermeldt de berichten en wat te doen bij elk.
