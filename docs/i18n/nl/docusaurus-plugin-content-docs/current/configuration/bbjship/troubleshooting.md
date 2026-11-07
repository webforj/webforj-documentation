---
title: Probleemoplossing
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: 2db5dad70f56c06a6aab75752a9c260f
---
# Problemen oplossen <DocChip chip='since' label='26.03' /> {#troubleshooting}

Op deze pagina staan de meldingen die je kunt tegenkomen wanneer je BBjShip instelt, een app implementeert, een ontwikkelrun start of de opdrachtregel `bbjship` gebruikt. Bij elke melding staat de oorzaak en wat je moet doen.

Een mislukte Maven-doel beëindigt de build met een korte melding van het doel, gevolgd door de reden. Op deze pagina vind je de reden:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Referenties en verbinding {#credentials-and-connection}

Deze meldingen kunnen afkomstig zijn van elk doel dat met BBjShip communiceert: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` en `watch`.

| Melding | Oorzaak | Wat moet je doen? |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Er is geen gebruikersnaam en geen wachtwoord ingesteld, dus het doel meldt zich aan als `admin` met `admin123`. | Wijzig het wachtwoord in Enterprise Manager en configureer de nieuwe referenties. Zie [Instellingen](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Er is slechts een van beide ingesteld: de gebruikersnaam of het wachtwoord. | Configureer beide, of geen van beide om het fabrieksaccount te gebruiken. `bbj-deploy` en `bbj-undeploy` accepteren ook een token. |
| `BBj deployment requires bbj.deployment.url...` | Er is geen implementatie-URL ingesteld. | Stel `<bbj><deployment><url>` in of geef `-Dwebforj.bbj.deployment.url=<URL>` mee. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | De URL gebruikt een ander schema of bevat een gebruikersnaam, query of fragment. | Gebruik `http://<host>:<port>/webforj-bbjship` of `https://<host>:<port>/webforj-bbjship` en plaats de referenties in `<admin>`. |
| `Connect to ... failed: Connection refused` | Er reageert niets op de host en poort van de implementatie-URL. | Start BBjServices en controleer of de host en poort van de URL overeenkomen met de webserver van BBjServices. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | De URL leidt niet naar BBjShip, of BBjShip is niet geïnstalleerd of het eindpunt is niet ingeschakeld op die poort. | Controleer het pad `/webforj-bbjship` en de poort. Voer op de BBj-machine `mvn webforj:bbj-setup` uit, of voer op een server `bbjship remote enable` uit. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices heeft de gebruikersnaam en het wachtwoord of het token geweigerd. | Controleer de referenties. Vernieuw een token dat is verlopen of ingetrokken in Enterprise Manager. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | De geïnstalleerde BBjShip-versie en `webforj-maven-plugin` hebben verschillende versies. | Voer op je eigen machine `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. Installeer op een server de overeenkomende BBjShip-versie met BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java vertrouwt het certificaat van de BBjServices-webserver niet. | Installeer op de BBj-machine een certificaat voor de hostnaam van de URL, of voeg het certificaat toe aan de Java-truststore van de build. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | De implementatie-URL is een `http://`-URL naar een andere machine. Het doel stopt voordat er iets wordt verzonden. Met `allowUnencrypted` wordt dezelfde tekst een waarschuwing en gaat het doel verder. | Gebruik een `https://`-URL met de SSL-poort, of sta HTTP aan beide kanten toe. Zie [Implementeren vanaf een andere machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Instellen {#setup}

Deze meldingen zijn afkomstig van `mvn webforj:bbj-setup` en van de eerste [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) van een project. Die stelt BBjShip op dezelfde manier in.

| Melding | Oorzaak | Wat moet je doen? |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | Het doel is mislukt. De reden volgt erna. Als de reden over het oplossen van een artefact gaat, kon Maven `webforj-bbjship-plugin` niet downloaden in de versie van `webforj-maven-plugin`. | Lees de reden. Controleer bij problemen met het oplossen van een artefact de repositories en netwerktoegang van de build. |
| `BBj setup is local only. Run setup on the BBj host` | De implementatie-URL verwijst naar een andere machine. | Voer de installatie uit op de BBj-machine, of installeer BBjShip daar met [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | De hostnaam van de implementatie-URL is onbekend. | Gebruik `localhost` of een naam van deze machine. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Er is een token ingesteld voor een ontwikkelrun. Ontwikkelruns melden zich alleen aan met een gebruikersnaam en wachtwoord. | Configureer `<admin><username>` en `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | Het doel heeft geen BBj-installatie op deze machine gevonden. | Stel `<bbj><home>`, `-Dwebforj.bbj.home` of de variabele `BBJ_HOME` in op de map met de BBj-installatie. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | De ingestelde map is geen volledige BBj-installatie. | Stel de map in die de mappen `bin` en `cfg` van BBj bevat. |
| `Installed BBjShip version <version> does not match <version>...` | Er is een andere BBjShip-versie geïnstalleerd, bijvoorbeeld na een upgrade van webforJ. | Voer `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. Geïmplementeerde apps blijven geïmplementeerd. |
| `Installed BBjShip version is missing...` | De geïnstalleerde BBjShip-map is onvolledig. | Voer `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | De BBj-map `plugins` of de BBjShip-map daarin is een symbolische link of bevat er een. | Installeer in een gewone map. |
| `BBj setup command failed. Check credentials and local BBjServices.` gevolgd door de uitvoer van de opdracht | De opdracht die het implementatie-eindpunt inschakelt, is mislukt. De uitvoer vermeldt de reden, bijvoorbeeld een geweigerde aanmelding of een poort waarop de webserver niet luistert. | Controleer de referenties, of BBjServices actief is en of de poort van de implementatie-URL een poort van de BBjServices-webserver is. |
| `BBj setup command timed out. Check local BBjServices` | De opdracht is niet binnen 120 seconden voltooid. | Controleer of BBjServices actief is en reageert, en voer het doel daarna opnieuw uit. |
| `Previous BBjShip files are preserved at <folder>` | De vervanging is mislukt en de bestanden van de vorige versie zijn in die map bewaard. | Verhelp de oorzaak van de fout en voer het doel opnieuw uit. |

## Implementeren en implementatie ongedaan maken {#deploy-and-undeploy}

### Vóór het uploaden {#before-the-upload}

Deze meldingen zijn afkomstig van `bbj-package`, `bbj-deploy` en `bbj-undeploy`, voordat er iets bij BBjShip aankomt.

| Melding | Oorzaak | Wat moet je doen? |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Het project is niet als jar verpakt. | Stel `<packaging>jar</packaging>` in. |
| `BBj deployment requires the packaged application jar...` | Het doel is uitgevoerd zonder de fase `package` in dezelfde build. | Voer `mvn install` of `mvn package webforj:bbj-deploy` uit. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` staat op het runtime-classpath van de app. | Sluit het uit van de afhankelijkheid `webforj`. Zie [Zonder de install-plugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | De app heeft tijdens runtime geen webforJ-afhankelijkheid. | Voeg de afhankelijkheid `webforj` toe met de scope `compile`. |
| `BBj deployment dependency is unresolved...` | Een afhankelijkheid van de app heeft geen bestand. | Controleer of Maven alle projectafhankelijkheden kan oplossen. |
| `Publish name <name> is invalid...` | De publicatienaam voldoet niet aan de regels in [Publicatienamen](/docs/configuration/bbjship/deploying#publish-names). | Kies een andere `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices gebruikt die naam voor eigen adressen. | Kies een andere `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` kon `target/bbj/<publish name>.zip` niet schrijven. De reden volgt erna. | Lees de reden. |

### Antwoorden van BBjShip {#answers-of-bbjship}

Wanneer BBjShip een implementatie of het ongedaan maken ervan weigert, begint de melding met `BBj deployment failed with HTTP <status>` of `BBj undeployment failed with HTTP <status>`, gevolgd door de reden.

| Status | Reden | Oorzaak | Wat moet je doen? |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` of `...is reserved by BBjServices...` | De publicatienaam wordt geweigerd. | Kies een andere publicatienaam. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Het bestand `webforj.conf` van de app kan niet worden gelezen, of een [app-instelling](/docs/configuration/bbjship/deploying#app-settings) heeft het verkeerde type. | Herstel het bestand zoals aangegeven in de details. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` of een andere reden die over het archief gaat | Het archief is niet geschreven door de build-plugin of is daarna gewijzigd. | Implementeer het archief dat door de build wordt geschreven, met dezelfde webforJ-versie als BBjShip. |
| 401 | `Check the BBj administration credentials or token` | De aanmelding is geweigerd. | Controleer de referenties of vernieuw het token. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | Het verzoek is via HTTP vanaf een andere machine binnengekomen. | Gebruik het HTTPS-adres van het eindpunt, of voer `bbjship remote enable --allow-unencrypted` uit op de BBj-machine. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Een ontwikkelrun heeft een machine bereikt waarop ontwikkelruns zijn uitgeschakeld. | Voer `bbjship development enable` uit op de BBj-machine. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Bij het ongedaan maken van een implementatie is een app opgegeven die niet door BBjShip is geïmplementeerd. | Controleer de publicatienaam met `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | De entry class van de app draait al onder een andere publicatienaam. | Maak de implementatie van de andere app ongedaan of implementeer onder de naam ervan. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Een app die in Enterprise Manager is geregistreerd, gebruikt die naam al. | Kies een andere publicatienaam of verwijder die app in Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Een andere app gebruikt dezelfde naam, maar met een andere combinatie van hoofdletters en kleine letters. | Kies een andere publicatienaam. |
| 409 | `<name> is a development run. Stop the watch first` | Er is een actieve ontwikkelrun van de app. | Stop de watch met <kbd>Ctrl</kbd>+<kbd>C</kbd> en implementeer daarna. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Een ontwikkelrun gebruikt de naam van een geïmplementeerde app. | Maak de implementatie van de app ongedaan of stel een andere `<publishName>` in voor de ontwikkelrun. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | Het archief is groter dan BBjShip toestaat. | Verwijder afhankelijkheden die de app tijdens runtime niet nodig heeft. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices is mislukt tijdens het installeren of verwijderen van de app. | Lees de BBjServices-logboeken. |

Een implementatie die eindigt met `did not confirm the requested application. Check the BBjShip version and BBjServices log` heeft BBjShip bereikt, maar geen bevestiging voor de app ontvangen. Controleer of BBjShip en `webforj-maven-plugin` dezelfde versie hebben en lees daarna het BBjServices-logboek.

### De actieve app {#the-running-app}

| Melding | Oorzaak | Wat moet je doen? |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | De app gebruikt `icons://` zonder verpakte assets. | Voeg `webforj.assetsSource = packaged` toe aan `webforj.conf` en implementeer opnieuw. |

## Ontwikkelruns {#development-runs}

Deze meldingen zijn afkomstig van `mvn` of `mvn compile webforj:watch` in een project dat is geconfigureerd voor BBjShip. Een mislukte ontwikkelrun begint met `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, gevolgd door de reden uit [Instellen](#setup), [Referenties en verbinding](#credentials-and-connection) of [Antwoorden van BBjShip](#answers-of-bbjship).

| Melding | Oorzaak | Wat moet je doen? |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | De watch is gestart voordat het project is gecompileerd. | Voer `mvn compile webforj:watch` uit, of voer `mvn` uit in een archetypeproject. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` verwijst naar een map die BBjServices niet kan vinden. | Stel het absolute pad in van de classmap op de BBj-machine. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | Het BBjServices-eigenschappenbestand staat niet op de locatie waar de run het zoekt. | Stel `<bbj><properties>` of `-Dwebforj.bbj.properties` in op het bestand. |
| `BBjServices did not stop within 180 seconds` | De run heeft BBjServices gevraagd te stoppen om de hotswap-instellingen toe te passen, maar BBjServices bleef actief. | Stop BBjServices zelf, start het opnieuw en voer de watch opnieuw uit. |
| `BBjServices start program is missing: <program>` | De BBj-installatie bevat geen programma om BBjServices te starten. | Start BBjServices zelf en voer de watch opnieuw uit. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices is na de herstart niet opnieuw gestart. | Start BBjServices zelf en voer de watch opnieuw uit. |
| `BBjShip did not answer within 120 seconds` | BBjServices is gestart, maar het implementatie-eindpunt reageerde niet. | Controleer het BBjServices-logboek en voer de watch opnieuw uit. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices is gestart met andere Java-argumenten dan die in het eigenschappenbestand staan. | Controleer de Java-instellingen van BBjServices in Enterprise Manager en in dat bestand. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | De app kon niet worden verwijderd toen de build stopte. | Verwijder de app met `bbjship apps undeploy <name>`, of start de watch opnieuw; die vervangt de app dan. |

De eerste ontwikkelrun die de hotswap-instellingen toevoegt, start BBjServices opnieuw. Op macOS en Linux start de run BBjServices met `bin/bbjservices`. Op Windows start de run de Windows-service `BBjServices` als die is geregistreerd; anders wordt `bin\BBjServices.exe -noservice` uitgevoerd. Een BBjServices die is gestart met `bin/bbjservices` of `-noservice` draait buiten de servicemanager van het besturingssysteem totdat de machine opnieuw wordt opgestart.

## Opdrachtregel {#command-line}

Elke opdracht `bbjship` wordt uitgevoerd binnen BBjServices, dus BBjServices moet actief zijn. Zonder BBjServices kan de opdracht niet starten.

Ongeldige argumenten eindigen met afsluitcode `2` en een melding die aangeeft wat je moet opgeven:

| Melding | Wat moet je doen? |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Geef een opdracht op. Zie [Opdrachtregel](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Gebruik een optie van die opdracht, zoals vermeld bij `--help`. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Geef een waarde op voor de optie. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Geef een poortnummer op. |
| `Specify <option> only once` | Verwijder de dubbele optie. |
| `Specify only one of --password and --password-stdin` | Gebruik een van beide opties. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Geef zowel de gebruikersnaam als het wachtwoord op. De melding vermeldt wat ontbreekt. |
| `Specify an application name or --all`, `Specify one application name` | Geef voor `apps undeploy` één publicatienaam of `--all` op. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Geef voor `apps deploy` één `.zip`-bestand op. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Controleer het pad. BBjServices leest het bestand, dus de besturingssysteemgebruiker van BBjServices moet er leestoegang toe hebben. |
| `Development archives are deployed by the watch, not by apps deploy` | Implementeer het archief van `bbj-package`, niet dat van een ontwikkelrun. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Geef het archief een andere naam. De bestandsnaam zonder `.zip` is de publicatienaam. |

Een mislukte opdracht eindigt met afsluitcode `1` en `Command failed.`, gevolgd door de reden:

| Reden | Oorzaak | Wat moet je doen? |
|---|---|---|
| Een mislukte aanmelding bij BBjServices | De gebruikersnaam of het wachtwoord is onjuist. | Controleer de referenties. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` of `--ssl-port` is geen poort waarop de BBjServices-webserver luistert. Het bestaande eindpunt blijft actief. | Gebruik de poorten die voor de webserver zijn ingesteld in Enterprise Manager. |
| `Application <name> is not deployed by BBjShip` | Bij `apps undeploy` is een app opgegeven die niet door BBjShip is geïmplementeerd. | Controleer de naam met `bbjship apps list`. |
| `Another deployment is in progress` | Een andere implementatie of het ongedaan maken ervan heeft de machine 30 seconden bezet gehouden. | Wacht tot de bewerking is voltooid en voer de opdracht daarna opnieuw uit. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Een andere app in Enterprise Manager gebruikt de webcontext `webforj-bbjship` of `webforj-bbjship-assets`. | Geef die context in Enterprise Manager een andere naam of verwijder deze. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | De classpaths `_webforj_bbjship_cli` of `_webforj_bbjship` in Enterprise Manager zijn gewijzigd. | Verwijder beide classpaths in Enterprise Manager en voer de opdracht opnieuw uit; ze worden dan opnieuw aangemaakt. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | De BBjShip-map is onvolledig. | Installeer BBjShip opnieuw. |
| `BBjServices does not manage web contexts. Update BBj` | De BBj-versie is te oud voor BBjShip. | Installeer de BBj-versie die overeenkomt met webforJ. |

`reset` eindigt met afsluitcode `1` en `Reset cancelled` wanneer het antwoord op `Continue? [y/N]` niet `y` of `Y` is. Wanneer de Java-instellingen van BBjServices zijn hersteld, eindigt de opdracht met `Restart BBjServices now to apply the restored Java settings`. Start BBjServices opnieuw om het zonder de hotswap-instellingen uit te voeren.

## Windows {#windows}

| Situatie | Wat moet je doen? |
|---|---|
| Het uitvoeren van `bbj.exe` met het BBjShip-programma geeft geen uitvoer. | Voer `bbjship.cmd` uit in `<BBj home>\plugins\webforj-bbjship`. Daarmee wordt de uitvoer van de opdracht weergegeven. |
| `--password-stdin` eindigt met afsluitcode `2` en `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Geef het wachtwoord mee met `--password`, of stel `WEBFORJ_BBJ_USERNAME` en `WEBFORJ_BBJ_PASSWORD` in. |
| `reset` eindigt met afsluitcode `2` en `Standard input is not available on Windows. Run reset --yes to confirm`. | Voer `bbjship.cmd reset --yes` uit. |
| `apps undeploy` of `reset` geeft `Not removed: <folder>. <reason>` weer en eindigt met afsluitcode `0`. | BBjServices heeft de bestanden van de app nog open. De app is uit Enterprise Manager verwijderd. Start BBjServices opnieuw. De map wordt verwijderd bij de volgende implementatie met die naam, of je kunt de map na de herstart verwijderen. |
| De installatie van BBjShip over een bestaande versie mislukt omdat de bestanden in gebruik zijn. | BBjServices heeft de bestanden van de geïnstalleerde BBjShip nog open. Start BBjServices opnieuw en installeer daarna opnieuw. |
| BBjServices draait na een herstart door een ontwikkelrun niet als Windows-service. | De ontwikkelrun heeft BBjServices gestart met `BBjServices.exe -noservice`, omdat er geen service `BBjServices` is geregistreerd. Het programma draait buiten de Windows-servicemanager totdat de machine opnieuw wordt opgestart. |
| Een opdracht `bbjship.cmd` kan niet starten. | Start BBjServices. Elke opdracht `bbjship` vereist dat BBjServices actief is. |
