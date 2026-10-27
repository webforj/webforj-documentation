---
title: Installing BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# BBjShip installeren <DocChip chip='since' label='26.03' />

BBjShip draait binnen BBjServices, dus het is altijd geïnstalleerd op de machine die BBjServices draait. Zodra het is geïnstalleerd en zijn implementatie-eindpunt is ingeschakeld, worden je builds naar dit eindpunt gedeployed via het adres `/webforj-bbjship` op de webserver van BBjServices, zowel vanaf diezelfde machine als vanaf een andere.

Hoe je BBjShip installeert, hangt af van waar BBjServices draait:

| Waar BBjServices draait | Wie installeert BBjShip | Hoe |
|---|---|---|
| Je eigen machine | De webforJ build plugin | Automatisch met `mvn`, of een keer met `mvn webforj:bbj-setup` |
| Een gedeelde of productie-server | Een administrator van die server | De BBj Plugin Manager, dan `bbjship remote enable` |

In beide gevallen installeer je de BBjShip-versie die overeenkomt met de webforJ-versie van je apps. Deploys falen wanneer deze twee verschillen.

## Op je eigen machine {#on-your-own-machine}

Wanneer BBj draait op dezelfde machine als je build, installeer je BBjShip niet zelf. De eerste [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) van een project dat is geconfigureerd voor BBjShip installeert het en schakelt het implementatie-eindpunt in:

```bash
mvn
```

### Via de Maven plugin {#through-the-maven-plugin}

Om te deployen met `mvn install` voordat je een ontwikkelrun start, installeer je BBjShip een keer vanuit de map van je project:

```bash
mvn webforj:bbj-setup
```

De doelopdracht installeert de BBjShip-versie die overeenkomt met de `webforj-maven-plugin` en schakelt het implementatie-eindpunt in op de poort van je implementatie-URL. Het vereist:

- BBjServices draaiende en antwoorden op de poort van de implementatie-URL
- Een implementatie-URL die je machine benoemt, bijvoorbeeld `http://localhost:8888/webforj-bbjship`
- De BBj administrator gebruikersnaam en wachtwoord, van `<admin>` of de `webforj.bbj.admin.*` eigenschappen. Zonder deze gebruikt de doelopdracht het fabrieksaccount `admin` met `admin123` en print een waarschuwing.
- De BBj installatie map, van `<home>`, `webforj.bbj.home` of `BBJ_HOME`. Zonder deze detecteert de doelopdracht het.

Na een webforJ upgrade vervang je de geïnstalleerde BBjShip met de nieuwe versie:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Je geleverde apps blijven gedeployed wanneer je de doelopdracht opnieuw uitvoert.

:::tip Bestanden in gebruik op Windows
Op Windows kan een vervanging mislukken terwijl BBjServices de bestanden van de geïnstalleerde BBjShip openhoudt. Start BBjServices opnieuw op en voer de doelopdracht opnieuw uit, zie [Problemen oplossen](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Op een server {#on-a-server}

Op een gedeelde of productie BBj-server installeert een administrator BBjShip een keer op die server. Ontwikkelaars en CI-pijplijnen kunnen er dan vanuit hun eigen machines via HTTPS naartoe deployen.

1. Installeer BBjShip met de [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Schakel het implementatie-eindpunt in](#enabling-the-deployment-endpoint) met de HTTPS-poort van de BBjServices webserver.
3. Maak een autorisatietoken aan in Enterprise Manager voor de builds die deployen, zie [Deployen vanaf een andere machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Via de BBj Plugin Manager {#through-the-bbj-plugin-manager}

In de [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) installeer je BBjShip vanuit de `webforj-bbjship` repository van de `BBj-Plugins` organisatie. Een tag installeert een webforJ release, en `master` installeert de ontwikkelings-snapshot. Werk het later bij op het tabblad Geïnstalleerd.

### Het implementatie-eindpunt inschakelen {#enabling-the-deployment-endpoint}

Het implementatie-eindpunt is het adres waar builds naartoe implementeren. Schakel het in op de server met je BBj administratoraccount en de poorten waarop de BBjServices webserver luistert. `--password` neemt het wachtwoord als waarde, die andere gebruikers van de machine zien in de proceslijst terwijl de opdracht draait. Op macOS en Linux leest `--password-stdin` het uit de standaardinvoer, en `WEBFORJ_BBJ_PASSWORD` houdt het uit de opdrachtregel op elk systeem:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Zonder `--port` gebruikt het eindpunt poort 8888. Zonder `--ssl-port` heeft het geen HTTPS-adres. BBjServices moet draaien. Zie [Opdrachtregel](/docs/configuration/bbjship/command-line) voor elke opdracht.

:::tip Wachtwoord op Windows
Op Windows, voer `bbjship.cmd` uit en geef het wachtwoord door met `--password` of `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` is daar niet beschikbaar en eindigt met exitcode `2`.
:::

## Uninstalleren {#uninstalling}

Voer eerst `bbjship reset` uit. Dit undeploys elke app en verwijdert alles wat BBjShip in Enterprise Manager heeft ingesteld. Verwijder dan BBjShip in de BBj Plugin Manager.

:::warning Maak eerst een back-up
`bbjship reset` kan niet ongedaan gemaakt worden. Maak een back-up van `<BBj home>/cfg/BBj.properties` en bewaar het implementatie-archief van elke app eerst, zie [Reset](/docs/configuration/bbjship/command-line#reset).
:::
