---
title: Deployointi
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# Sovellus <DocChip chip='since' label='26.03' /> {#deploying}

webforJ Maven -laajennus julkaisee sovelluksen [BBjShip](/docs/configuration/bbjship/overview) käyttäen kolmea tavoitetta:

| Tavoite | Suoritetaan | Mitä se tekee |
|---|---|---|
| `bbj-package` | `package` -vaiheessa | Kirjoittaa julkaisuarkiston `target/bbj/<julkaisun nimi>.zip` |
| `bbj-deploy` | `install` -vaiheessa | Kirjoittaa arkiston ja julkaisee sen BBjShipille |
| `bbj-undeploy` | käsin | Poistaa sovelluksen BBjServicesista |

Vaiheet pätevät, kun liitännäinen on määritetty `<extensions>true</extensions>` ja `<bbj>` -lohkon kanssa, katso [Projektin määrittäminen](/docs/configuration/bbjship/existing-projects).

## Julkaise {#deploy}

```bash
mvn install
```

Ilman liitännäistä, suorita `mvn package webforj:bbj-deploy`. Tavoite tulostaa sovelluksen osoitteen, kun julkaisu valmistuu:

```text
[INFO] BBj julkaisu varastosta on valmis http://localhost:8888/webapp/inventory
```

Kun julkaisu epäonnistuu, edellinen versio jatkaa toimintaa.

:::warning Korvataan sovellus
Julkaisu korvasi sovelluksen, jolle on jo annettu sama julkaisu nimi. Aiempi julkaisu kansio poistetaan ja kyseisen sovelluksen käynnissä olevat istunnot päättyvät. Säilytä edellisen version arkisto, jos saatat tarvita palata takaisin.
:::

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) luettelee epäonnistuneen julkaisun viestit ja mitä tehdä kunkin osalta.

## Paketti julkaisematta {#package-without-deploying}

`bbj-package` kirjoittaa saman arkiston ilman julkaisun URL-osoitetta tai käyttöoikeustietoja. CI-työ voi liittää `target/bbj/<julkaisun nimi>.zip` rakennukseen, ja BBj-järjestelmänvalvoja julkaisee sen BBj-koneella käyttäen [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Poista julkaisu {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

Sovellus pysähtyy ja poistetaan Enterprise Managerista yhdessä sen tiedostojen kanssa.

:::warning Toimenpidettä ei voi peruuttaa
`bbj-undeploy` lopettaa sovelluksen käynnissä olevat istunnot ja poistaa sen julkaisukansion. Tätä ei voi peruuttaa. Säilytä arkisto `target/bbj/<julkaisun nimi>.zip` -tiedostosta, jos saatat julkaista sovelluksen uudelleen.
:::

## Julkaisun nimet {#publish-names}

Julkaisun nimi oletusarvoisesti on rakennuksen lopullinen nimi ja se näkyy sovelluksen osoitteessa. Sen on aloitettava kirjaimella tai numerolla, ja siinä on oltava vain kirjaimia, numeroita, `_`, `.` ja `-`, eikä sen saa päättyä pisteeseen. Varatut nimet, kuten BBjServices-reitit `webapp` ja `files` tai Windowsin laite nimi `con`, hylätään ennen arkiston kirjoittamista.

Jokainen tietue luokka kuuluu yhteen julkaistuun sovellukseen. Julkaiseminen toisen nimen alle epäonnistuu:

```text
BBj julkaisu epäonnistui HTTP 409:llä. Tietue luokka com.example.Application on jo julkaistu inventaarina. Poista inventaarin julkaisu ensin
```

## Julkaisu toiselta koneelta {#deploying-from-another-machine}

Julkaisu toiselta koneelta, kuten CI-palvelimelta, käyttää valtuutustunnusta HTTPS:n kautta:

1. BBj-koneella, [ota käyttöön päätepiste](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) SSL-portilla.
2. Enterprise Managerissä, luo tunnus Turvallisuus, Valtuutustunnukset -osiosta. Uudista ja peruuta se myös siellä.
3. Siirrä tunnus rakennukseen:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

BBjServicesin web-palvelimen sertifikaatin on vastattava URL-osoitteen isäntänimeä tai oltava rakennuksen Java-truststoressa. Java ei luota BBj:n oletussertifikaattiin, joka on itse allekirjoitettu. `http://` URL-osoite toiselle koneelle lähettää käyttöoikeustiedot, jotka ovat luettavissa verkossa, ja tavoitteet tulostavat varoituksen siitä.

## Sovelluksen asetukset {#app-settings}

BBjShip lukee sovelluksen `webforj.conf` jokaisessa julkaisussa ja soveltaa nämä ominaisuudet sovellukseen Enterprise Managerissa. Uudelleen julkaisu ottaa käyttöön muuttuneen arvon. Asetus, jota ei ole määritetty, säilyttää BBjServicesin oletusasetukset.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Ominaisuus | Oletus | Kuvaus |
|---|---|---|
| `webforj.assetsSource` | Ei asetettu | `packaged` palvelee `ws://` staattisia tiedostoja ja `icons://` kuvakkeita sovelluksesta, osoitteessa `/webforj/<julkaisun nimi>/`. Ilman sitä sovellus, joka käyttää `icons://`, ei käynnisty. |
| `webforj.showClientConfirmationDialog` | `false` | Kysyykö selain vahvistusta ennen sivun sulkemista. |
| `webforj.disallowConsole` | `true` | Onko konsoli estetty sovelluksessa. |
| `webforj.secure` | BBjServicesin oletus | Käykö sovellus suojatun yhteyden kautta. |
| `webforj.configFile` | BBjServicesin oletus | BBj:n konfiguraatiotiedoston polku, jota sovellus käyttää BBj-koneella. Polku annetaan sellaisena kuin se on kirjoitettu, joten se voi nimetä vain palvelimella olevan tiedoston. |
| `webforj.programArguments` | Ei mitään | Sovelluksen ohjelmaargumentit merkkijonoluettelona tai yhtenä merkkijonona. |

Virheellinen `webforj.conf` epäonnistuttaa julkaisun HTTP 400:lla ja syyn.

## Asetukset {#settings}

Asetukset löytyvät `webforj-maven-plugin` -liitännän `<bbj>` -lohkon sisältä, kuten on esitetty [Rakenna liitännäinen](/docs/configuration/bbjship/existing-projects#the-build-plugin). Jokainen ominaisuus ohittaa sen elementin komentorivillä.

| Maven-elementti | Maven-ominaisuus | Oletus | Kuvaus |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Havaitut | BBj-asennushakemisto, jota käytetään `bbj-setup` ja `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Rakennuksen lopullinen nimi | Nimi, jonka mukaan sovellus julkaistaan, katso [Julkaisun nimet](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Ei mitään, vaaditaan | BBjShipin osoite, `http://<isäntä>:<HTTP port>/webforj-bbjship` tai `https://<isäntä>:<SSL port>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin`, kun käyttäjänimi ja salasana ovat molemmat puuttuvat | BBj-järjestelmänvalvojan käyttäjänimi. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123`, kun käyttäjänimi ja salasana ovat molemmat puuttuvat | BBj-järjestelmänvalvojan salasana. |
| `<admin><token>` | `webforj.bbj.admin.token` | Tyhjät | Valtuutustunnus `bbj-deploy` ja `bbj-undeploy` varten. Ohittaa käyttäjänimen ja salasanan. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServicesin ominaisuustiedostoon, jonne [kehitysjulkaisun](/docs/configuration/deploy-reload/bbjservices) vaihdot kirjoitetaan. |
| `<development><classFolder>` | — | Rakennuksen tulostushakemisto | Kehitysjulkaisun luokka hakemisto, kuten BBjServices sen lukee. |
| — | `webforj.bbj.force` | `false` | Antaa `bbj-setup`:n korvata toisen version asennetun BBjShipin. Vain ominaisuus. |

Käyttäjänimi ilman salasanaa tai salasana ilman käyttäjänimeä pysäyttää tavoitteen.
