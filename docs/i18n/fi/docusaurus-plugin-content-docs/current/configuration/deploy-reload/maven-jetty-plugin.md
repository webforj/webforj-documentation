---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 6ce3da7be312bb71f2ded56a583d7687
---
Maven Jetty -laajennus on suosittu työkalu, joka mahdollistaa kehittäjien ajaa Java-web-sovelluksia upotetussa Jetty-palvelimessa suoraan Maven-projekteista.

Jetty-laajennus käynnistää upotetun Jetty-palvelimen, joka valvoo sovelluksen tiedostoja, mukaan lukien Java-luokat ja resurssit, muutosten varalta. Kun se havaitsee päivityksiä, se ottaa sovelluksen automaattisesti uudelleen käyttöön, mikä nopeuttaa kehitystä poistamalla manuaaliset rakennus- ja käyttöönottoaskeleet.

:::tip Etupään muutokset
Muuttumiset `src/main/frontend`-kansiossa käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -toiminnolla, joka uudelleenrakentaa ne ja päivittää selaimen palvelimen rinnalla.
:::

## Jetty-konfiguraatiot {#jetty-configurations}

Tässä on joitain olennaisia konfiguraatioita laajennuksen kuuman käyttöönoton ja palvelinvaihtoehtojen hienosäätämiseen:

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Määrittää, kuinka usein Jetty-palvelin skannaa tiedostomuutoksia **`pom.xml`**-tiedostossa. Luonnospäiväys asettaa tämän `2` sekunniksi. Tämän aikavälin pidentäminen voi vähentää CPU-kuormitusta, mutta saattaa viivästyttää muutosten näkyvyyttä sovelluksessa. | `1`            |

## webforJ-konfiguraatiot {#webforj-configurations}

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Käytettäessä kuumaa uudelleenkäynnistystä, koko WAR-tiedosto vaihdetaan. Jos asiakas lähettää pyynnön palvelimen ollessa käynnistymässä, tapahtuu virhe. Tämä asetus antaa asiakkaalle mahdollisuuden yrittää sivun uudelleen lataamista, oletettavasti palvelimen palaavan pian takaisin online-tilaan. Soveltuu vain kehitysympäristöihin ja käsittelee vain kuumaan käyttöönottoon liittyviä virheitä. | `on`           |
| **`webforj.clientHeartbeatRate`** | Määrittää aikavälin asiakasviesteille palvelimen saatavuuden tarkistamiseksi. Tämä pitää asiakas-palvelin -viestinnän avoinna. Kehityksessä käytä lyhyempiä aikavälejä nopeampaa virheiden havaitsemiseksi. Tuotannossa aseta tämä vähintään 50 sekuntiin liiallisen pyyntöjen välttämiseksi. | `50s`          |

## Käyttöhuomiot {#usage-considerations}

Vaikka Jetty-laajennus on erittäin tehokas kehityksessä, sillä on muutamia mahdollisia rajoituksia:

- **Muistin ja CPU:n käyttö**: Usein tapahtuva tiedostojen skannaus, joita alhaiset `scan`-arvot `pom.xml`:ssä voivat kasvattaa resurssien kulutusta, erityisesti suurissa projekteissa. Aikavälin pidentäminen voi vähentää kuormitusta, mutta myös hidastaa uudelleenkäynnistystä.

- **Rajoitettu tuotantokäyttö**: Jetty-laajennus on suunniteltu kehitykseen, ei tuotantoympäristöihin. Se ei sisällä tuotannolle tarvittavia suorituskyvyn optimointeja ja turvallisuusasetuksia, joten se on paremmin soveltuva paikalliseen testaukseen.

- **Istunnon hallinta**: Kuuman uudelleenkäynnistyksen aikana käyttäjäistuntoja ei ehkä säilytetä, erityisesti silloin, kun koodissa tapahtuu suuria rakenteellisia muutoksia. Tämä voi häiritä käyttäjäistuntotietoja sisältäviä testejä, mikä vaatii manuaalista istunnon hallintaa tai kiertoratkaisuja kehitykseen.
