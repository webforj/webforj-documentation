---
title: Maven Jetty plugin
_i18n_hash: 13b8de676f30b5a21eb7e9c2b49945b6
---
Maven Jetty -laajennus on suosittu työkalu, joka mahdollistaa kehittäjille Java-verkkosovellusten ajamisen sisäänrakennetussa Jetty-palvelimessa suoraan heidän Maven-projekteistaan.

Jetty-plugin käynnistää sisäänrakennetun Jetty-palvelimen, joka valvoo sovelluksesi tiedostoja, mukaan lukien Java-luokat ja resurssit, muutosten varalta. Kun se havaitsee päivityksiä, se automaattisesti uudelleen asettaa sovelluksen, mikä nopeuttaa kehitystä poistamalla manuaaliset rakentamis- ja käyttöönottovaiheet.

## Jetty-konfiguraatiot {#jetty-configurations}

Tässä on joitakin olennaisia konfiguraatioita pluginin kuuman käyttöönoton ja palvelinvuorovaikutuksen asetusten hienosäätöön:

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Määrittää, kuinka usein Jetty-palvelin skannaa tiedostomuutoksia **`pom.xml`**-tiedostossa. Luonnosprojektissa tämä on asetettu `2` sekunniksi. Tämän välin pidentäminen voi vähentää CPU-kuormitusta, mutta voi viivästyttää muutosten näkymistä sovelluksessa. | `1`            |

## webforJ-konfiguraatiot {#webforj-configurations}

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Kun käytetään kuumaa uudelleenasennusta, koko WAR-tiedosto vaihdetaan. Jos asiakas lähettää pyynnön palvelimen käynnistymisen aikana, tapahtuu virhe. Tämä asetuksella asiakas voi yrittää ladata sivun uudelleen, olettaen, että palvelin on pian online-tilassa. Soveltuu vain kehitysympäristöihin ja käsittelee vain virheitä, jotka liittyvät kuumaan uudelleenasennukseen. | `on`           |
| **`webforj.clientHeartbeatRate`** | Määrittää välin asiakasviestien lähettämiseksi palvelimen saatavuuden kysymiseksi. Tämä pitää asiakas-palvelin -viestinnän avoimena. Kehityksessä käytä lyhyempiä välejä nopeaa virheiden havaitsemista varten. Tuotannossa aseta tämä vähintään 50 sekuntiin liiallisen pyyntökuorman välttämiseksi. | `50s`          |

## Käyttöön liittyvät huomiot {#usage-considerations}

Vaikka Jetty-plugin on erittäin tehokas kehityksessä, sillä on joitakin mahdollisia rajoituksia:

- **Muistin ja CPU:n käyttö**: Usein tapahtuva tiedostoskannaus, jolloin `pom.xml`:ssä on matalat `scan`-arvot, voi lisätä resurssien kulutusta, erityisesti suurissa projekteissa. Välin pidentäminen voi vähentää kuormitusta, mutta hidastaa myös uudelleenasennusta.

- **Rajoitettu tuotantokäyttö**: Jetty-plugin on suunniteltu kehitystä varten, ei tuotantoympäristöjä varten. Siinä ei ole suorituskyvyn optimointia ja turvallisuusasetuksia, joita tarvitaan tuotannossa, joten se soveltuu paremmin paikalliseen testaukseen.

- **Istunnon hallinta**: Kuuman uudelleenasennuksen aikana käyttäjäistuntoja ei välttämättä säilytetä, erityisesti silloin, kun suuria rakenteellisia muutoksia tapahtuu koodissa. Tämä voi keskeyttää käyttäjäistuntodataa sisältävät testit, mikä vaatii manuaalista istunnon hallintaa tai kiertoratkaisuasetuksia kehityksessä.
