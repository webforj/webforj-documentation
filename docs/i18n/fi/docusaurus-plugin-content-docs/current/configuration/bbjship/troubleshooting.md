---
title: Vianmääritys
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: c517c84b00b3b7eb6bb9d485c528c4f7
---
# Vianmääritys <DocChip chip='since' label='26.03' /> {#troubleshooting}

Tällä sivulla luetellaan viestit, joita voi tulla vastaan, kun määrität BBjShipin, otat sovelluksen käyttöön, käynnistät kehitysajon tai käytät `bbjship`-komentorivityökalua. Kunkin viestin yhteydessä kerrotaan sen syy ja toimintaohje.

Epäonnistunut Maven-tavoite keskeyttää koonnin lyhyellä viestillä, jossa mainitaan ensin tavoite ja sitten syy. Tällä sivulla luetellaan syyt:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Tunnistetiedot ja yhteys {#credentials-and-connection}

Nämä viestit tulevat kaikista tavoitteista, jotka viestivät BBjShipin kanssa: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` ja `watch`.

| Viesti | Syy | Toimintaohje |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Käyttäjänimeä tai salasanaa ei ole määritetty, joten tavoite kirjautuu sisään tunnuksilla `admin` ja `admin123`. | Vaihda salasana Enterprise Managerissa ja määritä uudet tunnistetiedot. Katso [Asetukset](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Vain käyttäjänimi tai salasana on määritetty. | Määritä molemmat tai älä kumpaakaan, jos haluat käyttää oletustunnuksia. `bbj-deploy` ja `bbj-undeploy` hyväksyvät myös tunnisteen. |
| `BBj deployment requires bbj.deployment.url...` | Käyttöönoton URL-osoitetta ei ole määritetty. | Määritä `<bbj><deployment><url>` tai välitä `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | URL-osoitteessa on jokin muu skeema tai se sisältää käyttäjänimen, kyselyosan tai fragmentin. | Käytä osoitetta `http://<host>:<port>/webforj-bbjship` tai `https://<host>:<port>/webforj-bbjship` ja sijoita tunnistetiedot kohtaan `<admin>`. |
| `Connect to ... failed: Connection refused` | Käyttöönoton URL-osoitteen isäntä ja portti eivät vastaa. | Käynnistä BBjServices ja tarkista, että URL-osoitteen isäntä ja portti vastaavat BBjServicesin verkkopalvelimen asetuksia. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | URL-osoite ei johda BBjShipiin, BBjShipiä ei ole asennettu tai sen päätepiste ei ole käytössä kyseisessä portissa. | Tarkista polku `/webforj-bbjship` ja portti. Suorita BBj-koneessa `mvn webforj:bbj-setup` tai palvelimessa `bbjship remote enable`. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices hylkäsi käyttäjänimen ja salasanan tai tunnisteen. | Tarkista tunnistetiedot. Uusi Enterprise Managerissa tunniste, joka on vanhentunut tai peruutettu. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Asennetun BBjShipin ja `webforj-maven-plugin`-lisäosan versiot eroavat toisistaan. | Suorita omalla koneellasi `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Asenna palvelimessa vastaava BBjShip-versio BBj Plugin Managerilla. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java ei luota BBjServicesin verkkopalvelimen varmenteeseen. | Asenna URL-osoitteen isäntänimelle varmenne BBj-koneessa tai lisää varmenne koontiympäristön Java-luottamusvarastoon. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | Käyttöönoton URL-osoite on toiseen koneeseen osoittava `http://`-osoite. Tavoite keskeytyy ennen tietojen lähettämistä. Asetuksen `allowUnencrypted` ollessa käytössä sama teksti on varoitus ja tavoite jatkaa. | Käytä `https://`-osoitetta SSL-portin kanssa tai salli HTTP molemmissa päissä. Katso [Käyttöönotto toisesta koneesta](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Määritys {#setup}

Nämä viestit tulevat komennosta `mvn webforj:bbj-setup` ja projektin ensimmäisestä [kehitysajosta](/docs/configuration/deploy-reload/bbjservices), joka määrittää BBjShipin samalla tavalla.

| Viesti | Syy | Toimintaohje |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | Tavoite epäonnistui. Syy ilmoitetaan viestin jälkeen. Artefaktin selvitykseen liittyvä syy tarkoittaa, ettei Maven pystynyt lataamaan `webforj-bbjship-plugin`-lisäosaa `webforj-maven-plugin`-lisäosan käyttämällä versiolla. | Lue syy. Jos kyse on artefaktin selvityksestä, tarkista tietovarastot ja koontiympäristön verkkoyhteys. |
| `BBj setup is local only. Run setup on the BBj host` | Käyttöönoton URL-osoite viittaa toiseen koneeseen. | Suorita määritys BBj-koneessa tai asenna BBjShip sinne [BBj Plugin Managerilla](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Käyttöönoton URL-osoitteen isäntänimeä ei tunnisteta. | Käytä nimeä `localhost` tai tämän koneen nimeä. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Kehitysajolle on määritetty tunniste. Kehitysajot kirjautuvat sisään vain käyttäjänimellä ja salasanalla. | Määritä `<admin><username>` ja `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | Tavoite ei löytänyt BBj-asennusta tältä koneelta. | Aseta `<bbj><home>`, `-Dwebforj.bbj.home` tai `BBJ_HOME`-ympäristömuuttuja BBj-asennuskansioon. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Määritetty kansio ei ole täydellinen BBj-asennus. | Määritä asetukseksi kansio, jossa BBj:n `bin`- ja `cfg`-kansiot sijaitsevat. |
| `Installed BBjShip version <version> does not match <version>...` | Asennettuna on toinen BBjShip-versio, esimerkiksi webforJ-päivityksen jälkeen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Käyttöönotetut sovellukset säilyvät. |
| `Installed BBjShip version is missing...` | Asennettu BBjShip-kansio on puutteellinen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | BBj:n `plugins`-kansio tai sen sisällä oleva BBjShip-kansio on symbolinen linkki tai sisältää sellaisen. | Asenna tavalliseen kansioon. |
| `BBj setup command failed. Check credentials and local BBjServices.` ja sen jälkeen komennon tuloste | Käyttöönoton päätepisteen käyttöön ottava komento epäonnistui. Tuloste ilmoittaa syyn, esimerkiksi kirjautumisen hylkäämisen tai portin, jota verkkopalvelin ei kuuntele. | Tarkista tunnistetiedot, että BBjServices on käynnissä ja että käyttöönoton URL-osoitteen portti kuuluu BBjServicesin verkkopalvelimelle. |
| `BBj setup command timed out. Check local BBjServices` | Komento ei valmistunut 120 sekunnissa. | Tarkista, että BBjServices on käynnissä ja vastaa, ja suorita sitten tavoite uudelleen. |
| `Previous BBjShip files are preserved at <folder>` | Korvaaminen epäonnistui, ja edellisen version tiedostot säilytettiin kyseisessä kansiossa. | Korjaa virheen syy ja suorita tavoite uudelleen. |

## Käyttöönotto ja käyttöönoton poistaminen {#deploy-and-undeploy}

### Ennen latausta {#before-the-upload}

Nämä viestit tulevat tavoitteista `bbj-package`, `bbj-deploy` ja `bbj-undeploy` ennen kuin mitään lähetetään BBjShipiin.

| Viesti | Syy | Toimintaohje |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Projektin pakkaustyyppi ei ole jar. | Aseta arvoksi `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | Tavoite suoritettiin ilman `package`-vaihetta samassa koonnissa. | Suorita `mvn install` tai `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` on sovelluksen ajonaikaisella luokkapolulla. | Jätä se pois `webforj`-riippuvuudesta. Katso [Ilman asennuslisäosaa](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | Sovelluksella ei ole ajonaikaista webforJ-riippuvuutta. | Lisää `webforj`-riippuvuus `compile`-laajuudella. |
| `BBj deployment dependency is unresolved...` | Sovelluksen riippuvuudelta puuttuu tiedosto. | Tarkista, että Maven selvittää kaikki projektin riippuvuudet. |
| `Publish name <name> is invalid...` | Julkaisunimi ei noudata kohdassa [Julkaisunimet](/docs/configuration/bbjship/deploying#publish-names) määritettyjä sääntöjä. | Valitse toinen `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices käyttää kyseistä nimeä omissa osoitteissaan. | Valitse toinen `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` ei pystynyt kirjoittamaan tiedostoa `target/bbj/<publish name>.zip`. Syy ilmoitetaan viestin jälkeen. | Lue syy. |

### BBjShipin vastaukset {#answers-of-bbjship}

Kun BBjShip hylkää käyttöönoton tai käyttöönoton poistamisen, viesti alkaa tekstillä `BBj deployment failed with HTTP <status>` tai `BBj undeployment failed with HTTP <status>`, jota seuraa syy.

| Tila | Syy | Syy | Toimintaohje |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` tai `...is reserved by BBjServices...` | Julkaisunimi hylätään. | Valitse toinen julkaisunimi. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Sovelluksen `webforj.conf`-tiedostoa ei voi lukea tai [sovellusasetuksen](/docs/configuration/bbjship/deploying#app-settings) tyyppi on väärä. | Korjaa tiedosto lisätiedoissa kuvatulla tavalla. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` tai muu arkistoon liittyvä syy | Koontilisäosa ei kirjoittanut arkistoa tai sitä on muutettu jälkikäteen. | Ota käyttöön koonnin kirjoittama arkisto ja käytä samaa webforJ-versiota kuin BBjShipissä. |
| 401 | `Check the BBj administration credentials or token` | Kirjautuminen hylättiin. | Tarkista tunnistetiedot tai uusi tunniste. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | Pyyntö saapui HTTP-yhteydellä toisesta koneesta. | Käytä päätepisteen HTTPS-osoitetta tai suorita BBj-koneessa `bbjship remote enable --allow-unencrypted`. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Kehitysajo yritettiin suorittaa koneessa, jossa kehitysajot eivät ole käytössä. | Suorita BBj-koneessa `bbjship development enable`. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Käyttöönoton poistamista yritettiin sovellukselta, jota BBjShip ei ollut ottanut käyttöön. | Tarkista julkaisunimi komennolla `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | Sovelluksen aloitusluokka on jo käynnissä toisella julkaisunimellä. | Poista toisen sovelluksen käyttöönotto tai ota sovellus käyttöön sen nimellä. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Enterprise Manageriin rekisteröity sovellus käyttää jo kyseistä nimeä. | Valitse toinen julkaisunimi tai poista sovellus Enterprise Managerista. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Toinen sovellus käyttää samaa nimeä eri kirjainkoolla. | Valitse toinen julkaisunimi. |
| 409 | `<name> is a development run. Stop the watch first` | Sovelluksen kehitysajo on aktiivinen. | Pysäytä watch painamalla `Ctrl+C` ja ota sovellus sitten käyttöön. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Kehitysajo käyttää käyttöön otetun sovelluksen nimeä. | Poista sovelluksen käyttöönotto tai aseta kehitysajolle toinen `<publishName>`. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | Arkisto on suurempi kuin BBjShipin sallima koko. | Poista riippuvuudet, joita sovellus ei tarvitse ajon aikana. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServicesin sovelluksen asennus tai poisto epäonnistui. | Tarkista BBjServicesin lokit. |

Jos käyttöönotto päättyy viestiin `did not confirm the requested application. Check the BBjShip version and BBjServices log`, pyyntö saavutti BBjShipin, mutta sovelluksen käyttöönottoa ei vahvistettu. Tarkista, että BBjShipin ja `webforj-maven-plugin`-lisäosan versiot ovat samat, ja tarkista sitten BBjServicesin loki.

### Käynnissä oleva sovellus {#the-running-app}

| Viesti | Syy | Toimintaohje |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | Sovellus käyttää `icons://`-osoitteita ilman paketoituja resursseja. | Lisää `webforj.assetsSource = packaged` tiedostoon `webforj.conf` ja ota sovellus uudelleen käyttöön. |

## Kehitysajot {#development-runs}

Nämä viestit tulevat komennoista `mvn` tai `mvn compile webforj:watch` projektissa, joka on määritetty käyttämään BBjShipiä. Epäonnistuneen kehitysajon alussa on viesti `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, jota seuraa syy kohdasta [Määritys](#setup), [Tunnistetiedot ja yhteys](#credentials-and-connection) tai [BBjShipin vastaukset](#answers-of-bbjship).

| Viesti | Syy | Toimintaohje |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Watch käynnistettiin ennen projektin kääntämistä. | Suorita `mvn compile webforj:watch` tai arkkityyppiprojektissa `mvn`. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` viittaa kansioon, jota BBjServices ei löydä. | Määritä luokkakansion absoluuttinen polku BBj-koneessa. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | BBjServicesin ominaisuustiedosto ei ole ajon etsimässä paikassa. | Määritä tiedoston polku asetuksella `<bbj><properties>` tai `-Dwebforj.bbj.properties`. |
| `BBjServices did not stop within 180 seconds` | Ajo pyysi BBjServicesiä pysähtymään hot swap -asetusten käyttöönottoa varten, mutta se jäi käyntiin. | Pysäytä BBjServices itse, käynnistä se uudelleen ja suorita watch uudelleen. |
| `BBjServices start program is missing: <program>` | BBj-asennuksessa ei ole ohjelmaa BBjServicesin käynnistämiseen. | Käynnistä BBjServices itse ja suorita watch uudelleen. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices ei käynnistynyt uudelleen. | Käynnistä BBjServices itse ja suorita watch uudelleen. |
| `BBjShip did not answer within 120 seconds` | BBjServices käynnistyi, mutta käyttöönoton päätepiste ei vastannut. | Tarkista BBjServicesin loki ja suorita sitten watch uudelleen. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices käynnistyi Java-argumenteilla, jotka poikkeavat ominaisuustiedostoon kirjoitetuista. | Tarkista BBjServicesin Java-asetukset Enterprise Managerissa ja kyseisessä tiedostossa. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | Sovellusta ei voitu poistaa koonnin päättyessä. | Poista se komennolla `bbjship apps undeploy <name>` tai käynnistä watch uudelleen, jolloin se korvataan. |

Ensimmäinen kehitysajo, joka lisää hot swap -asetukset, käynnistää BBjServicesin uudelleen. macOS- ja Linux-käyttöjärjestelmissä BBjServices käynnistetään komennolla `bin/bbjservices`. Windowsissa käynnistetään `BBjServices`-Windows-palvelu, jos sellainen on rekisteröity, ja muussa tapauksessa suoritetaan `bin\BBjServices.exe -noservice`. Komennolla `bin/bbjservices` tai valitsimella `-noservice` käynnistetty BBjServices toimii käyttöjärjestelmän palvelunhallinnan ulkopuolella, kunnes kone käynnistetään uudelleen.

## Komentorivi {#command-line}

Jokainen `bbjship`-komento suoritetaan BBjServicesin sisällä, joten BBjServicesin on oltava käynnissä. Ilman sitä komentoa ei voi käynnistää.

Virheelliset argumentit päättyvät paluukoodiin `2` ja viestiin, joka kertoo, mitä on määritettävä:

| Viesti | Toimintaohje |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Anna komento. Katso [Komentorivi](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Käytä kyseiselle komennolle sopivaa valitsinta. Saatavilla olevat valitsimet näkee komennolla `--help`. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Anna valitsimelle arvo. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Anna portin numero. |
| `Specify <option> only once` | Poista toistettu valitsin. |
| `Specify only one of --password and --password-stdin` | Jätä käyttöön vain toinen näistä. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Anna käyttäjänimi ja salasana yhdessä. Viesti kertoo, kumpi puuttuu. |
| `Specify an application name or --all`, `Specify one application name` | Anna komennolle `apps undeploy` yksi julkaisunimi tai valitsin `--all`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Anna komennolle `apps deploy` yksi `.zip`-tiedosto. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Tarkista polku. BBjServices lukee tiedoston, joten BBjServicesin käyttöjärjestelmäkäyttäjällä on oltava siihen lukuoikeus. |
| `Development archives are deployed by the watch, not by apps deploy` | Ota käyttöön `bbj-package`-komennon arkisto, älä kehitysajon arkistoa. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Nimeä arkisto uudelleen. Sen tiedostonimi ilman `.zip`-päätettä on julkaisunimi. |

Epäonnistunut komento päättyy paluukoodiin `1` ja viestiin `Command failed.`, jota seuraa syy:

| Syy | Syy | Toimintaohje |
|---|---|---|
| BBjServicesin kirjautumisvirhe | Käyttäjänimi tai salasana on väärä. | Tarkista tunnistetiedot. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` tai `--ssl-port` ei ole portti, jota BBjServicesin verkkopalvelin kuuntelee. Aiempi päätepiste säilyy. | Käytä Enterprise Managerissa verkkopalvelimelle määritettyjä portteja. |
| `Application <name> is not deployed by BBjShip` | Komento `apps undeploy` kohdistui sovellukseen, jota BBjShip ei ollut ottanut käyttöön. | Tarkista nimi komennolla `bbjship apps list`. |
| `Another deployment is in progress` | Toinen käyttöönotto tai sen poistaminen varasi koneen 30 sekunniksi. | Odota, että se valmistuu, ja suorita komento sitten uudelleen. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Toinen Enterprise Managerin sovellus käyttää verkkokontekstia `webforj-bbjship` tai `webforj-bbjship-assets`. | Nimeä kyseinen konteksti uudelleen tai poista se Enterprise Managerissa. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Enterprise Managerin luokkapolkuja `_webforj_bbjship_cli` tai `_webforj_bbjship` on muutettu. | Poista molemmat luokkapolut Enterprise Managerissa ja suorita komento uudelleen. Komento luo ne uudelleen. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | BBjShip-kansio on puutteellinen. | Asenna BBjShip uudelleen. |
| `BBjServices does not manage web contexts. Update BBj` | BBj-versio on liian vanha BBjShipille. | Asenna webforJ-versiota vastaava BBj-versio. |

`reset` päättyy paluukoodiin `1` ja viestiin `Reset cancelled`, jos kysymykseen `Continue? [y/N]` annettu vastaus ei ole `y` tai `Y`. Jos se palautti BBjServicesin Java-asetukset, komento päättyy viestiin `Restart BBjServices now to apply the restored Java settings`. Käynnistä BBjServices uudelleen, jotta se toimii ilman hot swap -asetuksia.

## Windows {#windows}

| Tilanne | Toimintaohje |
|---|---|
| `bbj.exe`-ohjelman suorittaminen BBjShip-ohjelmalla ei tulosta mitään. | Suorita tiedosto `<BBj home>\plugins\webforj-bbjship\bbjship.cmd`, joka näyttää komennon tulosteen. |
| `--password-stdin` päättyy paluukoodiin `2` ja viestiin `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Välitä salasana valitsimella `--password` tai määritä `WEBFORJ_BBJ_USERNAME` ja `WEBFORJ_BBJ_PASSWORD`. |
| `reset` päättyy paluukoodiin `2` ja viestiin `Standard input is not available on Windows. Run reset --yes to confirm`. | Suorita `bbjship.cmd reset --yes`. |
| `apps undeploy` tai `reset` tulostaa `Not removed: <folder>. <reason>` ja päättyy paluukoodiin `0`. | BBjServices pitää sovelluksen tiedostoja yhä avoinna. Sovellus poistetaan Enterprise Managerista. Käynnistä BBjServices uudelleen. Kansio poistuu, kun kyseinen nimi otetaan seuraavan kerran käyttöön. Voit myös poistaa kansion uudelleenkäynnistyksen jälkeen. |
| BBjShipin asennus olemassa olevan version päälle epäonnistuu, koska sen tiedostoja käytetään. | BBjServices pitää asennetun BBjShipin tiedostoja yhä avoinna. Käynnistä BBjServices uudelleen ja yritä sitten asentaa uudelleen. |
| BBjServices ei toimi Windows-palveluna sen jälkeen, kun kehitysajo käynnisti sen uudelleen. | Kehitysajo käynnisti sen komennolla `BBjServices.exe -noservice`, koska `BBjServices`-palvelua ei ole rekisteröity. Se toimii Windowsin palvelunhallinnan ulkopuolella, kunnes kone käynnistetään uudelleen. |
| `bbjship.cmd`-komento ei käynnisty. | Käynnistä BBjServices. Kaikki `bbjship`-komennot edellyttävät, että se on käynnissä. |
