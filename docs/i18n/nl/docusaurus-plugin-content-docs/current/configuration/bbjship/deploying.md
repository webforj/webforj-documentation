---
title: Implementeren
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# Implementeren <DocChip chip='since' label='26.03' /> {#deploying}

De webforJ Maven-plug-in implementeert een app op [BBjShip](/docs/configuration/bbjship/overview) met drie doelen:

| Doel | Wordt uitgevoerd | Wat het doet |
|---|---|---|
| `bbj-package` | tijdens de fase `package` | Schrijft het implementatiearchief `target/bbj/<publish name>.zip` |
| `bbj-deploy` | tijdens de fase `install` | Schrijft het archief en implementeert het op BBjShip |
| `bbj-undeploy` | handmatig | Verwijdert de app uit BBjServices |

De fasen zijn van toepassing wanneer de plug-in is gedeclareerd met `<extensions>true</extensions>` en een `<bbj>`-blok. Zie [Een project configureren](/docs/configuration/bbjship/existing-projects).

## Implementeren {#deploy}

```bash
mvn install
```

Zonder de extensie voer je `mvn package webforj:bbj-deploy` uit. Zodra de implementatie is voltooid, geeft het doel het adres van de app weer:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

Als een implementatie mislukt, blijft de vorige versie actief.

:::warning De app wordt vervangen
Een implementatie vervangt de app met dezelfde publicatienaam. De vorige implementatiemap wordt verwijderd en de actieve sessies van die app worden beëindigd. Bewaar het archief van de vorige versie voor het geval je wilt teruggaan.
:::

[Probleemoplossing](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) bevat de meldingen van een mislukte implementatie en wat je bij elke melding kunt doen.

## Pakket maken zonder te implementeren {#package-without-deploying}

`bbj-package` schrijft hetzelfde archief, zonder implementatie-URL of inloggegevens. Een CI-taak kan `target/bbj/<publish name>.zip` aan de build toevoegen; een BBj-beheerder implementeert het vervolgens op de BBj-machine met [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Implementatie ongedaan maken {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

De app wordt gestopt en samen met de bijbehorende bestanden uit Enterprise Manager verwijderd.

:::warning Kan niet ongedaan worden gemaakt
`bbj-undeploy` beëindigt de actieve sessies van de app en verwijdert de implementatiemap. Dit kan niet ongedaan worden gemaakt. Bewaar het archief uit `target/bbj/<publish name>.zip` als je de app misschien opnieuw wilt implementeren.
:::

## Publicatienamen {#publish-names}

De publicatienaam is standaard de definitieve naam van de build en komt voor in het app-adres. De naam begint met een letter of cijfer, bevat alleen letters, cijfers, `_`, `.` en `-`, en eindigt niet op een punt. Gereserveerde namen, zoals de BBjServices-paden `webapp` en `files` of de Windows-apparaatnaam `con`, worden geweigerd voordat het archief wordt geschreven.

Elke entry class hoort bij één geïmplementeerde app. Als je die onder een tweede naam implementeert, mislukt dit met:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Implementeren vanaf een andere machine {#deploying-from-another-machine}

Voor implementatie vanaf een andere machine, zoals een CI-server, gebruik je een autorisatietoken via HTTPS:

1. [Schakel het endpoint in](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) op de BBj-machine met een SSL-poort.
2. Maak in Enterprise Manager een token aan onder Security, Authentication Tokens. Je kunt het daar ook vernieuwen en intrekken.
3. Geef het token door aan de build:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Het certificaat van de BBjServices-webserver moet overeenkomen met de hostnaam in de URL of in de Java-truststore van de build staan. Java vertrouwt het standaardcertificaat van BBj niet, omdat dit zelfondertekend is.

Een `http://`-URL naar een andere machine wordt aan beide kanten geweigerd. Het doel stopt voordat het iets verstuurt, en BBjShip antwoordt met HTTP 403 op een verzoek dat via HTTP vanaf een andere machine binnenkomt. Een URL naar de BBj-machine zelf, `localhost` of een van de eigen adressen ervan, werkt via HTTP.

Als je toch via HTTP naar een andere machine wilt implementeren, bijvoorbeeld naar een virtuele machine op je eigen computer, moet je dit aan beide kanten toestaan. Voer op de BBj-machine `bbjship remote enable --allow-unencrypted` uit met de poorten van het endpoint. Stel in de build `<deployment><allowUnencrypted>true</allowUnencrypted>` in of geef de eigenschap mee:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

Het doel geeft dan een waarschuwing weer en voert de implementatie uit. De inloggegevens worden via die verbinding onversleuteld over het netwerk verzonden.

## App-instellingen {#app-settings}

BBjShip leest bij elke implementatie het `webforj.conf`-bestand van de app en past de volgende eigenschappen toe op de app in Enterprise Manager. Bij een nieuwe implementatie wordt een gewijzigde waarde overgenomen. Een eigenschap die niet is ingesteld, behoudt de standaardwaarde van BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Eigenschap | Standaardwaarde | Beschrijving |
|---|---|---|
| `webforj.assetsSource` | Niet ingesteld | Met `packaged` worden de statische bestanden `ws://` en de pictogrammen `icons://` vanuit de app aangeboden op `/webforj/<publish name>/`. Zonder deze instelling kan een app die `icons://` gebruikt niet starten. |
| `webforj.showClientConfirmationDialog` | `false` | Bepaalt of de browser om bevestiging vraagt voordat de pagina wordt gesloten. |
| `webforj.disallowConsole` | `true` | Bepaalt of de console in de app niet is toegestaan. |
| `webforj.secure` | Standaardwaarde van BBjServices | Bepaalt of de app via een beveiligde verbinding wordt uitgevoerd. |
| `webforj.configFile` | Standaardwaarde van BBjServices | Pad naar het BBj-configuratiebestand waarmee de app wordt uitgevoerd, zoals BBjServices dit op de BBj-machine leest. Het pad wordt ongewijzigd doorgegeven en kan dus verwijzen naar een bestand dat alleen op de server beschikbaar is. |
| `webforj.programArguments` | Geen | Programmaargumenten van de app, als lijst met tekenreeksen of als één tekenreeks. |

Een ongeldig `webforj.conf`-bestand laat de implementatie mislukken met HTTP 400 en een reden.

## Instellingen {#settings}

De instellingen staan in het `<bbj>`-blok van de configuratie van `webforj-maven-plugin`, zoals getoond bij [Build-plug-in](/docs/configuration/bbjship/existing-projects#the-build-plugin). Elke eigenschap overschrijft het bijbehorende element op de opdrachtregel.

| Maven-element | Maven-eigenschap | Standaardwaarde | Beschrijving |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Automatisch gedetecteerd | Installatiemap van BBj, gebruikt door `bbj-setup` en `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Definitieve naam van de build | Naam waaronder de app wordt gepubliceerd; zie [Publicatienamen](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Geen, vereist | Adres van BBjShip: `http://<host>:<HTTP port>/webforj-bbjship` of `https://<host>:<SSL port>/webforj-bbjship`. |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | Hiermee mogen `bbj-deploy` en `bbj-undeploy` de inloggegevens via een `http://`-URL naar een andere machine verzenden; zie [Implementeren vanaf een andere machine](#deploying-from-another-machine). |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` wanneer zowel de gebruikersnaam als het wachtwoord ontbreken | Gebruikersnaam van de BBj-beheerder. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` wanneer zowel de gebruikersnaam als het wachtwoord ontbreken | Wachtwoord van de BBj-beheerder. |
| `<admin><token>` | `webforj.bbj.admin.token` | Leeg | Autorisatietoken voor `bbj-deploy` en `bbj-undeploy`. Heeft voorrang op de gebruikersnaam en het wachtwoord. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices-eigenschappenbestand waarin een [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) de hotswap-instellingen schrijft. |
| `<development><classFolder>` | — | Uitvoermap van de build | Class-map van een ontwikkelrun, zoals BBjServices die leest. |
| — | `webforj.bbj.force` | `false` | Hiermee kan `bbj-setup` een geïnstalleerde BBjShip van een andere versie vervangen. Alleen als eigenschap beschikbaar. |

Een gebruikersnaam zonder wachtwoord, of een wachtwoord zonder gebruikersnaam, zorgt ervoor dat het doel stopt.
