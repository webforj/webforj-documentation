---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
Tehokkaat kehitystyönkulut perustuvat työkaluihin, jotka havaitsevat koodin muutokset ja päivittävät sovelluksen automaattisesti reaaliajassa. Jatkuva käyttöönotto ja dynaaminen lataus toimivat yhdessä yksinkertaistaakseen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutoksesi nopeasti ilman, että palvelinta tarvitsee käynnistää manuaalisesti uudelleen.

## Uudelleenkäynnistys {#redeployment}

Uudelleenkäynnistys Java-kehityksessä tarkoittaa koodin muutosten automaattista havaitsemista ja käyttöönottoa, jotta päivitykset näkyvät sovelluksessa ilman manuaalista palvelimen uudelleenkäynnistystä. Tämä prosessi sisältää tyypillisesti Java-luokkien ja verkkoresurssien päivittämisen lennossa.

webforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston uudelleensuunnittelua aina, kun koodiin tehdään muutoksia.

Muutokset Java-luokkiin ja resurssit, jotka ovat luokkateissä, havaitaan tyypillisesti IDE:n toimesta. Kun Java-luokkaa muokataan ja tiedosto tallennetaan, joko IDE:n automaattisesti tai kehittäjän manuaalisesti, nämä työkalut aktivoituvat kääntämään ja sijoittamaan päivitetyt luokk tiedostot kohdekansioon muutosten soveltamiseksi.

Parhaan käyttökokemuksen saavuttamiseksi käytä automaattista uudelleenkäynnistystä yhdessä työkalujen tai asetusten kanssa, jotka automatisoivat selaimen lataamisen.

## Reaaliaikainen lataus {#live-reload}

Kun muutokset on otettu käyttöön, reaaliaikainen lataus lataa sovelluksen automaattisesti, jotta selain heijastaa päivitykset välittömästi ilman, että manuaalista selainpäivitystä tarvitaan.

webforJ-sovelluksessa reaaliaikainen lataus voi automaattisesti päivittää näkymän, uudelleenrenderöimällä komponentteja näyttääkseen sovelluksen viimeisimmän tilan tai jopa paikkaamaan muutoksia tarvittaessa kysynnä.

Frontend-lähteille [frontend watch](/docs/configuration/deploy-reload/frontend-watch) rakennetaan jokaisella muutoksella ja paikkaa tyylitiedoston tai kuvan paikallaan, lataamalla näkymä vain, kun skripti muuttuu.

## Aiheet {#topics}

<DocCardList className="topics-section" />
