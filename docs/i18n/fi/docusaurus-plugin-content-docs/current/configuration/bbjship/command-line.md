---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# Komentorivillä <DocChip chip='since' label='26.03' /> {#command-line}

`bbjship` komento hallitsee BBjShipiä BBj-koneella. Se sijaitsee kohdassa `<BBj home>/plugins/webforj-bbjship`, nimellä `bbjship.cmd` Windowsissa. Jokainen komento suoritetaan BBjServicesin sisällä, joten BBjServicesin on oltava käynnissä jokaiselle komennolle.

```text
bbjship <ryhmä> <komento> [vaihtoehdot]
```

## Komennot {#commands}

| Komento | Kuvaus |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | Aktivoi tai päivittää käyttöönotto-osoitteen ja tulostaa sen osoitteet. |
| [`remote disable`](#remote-disable) | Poistaa käyttöönotto-osoitteen. Käynnistetyt sovellukset jatkavat toimintaa. |
| [`remote status`](#remote-status) | Tulostaa osoitteet ja onko kehityskäynnistykset aktivoitu. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Käynnistää käyttöönottoarkiston. |
| [`apps list [--long]`](#apps-list) | Listaa BBjShipin käyttöönotetut sovellukset. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Poistaa yhden tai kaikki BBjShipin käyttöönotetut sovellukset. |
| [`development enable`, `development disable`](#development) | Kytkee [kehityskäynnistykset](/docs/configuration/deploy-reload/bbjservices) päälle tai pois päältä tällä koneella. |
| [`reset [--yes] [--properties FILE]`](#reset) | Poistaa kaikki sovellukset ja kaiken, mitä BBjShip on asettanut. |

## Yhteiset vaihtoehdot {#options}

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--username KÄYTTÄJÄ` | Asetettu | BBj-järjestelmänvalvojan käyttäjänimi. |
| `--password SALASANA` | Asetettu | BBj-järjestelmänvalvojan salasana. |
| `--password-stdin` | Pois päältä | Lukee salasanan ensimmäiseltä riviltä vakiotuloa. Ei saatavilla Windowsissa. |
| `--help`, `-h` | | Tulostaa komentorivin tai yhden komennon käytön. |
| `--version`, `-V` | | Tulostaa BBjShipin version. |

`--password` -vaihtoehdolla annettu salasana näkyy muille koneen käyttäjille prosessiluettelossa komennon suorittamisen aikana. macOS:ssä ja Linuxissa skripti siirtää salasanan vakiotuloon näin:

```bash
printf '%s\n' "$SENALASANA" | bbjship remote enable --username admin --password-stdin
```

Jokaisella järjestelmällä muuttujat pitävät salasanan komentorivin ulkopuolella:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$SENALASANA" bbjship remote enable
```

Windowsissa vakiotulo ei ole saatavilla, joten `--password-stdin` päättyy poistumiskoodilla `2`. Siirrä salasana arvona tai muuttujien kautta:

```text
bbjship.cmd remote enable --username admin --password <arvo>
```

Antaminen sekä `--password` että `--password-stdin`, käyttäjänimi ilman salasanaa tai salasana ilman käyttäjänimeä päättyy poistumiskoodiin `2` ja viestiin, joka ilmoittaa, mitä määrittää.

Ilman käyttäjätietoja komento käyttää tehdastiliä `admin` ja salasanaa `admin123` ja tulostaa varoituksen.

## Ympäristömuuttujat {#environment-variables}

| Muuttuja | Oletus | Kuvaus |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Asetettu | Käyttäjänimi, kun `--username` ei ole käytössä. |
| `WEBFORJ_BBJ_PASSWORD` | Asetettu | Salasana, kun kumpikaan `--password` tai `--password-stdin` ei ole annettu. |
| `NO_COLOR` | Asetettu | Poistaa värillisen tulosteen. |

## Etä {#remote}

`remote` komennot hallitsevat käyttöönotto-osoitetta.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

Aktivoi tai päivittää käyttöönotto-osoitteen ja tulostaa sen osoitteet.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--port PORT` | `8888` | BBjServicesin verkkopalvelimen HTTP-portti osoitteelle. |
| `--ssl-port PORT` | Ei mitään | BBjServicesin verkkopalvelimen SSL-portti osoitteelle. Ilman sitä käyttöosoitteella ei ole HTTPS-osoitetta. |

Porttien on oltava portteja, joihin BBjServicesin verkkopalvelin jo kuuntelee. Muutoin `remote enable` päättyy poistumiskoodiin `1`, nimetään portti, joka ei vastaa, ja säilyttää edellinen käyttöosoite.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Poistaa käyttöönotto-osoitteen. Käynnistetyt sovellukset jatkavat toimintaa.

:::warning Käyttöönottoa ei voi palauttaa
`remote disable` poistaa käyttöönotto-osoitteen. Rakennukset ja CI-työt, jotka käyttävät sitä, epäonnistuvat, kunnes suoritat `remote enable` jälleen. Huomaa portit ja käyttöosoitteen osoite ennen kuin poistat sen. Käynnistetyt sovellukset jatkavat toimintaa.
:::

### `remote status` {#remote-status}

```text
bbjship remote status
```

Tulostaa osoitteet ja onko kehityskäynnistykset aktivoitu.

```text
$ bbjship remote status
Aktivoitu http://localhost:8888/webforj-bbjship
Aktivoitu https://localhost:8443/webforj-bbjship
Kehityskäynnistykset  pois päältä
```

## Sovellukset {#apps}

`sovellus` komennot listaavat ja poistavat vain sovelluksia, jotka BBjShip on käyttöönotanut. Enterprise Manageriin rekisteröityjä sovelluksia ei vaikuta.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARKISTO
```

Käynnistää käyttöönottoarkiston. `apps deploy` asentaa arkiston, jonka on kirjoittanut [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), ilman käyttöönotto-osoitetta. Tiedostonimi ilman `.zip`-päätettä on [julkaisu nimi](/docs/configuration/bbjship/deploying#publish-names). BBjServices lukee arkiston, joten BBjServicesin käyttöjärjestelmän käyttäjällä on oltava lukuoikeus siihen.

```text
$ bbjship apps deploy inventory.zip
Käyttöönotettu inventory /webapp/inventory
```

:::warning Korvataan sovellus
Arkiston käyttöönotto sovelluksen nimellä, joka on jo otettu käyttöön, korvataan se sovellus. Aiempi käyttöönotto hakemisto poistetaan ja sovelluksen käynnissä olevat istunnot päättyvät. Säilytä aikaisempi arkisto, jos saatat tarvita palaamista.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Listaa BBjShipin käyttöönotetut sovellukset.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--long`, `-l` | Pois päältä | Lisää käyttöönottokansion ja jokaisen sovelluksen luokkakansio. |

```text
$ bbjship apps list
NIMI       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NIMI
bbjship apps undeploy --all
```

Poistaa yhden tai kaikki BBjShipin käyttöönotetut sovellukset.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--all` | Pois päältä | Poistaa kaikki BBjShipin käyttöönotetut sovellukset. |

:::warning Ei voi peruuttaa
`apps undeploy` poistaa sovelluksen Enterprise Managerista, lopettaa sen käynnissä olevat istunnot ja poistaa sen käyttöönottohakemiston. `apps undeploy --all` tekee tämän kaikille BBjShipin käyttöönotetuille sovelluksille. Tätä ei voi peruuttaa. Säilytä jokaisen sovelluksen käyttöönottoarkisto, esimerkiksi `target/bbj/<publish name>.zip`, jotta voit ottaa sen uudelleen käyttöön.
:::

## Kehitys {#development}

Kehityskäynnistykset ovat pois päältä uudessa asennuksessa, ja ensimmäinen `webforj:watch` samalla koneella kytkee ne päälle. `bbjship development disable` kytkee ne pois päältä, kunnes seuraava `webforj:watch` tällä koneella kytkee ne uudelleen päälle. Kehityskäynnistys toiselta koneelta hylätään, koska sen suorittama asennus toimii vain BBj-koneella.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Kytkee [kehityskäynnistykset](/docs/configuration/deploy-reload/bbjservices) päälle tällä koneella.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Kytkee [kehityskäynnistykset](/docs/configuration/deploy-reload/bbjservices) pois päältä tällä koneella.

## Nollaa {#reset}

```text
bbjship reset [--yes] [--properties TIEDOSTO]
```

`reset` poistaa kaikki sovellukset, jotka BBjShip on ottanut käyttöön, poistaa käyttöönotto-osoitteen ja kehityskytkimen, palauttaa BBjServicesin ominaisuustiedoston, jota kehityskäynnistys muutti, ja poistaa BBjShipin kirjoittamat tiedostot. Komento ei koskaan pysäytä BBjServicesia. Kun se palautti ominaisuustiedoston, käynnistä BBjServices uudelleen sen käyttöönottamiseksi. Liitteen hakemisto pysyy.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--yes` | Pois päältä | Ohittaa vahvistuksen. |
| `--properties TIEDOSTO` | `<BBj home>/cfg/BBj.properties` | BBjServicesin ominaisuustiedosto, jonka `reset` palauttaa. |

Komento tulostaa, mitä se tekee, ja kysyy vahvistusta, ellei `--yes` anneta. Windowsissa `reset`, ilman `--yes`, päättyy poistumiskoodiin `2`, ennen kuin se kirjautuu sisään.

:::warning Ennen varmuuskopiointia
`reset` ei voi peruuttaa. Se poistaa jokaisen BBjShipin käyttöönotetun sovelluksen hakemiston ja lopettaa niiden käynnissä olevat istunnot. Ennen kuin suoritat sen, säilytä jokaisen sovelluksen käyttöönottoarkisto, esimerkiksi `target/bbj/<publish name>.zip` ja varmuuskopioi `<BBj home>/cfg/BBj.properties`. Kun tiedoston kopio oli tallennettuna BBjShipin suoritusympäristön hakemistoon, `reset` palauttaa sen kopion takaisin `cfg/BBj.properties`:ään, joten kaikki tiedostoon tekemäsi muutokset ensimmäisen kehityskäynnistyksen jälkeen menetetään. Käynnistä BBjServices uudelleen sen jälkeen, jotta voit ottaa palautetut Java-asetukset käyttöön.
:::

## Poistumiskoodit {#exit-codes}

| Koodi | Merkitys |
|---|---|
| `0` | Komento onnistui. |
| `1` | Komento epäonnistui ja tulosti syyn, tai `reset` peruutettiin. |
| `2` | Argumentit ovat virheellisiä tai komento tarvitsee vakiotuloa Windowsissa. Viesti näyttää, mitä määrittää. |

[Ongelmanratkaisu](/docs/configuration/bbjship/troubleshooting#command-line) luettelee viestit ja mitä tehdä jokaiselle.
