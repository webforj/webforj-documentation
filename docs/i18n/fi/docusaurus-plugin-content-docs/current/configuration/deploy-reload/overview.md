---
title: Redeployointi ja Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
Tehokkaat kehitystyöskentelyt nojaavat työkaluihin, jotka havaitsevat koodimuutoksia ja päivittävät sovelluksen automaattisesti reaaliajassa. Jatkuva käyttöönotto ja dynaaminen uudelleenlataus toimivat yhdessä yksinkertaistaen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutokset nopeasti ilman, että sinun tarvitsee käynnistää palvelinta manuaalisesti.

## Uudelleenversiointi {#redeployment}

Uudelleenversiointi Java-kehityksessä tarkoittaa koodimuutosten automaattista havaitsemista ja käyttöönottoa, jotta päivitykset näkyvät sovelluksessa ilman manuaalista palvelimen käynnistämistä. Tähän prosessiin liittyy tyypillisesti Java-luokkien ja verkkoresurssien päivittäminen lennossa.

WebforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston uudelleenluontia aina, kun koodissa tehdään muutoksia.

Muutoksia Java-luokissa ja resurssien luokassa seurataan tyypillisesti IDE:llä. Kun Java-luokkaa muokataan ja tiedosto tallennetaan joko IDE:n toimesta automaattisesti tai kehittäjän toimesta manuaalisesti, nämä työkalut aktivoituvat kääntämään ja sijoittamaan päivitetyt luokkatehtävät kohdekansioon muutosten soveltamiseksi.

Parhaan kokemuksen saavuttamiseksi käytä automaattista uudelleenversiointia yhdessä työkalujen tai asetusten kanssa, jotka automatisoivat selaimen lataamisen.

## Live reload {#live-reload}

Kun muutokset on otettu käyttöön, live reload lataa sovelluksen automaattisesti uudelleen, jotta selain heijastaa päivityksiä välittömästi ilman manuaalista selaimen päivittämistä.

WebforJ-sovelluksessa live reload voi automaattisesti päivittää näkymän, renderöimällä komponentteja uudelleen näyttämään sovelluksen viimeisimmän tilan, tai jopa korjata muutoksia tarpeen mukaan pyynnöstä.

## Aiheita {#topics}

<DocCardList className="topics-section" />
