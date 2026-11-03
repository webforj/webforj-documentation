---
title: Komentorivi
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# Komentorivi <DocChip chip='since' label='26.03' /> {#command-line}

`bbjship`-komentoriviohjelmalla hallitaan BBjShipiä BBj-koneessa. Se sijaitsee polussa `<BBj home>/plugins/webforj-bbjship`, ja Windowsissa sen nimi on `bbjship.cmd`. Jokainen komento suoritetaan BBjServicesissä, joten BBjServicesin on oltava käynnissä jokaista komentoa varten.

```text
bbjship <group> <command> [options]
```

## Komennot {#commands}

| Komento | Kuvaus |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Ottaa käyttöön käyttöönottopäätepisteen tai päivittää sen ja tulostaa sen osoitteet. |
| [`remote disable`](#remote-disable) | Poistaa käyttöönottopäätepisteen. Käyttöönotetut sovellukset jatkavat toimintaansa. |
| [`remote status`](#remote-status) | Tulostaa päätepisteen osoitteet, tiedon siitä, ovatko kehitysajot käytössä, sekä tiedon siitä, sallitaanko salaamattomat pyynnöt toisesta koneesta. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Ottaa käyttöön käyttöönottopaketin. |
| [`apps list [--long]`](#apps-list) | Näyttää BBjShipillä käyttöönotetut sovellukset. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Poistaa yhden tai kaikki BBjShipillä käyttöönotetut sovellukset. |
| [`development enable`, `development disable`](#development) | Ottaa [kehitysajot](/docs/configuration/deploy-reload/bbjservices) käyttöön tai poistaa ne käytöstä tässä koneessa. |
| [`reset [--yes] [--properties FILE]`](#reset) | Poistaa kaikki sovellukset ja kaiken BBjShipin tekemän määrityksen. |

## Yhteiset valitsimet {#options}

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--username USER` | Ei asetettu | BBj-järjestelmänvalvojan käyttäjätunnus. |
| `--password PASSWORD` | Ei asetettu | BBj-järjestelmänvalvojan salasana. |
| `--password-stdin` | Pois käytöstä | Lukee salasanan vakiosyötteen ensimmäiseltä riviltä. Ei käytettävissä Windowsissa. |
| `--help`, `-h` | | Tulostaa komentorivin tai yksittäisen komennon käyttöohjeen. |
| `--version`, `-V` | | Tulostaa BBjShip-version. |

`--password`-valitsimella annettu salasana näkyy koneen muille käyttäjille prosessiluettelossa komennon suorituksen ajan. macOS- ja Linux-järjestelmissä komentosarja välittää salasanan sen sijaan vakiosyötteestä:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Kaikissa järjestelmissä muuttujien avulla salasana pysyy poissa komentoriviltä:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Windowsissa vakiosyöte ei ole käytettävissä, joten `--password-stdin` palauttaa poistumiskoodin `2`. Anna salasana arvona tai muuttujien kautta:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Jos annat sekä `--password`- että `--password-stdin`-valitsimen, käyttäjätunnuksen ilman salasanaa tai salasanan ilman käyttäjätunnusta, komento päättyy poistumiskoodiin `2` ja tulostaa viestin, jossa kerrotaan, mitä pitää määrittää.

Jos tunnistetietoja ei anneta, komento käyttää tehdastiliä `admin` salasanalla `admin123` ja tulostaa varoituksen.

## Ympäristömuuttujat {#environment-variables}

| Muuttuja | Oletus | Kuvaus |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Ei asetettu | Käyttäjätunnus, kun `--username`-valitsinta ei ole annettu. |
| `WEBFORJ_BBJ_PASSWORD` | Ei asetettu | Salasana, kun `--password`- tai `--password-stdin`-valitsinta ei ole annettu. |
| `NO_COLOR` | Ei asetettu | Poistaa värillisen tulosteen käytöstä. |

## Etäkäyttö {#remote}

`remote`-komennoilla hallitaan käyttöönottopäätepistettä.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Ottaa käyttöön käyttöönottopäätepisteen tai päivittää sen ja tulostaa sen osoitteet.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--port PORT` | `8888` | Päätepisteen HTTP-portti BBjServices-verkkopalvelimessa. |
| `--ssl-port PORT` | Ei mitään | Päätepisteen SSL-portti BBjServices-verkkopalvelimessa. Ilman tätä päätepisteellä ei ole HTTPS-osoitetta. |
| `--allow-unencrypted` | Pois käytöstä | Sallii järjestelmänvalvojan tunnistetietojen lähettämisen HTTP-yhteydellä toisesta koneesta. Ilman tätä toisen koneen pyynnössä on käytettävä SSL-porttia, ja päätepiste vastaa siihen HTTP 403 -virheellä. BBj-koneesta tulevat pyynnöt käyttävät HTTP-yhteyttä kummassakin tapauksessa. |

Porttien on oltava sellaisia, joissa BBjServices-verkkopalvelin jo kuuntelee. Muussa tapauksessa `remote enable` päättyy poistumiskoodiin `1`, ilmoittaa vastaamattoman portin ja säilyttää edellisen päätepisteen.

Jokainen `remote enable` tallentaa portit ja `--allow-unencrypted`-asetuksen uudelleen. Kun komento suoritetaan ilman valitsinta, asetus poistetaan käytöstä ja tulostetaan `Refused unencrypted requests from another machine`. Kun se suoritetaan valitsimen kanssa, tulostetaan `Allowed unencrypted requests from another machine`. Koontiversio, joka ottaa sovelluksen käyttöön HTTP-yhteydellä toisesta koneesta, määrittää myös [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine)-asetuksen.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Poistaa käyttöönottopäätepisteen. Käyttöönotetut sovellukset jatkavat toimintaansa.

<!-- vale Google.WordListCase = NO -->
:::warning Koontiversiot eivät voi ottaa sovelluksia käyttöön
`remote disable` poistaa käyttöönottopäätepisteen. Koontiversioiden ja CI-töiden käyttöönotot epäonnistuvat, kunnes suoritat `remote enable` -komennon uudelleen. Kirjaa portit ja päätepisteen osoite muistiin ennen sen poistamista käytöstä. Käyttöönotetut sovellukset jatkavat toimintaansa.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Tulostaa päätepisteen osoitteet, tiedon siitä, ovatko kehitysajot käytössä, sekä tiedon siitä, sallitaanko salaamattomat pyynnöt toisesta koneesta.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Sovellukset {#apps}

`apps`-komennoilla voi luetella ja poistaa vain BBjShipillä käyttöönotettuja sovelluksia. Enterprise Managerissa rekisteröityihin sovelluksiin ne eivät vaikuta.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Ottaa käyttöön käyttöönottopaketin. `apps deploy` asentaa [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying)-komennolla luodun paketin ilman käyttöönottopäätepistettä. `.zip`-päätteetön tiedostonimi on [julkaisun nimi](/docs/configuration/bbjship/deploying#publish-names). BBjServices lukee paketin, joten BBjServicesin käyttöjärjestelmäkäyttäjällä on oltava siihen lukuoikeus.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Korvaa sovelluksen
Jos paketti otetaan käyttöön jo käytössä olevan sovelluksen nimellä, se korvaa sovelluksen. Edellinen käyttöönottokansio poistetaan ja sovelluksen käynnissä olevat istunnot päättyvät. Säilytä edellinen paketti, jos saatat tarvita sen palauttamista.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Näyttää BBjShipillä käyttöönotetut sovellukset.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--long`, `-l` | Pois käytöstä | Lisää kunkin sovelluksen käyttöönottokansion ja luokkakansiot. |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Poistaa yhden tai kaikki BBjShipillä käyttöönotetut sovellukset.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--all` | Pois käytöstä | Poistaa kaikki BBjShipillä käyttöönotetut sovellukset. |

:::warning Toimintoa ei voi kumota
`apps undeploy` poistaa sovelluksen Enterprise Managerista, päättää sen käynnissä olevat istunnot ja poistaa sen käyttöönottokansion. `apps undeploy --all` tekee tämän kaikille BBjShipillä käyttöönotetuille sovelluksille. Toimintoa ei voi kumota. Säilytä kunkin sovelluksen käyttöönottopaketti, esimerkiksi `target/bbj/<publish name>.zip`, jotta voit ottaa sovelluksen uudelleen käyttöön.
:::

## Kehitys {#development}

Kehitysajot ovat uudessa asennuksessa pois käytöstä, ja ensimmäinen `webforj:watch` samassa koneessa ottaa ne käyttöön. `bbjship development disable` poistaa ne käytöstä, kunnes seuraava `webforj:watch` tässä koneessa ottaa ne jälleen käyttöön. Toisesta koneesta käynnistetty kehitysajo hylätään, koska sen aluksi suorittama määritys toimii vain BBj-koneessa.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Ottaa [kehitysajot](/docs/configuration/deploy-reload/bbjservices) käyttöön tässä koneessa.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Poistaa [kehitysajot](/docs/configuration/deploy-reload/bbjservices) käytöstä tässä koneessa.

## Nollaus {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` poistaa kaikki BBjShipillä käyttöönotetut sovellukset, käyttöönottopäätepisteen, kehityksen käyttöönottoasetuksen ja `--allow-unencrypted`-asetuksen, palauttaa kehitysajon muuttaman BBjServicesin ominaisuustiedoston ja poistaa BBjShipin kirjoittamat tiedostot. Komento ei koskaan pysäytä BBjServicesiä. Kun ominaisuustiedosto on palautettu, käynnistä BBjServices uudelleen, jotta muutokset tulevat voimaan. Liitännäiskansio säilyy.

| Asetus | Oletus | Kuvaus |
|---|---|---|
| `--yes` | Pois käytöstä | Ohittaa vahvistuspyynnön. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | BBjServicesin ominaisuustiedosto, jonka `reset` palauttaa. |

Komento tulostaa tekemänsä toimet ja pyytää vahvistusta, ellei `--yes`-valitsinta ole annettu. Windowsissa `reset` ilman `--yes`-valitsinta päättyy poistumiskoodiin `2` ennen sisäänkirjautumista.

:::warning Tee ensin varmuuskopio
`reset`-toimintoa ei voi kumota. Se poistaa kaikkien BBjShipillä käyttöönotettujen sovellusten kansiot ja päättää niiden käynnissä olevat istunnot. Ennen komennon suorittamista säilytä kunkin sovelluksen käyttöönottopaketti, esimerkiksi koontiversion `target/bbj/<publish name>.zip`, ja varmuuskopioi `<BBj home>/cfg/BBj.properties`. Jos tiedostosta on tallennettu kopio BBjShipin ajonaikaiseen kansioon, `reset` palauttaa kyseisen kopion tiedoston `cfg/BBj.properties` päälle, jolloin kaikki ensimmäisen kehitysajon jälkeen tekemäsi muutokset menetetään. Käynnistä BBjServices sen jälkeen uudelleen, jotta palautetut Java-asetukset tulevat voimaan.
:::

## Poistumiskoodit {#exit-codes}

| Koodi | Merkitys |
|---|---|
| `0` | Komento onnistui. |
| `1` | Komento epäonnistui ja tulosti syyn, tai `reset` peruutettiin. |
| `2` | Argumentit eivät kelpaa tai komento tarvitsee Windowsissa vakiosyötettä. Viestissä kerrotaan, mitä pitää määrittää. |

[Vianmääritys](/docs/configuration/bbjship/troubleshooting#command-line) sisältää viestit ja ohjeet niiden käsittelyyn.
