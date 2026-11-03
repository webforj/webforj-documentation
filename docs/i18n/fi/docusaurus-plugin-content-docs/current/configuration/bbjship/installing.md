---
title: BBjShipin asentaminen
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---
# BBjShipin asentaminen <DocChip chip='since' label='26.03' />

BBjShip toimii BBjServices-palvelun sisällä, joten se asennetaan aina koneelle, jolla BBjServices-palvelu toimii. Kun BBjShip on asennettu ja sen käyttöönoton päätepiste on otettu käyttöön, buildit otetaan käyttöön siinä BBjServices-palvelimen osoitteessa `/webforj-bbjship` joko samalta koneelta tai toiselta koneelta.

BBjShipin asennustapa riippuu siitä, missä BBjServices-palvelu toimii:

| BBjServices-palvelun sijainti | BBjShipin asentaja | Miten |
|---|---|---|
| Oma koneesi | webforJ-build-lisäosa | Automaattisesti komennolla `mvn` tai kerran komennolla `mvn webforj:bbj-setup` |
| Jaettu tai tuotantopalvelin | Palvelimen järjestelmänvalvoja | BBj Plugin Managerilla ja sitten komennolla `bbjship remote enable` |

Molemmissa tapauksissa asenna BBjShip-versio, joka vastaa sovellustesi webforJ-versiota. Käyttöönotot epäonnistuvat, jos versiot eroavat toisistaan.

## Omalla koneella {#on-your-own-machine}

Kun BBj toimii samalla koneella kuin buildisi, sinun ei tarvitse asentaa BBjShipiä itse. Projektin ensimmäinen [kehitysajo](/docs/configuration/deploy-reload/bbjservices), kun projekti on määritetty käyttämään BBjShipiä, asentaa sen ja ottaa käyttöön sen käyttöönoton päätepisteen:

```bash
mvn
```

### Maven-lisäosan kautta {#through-the-maven-plugin}

Jos haluat tehdä käyttöönoton komennolla `mvn install` ennen ensimmäistä kehitysajoa, asenna BBjShip kerran projektisi kansiosta:

```bash
mvn webforj:bbj-setup
```

Tavoite asentaa version, joka vastaa `webforj-maven-plugin`-lisäosan versiota, ja ottaa käyttöön käyttöönoton päätepisteen käyttöönotto-URL-osoitteesi portissa. Se tarvitsee seuraavat:

- BBjServices-palvelun on oltava käynnissä ja vastattava käyttöönotto-URL-osoitteen portissa
- Käyttöönotto-URL-osoitteessa on oltava koneesi nimi, esimerkiksi `http://localhost:8888/webforj-bbjship`
- BBj-järjestelmänvalvojan käyttäjänimi ja salasana, jotka saadaan määrityksestä `<admin>` tai ominaisuuksista `webforj.bbj.admin.*`. Jos niitä ei ole, tavoite käyttää tehdastiliä `admin` salasanalla `admin123` ja tulostaa varoituksen.
- BBj-asennuskansio, joka saadaan määrityksestä `<home>`, ominaisuudesta `webforj.bbj.home` tai ympäristömuuttujasta `BBJ_HOME`. Jos sitä ei ole määritetty, tavoite tunnistaa sen automaattisesti.

Päivitä webforJ-päivityksen jälkeen asennettu BBjShip uuteen versioon:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Käyttöönotetut sovelluksesi pysyvät käytössä, kun suoritat tavoitteen uudelleen.

:::tip Käytössä olevat tiedostot Windowsissa
Windowsissa korvaaminen voi epäonnistua, jos BBjServices pitää asennetun BBjShipin tiedostoja avoinna. Käynnistä BBjServices uudelleen ja suorita tavoite uudelleen. Katso [Vianmääritys](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Palvelimella {#on-a-server}

Jaetulla tai tuotantokäytössä olevalla BBj-palvelimella järjestelmänvalvoja asentaa BBjShipin kerran kyseiselle palvelimelle. Kehittäjät ja CI-putket ottavat sovelluksia käyttöön palvelimella omilta koneiltaan HTTPS-yhteyden kautta.

1. Asenna BBjShip [BBj Plugin Managerilla](#through-the-bbj-plugin-manager).
2. [Ota käyttöönoton päätepiste käyttöön](#enabling-the-deployment-endpoint) BBjServices-palvelimen HTTPS-portissa.
3. Luo käyttöönottoja tekeville buildeille valtuutustunnus Enterprise Managerissa. Katso [Käyttöönotto toiselta koneelta](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### BBj Plugin Managerilla {#through-the-bbj-plugin-manager}

Asenna BBjShip [BBj Plugin Managerissa](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) `BBj-Plugins`-organisaation `webforj-bbjship`-tietovarastosta. Tunniste asentaa webforJ-julkaisun, ja `master` asentaa kehitysversion tilannevedoksen. Voit päivittää sen myöhemmin Installed-välilehdellä.

### Käyttöönoton päätepisteen ottaminen käyttöön {#enabling-the-deployment-endpoint}

Käyttöönoton päätepiste on osoite, johon buildit tekevät käyttöönotot. Ota se käyttöön palvelimella BBj-järjestelmänvalvojan tunnuksella ja käyttäen BBjServices-palvelimen kuuntelemia portteja. `--password` ottaa salasanan arvona, jonka muut koneen käyttäjät näkevät prosessiluettelossa komennon suorituksen aikana. macOS- ja Linux-järjestelmissä `--password-stdin` lukee salasanan vakiosyötteestä, ja `WEBFORJ_BBJ_PASSWORD` pitää sen poissa komentoriviltä kaikissa järjestelmissä:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Jos `--port`-arvoa ei anneta, päätepiste käyttää porttia 8888. Jos `--ssl-port`-arvoa ei anneta, HTTPS-osoitetta ei ole. Muilla koneilla olevat buildit tekevät käyttöönotot HTTPS-osoitteeseen. Päätepiste vastaa HTTP 403 -virheellä niiden HTTP-yhteyden kautta lähettämiin pyyntöihin, ellei valitsinta `--allow-unencrypted` anneta. BBjServices-palvelun on oltava käynnissä. Katso kaikki komennot kohdasta [Komentorivi](/docs/configuration/bbjship/command-line).

:::tip Salasana Windowsissa
Suorita Windowsissa `bbjship.cmd` ja anna salasana valitsimella `--password` tai ympäristömuuttujalla `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` ei ole käytettävissä Windowsissa, ja se päättyy paluukoodiin `2`.
:::

## Asennuksen poistaminen {#uninstalling}

Suorita ensin `bbjship reset`. Se poistaa kaikkien sovellusten käyttöönotot ja kaiken, minkä BBjShip määritti Enterprise Managerissa. Poista sitten BBjShip BBj Plugin Managerissa.

:::warning Tee ensin varmuuskopio
Komentoa `bbjship reset` ei voi peruuttaa. Varmuuskopioi ensin `<BBj home>/cfg/BBj.properties` ja säilytä jokaisen sovelluksen käyttöönottoarkisto. Katso [Nollaus](/docs/configuration/bbjship/command-line#reset).
:::
