---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
Tehokkaat kehitysprosessit riippuvat työkaluista, jotka havaitsevat koodimuutoksia ja päivittävät sovelluksen automaattisesti reaaliajassa. Jatkuva julkaisu (Continuous Deployment) ja dynaaminen uudelleenlataus (Dynamic Reload) toimivat yhdessä yksinkertaistaen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutoksesi nopeasti ilman, että sinun tarvitsee käynnistää palvelinta manuaalisesti uudelleen.

## Uudelleenjulkaisu {#redeployment}

Uudelleenjulkaisu Java-kehityksessä viittaa koodimuutosten automaattiseen havaitsemiseen ja julkaisemiseen, jotta päivitykset heijastuvat sovelluksessa ilman manuaalista palvelimen uudelleenkäynnistystä. Tämä prosessi sisältää tyypillisesti Java-luokkien ja verkkoresurssien päivittämisen lennossa.

webforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston uudelleenluontia aina, kun koodissa tehdään muutoksia.

Muutoksia Java-luokkiin ja resursseihin luokkapolussa seurataan tyypillisesti IDE:ssä. Kun Java-luokkaa muokataan ja tiedosto tallennetaan, joko IDE:n toimesta automaattisesti tai kehittäjän toimesta manuaalisesti, nämä työkalut aktivoituvat kääntämään ja sijoittamaan päivitetyt luokkafileet kohdekansioon sovelluksen muutosten soveltamiseksi.

Työkaluja ja asetuksia, jotka automatisoivat tai optimoivat selainreloadingia, voidaan lisätä sujuvamman käyttökokemuksen saavuttamiseksi.

## Eli uudelleenlataus {#live-reload}

Eli uudelleenlataus varmistaa, että kun muutoksia julkaistaan, selain heijastaa näitä päivityksiä reaaliajassa ilman manuaalista selainpäivitystä.

webforJ-sovelluksessa eli uudelleenlataus voi automaattisesti päivittää näkymän, uudelleenrenderöimällä komponentteja näyttämään sovelluksen viimeisimmän tilan tai jopa soveltamalla muutoksia tarpeen mukaan.

## Aiheet {#topics}

<DocCardList className="topics-section" />
