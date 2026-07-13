---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: 1b9e4b7fe64a9bcb0aa2aa16b0866ec9
---
Tehokkaat kehitysprosessit perustuvat työkaluihin, jotka havaitsevat koodimuutoksia ja päivittävät sovelluksen automaattisesti reaaliajassa. Jatkuva käyttöönottaminen ja dynaaminen uudelleenlataus toimivat yhdessä yksinkertaistaakseen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutoksesi nopeasti ilman, että sinun tarvitsee käynnistää palvelinta manuaalisesti uudelleen.

## Uudelleenluonti {#redeployment}

Uudelleenluonti Java-kehityksessä tarkoittaa koodimuutosten automaattista havaitsemista ja käyttöönottoa siten, että päivitykset näkyvät sovelluksessa ilman manuaalista palvelimen käynnistämistä. Tämä prosessi sisältää tyypillisesti Java-luokkien ja verkkoresurssien päivittämisen lennossa.

webforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston regeneroimista aina, kun koodia muokataan.

Muutoksia Java-luokkiin ja resursseihin luokkamatkalla seurataan tyypillisesti IDE:llä. Kun Java-luokkaa muokataan ja tiedosto tallennetaan, joko IDE:n automaattisesti tai kehittäjän manuaalisesti, nämä työkalut aktivoituvat koostamaan ja sijoittamaan päivitetyt luokkakentät kohdekansioon muutosten soveltamiseksi.

Parhaan käyttökokemuksen saavuttamiseksi käytä automaattista uudelleenlatausta yhdessä työkalujen tai asetusten kanssa, jotka automatisoivat selaimen lataamisen.

## Reaalilataus {#live-reload}

Kun muutokset on otettu käyttöön, reaaliuudelleenlataus latautuu automaattisesti sovelluksen niin, että selain heijastaa päivityksiä heti, ilman että manuaalista selaimen uudelleenlatausta tarvitaan.

webforJ-sovelluksessa reaaliuudelleenlataus voi automaattisesti päivittää näkymän, renderöidä komponentit uudelleen näyttämään sovelluksen viimeisintä tilaa, tai jopa korjata muutoksia tarpeen mukaan pyynnöstä.

Frontend-lähteille [frontend watch](/docs/configuration/deploy-reload/frontend-watch) uudelleenrakentaa jokaisen muutoksen yhteydessä ja päivittää tyylitiedoston tai kuvan paikan päällä, lataamalla näkymä uudelleen vain, kun skripti muuttuu.

## Aiheet {#topics}

<DocCardList className="topics-section" />
