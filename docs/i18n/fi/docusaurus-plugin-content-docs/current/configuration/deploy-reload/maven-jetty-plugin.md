---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 7b8efc852651f2bea9db4ca110fe11ed
---
Maven Jetty -liitännäinen on suosittu työkalu, joka mahdollistaa kehittäjille Java-verkkosovellusten suorittamisen sisäänrakennetussa Jetty-palvelimessa suoraan heidän Maven-projekteistaan.

Jetty-liitännäinen käynnistää sisäänrakennetun Jetty-palvelimen, joka valvoo sovelluksen tiedostoja, mukaan lukien Java-luokkia ja resursseja, muutosten varalta. Kun se havaitsee päivityksiä, se automaattisesti uudelleenasentaa sovelluksen, mikä nopeuttaa kehitystyötä poistamalla manuaaliset rakennus- ja käyttöönottoaskeleet.

:::tip Frontend-muutokset
Muutokset `src/main/frontend` -kansiossa käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -toiminnolla, joka uudelleenkokoaa ne ja päivittää selaimen yhdessä palvelimen kanssa.
:::

## Jetty-asetukset {#jetty-configurations}

Tässä on joitakin olennaisia asetuksia liitännäisen kuumaa käyttöönottoa ja palvelimen vuorovaikutusasetuksia hienosäätämiseksi:

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Määrittää, kuinka usein Jetty-palvelin tarkistaa tiedostomuutoksia **`pom.xml`** -tiedostossa. Luonnosprojekti asettaa tämän 2 sekuntiin. Tämä välin lisääminen voi vähentää CPU-kuormitusta, mutta voi viivästyttää muutosten heijastumista sovelluksessa. | `1`            |

## webforJ-asetukset {#webforj-configurations}

| Ominaisuus                          | Kuvaus                                                                                                                                                                           | Oletus        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Kun käytetään kuumaa uudelleenasennusta, koko WAR-tiedosto vaihdetaan. Jos asiakas lähettää pyynnön, kun palvelin käynnistyy uudelleen, syntyy virhe. Tämä asetus sallii asiakkaan yrittää ladata sivun uudelleen, olettaen, että palvelin palaa takaisin verkkoon pian. Koskee vain kehitysympäristöjä ja käsittelee vain kuumaan uudelleenasentamiseen liittyviä virheitä. | `on`           |
| **`webforj.clientHeartbeatRate`** | Määrittää asiakaspingien välin palvelimen saatavuuden kyselyyn. Tämä pitää asiakas-palvelin-vuorovaikutuksen avoinna. Kehityksessä käytä lyhyempiä välejä nopeampaa virheiden havaitsemista varten. Produksiossa aseta tämä vähintään 50 sekuntiin liiallisen kysynnän välttämiseksi. | `50s`          |

## Käyttöhuomiot {#usage-considerations}

Vaikka Jetty-liitännäinen on erittäin tehokas kehityksessä, sillä on joitakin mahdollisia rajoituksia:

- **Muistin ja CPU:n käyttö**: Usein tapahtuva tiedostojen skannaus, jota matalat `scan` -arvot `pom.xml` -tiedostossa määrittävät, voivat lisätä resurssien kulutusta, erityisesti suurissa projekteissa. Välin lisääminen voi vähentää kuormitusta, mutta myös hidastaa uudelleenasennusta.

- **Rajoitettu tuotantokäyttö**: Jetty-liitännäinen on suunniteltu kehitykseen, ei tuotantoympäristöihin. Se ei omaa tuottavuusoptimointia tai turvallisuusasetuksia, joita tuotannossa vaaditaan, joten se sopii paremmin paikalliseen testaukseen.

- **Istunnon hallinta**: Kuuman uudelleenasennuksen aikana käyttäjäistuntoja ei välttämättä säilytetä, erityisesti kun koodissa tapahtuu suuria rakenteellisia muutoksia. Tämä voi häiritä käyttäjäistuntotietoihin liittyviä testejä, ja vaatii manuaalista istunnon hallintaa tai kiertotieasetuksia kehityksessä.
