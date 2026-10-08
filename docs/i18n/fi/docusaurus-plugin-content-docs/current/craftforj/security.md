---
title: Suojaus
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ lukee ja kirjoittaa projektin lähdekoodia, johon se on liitetty. Tämä sivu kuvaa, mitkä ovat rajat ja kuinka varmistaa, että craftforJ on pois päältä niissä rakennuksissa, joita julkaiset.

## Kaksi pakollista asetusta {#two-required-settings}

craftforJ vaatii, että molemmat seuraavista on käytössä:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Mikään näistä ei tee mitään yksinään. Sovellus, joka saavuttaa tuotannon, kun virheenkorjaustila on päällä, ei paljasta craftforJ:ta, ja sovellus, joka sisältää craftforJ-ominaisuuden jaettu konfigurointitiedostossa, ei paljasta sitä virheenkorjaustilan ulkopuolella.

Projektit, jotka on luotu [startforJ](https://docs.webforj.com/startforj) tai webforJ [archetype](/docs/building-ui/archetypes/overview) -mallista, ovat molemmat käytössä, joten craftforJ toimii ensimmäisestä käyttöönottokerrasta alkaen. Ennen kuin julkaiset, käy läpi [tuotantotarkistuslista](#in-production) alla.

## Paikallinen käyttö oletuksena {#local-access-by-default}

Vain selain, joka toimii sovellusta ajavassa koneessa, voi käyttää craftforJ:ta. Kaikki muu estetään, ja tämä pätee ilman mitään konfiguraatiota puoleltasi. Jotta voisit käyttää craftforJ:ta toiselta koneelta, nimeä kyseinen kone [`hosts-allowed`](/docs/craftforj/configuration#access) -asetuksessa. Osoitteet vertaillaan sellaisenaan, joten asiakas ei voi päästä läpi väittämällä olevansa jotain muuta.

:::warning Villikortti poistaa rajoituksen kokonaan
Asetus `hosts-allowed = "*"` tarkoittaa, että kuka tahansa, joka voi päästä sovelluksesi porttiin, voi lukea ja kirjoittaa projektisi lähdekoodin. Tämä on olemassa suljetuissa ympäristöissä, kuten säilössä, johon voit päästä vain sinä. Älä käytä sitä muualla.
:::

## Ei lisättyä HTTP-pintaa {#no-added-http-surface}

craftforJ ei lisää HTTP-päätepistettä, servletiä tai suodatinta sovellukseesi. Se toimii olemassa olevan yhteyden yli, joten sovelluksesi vastaa tarkalleen samaan pyyntöjen joukkoon craftforJ käytössä kuin ilman sitä.

## Pyyntöjä tulee sivultasi {#requests-come-from-your-page}

craftforJ reagoi vain pyyntöihin, jotka tulevat sivulta, jonka palvelimesi oikeasti palvelee. Skripti, joka löytää tiensä sivulle jostakin muualta, kuten vaarantuneesta riippuvuudesta tai konsoliin liitetystä, ei voi ohjata craftforJ:ta.

## API-avaimet {#api-keys}

Avaimesi tallennetaan koneelle, joka ajaa sovellustasi. [AI-avustaja](/docs/craftforj/ai) toimii selaimessa, joten craftforJ:n on annettava avain työskennelläkseen, ja se pitää avainta muistissa niin kauan kuin sivu on auki. Mikään ei tallenneta selainmuistiin, ja sivun sulkeminen ei jätä mitään jälkeensä.

Avustaja kommunikoi sitten palveluntarjoajasi kanssa selaimesta sen sijaan, että se menisi palvelimesi kautta. Ei ole välikättä, ei proxyä, ei telemetriaa ja ei kolmatta osapuolta välissä.

Mikä pääsee palveluntarjoajallesi, on itse keskustelu, joka sisältää osat sovelluksestasi, joita avustaja tarkasteli, ja kaikki kuvakaappaukset, joita se otti. Ota tämä huomioon ennen kuin osoitat isännöityä mallia sovellukseen, joka toimii oikeiden tietojen kanssa. Paikallisesti toimiva malli pitää kaiken koneellasi.

## Mitä craftforJ voi muuttaa {#what-craftforj-can-change}

Kun kaikki ominaisuudet ovat käytössä, craftforJ voi:

- Lukea minkä tahansa lähdetiedoston projektin juuressa
- Kirjoittaa Java-lähdetiedostoja, mukaan lukien reitin pääsyannotaatiot
- Kirjoittaa sovelluksesi tyylitiedoston
- Muuttaa ja poistaa komponentteja käynnissä olevasta sovelluksesta
- Navigoida käynnissä olevaa sovellusta

Jokainen näistä voidaan [sammuttaa](/docs/craftforj/configuration#feature-flags) erikseen, ja jokainen kirjoitus levylle tapahtuu läpi diffin, jonka hyväksyt.

## Tuotannossa {#in-production}

Jätä craftforJ pois päältä. Se on pois päältä, ellei olet sen päällä, joten useimmissa tapauksissa ei ole mitään tehtävää. Varmistaaksesi:

1. `webforj.devtools.craftforj.enabled` on asetettu tai `false` konfiguraatiossa, jonka oikeasti julkaiset.
2. `webforj.debug` on asetettu tai `false` samassa konfiguraatiossa.
3. Yhtään ominaisuutta ei aseteta ympäristömuuttujalla tai profiililla, joka koskee vain tuotantoa.
4. Lataa julkaistu sovellus ja varmista, ettei sivulla ole craftforJ-aktivointia.

Laajemman kuvan katsomiseksi, katso [Tuotannon kovettaminen](/docs/security/application-security/production-hardening).

## Turvallisuusongelman raportointi {#reporting-a-security-issue}

Jos löydät turvallisuusongelman craftforJ:ssa, raportoi se [webforJ:n turvallisuuspolitiikan](https://github.com/webforj/webforj/security) kautta sen sijaan, että tekisit siitä julkista asiaa.
