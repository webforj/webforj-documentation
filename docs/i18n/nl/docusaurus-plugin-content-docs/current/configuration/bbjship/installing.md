---
title: BBjShip installeren
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---

# BBjShip installeren <DocChip chip='since' label='26.03' />

BBjShip draait in BBjServices en is daarom altijd geïnstalleerd op de machine waarop BBjServices draait. Zodra het is geïnstalleerd en het implementatie-eindpunt is ingeschakeld, worden je builds ernaartoe geïmplementeerd via het adres `/webforj-bbjship` op de BBjServices-webserver, vanaf diezelfde machine of vanaf een andere machine.

Hoe je BBjShip installeert, hangt af van waar BBjServices draait:

| Waar BBjServices draait | Wie BBjShip installeert | Hoe |
|---|---|---|
| Je eigen machine | De webforJ-buildplugin | Automatisch met `mvn`, of eenmalig met `mvn webforj:bbj-setup` |
| Een gedeelde of productie-server | Een beheerder van die server | De BBj Plugin Manager, daarna `bbjship remote enable` |

Installeer in beide gevallen de versie van BBjShip die overeenkomt met de webforJ-versie van je apps. Implementaties mislukken als de versies verschillen.

## Op je eigen machine {#on-your-own-machine}

Wanneer BBj op dezelfde machine draait als je build, hoef je BBjShip niet zelf te installeren. De eerste [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) van een project dat is geconfigureerd voor BBjShip, installeert het en schakelt het implementatie-eindpunt in:

```bash
mvn
```

### Via de Maven-plugin {#through-the-maven-plugin}

Als je wilt implementeren met `mvn install` voordat je een ontwikkelrun start, installeer BBjShip dan eenmalig vanuit de map van je project:

```bash
mvn webforj:bbj-setup
```

Het doel installeert de BBjShip-versie die overeenkomt met de `webforj-maven-plugin` en schakelt het implementatie-eindpunt in op de poort van je implementatie-URL. Hiervoor is het volgende nodig:

- BBjServices moet actief zijn en reageren op de poort van de implementatie-URL
- Een implementatie-URL met de naam van je machine, bijvoorbeeld `http://localhost:8888/webforj-bbjship`
- De gebruikersnaam en het wachtwoord van de BBj-beheerder, uit `<admin>` of de eigenschappen `webforj.bbj.admin.*`. Als deze ontbreken, gebruikt het doel het fabrieksaccount `admin` met `admin123` en geeft het een waarschuwing weer.
- De installatiemap van BBj, uit `<home>`, `webforj.bbj.home` of `BBJ_HOME`. Als deze ontbreken, wordt de map automatisch gedetecteerd.

Vervang na een webforJ-upgrade de geïnstalleerde BBjShip-versie door de nieuwe versie:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Je geïmplementeerde apps blijven geïmplementeerd wanneer je het doel opnieuw uitvoert.

:::tip Bestanden in gebruik op Windows
Op Windows kan vervanging mislukken terwijl BBjServices de bestanden van de geïnstalleerde BBjShip openhoudt. Start BBjServices opnieuw en voer het doel opnieuw uit. Zie [Probleemoplossing](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Op een server {#on-a-server}

Op een gedeelde of productie-BBj-server installeert een beheerder BBjShip eenmalig op die server. Ontwikkelaars en CI-pipelines implementeren er vervolgens vanaf hun eigen machines naartoe via HTTPS.

1. Installeer BBjShip met de [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Schakel het implementatie-eindpunt in](#enabling-the-deployment-endpoint) met de HTTPS-poort van de BBjServices-webserver.
3. Maak in Enterprise Manager een autorisatietoken voor de builds die implementeren. Zie [Implementeren vanaf een andere machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Via de BBj Plugin Manager {#through-the-bbj-plugin-manager}

Installeer BBjShip in de [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) vanuit de repository `webforj-bbjship` van de organisatie `BBj-Plugins`. Een tag installeert een webforJ-release en `master` installeert de ontwikkelingssnapshot. Werk de versie later bij via het tabblad Installed.

### Het implementatie-eindpunt inschakelen {#enabling-the-deployment-endpoint}

Het implementatie-eindpunt is het adres waarnaar builds worden geïmplementeerd. Schakel het op de server in met je BBj-beheerdersaccount en de poorten waarop de BBjServices-webserver luistert. `--password` verwacht het wachtwoord als waarde. Andere gebruikers van de machine kunnen het wachtwoord tijdens de uitvoering van de opdracht zien in de proceslijst. Op macOS en Linux leest `--password-stdin` het wachtwoord van de standaardinvoer, en met `WEBFORJ_BBJ_PASSWORD` blijft het op elk systeem buiten de opdrachtregel:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Zonder `--port` gebruikt het eindpunt poort 8888. Zonder `--ssl-port` heeft het geen HTTPS-adres. Builds op andere machines implementeren naar het HTTPS-adres. Het eindpunt antwoordt met HTTP 403 op hun verzoeken via HTTP, tenzij `--allow-unencrypted` is opgegeven. BBjServices moet actief zijn. Zie [Opdrachtregel](/docs/configuration/bbjship/command-line) voor alle opdrachten.

:::tip Wachtwoord op Windows
Voer op Windows `bbjship.cmd` uit en geef het wachtwoord op met `--password` of `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` is daar niet beschikbaar en eindigt met exitcode `2`.
:::

## Verwijderen {#uninstalling}

Voer eerst `bbjship reset` uit. Hiermee worden alle apps gedeïmplementeerd en wordt alles verwijderd wat BBjShip in Enterprise Manager heeft ingesteld. Verwijder BBjShip vervolgens via de BBj Plugin Manager.

:::warning Maak eerst een back-up
`bbjship reset` kan niet ongedaan worden gemaakt. Maak eerst een back-up van `<BBj home>/cfg/BBj.properties` en bewaar het implementatiearchief van elke app. Zie [Resetten](/docs/configuration/bbjship/command-line#reset).
:::
