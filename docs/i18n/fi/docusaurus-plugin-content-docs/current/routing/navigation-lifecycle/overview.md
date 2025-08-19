---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 6ed66a95c218f7a03552269dd824ffd8
---
Navigointi eri näkymien läpi web-sovelluksessa sisältää useita vaiheita, tarjoten mahdollisuuksia suorittaa toimintoja ennen, aikana tai jälkeen siirtymisen. Navigoinnin elinkaari tarjoaa tapahtumapohjaisen järjestelmän, joka mahdollistaa kehittäjille keskeisten näkökohtien hallinnan navigoinnista, kuten datan vahvistamisesta, pääsyn hallinnasta, käyttöliittymän päivittämisestä ja siivoustehtävien käsittelystä.

Tämä joustava järjestelmä varmistaa sujuvat, johdonmukaiset siirtymät antamalla kehittäjille mahdollisuuden liittää toimintoja kriittisiin kohtiin navigointiprosessissa. Olitpa sitten estämässä navigointia, hakemassa dataa, kun komponentti näytetään, tai hallinnoimassa tallentamattomia muutoksia, sinulla on täydellinen kontrolli navigointivirran yli elinkaaritapahtumien ja tarkkailijoiden kautta.

## Elinkaaritapahtumien yleiskatsaus {#lifecycle-events-overview}

Navigointiprosessia säätelee sarja tapahtumia, jotka laukaistaan ​​reitinvaihdosten aikana. Nämä tapahtumat antavat sinulle mahdollisuuden reagoida tietyissä elinkaaren kohdissa:

1. **WillEnter**: Laukaistaan ennen navigointia reitille ja ennen kuin sen komponentti liitetään DOM:iin. Tämä on ihanteellinen tehtäville, kuten todennus tarkistuksille tai navigoinnin estämiselle, jos tarpeen.
2. **DidEnter**: Laukaistaan navigoinnin jälkeen, ja komponentti on liitetty DOM:iin. Tämä tapahtuma sopii toimille, kuten datan hakemiseen, animaatioiden suorittamiseen tai keskittymiseen käyttöliittymän elementteihin.
3. **WillLeave**: Laukaistaan ennen kuin navigoidaan pois nykyiseltä reitiltä ja ennen kuin sen komponentti poistetaan DOM:ista. Se on hyödyllinen tallentamattoman datan hallintaan, käyttäjän vahvistuksen kysymiseen tai siivoustehtävien käsittelemiseen.
4. **DidLeave**: Laukaistaan siirryttäessä toiseen reittiin ja sen jälkeen, kun komponentti on poistettu DOM:ista. Tämä tapahtuma on ihanteellinen resurssien tyhjentämiseen tai käyttöliittymän palauttamiseen tulevaa käyttöä varten.

Nämä tapahtumat tarjoavat hienojakoista kontrollia navigointielinkaaren yli, mikä helpottaa monimutkaisten siirtymien hallintaa ja sujuvien vuorovaikutusten varmistamista reittien välillä.

## Aiheet {#topics}

<DocCardList className="topics-section" />
