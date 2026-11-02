---
title: Probleemoplossing
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# Probleemoplossing <DocChip chip='since' label='26.03' /> {#troubleshooting}

Deze pagina somt de berichten op die je kunt tegenkomen wanneer je BBjShip instelt, een app implementeert, een ontwikkelrun start of het `bbjship`-opdrachtregelprogramma gebruikt. Voor elk bericht geeft het de oorzaak en wat te doen.

Een mislukte Maven-doelstelling beëindigt de build met een kort bericht van de doelstelling, gevolgd door de reden. De reden is het deel dat deze pagina opsomt:

```text
BBj implementatie mislukt. Controleer het applicatiepakket, afhankelijkheden en implementatie-instellingen: BBj-implementatie mislukt met HTTP 409. De invoerklasse com.example.Application is al gepubliceerd als inventaris. Ontdek de inventaris eerst
```

## Referenties en verbinding {#credentials-and-connection}

Deze berichten komen van elke doelstelling die met BBjShip communiceert: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` en `watch`.

| Bericht | Oorzaak | Wat te doen |
|---|---|---|
| `Waarschuwing: het BBj-fabrieksbeheerderaccount admin wordt gebruikt met het bijbehorende fabriekswachtwoord...` | Noch een gebruikersnaam, noch een wachtwoord is geconfigureerd, waardoor de doelstelling inlogt als `admin` met `admin123`. | Wijzig het wachtwoord in de Enterprise Manager en configureer de nieuwe referenties, zie [Instellingen](/docs/configuration/bbjship/deploying#settings). |
| `De BBj-beheerder gebruikersnaam ontbreekt`, `Het BBj-beheerder wachtwoord ontbreekt` | Slechts een van de gebruikersnaam of het wachtwoord is geconfigureerd. | Configureer beide, of gebruik geen van beiden om het fabrieksaccount te gebruiken. `bbj-deploy` en `bbj-undeploy` accepteren ook een token. |
| `BBj-implementatie vereist bbj.deployment.url...` | Er is geen implementatie-URL geconfigureerd. | Stel `<bbj><deployment><url>` in of geef `-Dwebforj.bbj.deployment.url=<URL>` door. |
| `BBj-implementatie-URL moet een HTTP- of HTTPS-URL zijn zonder referenties, query of fragment` | De URL heeft een andere indeling, of bevat een gebruikersnaam, een query of een fragment. | Gebruik `http://<host>:<port>/webforj-bbjship` of `https://<host>:<port>/webforj-bbjship`, en zet de referenties in `<admin>`. |
| `Verbinding met ... mislukt: Verbinding geweigerd` | Er reageert niets op de host en poort van de implementatie-URL. | Start BBjServices, en controleer de host en poort van de URL met de webserver van BBjServices. |
| `kon de BBjShip-versie op <URL> niet lezen, HTTP 404...` | De URL leidt niet naar BBjShip, of BBjShip is niet geïnstalleerd of de eindpunt is op die poort niet ingeschakeld. | Controleer het pad `/webforj-bbjship` en de poort. Voer op de BBj-machine `mvn webforj:bbj-setup` uit, of `bbjship remote enable` op een server. |
| `mislukt met HTTP 401. Controleer de BBj-beheerdersreferenties of token` | BBjServices heeft de gebruikersnaam en het wachtwoord of de token geweigerd. | Controleer de referenties. Vernieuw een token dat verlopen is of ingetrokken in de Enterprise Manager. |
| `BBjShip <versie> op <URL> komt niet overeen met webforJ <versie>...` | De geïnstalleerde BBjShip en de `webforj-maven-plugin` hebben verschillende versies. | Voer op je eigen machine `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. Op een server, installeer de overeenkomende BBjShip met de BBj Plugin Manager. |
| `kon geen vertrouwde HTTPS-verbinding tot stand brengen met <URL>...` | Java vertrouwt het certificaat van de webserver van BBjServices niet. | Installeer een certificaat voor de hostnaam van de URL op de BBj-machine, of voeg het certificaat toe aan de Java truststore van de build. |
| `BBj-implementatie-URL <URL> verzendt de beheerdersreferenties leesbaar over het netwerk...` | Een waarschuwing voor een `http://`-URL naar een andere machine. | Gebruik een `https://`-URL met de SSL-poort, zie [Implementeren vanaf een andere machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuratie {#setup}

Deze berichten komen van `mvn webforj:bbj-setup` en van de eerste [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) van een project, dat BBjShip op dezelfde manier instelt.

| Bericht | Oorzaak | Wat te doen |
|---|---|---|
| `BBj-configuratie mislukt. Controleer het BBj-pluginartifact en de configuratie-instellingen` | De doelstelling is mislukt. De reden volgt daarop. Een reden over artifactresolutie betekent dat Maven `webforj-bbjship-plugin` niet kon downloaden in de versie van de `webforj-maven-plugin`. | Lees de reden. Voor artifactresolutie, controleer de repositories en de netwerktoegang van de build. |
| `BBj-configuratie is alleen lokaal. Voer configuratie uit op de BBj-host` | De implementatie-URL wijst naar een andere machine. | Voer de configuratie uit op de BBj-machine, of installeer BBjShip daar met de [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj-configuratie kan de host van de implementatie-URL <host> niet resolven...` | De hostnaam van de implementatie-URL is onbekend. | Gebruik `localhost` of een naam van deze machine. |
| `BBj-configuratie vereist het beheerderswachtwoord. Configureer in plaats daarvan bbj.admin.password` | Een ontwikkelrun heeft een token geconfigureerd. Ontwikkelruns loggen in met alleen een gebruikersnaam en wachtwoord. | Configureer `<admin><username>` en `<admin><password>`. |
| `Kan een BBj-installatie niet detecteren. Configureer bbj.home of BBJ_HOME` | De doelstelling vond geen BBj-installatie op deze machine. | Stel `<bbj><home>`, `-Dwebforj.bbj.home` of de `BBJ_HOME`-variabele in op de map van de BBj-installatie. |
| `Ongeldige BBj-home. Configureer een lokale BBj-installatie`, `BBj-uitvoerbare bestanden ontbreken of zijn niet uitvoerbaar. Controleer bbj.home` | De geconfigureerde map is geen volledige BBj-installatie. | Wijs de instelling naar de map die `bin` en `cfg` van BBj bevat. |
| `Geadviseerde BBjShip-versie <versie> komt niet overeen met <versie>...` | Een andere versie BBjShip is geïnstalleerd, bijvoorbeeld na een upgrade van webforJ. | Voer `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. Geïmplementeerde apps blijven geïmplementeerd. |
| `Geadviseerde BBjShip-versie ontbreekt...` | De geïnstalleerde BBjShip-map is onvolledig. | Voer `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` uit. |
| `De pluginmap is een symbolische link...`, `BBj-pluginbestemming mag geen symbolische links bevatten` | De `plugins`-map van BBj, of de BBjShip-map daarin, is een symbolische link of bevat er één. | Installeer in een reguliere map. |
| `BBj-configuratieopdracht is mislukt. Controleer referenties en lokale BBjServices.` gevolgd door de uitvoer van de opdracht | De opdracht die het implementatie-eindpunt inschakelt is mislukt. De uitvoer noemt de reden, bijvoorbeeld een geweigerde login of een poort waarop de webserver niet luistert. | Controleer de referenties, dat BBjServices draait, en dat de poort van de implementatie-URL een poort van de BBjServices-webserver is. |
| `BBj-configuratieopdracht liep vast. Controleer lokale BBjServices` | De opdracht voltooide niet binnen 120 seconden. | Controleer of BBjServices draait en reageert, voer dan de doelstelling opnieuw uit. |
| `Vorige BBjShip-bestanden zijn bewaard in <map>` | Een vervangende poging is mislukt, en de bestanden van de vorige versie zijn in die map bewaard. | Los de reden van de mislukking op en voer de doelstelling opnieuw uit. |

## Implementeren en verwijderen {#deploy-and-undeploy}

### Voor de upload {#before-the-upload}

Deze berichten komen van `bbj-package`, `bbj-deploy` en `bbj-undeploy` voordat er iets naar BBjShip gaat.

| Bericht | Oorzaak | Wat te doen |
|---|---|---|
| `BBj-implementatie vereist jar-pakket, dit project is verpakt als <packaging>` | Het project is niet verpakt als een jar. | Stel `<packaging>jar</packaging>` in. |
| `BBj-implementatie vereist het verpakte applicatiejar...` | De doelstelling is uitgevoerd zonder de `package`-fase in dezelfde build. | Voer `mvn install`, of `mvn package webforj:bbj-deploy` uit. |
| `BBj-implementatie mag geen webforj-engine dragen, BBj is de engine...` | `webforj-engine` staat op de runtime classpath van de app. | Sluit het uit van de `webforj`-afhankelijkheid, zie [Zonder de install-plugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj-implementatie vereist webforj-foundation op de runtime classpath` | De app heeft geen webforJ-afhankelijkheid tijdens runtime. | Voeg de `webforj`-afhankelijkheid toe in `compile`-scope. |
| `BBj-implementatieafhankelijkheid is onopgelost...` | Een afhankelijkheid van de app heeft geen bestand. | Controleer of Maven elke afhankelijkheid van het project oplost. |
| `Publicatienaam <naam> is ongeldig...` | De publicatienaam breekt de regels in [Publicatienamen](/docs/configuration/bbjship/deploying#publish-names). | Kies een andere `<publishName>`. |
| `De publicatienaam <naam> is gereserveerd door BBjServices voor zijn eigen webpaden...` | BBjServices gebruikt die naam voor zijn eigen adressen. | Kies een andere `<publishName>`. |
| `BBj-implementatie-archief kon niet worden geschreven...` | `bbj-package` kon `target/bbj/<publikatie naam>.zip` niet schrijven. De reden volgt. | Lees de reden. |

### Reacties van BBjShip {#answers-of-bbjship}

Wanneer BBjShip een implementatie of een verwijdering weigert, begint het bericht met `BBj-implementatie mislukt met HTTP <status>` of `BBj-verwijdering mislukt met HTTP <status>`, gevolgd door de reden.

| Status | Reden | Oorzaak | Wat te doen |
|---|---|---|---|
| 400 | `Publicatienaam <naam> is ongeldig...` of `...is gereserveerd door BBjServices...` | De publicatienaam is geweigerd. | Kies een andere publicatienaam. |
| 400 | `BBj-implementatie webforj.conf is ongeldig. <detail>` | De `webforj.conf` van de app kan niet worden gelezen, of een [app-instelling](/docs/configuration/bbjship/deploying#app-settings) heeft het verkeerde type. | Los het bestand op zoals het detail zegt. |
| 400 | `De geüploade body is geen BBj-implementatie-archief...`, `Ongeldig BBj-implementatie-archiefitem: <item>` of een andere reden over het archief | Het archief is niet geschreven door de build-plugin, of is daarna gewijzigd. | Implementeer het archief dat de build schrijft, met dezelfde webforJ-versie als BBjShip. |
| 401 | `Controleer de BBj-beheerderreferenties of token` | De login werd geweigerd. | Controleer de referenties of vernieuw de token. |
| 403 | `Ontwikkelruns zijn niet ingeschakeld op deze BBj-machine. Voer bbjship development enable uit` | Een ontwikkelrun bereikte een machine waarontwikkelruns zijn uitgeschakeld. | Voer `bbjship development enable` uit op de BBj-machine. |
| 404 | `Geen BBj-applicatie genaamd <naam> is geïmplementeerd door BBjShip. Controleer de publicatienaam` | Een verwijdering noemde een app die BBjShip niet heeft geïmplementeerd. | Controleer de publicatienaam met `bbjship apps list`. |
| 409 | `Invoertype <klasse> is al gepubliceerd als <naam>. Verwijder <naam> eerst` | De invoerklasse van de app draait al onder een andere publicatienaam. | Verwijder de andere app, of implementeer onder zijn naam. |
| 409 | `Gepubliceerde BBj-applicatie <naam> wordt niet beheerd door BBjShip...` | Een app die is geregistreerd in de Enterprise Manager gebruikt al die naam. | Kies een andere publicatienaam, of verwijder die app in de Enterprise Manager. |
| 409 | `Gepubliceerde BBj-applicatie <ander> verschilt van <naam> alleen door lettergrootte...` | Een andere app gebruikt dezelfde naam met een andere lettergrootte. | Kies een andere publicatienaam. |
| 409 | `<naam> is een ontwikkelrun. Stop eerst de watch` | Een ontwikkelrun van de app is actief. | Stop de watch met `Ctrl+C`, voer dan de implementatie uit. |
| 409 | `<naam> is geïmplementeerd vanaf een archief. Verwijder het eerst` | Een ontwikkelrun gebruikt de naam van een geïmplementeerde app. | Verwijder de app, of stel een andere `<publishName>` in voor de ontwikkelrun. |
| 413 | `BBj-implementatie-archief overschrijdt de toegestane grootte van 1 GiB` | Het archief is groter dan BBjShip accepteert. | Verwijder afhankelijkheden die de app niet nodig heeft tijdens runtime. |
| 500 | `BBj-implementatie mislukt. Controleer de BBjServices-log` | BBjServices is mislukt bij het installeren of verwijderen van de app. | Lees de BBjServices-logs. |

Een implementatie die eindigt met `heeft de gevraagde applicatie niet bevestigd. Controleer de BBjShip-versie en de BBjServices-log` bereikte BBjShip maar kreeg geen bevestiging voor de app. Controleer of BBjShip en de `webforj-maven-plugin` dezelfde versie hebben, lees dan de BBjServices-log.

### De draaiende app {#the-running-app}

| Bericht | Oorzaak | Wat te doen |
|---|---|---|
| `Het protocol voor pictogrammen vereist webforj.assetsSource = verpakt wanneer het draait met BBj Services` | De app gebruikt `icons://` zonder verpakte middelen. | Voeg `webforj.assetsSource = verpakt` toe aan `webforj.conf` en implementeer opnieuw. |

## Ontwikkelruns {#development-runs}

Deze berichten komen van `mvn` of `mvn compile webforj:watch` in een project dat is geconfigureerd voor BBjShip. Een mislukte ontwikkelrun begint met `BBj ontwikkelrun mislukt. Controleer de implementatie-instellingen en dat ontwikkelruns zijn ingeschakeld op de BBj-machine`, gevolgd door de reden uit [Configuratie](#setup), [Referenties en verbinding](#credentials-and-connection), of [Reacties van BBjShip](#answers-of-bbjship).

| Bericht | Oorzaak | Wat te doen |
|---|---|---|
| `De klassenmap <map> bestaat niet. Voer compile uit voordat webforj:watch` | De watch is gestart voordat het project was gecompileerd. | Voer `mvn compile webforj:watch`, of `mvn` in een archetype-project uit. |
| `BBj ontwikkelklassenmap moet een absolute map zijn op de BBj-machine` | `<development><classFolder>` verwijst naar een map die BBjServices niet kan vinden. | Stel het absolute pad van de klassenmap in op de BBj-machine. |
| `BBjServices-eigenschappenbestand niet gevonden op <bestand>. Configureer bbj.properties` | Het BBjServices-eigenschappenbestand bevindt zich niet waar de run ernaar zoekt. | Stel `<bbj><properties>` of `-Dwebforj.bbj.properties` in op het bestand. |
| `BBjServices stopte niet binnen 180 seconden` | De run vroeg BBjServices om te stoppen om de hotswap-instellingen toe te passen, en het bleef draaien. | Stop BBjServices zelf, start het opnieuw op, en voer de watch opnieuw uit. |
| `BBjServices startprogramma ontbreekt: <programma>` | De BBj-installatie heeft geen programma om BBjServices te starten. | Start BBjServices zelf en voer de watch opnieuw uit. |
| `BBjServices startte niet. Het programma eindigde met exitcode <code>`, `BBjServices startte niet binnen 120 seconden` | BBjServices is niet teruggekomen na de herstart. | Start BBjServices zelf, en voer de watch opnieuw uit. |
| `BBjShip reageerde niet binnen 120 seconden` | BBjServices is gestart, maar het implementatie-eindpunt reageerde niet. | Controleer de BBjServices-log, en voer de watch opnieuw uit. |
| `BBjServices herstartte zonder de hotswap-instellingen. Controleer <bestand>` | BBjServices startte met Java-argumenten anders dan die zijn geschreven in het eigenschappenbestand. | Controleer de Java-instellingen van BBjServices in de Enterprise Manager en in dat bestand. |
| `webforJ watch: ontwikkelrun van <naam> is niet gestopt. <reden>` | De app kon niet worden verwijderd toen de build stopte. | Verwijder het met `bbjship apps undeploy <naam>`, of start de watch opnieuw, wat het vervangt. |

De eerste ontwikkelrun die de hotswap-instellingen toevoegt herstart BBjServices. Het start BBjServices met `bin/bbjservices` op macOS en Linux. Op Windows start het de `BBjServices` Windows-service wanneer deze is geregistreerd, en draait `bin\BBjServices.exe -noservice` anders. Een BBjServices gestart door `bin/bbjservices` of `-noservice` draait buiten de servicebeheerder van het besturingssysteem totdat de machine opnieuw opstart.

## Opdrachtregel {#command-line}

Elke `bbjship` opdracht draait binnen BBjServices, dus BBjServices moet draaien. Zonder dat kan de opdracht niet starten.

Ongeldige argumenten eindigen met exitcode `2` en een bericht dat vermeldt wat je moet specificeren:

| Bericht | Wat te doen |
|---|---|
| `Specificeer een opdracht. Gebruik --help voor beschikbare opdrachten`, `Onbekende opdracht <opdracht>...` | Geef een opdracht op, zie [Opdrachtregel](/docs/configuration/bbjship/command-line). |
| `Onbekende optie <optie>...`, `Optie <optie> is niet beschikbaar voor <opdracht>...` | Gebruik een optie van die opdracht, zoals `--help` ze opsomt. |
| `Specificeer een gebruikersnaam na --username`, `Specificeer een wachtwoord na --password`, `Specificeer een poort na --port` | Geef de optie een waarde. |
| `Specificeer --port van 1 tot 65535`, `Specificeer --ssl-port van 1 tot 65535` | Geef een poortnummer op. |
| `Specificeer <optie> maar één keer` | Verwijder de herhaalde optie. |
| `Specificeer slechts één van --password en --password-stdin` | Houd een van de twee. |
| `Het BBj-beheerderwachtwoord ontbreekt. Specificeer --username en --password <waarde> of --password-stdin, of stel WEBFORJ_BBJ_USERNAME en WEBFORJ_BBJ_PASSWORD in` | Geef de gebruikersnaam en het wachtwoord samen op. Het bericht vermeldt degene die ontbreekt. |
| `Specificeer een applicatienaam of --all`, `Specificeer één applicatienaam` | Geef `apps undeploy` één publicatienaam, of `--all`. |
| `Specificeer het pad van een implementatie-archief dat eindigt op .zip`, `Specificeer één implementatie-archief` | Geef `apps deploy` één `.zip` bestand op. |
| `Implementatie-archief <pad> bestaat niet`, `Implementatie-archief <pad> kan niet worden gelezen` | Controleer het pad. BBjServices leest het bestand, dus de besturingssysteemgebruiker van BBjServices heeft leesrechten ervoor nodig. |
| `Ontwikkelarchieven worden geïmplementeerd door de watch, niet door apps deploy` | Implementeer het archief van `bbj-package`, niet dat van een ontwikkelrun. |
| `Publicatienaam <naam> is ongeldig...`, `De publicatienaam <naam> is gereserveerd...` | Hernoem het archief. De bestandsnaam zonder `.zip` is de publicatienaam. |

Een opdracht die mislukt eindigt met exitcode `1` en `Opdracht mislukt.` gevolgd door de reden:

| Reden | Oorzaak | Wat te doen |
|---|---|---|
| Een inlogfout van BBjServices | De gebruikersnaam of het wachtwoord is onjuist. | Controleer de referenties. |
| `De BBjServices-webserver beantwoordt niet op poort <poort>. Gebruik de HTTP-poort van de BBjServices-webserver` | `--port` of `--ssl-port` is geen poort waarop de BBjServices-webserver luistert. De vorige eindpunt blijft. | Gebruik de poorten die zijn geconfigureerd voor de webserver in de Enterprise Manager. |
| `Applicatie <naam> is niet geïmplementeerd door BBjShip` | `apps undeploy` noemde een app die BBjShip niet heeft geïmplementeerd. | Controleer de naam met `bbjship apps list`. |
| `Een andere implementatie is in uitvoering` | Een andere implementatie of verwijdering heeft de machine 30 seconden in beslag genomen. | Wacht tot deze is voltooid, voer vervolgens de opdracht opnieuw uit. |
| `De BBjShip-context behoort tot een andere applicatie`, `De middelencontext behoort tot een andere applicatie` | Een andere app in de Enterprise Manager gebruikt de webcontext `webforj-bbjship` of `webforj-bbjship-assets`. | Hernoem of verwijder die context in de Enterprise Manager. |
| `Het BBjShip-opdrachtclasspad behoort tot een andere applicatie`, `Het basisclasspad van BBjShip bevat een niet-beheerde bibliotheek` | De classpaths `_webforj_bbjship_cli` of `_webforj_bbjship` in de Enterprise Manager zijn gewijzigd. | Verwijder beide classpaths in de Enterprise Manager en voer de opdracht opnieuw uit, wat ze opnieuw aanmaakt. |
| `De BBjShip-servlet ontbreekt. Controleer de plugininstallatie`, `De BBjShip-bibliotheek ontbreekt. Controleer de plugininstallatie` | De BBjShip-map is onvolledig. | Installeer BBjShip opnieuw. |
| `BBjServices beheert geen webcontexts. Update BBj` | De BBj-versie is te oud voor BBjShip. | Installeer de BBj-versie die overeenkomt met webforJ. |

`reset` eindigt met exitcode `1` en `Reset geannuleerd` wanneer het antwoord op `Doorgaan? [y/N]` niet `y` of `Y` is. Wanneer het de Java-instellingen van BBjServices heeft hersteld, eindigt het met `Herstart BBjServices nu om de herstelde Java-instellingen toe te passen`. Herstart BBjServices om het zonder de hotswap-instellingen uit te voeren.

## Windows {#windows}

| Situatie | Wat te doen |
|---|---|
| Het uitvoeren van `bbj.exe` met het BBjShip-programma geeft niets weer. | Voer `bbjship.cmd` uit in `<BBj home>\plugins\webforj-bbjship`, dat toont de uitvoer van de opdracht. |
| `--password-stdin` eindigt met exitcode `2` en `Standaardinvoer is niet beschikbaar op Windows. Gebruik --password <waarde> of WEBFORJ_BBJ_PASSWORD`. | Geef het wachtwoord door met `--password`, of stel `WEBFORJ_BBJ_USERNAME` en `WEBFORJ_BBJ_PASSWORD` in. |
| `reset` eindigt met exitcode `2` en `Standaardinvoer is niet beschikbaar op Windows. Voer reset --yes uit om te bevestigen`. | Voer `bbjship.cmd reset --yes` uit. |
| `apps undeploy` of `reset` geeft `Niet verwijderd: <map>. <reden>` weer en eindigt met exitcode `0`. | BBjServices houdt nog steeds bestanden van de app open. De app is verwijderd uit Enterprise Manager. Herstart BBjServices. De map verdwijnt met de volgende implementatie van die naam, of verwijder deze na de herstart. |
| Het installeren van BBjShip bovenop een geïnstalleerde versie mislukt omdat de bestanden in gebruik zijn. | BBjServices houdt nog steeds de bestanden van de geïnstalleerde BBjShip open. Herstart BBjServices, installeer dan opnieuw. |
| BBjServices draait niet als een Windows-service nadat een ontwikkelrun het opnieuw heeft gestart. | De ontwikkelrun startte het met `BBjServices.exe -noservice` omdat er geen `BBjServices`-service is geregistreerd. Het draait buiten de Windows-servicebeheerder totdat de machine opnieuw opstart. |
| Een `bbjship.cmd`-opdracht kan niet starten. | Start BBjServices. Elke `bbjship`-opdracht heeft deze nodig om te draaien. |
