---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 14d81d1a9ff86af17370e0a7eb50608b
---
Navigointi eri näkymien välillä web-sovelluksessa sisältää useita vaiheita, jotka tarjoavat mahdollisuuksia suorittaa toimintoja ennen, aikana tai jälkeen siirtymän. Navigointisyklillä on tapahtumapohjainen järjestelmä, joka antaa kehittäjille mahdollisuuden hallita navigoinnin keskeisiä näkökohtia, kuten tietojen validoimista, pääsyn hallintaa, käyttöliittymän päivittämistä ja siivoustöiden käsittelyä.

Tämä joustava järjestelmä antaa kehittäjille mahdollisuuden hallita siirtymiä erikseen kytkemällä kriittisiin kohtiin navigointiprosessissa. Olipa tarpeesi estää navigointi, ladata tietoja, kun komponentti näytetään, tai hallita tallentamattomia muutoksia, sinulla on täysi hallinta navigointivirtaan sen elinkaaritapahtumien ja havainnoijien kautta.

## Elinkaaritapahtumien yleiskatsaus {#lifecycle-events-overview}

Navigointiprosessia ohjaa joukko tapahtumia, jotka laukaistaan reittisiirtymien aikana. Nämä tapahtumat mahdollistavat reagoimisen tietyissä elinkaaren kohdissa:

1. **WillEnter**: Laukaistaan ennen siirtymistä reitille ja ennen kuin sen komponentti liitetään DOM:iin. Tämä on ihanteellinen tehtäville, kuten todennustarkistuksille tai navigoinnin estämiselle, jos tarpeen.
2. **DidEnter**: Laukaistaan siirtymisen jälkeen ja komponentin liittämisen jälkeen DOM:iin. Tämä tapahtuma sopii toimiin, kuten tietojen hakemiseen, animaatioiden suorittamiseen tai käyttöliittymäelementtien tarkennukseen.
3. **WillLeave**: Laukaistaan ennen siirtymistä nykyiseltä reitiltä ja ennen kuin sen komponentti poistetaan DOM:ista. Se on hyödyllinen tallentamattomien tietojen hallintaan, käyttäjältä vahvistuksen pyytämiseen tai siivoustöiden käsittelyyn.
4. **DidLeave**: Laukaistaan siirryttäessä toiseen reittiin ja komponentin poistamisen jälkeen DOM:ista. Tämä tapahtuma on ihanteellinen resurssien tyhjentämiseen tai käyttöliittymän palauttamiseen tulevaa käyttöä varten.
5. **Activate** (desde `25.03`): Laukaistaan, kun välimuistit käytetään uudelleen luomisen sijaan. Tämä tapahtuu, kun siirrytään samaan reittiin eri parametreilla tai palataan aiemmin vieraillulle reitille. Tapahtuma laukaistaan kaikille välimuistissa oleville komponenteille reittihierarkiassa, jotka pysyvät nykyisellä polulla, mahdollistaen sekä vanhempien asettelujen että lastenkomponenttien päivittää tietojaan tai päivittää käyttöliittymäänsä uusien parametrien perusteella säilyttäen samalla komponentin tilan.

Nämä tapahtumat tarjoavat yksityiskohtaista kontrollia navigointielinkaaresta, mahdollistaen kehittäjien koordinoida tietojen validointia, käyttöliittymän päivityksiä ja resurssien hallintaa reittisiirtymien aikana.

## Aihealueet {#topics}

<DocCardList className="topics-section" />
