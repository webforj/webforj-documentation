---
title: BBjShip
sidebar_position: 1
hide_table_of_contents: true
description: >-
  Deploy webforJ apps to BBjServices with BBjShip and the webforJ Maven plugin,
  and run them on your local BBj during development.
_i18n_hash: c2a89de1c4f3ad8b0f4bc5d364b27a67
---
# BBjShip <DocChip chip='since' label='26.03' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

BBjShip on BBj laajennus, joka ottaa käyttöön webforJ-sovelluksia BBjServices:ssä. Voit rakentaa ja ottaa käyttöön sovelluksesi [webforJ build pluginin](/docs/configuration/build-plugin) avulla, joko BBj-koneessa tai muulta koneelta, kuten CI-palvelimelta, ja sovellus toimii BBjServices:ssä kuin mikä tahansa muu sovellus, jota hallinnoit Enterprise Managerissa.

BBjShip korvasi webforJ asennuspluginin, joka on kuvattu [Running with BBjServices](/docs/configuration/bbj-installation/overview), joka on poistettu käytöstä ja tullaan poistamaan tulevassa julkaisussa.

:::info Vain Maven
BBjShip toimii Maven-projektejen kanssa `webforj-maven-plugin`in kautta. webforJ Gradle plugin ei tue BBjShipiä.
:::

## How it works {#how-it-works}

Käyttöönottoprosessi kulkee kolmen vaiheen läpi:

1. **webforJ build plugin**: pakkaa sovelluksesi sen riippuvuuksien kanssa ja lataa sen BBjShipiin
2. **BBjShip**: asentaa sovelluksen BBjServices:ssä ja julkaisee sen `/webapp/<publish name>`
3. **BBjServices**: suorittaa sovelluksen, jota hallitset Enterprise Managerissa kuten mitä tahansa muuta sovellusta

Kehityksen aikana sama build plugin suorittaa sovelluksesi projektistasi BBjServices:ssä omassa koneessasi. Kääntämäsi muutokset saavuttavat käynnissä olevan sovelluksen ilman käyttöönottamista, ja avoimet sivut päivittyvät, katso [BBjServices](/docs/configuration/deploy-reload/bbjservices) uudelleen käyttöönotosta ja live-päivityksestä.

## Topics {#topics}

<DocCardList className="topics-section" />
