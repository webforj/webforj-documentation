---
title: Local BLS License
sidebar_class_name: new-content
sidebar_position: 25
description: >-
  Configure a webforJ project to use a locally installed BLS certificate and
  client configuration.
_i18n_hash: 981d7333984cb25b65a90d27775eab9f
---
Een lokale BASIS License Service (BLS) stelt een webforJ-app in staat om een licentie aan te vragen bij een service die draait op je ontwikkeling machine of intern netwerk. Deze opzet is nuttig wanneer je een serienummer en autorisatienummer hebt en wilt dat het project de gegenereerde lokale licentiebestanden gebruikt in plaats van de standaard licentieconfiguratie.

Een webforJ-project gemaakt met [startforJ](https://docs.webforj.com/startforj) bevat twee licentiebestanden onder `src/main/resources`:

```
src/main/resources/blsclient.conf
src/main/resources/certificate.bls
```

Hier volgt hoe je de standaard licentiebestanden vervangt door de bestanden die zijn gegenereerd met een lokale BLS-installatie:

## Vereisten {#prerequisites}

Voordat je begint, zorg ervoor dat je hebt:

- Java 21 of Java 25 beschikbaar om de BLS 26-installatieprogramma uit te voeren.
- Een serienummer en autorisatienummer.
- Een webforJ-project met een `src/main/resources`-directory.
- Toegang tot de machine waar de BLS zal draaien.

## 1. Download het BLS-installatieprogramma {#1-download-bls}

Om het BLS-installatieprogramma te verkrijgen, ga naar de [BASIS Product Suite Downloads](https://basis.cloud/bbj-downloads/) pagina. Zodra je een gewenste taal voor het formulier hebt geselecteerd, ga je naar de sectie **Select Product**. In de dropdown **Product** selecteer je `BLS`, en in de dropdown **Revision** selecteer je de nieuwste versie. De vereiste Java-versies voor het draaien van de BLS staan onder de dropdown **Revision**.

Vul vervolgens het formulier in onder **Contact Information** en selecteer de vakjes onder **Download Terms**. Zodra je het formulier hebt ingevuld, selecteer je de knop `Download` om de BLS-installatie JAR te downloaden.

   ![Downloadformulier met BLS geselecteerd als product](/img/configuration/local-bls-license/download-bls.png#rounded-border)

   *Downloadformulier met `BLS` geselecteerd als product.*

## 2. Installeer en configureer de BLS {#2-install-andc-onfig-bls}

De gedownloade uitvoerbare JAR heeft de volgende naamgevingsconventie: `BLS<revision><date>_<time>.jar`. Zoek de JAR op en dubbelklik erop om het installatieprogramma te starten, of voer het uit vanuit een opdrachtconsole:

   ```bash
   java -jar <downloaded-bls-installer>.jar
   ```

Volg de aanwijzingen van het installatieprogramma en vul de vereiste gegevens in.

Standaard installeert de BLS in specifieke directories, afhankelijk van het besturingssysteem, maar kan dit worden gewijzigd in het venster **Directory Selection**. Voortaan verwijst `<blshome>` naar de installatielocatie van de BLS.

- **Windows**: `C:\bls`
- **macOS**: `/Applications/bls`
- **Andere besturingssystemen**: `/usr/local/bls`

Zodra je de BLS hebt geïnstalleerd, opent deze de **License Registration Wizard**.

### Licentieregistratie {#license-registration}

1. Kies in de License Registration Wizard de optie `Retrieve a license`:

   ![Licentieregistratiewizard met Retrieve a license geselecteerd](/img/configuration/local-bls-license/retrieve-license.png#rounded-border)

   *Licentieregistratiewizard met `Retrieve a license` geselecteerd.*

2. Voer in de volgende vensters je contactinformatie, serienummer en autorisatienummer in.

3. Wanneer je bij het venster **License Registration and Delivery Methods** komt, kies je `Register and install a license automatically`.

Na het registreren van je licentie, voltooi je de configuratie van de lokale BLS zoals nodig. Als je op een later moment je BLS-instellingen moet wijzigen of een andere licentie moet ophalen, gebruik dan de BLS Admin op `<blshome>/bin/BLSAdmin`.

## 3. Kopieer de gegenereerde licentiebestanden {#3-copy-the-generated-license-files}

Ga nu naar de `<blshome>/cfg`-directory en zoek de gegenereerde licentiebestanden `blsclient.conf` en `certificate.bls`:

![BLS cfg-map met de gegenereerde clientconfiguratie en certificaat](/img/configuration/local-bls-license/bls-cfg-folder.png#rounded-border)

*BLS-installatie `cfg`-map met de gegenereerde clientconfiguratie en certificaat.*

Kopieer `blsclient.conf` en `certificate.bls` naar je webforJ-project en vervang eventuele bestaande bestanden met dezelfde namen in de resources-directory. Nu, wanneer je lokale BLS draait, vraagt je webforJ-app de licentie aan bij die service.

```
src
├───main
│   ├───java/
│   └───resources
│       ├───icons/
│       ├───static/
│       ├───application.properties
│       ├───banner.txt
// highlight-next-line
│       ├───blsclient.conf
// highlight-next-line
│       └───certificate.bls
```

:::tip
Als je licentiebestanden zich buiten de standaard webforJ-configuratiemap bevinden, kun je de licentiemap configureren met [`webforj.license.cfg`](./properties#configuration-options).
:::
