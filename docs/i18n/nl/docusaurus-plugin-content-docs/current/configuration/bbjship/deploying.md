---
title: Deployen
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# Implementeren van <DocChip chip='since' label='26.03' /> {#deploying}

De webforJ Maven-plugin implementeert een app naar [BBjShip](/docs/configuration/bbjship/overview) met drie doelen:

| Doel | Draait | Wat het doet |
|---|---|---|
| `bbj-package` | tijdens de `package` fase | Schrijft het implementatiearchief `target/bbj/<publish name>.zip` |
| `bbj-deploy` | tijdens de `install` fase | Schrijft het archief en implementeert het naar BBjShip |
| `bbj-undeploy` | handmatig | Verwijdert de app van BBjServices |

De fasen zijn van toepassing wanneer de plugin is gedeclareerd met `<extensions>true</extensions>` en een `<bbj>` blok, zie [Een project configureren](/docs/configuration/bbjship/existing-projects).

## Implementeren {#deploy}

```bash
mvn install
```

Zonder de extensie, voer `mvn package webforj:bbj-deploy` uit. Het doel print het adres van de app wanneer de implementatie is voltooid:

```text
[INFO] BBj-implementatie van inventaris volbracht op http://localhost:8888/webapp/inventory
```

Wanneer een implementatie mislukt, blijft de vorige versie draaien.

:::warning Vervangt de app
Een implementatie vervangt de app die al dezelfde publicatienaam heeft. De vorige implementatiemap wordt verwijderd en de actieve sessies van die app eindigen. Bewaar het archief van de vorige versie als je mogelijk terug wilt gaan.
:::

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) geeft de berichten van een mislukte implementatie weer en wat je bij elk moet doen.

## Pakket zonder implementatie {#package-without-deploying}

`bbj-package` schrijft hetzelfde archief zonder een implementatie-URL of inloggegevens. Een CI-taak kan `target/bbj/<publish name>.zip` aan de build koppelen, en een BBj-beheerder implementeert het op de BBj-machine met [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Onimplementeren {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

De app stopt en wordt verwijderd uit Enterprise Manager, samen met zijn bestanden.

:::warning Kan niet ongedaan gemaakt worden
`bbj-undeploy` beëindigt de actieve sessies van de app en verwijdert de implementatiemapper. Dit kan niet ongedaan gemaakt worden. Bewaar het archief van `target/bbj/<publish name>.zip` als je de app mogelijk opnieuw wilt implementeren.
:::

## Publicatienamen {#publish-names}

De publicatienaam is standaard de uiteindelijke naam van de build en verschijnt in het app-adres. Het begint met een letter of cijfer, bevat alleen letters, cijfers, `_`, `.` en `-`, en eindigt niet met een punt. Gereserveerde namen, zoals de BBjServices-paden `webapp` en `files` of de Windows-apparaatnaam `con`, worden geweigerd voordat het archief wordt geschreven.

Elke invoerklasse behoort tot één geïmplementeerde app. Implementatie onder een tweede naam mislukt met:

```text
BBj-implementatie mislukt met HTTP 409. Invoergroep com.example.Application is al gepubliceerd als inventaris. Onimplementeer inventaris eerst
```

## Implementeren vanaf een andere machine {#deploying-from-another-machine}

Een implementatie vanaf een andere machine, zoals een CI-server, gebruikt een autorisatietoken over HTTPS:

1. Schakel op de BBj-machine [de endpoint in](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) met een SSL-poort.
2. Maak in Enterprise Manager een token aan onder Beveiliging, Authenticatietokens. Vernieuwing en intrekken daar ook.
3. Geef het token door aan de build:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Het certificaat van de BBjServices-webserver moet overeenkomen met de hostnaam van de URL, of in de Java truststore van de build staan. Java vertrouwt het standaard BBj-certificaat niet, dat zelfondertekend is. Een `http://` URL naar een andere machine verzendt de inloggegevens leesbaar over het netwerk, en de doelen geven daar een waarschuwing voor.

## App-instellingen {#app-settings}

BBjShip leest de `webforj.conf` van de app bij elke implementatie en past deze eigenschappen toe op de app in Enterprise Manager. Een herimplementatie pakt een gewijzigde waarde op. Een eigenschap die niet is ingesteld, behoudt de standaard van BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Eigenschap | Standaard | Beschrijving |
|---|---|---|
| `webforj.assetsSource` | Niet ingesteld | `packaged` bedient de `ws://` statische bestanden en de `icons://` iconen van de app, op `/webforj/<publish name>/`. Zonder deze, kan een app die `icons://` gebruikt niet starten. |
| `webforj.showClientConfirmationDialog` | `false` | Of de browser om bevestiging vraagt voordat de pagina sluit. |
| `webforj.disallowConsole` | `true` | Of de console in de app is verboden. |
| `webforj.secure` | BBjServices standaard | Of de app draait via een beveiligde verbinding. |
| `webforj.configFile` | BBjServices standaard | Pad van het BBj-configuratiebestand waarmee de app draait, zoals BBjServices het op de BBj-machine leest. Het pad wordt doorgegeven zoals geschreven, dus het kan een bestand benoemen dat alleen de server heeft. |
| `webforj.programArguments` | Geen | Programma-argumenten van de app, als een lijst van strings of als één string. |

Een ongeldige `webforj.conf` mislukt de implementatie met HTTP 400 en de reden.

## Instellingen {#settings}

De instellingen bevinden zich in het `<bbj>` blok van de configuratie van de `webforj-maven-plugin`, zoals weergegeven in [Build-plugin](/docs/configuration/bbjship/existing-projects#the-build-plugin). Elke eigenschap overschrijft zijn element op de opdrachtregel.

| Maven-element | Maven-eigenschap | Standaard | Beschrijving |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Gedetecteerd | BBj-installatiemap, gebruikt door `bbj-setup` en `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Uiteindelijke naam van de build | Naam waaronder de app wordt gepubliceerd, zie [Publicatienamen](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Geen, vereist | Adres van BBjShip, `http://<host>:<HTTP port>/webforj-bbjship` of `https://<host>:<SSL port>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` wanneer gebruikersnaam en wachtwoord beide afwezig zijn | BBj-beheerder gebruikersnaam. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` wanneer gebruikersnaam en wachtwoord beide afwezig zijn | BBj-beheerder wachtwoord. |
| `<admin><token>` | `webforj.bbj.admin.token` | Leeg | Autorisatietoken voor `bbj-deploy` en `bbj-undeploy`. Heeft voorrang boven de gebruikersnaam en het wachtwoord. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices-eigenschapsbestand waarin een [ontwikkelingsrun](/docs/configuration/deploy-reload/bbjservices) de hotswap-instellingen schrijft. |
| `<development><classFolder>` | — | Build-uitvoer directory | Klassenmap van een ontwikkelingsrun zoals BBjServices deze leest. |
| — | `webforj.bbj.force` | `false` | Laat `bbj-setup` een geïnstalleerde BBjShip van een andere versie vervangen. Alleen eigenschap. |

Een gebruikersnaam zonder wachtwoord, of een wachtwoord zonder gebruikersnaam, stopt het doel.
