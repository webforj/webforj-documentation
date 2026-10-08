---
sidebar_position: 3
title: Tuotannon kovennus
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
webforJ:n [server-driven model](/docs/architecture/client-server) ja sisäänrakennetut suojat [yleisiä uhkia](/docs/security/application-security/common-threats) vastaan kattavat paljon, mutta turvallinen käyttöönotto riippuu silti siitä, miten käytät sovellusta. Alla olevat vaiheet täydentävät kokonaiskuvaa.

## Salakirjoita jokainen yhteys {#encrypt-every-connection}

Käytä tuotantoliikennettä vain HTTPS:n yli. Päätä TLS kontissa, proxyssä tai kuormantasaimessa sovelluksen edessä, ja ohjaa jokaiseen tavalliseen HTTP-pyyntöön sen turvallinen vastine, jotta tunnistetiedot ja istuntotunnukset eivät koskaan kulje salaamattomina.

## Älä luota mihinkään selaimesta {#trust-nothing-from-the-browser}

Manipuloitu asiakas voi lähettää mitä tahansa. Vahvista jokainen arvo, jonka koodisi vastaanottaa, jopa arvot, jotka käyttöliittymäsi on jo rajoittanut, ennen kuin tallennat tai käytät niitä. [Asiakas/palvelin -vuorovaikutus](/docs/architecture/client-server) -artikkeli selittää, miksi palvelin on ainoa paikka, jossa sääntö voi todella pitää.

webforJ:n [tietojen sitominen ja validoiminen](/docs/data-binding/validation/overview) auttaa tässä: koska sitominen tapahtuu Javassa palvelimella, mallit, joihin lisäät rajoja, mukaan lukien [Jakarta-validointi](/docs/data-binding/validation/jakarta-validation), valvotaan palvelinpuolella eikä vain selaimessa. Käsittele tätä eheyden kerroksena, ei puolustuksena injektointi- tai muotoilu hyökkäyksiä vastaan, jotka tarvitsevat edelleen käsittelyä, joka on kuvattu [Yleisissä uhkissa](/docs/security/application-security/common-threats) -artikkelissa.

## Poistettu ja piilotettu eivät ole turvallisuutta {#disabled-and-hidden-arent-security}

`setEnabled(false)` ja `setVisible(false)` ovat käyttöliittymän vihjeitä, eivät pääsynvalvontaa. webforJ heijastaa ohjaimen poistettua tilaa asiakkaalle, mutta se ei estä manipuloitua asiakasta palauttamasta ohjainta käyttöön ja käynnistämästä sen toimintoa. Älä koskaan luota poistettuun tai piilotettuun ohjaimeen estääksesi jotain tapahtumasta.

Laita todellinen sääntö palvelinpuolen käsittelijään: varmista, että käyttäjällä on oikeudet ja että ennakkoehdot ovat voimassa ennen toimintoa, juuri kuten toimisit, jos ohjain olisi ollut koko ajan käytössä. Poistetila opastaa rehellisiä käyttäjiä; palvelinpuolen sääntö estää epärehellisiä.

## Rajaa näkymäsi {#lock-down-your-views}

Rajoita näkymiä [reittiturvalla](/docs/security/overview), jotta jokainen vaatii oikean todistamisen ja roolit. Anna ihmisille kapein mahdollinen pääsy, joka mahdollistaa heidän työnsä, ja suosii oletusarvoista turvattua lähestymistapaa, jossa merkitsemätön reitti vaatii edelleen sisäänkirjautumisen.

## Pidä salaisuudet ulkopuolella {#keep-secrets-external}

Tunnistetiedot, avaimet ja tokenit eivät kuulu koodiin tai varastoosi. Hae ne ympäristöstä tai ulkoisesta lähteestä sen sijaan, kuten on esitetty [Salausten hallinnassa](/docs/security/application-security/managing-secrets).

## Pidä kehitystyökalut pois päältä {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) on kehitysympäristö, joka tarkkailee toimivaa sovellusta ja kirjoittaa muutokset takaisin sen Java-lähteeseen. Se vaatii sekä `webforj.debug` että `webforj.devtools.craftforj.enabled`, ja oletuksena se vastaa vain koneelle, jossa sovellus toimii. Projekti, joka on luotu [startforJ](https://docs.webforj.com/startforj) avulla tai webforJ:n [archetypestä](/docs/building-ui/archetypes/overview), on molemmat asetukset käytössä kehitykselle, joten varmista ne sen sijaan, että olettaisit.

Tarkista, että molemmat ominaisuudet ovat asetettuina joko pois päältä tai `false` konfiguraatiossa, jonka todella otat käyttöön, mukaan lukien ympäristömuuttuja tai profiili, joka koskee vain tuotantoa. Lataa sitten otettu sovellus ja varmista, että mitään craftforJ:ta laukaisevaa tapahtumaa ei näy sivulla. Katso [craftforJ:n turvallisuudesta](/docs/craftforj/security) täydellisen kuvan saamiseksi.

## Pidä riippuvuudet ajan tasalla {#stay-current-on-dependencies}

Kirjastot, jotka otat käyttöön, ovat suurempi riskilähde kuin oma koodisi. Seuraa ilmoituksia, päivitä webforJ:tä ja muita riippuvuuksia säännöllisesti, ja kun korjattu versio välittömästä kirjastosta julkaistaan ennen kirjastoa, joka tuo sen käyttöön, lukitse korjattu versio `pom.xml`:ssäsi.

## Epäonnistu hiljaisesti {#fail-quietly}

Älä anna pinojälkien, tiedostopolkujen tai sisäisten tunnisteiden saavuttaa loppukäyttäjiä. Tallenna tiedot palvelinlokkeihisi ja esitä yksinkertainen, yleinen viesti käyttöliittymässä. Rekisteröi mukautettu käsittelijä webforJ:n [virheenkäsittelyssä](/docs/advanced/error-handling), jotta käsittelemättömät poikkeukset näyttävät hallitun sivun raakojen diagnostiikoiden sijaan.

## Ilmoita vastuullisesti {#disclose-responsibly}

Löysitkö mahdollisen virheen itse webforJ:ssä? Ilmoita siitä yksityisesti GitHubin [yksityisen haavoittuvuuden raportoinnin](https://github.com/webforj/webforj/security/advisories) kautta sen sijaan, että avaisit julkisen ongelman tai vetopyynnön, jotta korjaus voi saapua ennen kuin tiedot ovat tiedossa.
