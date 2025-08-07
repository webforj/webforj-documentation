---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: ef15124e21d87b0b23f9b1acae9228a8
---
Navigointi eri näkymien läpi verkkosovelluksessa koostuu useista vaiheista, jotka tarjoavat mahdollisuuksia suorittaa toimintoja ennen, aikana tai jälkeen siirtymisen. Navigointielämänkaari tarjoaa tapahtumapohjaisen järjestelmän, joka antaa kehittäjille mahdollisuuden hallita keskeisiä navigoinnin näkökohtia, kuten datan validoimista, käyttöoikeuksien hallintaa, käyttöliittymän päivittämistä ja siivoustehtävien hoitamista.

Tämä joustava järjestelmä varmistaa sujuvat ja johdonmukaiset siirtymät, koska se antaa kehittäjille mahdollisuuden liittää toimintoja kriittisiin kohtiin navigointiprosessissa. Olitpa sitten estämässä navigointia, hakemassa dataa komponentin näyttämisen yhteydessä tai hallitsemassa tallentamattomia muutoksia, sinulla on täysi hallinta navigointivirtaasi sen elämänkaaren tapahtumien ja tarkkailijoiden kautta.

## Elämänkaari tapahtumien yleiskatsaus {#lifecycle-events-overview}

Navigointiprosessia ohjaavat sarja tapahtumia, jotka laukaistaan reittisiirtymien aikana. Nämä tapahtumat mahdollistavat reagoinnin tietyissä pisteissä elämänkaarella:

1. **WillEnter**: Laikaistaan ennen reitille siirtymistä ja ennen kuin sen komponentti liitetään DOM:iin. Tämä on ihanteellinen tehtäville, kuten todennustarkistuksille tai navigoinnin estämiselle, jos tarpeen.
2. **DidEnter**: Laikaistaan sen jälkeen, kun navigointi on saatu päätökseen ja komponentti on liitetty DOM:iin. Tämä tapahtuma soveltuu toimille, kuten datan hakemiseen, animaatioiden suorittamiseen tai keskittymiseen käyttöliittymän elementteihin.
3. **WillLeave**: Laikaistaan ennen kuin siirrytään pois nykyiseltä reitiltä ja ennen kuin sen komponentti poistetaan DOM:ista. Se on hyödyllinen tallentamattoman datan hallinnassa, käyttäjältä vahvistuksen kysymisessä tai siivoustehtävien hoitamisessa.
4. **DidLeave**: Laikaistaan sen jälkeen, kun on siirrytty toiseen reittiin ja sen jälkeen, kun komponentti on poistettu DOM:ista. Tämä tapahtuma on ihanteellinen resurssien tyhjentämiseen tai käyttöliittymän nollaamiseen tulevaa käyttöä varten.

Nämä tapahtumat tarjoavat yksityiskohtaista hallintaa navigointielämänkaaresta, mikä helpottaa monimutkaisten siirtymien hallintaa ja sujuvien vuorovaikutusten varmistamista reittien välillä.

## Aiheet {#topics}

<DocCardList className="topics-section" />
