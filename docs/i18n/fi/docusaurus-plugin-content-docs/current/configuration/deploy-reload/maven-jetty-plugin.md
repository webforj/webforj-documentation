---
title: Maven Jetty plugin
_i18n_hash: 7311fe4d0b6c5382244d898f099b9435
---
Maven Jetty -lisäosa on suosittu työkalu, joka mahdollistaa kehittäjien suorittaa Java-verkkosovelluksia sisäisessä Jetty-palvelimessa suoraan Maven-projekteistaan.

Jetty-lisäosa käynnistää sisäänrakennetun Jetty-palvelimen, joka valvoo sovelluksesi tiedostoja, mukaan lukien Java-luokkia ja resursseja, muutosten varalta. Kun se havaitsee päivityksiä, se ottaa sovelluksen automaattisesti uudelleen käyttöön, mikä nopeuttaa kehitystä poistamalla manuaaliset rakennus- ja käyttöönottoaskeleet.

## Jetty configurations {#jetty-configurations}

Tässä on joitakin olennaisia konfiguraatioita lisäosan kuuman käyttöönoton ja palvelinvuorovaikutuksen asetusten hienosäätämiseksi:

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletusarvo     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Määrittää, kuinka usein Jetty-palvelin skannaa tiedostomuutoksia **`pom.xml`**-tiedostossa. Luonnosprojekti asettaa tämän `2` sekunniksi. Tämän välin pidentäminen voi vähentää prosessorin kuormitusta, mutta se voi myös viivästyttää muutosten näkymistä sovelluksessa. | `1`            |

## webforJ configurations {#webforj-configurations}

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletusarvo     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Kun käytetään kuumaa uudelleenkäyttöä, koko WAR-tiedosto vaihdetaan. Jos asiakas lähettää pyyntöjä, kun palvelin on käynnistymässä, virhe tapahtuu. Tämä asetus mahdollistaa asiakkaan yrittää ladata sivua uudelleen olettaen, että palvelin on pian käytettävissä. Koskee vain kehitysympäristöjä ja käsittelee vain kuumaan uudelleenkäyttöön liittyviä virheitä. | `on`           |
| **`webforj.clientHeartbeatRate`** | Määrittää vaiheen asiakaspinguille palvelimen saatavuuden kyselyä varten. Tämä pitää asiakas-palvelin-viestinnän avoimena. Kehityksessä käytä lyhyempiä välejä nopeamman virheiden havaitsemisen vuoksi. Tuotannossa aseta tämä vähintään 50 sekuntiin liiallisten pyyntöjen välttämiseksi. | `50s`          |

## Käytön huomioitavaa {#usage-considerations}

Vaikka Jetty-lisäosa on erittäin tehokas kehityksessä, sillä on joitakin mahdollisia rajoituksia:

- **Muistin ja prosessorin käyttö**: Usein tapahtuva tiedostojen skannaus, joka asetetaan matalilla `scan`-arvoilla `pom.xml`-tiedostossa, voi lisätä resurssien kulutusta, erityisesti suurissa projekteissa. Välin pidentäminen voi vähentää kuormitusta, mutta se hidastaa myös uudelleenkäyttöönottoa.

- **Rajoitettu tuotantokäyttö**: Jetty-lisäosa on suunniteltu kehitykseen, ei tuotantoympäristöihin. Siltä puuttuvat tuotantoon tarvittavat suorituskyvyn optimointi- ja suojausasetukset, joten se soveltuu paremmin paikalliseen testaukseen.

- **Istunnon hallinta**: Kuuman uudelleenkäytön aikana käyttäjäistuntoja ei välttämättä säilytetä, erityisesti silloin, kun koodissa tapahtuu suuria rakenteellisia muutoksia. Tämä voi häiritä testejä, joissa on käyttäjäistuntoon liittyviä tietoja, mikä vaatii manuaalista istunnon hallintaa tai kiertoratkaisuja kehitykselle.
