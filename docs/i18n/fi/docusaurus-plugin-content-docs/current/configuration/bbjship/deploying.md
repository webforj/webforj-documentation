---
title: Käyttöönotto
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# Käyttöönotto <DocChip chip='since' label='26.03' /> {#deploying}

webforJ Maven -laajennus ottaa sovelluksen käyttöön [BBjShipissä](/docs/configuration/bbjship/overview) kolmella tavoitteella:

| Tavoite | Suoritetaan | Toiminto |
|---|---|---|
| `bbj-package` | `package`-vaiheen yhteydessä | Kirjoittaa käyttöönottoarkiston `target/bbj/<publish name>.zip` |
| `bbj-deploy` | `install`-vaiheen yhteydessä | Kirjoittaa arkiston ja ottaa sovelluksen käyttöön BBjShipissä |
| `bbj-undeploy` | manuaalisesti | Poistaa sovelluksen BBjServicesistä |

Vaiheet ovat käytössä, kun laajennus on määritetty asetuksilla `<extensions>true</extensions>` ja `<bbj>`-lohkolla. Katso [Projektin määrittäminen](/docs/configuration/bbjship/existing-projects).

## Ota käyttöön {#deploy}

```bash
mvn install
```

Jos laajennus ei ole käytössä, suorita `mvn package webforj:bbj-deploy`. Tavoite tulostaa sovelluksen osoitteen, kun käyttöönotto on valmis:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

Jos käyttöönotto epäonnistuu, edellinen versio jatkaa toimintaansa.

:::warning Korvaa sovelluksen
Käyttöönotto korvaa sovelluksen, jolla on sama julkaisu nimi. Edellinen käyttöönottokansio poistetaan, ja sovelluksen käynnissä olevat istunnot päättyvät. Säilytä edellisen version arkisto, jos saatat haluta palata siihen.
:::

[Ongelmien vianmääritys](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) sisältää epäonnistuneen käyttöönoton viestit ja ohjeet niiden käsittelyyn.

## Pakkaa ottamatta käyttöön {#package-without-deploying}

`bbj-package` kirjoittaa saman arkiston ilman käyttöönoton URL-osoitetta tai tunnistetietoja. CI-työ voi liittää tiedoston `target/bbj/<publish name>.zip` koontiversioon, minkä jälkeen BBj-järjestelmänvalvoja ottaa sen käyttöön BBj-koneella komennolla [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Poista käyttöönotosta {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

Sovellus pysäytetään ja poistetaan Enterprise Managerista tiedostoineen.

:::warning Toimintoa ei voi perua
`bbj-undeploy` päättää sovelluksen käynnissä olevat istunnot ja poistaa sen käyttöönottokansion. Tätä ei voi perua. Säilytä arkisto `target/bbj/<publish name>.zip`, jos saatat ottaa sovelluksen uudelleen käyttöön.
:::

## Julkaisunimet {#publish-names}

Julkaisunimi on oletusarvoisesti koontiversion lopullinen nimi, ja se näkyy sovelluksen osoitteessa. Nimen on alettava kirjaimella tai numerolla, ja se voi sisältää vain kirjaimia, numeroita sekä merkit `_`, `.` ja `-`. Nimi ei saa päättyä pisteeseen. Varatut nimet, kuten BBjServicesin polut `webapp` ja `files` tai Windowsin laitteen nimi `con`, hylätään ennen arkiston kirjoittamista.

Jokainen aloitusluokka kuuluu yhteen käyttöönotettuun sovellukseen. Sen käyttöönotto toisella nimellä epäonnistuu seuraavasti:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Käyttöönotto toiselta koneelta {#deploying-from-another-machine}

Käyttöönotto toiselta koneelta, kuten CI-palvelimelta, käyttää valtuutustunnusta HTTPS-yhteyden kautta:

1. [Ota käyttöönottopäätepiste käyttöön](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) BBj-koneella SSL-porttia käyttäen.
2. Luo Enterprise Managerissa tunnus kohdassa Security, Authentication Tokens. Voit uusia ja mitätöidä tunnuksen samassa paikassa.
3. Välitä tunnus koontiversiolle:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

BBjServices-verkkopalvelimen varmenteen on vastattava URL-osoitteen isäntänimeä tai sen on oltava koontiversion Javan luottamusvarastossa. Java ei luota BBj:n oletusvarmenteeseen, koska se on itse allekirjoitettu.

Toiseen koneeseen osoittava `http://`-URL hylätään molemmissa päissä. Tavoite pysähtyy ennen kuin se lähettää mitään, ja BBjShip vastaa HTTP 403 -virheellä pyyntöön, joka saapuu HTTP:n kautta toiselta koneelta. BBj-koneeseen itseensä osoittava URL, kuten `localhost` tai jokin sen omista osoitteista, toimii HTTP:n kautta.

Jos haluat silti ottaa sovelluksen käyttöön HTTP:n kautta toisella koneella, esimerkiksi omalla tietokoneellasi olevassa virtuaalikoneessa, salli se molemmissa päissä. Suorita BBj-koneella `bbjship remote enable --allow-unencrypted` käyttäen päätepisteen portteja. Aseta koontiversiossa `<deployment><allowUnencrypted>true</allowUnencrypted>` tai välitä seuraava ominaisuus:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

Tavoite tulostaa tällöin varoituksen ja ottaa sovelluksen käyttöön. Tunnistetiedot kulkevat kyseisen yhteyden kautta verkossa luettavassa muodossa.

## Sovellusasetukset {#app-settings}

BBjShip lukee sovelluksen `webforj.conf`-tiedoston jokaisen käyttöönoton yhteydessä ja määrittää sen ominaisuudet sovellukselle Enterprise Managerissa. Uudelleenkäyttöönotto ottaa muuttuneen arvon käyttöön. Jos ominaisuutta ei ole asetettu, käyttöön jää BBjServicesin oletusarvo.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Ominaisuus | Oletusarvo | Kuvaus |
|---|---|---|
| `webforj.assetsSource` | Ei asetettu | `packaged` tarjoaa sovelluksesta `ws://`-staattiset tiedostot ja `icons://`-kuvakkeet polussa `/webforj/<publish name>/`. Ilman tätä asetusta `icons://`-osoitteita käyttävän sovelluksen käynnistys epäonnistuu. |
| `webforj.showClientConfirmationDialog` | `false` | Kysyykö selain vahvistusta ennen sivun sulkemista. |
| `webforj.disallowConsole` | `true` | Onko konsolin käyttö sovelluksessa estetty. |
| `webforj.secure` | BBjServicesin oletusarvo | Suoritetaanko sovellus suojatun yhteyden kautta. |
| `webforj.configFile` | BBjServicesin oletusarvo | Sen BBj-määritystiedoston polku, jonka kanssa sovellus suoritetaan. BBjServices lukee tiedoston BBj-koneella. Polku välitetään sellaisenaan, joten se voi osoittaa tiedostoon, joka on olemassa vain palvelimella. |
| `webforj.programArguments` | Ei mitään | Sovelluksen ohjelma-argumentit merkkijonoluettelona tai yhtenä merkkijonona. |

Virheellinen `webforj.conf` aiheuttaa käyttöönoton epäonnistumisen HTTP 400 -virheellä ja syyn sisältävällä viestillä.

## Asetukset {#settings}

Asetukset määritetään `webforj-maven-plugin`-laajennuksen `<bbj>`-lohkossa, kuten kohdassa [Koontilaajennus](/docs/configuration/bbjship/existing-projects#the-build-plugin) näytetään. Kukin ominaisuus ohittaa komentoriviltä annetun arvon.

| Maven-elementti | Maven-ominaisuus | Oletusarvo | Kuvaus |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Tunnistetaan automaattisesti | BBj-asennuskansio, jota `bbj-setup` ja `watch` käyttävät. |
| `<publishName>` | `webforj.bbj.publishName` | Koontiversion lopullinen nimi | Nimi, jolla sovellus julkaistaan. Katso [Julkaisunimet](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Ei ole, pakollinen | BBjShipin osoite: `http://<host>:<HTTP port>/webforj-bbjship` tai `https://<host>:<SSL port>/webforj-bbjship`. |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | Sallii komentojen `bbj-deploy` ja `bbj-undeploy` lähettää tunnistetiedot `http://`-URL-osoitteeseen toisella koneella. Katso [Käyttöönotto toiselta koneelta](#deploying-from-another-machine). |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin`, jos sekä käyttäjänimi että salasana puuttuvat | BBj-järjestelmänvalvojan käyttäjänimi. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123`, jos sekä käyttäjänimi että salasana puuttuvat | BBj-järjestelmänvalvojan salasana. |
| `<admin><token>` | `webforj.bbj.admin.token` | Tyhjä | `bbj-deploy`- ja `bbj-undeploy`-komentojen valtuutustunnus. Ohittaa käyttäjänimen ja salasanan. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServicesin ominaisuustiedosto, johon [kehitysajossa](/docs/configuration/deploy-reload/bbjservices) kirjoitetaan hot swap -asetukset. |
| `<development><classFolder>` | — | Koontiversion tulostehakemisto | Kehitysajon luokkakansio sellaisena kuin BBjServices sen lukee. |
| — | `webforj.bbj.force` | `false` | Sallii `bbj-setup`-komennon korvata asennetun, eri versiota olevan BBjShipin. Vain ominaisuus. |

Jos käyttäjänimi annetaan ilman salasanaa tai salasana ilman käyttäjänimeä, tavoite pysähtyy.
