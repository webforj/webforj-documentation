---
title: Vianmääritys
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: 2db5dad70f56c06a6aab75752a9c260f
---
# Vianmääritys <DocChip chip='since' label='26.03' /> {#troubleshooting}

Tällä sivulla luetellaan viestit, joita voi tulla vastaan, kun määrität BBjShipin, otat sovelluksen käyttöön, käynnistät kehitysajon tai käytät `bbjship`-komentorivityökalua. Kunkin viestin kohdalla kerrotaan sen syy ja tarvittavat toimet.

Epäonnistunut Maven-tavoite päättää koonnin lyhyeen viestiin, jossa mainitaan ensin tavoite ja sitten syy. Tällä sivulla luetellaan syyt:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Tunnistetiedot ja yhteys {#credentials-and-connection}

Nämä viestit voivat tulla kaikista BBjShipin kanssa yhteyttä ottavista tavoitteista: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` ja `watch`.

| Viesti | Syy | Toimi näin |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Käyttäjänimeä tai salasanaa ei ole määritetty, joten tavoite kirjautuu käyttäjänä `admin` salasanalla `admin123`. | Vaihda salasana Enterprise Managerissa ja määritä uudet tunnistetiedot. Katso [Asetukset](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Vain käyttäjänimi tai salasana on määritetty. | Määritä molemmat tai älä kumpaakaan, jos haluat käyttää oletustunnusta. `bbj-deploy` ja `bbj-undeploy` hyväksyvät myös tunnisteen. |
| `BBj deployment requires bbj.deployment.url...` | Käyttöönoton URL-osoitetta ei ole määritetty. | Määritä `<bbj><deployment><url>` tai välitä `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | URL-osoitteessa on jokin muu skeema, tai se sisältää käyttäjänimen, kyselymerkkijonon tai fragmentin. | Käytä osoitetta `http://<host>:<port>/webforj-bbjship` tai `https://<host>:<port>/webforj-bbjship` ja lisää tunnistetiedot kohtaan `<admin>`. |
| `Connect to ... failed: Connection refused` | Käyttöönoton URL-osoitteen isäntänimessä ja portissa ei vastata. | Käynnistä BBjServices ja tarkista, että URL-osoitteen isäntänimi ja portti vastaavat BBjServices-verkkopalvelimen asetuksia. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | URL-osoite ei johda BBjShipiin, BBjShipiä ei ole asennettu tai sen päätepiste ei ole käytössä kyseisessä portissa. | Tarkista polku `/webforj-bbjship` ja portti. Suorita BBj-koneessa `mvn webforj:bbj-setup` tai ota palvelimessa käyttöön komennolla `bbjship remote enable`. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices hylkäsi käyttäjänimen ja salasanan tai tunnisteen. | Tarkista tunnistetiedot. Uusi Enterprise Managerissa vanhentunut tai peruutettu tunniste. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Asennettu BBjShip ja `webforj-maven-plugin` ovat eri versioita. | Suorita omassa koneessasi `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Asenna palvelimeen yhteensopiva BBjShip BBj Plugin Managerilla. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java ei luota BBjServices-verkkopalvelimen varmenteeseen. | Asenna URL-osoitteen isäntänimelle varmenne BBj-koneeseen tai lisää sen varmenne koonnin Java-luottamusvarastoon. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | Käyttöönoton URL-osoite on toiseen koneeseen osoittava `http://`-osoite. Tavoite keskeytyy ennen tietojen lähettämistä. Kun `allowUnencrypted` on käytössä, sama teksti on varoitus ja tavoite jatkaa. | Käytä `https://`-osoitetta ja SSL-porttia tai salli HTTP-yhteys molemmissa päissä. Katso [Käyttöönotto toisesta koneesta](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Asennus {#setup}

Nämä viestit tulevat komennosta `mvn webforj:bbj-setup` ja projektin ensimmäisestä [kehitysajosta](/docs/configuration/deploy-reload/bbjservices), joka määrittää BBjShipin samalla tavalla.

| Viesti | Syy | Toimi näin |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | Tavoite epäonnistui. Syy ilmoitetaan viestin jälkeen. Jos syy liittyy artefaktin selvittämiseen, Maven ei pystynyt lataamaan `webforj-bbjship-plugin`-liitännäistä `webforj-maven-plugin`-liitännäisen versiolla. | Lue syy. Jos kyse on artefaktin selvittämisestä, tarkista koonnin käyttämät tietovarastot ja verkkoyhteys. |
| `BBj setup is local only. Run setup on the BBj host` | Käyttöönoton URL-osoite viittaa toiseen koneeseen. | Suorita asennus BBj-koneessa tai asenna BBjShip sinne [BBj Plugin Managerilla](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Käyttöönoton URL-osoitteen isäntänimeä ei tunnisteta. | Käytä nimeä `localhost` tai tämän koneen nimeä. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Kehitysajolle on määritetty tunniste. Kehitysajot kirjautuvat vain käyttäjänimellä ja salasanalla. | Määritä `<admin><username>` ja `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | Tavoite ei löytänyt BBj-asennusta tästä koneesta. | Aseta `<bbj><home>`, `-Dwebforj.bbj.home` tai `BBJ_HOME`-ympäristömuuttuja osoittamaan BBj-asennuskansioon. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Määritetty kansio ei ole täydellinen BBj-asennus. | Osoita asetuksella kansioon, jossa ovat BBj:n `bin`- ja `cfg`-kansiot. |
| `Installed BBjShip version <version> does not match <version>...` | Asennettuna on jokin muu BBjShip-versio, esimerkiksi webforJ-päivityksen jälkeen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Käyttöönotetut sovellukset säilyvät käytössä. |
| `Installed BBjShip version is missing...` | Asennettu BBjShip-kansio on puutteellinen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | BBj:n `plugins`-kansio tai sen sisällä oleva BBjShip-kansio on symbolinen linkki tai sisältää sellaisen. | Asenna tavalliseen kansioon. |
| `BBj setup command failed. Check credentials and local BBjServices.` ja sen jälkeen komennon tuloste | Käyttöönoton päätepisteen käyttöönottava komento epäonnistui. Tuloste kertoo syyn, esimerkiksi kirjautumisen hylkäämisen tai portin, jota verkkopalvelin ei kuuntele. | Tarkista tunnistetiedot, että BBjServices on käynnissä ja että käyttöönoton URL-osoitteen portti kuuluu BBjServices-verkkopalvelimelle. |
| `BBj setup command timed out. Check local BBjServices` | Komento ei valmistunut 120 sekunnin kuluessa. | Tarkista, että BBjServices on käynnissä ja vastaa, ja suorita tavoite sitten uudelleen. |
| `Previous BBjShip files are preserved at <folder>` | Korvaaminen epäonnistui, ja edellisen version tiedostot säilytettiin kyseisessä kansiossa. | Korjaa virheen syy ja suorita tavoite uudelleen. |

## Käyttöönotto ja käytöstä poistaminen {#deploy-and-undeploy}

### Ennen latausta {#before-the-upload}

Nämä viestit tulevat tavoitteista `bbj-package`, `bbj-deploy` ja `bbj-undeploy` ennen kuin mitään lähetetään BBjShipiin.

| Viesti | Syy | Toimi näin |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Projektin pakkausmuoto ei ole jar. | Aseta `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | Tavoite suoritettiin ilman `package`-vaihetta samassa koonnissa. | Suorita `mvn install` tai `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` on sovelluksen ajonaikaisessa luokkapolussa. | Sulje se pois `webforj`-riippuvuudesta. Katso [Ilman install-liitännäistä](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | Sovelluksella ei ole webforJ-riippuvuutta ajon aikana. | Lisää `webforj`-riippuvuus `compile`-laajuudessa. |
| `BBj deployment dependency is unresolved...` | Joltakin sovelluksen riippuvuudelta puuttuu tiedosto. | Tarkista, että Maven ratkaisee kaikki projektin riippuvuudet. |
| `Publish name <name> is invalid...` | Julkaisunimi ei noudata kohdassa [Julkaisunimet](/docs/configuration/bbjship/deploying#publish-names) annettuja sääntöjä. | Valitse toinen `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices käyttää nimeä omissa osoitteissaan. | Valitse toinen `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` ei pystynyt kirjoittamaan tiedostoa `target/bbj/<publish name>.zip`. Syy ilmoitetaan viestin jälkeen. | Lue syy. |

### BBjShipin vastaukset {#answers-of-bbjship}

Kun BBjShip hylkää käyttöönoton tai käytöstä poistamisen, viesti alkaa tekstillä `BBj deployment failed with HTTP <status>` tai `BBj undeployment failed with HTTP <status>`, jota seuraa syy.

| Tila | Syy | Syy | Toimi näin |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` tai `...is reserved by BBjServices...` | Julkaisunimi hylätään. | Valitse toinen julkaisunimi. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Sovelluksen `webforj.conf`-tiedostoa ei voi lukea tai [sovellusasetuksen](/docs/configuration/bbjship/deploying#app-settings) tyyppi on väärä. | Korjaa tiedosto viestin yksityiskohtien mukaisesti. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` tai jokin muu arkistoon liittyvä syy | Koonnin liitännäinen ei kirjoittanut arkistoa tai sitä on muutettu jälkikäteen. | Ota käyttöön koonnin kirjoittama arkisto ja käytä samaa webforJ-versiota kuin BBjShipissä. |
| 401 | `Check the BBj administration credentials or token` | Kirjautuminen hylättiin. | Tarkista tunnistetiedot tai uusi tunniste. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | Pyyntö saapui HTTP-yhteyden kautta toisesta koneesta. | Käytä päätepisteen HTTPS-osoitetta tai suorita BBj-koneessa `bbjship remote enable --allow-unencrypted`. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Kehitysajo yritettiin käynnistää koneessa, jossa kehitysajot eivät ole käytössä. | Suorita BBj-koneessa `bbjship development enable`. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Käytöstä poistettava sovellus ei ole BBjShipin käyttöönottama. | Tarkista julkaisunimi komennolla `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | Sovelluksen aloitusluokka on jo käynnissä toisella julkaisunimellä. | Poista toinen sovellus käytöstä tai ota sovellus käyttöön sen nimellä. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Enterprise Manageriin rekisteröity sovellus käyttää jo kyseistä nimeä. | Valitse toinen julkaisunimi tai poista kyseinen sovellus Enterprise Managerissa. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Toinen sovellus käyttää samaa nimeä eri kirjainkoolla. | Valitse toinen julkaisunimi. |
| 409 | `<name> is a development run. Stop the watch first` | Sovelluksen kehitysajo on käynnissä. | Pysäytä watch komennolla <kbd>Ctrl</kbd>+<kbd>C</kbd> ja ota sovellus sitten käyttöön. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Kehitysajo käyttää käyttöönotetun sovelluksen nimeä. | Poista sovellus käytöstä tai määritä kehitysajolle toinen `<publishName>`. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | Arkisto on suurempi kuin BBjShip hyväksyy. | Poista riippuvuudet, joita sovellus ei tarvitse ajonaikana. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices epäonnistui sovelluksen asennuksen tai poiston aikana. | Lue BBjServices-lokit. |

Käyttöönotto, joka päättyy viestiin `did not confirm the requested application. Check the BBjShip version and BBjServices log`, saavutti BBjShipin, mutta ei saanut sovelluksen käyttöönotosta vahvistusta. Tarkista, että BBjShip ja `webforj-maven-plugin` ovat samaa versiota, ja lue sitten BBjServices-loki.

### Käynnissä oleva sovellus {#the-running-app}

| Viesti | Syy | Toimi näin |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | Sovellus käyttää `icons://`-osoitteita ilman paketoituja resursseja. | Lisää `webforj.assetsSource = packaged` tiedostoon `webforj.conf` ja ota sovellus uudelleen käyttöön. |

## Kehitysajot {#development-runs}

Nämä viestit tulevat komennosta `mvn` tai `mvn compile webforj:watch` projektissa, joka on määritetty käyttämään BBjShipiä. Epäonnistuneen kehitysajon alussa on teksti `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, jota seuraa syy kohdasta [Asennus](#setup), [Tunnistetiedot ja yhteys](#credentials-and-connection) tai [BBjShipin vastaukset](#answers-of-bbjship).

| Viesti | Syy | Toimi näin |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Watch käynnistettiin ennen projektin kääntämistä. | Suorita `mvn compile webforj:watch` tai arkkityyppiprojektissa `mvn`. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` osoittaa kansioon, jota BBjServices ei löydä. | Aseta luokkakansion absoluuttinen polku BBj-koneessa. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | BBjServicesin ominaisuustiedostoa ei löydy ajon etsimästä paikasta. | Aseta `<bbj><properties>` tai `-Dwebforj.bbj.properties` osoittamaan tiedostoon. |
| `BBjServices did not stop within 180 seconds` | Ajo pyysi BBjServicesiä pysähtymään hot swap -asetusten käyttöön ottamista varten, mutta se jäi käyntiin. | Pysäytä BBjServices itse, käynnistä se uudelleen ja suorita watch uudelleen. |
| `BBjServices start program is missing: <program>` | BBj-asennuksessa ei ole ohjelmaa, jolla BBjServices käynnistetään. | Käynnistä BBjServices itse ja suorita watch uudelleen. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices ei käynnistynyt uudelleen. | Käynnistä BBjServices itse ja suorita watch uudelleen. |
| `BBjShip did not answer within 120 seconds` | BBjServices käynnistyi, mutta käyttöönoton päätepiste ei vastannut. | Tarkista BBjServices-loki ja suorita watch uudelleen. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices käynnistyi eri Java-argumenteilla kuin ominaisuustiedostoon kirjoitetut. | Tarkista BBjServicesin Java-asetukset Enterprise Managerissa ja kyseisessä tiedostossa. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | Sovellusta ei voitu poistaa, kun koonti pysähtyi. | Poista se komennolla `bbjship apps undeploy <name>` tai käynnistä watch uudelleen, jolloin se korvataan. |

Ensimmäinen kehitysajo, joka lisää hot swap -asetukset, käynnistää BBjServicesin uudelleen. macOS- ja Linux-käyttöjärjestelmissä se käynnistää BBjServicesin ohjelmalla `bin/bbjservices`. Windowsissa se käynnistää `BBjServices`-Windows-palvelun, jos sellainen on rekisteröity, ja muussa tapauksessa suorittaa ohjelman `bin\BBjServices.exe -noservice`. Ohjelmalla `bin/bbjservices` tai valitsimella `-noservice` käynnistetty BBjServices toimii käyttöjärjestelmän palvelunhallinnan ulkopuolella, kunnes kone käynnistetään uudelleen.

## Komentorivi {#command-line}

Kaikki `bbjship`-komennot suoritetaan BBjServicesin sisällä, joten BBjServicesin on oltava käynnissä. Ilman sitä komentoa ei voi käynnistää.

Virheelliset argumentit päättyvät poistumiskoodiin `2`, ja viestissä kerrotaan, mitä on määritettävä:

| Viesti | Toimi näin |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Määritä komento. Katso [Komentorivi](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Käytä kyseisen komennon valitsinta. Käytettävissä olevat valitsimet näkyvät komennon `--help`-ohjeessa. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Anna valitsimelle arvo. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Anna portin numero. |
| `Specify <option> only once` | Poista toistettu valitsin. |
| `Specify only one of --password and --password-stdin` | Käytä vain toista näistä. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Anna käyttäjänimi ja salasana yhdessä. Viestissä kerrotaan, kumpi puuttuu. |
| `Specify an application name or --all`, `Specify one application name` | Anna `apps undeploy` -komennolle yksi julkaisunimi tai valitsin `--all`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Anna `apps deploy` -komennolle yksi `.zip`-tiedosto. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Tarkista polku. BBjServices lukee tiedoston, joten BBjServicesin käyttöjärjestelmäkäyttäjällä on oltava siihen lukuoikeus. |
| `Development archives are deployed by the watch, not by apps deploy` | Ota käyttöön `bbj-package`-komennon arkisto, älä kehitysajon arkistoa. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Nimeä arkisto uudelleen. Tiedostonimi ilman `.zip`-päätettä on julkaisunimi. |

Epäonnistunut komento päättyy poistumiskoodiin `1` ja viestiin `Command failed.`, jota seuraa syy:

| Syy | Syy | Toimi näin |
|---|---|---|
| BBjServicesin kirjautuminen epäonnistui | Käyttäjänimi tai salasana on väärä. | Tarkista tunnistetiedot. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` tai `--ssl-port` ei ole portti, jota BBjServices-verkkopalvelin kuuntelee. Aiempi päätepiste jää käyttöön. | Käytä Enterprise Managerissa verkkopalvelimelle määritettyjä portteja. |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` -komennossa määritettyä sovellusta ei ole ottanut käyttöön BBjShip. | Tarkista nimi komennolla `bbjship apps list`. |
| `Another deployment is in progress` | Toinen käyttöönotto tai käytöstä poistaminen varasi koneen 30 sekunniksi. | Odota, että se valmistuu, ja suorita komento uudelleen. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Toinen Enterprise Managerissa oleva sovellus käyttää verkkokontekstia `webforj-bbjship` tai `webforj-bbjship-assets`. | Nimeä kyseinen konteksti uudelleen tai poista se Enterprise Managerissa. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Enterprise Managerin luokkapolkuja `_webforj_bbjship_cli` tai `_webforj_bbjship` on muutettu. | Poista molemmat luokkapolut Enterprise Managerissa ja suorita komento uudelleen, jotta ne luodaan uudestaan. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | BBjShip-kansio on puutteellinen. | Asenna BBjShip uudelleen. |
| `BBjServices does not manage web contexts. Update BBj` | BBj-versio on liian vanha BBjShipille. | Asenna webforJ-versiota vastaava BBj-versio. |

`reset` päättyy poistumiskoodiin `1` ja viestiin `Reset cancelled`, jos kysymykseen `Continue? [y/N]` annettu vastaus ei ole `y` tai `Y`. Kun komento palauttaa BBjServicesin Java-asetukset, se päättyy viestiin `Restart BBjServices now to apply the restored Java settings`. Käynnistä BBjServices uudelleen, jotta se toimii ilman hot swap -asetuksia.

## Windows {#windows}

| Tilanne | Toimi näin |
|---|---|
| `bbj.exe`-ohjelman suorittaminen BBjShip-ohjelman kanssa ei tulosta mitään. | Suorita komento `<BBj home>\plugins\webforj-bbjship`-kansion tiedostolla `bbjship.cmd`. Se näyttää komennon tulosteen. |
| `--password-stdin` päättyy poistumiskoodiin `2` ja viestiin `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Välitä salasana valitsimella `--password` tai aseta `WEBFORJ_BBJ_USERNAME` ja `WEBFORJ_BBJ_PASSWORD`. |
| `reset` päättyy poistumiskoodiin `2` ja viestiin `Standard input is not available on Windows. Run reset --yes to confirm`. | Suorita `bbjship.cmd reset --yes`. |
| `apps undeploy` tai `reset` tulostaa viestin `Not removed: <folder>. <reason>` ja päättyy poistumiskoodiin `0`. | BBjServices pitää sovelluksen tiedostoja yhä avoinna. Sovellus poistetaan Enterprise Managerista. Käynnistä BBjServices uudelleen. Kansio poistuu, kun seuraavan kerran otetaan käyttöön samanniminen sovellus. Voit myös poistaa sen uudelleenkäynnistyksen jälkeen. |
| BBjShipin asentaminen aiemmin asennetun version päälle epäonnistuu, koska sen tiedostot ovat käytössä. | BBjServices pitää asennetun BBjShipin tiedostoja yhä avoinna. Käynnistä BBjServices uudelleen ja asenna sitten uudelleen. |
| BBjServices ei toimi Windows-palveluna kehitysajon käynnistettyä sen uudelleen. | Kehitysajo käynnisti sen komennolla `BBjServices.exe -noservice`, koska `BBjServices`-palvelua ei ole rekisteröity. Se toimii Windowsin palvelunhallinnan ulkopuolella, kunnes kone käynnistetään uudelleen. |
| `bbjship.cmd`-komento ei käynnisty. | Käynnistä BBjServices. Kaikki `bbjship`-komennot edellyttävät sen olevan käynnissä. |
