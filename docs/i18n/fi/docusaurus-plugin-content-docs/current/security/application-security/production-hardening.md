---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
webforJ:n [palvelinohjattu malli](/docs/architecture/client-server) ja sisäänrakennetut suojausmekanismit [yleisiä uhkia](/docs/security/application-security/common-threats) vastaan kattavat paljon, mutta turvallinen käyttöönotto riippuu silti siitä, kuinka sovellusta käytetään. Alla olevat vaiheet täydentävät kuvaa.

## Salaa jokainen yhteys {#encrypt-every-connection}

Aja tuotantoliikenne vain HTTPS:n yli. Lopeta TLS asti säiliössä, väylässä tai kuormantasaimessa sovelluksen edessä, ja ohjaa kaikki tavallisella HTTP:llä tehdyt pyynnöt niiden turvallisiin vastineisiin, jotta käyttäjätunnukset ja istuntotunnukset eivät koskaan kulje salattomana.

## Älä luota mihinkään selaimesta {#trust-nothing-from-the-browser}

Manipuloitu asiakas voi lähettää mitä tahansa. Tarkista jokainen arvo, jonka koodisi vastaanottaa, jopa arvot, joita käyttöliittymäsi on jo rajoittanut, ennen kuin tallennat tai toimit niiden perusteella. [Asiakas/Palvelin vuorovaikutus](/docs/architecture/client-server) -artikkeli selittää, miksi palvelin on ainoa paikka, jossa sääntö voi todella pitää.

webforJ:n [tietosidonta ja validointi](/docs/data-binding/validation/overview) auttaa tässä: koska sidonta tapahtuu Javassa palvelimella, mallit, joihin liität rajoituksia, mukaan lukien [Jakarta validointi](/docs/data-binding/validation/jakarta-validation), pannaan täytäntöön palvelinpuolella eikä vain selaimessa. Käsittele tätä eheytesi kerroksena, ei puolustuksena injektio- tai merkintähyökkäyksiä vastaan, jotka tarvitsevat silti käsittelyn, joka on kuvattu [Yleisissä uhissa](/docs/security/application-security/common-threats).

## Poistettu ja piilotettu ei ole turvallisuutta {#disabled-and-hidden-arent-security}

`setEnabled(false)` ja `setVisible(false)` ovat käyttöliittymän vihjeitä, eivät pääsynvalvontaa. webforJ peilaa ohjaimen poistettua tilaa asiakkaalle, mutta se ei estä manipuloitua asiakasta palauttamasta ohjainta takaisin toimintakuntoon ja aktivoimasta sen toimintoa. Älä koskaan luota poistettuun tai piilotettuun ohjaimeen estämään jotain tapahtumasta.

Aseta todellinen sääntö palvelinpuolen käsittelijään: varmista, että käyttäjällä on lupa ja ennakkoedellytykset täyttyvät ennen toiminnan suorittamista, aivan kuten tekisit, jos ohjain olisi ollut koko ajan käytössä. Poistettu tila ohjaa rehellisiä käyttäjiä; palvelinpuolen sääntö pysäyttää epärehelliset.

## Rajoita näkymiäsi {#lock-down-your-views}

Rajoita näkymiä [reittiturvalla](/docs/security/overview), jotta jokainen niistä vaatii oikean autentikoinnin ja roolit. Anna ihmisille kapein pääsy, joka mahdollistaa työn tekemisen, ja suosii oletusarvoista turvallista lähestymistapaa, jossa merkitsemätön reitti vaatii silti kirjautumisen.

## Pidä salaisuudet ulkopuolisina {#keep-secrets-external}

Käyttäjätunnukset, avaimet ja tunnukset eivät kuulu koodiin tai varastoon. Ota ne sijainnista tai ulkoisesta lähteestä, kuten on esitetty [Salaisuuksien hallinnassa](/docs/security/application-security/managing-secrets).

## Pysy ajan tasalla riippuvuuksista {#stay-current-on-dependencies}

Kirjastot, joita käytät, ovat suurempi riski kuin oma koodisi. Seuraa tiedotteita, päivitä webforJ ja muut riippuvuudet säännöllisesti, ja kun korjattu versio siirtyy siirtokirjastosta ennen kirjastoa, joka ottaa sen käyttöön, valitse korjattu versio `pom.xml`-tiedostossasi.

## Epäonnistu hiljaa {#fail-quietly}

Älä anna pinojäljitteiden, tiedostopolkujen tai sisäisten tunnisteiden näkyä loppukäyttäjille. Tallenna yksityiskohdat palvelinlokisi, ja esitä käyttöliittymässä pelkkä, yleinen viesti. Rekisteröi mukautettu käsittelijä webforJ:n [virheiden käsittelyyn](/docs/advanced/error-handling), jotta käsittelemättömät poikkeukset tuottavat hallitun sivun raakojen diagnoosien sijaan.

## Paljasta vastuullisesti {#disclose-responsibly}

Löysitkö mahdollisen virheen webforJ:stä? Ilmoita siitä yksityisesti GitHubin [yksityisen haavoittuvuuden raportoinnin](https://github.com/webforj/webforj/security/advisories) kautta sen sijaan, että avaat julkisen ongelman tai vetopyynnön, jotta korjaus voidaan saada käyttöön ennen kuin yksityiskohdat ovat tiedossa.
