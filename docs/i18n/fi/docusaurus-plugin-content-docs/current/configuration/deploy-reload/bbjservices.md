---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
BBjServicesissä rakentaminen ei aloita omaa palvelinta. `mvn compile webforj:watch` käynnistää sovelluksen oman koneen BBjServicesissä suoraan projektilta. BBj-makuinen arkkityyppiprojekti asettaa tämän oletus Maven-tavoitteeksi, joten `mvn` ilman argumentteja kääntää sovelluksen, käynnistää [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -tapahtuman ja suorittaa sovelluksen.

## Vaatimukset {#requirements}

Maven-projekti, joka on asetettu BBjShipille, katso [Aloittaminen](/docs/configuration/bbjship/getting-started) tai [Olemassa olevat projektit](/docs/configuration/bbjship/existing-projects), jossa BBj on asennettu samaan koneeseen projektin kanssa. BBjServices lataa luokkia rakennuksen kääntämistä varten, joten BBj-palvelin toisessa koneessa saa vain [deployt](/docs/configuration/bbjship/deploying), eikä siinä ole live reloadia eikä hotswapia.

## Live reloadin kytkeminen päälle {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Loput avaimet on lueteltu [asetuksissa](/docs/configuration/deploy-reload/overview#settings).

## Luokkamuutokset {#class-changes}

BBjServicesissä luokkamuutos saavuttaa toimivan sovelluksen vain [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) kautta. Ilman sellaista käännetty muutos ei sovellu, eikä sivu päivity ennen kuin watch alkaa uudelleen. Arkkityyppiprojekti määrittää HotswapAgentin:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Työkalu liitetään BBjServicesiin, ei rakennukseen. Ensimmäinen suoritus lisää sen Java-argumentteihin BBjServicesissä ja käynnistää BBjServicesin kerran uudelleen. Myöhemmät suoritukset alkavat ilman uudelleenkäynnistystä. macOS:llä ja Linuxilla sekä Windowsissa, kun BBjServices ei ole rekisteröity Windows-palveluna, uudelleen käynnistetty BBjServices toimii käyttöjärjestelmän palvelinhallinnan ulkopuolella, kunnes koneet käynnistetään uudelleen. [Vianetsintä](/docs/configuration/bbjship/troubleshooting#development-runs) listaa kehityssuorituksen viestit ja mitä tehdä jokaiselle.

:::warning BBjServicesin uudelleenkäynnistykset
Ensimmäinen suoritus muuttaa BBjServicesin Java-asetuksia tiedostossa `<BBj home>/cfg/BBj.properties` ja käynnistää BBjServicesin uudelleen. Uudelleenkäynnistys päättää kaikki toimivat BBj-istunnot sillä koneella, ei vain webforJ-istunnot, joten suorita se, kun kukaan muu ei käytä sitä BBj:tä. Ennen ensimmäistä muutosta tiedostosta otetaan kopio `BBj.properties.before-webforj` kansioon `<BBj home>/plugins/.webforj-bbjship-deployments`. Varmista, että varmuuskopioit tiedoston itse, jos olet muuttanut sitä käsin. `mvn webforj:bbj-setup` tekee saman muutoksen.
:::

Riippuvuuden muutos vaatii watchin uuden aloituksen.

### BBjServicesin Java {#java-of-bbjservices}

HotswapAgentin avulla [luokkarakenteen muutokset](/docs/configuration/deploy-reload/hotswap#class-structure-changes) riippuvat Java-versiosta, jota BBjServices käyttää, eivät rakentamisen Java-versiosta. Watch ei koskaan muuta sitä. Suorittaaksesi BBjServicesin [JetBrains Runtime]in (https://github.com/JetBrains/JetBrainsRuntime/releases) päällä:

1. Mene Enterprise Managerissa kohtaan **BBjServices**, **Java Settings**.
2. Valitse **BBjServices** kohdassa **Application** ja aseta Java-koti.
3. Käynnistä BBjServices uudelleen. **Java Details** Enterprise Managerissa näyttää sitten JDK:n ja version, jota se käyttää.

Katso [Prerequisites](/docs/introduction/prerequisites#java-development-kit-jdk-21) JetBrains Runtime -asennukseen.

## Tuotantorakennukset {#production-builds}

`mvn install` julkaisee pakatun sovelluksen ilman kehitystyökaluja, katso [Julkaiseminen](/docs/configuration/bbjship/deploying). Ominaisuus `webforj.devtools.livereload.enabled` ei vaikuta pakattuun sovellukseen.
