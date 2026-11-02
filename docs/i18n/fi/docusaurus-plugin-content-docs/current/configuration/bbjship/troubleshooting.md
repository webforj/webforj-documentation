---
title: Vianetsintä
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# Vian ratkaisu <DocChip chip='since' label='26.03' /> {#troubleshooting}

Tällä sivulla luetellaan viestit, joita voit kohdata, kun asetat BBjShipiä, julkaiset sovellusta, aloitat kehityskäynnin tai käytät `bbjship` komentoriviä. Jokaiselle viestille annetaan syy ja mitä tehdä.

Epäonnistuva Maven-tavoite lopettaa rakennuksen lyhyellä viestillä tavoitteesta seuraavaksi syyn. Syy on se osa, jonka tämä sivu luettelee:

```text
BBj julkaisu epäonnistui. Tarkista sovelluspaketti, riippuvuudet ja julkaisuasetukset: BBj julkaisu epäonnistui HTTP 409:llä. Entry class com.example.Application on jo julkaistu inventaariona. Peruuta inventaario ensin
```

## Käyttäjätunnukset ja yhteys {#credentials-and-connection}

Nämä viestit tulevat kaikista tavoitteista, jotka kommunikoivat BBjShipin kanssa: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` ja `watch`.

| Viesti | Syy | Mitä tehdä |
|---|---|---|
| `Varoitus: BBj-tehtaan pääkäyttäjätili admin on käytössä tehdas salasanallaan...` | Käyttäjätunnusta tai salasanaa ei ole määritetty, joten tavoite kirjaa sisään `admin`-tilille salasanalla `admin123`. | Vaihda salasana Enterprise Managerissa ja määritä uudet tunnukset, katso [Asetukset](/docs/configuration/bbjship/deploying#settings). |
| `BBj hallintakäyttäjänimeä ei ole` `BBj hallintasalasanaa ei ole` | Vain yksi käyttäjätunnus tai salasana on määritetty. | Määritä molemmat tai kumpikaan käyttääksesi tehdastiliä. `bbj-deploy` ja `bbj-undeploy` hyväksyvät myös tokenin. |
| `BBj julkaisu vaatii bbj.deployment.url...` | Julkaisun URL-osoitetta ei ole määritetty. | Aseta `<bbj><deployment><url>` tai siirrä `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj julkaisu URL:n on oltava HTTP tai HTTPS-URL ilman tunnuksia, kyselyä tai fragmenttia` | URL-osoitteessa on toinen protokolla tai se sisältää käyttäjätunnuksen, kyselyn tai fragmentin. | Käytä `http://<host>:<port>/webforj-bbjship` tai `https://<host>:<port>/webforj-bbjship`, ja laita tunnukset `<admin>`-elementtiin. |
| `Yhteys ... epäonnistui: Yhteyttä ei hyväksytty` | Mikään ei vastaa julkaisun URL:n isäntä- ja porttiosoitteessa. | Käynnistä BBjServices ja tarkista URL:n isäntä ja portti BBjServicesin web-palvelimen kanssa. |
| `ei voitu lukea BBjShip-versiona <URL>, HTTP 404...` | URL ei johda BBjShipiin tai BBjShipiä ei ole asennettu tai sen päätepistettä ei ole käytössä kyseisellä portilla. | Tarkista polku `/webforj-bbjship` ja portti. BBj-koneella suorita `mvn webforj:bbj-setup` tai `bbjship remote enable` palvelimella. |
| `epäonnistui HTTP 401. Tarkista BBj hallintatunnukset tai token` | BBjServices hylkäsi käyttäjätunnuksen ja salasanan tai tokenin. | Tarkista tunnukset. Uudista vanhentunut tai peruutettu token Enterprise Managerissa. |
| `BBjShip <versio> <URL> ei vastaa webforJ <versio>...` | Asennettu BBjShip ja `webforj-maven-plugin` ovat eri versioita. | Suorita omalla koneellasi `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Palvelimella asenna yhteensopiva BBjShip BBj Plugin Managerin avulla. |
| `ei voitu luoda luotettavaa HTTPS-yhteyttä <URL>...` | Java ei luota BBjServicesin web-palvelimen sertifikaattiin. | Asenna sertifikaatti URL:n isäntänimelle BBj-koneelle tai lisää sen sertifikaatti Java-turvavarastoon. |
| `BBj julkaisu URL <URL> lähettää hallintatunnukset luettavissa verkossa...` | Varoitus `http://`-URL:lle muuhun koneeseen. | Käytä `https://`-URL:ia SSL-portilla, katso [Julkaisu toiselta koneelta](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Asetus {#setup}

Nämä viestit tulevat `mvn webforj:bbj-setup`:stä ja ensimmäisestä [kehityskäynnistä](/docs/configuration/deploy-reload/bbjservices) projektista, joka asettaa BBjShipin samalla tavalla.

| Viesti | Syy | Mitä tehdä |
|---|---|---|
| `BBj asennus epäonnistui. Tarkista BBj-laajennusartifact ja asennusasetukset` | Tavoite epäonnistui. Syyn jälkeen seuraa syy. Syy artifactin resoluutiosta tarkoittaa sitä, että Maven ei voinut ladata `webforj-bbjship-plugin` versiota `webforj-maven-plugin`:ista. | Lue syy. Artifactin resoluutiota varten tarkista varastot ja rakennuksen verkkoyhteys. |
| `BBj asennus on vain paikallinen. Suorita asennus BBj-isännällä` | Julkaisun URL osoittaa toiseen koneeseen. | Suorita asennus BBj-koneella tai asenna BBjShip sinne [BBj Plugin Managerin](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager) avulla. |
| `BBj asennus ei voi ratkaista julkaisun URL-isäntä <host>...` | Julkaisun URL-osoitteen isäntänimi ei ole tunnettu. | Käytä `localhost` tai tämän koneen nimeä. |
| `BBj asennus vaatii hallintasalasanan. Määritä bbj.admin.password tokenin sijaan` | Kehityskäynnillä on määritetty token. Kehityskäynnit kirjautuvat sisään vain käyttäjätunnuksella ja salasanalla. | Määritä `<admin><username>` ja `<admin><password>`. |
| `BBj asennusta ei voida havaita. Määritä bbj.home tai BBJ_HOME` | Tavoite ei löytänyt BBj-asennusta tältä koneelta. | Aseta `<bbj><home>`, `-Dwebforj.bbj.home` tai `BBJ_HOME` muuttuja BBj-asennushakemistoon. |
| `Virheellinen BBj-koti. Määritä paikallinen BBj-asennus`, `BBj suoritettava tiedosto puuttuu tai ei ole suoritettavissa. Tarkista bbj.home` | Määritetty kansio ei ole täydellinen BBj-asennus. | Suuntaa asetukset kansioon, joka sisältää `bin` ja `cfg` BBj:stä. |
| `Asennettu BBjShip-versio <versio> ei vastaa <versio>...` | Toinen BBjShip-versio on asennettuna, esimerkiksi webforJ-päivityksen jälkeen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Julkaistut sovellukset pysyvät julkaistuna. |
| `Asennettu BBjShip-versio puuttuu...` | Asennettu BBjShip-kansio on puutteellinen. | Suorita `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `Laajennushakemisto on symbolinen linkki...`, `BBj laajennuskohde ei saa sisältää symboleja` | BBj:n `plugins`-kansio tai BBjShip-kansio siinä on symbolinen linkki tai sisältää sellaisen. | Asenna tavalliseen kansioon. |
| `BBj asennuskomento epäonnistui. Tarkista tunnukset ja paikalliset BBjServices.` ja seuraavat komennon tulokset | Komento, joka mahdollistaa julkaisupisteen, epäonnistui. Tulokset nimeävät syyn, kuten epääntynyt kirjautuminen tai portti, johon web-palvelin ei kuuntele. | Tarkista tunnukset, että BBjServices toimii ja että julkaisun URL:n portti on BBjServices web-palvelimen portti. |
| `BBj asennuskomento aikakatkaistiin. Tarkista paikalliset BBjServices` | Komento ei valmistunut 120 sekunnissa. | Tarkista, että BBjServices toimii ja vastaa, ja suorita sitten tavoite uudelleen. |
| `Aiemmat BBjShip-tiedostot on säilytetty kansiossa <folder>` | Vaihto epäonnistui ja aikaisemman version tiedostot pidettiin tuossa kansiossa. | Korjaa epäonnistumisen syy ja suorita tavoite uudelleen. |

## Julkaisu ja peruuttaminen {#deploy-and-undeploy}

### Ennen latausta {#before-the-upload}

Nämä viestit tulevat `bbj-package`, `bbj-deploy` ja `bbj-undeploy` ennen kuin mikään saavuttaa BBjShipin.

| Viesti | Syy | Mitä tehdä |
|---|---|---|
| `BBj julkaisu vaatii jar-paketointia, tämä projekti on paketoitu muodossa <paketti>` | Projekti ei ole paketoitu jar-muotoon. | Aseta `<packaging>jar</packaging>`. |
| `BBj julkaisu vaatii paketoidun sovellusjarin...` | Tavoite suoritettiin ilman `package`-vaihetta samassa rakennuksessa. | Suorita `mvn install` tai `mvn package webforj:bbj-deploy`. |
| `BBj julkaisu ei saa sisältää webforj-engineä, BBj on moottori...` | `webforj-engine` on sovelluksen suoritusajan luetteloissa. | Poista se `webforj`-riippuvuudesta, katso [Ilman asennusliitintä](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj julkaisu vaatii webforj-foundationin suoritusajan luetteloissa` | Sovelluksessa ei ole webforJ-riippuvuutta suoritusajassa. | Lisää `webforj`-riippuvuus `compile`-laajuudessa. |
| `BBj julkaisu riippuvuus on ratkaisematon...` | Sovelluksen riippuvaisuudella ei ole tiedostoa. | Tarkista, että Maven ratkaisee kaikki projektin riippuvuudet. |
| `Julkaisun nimi <name> on virheellinen...` | Julkaisutunnus rikkoo sääntöjä [Julkaisun nimille](/docs/configuration/bbjship/deploying#publish-names). | Valitse toinen `<publishName>`. |
| `Julkaisun nimi <name> on BBjServicesin varaama sen omille web-osoitteille...` | BBjServices käyttää tätä nimeä omille osoitteilleen. | Valitse toinen `<publishName>`. |
| `BBj julkaisuarkistoa ei voitu kirjoittaa...` | `bbj-package` ei voinut kirjoittaa tiedostoon `target/bbj/<publish name>.zip`. Syy seuraa. | Lue syy. |

### BBjShipin vastaukset {#answers-of-bbjship}

Kun BBjShip hylkää julkaisu- tai peruuttamishakemuksen, viesti alkaa `BBj julkaisu epäonnistui HTTP <status>` tai `BBj peruuttaminen epäonnistui HTTP <status>`, jonka jälkeen on syy.

| Tila | Syynä | Syy | Mitä tehdä |
|---|---|---|---|
| 400 | `Julkaisun nimi <name> on virheellinen...` tai `...on BBjServicesin varaama...` | Julkaisun nimi hylättiin. | Valitse toinen julkaisutunnus. |
| 400 | `BBj julkaisu webforj.conf on virheellinen. <detail>` | Sovelluksen `webforj.conf`-tiedostoa ei voida lukea, tai jokin [sovellusasetuksista](/docs/configuration/bbjship/deploying#app-settings) on väärän tyyppinen. | Korjaa tiedosto niin kuin yksityiskohtaisessa viestissä mainitaan. |
| 400 | `Ladattu sisältö ei ole BBj julkaisuarkisto...`, `Virheellinen BBj julkaisuarkiston merkintä: <entry>` tai jokin muu syy arkistolle | Arkistoa ei kirjoitettu rakennusliittimestä tai se muutettiin jälkikäteen. | Julkaise arkisto, jonka rakennus kirjoittaa, samalla webforJ-versiolla kuin BBjShip. |
| 401 | `Tarkista BBj hallintatunnukset tai token` | Kirjautuminen evättiin. | Tarkista tunnukset tai uusi token. |
| 403 | `Kehityskäynnit eivät ole käytössä tällä BBj-koneella. Suorita bbjship development enable` | Kehityskäynti saavutti koneen, jossa kehityskäynnit ovat pois päältä. | Suorita `bbjship development enable` BBj-koneella. |
| 404 | `BBj sovellusta nimeltä <name> ei olla julkaistu BBjShipin toimesta. Tarkista julkaisun nimi` | Peruuttaminen nimesi sovelluksen, jota BBjShip ei julkaissut. | Tarkista julkaisun nimi `bbjship apps list` -komennolla. |
| 409 | `Entry class <class> on jo julkaistu nimellä <name>. Peruuta <name> ensin` | Sovelluksen entry class on jo käynnissä toisen julkaisun nimen alla. | Peruuta toinen sovellus tai julkaise sen nimellä. |
| 409 | `Julkaistu BBj-sovellus <name> ei ole BBjShipin omistama...` | Enterprise Managerissa rekisteröity sovellus käyttää jo nimeä. | Valitse toinen julkaisun nimi tai poista se Enterprise Managerissa. |
| 409 | `Julkaistu BBj-sovellus <other> eroaa <name> vain kirjainkooltaan...` | Toinen sovellus käyttää samaa nimeä eri kirjainkoossa. | Valitse toinen julkaisun nimi. |
| 409 | `<name> on kehityskäynti. Pysäytä seuraaminen ensin` | Kehityskäynti sovelluksesta on aktiivinen. | Pysäytä seuraaminen `Ctrl+C`-näppäimellä, sitten julkaise. |
| 409 | `<name> on julkaistu arkistosta. Peruuta se ensin` | Kehityskäynti käyttää julkaistun sovelluksen nimeä. | Peruuta sovellus tai määritä toinen `<publishName>` kehityskäynnille. |
| 413 | `BBj julkaisuarkisto ylittää sallitun koon 1 GiB` | Arkisto on suurempi kuin BBjShip hyväksyy. | Poista sovelluksen tarpeettomat riippuvuudet suoritusaikana. |
| 500 | `BBj julkaisu epäonnistui. Tarkista BBjServicesin loki` | BBjServices epäonnistui, kun se asensi tai poistui sovelluksesta. | Lue BBjServicesin lokit. |

Julkaisu, joka päättyy `ei vahvistanut pyydettyä sovellusta. Tarkista BBjShipin versio ja BBjServicesin loki`, tavoitti BBjShipin, mutta ei saanut vahvistusta sovelluksesta. Tarkista, että BBjShip ja `webforj-maven-plugin` ovat samassa versiossa, ja lue sitten BBjServicesin loki.

### Suorittava sovellus {#the-running-app}

| Viesti | Syy | Mitä tehdä |
|---|---|---|
| `Ikonien protokolla vaatii webforj.assetsSource = paketoitu käytettäessä BBj Servicesia` | Sovellus käyttää `icons://` ilman paketoituja resursseja. | Lisää `webforj.assetsSource = paketoitu` tiedostoon `webforj.conf` ja julkaise uudelleen. |

## Kehityskäynnit {#development-runs}

Nämä viestit tulevat `mvn` tai `mvn compile webforj:watch` projektissa, joka on määritetty BBjShipille. Epäonnistuva kehityskäynti alkaa `BBj kehityskäynti epäonnistui. Tarkista julkaisuasetukset ja että kehityskäynnit ovat käytössä BBj-koneella`, jonka jälkeen seuraa syy [Asetuksesta](#setup), [Käyttäjätunnuksista ja yhteydestä](#credentials-and-connection) tai [BBjShipin vastauksista](#answers-of-bbjship).

| Viesti | Syy | Mitä tehdä |
|---|---|---|
| `Luokkakansio <folder> ei ole olemassa. Suorita komento ennen webforj:watchia` | Seuranta alkoi ennen kuin projekti koottiin. | Suorita `mvn compile webforj:watch` tai `mvn` arkkityypin projektissa. |
| `BBj kehitysluokkakansion on oltava absoluuttinen hakemisto BBj-koneella` | `<development><classFolder>` viittaa kansioon, jota BBjServices ei voi löytää. | Aseta luokkakansion absoluuttinen polku BBj-koneelle. |
| `BBjServicesin asetustiedostoa ei löytynyt <file>. Määritä bbj.properties` | BBjServicesin asetustiedosto ei ole siellä, mihin käynti katsoo. | Aseta `<bbj><properties>` tai `-Dwebforj.bbj.properties` tiedostolle. |
| `BBjServices ei pysähtynyt 180 sekunnissa` | Käynti pyysi BBjServicesia pysähtymään soveltaakseen hot-swap-asetuksia, ja se jatkoi ajamista. | Pysäytä BBjServices itse, käynnistä se uudelleen ja suorita seuraaminen jälleen. |
| `BBjServicesin käynnistysohjelma puuttuu: <program>` | BBj-asennuksessa ei ole ohjelmaa BBjServicesin käynnistämiseksi. | Käynnistä BBjServices itse ja suorita seuraaminen uudelleen. |
| `BBjServices ei käynnistynyt. Ohjelma päättyi poistokoodilla <code>`, `BBjServices ei käynnistynyt 120 sekunnissa` | BBjServices ei palannut uudelleenkäynnistyksen jälkeen. | Käynnistä BBjServices itse ja suorita sitten seuraaminen uudelleen. |
| `BBjShip ei vastannut 120 sekunnissa` | BBjServices käynnistyi, mutta julkaisupiste ei vastannut. | Tarkista BBjServicesin loki ja suorita sitten seuraaminen uudelleen. |
| `BBjServices käynnistyi ilman hot-swap-asetuksia. Tarkista <file>` | BBjServices käynnistyi Java-argumenttien kanssa, jotka poikkeavat asetustiedostoon kirjoitetuista. | Tarkista BBjServicesin Java-asetukset Enterprise Managerissa ja kyseisessä tiedostossa. |
| `webforJ watch: kehityskäynti <name> ei pysähtynyt. <reason>` | Sovellusta ei voitu poistaa, kun rakennus pysähtyi. | Poista se `bbjship apps undeploy <name>`-komennolla, tai käynnistä seuraaminen uudelleen, mikä korvasi sen. |

Ensimmäinen kehityskäynti, joka lisää hot-swap-asetukset, käynnistää BBjServicesin. Se käynnistää BBjServicesin `bin/bbjservices` macOS:llä ja Linuxilla. Windowsissa se käynnistää `BBjServices` Windows-palvelun, kun yksi on rekisteröity, ja ajaa `bin\BBjServices.exe -noservice` muuten. BBjServices, jonka `bin/bbjservices` tai `-noservice` on käynnistänyt, toimii käyttöjärjestelmän palvelunhallinnan ulkopuolella, kunnes kone käynnistetään uudelleen.

## Komentorivi {#command-line}

Jokainen `bbjship` komento suoritetaan BBjServicesin sisällä, joten BBjServicesin on oltava käynnissä. Ilman sitä komentoa ei voida käynnistää.

Virheelliset argumentit päättyvät poistokoodiin `2` ja viestiin, jossa kerrotaan, mitä ilmoittaa:

| Viesti | Mitä tehdä |
|---|---|
| `Määritä komento. Käytä --help saatavilla olevista komennoista`, `Tuntematon komento <command>...` | Nimeä komento, katso [Komentorivi](/docs/configuration/bbjship/command-line). |
| `Tuntematon vaihtoehto <option>...`, `Vaihtoehto <option> ei ole saatavilla komennolle <command>...` | Käytä vaihtoehtoa kyseiselle komennolle, kuten `--help` luettelee ne. |
| `Määritä käyttäjänimi --username jälkeen`, `Määritä salasana --password jälkeen`, `Määritä portti --port jälkeen` | Anna vaihtoehdolle arvo. |
| `Määritä --port arvot 1 ja 65535`, `Määritä --ssl-port arvot 1 ja 65535` | Anna porttinumero. |
| `Määritä <option> vain kerran` | Poista toistuva vaihtoehto. |
| `Määritä vain yksi --password ja --password-stdin` | Säilytä yksi kahdesta. |
| `BBj hallintasalasana puuttuu. Määritä --username ja --password <value> tai --password-stdin, tai aseta WEBFORJ_BBJ_USERNAME ja WEBFORJ_BBJ_PASSWORD` | Anna käyttäjänimi ja salasana yhdessä. Viesti mainitsee puuttuvan. |
| `Määritä sovelluksen nimi tai --all`, `Määritä yksi sovelluksen nimi` | Anna `apps undeploy`-komennolle yksi julkaisun nimi tai `--all`. |
| `Määritä julkaisuarvo, joka päättyy .zip`, `Määritä yksi julkaisuarvio` | Anna `apps deploy`-komennolle yksi `.zip`-tiedosto. |
| `Julkaisuarkisto <path> ei ole olemassa`, `Julkaisuarkisto <path> ei voi olla luettavissa` | Tarkista polku. BBjServices lukee tiedoston, joten BBjServicesin käyttöjärjestelmän käyttäjällä on oltava lukuoikeus siihen. |
| `Kehitysarkistot julkaistaan watchin, eivät apps deploy` | Julkaise `bbj-package` arkisto, ei kehityskäynnin omaa arkistoa. |
| `Julkaisun nimi <name> on virheellinen...`, `Julkaisun nimi <name> on varattu...` | Nimeä arkisto uudelleen. Sen tiedostonimi ilman `.zip` on julkaisun nimi. |

Komento, joka epäonnistuu, päättyy poistokoodiin `1` ja `Komento epäonnistui.` seuraavalla syyllä:

| Syy | Syy | Mitä tehdä |
|---|---|---|
| BBjServicesin kirjautumisen epäonnistuminen | Käyttäjätunnus tai salasana on väärin. | Tarkista tunnukset. |
| `BBjServicesin web-palvelin ei vastaa portissa <port>. Käytä BBjServicesin web-palvelimen HTTP-porttia` | `--port` tai `--ssl-port` ei ole portti, johon BBjServicesin web-palvelin kuuntelee. Edellinen päätepiste pysyy. | Käytä Enterprise Managerissa määritettyjä portteja. |
| `Sovellus <name> ei ole julkaistu BBjShipin toimesta` | `apps undeploy` nimitti sovelluksen, jota BBjShip ei julkaissut. | Tarkista nimi `bbjship apps list`-komennolla. |
| `Toinen julkaisu on käynnissä` | Toinen julkaisu tai peruuttaminen varasi koneen 30 sekunniksi. | Odota, että se valmistuu ja suorita komento uudelleen. |
| `BBjShipin konteksti kuuluu toiseen sovellukseen`, `Resurssien konteksti kuuluu toiseen sovellukseen` | Toinen sovellus Enterprise Managerissa käyttää web-kontekstia `webforj-bbjship` tai `webforj-bbjship-assets`. | Nimeä tai poista se konteksti Enterprise Managerissa. |
| `BBjShipin komento luokkareunat kuuluvat toiseen sovellukseen`, `BBjShipin perusluokkareunat sisältävät omistamattoman kirjaston` | Luokkareunat `_webforj_bbjship_cli` tai `_webforj_bbjship` Enterprise Managerissa on muutettu. | Poista molemmat luokkareunat Enterprise Managerissa ja suorita komento uudelleen, mikä luo ne uudelleen. |
| `BBjShipin servletti puuttuu. Tarkista liitännäinen asennus`, `BBjShipin kirjasto puuttuu. Tarkista liitännäinen asennus` | BBjShip-kansio on puutteellinen. | Asenna BBjShip uudelleen. |
| `BBjServices ei hallitse web-konteksteja. Päivitä BBj` | BBj-versio on liian vanha BBjShipille. | Asenna BBj-versio, joka vastaa webforJ:ta. |

`reset` päättyy poistokoodiin `1` ja `Nollaus peruttiin`, kun vastaus kysymykseen `Jatketaanko? [y/N]` ei ole `y` tai `Y`. Kun se palautti BBjServicesin Java-asetukset, se päättyy viestiin `Käynnistä BBjServices nyt, jotta voit soveltaa palautettuja Java-asetuksia`. Käynnistä BBjServices toimiakseen ilman hot-swap-asetuksia.

## Windows {#windows}

| Tilanne | Mitä tehdä |
|---|---|
| Suorittamalla `bbj.exe` BBjShip-ohjelmalla ei tulosteta mitään. | Suorita `bbjship.cmd` hakemistossa `<BBj home>\plugins\webforj-bbjship`, joka näyttää komennon ulostulon. |
| `--password-stdin` päättyy poistokoodiin `2` ja `Standardi syöttö ei ole saatavilla Windowsissa. Käytä -salasanaa <arvo> tai WEBFORJ_BBJ_PASSWORD`. | Anna salasana `--password`-vaihtoehdolla tai määritä `WEBFORJ_BBJ_USERNAME` ja `WEBFORJ_BBJ_PASSWORD`. |
| `reset` päättyy poistokoodiin `2` ja `Standardi syöttö ei ole saatavilla Windowsissa. Suorita reset --yes vahvistaaksesi`. | Suorita `bbjship.cmd reset --yes`. |
| `apps undeploy` tai `reset` tulostaa `Ei poistettu: <folder>. <reason>` ja päättyy poistokoodiin `0`. | BBjServices pitää yhä sovelluksen tiedostoja auki. Sovellus on poistettu Enterprise Managerista. Käynnistä BBjServices uudelleen. Kansio poistuu seuraavasta julkaisuista or poista se käynnistyksen jälkeen. |
| BBjShipin asentaminen asennetun version päälle epäonnistuu, koska sen tiedostot ovat käytössä. | BBjServices pitää nyt asennettujen BBjShipin tiedostoja auki. Käynnistä BBjServices uudelleen ja asenna sitten uudestaan. |
| BBjServices ei käynnisty Windows-palveluna kehityskäynnin uudelleenkäynnistyksen jälkeen. | Kehityskäynti käynnisti sen `BBjServices.exe -noservice` koska `BBjServices`-palvelua ei ole rekisteröity. Se toimii Windows-palvelunhallinnan ulkopuolella kunnes kone käynnistyy uudelleen. |
| `bbjship.cmd` komento ei voi käynnistyä. | Käynnistä BBjServices. Jokainen `bbjship` komento tarvitsee sen olevan käynnissä. |
