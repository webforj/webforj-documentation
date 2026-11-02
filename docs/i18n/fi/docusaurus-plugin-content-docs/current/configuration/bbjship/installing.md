---
title: Asentaminen BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# BBjShipin asentaminen <DocChip chip='since' label='26.03' />

BBjShip toimii BBjServicesin sisällä, joten se on aina asennettuna koneeseen, joka ajaa BBjServicesia. Kun se on asennettu ja sen käyttöönottoendpointti on käytössä, rakennuksesi lähetetään siihen osoitteen `/webforj-bbjship` kautta BBjServicesin verkkopalvelimella, joko samalta koneelta tai toiselta.

BBjShipin asentaminen riippuu siitä, missä BBjServices toimii:

| Missä BBjServices toimii | Kuka asentaa BBjShipin | Miten |
|---|---|---|
| Oma koneesi | webforJ-rakennusliitännäinen | Automaattisesti `mvn`:llä, tai kerran `mvn webforj:bbj-setup`-komennolla |
| Jaetulla tai tuotantopalvelimella | Tuon palvelimen ylläpitäjä | BBj Plugin Manager, sitten `bbjship remote enable` |

Molemmissa tapauksissa asenna BBjShip-versio, joka vastaa sovellustesi webforJ-versiota. Asennukset epäonnistuvat, kun ne kaksi eroavat.

## Omalla koneellasi {#on-your-own-machine}

Kun BBj toimii samalla koneella kuin rakennuksesi, et asenna BBjShipiä itse. Ensimmäinen [kehityskäynnistys](/docs/configuration/deploy-reload/bbjservices) projektilta, joka on konfiguroitu BBjShipiä varten, asentaa sen ja ottaa käyttöön sen käyttöönottoendpointin:

```bash
mvn
```

### Maven-liitännäisen kautta {#through-the-maven-plugin}

Jos haluat lähettää käyttöön `mvn install` ennen kuin aloitat kehityskäynnistyksen, asenna BBjShip kerran projektisi kansiosta:

```bash
mvn webforj:bbj-setup
```

Tavoite asentaa BBjShipin versio, joka vastaa `webforj-maven-plugin`:ia ja ottaa käyttöön käyttöönottoendpointin käyttöönotto-URL:si portilla. Se vaatii:

- BBjServicesin toimivan ja vastaavan käyttöönotto-URL:n portilla
- Käyttöönotto-URL:n, joka nimeää koneesi, esimerkiksi `http://localhost:8888/webforj-bbjship`
- BBj-ylläpitäjän käyttäjänimen ja salasanan, `<admin>` tai `webforj.bbj.admin.*` -ominaisuuksista. Ilman niitä tavoite käyttää tehdaskäyttäjätunnusta `admin` salasanalla `admin123` ja tulostaa varoituksen.
- BBj-asennuskansion, `<home>`, `webforj.bbj.home` tai `BBJ_HOME`. Ilman niitä tavoite havaitsee sen.

WebforJ-päivityksen jälkeen vaihda asennettu BBjShip uudella versiolla:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Käytössäsi olevat sovellukset pysyvät käytössä, kun suoritat tavoitteen uudelleen.

:::tip Käytössä olevat tiedostot Windowsissa
Windowsissa korvaus voi epäonnistua, kun BBjServices pitää asennettujen BBjShipin tiedostoja auki. Käynnistä BBjServices uudelleen ja suorita tavoite uudelleen, katso [Vianetsintä](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Palvelimella {#on-a-server}

Jaetulla tai tuotantopalvelimella BBj:llä, ylläpitäjä asentaa BBjShipin kerran palvelimelle. Kehittäjät ja CI-putket lähettävät siihen omilta koneiltaan HTTPS:n kautta.

1. Asenna BBjShip [BBj Plugin Managerin](#through-the-bbj-plugin-manager) avulla.
2. [Ota käyttöön käyttöönottoendpointti](#enabling-the-deployment-endpoint) BBjServicesin verkkopalvelimen HTTPS-portilla.
3. Luo valtuutustoken Enterprise Managerissa rakentamista varten, joka lähettää, katso [Toiselta koneelta lähettäminen](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### BBj Plugin Managerin kautta {#through-the-bbj-plugin-manager}

BBj Plugin Managerissa [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) asentaa BBjShipin `webforj-bbjship`-varastosta `BBj-Plugins`-organisaatiossa. Tag asettaa webforJ-version, ja `master` asentaa kehityssnapshootin. Päivitä se myöhemmin Asennetut-välilehdellä.

### Käyttöönottoendpointin käyttöönottaminen {#enabling-the-deployment-endpoint}

Käyttöönottoendpointti on osoite, johon rakennukset lähettävät. Ota se käyttöön palvelimella BBj-ylläpitäjän tilillä ja BBjServicesin verkkopalvelimen kuunteluporreilla. `--password` ottaa salasanan arvona, jonka muut koneen käyttäjät näkevät prosessiluettelossa prosessin aikana. macOS:ssä ja Linuxissa `--password-stdin` lukee sen standardisisäänmenosta, ja `WEBFORJ_BBJ_PASSWORD` pitää sen poissa komentoriviltä kaikilla järjestelmillä:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Otetaan käyttöön http://localhost:8888/webforj-bbjship
Otetaan käyttöön https://localhost:8443/webforj-bbjship
```

Ilman `--port` endpointti käyttää porttia 8888. Ilman `--ssl-port` sillä ei ole HTTPS-osoitetta. BBjServicesin on pakko olla käynnissä. Katso [Komentorivi](/docs/configuration/bbjship/command-line) jokaisesta komennosta.

:::tip Salasana Windowsissa
Windowsissa suorita `bbjship.cmd` ja anna salasana käyttäen `--password` tai `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` ei ole käytettävissä siellä ja päättyy poistumiskoodilla `2`.
:::

## Poistaminen {#uninstalling}

Suorita ensin `bbjship reset`. Se poistaa kaiken sovelluksen ja poistaa kaiken tai BBjShipin, joka on asetettu Enterprise Manageriin. Poista sitten BBjShip BBj Plugin Managerissa.

:::warning Varmuuskopiointi ensin
`bbjship reset` ei voi peruuttaa. Varmuuskopioi `<BBj home>/cfg/BBj.properties` ja säilytä kaikkien sovellusten käyttöönottoarkisto ensin, katso [Nollaa](/docs/configuration/bbjship/command-line#reset).
:::
